package me.shane.kitgui.kitgui;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.shane.kitgui.kitgui.Library.Vault;

import java.util.ArrayList;

public class Util {

    public static ArrayList<Kit> getKits(Player player){
        ArrayList<Kit> list = new ArrayList<Kit>();
        for(String kitName:EssentialsKitGUI.es.getSettings().getKits().getKeys(false)){

            if(hasPermission(player, "essentials.kits." + kitName) || hasPermission(player, "essentials.kits.*")){
                Kit kit = EssentialsKitGUI.kits.getKit(kitName);
                list.add(kit);
            }

        }
        return list;
    }

    public static com.earth2me.essentials.Kit getKit(String kitname){
        ConfigurationSection kits = EssentialsKitGUI.es.getSettings().getKits();
        for (String kitItem:kits.getKeys(false)){
            try {
                com.earth2me.essentials.Kit kit = new com.earth2me.essentials.Kit(kitItem, EssentialsKitGUI.es);
                if(kit.getName().equalsIgnoreCase(kitname)){
                    return kit;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean givePlayerItems(Player player, String kitname){
        ConfigurationSection kits = EssentialsKitGUI.es.getSettings().getKits();
        for (String kitItem:kits.getKeys(false)){
            try {
                com.earth2me.essentials.Kit kit = new com.earth2me.essentials.Kit(kitItem, EssentialsKitGUI.es);
                if(kit.getName().equals(kitname)){
                    kit.expandItems(EssentialsKitGUI.es.getUser(player));
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static String firstCharCaps(String str){
        if(EssentialsKitGUI.CONFIG_FIRSTCHARCAPS == true)
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        return str;
    }

    public static ItemStack getItemStack(ItemStack is, String name){
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return is;
    }

    public static boolean isInteger(String str){
        try{
            Integer.valueOf(str);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static String getItemStackName(ItemStack is){
        if(is == null || is.getItemMeta() == null)
            return null;
        else
            return is.getItemMeta().getDisplayName();
    }

    public static boolean hasPermission(CommandSender sender, String permission){
        if(sender instanceof Player){

            Player player = (Player) sender;

            if(player.isOp()) return true;

            Boolean bl = Vault.hasPermission(player, permission);
            if(bl != null)
                return bl;
            else
                return player.hasPermission(permission);
        }else
            return true;
    }
}
