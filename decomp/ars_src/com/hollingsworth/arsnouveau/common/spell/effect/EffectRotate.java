package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.List;
import java.util.Set;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EffectRotate extends AbstractEffect {
   public static EffectRotate INSTANCE = new EffectRotate();

   public EffectRotate() {
      super(GlyphLib.EffectRotateID, "Rotate");
   }

   @Override
   public String getBookDescription() {
      return "Rotates a block or an entity clockwise. If augmented with sensitive it will change the axis of the block (if possible) or force the entity to turn their look. Dampen will rotate counter-clockwise.";
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.ONE;
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Entity entity = rayTraceResult.m_82443_();
      boolean sensitive = spellStats.isSensitive();
      int ampMod = (int)spellStats.getAmpMultiplier();
      boolean counterClockwise = ampMod < 0;

      for (int i = 0; i < 1 + Math.abs(ampMod); i++) {
         float angle = entity.m_7890_(counterClockwise ? Rotation.COUNTERCLOCKWISE_90 : Rotation.CLOCKWISE_90);
         if (sensitive) {
            entity.m_7618_(Anchor.FEET, entity.f_19825_.m_82549_(entity.m_20154_().m_82524_(angle)));
         } else {
            entity.m_146922_(angle);
         }

         if (entity instanceof Projectile projectile) {
            Vec3 vec3d = projectile.m_20184_();
            projectile.m_20256_(this.rotateVec(vec3d, counterClockwise ? -90.0F : 90.0F));
         }

         entity.f_19864_ = true;
      }
   }

   public Vec3 rotateVec(Vec3 vec, float angle) {
      double x = vec.f_82479_ * Math.cos((double)angle) - vec.f_82481_ * Math.sin((double)angle);
      double z = vec.f_82479_ * Math.sin((double)angle) + vec.f_82481_ * Math.cos((double)angle);
      return new Vec3(x, vec.f_82480_, z);
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      List<BlockPos> posList = SpellUtil.calcAOEBlocks(shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats);
      boolean swapAxis = spellStats.isSensitive();

      for (BlockPos pos : posList) {
         BlockState state = world.m_8055_(pos);
         int ampMod = (int)spellStats.getAmpMultiplier();
         boolean counterClockwise = ampMod < 0;

         for (int i = 0; i < (counterClockwise ? 0 : 1) + Math.abs(ampMod); i++) {
            if (swapAxis) {
               if (state.m_61138_(BlockStateProperties.f_61365_)) {
                  state = (BlockState)state.m_61124_(BlockStateProperties.f_61365_, switch ((Axis)state.m_61143_(BlockStateProperties.f_61365_)) {
                     case X -> counterClockwise ? Axis.Z : Axis.Y;
                     case Y -> counterClockwise ? Axis.X : Axis.Z;
                     case Z -> counterClockwise ? Axis.Y : Axis.X;
                     default -> throw new IncompatibleClassChangeError();
                  });
               } else if (state.m_61138_(BlockStateProperties.f_61372_)) {
                  Direction curr = (Direction)state.m_61143_(BlockStateProperties.f_61372_);

                  state = (BlockState)state.m_61124_(BlockStateProperties.f_61372_, switch (curr) {
                     case DOWN -> counterClockwise ? Direction.SOUTH : Direction.NORTH;
                     case UP -> counterClockwise ? Direction.NORTH : Direction.SOUTH;
                     case NORTH, EAST -> counterClockwise ? Direction.DOWN : Direction.UP;
                     case SOUTH, WEST -> counterClockwise ? Direction.UP : Direction.DOWN;
                     default -> throw new IncompatibleClassChangeError();
                  });
               }
            } else {
               state = state.rotate(world, pos, counterClockwise ? Rotation.COUNTERCLOCKWISE_90 : Rotation.CLOCKWISE_90);
            }
         }

         world.m_46597_(pos, state);
         ShapersFocus.tryPropagateBlockSpell(rayTraceResult, world, shooter, spellContext, resolver);
      }
   }

   @Override
   public int getDefaultManaCost() {
      return 10;
   }

   @NotNull
   @Override
   protected Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }

   @NotNull
   @Override
   protected Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{AugmentAmplify.INSTANCE, AugmentSensitive.INSTANCE, AugmentAOE.INSTANCE, AugmentPierce.INSTANCE, AugmentDampen.INSTANCE}
      );
   }
}
