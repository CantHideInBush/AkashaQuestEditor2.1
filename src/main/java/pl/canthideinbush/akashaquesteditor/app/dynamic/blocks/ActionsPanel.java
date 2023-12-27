package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.quest.InstructionsList;
import pl.canthideinbush.akashaquesteditor.app.components.quest.compose.DragZoomPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ActionsPanel extends JPanel {

    private final ConversationBlock parent;

    ActionsPanel instance = this;

    private JButton events;
    private JButton conditions;
    private JButton objectives;
    private JButton journal;
    private JButton custom;

    public ActionsPanel(ConversationBlock parent) {
        this.parent = parent;
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
        Font font = getFont();

        events = new JButton("Zdarzenia", new ImageIcon(eventsIcon.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        events.setFont(font);
        add(events);

        events.addActionListener(e -> Application.instance.sessionContainer.instructionsPanel.enterEdit(parent, InstructionsList.Category.EVENTS));
        events.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != 1) return;
                Application.instance.sessionContainer.instructionsPanel.enterEdit(parent, InstructionsList.Category.EVENTS);
            }
        });

        conditions = new JButton("Warunki", new ImageIcon(conditionsIcon.getScaledInstance(26, 26, Image.SCALE_REPLICATE)));
        conditions.setFont(font);
        add(conditions);

        conditions.addActionListener(e -> Application.instance.sessionContainer.instructionsPanel.enterEdit(parent, InstructionsList.Category.CONDITIONS));
        conditions.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != 1) return;
                Application.instance.sessionContainer.instructionsPanel.enterEdit(parent, InstructionsList.Category.CONDITIONS);
            }
        });


        objectives = new JButton("Cele", new ImageIcon(objectivesIcon.getScaledInstance(32, 32, Image.SCALE_REPLICATE)));
        objectives.setFont(font);
        add(objectives);

        objectives.addActionListener(
                e -> Application.instance.sessionContainer.instructionsPanel.enterEdit(parent, InstructionsList.Category.OBJECTIVES)
        );
        objectives.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != 1) return;
                Application.instance.sessionContainer.instructionsPanel.enterEdit(parent, InstructionsList.Category.OBJECTIVES);
            }
        });

        journal = new JButton("Dziennik", new ImageIcon(journalIcon.getScaledInstance(25, 25, Image.SCALE_REPLICATE)));
        journal.setFont(font);

        add(journal);


        custom = new JButton("Akcje");
        custom.setFont(font);
        add(custom);

        custom.addActionListener(e -> showMenu()
        );
        custom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMenu();
            }
        });



        addCustomBackgroundEffect();
    }

    private void showMenu() {
        JPopupMenu menu = parent.getMenu();
        DragZoomPanel panel = Application.instance.sessionContainer.conversationComposerPanel;
        Point converted = SwingUtilities.convertPoint(custom, custom.getWidth(), 0, Application.instance.sessionContainer.conversationComposerPanel.zoomedComponentEventProxy);
        Point viewPosition = panel.getViewport().getViewPosition();
        menu.show(panel, (int) (converted.x * panel.getComponentZoom()) - viewPosition.x, (int) (converted.y * panel.getComponentZoom()) - viewPosition.y);
        menu.requestFocus();
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
