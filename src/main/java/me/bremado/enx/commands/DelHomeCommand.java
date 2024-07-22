package me.bremado.enx.commands;

import me.bremado.core.Core;
import me.bremado.core.model.EnxPlayer;
import me.bremado.core.model.homes.Home;
import me.bremado.core.utils.LocationInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class DelHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        if (!cmd.getLabel().equalsIgnoreCase("delhome")) return true;

        Player player = (Player) sender;
        EnxPlayer enxPlayer = Core.getPlayerCache().find(p -> p.getUniqueId() == player.getUniqueId());

        if (args.length == 0) {
            player.sendMessage("§cSintaxe incorreta, utilize '/delhome [nome]' para definir uma home.");
            return true;
        }

        var homes = enxPlayer.getHomes();
        if (homes == null || homes.isEmpty()) {
            player.sendMessage("§cVocê não possui nenhuma home.");
        }

        String homeName = args[0];
        if (!Pattern.matches("^[a-zA-Z0-9_]+$", homeName)) {
            player.sendMessage("§cNome de home inválido.");
            return true;
        }

        Home home = homes.stream().filter(h -> h.getName().equalsIgnoreCase(homeName)).findFirst().orElse(null);

        if (home == null) {
            player.sendMessage("§cHome não encontrada.");
            return true;
        }

        enxPlayer.getHomes().remove(home);
        player.sendMessage("§aHome removida com sucesso.");
        Core.getHomesTable().delete(player.getUniqueId(), home);
        return false;
    }
}
