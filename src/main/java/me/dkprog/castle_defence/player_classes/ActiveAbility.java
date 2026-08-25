package me.dkprog.castle_defence.player_classes;

public interface ActiveAbility extends Ability {

    void onActivation();

    int getCooldown();
}
