package pl.canthideinbush.akashaquesteditor.app.components;

import pl.canthideinbush.akashaquesteditor.quest.objects.QuestObject;

public interface QuestComponent<T extends QuestObject> {



    T create();


}
