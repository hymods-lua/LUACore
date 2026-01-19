package com.lua.core.config.asset;

import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
import org.bson.BsonDocument;

public class LuaConfigAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, LuaConfigAsset>> {
    private String id;
    private BsonDocument rawData = new BsonDocument();

    public LuaConfigAsset() { }

    @Override public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public BsonDocument getRawData() { return rawData; }
    public void setRawData(BsonDocument rawData) { this.rawData = rawData; }
}
