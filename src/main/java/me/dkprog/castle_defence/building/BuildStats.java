package me.dkprog.castle_defence.building;

import org.bukkit.Material;

public class BuildStats {

    private final int totalBlocksCount;
    private int blocksCount;
    private int weightsSum;
    private final int totalWeightsSum;
    private final MaterialWeights weights;

    public BuildStats(int blocksCount, int weightsSum, MaterialWeights weights) {
        this.blocksCount = blocksCount;
        this.totalBlocksCount = blocksCount;
        this.weightsSum = weightsSum;
        this.totalWeightsSum = weightsSum;
        this.weights = weights;
    }

    public int getBlocksCount() {
        return blocksCount;
    }

    public int getTotalBlocksCount() {
        return totalBlocksCount;
    }

    public int getTotalWeightsSum() {
        return totalWeightsSum;
    }

    public int getWeightsSum() {
        return weightsSum;
    }

    public void removeBlock(Material material) {
        if (!weights.isStructural(material)) return;
        weightsSum -= weights.getWeight(material);
        blocksCount--;
    }

    public double getHp() {
        if (totalWeightsSum == 0) return 0.0;
        return (double) weightsSum / totalWeightsSum;
    }
}
