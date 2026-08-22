package me.dkprog.castle_defence.building;

import java.util.HashMap;
import java.util.Map;

public class WallDamageTracker {
    private final Map<BlockPosition, Double> damageTracker;

    public WallDamageTracker() {
        damageTracker = new HashMap<>();
    }

    public double getBlockDamage(BlockPosition position) {
        return damageTracker.getOrDefault(position, 0.0);
    }

    public void addDamage(BlockPosition position, double amount) {
        damageTracker.put(position, getBlockDamage(position) + amount);
    }

    public void clearDamage(BlockPosition position) {
        damageTracker.put(position, 0.0);
    }

    public void removeBlock(BlockPosition position) {
        damageTracker.remove(position);
    }

}
