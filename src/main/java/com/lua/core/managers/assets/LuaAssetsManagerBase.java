package com.lua.core.managers.assets;

import com.hypixel.hytale.assetstore.AssetMap;
import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.AssetStore;
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.ExtraInfo;
import com.lua.core.LUA;
import com.lua.core.LuaContext;
import com.lua.core.managers.assets.live.LuaLiveState;
import com.lua.core.managers.assets.live.LuaLiveStorage;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Generic base for managing Hytale Assets using String IDs.
 * @param <T> The Asset type (e.g., EcoMarketItemAsset)
 */
public abstract class LuaAssetsManagerBase<T extends JsonAssetWithMap<String, M>, M extends AssetMap<String, T>> {
    protected final LuaContext luaContext;
    protected final Class<T> assetClass;
    protected final Codec<T> codec;
    protected LuaLiveStorage<LuaLiveState> liveStorage;

    protected LuaAssetsManagerBase(LuaContext luaContext, Class<T> assetClass, Codec<T> codec) {
        this.luaContext = luaContext;
        this.assetClass = assetClass;
        this.codec = codec;
    }

    /**
     * Retrieves the Store from the global Hytale registry.
     */
    @SuppressWarnings("unchecked")
    public AssetStore<String, T, M> getStore() {
        return (AssetStore<String, T, M>) AssetRegistry.getAssetStore(assetClass);
    }

    public Optional<T> getById(String id) {
        AssetStore<String, T, M> store = getStore();
        if (store == null || store.getAssetMap() == null) return Optional.empty();
        return Optional.ofNullable(store.getAssetMap().getAsset(id));
    }

    public Collection<T> getAll() {
        AssetStore<String, T, M> store = getStore();
        if (store == null || store.getAssetMap() == null) return Collections.emptyList();
        return store.getAssetMap().getAssetMap().values();
    }

    // Encodes asset to JSON using the provided codec
    public String toJson(T asset, ExtraInfo info) {
        if (codec == null) return "{}";
        return codec.encode(asset, info).toString();
    }

    public void remove(String id) {
        AssetStore<String, T, M> store = getStore();
        if (store != null) {
            store.removeAssets(java.util.List.of(id));
            LUA.logger.info("[LUA] Asset removido de RAM: " + id);
        }
    }

    public void reload() {
        AssetStore<String, T, M> store = getStore();
        if (store == null) return;

        // El nombre del pack es el nombre de tu mod/plugin
        String packName = luaContext.getPlugin().getManifest().getName();

        store.removeAssetPack(packName);

        LUA.logger.info("[LUA] Pack '" + packName + "' purgado para recarga.");
    }

    public void save(T asset) {
        String fileName = asset.getId().contains(":")
                ? asset.getId().split(":")[1] + ".json"
                : asset.getId() + ".json";

        saveToDisk(fileName, asset);
    }

    /**
     * Guarda un asset en el disco (JSON) y lo actualiza en la memoria automáticamente.
     * @param fileName El nombre del archivo (ej: "wood_market.json")
     */
    public void saveToDisk(String fileName, T asset) {
        AssetStore<String, T, M> store = getStore();
        if (store == null) return;

        try {
            // Necesitamos el objeto AssetPack de Hytale
            // Se puede obtener del AssetModule que ya usamos antes
            var assetModule = com.hypixel.hytale.server.core.asset.AssetModule.get();
            var pack = assetModule.getAssetPack(luaContext.getPlugin().getManifest().getName());

            if (pack != null) {
                Map<Path, T> updateMap = new java.util.HashMap<>();
                updateMap.put(java.nio.file.Paths.get(fileName), asset);

                // Esto escribe el JSON y llama a loadAssets() internamente
                store.writeAssetToDisk(pack, updateMap);

                LUA.logger.info("[LUA] Asset persistido en disco: " + asset.getId());
            }
        } catch (java.io.IOException e) {
            LUA.logger.error("[LUA] Error al escribir asset en disco", e);
        }
    }

    public <S extends LuaLiveState> void seed(LuaLiveStorage<S> storage, Function<T, S> factory) {
        Collection<T> assets = getAll();
        int newCount = 0;
        int updatedCount = 0;

        for (T asset : assets) {
            if (!storage.exists(asset.getId())) {
                storage.save(factory.apply(asset));
                newCount++;
            } else {
                // Si ya existe, podrías querer refrescar la data desde el JSON
                updatedCount++;
            }
        }
        LUA.logger.info("[LUA] Sincronización de " + assetClass.getSimpleName() + ": " + newCount + " nuevos, " + updatedCount + " actualizados.");
    }
}