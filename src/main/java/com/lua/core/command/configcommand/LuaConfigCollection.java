package com.lua.core.command.configcommand;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.lua.core.command.configcommand.sub.LuaGetConfig;
import com.lua.core.command.configcommand.sub.LuaGetKeyConfig;
import com.lua.core.command.configcommand.sub.LuaReloadConfig;
import com.lua.core.command.configcommand.sub.LuaSetConfig;

public class LuaConfigCollection extends AbstractCommandCollection {

    public LuaConfigCollection() {
        super("config", "Config commands");
        // Commands
        this.addSubCommand(new LuaGetConfig());
        this.addSubCommand(new LuaGetKeyConfig());
        this.addSubCommand(new LuaSetConfig());
        this.addSubCommand(new LuaReloadConfig());
    }
}
