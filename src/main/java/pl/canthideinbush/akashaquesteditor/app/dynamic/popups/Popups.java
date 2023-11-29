package pl.canthideinbush.akashaquesteditor.app.dynamic.popups;

import org.jetbrains.annotations.NotNull;
import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.WrapEditorKit;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimation;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimationContainer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Popups {

    static ImageIcon tick;
    static ImageIcon close;

    static ImageIcon tickStatic;

    static {
        tick = new ImageIcon(Popups.class.getResource("/assets/tick.gif"));
        tickStatic = new ImageIcon(Popups.class.getResource("/assets/tick-static.png"));
        close = new ImageIcon(Popups.class.getResource("/assets/close.png"));
    }



    public static String createShortTextPopup(String title, String message, String initial) {
        return (String) JOptionPane.showInputDialog(Application.instance, message, title, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Application.icon.getScaledInstance(64, 64,  Image.SCALE_SMOOTH)), null, initial);
    }


    static Dimension cachedSize = new Dimension(600, 450);

    public static void createLongTextPopup(String title) {
        JDialog dialog = templateDialog(title);

        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JTextPane pane = new JTextPane();
        pane.setEditorKit(new WrapEditorKit());
        pane.setFont(pane.getFont().deriveFont(15f));


        pane.setBorder(new CompoundBorder(new LineBorder(dialog.getBackground(), 15), new EmptyBorder(5, 15, 5,15)));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.8;
        gbc.weighty = 1;


        JScrollPane scrollPane = new JScrollPane(pane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        dialog.add(scrollPane, gbc);

        JPanel confirmPanel = new JPanel();
        gbc.weightx = 0.2;

        ResizeAnimationContainer confirmButtonContainer = createAnimatedConfirmButton();

        confirmPanel.add(confirmButtonContainer);


        dialog.add(confirmPanel, gbc);
        dialog.setResizable(true);
        dialog.pack();
        dialog.setVisible(true);
    }

    @NotNull
    private static ResizeAnimationContainer createAnimatedConfirmButton() {
        ConfirmButton confirmButton = new ConfirmButton();
        confirmButton.setSize(42, 42);
        confirmButton.setLocation(0, 0);
        ResizeAnimationContainer confirmButtonContainer = new ResizeAnimationContainer(confirmButton);
        ResizeAnimation resizeAnimation = new ResizeAnimation(confirmButtonContainer, new Dimension(50, 50), 200);
        confirmButton.repaint();
        confirmButtonContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resizeAnimation.start(false);
                confirmButton.animated = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resizeAnimation.start(true);
                confirmButton.animated = false;

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
