package pl.canthideinbush.akashaquesteditor.quest.session;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.ConversationBlock;
import pl.canthideinbush.akashaquesteditor.io.ISerialization;
import pl.canthideinbush.akashaquesteditor.io.SF;
import pl.canthideinbush.akashaquesteditor.io.SelfAttach;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EditorConversation implements SelfAttach {

    String name;
    @SF
    public List<ConversationBlock> conversationBlocks = new ArrayList<>();

    public EditorConversation(Map<String, Object> data) {
        deserializeFromMap(data);
        ISerialization.register(this);
    }

    public EditorConversation(String name) {
        this.name = name;
        ISerialization.register(this);
    }

    @Override
    public void attach() {
        QuestSession session = Application.instance.sessionContainer.session;
        session.conversations.add(this);
        if (this.equals(session.activeConversation)) {
            deploy();
        }
    }

    public void deploy() {
        for (ConversationBlock conversationBlock : conversationBlocks) {
            conversationBlock.initialize();
            conversationBlock.initializeComponents();
            Application.instance.sessionContainer.conversationComposer.add(conversationBlock);
        }
        conversationBlocks.forEach(ConversationBlock::updateLinkedBlocksCache);
    }

    public void contract() {
        for (ConversationBlock conversationBlock : conversationBlocks) {
            conversationBlock.initialize();
            conversationBlock.initializeComponents();
            Application.instance.sessionContainer.conversationComposer.remove(conversationBlock);
        }
    }

    @Override
    public List<Class<? extends SelfAttach>> dependencies() {
        return Collections.singletonList(QuestSession.class);
    }


}
