package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MobPlacerCoordinatesReturnProcedure {
   public static String execute(Entity entity) {
      return entity == null
         ? ""
         : "x: "
            + (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("xcord")
            + "y: "
            + (entity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("ycord")
            + "z: "
            + (entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("zcord");
   }
}
