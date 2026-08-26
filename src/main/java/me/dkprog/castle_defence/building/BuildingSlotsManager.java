package me.dkprog.castle_defence.building;

import me.dkprog.castle_defence.ParticleManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuildingSlotsManager {
    private final List<BuildSlot> buildSlotsList;

    public BuildingSlotsManager(FileConfiguration config) {
        List<Map<?, ?>> buildSlotsParameters = config.getMapList("buildslots");
        buildSlotsList = new ArrayList<>();
        IllegalArgumentException errorMessage = new IllegalArgumentException("Incorrect plugin config");
        for (Map<?, ?> slotParameters : buildSlotsParameters) {
            if (!(checkParameters(slotParameters))) throw errorMessage;

            BuildSlot slot = new BuildSlot(BuildSlot.SlotType.valueOf((String) slotParameters.get("type")), new BoundingBox(((Number) slotParameters.get("x1")).intValue(), ((Number) slotParameters.get("y1")).intValue(), ((Number) slotParameters.get("z1")).intValue(), ((Number) slotParameters.get("x2")).intValue(), ((Number) slotParameters.get("y2")).intValue(), ((Number) slotParameters.get("z2")).intValue()), Bukkit.getWorld((String) slotParameters.get("world")));
            buildSlotsList.add(slot);
        }

    }

    private boolean checkParameters(Map<?, ?> parameters) {
        String[] requiredFields = {"x1", "x2", "y1", "y2", "z1", "z2"};
        for (String field : requiredFields) {
            if (!(parameters.get(field) instanceof Number)) return false;
        }
        if (!(parameters.get("world") instanceof String && parameters.get("type") instanceof String)) return false;

        if (Bukkit.getWorld((String) parameters.get("world")) == null) return false;

        try {
            BuildSlot.SlotType.valueOf((String) parameters.get("type"));
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    public List<BuildSlot> getBuildSlotsList() {
        return new ArrayList<>(buildSlotsList); //Создаётся копия чтобы не передавать ссылку на оригинальный лист(защита от изменений)
    }
}
