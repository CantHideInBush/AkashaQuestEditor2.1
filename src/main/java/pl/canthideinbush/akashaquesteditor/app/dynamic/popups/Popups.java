package pl.canthideinbush.akashaquesteditor.app.dynamic.popups;

import pl.canthideinbush.akashaquesteditor.app.Application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class Popups {


    public static String createShortTextPopup(String title, String message, String initial) {
        return (String) JOptionPane.showInputDialog(Application.instance, message, title, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Application.icon.getScaledInstance(64, 64,  Image.SCALE_SMOOTH)), null, initial);
    }


    public static void createLongTextPopup(String title) {
        JDialog dialog = templateDialog(title);

        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        
        JTextPane pane = new JTextPane();

        pane.setBorder(BorderFactory.createCompoundBorder(new LineBorder(dialog.getBackground(), 15),
                new EmptyBorder(5, 10, 5, 10)));


        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.8;
        gbc.weighty = 1;


        JScrollPane scrollPane = new JScrollPane(pane, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        dialog.add(scrollPane, gbc);

        JPanel confirmPanel = new JPanel();
        confirmPanel.setOpaque(true);
        gbc.weightx = 0.2;

        dialog.add(confirmPanel, gbc);

        pane.setMinimumSize(pane.getSize());
        pane.setPreferredSize(pane.getSize());
        pane.setMaximumSize(pane.getSize());

        dialog.pack();
        dialog.setVisible(true);
    }

    private static JDialog templateDialog(String title) {
        JDialog dialog = new JDialog(Application.instance.frame, title, true);
        dialog.setPreferredSize(new Dimension(500, 325));
        dialog.setIconImage(Application.icon);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(Application.instance);
        dialog.setLocation(Application.instance.getWidth() / 2 - dialog.getWidth(), Application.instance.getHeight() / 2 - dialog.getHeight());
        return dialog;

    }

}
