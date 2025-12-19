// src/main/java/me/xarta/xadvancementannouncer/util/MessageFormatter.java
package me.xarta.xadvancementannouncer.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class MessageFormatter {

    /**
     * Format the message with placeholders (legacy method for string-only)
     */
    public static String format(String template, String playerName, String advancementName) {
        return template
                .replace("%player%", playerName)
                .replace("%advancement%", advancementName)
                .replace("%challenge%", advancementName)
                .replace("%goal%", advancementName);
    }

    /**
     * Build a Component message with proper localization support
     */
    public static Component buildComponent(String format, String playerName, Component advancementTitle) {
        MutableComponent result = Component.empty();
        StringBuilder current = new StringBuilder();
        ChatFormatting currentFormat = ChatFormatting.WHITE;
        boolean bold = false;
        boolean italic = false;

        for (int i = 0; i < format.length(); i++) {
            char c = format.charAt(i);

            // Check for placeholder
            if (c == '%' && i + 1 < format.length()) {
                int endIndex = format.indexOf('%', i + 1);
                if (endIndex != -1) {
                    String placeholder = format.substring(i + 1, endIndex);

                    // Append current text first
                    if (current.length() > 0) {
                        MutableComponent part = Component.literal(current.toString());
                        part.withStyle(currentFormat);
                        if (bold) part.withStyle(ChatFormatting.BOLD);
                        if (italic) part.withStyle(ChatFormatting.ITALIC);
                        result.append(part);
                        current = new StringBuilder();
                    }

                    // Add the appropriate component
                    if (placeholder.equals("player")) {
                        MutableComponent playerPart = Component.literal(playerName);
                        playerPart.withStyle(currentFormat);
                        if (bold) playerPart.withStyle(ChatFormatting.BOLD);
                        if (italic) playerPart.withStyle(ChatFormatting.ITALIC);
                        result.append(playerPart);
                    } else if (placeholder.equals("advancement") || placeholder.equals("challenge") || placeholder.equals("goal")) {
                        // Append the translatable advancement title with current formatting
                        MutableComponent titleCopy = advancementTitle.copy();
                        titleCopy.withStyle(currentFormat);
                        if (bold) titleCopy.withStyle(ChatFormatting.BOLD);
                        if (italic) titleCopy.withStyle(ChatFormatting.ITALIC);
                        result.append(titleCopy);
                    }

                    i = endIndex; // Skip to the end of placeholder
                    continue;
                }
            }

            // Check for color code
            if (c == '&' && i + 1 < format.length()) {
                // Append current text before applying new format
                if (current.length() > 0) {
                    MutableComponent part = Component.literal(current.toString());
                    part.withStyle(currentFormat);
                    if (bold) part.withStyle(ChatFormatting.BOLD);
                    if (italic) part.withStyle(ChatFormatting.ITALIC);
                    result.append(part);
                    current = new StringBuilder();
                }

                char code = format.charAt(i + 1);
                switch (code) {
                    case '0' -> currentFormat = ChatFormatting.BLACK;
                    case '1' -> currentFormat = ChatFormatting.DARK_BLUE;
                    case '2' -> currentFormat = ChatFormatting.DARK_GREEN;
                    case '3' -> currentFormat = ChatFormatting.DARK_AQUA;
                    case '4' -> currentFormat = ChatFormatting.DARK_RED;
                    case '5' -> currentFormat = ChatFormatting.DARK_PURPLE;
                    case '6' -> currentFormat = ChatFormatting.GOLD;
                    case '7' -> currentFormat = ChatFormatting.GRAY;
                    case '8' -> currentFormat = ChatFormatting.DARK_GRAY;
                    case '9' -> currentFormat = ChatFormatting.BLUE;
                    case 'a' -> currentFormat = ChatFormatting.GREEN;
                    case 'b' -> currentFormat = ChatFormatting.AQUA;
                    case 'c' -> currentFormat = ChatFormatting.RED;
                    case 'd' -> currentFormat = ChatFormatting.LIGHT_PURPLE;
                    case 'e' -> currentFormat = ChatFormatting.YELLOW;
                    case 'f' -> currentFormat = ChatFormatting.WHITE;
                    case 'l' -> bold = true;
                    case 'o' -> italic = true;
                    case 'r' -> {
                        currentFormat = ChatFormatting.WHITE;
                        bold = false;
                        italic = false;
                    }
                    default -> {
                        current.append(c);
                        continue;
                    }
                }
                i++; // Skip the format code
            } else {
                current.append(c);
            }
        }

        // Append remaining text
        if (current.length() > 0) {
            MutableComponent part = Component.literal(current.toString());
            part.withStyle(currentFormat);
            if (bold) part.withStyle(ChatFormatting.BOLD);
            if (italic) part.withStyle(ChatFormatting.ITALIC);
            result.append(part);
        }

        return result;
    }

    /**
     * Convert color-coded string to Minecraft Component (legacy method)
     */
    public static Component toComponent(String message) {
        MutableComponent result = Component.empty();
        StringBuilder current = new StringBuilder();
        ChatFormatting currentFormat = ChatFormatting.WHITE;
        boolean bold = false;
        boolean italic = false;

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);

            if (c == '&' && i + 1 < message.length()) {
                // Append current text before applying new format
                if (current.length() > 0) {
                    MutableComponent part = Component.literal(current.toString());
                    part.withStyle(currentFormat);
                    if (bold) part.withStyle(ChatFormatting.BOLD);
                    if (italic) part.withStyle(ChatFormatting.ITALIC);
                    result.append(part);
                    current = new StringBuilder();
                }

                char code = message.charAt(i + 1);
                switch (code) {
                    case '0' -> currentFormat = ChatFormatting.BLACK;
                    case '1' -> currentFormat = ChatFormatting.DARK_BLUE;
                    case '2' -> currentFormat = ChatFormatting.DARK_GREEN;
                    case '3' -> currentFormat = ChatFormatting.DARK_AQUA;
                    case '4' -> currentFormat = ChatFormatting.DARK_RED;
                    case '5' -> currentFormat = ChatFormatting.DARK_PURPLE;
                    case '6' -> currentFormat = ChatFormatting.GOLD;
                    case '7' -> currentFormat = ChatFormatting.GRAY;
                    case '8' -> currentFormat = ChatFormatting.DARK_GRAY;
                    case '9' -> currentFormat = ChatFormatting.BLUE;
                    case 'a' -> currentFormat = ChatFormatting.GREEN;
                    case 'b' -> currentFormat = ChatFormatting.AQUA;
                    case 'c' -> currentFormat = ChatFormatting.RED;
                    case 'd' -> currentFormat = ChatFormatting.LIGHT_PURPLE;
                    case 'e' -> currentFormat = ChatFormatting.YELLOW;
                    case 'f' -> currentFormat = ChatFormatting.WHITE;
                    case 'l' -> bold = true;
                    case 'o' -> italic = true;
                    case 'r' -> {
                        currentFormat = ChatFormatting.WHITE;
                        bold = false;
                        italic = false;
                    }
                    default -> {
                        current.append(c);
                        continue;
                    }
                }
                i++; // Skip the format code
            } else {
                current.append(c);
            }
        }

        // Append remaining text
        if (current.length() > 0) {
            MutableComponent part = Component.literal(current.toString());
            part.withStyle(currentFormat);
            if (bold) part.withStyle(ChatFormatting.BOLD);
            if (italic) part.withStyle(ChatFormatting.ITALIC);
            result.append(part);
        }

        return result;
    }
}