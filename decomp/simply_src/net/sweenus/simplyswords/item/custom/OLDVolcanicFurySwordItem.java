package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class OLDVolcanicFurySwordItem extends SwordItem {
   private static int stepMod = 0;

   public OLDVolcanicFurySwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         int fhitchance = (int)SimplySwordsConfig.getFloatValue("volcanic_fury_chance");
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= fhitchance) {
            target.m_147207_(new MobEffectInstance(MobEffects.f_19620_, 10, 1), attacker);
            target.m_20334_(target.m_20185_() - attacker.m_20185_(), 0.5, target.m_20189_() - attacker.m_20189_());
            target.m_20254_(5);
            int choose_sound = (int)(Math.random() * 30.0);
            if (choose_sound <= 10) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_01.get(), SoundSource.PLAYERS, 0.5F, 1.2F);
            }

            if (choose_sound <= 20 && choose_sound > 10) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.5F, 1.2F);
            }

            if (choose_sound <= 30 && choose_sound > 20) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.5F, 1.2F);
            }
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123801_, ParticleTypes.f_123801_, ParticleTypes.f_123777_, false);
      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123777_, ParticleTypes.f_123777_, ParticleTypes.f_123783_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(
         Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.volcanicfurysworditem.tooltip3"));
   }
}
