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
        locationHashMap.put(key, pointsToLocation);
    }

    public Set<String> getHomeKeySet() {
        return locationHashMap.keySet();
    }
}
