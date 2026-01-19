package com.lua.core.ui;

import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.component.Ref;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class LuaUI {

    /**
     */
    public static void open(@Nonnull Player player, @Nonnull Supplier<CustomUIPage> pageSupplier) {
        executeSync(player, (ref, store) -> {
            // Se crea la instancia AQUÍ, ya dentro del hilo del mundo
            CustomUIPage page = pageSupplier.get();
            player.getPageManager().openCustomPage(ref, store, page);
        });
    }

    /**
     * Cierra cualquier página personalizada que el jugador tenga abierta.
     */
    public static void close(@Nonnull Player player) {
        executeSync(player, (ref, store) -> {
            player.getPageManager().setPage(ref, store, Page.None);
        });
    }

    /**
     * MÉTODO MAESTRO (Factorización)
     * Extrae el Ref, el Store y el World, y ejecuta la tarea en el hilo correcto.
     */
    private static void executeSync(@Nonnull Player player, UIAction action) {
        Ref<EntityStore> ref = player.getReference();
        if (ref == null || !ref.isValid()) return;

        Store<EntityStore> store = ref.getStore();
        EntityStore entityStore = store.getExternalData();
        World world = entityStore.getWorld();

        // Saltamos al hilo del mundo y pasamos las herramientas necesarias (ref y store)
        world.execute(() -> action.run(ref, store));
    }

    /**
     * Obtiene el PlayerRef (componente ECS) de un Player.
     */
    @Nullable
    public static PlayerRef getPlayerRef(@Nonnull Player player) {
        Ref<EntityStore> ref = player.getReference();
        if (ref == null || !ref.isValid()) return null;
        return ref.getStore().getComponent(ref, PlayerRef.getComponentType());
    }
    /**
     * Obtiene el mundo asociado a un jugador.
     */
    @Nullable
    public static World getWorld(@Nonnull Player player) {
        Ref<EntityStore> ref = player.getReference();
        if (ref == null || !ref.isValid()) return null;
        return ((EntityStore) ref.getStore().getExternalData()).getWorld();
    }

    /**
     * Interfaz funcional para permitir el paso de lambdas con parámetros de Hytale.
     */
    @FunctionalInterface
    private interface UIAction {
        void run(Ref<EntityStore> ref, Store<EntityStore> store);
    }
}
