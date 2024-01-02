package pl.canthideinbush.akashaquesteditor.app.components.quest.items;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.components.compose.ConversationComposer;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class EnchantmentComponent extends JPanel {

    public JButton removeButton;
    public JComboBox<String> enchantmentTypeComp;
    public JTextField levelField;

    public EnchantmentComponent() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        initializeComponents();
        setBackground(ConversationComposer.background);

    }

    private void initializeComponents() {
        removeButton = new JButton(new ImageIcon(Popups.close.getImage().getScaledInstance(25, 25, Image.SCALE_REPLICATE)));
        removeButton.setBackground(ConversationComposer.background);
        removeButton.setPreferredSize(new Dimension(25, 25));
        add(removeButton);
        enchantmentTypeComp = new JComboBox<>() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 25);
            }
        };
        for (String enchantment : extractEnchantments()) {
            enchantmentTypeComp.addItem(enchantment);
        }
//        enchantmentTypeComp.setPreferredSize(new Dimension(50, 25));
        add(enchantmentTypeComp);
        levelField = new JTextField();
        levelField.setText("1");
        levelField.setPreferredSize(new Dimension(195, 25));
        levelField.setHorizontalAlignment(SwingConstants.CENTER);
        add(levelField);
    }


    private ArrayList<String> extractEnchantments() {
        ArrayList<String> enchantments = new ArrayList<>();
        try {
        for (Field field : Enchantment.class.getFields()) {
            if (Enchantment.class.isAssignableFrom(field.getType())) {
                    enchantments.add(((Enchantment) field.get(null)).getKey().getKey());
            }
        }
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return enchantments;
    }

}
