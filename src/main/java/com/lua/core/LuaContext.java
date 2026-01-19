package com.lua.core;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

// Manages plugin state and singleton instances
public class LuaContext {
    private final JavaPlugin plugin;
    private final Map<Class<?>, Object> managers = new HashMap<>();

    public LuaContext(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public String getVersion() {
        return plugin.getManifest().getVersion().toString();
    }

    // Retrieves or lazily creates a singleton manager instance
    public <T> T getAssetManager(Class<T> clazz, java.util.function.Supplier<T> supplier) {
        return clazz.cast(managers.computeIfAbsent(clazz, k -> {
            LUA.logger.info("Starting new Manager: " + clazz.getSimpleName());
            return supplier.get();
        }));
    }

    public JavaPlugin getPlugin() { return plugin; }
}