package de.TntTastisch.Spigot;

import de.TntTastisch.Spigot.commands.MaintenanceCMD;
import de.TntTastisch.Spigot.listener.ServerPingListener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Maintenance extends Plugin implements Listener {

    private static Maintenance instance;

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
    public static String motdEnable;
    public static String motdDisable;


    public static boolean motdEnabled;
    public static String FirstMOTD;
    public static String SecondMOTD;
    public static String MaintenanceFirstMOTD;
    public static String MaintenanceSecondMOTD;

    public static File file;
    public static Configuration config;


    @Override
    public void onEnable() {
        instance = this;
        loadConfiguration();
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();

        pluginManager.registerCommand(this, new MaintenanceCMD());
        pluginManager.registerListener(this, new ServerPingListener());

        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[]===========[ Maintenance ]===========[]"));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[] §aThis Plugin was successfully activated."));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[] §aAuthor: TntTastisch."));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[] §aVersion: " + getDescription().getVersion() + "."));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[]===========[ Maintenance ]===========[]"));
    }

    @Override
    public void onDisable() {
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[]===========[ Maintenance ]===========[]"));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[] §cThis Plugin was successfully deactivated."));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[] §cAuthor: TntTastisch."));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[] §cVersion: " + getDescription().getVersion() + "."));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[]===========[ Maintenance ]===========[]"));
    }

    private void loadConfiguration() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();

        file = new File(getDataFolder().getPath(), "config.yml");

        try {
            if (!file.exists()) {
                file.createNewFile();
                createConfigurationSection(file);
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadVariables();
    }

    private void createConfigurationSection(File file) throws IOException {
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        config.set("Maintenance.enable", false);
        config.set("Maintenance.prefix", "&8[&4Maintenance&8] &7");
        config.set("Maintenance.permissions", "maintenance.use");
        config.set("Maintenance.noperms", "&cYou have no rights to this command.");
        config.set("Maintenance.reloaded", "&7The configuration was &asuccessfully &7reloaded.");
        config.set("Maintenance.maintenancejoin", "maintenance.bypass");
        config.set("Maintenance.reason", "&7Construction");
        config.set("Maintenance.noreason", "&cPlease specify a maintenance reason!");
        config.set("Messages.Version", "&4Maintenance");
        config.set("Messages.MaintenanceKickMessage", "&8>> &4ServerNetwork.com &8<<\n&7This network is now under maintenance\n\n&7Reason&8: &e%reason%\n\n&7You want to contact us?\n&7TeamSpeak &8>> &ets.yourservernetwork.com\n&7Homepage &8>> &ewww.yourservernetwork.com");
        config.set("Messages.MaintenanceMessage", "&8>> &4ServerNetwork.com &8<<\n&7This network is currently under maintenance\n\n&7Reason&8: &e%reason%\n\n&7You want to contact us?\n&7TeamSpeak &8>> &ets.yourservernetwork.com\n&7Homepage &8>> &ewww.yourservernetwork.com");
        config.set("Messages.Enabled", "&7The maintenancemode was successfully &aenabled&7 for %reason%");
        config.set("Messages.Disabled", "&7The maintenancemode was successfully &cdisabled&7.");
        config.set("Messages.alreadyEnable", "&cThe maintenancemode is currently &aenabled&7.");
        config.set("Messages.alreadyDisabled", "&cThe maintenancemode is currently &cdisabled&7.");
        config.set("Messages.MOTD.Enabled", "&7MOTD was successfully &aenabled&7.");
        config.set("Messages.MOTD.Disabled", "&7MOTD was successfully &cdisabled&7.");
        config.set("MOTD.Enabled", true);
        config.set("MOTD.FirstMOTD", "&4ServerNetwork.com &8- &fServer Network &8[&e1.8-1.12&8]");
        config.set("MOTD.SecondMOTD", "&aWe are online again &8| &4Maintenance by TntTastisch");
        config.set("MOTD.MaintenanceFirstMOTD", "&4ServerNetwork.com &8- &fServer Network &8[&e1.8-1.12&8]");
        config.set("MOTD.MaintenanceSecondMOTD", "&8[&c!&8] &7This network is currently under &4maintenance&7!");
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
    }

    public void loadVariables() {
        maintenance = config.getBoolean("Maintenance.enable");
        prefix = config.getString("Maintenance.prefix").replaceAll("&", "§");
        permissions = config.getString("Maintenance.permissions").replaceAll("&", "§");
        noperms = config.getString("Maintenance.noperms").replaceAll("&", "§");
        reloaded = config.getString("Maintenance.reloaded").replaceAll("&", "§");
        version = config.getString("Messages.Version").replaceAll("&", "§");
        maintenancejoin = config.getString("Maintenance.maintenancejoin").replaceAll("&", "§");
        reason = config.getString("Maintenance.reason").replaceAll("&", "§");
        noreason = config.getString("Maintenance.noreason").replaceAll("&", "§");

        MaintenanceKickMessage = config.getString("Messages.MaintenanceKickMessage").replaceAll("&", "§");
        MaintenanceMessage = config.getString("Messages.MaintenanceMessage").replaceAll("&", "§");
        Enabled = config.getString("Messages.Enabled").replaceAll("&", "§");
        Disabled = config.getString("Messages.Disabled").replaceAll("&", "§");
        alreadyEnabled = config.getString("Messages.alreadyEnable").replaceAll("&", "§");
        alreadyDisabled = config.getString("Messages.alreadyDisabled").replaceAll("&", "§");
        motdEnable = config.getString("Messages.MOTD.Enabled").replaceAll("&", "§");
        motdDisable = config.getString("Messages.MOTD.Disabled").replaceAll("&", "§");

        motdEnabled = config.getBoolean("MOTD.Enabled");
        FirstMOTD = config.getString("MOTD.FirstMOTD").replaceAll("&", "§");
        SecondMOTD = config.getString("MOTD.SecondMOTD").replaceAll("&", "§");
        MaintenanceFirstMOTD = config.getString("MOTD.MaintenanceFirstMOTD").replaceAll("&", "§");
        MaintenanceSecondMOTD = config.getString("MOTD.MaintenanceSecondMOTD").replaceAll("&", "§");
    }

    public static Maintenance getInstance() {
        return instance;
    }
}
