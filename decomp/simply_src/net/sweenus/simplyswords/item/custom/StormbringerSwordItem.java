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
import net.sweenus.simplyswords.util.HelperMethods;

public class StormbringerSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   int radius = 3;
   int ability_timer_max = (int)SimplySwordsConfig.getFloatValue("shockdeflect_block_duration");
   int skillCooldown = (int)SimplySwordsConfig.getFloatValue("shockdeflect_cooldown");
   int perfectParryWindow = (int)SimplySwordsConfig.getFloatValue("shockdeflect_parry_duration");
   int abilityDamage = (int)SimplySwordsConfig.getFloatValue("shockdeflect_damage");
   boolean parrySuccess;
   int parrySuccession;

   public StormbringerSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
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
         world.m_6269_(
            null, user, (SoundEvent)SoundRegistry.MAGIC_SWORD_PARRY_02.get(), SoundSource.PLAYERS, 0.8F, (float)(0.8F * (double)this.parrySuccession * 0.1)
         );
         user.m_147207_(new MobEffectInstance(MobEffects.f_19617_, this.ability_timer_max, 2), user);
         user.m_6672_(hand);
         return InteractionResultHolder.m_19096_(itemStack);
      }
   }

   public void m_5929_(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
      if (!world.f_46443_) {
         if (remainingUseTicks <= 2) {
            user.m_21253_();
         }

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
               if (HelperMethods.checkFriendlyFire(le, (Player)user) && le.f_20911_ && remainingUseTicks > this.m_8105_(stack) - this.perfectParryWindow) {
                  this.parrySuccess = true;
                  if (this.parrySuccession < 20) {
                     this.parrySuccession++;
                  }

                  user.m_21253_();
                  le.f_20911_ = false;
                  world.m_6269_(
                     null,
                     user,
                     (SoundEvent)SoundRegistry.MAGIC_SWORD_PARRY_01.get(),
                     SoundSource.PLAYERS,
                     1.0F,
                     (float)(0.8F * (double)this.parrySuccession * 0.1)
                  );
               }
            }
         }
      }
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      if (!world.f_46443_) {
         if (this.parrySuccess) {
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
                  if (HelperMethods.checkFriendlyFire(le, (Player)user)) {
                     float choose = (float)(Math.random() * 1.0);
                     le.m_6469_(DamageSource.f_19319_, (float)(this.abilityDamage + this.parrySuccession));
                     world.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_POISON_ATTACK_01.get(), SoundSource.PLAYERS, 0.3F, choose);
                     le.m_20334_(le.m_20185_() - user.m_20185_(), 0.1, le.m_20189_() - user.m_20189_());
                     user.m_20256_(le.m_20154_().m_82490_(1.5));
                     user.m_20334_(user.m_20184_().f_82479_, 0.0, user.m_20184_().f_82481_);
                     user.f_19864_ = true;
                  }
               }
            }

            world.m_6269_(
               null,
               user,
               (SoundEvent)SoundRegistry.ELEMENTAL_BOW_THUNDER_SHOOT_IMPACT_01.get(),
               SoundSource.PLAYERS,
               (float)(0.2F * (double)this.parrySuccession * 0.04),
               0.8F
            );
            ((Player)user).m_36335_().m_41524_(stack.m_41720_(), this.skillCooldown / 2 + this.parrySuccession * 2);
         }

         if (!this.parrySuccess) {
            ((Player)user).m_36335_().m_41524_(stack.m_41720_(), this.skillCooldown);
            this.parrySuccession = 0;
         }

         this.parrySuccess = false;
      }
   }

   public int m_8105_(ItemStack stack) {
      return this.ability_timer_max;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BLOCK;
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
         Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip3"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip4"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip6"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip7"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip8"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip9"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip10"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormbringersworditem.tooltip11"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
