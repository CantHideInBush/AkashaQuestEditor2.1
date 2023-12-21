package pl.canthideinbush.akashaquesteditor.app.components.quest;

import pl.canthideinbush.akashaquesteditor.app.TextComponents;
import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.dynamic.blocks.ConversationBlock;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class InstructionsTable extends JPanel {

    private final Map<String, String> instructions;

    private final ArrayList<InstructionBlock> instructionBlocks = new ArrayList<>();
    private GridBagConstraints constraints = newConstraints();

    private ConversationBlock editedBlock;

    enum Category {
        EVENTS(0),
        CONDITIONS(1),
        OBJECTIVES(2);

        public final int id;

        Category(int id) {
            this.id = id;
        }
    }


    public InstructionsTable(Map<String, String> instructions) {
        initialize();
        this.instructions = instructions;
    }

    JPanel filler = new JPanel();


    private void initialize() {
        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);
        InstructionBlock header = new InstructionBlock(null, "Nazwa", "Instrukcja", false);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 16f));
        header.setBackground(Color.LIGHT_GRAY);
        add(header, constraints);
        filler.setPreferredSize(new Dimension(0, 9999));
        filler.setBackground(Color.DARK_GRAY);

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
        InstructionBlock instructionBlock = new InstructionBlock(this, name, instruction, true);
        instructionBlocks.add(instructionBlock);
        constraints.gridy += 1;
        add(instructionBlock, constraints);
    }


    private void handleInstructionClick(InstructionBlock instance) {
        if (editedBlock != null) {

        }
    }

    public static class InstructionBlock extends JPanel {


        private final GridBagConstraints constraints;
        private final InstructionsTable parent;
        private JLabel actionButton;

        InstructionBlock instance = this;

        public InstructionBlock(InstructionsTable parent, String name, String instruction, boolean withRemove) {
            this.parent = parent;
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
            nameField.setBorder(new LineBorder(Color.BLACK));
            TextComponents.disableSelection(nameField);
            add(nameField, constraints);


            JTextField instructionField = new JTextField(instruction);
            constraints.gridx = 1;
            constraints.weightx = 0.6;
            instructionField.setPreferredSize(new Dimension(9999, 30));
            instructionField.setFont(getFont().deriveFont(15f));
            instructionField.setHorizontalAlignment(SwingConstants.CENTER);
            instructionField.setBorder(new LineBorder(Color.BLACK));
            TextComponents.disableSelection(instructionField);
            add(instructionField, constraints);


            constraints.gridx = 2;
            constraints.weightx = 0.01;
            if (withRemove) {
                actionButton = new JLabel(new ImageIcon(Popups.close.getImage().getScaledInstance(30, 30, Image.SCALE_REPLICATE)));
                actionButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        actionButton.setBackground(Color.LIGHT_GRAY);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        actionButton.setBackground(Color.WHITE);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        parent.handleInstructionClick(instance);
                        parent.instructions.remove(nameField.getText());
                        parent.update();
                    }
                });
            }
            else {
                actionButton = new JLabel(new ImageIcon(Popups.close.getImage().getScaledInstance(30, 30, Image.SCALE_FAST))) {
                    @Override
                    protected void paintComponent(Graphics g) {

                    }
                };
            }
            actionButton.setPreferredSize(new Dimension(30, 30));
            actionButton.setBorder(new LineBorder(Color.BLACK));
            actionButton.setOpaque(true);



            add(actionButton, constraints);
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

    public void enterEdit(ConversationBlock conversationBlock) {
        this.editedBlock = conversationBlock;
    }

    public void exitEdit() {
        this.editedBlock = null;
    }

}
