package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.app.dynamic.ConversationComposer;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.swing.*;
import java.awt.*;

public class QuestSessionContainer extends JTabbedPane {

    private final QuestSession session;

    public QuestSessionContainer(QuestSession session) {
        this.session = session;
        initialize();
        initializeComponents();
    }

    public ConversationComposer conversationComposer = new ConversationComposer();

    private void initialize() {
        setPreferredSize(Application.instance.getPreferredSize());
        setForeground(Color.BLACK);
        setOpaque(true);


    }


    private void initializeComponents() {
        conversationComposer.setPreferredSize(getPreferredSize());
        conversationComposer.setOpaque(true);
        addTab("Konwersacja", conversationComposer);
    }
}
