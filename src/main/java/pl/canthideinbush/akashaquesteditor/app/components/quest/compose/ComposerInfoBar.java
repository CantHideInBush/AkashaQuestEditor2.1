package pl.canthideinbush.akashaquesteditor.app.components.quest.compose;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.QuestSessionContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ComposerInfoBar extends JPanel {

    private final QuestSessionContainer container;

    public ComposerInfoBar(QuestSessionContainer container) {
        this.container = container;
        initialize();
        addInfoItems();
    }

    private void initialize() {
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        flowLayout.setHgap(0);
        flowLayout.setVgap(1);
        setLayout(flowLayout);
    }

    JLabel xTracker = createShortInfoDisplay("X: ");
    JLabel yTracker = createShortInfoDisplay("Y: ");
    private void addInfoItems() {

        container.conversationComposer.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updateXYDisplay();
            }
        });

        MouseAdapter jumpAdapter = new MouseAdapter() {

            final JSpinner x = new JSpinner();
            final JSpinner y = new JSpinner();

            {

                x.setPreferredSize(new Dimension(200, 25));
                y.setPreferredSize(new Dimension(200, 25));
                JPanel xPanel = new JPanel();
                xPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                xPanel.add(new JLabel("X: "));
                xPanel.add(x);
                JPanel yPanel = new JPanel();
                yPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                yPanel.add(new JLabel("Y: "));
                yPanel.add(y);

                message = new Object[]{
                        "", xPanel,
                        "", yPanel
                };
            }
            final Object[] message;
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Point view = container.conversationComposerPanel.getViewport().getViewPosition();
                    x.setValue(view.x);
                    y.setValue(view.y);
                    int result = JOptionPane.showConfirmDialog(Application.instance, message, "Wprowadź współrzędne", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                    if (result == 0) {
                        container.conversationComposerPanel.setView((int) x.getValue(), (int) y.getValue());
                        updateXYDisplay();
                    }
                }
            }
        };

        xTracker.addMouseListener(jumpAdapter);
        yTracker.addMouseListener(jumpAdapter);

        add(xTracker);
        add(yTracker);
        updateXYDisplay();

    }

    private JLabel createShortInfoDisplay(String prefix) {
        JLabel label = new JLabel() {
            @Override
            public void setText(String text) {
                text = prefix + text;
                super.setText(text);
            }
        };
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3), BorderFactory.createLineBorder(Color.BLACK)));

        return  label;
    }

    public void updateXYDisplay() {
        Point viewPosition = container.conversationComposerPanel.getViewport().getViewPosition();
        xTracker.setText(""+viewPosition.x);
        yTracker.setText(""+viewPosition.y);
    }


}
