package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MagmanumOreVileProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double detect = 0.0;
         if ((new Object() {
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
            .checkGamemode(entity)) {
            detect = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 5);
            if (detect == 2.0) {
               if (world.m_46859_(new BlockPos(x, y, z))) {
                  world.m_7731_(new BlockPos(x, y, z), Blocks.f_49991_.m_49966_(), 3);
               }
            } else if (detect == 3.0) {
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(ParticleTypes.f_123744_, x + 0.5, y + 0.5, z + 0.5, 32, 0.3, 0.3, 0.3, 0.25);
               }

               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(3.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))) {
                     entityiterator.m_20254_(10);
                  }
               }
            } else if (detect == 4.0 && world instanceof Level _level && !_level.m_5776_()) {
               _level.m_46511_(null, x + 0.5, y + 0.5, z + 0.5, 2.0F, BlockInteraction.NONE);
            }
         }
      }
   }
}
