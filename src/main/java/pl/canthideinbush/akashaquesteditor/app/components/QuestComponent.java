package pl.canthideinbush.akashaquesteditor.app.components;

import org.bukkit.configuration.ConfigurationSection;
import pl.canthideinbush.akashaquesteditor.io.ConfigurationObject;
import pl.canthideinbush.akashaquesteditor.quest.objects.QuestObject;

public interface QuestComponent<T extends QuestObject> extends ConfigurationObject {



    T create();


}
