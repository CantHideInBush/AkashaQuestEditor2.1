package pl.canthideinbush.akashaquesteditor.app.dynamic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class DragZoomPanel extends JScrollPane {


    private final Component component;

    public DragZoomPanel(Component component) {
        super(component);
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
                super.mouseReleased(e);
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
                super.mouseWheelMoved(e);
            }

            Point screenOrigin;

            @Override
            public void mouseDragged(MouseEvent e) {
                if (screenOrigin != null) {
                    int x = e.getLocationOnScreen().x - screenOrigin.x;
                    int y = e.getLocationOnScreen().y - screenOrigin.y;

                    Point view = getViewport().getViewPosition();
                    getViewport().setViewPosition(new Point(
                            view.x - x,
                            view.y - y
                            )
                    );

                    screenOrigin = e.getLocationOnScreen();
                    component.repaint();
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
    }



    private void initialize() {
        setAutoscrolls(false);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    }


}
