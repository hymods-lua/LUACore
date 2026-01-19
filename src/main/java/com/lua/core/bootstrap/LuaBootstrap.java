package com.lua.core.bootstrap;

import com.lua.core.LuaContext;
import com.lua.core.module.LuaModule;

import java.util.LinkedHashMap;
import java.util.Map;

public class LuaBootstrap {
    private final LuaContext luaContext;
    private final Map<Class<? extends LuaModule>, LuaModule> modules = new LinkedHashMap<>();

    public LuaBootstrap(LuaContext luaContext) {
        this.luaContext = luaContext;
    }

    public void addModule(LuaModule luaModule) {
        modules.put(luaModule.getClass(), luaModule);
    }

    public void launchSetup() {
        modules.values().forEach(m -> m.onSetup(luaContext));
    }

    public void launchStart() {
        modules.values().forEach(m -> m.onStart(luaContext));
    }

    public void launchShutdown() {
        modules.values().forEach(m -> m.onShutdown(luaContext));
    }
}
