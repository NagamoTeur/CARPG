package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IDamageEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.entity.EntityEvokerFangs;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAccelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectFangs extends AbstractEffect implements IDamageEffect {
   public static EffectFangs INSTANCE = new EffectFangs();

   private EffectFangs() {
      super(GlyphLib.EffectFangsID, "Fangs");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (shooter == null && spellContext.castingTile != null) {
         shooter = ANFakePlayer.getPlayer((ServerLevel)world);
         BlockPos pos = spellContext.castingTile.m_58899_();
         shooter.m_6034_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      }

      if (shooter != null) {
         Vec3 vec = rayTraceResult.m_82450_();
         double damage = (Double)this.DAMAGE.get() + (Double)this.AMP_VALUE.get() * spellStats.getAmpMultiplier();
         double targetX = vec.f_82479_;
         double targetY = vec.f_82480_;
         double targetZ = vec.f_82481_;
         double d0 = Math.min(targetY, shooter.m_20186_());
         double d1 = Math.max(targetY, shooter.m_20186_()) + 1.0;
         float f = (float)Mth.m_14136_(targetZ - shooter.m_20189_(), targetX - shooter.m_20185_());
         int accelerate = spellStats.getBuffCount(AugmentAccelerate.INSTANCE);
         double durationModifier = spellStats.getDurationMultiplier();
         if (rayTraceResult instanceof EntityHitResult && shooter.equals(((EntityHitResult)rayTraceResult).m_82443_())) {
            for (int i = 0; i < 5; i++) {
               float f1 = f + (float)i * (float) Math.PI * 0.4F;
               int j = (int)(((double)i + durationModifier) / (double)(1 + accelerate));
               this.spawnFangs(
                  world,
                  shooter.m_20185_() + (double)Mth.m_14089_(f1) * 1.5,
                  shooter.m_20189_() + (double)Mth.m_14031_(f1) * 1.5,
                  d0,
                  d1,
                  f1,
                  j,
                  shooter,
                  (float)damage
               );
            }

            for (int k = 0; k < 8; k++) {
               float f2 = f + (float)k * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
               int j = (int)(((double)k + durationModifier) / (double)(1 + accelerate));
               this.spawnFangs(
                  world,
                  shooter.m_20185_() + (double)Mth.m_14089_(f2) * 2.5,
                  shooter.m_20189_() + (double)Mth.m_14031_(f2) * 2.5,
                  d0,
                  d1,
                  f2,
                  j,
                  shooter,
                  (float)damage
               );
            }
         } else {
            for (int l = 0; l < 16; l++) {
               double d2 = 1.25 * (double)(l + 1);
               int j = (int)(((double)l + durationModifier) / (double)(1 + accelerate));
               this.spawnFangs(
                  world,
                  shooter.m_20185_() + (double)Mth.m_14089_(f) * d2,
                  shooter.m_20189_() + (double)Mth.m_14031_(f) * d2,
                  d0,
                  d1,
                  f,
                  j,
                  shooter,
                  (float)damage
               );
            }
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addDamageConfig(builder, 6.0);
      this.addAmpConfig(builder, 3.0);
   }

   @Override
   protected void addDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 2);
   }

   private void spawnFangs(
      Level world, double xAngle, double zAngle, double yStart, double yEnd, float rotationYaw, int tickDelay, LivingEntity caster, float damage
   ) {
      BlockPos blockpos = new BlockPos(xAngle, yEnd, zAngle);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.m_7495_();
         BlockState blockstate = world.m_8055_(blockpos1);
         if (blockstate.m_60783_(world, blockpos1, Direction.UP)) {
            if (!world.m_46859_(blockpos)) {
               BlockState blockstate1 = world.m_8055_(blockpos);
               VoxelShape voxelshape = blockstate1.m_60812_(world, blockpos);
               if (!voxelshape.m_83281_()) {
                  d0 = voxelshape.m_83297_(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= Mth.m_14107_(yStart) - 1);

      if (flag) {
         world.m_7967_(new EntityEvokerFangs(world, xAngle, (double)blockpos.m_123342_() + d0, zAngle, rotationYaw, tickDelay, caster, damage));
      }
   }

   @Override
   public int getDefaultManaCost() {
      return 35;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.THREE;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE, AugmentAccelerate.INSTANCE
         }
      );
   }

   @Override
   public String getBookDescription() {
      return "Summons Evoker Fangs in the direction where the spell was targeted. Using fangs on your self will spawn them in an area around you.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.CONJURATION});
   }
}
