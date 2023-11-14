package pl.canthideinbush.akashaquesteditor.app.dynamic;

import org.bukkit.configuration.ConfigurationSection;
import pl.canthideinbush.akashaquesteditor.quest.objects.ConversationOption;

public class NPCBlock extends WorkspaceBlock<ConversationOption> {
    @Override
    public ConversationOption create() {
        return null;
    }

    @Override
    public void load(ConfigurationSection section) {

    }

    @Override
    public void save(ConfigurationSection section) {

    }
}
