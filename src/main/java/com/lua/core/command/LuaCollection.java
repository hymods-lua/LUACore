package com.lua.core.command;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.lua.core.command.configcommand.LuaConfigCollection;
import com.lua.core.command.uicommand.LuaUICollection;

public class LuaCollection extends AbstractCommandCollection {
    public LuaCollection() {
        super("lua", "Lua commands");
        // Commands
        this.addSubCommand(new LuaConfigCollection());
        this.addSubCommand(new LuaUICollection());
    }
}
