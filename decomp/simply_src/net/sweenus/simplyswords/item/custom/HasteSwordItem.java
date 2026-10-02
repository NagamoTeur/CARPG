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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class HasteSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;

   public HasteSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         int fhitchance = (int)SimplySwordsConfig.getFloatValue("ferocity_chance");
         int fduration = (int)SimplySwordsConfig.getFloatValue("ferocity_duration");
         int maximum_stacks = (int)SimplySwordsConfig.getFloatValue("ferocity_max_stacks");
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= fhitchance) {
            if (attacker.m_21023_(MobEffects.f_19598_)) {
               int a = attacker.m_21124_(MobEffects.f_19598_).m_19564_() + 1;
               world.m_6269_(
                  null, attacker, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_HOLY_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.3F, 1.0F + (float)a / 10.0F
               );
               if (attacker.m_21124_(MobEffects.f_19598_).m_19564_() < maximum_stacks) {
                  attacker.m_147207_(new MobEffectInstance(MobEffects.f_19598_, fduration, a), attacker);
               } else {
                  attacker.m_147207_(new MobEffectInstance(MobEffects.f_19598_, fduration, a - 1), attacker);
               }
            } else {
               attacker.m_147207_(new MobEffectInstance(MobEffects.f_19598_, fduration, 1), attacker);
            }
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      if (user.m_21023_(MobEffects.f_19598_)) {
         int strength_tier = (int)SimplySwordsConfig.getFloatValue("ferocity_strength_tier");
         int a = user.m_21124_(MobEffects.f_19598_).m_19564_() * 20;
         user.m_147207_(new MobEffectInstance(MobEffects.f_19600_, a, strength_tier), user);
         user.m_6674_(hand);
         user.m_21195_(MobEffects.f_19598_);
         world.m_5594_(null, user.m_20183_(), (SoundEvent)SoundRegistry.ELEMENTAL_BOW_SCIFI_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.5F, 1.5F);
      }

      return super.m_7203_(world, user, hand);
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123811_, ParticleTypes.f_123811_, ParticleTypes.f_123811_, false);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.ferocitysworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.ferocitysworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.ferocitysworditem.tooltip3"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.ferocitysworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.ferocitysworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.ferocitysworditem.tooltip6"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
