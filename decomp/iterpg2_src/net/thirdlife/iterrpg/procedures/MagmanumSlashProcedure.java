package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class MagmanumSlashProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity, ItemStack itemstack) {
      if (sourceentity != null) {
         double pitch = 0.0;
         double yaw = 0.0;
         double pitch_off = 0.0;
         double yaw_off = 0.0;
         double xdec = 0.0;
         double ydec = 0.0;
         double zdec = 0.0;
         double repeat = 0.0;
         double dist = 0.0;
         double splashdmg = 0.0;
         double distabs = 0.0;
         double iteration = 0.0;
         double true_pitch = 0.0;
         if (((IterRpgModVariables.PlayerVariables)sourceentity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .MeleeAttackCooldown
            <= 0.0) {
            double _setval = 13.0;
            sourceentity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.MeleeAttackCooldown = _setval;
               capability.syncPlayerVariables(sourceentity);
            });
            if (sourceentity instanceof Player _player) {
               _player.m_36335_().m_41524_(itemstack.m_41720_(), 13);
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                     SoundSource.PLAYERS,
                     0.25F,
                     2.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                     SoundSource.PLAYERS,
                     0.25F,
                     2.0F,
                     false
                  );
               }
            }

            yaw = (double)sourceentity.m_146908_();
            true_pitch = (double)sourceentity.m_146909_();
            pitch_off = Mth.m_216263_(RandomSource.m_216327_(), -30.0, 30.0);
            yaw_off = Mth.m_216263_(RandomSource.m_216327_(), 75.0, 100.0);
            pitch = true_pitch - pitch_off;
            yaw -= yaw_off;
            repeat = 36.0;
            distabs = (Math.abs(sourceentity.m_20154_().f_82479_) + Math.abs(sourceentity.m_20154_().f_82481_) + 0.25) / 1.5;
            iteration = 0.0;

            for (int index0 = 0; index0 < (int)repeat; index0++) {
               dist = distabs * Mth.m_216263_(RandomSource.m_216327_(), 1.5, 2.0);
               if (world instanceof ServerLevel _levelx) {
                  _levelx.m_8767_(
                     ParticleTypes.f_123744_,
                     sourceentity.m_20185_() - Math.sin(Math.toRadians(yaw)) * dist,
                     sourceentity.m_20186_()
                        + 1.5
                        - (
                           Math.sin(Math.toRadians(true_pitch)) * dist * (0.25 - (iteration / repeat - 0.5) * (iteration / repeat - 0.5)) * 5.0
                              + Math.sin(Math.toRadians(pitch)) / 2.0
                        ),
                     sourceentity.m_20189_() + Math.cos(Math.toRadians(yaw)) * dist,
                     1,
                     0.01,
                     0.01,
                     0.01,
                     0.0032
                  );
               }

               Vec3 _center = new Vec3(
                  sourceentity.m_20185_() - Math.sin(Math.toRadians(yaw)) * dist,
                  sourceentity.m_20186_()
                     + 1.5
                     - (
                        Math.sin(Math.toRadians(true_pitch)) * dist * (0.25 - (iteration / repeat - 0.5) * (iteration / repeat - 0.5)) * 5.0
                           + Math.sin(Math.toRadians(pitch)) / 2.0
                     ),
                  sourceentity.m_20189_() + Math.cos(Math.toRadians(yaw)) * dist
               );

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.75), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if ((!(entityiterator instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                     && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                     && entityiterator instanceof LivingEntity
                     && sourceentity != entityiterator) {
                     entityiterator.m_6469_(DamageSource.f_19318_, 2.0F);
                     entityiterator.m_20254_(4);
                  }
               }

               pitch += 2.0 * (pitch_off / repeat);
               yaw += 2.0 * (yaw_off / repeat);
               iteration++;
            }
         }
      }
   }
}
