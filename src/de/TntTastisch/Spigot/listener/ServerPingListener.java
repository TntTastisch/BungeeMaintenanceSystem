package de.TntTastisch.Spigot.listener;

import de.TntTastisch.Spigot.Maintenance;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerPingListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        ServerPing.Players players = ping.getPlayers();
        ServerPing.Protocol protocol = ping.getVersion();
        if (Maintenance.config.getBoolean("Maintenance.enable")) {
            protocol.setProtocol(2);
            ping.setDescription(Maintenance.MaintenanceFirstMOTD + "\n" + Maintenance.MaintenanceSecondMOTD);
            protocol.setName(Maintenance.version);
            ping.setVersion(protocol);
            ping.setPlayers(players);
        } else if (!Maintenance.maintenance) {
            ping.setDescription(Maintenance.FirstMOTD + "\n" + Maintenance.SecondMOTD);
        }
    }

    @EventHandler
    public void onLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (Maintenance.maintenance) {
            if (!(player.hasPermission(Maintenance.permissions) || player.hasPermission(Maintenance.maintenancejoin))) {
                player.disconnect(new TextComponent(Maintenance.MaintenanceMessage));
            }
        }
    }
}
