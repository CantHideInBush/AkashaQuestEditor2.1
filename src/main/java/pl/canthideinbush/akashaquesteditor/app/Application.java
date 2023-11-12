package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.io.Serialization;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

public class Application extends JPanel {

    public static Application instance;
    public QuestSessionContainer sessionContainer;
    public WelcomePanel welcomePanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("AkashaQuestEditor 2.1");
            frame.setLayout(new FlowLayout(FlowLayout.CENTER));
            frame.setResizable(true);
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

            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    Dimension dimension = new Dimension(frame.getWidth() - 25, frame.getHeight() - 60);
                    instance.setMinimumSize(dimension);
                    instance.setPreferredSize(dimension);
                    instance.setMaximumSize(dimension);
                    instance.revalidate();
                    instance.repaint();
                }
            });

            new Serialization();
        });
    }

    public Application() {
    }

    private void initializeComponents() {
        welcomePanel = new WelcomePanel();
        add(welcomePanel);
    }

    private void initialize() {
        setMinimumSize(new Dimension(655, 357));
        setPreferredSize(new Dimension(1110, 715));
        setLayout(new GridLayout(1, 1));
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

    public void createNewSession() {
        clean();

        sessionContainer = new QuestSessionContainer(new QuestSession());
        remove(welcomePanel);
        sessionContainer.setVisible(true);
        add(sessionContainer);
        revalidate();
        repaint();
    }

    public void clean() {
        if (sessionContainer != null) {
            remove(sessionContainer);
        }
    }

}
