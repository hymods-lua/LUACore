package com.lua.core.module;

import com.hypixel.hytale.assetstore.codec.AssetCodec;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
import com.lua.core.LUA;
import com.lua.core.LuaContext;
import com.lua.core.config.LuaConfigManager;
import com.lua.core.config.asset.LuaConfigAsset;
import com.lua.core.config.asset.LuaConfigCodec;
import com.lua.core.managers.LuaManagerContainer;

public class LuaConfigModule implements LuaModule {
    @Override
    public String getId() { return "LuaConfig"; }
    private final LuaConfigManager configManager = LuaManagerContainer.getConfigManager();

    @Override
    public void onSetup(LuaContext context) {
        String modPath = "Lua/Config";

        // Hytale Official Registry
        context.getPlugin().getAssetRegistry().register(
                HytaleAssetStore.builder(LuaConfigAsset.class, new DefaultAssetMap<>())
                        .setPath(modPath)
                        .setCodec((AssetCodec<String, LuaConfigAsset>) LuaConfigCodec.CODEC)
                        .setKeyFunction(LuaConfigAsset::getId)
                        .build()
        );

        LUA.logger.info("LuaConfigModule onSetup: Registrando almacén de configuraciones.");
    }

    @Override
    public void onStart(LuaContext context) {
        configManager.init();
    }

    @Override
    public void onShutdown(LuaContext context) {
        //
    }
}
