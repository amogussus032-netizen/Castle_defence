package me.dkprog.castle_defence.building;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.TextDisplay;

import java.util.List;

public class HpVisualizeCommand implements CommandExecutor {
    private final List<BuildSlot> buildSlots;
    private final BuildScanner scanner;
    private final WallDamageTracker damageTracker;

    public HpVisualizeCommand(List<BuildSlot> buildSlots, BuildScanner scanner, WallDamageTracker damageTracker) {
        this.buildSlots = buildSlots;
        this.scanner = scanner;
        this.damageTracker = damageTracker;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (World world : Bukkit.getWorlds()) {
            for (TextDisplay entity : world.getEntitiesByClass(TextDisplay.class)) {
                entity.remove();
            }
        }
        for (BuildSlot slot : buildSlots) {
            scanner.visualizeBlockHp(slot, damageTracker);
            sender.sendMessage("HP visualized");
        }
        return true;
    }
}
