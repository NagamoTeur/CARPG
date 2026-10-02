package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.util.HelperMethods;

public class BrambleSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;

   public BrambleSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         int fhitchance = (int)SimplySwordsConfig.getFloatValue("bramble_chance");
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= fhitchance) {
            int sradius = (int)SimplySwordsConfig.getFloatValue("bramble_radius");
            int vradius = (int)(SimplySwordsConfig.getFloatValue("bramble_radius") / 2.0F);
            double x = target.m_20185_();
            double y = target.m_20186_();
            double z = target.m_20189_();
            AABB box = new AABB(x + 10.0, y + 5.0, z + 10.0, x - 10.0, y - 5.0, z - 10.0);

            for (Entity e : world.m_6249_(attacker, box, EntitySelector.f_20403_)) {
               if (e != null && HelperMethods.checkFriendlyFire((LivingEntity)e, (Player)attacker)) {
                  target = (LivingEntity)e;
                  target.m_147207_(new MobEffectInstance(MobEffects.f_19614_, 300, 1), attacker);
                  target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 150, 1), attacker);
               }
            }
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

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_175833_, ParticleTypes.f_175833_, ParticleTypes.f_123757_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.bramblesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.bramblesworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.bramblesworditem.tooltip3"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
