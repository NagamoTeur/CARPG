package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.spell.ILightable;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.block.MirrorWeave;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class MirrorWeaveTile extends ModdedTile implements IAnimatable, ILightable {
   public BlockState mimicState;
   public BlockState nextState = BlockRegistry.MIRROR_WEAVE.m_49966_();
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public MirrorWeaveTile(BlockPos pos, BlockState state) {
      this(BlockRegistry.MIRROR_WEAVE_TILE, pos, state);
   }

   public MirrorWeaveTile(BlockEntityType type, BlockPos pos, BlockState state) {
      super(type, pos, state);
      this.mimicState = this.getDefaultBlockState();
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128365_("mimic_state", NbtUtils.m_129202_(this.mimicState));
   }

   public void m_142466_(CompoundTag pTag) {
      super.m_142466_(pTag);
      if (pTag.m_128441_("mimic_state")) {
         this.mimicState = NbtUtils.m_129241_(pTag.m_128469_("mimic_state"));
      } else {
         this.mimicState = this.getDefaultBlockState();
      }
   }

   public BlockState getDefaultBlockState() {
      return BlockRegistry.MIRROR_WEAVE.m_49966_();
   }

   @Override
   public void onLight(HitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats stats, SpellContext spellContext) {
      if (rayTraceResult instanceof BlockHitResult) {
         BlockState state = world.m_8055_(((BlockHitResult)rayTraceResult).m_82425_());
         world.m_7731_(
            this.m_58899_(), (BlockState)state.m_61124_(MirrorWeave.LIGHT_LEVEL, Math.min(Math.max(0, 15 - stats.getBuffCount(AugmentDampen.INSTANCE)), 15)), 3
         );
         world.m_7260_(
            ((BlockHitResult)rayTraceResult).m_82425_(),
            state,
            (BlockState)state.m_61124_(MirrorWeave.LIGHT_LEVEL, Math.min(Math.max(0, 15 - stats.getBuffCount(AugmentDampen.INSTANCE)), 15)),
            3
         );
      }

      this.updateBlock();
   }
}
