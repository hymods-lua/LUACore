package com.lua.core;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.lua.core.config.LuaConfigManager;
import com.lua.core.logger.LuaLogger;

import java.util.function.Supplier;

// Core static access point for plugin context and utilities
public class LUA {
    private static LuaContext luaContext;
    public static LuaLogger logger;

    /**
     * Initializes the Core. Call this within the Main constructor.
     */
    public static void init(JavaPlugin plugin) {
        if (luaContext == null) {
            luaContext = new LuaContext(plugin);
            logger = new LuaLogger("LUA");
        }
    }

    public static LuaContext getContext() {
        if (luaContext == null) throw new IllegalStateException("LUA no ha sido inicializado.");
        return luaContext;
    }

    // Quick access to the main plugin instance
    public static JavaPlugin getPlugin() { return getContext().getPlugin(); }

    // Asset/Service manager helper
    public static <T> T get(Class<T> clazz, java.util.function.Supplier<T> supplier) {
        return getContext().getAssetManager(clazz, supplier);
    }

    /**
     * ACCESO GLOBAL A CONFIGURACIONES
     * @param id El ID del asset (ej: "ecomarket:settings")
     * @param codec El codec del mod para convertir el Bson
     * @param defaultSupplier Función que devuelve valores por defecto si el JSON no existe
     */
    public static <T> T config(String id, Codec<T> codec, Supplier<T> defaultSupplier) {
        LuaConfigManager mgr = get(LuaConfigManager.class, () -> new LuaConfigManager(getContext()));
        return mgr.getConfig(id, codec, defaultSupplier);
    }
}