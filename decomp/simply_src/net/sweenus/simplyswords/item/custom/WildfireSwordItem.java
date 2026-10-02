package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.registry.EffectRegistry;

public class WildfireSwordItem extends SwordItem {
   public WildfireSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int phitchance = (int)SimplySwordsConfig.getFloatValue("wildfire_chance");
      int pduration = (int)SimplySwordsConfig.getFloatValue("wildfire_duration");
      if (attacker.m_217043_().m_188503_(100) <= phitchance) {
         target.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.WILDFIRE.get(), pduration, 3), attacker);
      }

      return super.m_7579_(stack, target, attacker);
   }

   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.wildfiresworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.wildfiresworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.wildfiresworditem.tooltip3"));
   }
}
