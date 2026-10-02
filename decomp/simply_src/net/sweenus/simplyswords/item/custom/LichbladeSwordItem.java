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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.ItemsRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.AbilityMethods;
import net.sweenus.simplyswords.util.HelperMethods;

public class LichbladeSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   int radius = (int)SimplySwordsConfig.getFloatValue("soulanguish_radius");
   int abilityDamage = (int)SimplySwordsConfig.getFloatValue("soulanguish_damage");
   int ability_timer_max = (int)SimplySwordsConfig.getFloatValue("soulanguish_duration");
   int skillCooldown = (int)SimplySwordsConfig.getFloatValue("soulanguish_cooldown");
   float healAmount = SimplySwordsConfig.getFloatValue("soulanguish_heal");
   int range = (int)SimplySwordsConfig.getFloatValue("soulanguish_range");
   int damageTracker;
   int chanceReduce;
   double lastX;
   double lastY;
   double lastZ;
   double targetX;
   double targetY;
   double targetZ;
   LivingEntity abilityTarget;

   public LichbladeSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
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
         if (this.m_7968_().m_150930_((Item)ItemsRegistry.AWAKENED_LICHBLADE.get()) || this.m_7968_().m_150930_((Item)ItemsRegistry.WAKING_LICHBLADE.get())) {
            this.abilityTarget = (LivingEntity)HelperMethods.getTargetedEntity(user, this.range);
            if (this.abilityTarget != null) {
               this.abilityTarget.m_147207_(new MobEffectInstance(MobEffects.f_19619_, 10, 0), user);
               world.m_6269_(null, user, (SoundEvent)SoundRegistry.DARK_SWORD_ENCHANT.get(), SoundSource.PLAYERS, 0.5F, 0.5F);
               this.lastX = user.m_20185_();
               this.lastY = user.m_20186_();
               this.lastZ = user.m_20189_();
               this.chanceReduce = 0;
            }
         }

         user.m_6672_(hand);
         return InteractionResultHolder.m_19096_(itemStack);
      }
   }

   public void m_5929_(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
      if (user.m_6844_(EquipmentSlot.MAINHAND) == stack && user instanceof Player player) {
         if (remainingUseTicks < 200) {
            if (stack.m_150930_((Item)ItemsRegistry.AWAKENED_LICHBLADE.get())) {
               if (this.abilityTarget != player) {
                  this.abilityTarget = player;
               }

               if (player.m_20275_(this.lastX, this.lastY, this.lastZ) < (double)this.radius) {
                  if (!player.m_21023_(MobEffects.f_19617_)) {
                     player.m_147207_(new MobEffectInstance(MobEffects.f_19617_, 10, 1), player);
                  }

                  this.damageTracker = 0;
                  remainingUseTicks = 0;
                  player.m_21253_();
                  world.m_6269_(null, player, (SoundEvent)SoundRegistry.DARK_SWORD_SPELL.get(), SoundSource.PLAYERS, 0.04F, 0.5F);
               }
            } else {
               remainingUseTicks = 0;
               player.m_21253_();
               this.damageTracker = 0;
            }
         }

         if (this.abilityTarget != null && player.f_19797_ % 5 == 0) {
            this.targetX = this.abilityTarget.m_20185_();
            this.targetY = this.abilityTarget.m_20186_();
            this.targetZ = this.abilityTarget.m_20189_();
            if (this.targetX > this.lastX) {
               this.lastX++;
            }

            if (this.targetX < this.lastX) {
               this.lastX--;
            }

            if (this.targetZ > this.lastZ) {
               this.lastZ++;
            }

            if (this.targetZ < this.lastZ) {
               this.lastZ--;
            }

            if (this.targetY > this.lastY) {
               this.lastY++;
            }

            if (this.targetY < this.lastY) {
               this.lastY--;
            }
         }

         AbilityMethods.tickAbilitySoulAnguish(
            stack,
            world,
            user,
            remainingUseTicks,
            this.ability_timer_max,
            this.abilityDamage,
            this.skillCooldown,
            this.radius,
            this.damageTracker,
            this.chanceReduce,
            this.lastX,
            this.lastY,
            this.lastZ,
            this.targetX,
            this.targetY,
            this.targetZ,
            this.range,
            this.healAmount,
            this.abilityTarget
         );
      }
   }

   public int m_8105_(ItemStack stack) {
      return this.ability_timer_max;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.CROSSBOW;
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      if (!world.f_46443_ && user instanceof Player player && this.abilityTarget != null) {
         player.m_36335_().m_41524_(stack.m_41720_(), this.skillCooldown);
      }
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (!entity.f_19853_.m_5776_()
         && entity instanceof Player player
         && player.f_19797_ % 35 == 0
         && player.m_6844_(EquipmentSlot.MAINHAND) == stack
         && !player.m_6117_()) {
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
                  le.m_6469_(DamageSource.f_19319_, (float)this.abilityDamage);
               }
            }
         }

         world.m_6269_(null, player, (SoundEvent)SoundRegistry.DARK_SWORD_BLOCK.get(), SoundSource.PLAYERS, 0.1F, 0.2F);
         double xpos = player.m_20185_() - (double)(this.radius + 1);
         double ypos = player.m_20186_();
         double zpos = player.m_20189_() - (double)(this.radius + 1);

         for (int i = this.radius * 2; i > 0; i--) {
            for (int j = this.radius * 2; j > 0; j--) {
               float choose = (float)(Math.random() * 1.0);
               HelperMethods.spawnParticle(
                  world, ParticleTypes.f_235898_, xpos + (double)i + (double)choose, ypos, zpos + (double)j + (double)choose, 0.0, 0.1, 0.0
               );
               HelperMethods.spawnParticle(
                  world, ParticleTypes.f_123746_, xpos + (double)i + (double)choose, ypos + 0.1, zpos + (double)j + (double)choose, 0.0, 0.0, 0.0
               );
               HelperMethods.spawnParticle(
                  world, ParticleTypes.f_123757_, xpos + (double)i + (double)choose, ypos + 2.0, zpos + (double)j + (double)choose, 0.0, 0.0, 0.0
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

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123746_, ParticleTypes.f_123746_, ParticleTypes.f_123757_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return this.m_7968_().m_150930_((Item)ItemsRegistry.AWAKENED_LICHBLADE.get())
         ? Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD, ChatFormatting.UNDERLINE})
         : Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      if (this.m_7968_().m_150930_((Item)ItemsRegistry.SLUMBERING_LICHBLADE.get())) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.lichbladesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
         );
      }

      if (this.m_7968_().m_150930_((Item)ItemsRegistry.WAKING_LICHBLADE.get())) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.lichbladesworditem.tooltip1.2").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
         );
      }

      if (this.m_7968_().m_150930_((Item)ItemsRegistry.AWAKENED_LICHBLADE.get())) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.lichbladesworditem.tooltip1.3").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
         );
      }

      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.lichbladesworditem.tooltip2"));
      tooltip.add(Component.m_237110_("item.simplyswords.lichbladesworditem.tooltip3", new Object[]{this.radius}));
      tooltip.add(Component.m_237113_(""));
      if (this.m_7968_().m_150930_((Item)ItemsRegistry.WAKING_LICHBLADE.get()) || this.m_7968_().m_150930_((Item)ItemsRegistry.AWAKENED_LICHBLADE.get())) {
         tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
         tooltip.add(Component.m_237115_("item.simplyswords.lichbladesworditem.tooltip4"));
         tooltip.add(Component.m_237115_("item.simplyswords.lichbladesworditem.tooltip5"));
         tooltip.add(Component.m_237115_("item.simplyswords.lichbladesworditem.tooltip6"));
         tooltip.add(Component.m_237113_(""));
      }

      if (this.m_7968_().m_150930_((Item)ItemsRegistry.AWAKENED_LICHBLADE.get())) {
         tooltip.add(Component.m_237115_("item.simplyswords.lichbladesworditem.tooltip7"));
         tooltip.add(Component.m_237115_("item.simplyswords.lichbladesworditem.tooltip8"));
         tooltip.add(Component.m_237115_("item.simplyswords.lichbladesworditem.tooltip9"));
         tooltip.add(Component.m_237113_(""));
      }

      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
