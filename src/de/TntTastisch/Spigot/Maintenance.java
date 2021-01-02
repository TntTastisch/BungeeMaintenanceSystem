package de.TntTastisch.Spigot;

import de.TntTastisch.Spigot.commands.MaintenanceCMD;
import de.TntTastisch.Spigot.listener.ServerPingListener;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.conf.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Maintenance extends Plugin implements Listener {
    
    public static boolean maintenance;


    public static String prefix;
    public static String permissions;
    public static String noperms;
    public static String reloaded;
    public static String version;
    public static String maintenancejoin;
    public static String reason;
    public static String noreason;

    public static String MaintenanceKickMessage;
    public static String MaintenanceMessage;
    public static String Enabled;
    public static String Disabled;
    public static String alreadyEnabled;
    public static String alreadyDisabled;

    public static String FirstMOTD;
    public static String SecondMOTD;
    public static String MaintenanceFirstMOTD;
    public static String MaintenanceSecondMOTD;

    public static File file;
    public static net.md_5.bungee.config.Configuration config;


    @Override
    public void onEnable() {
        loadConfiguration();
        PluginManager pluginManager = BungeeCord.getInstance().getPluginManager();

        pluginManager.registerCommand(this, new MaintenanceCMD());
        pluginManager.registerListener(this, new ServerPingListener());



        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[]===========[ Maintenance ]===========[]"));
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[] §aThis Plugin was successfully activated."));
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[] §aAuthor: TntTastisch."));
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[] §aVersion: " + getDescription().getVersion() +  "."));
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[]===========[ Maintenance ]===========[]"));
    }

    @Override
    public void onDisable() {
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[]===========[ Maintenance ]===========[]"));
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[] §cThis Plugin was successfully deactivated."));
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[] §cAuthor: TntTastisch."));
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[] §cVersion: " + getDescription().getVersion() +  "."));
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("[]===========[ Maintenance ]===========[]"));
    }
    
    public void loadConfiguration() {
        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        file = new File(getDataFolder().getPath(), "config.yml");

        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {

            e.printStackTrace();
        }

            if(!config.contains("Maintenance.enable")) {
                config.set("Maintenance.enable", false);
            }

            if(!config.contains("Maintenance.prefix")) {
                config.set("Maintenance.prefix", "&8[&4Maintenance&8] &7");
            }

            if(!config.contains("Maintenance.permissions")) {
                config.set("Maintenance.permissions", "maintenance.use");
            }

            if(!config.contains("Maintenance.noperms")) {
                config.set("Maintenance.noperms", "&cYou have no rights to this command.");
            }

            if(!config.contains("Maintenance.reloaded")) {
                config.set("Maintenance.reloaded", "&7The configuration was &asuccessfully &7reloaded.");
            }

            if(!config.contains("Maintenance.maintenancejoin")) {
                config.set("Maintenance.maintenancejoin", "maintenance.bypass");
            }

            if(!config.contains("Maintenance.reason")) {
                config.set("Maintenance.reason", "&7Cunstruction");
            }

            if(!config.contains("Maintenance.noreason")) {
                config.set("Maintenance.noreason", "&cPlease specify a maintenance reason!");
            }

            if(!config.contains("Messages.Version")) {
                config.set("Messages.Version", "&4Maintenance");
            }

            if(!config.contains("Messages.MaintenanceKickMessage")) {
                config.set("Messages.MaintenanceKickMessage", "&8>> &4ServerNetwork.com &8<<\n&7This network is now under maintenance\n\n&7Reason&8: &e%reason%\n\n&7You want to contact us?\n&7TeamSpeak &8>> &ets.yourservernetwork.com\n&7Homepage &8>> &ewww.yourservernetwork.com");
            }

            if(!config.contains("Messages.MaintenanceMessage")) {
                config.set("Messages.MaintenanceMessage", "&8>> &4ServerNetwork.com &8<<\n&7This network is currently under maintenance\n\n&7Reason&8: &e%reason%\n\n&7You want to contact us?\n&7TeamSpeak &8>> &ets.yourservernetwork.com\n&7Homepage &8>> &ewww.yourservernetwork.com");
            }

            if(!config.contains("Messages.Enabled")) {
                config.set("Messages.Enabled", "&7The maintenancemode was successfully &aenabled&7 for %reason%");
            }

            if(!config.contains("Messages.Disabled")) {
                config.set("Messages.Disabled", "&7The maintenancemode was successfully &cdisabled&7.");
            }

            if(!config.contains("Messages.alreadyEnable")) {
                config.set("Messages.alreadyEnable", "&cThe maintenancemode is currently &aenabled&7.");
            }

            if(!config.contains("Messages.alreadyDisabled")) {
                config.set("Messages.alreadyDisabled", "&cThe maintenancemode is currently &cdisabled&7.");
            }

            if(!config.contains("MOTD.FirstMOTD")) {
                config.set("MOTD.FirstMOTD", "&4ServerNetwork.com &8- &fServer Network &8[&e1.8-1.12&8]");
            }

            if(!config.contains("MOTD.SecondMOTD")) {
                config.set("MOTD.SecondMOTD", "&aWe are online again &8| &4Maintenance by TntTastisch");
            }

            if(!config.contains("MOTD.MaintenanceFirstMOTD")) {
                config.set("MOTD.MaintenanceFirstMOTD", "&4ServerNetwork.com &8- &fServer Network &8[&e1.8-1.12&8]");
            }

            if(!config.contains("MOTD.MaintenanceSecondMOTD")) {
                config.set("MOTD.MaintenanceSecondMOTD", "&8[&c!&8] &7This network is currently under &4maintenance&7!");
            }



            maintenance = config.getBoolean("Maintenance.enable");

            prefix = config.getString("Maintenance.prefix").replaceAll("&", "§");
            permissions = config.getString("Maintenance.permissions").replaceAll("&", "§");
            noperms = config.getString("Maintenance.noperms").replaceAll("&", "§");
            reloaded = config.getString("Maintenance.reloaded").replaceAll("&", "§");
            version = config.getString("Messages.Version").replaceAll("&", "§");
            maintenancejoin = config.getString("Maintenance.maintenancejoin").replaceAll("&", "§");
            reason = config.getString("Maintenance.reason").replaceAll("&", "§");
            noreason = config.getString("Maintenance.noreason").replaceAll("&", "§");

            MaintenanceKickMessage = config.getString("Messages.MaintenanceKickMessage").replaceAll("&", "§").replace("%reason%", reason);
            MaintenanceMessage = config.getString("Messages.MaintenanceMessage").replaceAll("&", "§").replace("%reason%", reason);
            Enabled = config.getString("Messages.Enabled").replaceAll("&", "§").replace("%reason%", reason);
            Disabled = config.getString("Messages.Disabled").replaceAll("&", "§");
            alreadyEnabled = config.getString("Messages.alreadyEnable").replaceAll("&", "§");
            alreadyDisabled = config.getString("Messages.alreadyDisabled").replaceAll("&", "§");

            FirstMOTD = config.getString("MOTD.FirstMOTD").replaceAll("&", "§");
            SecondMOTD = config.getString("MOTD.SecondMOTD").replaceAll("&", "§");
            MaintenanceFirstMOTD = config.getString("MOTD.MaintenanceFirstMOTD").replaceAll("&", "§");
            MaintenanceSecondMOTD = config.getString("MOTD.MaintenanceSecondMOTD").replaceAll("&", "§");

            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

}
