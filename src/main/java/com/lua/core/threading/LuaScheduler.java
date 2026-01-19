package com.lua.core.threading;

import com.hypixel.hytale.server.core.universe.world.World;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LuaScheduler {
    /**
     * Ejecuta algo en un hilo asíncrono (fuera del tick del mundo).
     * Útil para: Consultas a Base de Datos, peticiones Web, cálculos pesados.
     */
    public static <T> CompletableFuture<T> runAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

    /**
     * Ejecuta algo de forma síncrona en un mundo específico.
     * Hytale requiere esto para modificar bloques, entidades o componentes.
     */
    public static void runSync(World world, Runnable task) {
        world.execute(task);
    }

    /**
     * Utilidad Maestra: Ejecuta algo asíncrono y al terminar vuelve al hilo del mundo.
     * Ejemplo: Cargar datos de SQL y luego dárselos al jugador.
     */
    public static <T> void asyncThenSync(Supplier<T> asyncTask, World world, Consumer<T> syncCallback) {
        runAsync(asyncTask).thenAccept(result -> {
            runSync(world, () -> syncCallback.accept(result));
        });
    }
}
