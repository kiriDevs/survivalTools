package de.kiridevs.survivaltools.listeners;

import de.kiridevs.kiricore.managers.MessageService;
import de.kiridevs.survivaltools.managers.HomeManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class LISTonPlayerQuitEvent implements Listener {
    Plugin survivaltools;
    MessageService msgSer;
    public LISTonPlayerQuitEvent(MessageService messageService, Plugin plugin) {
        this.msgSer = messageService;
        this.survivaltools = plugin;
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();

        Location homeLoc = HomeManager.getHome(player);

        if (homeLoc == null) {
            msgSer.sendErrorMessage(Bukkit.getConsoleSender(), player.getDisplayName() + " didn't set a home location!");
            return;
        }

        FileConfiguration config = survivaltools.getConfig();
        final String PATH_PREFIX = "homes." + player.getUniqueId().toString() + ".";

        config.set(PATH_PREFIX + "x", homeLoc.getX());
        config.set(PATH_PREFIX + "y", homeLoc.getY());
        config.set(PATH_PREFIX + "z", homeLoc.getZ());
        config.set(PATH_PREFIX + "yaw", homeLoc.getYaw());
        config.set(PATH_PREFIX + "pitch", homeLoc.getPitch());
        //noinspection ConstantConditions // the location is saved by the plugin and always includes a world
        config.set(PATH_PREFIX + "world", homeLoc.getWorld().getName());

        survivaltools.saveConfig();
    }
}
