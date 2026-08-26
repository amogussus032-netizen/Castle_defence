package me.dkprog.castle_defence.building;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BuildListener implements Listener {
    private final List<BuildSlot> buildSlots;
    private final BuildScanner scanner;
    private final JavaPlugin plugin;
    private final WallDamageTracker wallDamageTracker;

    public BuildListener(List<BuildSlot> buildSlots, BuildScanner scanner, WallDamageTracker wallDamageTracker, JavaPlugin plugin) {
        this.buildSlots = buildSlots;
        this.scanner = scanner;
        this.plugin = plugin;
        this.wallDamageTracker = wallDamageTracker;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        for (BuildSlot slot : buildSlots) {
            if (!slot.getBounds().contains(block.getX(), block.getY(), block.getZ())) continue;
            if (!slot.getWorld().equals(block.getWorld())) continue;

            int blocksCount = scanner.scanBuilding(slot);

            event.getPlayer().sendMessage("totalBlocks=" + blocksCount);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        for (BuildSlot slot : buildSlots) {
            if (!slot.getBounds().contains(block.getX(), block.getY(), block.getZ())) continue;
            if (!slot.getWorld().equals(block.getWorld())) continue;

            wallDamageTracker.removeBlock(new BlockPosition(block.getX(), block.getY(), block.getZ(), slot.getWorld()));

            Bukkit.getScheduler().runTask(plugin, () -> {
                int blocksCount = scanner.scanBuilding(slot);

                event.getPlayer().sendMessage("totalBlocks=" + blocksCount);
            });
        }
    }
}
