package pl.canthideinbush.akashaquesteditor.app;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {

    public WelcomePanel() {
        initialize();
        initializeComponents();

    }

    private void initialize() {
        setPreferredSize(new Dimension(Application.instance.getPreferredSize().width, Application.instance.getPreferredSize().height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void initializeComponents() {
        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(getPreferredSize().width, 9999));
        add(contentPanel);

        Component versionText = createVersionText();
        add(versionText);
    }

    @NotNull
    private JTextField createVersionText() {
        JTextField versionText = new JTextField();
        versionText.setPreferredSize(new Dimension(600, 100));
        versionText.setOpaque(false);
        versionText.setText("AkashaQuestEditor v.2.1.0 autorstwa Karwsz - używasz na własną odpowiedzialność");
        versionText.setBorder(null);
        versionText.setForeground(Color.DARK_GRAY);
        versionText.setFont(versionText.getFont().deriveFont(15f));
        TextComponents.disableSelection(versionText);
        return versionText;
    }



}
