package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.swing.*;
import java.awt.*;

public class QuestSessionContainer extends JTabbedPane {

    private final QuestSession session;

    public QuestSessionContainer(QuestSession session) {
        this.session = session;
        initialize();
    }

    private void initialize() {
        setPreferredSize(Application.instance.getPreferredSize());
        setForeground(Color.BLACK);
        setOpaque(true);
        addTab("Test", new JLabel());
    }

}
