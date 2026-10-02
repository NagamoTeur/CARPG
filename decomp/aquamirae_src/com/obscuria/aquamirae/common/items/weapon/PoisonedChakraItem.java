package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.aquamirae.registry.AquamiraeCreativeTab;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.api.common.DynamicProjectile;
import com.obscuria.obscureapi.api.common.DynamicProjectileItem;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import com.obscuria.obscureapi.api.common.classes.Ability.Cost.Type;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import java.util.UUID;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@ClassItem(
   clazz = "aquamirae:sea_wolf",
   type = "weapon"
)
@DynamicProjectileItem(
   mirror = true,
   distance = true,
   fastSpin = true
)
public class PoisonedChakraItem extends TieredItem implements Vanishable {
   @ClassAbility
   public final Ability ABILITY = Ability.create("aquamirae", "poisoned_chakra")
      .cost(Type.COOLDOWN, 30)
      .action(
         (stack, entity, target, context, values) -> {
            stack.m_220157_(3, entity.m_217043_(), null);
            DynamicProjectile.create(
               (EntityType)AquamiraeEntities.POISONED_CHAKRA.get(),
               entity,
               entity.f_19853_,
               stack,
               (Integer)values.get(0),
               0.0F,
               20 * (Integer)values.get(1),
               1000
            );
            return true;
         }
      )
      .var(3, "")
      .var(30, "s")
      .build(PoisonedBladeItem.class);

   public PoisonedChakraItem() {
      super(AquamiraeTiers.POISONED_CHAKRA, new Properties().m_41491_(Aquamirae.TAB));
   }

   public void m_6787_(@NotNull CreativeModeTab tab, @NotNull NonNullList<ItemStack> list) {
      super.m_6787_(tab, list);
      if (tab == Aquamirae.TAB) {
         list.addAll(AquamiraeCreativeTab.poisonedChakra());
      }
   }

   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
      Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
      if (slot == EquipmentSlot.OFFHAND) {
         Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
         builder.putAll(multimap);
         builder.put(
            (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get(),
            new AttributeModifier(UUID.fromString("A33F51D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", 0.1, Operation.MULTIPLY_BASE)
         );
         return builder.build();
      } else {
         return multimap;
      }
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
      ItemStack stack = entity.m_21120_(hand);
      if (world instanceof ServerLevel) {
         this.ABILITY.use(stack, entity, null, null);
         return InteractionResultHolder.m_19090_(stack);
      } else {
         return InteractionResultHolder.m_19098_(stack);
      }
   }
}
