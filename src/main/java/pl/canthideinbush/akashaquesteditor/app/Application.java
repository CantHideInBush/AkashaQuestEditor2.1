package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.io.Serialization;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.swing.*;
import java.awt.*;

public class Application extends JPanel {

    public QuestSession session = new QuestSession();

    public static void main(String[] args) {
        JFrame frame = new JFrame("AkashaQuestEditor 2.1");
        frame.setJMenuBar(new QuestMenuBar());
        Application application = new Application();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.add(application);
        frame.pack();
        frame.setVisible(true);

        new Serialization();
    }

    public Application() {
        initialize();
    }

    private void initialize() {
        setPreferredSize(new Dimension(1110, 715));

    }



}
