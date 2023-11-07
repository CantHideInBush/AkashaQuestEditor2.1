package pl.canthideinbush.akashaquesteditor.quest.objects;

import org.bukkit.configuration.ConfigurationSection;

public class Item implements QuestObject {


    private String name;
    private String itemRaw = "";
    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getItemRaw() {
        return itemRaw;
    }

    public void setItemRaw(String itemRaw) {
        this.itemRaw = itemRaw;
    }

    public void save(ConfigurationSection itemsSection) {
        itemsSection.set(name, itemRaw);
    }
}
