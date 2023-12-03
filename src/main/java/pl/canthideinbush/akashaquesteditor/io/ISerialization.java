package pl.canthideinbush.akashaquesteditor.io;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public interface ISerialization {



    int version();


    ClassScan scan = new ClassScan();
    static void scan() {
        scan.getClasses("pl.canthideinbush.akashaquesteditor");
        scan.getAssignableFrom(ConfigurationSerializable.class).forEach(c -> {
            ConfigurationSerialization.registerClass((Class<? extends ConfigurationSerializable>) c);
        });
    }


    static void serialize(File file) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        ISerialization latest = latest();
        yamlConfiguration.set("version", latest.version());
        latest.serialize(yamlConfiguration);
        try {
            yamlConfiguration.save(file);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void deserialize(File file) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(file);
        } catch (
                IOException |
                InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        int version = yamlConfiguration.getInt("version", -1);
        if (version == -1) {
            return;
        }
        serializations.get(version).deserialize(yamlConfiguration);
    }

    void serialize(YamlConfiguration configuration);
    void deserialize(YamlConfiguration yamlConfiguration);

    HashMap<Integer, ISerialization> serializations = new HashMap<>();


    static void registerSerialization(ISerialization serialization) {
        if (serializations.containsKey(serialization.version())) {
            throw new IllegalArgumentException("This version of serialization is already registered!");
        }
        serializations.put(serialization.version(), serialization);
    }

    static ISerialization latest() {
        return serializations.getOrDefault(serializations.keySet().stream().max(Integer::compareTo).orElse(null), null);
    }

    HashMap<Class<? extends SelfAttach>, HashSet<SelfAttach>> registeredClasses = new HashMap<>();

    static <T extends SelfAttach> void register(T object) {
        if (!registeredClasses.containsKey(object.getClass())) {
            registeredClasses.put(object.getClass(), new HashSet<>());
        }
        registeredClasses.get(object.getClass()).add(object);
    }

    static void terminate(SelfAttach object) {
        if (!registeredClasses.containsKey(object.getClass())) return;
        HashSet<SelfAttach> registered = registeredClasses.get(object.getClass());
        registered.remove(object);
        if (registered.isEmpty()) {
            registeredClasses.remove(object.getClass());
        }
    }
    





}
