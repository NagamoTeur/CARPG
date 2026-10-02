package net.thirdlife.iterrpg.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.init.IterRpgModGameRules;

public class VaseProjectileDestroyProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world.m_6106_().m_5470_().m_46207_(IterRpgModGameRules.FRAGILEBLOCKS)) {
         if (world instanceof ServerLevel _level) {
            _level.m_7654_()
               .m_129892_()
               .m_230957_(
                  new CommandSourceStack(
                        CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null
                     )
                     .m_81324_(),
                  "loot spawn ~ ~ ~ mine ~ ~ ~"
               );
         }

         world.m_46961_(new BlockPos(x, y, z), false);
         ExplosionExpDropProcedure.execute(world, x, y, z);
      }
   }
}
