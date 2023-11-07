package pl.canthideinbush.akashaquesteditor.quest.objects;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConversationOption implements QuestObject {

    public ConversationOption(ConfigurationSection section) {
        this.name = section.getName();
        this.textRaw = section.getString("text");
        this.conditionsRaw = section.getString("conditions");
        this.eventsRaw = section.getString("events", "");
        this.pointerRaw = section.getString("pointer");

        conditions = rawToList(conditionsRaw);
        events = rawToList(eventsRaw);
        pointers = rawToList(pointerRaw);

    }

    private String name = "";
    private String textRaw = "";
    private String conditionsRaw = "";
    private String eventsRaw = "";
    private String pointerRaw = "";

    private List<String> conditions = new ArrayList<>();
    private List<String> events = new ArrayList<>();
    private List<String> pointers = new ArrayList<>();


    public ConversationOption(String name) {
        this.name = name;
    }

    public void setTextRaw(String textRaw) {
        this.textRaw = textRaw;
    }

    public void setConditionsRaw(String conditionsRaw) {
        this.conditionsRaw = conditionsRaw;
    }

    public void setEventsRaw(String eventsRaw) {
        this.eventsRaw = eventsRaw;
    }

    public void setPointerRaw(String pointerRaw) {
        this.pointerRaw = pointerRaw;
    }
    
    public void addCondition(String condition) {
        conditions.add(condition);
        conditionsRaw = listToRaw(conditions);
    }
    public void removeCondition(String condition) {
        conditions.remove(condition);
        conditionsRaw = listToRaw(conditions);
    }    
    
    public void addPointer(String pointer) {
        pointers.add(pointer);
        pointerRaw = listToRaw(pointers);
    }
    public void removePointer(String pointer) {
        pointers.remove(pointer);
        pointerRaw = listToRaw(pointers);
    }    
    
    public void addEvent(String event) {
        events.add(event);
        eventsRaw = listToRaw(events);
    }
    public void removeEvent(String event) {
        events.remove(event);
        eventsRaw = listToRaw(events);
    }

    public void save(ConfigurationSection npcOptionsSection) {
        ConfigurationSection optionSection = npcOptionsSection.createSection(name);
        if (!textRaw.equals("")) optionSection.set("text", textRaw);
        if (!conditionsRaw.equals("")) optionSection.set("conditions", conditionsRaw);
        if (!eventsRaw.equals("")) optionSection.set("events", eventsRaw);
        if (!pointerRaw.equals("")) optionSection.set("pointer", pointerRaw);
    }

    private List<String> rawToList(String raw) {
        return Arrays.stream(raw.split(", ")).toList();
    }

    private String listToRaw(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s).append(", ");
        }
        return builder.length() > 0 ? builder.substring(0, builder.length() - 2) : "";
    }
}
