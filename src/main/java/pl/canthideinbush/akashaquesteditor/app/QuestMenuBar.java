package pl.canthideinbush.akashaquesteditor.app;

import org.jetbrains.annotations.NotNull;
import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.components.quest.compose.StartOptionsOrderPanel;
import pl.canthideinbush.akashaquesteditor.io.IO;
import pl.canthideinbush.akashaquesteditor.quest.session.EditorConversation;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
            String conversation = getNewConversationName();
            if (conversation != null) {
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

        JMenu conversationMenu = createConversationMenu();

        add(conversationMenu);
    }

    private String getNewConversationName() {
        String conversation = Popups.createShortTextPopup("Nowa konwersacja", "Wprowadź nazwę konwersacji", "");
        if ("".equalsIgnoreCase(conversation)) {
            JOptionPane.showMessageDialog(Application.instance, "Wprowadzono błędną nazwę", "Błąd użytkownika", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return conversation;
    }

    @NotNull
    private static JMenu createConversationMenu() {
        JMenu conversationMenu = new JMenu("Konwersacja");
        conversationMenu.setMnemonic('O');


        JMenuItem newConversation = getNewConversationMenuItem();
        newConversation.setMnemonic('N');

        conversationMenu.add(newConversation);

        JMenuItem deleteConversation = new JMenuItem("Usuń");
        deleteConversation.addActionListener(e -> {
            if (nullSessionError()) return;
            int result = JOptionPane.showConfirmDialog(Application.instance, "Czy na pewno chcesz usunąć konwersację " + Application.instance.sessionContainer.session.activeConversation.getName() + "?", "Ostrzeżenie", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null);
            if (result == 0) {
                Application.instance.sessionContainer.session.removeActiveConversation();
            }
        });
        deleteConversation.setMnemonic('U');
        conversationMenu.add(deleteConversation);


        JMenuItem selectConversation = getSelectConversationMenuItem();
        selectConversation.setMnemonic('W');

        conversationMenu.add(selectConversation);

        JMenuItem firstOptions = new JMenuItem("Kolejność wypowiedzi");
        firstOptions.addActionListener(e -> {
            if (nullSessionError()) return;
            if (Application.instance.sessionContainer == null) {
                JOptionPane.showMessageDialog(Application.instance, "Brak sesji otwartej w edytorze!", "Błąd", JOptionPane.ERROR_MESSAGE, null);
                return;
            }
            new StartOptionsOrderPanel();
        });

        firstOptions.setMnemonic('K');


        conversationMenu.add(firstOptions);
        return conversationMenu;
    }

    private static JMenuItem getSelectConversationMenuItem() {
        JMenu selectConversationItem = new JMenu("Wybierz");
        selectConversationItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectConversationItem.removeAll();
                if (Application.instance.sessionContainer == null || Application.instance.sessionContainer.session == null) {
                    return;
                }
                for (EditorConversation editorConversation : Application.instance.sessionContainer.session.conversations) {
                    JMenuItem conversationItem = new JMenuItem(editorConversation.getName());
                    conversationItem.addActionListener(e1 -> {
                        Application.instance.sessionContainer.session.setActiveConversation(editorConversation);
                    });
                    conversationItem.setFocusable(true);
                    selectConversationItem.add(conversationItem);
                }
            }
        }
        );



        return selectConversationItem;
    }

    @NotNull
    private static JMenuItem getNewConversationMenuItem() {
        JMenuItem newConversation = new JMenuItem("Nowa");
        newConversation.addActionListener(e -> {
            String conversation = Popups.createShortTextPopup("Nowa konwersacja", "Wprowadź nazwę konwersacji", "");
            if (conversation != null) {
                if (Application.instance.sessionContainer.session.getConversationNames().contains(conversation.toLowerCase())) {
                    JOptionPane.showMessageDialog(Application.instance, "Konwersacja o podanej nazwie już istnieje", "Błąd użytkownika", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EditorConversation editorConversation = new EditorConversation(conversation);
                Application.instance.sessionContainer.session.activeConversation.deactivate();
                Application.instance.sessionContainer.session.conversations.add(editorConversation);
                Application.instance.sessionContainer.session.activeConversation = editorConversation;
                editorConversation.initialize();
            }
        });
        return newConversation;
    }

    /**
     *
     * @return true if confirmation button was pressed
     */
    public static boolean shouldOverride() {
        if (Application.instance.sessionContainer == null) return true;
        return JOptionPane.showConfirmDialog(Application.instance, "W edytorze otwarte jest zadanie. Czy chcesz kontynuować?", "Ryzyko utraty danych", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Application.icon.getScaledInstance(64, 64, Image.SCALE_SMOOTH))) == 0;
    }

    /**
     *
     * @return true if there was no conversation open, false otherwise
     */
    public static boolean nullSessionError() {
        if (Application.instance.sessionContainer == null) {
            JOptionPane.showMessageDialog(Application.instance, "Brak konwersacji otwartej w edytorze!", "Błąd", JOptionPane.ERROR_MESSAGE, null);
            return true;
        }
        return false;
    }


}
