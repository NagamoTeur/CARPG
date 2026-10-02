package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.MagelightTorch;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.HitResult;

public class MagelightTorchTile extends SconceTile {
   private boolean horizontalFire;

   public MagelightTorchTile(BlockPos pPos, BlockState pBlockState) {
      super((BlockEntityType)BlockRegistry.MAGELIGHT_TORCH_TILE.get(), pPos, pBlockState);
   }

   @Override
   public void onLight(HitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats stats, SpellContext spellContext) {
      super.onLight(rayTraceResult, world, shooter, stats, spellContext);
   }

   @Override
   public void tick() {
      if (this.f_58857_.m_5776_() && this.lit) {
         BlockPos pos = this.m_58899_();
         double xzOffset = 0.15;
         BlockState state = this.m_58904_().m_8055_(this.m_58899_());
         boolean onFloor = state.m_61138_(MagelightTorch.FLOOR) && (Boolean)state.m_61143_(MagelightTorch.FLOOR);
         boolean onRoof = state.m_61138_(MagelightTorch.ROOF) && (Boolean)state.m_61143_(MagelightTorch.ROOF);
         double yOffset = onFloor ? 0.4 : 0.5;
         yOffset = onRoof ? 0.5 : yOffset;
         double xOffset = ParticleUtil.inRange(-xzOffset / 4.0, xzOffset / 4.0) + 0.5;
         double zOffset = ParticleUtil.inRange(-xzOffset / 4.0, xzOffset / 4.0) + 0.5;
         double centerX = (double)pos.m_123341_() + xOffset;
         double centerZ = (double)pos.m_123343_() + zOffset;
         double xSpeedOffset = 0.0;
         double ySpeedOffset = ParticleUtil.inRange(0.0, 0.03F);
         double zSpeedOffset = 0.0;
         xSpeedOffset = ParticleUtil.inRange(-0.01F, 0.01F);
         zSpeedOffset = ParticleUtil.inRange(-0.01F, 0.01F);
         if (!onFloor && !onRoof && this.isHorizontalFire() && state.m_61138_(BlockStateProperties.f_61372_)) {
            Direction facing = (Direction)state.m_61143_(BlockStateProperties.f_61372_);
            switch (facing) {
               case NORTH:
                  zSpeedOffset = ParticleUtil.inRange(-0.03F, 0.0);
                  break;
               case SOUTH:
                  zSpeedOffset = ParticleUtil.inRange(0.0, 0.03F);
                  break;
               case EAST:
                  xSpeedOffset = ParticleUtil.inRange(0.0, 0.03F);
                  break;
               case WEST:
                  xSpeedOffset = ParticleUtil.inRange(-0.03F, 0.0);
            }

            ySpeedOffset = ParticleUtil.inRange(-0.01F, 0.01F);
         }

         ySpeedOffset = onRoof ? -ySpeedOffset : ySpeedOffset;
         ParticleColor nextColor = this.color.transition((int)this.f_58857_.m_46467_() * 10);

         for (int i = 0; i < 5; i++) {
            this.f_58857_
               .m_7106_(
                  GlowParticleData.createData(nextColor),
                  centerX,
                  (double)pos.m_123342_() + yOffset + ParticleUtil.inRange(-0.0, 0.1),
                  centerZ,
                  xSpeedOffset,
                  ySpeedOffset,
                  zSpeedOffset
               );
         }
      }
   }

   public void setHorizontalFire(boolean horizontalFire) {
      this.horizontalFire = horizontalFire;
      this.updateBlock();
   }

   public boolean isHorizontalFire() {
      return this.horizontalFire;
   }

   @Override
   public void m_183515_(CompoundTag compound) {
      super.m_183515_(compound);
      compound.m_128379_("horizontalFire", this.horizontalFire);
   }

   @Override
   public void m_142466_(CompoundTag nbt) {
      super.m_142466_(nbt);
      this.horizontalFire = nbt.m_128471_("horizontalFire");
   }
}
