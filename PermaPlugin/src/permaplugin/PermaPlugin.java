/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package permaplugin;

import Commands.CommandHome;
import Commands.CommandResetHomeCD;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Youri
 */
public class PermaPlugin extends JavaPlugin {

    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
        Config.MainConfig = this.getConfig();
        Config.ConfigurePlugin(this);

        this.getCommand("home").setExecutor(new CommandHome());
        this.getCommand("ResetHomeCD").setExecutor(new CommandResetHomeCD());
        
        
        saveConfig();
    }

    // Fired when plugin is disabled
    @Override
    public void onDisable() {
        Config.ShutDownPlugin();
        saveConfig();
    }

}
