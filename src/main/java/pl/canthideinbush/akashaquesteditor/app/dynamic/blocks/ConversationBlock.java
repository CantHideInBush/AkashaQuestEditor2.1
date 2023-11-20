package pl.canthideinbush.akashaquesteditor.app.dynamic.blocks;

import org.bukkit.configuration.ConfigurationSection;
import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.TextComponents;
import pl.canthideinbush.akashaquesteditor.app.dynamic.CenterAbleComponent;
import pl.canthideinbush.akashaquesteditor.app.dynamic.popups.Popups;
import pl.canthideinbush.akashaquesteditor.io.Load;
import pl.canthideinbush.akashaquesteditor.quest.objects.ConversationOption;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class ConversationBlock extends WorkspaceBlock<ConversationOption> implements CenterAbleComponent {

    @Load
    public UUID uuid;
    protected JTextPane nameLabel;
    protected JTextPane text;
    protected ActionsPanel actionsPanel;
    private SimpleAttributeSet centerAttributeSet;

    public ConversationBlock(String name) {
        setName(name);
        initialize();
        initializeComponents();
        uuid = UUID.randomUUID();
    }

    GridBagLayout gridBagLayout;
    GridBagConstraints constraints;

    private void initializeComponents() {
        addNamePanel();
        addTextPanel();
        addActionsPanel();

        setLooks();
        addHoverEffects();
        addActions();

    }

    private void addActions() {
        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String newName = Popups.createShortTextPopup("Zmiana nazwy komponentu", "Nowa nazwa", getName());
                    if (newName != null) {
                        setName(newName);
                        updateNameLabel();
                    }
                }
            }
        });

        text.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Popups.createLongTextPopup("Edytor wypowiedzi");
                }
            }
        });

    }

    private void addHoverEffects() {
        addHoverEffects(text);
        addHoverEffects(nameLabel);
//        addHoverEffects(actionsPanel);
    }

    private void addHoverEffects(Component component) {
        Color originalBackground = component.getBackground();
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                component.setBackground(originalBackground.darker());
                Application.instance.sessionContainer.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                component.setBackground(originalBackground);
                Application.instance.sessionContainer.repaint();
            }
        });
    }


    private void addActionsPanel() {
        actionsPanel = new ActionsPanel();
        actionsPanel.setBackground(Color.WHITE);
        actionsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(-5, 0, -5, -5), BorderFactory.createDashedBorder(defaultBorderColor(), 1, 5, 4, false))
        , BorderFactory.createEmptyBorder()));

        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.gridheight = 2;
        constraints.gridwidth = 1;
        constraints.weightx = 0.1;
        constraints.weighty = 1;

        add(actionsPanel, constraints);
    }

    private void addTextPanel() {

        text = new JTextPane();
        text.setBorder(BorderFactory.createEmptyBorder());
        text.setBackground(Color.WHITE);
        text.setOpaque(true);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        constraints.weighty = 1;

        TextComponents.disableSelection(text);

        add(text, constraints);
    }

    private void addNamePanel() {
        nameLabel = new JTextPane();
        nameLabel.setBorder(BorderFactory.createEmptyBorder());

        nameLabel.setBackground(Color.LIGHT_GRAY);
        nameLabel.setOpaque(true);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.9;
        constraints.weighty = 0;


        nameLabel.setOpaque(true);
        nameLabel.setContentType("text/html");


        TextComponents.disableSelection(nameLabel);

        updateNameLabel();

        add(nameLabel, constraints);

    }


    public void initializeCenterAttributeSet() {
        centerAttributeSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttributeSet, StyleConstants.ALIGN_CENTER);
    }

    public void updateNameLabel() {
        Font font = nameLabel.getFont();
        nameLabel.setText("<html><body style=\"font-family: " + font.getFamily() +
                ";font-size: 15" +
                "\"" + "<b>" + who() + ":</b> <b style=\"text-align: center; color: rgb(" + defaultBorderColor().getRed() + "," + defaultBorderColor().getGreen() + "," + defaultBorderColor().getBlue() + ");\">" + getName() + "</b></body></html>");
        StyledDocument styledDocument = nameLabel.getStyledDocument();
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), centerAttributeSet, false);
    }

    private void initialize() {
        setSize(new Dimension(350, 175));
        setBorder(new LineBorder(defaultBorderColor(), 2));
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        setOpaque(false);
        initializeCenterAttributeSet();
        if (connectionPoints == null) createConnectionPoints();
    }

    /**
     * Set backgrounds, borders, etc. for key components
     */
    protected abstract void setLooks();

    @Override
    public ConversationOption create() {
        ConversationOption conversationOption = new ConversationOption(getName());
        conversationOption.setTextRaw(text.getText());

        return conversationOption;
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

    protected Color defaultBorderColor() {
        return Color.BLACK;
    }

    abstract String who();


    public HashSet<ConversationBlock> linkedBlocksCache = new HashSet<>();

    public void updateLinkedBlocksCache() {
        linkedBlocksCache.clear();
        linkedBlocks.forEach(uuid -> {
            linkedBlocksCache.add(Application.instance.sessionContainer.conversationComposer.getConversationBlockByUUID(uuid));
        });
    }

    public Set<UUID> linkedBlocks = new HashSet<>();


    static ArrayList<Point> connectionPoints;

    private void createConnectionPoints() {
        connectionPoints = new ArrayList<>();
        connectionPoints.add(new Point(getWidth() / 2, 0));
        connectionPoints.add(new Point(getWidth() / 2, getHeight()));
        connectionPoints.add(new Point(0, getHeight() / 2));
        connectionPoints.add(new Point(getWidth(), getHeight() / 2));
    }


    public void linkBlocks(Graphics2D g2d) {
        linkedBlocksCache.forEach(linked -> {
            linkBlock(linked, g2d);
        });
    }

    private enum ConnectionPoints {
        TOP(0),BOTTOM(1),LEFT(2),RIGHT(3);

        final int index;
        ConnectionPoints(int index) {
            this.index = index;
        }
        Point getPoint() {
            return connectionPoints.get(index);
        }
    }

    public void linkBlock(ConversationBlock block, Graphics2D g2d) {
        int xDiff = block.getX() - getX();
        int yDiff = block.getY() - getY();
        ConnectionPoints firstPoint;
        if (Math.abs(xDiff) < Math.abs(yDiff)) {
            if (getY() > block.getY()) {
                firstPoint = ConnectionPoints.TOP;
            }
            else firstPoint = ConnectionPoints.BOTTOM;
        }
        else {
            if (getX() > block.getX()) {
                firstPoint = ConnectionPoints.LEFT;
            }
            else firstPoint = ConnectionPoints.RIGHT;
        }

        ConnectionPoints secondPoint;
        if (firstPoint.equals(ConnectionPoints.TOP)) secondPoint = ConnectionPoints.BOTTOM;
        else if (firstPoint.equals(ConnectionPoints.BOTTOM)) secondPoint = ConnectionPoints.TOP;
        else if (firstPoint.equals(ConnectionPoints.LEFT)) secondPoint = ConnectionPoints.RIGHT;
        else secondPoint = ConnectionPoints.LEFT;

        int x = getX() + firstPoint.getPoint().x;
        int y = getY() + firstPoint.getPoint().y;
        int endX = block.getX() + secondPoint.getPoint().x;
        int endY = block.getY() + secondPoint.getPoint().y;
        xDiff = endX - x;
        yDiff = endY - y;


        g2d.setColor(Color.DARK_GRAY);
        if (firstPoint.equals(ConnectionPoints.LEFT) || firstPoint.equals(ConnectionPoints.RIGHT)) {
            g2d.drawLine(x, y, x + xDiff / 2, y);
            g2d.drawLine(endX, endY, endX - xDiff / 2, endY);
            g2d.drawLine(x + xDiff / 2, y, endX - xDiff / 2, endY);
        }
        else {
            g2d.drawLine(x, y, x, y + yDiff / 2);
            g2d.drawLine(endX, endY, endX, endY - yDiff / 2);
            g2d.drawLine(x, y + yDiff / 2, endX, endY - yDiff / 2);
        }

        g2d.setColor(Color.GREEN);
        g2d.fillOval(endX - 3, endY - 3, 6, 6);
    }
}
