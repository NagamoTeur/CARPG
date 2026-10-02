package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

public class LevitationSwordItem extends UniqueSwordItem {
   public LevitationSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("levitation_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("levitation_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         target.m_147207_(new MobEffectInstance(MobEffects.f_19620_, lduration, 3), attacker);
      }

      return super.m_7579_(stack, target, attacker);
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(
         Component.m_237115_("item.simplyswords.levitationsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237115_("item.simplyswords.levitationsworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.levitationsworditem.tooltip3"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
