package com.lua.core.command.configcommand.sub;

import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.lua.core.config.LuaConfigManager;
import com.lua.core.managers.LuaManager;
import com.lua.core.utils.LuaText;
import com.lua.core.utils.LuaJsonUtil;

import java.util.concurrent.CompletableFuture;

public class LuaSetConfig extends AbstractCommand {
    private final RequiredArg<String> configArg;
    private final RequiredArg<String> keyArg;
    private final RequiredArg<String> valArg;


    public LuaSetConfig() {
        super("set", "Cambia un valor de la configuración y lo guarda");
        configArg = withRequiredArg("config", "Config ID", ArgTypes.STRING);
        keyArg = withRequiredArg("key", "Key Path (ej: DebugMode)", ArgTypes.STRING);
        valArg = withRequiredArg("value", "Nuevo Valor", ArgTypes.STRING);
    }

    @Override
    protected CompletableFuture<Void> execute(CommandContext context) {
        String id = configArg.get(context);
        String key = keyArg.get(context);
        String value = valArg.get(context);

        LuaConfigManager manager = LuaManager.getConfigManager();

        manager.getById(id).ifPresentOrElse(asset -> {
            // 1. Modificamos el BsonDocument en memoria
            LuaJsonUtil.setValueByPath(asset.getRawData(), key, value);

            // 2. Persistimos al DISCO
            manager.save(asset);
            // 3. Sincronizamos el LiveStorage
            manager.init();

            context.sendMessage(LuaText.join(
                    LuaText.prefix(),
                    LuaText.color("Actualizado: ", LuaText.WHITE),
                    LuaText.color(key, LuaText.GOLD),
                    LuaText.color(" = ", LuaText.WHITE),
                    LuaText.color(value, LuaText.GREEN)
            ));

        }, () -> context.sendMessage(LuaText.color("No existe la config: " + id, LuaText.RED)));

        return CompletableFuture.completedFuture(null);
    }
}
