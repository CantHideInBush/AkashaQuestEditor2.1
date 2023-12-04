package pl.canthideinbush.akashaquesteditor.io;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import pl.canthideinbush.akashaquesteditor.app.Application;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface ISerialization {



    int version();


    ClassScan scan = new ClassScan();
    static void scan() {
        scan.getClasses("pl.canthideinbush.akashaquesteditor");
        scan.getAssignableFrom(ConfigurationSerializable.class).forEach(c -> {
            if (!c.isInterface()) {
                ConfigurationSerialization.registerClass((Class<? extends ConfigurationSerializable>) c);
                System.out.println("Registered serializable class: " + c.getName());
            }
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
            JOptionPane.showMessageDialog(Application.instance, "Podczas zapisywania pliku wystąpił błąd", "Błąd zapisu", JOptionPane.ERROR_MESSAGE);
            System.out.println("Podczas zapisywania pliku wystąpił błąd");
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
            JOptionPane.showMessageDialog(Application.instance, "Podczas wczytywania pliku wystąpił błąd", "Błąd odczytu", JOptionPane.ERROR_MESSAGE);
            System.out.println("Podczas wczytywania pliku wystąpił błąd");
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

    HashMap<Class<? extends SelfAttach>, ArrayList<SelfAttach>> registeredClasses = new HashMap<>();

    static <T extends SelfAttach> void register(T object) {
        if (!registeredClasses.containsKey(object.getClass())) {
            registeredClasses.put(object.getClass(), new ArrayList<>());
        }
        registeredClasses.get(object.getClass()).add(object);
    }

    static void terminate(SelfAttach object) {
        if (!registeredClasses.containsKey(object.getClass())) return;
        ArrayList<SelfAttach> registered = registeredClasses.get(object.getClass());
        registered.remove(object);
        if (registered.isEmpty()) {
            registeredClasses.remove(object.getClass());
        }
    }
    





}
