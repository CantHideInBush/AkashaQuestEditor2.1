package pl.canthideinbush.akashaquesteditor.app.dynamic.compose;

import pl.canthideinbush.akashaquesteditor.app.dynamic.Zoomable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class DragZoomPanel extends JScrollPane {


    private final JLayeredPane component;
    public ZoomedComponentEventProxy zoomedComponentEventProxy;

    public DragZoomPanel(JLayeredPane component) {
        super(component);
        if (!(component instanceof Zoomable)) {
            throw new IllegalArgumentException("Component must implement Zoomable interface!");
        }
        this.component = component;
        initialize();
    }

    public void configureComponent() {

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                this.screenOrigin = e.getLocationOnScreen();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                screenOrigin = null;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double rotation = e.getWheelRotation();
                double currentZoom = ((Zoomable) component).getZoom();
                if (rotation < 0) {
                    currentZoom += 0.1;
                }
                else currentZoom -= 0.1;
                currentZoom = Math.max(0.5, currentZoom);
                currentZoom = Math.min(3, currentZoom);
                ((Zoomable) component).setZoom(currentZoom);
                component.repaint();
            }

            Point screenOrigin;

            @Override
            public void mouseDragged(MouseEvent e) {
                if (screenOrigin != null) {
                    int x = e.getLocationOnScreen().x - screenOrigin.x;
                    int y = e.getLocationOnScreen().y - screenOrigin.y;

                    Point view = getViewport().getViewPosition();
                    getViewport().setViewPosition(fixViewInBounds(new Point(
                            view.x - x,
                            view.y - y
                            )
                    ));

                    screenOrigin = e.getLocationOnScreen();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        };

        component.addMouseListener(adapter);
        component.addMouseMotionListener(adapter);
        component.addMouseWheelListener(adapter);

        zoomedComponentEventProxy = new ZoomedComponentEventProxy(component);
    }

    public Point fixViewInBounds(Point point) {
        if (point.x < 0) {
            point.x = 0;
        }
        if (point.y < 0) {
            point.y = 0;
        }
        if (point.x > component.getWidth() - getWidth()) {
            point.x = component.getWidth() - getWidth();
        }
        if (point.y > component.getHeight() - getHeight()) {
            point.y = component.getHeight() - getHeight();
        }

        return point;
    }


    private void initialize() {
        setAutoscrolls(false);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    }


}
