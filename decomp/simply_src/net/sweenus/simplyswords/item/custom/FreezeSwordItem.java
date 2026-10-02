package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.EffectRegistry;

public class FreezeSwordItem extends UniqueSwordItem {
   public FreezeSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int fhitchance = (int)SimplySwordsConfig.getFloatValue("freeze_chance");
      int fduration = (int)SimplySwordsConfig.getFloatValue("freeze_duration");
      int sduration = (int)SimplySwordsConfig.getFloatValue("slowness_duration");
      target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, sduration, 1), attacker);
      if (attacker.m_217043_().m_188503_(100) <= fhitchance) {
         target.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.FREEZE.get(), fduration, 1), attacker);
      }

      return super.m_7579_(stack, target, attacker);
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.freezesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.freezesworditem.tooltip2"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
