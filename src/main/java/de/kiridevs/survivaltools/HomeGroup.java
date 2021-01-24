package de.kiridevs.survivaltools;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Set;

/** A class to group multiple home Locations in a single Instance */
public class HomeGroup {
    /** The HashMap mapping a String (a "homeKey") to a Location (the Location of the home) */
    private HashMap<String, Location> locationHashMap;

    /** Contructor of the HomeGroup - Creates an empty HomeGroup */
    public HomeGroup() {
        locationHashMap = new HashMap<>();
    }

    /**
     * Method to get a specific home Location using the Location's key
     *
     * @param fromKey The key to check the mapped Location for
     * @return The queried Location, if it exists;
     *         null if the given key is not mapped to a Location
     */
    public Location getHome(String fromKey) {
        return locationHashMap.get(fromKey);
    }

    /**
     * Method to set a specific home Location
     *
     * @param key The key of the new / replaced home Location
     * @param pointsToLocation The Location of the new / replaced home
     * @throws IllegalArgumentException Thrown when the passen Location does not include a world
     */
    public void setHome(String key, Location pointsToLocation) {
        if (pointsToLocation.getWorld() == null) {
            throw new IllegalArgumentException("The passed Location doesn't include a world!");
        }

        locationHashMap.put(key, pointsToLocation);
    }

    /**
     * Method to get all valid keys that map to a Location in the HomeGroup
     *
     * @return A Set of all the valid keys that are mapped to a Location
     */
    public Set<String> getHomeKeySet() {
        return locationHashMap.keySet();
    }
}
