package pl.canthideinbush.akashaquesteditor.quest.objects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

public class PackageFile extends QuestFile {


    private HashMap<String, String> npcs = new HashMap<>();

    public PackageFile(String name) {
        super(name);
    }


    public void addNPC(int id, String name) {
        npcs.put(String.valueOf(id), name);
    }

    public void addNPC(String id, String name) {
        npcs.put(id, name);
    }

    public void removeNPCByName(int id) {
        npcs.remove(String.valueOf(id));
    }

    public void removeNPCByName(String name) {
        ArrayList<String> toRemove = new ArrayList<>();
        npcs.forEach((s, s1) -> {
            if (s1.equalsIgnoreCase(name)) toRemove.add(s);
        });
        toRemove.forEach(s -> npcs.remove(s));
    }


    @Override
    public void save(YamlConfiguration configuration) {
        ConfigurationSection npcsSection = configuration.createSection("npcs");
        npcs.forEach(npcsSection::set);
        super.save(configuration);
    }
}
