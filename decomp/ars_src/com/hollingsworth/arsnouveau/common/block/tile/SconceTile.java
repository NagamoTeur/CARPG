package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.particle.ParticleColorRegistry;
import com.hollingsworth.arsnouveau.api.spell.ILightable;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.SconceBlock;
import com.hollingsworth.arsnouveau.common.block.ScribesBlock;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public class SconceTile extends ModdedTile implements ILightable, ITickable, IDispellable {
   public ParticleColor color = ParticleColor.defaultParticleColor();
   public boolean lit;

   public SconceTile(BlockPos pos, BlockState state) {
      this(BlockRegistry.SCONCE_TILE, pos, state);
   }

   public SconceTile(BlockEntityType type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public void m_142466_(CompoundTag nbt) {
      super.m_142466_(nbt);
      this.color = ParticleColorRegistry.from(nbt.m_128469_("color"));
      this.lit = nbt.m_128471_("lit");
   }

   @Override
   public void m_183515_(CompoundTag compound) {
      super.m_183515_(compound);
      compound.m_128365_("color", this.color.serialize());
      compound.m_128379_("lit", this.lit);
   }

   @Override
   public void onLight(HitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats stats, SpellContext spellContext) {
      this.color = spellContext.getColors().clone();
      this.lit = true;
      if (rayTraceResult instanceof BlockHitResult) {
         BlockState state = world.m_8055_(((BlockHitResult)rayTraceResult).m_82425_());
         world.m_7731_(
            this.m_58899_(), (BlockState)state.m_61124_(SconceBlock.LIGHT_LEVEL, Math.min(Math.max(0, 15 - stats.getBuffCount(AugmentDampen.INSTANCE)), 15)), 3
         );
         world.m_7260_(
            ((BlockHitResult)rayTraceResult).m_82425_(),
            state,
            (BlockState)state.m_61124_(SconceBlock.LIGHT_LEVEL, Math.min(Math.max(0, 15 - stats.getBuffCount(AugmentDampen.INSTANCE)), 15)),
            3
         );
      }

      this.updateBlock();
   }

   @Override
   public void tick() {
      if (this.f_58857_.m_5776_() && this.lit) {
         BlockPos pos = this.m_58899_();
         double xzOffset = 0.15;
         BlockState state = this.m_58904_().m_8055_(this.m_58899_());
         if (state.m_61138_(ScribesBlock.FACING)) {
            double xOffset = ParticleUtil.inRange(-xzOffset / 4.0, xzOffset / 4.0);
            double zOffset = ParticleUtil.inRange(-xzOffset / 4.0, xzOffset / 4.0);
            double centerX = (double)pos.m_123341_() + xOffset;
            double centerZ = (double)pos.m_123343_() + zOffset;
            if (state.m_61143_(ScribesBlock.FACING) == Direction.NORTH) {
               centerX += 0.5;
               centerZ += 0.8;
            }

            if (state.m_61143_(ScribesBlock.FACING) == Direction.SOUTH) {
               centerX += 0.5;
               centerZ += 0.2;
            }

            if (state.m_61143_(ScribesBlock.FACING) == Direction.EAST) {
               centerX += 0.2;
               centerZ += 0.5;
            }

            if (state.m_61143_(ScribesBlock.FACING) == Direction.WEST) {
               centerX += 0.8;
               centerZ += 0.5;
            }

            ParticleColor nextColor = this.color.transition((int)this.f_58857_.m_46467_() * 10);

            for (int i = 0; i < 10; i++) {
               this.f_58857_
                  .m_7106_(
                     GlowParticleData.createData(nextColor),
                     centerX,
                     (double)pos.m_123342_() + 0.9 + ParticleUtil.inRange(-0.0, 0.1),
                     centerZ,
                     0.0,
                     ParticleUtil.inRange(0.0, 0.03F),
                     0.0
                  );
            }
         }
      }
   }

   @Override
   public boolean onDispel(@Nullable LivingEntity caster) {
      this.lit = false;
      this.f_58857_.m_7731_(this.m_58899_(), (BlockState)this.f_58857_.m_8055_(this.m_58899_()).m_61124_(SconceBlock.LIGHT_LEVEL, 0), 3);
      this.updateBlock();
      return true;
   }
}
