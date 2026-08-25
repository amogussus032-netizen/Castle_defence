package me.dkprog.castle_defence.player_classes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassSetCommand implements CommandExecutor {

    private final PlayerClassManager classManager;

    public ClassSetCommand(PlayerClassManager classManager) {
        this.classManager = classManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) return false;
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) return false;

        try {
            ClassType classType = ClassType.valueOf(args[1].toUpperCase());
            classManager.setPlayerClass(player, classType);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
