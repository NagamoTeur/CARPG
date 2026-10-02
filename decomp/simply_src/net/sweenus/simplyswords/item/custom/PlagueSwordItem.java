package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.util.HelperMethods;

public class PlagueSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;

   public PlagueSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int phitchance = (int)SimplySwordsConfig.getFloatValue("plague_chance");
      HelperMethods.playHitSounds(attacker, target);
      if (attacker.m_217043_().m_188503_(100) <= phitchance) {
         if (target.m_21023_(MobEffects.f_19598_)) {
            int statdur = target.m_21124_(MobEffects.f_19597_).m_19557_();
            int statamp = target.m_21124_(MobEffects.f_19597_).m_19564_();
            target.m_147207_(new MobEffectInstance(MobEffects.f_19599_, statdur, statamp), attacker);
            target.m_21195_(MobEffects.f_19598_);
         }

         if (target.m_21023_(MobEffects.f_19605_)) {
            int statdur = target.m_21124_(MobEffects.f_19605_).m_19557_();
            int statamp = target.m_21124_(MobEffects.f_19605_).m_19564_();
            target.m_147207_(new MobEffectInstance(MobEffects.f_19615_, statdur, statamp), attacker);
            target.m_21195_(MobEffects.f_19605_);
         }

         if (target.m_21023_(MobEffects.f_19600_)) {
            int statdur = target.m_21124_(MobEffects.f_19600_).m_19557_();
            int statamp = target.m_21124_(MobEffects.f_19600_).m_19564_();
            target.m_147207_(new MobEffectInstance(MobEffects.f_19613_, statdur, statamp), attacker);
            target.m_21195_(MobEffects.f_19600_);
         }

         if (target.m_21023_(MobEffects.f_19596_)) {
            int statdur = target.m_21124_(MobEffects.f_19596_).m_19557_();
            int statamp = target.m_21124_(MobEffects.f_19596_).m_19564_();
            target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, statdur, statamp), attacker);
            target.m_21195_(MobEffects.f_19596_);
         }

         if (target.m_21023_(MobEffects.f_19609_)) {
            int statdur = target.m_21124_(MobEffects.f_19609_).m_19557_();
            int statamp = target.m_21124_(MobEffects.f_19609_).m_19564_();
            target.m_147207_(new MobEffectInstance(MobEffects.f_19619_, statdur, statamp), attacker);
            target.m_21195_(MobEffects.f_19609_);
         }

         if (target.m_21023_(MobEffects.f_19606_)) {
            int statdur = target.m_21124_(MobEffects.f_19606_).m_19557_();
            int statamp = target.m_21124_(MobEffects.f_19606_).m_19564_();
            target.m_147207_(new MobEffectInstance(MobEffects.f_19604_, statdur, statamp), attacker);
            target.m_21195_(MobEffects.f_19606_);
         }

         if (target.m_21023_(MobEffects.f_19618_)) {
            int statdur = target.m_21124_(MobEffects.f_19618_).m_19557_();
            int statamp = target.m_21124_(MobEffects.f_19618_).m_19564_();
            target.m_147207_(new MobEffectInstance(MobEffects.f_19612_, statdur, statamp), attacker);
            target.m_21195_(MobEffects.f_19618_);
         }

         if (target.m_21023_(MobEffects.f_19607_)) {
            int statdur = target.m_21124_(MobEffects.f_19607_).m_19557_();
            int statamp = target.m_21124_(MobEffects.f_19607_).m_19564_();
            target.m_147207_(new MobEffectInstance(MobEffects.f_19614_, statdur, statamp), attacker);
            target.m_21195_(MobEffects.f_19607_);
         }

         if (target.m_21023_(MobEffects.f_19617_)) {
            int statdur = target.m_21124_(MobEffects.f_19617_).m_19557_();
            int statamp = target.m_21124_(MobEffects.f_19617_).m_19564_() / 2;
            target.m_147207_(new MobEffectInstance(MobEffects.f_19602_, 0, statamp), attacker);
            target.m_21195_(MobEffects.f_19617_);
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_175833_, ParticleTypes.f_175833_, ParticleTypes.f_175832_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.plaguesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.plaguesworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.plaguesworditem.tooltip3"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
