package pl.canthideinbush.akashaquesteditor.app.components.quest;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.components.quest.compose.ConversationComposer;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimationContainer;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.components.ResizableIcon;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.ConversationBlock;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class InstructionsPanel extends JPanel {

    private ResizeAnimationContainer cbResizeContainer;
    public JTextField instructionNameField;
    private JTextField instructionField;
    private JComboBox<Object> categoriesBox;
    private GridBagConstraints constraints;
    public JTabbedPane instructionTables;
    public InstructionsTable eventsTable;
    public InstructionsTable conditionsTable;
    public InstructionsTable objectivesTable;

    public ConversationBlock editedBlock;

    public InstructionsPanel() {
        initialize();
    }

    Component[] resize = new Component[5];


    public void initializeComponents() {
        addInstructionTables();
        addInstructionCreationPanel();
    }

    private void addInstructionTables() {
        instructionTables = new JTabbedPane();

        instructionTables.addTab("Zdarzenia", new JScrollPane(eventsTable = new InstructionsTable(InstructionsTable.Category.EVENTS, Application.instance.sessionContainer.session.instructions.get("events"))));
        instructionTables.addTab("Warunki", new JScrollPane(conditionsTable = new InstructionsTable(InstructionsTable.Category.CONDITIONS, Application.instance.sessionContainer.session.instructions.get("conditions"))));
        instructionTables.addTab("Cele", new JScrollPane(objectivesTable = new InstructionsTable(InstructionsTable.Category.OBJECTIVES, Application.instance.sessionContainer.session.instructions.get("objectives"))));

        eventsTable.update();
        conditionsTable.update();
        objectivesTable.update();

        constraints.insets = new Insets(0, 5, 0, 5);
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 0.5;
        add(instructionTables, constraints);

        instructionTables.addChangeListener(e -> categoriesBox.setSelectedIndex(instructionTables.getSelectedIndex()));


    }


    private void addInstructionCreationPanel() {
        JPanel createInstructionPanel = new JPanel();
        createInstructionPanel.setBackground(Color.LIGHT_GRAY);
        resize[0] = createInstructionPanel;
        createInstructionPanel.setLayout(new GridBagLayout());
        createInstructionPanel.setMinimumSize(new Dimension(500, 100));
        createInstructionPanel.setPreferredSize(new Dimension(1100, 100));
        createInstructionPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(5, 0, 0, 0)));

        GridBagConstraints instructionCreationPanelConstraints = new GridBagConstraints();
        createInstructionPanelFields(instructionCreationPanelConstraints, createInstructionPanel);
        createInstructionPanelLabels(instructionCreationPanelConstraints, createInstructionPanel);

        constraints.insets = new Insets(0, 5,5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.NORTH;


        add(createInstructionPanel, constraints);


    }

    private void createInstructionPanelLabels(GridBagConstraints constraints, JPanel createInstructionPanel) {
        constraints.insets.top = 15;
        constraints.weighty = 0.1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        constraints.gridy = 0;

        JLabel nameLabel = bottomCenterLabel("Nazwa");
        constraints.weightx = 0.2;
        constraints.gridx = 0;
        createInstructionPanel.add(nameLabel, constraints);

        JLabel instructionLabel = bottomCenterLabel("Instrukcja");
        constraints.weightx = 0.6;
        constraints.gridx = 1;
        createInstructionPanel.add(instructionLabel, constraints);

        JLabel categoryLabel = bottomCenterLabel("Kategoria");
        constraints.weightx = 0.1;
        constraints.gridx = 2;
        createInstructionPanel.add(categoryLabel, constraints);
    }

    private JLabel bottomCenterLabel(String name) {
        JLabel label = new JLabel(name);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private void createInstructionPanelFields(GridBagConstraints constraints, JPanel createInstructionPanel) {
        constraints.insets = new Insets(-15, 5, 0, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.2;
        constraints.weighty = 0.9;
        constraints.gridy = 1;

        instructionNameField = new JTextField();
        instructionNameField.setHorizontalAlignment(SwingConstants.CENTER);
        instructionNameField.setFont(instructionNameField.getFont().deriveFont(20f));
        Dimension preferred = new Dimension(0, 30);
        instructionNameField.setPreferredSize(preferred);
        createInstructionPanel.add(instructionNameField, constraints);

        instructionNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    instructionField.requestFocus();
                }
            }
        });


        constraints.weightx = 0.6;
        constraints.gridx = 1;
        instructionField = new JTextField();
        instructionField.setHorizontalAlignment(SwingConstants.CENTER);
        instructionField.setFont(instructionField.getFont().deriveFont(20f));
        instructionField.setPreferredSize(preferred);
        createInstructionPanel.add(instructionField, constraints);

        InputMap inputMap = instructionField.getInputMap();
        ActionMap actionMap = instructionField.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("control E"), "add-event");
        actionMap.put("add-event", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriesBox.setSelectedIndex(0);
                addInstruction();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("control C"), "add-condition");
        actionMap.put("add-condition", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriesBox.setSelectedIndex(1);
                addInstruction();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("control O"), "add-objective");
        actionMap.put("add-objective", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriesBox.setSelectedIndex(2);
                addInstruction();
            }
        });



        categoriesBox = new JComboBox<>();
        ((JLabel) categoriesBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        categoriesBox.addItem("events");
        categoriesBox.addItem("conditions");
        categoriesBox.addItem("objectives");
        categoriesBox.setPreferredSize(preferred);

        constraints.gridx = 2;
        constraints.weightx = 0.1;
        createInstructionPanel.add(categoriesBox, constraints);

        ResizableIcon confirmButton = new ResizableIcon(Popups.tickStatic.getImage(), Popups.tick);
        confirmButton.setPreferredSize(new Dimension(20, 20));
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));

        cbResizeContainer = Popups.createResizableComponent(confirmButton, new Dimension(45, 45), new Dimension(52, 52), 150);
        cbResizeContainer.setFocusable(true);
        cbResizeContainer.setBackground(Color.LIGHT_GRAY);


        cbResizeContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addInstruction();
            }
        });

        cbResizeContainer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    addInstruction();
                }
            }
        });



        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.weightx = 0;
        constraints.weighty = 1;
        constraints.insets.top = -10;
        constraints.insets.right = 10;
        createInstructionPanel.add(cbResizeContainer, constraints);
        cbResizeContainer.centerComponent();
    }

    private void addInstruction() {
        if (!shouldOverride()) {
            return;
        }
        String instruction = instructionField.getText();
        String error = verifyInstruction(instruction);
        if (error != null) {
            JOptionPane.showMessageDialog(Application.instance, error, "Błąd dodawania instrukcji", JOptionPane.ERROR_MESSAGE, null);
            return;
        }
        Application.instance.sessionContainer.session.instructions.get((String) categoriesBox.getSelectedItem()).put(instructionNameField.getText(), instruction);
        assert categoriesBox.getSelectedItem() != null;
        switch ((String) categoriesBox.getSelectedItem()) {
            case "events":
                eventsTable.update();
                instructionTables.setSelectedIndex(0);
                break;
            case "conditions":
                conditionsTable.update();
                instructionTables.setSelectedIndex(1);
                break;
            case "objectives":
                objectivesTable.update();
                instructionTables.setSelectedIndex(2);
        }
        instructionNameField.requestFocus();
        instructionNameField.setText("");
        instructionField.setText("");
    }

    private boolean shouldOverride() {
        String name = instructionNameField.getText();
        if ("".equalsIgnoreCase(name) || name == null) return false;
        if (Application.instance.sessionContainer.session.instructions.get((String) categoriesBox.getSelectedItem()).containsKey(name)) {
            //result 0 = User pressed ok button
            return JOptionPane.showConfirmDialog(Application.instance, "Nadpisać instrukcję " + name + "?", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null) == 0;
        }

        return true;
    }
    private String verifyInstruction(String instruction) {
        if ("".equalsIgnoreCase(instruction) || instruction == null) {
            return "Pole instrukcji nie może być puste!";
        }
        return null;
    }

    private void initialize() {
        setPreferredSize(new Dimension(9999, 9999));
        setBackground(ConversationComposer.background);
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        //integrateResize();

    }

    private void integrateResize() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (Component component : resize) {
                    if (component != null) {
                        component.setPreferredSize(new Dimension(getWidth() - 50, component.getPreferredSize().height));
                    }
                }
                updateUI();
            }
        });
    }

    public void enterEdit(ConversationBlock conversationBlock, InstructionsTable.Category category) {
        this.editedBlock = conversationBlock;
        conditionsTable.enterEdit(editedBlock);
        eventsTable.enterEdit(editedBlock);
        Application.instance.sessionContainer.setSelectedIndex(1);
        Application.instance.sessionContainer.instructionsPanel.instructionTables.setSelectedIndex(category.id);
    }

    public void exitEdit() {
        this.editedBlock = null;
        eventsTable.exitEdit();
        conditionsTable.exitEdit();
        eventsTable.exitEdit();
    }


}
