package net.lapismc.lapisavatarjoin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

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
        List<String> configLines = plugin.config.getMessages().getStringList("Lines");
        //Loop over each row of the image to append text to the end of it
        for (int i = 0; i < avatar.length; i++) {
            String avatarLine = avatar[i];
            //Get the text from the config, color it and insert placeholders
            String appendText = plugin.config.colorMessage(plugin.config.replacePlaceholders(configLines.get(i), p));
            //TODO: if we want to try and center text, here is where to do it
            //Join the image and text together with a space in between
            avatar[i] = avatarLine + " " + appendText;
        }
        if (plugin.getConfig().getBoolean("DelayMessage"))
            Bukkit.getScheduler().runTaskLater(plugin, () -> p.sendMessage(avatar), 5);
        else
            p.sendMessage(avatar);
    }

}
