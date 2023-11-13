package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.app.dynamic.ConversationComposer;
import pl.canthideinbush.akashaquesteditor.app.dynamic.DragZoomPanel;
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
        conversationComposer.setOpaque(true);
        DragZoomPanel conversationComposerPanel = new DragZoomPanel(conversationComposer);
        conversationComposer.setPreferredSize(new Dimension(9999, 9999));
        conversationComposer.setLocation(0, 0);
        conversationComposerPanel.configureComponent();
        conversationComposerPanel.setPreferredSize(getPreferredSize());
        addTab("Konwersacja", conversationComposerPanel);
    }
}
