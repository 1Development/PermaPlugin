/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Models.PlayerCooldown;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import permaplugin.Config;

/**
 *
 * @author Youri
 */
public class CommandResetHomeCD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {

        if (cs instanceof Player && cs.isOp()) {
            if (args.length == 0) {
                Config.playerCooldowns.list.clear();
                Bukkit.broadcastMessage("/home cooldowns of all players have been reset!");
                return true;
            } else if (args.length > 0) {
                PlayerCooldown cd = Config.playerCooldowns.SearchPlayerName(args[0]);
                if (cd != null) {
                    Config.playerCooldowns.list.remove(cd);
                    cs.sendMessage("Succesfully reset cooldown of '" + args[0] + "'");
                    return true;
                } else {
                    cs.sendMessage("Player '" + args[0] + "' not found");
                    return true;
                }

            }

        }
        return false;
    }

}
