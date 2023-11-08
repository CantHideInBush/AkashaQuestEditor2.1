package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.io.Serialization;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Application extends JPanel {

    public static Application instance;
    public QuestSession session = new QuestSession();
    public WelcomePanel welcomePanel;


    public static void main(String[] args) {
        JFrame frame = new JFrame("AkashaQuestEditor 2.1");
        frame.setJMenuBar(new QuestMenuBar());
        instance = new Application();
        instance.initialize();
        instance.initializeComponents();
        setAkashaIcon(frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.add(instance);
        frame.pack();
        frame.setVisible(true);

        new Serialization();
    }

    public Application() {
    }

    private void initializeComponents() {
        welcomePanel = new WelcomePanel();
        add(welcomePanel);
    }

    private void initialize() {
        setPreferredSize(new Dimension(1110, 715));
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }



    static Image icon;

    static {
        try {
            icon = ImageIO.read(Application.class.getResource("/assets/akasha.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setAkashaIcon(JFrame frame) {
        frame.setIconImage(icon);
    }

}
