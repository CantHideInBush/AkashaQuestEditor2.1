package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.app.dynamic.popups.Popups;
import pl.canthideinbush.akashaquesteditor.quest.session.EditorConversation;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            if (Application.instance.sessionContainer != null) {
                if (JOptionPane.showConfirmDialog(Application.instance, "W edytorze otwarte jest zadanie. Czy chcesz kontynuować?", "Ryzyko utraty danych", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Application.icon.getScaledInstance(64, 64, Image.SCALE_SMOOTH))) != 0) return;
            }
            QuestSession session = new QuestSession();
            String conversation = Popups.createShortTextPopup("Nowa konwersacja", "Wprowadź nazwę konwersacji", "");
            if (conversation != null) {
                if (conversation.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(Application.instance, "Wprowadzono błędną nazwę", "Błąd użytkownika", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                session.conversations.add(new EditorConversation(conversation));
                session.activeConversation = conversation;
                Application.instance.createNewSession(session);
            }
        });
        fileMenu.add(newSession);
        add(fileMenu);
    }

}
