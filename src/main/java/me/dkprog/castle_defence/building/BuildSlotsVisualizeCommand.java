package me.dkprog.castle_defence.building;

import me.dkprog.castle_defence.ParticleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BuildSlotsVisualizeCommand implements CommandExecutor {
    private final List<BuildSlot> buildSlots;

    public BuildSlotsVisualizeCommand(List<BuildSlot> buildSlots) {
        this.buildSlots = buildSlots;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (BuildSlot slot : buildSlots) {
            ParticleManager.visualizeBox(slot.getBounds(), slot.getWorld());
        }
        return true;
    }
}
