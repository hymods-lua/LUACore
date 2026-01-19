package com.lua.core.command.uicommand;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.lua.core.ui.LuaUI;
import javax.annotation.Nonnull;
import com.hypixel.hytale.component.Ref;
import java.util.concurrent.CompletableFuture;


public abstract class LuaUICommand extends AbstractAsyncCommand {

    public LuaUICommand(String name, String description) {
        super(name, description);
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
        // 1. Validar instancia de Player
        if (!(context.sender() instanceof Player player)) {
            return CompletableFuture.completedFuture(null);
        }

        // 2. Obtener World y Ref por separado para checar nulos
        World world = player.getWorld();
        Ref<EntityStore> ref = player.getReference();

        // Si el mundo o la referencia no son válidos, abortamos (Evita NPE)
        if (world == null || ref == null || !ref.isValid()) {
            return CompletableFuture.completedFuture(null);
        }

        // 3. Ejecutar de forma segura
        world.execute(() -> {
            try {
                // Obtenemos el PlayerRef a través de nuestra utilidad
                PlayerRef pRef = LuaUI.getPlayerRef(player);
                if (pRef == null) return;

                if (canOpen(context, pRef)) {
                    CustomUIPage page = createPage(pRef);
                    // Usamos las variables que ya validamos arriba (ref y world)
                    player.getPageManager().openCustomPage(ref, ref.getStore(), page);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return CompletableFuture.completedFuture(null);
    }

    /**
     * Define la instancia de la página (Ej: return new MyPage(pRef))
     */
    protected abstract CustomUIPage createPage(PlayerRef pRef);

    /**
     * Sobreescribir si necesitas validar permisos antes de abrir
     */
    protected boolean canOpen(CommandContext context, PlayerRef pRef) {
        return true;
    }
}
