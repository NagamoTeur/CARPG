package shadows.apotheosis.adventure.loot;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.placebo.codec.PlaceboCodecs;

public final class LootCategory {
   private static final Map<String, LootCategory> BY_ID_INTERNAL = new HashMap<>();
   private static final List<LootCategory> VALUES_INTERNAL = new LinkedList<>();
   public static final Map<String, LootCategory> BY_ID = Collections.unmodifiableMap(BY_ID_INTERNAL);
   public static final List<LootCategory> VALUES = Collections.unmodifiableList(VALUES_INTERNAL);
   public static final Codec<LootCategory> CODEC = ExtraCodecs.m_184405_(LootCategory::getName, LootCategory::byId);
   public static final Codec<Set<LootCategory>> SET_CODEC = PlaceboCodecs.setOf(CODEC);
   public static final LootCategory BOW = register("bow", s -> s.m_41720_() instanceof BowItem, arr(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
   public static final LootCategory CROSSBOW = register(
      "crossbow", s -> s.m_41720_() instanceof CrossbowItem, arr(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)
   );
   public static final LootCategory PICKAXE = register("pickaxe", s -> s.canPerformAction(ToolActions.PICKAXE_DIG), arr(EquipmentSlot.MAINHAND));
   public static final LootCategory SHOVEL = register("shovel", s -> s.canPerformAction(ToolActions.SHOVEL_DIG), arr(EquipmentSlot.MAINHAND));
   public static final LootCategory HEAVY_WEAPON = register("heavy_weapon", new ShieldBreakerTest(), arr(EquipmentSlot.MAINHAND));
   public static final LootCategory HELMET = register("helmet", armorSlot(EquipmentSlot.HEAD), arr(EquipmentSlot.HEAD));
   public static final LootCategory CHESTPLATE = register("chestplate", armorSlot(EquipmentSlot.CHEST), arr(EquipmentSlot.CHEST));
   public static final LootCategory LEGGINGS = register("leggings", armorSlot(EquipmentSlot.LEGS), arr(EquipmentSlot.LEGS));
   public static final LootCategory BOOTS = register("boots", armorSlot(EquipmentSlot.FEET), arr(EquipmentSlot.FEET));
   public static final LootCategory SHIELD = register(
      "shield", s -> s.canPerformAction(ToolActions.SHIELD_BLOCK), arr(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)
   );
   public static final LootCategory TRIDENT = register("trident", s -> s.m_41720_() instanceof TridentItem, arr(EquipmentSlot.MAINHAND));
   public static final LootCategory SWORD = register(
      "sword",
      s -> s.canPerformAction(ToolActions.SWORD_DIG)
            || s.m_41720_().getAttributeModifiers(EquipmentSlot.MAINHAND, s).get(Attributes.f_22281_).stream().anyMatch(m -> m.m_22218_() > 0.0),
      arr(EquipmentSlot.MAINHAND)
   );
   public static final LootCategory NONE = register("none", Predicates.alwaysFalse(), new EquipmentSlot[0]);
   private final String name;
   private final Predicate<ItemStack> validator;
   private final EquipmentSlot[] slots;

   private LootCategory(String name, Predicate<ItemStack> validator, EquipmentSlot[] slots) {
      this.name = (String)Preconditions.checkNotNull(name);
      this.validator = (Predicate<ItemStack>)Preconditions.checkNotNull(validator);
      this.slots = (EquipmentSlot[])Preconditions.checkNotNull(slots);
   }

   public String getDescId() {
      return "text.apotheosis.category." + this.name;
   }

   public String getDescIdPlural() {
      return this.getDescId() + ".plural";
   }

   public String getName() {
      return this.name;
   }

   public EquipmentSlot[] getSlots() {
      return this.slots;
   }

   public boolean isValid(ItemStack stack) {
      return this.validator.test(stack);
   }

   public boolean isArmor() {
      return this == HELMET || this == CHESTPLATE || this == LEGGINGS || this == BOOTS;
   }

   public boolean isBreaker() {
      return this == PICKAXE || this == SHOVEL;
   }

   public boolean isRanged() {
      return this == BOW || this == CROSSBOW || this == TRIDENT;
   }

   public boolean isDefensive() {
      return this.isArmor() || this == SHIELD;
   }

   public boolean isLightWeapon() {
      return this == SWORD || this == TRIDENT;
   }

   public boolean isWeapon() {
      return this == SWORD || this == HEAVY_WEAPON || this == TRIDENT;
   }

   public boolean isWeaponOrShield() {
      return this.isLightWeapon() || this == SHIELD;
   }

   public boolean isNone() {
      return this == NONE;
   }

   @Override
   public String toString() {
      return String.format("LootCategory[%s]", this.name);
   }

   @Override
   public int hashCode() {
      return this.name.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof LootCategory cat && cat.name.equals(this.name)) {
         return true;
      }

      return false;
   }

   public static final LootCategory register(@Nullable LootCategory orderRef, String name, Predicate<ItemStack> validator, EquipmentSlot[] slots) {
      LootCategory cat = new LootCategory(name, validator, slots);
      if (BY_ID_INTERNAL.containsKey(name)) {
         throw new IllegalArgumentException("Cannot register a loot category with a duplicate name.");
      } else {
         BY_ID_INTERNAL.put(name, cat);
         int idx = VALUES_INTERNAL.size();
         if (orderRef != null) {
            idx = VALUES_INTERNAL.indexOf(orderRef);
         }

         VALUES_INTERNAL.add(idx, cat);
         return cat;
      }
   }

   @Nullable
   public static LootCategory byId(String name) {
      return BY_ID.get(name);
   }

   public static LootCategory forItem(ItemStack item) {
      if (item.m_41619_()) {
         return NONE;
      } else {
         LootCategory override = AdventureConfig.TYPE_OVERRIDES.get(ForgeRegistries.ITEMS.getKey(item.m_41720_()));
         if (override != null) {
            return override;
         } else {
            for (LootCategory c : VALUES) {
               if (c.isValid(item)) {
                  return c;
               }
            }

            return NONE;
         }
      }
   }

   private static EquipmentSlot[] arr(EquipmentSlot... s) {
      return s;
   }

   private static Predicate<ItemStack> armorSlot(EquipmentSlot slot) {
      return stack -> {
         if (stack.m_150930_(Items.f_42047_)) {
            return false;
         } else {
            if (stack.m_41720_() instanceof BlockItem bi && bi.m_40614_() instanceof AbstractSkullBlock) {
               return false;
            }

            return LivingEntity.m_147233_(stack) == slot;
         }
      };
   }

   static final LootCategory register(String name, Predicate<ItemStack> validator, EquipmentSlot[] slots) {
      return register(null, name, validator, slots);
   }
}
