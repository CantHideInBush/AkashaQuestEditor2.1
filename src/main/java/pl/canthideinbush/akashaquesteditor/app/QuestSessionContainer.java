package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.app.components.ItemEditorTab;
import pl.canthideinbush.akashaquesteditor.app.components.quest.InstructionsPanel;
import pl.canthideinbush.akashaquesteditor.app.components.quest.JournalEntriesContainer;
import pl.canthideinbush.akashaquesteditor.app.components.compose.ComposerInfoBar;
import pl.canthideinbush.akashaquesteditor.app.components.compose.ConversationComposer;
import pl.canthideinbush.akashaquesteditor.app.components.compose.DragZoomPanel;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class QuestSessionContainer extends JTabbedPane {

    public final QuestSession session;
    public DragZoomPanel conversationComposerPanel;
    public ComposerInfoBar composerInfoBar;
    public InstructionsPanel instructionsPanel;
    public JournalEntriesContainer journalEntriesContainer;

    public ItemEditorTab itemEditorTab;



    public QuestSessionContainer(QuestSession session) {
        this.session = session;
        conversationComposer = new ConversationComposer(session);
        initialize();
        initializeComponents();
    }

    public ConversationComposer conversationComposer;

    private void initialize() {
        setPreferredSize(Application.instance.getPreferredSize());
        setForeground(Color.BLACK);
        setOpaque(true);
    }


    private void initializeComponents() {

        conversationComposer.setOpaque(true);
        conversationComposer.setPreferredSize(new Dimension(9999, 9999));
        conversationComposer.setLocation(0, 0);



        conversationComposerPanel = new DragZoomPanel(conversationComposer);

        conversationComposerPanel.configureComponent();
        conversationComposerPanel.setPreferredSize(getPreferredSize());

        JPanel conversationComposerHolder = createConversationComposerHolder();


        addTab("Konwersacja", conversationComposerHolder);
        setMnemonicAt(0, KeyEvent.VK_K);

        instructionsPanel = new InstructionsPanel();

        addTab("Instrukcje", instructionsPanel);
        setMnemonicAt(1, KeyEvent.VK_I);

        addChangeListener(e -> {
            //If instructions panel got selected
            if (getSelectedIndex() == 1) {
                instructionsPanel.instructionNameField.requestFocus();
            }
            else instructionsPanel.exitEdit();
        });

        addTab("Dziennik", journalEntriesContainer = new JournalEntriesContainer());
        setMnemonicAt(2, KeyEvent.VK_D);

        addTab("Przedmioty", itemEditorTab = new ItemEditorTab());
        setMnemonicAt(3, KeyEvent.VK_P);

    }

    private JPanel createConversationComposerHolder() {
        JPanel conversationComposerHolder = new JPanel();
        conversationComposerHolder.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 0.95;
        conversationComposerHolder.add(conversationComposerPanel, constraints);
        createInfoBar(constraints, conversationComposerHolder);
        return conversationComposerHolder;
    }


    private void createInfoBar(GridBagConstraints constraints, JPanel conversationComposerHolder) {
        constraints.gridy = 1;
        constraints.weighty = 0;
        composerInfoBar = new ComposerInfoBar(this);
        composerInfoBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        composerInfoBar.setBackground(conversationComposer.getBackground());
        composerInfoBar.setPreferredSize(new Dimension(9999, 5));
        composerInfoBar.setMaximumSize(composerInfoBar.getPreferredSize());
        conversationComposerHolder.add(composerInfoBar, constraints);
    }


    public void attachJournalEntriesContainer(JournalEntriesContainer journalEntriesContainer) {
        this.journalEntriesContainer.cloneFrom(journalEntriesContainer);
    }
}
