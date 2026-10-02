package com.hollingsworth.arsnouveau.common.entity.goal.wixie;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import com.hollingsworth.arsnouveau.common.entity.goal.ExtendedRangeGoal;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CompleteCraftingGoal extends ExtendedRangeGoal {
   EntityWixie wixie;
   int ticksNearby;
   boolean hasCast;

   public CompleteCraftingGoal(EntityWixie wixie) {
      super(10);
      this.wixie = wixie;
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      this.ticksNearby = 0;
      this.hasCast = false;
      this.startDistance = BlockUtil.distanceFrom(this.wixie.f_19825_, this.wixie.cauldronPos.m_7494_());
   }

   @Override
   public boolean m_8036_() {
      if (this.wixie.cauldronPos == null) {
         return false;
      } else {
         BlockEntity tileEntity = this.wixie.f_19853_.m_7702_(this.wixie.cauldronPos);
         return tileEntity instanceof WixieCauldronTile && ((WixieCauldronTile)tileEntity).isCraftingDone();
      }
   }

   @Override
   public void m_8037_() {
      super.m_8037_();
      if (BlockUtil.distanceFrom(this.wixie.m_20182_(), this.wixie.cauldronPos.m_7494_()) < 1.5 + this.extendedRange) {
         this.ticksNearby++;
         if (!this.hasCast) {
            this.wixie.inventoryBackoff = 40;
         }

         if (this.ticksNearby >= 40
            && this.wixie.f_19853_.m_7702_(this.wixie.cauldronPos) instanceof WixieCauldronTile cauldronTile
            && cauldronTile.isCraftingDone()) {
            cauldronTile.attemptFinish();
         }

         this.hasCast = true;
      } else {
         this.setPath((double)this.wixie.cauldronPos.m_123341_(), (double)this.wixie.cauldronPos.m_123342_(), (double)this.wixie.cauldronPos.m_123343_(), 1.2);
      }
   }

   public void setPath(double x, double y, double z, double speedIn) {
      this.wixie.m_21573_().m_26536_(this.wixie.m_21573_().m_26524_(x + 0.5, y + 1.5, z + 0.5, 0), speedIn);
   }
}
