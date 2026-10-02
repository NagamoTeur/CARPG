package net.thirdlife.iterrpg.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class EnemySayDamageProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double damage = 0.0;
         damage = (double)(
            (entity instanceof LivingEntity _livEntx ? _livEntx.m_21233_() : -1.0F) - (entity instanceof LivingEntity _livEnt ? _livEnt.m_21223_() : -1.0F)
         );
         if (world instanceof ServerLevel _level) {
            _level.m_7654_()
               .m_129892_()
               .m_230957_(
                  new CommandSourceStack(
                        CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null
                     )
                     .m_81324_(),
                  "/say Damage:" + damage
               );
         }

         if (entity instanceof LivingEntity _entity) {
            _entity.m_21153_(entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F);
         }
      }
   }
}
