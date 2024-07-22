package me.bremado.enx.listener;

import me.bremado.core.Core;
import me.bremado.core.database.tables.HomesTable;
import me.bremado.core.model.EnxPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        var homes = Core.getHomesTable().find(event.getUniqueId());

        if (homes == null || homes.isEmpty()) {
            Core.getPlayerCache().save(EnxPlayer.builder().uniqueId(event.getUniqueId()).homes(new ArrayList<>()).build());
            return;
        }

        var player = EnxPlayer.builder().uniqueId(event.getUniqueId()).homes(homes).build();

        Core.getPlayerCache().save(player);
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Core.getPlayerCache().delete(player -> player.getUniqueId() == event.getPlayer().getUniqueId());
    }
}
