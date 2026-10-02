package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.RitualBrazierBlock;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BrazierRelayTile extends RitualBrazierTile {
   int ticksToLightOff = 0;
   public BlockPos brazierPos;
   private static List<BlockPos> relayingTraversed = new ArrayList<>();

   public BrazierRelayTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public BrazierRelayTile(BlockPos p, BlockState s) {
      super((BlockEntityType<?>)BlockRegistry.BRAZIER_RELAY_TILE.get(), p, s);
   }

   @Override
   public void tick() {
      if (this.isDecorative && this.f_58857_.f_46443_) {
         this.makeParticle(this.color.transition((int)this.f_58857_.m_46467_() * 10), this.color.transition((int)this.f_58857_.m_46467_() * 10), 5);
      }

      if (!this.f_58857_.f_46443_) {
         this.ticksToLightOff--;
         if (this.ticksToLightOff <= 0) {
            this.ticksToLightOff = 0;
            if (!this.isDecorative && (Boolean)this.f_58857_.m_8055_(this.f_58858_).m_61143_(RitualBrazierBlock.LIT)) {
               this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.f_58857_.m_8055_(this.f_58858_).m_61124_(RitualBrazierBlock.LIT, false));
            }
         }

         if (!(Boolean)this.f_58857_.m_8055_(this.f_58858_).m_61143_(RitualBrazierBlock.LIT) && this.ticksToLightOff > 0) {
            this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.f_58857_.m_8055_(this.f_58858_).m_61124_(RitualBrazierBlock.LIT, true));
         }
      }
   }

   @Override
   public void makeParticle(ParticleColor centerColor, ParticleColor outerColor, int intensity) {
      Level world = this.m_58904_();
      BlockPos pos = this.m_58899_();
      double xzOffset = 0.25;

      for (int i = 0; i < intensity; i++) {
         world.m_7106_(
            GlowParticleData.createData(centerColor.transition((int)this.f_58857_.m_46467_() * 20)),
            (double)pos.m_123341_() + 0.5 + ParticleUtil.inRange(-xzOffset / 2.0, xzOffset / 2.0),
            (double)pos.m_123342_() + 0.2 + ParticleUtil.inRange(-0.05, 0.2),
            (double)pos.m_123343_() + 0.5 + ParticleUtil.inRange(-xzOffset / 2.0, xzOffset / 2.0),
            0.0,
            ParticleUtil.inRange(0.0, 0.05F),
            0.0
         );
      }

      for (int i = 0; i < intensity; i++) {
         world.m_7106_(
            GlowParticleData.createData(outerColor.transition((int)this.f_58857_.m_46467_() * 20)),
            (double)pos.m_123341_() + 0.5 + ParticleUtil.inRange(-xzOffset, xzOffset),
            (double)pos.m_123342_() + 0.2 + ParticleUtil.inRange(0.0, 0.7),
            (double)pos.m_123343_() + 0.5 + ParticleUtil.inRange(-xzOffset, xzOffset),
            0.0,
            ParticleUtil.inRange(0.0, 0.05F),
            0.0
         );
      }
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
   }

   @Override
   public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null && this.f_58857_ != null && this.f_58857_.m_7702_(storedPos) instanceof RitualBrazierTile brazierTile) {
         if (BlockUtil.distanceFrom(this.m_58899_(), storedPos) > 16.0) {
            return;
         }

         this.brazierPos = storedPos;
         this.updateBlock();
      }
   }

   @Override
   public void onWanded(Player playerEntity) {
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128405_("ticksToLightOff", this.ticksToLightOff);
      if (this.brazierPos != null) {
         tag.m_128356_("brazierPos", this.brazierPos.m_121878_());
      }
   }

   @Override
   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      this.ticksToLightOff = tag.m_128451_("ticksToLightOff");
      if (tag.m_128441_("brazierPos")) {
         this.brazierPos = BlockPos.m_122022_(tag.m_128454_("brazierPos"));
      }
   }

   @Override
   public InventoryManager getInventoryManager() {
      if (this.brazierPos != null
         && this.f_58857_ != null
         && relayingTraversed.size() < 256
         && !relayingTraversed.contains(this.brazierPos)
         && this.f_58857_.m_46749_(this.brazierPos)
         && this.f_58857_.m_7702_(this.brazierPos) instanceof RitualBrazierTile brazierTile) {
         relayingTraversed.add(this.brazierPos);
         InventoryManager brazierInv = brazierTile.getInventoryManager();
         if (!brazierInv.getInventory().isEmpty()) {
            relayingTraversed.clear();
            return brazierInv;
         }
      }

      relayingTraversed.clear();
      return super.getInventoryManager();
   }
}
