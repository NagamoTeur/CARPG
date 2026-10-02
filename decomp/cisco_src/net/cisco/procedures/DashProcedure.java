package net.cisco.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class DashProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.m_20256_(
            new Vec3(
               (entity instanceof Mob _mobEntxxxxx ? _mobEntxxxxx.m_5448_() : null).m_20185_()
                  - entity.m_20185_() * 0.15
                  - ((entity instanceof Mob _mobEntxxxx ? _mobEntxxxx.m_5448_() : null).m_20185_() - entity.m_20185_() * 0.05),
               (entity instanceof Mob _mobEntxxx ? _mobEntxxx.m_5448_() : null).m_20186_()
                  - entity.m_20186_() * 0.15
                  - ((entity instanceof Mob _mobEntxx ? _mobEntxx.m_5448_() : null).m_20186_() - entity.m_20186_() * 0.05)
                  + 0.15,
               (entity instanceof Mob _mobEntx ? _mobEntx.m_5448_() : null).m_20189_()
                  - entity.m_20189_() * 0.15
                  - ((entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null).m_20189_() - entity.m_20189_() * 0.05)
            )
         );
      }
   }
}
