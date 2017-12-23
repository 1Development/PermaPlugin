/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Youri
 */
public class PlayerCooldownList extends Object {

    public ArrayList<PlayerCooldown> list;

    public PlayerCooldownList() {
        list = new ArrayList<>();
    }

    public PlayerCooldown SearchPlayer(String UID) {
        boolean found = false;
        for (PlayerCooldown plr : list) {
            if (plr.UUID.equals(UID)) {
                found = true;
                return plr;
            }
        }
        if (!found) {
            PlayerCooldown cd = new PlayerCooldown(null, UID);
            list.add(cd);
            return cd;
        }
        return null;
    }
    
    public PlayerCooldown SearchPlayerName(String name) {
        boolean found = false;
        for (PlayerCooldown plr : list) {
            if(Bukkit.getOfflinePlayer(UUID.fromString(plr.UUID)).getName().equalsIgnoreCase(name)) {
                return plr;
            }
        }
        return null;
    }

    public void SetCooldown(String UID) {
        for (PlayerCooldown plr : list) {
            if (plr.UUID == UID) {
                plr.upTime = new Date();
            }
        }
        PlayerCooldown cd = new PlayerCooldown(new Date(), UID);
        list.add(cd);
    }
}
