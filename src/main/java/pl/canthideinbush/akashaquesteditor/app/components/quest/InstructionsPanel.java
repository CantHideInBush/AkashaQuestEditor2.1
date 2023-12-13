package pl.canthideinbush.akashaquesteditor.app.components.quest;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimationContainer;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.components.ResizableIcon;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class InstructionsPanel extends JPanel {

    private ResizeAnimationContainer cbResizeContainer;
    private JTextField instructionNameField;
    private JTextField instructionField;
    private JComboBox<Object> categoriesBox;
    private GridBagConstraints constraints;
    private InstructionsTable instructionsTable;
    public InstructionsPanel() {
        initialize();
        initializeComponents();
    }

    Component[] resize = new Component[5];

    private void initializeComponents() {
        addInstructionCreationPanel();
        addInstructionsTable();
    }

    private void addInstructionsTable() {
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.weighty = 1;
        constraints.weightx = 1;
        instructionsTable = new InstructionsTable();
        instructionsTable.setPreferredSize(new Dimension(1, 1));
        add(instructionsTable, constraints);
    }

    private void addInstructionCreationPanel() {
        JPanel createInstructionPanel = new JPanel();
        resize[0] = createInstructionPanel;
        createInstructionPanel.setLayout(new GridBagLayout());
        createInstructionPanel.setMinimumSize(new Dimension(500, 75));
        createInstructionPanel.setPreferredSize(new Dimension(1100, 75));
        createInstructionPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(5, 0, 0, 0)));

        GridBagConstraints constraints = new GridBagConstraints();
        createInstructionPanelFields(constraints, createInstructionPanel);
        createInstructionPanelLabels(constraints, createInstructionPanel);

        constraints.insets = new Insets(10, 5,5, 5);
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.NORTH;


        add(createInstructionPanel, constraints);


    }

    private void createInstructionPanelLabels(GridBagConstraints constraints, JPanel createInstructionPanel) {
        constraints.weighty = 0.25;
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
        constraints.insets = new Insets(0, 5, 0, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.2;
        constraints.weighty = 0.75;
        constraints.gridy = 1;

        instructionNameField = new JTextField();
        instructionNameField.setHorizontalAlignment(SwingConstants.CENTER);
        instructionNameField.setFont(instructionNameField.getFont().deriveFont(20f));
        Dimension preferred = new Dimension(0, 40);
        instructionNameField.setPreferredSize(preferred);
        createInstructionPanel.add(instructionNameField, constraints);


        constraints.weightx = 0.6;
        constraints.gridx = 1;
        instructionField = new JTextField();
        instructionField.setHorizontalAlignment(SwingConstants.CENTER);
        instructionField.setFont(instructionField.getFont().deriveFont(20f));
        instructionField.setPreferredSize(preferred);
        createInstructionPanel.add(instructionField, constraints);

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
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3, true));

        cbResizeContainer = Popups.createResizableComponent(confirmButton, new Dimension(45, 45), new Dimension(50, 50), 150);


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
        constraints.weightx = 0;
        createInstructionPanel.add(cbResizeContainer, constraints);
    }

    private void addInstruction() {
        if (!shouldOverride()) {
            return;
        }
        String instruction = instructionField.getText();
        String error = verifyInstruction(instruction);
        if (error != null) {
            JOptionPane.showMessageDialog(Application.instance, error, "Błąd dodawania instrukcji", JOptionPane.ERROR_MESSAGE, null);
        }
        Application.instance.sessionContainer.session.instructions.get((String) categoriesBox.getSelectedItem()).put(instructionNameField.getText(), instruction);
    }

    private boolean shouldOverride() {
        String name = instructionNameField.getText();
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

    public void fixAnimation() {
        cbResizeContainer.centerComponent();
    }

    private void initialize() {
        setPreferredSize(new Dimension(9999, 9999));
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



}