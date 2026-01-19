package com.lua.core.utils;

import com.hypixel.hytale.server.core.Message;

import java.awt.*;

public class LuaText {

    // Paleta de colores Hytale (Hexadecimal)
    public static final String GOLD = "#eec11a";
    public static final String RED = "#ff5555";
    public static final String GREEN = "#55ff55";
    public static final String AQUA = "#55ffff";
    public static final String WHITE = "#ffffff";
    public static final String GRAY = "#aaaaaa";

    /**
     * Crea un mensaje con color de forma rápida.
     */
    public static Message color(String text, String hexColor) {
        return Message.raw(text).color(hexColor);
    }

    /**
     * Crea un mensaje con color usando la clase Color de Java.
     */
    public static Message color(String text, Color color) {
        return Message.raw(text).color(color);
    }

    /**
     * Une varios mensajes preservando sus estilos individuales.
     */
    public static Message join(Message... messages) {
        return Message.join(messages);
    }

    /**
     * Helper para el prefijo de LUA.
     */
    public static Message prefix() {
        return color("[LUA] ", "#00fbff").bold(true);
    }
}
