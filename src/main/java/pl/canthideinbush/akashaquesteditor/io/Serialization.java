package pl.canthideinbush.akashaquesteditor.io;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.QuestSessionContainer;
import pl.canthideinbush.akashaquesteditor.quest.objects.Conversation;
import pl.canthideinbush.akashaquesteditor.quest.objects.PackageFile;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;

public class Serialization implements ISerialization {


    public Serialization() {

    }





    @Override
    public int version() {
        return 0;
    }

    @Override
    public void export(File file) {
        QuestSession questSession = Application.instance.sessionContainer.session;
        PackageFile questPackageFile = questSession.generatePackage();
        File packageFile = new File(file, "package.yml");
        try {
            packageFile.createNewFile();
            YamlConfiguration packageConfiguration= new YamlConfiguration();
            questPackageFile.save(packageConfiguration);
            packageConfiguration.save(packageFile);
        } catch (
                IOException e) {
            System.out.println("Eksport " + packageFile + " zakończony niepowodzeniem");
            throw new RuntimeException(e);
        }

        File conversationsDir = new File(file + File.separator + "conversations");
        conversationsDir.mkdir();
        for (Conversation conversation : questSession.generateConversations()) {
            File conversationFile = new File(conversationsDir, conversation.name + ".yml");
            try {
                conversationFile.createNewFile();
            } catch (
                    IOException e) {
                throw new RuntimeException(e);
            }
            YamlConfiguration conversationConfiguration = new YamlConfiguration();
            try {
                conversationConfiguration.save(conversationFile);
            } catch (
                    IOException e) {
                System.out.println("Eksport konwersacji zakonczony niepowodzeniem: " + conversationFile);
                throw new RuntimeException(e);
            }
        }
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
