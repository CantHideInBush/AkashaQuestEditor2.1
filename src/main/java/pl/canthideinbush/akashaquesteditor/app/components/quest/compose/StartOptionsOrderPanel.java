package pl.canthideinbush.akashaquesteditor.app.components.quest.compose;

import pl.canthideinbush.akashaquesteditor.app.Application;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.UUID;

public class StartOptionsOrderPanel extends JFrame {

    private final GridBagConstraints constraints;
    private JTable jTable;

    public StartOptionsOrderPanel() {
        super("Kolejność wypowiedzi");
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        initialize();
        initializeComponents();
        pack();
        setVisible(true);
    }


    private void initialize() {
        Application.setAkashaIcon(this);
        setPreferredSize(new Dimension(500, 800));
        setLocationByPlatform(true);
    }

    private void initializeComponents() {
        Object[][] objects = new Object[Application.instance.sessionContainer.session.activeConversation.startOptions.size()][1];
        int i = 0;
        for (String option : Application.instance.sessionContainer.session.activeConversation.startOptions) {
            objects[i][0] = uuidToName(option);
            i++;
        }
        jTable = new JTable(new DefaultTableModel(objects, new String[]{"Kolejność wypowiedzi"})){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable.setDropMode(DropMode.ON);
        jTable.setRowHeight(35);
        jTable.setTableHeader(null);
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        jTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setHorizontalAlignment(SwingConstants.CENTER);
                component.setFont(getFont().deriveFont(Font.BOLD, 17f));
                return component;
            }
        });

        jTable.addKeyListener(new KeyAdapter() {

            int selected;
            @Override
            public void keyPressed(KeyEvent e) {
                selected = jTable.getSelectedRow();
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    moveAndSwapOrder(tableModel, selected, -1);
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    moveAndSwapOrder(tableModel, selected, 1);
                }

            }
        });



        JLabel infoLabel = new JLabel("Użyj strzałek góra/dół aby dostosować kolejność");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(infoLabel.getFont().deriveFont(15f));
        infoLabel.setPreferredSize(new Dimension(500, 45));

        constraints.gridy = 0;
        constraints.weighty = 0;
        add(infoLabel, constraints);

        JScrollPane scrollPane = new JScrollPane(jTable);
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(scrollPane, constraints);
    }

    private void moveAndSwapOrder(DefaultTableModel tableModel, int row, int by) {
        if (row + by < 0 || row + by > tableModel.getRowCount() - 1) return;
        tableModel.moveRow(row, row, row + by);
        jTable.getSelectionModel().setSelectionInterval(row + by, row + by);
        List<String> startOptionUUIDs = Application.instance.sessionContainer.session.activeConversation.startOptions;
        String oldUUID = startOptionUUIDs.get(row);
        startOptionUUIDs.set(row, startOptionUUIDs.get(row + by));
        startOptionUUIDs.set(row + by, oldUUID);
    }


    private static String uuidToName(String uuid) {
        return Application.instance.sessionContainer.conversationComposer.getConversationBlockByUUID(UUID.fromString(uuid)).getName();
    }


}
