package archive;

import archive.listener.EntityAddListener;
import archive.listener.ProjectileLaunchListener;
import io.papermc.lib.PaperLib;
import org.bukkit.plugin.java.JavaPlugin;

public class ArchiveEntityFix extends JavaPlugin {

  @Override
  public void onEnable() {
    PaperLib.suggestPaper(this);
    saveDefaultConfig();
    reloadConfig();
    getLogger().info("Loading ArchiveEntityFix...");
    if (getConfig().getBoolean("entity-nan-velocity-remove")) {
      getLogger().info("Enabling Entity NaN Velocity Removal");
      getServer().getPluginManager().registerEvents(new EntityAddListener(), this);
    }

    if (getConfig().getBoolean("wither-skull-spawn-remove")) {
      getLogger().info("Enabling Wither Skull Spawn Removal");
      getServer().getPluginManager().registerEvents(new ProjectileLaunchListener(), this);
    }
  }
}
