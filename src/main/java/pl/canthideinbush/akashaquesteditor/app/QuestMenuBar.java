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
        JMenuItem newSession = new JMenuItem("Nowy");
        newSession.addActionListener(e -> {
            Application.instance.createNewSession();
        });
        fileMenu.add(newSession);
        add(fileMenu);



    }

}
