package pl.canthideinbush.akashaquesteditor.app;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class WelcomePanel extends JPanel {

    public WelcomePanel() {
        initialize();
        initializeComponents();

    }

    private void initialize() {
        setPreferredSize(new Dimension(Application.instance.getPreferredSize().width, Application.instance.getPreferredSize().height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new LineBorder(Color.RED, 1));

    }

    private void initializeComponents() {
        JPanel textContainer = getTextContainer();
        add(textContainer);

        JTextField versionText = new JTextField();
        versionText.setPreferredSize(new Dimension(600, 200));
        versionText.setBorder(new LineBorder(Color.RED, 1));
        versionText.setOpaque(false);
        versionText.setText("AkashaQuestEditor v.2.1.0 autorstwa Karwsz, używaj na własną odpowiedzialność");
        versionText.setForeground(Color.GRAY);
        versionText.setFont(versionText.getFont().deriveFont(17f));
        TextComponents.disableSelection(versionText);
        add(versionText);

    }

    @NotNull
    private JPanel getTextContainer() {
        JPanel textContainer = new JPanel();
        textContainer.setBackground(Color.LIGHT_GRAY);
        textContainer.setOpaque(true);
        textContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        textContainer.setPreferredSize(new Dimension(getPreferredSize().width, 9999));
        textContainer.setBorder(new LineBorder(Color.RED, 1));
        JTextArea jTextArea = new JTextArea();
        jTextArea.setFont(jTextArea.getFont().deriveFont(25f));
        jTextArea.setForeground(Color.DARK_GRAY);
        jTextArea.setText("Utwórz sesję aby rozpocząć");
        jTextArea.setVisible(true);
        jTextArea.setOpaque(false);
        TextComponents.disableSelection(jTextArea);
        textContainer.add(jTextArea);
        return textContainer;
    }


}
