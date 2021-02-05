package de.kiridevs.survivaltools.listeners;

import de.kiridevs.kiricore.managers.AfkManager;
import de.kiridevs.kiricore.managers.MessageService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class LISTonEntityDamageEvent implements Listener {
    MessageService messageService;
    Plugin survivaltools;

    public LISTonEntityDamageEvent(MessageService messageService, Plugin plugin) {
        this.messageService = messageService;
        this.survivaltools = plugin;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent entityDamageEvent) {
        if (!(entityDamageEvent.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) entityDamageEvent.getEntity();
        if (AfkManager.isAfk(player)) {
            entityDamageEvent.setCancelled(true);
        }

    }
}
