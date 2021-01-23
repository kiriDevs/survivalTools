package de.kiridevs.survivaltools.commands;

import de.kiridevs.kiricore.managers.MessageService;
import de.kiridevs.survivaltools.managers.HomeManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CMDhome implements CommandExecutor {
    MessageService msgSer;
    public CMDhome(MessageService messageService) { this.msgSer = messageService; }

    @Override
    public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
        if (!cmdSender.hasPermission("survival.home")) {
            ArrayList<String> completion = new ArrayList<>() {{ add("survival.home"); }};
            msgSer.sendErrorMessage(cmdSender, "noperm", completion);
            return true;
        }

        if (!(cmdSender instanceof Player)) {
            msgSer.sendErrorMessage(cmdSender, "playersonly", null);
            return true;
        }

        if (!(args.length == 0)) {
            ArrayList<String> completion = new ArrayList<>() {{ add("/home"); }};
            msgSer.sendErrorMessage(cmdSender, "syntax", completion);
            return true;
        }

        Player player = (Player) cmdSender;
        Location playerHome = HomeManager.getHome(player);

        if (playerHome == null) {
            msgSer.sendErrorMessage(player, "You did not set a home point yet!");
        } else {
            player.teleport(playerHome);
            msgSer.sendSuccessMessage(player, "You were teleported to your home position!");
        }
        return true;
    }
}
