package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Cursed_Sandstorm_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class Wrath_of_the_desert extends Item {
   public Wrath_of_the_desert(Properties group) {
      super(group);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      player.m_6672_(hand);
      return InteractionResultHolder.m_19096_(itemstack);
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
         Entity pointedEntity = this.getPlayerLookTarget(level, living);
         int i = this.m_8105_(stack) - timeleft;
         float f = getPowerForTime(i);
         if (!((double)f < 0.1) && !level.f_46443_) {
            float baseYaw = player.m_146908_();
            float pitch = player.m_146909_();

            for (int j = -1; j <= 1; j++) {
               float yaw = baseYaw + (float)(j * 15);
               float directionX = -Mth.m_14031_(yaw * (float) (Math.PI / 180.0)) * Mth.m_14089_(pitch * (float) (Math.PI / 180.0));
               float directionY = -Mth.m_14031_(pitch * (float) (Math.PI / 180.0));
               float directionZ = Mth.m_14089_(yaw * (float) (Math.PI / 180.0)) * Mth.m_14089_(pitch * (float) (Math.PI / 180.0));
               double theta = (double)yaw * (Math.PI / 180.0);
               double vecX = Math.cos(++theta);
               double vecZ = Math.sin(theta);
               double x = player.m_20185_() + vecX;
               double Z = player.m_20189_() + vecZ;
               if (pointedEntity instanceof LivingEntity target && !target.m_7307_(living)) {
                  Cursed_Sandstorm_Entity largefireball = new Cursed_Sandstorm_Entity(
                     player, (double)directionX, (double)directionY, (double)directionZ, player.f_19853_, (float)CMConfig.CursedSandstormDamage * f, target
                  );
                  largefireball.m_6034_(x, player.m_20188_() - 0.5, Z);
                  largefireball.setUp(15);
                  level.m_7967_(largefireball);
                  continue;
               }

               Cursed_Sandstorm_Entity largefireball = new Cursed_Sandstorm_Entity(
                  player, (double)directionX, (double)directionY, (double)directionZ, player.f_19853_, (float)CMConfig.CursedSandstormDamage * f, null
               );
               largefireball.m_6034_(x, player.m_20188_() - 0.5, Z);
               largefireball.setUp(15);
               level.m_7967_(largefireball);
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
            player.m_36246_(Stats.f_12982_.m_12902_(this));
         }
      }
   }

   public boolean m_8120_(ItemStack stack) {
      return true;
   }

   public int m_6473_() {
      return 16;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return !oldStack.m_150930_((Item)ModItems.WRATH_OF_THE_DESERT.get()) || !newStack.m_150930_((Item)ModItems.WRATH_OF_THE_DESERT.get());
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.wrath_of_the_desert.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
