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

public class GlaciesRightClickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (world instanceof Level _level) {
            if (!_level.m_5776_()) {
               _level.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:glaciate")),
                  SoundSource.NEUTRAL,
                  1.0F,
                  1.0F
               );
            } else {
               _level.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:glaciate")),
                  SoundSource.NEUTRAL,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }

         for (int index0 = 0; index0 < 3; index0++) {
            if (world instanceof ServerLevel _levelx) {
               _levelx.m_8767_(ParticleTypes.f_175821_, x, y, z, 70, 1.0, 1.0, 1.0, 0.2);
            }
         }

         if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
            _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 120, 0, false, false));
         }

         if (!((LivingEntity)entity)
            .m_21051_(Attributes.f_22284_)
            .m_22109_(new AttributeModifier(UUID.fromString("a287afe2-c9b8-4738-9af2-68fc0cce0201"), "glacies_armor", 0.8, Operation.MULTIPLY_TOTAL))) {
            ((LivingEntity)entity)
               .m_21051_(Attributes.f_22284_)
               .m_22118_(new AttributeModifier(UUID.fromString("a287afe2-c9b8-4738-9af2-68fc0cce0201"), "glacies_armor", 0.8, Operation.MULTIPLY_TOTAL));
         }

         CiscoModMod.queueServerWork(
            140,
            () -> ((LivingEntity)entity)
                  .m_21051_(Attributes.f_22284_)
                  .m_22130_(new AttributeModifier(UUID.fromString("a287afe2-c9b8-4738-9af2-68fc0cce0201"), "glacies_armor", 0.8, Operation.MULTIPLY_TOTAL))
         );
         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_(itemstack.m_41720_(), 380);
         }
      }
   }
}
