package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.Bonus;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassBonus;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.api.common.classes.Bonus.Operation;
import com.obscuria.obscureapi.api.common.classes.Bonus.Type;
import com.obscuria.obscureapi.common.items.ObscureRarity;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import java.util.UUID;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "weapon"
)
public class CoralLanceItem extends SwordItem {
   @ClassAbility
   public final Ability ABILITY = Ability.create("aquamirae", "coral_lance").var(50, "%").build(CoralLanceItem.class);
   @ClassBonus
   public final Bonus BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "armor").type(Type.POWER, Operation.PERCENT).value(50).build();

   public CoralLanceItem() {
      super(AquamiraeTiers.CORAL_LANCE, 3, -2.8F, new Properties().m_41486_().m_41497_(ObscureRarity.MYTHIC).m_41491_(Aquamirae.TAB));
   }

   public void m_6787_(@NotNull CreativeModeTab tab, @NotNull NonNullList<ItemStack> list) {
      if (this.m_220152_(tab)) {
         list.add(this.m_7968_());
      }
   }

   @NotNull
   public Multimap<Attribute, AttributeModifier> m_7167_(@NotNull EquipmentSlot slot) {
      Multimap<Attribute, AttributeModifier> multimap = super.m_7167_(slot);
      if (slot == EquipmentSlot.MAINHAND) {
         Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
         builder.putAll(multimap);
         builder.put(
            (Attribute)ObscureAPIAttributes.ACCURACY.get(),
            new AttributeModifier(
               UUID.fromString("AB3F55D3-646C-4F38-A497-1C13A33DB5CF"),
               "Weapon modifier",
               0.3,
               net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_BASE
            )
         );
         builder.put(
            (Attribute)ForgeMod.ATTACK_RANGE.get(),
            new AttributeModifier(
               UUID.fromString("A23F54D3-645C-4F36-A497-9C11A337B5CF"),
               "Weapon modifier",
               0.25,
               net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_BASE
            )
         );
         return builder.build();
      } else {
         return multimap;
      }
   }

   @NotNull
   public ItemStack m_7968_() {
      ItemStack stack = new ItemStack(this);
      stack.m_41663_(Enchantments.f_44986_, 4);
      return stack;
   }
}
