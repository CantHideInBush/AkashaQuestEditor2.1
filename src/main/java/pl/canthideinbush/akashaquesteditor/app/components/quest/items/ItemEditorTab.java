package pl.canthideinbush.akashaquesteditor.app.components.quest.items;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;
import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.components.compose.ConversationComposer;
import pl.canthideinbush.akashaquesteditor.quest.objects.Item;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemEditorTab extends JPanel {

    private JComboBox<String> selectedMaterial;
    private JTextField idField;

    private final GridBagConstraints constraints = new GridBagConstraints();
    private JTextField displayNameField;

    private final ArrayList<EnchantmentComponent> enchantmentComponents = new ArrayList<>();
    private JComboBox<String> unbreakableSelect;
    private JCheckBox isUnbreakableComp;
    private FlagsComponent flagsComponent;
    private JSpinner customModelDataComp;
    private JTextField bookTitle;

    private JTextField bookAuthor;
    private JButton bookTextComp;
    private String bookText;
    private JLabel bookTitleL;
    private JLabel bookAuthorL;
    private JLabel bookTextL;
    private JLabel potionTypeL;
    private JComboBox<String> potionType;
    private JLabel potionExtendedL;
    private JComboBox<String> potionExtended;
    private JLabel potionUpgradedL;
    private JComboBox<String> potionUpgraded;

    public ItemEditorTab() {
        setBackground(ConversationComposer.background);
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new EmptyBorder(15, 5, 15, 15)));
        setLayout(new GridBagLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(3, 0, 0,0);
        createItemNameInput();
        nextRow();
        createMaterialSelection();
        nextRow();
        createDisplayNameSelection();
        nextRow();
        createLoreSection();
        nextRow();
        createEnchantmentsSection();
        nextRow();
        createUnbreakableSelection();
        nextRow();
        createItemFlagsSelection();
        nextRow();
        createCustomModelDataSelection();
        nextRow();
        createBookDataFields();
        nextRow();
        createPotionDataFields();


        for (Component component : getComponents()) {
            component.setPreferredSize(new Dimension(component.getPreferredSize().width, 25));
        }

        nextRow();
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        add(filler, constraints);
        constraints.fill = GridBagConstraints.HORIZONTAL;
    }

    private void createPotionDataFields() {
        potionTypeL = createOpaqueCenteredLabel("Typ mikstury");
        potionType = new JComboBox<>();
        potionType.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });
        for (String potionEffectType : extractPotionEffectTypes()) {
            potionType.addItem(potionEffectType);
        }
        add(potionTypeL, constraints);
        nextCol();
        add(potionType, constraints);
        nextRow();

        potionExtendedL = createOpaqueCenteredLabel("Mikstura przedłużona?");
        potionExtended = new JComboBox<>();
        potionExtended.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });
        for (Item.PotionExtended extended : Item.PotionExtended.values()) {
            potionExtended.addItem(extended.value);
        }

        add(potionExtendedL, constraints);
        nextCol();
        add(potionExtended, constraints);

        nextRow();
        potionUpgradedL = createOpaqueCenteredLabel("Mikstura ulepszona?");
        potionUpgraded = new JComboBox<>();
        for (Item.PotionUpgraded upgraded : Item.PotionUpgraded.values()) {
            potionUpgraded.addItem(upgraded.value);
        }
        potionUpgraded.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });
        add(potionUpgradedL, constraints);
        nextCol();
        add(potionUpgraded, constraints);

        setPotionDataVisible(false);

    }

    public void setPotionDataVisible(boolean visible) {

    }



    private void createBookDataFields() {
        bookTitleL = new JLabel("Tytuł książki");
        bookTitleL.setOpaque(true);
        bookTitleL.setHorizontalAlignment(SwingConstants.CENTER);
        bookTitle = new JTextField();
        bookTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(bookTitleL, constraints);
        nextCol();
        add(bookTitle, constraints);
        nextRow();
        bookAuthorL = new JLabel("Autor książki");
        bookAuthorL.setOpaque(true);
        bookAuthorL.setHorizontalAlignment(SwingConstants.CENTER);
        add(bookAuthorL, constraints);
        bookAuthor = new JTextField();
        bookAuthor.setHorizontalAlignment(SwingConstants.CENTER);
        nextCol();
        add(bookAuthor, constraints);
        nextRow();
        bookTextL = new JLabel("Tekst książki");
        bookTextL.setOpaque(true);
        bookTextL.setHorizontalAlignment(SwingConstants.CENTER);
        add(bookTextL, constraints);
        bookTextComp = new JButton("Edytuj");
        bookTextComp.setHorizontalAlignment(SwingConstants.CENTER);
        nextCol();
        add(bookTextComp, constraints);
        bookTextComp.addActionListener(e -> {
            Popups.createLongTextPopup("Tekst książki", bookText, (s) -> bookText = s);
        });
        setBookParamsVisible(false);
    }

    public void setBookParamsVisible(boolean visible) {
        bookTitle.setVisible(visible);
        bookAuthor.setVisible(visible);
        bookTextComp.setVisible(visible);
        bookTitleL.setVisible(visible);
        bookAuthorL.setVisible(visible);
        bookTextL.setVisible(visible);
    }

    private void createCustomModelDataSelection() {
        customModelDataComp = new JSpinner();
        customModelDataComp.setValue(-1);
        JLabel ctmLabel1 = new JLabel("CustomModelData");
        ctmLabel1.setOpaque(true);
        ctmLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        add(ctmLabel1, constraints);
        nextCol();
        add(customModelDataComp, constraints);
        nextRow();
        constraints.gridwidth = 2;
        JLabel ctmLabel = new JLabel("CustomModelData < -1: no-custom-model-data");
        ctmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ctmLabel.setOpaque(true);
        add(ctmLabel, constraints);
        constraints.gridwidth = 1;
    }

    private void createItemFlagsSelection() {
        flagsComponent = new FlagsComponent();
        flagsComponent.setVisible(false);
        JButton flagsVisible = new JButton("Pokaż flagi");
        flagsVisible.addActionListener(new ActionListener() {
            boolean show = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                show = !show;
                flagsVisible.setText(show ? "Ukryj flagi" : "Pokaż flagi");
                flagsComponent.setVisible(show);
            }
        });
        constraints.gridwidth = 2;
        add(flagsVisible, constraints);
        nextRow();
        add(flagsComponent, constraints);
        constraints.gridwidth = 1;
    }

    private void createUnbreakableSelection() {
        JLabel unbreakableLabel = new JLabel("Niezniszczalny?");
        unbreakableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        unbreakableLabel.setOpaque(true);
        add(unbreakableLabel, constraints);
        nextCol();
        isUnbreakableComp = new JCheckBox();
        isUnbreakableComp.setHorizontalAlignment(SwingConstants.CENTER);
        isUnbreakableComp.setPreferredSize(new Dimension(25, 25));
        isUnbreakableComp.addActionListener(e -> {
            unbreakableSelect.setVisible(isUnbreakableComp.isSelected());
        });
        add(isUnbreakableComp, constraints);
        nextRow();
        constraints.gridwidth = 2;
        unbreakableSelect = new JComboBox<>();
        unbreakableSelect.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel comp = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                comp.setHorizontalAlignment(SwingConstants.CENTER);
                return comp;
            }
        });
        unbreakableSelect.setVisible(false);

        for (Item.Unbreakable unbreakable : Item.Unbreakable.values()) {
            unbreakableSelect.addItem(unbreakable.value);
        }
        add(unbreakableSelect, constraints);
        constraints.gridwidth = 1;
    }

    private void createEnchantmentsSection() {
        constraints.gridwidth = 2;
        JButton createEnchantmentB = new JButton("Dodaj zaklęcie");
        add(createEnchantmentB, constraints);
        nextRow();
        JPanel enchantmentCompContainer = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(0, 35 * getComponentCount());
            }
        };
        enchantmentCompContainer.setLayout(new BoxLayout(enchantmentCompContainer, BoxLayout.Y_AXIS));
        add(enchantmentCompContainer, constraints);
        constraints.gridwidth = 1;

        createEnchantmentB.addActionListener(e -> {
            EnchantmentComponent enchantmentComp = new EnchantmentComponent();
            enchantmentComp.removeButton.addActionListener(e1 -> {
                enchantmentComponents.remove(enchantmentComp);
                enchantmentCompContainer.remove(enchantmentComp);
                enchantmentCompContainer.updateUI();
                repaint();
            });
            enchantmentComponents.add(enchantmentComp);
            enchantmentCompContainer.add(enchantmentComp);
            enchantmentCompContainer.updateUI();
            repaint();
        });
    }

    ArrayList<LoreLineComponent> loreComponents = new ArrayList<>();

    private void createLoreSection() {
        JButton addLoreLine = new JButton("Dodaj opis");
        constraints.gridwidth  = 2;
        add(addLoreLine, constraints);
        nextRow();
        JPanel loreComponentsContainer = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(0, 35 * getComponentCount());
            }
        };
        loreComponentsContainer.setLayout(new BoxLayout(loreComponentsContainer, BoxLayout.Y_AXIS));
        add(loreComponentsContainer, constraints);
        constraints.weighty = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        addLoreLine.addActionListener(e -> {
            LoreLineComponent newComponent = new LoreLineComponent();
            newComponent.removeButton.addActionListener(e1 -> {
                loreComponents.remove(newComponent);
                loreComponentsContainer.remove(newComponent);
                loreComponentsContainer.updateUI();
                getParent().repaint();
            });
            loreComponents.add(newComponent);
            loreComponentsContainer.add(newComponent);
            loreComponentsContainer.updateUI();
            getParent().repaint();
        });
    }

    private void createDisplayNameSelection() {
        addColLabel("Wyświetlana nazwa");
        nextCol();
        displayNameField = addTextField();
    }


    private void nextRow() {
        constraints.gridx = 0;
        constraints.gridy+=1;
    }
    private void nextCol() {
        constraints.gridx++;
    }

    private void createItemNameInput() {
        addColLabel("ID przedmiotu");
        nextCol();
        idField = addTextField();
    }

    private void createMaterialSelection() {
        addColLabel("Materiał");
        nextCol();
        selectedMaterial = new JComboBox<>();
        selectedMaterial.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });
        for (String value : Arrays.stream(Material.values()).map(Material::name).sorted().toList()) {
            selectedMaterial.addItem(value);
        }
        selectedMaterial.addActionListener(e -> {
            Material material = Material.valueOf(String.valueOf(selectedMaterial.getSelectedItem()));
            setBookParamsVisible(Material.WRITTEN_BOOK.equals(material) || Material.WRITABLE_BOOK.equals(material));
        });
        add(selectedMaterial, constraints);
    }

    private JLabel addColLabel(String label) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(0, 10, 0, 10)));
        jLabel.setOpaque(true);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(jLabel, constraints);
        return jLabel;
    }

    private JTextField addTextField() {
        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(0, 5, 0, 5)));
        add(textField, constraints);
        return textField;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, (-2 + getComponentCount()) * 28 + loreComponents.size() * 35);
    }

    public Material getSelectedMaterial() {
        return Material.valueOf((String) selectedMaterial.getSelectedItem());
    }

    public String itemId() {
        return idField.getText();
    }

    public String itemDisplayName() {
        return displayNameField.getText().replaceAll(" ", "_");
    }

    public ArrayList<String> editorLore() {
        ArrayList<String> lore = new ArrayList<>();
        for (LoreLineComponent comp : loreComponents) {
            lore.add(comp.textField.getText().replaceAll(" ", "_"));
        }
        return lore;
    }

    public HashMap<String, String> editorEnchantments() {
        HashMap<String, String> enchantments = new HashMap<>();
        for (EnchantmentComponent enchantmentComponent : enchantmentComponents) {
            enchantments.put((String) enchantmentComponent.enchantmentTypeComp.getSelectedItem(), enchantmentComponent.levelField.getText());
        }
        return enchantments;
    }

    public Item.Unbreakable isUnbreakable() {
        if (!isUnbreakableComp.isSelected()) {
            return null;
        }
        return Item.Unbreakable.valueOf((String) unbreakableSelect.getSelectedItem());
    }

    private List<String> extractPotionEffectTypes() {
        ArrayList<String> potionEffectTypes = new ArrayList<>();
        try {
        for (Field field : PotionEffectType.class.getFields()) {
            if (PotionEffectType.class.isAssignableFrom(field.getType())) {
                potionEffectTypes.add(((PotionEffectType) field.get(null)).getKey().getKey());
            }
        }
        } catch (
                IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return potionEffectTypes.stream().sorted().toList();
    }

    private JLabel createOpaqueCenteredLabel(String text) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
}
