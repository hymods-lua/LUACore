package com.lua.core;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.lua.core.logger.Logger;

// Core static access point for plugin context and utilities
public class LUA {
    private static Context context;
    public static Logger logger;

    /**
     * Initializes the Core. Call this within the Main constructor.
     */
    public static void init(JavaPlugin plugin) {
        if (context == null) {
            context = new Context(plugin);
            logger = new Logger("LUA");
        }
    }

    public static Context getContext() {
        if (context == null) throw new IllegalStateException("LUA no ha sido inicializado.");
        return context;
    }

    // Quick access to the main plugin instance
    public static JavaPlugin getPlugin() { return getContext().getPlugin(); }

    // Asset/Service manager helper
    public static <T> T get(Class<T> clazz, java.util.function.Supplier<T> supplier) {
        return getContext().getAssetManager(clazz, supplier);
    }
}