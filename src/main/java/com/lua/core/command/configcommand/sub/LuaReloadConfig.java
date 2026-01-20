package com.lua.core.command.configcommand.sub;

import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.lua.core.config.LuaConfigManager;
import com.lua.core.managers.LuaManagerContainer;
import com.lua.core.utils.LuaText;

import java.util.concurrent.CompletableFuture;

public class LuaReloadConfig extends AbstractCommand {

    private final RequiredArg<String> configArg;

    public LuaReloadConfig() {
        super("reload", "Recarga una configuración desde el disco");
        configArg = withRequiredArg("config", "Config ID", ArgTypes.STRING);
    }

    @Override
    protected CompletableFuture<Void> execute(CommandContext context) {
        String id = configArg.get(context);
        LuaConfigManager manager = LuaManagerContainer.getConfigManager();

        // 1. Purgamos y recargamos del disco a la RAM de Hytale (AssetStore)
        manager.reload();

        // 2. Volvemos a ejecutar el Seeder para actualizar nuestra RAM de LUA (LiveStorage)
        manager.init();

        context.sendMessage(LuaText.join(
                LuaText.prefix(),
                LuaText.color("Configuración ", LuaText.WHITE),
                LuaText.color(id, LuaText.AQUA),
                LuaText.color(" recargada exitosamente.", LuaText.GREEN)
        ));

        return CompletableFuture.completedFuture(null);
    }
}
