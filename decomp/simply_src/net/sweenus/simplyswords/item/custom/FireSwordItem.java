package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class FireSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;

   public FireSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         int fhitchance = (int)SimplySwordsConfig.getFloatValue("brimstone_chance");
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= fhitchance && attacker instanceof Player player) {
            int choose_sound = (int)(Math.random() * 3.0);
            BlockPos position = target.m_20183_();

            for (int i = 0; i < 5 * choose_sound; i++) {
               HelperMethods.spawnParticle(
                  world,
                  ParticleTypes.f_123756_,
                  (double)position.m_123341_(),
                  (double)position.m_123342_() + 0.5,
                  (double)position.m_123343_(),
                  (double)choose_sound,
                  0.5 + (double)choose_sound,
                  (double)choose_sound
               );
               HelperMethods.spawnParticle(
                  world, ParticleTypes.f_123762_, (double)position.m_123341_(), (double)position.m_123342_() + 0.5, (double)position.m_123343_(), 0.0, 0.0, 0.0
               );
            }

            world.m_46511_(attacker, target.m_20185_(), target.m_20186_(), target.m_20189_(), (float)choose_sound, BlockInteraction.NONE);
            target.m_20254_(3);
            if (choose_sound <= 1) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_01.get(), SoundSource.PLAYERS, 0.5F, 1.2F);
            }

            if (choose_sound <= 2 && choose_sound > 1) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.7F, 1.1F);
            }

            if (choose_sound <= 3 && choose_sound > 2) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.9F, 1.0F);
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

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123801_, ParticleTypes.f_123801_, ParticleTypes.f_123762_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.firesworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.firesworditem.tooltip2"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
