package pl.canthideinbush.akashaquesteditor.app;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class WelcomePanel extends JPanel {

    public WelcomePanel() {
        initialize();
        initializeComponents();

    }

    private void initialize() {
        setPreferredSize(new Dimension(500, Application.instance.getPreferredSize().height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new LineBorder(Color.RED, 1));

    }

    private void initializeComponents() {
        JPanel textContainer = new JPanel();
        textContainer.setPreferredSize(new Dimension(getPreferredSize().width, 9999));
        textContainer.setBorder(new LineBorder(Color.RED, 1));
        JTextArea jTextArea = new JTextArea();
        jTextArea.setFont(jTextArea.getFont().deriveFont(20f));
        jTextArea.setText("Utwórz sesję aby rozpocząć");
        jTextArea.setVisible(true);
        jTextArea.setOpaque(false);
        jTextArea.setEditable(false);
        textContainer.add(jTextArea);
        add(textContainer);

        JTextField versionText = new JTextField();
        versionText.setPreferredSize(new Dimension(getPreferredSize().width, 200));
        versionText.setBorder(new LineBorder(Color.RED, 1));
        versionText.setOpaque(false);
        versionText.setText("AkashaQuestEditor v.2.1.0 autorstwa Karwsz, używaj na własną odpowiedzialność");
        versionText.setForeground(Color.GRAY);
        versionText.setFont(versionText.getFont().deriveFont(17f));
        add(versionText);
    }

}
