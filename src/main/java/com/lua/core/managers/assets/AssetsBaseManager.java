package com.lua.core.managers.assets;

import com.hypixel.hytale.assetstore.AssetMap;
import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.AssetStore;
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.ExtraInfo;
import com.lua.core.Context;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Generic base for managing Hytale Assets using String IDs.
 * @param <T> The Asset type (e.g., EcoMarketItemAsset)
 */
public abstract class AssetsBaseManager<T extends JsonAssetWithMap<String, M>, M extends AssetMap<String, T>> {
    protected final Context context;
    protected final Class<T> assetClass;
    protected final Codec<T> codec;

    protected AssetsBaseManager(Context context, Class<T> assetClass, Codec<T> codec) {
        this.context = context;
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
}