package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;

public class PandaBehavior extends JarBehavior<Panda> {
   @Override
   public void tick(MobJarTile tile) {
      if (!tile.m_58904_().f_46443_) {
         Panda panda = this.entityFromJar(tile);
         if (!panda.m_29149_() && this.canSneeze(panda)) {
            panda.m_29220_(true);
         }

         if (panda.m_29149_()) {
            panda.m_29210_(panda.m_29153_() + 1);
            if (panda.m_29153_() > 20) {
               panda.m_29220_(false);
               this.afterSneeze(panda, tile);
            } else if (panda.m_29153_() == 1) {
               panda.m_5496_(SoundEvents.f_12177_, 1.0F, 1.0F);
            }
         }
      }
   }

   public void afterSneeze(Panda panda, MobJarTile tile) {
      panda.m_5496_(SoundEvents.f_12178_, 1.0F, 1.0F);
      if (!panda.f_19853_.m_5776_() && panda.m_217043_().m_188503_(700) == 0 && panda.f_19853_.m_46469_().m_46207_(GameRules.f_46135_)) {
         ItemEntity itementity = new ItemEntity(tile.m_58904_(), tile.getX(), tile.getY() + 1.0, tile.getZ(), Items.f_42518_.m_7968_());
         itementity.m_32060_();
         tile.m_58904_().m_7967_(itementity);
      }
   }

   public boolean canSneeze(Panda panda) {
      if (panda.m_6162_()) {
         return panda.m_29164_() && panda.m_217043_().m_188503_(reducedTickDelay(500)) == 1 ? true : panda.m_217043_().m_188503_(reducedTickDelay(6000)) == 1;
      } else {
         return false;
      }
   }

   public static int reducedTickDelay(int pReduction) {
      return Mth.m_184652_(pReduction, 2);
   }
}
