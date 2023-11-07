package pl.canthideinbush.akashaquesteditor.quest.objects;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class Conversation {

    public Conversation(ConfigurationSection section) {
        this.name = section.getName();
        this.quester = section.getString("quester");
        this.rawFirst = section.getString("first");
        ConfigurationSection npcOptionsSection = section.getConfigurationSection("NPC_options");
        ConfigurationSection playerOptionsSection = section.getConfigurationSection("player_options");

        assert npcOptionsSection != null;
        for (String key : npcOptionsSection.getKeys(false)) {
            npcOptions.add(new ConversationOption(npcOptionsSection.getConfigurationSection(key)));
        }
        assert playerOptionsSection != null;
        for (String key : playerOptionsSection.getKeys(false)) {
            playerOptions.add(new ConversationOption(npcOptionsSection.getConfigurationSection(key)));
        }

    }

    public Conversation(String name) {
        this.name = name;
    }


    public String name;
    private String quester = "";
    private String rawFirst = "";

    public void addFirst(String first) {
        if (rawFirst.equals("")) rawFirst = first;
        else rawFirst = rawFirst + "," + first;
    }


    private ArrayList<ConversationOption> npcOptions = new ArrayList<>();
    private ArrayList<ConversationOption> playerOptions = new ArrayList<>();

    public void addNPCOption(ConversationOption option) {
        npcOptions.add(option);
    }

    public void addPlayerOption(ConversationOption option) {
        playerOptions.add(option);
    }


    public void setRawFirst(String rawFirst) {
        this.rawFirst = rawFirst;
    }


    public void setQuester(String quester) {
        this.quester = quester;
    }

    public void save(ConfigurationSection conversationsSection) {
        ConfigurationSection localSection = conversationsSection.createSection(name);
        localSection.set("quester", quester);
        localSection.set("first", rawFirst);
        ConfigurationSection npcOptionsSection = localSection.createSection("NPC_options");
        ConfigurationSection playerOptionsSection = localSection.createSection("player_options");
        npcOptions.forEach(conversationOption -> conversationOption.save(npcOptionsSection));
        playerOptions.forEach(conversationOption -> conversationOption.save(playerOptionsSection));
    }

}
