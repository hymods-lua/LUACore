package com.lua.core.command.uicommand.sub;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.lua.core.command.uicommand.LuaUICommand;
import com.lua.core.ui.pages.LayoutPage;
import com.lua.core.ui.pages.LuaSettingsPage;

public class LuaUISettingsCommand extends LuaUICommand {

    public LuaUISettingsCommand() {
        super("settings", "Abre la configuración de LUA");
    }

    @Override
    protected CustomUIPage createPage(PlayerRef pRef) {
        return new LayoutPage(pRef);
    }
}
