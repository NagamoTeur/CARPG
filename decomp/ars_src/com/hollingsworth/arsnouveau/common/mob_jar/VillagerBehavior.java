package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import net.minecraft.world.entity.npc.Villager;

public class VillagerBehavior extends JarBehavior<Villager> {
   @Override
   public void tick(MobJarTile tile) {
      if (!tile.m_58904_().f_46443_) {
         Villager villager = this.entityFromJar(tile);
         if (!villager.m_35306_() && villager.f_35373_ > 0) {
            villager.f_35373_--;
            if (villager.f_35373_ <= 0 && villager.f_35374_) {
               villager.m_35528_();
               villager.f_35374_ = false;
            }
         }

         if (villager.m_35511_()) {
            villager.m_35510_();
         }
      }
   }
}
