package daripher.skilltree.data.generation;

import com.google.gson.JsonElement;
import daripher.skilltree.data.reloader.GemTypesReloader;
import daripher.skilltree.init.PSTAttributes;
import daripher.skilltree.init.PSTTags;
import daripher.skilltree.item.gem.GemType;
import daripher.skilltree.item.gem.bonus.GemBonusProvider;
import daripher.skilltree.item.gem.bonus.GemRemovalBonusProvider;
import daripher.skilltree.item.gem.bonus.RandomGemBonusProvider;
import daripher.skilltree.item.gem.bonus.SimpleGemBonusProvider;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.item.EquipmentCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemTagCondition;
import daripher.skilltree.skill.bonus.condition.item.NoneItemCondition;
import daripher.skilltree.skill.bonus.event.AttackEventListener;
import daripher.skilltree.skill.bonus.event.BlockEventListener;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.item.ItemDurabilityBonus;
import daripher.skilltree.skill.bonus.item.ItemSkillBonus;
import daripher.skilltree.skill.bonus.player.AttributeBonus;
import daripher.skilltree.skill.bonus.player.CritChanceBonus;
import daripher.skilltree.skill.bonus.player.DamageBonus;
import daripher.skilltree.skill.bonus.player.GainedExperienceBonus;
import daripher.skilltree.skill.bonus.player.HealingBonus;
import daripher.skilltree.skill.bonus.player.IncomingHealingBonus;
import daripher.skilltree.skill.bonus.player.JumpHeightBonus;
import daripher.skilltree.skill.bonus.player.LootDuplicationBonus;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.NotNull;

public class PSTGemTypesProvider implements DataProvider {
   private final Map<ResourceLocation, GemType> gemTypes = new HashMap<>();
   private final DataGenerator dataGenerator;

   public PSTGemTypesProvider(DataGenerator dataGenerator) {
      this.dataGenerator = dataGenerator;
   }

   private void addGemTypes() {
      this.addSimpleGems(
         "sapphire",
         this.createGemBonuses(
            new ItemSkillBonus(new DamageBonus(0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22285_, "Sapphire", 0.1F, Operation.ADDITION)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22284_, "Sapphire", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22278_, "Sapphire", 0.01F, Operation.ADDITION)),
            new ItemDurabilityBonus(10.0F, Operation.ADDITION),
            new ItemSkillBonus(new AttributeBonus((Attribute)PSTAttributes.BLOCKING.get(), "Sapphire", 0.005F, Operation.MULTIPLY_BASE))
         )
      );
      this.addSimpleGems(
         "jade",
         this.createGemBonuses(
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22283_, "Jade", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus((Attribute)PSTAttributes.EVASION.get(), "Jade", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new GainedExperienceBonus(0.025F, GainedExperienceBonus.ExperienceSource.MOBS)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22279_, "Jade", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus((Attribute)PSTAttributes.BLOCKING.get(), "Jade", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus((Attribute)PSTAttributes.EVASION.get(), "Jade", 0.005F, Operation.MULTIPLY_BASE))
         )
      );
      this.addSimpleGems(
         "ruby",
         this.createGemBonuses(
            new ItemSkillBonus(new HealingBonus(1.0F, 0.1F, new AttackEventListener().setTarget(SkillBonus.Target.PLAYER))),
            new ItemSkillBonus(new IncomingHealingBonus(0.01F)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22276_, "Ruby", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus((Attribute)PSTAttributes.REGENERATION.get(), "Ruby", 0.01F, Operation.ADDITION)),
            new ItemSkillBonus(new HealingBonus(1.0F, 0.1F, new BlockEventListener().setTarget(SkillBonus.Target.PLAYER))),
            new ItemSkillBonus(new IncomingHealingBonus(0.005F))
         )
      );
      this.addSimpleGems(
         "citrine",
         this.createGemBonuses(
            new ItemSkillBonus(new CritChanceBonus(0.01F)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22286_, "Citrine", 0.1F, Operation.ADDITION)),
            new ItemSkillBonus(new LootDuplicationBonus(0.01F, 2.0F, LootDuplicationBonus.LootType.GEMS)),
            new ItemSkillBonus(new JumpHeightBonus(0.01F)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22286_, "Citrine", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new LootDuplicationBonus(0.01F, 1.0F, LootDuplicationBonus.LootType.FISHING))
         )
      );
      Map<Integer, List<GemBonusProvider>> irisciteBonuses = new HashMap<>();
      this.gemTypes.forEach((key, value) -> {
         String gemId = key.m_135815_();
         int gemTier = Integer.parseInt(gemId.substring(gemId.length() - 1));
         List<GemBonusProvider> providers = irisciteBonuses.getOrDefault(gemTier, new ArrayList<>());
         providers.addAll(value.bonuses().values());
         irisciteBonuses.put(gemTier, providers);
      });

      for (int i = 0; i < 6; i++) {
         ResourceLocation id = new ResourceLocation("skilltree", "iriscite_" + i);
         Map<ItemCondition, GemBonusProvider> bonuses = Map.of(NoneItemCondition.INSTANCE, new RandomGemBonusProvider(irisciteBonuses.get(i)));
         this.gemTypes.put(id, new GemType(id, bonuses));
      }

      ResourceLocation vacuciteId = new ResourceLocation("skilltree", "vacucite_3");
      Map<ItemCondition, GemBonusProvider> vacuciteBonuses = Map.of(NoneItemCondition.INSTANCE, new GemRemovalBonusProvider());
      this.gemTypes.put(vacuciteId, new GemType(vacuciteId, vacuciteBonuses));
   }

   private void addSimpleGems(String name, Map<ItemCondition, ItemBonus<?>> bonuses) {
      for (int i = 0; i < 6; i++) {
         ResourceLocation id = new ResourceLocation("skilltree", "%s_%d".formatted(name, i));
         HashMap<ItemCondition, GemBonusProvider> bonusProviders = new HashMap<>();
         int tier = i;
         bonuses.forEach((c, b) -> {
            SimpleGemBonusProvider bonusProvider = new SimpleGemBonusProvider(b.copy().multiply(tier < 5 ? (double)(1 + tier) : 10.0));
            bonusProviders.put(c, bonusProvider);
         });
         this.gemTypes.put(id, new GemType(id, bonusProviders));
      }
   }

   private Map<ItemCondition, ItemBonus<?>> createGemBonuses(
      ItemBonus<?> weaponBonus,
      ItemBonus<?> chestplateBonus,
      ItemBonus<?> helmetBonus,
      ItemBonus<?> bootsBonus,
      ItemBonus<?> shieldBonus,
      ItemBonus<?> jewelryBonus
   ) {
      Map<ItemCondition, ItemBonus<?>> bonuses = new HashMap<>();
      bonuses.put(new EquipmentCondition(EquipmentCondition.Type.WEAPON), weaponBonus);
      bonuses.put(new EquipmentCondition(EquipmentCondition.Type.CHESTPLATE), chestplateBonus);
      bonuses.put(new EquipmentCondition(EquipmentCondition.Type.HELMET), helmetBonus);
      bonuses.put(new EquipmentCondition(EquipmentCondition.Type.BOOTS), bootsBonus);
      bonuses.put(new EquipmentCondition(EquipmentCondition.Type.SHIELD), shieldBonus);
      bonuses.put(new ItemTagCondition(PSTTags.JEWELRY.f_203868_()), jewelryBonus);
      return bonuses;
   }

   public void m_213708_(@NotNull CachedOutput output) {
      this.addGemTypes();
      this.gemTypes.values().forEach(gemType -> this.save(output, gemType));
   }

   private void save(CachedOutput output, GemType gemType) {
      Path path = this.dataGenerator.m_123916_().resolve(this.getPath(gemType));
      JsonElement json = GemTypesReloader.GSON.toJsonTree(gemType);

      try {
         DataProvider.m_236072_(output, json, path);
      } catch (IOException var6) {
         var6.printStackTrace();
      }
   }

   public String getPath(GemType gemType) {
      ResourceLocation id = gemType.id();
      return "data/%s/gem_types/%s.json".formatted(id.m_135827_(), id.m_135815_());
   }

   public Map<ResourceLocation, GemType> getGemTypes() {
      return this.gemTypes;
   }

   @NotNull
   public String m_6055_() {
      return "Gem Types Provider";
   }
}
