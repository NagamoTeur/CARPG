package net.xylonity.knightquest.common.material;

import dev.xylonity.knightlib.compat.registry.KnightLibItems;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public enum KQArmorMaterials implements ArmorMaterial {
   APPLE_SET(
      "apple",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   BAMBOOSET_BLUE(
      "bamboo_blue",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   BAMBOOSET_GREEN(
      "bamboo_green",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   BAMBOOSET(
      "bamboo",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   BATSET(
      "bat",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   BLAZESET(
      "blaze",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   BOWSET(
      "bow",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   HORNSET(
      "horn",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   CREEPERSET(
      "creeper",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   DEEPSLATESET(
      "deepslate",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   DRAGONSET(
      "dragon",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   ENDERMANSET(
      "enderman",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   EVOKERSET(
      "evoker",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   FORZESET(
      "forze",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   HOLLOWSET(
      "hollow",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   NETHERSET(
      "nether",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   VETERANSET(
      "veteran",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   PATHSET(
      "path",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   PHANTOMSET(
      "phantom",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   SEASET(
      "sea",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   SHIELDSET(
      "shield",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   SILVERSET(
      "silver",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   SILVERFISHSET(
      "silverfish",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   SKELETONSET(
      "skeleton",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   SPIDERSET(
      "spider",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   WARLORDSET(
      "warlord",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   STRAWHATSET(
      "strawhat",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   PIRATESET(
      "pirate",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   CONQUISTADORSET(
      "conquistador",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   ZOMBIESET(
      "zombie",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   HUSKSET(
      "husk",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   WITHERSET(
      "wither",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   SQUIRESET(
      "squire",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   TUNIC_BLUE(
      "tunic_blue",
      20,
      new int[]{2, 5, 5, 2},
      12,
      SoundEvents.f_11678_,
      0.5F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   TUNIC_GREEN(
      "tunic_green",
      20,
      new int[]{2, 5, 5, 2},
      12,
      SoundEvents.f_11678_,
      0.5F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   TUNIC_YELLOW(
      "tunic_yellow",
      20,
      new int[]{2, 5, 5, 2},
      12,
      SoundEvents.f_11678_,
      0.5F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   TUNIC_RED(
      "tunic_red",
      20,
      new int[]{2, 5, 5, 2},
      12,
      SoundEvents.f_11678_,
      0.5F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   TUNIC_SEA(
      "tunic_sea",
      20,
      new int[]{2, 5, 5, 2},
      12,
      SoundEvents.f_11678_,
      0.5F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   CHAINMAIL(
      "chainmail",
      20,
      new int[]{2, 5, 5, 2},
      12,
      SoundEvents.f_11677_,
      0.5F,
      0.0F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   WITCH(
      "witch",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   POLAR(
      "polar",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   SHINOBI(
      "shinobi",
      35,
      new int[]{3, 6, 8, 3},
      20,
      SoundEvents.f_11673_,
      2.5F,
      0.05F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   SKULK(
      "skulk",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   ),
   TENGU(
      "tengu",
      40,
      new int[]{3, 6, 8, 3},
      25,
      SoundEvents.f_11679_,
      4.0F,
      0.1F,
      () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)KnightLibItems.GREAT_ESSENCE.get()})
   );

   private final String name;
   private final int durabilityMultiplier;
   private final int[] protectionAmounts;
   private final int enchantmentValue;
   private final SoundEvent equipSound;
   private final float toughness;
   private final float knockbackResistance;
   private final Supplier<Ingredient> repairIngredient;
   private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

   private KQArmorMaterials(
      String name,
      int durabilityMultiplier,
      int[] protectionAmounts,
      int enchantmentValue,
      SoundEvent equipSound,
      float toughness,
      float knockbackResistance,
      Supplier<Ingredient> repairIngredient
   ) {
      this.name = name;
      this.durabilityMultiplier = durabilityMultiplier;
      this.protectionAmounts = protectionAmounts;
      this.enchantmentValue = enchantmentValue;
      this.equipSound = equipSound;
      this.toughness = toughness;
      this.knockbackResistance = knockbackResistance;
      this.repairIngredient = repairIngredient;
   }

   public int m_7366_(EquipmentSlot equipmentSlot) {
      return HEALTH_PER_SLOT[equipmentSlot.m_20749_()] * this.durabilityMultiplier;
   }

   public int m_7365_(EquipmentSlot equipmentSlot) {
      return this.protectionAmounts[equipmentSlot.m_20749_()];
   }

   public int m_6646_() {
      return this.enchantmentValue;
   }

   @NotNull
   public SoundEvent m_7344_() {
      return this.equipSound;
   }

   @NotNull
   public Ingredient m_6230_() {
      return this.repairIngredient.get();
   }

   @NotNull
   public String m_6082_() {
      return "knightquest:" + this.name;
   }

   public String getKeyName() {
      return this.name;
   }

   public float m_6651_() {
      return this.toughness;
   }

   public float m_6649_() {
      return this.knockbackResistance;
   }
}
