package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.AbilityMethods;
import net.sweenus.simplyswords.util.HelperMethods;

public class IcewhisperSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   int radius = (int)SimplySwordsConfig.getFloatValue("permafrost_radius");
   int frostDamage = (int)SimplySwordsConfig.getFloatValue("permafrost_damage");
   int blizzard_timer_max = (int)SimplySwordsConfig.getFloatValue("permafrost_duration");
   int skillCooldown = (int)SimplySwordsConfig.getFloatValue("permafrost_cooldown");
   int blizzard_timer;
   double lastX;
   double lastY;
   double lastZ;

   public IcewhisperSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      HelperMethods.playHitSounds(attacker, target);
      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      ItemStack itemStack = user.m_21120_(hand);
      if (itemStack.m_41773_() >= itemStack.m_41776_() - 1) {
         return InteractionResultHolder.m_19100_(itemStack);
      } else {
         this.lastX = user.m_20185_();
         this.lastY = user.m_20186_();
         this.lastZ = user.m_20189_();
         user.m_6672_(hand);
         return InteractionResultHolder.m_19096_(itemStack);
      }
   }

   public void m_5929_(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
      if (user.m_6844_(EquipmentSlot.MAINHAND) == stack && user instanceof Player player) {
         AbilityMethods.tickAbilityPermafrost(
            stack,
            world,
            user,
            remainingUseTicks,
            this.blizzard_timer_max,
            this.frostDamage,
            this.skillCooldown,
            this.radius,
            this.lastX,
            this.lastY,
            this.lastZ
         );
      }
   }

   public int m_8105_(ItemStack stack) {
      return this.blizzard_timer_max;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.CROSSBOW;
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      if (!world.f_46443_ && user instanceof Player player) {
         player.m_36335_().m_41524_(stack.m_41720_(), this.skillCooldown);
      }
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (!world.f_46443_ && entity instanceof Player player && player.f_19797_ % 35 == 0 && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
         AABB box = new AABB(
            player.m_20185_() + (double)this.radius,
            player.m_20186_() + (double)this.radius,
            player.m_20189_() + (double)this.radius,
            player.m_20185_() - (double)this.radius,
            player.m_20186_() - (double)this.radius,
            player.m_20189_() - (double)this.radius
         );

         for (Entity entities : world.m_6249_(player, box, EntitySelector.f_20403_)) {
            if (entities != null && entities instanceof LivingEntity) {
               LivingEntity le = (LivingEntity)entities;
               if (HelperMethods.checkFriendlyFire(le, player)) {
                  if (le.m_21023_(MobEffects.f_19597_)) {
                     int a = le.m_21124_(MobEffects.f_19597_).m_19564_() + 1;
                     if (a < 4) {
                        le.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 120, a), player);
                     } else {
                        le.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 120, a - 1), player);
                     }
                  } else {
                     le.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 120, 0), player);
                  }

                  float choose = (float)(Math.random() * 1.0);
                  world.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_ICE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.1F, choose);
                  le.m_6469_(DamageSource.f_146701_, (float)this.frostDamage);
               }
            }
         }

         world.m_6269_(null, player, (SoundEvent)SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_02.get(), SoundSource.PLAYERS, 0.1F, 0.6F);
         double xpos = player.m_20185_() - (double)(this.radius + 1);
         double ypos = player.m_20186_();
         double zpos = player.m_20189_() - (double)(this.radius + 1);

         for (int i = this.radius * 2; i > 0; i--) {
            for (int j = this.radius * 2; j > 0; j--) {
               float choose = (float)(Math.random() * 1.0);
               HelperMethods.spawnParticle(
                  world, ParticleTypes.f_175821_, xpos + (double)i + (double)choose, ypos + 0.4, zpos + (double)j + (double)choose, 0.0, 0.1, 0.0
               );
               HelperMethods.spawnParticle(
                  world, ParticleTypes.f_123796_, xpos + (double)i + (double)choose, ypos + 0.1, zpos + (double)j + (double)choose, 0.0, 0.0, 0.0
               );
               HelperMethods.spawnParticle(
                  world, ParticleTypes.f_123790_, xpos + (double)i + (double)choose, ypos + 2.0, zpos + (double)j + (double)choose, 0.0, 0.0, 0.0
               );
            }
         }
      }

      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_175821_, ParticleTypes.f_175821_, ParticleTypes.f_123790_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(
         Component.m_237115_("item.simplyswords.icewhispersworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.icewhispersworditem.tooltip2"));
      tooltip.add(Component.m_237110_("item.simplyswords.icewhispersworditem.tooltip3", new Object[]{this.radius}));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.icewhispersworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.icewhispersworditem.tooltip5"));
      tooltip.add(Component.m_237110_("item.simplyswords.icewhispersworditem.tooltip6", new Object[]{this.radius * 2}));
      tooltip.add(Component.m_237113_(""));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
