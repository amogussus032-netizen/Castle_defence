package me.dkprog.castle_defence.building;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.BoundingBox;

import java.util.*;

public class ExplodeEventHandler implements Listener {
    private final List<BuildSlot> buildSlots;
    private final BuildScanner scanner;
    private final MaterialWeights weights;
    private final WallDamageTracker damageTracker;

    private final double explosionRadius = 3;
    double BASE_DAMAGE_PER_HIT = 30;

    public ExplodeEventHandler(List<BuildSlot> slotsList, BuildScanner scanner, MaterialWeights weights, WallDamageTracker damageTracker) {
        this.buildSlots  = slotsList;
        this.scanner = scanner;
        this.weights = weights;
        this.damageTracker = damageTracker;
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {

        if (!(event.getEntity() instanceof TNTPrimed)) return;

        Location center = event.getLocation();

        for (BuildSlot slot : buildSlots) {
            if (!(slot.getType() == BuildSlot.SlotType.WALL)) continue;

            BoundingBox explosionBox = new BoundingBox(center.getX() - explosionRadius, center.getY() - explosionRadius, center.getZ() - explosionRadius, center.getX() + explosionRadius, center.getY() + explosionRadius, center.getZ() + explosionRadius);

            if (!explosionBox.overlaps(slot.getBounds())) continue;
            if (!slot.getWorld().equals(event.getEntity().getWorld())) continue;

            event.blockList().clear();

            explosionBox.intersection(slot.getBounds());

            int minX = (int) Math.floor(explosionBox.getMinX());
            int maxX = (int) Math.ceil(explosionBox.getMaxX());
            int minY = (int) Math.floor(explosionBox.getMinY());
            int maxY = (int) Math.ceil(explosionBox.getMaxY());
            int minZ = (int) Math.floor(explosionBox.getMinZ());
            int maxZ = (int) Math.ceil(explosionBox.getMaxZ());

            World world = slot.getWorld();
            Queue<Block> toBreak = new ArrayDeque<>();

            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        Block block = world.getBlockAt(x, y, z);

                        if (!(weights.isStructural(block.getType()))) continue;

                        double distanceToExplosion = block.getLocation().add(0.5, 0.5, 0.5).distance(center);

                        if (distanceToExplosion > explosionRadius) continue;

                        double distanceRatio = distanceToExplosion / explosionRadius;
                        double distanceFactor = 1.0 - 0.5 * (distanceRatio * distanceRatio);
                        double damageToBlock = distanceFactor * BASE_DAMAGE_PER_HIT;

                        BlockPosition position = new BlockPosition(x, y, z, world);
                        damageTracker.addDamage(position, damageToBlock);

                        double blockThreshold = weights.getWeight(block.getType()) * (scanner.countFilledNeighbors(block) + 1);

                        if (damageTracker.getBlockDamage(position) >= blockThreshold) {
                            toBreak.add(block);
                        } else {
                            Collection<Player> nearPlayers = center.getNearbyPlayers(30);
                            float progress = (float) (damageTracker.getBlockDamage(position) / blockThreshold);
                            for (Player player : nearPlayers) {
                                player.sendBlockDamage(block.getLocation(), progress, position.hashCode());
                            }
                        }
                    }
                }
            }

            while (!toBreak.isEmpty()) {
                Block block = toBreak.poll();
                breakBlock(world, block, toBreak);
            }
        }
    }

    private void breakBlock(World world, Block block, Queue<Block> toBreak) {
        if (block.getType() == Material.AIR) return;

        world.spawnParticle(Particle.BLOCK, block.getLocation().add(0.5, 0.5, 0.5), 201, 0.5, 0.5, 0.5, 1, block.getBlockData());
        block.setType(Material.AIR);
        damageTracker.removeBlock(new BlockPosition(block.getX(), block.getY(), block.getZ(), world));

        BlockFace[] blockFaces = {
                BlockFace.UP, BlockFace.DOWN,
                BlockFace.NORTH, BlockFace.SOUTH,
                BlockFace.EAST, BlockFace.WEST
        };

        for (BlockFace face : blockFaces) {
            Block neighbor = block.getRelative(face);

            if (!(weights.isStructural(neighbor.getType()))) continue;

            double blockThreshold = weights.getWeight(neighbor.getType()) * (scanner.countFilledNeighbors(neighbor) + 1);
            if (damageTracker.getBlockDamage(new BlockPosition(neighbor.getX(), neighbor.getY(), neighbor.getZ(), world)) >= blockThreshold) {
                toBreak.add(neighbor);
            }
        }
    }
}
