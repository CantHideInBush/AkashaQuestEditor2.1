package pl.canthideinbush.akashaquesteditor.io;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class Serialization implements ISerialization {


    public Serialization() {

    }





    @Override
    public int version() {
        return 0;
    }

    @Override
    public void serialize(YamlConfiguration configuration) {
        ArrayList<SelfAttach> toSave = new ArrayList<>();
        for (Map.Entry<Class<? extends SelfAttach>, ArrayList<SelfAttach>> entry : registeredClasses.entrySet()) {
            toSave.addAll(entry.getValue());
        }
        configuration.set("SelfAttach", toSave);

    }

    @Override
    public void deserialize(YamlConfiguration configuration) {
        List<SelfAttach> loaded = (List<SelfAttach>) configuration.getList("SelfAttach", new ArrayList<>());
        System.out.println("Found self-attach objects: "  + configuration.get("SelfAttach"));
        for (SelfAttach selfAttach : resolveDependencies(loaded)) {
            selfAttach.attach();
        }
    }

    public ArrayList<SelfAttach> resolveDependencies(List<SelfAttach> attaches) {
        ArrayList<SelfAttach> resolved = new ArrayList<>();
        ArrayList<SelfAttach> queue = new ArrayList<>(attaches);
        while (!queue.isEmpty()) {
            Iterator<SelfAttach> iterator = queue.iterator();
            while (iterator.hasNext()) {
                SelfAttach next = iterator.next();
                if (queue.stream().noneMatch(selfAttach -> next.dependencies().contains(selfAttach.getClass()))) {
                    resolved.add(next);
                    iterator.remove();
                }
            }
        }
        return resolved;
    }







}
