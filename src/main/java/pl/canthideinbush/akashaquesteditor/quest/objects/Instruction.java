package pl.canthideinbush.akashaquesteditor.quest.objects;

import org.bukkit.configuration.ConfigurationSection;

public class Instruction implements QuestObject {

    private String name;
    private String instruction = "";




    public Instruction(String name) {
        this.name = name;
    }

    public Instruction(String name, String instruction) {
        this.name = name;
        this.instruction = instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void save(ConfigurationSection conditionsSection) {
        conditionsSection.set(name, instruction);
    }
}
