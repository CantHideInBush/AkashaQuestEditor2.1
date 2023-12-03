package pl.canthideinbush.akashaquesteditor.app.dynamic.compose;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.Zoomable;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.ConversationBlock;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.NPCBlock;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.PlayerBlock;
import pl.canthideinbush.akashaquesteditor.app.dynamic.popups.Popups;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConversationComposer extends JLayeredPane implements Zoomable {

    private final QuestSession session;


    private double zoom = 1.0;

    public ConversationComposer(QuestSession session) {
        this.session = session;
        initialize();
    }


    private void initialize() {
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setBackground(new Color(37, 41, 51));
        setOpaque(true);
        listenToEvents();
    }

    private void listenToEvents() {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isShiftDown()) {
                    String name = Popups.createShortTextPopup("Utwórz nowy blok", "Wprowadź nazwę", "");
                    if (name == null) return;
                    ConversationBlock conversationBlock = e.getButton() == MouseEvent.BUTTON1 ? new NPCBlock(name) : new PlayerBlock(name);
                    conversationBlock.centerIn(convert(e.getPoint()));
                    add(conversationBlock);
                    Application.instance.sessionContainer.conversationComposerPanel.zoomedComponentEventProxy.registerDrag(conversationBlock);
                    repaint();
                }
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = ((Graphics2D) g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        super.paintComponent(g);

        AffineTransform affineTransform = g2d.getTransform();
        affineTransform.scale(zoom, zoom);
        g2d.setTransform(affineTransform);

        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        for (int h = 10; h <= getHeight() - 10; h+= 40) {
            for (int w = 10; w <= getWidth() - 10; w+=40) {
                g2d.setColor(new Color(73, 79, 85));
                g2d.fillRect(w, h, 3, 3);
            }
        }

        for (ConversationBlock conversationBlock : getConversationBlocks()) {
            conversationBlock.linkBlocks(g2d);
        }
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2d = ((Graphics2D) g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        super.paintChildren(g);
    }

    @Override
    public void paintComponents(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        super.paintComponents(g);
    }

    @Override
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    @Override
    public double getZoom() {
        return zoom;
    }

    public ConversationBlock getConversationBlockByUUID(UUID uuid) {
        return (ConversationBlock) Arrays.stream(getComponents()).filter(component -> component instanceof ConversationBlock && ((ConversationBlock) component).uuid.equals(uuid)).findAny().orElse(null);
    }

    public Collection<ConversationBlock> getConversationBlocks() {
        return Arrays.stream(getComponents()).filter(component -> component instanceof ConversationBlock).map(component -> ((ConversationBlock) component)).collect(Collectors.toList());
    }
}
