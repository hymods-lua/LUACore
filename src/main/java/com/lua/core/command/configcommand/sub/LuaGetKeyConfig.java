package com.lua.core.command.configcommand.sub;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.lua.core.config.LuaConfigManager;
import com.lua.core.managers.LuaManager;
import com.lua.core.utils.LuaText;
import com.lua.core.utils.LuaJsonUtil;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class LuaGetKeyConfig extends AbstractCommand {
    private final RequiredArg<String> configArg;
    private final OptionalArg<String> keyArg; // Ahora es Opcional

    public LuaGetKeyConfig() {
        super("get", "Obtiene la configuración completa o una llave específica");

        configArg = withRequiredArg("config", "ID de la Configuración", ArgTypes.STRING);
        keyArg = withOptionalArg("key", "Ruta de la llave (opcional)", ArgTypes.STRING);
    }

    @Override
    @Nullable
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
        String id = configArg.get(context);
        LuaConfigManager manager = LuaManager.getConfigManager();

        manager.getById(id).ifPresentOrElse(asset -> {
            BsonDocument doc = asset.getRawData();

            // Verificamos si el usuario proporcionó la llave
            if (context.provided(keyArg)) {
                // CASO A: Buscar llave específica
                String keyPath = keyArg.get(context);
                BsonValue foundValue = LuaJsonUtil.getValueByPath(doc, keyPath);

                if (foundValue != null) {
                    context.sendMessage(LuaText.join(
                            LuaText.prefix(),
                            LuaText.color("Llave [", LuaText.WHITE),
                            LuaText.color(keyPath, LuaText.AQUA),
                            LuaText.color("]: ", LuaText.WHITE),
                            Message.raw(LuaJsonUtil.formatBsonValue(foundValue))
                    ));
                } else {
                    context.sendMessage(LuaText.color("La llave '" + keyPath + "' no existe en " + id, LuaText.RED));
                }
            } else {
                // CASO B: Retornar todo el JSON
                context.sendMessage(LuaText.join(
                        LuaText.prefix(),
                        LuaText.color("Configuración completa de: ", LuaText.WHITE),
                        LuaText.color(id, LuaText.GOLD)
                ));

                // Imprimimos el BSON convertido a String legible
                context.sendMessage(Message.raw( doc.toString() ).color(LuaText.GOLD));
            }

        }, () -> {
            context.sendMessage(LuaText.color("La configuración '" + id + "' no existe.", LuaText.RED));
        });

        return CompletableFuture.completedFuture(null);
    }

}
