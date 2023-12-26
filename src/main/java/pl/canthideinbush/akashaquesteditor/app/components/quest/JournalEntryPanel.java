package pl.canthideinbush.akashaquesteditor.app.components.quest;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import pl.canthideinbush.akashaquesteditor.app.Application;
import pl.canthideinbush.akashaquesteditor.app.components.Popups;
import pl.canthideinbush.akashaquesteditor.app.components.QuestComponent;
import pl.canthideinbush.akashaquesteditor.app.components.TextPane;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.ResizeAnimationContainer;
import pl.canthideinbush.akashaquesteditor.app.dynamic.animations.components.ResizableIcon;
import pl.canthideinbush.akashaquesteditor.quest.objects.JournalEntry;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JournalEntryPanel extends JPanel implements QuestComponent<JournalEntry>, ConfigurationSerializable {


    static Image bookBackground;

    static {
        try {
            bookBackground = ImageIO.read(JournalEntry.class.getResource("/assets/book.png"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Font minecraftFont;
    static {
        String path = "/assets/fonts/MinecraftRegular-Bmg3.otf";
        InputStream inputStream = JournalEntryPanel.class.getResourceAsStream(path);
        File fontFile = new File("." + path);
        try {
            fontFile.getParentFile().mkdirs();
            fontFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(fontFile);
            assert inputStream != null;
            fileOutputStream.write(inputStream.readAllBytes());
            minecraftFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(minecraftFont);
        } catch (
                IOException |
                FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public TextPane textPane = null;

    public JournalEntryPanel(String name, String text) {
        setName(name);
        setOpaque(false);
        setPreferredSize(new Dimension(300, 350));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        initializeComponents(constraints);
        textPane.setText(text);
    }

    public static JournalEntryPanel deserialize(Map<String, Object> data) {
        return new JournalEntryPanel((String) data.get("name"), (String) data.get("text"));
    }

    private void initializeComponents(GridBagConstraints constraints) {
        JLabel nameLabel = new JLabel(getName());
        nameLabel.setFont(minecraftFont.deriveFont(Font.BOLD, 20f));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 0.4;
        constraints.fill = GridBagConstraints.BOTH;

        nameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String newName = Popups.createShortTextPopup("Edycja dziennika", "Nowa nazwa wpisu", getName());
                    if (!"".equalsIgnoreCase(newName) && newName != null) {
                        setName(newName);
                        nameLabel.setText(newName);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nameLabel.setForeground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nameLabel.setForeground(Color.BLACK);
            }
        });

        add(nameLabel, constraints);


        JLabel removeContainer = new JLabel(new ImageIcon(Popups.close.getImage().getScaledInstance(32, 32, Image.SCALE_REPLICATE)));


        JournalEntryPanel instance = this;
        removeContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(Application.instance, "Czy na pewno chcesz usunąć wpis " + getName() + "?", "Usuwanie wpisu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null);
                if (result == 0) {
                    Application.instance.sessionContainer.journalEntriesContainer.removeJournalEntry(instance);
                }
            }
        });

        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;

        removeContainer.setBorder(new EmptyBorder(10, 0, 0, 19));
        add(removeContainer, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;

        textPane = new TextPane();
        textPane.setDefaultFont(minecraftFont.deriveFont(20f));
        textPane.setOpaque(false);
        textPane.setBorder(new EmptyBorder(0, 32, 32, 20));
        textPane.setEditable(false);

        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Popups.createLongTextPopup("Nowa treść wpisu", textPane.getText(), newText -> {
                        if (newText != null) {
                            textPane.setText(newText);
                        }
                    });
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                textPane.setForeground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                textPane.setForeground(Color.BLACK);
            }
        });

        constraints.gridy = 2;
        constraints.weighty = 0.4;
        textPane.setPreferredSize(new Dimension(0, 225));
        textPane.setMaximumSize(textPane.getPreferredSize());
        add(textPane, constraints);
        JPanel fillerPanel = new JPanel();
        fillerPanel.setOpaque(false);
        constraints.gridy = 3;
        constraints.weighty = 0.2;
        add(fillerPanel, constraints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D) g);
        g2d.drawImage(bookBackground.getScaledInstance(getWidth(), getHeight(), Image.SCALE_REPLICATE), 0, 0, this);
    }

    @Override
    public JournalEntry create() {

        return new JournalEntry(getName(), textPane.getText().replaceAll("\\r", ""));
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", getName());
        data.put("text", textPane.getText());

        return data;
    }
}