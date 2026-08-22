package me.dkprog.castle_defence.building;

import java.util.HashMap;
import java.util.Map;

public class WallDamageTracker {
    private final Map<BlockPosition, Integer> damageTracker;

    public WallDamageTracker() {
        damageTracker = new HashMap<>();
    }

    public int getBlockDamage(BlockPosition position) {
        return damageTracker.getOrDefault(position, 0);
    }

    public void addDamage(BlockPosition position, int amount) {
        damageTracker.put(position, getBlockDamage(position) + amount);
    }
}
