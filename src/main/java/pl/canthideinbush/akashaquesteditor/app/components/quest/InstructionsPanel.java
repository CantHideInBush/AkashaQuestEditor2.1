package pl.canthideinbush.akashaquesteditor.app.components.quest;

import javax.swing.*;
import java.awt.*;

public class InstructionsPanel extends JPanel {

    public InstructionsPanel() {
        initialize();
        initializeComponents();
    }

    private void initializeComponents() {
        JPanel createInstructionPanel = new JPanel();
        createInstructionPanel.setLayout(new GridLayout(0, 1));
        JTextField instructionField = new JTextField();
        instructionField.setPreferredSize(new Dimension(9999, 50));
        JComboBox<String> categoriesBox = new JComboBox<>();
        createInstructionPanel.add(instructionField);
        createInstructionPanel.add(categoriesBox);
        add(createInstructionPanel);
    }

    private void initialize() {
        setPreferredSize(new Dimension(9999, 100));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }



}
