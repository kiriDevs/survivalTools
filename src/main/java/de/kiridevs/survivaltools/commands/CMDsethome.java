package de.kiridevs.survivaltools.commands;

import de.kiridevs.kiricore.managers.MessageService;
import de.kiridevs.survivaltools.managers.HomeManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CMDsethome implements CommandExecutor {
    MessageService msgSer;

    public CMDsethome(MessageService messageService) {
        this.msgSer = messageService;
    }

    @Override
    public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
        if (!(cmdSender.hasPermission("survival.home"))) {
            ArrayList<String> completion = new ArrayList<>(){{ add("survival.home"); }};
            msgSer.sendErrorMessage(cmdSender, "noperm", completion);
            return true;
        }

        if (!(cmdSender instanceof Player)) {
            msgSer.sendErrorMessage(cmdSender, "playersonly", null);
            return true;
        }

        if (args.length > 1) {
            ArrayList<String> completion = new ArrayList<>(){{ add("/sethome [home]"); }};
            msgSer.sendErrorMessage(cmdSender, "syntax", completion);
            return true;
        }

        Player player = (Player) cmdSender;

        String homeKey;
        if (args.length == 0) {
            homeKey = "home";
        } else {
            homeKey = args[0];
        }

        Location loc = player.getLocation();

        HomeManager.setHome(player, homeKey, loc);
        msgSer.sendSuccessMessage(cmdSender, "Your new home \"§6" + homeKey + "\"§a was successfully set!");

        return true;
    }

}
