package net.lapismc.lapisavatarjoin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinManager implements Listener {

    private final LapisAvatarJoin plugin;

    public JoinManager(LapisAvatarJoin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        Player p = e.getPlayer();
        String[] avatar = plugin.generator.getAvatar(p.getUniqueId());
        p.sendMessage(avatar);
    }

}
