package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.MirrorWeave;
import com.hollingsworth.arsnouveau.common.event.timed.SkyweaveVisibilityEvent;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SkyBlockTile extends MirrorWeaveTile implements ITickable, IDispellable {
   private boolean showFacade;
   public int previousLight;

   public SkyBlockTile(BlockPos pos, BlockState state) {
      super((BlockEntityType)BlockRegistry.SKYWEAVE_TILE.get(), pos, state);
   }

   @Override
   public void tick() {
      if (this.showFacade && !this.f_58857_.f_46443_) {
         if ((Integer)this.m_58900_().m_61143_(MirrorWeave.LIGHT_LEVEL) != this.mimicState.getLightEmission(this.f_58857_, this.f_58858_)) {
            this.f_58857_
               .m_46597_(
                  this.f_58858_, (BlockState)this.m_58900_().m_61124_(MirrorWeave.LIGHT_LEVEL, this.mimicState.getLightEmission(this.f_58857_, this.f_58858_))
               );
         }
      } else {
         if (!this.f_58857_.f_46443_ && this.f_58857_.m_46461_() && (Integer)this.m_58900_().m_61143_(MirrorWeave.LIGHT_LEVEL) != 15) {
            this.previousLight = (Integer)this.m_58900_().m_61143_(MirrorWeave.LIGHT_LEVEL);
            this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.m_58900_().m_61124_(MirrorWeave.LIGHT_LEVEL, 15));
         }

         if (!this.f_58857_.f_46443_ && !this.f_58857_.m_46461_() && (Integer)this.m_58900_().m_61143_(MirrorWeave.LIGHT_LEVEL) != this.previousLight) {
            this.f_58857_
               .m_46597_(
                  this.f_58858_, (BlockState)this.m_58900_().m_61124_(MirrorWeave.LIGHT_LEVEL, this.mimicState.getLightEmission(this.f_58857_, this.f_58858_))
               );
         }
      }
   }

   public void setShowFacade(boolean showFacade) {
      if (this.showFacade != showFacade) {
         int ticks = 1;

         for (Direction d : Direction.values()) {
            BlockPos offset = this.m_58899_().m_121945_(d);
            BlockEntity var9 = this.f_58857_.m_7702_(offset);
            if (var9 instanceof SkyBlockTile) {
               SkyBlockTile neighbor = (SkyBlockTile)var9;
               if (this.showFacade() == neighbor.showFacade()) {
                  EventQueue.getServerInstance().addEvent(new SkyweaveVisibilityEvent(neighbor, ++ticks, showFacade));
               }
            }
         }

         this.showFacade = showFacade;
         this.updateBlock();
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128379_("showFacade", this.showFacade);
      tag.m_128405_("previousLight", this.previousLight);
   }

   @Override
   public void m_142466_(CompoundTag pTag) {
      super.m_142466_(pTag);
      this.showFacade = pTag.m_128471_("showFacade");
      this.previousLight = pTag.m_128451_("previousLight");
   }

   public boolean showFacade() {
      return this.showFacade;
   }

   @Override
   public boolean onDispel(@NotNull LivingEntity caster) {
      this.setShowFacade(!this.showFacade());
      return true;
   }
}
