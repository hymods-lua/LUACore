package com.lua.core.logger;

import com.hypixel.hytale.logger.HytaleLogger;
import java.util.logging.Level;

public class Logger {
    private final HytaleLogger internal;

    public Logger(String name) {
        this.internal = HytaleLogger.get(name);
    }

    public void info(String msg) {
        internal.at(Level.INFO).log(msg);
    }

    public void warn(String msg) {
        internal.at(Level.WARNING).log(msg);
    }

    public void error(String msg) {
        internal.at(Level.SEVERE).log(msg);
    }

    public void error(String msg, Throwable t) {
        internal.at(Level.SEVERE).withCause(t).log(msg);
    }

    public void debug(String msg) {
        internal.at(Level.FINE).log(msg);
    }

    public HytaleLogger hytale() {
        return internal;
    }
}