package com.lua.core.command.uicommand;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.lua.core.command.uicommand.sub.LuaUISettingsCommand;

public class LuaUICollection extends AbstractCommandCollection {

    public LuaUICollection() {
        super("ui", "Config commands");
        // Commands
        this.addSubCommand(new LuaUISettingsCommand());
    }
}
