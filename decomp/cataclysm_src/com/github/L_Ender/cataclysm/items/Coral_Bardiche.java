package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.entity.projectile.ThrownCoral_Bardiche_Entity;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolActions;

public class Coral_Bardiche extends Item implements Vanishable, More_Tool_Attribute {
   public static final int THROW_THRESHOLD_TIME = 10;
   public static final float BASE_DAMAGE = 8.0F;
   public static final float SHOOT_POWER = 2.5F;
   private final Multimap<Attribute, AttributeModifier> defaultModifiers;

   public Coral_Bardiche(Properties p_43381_) {
      super(p_43381_);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", 9.0, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", -3.2F, Operation.ADDITION));
      builder.put((Attribute)ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, "Tool modifier", 1.5, Operation.ADDITION));
      this.defaultModifiers = builder.build();
   }

   public boolean m_6777_(BlockState p_43409_, Level p_43410_, BlockPos p_43411_, Player p_43412_) {
      return !p_43412_.m_7500_();
   }

   public UseAnim m_6164_(ItemStack p_43417_) {
      return UseAnim.SPEAR;
   }

   public int m_8105_(ItemStack p_43419_) {
      return 72000;
   }

   public void m_5551_(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
      if (p_43396_ instanceof Player player) {
         int i = this.m_8105_(p_43394_) - p_43397_;
         if (i >= 10) {
            int j = EnchantmentHelper.m_44932_(p_43394_);
            if (j <= 0 || player.m_20070_()) {
               if (!p_43395_.f_46443_) {
                  p_43394_.m_41622_(1, player, p_43388_ -> p_43388_.m_21190_(p_43396_.m_7655_()));
                  if (j == 0) {
                     ThrownCoral_Bardiche_Entity throwntrident = new ThrownCoral_Bardiche_Entity(p_43395_, player, p_43394_);
                     throwntrident.m_37251_(player, player.m_146909_(), player.m_146908_(), 0.0F, 2.5F + (float)j * 0.5F, 1.0F);
                     if (player.m_150110_().f_35937_) {
                        throwntrident.f_36705_ = Pickup.CREATIVE_ONLY;
                     }

                     p_43395_.m_7967_(throwntrident);
                     p_43395_.m_6269_((Player)null, throwntrident, SoundEvents.f_12520_, SoundSource.PLAYERS, 1.0F, 1.0F);
                     if (!player.m_150110_().f_35937_) {
                        player.m_150109_().m_36057_(p_43394_);
                     }
                  }
               }

               player.m_36246_(Stats.f_12982_.m_12902_(this));
               if (j > 0) {
                  float f7 = player.m_146908_();
                  float f = player.m_146909_();
                  float f1 = -Mth.m_14031_(f7 * (float) (Math.PI / 180.0)) * Mth.m_14089_(f * (float) (Math.PI / 180.0));
                  float f2 = -Mth.m_14031_(f * (float) (Math.PI / 180.0));
                  float f3 = Mth.m_14089_(f7 * (float) (Math.PI / 180.0)) * Mth.m_14089_(f * (float) (Math.PI / 180.0));
                  float f4 = Mth.m_14116_(f1 * f1 + f2 * f2 + f3 * f3);
                  float f5 = 3.0F * ((1.0F + (float)j) / 4.0F);
                  f1 *= f5 / f4;
                  f2 *= f5 / f4;
                  f3 *= f5 / f4;
                  player.m_5997_((double)f1, (double)f2, (double)f3);
                  player.m_204079_(20);
                  if (player.m_20096_()) {
                     float f6 = 1.1999999F;
                     player.m_6478_(MoverType.SELF, new Vec3(0.0, 1.1999999F, 0.0));
                  }

                  SoundEvent soundevent;
                  if (j >= 3) {
                     soundevent = SoundEvents.f_12519_;
                  } else if (j == 2) {
                     soundevent = SoundEvents.f_12518_;
                  } else {
                     soundevent = SoundEvents.f_12517_;
                  }

                  p_43395_.m_6269_((Player)null, player, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
      ItemStack item = p_77659_2_.m_21120_(p_77659_3_);
      InteractionHand otherhand = p_77659_3_ == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
      ItemStack otheritem = p_77659_2_.m_21120_(otherhand);
      if (otheritem.canPerformAction(ToolActions.SHIELD_BLOCK) && !p_77659_2_.m_36335_().m_41519_(otheritem.m_41720_())) {
         return InteractionResultHolder.m_19100_(item);
      } else if (item.m_41773_() >= item.m_41776_() - 1) {
         return InteractionResultHolder.m_19100_(item);
      } else if (EnchantmentHelper.m_44932_(item) > 0 && !p_77659_2_.m_20070_()) {
         return InteractionResultHolder.m_19100_(item);
      } else {
         p_77659_2_.m_6672_(p_77659_3_);
         return InteractionResultHolder.m_19096_(item);
      }
   }

   public boolean m_7579_(ItemStack p_43390_, LivingEntity p_43391_, LivingEntity p_43392_) {
      p_43390_.m_41622_(1, p_43392_, p_43414_ -> p_43414_.m_21166_(EquipmentSlot.MAINHAND));
      return true;
   }

   public boolean m_8120_(ItemStack stack) {
      return true;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.f_44672_ == EnchantmentCategory.BREAKABLE || enchantment.f_44672_ == EnchantmentCategory.TRIDENT;
   }

   public boolean m_6813_(ItemStack p_43399_, Level p_43400_, BlockState p_43401_, BlockPos p_43402_, LivingEntity p_43403_) {
      if ((double)p_43401_.m_60800_(p_43400_, p_43402_) != 0.0) {
         p_43399_.m_41622_(2, p_43403_, p_43385_ -> p_43385_.m_21166_(EquipmentSlot.MAINHAND));
      }

      return true;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot p_43383_) {
      return p_43383_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.m_7167_(p_43383_);
   }

   public int m_6473_() {
      return 1;
   }
}
