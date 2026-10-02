package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class void_forge extends PickaxeItem {
   public void_forge(Tier toolMaterial, Properties props) {
      super(toolMaterial, 8, -3.0F, props);
   }

   public boolean m_7579_(ItemStack heldItemStack, LivingEntity target, LivingEntity attacker) {
      if (!target.f_19853_.f_46443_) {
         target.m_5496_((SoundEvent)ModSounds.HAMMERTIME.get(), 0.5F, 0.5F);
         target.m_147240_(1.0, attacker.m_20185_() - target.m_20185_(), attacker.m_20189_() - target.m_20189_());
      }

      return true;
   }

   public InteractionResult m_6225_(UseOnContext context) {
      ItemStack stack = context.m_43722_();
      Player player = context.m_43723_();
      int standingOnY = Mth.m_14107_(player.m_20186_()) - 3;
      Level world = context.m_43725_();
      if (player.m_21205_() != stack) {
         return super.m_6225_(context);
      } else {
         Vec3 looking = player.m_20154_();
         double headY = player.m_20186_() + 1.0;
         Vec3[] all = new Vec3[]{
            looking,
            looking.m_82524_(0.3F),
            looking.m_82524_(-0.3F),
            looking.m_82524_(0.6F),
            looking.m_82524_(-0.6F),
            looking.m_82524_(0.9F),
            looking.m_82524_(-0.9F)
         };
         world.m_6263_(
            null,
            player.m_20185_(),
            player.m_20186_(),
            player.m_20189_(),
            SoundEvents.f_11913_,
            SoundSource.PLAYERS,
            1.5F,
            1.0F / (player.m_217043_().m_188501_() * 0.4F + 0.8F)
         );
         ScreenShake_Entity.ScreenShake(world, player.m_20182_(), 30.0F, 0.1F, 0, 30);

         for (Vec3 vector3d : all) {
            float f = (float)Mth.m_14136_(vector3d.f_82481_, vector3d.f_82479_);
            player.m_36335_().m_41524_(this, CMConfig.VoidForgeCooldown);

            for (int i = 0; i < 5; i++) {
               double d2 = 1.75 * (double)(i + 1);
               int j = 1 * i;
               this.spawnFangs(
                  player.m_20185_() + (double)Mth.m_14089_(f) * d2, headY, player.m_20189_() + (double)Mth.m_14031_(f) * d2, standingOnY, f, j, world, player
               );
            }
         }

         return InteractionResult.SUCCESS;
      }
   }

   public void setDamage(ItemStack stack, int damage) {
      super.setDamage(stack, 0);
   }

   public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
      return true;
   }

   public boolean m_6832_(ItemStack itemStack, ItemStack itemStackMaterial) {
      return false;
   }

   public int m_6473_() {
      return 16;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.f_44672_ != EnchantmentCategory.BREAKABLE
            && enchantment.f_44672_ == EnchantmentCategory.WEAPON
            && enchantment != Enchantments.f_44983_
         || enchantment.f_44672_ == EnchantmentCategory.DIGGER;
   }

   private boolean spawnFangs(double x, double y, double z, int lowestYCheck, float yRot, int warmupDelayTicks, Level world, Player player) {
      BlockPos blockpos = new BlockPos(x, y, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.m_7495_();
         BlockState blockstate = world.m_8055_(blockpos1);
         if (blockstate.m_60783_(world, blockpos1, Direction.UP)) {
            if (!world.m_46859_(blockpos)) {
               BlockState blockstate1 = world.m_8055_(blockpos);
               VoxelShape voxelshape = blockstate1.m_60812_(world, blockpos);
               if (!voxelshape.m_83281_()) {
                  d0 = voxelshape.m_83297_(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= lowestYCheck);

      if (flag) {
         world.m_7967_(new Void_Rune_Entity(world, x, (double)blockpos.m_123342_() + d0, z, yRot, warmupDelayTicks, (float)CMConfig.Voidrunedamage, player));
         return true;
      } else {
         return false;
      }
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.void_forge.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.void_forge.desc2").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
