package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.entity.EntityDummy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DecoyBehavior extends JarBehavior<EntityDummy> {
   @Override
   public void tick(MobJarTile tile) {
      super.tick(tile);
      if ((Boolean)tile.m_58900_().m_61143_(BlockStateProperties.f_61448_)) {
         Level level = tile.m_58904_();
         if (level != null) {
            if (!level.f_46443_) {
               BlockPos pos = tile.m_58899_();
               EntityDummy dummy = this.entityFromJar(tile);

               for (Mob entity : level.m_45976_(Mob.class, new AABB(pos).m_82400_(10.0))) {
                  Vec3 vec3d = new Vec3(
                     (double)pos.m_123341_() - entity.m_20185_(), (double)pos.m_123342_() - entity.m_20186_(), (double)pos.m_123343_() - entity.m_20189_()
                  );
                  if (!(vec3d.m_82553_() < 1.0)) {
                     entity.m_20256_(entity.m_20184_().m_82549_(vec3d.m_82541_()).m_82490_(0.2F));
                     entity.f_19864_ = true;
                  }
               }
            }
         }
      }
   }
}
