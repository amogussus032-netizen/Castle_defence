package me.dkprog.castle_defence;

import me.dkprog.castle_defence.building.*;
import me.dkprog.castle_defence.player_classes.ClassSetCommand;
import me.dkprog.castle_defence.player_classes.PlayerClassManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BoundingBox;

public final class Castle_defence extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Castle_defence enabled successfully!");

        World world = Bukkit.getWorlds().getFirst();
        Location spawnLocation = world.getSpawnLocation();
        double startX = spawnLocation.getX();
        double startY = spawnLocation.getY();
        double startZ = spawnLocation.getZ();

        BuildSlot firstSlot = new BuildSlot(BuildSlot.SlotType.WALL, new BoundingBox(startX, startY, startZ, startX + 10, startY + 10, startZ + 10), world);
        MaterialWeights weights = new MaterialWeights();
        BuildScanner scanner = new BuildScanner(weights);
        WallDamageTracker wallDamageTracker = new WallDamageTracker();
        PlayerClassManager classManager = new PlayerClassManager(this);

        getServer().getPluginManager().registerEvents(new BuildListener(firstSlot, scanner, wallDamageTracker, this), this);
        getServer().getPluginManager().registerEvents(new ExplodeEventHandler(firstSlot, scanner, weights, wallDamageTracker), this);
        getServer().getPluginManager().registerEvents(classManager, this);
        getCommand("visualizeneighbors").setExecutor(new NeighborsVisualizeCommand(firstSlot, scanner));
        getCommand("visualizehp").setExecutor(new HpVisualizeCommand(firstSlot, scanner, wallDamageTracker));
        getCommand("setplayerclass").setExecutor(new ClassSetCommand(classManager));
    }

    @Override
    public void onDisable() {
        getLogger().info("Castle_defence disabled.");
    }
}
