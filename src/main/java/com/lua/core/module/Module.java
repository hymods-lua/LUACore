package com.lua.core.module;

import com.lua.core.Context;

public interface Module {
    String getId();

    /**
     * Fase 1: Registro de Codecs, Assets y configuración inicial.
     */
    void onSetup(Context context);
    /**
     * Fase 2: Registro de Comandos, Listeners y tareas programadas.
     */
    void onStart(Context context);
    /**
     * Fase 3: Guardado de datos y limpieza.
     */
    void onShutdown(Context context);
}
