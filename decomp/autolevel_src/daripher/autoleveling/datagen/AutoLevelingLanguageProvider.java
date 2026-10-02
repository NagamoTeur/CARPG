package daripher.autoleveling.datagen;

import daripher.autoleveling.init.AutoLevelingItems;
import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public class AutoLevelingLanguageProvider extends LanguageProvider {
   public AutoLevelingLanguageProvider(DataGenerator gen) {
      super(gen, "autoleveling", "en_us");
   }

   protected void addTranslations() {
      this.add("itemGroup.autoleveling", "Auto Leveling Tools");
      this.add((Item)AutoLevelingItems.BLACKLIST_TOOL.get(), "Blacklist Tool");
      this.specialText((Item)AutoLevelingItems.BLACKLIST_TOOL.get(), "tooltip", ChatFormatting.YELLOW + "Adds or removes entity from blacklist");
      this.specialText((Item)AutoLevelingItems.BLACKLIST_TOOL.get(), "removed", "%s was removed from blacklist");
      this.specialText((Item)AutoLevelingItems.BLACKLIST_TOOL.get(), "added", "%s was added to blacklist");
      this.add((Item)AutoLevelingItems.WHITELIST_TOOL.get(), "Whitelist Tool");
      this.specialText((Item)AutoLevelingItems.WHITELIST_TOOL.get(), "tooltip", ChatFormatting.YELLOW + "Adds or removes entity from whitelist");
      this.specialText((Item)AutoLevelingItems.WHITELIST_TOOL.get(), "removed", "%s was removed from whitelist");
      this.specialText((Item)AutoLevelingItems.WHITELIST_TOOL.get(), "added", "%s was added to whitelist");
      this.add("jade.autoleveling.tooltip", "Level: %d");
      this.add("config.jade.plugin_autoleveling.level", "Level");
      this.add("autoleveling.level", "Lv.%s");
   }

   public void specialText(Item key, String type, String translation) {
      this.add(key.m_5524_() + "." + type, translation);
   }
}
