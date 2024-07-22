package me.bremado.core.cache.register;

import me.bremado.core.cache.Cache;
import me.bremado.core.database.tables.HomesTable;
import me.bremado.core.model.EnxPlayer;

public class PlayerCache extends Cache<EnxPlayer> {

    @Override
    public void update(EnxPlayer object) {
        var player = find(p -> p.getUniqueId() == object.getUniqueId());
        if (player == null) {
            save(object);
            return;
        }

        player.setHomes(object.getHomes());
        player.setUniqueId(object.getUniqueId());
    }
}
