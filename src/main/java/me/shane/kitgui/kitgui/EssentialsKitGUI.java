package me.shane.kitgui.kitgui;

import java.io.File;

import me.shane.kitgui.kitgui.Config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import me.shane.kitgui.kitgui.command.kit;
import me.shane.kitgui.kitgui.Config.KitConfig;
import me.shane.kitgui.kitgui.Config.Config;
import me.shane.kitgui.kitgui.Config.LanguageConfig;
import net.ess3.api.IEssentials;
public class EssentialsKitGUI extends JavaPlugin {
    public static Plugin plugin;

    public static IEssentials es = null;

    public static String CONFIG_INVTITLE = ChatColor.DARK_AQUA + "Kits";
    public static boolean CONFIG_FIRSTCHARCAPS = false;
    public static boolean CONFIG_INCLCMD_KITS = true;

    public static KitConfig kits = new KitConfig();

    public void onEnable(){
        plugin = this;

        // get essentials variable
        es = (IEssentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
        if(es == null) // if he isn't using the spigot version of spigot
            es = (IEssentials) Bukkit.getServer().getPluginManager().getPlugin("EssentialsX");
        if(es == null) // he isn't using essentials or essentialsX
            new NullPointerException("You aren't using the spigot version of Essentials or EssentialsX!").printStackTrace();

        // setup
        getServer().getPluginManager().registerEvents(listener, this);
        getCommand("kit").setExecutor(new me.shane.kitgui.kitgui.Kit());
        getCommand("kitcfg").setExecutor(new me.shane.kitgui.kitgui.command.kitcfg());

        // load config
        File dir = new File("plugins/Essentials_KitGUI");
        if(!dir.exists()) dir.mkdir();

        Config.load();
        LanguageConfig.load();
        if(KitConfig.exists()) kits = KitConfig.load();
    }

    private Listener listener = new Listener(){
        @EventHandler
        public void onInventoryClick(InventoryClickEvent event){
            kit.onInventoryClickEvent(event);
        }

        @EventHandler
        public void onInventoryDragEvent(InventoryDragEvent event){
            kit.onInventoryDragEvent(event);
        }

        @EventHandler
        public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event){
            String[] strs = event.getMessage().split(" ");
            String label = strs[0].replace("/", "");
            String[] args = new String[strs.length - 1];

            for(int i=1; i<strs.length; i++)
                args[i - 1] = strs[i];

            if(CONFIG_INCLCMD_KITS && label.equalsIgnoreCase("kits")){
                kit.onCommand(event.getPlayer(), label, args);
                event.setCancelled(true);
            }
        }
    };

    public static String getVersion(){
        return EssentialsKitGUI.plugin.getDescription().getVersion();
    }
}
