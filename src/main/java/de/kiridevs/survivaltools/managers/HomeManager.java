package de.kiridevs.survivaltools.managers;

import de.kiridevs.survivaltools.HomeGroup;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Class containing the HomeManager. Responsible for home location management
 * consisting of reading and manipulating Players' home locations.
 */
public class HomeManager {
    /**
     * The Map the HomeGroups for every Player are saved to,
     * mapping players' UUIDs to HomeGroup objects.
     */
    private static HashMap<UUID, HomeGroup> homeGroupMap = new HashMap<>();
    
    public static Location getHome(Player forPlayer, String key) {
        UUID playerUuid = forPlayer.getUniqueId();
        return homeGroupMap.getOrDefault(playerUuid, new HomeGroup()).getHome(key);
    }

    public static void setHome(Player forPlayer, String key, Location homeToSet) {
        UUID playerUuid = forPlayer.getUniqueId();

        HomeGroup manipulationHomeGroup = homeGroupMap.getOrDefault(playerUuid, new HomeGroup()); //  Get working copy
        manipulationHomeGroup.setHome(key, homeToSet);                                            // Edit working copy
        homeGroupMap.put(playerUuid, manipulationHomeGroup);                                      // Save working copy
    }

    public static Set<String> getHomeGroupKeys(Player ofPlayer) {
        UUID playerUuid = ofPlayer.getUniqueId();
        HomeGroup homeGroup = homeGroupMap.getOrDefault(playerUuid, new HomeGroup());

        return homeGroup.getHomeKeySet();
    }
}
