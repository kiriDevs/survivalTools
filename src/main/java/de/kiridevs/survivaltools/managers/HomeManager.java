package de.kiridevs.survivaltools.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class HomeManager {
    private static HashMap<UUID, Location> homeMap = new HashMap<>();

    public static Location getHome(Player forPlayer) {
        return homeMap.getOrDefault(forPlayer.getUniqueId(), null);
    }

    public static void setHome(Player forPlayer, Location newHome) {
        homeMap.put(forPlayer.getUniqueId(), newHome);
    }

    public static HashMap<UUID, Location> getHomeMap() {
        return homeMap;
    }
}
