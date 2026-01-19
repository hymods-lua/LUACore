package com.lua.core.config.asset;

import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
import com.hypixel.hytale.assetstore.codec.AssetCodec;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import org.bson.BsonDocument;

public class LuaConfigCodec {

    public static final KeyedCodec<BsonDocument> JSON_DATA_KEY = new KeyedCodec<>("Config", Codec.BSON_DOCUMENT);

    public static final AssetCodec<String, LuaConfigAsset> CODEC = AssetBuilderCodec.builder(
                LuaConfigAsset.class,
                LuaConfigAsset::new,
                Codec.STRING,
                LuaConfigAsset::setId,
                LuaConfigAsset::getId,
                (asset, data) -> {},
                (asset) -> null
        )
        .append(JSON_DATA_KEY,
                LuaConfigAsset::setRawData,
                LuaConfigAsset::getRawData).add()
        .build();
}
