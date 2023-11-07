package pl.canthideinbush.akashaquesteditor.quest.objects;

import org.bukkit.configuration.ConfigurationSection;

public class JournalEntry implements QuestObject {

    private String name;
    private String text = "";

    public JournalEntry(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void save(ConfigurationSection journalSection) {
        journalSection.set(name, text);
    }
}
