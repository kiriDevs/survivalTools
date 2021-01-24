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

    /**
     * Method to get a specific Location object of a specific home for a
     * specific Player
     *
     * @param forPlayer The Player to get a home for
     * @param key The player-chosen key for the home
     * @return The Location object for the requested home
     */
    public static Location getHome(Player forPlayer, String key) {
        UUID playerUuid = forPlayer.getUniqueId();
        return homeGroupMap.getOrDefault(playerUuid, new HomeGroup()).getHome(key);
    }

    /**
     * Method to set (or replace) a specific home for a specific player
     *
     * @param forPlayer The Player to set / change the home for
     * @param key The player-specific home-key to save the new home under
     * @param homeToSet The Location to set the home to
     */
    public static void setHome(Player forPlayer, String key, Location homeToSet) {
        UUID playerUuid = forPlayer.getUniqueId();

        HomeGroup manipulationHomeGroup = homeGroupMap.getOrDefault(playerUuid, new HomeGroup()); //  Get working copy
        manipulationHomeGroup.setHome(key, homeToSet);                                            // Edit working copy
        homeGroupMap.put(playerUuid, manipulationHomeGroup);                                      // Save working copy
    }

    /**
     * Method to query the keys of all homes a spefic Player has set
     *
     * @param ofPlayer The Player to get the homeKeys from
     * @return A String-Set containing the keys of all homes the queried player has set
     */
    public static Set<String> getHomeGroupKeys(Player ofPlayer) {
        UUID playerUuid = ofPlayer.getUniqueId();
        HomeGroup homeGroup = homeGroupMap.getOrDefault(playerUuid, new HomeGroup());

        return homeGroup.getHomeKeySet();
    }
}
