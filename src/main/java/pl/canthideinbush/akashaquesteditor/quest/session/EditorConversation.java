package pl.canthideinbush.akashaquesteditor.quest.session;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.ConversationBlock;
import pl.canthideinbush.akashaquesteditor.io.ISerialization;
import pl.canthideinbush.akashaquesteditor.io.SF;
import pl.canthideinbush.akashaquesteditor.io.SelfAttach;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditorConversation implements SelfAttach {

    String name;
    @SF
    List<ConversationBlock> conversationBlocks = new ArrayList<>();
    public EditorConversation(String name) {
        this.name = name;
        ISerialization.register(this);
    }

    @Override
    public void attach() {
        Application.instance.sessionContainer.session.conversations.add(this);
    }

    @Override
    public List<Class<? extends SelfAttach>> dependencies() {
        return Collections.singletonList(QuestSession.class);
    }
}
