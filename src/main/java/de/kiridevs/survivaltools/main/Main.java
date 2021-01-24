package de.kiridevs.survivaltools.main;

import de.kiridevs.kiricore.Prefix;
import de.kiridevs.kiricore.managers.MessageService;
import de.kiridevs.survivaltools.commands.CMDhome;
import de.kiridevs.survivaltools.commands.CMDsethome;
import de.kiridevs.survivaltools.listeners.LISTonPlayerJoinEvent;
import de.kiridevs.survivaltools.listeners.LISTonPlayerQuitEvent;
import de.kiridevs.survivaltools.managers.HomeManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Main extends JavaPlugin {
    HashMap<String, Prefix> prefixHashMap = new HashMap<>() {{
        put("success", new Prefix("SurvivalTools", "9", "a"));
        put("error"  , new Prefix("SurvivalTools", "9", "c"));
        put("info"   , new Prefix("SurvivalTools", "9", "b"));
    }};

    HashMap<String, String> defaultMessages = new HashMap<>() {{
        put("noperm"     , "Sorry, but you don't have permission to that! (§6{0}§c)");
        put("playersonly", "Only players can use that command!"                     );
        put("syntax"     , "Please use the command like this: §6{0}"                );
    }};

    MessageService messageService = new MessageService(prefixHashMap, defaultMessages);

    @Override
    public void onEnable() {
        messageService.sendInfoMessage(Bukkit.getConsoleSender(), "Registering listeners...");
        Bukkit.getPluginManager().registerEvents(new LISTonPlayerJoinEvent(messageService, this), this);
        Bukkit.getPluginManager().registerEvents(new LISTonPlayerQuitEvent(messageService, this), this);

        messageService.sendInfoMessage(Bukkit.getConsoleSender(), "Registering commands...");
        getCommand("home").setExecutor(new CMDhome(messageService));
        getCommand("sethome").setExecutor(new CMDsethome(messageService));

        messageService.sendInfoMessage(Bukkit.getConsoleSender(), "Loading home location of online players...");
        FileConfiguration config = getConfig();
        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();

        for (Player player : onlinePlayers) {
            UUID uuid = player.getUniqueId();

            String index = config.getString("homes." + uuid + ".index");
            if (index == null) { return; }
            String[] existingHomes = index.split(" ");

            for (String homeKey : existingHomes) {
                double locX = config.getDouble("homes." + uuid + "." + homeKey + "x");
                double locY = config.getDouble("homes." + uuid + "." + homeKey + "y");
                double locZ = config.getDouble("homes." + uuid + "." + homeKey + "z");
                float locYaw = (float) config.getDouble("homes." + uuid + "." + homeKey + ".yaw");
                float locPitch = (float) config.getDouble("homes." + uuid + "." + homeKey + ".pitch");
                String locWorldName = config.getString("homes." + uuid + "." + homeKey + ".world");

                assert locWorldName != null;
                World locWorld = Bukkit.getWorld(locWorldName);
                Location homeLocation = new Location(locWorld, locX, locY, locZ, locYaw, locPitch);

                HomeManager.setHome(player, homeKey, homeLocation);
            }
        }

        messageService.sendSuccessMessage(Bukkit.getConsoleSender(), "The PlugIn was successfully enabled!");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        messageService.sendInfoMessage(Bukkit.getConsoleSender(), "Saving home locations of online players...");
        FileConfiguration config = getConfig();
        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (Player player : onlinePlayers) {
            UUID uuid = player.getUniqueId();
            Set<String> homeKeys = HomeManager.getHomeGroupKeys(player);
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
            }
        }

        messageService.sendSuccessMessage(Bukkit.getConsoleSender(), "The PlugIn was successfully disabled!");
        super.onDisable();
    }
}
