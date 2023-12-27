package pl.canthideinbush.akashaquesteditor.app.settings;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Settings {

    YamlConfiguration settingsConfiguration = new YamlConfiguration();

    public void load() {
        this.settingsConfiguration = new YamlConfiguration();
        settingsConfiguration.setDefaults(createDefaults());
        File file = new File("./settings.yml");
        try {
            file.createNewFile();
            settingsConfiguration.load(file);
        } catch (
                IOException |
                InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    private YamlConfiguration createDefaults() {
        YamlConfiguration defaults = new YamlConfiguration();
        defaults.set("autoGenerateJournalInstructions", true);
        return defaults;
    }


    public void save() {
        try {
            settingsConfiguration.save(new File("./settings.yml"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean shouldAutoGenerateJournalInstructions() {
        return settingsConfiguration.getBoolean("autoGenerateJournalInstructions");
    }


}
