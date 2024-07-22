package me.bremado.core.database.tables;

import me.bremado.core.Core;
import me.bremado.core.model.homes.Home;
import me.bremado.core.utils.LocationInfo;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomesTable {

    private final Connection con;

    public HomesTable() {
        try {
            this.con = Core.getDatabase().getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        try {
            var statement = con.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS `homes` (id INT AUTO_INCREMENT PRIMARY KEY, uniqueId VARCHAR(36), homeName TEXT, home TEXT)");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(UUID uniqueId, Home home) {
        try {
            var statement = con.prepareStatement("INSERT INTO `homes` (uniqueId, homeName, home) VALUES (?,?, ?)");
            statement.setString(1, uniqueId.toString());
            statement.setString(2, home.getName());
            statement.setString(3, home.getLocationInfo().toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Home> find(UUID uniqueId) {
        try {
            var statement = con.prepareStatement("SELECT * FROM `homes` WHERE uniqueId = ?");
            statement.setString(1, uniqueId.toString());
            var result = statement.executeQuery();
            List<Home> homes = new ArrayList<>();
            while (result.next()) {
                var loc = result.getString("home");
                var locInfo = LocationInfo.fromString(loc);

                homes.add(Home.builder().name(result.getString("homeName")).locationInfo(locInfo).build());
            }
            statement.close();
            return homes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(UUID uniqueId, Home home) {
        try {
            var statement = con.prepareStatement("DELETE FROM `homes` WHERE uniqueId = ? AND homeName = ?");
            statement.setString(1, uniqueId.toString());
            statement.setString(2, home.getName());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
