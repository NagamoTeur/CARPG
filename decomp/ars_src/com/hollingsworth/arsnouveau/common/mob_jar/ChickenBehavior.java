package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;

public class ChickenBehavior extends JarBehavior<Chicken> {
   @Override
   public void tick(MobJarTile tile) {
      if (!tile.m_58904_().f_46443_) {
         Chicken chicken = this.entityFromJar(tile);
         if (!this.isEntityBaby(chicken)) {
            chicken.f_28231_--;
            if (chicken.f_28231_ <= 0) {
               chicken.m_5496_(SoundEvents.f_11752_, 1.0F, (chicken.m_217043_().m_188501_() - chicken.m_217043_().m_188501_()) * 0.2F + 1.0F);
               chicken.m_19998_(Items.f_42521_);
               chicken.m_146850_(GameEvent.f_157810_);
               chicken.f_28231_ = chicken.m_217043_().m_188503_(6000) + 6000;
            }
         }
      }
   }
}
