package me.bremado.core;

import lombok.Getter;
import lombok.Setter;
import me.bremado.core.cache.register.PlayerCache;
import me.bremado.core.database.HikariDatabase;
import me.bremado.core.database.tables.HomesTable;

public class Core {

    @Getter
    private static HikariDatabase database = new HikariDatabase();

    // TABLES
    @Getter @Setter
    private static HomesTable homesTable;

    // CACHES
    @Getter
    private static PlayerCache playerCache = new PlayerCache();
}
