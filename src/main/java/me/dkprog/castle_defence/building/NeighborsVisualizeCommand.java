package me.dkprog.castle_defence.building;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NeighborsVisualizeCommand implements CommandExecutor {
    private final BuildSlot slot;
    private final BuildScanner scanner;

    public NeighborsVisualizeCommand(BuildSlot slot, BuildScanner scanner) {
        this.slot = slot;
        this.scanner = scanner;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        scanner.visualizeNeighborCount(slot);
        sender.sendMessage("Visualized");
        return true;
    }

}