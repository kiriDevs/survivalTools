package de.kiridevs.survivaltools;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Set;

public class HomeGroup {
    private HashMap<String, Location> locationHashMap;

    public HomeGroup() {
        locationHashMap = new HashMap<>();
    }

    public Location getHome(String fromKey) {
        return locationHashMap.get(fromKey);
    }

    public void setHome(String key, Location pointsToLocation) {
        if (pointsToLocation.getWorld() == null) {
            throw new IllegalArgumentException("The passed Location doesn't include a world!");
        }

        locationHashMap.put(key, pointsToLocation);
    }

    public Set<String> getHomeKeySet() {
        return locationHashMap.keySet();
    }
}
