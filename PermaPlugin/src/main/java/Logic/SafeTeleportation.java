/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 *
 * @author Youri
 */
public class SafeTeleportation {

    public static Location FindSafeLocation(Location targetLoc) {
        targetLoc = new Location(targetLoc.getWorld(), targetLoc.getBlockX(), targetLoc.getBlockY(), targetLoc.getBlockZ());
        Location returnLoc = targetLoc.clone();
        if (IsSafe(returnLoc)) {
            return returnLoc.add(0.5, 0, 0.5);
        }

        for (int range = 1; range < 5; range++) {
            for (int x = -1* range; x < range; x++) {
                if (x > -1* range || x < range - 1) {
                    continue;
                }
                for (int y = -1* range; y < range; y++) {
                    if (y > -1*range || y < range - 1) {
                        continue;
                    }
                    for (int z = -1*range; z < range; z++) {
                        if (z > -1*range || z < range - 1) {
                            continue;
                        }
                        returnLoc = targetLoc.clone().add(x, y, z);
                        if (IsSafe(returnLoc)) {
                            return returnLoc.add(0.5, 0, 0.5);
                        }
                    }
                }
            }
        }

        targetLoc.setY(targetLoc.clone().getWorld().getHighestBlockYAt(targetLoc));
        return targetLoc.add(0.5, 0, 0.5);
    }

    private static boolean IsSafe(Location loc) {
        Block blockBelow = loc.getBlock();
        if (loc == null) {
            return false;
        }
        if (!loc.clone().add(0, -1, 0).getBlock().getType().isSolid()) {
            return false;
        }
        if (loc.clone().getBlock().getType().isSolid()) {
            return false;
        }
        if (loc.clone().add(0, 1, 0).getBlock().getType().isSolid()) {
            return false;
        }

        return true;
    }
}
