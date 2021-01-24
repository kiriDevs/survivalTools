package de.kiridevs.survivaltools.listeners;

import de.kiridevs.kiricore.managers.MessageService;
import de.kiridevs.survivaltools.managers.HomeManager;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.UUID;

public class LISTonPlayerQuitEvent implements Listener {
    Plugin survivalTools;
    MessageService msgSer;
    FileConfiguration config;
    public LISTonPlayerQuitEvent(MessageService messageService, Plugin plugin) {
        this.survivalTools = plugin;
        this.msgSer = messageService;
        this.config = plugin.getConfig();
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        UUID uuid = player.getUniqueId();

        Set<String> homeKeys = HomeManager.getHomeGroupKeys(player);
        StringBuilder indexBuilder = new StringBuilder();
        for (String homeKey : homeKeys) {
            String PATH_PREFIX = "homes." + uuid + "." + homeKey + ".";
            Location homeLoc = HomeManager.getHome(player, homeKey);

            //noinspection ConstantConditions // getWorld() can't be null: Locs w/o world can't be saved
            config.set(PATH_PREFIX+"world", homeLoc.getWorld().getName());
            config.set(PATH_PREFIX+"x",     homeLoc.getX());
            config.set(PATH_PREFIX+"y",     homeLoc.getY());
            config.set(PATH_PREFIX+"z",     homeLoc.getZ());
            config.set(PATH_PREFIX+"yaw",   homeLoc.getYaw());
            config.set(PATH_PREFIX+"pitch", homeLoc.getPitch());

            indexBuilder.append(homeKey);
            indexBuilder.append(" ");
        }
        String index = indexBuilder.substring(0, indexBuilder.length()-1);
        config.set("homes." + uuid + ".index", index);
        survivalTools.saveConfig();
    }
}
