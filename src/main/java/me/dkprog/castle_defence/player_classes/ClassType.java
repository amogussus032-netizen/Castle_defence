package me.dkprog.castle_defence.player_classes;

import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

public enum ClassType {
    DEFAULT(List.of(), NamedTextColor.GRAY, "Дурачок"),
    ARCHER(List.of(), NamedTextColor.GREEN, "Лучник"),
    WARRIOR(List.of(), NamedTextColor.RED, "Воин"),
    ENGINEER(List.of(), NamedTextColor.GOLD, "Инженер");

    private final List<Ability> playerAbilities;
    private final NamedTextColor prefixColor;
    private final String prefixText;

    ClassType(List<Ability> abilities, NamedTextColor prefixColor, String prefixText) {
        playerAbilities = abilities;
        this.prefixColor = prefixColor;
        this.prefixText = prefixText;
    }

    public List<Ability> getPlayerAbilities() {
        return playerAbilities;
    }

    public NamedTextColor getPrefixColor() {
        return prefixColor;
    }

    public String getPrefixText() {
        return prefixText;
    }
}
