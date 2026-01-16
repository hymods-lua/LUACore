package com.lua.core.bootstrap;

import com.lua.core.Context;
import com.lua.core.module.Module;

import java.util.LinkedHashMap;
import java.util.Map;

public class Bootstrap {
    private final Context context;
    private final Map<Class<? extends Module>, Module> modules = new LinkedHashMap<>();

    public Bootstrap(Context context) {
        this.context = context;
    }

    public void addModule(Module module) {
        modules.put(module.getClass(), module);
    }

    public void launchSetup() {
        modules.values().forEach(m -> m.onSetup(context));
    }

    public void launchStart() {
        modules.values().forEach(m -> m.onStart(context));
    }

    public void launchShutdown() {
        modules.values().forEach(m -> m.onShutdown(context));
    }
}
