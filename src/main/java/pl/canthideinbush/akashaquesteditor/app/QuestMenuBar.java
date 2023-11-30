package pl.canthideinbush.akashaquesteditor.app;

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
            Application.instance.createNewSession();
        });
        fileMenu.add(newSession);
        add(fileMenu);



    }

}
