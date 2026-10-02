package daripher.skilltree.data.generation.skills;

import com.google.gson.JsonElement;
import daripher.skilltree.data.reloader.SkillsReloader;
import daripher.skilltree.init.PSTAttributes;
import daripher.skilltree.init.PSTEffects;
import daripher.skilltree.init.PSTTags;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.damage.MeleeDamageCondition;
import daripher.skilltree.skill.bonus.condition.damage.ProjectileDamageCondition;
import daripher.skilltree.skill.bonus.condition.enchantment.ArmorEnchantmentCondition;
import daripher.skilltree.skill.bonus.condition.enchantment.WeaponEnchantmentCondition;
import daripher.skilltree.skill.bonus.condition.item.EnchantedCondition;
import daripher.skilltree.skill.bonus.condition.item.EquipmentCondition;
import daripher.skilltree.skill.bonus.condition.item.FoodCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemTagCondition;
import daripher.skilltree.skill.bonus.condition.item.NoneItemCondition;
import daripher.skilltree.skill.bonus.condition.item.PotionCondition;
import daripher.skilltree.skill.bonus.condition.living.AttributeValueCondition;
import daripher.skilltree.skill.bonus.condition.living.BurningCondition;
import daripher.skilltree.skill.bonus.condition.living.EffectAmountCondition;
import daripher.skilltree.skill.bonus.condition.living.FishingCondition;
import daripher.skilltree.skill.bonus.condition.living.FoodLevelCondition;
import daripher.skilltree.skill.bonus.condition.living.HasEffectCondition;
import daripher.skilltree.skill.bonus.condition.living.HasGemsCondition;
import daripher.skilltree.skill.bonus.condition.living.HasItemEquippedCondition;
import daripher.skilltree.skill.bonus.condition.living.HasItemInHandCondition;
import daripher.skilltree.skill.bonus.condition.living.HealthPercentageCondition;
import daripher.skilltree.skill.bonus.event.AttackEventListener;
import daripher.skilltree.skill.bonus.event.BlockEventListener;
import daripher.skilltree.skill.bonus.event.ItemUsedEventListener;
import daripher.skilltree.skill.bonus.item.FoodEffectBonus;
import daripher.skilltree.skill.bonus.item.FoodHealingBonus;
import daripher.skilltree.skill.bonus.item.FoodSaturationBonus;
import daripher.skilltree.skill.bonus.item.ItemDurabilityBonus;
import daripher.skilltree.skill.bonus.item.ItemSkillBonus;
import daripher.skilltree.skill.bonus.item.ItemSocketsBonus;
import daripher.skilltree.skill.bonus.item.PotionAmplificationBonus;
import daripher.skilltree.skill.bonus.item.PotionDurationBonus;
import daripher.skilltree.skill.bonus.item.QuiverCapacityBonus;
import daripher.skilltree.skill.bonus.multiplier.AttributeValueMultiplier;
import daripher.skilltree.skill.bonus.multiplier.DistanceToTargetMultiplier;
import daripher.skilltree.skill.bonus.multiplier.EffectAmountMultiplier;
import daripher.skilltree.skill.bonus.multiplier.EnchantLevelsAmountMultiplier;
import daripher.skilltree.skill.bonus.multiplier.EnchantsAmountMultiplier;
import daripher.skilltree.skill.bonus.multiplier.GemsAmountMultiplier;
import daripher.skilltree.skill.bonus.multiplier.HungerLevelMultiplier;
import daripher.skilltree.skill.bonus.player.ArrowRetrievalBonus;
import daripher.skilltree.skill.bonus.player.AttributeBonus;
import daripher.skilltree.skill.bonus.player.BlockBreakSpeedBonus;
import daripher.skilltree.skill.bonus.player.CraftedItemBonus;
import daripher.skilltree.skill.bonus.player.CritChanceBonus;
import daripher.skilltree.skill.bonus.player.CritDamageBonus;
import daripher.skilltree.skill.bonus.player.DamageBonus;
import daripher.skilltree.skill.bonus.player.EnchantmentAmplificationBonus;
import daripher.skilltree.skill.bonus.player.EnchantmentRequirementBonus;
import daripher.skilltree.skill.bonus.player.FreeEnchantmentBonus;
import daripher.skilltree.skill.bonus.player.GainedExperienceBonus;
import daripher.skilltree.skill.bonus.player.GemPowerBonus;
import daripher.skilltree.skill.bonus.player.HealingBonus;
import daripher.skilltree.skill.bonus.player.IgniteBonus;
import daripher.skilltree.skill.bonus.player.IncomingHealingBonus;
import daripher.skilltree.skill.bonus.player.JumpHeightBonus;
import daripher.skilltree.skill.bonus.player.LootDuplicationBonus;
import daripher.skilltree.skill.bonus.player.PlayerSocketsBonus;
import daripher.skilltree.skill.bonus.player.RecipeUnlockBonus;
import daripher.skilltree.skill.bonus.player.RepairEfficiencyBonus;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.common.CuriosHelper;

public class PSTSkillsProvider implements DataProvider {
   private final Map<ResourceLocation, PassiveSkill> skills = new HashMap<>();
   private final DataGenerator dataGenerator;
   private final String[] playerClasses = new String[]{"alchemist", "hunter", "enchanter", "cook", "blacksmith", "miner"};

   public PSTSkillsProvider(DataGenerator dataGenerator) {
      this.dataGenerator = dataGenerator;
   }

   private void addSkills() {
      Arrays.stream(this.playerClasses).forEach(this::addClassSkills);
      this.addGateways();
   }

   private void addClassSkills(String playerClass) {
      this.addSkill(playerClass, "class", "class", 24);
      this.addSkillBranch(playerClass, "defensive", "defensive_1", 16, 1, 8);
      this.addSkillBranch(playerClass, "offensive", "offensive_1", 16, 1, 8);
      this.addSkillBranch(playerClass, "defensive_crafting", "defensive_crafting_1", 16, 1, 7);
      this.addSkillBranch(playerClass, "offensive_crafting", "offensive_crafting_1", 16, 1, 7);
      this.addSkillBranch(playerClass, "crafting", "crafting_1", 16, 1, 3);
      this.addSkillBranch(playerClass, "life", "life_1", 16, 1, 2);
      this.addSkillBranch(playerClass, "speed", "speed_1", 16, 1, 2);
      this.addSkillBranch(playerClass, "healing", "healing_1", 16, 1, 4);
      this.addSkillBranch(playerClass, "lesser", "lesser_1", 16, 1, 6);
      this.addSkillBranch(playerClass, "crit", "crit_1", 16, 1, 2);
      this.addSkillBranch(playerClass, "subclass_1_defensive", "subclass_1_defensive_1", 16, 1, 4);
      this.addSkillBranch(playerClass, "subclass_2_defensive", "subclass_2_defensive_1", 16, 1, 4);
      this.addSkillBranch(playerClass, "subclass_1_crafting", "subclass_1_crafting_1", 16, 1, 5);
      this.addSkillBranch(playerClass, "subclass_1_offensive", "subclass_1_offensive_1", 16, 1, 4);
      this.addSkillBranch(playerClass, "subclass_2_crafting", "subclass_2_crafting_1", 16, 1, 5);
      this.addSkillBranch(playerClass, "subclass_2_life", "subclass_2_life_1", 16, 1, 4);
      this.addSkill(playerClass, "defensive_notable_1", "defensive_notable_1", 20);
      this.addSkill(playerClass, "offensive_notable_1", "offensive_notable_1", 20);
      this.addSkill(playerClass, "crafting_notable_1", "crafting_notable_1", 20);
      this.addSkill(playerClass, "life_notable_1", "life_notable_1", 20);
      this.addSkill(playerClass, "speed_notable_1", "speed_notable_1", 20);
      this.addSkill(playerClass, "healing_notable_1", "healing_notable_1", 20);
      this.addSkill(playerClass, "crit_notable_1", "crit_notable_1", 20);
      this.addSkill(playerClass, "subclass_special", "subclass_special_1", 20);
      this.addSkill(playerClass, "subclass_1_offensive_notable_1", "subclass_1_offensive_notable_1", 20);
      this.addSkill(playerClass, "subclass_1_crafting_notable_1", "subclass_1_crafting_notable_1", 20);
      this.addSkill(playerClass, "subclass_2_life_notable_1", "subclass_2_life_notable_1", 20);
      this.addSkill(playerClass, "subclass_2_crafting_notable_1", "subclass_2_crafting_notable_1", 20);
      this.addSkill(playerClass, "defensive_keystone_1", "defensive_keystone_1", 32);
      this.addSkill(playerClass, "offensive_keystone_1", "offensive_keystone_1", 32);
      this.addSkill(playerClass, "defensive_crafting_keystone_1", "defensive_crafting_keystone_1", 32);
      this.addSkill(playerClass, "offensive_crafting_keystone_1", "offensive_crafting_keystone_1", 32);
      this.addSkill(playerClass, "mastery", "mastery", 32);
      this.addSkill(playerClass, "subclass_1", "subclass_1", 24);
      this.addSkill(playerClass, "subclass_2", "subclass_2", 24);
      this.addSkill(playerClass, "subclass_1_mastery", "subclass_1_mastery", 32);
      this.addSkill(playerClass, "subclass_2_mastery", "subclass_2_mastery", 32);
   }

   protected void addGateways() {
      Arrays.stream(this.playerClasses).forEach(this::addGateway);
      this.addGatewayConnection("alchemist_gateway", "cook_gateway");
      this.addGatewayConnection("hunter_gateway", "blacksmith_gateway");
      this.addGatewayConnection("enchanter_gateway", "miner_gateway");
   }

   private void shapeSkillTree() {
      Arrays.stream(this.playerClasses).forEach(this::shapeClassTree);
      this.connectClassTrees();
   }

   private void shapeClassTree(String playerClass) {
      this.setSkillPosition(playerClass, null, 140.0F, 0.0F, "class");
      this.setSkillBranchPosition(playerClass, "class", 10, "defensive", 30.0F, 0.0F, 1, 4);
      this.setSkillBranchPosition(playerClass, "class", 10, "offensive", -30.0F, 0.0F, 1, 4);
      this.setSkillPosition(playerClass, "class", 10.0F, 120.0F, "defensive_crafting_1");
      this.setSkillPosition(playerClass, "class", 10.0F, -120.0F, "offensive_crafting_1");
      this.setSkillBranchPosition(playerClass, "defensive_crafting_1", 10, "defensive_crafting", 30.0F, 0.0F, 2, 4);
      this.setSkillBranchPosition(playerClass, "offensive_crafting_1", 10, "offensive_crafting", -30.0F, 0.0F, 2, 4);
      this.setSkillPosition(playerClass, "defensive_crafting_4", 12.0F, 30.0F, "defensive_notable_1");
      this.setSkillPosition(playerClass, "offensive_crafting_4", 12.0F, -30.0F, "offensive_notable_1");
      this.connectSkills(playerClass, "defensive_notable_1", "defensive_4");
      this.connectSkills(playerClass, "offensive_notable_1", "offensive_4");
      this.setSkillBranchPosition(playerClass, "class", 11, "crafting", 180.0F, 0.0F, 1, 3);
      this.setSkillPosition(playerClass, "crafting_3", 12.0F, 180.0F, "crafting_notable_1");
      this.setSkillPosition(playerClass, "defensive_4", 10.0F, -90.0F, "life_1");
      this.setSkillPosition(playerClass, "offensive_4", 10.0F, 90.0F, "life_2");
      this.setSkillPosition(playerClass, "life_2", 10.0F, 90.0F, "life_notable_1");
      this.connectSkills(playerClass, "life_1", "life_notable_1");
      this.setSkillBranchPosition(playerClass, "defensive_4", 10, "defensive", 0.0F, 0.0F, 5, 8);
      this.setSkillBranchPosition(playerClass, "offensive_4", 10, "offensive", 0.0F, 0.0F, 5, 8);
      this.setSkillPosition(playerClass, "defensive_5", 10.0F, -90.0F, "speed_1");
      this.setSkillPosition(playerClass, "offensive_5", 10.0F, 90.0F, "speed_2");
      this.setSkillPosition(playerClass, "speed_2", 10.0F, 90.0F, "speed_notable_1");
      this.connectSkills(playerClass, "speed_1", "speed_notable_1");
      this.setSkillPosition(playerClass, "defensive_8", 10.0F, 0.0F, "defensive_keystone_1");
      this.setSkillPosition(playerClass, "offensive_8", 10.0F, 0.0F, "offensive_keystone_1");
      this.setSkillPosition(playerClass, "defensive_notable_1", 8.0F, 30.0F, "defensive_crafting_5");
      this.setSkillPosition(playerClass, "offensive_notable_1", 8.0F, -30.0F, "offensive_crafting_5");
      this.setSkillBranchPosition(playerClass, "defensive_crafting_5", 10, "defensive_crafting", 0.0F, 0.0F, 6, 7);
      this.setSkillBranchPosition(playerClass, "offensive_crafting_5", 10, "offensive_crafting", 0.0F, 0.0F, 6, 7);
      this.setSkillPosition(playerClass, "defensive_crafting_7", 10.0F, 0.0F, "defensive_crafting_keystone_1");
      this.setSkillPosition(playerClass, "offensive_crafting_7", 10.0F, 0.0F, "offensive_crafting_keystone_1");
      this.setSkillPosition(playerClass, "defensive_crafting_3", 12.0F, 120.0F, "healing_1");
      this.setSkillPosition(playerClass, "healing_1", 10.0F, 165.0F, "healing_3");
      this.setSkillPosition(playerClass, "healing_3", 10.0F, 75.0F, "healing_2");
      this.setSkillPosition(playerClass, "healing_3", 10.0F, 210.0F, "healing_4");
      this.setSkillPosition(playerClass, "healing_4", 12.0F, 210.0F, "healing_notable_1");
      this.setSkillPosition(playerClass, "defensive_7", 10.0F, -90.0F, "lesser_1");
      this.setSkillPosition(playerClass, "offensive_7", 10.0F, 90.0F, "lesser_2");
      this.setSkillPosition(playerClass, "lesser_1", 10.0F, 0.0F, "lesser_3");
      this.setSkillPosition(playerClass, "lesser_2", 10.0F, 0.0F, "lesser_4");
      this.setSkillPosition(playerClass, "lesser_4", 12.0F, 90.0F, "lesser_5");
      this.connectSkills(playerClass, "lesser_5", "lesser_3");
      this.setSkillPosition(playerClass, "lesser_5", 10.0F, 0.0F, "lesser_6");
      this.setSkillPosition(playerClass, "lesser_6", 10.0F, 0.0F, "mastery");
      this.setSkillPosition(playerClass, "defensive_6", 10.0F, -90.0F, "crit_1");
      this.setSkillPosition(playerClass, "offensive_6", 10.0F, 90.0F, "crit_2");
      this.setSkillPosition(playerClass, "crit_2", 10.0F, 90.0F, "crit_notable_1");
      this.connectSkills(playerClass, "crit_1", "crit_notable_1");
      this.setSkillPosition(playerClass, "offensive_notable_1", 12.0F, -120.0F, "subclass_1_defensive_1");
      this.setSkillPosition(playerClass, "defensive_notable_1", 12.0F, 120.0F, "subclass_2_defensive_1");
      this.setSkillBranchPosition(playerClass, "subclass_1_defensive_1", 10, "subclass_1_defensive", -30.0F, 0.0F, 2, 4);
      this.setSkillBranchPosition(playerClass, "subclass_2_defensive_1", 10, "subclass_2_defensive", 30.0F, 0.0F, 2, 4);
      this.setSkillPosition(playerClass, "subclass_1_defensive_4", 12.0F, 0.0F, "subclass_1");
      this.setSkillPosition(playerClass, "subclass_2_defensive_4", 12.0F, 0.0F, "subclass_2");
      this.setSkillPosition(playerClass, "subclass_2_defensive_1", 8.0F, 180.0F, "gateway");
      this.setSkillPosition(playerClass, "subclass_1", 12.0F, 30.0F, "subclass_1_offensive_1");
      this.setSkillPosition(playerClass, "subclass_2", 12.0F, 30.0F, "subclass_2_crafting_1");
      this.setSkillBranchPosition(playerClass, "subclass_1_offensive_1", 10, "subclass_1_offensive", 0.0F, 0.0F, 2, 4);
      this.setSkillBranchPosition(playerClass, "subclass_2_crafting_1", 10, "subclass_2_crafting", 0.0F, 0.0F, 2, 4);
      this.setSkillPosition(playerClass, "subclass_1", 12.0F, -30.0F, "subclass_1_crafting_1");
      this.setSkillPosition(playerClass, "subclass_2", 12.0F, -30.0F, "subclass_2_life_1");
      this.setSkillBranchPosition(playerClass, "subclass_1_crafting_1", 10, "subclass_1_crafting", 0.0F, 0.0F, 2, 4);
      this.setSkillBranchPosition(playerClass, "subclass_2_life_1", 10, "subclass_2_life", 0.0F, 0.0F, 2, 4);
      this.setSkillPosition(playerClass, "subclass_1_crafting_2", 10.0F, -60.0F, "subclass_1_crafting_5");
      this.setSkillPosition(playerClass, "subclass_2_crafting_2", 10.0F, 60.0F, "subclass_2_crafting_5");
      this.setSkillPosition(playerClass, "subclass_1_crafting_5", 14.0F, -120.0F, "subclass_special");
      this.setSkillPosition(playerClass, "subclass_1_crafting_4", 12.0F, 30.0F, "subclass_1_mastery");
      this.connectSkills(playerClass, "subclass_1_mastery", "subclass_1_offensive_4");
      this.setSkillPosition(playerClass, "subclass_2_crafting_4", 12.0F, -30.0F, "subclass_2_mastery");
      this.connectSkills(playerClass, "subclass_2_mastery", "subclass_2_life_4");
      this.setSkillPosition(playerClass, "subclass_1_crafting_4", 12.0F, -60.0F, "subclass_1_crafting_notable_1");
      this.setSkillPosition(playerClass, "subclass_1_offensive_4", 12.0F, 60.0F, "subclass_1_offensive_notable_1");
      this.setSkillPosition(playerClass, "subclass_2_life_4", 12.0F, -60.0F, "subclass_2_life_notable_1");
      this.setSkillPosition(playerClass, "subclass_2_crafting_4", 12.0F, 60.0F, "subclass_2_crafting_notable_1");
      this.connectSkills(playerClass, "subclass_2_crafting_3", "subclass_2_crafting_5");
      this.connectSkills(playerClass, "subclass_1_crafting_3", "subclass_1_crafting_5");
   }

   protected void connectClassTrees() {
      this.connectSkillsBetweenClasses("healing_2", "offensive_crafting_3");
      this.connectSkillsBetweenClasses("subclass_2_defensive_1", "subclass_1_defensive_1");
      this.connectSkillsBetweenClasses("gateway", "subclass_1_defensive_1");
      this.connectSkillsBetweenClasses("subclass_2_crafting_5", "subclass_special");
   }

   private void setSkillsAttributeModifiers() {
      this.addSkillBonus("alchemist_class", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.ANY), new PotionDurationBonus(0.4F)));
      this.addSkillBranchBonuses(
         "alchemist_defensive_crafting", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.BENEFICIAL), new PotionAmplificationBonus(0.1F)), 1, 7
      );
      this.addSkillBranchBonuses(
         "alchemist_offensive_crafting", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.HARMFUL), new PotionAmplificationBonus(0.1F)), 1, 7
      );
      this.addSkillBranchBonuses("alchemist_defensive", PSTAttributes.EVASION, 1.0, Operation.ADDITION, 1, 8);
      this.addSkillBranchBonuses(
         "alchemist_offensive", new DamageBonus(0.05F, Operation.MULTIPLY_BASE).setTargetCondition(new HasEffectCondition(MobEffects.f_19614_)), 1, 8
      );
      this.addSkillBonus(
         "alchemist_defensive_notable_1",
         createAttributeBonus((Attribute)PSTAttributes.EVASION.get(), 10.0, Operation.ADDITION).setCondition(new EffectAmountCondition(1, -1))
      );
      this.addSkillBonus(
         "alchemist_offensive_notable_1", new DamageBonus(0.25F, Operation.MULTIPLY_BASE).setTargetCondition(new HasEffectCondition(MobEffects.f_19614_))
      );
      this.addSkillBranchBonuses(
         "alchemist_life", createAttributeBonus(Attributes.f_22276_, 2.0, Operation.ADDITION).setCondition(new EffectAmountCondition(1, -1)), 1, 2
      );
      this.addSkillBonus(
         "alchemist_life_notable_1", createAttributeBonus(Attributes.f_22276_, 6.0, Operation.ADDITION).setCondition(new EffectAmountCondition(1, -1))
      );
      this.addSkillBranchBonuses(
         "alchemist_speed", createAttributeBonus(Attributes.f_22283_, 0.05, Operation.MULTIPLY_BASE).setCondition(new EffectAmountCondition(1, -1)), 1, 2
      );
      this.addSkillBonus(
         "alchemist_speed_notable_1", createAttributeBonus(Attributes.f_22283_, 0.1, Operation.MULTIPLY_BASE).setCondition(new EffectAmountCondition(1, -1))
      );
      this.addSkillBranchBonuses("alchemist_lesser", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.ANY), new PotionDurationBonus(0.05F)), 1, 6);
      this.addSkillBonus("alchemist_mastery", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.ANY), new PotionAmplificationBonus(1.0F)));
      this.addSkillBranchBonuses("alchemist_crit", new CritChanceBonus(0.02F).setTargetCondition(new HasEffectCondition(MobEffects.f_19614_)), 1, 2);
      this.addSkillBonus("alchemist_crit_notable_1", new CritDamageBonus(0.35F).setTargetCondition(new HasEffectCondition(MobEffects.f_19614_)));
      this.addSkillBranchBonuses(
         "alchemist_crafting", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.ANY), new PotionAmplificationBonus(0.1F)), 1, 3
      );
      this.addSkillBonus(
         "alchemist_defensive_crafting_keystone_1",
         new CraftedItemBonus(new PotionCondition(PotionCondition.Type.BENEFICIAL), new PotionAmplificationBonus(1.0F))
      );
      this.addSkillBonus(
         "alchemist_offensive_crafting_keystone_1", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.HARMFUL), new PotionAmplificationBonus(0.5F))
      );
      this.addSkillBonus("alchemist_offensive_crafting_keystone_1", new RecipeUnlockBonus(new ResourceLocation("skilltree:weapon_poisoning")));
      this.addSkillBranchBonuses("alchemist_healing", new HealingBonus(1.0F, 0.1F), 1, 4);
      this.addSkillBonus("alchemist_healing_notable_1", new HealingBonus(1.0F, 2.0F, new ItemUsedEventListener(new PotionCondition(PotionCondition.Type.ANY))));
      this.addSkillBonus("alchemist_crafting_notable_1", new RecipeUnlockBonus(new ResourceLocation("skilltree:potion_mixing")));
      this.addSkillBonus(
         "alchemist_defensive_keystone_1",
         createAttributeBonus((Attribute)PSTAttributes.EVASION.get(), 2.0, Operation.ADDITION).setMultiplier(new EffectAmountMultiplier())
      );
      this.addSkillBonus("alchemist_offensive_keystone_1", new DamageBonus(0.15F, Operation.MULTIPLY_BASE).setPlayerMultiplier(new EffectAmountMultiplier()));
      this.addSkillBranchBonuses("alchemist_subclass_1_defensive", PSTAttributes.EVASION, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("alchemist_subclass_1_defensive", Attributes.f_22284_, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBonus("alchemist_subclass_1", new CritChanceBonus(0.05F));
      this.addSkillBonus("alchemist_subclass_1", new CritDamageBonus(0.15F));
      this.addSkillBranchBonuses("alchemist_subclass_1_offensive", new CritChanceBonus(0.02F), 1, 4);
      this.addSkillBranchBonuses(
         "alchemist_subclass_1_crafting", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.HARMFUL), new PotionAmplificationBonus(0.1F)), 1, 5
      );
      this.addSkillBonus(
         "alchemist_subclass_1_crafting_notable_1", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.HARMFUL), new PotionDurationBonus(0.25F))
      );
      this.addSkillBonus("alchemist_subclass_1_offensive_notable_1", new CritChanceBonus(0.05F));
      this.addSkillBonus("alchemist_subclass_1_mastery", new CritDamageBonus(0.5F));
      this.addSkillBonus(
         "alchemist_subclass_special", new CraftedItemBonus(new ItemTagCondition(PSTTags.RINGS.f_203868_()), new ItemSkillBonus(new CritDamageBonus(0.1F)))
      );
      this.addSkillBranchBonuses("alchemist_subclass_2_defensive", PSTAttributes.EVASION, 1.0, Operation.ADDITION, 1, 4);
      this.addSkillBonus("alchemist_subclass_2", new IncomingHealingBonus(0.15F));
      this.addSkillBranchBonuses("alchemist_subclass_2_life", Attributes.f_22276_, 2.0, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("alchemist_subclass_2_life", new IncomingHealingBonus(0.05F), 1, 4);
      this.addSkillBranchBonuses(
         "alchemist_subclass_2_crafting", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.BENEFICIAL), new PotionAmplificationBonus(0.1F)), 1, 5
      );
      this.addSkillBonus(
         "alchemist_subclass_2_crafting_notable_1", new CraftedItemBonus(new PotionCondition(PotionCondition.Type.BENEFICIAL), new PotionDurationBonus(0.1F))
      );
      this.addSkillBonus("alchemist_subclass_2_life_notable_1", new IncomingHealingBonus(0.1F).setCondition(new HealthPercentageCondition(-1.0F, 0.5F)));
      this.addSkillBonus("alchemist_subclass_2_mastery", new IncomingHealingBonus(0.25F).setCondition(new HealthPercentageCondition(-1.0F, 0.5F)));
      this.addSkillBonus("hunter_class", new LootDuplicationBonus(0.15F, 1.0F, LootDuplicationBonus.LootType.MOBS));
      this.addSkillBranchBonuses(
         "hunter_defensive_crafting",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.ARMOR),
            new ItemSkillBonus(createAttributeBonus((Attribute)PSTAttributes.EVASION.get(), 1.0, Operation.ADDITION))
         ),
         1,
         7
      );
      this.addSkillBranchBonuses(
         "hunter_offensive_crafting",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.RANGED_WEAPON),
            new ItemSkillBonus(createAttributeBonus(Attributes.f_22283_, 0.04, Operation.MULTIPLY_BASE))
         ),
         1,
         7
      );
      this.addSkillBranchBonuses("hunter_defensive", PSTAttributes.EVASION, 1.0, Operation.ADDITION, 1, 8);
      this.addSkillBranchBonuses("hunter_offensive", new DamageBonus(0.1F, Operation.MULTIPLY_BASE).setDamageCondition(new ProjectileDamageCondition()), 1, 8);
      this.addSkillBonus(
         "hunter_defensive_notable_1",
         createAttributeBonus((Attribute)PSTAttributes.EVASION.get(), 10.0, Operation.ADDITION).setCondition(new HealthPercentageCondition(-1.0F, 0.5F))
      );
      this.addSkillBonus("hunter_offensive_notable_1", new DamageBonus(0.25F, Operation.MULTIPLY_BASE).setDamageCondition(new ProjectileDamageCondition()));
      this.addSkillBranchBonuses(
         "hunter_life",
         createAttributeBonus(Attributes.f_22276_, 0.05, Operation.ADDITION)
            .setMultiplier(new AttributeValueMultiplier((Attribute)PSTAttributes.EVASION.get())),
         1,
         2
      );
      this.addSkillBonus(
         "hunter_life_notable_1",
         createAttributeBonus(Attributes.f_22276_, 0.1, Operation.ADDITION).setMultiplier(new AttributeValueMultiplier((Attribute)PSTAttributes.EVASION.get()))
      );
      this.addSkillBranchBonuses(
         "hunter_speed",
         createAttributeBonus(Attributes.f_22283_, 0.02, Operation.MULTIPLY_BASE)
            .setCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.RANGED_WEAPON))),
         1,
         2
      );
      this.addSkillBonus(
         "hunter_speed_notable_1",
         createAttributeBonus(Attributes.f_22283_, 0.05, Operation.MULTIPLY_BASE)
            .setCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.RANGED_WEAPON)))
      );
      this.addSkillBranchBonuses("hunter_lesser", new LootDuplicationBonus(0.05F, 1.0F, LootDuplicationBonus.LootType.MOBS), 1, 6);
      this.addSkillBonus("hunter_mastery", new LootDuplicationBonus(0.15F, 2.0F, LootDuplicationBonus.LootType.MOBS));
      this.addSkillBranchBonuses("hunter_crit", new CritChanceBonus(0.02F).setDamageCondition(new ProjectileDamageCondition()), 1, 2);
      this.addSkillBonus("hunter_crit_notable_1", new CritDamageBonus(0.25F).setDamageCondition(new ProjectileDamageCondition()));
      this.addSkillBranchBonuses("hunter_crafting", new ArrowRetrievalBonus(0.05F), 1, 3);
      this.addSkillBonus(
         "hunter_defensive_crafting_keystone_1", new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.HELMET), new ItemSocketsBonus(1))
      );
      this.addSkillBonus(
         "hunter_offensive_crafting_keystone_1", new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.RANGED_WEAPON), new ItemSocketsBonus(1))
      );
      this.addSkillBranchBonuses("hunter_healing", new HealingBonus(1.0F, 0.1F), 1, 4);
      this.addSkillBonus(
         "hunter_healing_notable_1",
         new HealingBonus(1.0F, 1.0F, new AttackEventListener().setTarget(SkillBonus.Target.PLAYER).setDamageCondition(new ProjectileDamageCondition()))
      );
      this.addSkillBonus("hunter_crafting_notable_1", new ArrowRetrievalBonus(0.1F));
      this.addSkillBonus(
         "hunter_defensive_keystone_1",
         createAttributeBonus(Attributes.f_22284_, 0.25, Operation.ADDITION)
            .setMultiplier(new AttributeValueMultiplier((Attribute)PSTAttributes.EVASION.get()))
      );
      this.addSkillBonus("hunter_offensive_keystone_1", new DamageBonus(0.05F, Operation.MULTIPLY_BASE).setPlayerMultiplier(new DistanceToTargetMultiplier()));
      this.addSkillBranchBonuses("hunter_subclass_1_defensive", PSTAttributes.EVASION, 1.0, Operation.ADDITION, 1, 4);
      this.addSkillBonus("hunter_subclass_1", PSTAttributes.STEALTH, 10.0, Operation.ADDITION);
      this.addSkillBonus("hunter_subclass_1", new JumpHeightBonus(0.1F));
      this.addSkillBranchBonuses("hunter_subclass_1_offensive", PSTAttributes.STEALTH, 5.0, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("hunter_subclass_1_offensive", Attributes.f_22283_, 0.02, Operation.MULTIPLY_BASE, 1, 4);
      this.addSkillBranchBonuses(
         "hunter_subclass_1_crafting",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.ARMOR),
            new ItemSkillBonus(createAttributeBonus((Attribute)PSTAttributes.STEALTH.get(), 1.0, Operation.ADDITION))
         ),
         1,
         5
      );
      this.addSkillBonus(
         "hunter_subclass_1_crafting_notable_1",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.BOOTS),
            new ItemSkillBonus(createAttributeBonus((Attribute)PSTAttributes.STEALTH.get(), 5.0, Operation.ADDITION))
         )
      );
      this.addSkillBonus("hunter_subclass_1_offensive_notable_1", Attributes.f_22283_, 0.05, Operation.MULTIPLY_BASE);
      this.addSkillBonus("hunter_subclass_1_offensive_notable_1", PSTAttributes.STEALTH, 5.0, Operation.ADDITION);
      this.addSkillBonus("hunter_subclass_1_mastery", PSTAttributes.STEALTH, 10.0, Operation.ADDITION);
      this.addSkillBonus("hunter_subclass_1_mastery", new JumpHeightBonus(0.5F));
      this.addSkillBonus(
         "hunter_subclass_special",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.WEAPON),
            new ItemSkillBonus(new HealingBonus(1.0F, 0.5F, new AttackEventListener().setTarget(SkillBonus.Target.PLAYER)))
         )
      );
      this.addSkillBranchBonuses("hunter_subclass_2_defensive", PSTAttributes.EVASION, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("hunter_subclass_2_defensive", PSTAttributes.BLOCKING, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBonus(
         "hunter_subclass_2", new CraftedItemBonus(new ItemTagCondition(PSTTags.QUIVERS.f_203868_()), new ItemSkillBonus(new ArrowRetrievalBonus(0.05F)))
      );
      this.addSkillBranchBonuses(
         "hunter_subclass_2_life",
         createAttributeBonus(Attributes.f_22276_, 1.0, Operation.ADDITION)
            .setCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.RANGED_WEAPON))),
         1,
         4
      );
      this.addSkillBranchBonuses(
         "hunter_subclass_2_crafting",
         new CraftedItemBonus(new ItemTagCondition(PSTTags.QUIVERS.f_203868_()), new QuiverCapacityBonus(10.0F, Operation.ADDITION)),
         1,
         5
      );
      this.addSkillBonus(
         "hunter_subclass_2_crafting_notable_1",
         new CraftedItemBonus(new ItemTagCondition(PSTTags.QUIVERS.f_203868_()), new ItemSkillBonus(new ArrowRetrievalBonus(0.1F)))
      );
      this.addSkillBonus(
         "hunter_subclass_2_life_notable_1",
         new CraftedItemBonus(
            new ItemTagCondition(PSTTags.QUIVERS.f_203868_()), new ItemSkillBonus(createAttributeBonus(Attributes.f_22276_, 5.0, Operation.ADDITION))
         )
      );
      this.addSkillBonus(
         "hunter_subclass_2_mastery",
         new CraftedItemBonus(new ItemTagCondition(PSTTags.QUIVERS.f_203868_()), new QuiverCapacityBonus(25.0F, Operation.ADDITION))
      );
      this.addSkillBonus("miner_class", new BlockBreakSpeedBonus(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.PICKAXE)), 0.15F));
      this.addSkillBranchBonuses("miner_defensive_crafting", new GemPowerBonus(new EquipmentCondition(EquipmentCondition.Type.ARMOR), 0.1F), 1, 7);
      this.addSkillBranchBonuses("miner_offensive_crafting", new GemPowerBonus(new EquipmentCondition(EquipmentCondition.Type.WEAPON), 0.1F), 1, 7);
      this.addSkillBranchBonuses("miner_defensive", Attributes.f_22284_, 1.0, Operation.ADDITION, 1, 8);
      this.addSkillBranchBonuses(
         "miner_offensive",
         new DamageBonus(0.05F, Operation.MULTIPLY_BASE)
            .setPlayerCondition(new HasGemsCondition(1, -1, new EquipmentCondition(EquipmentCondition.Type.WEAPON))),
         1,
         8
      );
      this.addSkillBonus(
         "miner_defensive_notable_1",
         createAttributeBonus(Attributes.f_22284_, 2.0, Operation.ADDITION)
            .setMultiplier(new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.HELMET)))
      );
      this.addSkillBonus(
         "miner_offensive_notable_1",
         new DamageBonus(0.15F, Operation.MULTIPLY_BASE)
            .setPlayerCondition(new HasGemsCondition(1, -1, new EquipmentCondition(EquipmentCondition.Type.WEAPON)))
      );
      this.addSkillBranchBonuses(
         "miner_life",
         createAttributeBonus(Attributes.f_22276_, 1.0, Operation.ADDITION)
            .setMultiplier(new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.HELMET))),
         1,
         2
      );
      this.addSkillBonus(
         "miner_life_notable_1",
         createAttributeBonus(Attributes.f_22276_, 1.0, Operation.ADDITION)
            .setMultiplier(new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.ARMOR)))
      );
      this.addSkillBranchBonuses(
         "miner_speed",
         createAttributeBonus(Attributes.f_22283_, 0.02, Operation.MULTIPLY_BASE)
            .setCondition(new HasGemsCondition(1, -1, new EquipmentCondition(EquipmentCondition.Type.WEAPON))),
         1,
         2
      );
      this.addSkillBonus(
         "miner_speed_notable_1",
         createAttributeBonus(Attributes.f_22283_, 0.04, Operation.MULTIPLY_BASE)
            .setMultiplier(new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.WEAPON)))
      );
      this.addSkillBranchBonuses(
         "miner_lesser", new BlockBreakSpeedBonus(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.PICKAXE)), 0.05F), 1, 6
      );
      this.addSkillBonus("miner_mastery", new PlayerSocketsBonus(new EquipmentCondition(EquipmentCondition.Type.ANY), 1));
      this.addSkillBranchBonuses(
         "miner_crit", new CritChanceBonus(0.01F).setPlayerMultiplier(new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.WEAPON))), 1, 2
      );
      this.addSkillBonus(
         "miner_crit_notable_1",
         new CritDamageBonus(0.1F).setPlayerMultiplier(new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.WEAPON)))
      );
      this.addSkillBranchBonuses("miner_crafting", new LootDuplicationBonus(0.05F, 1.0F, LootDuplicationBonus.LootType.GEMS), 1, 3);
      this.addSkillBonus("miner_defensive_crafting_keystone_1", new GemPowerBonus(new EquipmentCondition(EquipmentCondition.Type.ARMOR), 0.3F));
      this.addSkillBonus("miner_defensive_crafting_keystone_1", new PlayerSocketsBonus(new EquipmentCondition(EquipmentCondition.Type.CHESTPLATE), 1));
      this.addSkillBonus("miner_offensive_crafting_keystone_1", new GemPowerBonus(new EquipmentCondition(EquipmentCondition.Type.WEAPON), 0.3F));
      this.addSkillBonus("miner_offensive_crafting_keystone_1", new PlayerSocketsBonus(new EquipmentCondition(EquipmentCondition.Type.WEAPON), 1));
      this.addSkillBranchBonuses("miner_healing", PSTAttributes.REGENERATION, 0.1, Operation.ADDITION, 1, 4);
      this.addSkillBonus(
         "miner_healing_notable_1",
         createAttributeBonus((Attribute)PSTAttributes.REGENERATION.get(), 0.1, Operation.ADDITION)
            .setMultiplier(new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.HELMET)))
      );
      this.addSkillBonus("miner_crafting_notable_1", new LootDuplicationBonus(0.1F, 1.0F, LootDuplicationBonus.LootType.GEMS));
      this.addSkillBonus(
         "miner_defensive_keystone_1",
         createAttributeBonus(Attributes.f_22284_, 5.0, Operation.ADDITION)
            .setMultiplier(new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.CHESTPLATE)))
      );
      this.addSkillBonus(
         "miner_offensive_keystone_1",
         new DamageBonus(0.1F, Operation.MULTIPLY_BASE).setPlayerMultiplier(new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.WEAPON)))
      );
      this.addSkillBranchBonuses("miner_subclass_1_defensive", Attributes.f_22284_, 1.0, Operation.ADDITION, 1, 4);
      this.addSkillBonus("miner_subclass_1", Attributes.f_22283_, 0.1, Operation.MULTIPLY_BASE);
      this.addSkillBonus("miner_subclass_1", Attributes.f_22279_, 0.1, Operation.MULTIPLY_BASE);
      this.addSkillBranchBonuses("miner_subclass_1_offensive", Attributes.f_22283_, 0.02, Operation.MULTIPLY_BASE, 1, 4);
      this.addSkillBranchBonuses(
         "miner_subclass_1_crafting",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.BOOTS), new ItemSkillBonus(createAttributeBonus(Attributes.f_22279_, 0.02, Operation.MULTIPLY_BASE))
         ),
         1,
         5
      );
      this.addSkillBonus(
         "miner_subclass_1_crafting_notable_1",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.BOOTS), new ItemSkillBonus(createAttributeBonus(Attributes.f_22279_, 0.05, Operation.MULTIPLY_BASE))
         )
      );
      this.addSkillBonus("miner_subclass_1_offensive_notable_1", Attributes.f_22283_, 0.05, Operation.MULTIPLY_BASE);
      this.addSkillBonus("miner_subclass_1_offensive_notable_1", Attributes.f_22279_, 0.05, Operation.MULTIPLY_BASE);
      this.addSkillBonus(
         "miner_subclass_1_mastery",
         new DamageBonus(0.1F, Operation.MULTIPLY_BASE).setPlayerMultiplier(new GemsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.BOOTS)))
      );
      this.addSkillBonus("miner_subclass_special", new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.BOOTS), new ItemSocketsBonus(1)));
      this.addSkillBranchBonuses("miner_subclass_2_defensive", PSTAttributes.EVASION, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("miner_subclass_2_defensive", Attributes.f_22284_, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBonus("miner_subclass_2", new PlayerSocketsBonus(new ItemTagCondition(PSTTags.RINGS.f_203868_()), 1));
      this.addSkillBranchBonuses(
         "miner_subclass_2_life",
         createAttributeBonus(Attributes.f_22276_, 1.0, Operation.ADDITION)
            .setMultiplier(new GemsAmountMultiplier(new ItemTagCondition(PSTTags.JEWELRY.f_203868_()))),
         1,
         4
      );
      this.addSkillBranchBonuses("miner_subclass_2_crafting", new GemPowerBonus(new ItemTagCondition(PSTTags.JEWELRY.f_203868_()), 0.05F), 1, 5);
      this.addSkillBonus("miner_subclass_2_crafting_notable_1", new GemPowerBonus(new ItemTagCondition(PSTTags.JEWELRY.f_203868_()), 0.25F));
      this.addSkillBonus(
         "miner_subclass_2_life_notable_1",
         new CraftedItemBonus(
            new ItemTagCondition(PSTTags.NECKLACES.f_203868_()), new ItemSkillBonus(createAttributeBonus(Attributes.f_22276_, 5.0, Operation.ADDITION))
         )
      );
      this.addSkillBonus("miner_subclass_2_mastery", CuriosHelper.getOrCreateSlotAttribute("ring"), 1.0, Operation.ADDITION);
      this.addSkillBonus(
         "blacksmith_class", new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.ANY), new ItemDurabilityBonus(0.25F, Operation.MULTIPLY_BASE))
      );
      this.addSkillBranchBonuses(
         "blacksmith_defensive_crafting",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.ARMOR), new ItemSkillBonus(createAttributeBonus(Attributes.f_22284_, 0.1, Operation.ADDITION))
         ),
         1,
         7
      );
      this.addSkillBranchBonuses(
         "blacksmith_offensive_crafting",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.MELEE_WEAPON),
            new ItemSkillBonus(createAttributeBonus(Attributes.f_22281_, 1.0, Operation.ADDITION))
         ),
         1,
         7
      );
      this.addSkillBranchBonuses("blacksmith_defensive", Attributes.f_22284_, 1.0, Operation.ADDITION, 1, 8);
      this.addSkillBranchBonuses(
         "blacksmith_offensive",
         new DamageBonus(0.05F, Operation.MULTIPLY_BASE).setPlayerCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.SHIELD))),
         1,
         8
      );
      this.addSkillBonus("blacksmith_defensive_notable_1", Attributes.f_22284_, 0.05, Operation.MULTIPLY_BASE);
      this.addSkillBonus(
         "blacksmith_offensive_notable_1",
         new DamageBonus(0.25F, Operation.MULTIPLY_BASE).setPlayerCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.SHIELD)))
      );
      this.addSkillBranchBonuses(
         "blacksmith_life",
         createAttributeBonus(Attributes.f_22276_, 1.0, Operation.ADDITION)
            .setCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.SHIELD))),
         1,
         2
      );
      this.addSkillBonus(
         "blacksmith_life_notable_1",
         createAttributeBonus(Attributes.f_22276_, 4.0, Operation.ADDITION)
            .setCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.SHIELD)))
      );
      this.addSkillBranchBonuses(
         "blacksmith_speed",
         createAttributeBonus(Attributes.f_22283_, 0.02, Operation.MULTIPLY_BASE)
            .setCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.SHIELD))),
         1,
         2
      );
      this.addSkillBonus(
         "blacksmith_speed_notable_1",
         createAttributeBonus(Attributes.f_22283_, 0.05, Operation.MULTIPLY_BASE)
            .setCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.SHIELD)))
      );
      this.addSkillBranchBonuses(
         "blacksmith_lesser",
         new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.ANY), new ItemDurabilityBonus(0.05F, Operation.MULTIPLY_BASE)),
         1,
         6
      );
      this.addSkillBonus(
         "blacksmith_mastery",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.ANY), new ItemSkillBonus(createAttributeBonus(Attributes.f_22285_, 1.0, Operation.ADDITION))
         )
      );
      this.addSkillBranchBonuses(
         "blacksmith_crit",
         new CritChanceBonus(0.02F).setPlayerCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.SHIELD))),
         1,
         2
      );
      this.addSkillBonus(
         "blacksmith_crit_notable_1",
         new CritDamageBonus(0.3F).setPlayerCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.SHIELD)))
      );
      this.addSkillBranchBonuses(
         "blacksmith_crafting",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.SHIELD), new ItemSkillBonus(createAttributeBonus(Attributes.f_22284_, 2.0, Operation.ADDITION))
         ),
         1,
         3
      );
      this.addSkillBonus(
         "blacksmith_defensive_crafting_keystone_1",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.ARMOR), new ItemSkillBonus(createAttributeBonus(Attributes.f_22284_, 1.0, Operation.ADDITION))
         )
      );
      this.addSkillBonus(
         "blacksmith_offensive_crafting_keystone_1",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.MELEE_WEAPON),
            new ItemSkillBonus(createAttributeBonus(Attributes.f_22283_, 0.25, Operation.MULTIPLY_BASE))
         )
      );
      this.addSkillBranchBonuses("blacksmith_healing", PSTAttributes.REGENERATION, 0.1, Operation.ADDITION, 1, 4);
      this.addSkillBonus(
         "blacksmith_healing_notable_1",
         createAttributeBonus((Attribute)PSTAttributes.REGENERATION.get(), 0.2, Operation.ADDITION)
            .setCondition(new HasItemInHandCondition(new EquipmentCondition(EquipmentCondition.Type.SHIELD)))
      );
      this.addSkillBonus(
         "blacksmith_crafting_notable_1",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.SHIELD), new ItemSkillBonus(createAttributeBonus(Attributes.f_22284_, 4.0, Operation.ADDITION))
         )
      );
      this.addSkillBonus(
         "blacksmith_defensive_keystone_1",
         createAttributeBonus(Attributes.f_22284_, 0.1, Operation.MULTIPLY_BASE).setCondition(new AttributeValueCondition(Attributes.f_22284_, 50.0F, -1.0F))
      );
      this.addSkillBonus(
         "blacksmith_defensive_keystone_1",
         createAttributeBonus(Attributes.f_22284_, 0.1, Operation.MULTIPLY_BASE).setCondition(new AttributeValueCondition(Attributes.f_22284_, 100.0F, -1.0F))
      );
      this.addSkillBonus(
         "blacksmith_offensive_keystone_1",
         createAttributeBonus(Attributes.f_22281_, 0.1, Operation.ADDITION).setMultiplier(new AttributeValueMultiplier(Attributes.f_22284_))
      );
      this.addSkillBranchBonuses("blacksmith_subclass_1_defensive", Attributes.f_22284_, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("blacksmith_subclass_1_defensive", PSTAttributes.BLOCKING, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBonus("blacksmith_subclass_1", Attributes.f_22284_, 5.0, Operation.ADDITION);
      this.addSkillBonus("blacksmith_subclass_1", PSTAttributes.BLOCKING, 5.0, Operation.ADDITION);
      this.addSkillBranchBonuses(
         "blacksmith_subclass_1_offensive", new DamageBonus(0.01F, Operation.MULTIPLY_BASE).setDamageCondition(new MeleeDamageCondition()), 1, 4
      );
      this.addSkillBranchBonuses("blacksmith_subclass_1_offensive", PSTAttributes.BLOCKING, 1.0, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses(
         "blacksmith_subclass_1_crafting",
         new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.MELEE_WEAPON), new ItemSkillBonus(new CritChanceBonus(0.01F))),
         1,
         5
      );
      this.addSkillBonus(
         "blacksmith_subclass_1_crafting_notable_1",
         new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.MELEE_WEAPON), new ItemSkillBonus(new CritChanceBonus(0.05F)))
      );
      this.addSkillBonus(
         "blacksmith_subclass_1_offensive_notable_1", new DamageBonus(0.2F, Operation.MULTIPLY_BASE).setDamageCondition(new MeleeDamageCondition())
      );
      this.addSkillBonus("blacksmith_subclass_1_offensive_notable_1", new CritDamageBonus(0.1F).setDamageCondition(new MeleeDamageCondition()));
      this.addSkillBonus("blacksmith_subclass_1_mastery", PSTAttributes.BLOCKING, 10.0, Operation.ADDITION);
      this.addSkillBonus("blacksmith_subclass_1_mastery", Attributes.f_22284_, 0.05, Operation.MULTIPLY_BASE);
      this.addSkillBonus("blacksmith_subclass_1_mastery", new DamageBonus(0.05F, Operation.MULTIPLY_BASE).setDamageCondition(new MeleeDamageCondition()));
      this.addSkillBonus(
         "blacksmith_subclass_special",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.WEAPON),
            new ItemSkillBonus(new LootDuplicationBonus(0.05F, 1.0F, LootDuplicationBonus.LootType.MOBS))
         )
      );
      this.addSkillBranchBonuses("blacksmith_subclass_2_defensive", Attributes.f_22284_, 1.0, Operation.ADDITION, 1, 4);
      this.addSkillBonus("blacksmith_subclass_2", new RepairEfficiencyBonus(new EquipmentCondition(EquipmentCondition.Type.ANY), 1.0F));
      this.addSkillBranchBonuses(
         "blacksmith_subclass_2_life",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.ARMOR), new ItemSkillBonus(createAttributeBonus(Attributes.f_22276_, 1.0, Operation.ADDITION))
         ),
         1,
         4
      );
      this.addSkillBranchBonuses("blacksmith_subclass_2_crafting", new RepairEfficiencyBonus(new EquipmentCondition(EquipmentCondition.Type.ANY), 0.05F), 1, 5);
      this.addSkillBonus(
         "blacksmith_subclass_2_crafting_notable_1",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.SHIELD),
            new ItemSkillBonus(createAttributeBonus((Attribute)PSTAttributes.BLOCKING.get(), 5.0, Operation.ADDITION))
         )
      );
      this.addSkillBonus(
         "blacksmith_subclass_2_life_notable_1",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.SHIELD), new ItemSkillBonus(createAttributeBonus(Attributes.f_22276_, 5.0, Operation.ADDITION))
         )
      );
      this.addSkillBonus("blacksmith_subclass_2_mastery", new RepairEfficiencyBonus(new EquipmentCondition(EquipmentCondition.Type.ANY), 0.05F));
      this.addSkillBonus(
         "blacksmith_subclass_2_mastery",
         new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.ANY), new ItemDurabilityBonus(0.05F, Operation.MULTIPLY_BASE))
      );
      this.addSkillBonus(
         "blacksmith_subclass_2_mastery",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.WEAPON),
            new ItemSkillBonus(createAttributeBonus(Attributes.f_22283_, 0.05, Operation.MULTIPLY_BASE))
         )
      );
      this.addSkillBonus(
         "blacksmith_subclass_2_mastery",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.ARMOR),
            new ItemSkillBonus(createAttributeBonus(Attributes.f_22285_, 0.05F, Operation.MULTIPLY_BASE))
         )
      );
      this.addSkillBonus(
         "blacksmith_subclass_2_mastery",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.SHIELD), new ItemSkillBonus(createAttributeBonus(Attributes.f_22284_, 5.0, Operation.ADDITION))
         )
      );
      this.addSkillBonus("enchanter_class", new EnchantmentRequirementBonus(-0.3F));
      this.addSkillBranchBonuses("enchanter_defensive_crafting", new EnchantmentAmplificationBonus(new ArmorEnchantmentCondition(), 0.1F), 1, 7);
      this.addSkillBranchBonuses("enchanter_offensive_crafting", new EnchantmentAmplificationBonus(new WeaponEnchantmentCondition(), 0.1F), 1, 7);
      this.addSkillBranchBonuses("enchanter_defensive", PSTAttributes.BLOCKING, 1.0, Operation.ADDITION, 1, 8);
      this.addSkillBranchBonuses(
         "enchanter_offensive",
         new DamageBonus(0.05F, Operation.MULTIPLY_BASE)
            .setPlayerCondition(new HasItemInHandCondition(new EnchantedCondition(new EquipmentCondition(EquipmentCondition.Type.WEAPON)))),
         1,
         8
      );
      this.addSkillBonus(
         "enchanter_defensive_notable_1",
         createAttributeBonus((Attribute)PSTAttributes.BLOCKING.get(), 10.0, Operation.ADDITION)
            .setCondition(new HasItemInHandCondition(new EnchantedCondition(new EquipmentCondition(EquipmentCondition.Type.SHIELD))))
      );
      this.addSkillBonus(
         "enchanter_offensive_notable_1",
         new DamageBonus(0.2F, Operation.MULTIPLY_BASE)
            .setPlayerCondition(new HasItemInHandCondition(new EnchantedCondition(new EquipmentCondition(EquipmentCondition.Type.WEAPON))))
      );
      this.addSkillBranchBonuses(
         "enchanter_life",
         createAttributeBonus(Attributes.f_22276_, 2.0, Operation.ADDITION)
            .setCondition(new HasItemEquippedCondition(new EnchantedCondition(NoneItemCondition.INSTANCE))),
         1,
         2
      );
      this.addSkillBonus(
         "enchanter_life_notable_1",
         createAttributeBonus(Attributes.f_22276_, 1.0, Operation.ADDITION)
            .setMultiplier(new EnchantsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.ARMOR)))
      );
      this.addSkillBranchBonuses(
         "enchanter_speed",
         createAttributeBonus(Attributes.f_22283_, 0.02, Operation.MULTIPLY_BASE)
            .setCondition(new HasItemInHandCondition(new EnchantedCondition(new EquipmentCondition(EquipmentCondition.Type.WEAPON)))),
         1,
         2
      );
      this.addSkillBonus(
         "enchanter_speed_notable_1",
         createAttributeBonus(Attributes.f_22283_, 0.05, Operation.MULTIPLY_BASE)
            .setCondition(new HasItemInHandCondition(new EnchantedCondition(new EquipmentCondition(EquipmentCondition.Type.WEAPON))))
      );
      this.addSkillBranchBonuses("enchanter_lesser", new EnchantmentRequirementBonus(-0.05F), 1, 6);
      this.addSkillBonus("enchanter_mastery", new EnchantmentAmplificationBonus(1.0F));
      this.addSkillBranchBonuses(
         "enchanter_crit",
         new CritChanceBonus(0.02F)
            .setPlayerCondition(new HasItemInHandCondition(new EnchantedCondition(new EquipmentCondition(EquipmentCondition.Type.WEAPON)))),
         1,
         2
      );
      this.addSkillBonus(
         "enchanter_crit_notable_1",
         new CritDamageBonus(0.05F).setPlayerMultiplier(new EnchantsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.WEAPON)))
      );
      this.addSkillBranchBonuses("enchanter_crafting", new FreeEnchantmentBonus(0.05F), 1, 3);
      this.addSkillBonus("enchanter_defensive_crafting_keystone_1", new EnchantmentAmplificationBonus(new ArmorEnchantmentCondition(), 0.4F));
      this.addSkillBonus("enchanter_offensive_crafting_keystone_1", new EnchantmentAmplificationBonus(new WeaponEnchantmentCondition(), 0.4F));
      this.addSkillBranchBonuses("enchanter_healing", new HealingBonus(1.0F, 0.1F, new BlockEventListener().setTarget(SkillBonus.Target.PLAYER)), 1, 4);
      this.addSkillBonus(
         "enchanter_healing_notable_1",
         new HealingBonus(
            1.0F,
            0.3F,
            new BlockEventListener()
               .setTarget(SkillBonus.Target.PLAYER)
               .setPlayerMultiplier(new EnchantsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.SHIELD)))
         )
      );
      this.addSkillBonus("enchanter_crafting_notable_1", new FreeEnchantmentBonus(0.1F));
      this.addSkillBonus(
         "enchanter_defensive_keystone_1",
         createAttributeBonus((Attribute)PSTAttributes.BLOCKING.get(), 5.0, Operation.ADDITION)
            .setMultiplier(new EnchantsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.SHIELD)))
      );
      this.addSkillBonus(
         "enchanter_offensive_keystone_1",
         new DamageBonus(0.05F, Operation.MULTIPLY_BASE)
            .setPlayerMultiplier(new EnchantLevelsAmountMultiplier(new EquipmentCondition(EquipmentCondition.Type.WEAPON)))
      );
      this.addSkillBranchBonuses("enchanter_subclass_1_defensive", PSTAttributes.EVASION, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("enchanter_subclass_1_defensive", PSTAttributes.BLOCKING, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBonus("enchanter_subclass_1", new DamageBonus(0.15F, Operation.MULTIPLY_BASE).setTargetCondition(new BurningCondition()));
      this.addSkillBonus("enchanter_subclass_1", new IgniteBonus(0.15F, 5));
      this.addSkillBranchBonuses(
         "enchanter_subclass_1_offensive", new DamageBonus(0.05F, Operation.MULTIPLY_BASE).setTargetCondition(new BurningCondition()), 1, 4
      );
      this.addSkillBranchBonuses(
         "enchanter_subclass_1_crafting",
         new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.WEAPON), new ItemSkillBonus(new IgniteBonus(0.05F, 5))),
         1,
         5
      );
      this.addSkillBonus(
         "enchanter_subclass_1_crafting_notable_1",
         new CraftedItemBonus(
            new EquipmentCondition(EquipmentCondition.Type.WEAPON),
            new ItemSkillBonus(new DamageBonus(0.2F, Operation.MULTIPLY_BASE).setTargetCondition(new BurningCondition()))
         )
      );
      this.addSkillBonus("enchanter_subclass_1_offensive_notable_1", new CritChanceBonus(0.1F).setTargetCondition(new BurningCondition()));
      this.addSkillBonus("enchanter_subclass_1_mastery", new IgniteBonus(0.1F, 5));
      this.addSkillBonus("enchanter_subclass_1_mastery", new DamageBonus(0.1F, Operation.MULTIPLY_BASE).setTargetCondition(new BurningCondition()));
      this.addSkillBonus("enchanter_subclass_1_mastery", new CritChanceBonus(0.1F).setTargetCondition(new BurningCondition()));
      this.addSkillBonus(
         "enchanter_subclass_special",
         new CraftedItemBonus(
            new ItemTagCondition(PSTTags.QUIVERS.f_203868_()),
            new ItemSkillBonus(new DamageBonus(0.1F, Operation.MULTIPLY_BASE).setTargetCondition(new BurningCondition()))
         )
      );
      this.addSkillBonus(
         "enchanter_subclass_special", new CraftedItemBonus(new ItemTagCondition(PSTTags.QUIVERS.f_203868_()), new ItemSkillBonus(new IgniteBonus(0.1F, 5)))
      );
      this.addSkillBranchBonuses("enchanter_subclass_2_defensive", PSTAttributes.BLOCKING, 1.0, Operation.ADDITION, 1, 4);
      this.addSkillBonus("enchanter_subclass_2", PSTAttributes.EXP_PER_MINUTE, 2.0, Operation.ADDITION);
      this.addSkillBranchBonuses("enchanter_subclass_2_life", Attributes.f_22276_, 2.0, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("enchanter_subclass_2_life", PSTAttributes.EXP_PER_MINUTE, 0.1, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("enchanter_subclass_2_crafting", PSTAttributes.EXP_PER_MINUTE, 0.2, Operation.ADDITION, 1, 5);
      this.addSkillBonus("enchanter_subclass_2_crafting_notable_1", new GainedExperienceBonus(1.0F, GainedExperienceBonus.ExperienceSource.ORE));
      this.addSkillBonus("enchanter_subclass_2_life_notable_1", Attributes.f_22276_, 6.0, Operation.ADDITION);
      this.addSkillBonus("enchanter_subclass_2_life_notable_1", PSTAttributes.EXP_PER_MINUTE, 0.1, Operation.ADDITION);
      this.addSkillBonus("enchanter_subclass_2_mastery", PSTAttributes.EXP_PER_MINUTE, 1.5, Operation.ADDITION);
      this.addSkillBonus("cook_class", new CraftedItemBonus(new FoodCondition(), new FoodSaturationBonus(0.2F)));
      this.addSkillBranchBonuses("cook_defensive_crafting", new CraftedItemBonus(new FoodCondition(), new FoodHealingBonus(0.1F)), 1, 7);
      this.addSkillBranchBonuses(
         "cook_offensive_crafting",
         new CraftedItemBonus(new FoodCondition(), new FoodEffectBonus(new MobEffectInstance((MobEffect)PSTEffects.DAMAGE_BONUS.get(), 1200, 1))),
         1,
         7
      );
      this.addSkillBranchBonuses("cook_defensive", PSTAttributes.BLOCKING, 1.0, Operation.ADDITION, 1, 8);
      this.addSkillBranchBonuses("cook_offensive", new DamageBonus(0.05F, Operation.MULTIPLY_BASE).setPlayerCondition(new FoodLevelCondition(15, -1)), 1, 8);
      this.addSkillBonus(
         "cook_defensive_notable_1",
         createAttributeBonus((Attribute)PSTAttributes.BLOCKING.get(), 10.0, Operation.ADDITION).setCondition(new FoodLevelCondition(15, -1))
      );
      this.addSkillBonus("cook_offensive_notable_1", new DamageBonus(0.2F, Operation.MULTIPLY_BASE).setPlayerCondition(new FoodLevelCondition(15, -1)));
      this.addSkillBranchBonuses(
         "cook_life", createAttributeBonus(Attributes.f_22276_, 2.0, Operation.ADDITION).setCondition(new FoodLevelCondition(15, -1)), 1, 2
      );
      this.addSkillBonus("cook_life_notable_1", createAttributeBonus(Attributes.f_22276_, 1.0, Operation.ADDITION).setMultiplier(new HungerLevelMultiplier()));
      this.addSkillBranchBonuses(
         "cook_speed", createAttributeBonus(Attributes.f_22283_, 0.02, Operation.MULTIPLY_BASE).setCondition(new FoodLevelCondition(15, -1)), 1, 2
      );
      this.addSkillBonus(
         "cook_speed_notable_1", createAttributeBonus(Attributes.f_22283_, 0.1, Operation.MULTIPLY_BASE).setCondition(new FoodLevelCondition(15, -1))
      );
      this.addSkillBranchBonuses("cook_lesser", new CraftedItemBonus(new FoodCondition(), new FoodSaturationBonus(0.05F)), 1, 6);
      this.addSkillBonus("cook_mastery", new CraftedItemBonus(new FoodCondition(), new FoodSaturationBonus(0.5F)));
      this.addSkillBranchBonuses("cook_crit", new CritChanceBonus(0.02F).setPlayerCondition(new FoodLevelCondition(15, -1)), 1, 2);
      this.addSkillBonus("cook_crit_notable_1", new CritDamageBonus(0.01F).setPlayerMultiplier(new HungerLevelMultiplier()));
      this.addSkillBranchBonuses(
         "cook_crafting",
         new CraftedItemBonus(new FoodCondition(), new FoodEffectBonus(new MobEffectInstance((MobEffect)PSTEffects.LIFE_REGENERATION_BONUS.get(), 1200, 9))),
         1,
         3
      );
      this.addSkillBonus("cook_defensive_crafting_keystone_1", new CraftedItemBonus(new FoodCondition(), new FoodHealingBonus(0.3F)));
      this.addSkillBonus(
         "cook_offensive_crafting_keystone_1",
         new CraftedItemBonus(new FoodCondition(), new FoodEffectBonus(new MobEffectInstance((MobEffect)PSTEffects.CRIT_DAMAGE_BONUS.get(), 1200, 19)))
      );
      this.addSkillBranchBonuses("cook_healing", new HealingBonus(1.0F, 0.1F, new BlockEventListener().setTarget(SkillBonus.Target.PLAYER)), 1, 4);
      this.addSkillBonus(
         "cook_healing_notable_1",
         new HealingBonus(1.0F, 1.0F, new BlockEventListener().setTarget(SkillBonus.Target.PLAYER).setPlayerCondition(new FoodLevelCondition(15, -1)))
      );
      this.addSkillBonus(
         "cook_crafting_notable_1",
         new CraftedItemBonus(new FoodCondition(), new FoodEffectBonus(new MobEffectInstance((MobEffect)PSTEffects.LIFE_REGENERATION_BONUS.get(), 1200, 19)))
      );
      this.addSkillBonus(
         "cook_defensive_keystone_1",
         createAttributeBonus((Attribute)PSTAttributes.BLOCKING.get(), 1.0, Operation.ADDITION).setMultiplier(new HungerLevelMultiplier())
      );
      this.addSkillBonus("cook_offensive_keystone_1", new DamageBonus(0.02F, Operation.MULTIPLY_BASE).setPlayerMultiplier(new HungerLevelMultiplier()));
      this.addSkillBranchBonuses("cook_subclass_1_defensive", PSTAttributes.BLOCKING, 1.0, Operation.ADDITION, 1, 4);
      this.addSkillBonus("cook_subclass_1", new DamageBonus(0.1F, Operation.MULTIPLY_BASE).setPlayerCondition(new HealthPercentageCondition(-1.0F, 0.75F)));
      this.addSkillBonus("cook_subclass_1", new DamageBonus(0.2F, Operation.MULTIPLY_BASE).setPlayerCondition(new HealthPercentageCondition(-1.0F, 0.5F)));
      this.addSkillBranchBonuses("cook_subclass_1_offensive", Attributes.f_22283_, 0.01, Operation.MULTIPLY_BASE, 1, 4);
      this.addSkillBranchBonuses(
         "cook_subclass_1_crafting",
         new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.AXE), new ItemSkillBonus(new CritChanceBonus(0.01F))),
         1,
         5
      );
      this.addSkillBonus(
         "cook_subclass_1_crafting_notable_1",
         new CraftedItemBonus(new EquipmentCondition(EquipmentCondition.Type.AXE), new ItemSkillBonus(new CritChanceBonus(0.05F)))
      );
      this.addSkillBonus(
         "cook_subclass_1_offensive_notable_1",
         createAttributeBonus(Attributes.f_22283_, 0.1, Operation.MULTIPLY_BASE).setCondition(new HealthPercentageCondition(-1.0F, 0.5F))
      );
      this.addSkillBonus("cook_subclass_1_mastery", new CritChanceBonus(0.15F).setPlayerCondition(new HealthPercentageCondition(-1.0F, 0.5F)));
      this.addSkillBonus(
         "cook_subclass_1_mastery",
         new HealingBonus(
            1.0F, 1.0F, new AttackEventListener().setTarget(SkillBonus.Target.PLAYER).setPlayerCondition(new HealthPercentageCondition(-1.0F, 0.5F))
         )
      );
      this.addSkillBonus("cook_subclass_special", new GainedExperienceBonus(0.5F, GainedExperienceBonus.ExperienceSource.MOBS));
      this.addSkillBranchBonuses("cook_subclass_2_defensive", Attributes.f_22284_, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("cook_subclass_2_defensive", PSTAttributes.BLOCKING, 0.5, Operation.ADDITION, 1, 4);
      this.addSkillBonus("cook_subclass_2", new LootDuplicationBonus(0.15F, 1.0F, LootDuplicationBonus.LootType.FISHING));
      this.addSkillBranchBonuses("cook_subclass_2_life", Attributes.f_22276_, 1.0, Operation.ADDITION, 1, 4);
      this.addSkillBranchBonuses("cook_subclass_2_crafting", new GainedExperienceBonus(0.2F, GainedExperienceBonus.ExperienceSource.FISHING), 1, 5);
      this.addSkillBonus("cook_subclass_2_crafting_notable_1", new GainedExperienceBonus(0.5F, GainedExperienceBonus.ExperienceSource.FISHING));
      this.addSkillBonus("cook_subclass_2_life_notable_1", Attributes.f_22276_, 4.0, Operation.ADDITION);
      this.addSkillBonus(
         "cook_subclass_2_life_notable_1", createAttributeBonus(Attributes.f_22286_, 1.0, Operation.ADDITION).setCondition(new FishingCondition())
      );
      this.addSkillBonus("cook_subclass_2_mastery", new GainedExperienceBonus(0.5F, GainedExperienceBonus.ExperienceSource.FISHING));
      this.addSkillBonus("cook_subclass_2_mastery", new LootDuplicationBonus(0.05F, 5.0F, LootDuplicationBonus.LootType.FISHING));
   }

   private void addSkillBranchBonuses(String branchName, Attribute attribute, double amount, Operation operation, int from, int to) {
      for (int node = from; node <= to; node++) {
         this.addSkillBonus(branchName + "_" + node, attribute, amount, operation);
      }
   }

   private void addSkillBranchBonuses(String branchName, SkillBonus<?> bonus, int from, int to) {
      for (int node = from; node <= to; node++) {
         this.addSkillBonus(branchName + "_" + node, bonus);
      }
   }

   private void addSkillBonus(String skillName, Attribute attribute, double amount, Operation operation) {
      this.getSkill(skillName).addSkillBonus(createAttributeBonus(attribute, amount, operation));
   }

   private void addSkillBonus(String skillName, SkillBonus<?> bonus) {
      this.getSkill(skillName).addSkillBonus(bonus);
   }

   @NotNull
   private static AttributeBonus createAttributeBonus(Attribute attribute, double amount, Operation operation) {
      return new AttributeBonus(attribute, new AttributeModifier(UUID.randomUUID(), "SkillBonus", amount, operation));
   }

   private void addSkillBranchBonuses(String branchName, RegistryObject<Attribute> attribute, double amount, Operation operation, int from, int to) {
      this.addSkillBranchBonuses(branchName, (Attribute)attribute.get(), amount, operation, from, to);
   }

   private void addSkillBonus(String skillName, RegistryObject<Attribute> attribute, double amount, Operation operation) {
      this.addSkillBonus(skillName, (Attribute)attribute.get(), amount, operation);
   }

   public void addSkillBranch(String playerClass, String branchName, String iconName, int nodeSize, int from, int to) {
      for (int node = from; node <= to; node++) {
         this.addSkill(playerClass, branchName + "_" + node, iconName, nodeSize);
      }
   }

   private void connectSkillsBetweenClasses(String from, String to) {
      for (int classId = 0; classId < this.playerClasses.length - 1; classId++) {
         this.connectSkills(this.playerClasses[classId] + "_" + from, this.playerClasses[classId + 1] + "_" + to);
      }

      this.connectSkills(this.playerClasses[5] + "_" + from, this.playerClasses[0] + "_" + to);
   }

   private void setSkillBranchPosition(
      String playerClass, String nodeName, int distance, String branchName, float rotation, float rotationPerNode, int from, int to
   ) {
      String branchNode = nodeName;

      for (int node = from; node <= to; node++) {
         this.setSkillPosition(playerClass, branchNode, (float)distance, rotation + (float)(node - from) * rotationPerNode, branchName + "_" + node);
         branchNode = branchName + "_" + node;
      }
   }

   private void setSkillPosition(String playerClass, @Nullable String previousSkillName, float distance, float angle, String skillName) {
      this.setSkillPosition(this.getClassId(playerClass), playerClass + "_" + previousSkillName, distance, angle, playerClass + "_" + skillName);
   }

   private void setSkillPosition(int classId, @Nullable String previousSkillName, float distance, float angle, String skillName) {
      angle *= (float) (Math.PI / 180.0);
      angle += this.getClassBranchRotation(classId);
      PassiveSkill previous = previousSkillName == null ? null : this.getSkill(previousSkillName);
      PassiveSkill skill = this.getSkill(skillName);
      float centerX = 0.0F;
      float centerY = 0.0F;
      int buttonSize = skill.getSkillSize();
      distance += (float)buttonSize / 2.0F;
      if (previous != null) {
         int previousButtonRadius = previous.getSkillSize() / 2;
         distance += (float)previousButtonRadius;
         centerX = previous.getPositionX();
         centerY = previous.getPositionY();
      }

      float skillX = centerX + Mth.m_14031_(angle) * distance;
      float skillY = centerY + Mth.m_14089_(angle) * distance;
      skill.setPosition(skillX, skillY);
      if (previous != null) {
         previous.connect(skill);
      }
   }

   protected int getClassId(String playerClass) {
      return Arrays.asList(this.playerClasses).indexOf(playerClass);
   }

   public float getClassBranchRotation(int classId) {
      return (float)classId * (float) Math.PI * 2.0F / 6.0F;
   }

   private PassiveSkill getSkill(String skillName) {
      return this.getSkills().get(this.getSkillId(skillName));
   }

   private void connectSkills(String skillName1, String skillName2) {
      this.getSkill(skillName1).connect(this.getSkill(skillName2));
   }

   private void connectSkills(String playerClass, String skillName1, String skillName2) {
      this.getSkill(playerClass + "_" + skillName1).connect(this.getSkill(playerClass + "_" + skillName2));
   }

   private ResourceLocation getSkillId(String skillName) {
      return new ResourceLocation("skilltree", skillName);
   }

   private void addSkill(String playerClass, String skillName, String iconName, int buttonSize) {
      this.addSkill(playerClass + "_" + skillName, playerClass + "_" + iconName, buttonSize);
   }

   private void addGateway(String playerClass) {
      ResourceLocation skillId = new ResourceLocation("skilltree", playerClass + "_gateway");
      ResourceLocation backgroundTexture = new ResourceLocation("skilltree", "textures/icons/background/gateway.png");
      ResourceLocation iconTexture = new ResourceLocation("skilltree", "textures/icons/void.png");
      ResourceLocation borderTexture = new ResourceLocation("skilltree", "textures/tooltip/gateway.png");
      PassiveSkill skill = new PassiveSkill(skillId, 30, backgroundTexture, iconTexture, borderTexture, false);
      this.skills.put(skillId, skill);
   }

   private void addGatewayConnection(String gatewayId1, String gatewayId2) {
      this.getSkill(gatewayId1).getLongConnections().add(new ResourceLocation("skilltree", gatewayId2));
   }

   private void addSkill(String name, String icon, int size) {
      ResourceLocation skillId = new ResourceLocation("skilltree", name);
      String background = !name.endsWith("class") && !name.endsWith("subclass_1") && !name.endsWith("subclass_2")
         ? (size == 32 ? "keystone" : (size == 20 ? "notable" : "lesser"))
         : "class";
      ResourceLocation backgroundTexture = new ResourceLocation("skilltree", "textures/icons/background/" + background + ".png");
      ResourceLocation iconTexture = new ResourceLocation("skilltree", "textures/icons/" + icon + ".png");
      String border = size == 32 ? "keystone" : (size == 20 ? "notable" : "lesser");
      ResourceLocation borderTexture = new ResourceLocation("skilltree", "textures/tooltip/" + border + ".png");
      this.skills.put(skillId, new PassiveSkill(skillId, size, backgroundTexture, iconTexture, borderTexture, name.endsWith("class")));
   }

   public void m_213708_(@NotNull CachedOutput output) {
      this.addSkills();
      this.shapeSkillTree();
      this.setSkillsAttributeModifiers();
      this.skills.values().forEach(skill -> this.save(output, skill));
   }

   private void save(CachedOutput output, PassiveSkill skill) {
      Path path = this.dataGenerator.m_123916_().resolve(this.getPath(skill));
      JsonElement json = SkillsReloader.GSON.toJsonTree(skill);

      try {
         DataProvider.m_236072_(output, json, path);
      } catch (IOException var6) {
         var6.printStackTrace();
      }
   }

   public String getPath(PassiveSkill skill) {
      ResourceLocation id = skill.getId();
      return "data/%s/skills/%s.json".formatted(id.m_135827_(), id.m_135815_());
   }

   public Map<ResourceLocation, PassiveSkill> getSkills() {
      return this.skills;
   }

   @NotNull
   public String m_6055_() {
      return "Skills Provider";
   }
}
