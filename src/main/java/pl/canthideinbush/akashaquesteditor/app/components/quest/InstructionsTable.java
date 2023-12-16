package pl.canthideinbush.akashaquesteditor.app.components.quest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class InstructionsTable extends JPanel {

    private final Map<String, String> instructions;

    private final ArrayList<InstructionBlock> instructionBlocks = new ArrayList<>();
    private GridBagConstraints constraints;


    public InstructionsTable(Map<String, String> instructions) {
        initialize();
        this.instructions = instructions;
    }

    private void initialize() {
        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);
    }

    public void update() {
        InstructionBlock next;
        Iterator<InstructionBlock> iterator = instructionBlocks.iterator();
        while (iterator.hasNext()) {
            next = iterator.next();
            remove(next);
            iterator.remove();
        }
        constraints = newConstraints();
        instructions.forEach(this::addInstructionBlock);
        updateUI();
    }


    private GridBagConstraints newConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        return constraints;
    }

    private void addInstructionBlock(String name, String instruction) {
        InstructionBlock instructionBlock = new InstructionBlock(name, instruction);
        instructionBlocks.add(instructionBlock);
        add(instructionBlock, constraints);
        constraints.gridy += 1;
    }


    public static class InstructionBlock extends JPanel {

        private final GridBagConstraints constraints;

        public InstructionBlock(String name, String instruction) {
            setLayout(new GridBagLayout());
            constraints = new GridBagConstraints();
            setName(name);
            initializeComponents(instruction);
        }

        private void initializeComponents(String instruction) {
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 0.3;
            constraints.weighty = 1;

            JTextField nameField = new JTextField(getName());
            add(nameField, constraints);

            JTextField instructionField = new JTextField(instruction);
            constraints.gridx = 1;
            constraints.weightx = 0.7;
            add(instructionField, constraints);
        }


    }

}
