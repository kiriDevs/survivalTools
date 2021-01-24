package de.kiridevs.survivaltools.listeners;

import de.kiridevs.kiricore.managers.MessageService;
import de.kiridevs.survivaltools.managers.HomeManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class LISTonPlayerJoinEvent implements Listener {
    Plugin survivaltools;
    MessageService msgSer;
    FileConfiguration config;
    public LISTonPlayerJoinEvent(MessageService msgSer, Plugin plugin) {
        this.msgSer = msgSer;
        this.survivaltools = plugin;
        this.config = plugin.getConfig();
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        UUID uuid = player.getUniqueId();

        String index = config.getString("homes." + uuid + ".index");
        if (index == null) { return; }
        String[] existingHomes = index.split(" ");

        for (String homeKey : existingHomes) {
            double locX = config.getDouble("homes." + uuid + "." + homeKey + "x");
            double locY = config.getDouble("homes." + uuid + "." + homeKey + "y");
            double locZ = config.getDouble("homes." + uuid + "." + homeKey + "z");
            float locYaw = (float) config.getDouble("homes." + uuid + "." + homeKey + ".yaw");
            float locPitch = (float) config.getDouble("homes." + uuid + "." + homeKey + ".pitch");
            String locWorldName = config.getString("homes." + uuid + "." + homeKey + ".world");

            assert locWorldName != null;
            World locWorld = Bukkit.getWorld(locWorldName);
            Location homeLocation = new Location(locWorld, locX, locY, locZ, locYaw, locPitch);

            HomeManager.setHome(player, homeKey, homeLocation);
        }
    }
}
