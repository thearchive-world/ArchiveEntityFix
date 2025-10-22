package archive;

import archive.listener.EntityAddListener;
import archive.listener.ProjectileLaunchListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for ArchiveEntityFix
 * Validates entity data and blocks problematic projectiles
 */
public class ArchiveEntityFix extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Loading ArchiveEntityFix...");

        // Entity validation feature
        if (getConfig().getBoolean("entity-nan-props-remove", true)) {
            getLogger().info("Enabling Entity NaN Validation");
            getServer().getPluginManager().registerEvents(new EntityAddListener(this), this);
        }

        // Wither skull blocking feature
        if (getConfig().getBoolean("wither-skull-spawn-remove", true)) {
            getLogger().info("Enabling Wither Skull Blocking");
            getServer().getPluginManager().registerEvents(new ProjectileLaunchListener(), this);
        }
    }
}
