package me.dkprog.castle_defence.player_classes;

import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

public enum ClassType {
    DEFAULT(List.of(), NamedTextColor.GRAY),
    ARCHER(List.of(), NamedTextColor.GREEN),
    WARRIOR(List.of(), NamedTextColor.RED),
    ENGINEER(List.of(), NamedTextColor.GOLD);

    private final List<Ability> playerAbilities;
    private final NamedTextColor prefixColor;

    ClassType(List<Ability> abilities, NamedTextColor prefixColor) {
        playerAbilities = abilities;
        this.prefixColor = prefixColor;
    }

    public List<Ability> getPlayerAbilities() {
        return playerAbilities;
    }

    public NamedTextColor getPrefixColor() {
        return prefixColor;
    }
}
