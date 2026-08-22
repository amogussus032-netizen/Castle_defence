package me.dkprog.castle_defence.building;

import org.bukkit.World;
import org.bukkit.util.BoundingBox;

public class BuildSlot {

    private final SlotType type;
    private final Axis sliceAxis;

    private final BoundingBox bounds;
    private final World world;

    public BuildSlot(SlotType type, BoundingBox bounds, World world, Axis sliceAxis) {
        this.type = type;
        this.bounds = bounds;
        this.world = world;
        this.sliceAxis = sliceAxis;
    }

    public BoundingBox getBounds() {
        return bounds;
    }

    public World getWorld() {
        return world;
    }

    public SlotType getType() {
        return type;
    }

    public Axis getSliceAxis() {
        return sliceAxis;
    }

    public enum SlotType {
        WALL,
        TOWER
    }

    public enum Axis {
        X,
        Y,
        Z
    }
}
