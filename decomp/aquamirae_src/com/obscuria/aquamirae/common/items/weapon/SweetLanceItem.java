package com.obscuria.aquamirae.common.items.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.common.items.ObscureRarity;
import java.util.UUID;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

public class SweetLanceItem extends SwordItem {
   public SweetLanceItem() {
      super(
         AquamiraeTiers.SWEET_LANCE,
         3,
         -3.0F,
         new Properties()
            .m_41486_()
            .m_41497_(ObscureRarity.MYTHIC)
            .m_41489_(new net.minecraft.world.food.FoodProperties.Builder().m_38760_(2).m_38758_(0.3F).m_38767_())
      );
   }

   @NotNull
   public Multimap<Attribute, AttributeModifier> m_7167_(@NotNull EquipmentSlot slot) {
      Multimap<Attribute, AttributeModifier> multimap = super.m_7167_(slot);
      if (slot == EquipmentSlot.MAINHAND) {
         Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
         builder.putAll(multimap);
         builder.put(
            (Attribute)ForgeMod.ATTACK_RANGE.get(),
            new AttributeModifier(UUID.fromString("AB3F54D3-645C-4F36-A467-9C11A33DB1CF"), "Weapon modifier", 0.25, Operation.MULTIPLY_BASE)
         );
         return builder.build();
      } else {
         return multimap;
      }
   }

   @NotNull
   public ItemStack m_5922_(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull LivingEntity entity) {
      if (entity instanceof Player player) {
         world.m_6263_(
            player,
            player.m_20185_(),
            player.m_20186_(),
            player.m_20189_(),
            player.m_7866_(itemstack),
            SoundSource.NEUTRAL,
            1.0F,
            1.0F + (player.m_217043_().m_188501_() - player.m_217043_().m_188501_()) * 0.4F
         );
         FoodProperties foodProperties = itemstack.getFoodProperties(entity);
         if (foodProperties != null) {
            player.m_36324_().m_38707_(foodProperties.m_38744_(), foodProperties.m_38745_());
         }
      }

      itemstack.m_41721_(itemstack.m_41773_() + 10);
      if (itemstack.m_41773_() >= itemstack.m_41776_()) {
         itemstack.m_41774_(1);
      }

      return itemstack;
   }

   @NotNull
   public ItemStack m_7968_() {
      ItemStack stack = new ItemStack(this);
      stack.m_41663_(Enchantments.f_44982_, 1);
      return stack;
   }
}
