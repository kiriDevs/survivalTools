package de.kiridevs.survivaltools.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Class containing the HomeManager. Responsible for home location management
 * consisting of reading and manipulating Players' home locations.
 */
public class HomeManager {
    /**
     * The Map the home locations are saved to,
     * mapping players' UUIDs to Location objects.
     */
    private static HashMap<UUID, Location> homeMap = new HashMap<>();


    /**
    * A function to get a Location object of the currently set
    * home location of the queried player.
    *
    * @param forPlayer The player to get the home location for
    * @return A world-including Location object when there is one,
    *         null if a Player doesn't have a home location set.
    */
    public static Location getHome(Player forPlayer) {
        return homeMap.getOrDefault(forPlayer.getUniqueId(), null);
    }

    /**
     * A method to update the home location of the given player to
     * the given Location object
     *
     * @param forPlayer The Player object of the player to update the
     *                  home location of
     * @param newHome The Location object of the new home location;
     *                should be world-including
     * @throws IllegalArgumentException Thrown when the Location (param
     *                                  newHome) does not include a world
     */
    public static void setHome(Player forPlayer, Location newHome) {
        if (newHome.getWorld() == null) {
            throw new IllegalArgumentException("No world was included in the passed Location!");
        }

        homeMap.put(forPlayer.getUniqueId(), newHome);
    }

    /**
     * A method to return the full Map of home locations currently loaded
     * (only locations of online players will be loaded at any given time)
     *
     * @return A HashMap mapping a Location object to a Players' UUID;
     *         The Location object will contain the currently set
     *         home location of the player
     */
    public static HashMap<UUID, Location> getHomeMap() {
        return homeMap;
    }
}
