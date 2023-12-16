package pl.canthideinbush.akashaquesteditor.app.components.quest;

import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimationContainer;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.components.ResizableIcon;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class InstructionsTable extends JPanel {

    private final Map<String, String> instructions;

    private final ArrayList<InstructionBlock> instructionBlocks = new ArrayList<>();
    private GridBagConstraints constraints = newConstraints();


    public InstructionsTable(Map<String, String> instructions) {
        initialize();
        this.instructions = instructions;
    }

    JPanel filler = new JPanel();


    private void initialize() {
        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);
        InstructionBlock header = new InstructionBlock("Nazwa", "Instrukcja", false);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 16f));
        header.setBackground(Color.LIGHT_GRAY);
        add(header, constraints);
        filler.setPreferredSize(new Dimension(0, 9999));
        filler.setBackground(getBackground());

    }

    public void update() {
        remove(filler);
        InstructionBlock next;
        Iterator<InstructionBlock> iterator = instructionBlocks.iterator();
        while (iterator.hasNext()) {
            next = iterator.next();
            remove(next);
            iterator.remove();
        }
        constraints = newConstraints();
        instructions.forEach(this::addInstructionBlock);

        constraints.weighty = 1;
        add(filler, constraints);
        updateUI();
    }


    private GridBagConstraints newConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.weightx = 1;
        constraints.weighty = 0.01;
        constraints.gridx = 0;
        constraints.gridy = 1;
        return constraints;
    }

    private void addInstructionBlock(String name, String instruction) {
        InstructionBlock instructionBlock = new InstructionBlock(name, instruction, true);
        instructionBlocks.add(instructionBlock);
        constraints.gridy += 1;
        add(instructionBlock, constraints);
    }


    public static class InstructionBlock extends JPanel {

        private final GridBagConstraints constraints;

        public InstructionBlock(String name, String instruction, boolean withRemove) {
            setLayout(new GridBagLayout());
            constraints = new GridBagConstraints();
            setName(name);
            initializeComponents(instruction, withRemove);
            setPreferredSize(new Dimension(getWidth(), 30));
            setMaximumSize(getPreferredSize());
        }

        private void initializeComponents(String instruction, boolean withRemove) {
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 0.3;
            constraints.weighty = 1;

            JTextField nameField = new JTextField(getName());
            nameField.setPreferredSize(new Dimension(getWidth(), 30));
            nameField.setFont(getFont().deriveFont(15f));
            nameField.setHorizontalAlignment(SwingConstants.CENTER);
            add(nameField, constraints);


            JTextField instructionField = new JTextField(instruction);
            constraints.gridx = 1;
            constraints.weightx = 0.6;
            instructionField.setPreferredSize(new Dimension(9999, 30));
            instructionField.setFont(getFont().deriveFont(15f));
            instructionField.setHorizontalAlignment(SwingConstants.CENTER);
            add(instructionField, constraints);


            if (withRemove) {
                JLabel removeButton = new JLabel(new ImageIcon(Popups.close.getImage().getScaledInstance(30, 30, Image.SCALE_REPLICATE)));
                removeButton.setPreferredSize(new Dimension(30, 30));
                removeButton.setBorder(new LineBorder(Color.BLACK));
                removeButton.setOpaque(true);
                removeButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        removeButton.setBackground(Color.LIGHT_GRAY);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        removeButton.setBackground(Color.WHITE);
                    }
                });
                constraints.gridx = 2;
                constraints.weightx = 0.01;
                add(removeButton, constraints);
            }
        }

        @Override
        public void setBackground(Color bg) {
            super.setBackground(bg);
            for (Component component : getComponents()) {
                component.setBackground(bg);
            }
        }

        @Override
        public void setFont(Font font) {
            super.setFont(font);
            for (Component component : getComponents()) {
                component.setFont(font);
            }
        }
    }

}
