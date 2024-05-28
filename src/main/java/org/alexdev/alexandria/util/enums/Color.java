package org.alexdev.alexandria.util.enums;

public enum Color {
    GOLD("§6", "gold", 255, 170, 0, "#FFAA00"),
    GRAY("§7", "gray", 170, 170, 170, "#AAAAAA"),
    BLUE("§9", "blue", 85, 85, 255, "#5555FF"),
    GREEN("§a", "green", 85, 255, 85, "#55FF55"),
    AQUA("§b", "aqua", 85, 255, 255, "#55FFFF"),
    RED("§c", "red", 255, 85, 85, "#FF5555"),
    PURPLE("§d", "purple", 255, 85, 255, "#FF55FF"),
    YELLOW("§e", "yellow", 255, 255, 85, "#FFFF55"),
    WHITE("§f", "white", 255, 255, 255, "#FFFFFF");

    private final String code;
    private final String name;
    private final int red;
    private final int green;
    private final int blue;
    private final String hex;

    Color(String code, String name, int red, int green, int blue, String hex) {
        this.code = code;
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.hex = hex;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public String getHex() {
        return hex;
    }

    @Override
    public String toString() {
        return String.format("Color{name='%s', code='%s', red=%d, green=%d, blue=%d, hex='%s'}",
                name, code, red, green, blue, hex);
    }
}