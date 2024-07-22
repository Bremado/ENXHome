package me.bremado.enx;

import me.bremado.core.Core;
import me.bremado.core.database.credential.DatabaseCredential;
import me.bremado.core.database.tables.HomesTable;
import me.bremado.core.model.homes.Home;
import me.bremado.enx.commands.DelHomeCommand;
import me.bremado.enx.commands.HomeCommand;
import me.bremado.enx.commands.SetHomeCommand;
import me.bremado.enx.listener.PlayerConnectionListener;
import me.bremado.enx.server.ServerConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

    @Override
    public void onLoad() {
        saveDefaultConfig();
        Core.getDatabase().connect(DatabaseCredential.builder()
                .host(getConfig().getString("database.host"))
                .port(getConfig().getInt("database.port"))
                .database(getConfig().getString("database.database"))
                .username(getConfig().getString("database.username"))
                .password(getConfig().getString("database.password"))
                .build()
        );
        ServerConfig.setCooldown(getConfig().getInt("cooldown-teleport"));
        ServerConfig.setSeeParticles(getConfig().getBoolean("see-particles"));
        ServerConfig.setParticleType(getConfig().getString("particle-type"));
        ServerConfig.setSoundType(getConfig().getString("sound-type"));

    }

    @Override
    public void onEnable() {
        Core.setHomesTable(new HomesTable());
        Core.getHomesTable().createTable();

        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("delhome").setExecutor(new DelHomeCommand());

        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);
    }

    @Override
    public void onDisable() {
        Core.getDatabase().disconnect();
    }
}
