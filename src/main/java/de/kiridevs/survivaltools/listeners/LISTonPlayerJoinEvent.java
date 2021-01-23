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
    private final Plugin survivaltools;
    private final MessageService msgSer;
    public LISTonPlayerJoinEvent(MessageService msgSer, Plugin plugin) {
        this.msgSer = msgSer;
        this.survivaltools = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent playerJoinEvent) {
        FileConfiguration config = survivaltools.getConfig();

        Player player = playerJoinEvent.getPlayer();
        UUID uuid = player.getUniqueId();

        final String KEY_PREFIX = "homes." + uuid.toString() + ".";

        double homeX = config.getDouble(KEY_PREFIX + "x");
        double homeY = config.getDouble(KEY_PREFIX + "y");
        double homeZ = config.getDouble(KEY_PREFIX + "z");
        float homeYaw = (float) config.getDouble(KEY_PREFIX + "yaw");
        float homePitch = (float) config.getDouble(KEY_PREFIX + "pitch");
        String homeWorldString = config.getString(KEY_PREFIX + "world");

        if (homeWorldString == null) {
            return;
        }
        Bukkit.getConsoleSender().sendMessage("hewo");
        World homeWorld = Bukkit.getWorld(homeWorldString);

        Location homeLocation = new Location(homeWorld, homeX, homeY, homeZ, homeYaw, homePitch);
        boolean xNull = homeX == 0.0;
        boolean yNull = homeY == 0.0;
        boolean zNull = homeZ == 0.0;
        boolean yawNull = homeYaw == 0.0;
        boolean pitchNull = homePitch == 0.0;
        boolean worldNull = homeWorld == null;

        if (xNull && yNull && zNull && yawNull && pitchNull && worldNull) {
            msgSer.sendErrorMessage(Bukkit.getConsoleSender(), "Couldn't find home location for " + player.getDisplayName() + "!");
            return;
        }

        HomeManager.setHome(player, homeLocation);
    }
}
