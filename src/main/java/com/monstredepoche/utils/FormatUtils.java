package com.monstredepoche.utils;

import com.monstredepoche.entities.monsters.MonsterType;

public class FormatUtils {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";

    public static final String PLAYER1_COLOR = CYAN;
    public static final String PLAYER2_COLOR = PURPLE;

    public static String colorize(String text, String color) {
        return color + text + RESET;
    }

    public static String colorizePlayer(String text, boolean isPlayer1) {
        return colorize(text, isPlayer1 ? PLAYER1_COLOR : PLAYER2_COLOR);
    }

    public static String formatStatus(String status) {
        return switch (status.toUpperCase()) {
            case "PARALYZED" -> colorize("PAR", YELLOW);
            case "BURNED" -> colorize("BRU", RED);
            case "POISONED" -> colorize("PSN", PURPLE);
            default -> "";
        };
    }

    public static String formatHealthBar(int current, int max) {
        int barLength = 20;
        int filledLength = (int)((double)current / max * barLength);
        StringBuilder bar = new StringBuilder("[");
        
        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                bar.append(colorize("█", GREEN));
            } else {
                bar.append(colorize("░", RED));
            }
        }
        bar.append(String.format("] %d/%d", current, max));
        return bar.toString();
    }

    public static String formatBattleHeader(String text) {
        // On retire les codes ANSI pour calculer la vraie longueur
        String visibleText = text.replaceAll("\u001B\\[[;\\d]*m", "");
        String border = "═".repeat(visibleText.length() + 10);
        return colorize("╔" + border + "╗\n" +
                       "║     " + text + "     ║\n" +
                       "╚" + border + "╝", BLUE);
    }

    public static String formatMonsterType(MonsterType type) {
        return switch (type) {
            case THUNDER -> colorize("FOUDRE", YELLOW);
            case WATER -> colorize("EAU", BLUE);
            case FIRE -> colorize("FEU", RED);
            case PLANT -> colorize("PLANTE", GREEN);
            case EARTH -> colorize("TERRE",  BLACK);
            case INSECT -> colorize("INSECTE", PURPLE);
        };
    }


    public static String formatItemInfo(String name, String description) {
        return colorize(name, YELLOW) + " - " + colorize(description, WHITE);
    }

    public static String formatMonsterInfo(String name, MonsterType type, int hp, int attack, int defense, int speed) {
        return String.format("%s %s %s\n    %s %s %s",
            colorize(name, BOLD + WHITE),
            formatMonsterType(type),
            formatHealthBar(hp, hp),
            colorize("ATK:" + attack, RED),
            colorize("DEF:" + defense, BLUE),
            colorize("SPD:" + speed, YELLOW)
        );
    }

    public static String formatActionPrompt(String playerName, boolean isPlayer1) {
        String color = isPlayer1 ? PLAYER1_COLOR : PLAYER2_COLOR;
        return color + "【" + playerName + "】" + RESET + " ► ";
    }
} 