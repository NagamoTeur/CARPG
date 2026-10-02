package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.util.AbilityMethods;
import net.sweenus.simplyswords.util.HelperMethods;

public class StormSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;

   public StormSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         HelperMethods.playHitSounds(attacker, target);
         int phitchance = (int)SimplySwordsConfig.getFloatValue("storm_chance");
         if (attacker.m_217043_().m_188503_(100) <= phitchance) {
            target.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.STORM.get(), 2, 1), attacker);
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      ItemStack itemStack = user.m_21120_(hand);
      if (itemStack.m_41773_() >= itemStack.m_41776_() - 1) {
         return InteractionResultHolder.m_19100_(itemStack);
      } else {
         user.m_147207_(new MobEffectInstance(MobEffects.f_19606_, 20, 5), user);
         user.m_6672_(hand);
         user.m_36335_().m_41524_(this, 700);
         return InteractionResultHolder.m_19096_(itemStack);
      }
   }

   public void m_5929_(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
      if (!world.f_46443_) {
         int radius = (int)SimplySwordsConfig.getFloatValue("storm_radius");
         AbilityMethods.tickAbilityStorm(stack, world, user, remainingUseTicks, 700, radius);
      }
   }

   public int m_8105_(ItemStack stack) {
      return 200;
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

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123815_, ParticleTypes.f_123815_, ParticleTypes.f_175830_, false);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.stormsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.stormsworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormsworditem.tooltip3"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.stormsworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormsworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.stormsworditem.tooltip6"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
