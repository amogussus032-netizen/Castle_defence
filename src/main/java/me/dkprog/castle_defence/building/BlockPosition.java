package me.dkprog.castle_defence.building;

import org.bukkit.World;

import java.util.Objects;

public class BlockPosition {
    private int x;
    private int y;
    private int z;
    private World world;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BlockPosition pos)) return false;

        return x == pos.x && y == pos.y && z == pos.z && world.equals(pos.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, world);
    }

    public BlockPosition(int x, int y, int z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }
}