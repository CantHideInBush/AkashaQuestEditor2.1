package pl.canthideinbush.akashaquesteditor.app.components.quest;

import pl.canthideinbush.akashaquesteditor.quest.objects.Instruction;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Map;

public class InstructionsTable extends JPanel {

    private final Map<String, String> instructions;

    public InstructionsTable(Map<String, String> instructions) {
        initialize();
        this.instructions = instructions;
    }

    private void initialize() {
        setLayout(new GridLayout());
        setBackground(Color.LIGHT_GRAY);
    }

    public void update() {

    }


    public static class InstructionBlock extends JPanel {

        private final String instruction;
        private final GridBagConstraints constraints;
        private JTextField nameField;

        public InstructionBlock(String name, String instruction) {
            setLayout(new GridBagLayout());
            constraints = new GridBagConstraints();
            setName(name);
            this.instruction = instruction;
            initializeComponents();
        }

        private void initializeComponents() {
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 0.8;
            constraints.weighty = 1;
            nameField = new JTextField();
        }

    }

}
