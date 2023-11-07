package pl.canthideinbush.akashaquesteditor.app;

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




    }

}
