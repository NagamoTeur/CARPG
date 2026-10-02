package daripher.skilltree.data.generation.translation;

import daripher.skilltree.init.PSTRegistries;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.damage.DamageCondition;
import daripher.skilltree.skill.bonus.condition.enchantment.EnchantmentCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import daripher.skilltree.skill.bonus.condition.living.LivingCondition;
import daripher.skilltree.skill.bonus.event.SkillEventListener;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.multiplier.LivingMultiplier;
import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class PSTTranslationProvider extends LanguageProvider {
   public PSTTranslationProvider(DataGenerator gen, String modid, String locale) {
      super(gen, modid, locale);
   }

   protected void addTooltip(Item item, String tooltip) {
      this.add(item.m_5524_() + ".tooltip", tooltip);
   }

   protected void addWarning(Item item, String tooltip) {
      this.add(item.m_5524_() + ".warning", tooltip);
   }

   protected void add(Attribute attribute, String name) {
      this.add(attribute.m_22087_(), name);
   }

   protected void addSkill(String skillName, String name, @Nullable String description) {
      String skillId = "skill.%s.%s".formatted("skilltree", skillName);
      this.add(skillId + ".name", name);
      if (description != null) {
         this.add(skillId + ".description", description);
      }
   }

   protected void addSkill(String skillName, String name) {
      this.addSkill(skillName, name, null);
   }

   protected void addSkillBranch(String skillName, String name, int from, int to) {
      for (int i = from; i <= to; i++) {
         this.addSkill("%s_%d".formatted(skillName, i), name, null);
      }
   }

   protected void addMixture(String name, String potionType, MobEffect... effects) {
      StringBuilder potionName = new StringBuilder("item.minecraft." + potionType + ".mixture");
      Arrays.stream(effects).<String>map(MobEffect::m_19481_).map(id -> id.replaceAll("effect.", "")).forEach(id -> potionName.append(".").append(id));
      this.add(potionName.toString(), name);
   }

   protected void add(LivingCondition.Serializer condition, String value) {
      ResourceLocation id = PSTRegistries.LIVING_CONDITIONS.get().getKey(condition);

      assert id != null;

      String key = "living_condition.%s.%s".formatted(id.m_135827_(), id.m_135815_());
      this.add(key, value);
   }

   protected void add(LivingCondition.Serializer condition, String type, String value) {
      ResourceLocation id = PSTRegistries.LIVING_CONDITIONS.get().getKey(condition);

      assert id != null;

      String key = "living_condition.%s.%s.%s".formatted(id.m_135827_(), id.m_135815_(), type);
      this.add(key, value);
   }

   protected void add(SkillEventListener.Serializer condition, String value) {
      ResourceLocation id = PSTRegistries.EVENT_LISTENERS.get().getKey(condition);

      assert id != null;

      String key = "event_listener.%s.%s".formatted(id.m_135827_(), id.m_135815_());
      this.add(key, value);
   }

   protected void add(SkillEventListener.Serializer condition, String type, String value) {
      ResourceLocation id = PSTRegistries.EVENT_LISTENERS.get().getKey(condition);

      assert id != null;

      String key = "event_listener.%s.%s.%s".formatted(id.m_135827_(), id.m_135815_(), type);
      this.add(key, value);
   }

   protected void add(DamageCondition.Serializer condition, String type, String value) {
      ResourceLocation id = PSTRegistries.DAMAGE_CONDITIONS.get().getKey(condition);

      assert id != null;

      String key = "damage_condition.%s.%s.%s".formatted(id.m_135827_(), id.m_135815_(), type);
      this.add(key, value);
   }

   protected void add(DamageCondition.Serializer condition, String value) {
      ResourceLocation id = PSTRegistries.DAMAGE_CONDITIONS.get().getKey(condition);

      assert id != null;

      String key = "damage_condition.%s.%s".formatted(id.m_135827_(), id.m_135815_());
      this.add(key, value);
   }

   protected void add(LivingMultiplier.Serializer multiplier, String value) {
      ResourceLocation id = PSTRegistries.LIVING_MULTIPLIERS.get().getKey(multiplier);

      assert id != null;

      String key = "skill_bonus_multiplier.%s.%s".formatted(id.m_135827_(), id.m_135815_());
      this.add(key, value);
   }

   protected void add(LivingMultiplier.Serializer multiplier, String type, String value) {
      ResourceLocation id = PSTRegistries.LIVING_MULTIPLIERS.get().getKey(multiplier);

      assert id != null;

      String key = "skill_bonus_multiplier.%s.%s.%s".formatted(id.m_135827_(), id.m_135815_(), type);
      this.add(key, value);
   }

   protected void add(SkillBonus.Serializer serializer, String value) {
      ResourceLocation id = PSTRegistries.SKILL_BONUSES.get().getKey(serializer);

      assert id != null;

      String key = "skill_bonus.%s.%s".formatted(id.m_135827_(), id.m_135815_());
      this.add(key, value);
   }

   protected void add(SkillBonus.Serializer serializer, String type, String value) {
      ResourceLocation id = PSTRegistries.SKILL_BONUSES.get().getKey(serializer);

      assert id != null;

      String key = "skill_bonus.%s.%s.%s".formatted(id.m_135827_(), id.m_135815_(), type);
      this.add(key, value);
   }

   protected void add(ItemBonus.Serializer serializer, String value) {
      ResourceLocation id = PSTRegistries.ITEM_BONUSES.get().getKey(serializer);

      assert id != null;

      String key = "item_bonus.%s.%s".formatted(id.m_135827_(), id.m_135815_());
      this.add(key, value);
   }

   protected void add(ItemCondition.Serializer serializer, String type, String value) {
      ResourceLocation id = PSTRegistries.ITEM_CONDITIONS.get().getKey(serializer);

      assert id != null;

      String key = "item_condition.%s.%s.%s".formatted(id.m_135827_(), id.m_135815_(), type);
      this.add(key, value);
   }

   protected void add(ItemCondition.Serializer serializer, String value) {
      ResourceLocation id = PSTRegistries.ITEM_CONDITIONS.get().getKey(serializer);

      assert id != null;

      String key = "item_condition.%s.%s".formatted(id.m_135827_(), id.m_135815_());
      this.add(key, value);
   }

   protected void add(TagKey<Item> itemTag, String value) {
      ResourceLocation id = itemTag.f_203868_();
      String key = "item_tag.%s".formatted(id.toString());
      this.add(key, value);
   }

   protected void add(TagKey<Item> itemTag, String type, String value) {
      ResourceLocation id = itemTag.f_203868_();
      String key = "item_tag.%s.%s".formatted(id.toString(), type);
      this.add(key, value);
   }

   protected void add(EnchantmentCondition.Serializer serializer, String value) {
      ResourceLocation id = PSTRegistries.ENCHANTMENT_CONDITIONS.get().getKey(serializer);

      assert id != null;

      String key = "enchantment_condition.%s.%s".formatted(id.m_135827_(), id.m_135815_());
      this.add(key, value);
   }

   protected void addGem(String type, String name, String... qualities) {
      for (int i = 0; i < qualities.length; i++) {
         this.add("item.skilltree.gem.skilltree." + type + "_" + i, qualities[i] + " " + name);
         this.add("item.apotheosis.gem.skilltree:" + type + "_" + i, name);
      }
   }
}
