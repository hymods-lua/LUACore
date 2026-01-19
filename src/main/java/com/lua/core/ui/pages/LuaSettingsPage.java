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
import com.lua.config.LuaModConfig;
import com.lua.core.LUA;
import com.lua.core.config.LuaConfigManager;
import com.lua.core.ui.LuaPage;

import javax.annotation.Nonnull;

public class LuaSettingsPage extends LuaPage<LuaSettingsPage.Data> {

    private LuaModConfig settings;

    public LuaSettingsPage(PlayerRef playerRef) {
        super(playerRef, Data.CODEC);
        // Cargamos la config actual usando el manager adaptable
        this.settings = LUA.get(LuaConfigManager.class, () -> new LuaConfigManager(LUA.getContext()))
                .bind("lua:core", LuaModConfig.CODEC)
                .orElse(new LuaModConfig());
    }

    @Override
    protected String getFilePath() {
        return "Pages/lua_settings.ui";
    }

    @Override
    protected void onBuild(UICommandBuilder cmd, UIEventBuilder events) {
        setText(cmd, "#DebugValue", settings.debugMode ? "ON" : "OFF");
        setTextColor(cmd, "#DebugValue", settings.debugMode ? "#00FF00" : "#FF0000");

        bindClick(events, "#BtnDebugToggle", "toggle_debug");
        bindClick(events, "#BtnSave", "save");
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull Data data) {
        switch (data.action) {
            case "toggle_debug":
                settings.debugMode = !settings.debugMode;
                this.rebuild();
                break;
            case "save":
                // 1. Aquí usaríamos el saveToDisk que hicimos en el Manager
                // Pero como es un objeto adaptable, primero hay que re-encapsularlo en el Asset
                LUA.logger.info("Guardando configuración de LUA...");
                this.closePage();
                break;
        }
    }

    public static class Data {
        public String action;
        public static final BuilderCodec<Data> CODEC = BuilderCodec.builder(Data.class, Data::new)
                .append(new KeyedCodec<>("Action", Codec.STRING), (d, s) -> d.action = s, d -> d.action).add()
                .build();
    }
}
