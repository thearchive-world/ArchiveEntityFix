package archive.listener;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.NumberConversions;

public class EntityAddListener implements Listener {

    @EventHandler
    public void onEntityAddToWorldEvent(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Fireball fireball) {
            var direction = fireball.getDirection();
            var power = fireball.getPower();
            if (!NumberConversions.isFinite(direction.getX()) || !NumberConversions.isFinite(direction.getY()) || !NumberConversions.isFinite(direction.getZ())) {
                fireball.remove();
                return;
            } else if (Double.isNaN(direction.getX()) || Double.isNaN(direction.getY()) || Double.isNaN(direction.getZ())) {
                fireball.remove();
                return;
            } else if (!NumberConversions.isFinite(power.getX()) || !NumberConversions.isFinite(power.getY()) || !NumberConversions.isFinite(power.getZ())) {
                fireball.remove();
                return;
            } else if (Double.isNaN(power.getX()) || Double.isNaN(power.getY()) || Double.isNaN(power.getZ())) {
                fireball.remove();
                return;
            }
        }
        var velocity = entity.getVelocity();
        if (Double.isNaN(velocity.getX()) || Double.isNaN(velocity.getY()) || Double.isNaN(velocity.getZ())) {
            entity.remove();
        } else if (Double.isNaN(entity.getX()) || Double.isNaN(entity.getY()) || Double.isNaN(entity.getZ())) {
            entity.remove();
        } else if (Float.isNaN(entity.getPitch()) || Float.isNaN(entity.getYaw())) {
            entity.remove();
        }
    }
}
