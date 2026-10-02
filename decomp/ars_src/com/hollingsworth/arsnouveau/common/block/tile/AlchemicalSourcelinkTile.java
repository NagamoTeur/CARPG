package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AlchemicalSourcelinkTile extends SourcelinkTile {
   public AlchemicalSourcelinkTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.ALCHEMICAL_TILE, pos, state);
   }

   @Override
   public int getMaxSource() {
      return 20000;
   }

   @Override
   public int getTransferRate() {
      return 10000;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.f_58857_ instanceof ServerLevel && this.f_58857_.m_46467_() % 20L == 0L && this.canAcceptSource()) {
         BlockPos potionPos = findNearbyPotion(this.f_58857_, this.f_58858_);
         if (potionPos != null && this.f_58857_.m_7702_(potionPos) instanceof PotionJarTile tile) {
            int source = 75;
            Set<MobEffect> effectTypes = new HashSet<>();

            for (MobEffectInstance e : tile.getData().fullEffects()) {
               source += e.m_19557_() / 50;
               source += e.m_19564_() * 250;
               source += 150;
               effectTypes.add(e.m_19544_());
            }

            if (effectTypes.size() > 1) {
               source = (int)((double)source * Math.pow(2.1, (double)effectTypes.size()));
            }

            if (source > 0 && this.canAcceptSource(source) || this.getSource() <= 0) {
               this.addSource(source);
               tile.remove(100);
            }
         }
      }
   }

   @Nullable
   public static BlockPos findNearbyPotion(Level level, BlockPos worldPosition) {
      for (BlockPos p : BlockPos.m_121925_(worldPosition.m_6625_(1), 1, 1, 1)) {
         if (level.m_7702_(p) instanceof PotionJarTile tile && tile.getAmount() >= 100) {
            return p;
         }
      }

      return null;
   }
}
