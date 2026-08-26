package me.dkprog.castle_defence.building;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.TextDisplay;

import java.util.List;

public class NeighborsVisualizeCommand implements CommandExecutor {
    private final List<BuildSlot> buildSlots;
    private final BuildScanner scanner;

    public NeighborsVisualizeCommand(List<BuildSlot> buildSlots, BuildScanner scanner) {
        this.buildSlots = buildSlots;
        this.scanner = scanner;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (World world : Bukkit.getWorlds()) {
            for (TextDisplay entity : world.getEntitiesByClass(TextDisplay.class)) {
                entity.remove();
            }
        }
        for (BuildSlot slot : buildSlots) {
            scanner.visualizeNeighborCount(slot);
            sender.sendMessage("Visualized");
        }
        return true;
    }

}