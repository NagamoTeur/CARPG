package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class WatcherSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;

   public WatcherSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         int thitchance = (int)SimplySwordsConfig.getFloatValue("watcher_chance");
         int phitchance = (int)SimplySwordsConfig.getFloatValue("omen_chance");
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= thitchance) {
            target.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.WATCHER.get(), 1, 1), attacker);
         }

         if (attacker.m_217043_().m_188503_(100) <= phitchance) {
            target.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.OMEN.get(), 1, 1), attacker);
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

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123809_, ParticleTypes.f_123809_, ParticleTypes.f_123757_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.watchersworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.watchersworditem.tooltip2"));
      tooltip.add(
         Component.m_237110_(
            "item.simplyswords.watchersworditem.tooltip3", new Object[]{SimplySwordsConfig.getFloatValue("omen_instantkill_threshold") * 100.0F}
         )
      );
      tooltip.add(Component.m_237115_("item.simplyswords.watchersworditem.tooltip4"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.watchersworditem.tooltip5").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.watchersworditem.tooltip6"));
      tooltip.add(Component.m_237115_("item.simplyswords.watchersworditem.tooltip7"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
