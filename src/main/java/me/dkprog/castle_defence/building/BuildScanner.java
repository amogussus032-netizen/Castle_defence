package me.dkprog.castle_defence.building;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;

public class BuildScanner {

    private final MaterialWeights materialWeights;

    private static final BlockFace[] blockFaces = {
            BlockFace.UP, BlockFace.DOWN,
            BlockFace.NORTH, BlockFace.SOUTH,
            BlockFace.EAST, BlockFace.WEST
    };

    public BuildScanner(MaterialWeights materialWeights) {
        this.materialWeights = materialWeights;
    }

    public BuildStats scanBuilding(BuildSlot slot) {
        int blocksCount = 0;
        int weightsSum = 0;

        int minX = (int) Math.floor(slot.getBounds().getMinX());
        int maxX = (int) Math.ceil(slot.getBounds().getMaxX());
        int minY = (int) Math.floor(slot.getBounds().getMinY());
        int maxY = (int) Math.ceil(slot.getBounds().getMaxY());
        int minZ = (int) Math.floor(slot.getBounds().getMinZ());
        int maxZ = (int) Math.ceil(slot.getBounds().getMaxZ());

        World world = slot.getWorld();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);

                    if (!materialWeights.isStructural(block.getType())) continue;

                    blocksCount++;
                    weightsSum += materialWeights.getWeight(block.getType());
                }
            }
        }

        return new BuildStats(blocksCount, weightsSum, materialWeights);
    }

    public void visualizeNeighborCount(BuildSlot slot) {

        int minX = (int) Math.floor(slot.getBounds().getMinX());
        int maxX = (int) Math.ceil(slot.getBounds().getMaxX());
        int minY = (int) Math.floor(slot.getBounds().getMinY());
        int maxY = (int) Math.ceil(slot.getBounds().getMaxY());
        int minZ = (int) Math.floor(slot.getBounds().getMinZ());
        int maxZ = (int) Math.ceil(slot.getBounds().getMaxZ());

        World world = slot.getWorld();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);

                    if (!materialWeights.isStructural(block.getType())) continue;

                    Component text;
                    if (materialWeights.isDecorative(block.getType())) {
                        text = Component.text("dec").color(NamedTextColor.GRAY);
                    } else {
                        int neighborsCount = countFilledNeighbors(block);
                        text = Component.text(String.valueOf(neighborsCount)).color(colorForNeighborCount(neighborsCount));
                    }

                    world.spawn(block.getLocation().add(0.5, 0.5, 0.5), TextDisplay.class, display -> {
                        display.text(text);
                        display.setBillboard(Display.Billboard.CENTER);
                    });
                }
            }
        }
    }

    private TextColor colorForNeighborCount(int count) {
        double t = count / 6.0;
        int red = (int) (255 * (1 - t));
        int green = (int) (255 * t);
        return TextColor.color(red, green, 0);
    }

    public int countFilledNeighbors(Block block) {
        int neighborsCount = 0;

        for (BlockFace face : blockFaces) {
            Block neighbor = block.getRelative(face);
            if (neighbor.getType().isSolid() && neighbor.getType().isOccluding() && !materialWeights.isDecorative(neighbor.getType())) neighborsCount++;
        }

        return neighborsCount;
    }
}
