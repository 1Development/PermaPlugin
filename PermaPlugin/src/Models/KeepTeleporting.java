/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Youri
 */
public class KeepTeleporting extends BukkitRunnable  {

    private int times;
    private Entity entity;
    private Location HomeLocation;
    public Entity Rider;

    public KeepTeleporting(Player entity, Location HomeLocation) {
        times = 4;
        this.entity = entity;
        this.HomeLocation = HomeLocation;
    }
    public KeepTeleporting(Player player, Horse horse, Location HomeLocation) {
        times = 8;
        this.entity = horse;
        this.Rider = player;
        this.HomeLocation = HomeLocation;
    }

    @Override
    public void run() {
        times--;
        entity.teleport(HomeLocation);
        
        if(Rider != null) {
            entity.eject();
            if(times <0)
                entity.addPassenger(Rider);
        }
        if(times<0) this.cancel();
    }
    
}
