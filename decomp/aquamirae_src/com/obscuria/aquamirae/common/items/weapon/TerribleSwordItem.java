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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "weapon"
)
public class TerribleSwordItem extends SwordItem {
   @ClassAbility
   public final Ability ABILITY = Ability.create("aquamirae", "terrible_sword").action((stack, entity, target, context, values) -> {
      if (target == null) {
         return false;
      } else {
         if (target.m_21223_() > 0.0F) {
            entity.m_6469_(DamageSource.f_19323_, (float)((Integer)values.get(0)).intValue());
         }

         return true;
      }
   }).var(1).build(TerribleSwordItem.class);

   public TerribleSwordItem() {
      super(AquamiraeTiers.TERRIBLE_SWORD, 3, -3.0F, new Properties().m_41491_(Aquamirae.TAB));
   }

   @NotNull
   public Multimap<Attribute, AttributeModifier> m_7167_(@NotNull EquipmentSlot slot) {
      Multimap<Attribute, AttributeModifier> multimap = super.m_7167_(slot);
      if (slot == EquipmentSlot.MAINHAND) {
         Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
         builder.putAll(multimap);
         builder.put(
            (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get(),
            new AttributeModifier(UUID.fromString("AB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", 0.5, Operation.MULTIPLY_BASE)
         );
         builder.put(
            (Attribute)ObscureAPIAttributes.CRITICAL_DAMAGE.get(),
            new AttributeModifier(UUID.fromString("AA3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", 4.0, Operation.MULTIPLY_BASE)
         );
         return builder.build();
      } else {
         return multimap;
      }
   }

   public boolean m_7579_(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
      boolean hurt = super.m_7579_(stack, entity, source);
      if (hurt) {
         this.ABILITY.use(stack, source, entity, null);
      }

      return hurt;
   }
}
