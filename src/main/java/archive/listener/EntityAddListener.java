package archive.listener;

import archive.ArchiveEntityFix;
import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityAddListener implements Listener {

    private final ArchiveEntityFix plugin;

    public EntityAddListener(ArchiveEntityFix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityAddToWorldEvent(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();

        // Validate fireball acceleration (modern Paper API)
        if (entity instanceof Fireball fireball) {
            var acceleration = fireball.getAcceleration();
            if (!Double.isFinite(acceleration.getX()) || !Double.isFinite(acceleration.getY()) || !Double.isFinite(acceleration.getZ())) {
                logRemoval(entity, "invalid Fireball acceleration");
                entity.remove();
                return;
            }
        }

        // Validate all entity velocity, position, and rotation
        var velocity = entity.getVelocity();
        if (!Double.isFinite(velocity.getX()) || !Double.isFinite(velocity.getY()) || !Double.isFinite(velocity.getZ())) {
            logRemoval(entity, "invalid velocity");
            entity.remove();
            return;
        }

        if (!Double.isFinite(entity.getX()) || !Double.isFinite(entity.getY()) || !Double.isFinite(entity.getZ())) {
            logRemoval(entity, "invalid position");
            entity.remove();
            return;
        }

        if (!Float.isFinite(entity.getPitch()) || !Float.isFinite(entity.getYaw())) {
            logRemoval(entity, "invalid rotation");
            entity.remove();
        }
    }

    private void logRemoval(Entity entity, String reason) {
        if (plugin.getConfig().getBoolean("log-removals", true)) {
            plugin.getLogger().info("Removed corrupted " + entity.getType() + ": " + reason);
        }
    }
}
