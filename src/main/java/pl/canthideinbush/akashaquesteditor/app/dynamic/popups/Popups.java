package pl.canthideinbush.akashaquesteditor.app.dynamic.popups;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.WrapEditorKit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class Popups {

    static ImageIcon tick;
    static ImageIcon close;

    static {
        tick = new ImageIcon(Popups.class.getResource("/assets/tick.gif"));
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

        JPanel confirmButton = templateConfirmButton(new Dimension(48, 48));

        confirmPanel.add(confirmButton);

        dialog.add(confirmPanel, gbc);
        dialog.setResizable(true);
        dialog.pack();
        dialog.setVisible(true);
    }

    public static JPanel templateConfirmButton(Dimension size) {
        JPanel confirm = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = ((Graphics2D) g);
                AffineTransform transform = new AffineTransform();

                g2d.setTransform(transform);


                g2d.drawImage(tick.getImage(), 0, 0, this);

            }

        };
        confirm.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true));
        confirm.setPreferredSize(size);
        return confirm;
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
