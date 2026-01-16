package com.lua;

import javax.annotation.Nonnull;
import com.lua.core.bootstrap.Bootstrap;
import com.lua.core.LUA;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

// Main entry point for the Hytale plugin
public class Main extends JavaPlugin {
    private final Bootstrap bootstrap;

    public Main(@Nonnull JavaPluginInit init) {
        super(init);
        // Initialize global context and the bootstrap logic
        LUA.init(this);
        this.bootstrap = new Bootstrap(LUA.getContext());
    }

    @Override
    protected void setup() {
        // Delegate setup phase to the bootstrap
        bootstrap.launchSetup();
    }

    @Override
    protected void start(){
        // Delegate start phase to the bootstrap
        bootstrap.launchStart();
    }

    @Override
    protected void shutdown(){
        // Delegate shutdown/cleanup phase to the bootstrap
        bootstrap.launchShutdown();
    }

}
