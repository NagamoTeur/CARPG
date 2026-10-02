package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import java.util.UUID;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "weapon"
)
public class RemnantsSaberItem extends SwordItem {
   @ClassAbility
   public final Ability ABILITY = Ability.create("aquamirae", "remnants_saber").var(100, "%").build(RemnantsSaberItem.class);

   public RemnantsSaberItem() {
      super(AquamiraeTiers.REMNANTS_SABER, 3, -2.0F, new Properties().m_41491_(Aquamirae.TAB));
   }

   @NotNull
   public Multimap<Attribute, AttributeModifier> m_7167_(@NotNull EquipmentSlot slot) {
      Multimap<Attribute, AttributeModifier> multimap = super.m_7167_(slot);
      if (slot == EquipmentSlot.MAINHAND) {
         Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
         builder.putAll(multimap);
         builder.put(
            (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get(),
            new AttributeModifier(UUID.fromString("AB3F55D3-644C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", 0.1, Operation.MULTIPLY_BASE)
         );
         return builder.build();
      } else {
         return multimap;
      }
   }
}
