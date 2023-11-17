package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import org.bukkit.configuration.ConfigurationSection;
import pl.canthideinbush.akashaquesteditor.app.dynamic.CenterAbleComponent;
import pl.canthideinbush.akashaquesteditor.quest.objects.ConversationOption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class ConversationBlock extends WorkspaceBlock<ConversationOption> implements CenterAbleComponent {

    private JLabel nameLabel;

    public ConversationBlock(String name) {
        setName(name);
        initialize();
        initializeComponents();
    }

    GridBagLayout gridBagLayout;
    GridBagConstraints constraints;

    private void initializeComponents() {
        addNamePanel();
        addActionsPanel();
        addTextPanel();

        initializeHover();
    }

    private void initializeHover() {
        for (Component component : getComponents()) {
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    component.setBackground(Color.WHITE);
                    getParent().repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    component.setBackground(Color.LIGHT_GRAY);
                    getParent().repaint();
                }
            });
        }
    }

    private void addActionsPanel() {
        JPanel actionsPanel = new JPanel();
        actionsPanel.setBackground(Color.LIGHT_GRAY);
        actionsPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 2, Color.BLACK));
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 2;
        add(actionsPanel, constraints);
    }

    private void addTextPanel() {
        JPanel text = new JPanel();

        text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        text.setBackground(Color.LIGHT_GRAY);

        constraints.weightx = 4;
        constraints.weighty = 8;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 5;

        add(text, constraints);
    }

    private void addNamePanel() {
        JPanel namePanel = new JPanel();
        namePanel.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.BLACK));

        namePanel.setBackground(Color.LIGHT_GRAY);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        namePanel.setLayout(new GridLayout(1, 1));

        nameLabel = new JLabel();
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.ITALIC,15f));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setText(getName());

        namePanel.add(nameLabel);
        add(namePanel, constraints);

    }

    private void initialize() {
        setSize(new Dimension(250, 175));
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        setOpaque(false);
    }

    @Override
    public ConversationOption create() {
        return null;
    }

    @Override
    public void load(ConfigurationSection section) {

    }

    @Override
    public void save(ConfigurationSection section) {

    }

    public Container getContainer() {
        return this;
    }

    public void centerIn(Point point) {
        setLocation(point.x - getWidth() / 2, point.y - getHeight() / 2);
    }


}
