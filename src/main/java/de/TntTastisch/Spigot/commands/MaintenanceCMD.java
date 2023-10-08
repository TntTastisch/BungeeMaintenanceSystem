package de.TntTastisch.Spigot.commands;

import de.TntTastisch.Spigot.Maintenance;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;

import static de.TntTastisch.Spigot.Maintenance.*;

public class MaintenanceCMD extends Command {

    public MaintenanceCMD() {
        super("maintenance");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission(permissions)) {
            if (strings.length == 0) {
                commandSender.sendMessage(new TextComponent("§8§m============§r§8[ §4§lMaintenance §8]§8§m============"));
                commandSender.sendMessage(new TextComponent("§8>> §e/maintenance reload"));
                commandSender.sendMessage(new TextComponent("§8>> §e/maintenance motd"));
                commandSender.sendMessage(new TextComponent("§8>> §e/maintenance <Reason>"));
                commandSender.sendMessage(new TextComponent("§8>> §e/maintenance off"));
                commandSender.sendMessage(new TextComponent("§8§m============§r§8[ §4§lMaintenance §8]§8§m============"));
                return;
            }

            switch (strings[0].toLowerCase()) {
                case "reload": {
                    if (strings.length != 1) {
                        commandSender.sendMessage(new TextComponent("§8>> §e/maintenance reload"));
                        return;
                    }

                    try {
                        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Maintenance.getInstance().loadVariables();
                    commandSender.sendMessage(new TextComponent(prefix + reloaded));

                    break;
                }
                case "motd": {
                    if(strings.length != 1) {
                        commandSender.sendMessage(new TextComponent("§8>> §e/maintenance motd"));
                        return;
                    }

                    if (config.getBoolean("MOTD.Enabled")) {
                        config.set("MOTD.Enabled", false);
                        motdEnabled = false;

                        try {
                            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        commandSender.sendMessage(new TextComponent(prefix + motdDisable));
                        return;
                    }

                    config.set("MOTD.Enabled", true);
                    motdEnabled = true;

                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    commandSender.sendMessage(new TextComponent(prefix + motdEnable));

                    break;
                }
                case "off": {
                    if (strings.length != 1) {
                        commandSender.sendMessage(new TextComponent("§8>> §e/maintenance off"));
                        return;
                    }

                    if (!(config.getBoolean("Maintenance.enable"))) {
                        commandSender.sendMessage(prefix + alreadyDisabled);
                        return;
                    }

                    config.set("Maintenance.enable", false);
                    maintenance = false;
                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ProxyServer.getInstance().broadcast(new TextComponent(prefix + Disabled));

                    break;
                }
                default: {
                    if (strings.length < 1) {
                        commandSender.sendMessage(new TextComponent("§8>> §e/maintenance <Reason>"));
                        return;
                    }

                    String reason = "";
                    for (int i = 0; i != strings.length; i++) {
                        reason += strings[i] + " ";
                    }

                    if (config.getBoolean("Maintenance.enable")) {
                        commandSender.sendMessage(prefix + alreadyEnabled);
                        return;
                    }

                    config.set("Maintenance.enable", true);
                    config.set("Maintenance.reason", reason);
                    maintenance = true;

                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        if (!all.hasPermission(maintenancejoin) || !all.hasPermission(permissions)) {
                            all.disconnect(new TextComponent(MaintenanceKickMessage.replace("%reason%", reason)));
                        }
                    }


                    ProxyServer.getInstance().broadcast(new TextComponent(prefix + Enabled.replace("%reason%", reason)));

                    break;
                }
            }
            return;
        }
        commandSender.sendMessage(prefix + noperms);
    }
}
