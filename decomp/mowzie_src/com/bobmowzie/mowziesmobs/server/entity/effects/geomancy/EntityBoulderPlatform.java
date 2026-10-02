package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.google.common.collect.Iterables;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import software.bernie.shadowed.eliotlash.mclib.utils.MathHelper;

public class EntityBoulderPlatform extends EntityBoulderBase {
   private static final float MAX_DIST_HORIZONTAL = 4.0F;
   private static final float MAX_DIST_VERTICAL = 2.4F;
   private static final int MAX_TRIES = 10;
   private EntityBoulderPlatform nextBoulder;
   private EntitySculptor sculptor;
   private EntityPillar pillar;
   protected boolean isMainPath = false;
   protected boolean descending = false;
   private boolean spawnedNextBoulders = false;

   public EntityBoulderPlatform(EntityType<? extends EntityBoulderBase> type, Level world) {
      super(type, world);
   }

   public EntityBoulderPlatform(
      EntityType<? extends EntityBoulderBase> type, Level world, LivingEntity caster, BlockState blockState, BlockPos pos, EntityGeomancyBase.GeomancyTier tier
   ) {
      super(type, world, caster, blockState, pos, tier);
   }

   public void descend() {
      this.descending = true;
   }

   @Override
   public boolean doRemoveTimer() {
      return false;
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if ((this.sculptor == null || this.pillar == null) && this.caster instanceof EntitySculptor) {
         this.sculptor = (EntitySculptor)this.caster;
         this.pillar = this.sculptor.getPillar();
      }

      if (this.tickTimer() <= 2
         || !this.hasSyncedCaster
         || this.sculptor != null
            && !this.sculptor.m_213877_()
            && this.pillar != null
            && !this.pillar.m_213877_()
            && (!this.pillar.isFalling() || this.descending)) {
         if (this.tickTimer() >= 2 && !this.spawnedNextBoulders && this.hasSyncedCaster) {
            this.nextBoulders();
         }

         if (this.pillar != null && !this.f_19853_.m_5776_() && this.pillar.m_20186_() + (double)this.pillar.getHeight() >= this.m_20186_()) {
            this.activate();
         }

         if (this.descending) {
            this.m_6478_(MoverType.SELF, new Vec3(0.0, -0.25, 0.0));
            if (Iterables.size(this.f_19853_.m_186434_(this, this.m_20191_().m_82400_(0.1))) > 0) {
               this.m_146870_();
               return;
            }
         }
      } else {
         this.explode();
      }
   }

   public void nextBoulders() {
      if (this.caster != null && this.sculptor != null && this.pillar != null) {
         this.spawnedNextBoulders = true;
         if (!this.f_19853_.m_5776_()) {
            if (!this.isMainPath && (double)this.f_19796_.m_188501_() < MathUtils.fit((double)this.sculptor.numLivePaths, 3.0, 7.0, 0.0, 0.4)) {
               this.sculptor.numLivePaths--;
            } else {
               int numNextBoulders = 1;
               if ((double)this.f_19796_.m_188501_() < MathUtils.fit((double)this.sculptor.numLivePaths, 1.0, 5.0, 0.2, 0.0)) {
                  numNextBoulders = 2;
               }

               for (int i = 0; i < numNextBoulders; i++) {
                  boolean success = this.nextSingleBoulder();
                  if (success && i > 0) {
                     this.sculptor.numLivePaths++;
                  }
               }
            }
         }
      }
   }

   public boolean nextSingleBoulder() {
      int whichTierIndex = (int)(Math.pow((double)this.f_19796_.m_188501_(), 2.0) * (double)(EntityGeomancyBase.GeomancyTier.values().length - 2) + 1.0);
      EntityGeomancyBase.GeomancyTier nextTier = EntityGeomancyBase.GeomancyTier.values()[whichTierIndex];
      EntityBoulderPlatform nextBoulder = new EntityBoulderPlatform(
         (EntityType<? extends EntityBoulderBase>)EntityHandler.BOULDER_PLATFORM.get(), this.m_9236_(), this.caster, this.getBlock(), this.m_20183_(), nextTier
      );

      for (int j = 0; j < 10; j++) {
         Vec3 randomPos;
         if (this.getHeightFrac() < 1.0F) {
            randomPos = this.chooseRandomLocation(nextBoulder);
         } else {
            if (!(this.m_20182_().m_82542_(1.0, 0.0, 1.0).m_82554_(this.sculptor.m_20182_().m_82542_(1.0, 0.0, 1.0)) > 4.0)) {
               return false;
            }

            randomPos = this.chooseTowardsSculptorLocation(nextBoulder);
         }

         nextBoulder.m_146884_(randomPos);
         if (this.f_19853_.m_6443_(EntityBoulderPlatform.class, nextBoulder.m_20191_(), b -> b != this).isEmpty()
            && Iterables.size(this.f_19853_.m_186434_(nextBoulder, nextBoulder.m_20191_())) == 0
            && !this.pillar.m_20191_().m_165893_(this.pillar.m_20186_() + (double)EntitySculptor.TEST_HEIGHT).m_82381_(nextBoulder.m_20191_())) {
            AABB toCheck = nextBoulder.m_20191_().m_82377_(4.0, 2.7F, 4.0).m_82386_(0.0, -2.7F, 0.0);
            List<EntityBoulderPlatform> platforms = this.f_19853_.m_45976_(EntityBoulderPlatform.class, toCheck);
            boolean obstructsPath = false;

            for (EntityBoulderPlatform platform : platforms) {
               if (platform != nextBoulder && !nextBoulder.checkJumpPath(platform)) {
                  obstructsPath = true;
                  break;
               }
            }

            if (!obstructsPath) {
               this.m_9236_().m_7967_(nextBoulder);
               if (this.isMainPath && this.nextBoulder == null) {
                  this.nextBoulder = nextBoulder;
                  this.nextBoulder.setMainPath();
               }

               return true;
            }
         }
      }

      if (this.isMainPath) {
         this.spawnedNextBoulders = false;
      }

      return false;
   }

   protected Vec3 chooseRandomLocation(EntityBoulderPlatform nextBoulder) {
      EntityDimensions thisDims = SIZE_MAP.get(this.getTier());
      EntityDimensions nextDims = SIZE_MAP.get(nextBoulder.getTier());
      Vec3 startLocation = this.m_20182_();
      Vec2 fromPillarPos = new Vec2((float)(this.caster.m_20185_() - startLocation.f_82479_), (float)(this.caster.m_20189_() - startLocation.f_82481_));
      float horizontalOffset = Mth.m_216267_(this.f_19796_, 1.0F, 4.0F) + thisDims.f_20377_ / 2.0F + nextDims.f_20377_ / 2.0F;
      float verticalOffset = Mth.m_216267_(this.f_19796_, 0.0F, 2.4F) - (nextDims.f_20378_ - thisDims.f_20378_);
      float baseAngle = (float)(-Math.toDegrees(Math.atan2((double)fromPillarPos.f_82471_, (double)fromPillarPos.f_82470_)));
      float minRandomAngle = (float)(Math.min(Math.pow(3.0, (double)(-fromPillarPos.m_165907_() + 3.0F)), 1.0) * 90.0);
      double radius = EntitySculptor.testRadiusAtHeight(startLocation.f_82480_ + (double)verticalOffset + (double)nextDims.f_20378_ - this.pillar.m_20186_());
      float maxRandomAngle = 180.0F - (float)(Math.min(Math.pow(3.0, (double)fromPillarPos.m_165907_() - radius), 1.0) * 90.0);
      float randomAngle = Mth.m_216267_(this.f_19796_, minRandomAngle, maxRandomAngle);
      if (this.f_19796_.m_188499_()) {
         randomAngle *= -1.0F;
      }

      randomAngle = (float)((double)randomAngle * (1.0 - Math.pow((double)this.getHeightFrac(), 5.0) * 0.75));
      Vec3 offset = new Vec3((double)horizontalOffset, (double)verticalOffset, 0.0);
      float finalAngle = (float)Math.toRadians((double)MathHelper.wrapDegrees(baseAngle + randomAngle));
      offset = offset.m_82524_(finalAngle);
      Vec3 nextLocation = startLocation.m_82549_(offset);
      if (nextLocation.m_7098_() + (double)nextDims.f_20378_ > this.pillar.m_20186_() + (double)EntitySculptor.TEST_HEIGHT) {
         nextLocation = new Vec3(
            nextLocation.m_7096_(), this.pillar.m_20186_() + (double)EntitySculptor.TEST_HEIGHT - (double)nextDims.f_20378_, nextLocation.m_7094_()
         );
      }

      return nextLocation;
   }

   protected Vec3 chooseTowardsSculptorLocation(EntityBoulderPlatform nextBoulder) {
      EntityDimensions thisDims = SIZE_MAP.get(this.getTier());
      EntityDimensions nextDims = SIZE_MAP.get(nextBoulder.getTier());
      Vec3 startLocation = this.m_20182_();
      Vec2 fromPillarPos = new Vec2((float)(this.caster.m_20185_() - startLocation.f_82479_), (float)(this.caster.m_20189_() - startLocation.f_82481_));
      float horizontalOffset = Mth.m_216267_(this.f_19796_, 1.0F, 4.0F) + thisDims.f_20377_ / 2.0F + nextDims.f_20377_ / 2.0F;
      float baseAngle = (float)(-Math.toDegrees(Math.atan2((double)fromPillarPos.f_82471_, (double)fromPillarPos.f_82470_)));
      Vec3 offset = new Vec3((double)horizontalOffset, 0.0, 0.0);
      float finalAngle = (float)Math.toRadians((double)MathHelper.wrapDegrees(baseAngle));
      offset = offset.m_82524_(finalAngle);
      return startLocation.m_82549_(offset);
   }

   public EntityBoulderPlatform getNextBoulder() {
      return this.nextBoulder;
   }

   public boolean checkJumpPath(EntityBoulderPlatform platform) {
      EntityBoulderPlatform next = platform.getNextBoulder();
      if (next == null) {
         return true;
      } else {
         EntityDimensions platDims = SIZE_MAP.get(platform.getTier());
         EntityDimensions nextDims = SIZE_MAP.get(next.getTier());
         Vec3 toNext = next.m_20182_().m_82546_(platform.m_20182_());
         Vec3 startPos = platform.m_20182_()
            .m_82520_(0.0, (double)platDims.f_20378_, 0.0)
            .m_82549_(toNext.m_82542_(1.0, 0.0, 1.0).m_82541_().m_82490_((double)(platDims.f_20377_ / 2.0F)));
         Vec3 endPos = next.m_20182_()
            .m_82520_(0.0, (double)nextDims.f_20378_, 0.0)
            .m_82549_(toNext.m_82542_(1.0, 0.0, 1.0).m_82541_().m_82490_((double)(-nextDims.f_20377_ / 2.0F)));
         double gravity = -((Attribute)ForgeMod.ENTITY_GRAVITY.get()).m_22082_();
         double jumpVelY = 1.0;
         double heightDiff = endPos.m_7098_() - startPos.m_7098_();
         double totalTime = (-jumpVelY - Math.sqrt(jumpVelY * jumpVelY - 4.0 * gravity * -heightDiff)) / (2.0 * gravity);
         double jumpVelX = (endPos.m_7096_() - startPos.m_7096_()) / totalTime;
         double jumpVelZ = (endPos.m_7094_() - startPos.m_7094_()) / totalTime;
         Vec3 jumpVel = new Vec3(jumpVelX, jumpVelY, jumpVelZ);
         AABB thisBounds = SIZE_MAP.get(this.getTier()).m_20393_(this.m_20182_());
         int substeps = 5;

         for (int i = 0; i < substeps; i++) {
            double time = totalTime / (double)substeps * (double)i;
            Vec3 jumpPosition = new Vec3(0.0, gravity * time * time, 0.0).m_82549_(jumpVel.m_82490_(time)).m_82549_(startPos);
            AABB playerBounds = EntityType.f_20532_.m_20680_().m_20393_(jumpPosition);
            if (thisBounds.m_82381_(playerBounds)) {
               return false;
            }
         }

         return true;
      }
   }

   public void setMainPath() {
      this.isMainPath = true;
   }

   public float getHeightFrac() {
      if (this.caster instanceof EntitySculptor sculptor) {
         EntityPillar pillar = sculptor.getPillar();
         if (pillar != null) {
            return (float)(this.m_20182_().m_7098_() + (double)this.m_20206_() - pillar.m_20186_()) / (float)EntitySculptor.TEST_HEIGHT;
         }
      }

      return -1.0F;
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("SpawnedNext", this.spawnedNextBoulders);
      compound.m_128379_("Descending", this.descending);
      compound.m_128379_("MainPath", this.isMainPath);
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.spawnedNextBoulders = compound.m_128471_("SpawnedNext");
      this.descending = compound.m_128471_("Descending");
      this.isMainPath = compound.m_128471_("MainPath");
   }
}
