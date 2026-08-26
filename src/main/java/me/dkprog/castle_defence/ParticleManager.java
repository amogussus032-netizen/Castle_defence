package me.dkprog.castle_defence;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class ParticleManager {

    private static final double STEP = 0.3;
    private static final long PERIOD_TICKS = 5L;

    public static void visualizeBox(BoundingBox box, World world) {
        JavaPlugin plugin = JavaPlugin.getPlugin(Castle_defence.class);
        List<Location> edgePoints = buildEdgePoints(box, world);

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Location point : edgePoints) {
                world.spawnParticle(Particle.FLAME, point, 1, 0, 0, 0, 0);
            }
        }, 0L, PERIOD_TICKS);
    }

    private static List<Location> buildEdgePoints(BoundingBox box, World world) {
        double minX = box.getMinX(), minY = box.getMinY(), minZ = box.getMinZ();
        double maxX = box.getMaxX(), maxY = box.getMaxY(), maxZ = box.getMaxZ();

        double[][] corners = {
                {minX, minY, minZ}, {maxX, minY, minZ},
                {minX, maxY, minZ}, {maxX, maxY, minZ},
                {minX, minY, maxZ}, {maxX, minY, maxZ},
                {minX, maxY, maxZ}, {maxX, maxY, maxZ}
        };

        int[][] edges = {
                {0, 1}, {2, 3}, {4, 5}, {6, 7},
                {0, 2}, {1, 3}, {4, 6}, {5, 7},
                {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        List<Location> points = new ArrayList<>();
        for (int[] edge : edges) {
            addPointsAlongEdge(points, world, corners[edge[0]], corners[edge[1]]);
        }

        return points;
    }

    private static void addPointsAlongEdge(List<Location> points, World world, double[] start, double[] end) {
        double length = Math.sqrt(
                Math.pow(end[0] - start[0], 2) +
                Math.pow(end[1] - start[1], 2) +
                Math.pow(end[2] - start[2], 2)
        );

        int steps = Math.max(1, (int) (length / STEP));

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            points.add(new Location(world,
                    start[0] + (end[0] - start[0]) * t,
                    start[1] + (end[1] - start[1]) * t,
                    start[2] + (end[2] - start[2]) * t));
        }
    }
}
