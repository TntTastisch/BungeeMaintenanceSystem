package de.TntTastisch.Spigot.commands;

import de.TntTastisch.Spigot.Maintenance;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import sun.applet.Main;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.concurrent.TimeUnit;

public class MaintenanceCMD extends Command {

    public MaintenanceCMD() {
        super("maintenance");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission(Maintenance.permissions)) {


            if (strings.length == 0) {
                commandSender.sendMessage(new TextComponent("§8§m============§r§8[ §4§lMaintenance §8]§8§m============"));
                commandSender.sendMessage(new TextComponent("§8>> §e/maintenance reload"));
                commandSender.sendMessage(new TextComponent("§8>> §e/maintenance <Reason>"));
                commandSender.sendMessage(new TextComponent("§8>> §e/maintenance off"));
                commandSender.sendMessage(new TextComponent("§8§m============§r§8[ §4§lMaintenance §8]§8§m============"));

            } else if (strings.length == 1) {

                if (strings[0].equalsIgnoreCase("reload")) {
                    try {
                        Maintenance.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Maintenance.file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Maintenance.maintenance = Maintenance.config.getBoolean("Maintenance.enable");

                    Maintenance.prefix = Maintenance.config.getString("Maintenance.prefix").replaceAll("&", "§");
                    Maintenance.permissions = Maintenance.config.getString("Maintenance.permissions").replaceAll("&", "§");
                    Maintenance.noperms = Maintenance.config.getString("Maintenance.noperms").replaceAll("&", "§");
                    Maintenance.reloaded = Maintenance.config.getString("Maintenance.reloaded").replaceAll("&", "§");
                    Maintenance.version = Maintenance.config.getString("Messages.Version").replaceAll("&", "§");
                    Maintenance.maintenancejoin = Maintenance.config.getString("Maintenance.maintenancejoin").replaceAll("&", "§");
                    Maintenance.reason = Maintenance.config.getString("Maintenance.reason").replaceAll("&", "§");
                    Maintenance.noreason = Maintenance.config.getString("Maintenance.noreason").replaceAll("&", "§");

                    Maintenance.MaintenanceKickMessage = Maintenance.config.getString("Messages.MaintenanceKickMessage").replaceAll("&", "§").replace("%reason%", Maintenance.reason);
                    Maintenance.MaintenanceMessage = Maintenance.config.getString("Messages.MaintenanceMessage").replaceAll("&", "§").replace("%reason%", Maintenance.reason);
                    Maintenance.Enabled = Maintenance.config.getString("Messages.Enabled").replaceAll("&", "§").replace("%reason%", Maintenance.reason);
                    Maintenance.Disabled = Maintenance.config.getString("Messages.Disabled").replaceAll("&", "§");
                    Maintenance.alreadyEnabled = Maintenance.config.getString("Messages.alreadyEnable").replaceAll("&", "§");
                    Maintenance.alreadyDisabled = Maintenance.config.getString("Messages.alreadyDisabled").replaceAll("&", "§");

                    Maintenance.FirstMOTD = Maintenance.config.getString("MOTD.FirstMOTD").replaceAll("&", "§");
                    Maintenance.SecondMOTD = Maintenance.config.getString("MOTD.SecondMOTD").replaceAll("&", "§");
                    Maintenance.MaintenanceFirstMOTD = Maintenance.config.getString("MOTD.MaintenanceFirstMOTD").replaceAll("&", "§");
                    Maintenance.MaintenanceSecondMOTD = Maintenance.config.getString("MOTD.MaintenanceSecondMOTD").replaceAll("&", "§");

                    commandSender.sendMessage(Maintenance.prefix + Maintenance.reloaded);

                } else if (strings[0].equalsIgnoreCase("off")) {
                    if (!Maintenance.config.getBoolean("Maintenance.enable") == false) {
                        Maintenance.config.set("Maintenance.enable", false);
                        Maintenance.maintenance = false;
                        try {
                            ConfigurationProvider.getProvider(YamlConfiguration.class).save(Maintenance.config, Maintenance.file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ProxyServer.getInstance().broadcast(new TextComponent(Maintenance.prefix + Maintenance.Disabled));
                    } else {
                        commandSender.sendMessage(Maintenance.prefix + Maintenance.alreadyDisabled);
                    }
                } else {
                    String reason = strings[0];
                    if (reason != null) {
                        if (!Maintenance.config.getBoolean("Maintenance.enable") == true) {

                            Maintenance.config.set("Maintenance.enable", true);
                            Maintenance.config.set("Maintenance.reason", reason);
                            Maintenance.maintenance = true;

                            try {
                                ConfigurationProvider.getProvider(YamlConfiguration.class).save(Maintenance.config, Maintenance.file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                Maintenance.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Maintenance.file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Maintenance.maintenance = Maintenance.config.getBoolean("Maintenance.enable");

                            Maintenance.prefix = Maintenance.config.getString("Maintenance.prefix").replaceAll("&", "§");
                            Maintenance.permissions = Maintenance.config.getString("Maintenance.permissions").replaceAll("&", "§");
                            Maintenance.noperms = Maintenance.config.getString("Maintenance.noperms").replaceAll("&", "§");
                            Maintenance.reloaded = Maintenance.config.getString("Maintenance.reloaded").replaceAll("&", "§");
                            Maintenance.version = Maintenance.config.getString("Messages.Version").replaceAll("&", "§");
                            Maintenance.maintenancejoin = Maintenance.config.getString("Maintenance.maintenancejoin").replaceAll("&", "§");
                            Maintenance.reason = Maintenance.config.getString("Maintenance.reason").replaceAll("&", "§");
                            Maintenance.noreason = Maintenance.config.getString("Maintenance.noreason").replaceAll("&", "§");

                            Maintenance.MaintenanceKickMessage = Maintenance.config.getString("Messages.MaintenanceKickMessage").replaceAll("&", "§").replace("%reason%", Maintenance.reason);
                            Maintenance.MaintenanceMessage = Maintenance.config.getString("Messages.MaintenanceMessage").replaceAll("&", "§").replace("%reason%", Maintenance.reason);
                            Maintenance.Enabled = Maintenance.config.getString("Messages.Enabled").replaceAll("&", "§").replace("%reason%", Maintenance.reason);
                            Maintenance.Disabled = Maintenance.config.getString("Messages.Disabled").replaceAll("&", "§");
                            Maintenance.alreadyEnabled = Maintenance.config.getString("Messages.alreadyEnable").replaceAll("&", "§");
                            Maintenance.alreadyDisabled = Maintenance.config.getString("Messages.alreadyDisabled").replaceAll("&", "§");

                            Maintenance.FirstMOTD = Maintenance.config.getString("MOTD.FirstMOTD").replaceAll("&", "§");
                            Maintenance.SecondMOTD = Maintenance.config.getString("MOTD.SecondMOTD").replaceAll("&", "§");
                            Maintenance.MaintenanceFirstMOTD = Maintenance.config.getString("MOTD.MaintenanceFirstMOTD").replaceAll("&", "§");
                            Maintenance.MaintenanceSecondMOTD = Maintenance.config.getString("MOTD.MaintenanceSecondMOTD").replaceAll("&", "§");


                            for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                if (!all.hasPermission(Maintenance.maintenancejoin) || !all.hasPermission(Maintenance.permissions)) {

                                    all.disconnect(new TextComponent(Maintenance.MaintenanceKickMessage));

                                }
                            }


                            ProxyServer.getInstance().broadcast(new TextComponent(Maintenance.prefix + Maintenance.Enabled));
                        } else {
                            commandSender.sendMessage(Maintenance.prefix + Maintenance.alreadyEnabled);
                        }
                    } else {
                        commandSender.sendMessage(Maintenance.prefix + Maintenance.noreason);
                    }
                }
            }
        } else {
            commandSender.sendMessage(Maintenance.prefix + Maintenance.noperms);
        }
    }
}
