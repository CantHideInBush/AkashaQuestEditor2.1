package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import pl.canthideinbush.akashaquesteditor.app.Application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ActionsPanel extends JPanel {

    private JButton events;
    private JButton conditions;
    private JButton objectives;
    private JButton journal;
    private JButton custom;

    public ActionsPanel() {

        initialize();
        initializeComponents();
    }

    private void initialize() {
        setLayout(new GridLayout(0, 1));
    }

    public static BufferedImage eventsIcon;

    private final BufferedImage conditionsIcon;

    private final BufferedImage objectivesIcon;

    private final BufferedImage journalIcon;

    {
        try {
            eventsIcon = ImageIO.read(getClass().getResource("/assets/play.png"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        try {
            conditionsIcon = ImageIO.read(getClass().getResource("/assets/question-mark.png"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        try {
            objectivesIcon = ImageIO.read(getClass().getResource("/assets/exclamation-mark.png"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        try {
            journalIcon = ImageIO.read(getClass().getResource("/assets/bookmark.png"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }



    }

    private void initializeComponents() {
        Font font = getFont();//.deriveFont(15f);

        events = new JButton("Zdarzenia", new ImageIcon(eventsIcon.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        events.setFont(font);
        add(events);

        conditions = new JButton("Warunki", new ImageIcon(conditionsIcon.getScaledInstance(26, 26, Image.SCALE_REPLICATE)));
        conditions.setFont(font);
        add(conditions);
    
        objectives = new JButton("Zadania", new ImageIcon(objectivesIcon.getScaledInstance(32, 32, Image.SCALE_REPLICATE)));
        objectives.setFont(font);
        add(objectives);

        journal = new JButton("Dziennik", new ImageIcon(journalIcon.getScaledInstance(25, 25, Image.SCALE_REPLICATE)));
        journal.setFont(font);

        add(journal);


        custom = new JButton("Własne");
        custom.setFont(font);
        add(custom);



        addCustomBackgroundEffect();
    }

    private void addCustomBackgroundEffect() {
        for (Component component : getComponents()) {
            component.setBackground(Color.WHITE);
            MouseAdapter adapter = new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    component.setBackground(Color.WHITE.darker());
                    Application.instance.sessionContainer.conversationComposer.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    component.setBackground(Color.WHITE);
                    Application.instance.sessionContainer.conversationComposer.repaint();
                }
            };
            component.addMouseListener(adapter);
        }
    }

}
