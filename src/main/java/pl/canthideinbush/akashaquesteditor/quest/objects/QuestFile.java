package pl.canthideinbush.akashaquesteditor.quest.objects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Collection;

public class QuestFile {


    private String name;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Conversation> conversations = new ArrayList<>();
    private ArrayList<Instruction> events = new ArrayList<>();
    private ArrayList<Instruction> conditions = new ArrayList<>();
    private ArrayList<Instruction> objectives = new ArrayList<>();
    private ArrayList<JournalEntry> journalEntries = new ArrayList<>();

    public QuestFile(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addEvent(Instruction event) {
        events.add(event);
    }

    public void addEvents(Collection<Instruction> events) {
        this.events.addAll(events);
    }

    public void addCondition(Instruction condition) {
        conditions.add(condition);
    }

    public void addConditions(Collection<Instruction> conditions) {
        this.conditions.addAll(conditions);
    }



    public void addObjective(Instruction objective) {
        objectives.add(objective);
    }

    public void addObjectives(Collection<Instruction> objectives) {
        this.objectives.addAll(objectives);
    }


    public void addJournalEntry(JournalEntry entry) {
        journalEntries.add(entry);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void removeEvent(Instruction event) {
        events.remove(event);
    }

    public void removeCondition(Instruction condition) {
        conditions.remove(condition);
    }

    public void removeObjective(Instruction objective) {
        objectives.remove(objective);
    }

    public void removeJournalEntry(JournalEntry entry) {
        journalEntries.remove(entry);
    }

    public void addConversation(Conversation conversation) {
        conversations.add(conversation);
    }

    public void save(YamlConfiguration configuration) {
        ConfigurationSection conversationsSection = configuration.createSection("conversations");
        this.conversations.forEach(item -> item.save(conversationsSection));
        ConfigurationSection itemsSection = configuration.createSection("items");
        this.items.forEach(item -> item.save(itemsSection));
        ConfigurationSection eventsSection = configuration.createSection("events");
        this.events.forEach(item -> item.save(eventsSection));
        ConfigurationSection conditionsSection = configuration.createSection("conditions");
        this.conditions.forEach(item -> item.save(conditionsSection));
        ConfigurationSection objectivesSection = configuration.createSection("objectives");
        this.objectives.forEach(item -> item.save(objectivesSection));
        ConfigurationSection journalSection = configuration.createSection("journal");
        this.journalEntries.forEach(item -> item.save(journalSection));
        configuration.getKeys(false).forEach(key -> {
            ConfigurationSection s;
            if ((s = configuration.getConfigurationSection(key)) != null) {
                if (s.getKeys(false).isEmpty()) configuration.set(key, null);
            }
        });
    }




}
