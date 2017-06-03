/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

/**
 *
 * @author Youri
 */
public class CommandResetRepair implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        if (cs instanceof Player) {
            Player player = (Player) cs;

            if (args.length != 1) {
                return false;
            }
            if (!args[0].equalsIgnoreCase("xp") && !args[0].equalsIgnoreCase("item")) {
                return false;
            }

            if (player.getInventory().getItemInMainHand() != null) {

                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType() != Material.AIR) {

                    if (player.getInventory().getItemInMainHand().getEnchantments().isEmpty()) {
                        cs.sendMessage("You cannot reset a non-enchanted item, just create a new one.");
                        return true;
                    }

                    Material mat = item.getType();
                    if (mat == Material.ELYTRA || mat == Material.DIAMOND_PICKAXE || mat == Material.DIAMOND_AXE || mat == Material.DIAMOND_HOE || mat == Material.DIAMOND_SPADE || mat == Material.DIAMOND_SWORD || mat == Material.BOW || mat == Material.SHIELD
                            || mat == Material.IRON_PICKAXE || mat == Material.IRON_AXE || mat == Material.IRON_HOE || mat == Material.IRON_SPADE || mat == Material.IRON_SWORD || mat == Material.BOW || mat == Material.SHIELD) {
                        if (args[0].equalsIgnoreCase("item")) {
                            if(mat == Material.ELYTRA) {
                                cs.sendMessage("You can't repair an elytra using items");
                                return true;
                            }
                            
                            if (RemoveMatInventory(player.getInventory(), mat, 2)) {
                                ResetMainHandItem(player);
                                cs.sendMessage("Your " + GetReadableMatName(mat) + " has been reset");
                            } else {
                                cs.sendMessage("Your " + GetReadableMatName(mat) + " could not be reset,");
                                cs.sendMessage("because you don't have 2 " + GetReadableMatName(mat) + " to pay for the reset.");
                            }
                        } else if (args[0].equalsIgnoreCase("xp")) {
                            if (RemoveXP(player, 30)) {
                                ResetMainHandItem(player);
                                cs.sendMessage("Your " + GetReadableMatName(mat) + " has been reset");
                            } else {
                                cs.sendMessage("Your " + GetReadableMatName(mat) + " could not be reset,");
                                cs.sendMessage("because you don't have 30 xp levels to pay for the reset.");
                            }
                        }
                    } else if (mat == Material.DIAMOND_CHESTPLATE || mat == Material.DIAMOND_HELMET || mat == Material.DIAMOND_BOOTS || mat == Material.DIAMOND_LEGGINGS
                            || mat == Material.IRON_CHESTPLATE || mat == Material.IRON_HELMET || mat == Material.IRON_BOOTS || mat == Material.IRON_LEGGINGS) {
                        if (args[0].equalsIgnoreCase("item")) {
                            if (RemoveMatInventory(player.getInventory(), mat, 2)) {
                                ResetMainHandItem(player);
                                cs.sendMessage("Your " + GetReadableMatName(mat) + " has been reset");
                            } else {
                                cs.sendMessage("Your " + GetReadableMatName(mat) + " could not be reset,");
                                cs.sendMessage("because you don't have 2 " + GetReadableMatName(mat) + " to pay for the reset.");
                            }
                        } else if (args[0].equalsIgnoreCase("xp")) {
                            if (RemoveXP(player, 40)) {
                                ResetMainHandItem(player);
                                cs.sendMessage("Your " + GetReadableMatName(mat) + " has been reset");
                            } else {
                                cs.sendMessage("Your " + GetReadableMatName(mat) + " could not be reset,");
                                cs.sendMessage("because you don't have 40 xp levels to pay for the reset.");
                            }
                        }
                    }

                } else {
                    cs.sendMessage("You have to hold a item to reset it.");
                }
            } else {
                cs.sendMessage("You have to hold a item to reset it.");
            }

            return true;
        }

        cs.sendMessage(
                "Only executable by a player");

        return true;
    }

    public static String GetReadableMatName(Material mat) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.YELLOW);
        String[] strings = mat.toString().split("_");

        for (int i = 0; i < strings.length; i++) {
            if (i != 0) {
                sb.append(" ");
            }
            sb.append(strings[i].substring(0, 1).toUpperCase());
            sb.append(strings[i].substring(1).toLowerCase());
        }

        sb.append(ChatColor.RESET);
        return sb.toString();
    }

    public static int FindMatInventory(Inventory inv, Material mat) {
        int count = 0;
        ItemStack[] islist = inv.getContents();
        for (int i = 0; i < islist.length; i++) {
            if (islist[i] != null && islist[i].getType() == mat && islist[i].getDurability() < 1 && islist[i].getEnchantments().isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public static Boolean RemoveMatInventory(Inventory inv, Material mat, int toRemoveCount) {
        if (FindMatInventory(inv, mat) >= toRemoveCount) {
            ItemStack[] islist = inv.getContents();
            for (int i = 0; i < islist.length; i++) {
                if (islist[i] != null && islist[i].getType() == mat && islist[i].getDurability() < 1 && islist[i].getEnchantments().isEmpty()) {
                    inv.setItem(i, new ItemStack(Material.AIR));
                    toRemoveCount--;
                    if (toRemoveCount <= 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Boolean RemoveXP(Player player, int toRemoveCount) {
        if (player.getLevel() >= toRemoveCount || player.getGameMode() == GameMode.CREATIVE) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                player.setLevel(player.getLevel() - toRemoveCount);
            }
            return true;
        }

        return false;
    }

    public static void ResetMainHandItem(Player player) {
        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
        ((Repairable) meta).setRepairCost(1);
        player.getInventory().getItemInMainHand().setItemMeta(meta);
    }
}
