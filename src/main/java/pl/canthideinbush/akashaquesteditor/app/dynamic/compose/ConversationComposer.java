package pl.canthideinbush.akashaquesteditor.app.dynamic.compose;

import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.ConversationBlock;
import pl.canthideinbush.akashaquesteditor.app.dynamic.Zoomable;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.NPCBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class ConversationComposer extends JLayeredPane implements Zoomable {

    private double zoom = 1.0;

    public ConversationComposer() {
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
        ConversationComposer inst = this;
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isShiftDown()) {
                    ConversationBlock conversationBlock = new NPCBlock("Test");
                    conversationBlock.centerIn(inst, convert(e.getPoint()));
                    repaint();
                }
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D) g);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

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
    }


    @Override
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    @Override
    public double getZoom() {
        return zoom;
    }
}
