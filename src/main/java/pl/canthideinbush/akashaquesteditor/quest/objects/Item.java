package pl.canthideinbush.akashaquesteditor.quest.objects;

import org.betonquest.betonquest.item.typehandler.PotionHandler;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import pl.canthideinbush.akashaquesteditor.app.Application;

import java.util.*;

public class Item implements QuestObject, ConfigurationSerializable {


    private String name;

    public Material material = Material.AIR;
    public ArrayList<String> lore = new ArrayList<>();
    public LinkedHashMap<String, String> enchantments = new LinkedHashMap<>();

    public Unbreakable unbreakable = null;


    /**
     * customModelData == -1 == null
     * customModelData < -1 == no-custom-model-data
     */
    public int customModelData = -1;

    public ArrayList<String> flags = new ArrayList<>();

    //============== Special Item Types ==============

    public String book_title;
    public String book_author;
    public String book_text;

    public PotionType potion_type;
    public PotionExtended potion_extended;
    public PotionUpgraded potion_upgraded;
    public ArrayList<InstructionPotionEffectType> potion_effectTypes = new ArrayList<>();

    public String head_owner;
    public String head_owner_uuid;
    public String head_base64_texture;

    public String leather_armor_color;

    public ArrayList<InstructionFireworkEffect> firework_effects = new ArrayList<>();
    public String firework_power;

    public boolean isQuestItem = false;


    public Item(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String generateInstruction() {
        StringBuilder instructionBuilder = new StringBuilder();

        instructionBuilder.append(material);

        if (isQuestItem) {
            lore.add(Application.instance.settings.getQuestItemNote());
        }
        if (!lore.isEmpty()) {
            instructionBuilder.append(" lore:");
            Iterator<String> loreIterator = lore.iterator();
            while (loreIterator.hasNext()) {
                String line = loreIterator.next();
                instructionBuilder.append(line.replaceAll(" ", "_"));
                if (loreIterator.hasNext()) instructionBuilder.append(";");
            }
        }

        if (!enchantments.isEmpty()) {
            instructionBuilder.append(" enchants:");
            Iterator<Map.Entry<String, String>> enchantmentIterator = enchantments.entrySet().iterator();
            while (enchantmentIterator.hasNext()) {
                Map.Entry<String, String> enchantment = enchantmentIterator.next();
                instructionBuilder.append(enchantment.getKey()).append(":").append(enchantment.getValue());
                if (enchantmentIterator.hasNext()) instructionBuilder.append(",");
            }
        }

        if (unbreakable != null) {
            instructionBuilder.append(" ").append(unbreakable.value);
        }

        if (customModelData != -1) {
            instructionBuilder.append(" ");
            if (customModelData > -1) {
                instructionBuilder.append("custom-model-data:").append(customModelData);
            }
            else instructionBuilder.append("no-custom-model-data");
        }

        if (!flags.isEmpty()) {
            instructionBuilder.append(" flags:");
            Iterator<String> flagsIterator = flags.iterator();
            while (flagsIterator.hasNext()) {
                String flag = flagsIterator.next();
                instructionBuilder.append(flag);
                if (flagsIterator.hasNext()) {
                    instructionBuilder.append(",");
                }
            }
        }

        if (book_title != null) {
            instructionBuilder.append(" title:").append(book_title);
        }
        if (book_author != null) {
            instructionBuilder.append(" author:").append(book_author);
        }
        if (book_text != null) {
            instructionBuilder.append(" text:").append(book_text);
        }

        if (potion_type != null) {
            instructionBuilder.append(" type:").append(potion_type.name());
        }
        if (potion_extended != null) {
            instructionBuilder.append(" extended:").append(potion_extended.value);
        }
        if (potion_upgraded != null) {
            instructionBuilder.append(" upgraded:").append(potion_upgraded.value);
        }
        boolean effectContaining = false;
        if (!potion_effectTypes.isEmpty()) {
            instructionBuilder.append(" effects:");
            Iterator<InstructionPotionEffectType> effectTypeIterator = potion_effectTypes.iterator();
            while (effectTypeIterator.hasNext()) {
                InstructionPotionEffectType effectType = effectTypeIterator.next();
                if (effectType.negated) {
                    instructionBuilder.append("none-");
                    effectContaining = true;
                }
                instructionBuilder.append(effectType.effectType.getName()).append(":").append(effectType.power).append(":").append(effectType.duration);
                if (effectTypeIterator.hasNext()) instructionBuilder.append(",");
            }
        }
        if (effectContaining) {
            instructionBuilder.append(" effects-containing");
        }

        if (head_owner != null) {
            instructionBuilder.append(" owner:").append(head_owner);
        }
        if (head_owner_uuid != null) {
            instructionBuilder.append(" player-id:").append(head_owner_uuid);
        }
        if (head_base64_texture != null) {
            instructionBuilder.append(" texture:").append(head_base64_texture);
        }

        if (leather_armor_color != null) {
            instructionBuilder.append(" color:").append(leather_armor_color);
        }

        if (!firework_effects.isEmpty()) {
            instructionBuilder.append(" firework:");
            Iterator<InstructionFireworkEffect> fireworkEffectIterator = firework_effects.iterator();
            while (fireworkEffectIterator.hasNext()) {
                InstructionFireworkEffect effect = fireworkEffectIterator.next();
                instructionBuilder.append(effect.effect).append(":");
                if (effect.colors.isEmpty()) {
                    instructionBuilder.append("none:");
                }
                else {
                    Iterator<String> colorsIterator = effect.colors.iterator();
                    while (colorsIterator.hasNext()) {
                        String color = colorsIterator.next();
                        instructionBuilder.append(color);
                        if (colorsIterator.hasNext())
                            instructionBuilder.append(";");
                    }
                    instructionBuilder.append(":");
                }
                if (effect.fadeColors.isEmpty()) {
                    instructionBuilder.append("none:");
                }
                else {
                    Iterator<String> colorsIterator = effect.colors.iterator();
                    while (colorsIterator.hasNext()) {
                        String color = colorsIterator.next();
                        instructionBuilder.append(color);
                        if (colorsIterator.hasNext())
                            instructionBuilder.append(";");
                    }
                    instructionBuilder.append(":");
                }

                instructionBuilder.append(effect.trailEffect).append(":").append(effect.flickerEffect);


                if (fireworkEffectIterator.hasNext()) instructionBuilder.append(",");
            }
        }

        if (firework_power != null) instructionBuilder.append(" power:").append(firework_power);




        return instructionBuilder.toString();
    }

    public void save(ConfigurationSection itemsSection) {
        itemsSection.set(name, generateInstruction());
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return null;
    }

    public static Item deserialize(Map<String, Object> data) {
        Item item = new Item((String) data.get("name"));

        return item;
    }

    public enum Unbreakable {
        TRUE("unbreakable:true"),
        FALSE_CHECK("unbreakable:false");

        public final String value;

        Unbreakable(String value) {
            this.value = value;
        }

    }


    public enum PotionExtended {
        TRUE("extended:true"),
        FALSE_CHECK("extended:false");

        public final String value;

        PotionExtended(String value) {
            this.value = value;
        }
    }
    public enum PotionUpgraded {
        TRUE("upgraded:true"),
        FALSE_CHECK("upgraded:false");

        public final String value;

        PotionUpgraded(String value) {
            this.value = value;
        }
    }

    public static class InstructionPotionEffectType {
        boolean negated = false;
        public PotionEffectType effectType = PotionEffectType.SPEED;
        public String duration = "0";
        public String power = "0";
    }

    public static class InstructionFireworkEffect {
        /**
         * FireworkEffect.Type
         */
        public String effect;
        public ArrayList<String> colors = new ArrayList<>();

        public ArrayList<String> fadeColors = new ArrayList<>();

        public String trailEffect = "?";

        public String flickerEffect = "?";
    }
}
