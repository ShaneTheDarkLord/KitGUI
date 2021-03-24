package me.shane.kitgui.kitgui.Config;
import me.shane.kitgui.kitgui.Language;
public class Config {
    public static ConfigManager manager = new ConfigManager("Essentials_KitGUI", "config.yml");

    public static void load(){
        manager.load();
        String version = manager.getConfigString("config-version");
        String invtitle = manager.getConfigString("inv-title");
        boolnull firstcharcaps = manager.getConfigBoolean("first-char-caps");
        boolnull incl_kits = manager.getConfigBoolean("includecmd-kits");

        if(invtitle != null)
            main.CONFIG_INVTITLE = Language.stringToChatColor(invtitle);
        if(firstcharcaps != boolnull.NULL)
            main.CONFIG_FIRSTCHARCAPS = firstcharcaps.toBoolean();
        if(incl_kits != boolnull.NULL)
            main.CONFIG_INCLCMD_KITS = incl_kits.toBoolean();

        if(version == null || version != null && !version.equals(main.getVersion()))
            save();
    }

    public static void save(){
        manager.clear();
        manager.addComment("Don't change this");
        manager.addConfig("config-version", main.getVersion());

        manager.addEmptyLine();

        manager.addComment("Set the title from the inventory");
        manager.addConfig("inv-title", Language.chatColorToString(main.CONFIG_INVTITLE));

        manager.addEmptyLine();

        manager.addComment("If it's enabled, the first character in the name of the kit is in caps");
        manager.addConfig("first-char-caps", main.CONFIG_FIRSTCHARCAPS);

        manager.addEmptyLine();

        manager.addComment("If it's enabled, /kits will open the GUI too");
        manager.addConfig("includecmd-kits", main.CONFIG_INCLCMD_KITS);

        manager.save();
    }
}
