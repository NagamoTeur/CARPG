package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

public class infernal_forge extends PickaxeItem {
   public infernal_forge(Tier toolMaterial, Properties props) {
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
      if (player.m_21205_() == stack) {
         this.EarthQuake(context);
         player.m_36335_().m_41524_(this, CMConfig.InfernalForgeCooldown);
         return InteractionResult.SUCCESS;
      } else {
         return super.m_6225_(context);
      }
   }

   private void EarthQuake(UseOnContext context) {
      Player player = context.m_43723_();
      Level world = context.m_43725_();
      boolean berserk = player.m_21233_() * 1.0F / 2.0F >= player.m_21223_();
      double radius = 4.0;
      ScreenShake_Entity.ScreenShake(world, player.m_20182_(), 30.0F, 0.1F, 0, 30);
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

      for (Entity entity : world.m_45933_(player, player.m_20191_().m_82377_(radius, radius, radius))) {
         if (entity instanceof LivingEntity) {
            entity.m_6469_(DamageSource.m_19370_(player), (float)player.m_21133_(Attributes.f_22281_));
            entity.m_20256_(entity.m_20184_().m_82542_(0.0, 2.0, 0.0));
            if (berserk) {
               entity.m_20254_(5);
            }
         }
      }

      if (world.f_46443_) {
         BlockState block = world.m_8055_(player.m_20183_().m_7495_());
         double NumberofParticles = radius * 4.0;

         for (double i = 0.0; i < 80.0; i++) {
            double d0 = player.m_20185_() + radius * (double)Mth.m_14031_((float)(i / NumberofParticles * 360.0));
            double d1 = player.m_20186_() + 0.15;
            double d2 = player.m_20189_() + radius * (double)Mth.m_14089_((float)(i / NumberofParticles * 360.0));
            double d3 = world.m_213780_().m_188583_() * 0.2;
            double d4 = world.m_213780_().m_188583_() * 0.2;
            double d5 = world.m_213780_().m_188583_() * 0.2;
            world.m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, block), d0, d1, d2, d3, d4, d5);
            if (berserk) {
               world.m_7106_(ParticleTypes.f_123744_, d0, d1, d2, d3, d4, d5);
            }
         }
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

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.f_44672_ != EnchantmentCategory.BREAKABLE
            && enchantment.f_44672_ == EnchantmentCategory.WEAPON
            && enchantment != Enchantments.f_44983_
         || enchantment.f_44672_ == EnchantmentCategory.DIGGER;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.infernal_forge.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.infernal_forge.desc2").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
