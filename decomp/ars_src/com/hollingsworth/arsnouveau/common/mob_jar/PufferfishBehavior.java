package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.mixin.PufferfishAccessor;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class PufferfishBehavior extends JarBehavior<Pufferfish> {
   @Override
   public void tick(MobJarTile tile) {
      super.tick(tile);
      Level level = tile.m_58904_();
      Pufferfish pufferfish = (Pufferfish)tile.getEntity();
      if (!level.f_46443_ && pufferfish != null) {
         PufferfishAccessor pufferfishAccessor = (PufferfishAccessor)pufferfish;
         boolean playerNearby = this.mobsNearby(tile, pufferfish);
         BlockPos worldPosition = tile.m_58899_();
         if (playerNearby) {
            if (pufferfish.m_29631_() == 0) {
               pufferfish.m_5496_(SoundEvents.f_12291_, 1.0F, pufferfish.m_6100_());
               pufferfish.m_29618_(1);
               level.m_46672_(worldPosition, BlockRegistry.ITEM_DETECTOR.get());
               this.syncClient(tile);
            } else if (pufferfishAccessor.getInflateCounter() > 40 && pufferfish.m_29631_() == 1) {
               pufferfish.m_5496_(SoundEvents.f_12291_, 1.0F, pufferfish.m_6100_());
               pufferfish.m_29618_(2);
               level.m_46672_(worldPosition, BlockRegistry.ITEM_DETECTOR.get());
               this.syncClient(tile);
            }

            pufferfishAccessor.setInflateCounter(pufferfishAccessor.getInflateCounter() + 1);
         } else if (pufferfish.m_29631_() != 0) {
            if (pufferfishAccessor.getDeflateTimer() > 60 && pufferfish.m_29631_() == 2) {
               pufferfish.m_5496_(SoundEvents.f_12290_, 1.0F, pufferfish.m_6100_());
               pufferfish.m_29618_(1);
               level.m_46672_(worldPosition, BlockRegistry.ITEM_DETECTOR.get());
               this.syncClient(tile);
            } else if (pufferfishAccessor.getDeflateTimer() > 100 && pufferfish.m_29631_() == 1) {
               pufferfish.m_5496_(SoundEvents.f_12290_, 1.0F, pufferfish.m_6100_());
               pufferfish.m_29618_(0);
               level.m_46672_(worldPosition, BlockRegistry.ITEM_DETECTOR.get());
               this.syncClient(tile);
            }

            pufferfishAccessor.setDeflateTimer(pufferfishAccessor.getDeflateTimer() + 1);
         }

         if (!playerNearby) {
            pufferfishAccessor.setInflateCounter(0);
            if (pufferfish.m_29631_() == 0) {
               pufferfishAccessor.setDeflateTimer(0);
            }
         }
      }
   }

   public boolean mobsNearby(MobJarTile tile, Pufferfish pufferfish) {
      List<LivingEntity> list = pufferfish.f_19853_
         .m_6443_(
            LivingEntity.class, new AABB(tile.m_58899_()).m_82400_(2.4), p_149015_ -> PufferfishAccessor.targetConditions().m_26885_(pufferfish, p_149015_)
         );
      return !list.isEmpty();
   }

   @Override
   public int getSignalPower(MobJarTile tile) {
      Pufferfish pufferfish = (Pufferfish)tile.getEntity();
      if (pufferfish.m_29631_() == 1) {
         return 8;
      } else {
         return pufferfish.m_29631_() == 2 ? 15 : 0;
      }
   }
}
