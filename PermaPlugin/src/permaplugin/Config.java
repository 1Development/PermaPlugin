/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package permaplugin;

import Models.PlayerCooldown;
import Models.PlayerCooldownList;
import com.google.gson.Gson;
import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Youri
 */
public class Config {

    public static FileConfiguration MainConfig;
    public static PlayerCooldownList playerCooldowns;
    public static int MinutesCooldown;
    public static Plugin SpigotPlugin;

    public static void ConfigurePlugin(Plugin plugin) {
        SpigotPlugin= plugin;
        Gson gson = new Gson();
        if (MainConfig.contains("Cooldown")) {
            MinutesCooldown = MainConfig.getInt("Cooldown");
        } else {
            MinutesCooldown = 120;
            MainConfig.set("Cooldown", 120);
        }
        if (MainConfig.contains("PlayerCooldowns")) {
            String allPlayers = MainConfig.get("PlayerCooldowns").toString();
            playerCooldowns = gson.fromJson(allPlayers, PlayerCooldownList.class);
            if (playerCooldowns == null) {
                playerCooldowns = new PlayerCooldownList();
            }
        } else {
            playerCooldowns = new PlayerCooldownList();
            System.out.println("Created playerCooldowns");
        }

    }

    public static void ShutDownPlugin() {
        Gson gson = new Gson();
        MainConfig.set("PlayerCooldowns", gson.toJson(playerCooldowns));
        MainConfig.options().copyDefaults(true);

    }

}
