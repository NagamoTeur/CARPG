package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
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
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class MoltenEdgeSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   private static SimpleParticleType particleWalk = ParticleTypes.f_123801_;
   private static SimpleParticleType particleSprint = ParticleTypes.f_123801_;
   private static SimpleParticleType particlePassive = ParticleTypes.f_123762_;
   private final int abilityCooldown = (int)SimplySwordsConfig.getFloatValue("moltenroar_cooldown");
   int radius = (int)SimplySwordsConfig.getFloatValue("moltenroar_radius");
   int knockbackStrength = (int)SimplySwordsConfig.getFloatValue("moltenroar_knockback_strength");
   int proc_chance = (int)SimplySwordsConfig.getFloatValue("moltenroar_chance");
   int roar_timer_max = (int)SimplySwordsConfig.getFloatValue("moltenroar_duration");

   public MoltenEdgeSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= this.proc_chance) {
            world.m_6269_(null, attacker, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.6F, 2.0F);
            if (attacker.m_217043_().m_188503_(100) <= 50) {
               if (attacker.m_21223_() > 2.0F) {
                  attacker.m_20254_(3);
               }

               if (attacker.m_21223_() < 4.0F) {
                  attacker.m_147207_(new MobEffectInstance(MobEffects.f_19605_, 60, 3), attacker);
               }
            } else if (attacker.m_217043_().m_188503_(100) > 50) {
               target.m_20254_(3);
            }
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      if (!user.f_19853_.m_5776_()) {
         int amp = 0;
         AABB box = new AABB(
            user.m_20185_() + (double)this.radius,
            user.m_20186_() + (double)this.radius,
            user.m_20189_() + (double)this.radius,
            user.m_20185_() - (double)this.radius,
            user.m_20186_() - (double)this.radius,
            user.m_20189_() - (double)this.radius
         );

         for (Entity entities : world.m_6249_(user, box, EntitySelector.f_20403_)) {
            if (entities != null && entities instanceof LivingEntity) {
               LivingEntity le = (LivingEntity)entities;
               if (HelperMethods.checkFriendlyFire(le, user)) {
                  amp++;
                  le.m_20334_(
                     (le.m_20185_() - user.m_20185_()) / (double)this.knockbackStrength,
                     0.6,
                     (le.m_20189_() - user.m_20189_()) / (double)this.knockbackStrength
                  );
                  le.m_20254_(3);
               }
            }
         }

         world.m_6269_(null, user, (SoundEvent)SoundRegistry.DARK_SWORD_ENCHANT.get(), SoundSource.PLAYERS, 0.7F, 1.5F);
         int duration = this.roar_timer_max * amp / 2;
         user.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.ONSLAUGHT.get(), duration, 0), user);
         user.m_147207_(new MobEffectInstance(MobEffects.f_19606_, duration, 3), user);
         user.m_36335_().m_41524_(this, this.abilityCooldown);
         particlePassive = ParticleTypes.f_123755_;
         particleWalk = ParticleTypes.f_123756_;
         particleSprint = ParticleTypes.f_123756_;
      }

      return super.m_7203_(world, user, hand);
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (!world.f_46443_ && entity instanceof Player player && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
         int amp = 0;
         if (player.f_19797_ % 40 == 0) {
            if (player.m_21223_() < player.m_21233_() / 2.0F && player.m_21223_() > player.m_21233_() / 3.0F) {
               amp = 1;
               particlePassive = ParticleTypes.f_123755_;
               particleWalk = ParticleTypes.f_123801_;
               particleSprint = ParticleTypes.f_123801_;
            } else if (player.m_21223_() < player.m_21233_() / 3.0F && player.m_21223_() > 2.0F) {
               amp = 2;
               particlePassive = ParticleTypes.f_123755_;
               particleWalk = ParticleTypes.f_123801_;
               particleSprint = ParticleTypes.f_123801_;
            } else if (player.m_21223_() <= 2.0F) {
               amp = 3;
               particlePassive = ParticleTypes.f_123755_;
               particleWalk = ParticleTypes.f_123756_;
               particleSprint = ParticleTypes.f_123756_;
            } else {
               particlePassive = ParticleTypes.f_123762_;
               particleWalk = ParticleTypes.f_123801_;
               particleSprint = ParticleTypes.f_123801_;
            }

            player.m_147207_(new MobEffectInstance(MobEffects.f_19600_, 40, amp), player);
            player.m_147207_(new MobEffectInstance(MobEffects.f_19596_, 40, amp - 1), player);
         }
      }

      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, particleWalk, particleSprint, particlePassive, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(
         Component.m_237115_("item.simplyswords.moltenedgesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.moltenedgesworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.moltenedgesworditem.tooltip3"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.moltenedgesworditem.tooltip4"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.moltenedgesworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.moltenedgesworditem.tooltip6"));
      tooltip.add(Component.m_237110_("item.simplyswords.moltenedgesworditem.tooltip7", new Object[]{this.roar_timer_max / 20}));
      tooltip.add(Component.m_237113_(""));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
