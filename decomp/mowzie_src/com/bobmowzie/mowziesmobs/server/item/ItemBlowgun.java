package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemBlowgun extends BowItem {
   public static final Predicate<ItemStack> DARTS = p_220002_0_ -> p_220002_0_.m_41720_() == ItemHandler.DART;

   public ItemBlowgun(Properties properties) {
      super(properties);
   }

   public void m_5551_(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
      if (entityLiving instanceof Player playerentity) {
         boolean flag = playerentity.m_150110_().f_35937_ || EnchantmentHelper.m_44843_(Enchantments.f_44952_, stack) > 0;
         ItemStack itemstack = playerentity.m_6298_(stack);
         int i = this.m_8105_(stack) - timeLeft;
         i = ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.m_41619_() || flag);
         if (i < 0) {
            return;
         }

         if (!itemstack.m_41619_() || flag) {
            if (itemstack.m_41619_()) {
               itemstack = new ItemStack(Items.f_42412_);
            }

            float f = getArrowVelocity(i);
            if (!((double)f < 0.1)) {
               boolean flag1 = playerentity.m_150110_().f_35937_
                  || itemstack.m_41720_() instanceof ItemDart && ((ItemDart)itemstack.m_41720_()).isInfinite(itemstack, stack, playerentity);
               if (!worldIn.f_46443_) {
                  ArrowItem arrowitem = (ArrowItem)(itemstack.m_41720_() instanceof ItemDart ? itemstack.m_41720_() : ItemHandler.DART);
                  AbstractArrow abstractarrowentity = arrowitem.m_6394_(worldIn, itemstack, playerentity);
                  abstractarrowentity = this.customArrow(abstractarrowentity);
                  abstractarrowentity.m_37251_(playerentity, playerentity.m_146909_(), playerentity.m_146908_(), 0.0F, f * 1.1F, 1.0F);
                  if (f == 1.0F) {
                     abstractarrowentity.m_36762_(true);
                  }

                  int j = EnchantmentHelper.m_44843_(Enchantments.f_44988_, stack);
                  if (j > 0) {
                     abstractarrowentity.m_36781_(abstractarrowentity.m_36789_() + (double)j * 0.5 + 0.5);
                  }

                  int k = EnchantmentHelper.m_44843_(Enchantments.f_44989_, stack);
                  if (k > 0) {
                     abstractarrowentity.m_36735_(k);
                  }

                  if (EnchantmentHelper.m_44843_(Enchantments.f_44990_, stack) > 0) {
                     abstractarrowentity.m_20254_(100);
                  }

                  stack.m_41622_(1, playerentity, player -> player.m_21190_(playerentity.m_7655_()));
                  if (flag1 || playerentity.m_150110_().f_35937_ && (itemstack.m_41720_() == Items.f_42737_ || itemstack.m_41720_() == Items.f_42738_)) {
                     abstractarrowentity.f_36705_ = Pickup.CREATIVE_ONLY;
                  }

                  worldIn.m_7967_(abstractarrowentity);
               }

               worldIn.m_6263_(
                  (Player)null,
                  playerentity.m_20185_(),
                  playerentity.m_20186_(),
                  playerentity.m_20189_(),
                  (SoundEvent)MMSounds.ENTITY_UMVUTHANA_BLOWDART.get(),
                  SoundSource.PLAYERS,
                  1.0F,
                  1.0F / (playerentity.m_217043_().m_188501_() * 0.4F + 1.2F) + f * 0.5F
               );
               if (!flag1 && !playerentity.m_150110_().f_35937_) {
                  itemstack.m_41774_(1);
                  if (itemstack.m_41619_()) {
                     playerentity.m_150109_().m_36057_(itemstack);
                  }
               }

               playerentity.m_36246_(Stats.f_12982_.m_12902_(this));
            }
         }
      }
   }

   public static float getArrowVelocity(int charge) {
      float f = (float)charge / 5.0F;
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   public int m_8105_(ItemStack p_77626_1_) {
      return 72000;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.1").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.2").m_6270_(ItemHandler.TOOLTIP_STYLE));
   }

   public Predicate<ItemStack> m_6437_() {
      return DARTS;
   }

   public Predicate<ItemStack> m_6442_() {
      return DARTS;
   }
}
