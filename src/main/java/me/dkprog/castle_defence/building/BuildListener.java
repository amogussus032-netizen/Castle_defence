package me.dkprog.castle_defence.building;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BuildListener implements Listener {
    private final BuildSlot slot;
    private final BuildScanner scanner;
    private final JavaPlugin plugin;
    private final WallDamageTracker wallDamageTracker;

    public BuildListener(BuildSlot slot, BuildScanner scanner, WallDamageTracker wallDamageTracker, JavaPlugin plugin) {
        this.slot = slot;
        this.scanner = scanner;
        this.plugin = plugin;
        this.wallDamageTracker = wallDamageTracker;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (!slot.getBounds().contains(block.getX(), block.getY(), block.getZ())) return;

        BuildStats stats = scanner.scanBuilding(slot);

        event.getPlayer().sendMessage("totalBlocks=" + stats.getBlocksCount());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (!slot.getBounds().contains(block.getX(), block.getY(), block.getZ())) return;

        wallDamageTracker.clearDamage(new BlockPosition(block.getX(), block.getY(), block.getZ(), slot.getWorld()));

        Bukkit.getScheduler().runTask(plugin, () -> {
            BuildStats stats = scanner.scanBuilding(slot);

            event.getPlayer().sendMessage("totalBlocks=" + stats.getBlocksCount());
        });
    }
}
