/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Youri
 */
public class PlayerCooldown {

    public Date upTime;
    public String UUID;

    public PlayerCooldown() {
    }

    public PlayerCooldown(Date lastusedDate, String uuid) {
        upTime = lastusedDate;
        UUID = uuid;
    }

}
