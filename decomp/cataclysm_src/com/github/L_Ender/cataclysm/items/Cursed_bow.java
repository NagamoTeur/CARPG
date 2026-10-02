package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.ForgeEventFactory;

public class Cursed_bow extends ProjectileWeaponItem {
   public Cursed_bow(Properties group) {
      super(group);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
      ItemStack itemstack = p_40673_.m_21120_(p_40674_);
      boolean flag = !p_40673_.m_6298_(itemstack).m_41619_();
      InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, p_40672_, p_40673_, p_40674_, flag);
      if (ret != null) {
         return ret;
      } else if (!p_40673_.m_150110_().f_35937_ && !flag) {
         return InteractionResultHolder.m_19100_(itemstack);
      } else {
         p_40673_.m_6672_(p_40674_);
         return InteractionResultHolder.m_19096_(itemstack);
      }
   }

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public void m_6883_(ItemStack stack, Level level, Entity entity, int i, boolean held) {
      boolean var10000;
      label30: {
         super.m_6883_(stack, level, entity, i, held);
         if (entity instanceof LivingEntity living && living.m_21211_().equals(stack)) {
            var10000 = true;
            break label30;
         }

         var10000 = false;
      }

      boolean using = var10000;
      int useTime = getUseTime(stack);
      if (level.f_46443_) {
         CompoundTag tag = stack.m_41784_();
         if (tag.m_128451_("PrevUseTime") != tag.m_128451_("UseTime")) {
            tag.m_128405_("PrevUseTime", getUseTime(stack));
         }

         int maxLoadTime = getMaxLoadTime();
         if (using && useTime < maxLoadTime) {
            int set = useTime + 1;
            setUseTime(stack, set);
         }
      }

      if (!using && (float)useTime > 0.0F) {
         setUseTime(stack, Math.max(0, useTime - 5));
      }
   }

   private static int getMaxLoadTime() {
      return 20;
   }

   public static int getUseTime(ItemStack stack) {
      CompoundTag compoundtag = stack.m_41783_();
      return compoundtag != null ? compoundtag.m_128451_("UseTime") : 0;
   }

   public static void setUseTime(ItemStack stack, int useTime) {
      CompoundTag tag = stack.m_41784_();
      tag.m_128405_("PrevUseTime", getUseTime(stack));
      tag.m_128405_("UseTime", useTime);
   }

   public static float getLerpedUseTime(ItemStack stack, float f) {
      CompoundTag compoundtag = stack.m_41783_();
      float prev = compoundtag != null ? (float)compoundtag.m_128451_("PrevUseTime") : 0.0F;
      float current = compoundtag != null ? (float)compoundtag.m_128451_("UseTime") : 0.0F;
      return prev + f * (current - prev);
   }

   public static float getPullingAmount(ItemStack itemStack, float partialTicks) {
      return Math.min(getLerpedUseTime(itemStack, partialTicks) / (float)getMaxLoadTime(), 1.0F);
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BOW;
   }

   public static float getPowerForTime(int i) {
      float f = (float)i / (float)getMaxLoadTime();
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   private Entity getPlayerLookTarget(Level level, LivingEntity living) {
      Entity pointedEntity = null;
      double range = 40.0;
      Vec3 srcVec = living.m_146892_();
      Vec3 lookVec = living.m_20252_(1.0F);
      Vec3 destVec = srcVec.m_82520_(lookVec.m_7096_() * range, lookVec.m_7098_() * range, lookVec.m_7094_() * range);
      float var9 = 2.0F;
      List<Entity> possibleList = level.m_45933_(
         living,
         living.m_20191_()
            .m_82363_(lookVec.m_7096_() * range, lookVec.m_7098_() * range, lookVec.m_7094_() * range)
            .m_82377_((double)var9, (double)var9, (double)var9)
      );
      double hitDist = 0.0;

      for (Entity possibleEntity : possibleList) {
         AABB collisionBB = possibleEntity.m_20191_().m_82377_(1.0, 1.0, 1.0);
         Optional<Vec3> interceptPos = collisionBB.m_82371_(srcVec, destVec);
         if (collisionBB.m_82390_(srcVec)) {
            if (0.0 < hitDist || hitDist == 0.0) {
               pointedEntity = possibleEntity;
               hitDist = 0.0;
            }
         } else if (interceptPos.isPresent()) {
            double possibleDist = srcVec.m_82554_(interceptPos.get());
            if (possibleDist < hitDist || hitDist == 0.0) {
               pointedEntity = possibleEntity;
               hitDist = possibleDist;
            }
         }
      }

      return pointedEntity;
   }

   public void m_5551_(ItemStack stack, Level level, LivingEntity living, int timeleft) {
      if (living instanceof Player player) {
         boolean flag = player.m_150110_().f_35937_ || EnchantmentHelper.m_44843_(Enchantments.f_44952_, stack) > 0;
         ItemStack itemstack = player.m_6298_(stack);
         Entity pointedEntity = this.getPlayerLookTarget(level, living);
         int i = this.m_8105_(stack) - timeleft;
         i = ForgeEventFactory.onArrowLoose(stack, level, player, i, !itemstack.m_41619_() || flag);
         if (i < 0) {
            return;
         }

         if (!itemstack.m_41619_() || flag) {
            if (itemstack.m_41619_()) {
               itemstack = new ItemStack(Items.f_42412_);
            }

            float f = getPowerForTime(i);
            if (!((double)f < 0.1)) {
               boolean flag1 = player.m_150110_().f_35937_
                  || itemstack.m_41720_() instanceof ArrowItem && ((ArrowItem)itemstack.m_41720_()).isInfinite(itemstack, stack, player);
               if (!level.f_46443_) {
                  ArrowItem arrowItem = itemstack.m_41720_() instanceof ArrowItem arrow ? arrow : (ArrowItem)Items.f_42412_;
                  boolean hommingArrows = itemstack.m_150930_(Items.f_42412_);
                  int arrowcount = itemstack.m_150930_(Items.f_42412_) ? 3 : 2;
                  float offsetangle = itemstack.m_150930_(Items.f_42412_) ? 12.0F : 3.0F;

                  for (int j = 0; j < arrowcount; j++) {
                     AbstractArrow abstractarrow;
                     abstractarrow = arrowItem.m_6394_(level, itemstack, player);
                     abstractarrow = this.customArrow(abstractarrow);
                     int p = EnchantmentHelper.m_44843_(Enchantments.f_44988_, stack);
                     label106:
                     if (hommingArrows) {
                        if (pointedEntity instanceof LivingEntity target && !target.m_7307_(living)) {
                           Phantom_Arrow_Entity hommingArrowEntity = new Phantom_Arrow_Entity(level, living, target);
                           hommingArrowEntity.m_36781_(CMConfig.PlayerPhantomArrowbasedamage * (double)f);
                           if (p > 0) {
                              hommingArrowEntity.m_36781_(hommingArrowEntity.m_36789_() + (double)p * 0.35 + 0.5);
                           }

                           abstractarrow = hommingArrowEntity;
                           break label106;
                        }

                        Phantom_Arrow_Entity hommingArrowEntity = new Phantom_Arrow_Entity(level, living);
                        hommingArrowEntity.m_36781_(CMConfig.PlayerPhantomArrowbasedamage * (double)f);
                        if (p > 0) {
                           hommingArrowEntity.m_36781_(hommingArrowEntity.m_36789_() + (double)p * 0.35 + 0.5);
                        }

                        abstractarrow = hommingArrowEntity;
                     } else if (p > 0) {
                        abstractarrow.m_36781_(abstractarrow.m_36789_() + (double)p * 0.7 + 0.5);
                     }

                     if (j != 1) {
                        abstractarrow.f_36705_ = Pickup.CREATIVE_ONLY;
                     } else if (flag1 || player.m_150110_().f_35937_ && (itemstack.m_41720_() == Items.f_42737_ || itemstack.m_41720_() == Items.f_42738_)) {
                        abstractarrow.f_36705_ = Pickup.ALLOWED;
                     }

                     abstractarrow.m_37251_(
                        player, player.m_146909_(), player.m_146908_() + ((float)j - (float)(arrowcount - 1) / 2.0F) * offsetangle, 0.0F, f * 3.0F, 1.0F
                     );
                     if (f == 1.0F) {
                        abstractarrow.m_36762_(true);
                     }

                     int k = EnchantmentHelper.m_44843_(Enchantments.f_44989_, stack);
                     if (k > 0) {
                        abstractarrow.m_36735_(k);
                     }

                     if (EnchantmentHelper.m_44843_(Enchantments.f_44990_, stack) > 0) {
                        abstractarrow.m_20254_(100);
                     }

                     level.m_7967_(abstractarrow);
                  }
               }

               level.m_6263_(
                  (Player)null,
                  player.m_20185_(),
                  player.m_20186_(),
                  player.m_20189_(),
                  SoundEvents.f_11687_,
                  SoundSource.PLAYERS,
                  1.0F,
                  1.0F / (level.m_213780_().m_188501_() * 0.4F + 1.2F) + f * 0.5F
               );
               if (!flag1 && !player.m_150110_().f_35937_) {
                  itemstack.m_41774_(1);
                  if (itemstack.m_41619_()) {
                     player.m_150109_().m_36057_(itemstack);
                  }
               }

               player.m_36246_(Stats.f_12982_.m_12902_(this));
            }
         }
      }
   }

   public AbstractArrow customArrow(AbstractArrow arrow) {
      return arrow;
   }

   public boolean m_8120_(ItemStack stack) {
      return true;
   }

   public int m_6473_() {
      return 16;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.f_44672_ == EnchantmentCategory.BOW && enchantment != Enchantments.f_44952_ && enchantment != Enchantments.f_44990_;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return !oldStack.m_150930_((Item)ModItems.CURSED_BOW.get()) || !newStack.m_150930_((Item)ModItems.CURSED_BOW.get());
   }

   public Predicate<ItemStack> m_6437_() {
      return f_43005_;
   }

   public int m_6615_() {
      return 64;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.cursed_bow.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.cursed_bow2.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
