package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.app.components.quest.JournalEntriesContainer;
import pl.canthideinbush.akashaquesteditor.app.settings.Settings;
import pl.canthideinbush.akashaquesteditor.io.IO;
import pl.canthideinbush.akashaquesteditor.io.ISerialization;
import pl.canthideinbush.akashaquesteditor.io.Serialization;
import pl.canthideinbush.akashaquesteditor.quest.session.EditorConversation;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class Application extends JPanel {


    public static Application instance;
    public QuestSessionContainer sessionContainer;
    public WelcomePanel welcomePanel;
    private Box footerSpacer;
    public JFrame frame;

    public Settings settings;

    public static void main(String[] args) {
        ISerialization.scan();
        ISerialization.registerSerialization(new Serialization());
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
            instance.registerActions();
            frame.pack();
            frame.setVisible(true);
            frame.createBufferStrategy(2);

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
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        this.settings = new Settings();
        settings.load();
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

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                settings.save();
            }
        });
    }

    private void registerActions() {
        getRootPane().registerKeyboardAction(e -> {
            if (sessionContainer != null && !sessionContainer.session.recentFilePath.equalsIgnoreCase("/")) {
                ISerialization.serialize(new File(sessionContainer.session.recentFilePath));
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), WHEN_IN_FOCUSED_WINDOW);

        getRootPane().registerKeyboardAction(e -> {
            if (sessionContainer != null && !sessionContainer.session.recentExportPath.equalsIgnoreCase("/")) {
                ISerialization.exportToDir(new File(sessionContainer.session.recentExportPath));
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), WHEN_IN_FOCUSED_WINDOW);

            getRootPane().registerKeyboardAction(e -> {
                IO.load();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK), WHEN_IN_FOCUSED_WINDOW);


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

    public void createNewSession(QuestSession session) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        sessionContainer = new QuestSessionContainer(session);
        sessionContainer.setVisible(true);
        add(sessionContainer);
        sessionContainer.instructionsPanel.initializeComponents();

        if (footerSpacer == null) {
            footerSpacer = Box.createHorizontalBox();
            footerSpacer.setPreferredSize(new Dimension(getWidth(), 10));
        }
        add(footerSpacer);

        /*if (session.conversations.isEmpty()) {
            session.conversations.add(session.activeConversation);
            session.activeConversation.initialize();
            session.activeConversation.activate();
        }*/

        revalidate();
        repaint();
    }


    /**
     * Clears workspace, preparing it for new QuestSession
     */
    public void clean() {
        if (ISerialization.registeredClasses.containsKey(EditorConversation.class)) ISerialization.registeredClasses.get(EditorConversation.class).clear();
        if (ISerialization.registeredClasses.containsKey(JournalEntriesContainer.class)) ISerialization.registeredClasses.get(JournalEntriesContainer.class).clear();

        if (sessionContainer != null) {
            remove(sessionContainer);
            ISerialization.terminate(sessionContainer.session);
        }
        if (welcomePanel != null) {
            remove(welcomePanel);
        }
        if (footerSpacer != null) {
            remove(footerSpacer);
        }
    }

}
