package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class ChainParticleDisplayProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double xpos = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         double difx = 0.0;
         double dify = 0.0;
         double difz = 0.0;
         double distance = 0.0;
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.5), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiterator.getPersistentData().m_128459_("chainedTime") > 1.0) {
               difx = entityiterator.m_20185_() - x;
               dify = entityiterator.m_20186_() + (double)entityiterator.m_20206_() / 1.25 - y;
               difz = entityiterator.m_20189_() - z;

               for (int index0 = 0; index0 < 2; index0++) {
                  distance = Mth.m_216263_(RandomSource.m_216327_(), 0.01, 1.0);
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(
                        (SimpleParticleType)IterRpgModParticleTypes.CHAIN_PARTICLE.get(),
                        x + difx * distance,
                        y + 0.16 + dify * distance,
                        z + difz * distance,
                        1,
                        0.0,
                        0.0,
                        0.0,
                        0.0
                     );
                  }
               }

               if ((entity.getPersistentData().m_128459_("timer") >= 100.0 || entity.getPersistentData().m_128459_("timer") <= 8.0)
                  && world instanceof ServerLevel _level) {
                  _level.m_8767_(ParticleTypes.f_123745_, x + difx * distance, y + 0.16 + dify * distance, z + difz * distance, 1, 0.0, 0.0, 0.0, 0.0);
               }
            }
         }

         entity.m_6021_(
            entity.getPersistentData().m_128459_("brainX"), entity.getPersistentData().m_128459_("brainY"), entity.getPersistentData().m_128459_("brainZ")
         );
         if (entity instanceof ServerPlayer _serverPlayer) {
            _serverPlayer.f_8906_
               .m_9774_(
                  entity.getPersistentData().m_128459_("brainX"),
                  entity.getPersistentData().m_128459_("brainY"),
                  entity.getPersistentData().m_128459_("brainZ"),
                  entity.m_146908_(),
                  entity.m_146909_()
               );
         }

         entity.m_20256_(new Vec3(0.0, 0.0, 0.0));
         entity.f_19789_ = 0.0F;
         if (entity.getPersistentData().m_128459_("timer") > 128.0) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.CHAIN_PARTICLE.get(), x, y + 0.08, z, 8, 0.16, 0.16, 0.16, 0.0);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123745_, x, y + 0.08, z, 8, 0.32, 0.32, 0.32, 0.008);
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chain.break")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chain.break")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof Level _levelx) {
               if (!_levelx.m_5776_()) {
                  _levelx.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.destroy")),
                     SoundSource.PLAYERS,
                     0.6F,
                     3.0F
                  );
               } else {
                  _levelx.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.destroy")),
                     SoundSource.PLAYERS,
                     0.6F,
                     3.0F,
                     false
                  );
               }
            }

            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }
         } else {
            entity.getPersistentData().m_128347_("timer", entity.getPersistentData().m_128459_("timer") + 1.0);
         }
      }
   }
}
