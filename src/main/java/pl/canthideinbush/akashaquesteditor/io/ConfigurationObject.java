package pl.canthideinbush.akashaquesteditor.io;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigurationObject {

    void load(ConfigurationSection section);

    void save(ConfigurationSection section);

}
