package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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

public class VolcanicFurySwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   int radius = (int)SimplySwordsConfig.getFloatValue("volcanic_fury_radius");
   int abilityDamage = (int)SimplySwordsConfig.getFloatValue("volcanic_fury_damage");
   int ability_timer_max = 120;
   int skillCooldown = (int)SimplySwordsConfig.getFloatValue("volcanic_fury_cooldown");
   int chargeChance = (int)SimplySwordsConfig.getFloatValue("volcanic_fury_chance");
   int chargePower;

   public VolcanicFurySwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= this.chargeChance) {
            target.m_147207_(new MobEffectInstance(MobEffects.f_19620_, 10, 1), attacker);
            target.m_20334_(target.m_20185_() - attacker.m_20185_(), 0.5, target.m_20189_() - attacker.m_20189_());
            target.m_20254_(5);
            int choose_sound = (int)(Math.random() * 30.0);
            if (choose_sound <= 10) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_01.get(), SoundSource.PLAYERS, 0.5F, 1.2F);
            }

            if (choose_sound <= 20 && choose_sound > 10) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.5F, 1.2F);
            }

            if (choose_sound <= 30 && choose_sound > 20) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.5F, 1.2F);
            }
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      user.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 20, 5), user);
      user.m_147207_(new MobEffectInstance(MobEffects.f_19599_, 20, 8), user);
      user.m_147207_(new MobEffectInstance(MobEffects.f_19606_, 20, 5), user);
      ItemStack itemStack = user.m_21120_(hand);
      if (itemStack.m_41773_() >= itemStack.m_41776_() - 1) {
         return InteractionResultHolder.m_19100_(itemStack);
      } else {
         user.m_6672_(hand);
         return InteractionResultHolder.m_19096_(itemStack);
      }
   }

   public void m_5929_(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
      if (user.m_6844_(EquipmentSlot.MAINHAND) == stack && user instanceof Player player) {
         AbilityMethods.tickAbilityVolcanicFury(
            stack, world, user, remainingUseTicks, this.ability_timer_max, this.abilityDamage, this.skillCooldown, this.radius, this.chargePower
         );
         if (player.f_19797_ % 20 == 0 && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
            this.chargePower += 2;
         }
      }
   }

   public int m_8105_(ItemStack stack) {
      return this.ability_timer_max;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.CROSSBOW;
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      if (!world.f_46443_ && user instanceof Player player) {
         player.m_36335_().m_41524_(this, this.skillCooldown);
         int choose_sound = (int)(Math.random() * 30.0);
         if (choose_sound <= 10) {
            world.m_6269_(null, player, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_01.get(), SoundSource.PLAYERS, 0.6F, 1.2F);
         }

         if (choose_sound <= 20 && choose_sound > 10) {
            world.m_6269_(null, player, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.6F, 1.2F);
         }

         if (choose_sound <= 30 && choose_sound > 20) {
            world.m_6269_(null, player, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.6F, 1.2F);
         }

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
                  float choose = (float)(Math.random() * 1.0);
                  le.m_6469_(DamageSource.f_19319_, (float)(this.abilityDamage * this.chargePower));
                  le.m_20254_(6);
                  world.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_POISON_ATTACK_01.get(), SoundSource.PLAYERS, 0.1F, choose);
                  this.chargePower = 0;
                  le.m_147207_(new MobEffectInstance(MobEffects.f_19620_, 10, 1), player);
                  le.m_20334_(le.m_20185_() - player.m_20185_(), 0.5, le.m_20189_() - player.m_20189_());
                  le.m_20254_(5);
               }
            }
         }
      }
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123757_, ParticleTypes.f_123757_, ParticleTypes.f_123757_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(
         Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip3"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip6"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip7"));
      tooltip.add(Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip8"));
      tooltip.add(Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip9"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
