package pl.canthideinbush.akashaquesteditor.app.dynamic.popups;

import org.jetbrains.annotations.NotNull;
import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.WrapEditorKit;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimation;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimationContainer;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.components.ResizableIcon;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class Popups {

    public static ImageIcon tick;
    public static ImageIcon close;

    public static ImageIcon tickStatic;

    static {
        tick = new ImageIcon(Popups.class.getResource("/assets/tick.gif"));
        tickStatic = new ImageIcon(Popups.class.getResource("/assets/tick-static.png"));
        close = new ImageIcon(Popups.class.getResource("/assets/close.png"));
    }



    public static String createShortTextPopup(String title, String message, String initial) {
        return (String) JOptionPane.showInputDialog(Application.instance, message, title, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Application.icon.getScaledInstance(64, 64,  Image.SCALE_SMOOTH)), null, initial);
    }


    static Dimension cachedSize = new Dimension(600, 450);

    public static void createLongTextPopup(String title, String text, Consumer<String> consumer) {
        JDialog dialog = templateDialog(title);

        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JTextPane pane = new JTextPane();
        pane.setEditorKit(new WrapEditorKit());
        pane.setFont(pane.getFont().deriveFont(15f));
        pane.setText(text);


        pane.setBorder(new CompoundBorder(new LineBorder(dialog.getBackground(), 15), new EmptyBorder(5, 15, 5,15)));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.8;
        gbc.weighty = 1;


        JScrollPane scrollPane = new JScrollPane(pane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        dialog.add(scrollPane, gbc);

        JPanel confirmPanel = new JPanel();
        gbc.weightx = 0.2;

        ResizableIcon confirmButton = new ResizableIcon(tickStatic, tick);
        confirmButton.setBorder(new LineBorder(Color.GREEN, 3, true));
        ResizeAnimationContainer confirmButtonContainer = createResizableComponent(confirmButton, new Dimension(42, 42), new Dimension(50, 50));

        //TODO: add way to offset
        ResizableIcon closeButton = new ResizableIcon(close, close);
        closeButton.setBorder(new LineBorder(Color.RED, 2, true));
        ResizeAnimationContainer closeButtonContainer = createResizableComponent(closeButton, new Dimension(42, 42), new Dimension(50, 50));

        confirmPanel.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));


        confirmPanel.add(buttonsPanel, BorderLayout.SOUTH);

        buttonsPanel.add(confirmButtonContainer);
        buttonsPanel.add(closeButtonContainer);


        closeButtonContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.dispose();
            }
        });

        confirmButtonContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(pane.getText());
                dialog.dispose();
            }
        });

        dialog.add(confirmPanel, gbc);
        dialog.setResizable(true);
        dialog.pack();
        dialog.setVisible(true);
    }

    @NotNull
    private static ResizeAnimationContainer createResizableComponent(ResizableIcon component, Dimension start, Dimension resize) {
        component.setSize(start );
        ResizeAnimationContainer confirmButtonContainer = new ResizeAnimationContainer(component);

        ResizeAnimation resizeAnimation = new ResizeAnimation(confirmButtonContainer, resize, 200);
        component.setLocation(confirmButtonContainer.getPreferredSize().width / 2 - component.getWidth() / 2, confirmButtonContainer.getPreferredSize().height / 2 - component.getHeight() / 2);

        confirmButtonContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resizeAnimation.start(false);
                component.setAnimated(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resizeAnimation.start(true);
                component.setAnimated(false);
            }
        });
        return confirmButtonContainer;
    }

    private static JDialog templateDialog(String title) {
        JDialog dialog = new JDialog(Application.instance.frame, title, true);
        dialog.setPreferredSize(cachedSize);
        dialog.setIconImage(Application.icon);
        dialog.setResizable(true);
        dialog.setLocationRelativeTo(Application.instance);
        dialog.setLocation(Application.instance.getWidth() / 2 - dialog.getWidth(), Application.instance.getHeight() / 2 - dialog.getHeight());

        dialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                cachedSize = e.getComponent().getSize();
            }
        });

        return dialog;

    }

}
