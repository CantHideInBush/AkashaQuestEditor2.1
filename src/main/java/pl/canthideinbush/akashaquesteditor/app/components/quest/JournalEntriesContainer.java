package pl.canthideinbush.akashaquesteditor.app.components.quest;

import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.components.compose.ConversationComposer;
import pl.canthideinbush.akashaquesteditor.io.ISerialization;
import pl.canthideinbush.akashaquesteditor.io.SF;
import pl.canthideinbush.akashaquesteditor.io.SelfAttach;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class JournalEntriesContainer extends JPanel implements SelfAttach {

    private final GridBagConstraints constraints;
    private JPanel container;

    @SF
    public List<JournalEntryPanel> journalEntryPanels = new ArrayList<>();

    public JournalEntriesContainer() {
        setLayout(new GridBagLayout());
        setBackground(ConversationComposer.background);
        constraints = new GridBagConstraints();
        initializeComponents();
        ISerialization.register(this);
        for (JournalEntryPanel panel : journalEntryPanels) {
            add(panel);
        }
    }

    public static JournalEntriesContainer deserialize(Map<String, Object> data) {
        JournalEntriesContainer container = new JournalEntriesContainer();
        container.deserializeFromMap(data);
        return container;
    }

    private void initializeComponents() {
        container = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                int width = Application.instance.sessionContainer.getWidth() - 50;
                int fit = width / 225;
                if (fit == 0) return new Dimension(0, 0);
                int rows = (getComponentCount() + getComponentCount() % fit) / fit;
                // entry height * rows + vGap * rows + bottom space
                return new Dimension(width, 350 * rows + 15 * rows + 30);
            }
        };
        container.setBorder(new EmptyBorder(25, 25, 25, 25));
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        flowLayout.setHgap(25);
        flowLayout.setVgap(15);
        container.setLayout(flowLayout);
        constraints.weighty = 0.95;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        container.setBackground(ConversationComposer.background);
        container.setPreferredSize(new Dimension(0, 0));
        JScrollPane scrollPane= new JScrollPane(container);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
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
                        JournalEntryPanel journalEntryPanel = new JournalEntryPanel(name, text);
                        journalEntryPanels.add(journalEntryPanel);
                        container.add(journalEntryPanel);

                        if (Application.instance.settings.shouldAutoGenerateJournalInstructions()) {
                            generateEventsForEntry(name);
                            InstructionsPanel.getInstance().eventsTable.update();
                        }

                        container.updateUI();
                    }
                });
            }
        });

        container.updateUI();
    }

    public void generateEventsForEntry(String name) {
        Map<String, String> events = Application.instance.sessionContainer.session.instructions.get("events");

        events.put("jrnl_add_" + name, "journal add " + name);
        events.put("jrnl_del_" + name, "journal delete " + name);


    }

    public void renameEventsOfEntry(String name, String newName) {
        removeEventsOfEntry(name);
        generateEventsForEntry(newName);
        InstructionsPanel.getInstance().eventsTable.update();
    }

    public void removeEventsOfEntry(String name) {
        Map<String, String> events = Application.instance.sessionContainer.session.instructions.get("events");
        events.remove("jrnl_add_" + name);
        events.remove("jrnl_del_" + name);
    }



    @Override
    public void attach() {
        Application.instance.sessionContainer.attachJournalEntriesContainer(this);
    }

    @Override
    public List<Class<? extends SelfAttach>> dependencies() {
        return Collections.singletonList(QuestSession.class);
    }


    public void cloneFrom(JournalEntriesContainer journalEntriesContainer) {
        container.removeAll();
        journalEntryPanels = journalEntriesContainer.journalEntryPanels;
        journalEntryPanels.forEach(container::add);
    }

    public void removeJournalEntry(JournalEntryPanel journalEntryPanel) {
        journalEntryPanels.remove(journalEntryPanel);
        container.remove(journalEntryPanel);
        container.updateUI();
    }
}
