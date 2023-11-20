package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.io.Serialization;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Locale;

public class Application extends JPanel {

    public static Application instance;
    public QuestSessionContainer sessionContainer;
    public WelcomePanel welcomePanel;
    private Box footerSpacer;
    public JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            instance = new Application();

            JFrame frame = new JFrame("AkashaQuestEditor 2.1");
            instance.frame = frame;


            frame.setLayout(new FlowLayout(FlowLayout.CENTER));
            frame.setResizable(true);
            frame.setJMenuBar(new QuestMenuBar());

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
        JOptionPane.setDefaultLocale(new Locale("pl"));
        welcomePanel = new WelcomePanel();
        add(welcomePanel);
    }

    private void initialize() {
        setMinimumSize(new Dimension(655, 357));
        setPreferredSize(new Dimension(1110, 715));
        setLayout(new GridLayout(1, 1));
    }



    public static Image icon;

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

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        sessionContainer = new QuestSessionContainer(new QuestSession());
        sessionContainer.setVisible(true);
        add(sessionContainer);

        if (footerSpacer == null) {
            footerSpacer = Box.createHorizontalBox();
            footerSpacer.setPreferredSize(new Dimension(getWidth(), 10));
        }
        add(footerSpacer);

        revalidate();
        repaint();
    }

    public void clean() {
        if (sessionContainer != null) {
            remove(sessionContainer);
        }
        if (welcomePanel != null) {
            remove(welcomePanel);
        }
        if (footerSpacer != null) {
            remove(footerSpacer);
        }
    }

}
