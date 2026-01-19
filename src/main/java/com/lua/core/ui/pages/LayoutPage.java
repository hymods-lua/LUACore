package com.lua.core.ui.pages;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.lua.core.ui.LuaPage;
import com.lua.core.utils.LuaText;

import javax.annotation.Nonnull;
import java.awt.*;


public class LayoutPage  extends LuaPage<LayoutPage.Data> {
    public LayoutPage(@Nonnull PlayerRef playerRef) {
        super(playerRef, Data.CODEC);
    }

    /**
     * Asegúrate de que el nombre del archivo coincida con el que guardaste
     * en resources/Server/UI/test_gui.ui
     */
    @Override
    protected String getFilePath() {
        return "Pages/layout.ui";
    }

    @Override
    protected void onBuild(UICommandBuilder cmd, UIEventBuilder events) {
        setText(cmd, "#Labelzqqc6", "SISTEMA LUA ACTIVO");
        bindClick(events, "#TextButtonovpo3", "action_accept");
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull Data data) {
        if ("action_accept".equals(data.action)) {
            // Lógica cuando el jugador presiona ACEPTAR
            this.playerRef.sendMessage(LuaText.color("Has aceptado los términos de LUA.", LuaText.GREEN));

            // Cerramos la página
            this.closePage();
        }
    }

    /**
     * DTO para recibir los eventos del cliente
     */
    public static class Data {
        public String action;

        public static final BuilderCodec<Data> CODEC = BuilderCodec.builder(Data.class, Data::new)
                .append(new KeyedCodec<>("Action", Codec.STRING),
                        (d, s) -> d.action = s,
                        d -> d.action)
                .add()
                .build();
    }
}
