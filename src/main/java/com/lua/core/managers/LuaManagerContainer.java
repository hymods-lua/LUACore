package com.lua.core.managers;

import com.lua.core.LUA;
import com.lua.core.config.LuaConfigManager;

public class LuaManagerContainer {

    public LuaManagerContainer() {}

    public static LuaConfigManager getConfigManager() {
        return LUA.get(LuaConfigManager.class, () -> new LuaConfigManager(LUA.getContext()));
    }
}
