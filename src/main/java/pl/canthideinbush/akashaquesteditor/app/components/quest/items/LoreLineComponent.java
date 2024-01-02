package pl.canthideinbush.akashaquesteditor.app.components.quest.items;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.components.compose.ConversationComposer;

import javax.swing.*;
import java.awt.*;

public class LoreLineComponent extends JPanel {

    public JButton removeButton;
    public JTextField textField;

    public LoreLineComponent() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        initializeComponents();
        setBackground(ConversationComposer.background);

    }

    private void initializeComponents() {
        removeButton = new JButton(new ImageIcon(Popups.close.getImage().getScaledInstance(25, 25, Image.SCALE_REPLICATE)));
        removeButton.setBackground(ConversationComposer.background);
        removeButton.setPreferredSize(new Dimension(25, 25));
        add(removeButton);
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(325, 25));
        add(textField);
    }


}
