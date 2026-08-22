package me.dkprog.castle_defence.building;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MaterialWeights {
    private final Map<Material, Integer> weights;
    private final Set<Material> decorativeMaterials;

    public MaterialWeights() {
        weights = new HashMap<>();
        weights.put(Material.OAK_PLANKS, 10);
        weights.put(Material.OAK_STAIRS, 30);
        weights.put(Material.COBBLESTONE, 20);
        weights.put(Material.COBBLESTONE_STAIRS, 30);
        weights.put(Material.STONE_BRICKS, 30);
        weights.put(Material.STONE_BRICK_STAIRS, 30);
        weights.put(Material.IRON_BLOCK, 40);
        weights.put(Material.OAK_DOOR, 10);
        weights.put(Material.IRON_DOOR, 40);

        decorativeMaterials = new HashSet<>();
        decorativeMaterials.add(Material.TORCH);
        decorativeMaterials.add(Material.WALL_TORCH);
        decorativeMaterials.add(Material.SOUL_TORCH);
        decorativeMaterials.add(Material.SOUL_WALL_TORCH);
        decorativeMaterials.add(Material.LANTERN);
        decorativeMaterials.add(Material.SOUL_LANTERN);
        decorativeMaterials.add(Material.FLOWER_POT);
        decorativeMaterials.add(Material.CHAIN);
        decorativeMaterials.add(Material.POPPY);
        decorativeMaterials.add(Material.DANDELION);

        // Плиты
        decorativeMaterials.add(Material.COBBLESTONE_SLAB);
        decorativeMaterials.add(Material.STONE_SLAB);
        decorativeMaterials.add(Material.STONE_BRICK_SLAB);
        decorativeMaterials.add(Material.OAK_SLAB);

        // Таблички
        decorativeMaterials.add(Material.OAK_SIGN);
        decorativeMaterials.add(Material.OAK_WALL_SIGN);

        // Стены (декоративный блок-стена, не путать со слотами построек)
        decorativeMaterials.add(Material.COBBLESTONE_WALL);
        decorativeMaterials.add(Material.STONE_BRICK_WALL);

        // Заборы
        decorativeMaterials.add(Material.OAK_FENCE);
        decorativeMaterials.add(Material.OAK_FENCE_GATE);

        for (Material decorative : decorativeMaterials) {
            weights.put(decorative, 1);
        }
    }

    public boolean isStructural(Material material) {
        return weights.containsKey(material);
    }

    public boolean isDecorative(Material material) {
        return decorativeMaterials.contains(material);
    }

    public int getWeight(Material material) {
        return weights.get(material);
    }
}
