package com.hollingsworth.arsnouveau.common.entity.goal.wixie;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import com.hollingsworth.arsnouveau.common.entity.EntityFollowProjectile;
import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import com.hollingsworth.arsnouveau.common.entity.goal.ExtendedRangeGoal;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketAnimEntity;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FindPotionGoal extends ExtendedRangeGoal {
   EntityWixie wixie;
   BlockPos movePos;
   boolean found;
   Potion potionNeeded;

   public FindPotionGoal(EntityWixie wixie) {
      super(15);
      this.wixie = wixie;
      this.m_7021_(EnumSet.of(Flag.LOOK, Flag.MOVE));
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      BlockEntity tileEntity = this.wixie.f_19853_.m_7702_(this.wixie.cauldronPos);
      this.found = false;
      if (tileEntity instanceof WixieCauldronTile cauldronTile) {
         this.potionNeeded = cauldronTile.getNeededPotion();
         this.movePos = WixieCauldronTile.findNeededPotion(this.potionNeeded, 300, this.wixie.f_19853_, this.wixie.cauldronPos);
         this.startDistance = BlockUtil.distanceFrom(this.wixie.f_19825_, this.movePos);
      } else {
         this.found = true;
      }

      if (this.movePos == null) {
         this.found = true;
      }
   }

   @Override
   public void m_8037_() {
      super.m_8037_();
      if (!this.found) {
         if (this.movePos != null && BlockUtil.distanceFrom(this.wixie.m_20182_(), this.movePos.m_7494_()) < 2.0 + this.extendedRange) {
            WixieCauldronTile tile = (WixieCauldronTile)this.wixie.m_20193_().m_7702_(this.wixie.cauldronPos);
            Level world = this.wixie.m_20193_();
            if (tile == null) {
               this.found = true;
               return;
            }

            PotionJarTile jar = (PotionJarTile)world.m_7702_(this.movePos);
            if (jar == null) {
               this.found = true;
               return;
            }

            jar.remove(300);
            tile.givePotion();
            Networking.sendToNearby(world, this.wixie, new PacketAnimEntity(this.wixie.m_19879_(), EntityWixie.Animations.SUMMON_ITEM.ordinal()));
            int color = jar.getColor();
            int r = color >> 16 & 0xFF;
            int g = color >> 8 & 0xFF;
            int b = color & 0xFF;
            EntityFollowProjectile aoeProjectile = new EntityFollowProjectile(world, this.movePos, this.wixie.cauldronPos, r, g, b);
            world.m_7967_(aoeProjectile);
            this.found = true;
         }

         if (this.movePos != null && !this.found) {
            this.setPath((double)this.movePos.m_123341_(), (double)(this.movePos.m_123342_() + 1), (double)this.movePos.m_123343_(), 1.2);
         }
      }
   }

   @Override
   public boolean m_8036_() {
      if (this.wixie.cauldronPos == null) {
         return false;
      } else {
         if (this.wixie.inventoryBackoff == 0
            && this.wixie.f_19853_.m_7702_(this.wixie.cauldronPos) instanceof WixieCauldronTile cauldronTile
            && cauldronTile.hasSource
            && cauldronTile.needsPotion()
            && !cauldronTile.isOff) {
            return true;
         }

         return false;
      }
   }

   public boolean m_8045_() {
      return !this.found;
   }

   public void setPath(double x, double y, double z, double speedIn) {
      this.wixie.m_21573_().m_26536_(this.wixie.m_21573_().m_26524_(x + 0.5, y + 0.5, z + 0.5, 0), speedIn);
   }

   @Override
   public void m_8041_() {
      super.m_8041_();
      this.potionNeeded = Potions.f_43598_;
      this.found = false;
   }
}
