package me.dkprog.castle_defence.building;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HpVisualizeCommand implements CommandExecutor {
    private final BuildSlot slot;
    private final BuildScanner scanner;
    private final WallDamageTracker damageTracker;

    public HpVisualizeCommand(BuildSlot slot, BuildScanner scanner, WallDamageTracker damageTracker) {
        this.slot = slot;
        this.scanner = scanner;
        this.damageTracker = damageTracker;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        scanner.visualizeBlockHp(slot, damageTracker);
        sender.sendMessage("HP visualized");
        return true;
    }
}
