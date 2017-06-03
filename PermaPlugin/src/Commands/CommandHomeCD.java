/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Models.KeepTeleporting;
import Models.PlayerCooldown;
import java.util.Calendar;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import permaplugin.Config;

/**
 *
 * @author Youri
 */
public class CommandHomeCD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        
        if (cs instanceof Player) {
            Player player = (Player) cs;
            String uuid = player.getUniqueId().toString();
            PlayerCooldown plr = Config.playerCooldowns.SearchPlayer(uuid);

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE, Config.MinutesCooldown);
            Date date = cal.getTime();

            if (plr.upTime == null || new Date().after(plr.upTime)) {
                player.sendMessage("Home command is available!");

            } else {
                player.sendMessage("Home command is on cooldown!");
                long diff = plr.upTime.getTime() - new Date().getTime();
                long minutesCd = diff / (60 * 1000);
                player.sendMessage(minutesCd + " minutes left");
            }
            return true;
        }
        return false;
    }

}
