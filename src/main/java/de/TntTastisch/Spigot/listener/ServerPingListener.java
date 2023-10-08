package de.TntTastisch.Spigot.listener;

import de.TntTastisch.Spigot.Maintenance;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static de.TntTastisch.Spigot.Maintenance.*;

public class ServerPingListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        ServerPing.Players players = ping.getPlayers();
        ServerPing.Protocol protocol = ping.getVersion();
        if (maintenance) {
            protocol.setProtocol(2);
            if(motdEnabled) {
                ping.setDescription(MaintenanceFirstMOTD + "\n" + MaintenanceSecondMOTD);
            }
            protocol.setName(version);
            ping.setVersion(protocol);
            ping.setPlayers(players);
            return;
        }
        if(motdEnabled) {
            ping.setDescription(FirstMOTD + "\n" + SecondMOTD);
        }
    }

    @EventHandler
    public void onLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (maintenance) {
            if (!(player.hasPermission(permissions) || player.hasPermission(maintenancejoin))) {
                player.disconnect(new TextComponent(MaintenanceMessage.replace("%reason%", reason)));
            }
        }
    }
}
