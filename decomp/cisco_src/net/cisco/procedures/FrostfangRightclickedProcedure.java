package net.cisco.procedures;

import java.util.UUID;
import net.cisco.CiscoModMod;
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
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class FrostfangRightclickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (world instanceof Level _level) {
            if (!_level.m_5776_()) {
               _level.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:frostfangactive")),
                  SoundSource.NEUTRAL,
                  1.0F,
                  1.0F
               );
            } else {
               _level.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:frostfangactive")),
                  SoundSource.NEUTRAL,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }

         if (world instanceof ServerLevel _levelx) {
            _levelx.m_8767_(ParticleTypes.f_175821_, x, y, z, 120, 1.0, 1.0, 1.0, 1.0);
         }

         if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
            > (entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 2.0F) {
            if (!((LivingEntity)entity)
               .m_21051_(Attributes.f_22281_)
               .m_22109_(
                  new AttributeModifier(
                     UUID.fromString("d408fed1-a2c8-4a85-82a5-8f0614b6a080"),
                     "activefrostatk",
                     ((LivingEntity)entity).m_21051_(Attributes.f_22276_).m_22135_() * 0.15,
                     Operation.MULTIPLY_TOTAL
                  )
               )) {
               ((LivingEntity)entity)
                  .m_21051_(Attributes.f_22281_)
                  .m_22118_(
                     new AttributeModifier(
                        UUID.fromString("d408fed1-a2c8-4a85-82a5-8f0614b6a080"),
                        "activefrostatk",
                        ((LivingEntity)entity).m_21051_(Attributes.f_22276_).m_22135_() * 0.15,
                        Operation.MULTIPLY_TOTAL
                     )
                  );
            }

            CiscoModMod.queueServerWork(
               200,
               () -> ((LivingEntity)entity)
                     .m_21051_(Attributes.f_22281_)
                     .m_22130_(
                        new AttributeModifier(
                           UUID.fromString("d408fed1-a2c8-4a85-82a5-8f0614b6a080"),
                           "activefrostatk",
                           ((LivingEntity)entity).m_21051_(Attributes.f_22276_).m_22135_() * 0.15,
                           Operation.MULTIPLY_TOTAL
                        )
                     )
            );
         } else {
            if (entity instanceof LivingEntity _entity) {
               _entity.m_21153_((float)((double)(entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) * 0.7));
            }

            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 160, 2, false, false));
            }
         }

         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_(itemstack.m_41720_(), 300);
         }
      }
   }
}
