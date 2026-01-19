package com.lua.core.module;

import com.lua.core.LuaContext;

public interface LuaModule {
    String getId();

    /**
     * Fase 1: Registro de Codecs, Assets y configuración inicial.
     */
    void onSetup(LuaContext luaContext);
    /**
     * Fase 2: Registro de Comandos, Listeners y tareas programadas.
     */
    void onStart(LuaContext luaContext);
    /**
     * Fase 3: Guardado de datos y limpieza.
     */
    void onShutdown(LuaContext luaContext);
}
