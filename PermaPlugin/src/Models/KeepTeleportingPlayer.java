/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Youri
 */
public class KeepTeleportingPlayer extends BukkitRunnable  {

    private int times = 8;
    private Player player;
    private Location HomeLocation;

    public KeepTeleportingPlayer(Player player, Location HomeLocation) {
        this.player = player;
        this.HomeLocation = HomeLocation;
    }

    @Override
    public void run() {
        times--;
        player.teleport(HomeLocation);
        if(times<0) this.cancel();
    }
    
}
