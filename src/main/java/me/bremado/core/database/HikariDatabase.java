package me.bremado.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.bremado.core.database.credential.DatabaseCredential;

import java.sql.Connection;

@Getter
public class HikariDatabase {

    private HikariDataSource dataSource;

    public void connect(DatabaseCredential credential) {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://" + credential.getHost() + ":" + credential.getPort() + "/" + credential.getDatabase());
        config.setUsername(credential.getUsername());
        config.setPassword(credential.getPassword());

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        dataSource.close();
    }
}
