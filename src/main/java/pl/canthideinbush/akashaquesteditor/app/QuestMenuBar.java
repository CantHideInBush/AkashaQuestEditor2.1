package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.io.IO;
import pl.canthideinbush.akashaquesteditor.quest.session.EditorConversation;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.swing.*;
import java.awt.*;

public class QuestMenuBar extends JMenuBar {

    private JMenu fileMenu;

    public QuestMenuBar() {
        initialize();
        initializeComponents();
    }

    private void initialize() {
        setPreferredSize(new Dimension(0, 20));

    }

    private void initializeComponents() {
        fileMenu = new JMenu("Plik");
        fileMenu.setMnemonic('P');
        JMenuItem newSession = new JMenuItem("Nowy");
        newSession.setMnemonic('N');
        newSession.addActionListener(e -> {
            if (!shouldOverride()) return;
            QuestSession session = new QuestSession();
            String conversation = Popups.createShortTextPopup("Nowa konwersacja", "Wprowadź nazwę konwersacji", "");
            if (conversation != null) {
                if (conversation.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(Application.instance, "Wprowadzono błędną nazwę", "Błąd użytkownika", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EditorConversation editorConversation = new EditorConversation(conversation);
                session.conversations.add(editorConversation);
                session.activeConversation = editorConversation;
                Application.instance.createNewSession(session);
            }
        });
        fileMenu.add(newSession);

        JMenuItem save = new JMenuItem("Zapisz");
        save.setMnemonic('S');
        save.addActionListener(e -> {
            IO.save();
        });

        fileMenu.add(save);

        JMenuItem load = new JMenuItem("Wczytaj (L)");
        load.setMnemonic('L');
        load.addActionListener(e -> {
            IO.load();
        });

        fileMenu.add(load);


        JMenuItem export = new JMenuItem("Eksportuj");
        export.setMnemonic('E');
        export.addActionListener(e -> {
            IO.export();
        });

        fileMenu.add(export);

        add(fileMenu);
    }

    /**
     *
     * @return true if confirmation button was pressed
     */
    public static boolean shouldOverride() {
        if (Application.instance.sessionContainer == null) return true;
        return JOptionPane.showConfirmDialog(Application.instance, "W edytorze otwarte jest zadanie. Czy chcesz kontynuować?", "Ryzyko utraty danych", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Application.icon.getScaledInstance(64, 64, Image.SCALE_SMOOTH))) == 0;
    }

}
