package immersive_armors;

import immersive_armors.armorEffects.ArrowBlockArmorEffect;
import immersive_armors.armorEffects.BerserkArmorEffect;
import immersive_armors.armorEffects.BouncingArmorEffect;
import immersive_armors.armorEffects.DivineArmorEffect;
import immersive_armors.armorEffects.ExplosionProtectionArmorEffect;
import immersive_armors.armorEffects.FireInflictingArmorEffect;
import immersive_armors.armorEffects.FireResistanceArmorEffect;
import immersive_armors.armorEffects.MagicProtectionArmorEffect;
import immersive_armors.armorEffects.SpikesArmorEffect;
import immersive_armors.armorEffects.SteamTechArmorEffect;
import immersive_armors.armorEffects.WeaponEfficiency;
import immersive_armors.armorEffects.WitherArmorEffect;
import immersive_armors.cobalt.registration.Registration;
import immersive_armors.config.Config;
import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;

public interface Items {
   Map<String, Supplier<Item>> coloredItems = new HashMap<>();
   Map<String, Supplier<Item>> items = new HashMap<>();
   Map<String, Map<Supplier<Item>, Float>> lootLookup = new HashMap<>();
   ExtendedArmorMaterial BONE_ARMOR = registerSet(
      new ExtendedArmorMaterial("bone")
         .addLoot("minecraft:chests/village/village_weaponsmith", 1.0F)
         .addLoot("minecraft:chests/jungle_temple", 1.0F)
         .durabilityMultiplier(8)
         .repairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42500_}))
         .protectionAmount(1, 3, 2, 1)
         .enchantability(15)
         .equipSound(SoundEvents.f_12423_)
         .antiSkeleton()
         .weight(-0.02F)
   );
   ExtendedArmorMaterial WITHER_ARMOR = registerSet(
      new ExtendedArmorMaterial("wither")
         .addLoot("minecraft:chests/jungle_temple", 0.5F)
         .addLoot("minecraft:chests/ruined_portal", 1.0F)
         .addLoot("minecraft:chests/bastion_other", 1.0F)
         .durabilityMultiplier(12)
         .repairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42500_}))
         .protectionAmount(2, 4, 3, 2)
         .enchantability(0)
         .effect(new WitherArmorEffect(1.0F, 10))
         .hideCape()
         .equipSound(SoundEvents.f_12559_)
         .antiSkeleton()
         .weight(-0.01F)
   );
   ExtendedArmorMaterial WARRIOR_ARMOR = registerSet(
      new ExtendedArmorMaterial("warrior")
         .addLoot("minecraft:chests/village/village_armorer", 1.0F)
         .addLoot("minecraft:chests/shipwreck_supply", 1.0F)
         .protectionAmount(2, 5, 6, 2)
         .durabilityMultiplier(15)
         .repairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42416_}))
         .toughness(1.0F)
         .enchantability(5)
         .hideCape()
         .effect(new BerserkArmorEffect(0.2F))
         .effect(new WeaponEfficiency(0.05F, AxeItem.class, "axe"))
         .equipSound(SoundEvents.f_11677_)
   );
   ExtendedArmorMaterial HEAVY_ARMOR = registerSet(
      new ExtendedArmorMaterial("heavy")
         .addLoot("minecraft:chests/village/village_armorer", 1.0F)
         .addLoot("minecraft:chests/stronghold_crossing", 1.0F)
         .protectionAmount(4, 6, 5, 3)
         .durabilityMultiplier(20)
         .repairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42416_}))
         .toughness(4.0F)
         .knockbackReduction(0.5F)
         .weight(0.05F)
         .enchantability(6)
         .equipSound(SoundEvents.f_11677_)
   );
   ExtendedArmorMaterial ROBE_ARMOR = registerDyeableSet(
      new ExtendedArmorMaterial("robe")
         .addLoot("minecraft:chests/village/village_shepherd", 0.25F)
         .addLoot("minecraft:chests/woodland_mansion", 1.0F)
         .addLoot("minecraft:chests/igloo_chest", 1.0F)
         .protectionAmount(2, 3, 2, 1)
         .enchantability(50)
         .durabilityMultiplier(14)
         .repairIngredient(() -> Ingredient.m_204132_(ItemTags.f_13167_))
         .color(11546150)
         .effect(new FireResistanceArmorEffect(0.25F))
         .effect(new FireInflictingArmorEffect(10))
         .effect(new MagicProtectionArmorEffect(0.2F))
         .equipSound(SoundEvents.f_12642_)
   );
   ExtendedArmorMaterial SLIME_ARMOR = registerSet(
      new ExtendedArmorMaterial("slime")
         .addLoot("minecraft:chests/simple_dungeon", 0.25F)
         .protectionAmount(3, 5, 4, 2)
         .enchantability(10)
         .durabilityMultiplier(20)
         .repairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42518_}))
         .knockbackReduction(0.25F)
         .effect(new BouncingArmorEffect(0.25F))
         .effect(new ExplosionProtectionArmorEffect(0.2F))
         .equipSound(SoundEvents.f_12388_)
   );
   ExtendedArmorMaterial DIVINE_ARMOR = registerDyeableSet(
      new ExtendedArmorMaterial("divine")
         .addLoot("minecraft:chests/village/village_temple", 0.4F)
         .addLoot("minecraft:chests/bastion_treasure", 0.25F)
         .addLoot("minecraft:chests/woodland_mansion", 0.25F)
         .addLoot("minecraft:chests/desert_pyramid", 1.0F)
         .addLoot("minecraft:blocks/coal_block", 0.1F)
         .protectionAmount(3, 7, 5, 3)
         .durabilityMultiplier(18)
         .repairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42417_}))
         .enchantability(30)
         .effect(new DivineArmorEffect(1200L))
         .color(11546150)
         .hideCape()
         .equipSound(SoundEvents.f_11677_)
   );
   ExtendedArmorMaterial PRISMARINE_ARMOR = registerSet(
      new ExtendedArmorMaterial("prismarine")
         .addLoot("minecraft:chests/underwater_ruin_big", 1.0F)
         .addLoot("minecraft:chests/underwater_ruin_small", 0.5F)
         .protectionAmount(3, 8, 6, 3)
         .enchantability(8)
         .durabilityMultiplier(18)
         .repairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42696_}))
         .weight(0.02F)
         .effect(new SpikesArmorEffect(1))
         .enchantment(Enchantments.f_44973_, 2)
         .equipSound(SoundEvents.f_11677_)
   );
   ExtendedArmorMaterial WOODEN_ARMOR = registerSet(
      new ExtendedArmorMaterial("wooden")
         .addLoot("minecraft:chests/village/village_fletcher", 0.25F)
         .protectionAmount(1, 3, 2, 1)
         .durabilityMultiplier(8)
         .repairIngredient(() -> Ingredient.m_204132_(ItemTags.f_13182_))
         .enchantability(4)
         .effect(new ArrowBlockArmorEffect(0.15F))
         .effect(new ExplosionProtectionArmorEffect(0.1F))
         .equipSound(SoundEvents.f_11678_)
   );
   ExtendedArmorMaterial STEAMPUNK_ARMOR = registerSet(
      new ExtendedArmorMaterial("steampunk")
         .addLoot("minecraft:chests/village/village_toolsmith", 0.25F)
         .addLoot("minecraft:chests/shipwreck_treasure", 1.0F)
         .protectionAmount(3, 6, 3, 2)
         .durabilityMultiplier(10)
         .repairIngredient(() -> Ingredient.m_43929_(new ItemLike[]{net.minecraft.world.item.Items.f_42417_}))
         .enchantability(4)
         .hideCape()
         .effect(new ExplosionProtectionArmorEffect(0.1F))
         .effect(new SteamTechArmorEffect())
         .equipSound(SoundEvents.f_12374_)
   );

   static void bootstrap() {
   }

   static ExtendedArmorMaterial registerSet(ExtendedArmorMaterial material) {
      if (Config.getInstance().enabledArmors.getOrDefault(material.m_6082_(), true)) {
         items.putAll(register(material.m_6082_() + "_helmet", () -> new ExtendedArmorItem(baseProps(), EquipmentSlot.HEAD, material), material));
         items.putAll(register(material.m_6082_() + "_chestplate", () -> new ExtendedArmorItem(baseProps(), EquipmentSlot.CHEST, material), material));
         items.putAll(register(material.m_6082_() + "_leggings", () -> new ExtendedArmorItem(baseProps(), EquipmentSlot.LEGS, material), material));
         items.putAll(register(material.m_6082_() + "_boots", () -> new ExtendedArmorItem(baseProps(), EquipmentSlot.FEET, material), material));
      }

      return material;
   }

   static ExtendedArmorMaterial registerDyeableSet(ExtendedArmorMaterial material) {
      if (Config.getInstance().enabledArmors.getOrDefault(material.m_6082_(), true)) {
         coloredItems.putAll(register(material.m_6082_() + "_helmet", () -> new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.HEAD, material), material));
         coloredItems.putAll(
            register(material.m_6082_() + "_chestplate", () -> new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.CHEST, material), material)
         );
         coloredItems.putAll(
            register(material.m_6082_() + "_leggings", () -> new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.LEGS, material), material)
         );
         coloredItems.putAll(register(material.m_6082_() + "_boots", () -> new DyeableExtendedArmorItem(baseProps(), EquipmentSlot.FEET, material), material));
         items.putAll(coloredItems);
      }

      return material;
   }

   static Map<String, Supplier<Item>> register(String name, Supplier<Item> item, ExtendedArmorMaterial material) {
      Supplier<Item> register = Registration.register(Registry.f_122827_, new ResourceLocation("immersive_armors", name), item);

      for (Entry<String, Float> entry : material.getLoot().entrySet()) {
         lootLookup.putIfAbsent(entry.getKey(), new HashMap<>());
         lootLookup.get(entry.getKey()).put(register, entry.getValue());
      }

      return Collections.singletonMap(name, register);
   }

   static Properties baseProps() {
      return new Properties().m_41491_(ItemGroups.ARMOR);
   }
}
