package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class EntityRockSling extends EntityBoulderProjectile implements IAnimatable, IAnimationTickable {
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private Vec3 launchVec;

   public EntityRockSling(EntityType<? extends EntityRockSling> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityRockSling(
      EntityType<? extends EntityBoulderProjectile> type,
      Level world,
      LivingEntity caster,
      BlockState blockState,
      BlockPos pos,
      EntityGeomancyBase.GeomancyTier tier
   ) {
      super(type, world, caster, blockState, pos, tier);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.f_19797_ > 30 + this.f_19796_.m_188503_(35) && this.launchVec != null) {
         this.m_20256_(
            this.launchVec.m_82541_().m_82542_((double)(2.0F + this.f_19796_.m_188501_() / 5.0F), 2.0, (double)(2.0F + this.f_19796_.m_188501_() / 5.0F))
         );
      }
   }

   public void setLaunchVec(Vec3 vec) {
      this.launchVec = vec;
   }

   @Override
   public void registerControllers(AnimationData data) {
      AnimationController<EntityRockSling> controller = new AnimationController(this, "controller", 0.0F, event -> {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("roll", EDefaultLoopTypes.LOOP));
         return PlayState.CONTINUE;
      });
      data.addAnimationController(controller);
   }

   @Override
   public int tickTimer() {
      return this.f_19797_;
   }
}
