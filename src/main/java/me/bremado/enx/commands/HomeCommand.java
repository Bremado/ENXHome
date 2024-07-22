package me.bremado.enx.commands;

import me.bremado.core.Core;
import me.bremado.core.model.EnxPlayer;
import me.bremado.enx.server.ServerConfig;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HomeCommand implements CommandExecutor {

    private HashMap<UUID, Long> cooldowns = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))return true;

        if (!cmd.getLabel().equalsIgnoreCase("home"))return true;

        Player player = (Player) sender;
        EnxPlayer enxPlayer = Core.getPlayerCache().find(p -> p.getUniqueId() == player.getUniqueId());

        if (args.length == 0) {
            if (enxPlayer.getHomes() == null || enxPlayer.getHomes().isEmpty()) {
                player.sendMessage("§cVocê ainda não possuí nenhuma home definida.");
                return true;
            }

            TextComponent text = new TextComponent("§aHomes disponíveis: ");
            enxPlayer.getHomes().forEach(home -> {
                TextComponent homeText = new TextComponent("§7" + home.getName() + " ");
                homeText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("§7Clique para ir até a home.")}));
                homeText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + home.getName()));
                text.addExtra(homeText);
            });
            player.spigot().sendMessage(text);
            return true;
        }

        var arg = args[0];
        var home = enxPlayer.getHomes().stream().filter(h -> h.getName().equalsIgnoreCase(arg)).findFirst().orElse(null);

        if (home == null) {
            player.sendMessage("§cHome não encontrada.");
            return true;
        }

        if (cooldowns.containsKey(player.getUniqueId()) && (System.currentTimeMillis() <= cooldowns.get(player.getUniqueId()))) {
            player.sendMessage("§cAguarde um pouco para teleportar-se novamente.");
            return true;
        }

        cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(ServerConfig.getCooldown()));

        var location = home.getLocationInfo().toLocation();

        player.teleport(location);

        if (ServerConfig.isSeeParticles()) {
            var particle = getParticle(ServerConfig.getParticleType());
            var sound = getSound(ServerConfig.getSoundType());

            if (particle != null) {
                player.getWorld().spawnParticle(particle, location.clone().add(0.0, 2.0, 0.0), new Random().nextInt(5));
            } else {
                Bukkit.getLogger().info("§cParticle not found: " + ServerConfig.getParticleType());
            }

            if (sound != null) {
                player.playSound(location, sound, 1, 1);
            } else {
                Bukkit.getLogger().info("§cSound not found: " + ServerConfig.getSoundType());
            }
        }

        player.sendMessage("§aVocê foi teleportado com sucesso para a home §7" + home.getName() + "§a.");
        return false;
    }

    private Particle getParticle(String particle) {
        var val = Arrays.stream(Particle.values()).collect(Collectors.toList());
        return val.stream().filter(par -> par.name().equalsIgnoreCase(particle)).findFirst().orElse(null);
    }

    private Sound getSound(String sound) {
        var val = Arrays.stream(Sound.values()).collect(Collectors.toList());
        return val.stream().filter(par -> par.name().equalsIgnoreCase(sound)).findFirst().orElse(null);
    }
}
