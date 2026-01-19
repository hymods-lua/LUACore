package com.lua.core.ui;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

/**
 * Base adaptable para interfaces LUA.
 * Simplifica la construcción de la UI y el registro de eventos.
 * @param <T> Clase de datos para los eventos (Dada por el Codec).
 */
public abstract class LuaPage<T> extends InteractiveCustomUIPage<T> {

    public LuaPage(@Nonnull PlayerRef playerRef, @Nonnull BuilderCodec<T> eventDataCodec) {
        super(playerRef, CustomPageLifetime.CanDismiss, eventDataCodec);
    }

    /**
     * Implementación base del build de Hytale.
     * Redirige a onBuild para un uso más limpio.
     */
    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder uiCommandBuilder,
                      @Nonnull UIEventBuilder uiEventBuilder, @Nonnull Store<EntityStore> store) {

        // 1. Cargamos el archivo .ui base
        uiCommandBuilder.append(getFilePath());

        // 2. Ejecutamos la lógica de construcción personalizada
        onBuild(uiCommandBuilder, uiEventBuilder);
    }

    /**
     * Define la ruta del archivo .ui (ej: "Market/market_main.ui")
     */
    protected abstract String getFilePath();

    /**
     * Aquí es donde colocarás tus setText, bindEvents, etc.
     */
    protected abstract void onBuild(UICommandBuilder cmd, UIEventBuilder events);

    // --- HELPERS PARA CODIGO LIMPIO ---

    /**
     * Setea el texto de un elemento.
     */
    protected void setText(UICommandBuilder cmd, String elementId, String text) {
        cmd.set(elementId + ".Text", text);
    }

    /**
     * Cambia el color del texto (Hexadecimal).
     */
    protected void setTextColor(UICommandBuilder cmd, String elementId, String hexColor) {
        cmd.set(elementId + ".Style.TextColor", hexColor);
    }

    /**
     * Muestra u oculta un elemento.
     */
    protected void setVisible(UICommandBuilder cmd, String elementId, boolean visible) {
        cmd.set(elementId + ".Visible", visible);
    }

    /**
     * Helper rápido para registrar un evento de clic (Activating).
     * @param events Builder de eventos.
     * @param elementId id del elemento en el .ui (ej: "#BtnBuy").
     * @param actionName El nombre de la acción que llegará al handleDataEvent.
     */
    protected void bindClick(UIEventBuilder events, String elementId, String actionName) {
        events.addEventBinding(CustomUIEventBindingType.Activating, elementId, EventData.of("Action", actionName));
    }

    /**
     * Cierra la ventana.
     */
    public void closePage() {
        this.close();
    }
}