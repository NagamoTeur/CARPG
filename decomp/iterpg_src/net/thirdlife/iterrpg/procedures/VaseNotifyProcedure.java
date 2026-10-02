package net.thirdlife.iterrpg.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelAccessor;

public class VaseNotifyProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (!world.m_5776_() && world.m_7654_() != null) {
         world.m_7654_().m_6846_().m_240416_(Component.m_237113_(x + "/" + y + "/" + z), false);
      }
   }
}
