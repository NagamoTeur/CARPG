package net.cisco.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class NightfallRightclickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (world instanceof Level _level) {
            if (!_level.m_5776_()) {
               _level.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:nightfallsound")),
                  SoundSource.NEUTRAL,
                  1.0F,
                  1.0F
               );
            } else {
               _level.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:nightfallsound")),
                  SoundSource.NEUTRAL,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }

         if (world instanceof ServerLevel _levelx) {
            _levelx.m_8767_(ParticleTypes.f_123746_, x, y, z, 120, 1.0, 1.0, 1.0, 0.5);
         }

         if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
            _entity.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 160, 2, false, false));
         }

         if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
            _entity.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 240, 2, false, false));
         }

         if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
            _entity.m_7292_(new MobEffectInstance(MobEffects.f_19598_, 240, 2, false, false));
         }

         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_(itemstack.m_41720_(), 300);
         }
      }
   }
}
