package de.kiridevs.survivaltools.main;

import de.kiridevs.kiricore.Prefix;
import de.kiridevs.kiricore.managers.MessageService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

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
        messageService.sendSuccessMessage(Bukkit.getConsoleSender(), "The PlugIn was successfully enabled!");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        messageService.sendSuccessMessage(Bukkit.getConsoleSender(), "The PlugIn was successfully disabled!");
        super.onDisable();
    }
}
