package com.lua.core.command.configcommand.sub;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.lua.core.config.LuaConfigManager;
import com.lua.core.managers.LuaManager;
import com.lua.core.utils.LuaText;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class LuaGetConfig extends AbstractCommand {
    private final OptionalArg<Integer> max;

    public LuaGetConfig() {
        super("all", "Get all config ");
        max = withOptionalArg("key", "Key JSON", ArgTypes.INTEGER);
    }

    @Override
    @Nullable
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
        Integer keyMax = max.get(context);
        if (keyMax == null) { keyMax = 5; }
        LuaConfigManager manager = LuaManager.getConfigManager();

        var allItems = manager.getAll();
        context.sendMessage(Message.raw("Total de items registrados en Market: " + allItems.size()));
        context.sendMessage(LuaText.join(
                LuaText.color(String.valueOf(allItems.size()), LuaText.AQUA),
                LuaText.color(" total configurations founded ", LuaText.GRAY)
        ));
        // Listamos los primeros 5 para no saturar el chat
        context.sendMessage(LuaText.join(
                LuaText.color(keyMax.toString(), LuaText.AQUA),
                LuaText.color(" configurations founded ", LuaText.GRAY)
        ));
        allItems.stream().limit(keyMax).forEach(item -> {
            // Creamos una línea con hover o estilo
            Message line = LuaText.join(
                    LuaText.prefix(),
                    LuaText.color(" - ", LuaText.GRAY),
                    LuaText.color(item.getId(), LuaText.AQUA)
            );
            context.sendMessage(line);
        });

        return CompletableFuture.completedFuture(null);
    }
}
