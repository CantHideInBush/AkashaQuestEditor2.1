package pl.canthideinbush.akashaquesteditor.app.components.quest;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.components.QuestComponent;
import pl.canthideinbush.akashaquesteditor.quest.objects.JournalEntry;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JournalEntriesContainer extends JPanel {

    private final GridBagConstraints constraints;
    private final GridBagLayout layout;
    private JPanel container;
    private FlowLayout flowLayout;

    public JournalEntriesContainer() {
        setLayout(layout = new GridBagLayout());
        setBackground(Color.DARK_GRAY);
        constraints = new GridBagConstraints();
        initializeComponents();
    }

    private void initializeComponents() {
        container = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                int width = Application.instance.sessionContainer.getWidth() - 50;
                int fit = width / 225;
                int rows = (getComponentCount() + getComponentCount() % fit) / fit;
                // entry height * rows + vGap * rows + bottom space
                return new Dimension(width, 350 * rows + 15 * rows + 30);
            }
        };
        container.setBackground(Color.DARK_GRAY);
        container.setBorder(new EmptyBorder(25, 25, 25, 25));
        flowLayout= new FlowLayout(FlowLayout.CENTER);
        flowLayout.setHgap(25);
        flowLayout.setVgap(15);
        container.setLayout(flowLayout);
        constraints.weighty = 0.95;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        container.setPreferredSize(new Dimension(0, 0));
        JScrollPane scrollPane= new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, constraints);


        JButton addJournalEntryButton = new JButton("Dodaj wpis");
        constraints.gridy = 1;
        constraints.weighty = 0.05;
        add(addJournalEntryButton, constraints);

        addJournalEntryButton.addActionListener(e -> {
            String name = Popups.createShortTextPopup("Tworzenie wpisu", "Podaj nazwę wpisu", "");
            if (!"".equalsIgnoreCase(name) && name != null) {
                Popups.createLongTextPopup("Podaj treść wpisu", "", (text) -> {
                    if (text != null) {
                        container.add(new JournalEntryPanel(name, text));
                        fillContainer();
                        container.updateUI();
                    }
                });
            }
        });

        for (int i = 0; i < 100; i++) {
            container.add(new JournalEntryPanel("test", ""));
        }
        container.updateUI();
    }


    List<Component> fillers = new ArrayList<>();
    private void fillContainer() {
        fillers.forEach(container::remove);
        fillers.clear();
        for (int i = 0; i < Math.max(0, 10 - container.getComponentCount()); i++) {
            JPanel filler;
            fillers.add(filler = new JPanel());
            filler.setBackground(Color.RED);
            container.add(filler);
        }
    }


    public static class JournalEntryPanel extends JPanel implements QuestComponent<pl.canthideinbush.akashaquesteditor.quest.objects.JournalEntry> {

        private final String text;

        public JournalEntryPanel(String name, String text) {
            setName(name);
            this.text = text;
            setPreferredSize(new Dimension(200, 350));
        }

        @Override
        public JournalEntry create() {

            return new JournalEntry(getName(), text.replaceAll("\\r", ""));
        }
    }
}
