package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WindswirlFlyProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity.getPersistentData().m_128459_("timer") >= 320.0) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123759_, x, y, z, 48, 0.16, 0.16, 0.16, 0.16);
            }

            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }
         } else {
            entity.m_20256_(
               new Vec3(
                  entity.getPersistentData().m_128459_("xVector") / 4.0,
                  entity.getPersistentData().m_128459_("yVector") / 4.0,
                  entity.getPersistentData().m_128459_("zVector") / 4.0
               )
            );

            for (int index0 = 0; index0 < Mth.m_216271_(RandomSource.m_216327_(), 2, 3); index0++) {
               world.m_7106_(
                  ParticleTypes.f_123759_,
                  x + Mth.m_216263_(RandomSource.m_216327_(), -0.5, 0.5),
                  y + Mth.m_216263_(RandomSource.m_216327_(), -0.16, 0.64),
                  z + Mth.m_216263_(RandomSource.m_216327_(), -0.5, 0.5),
                  entity.getPersistentData().m_128459_("xVector") / -8.0,
                  entity.getPersistentData().m_128459_("yVector") / -8.0,
                  entity.getPersistentData().m_128459_("zVector") / -8.0
               );
            }

            entity.getPersistentData().m_128347_("timer", entity.getPersistentData().m_128459_("timer") + 1.0);
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(1.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:player_allies")))
                  || entityiterator instanceof Player
                     && (
                        (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiterator)
                           || (new Object() {
                                 public boolean checkGamemode(Entity _ent) {
                                    if (_ent instanceof ServerPlayer _serverPlayer) {
                                       return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                    } else {
                                       return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                          ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                             && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.ADVENTURE
                                          : false;
                                    }
                                 }
                              })
                              .checkGamemode(entityiterator)
                     )) {
                  entityiterator.m_6469_(DamageSource.f_19318_, 2.0F);
                  entityiterator.m_20256_(
                     new Vec3(Mth.m_216263_(RandomSource.m_216327_(), -0.64, 0.64), 0.64, Mth.m_216263_(RandomSource.m_216327_(), -0.64, 0.64))
                  );
               }
            }
         }
      }
   }
}
