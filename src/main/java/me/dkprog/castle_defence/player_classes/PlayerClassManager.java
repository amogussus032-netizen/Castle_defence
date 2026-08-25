package me.dkprog.castle_defence.player_classes;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PlayerClassManager implements Listener {
    private final Map<UUID, ClassType> playerClassMap;
    private final JavaPlugin plugin;
    private final NamespacedKey classKey;
    private final Scoreboard mainScoreboard;

    public PlayerClassManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerClassMap = new HashMap<>();
        this.classKey = new NamespacedKey(plugin, "class-name");
        this.mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        scoreboardsSetup();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        String stringClassName = dataContainer.get(classKey, PersistentDataType.STRING);
        if (stringClassName == null) return;

        try {
            ClassType playerClass = ClassType.valueOf(stringClassName);
            setPlayerClass(player, playerClass);
        } catch (IllegalArgumentException e) {
            setPlayerClass(player, ClassType.DEFAULT);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        ClassType playerClass = playerClassMap.get(uuid);
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();

        if (playerClass != null) {
            playerClassMap.remove(uuid);
            dataContainer.set(classKey, PersistentDataType.STRING, playerClass.name());
        }
        else {
            dataContainer.set(classKey, PersistentDataType.STRING, "DEFAULT");
        }
    }

    public void setPlayerClass(Player player, ClassType type) {
        UUID uuid = player.getUniqueId();
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        playerClassMap.put(uuid, type);
        dataContainer.set(classKey, PersistentDataType.STRING, type.name());
        mainScoreboard.getTeam(type.name()).addPlayer(player);
    }

    private void scoreboardsSetup() {
        ClassType[] allClasses = ClassType.values();
        for (ClassType type : allClasses) {
            String teamName = type.name();
            Team team = mainScoreboard.getTeam(teamName);
            if (team == null) {
                mainScoreboard.registerNewTeam(teamName);
                team = mainScoreboard.getTeam(teamName);
            }
            Component prefixText = Component.text(teamName.toLowerCase()).color(type.getPrefixColor());

            team.prefix(prefixText);
        }
    }
}
