package me.dkprog.castle_defence.building;

import org.bukkit.Location;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.BoundingBox;

public class ExplodeEventHandler implements Listener {
    private final BuildSlot slot;
    private final BuildScanner scanner;
    private final MaterialWeights weights;
    private final WallDamageTracker damageTracker;

    private final double explosionRadius = 3.0;

    ExplodeEventHandler(BuildSlot slot, BuildScanner scanner, MaterialWeights weights, WallDamageTracker damageTracker) {
        this.slot  = slot;
        this.scanner = scanner;
        this.weights = weights;
        this.damageTracker = damageTracker;
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed)) return;

        Location center = event.getLocation();
        BoundingBox explosionBox = new BoundingBox(center.getX()-explosionRadius, center.getY()-explosionRadius, center.getZ()-explosionRadius, center.getX()+explosionRadius, center.getY()+explosionRadius, center.getZ()+explosionRadius);

        if (!explosionBox.overlaps(slot.getBounds())) return;
        explosionBox.intersection(slot.getBounds());


    }
}
