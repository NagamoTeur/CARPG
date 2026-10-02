package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class ForestArmorPassiveProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         double chance = 0.0;
         if (entity.m_6144_()
            && ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .ElementalArmorCooldown
               == 0.0) {
            if (world.m_8055_(
                     new BlockPos(entity.m_20185_() + entity.m_20154_().f_82479_ * 0.8, entity.m_20186_(), entity.m_20189_() + entity.m_20154_().f_82481_ * 0.8)
                  )
                  .m_204336_(BlockTags.create(new ResourceLocation("minecraft:leaves")))
               && world.m_8055_(
                     new BlockPos(
                        entity.m_20185_() + entity.m_20154_().f_82479_ * 0.8, entity.m_20186_() + 1.0, entity.m_20189_() + entity.m_20154_().f_82481_ * 0.8
                     )
                  )
                  .m_204336_(BlockTags.create(new ResourceLocation("minecraft:leaves")))) {
               entity.m_6021_(entity.m_20185_() + entity.m_20154_().f_82479_ * 0.001, entity.m_20186_(), entity.m_20189_() + entity.m_20154_().f_82481_ * 0.001);
               if (entity instanceof ServerPlayer _serverPlayer) {
                  _serverPlayer.f_8906_
                     .m_9774_(
                        entity.m_20185_() + entity.m_20154_().f_82479_ * 0.001,
                        entity.m_20186_(),
                        entity.m_20189_() + entity.m_20154_().f_82481_ * 0.001,
                        entity.m_146908_(),
                        entity.m_146909_()
                     );
               }

               double _setval = 16.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.ElementalArmorCooldown = _setval;
                  capability.syncPlayerVariables(entity);
               });
            } else if (world.m_8055_(new BlockPos(entity.m_20185_(), entity.m_20186_() - 0.001, entity.m_20189_()))
                  .m_204336_(BlockTags.create(new ResourceLocation("minecraft:leaves")))
               && entity.m_20154_().f_82480_ < -0.92) {
               double _setval = 20.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.ElementalArmorCooldown = _setval;
                  capability.syncPlayerVariables(entity);
               });
               entity.m_6021_(entity.m_20185_(), entity.m_20186_() - 0.001, entity.m_20189_());
               if (entity instanceof ServerPlayer _serverPlayer) {
                  _serverPlayer.f_8906_.m_9774_(entity.m_20185_(), entity.m_20186_() - 0.001, entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
               }
            }
         }
      }
   }
}
