package pl.canthideinbush.akashaquesteditor.app;

import pl.canthideinbush.akashaquesteditor.app.components.quest.InstructionsPanel;
import pl.canthideinbush.akashaquesteditor.app.components.quest.compose.ComposerInfoBar;
import pl.canthideinbush.akashaquesteditor.app.components.quest.compose.ConversationComposer;
import pl.canthideinbush.akashaquesteditor.app.components.quest.compose.DragZoomPanel;
import pl.canthideinbush.akashaquesteditor.quest.session.QuestSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class QuestSessionContainer extends JTabbedPane {

    public final QuestSession session;
    public DragZoomPanel conversationComposerPanel;
    public ComposerInfoBar composerInfoBar;
    public InstructionsPanel instructionsPanel;

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

        instructionsPanel = new InstructionsPanel();

        addTab("Instrukcje", instructionsPanel);
        addChangeListener(e -> {
            if (getSelectedIndex() == 1) {
                instructionsPanel.fixAnimation();
            }
        });


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


}
