package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.particle.ParticleColorRegistry;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LightTile extends ModdedTile implements ITickable {
   public ParticleColor color = ParticleColor.defaultParticleColor();
   public static RandomSource random = RandomSource.m_216343_();

   public LightTile(BlockPos pos, BlockState state) {
      this(BlockRegistry.LIGHT_TILE, pos, state);
   }

   public LightTile(BlockEntityType<?> lightTile, BlockPos pos, BlockState state) {
      super(lightTile, pos, state);
   }

   @Override
   public void tick(Level level, BlockState state, BlockPos pos) {
      if (level.f_46443_) {
         if (this.color.getColor() == 65793) {
            return;
         }

         level.m_7106_(
            GlowParticleData.createData(this.color.transition((int)(level.m_46467_() * 20L)), 0.25F, 0.9F, 36),
            (double)pos.m_123341_() + 0.5 + ParticleUtil.inRange(-0.1, 0.1),
            (double)pos.m_123342_() + 0.5 + ParticleUtil.inRange(-0.1, 0.1),
            (double)pos.m_123343_() + 0.5 + ParticleUtil.inRange(-0.1, 0.1),
            0.0,
            0.0,
            0.0
         );
      }
   }

   public void m_142466_(CompoundTag nbt) {
      super.m_142466_(nbt);
      this.color = ParticleColorRegistry.from(nbt.m_128469_("color"));
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128365_("color", this.color.serialize());
   }
}
