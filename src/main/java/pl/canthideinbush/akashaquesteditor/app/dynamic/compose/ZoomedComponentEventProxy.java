package pl.canthideinbush.akashaquesteditor.app.dynamic.compose;

import pl.canthideinbush.akashaquesteditor.app.dynamic.Zoomable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ZoomedComponentEventProxy extends JPanel {

    private final JLayeredPane intercepted;

    public ZoomedComponentEventProxy(JLayeredPane intercepted) {
        if (!(intercepted instanceof Zoomable)) {
            throw new IllegalArgumentException("Component must implement Zoomable interface!");
        }
        this.intercepted = intercepted;
        initialize();
        initializeIntercepting();
    }

    private void initialize() {
        setSize(intercepted.getPreferredSize());
        setLocation(0, 0);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        intercepted.add(this);
        intercepted.setLayer(this, JLayeredPane.DRAG_LAYER + 1);
    }

    private void initializeIntercepting() {
        MouseAdapter adapter = new MouseAdapter() {

            Component selected = null;

            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selected = null;
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = e.getPoint();
                Component component = selected != null ? selected : getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                Component component = getZoomComponentAt(point.x, point.y);
                if (component != null) {
                    component.dispatchEvent(e);
                }
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        addMouseWheelListener(adapter);
    }

    private double getZoom() {
        return ((Zoomable) intercepted).getZoom();
    }

    private Component getZoomComponentAt(int x, int y) {



        return getComponent((int) (x / getZoom()), (int) (y / getZoom()));
    }

    private Component getComponent(int x, int y) {
        for (Component component : intercepted.getComponents()) {
            if (component.equals(this) || component.equals(intercepted)) continue;
            Point local = SwingUtilities.convertPoint(intercepted, x, y, component);
            if (component.contains(local)) {
                return component.getComponentAt(local);
            }
        }
        return intercepted;
    }



}
