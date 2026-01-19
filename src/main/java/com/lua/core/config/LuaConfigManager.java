package com.lua.core.config;

import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.EmptyExtraInfo;
import com.lua.core.LuaContext;
import com.lua.core.config.asset.LuaConfigAsset;
import com.lua.core.config.asset.LuaConfigCodec;
import com.lua.core.config.asset.LuaConfigState;
import com.lua.core.managers.assets.LuaAssetsManagerBase;
import com.lua.core.managers.assets.live.LuaLiveStorage;
import org.bson.BsonDocument;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LuaConfigManager extends LuaAssetsManagerBase<LuaConfigAsset, DefaultAssetMap<String, LuaConfigAsset>> {

    private final LuaLiveStorage<LuaConfigState> configStorage = new LuaLiveStorage<>();
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    public LuaConfigManager(LuaContext context) {
        super(context, LuaConfigAsset.class, LuaConfigCodec.CODEC);
    }

    public void init() {
        // LLAMADA AL SEEDER GENÉRICO
        this.seed(configStorage, asset -> new LuaConfigState(
            asset.getId(),
            asset.getRawData()
        ));
    }

    /**
     * Obtiene la configuración de forma global y genérica.
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfig(String id, Codec<T> codec, java.util.function.Supplier<T> defaultSupplier) {
        // 1. Si ya está en caché, lo devolvemos (Máxima velocidad)
        if (cache.containsKey(id)) {
            return (T) cache.get(id);
        }

        // 2. Si no, lo bindeamos desde el BsonDocument (RAM)
        T boundObject = bind(id, codec).orElseGet(defaultSupplier);

        // 3. Guardamos en caché para la próxima vez
        cache.put(id, boundObject);
        return boundObject;
    }

    /**
     * Limpia la caché de un objeto (usar cuando se haga un /lua config reload o set)
     */
    public void invalidateCache(String id) {
        cache.remove(id);
    }

    public <T> Optional<T> bind(String id, Codec<T> codec) {
        // 1. Intentar desde la RAM (Live Storage)
        Optional<BsonDocument> liveData = configStorage.get(id).map(state -> state.getRawData());

        // 2. Si no hay nada vivo, intentar desde el AssetStore original
        return liveData.map(bsonDocument -> codec.decode(bsonDocument, EmptyExtraInfo.EMPTY)).or(() -> getById(id).map(asset -> codec.decode(asset.getRawData(), EmptyExtraInfo.EMPTY)));
    }
}
