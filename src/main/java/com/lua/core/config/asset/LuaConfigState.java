package com.lua.core.config.asset;

import com.lua.core.managers.assets.live.LuaLiveState;
import org.bson.BsonDocument;

public class LuaConfigState implements LuaLiveState {

    private final String id;
    private BsonDocument rawData;

    public LuaConfigState(String id, BsonDocument rawData) {
        this.id = id;
        this.rawData = rawData;
    }

    @Override public String getUniqueId() { return id; }

    public BsonDocument getRawData() {
        return rawData;
    }
}
