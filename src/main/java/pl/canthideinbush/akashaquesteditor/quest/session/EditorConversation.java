package pl.canthideinbush.akashaquesteditor.quest.session;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.ConversationBlock;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.NPCBlock;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.PlayerBlock;
import pl.canthideinbush.akashaquesteditor.io.ISerialization;
import pl.canthideinbush.akashaquesteditor.io.SF;
import pl.canthideinbush.akashaquesteditor.io.SelfAttach;
import pl.canthideinbush.akashaquesteditor.quest.objects.Conversation;
import pl.canthideinbush.akashaquesteditor.quest.objects.ConversationOption;

import java.util.*;
import java.util.List;

public class EditorConversation implements SelfAttach {

    @SF
    String name;
    @SF
    public List<ConversationBlock> conversationBlocks = new ArrayList<>();

    @SF
    public List<String> startOptions = new ArrayList<>();


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
            Application.instance.sessionContainer.conversationComposer.remove(conversationBlock);
        }
    }

    @Override
    public List<Class<? extends SelfAttach>> dependencies() {
        return Collections.singletonList(QuestSession.class);
    }

    public Conversation generateConversation() {
        Conversation conversation = new Conversation(name);
        conversation.setQuester(name);
        conversation.setRawFirst(generateFirstOptions());
        for (ConversationBlock conversationBlock : conversationBlocks) {
            ConversationOption option = conversationBlock.create();
            if (NPCBlock.class.isAssignableFrom(conversationBlock.getClass())) {
                conversation.addNPCOption(option);
            }
            else if (PlayerBlock.class.isAssignableFrom(conversationBlock.getClass())) {
                conversation.addPlayerOption(option);
            }
        }


        return conversation;
    }


    public String generateFirstOptions() {
        if (startOptions.isEmpty()) return "";
        StringBuilder builder = new StringBuilder();
        for (String uuid : startOptions) {
            builder.append(conversationBlocks.stream().filter(cB -> uuid.equals(cB.uuid.toString())).findFirst().get().getName()).append(", ");
        }
        return builder.substring(0, builder.length() - 2);
    }

}
