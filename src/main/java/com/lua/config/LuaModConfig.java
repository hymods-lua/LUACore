package com.lua.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class LuaModConfig {
    public boolean debugMode = false;
    public String prefix = "[LUA]";
    public double updateRate = 1.0;

    public static final BuilderCodec<LuaModConfig> CODEC = BuilderCodec.builder(LuaModConfig.class, LuaModConfig::new)
            .append(new KeyedCodec<>("DebugMode", Codec.BOOLEAN), (s, v) -> s.debugMode = v, s -> s.debugMode).add()
            .append(new KeyedCodec<>("Prefix", Codec.STRING), (s, v) -> s.prefix = v, s -> s.prefix).add()
            .append(new KeyedCodec<>("UpdateRate", Codec.DOUBLE), (s, v) -> s.updateRate = v, s -> s.updateRate).add()
            .build();
}
