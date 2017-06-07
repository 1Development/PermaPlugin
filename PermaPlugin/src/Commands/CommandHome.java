/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Models.KeepTeleporting;
import Models.PlayerCooldown;
import Models.PlayerCooldownList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import permaplugin.Config;

/**
 *
 * @author Youri
 */
public class CommandHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        
        if (strings.length == 0) {
            Player player = (Player) cs;
            String uuid = player.getUniqueId().toString();
            PlayerCooldown plr = Config.playerCooldowns.SearchPlayer(uuid);

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE, Config.MinutesCooldown);
            Date date = cal.getTime();

            if (player.getBedSpawnLocation() != null) {
                System.out.println("Player:" + player.getName() + " has a bed set at: " + player.getBedSpawnLocation().toString());
            } else {
                System.out.println("Player:" + player.getName() + " has no bed set");
            }

            if (plr.upTime == null || new Date().after(plr.upTime)) {
                player.sendMessage("You will be teleported!");
                plr.upTime = date;
                Location teleportLoc;
                if (player.getBedSpawnLocation() != null) {
                    teleportLoc = player.getBedSpawnLocation();
                } else {
                    teleportLoc = Bukkit.getWorld("newSurvival").getSpawnLocation();
                }

                KeepTeleporting ktp = new KeepTeleporting(player, teleportLoc);
                player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(5 * 20, 10));
                player.addPotionEffect(PotionEffectType.SATURATION.createEffect(5 * 20, 5));
                player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(5 * 20, 5));

                ktp.runTaskTimer(Config.SpigotPlugin, 5, 5);

                if (player.getVehicle() != null && (player.getVehicle().getType() == EntityType.HORSE || player.getVehicle().getType() == EntityType.SKELETON_HORSE || player.getVehicle().getType() == EntityType.ZOMBIE_HORSE || player.getVehicle().getType() == EntityType.DONKEY)) {
                    if (player.getVehicle() instanceof Horse) {
                        Horse horse = (Horse) player.getVehicle();
                        if (horse.isTamed()) {
                            KeepTeleporting ktph = new KeepTeleporting(player, horse, teleportLoc);
                            horse.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(5 * 20, 10));
                            horse.addPotionEffect(PotionEffectType.SATURATION.createEffect(5 * 20, 5));
                            horse.addPotionEffect(PotionEffectType.REGENERATION.createEffect(5 * 20, 5));
                            ktph.runTaskTimer(Config.SpigotPlugin, 1, 5);
                            horse.eject();
                        }
                    }
                }

                return true;
            } else {
                player.sendMessage("It is on cooldown!");
                long diff = plr.upTime.getTime() - new Date().getTime();
                long minutesCd = diff / (60 * 1000);
                player.sendMessage(minutesCd + " minutes left");
            }
            return true;
        }
        return false;
    }

}
