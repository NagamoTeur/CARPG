package daripher.skilltree.compat.apotheosis.gem;

import com.mojang.serialization.JsonOps;
import daripher.skilltree.init.PSTAttributes;
import daripher.skilltree.skill.bonus.SkillBonus;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import shadows.apotheosis.adventure.affix.socket.gem.Gem;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;

public class PSTGemsProvider extends JsonCodecProvider<Gem> {
   private static final Map<ResourceLocation, Gem> GEMS = new HashMap<>();
   private static final Map<String, GemClass> GEM_CLASSES = new HashMap<>();

   public PSTGemsProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
      super(dataGenerator, existingFileHelper, "skilltree", JsonOps.INSTANCE, PackType.SERVER_DATA, "gems", Gem.CODEC, generateGems());
   }

   private static Map<ResourceLocation, Gem> generateGems() {
      GEM_CLASSES.clear();
      GEMS.clear();
      createGemsClasses();
      addSimpleGems(
         "sapphire",
         createGemBonuses(
            new ItemSkillBonus(new DamageBonus(0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22285_, "Sapphire", 0.1F, Operation.ADDITION)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22284_, "Sapphire", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22278_, "Sapphire", 0.01F, Operation.ADDITION)),
            new ItemDurabilityBonus(10.0F, Operation.ADDITION),
            new ItemSkillBonus(new AttributeBonus((Attribute)PSTAttributes.BLOCKING.get(), "Sapphire", 0.005F, Operation.MULTIPLY_BASE))
         )
      );
      addSimpleGems(
         "jade",
         createGemBonuses(
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22283_, "Jade", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus((Attribute)PSTAttributes.EVASION.get(), "Jade", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new GainedExperienceBonus(0.025F, GainedExperienceBonus.ExperienceSource.MOBS)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22279_, "Jade", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus((Attribute)PSTAttributes.BLOCKING.get(), "Jade", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus((Attribute)PSTAttributes.EVASION.get(), "Jade", 0.005F, Operation.MULTIPLY_BASE))
         )
      );
      addSimpleGems(
         "ruby",
         createGemBonuses(
            new ItemSkillBonus(new HealingBonus(1.0F, 0.1F, new AttackEventListener().setTarget(SkillBonus.Target.PLAYER))),
            new ItemSkillBonus(new IncomingHealingBonus(0.01F)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22276_, "Ruby", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new AttributeBonus((Attribute)PSTAttributes.REGENERATION.get(), "Ruby", 0.01F, Operation.ADDITION)),
            new ItemSkillBonus(new HealingBonus(1.0F, 0.1F, new BlockEventListener().setTarget(SkillBonus.Target.PLAYER))),
            new ItemSkillBonus(new IncomingHealingBonus(0.005F))
         )
      );
      addSimpleGems(
         "citrine",
         createGemBonuses(
            new ItemSkillBonus(new CritChanceBonus(0.01F)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22286_, "Citrine", 0.1F, Operation.ADDITION)),
            new ItemSkillBonus(new LootDuplicationBonus(0.01F, 2.0F, LootDuplicationBonus.LootType.GEMS)),
            new ItemSkillBonus(new JumpHeightBonus(0.01F)),
            new ItemSkillBonus(new AttributeBonus(Attributes.f_22286_, "Citrine", 0.01F, Operation.MULTIPLY_BASE)),
            new ItemSkillBonus(new LootDuplicationBonus(0.01F, 1.0F, LootDuplicationBonus.LootType.FISHING))
         )
      );
      return GEMS;
   }

   private static void addSimpleGems(String name, List<PSTGemBonus> bonuses) {
      for (int i = 0; i < 6; i++) {
         ResourceLocation id = new ResourceLocation("skilltree", "%s_%d".formatted(name, i));
         List<GemBonus> bonusProviders = new ArrayList<>();
         int tier = i;
         bonuses.forEach(b -> {
            PSTGemBonus bonusProvider = b.copy().multiply(tier < 5 ? (float)(1 + tier) : 10.0F);
            bonusProviders.add(bonusProvider);
         });
         GEMS.put(id, createGem(bonusProviders));
      }
   }

   private static List<PSTGemBonus> createGemBonuses(
      ItemBonus<?> weaponBonus,
      ItemBonus<?> chestplateBonus,
      ItemBonus<?> helmetBonus,
      ItemBonus<?> bootsBonus,
      ItemBonus<?> shieldBonus,
      ItemBonus<?> jewelryBonus
   ) {
      List<PSTGemBonus> bonuses = new ArrayList<>();
      bonuses.add(new PSTGemBonus(GEM_CLASSES.get("weapon"), weaponBonus));
      bonuses.add(new PSTGemBonus(GEM_CLASSES.get("chestplate"), chestplateBonus));
      bonuses.add(new PSTGemBonus(GEM_CLASSES.get("helmet"), helmetBonus));
      bonuses.add(new PSTGemBonus(GEM_CLASSES.get("boots"), bootsBonus));
      bonuses.add(new PSTGemBonus(GEM_CLASSES.get("shield"), shieldBonus));
      bonuses.add(new PSTGemBonus(GEM_CLASSES.get("jewelry"), jewelryBonus));
      return bonuses;
   }

   private static void createGemsClasses() {
      GEM_CLASSES.put("shield", new GemClass("shield", Set.of(LootCategory.SHIELD)));
      GEM_CLASSES.put("helmet", new GemClass("helmet", Set.of(LootCategory.HELMET)));
      GEM_CLASSES.put("chestplate", new GemClass("chestplate", Set.of(LootCategory.CHESTPLATE)));
      GEM_CLASSES.put("boots", new GemClass("boots", Set.of(LootCategory.BOOTS)));
      GEM_CLASSES.put("jewelry", new GemClass("jewelry", Set.of(getRingCategory(), getNecklaceCategory())));
      GEM_CLASSES.put(
         "weapon", new GemClass("weapon", Set.of(LootCategory.HEAVY_WEAPON, LootCategory.SWORD, LootCategory.TRIDENT, LootCategory.BOW, LootCategory.CROSSBOW))
      );
   }

   public static Map<ResourceLocation, Gem> getGems() {
      return GEMS;
   }

   private static LootCategory getNecklaceCategory() {
      return LootCategory.byId("curios:necklace");
   }

   private static LootCategory getRingCategory() {
      return LootCategory.byId("curios:ring");
   }

   private static Gem createGem(List<GemBonus> gemBonuses) {
      ResourceLocation fakeDimension = new ResourceLocation("skilltree", "fake_dimension");
      return new Gem(0, 0.0F, Set.of(fakeDimension), Optional.of(LootRarity.EPIC), Optional.of(LootRarity.EPIC), gemBonuses, false, Optional.empty());
   }
}
