package me.bremado.enx.commands;

import me.bremado.core.Core;
import me.bremado.core.database.tables.HomesTable;
import me.bremado.core.model.EnxPlayer;
import me.bremado.core.model.homes.Home;
import me.bremado.core.utils.LocationInfo;
import me.bremado.enx.server.ServerConfig;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SetHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        if (!cmd.getLabel().equalsIgnoreCase("sethome")) return true;

        Player player = (Player) sender;
        EnxPlayer enxPlayer = Core.getPlayerCache().find(p -> p.getUniqueId() == player.getUniqueId());

        if (args.length == 0) {
            player.sendMessage("§cSintaxe incorreta, utilize '/sethome [nome]' para definir uma home.");
            return true;
        }

        var homes = enxPlayer.getHomes();
        if (homes == null) {
            homes = new ArrayList<>();
        }

        String homeName = args[0];

        if (homes.stream().filter(home -> home.getName().equals(homeName)).findFirst().orElse(null) != null) {
            player.sendMessage("§cVocê já possui uma home com esse nome.");
            return true;
        }

        if (homeName.length() > 16) {
            player.sendMessage("§cO nome da home não pode ter mais de 16 caracteres.");
            return true;
        }

        var pattern = Pattern.compile("[^a-zA-Z]");
        var matcher = pattern.matcher(homeName);

        if (matcher.find()) {
            player.sendMessage("§cO nome da home não pode conter caracteres especiais.");
            return true;
        }

        Home home = Home.builder().name(homeName).locationInfo(new LocationInfo(player.getLocation())).build();

        player.sendMessage("§aHome definida com sucesso!");

        enxPlayer.getHomes().add(home);
        Core.getPlayerCache().update(enxPlayer);
        Core.getHomesTable().insert(enxPlayer.getUniqueId(), home);
        return false;
    }
}
