package com.aqutheseal.celestisynth.common.entity.projectile;

import com.aqutheseal.celestisynth.api.mixin.LivingMixinSupport;
import com.aqutheseal.celestisynth.common.entity.skill.SkillCastRainfallRain;
import com.aqutheseal.celestisynth.common.item.weapons.RainfallSerenityItem;
import com.aqutheseal.celestisynth.common.registry.CSEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSItems;
import com.aqutheseal.celestisynth.common.registry.CSParticleTypes;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class RainfallArrow extends AbstractArrow implements IAnimatable {
   private static final EntityDataAccessor<Boolean> IS_STRONG = SynchedEntityData.m_135353_(RainfallArrow.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> IS_FLAMING = SynchedEntityData.m_135353_(RainfallArrow.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<BlockPos> ORIGIN = SynchedEntityData.m_135353_(RainfallArrow.class, EntityDataSerializers.f_135038_);
   private static final EntityDataAccessor<Boolean> SHOULD_IMBUE_QUASAR = SynchedEntityData.m_135353_(RainfallArrow.class, EntityDataSerializers.f_135035_);
   private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.rainfall_arrow.idle", EDefaultLoopTypes.LOOP);
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private final AnimationController<RainfallArrow> mainController = new AnimationController(this, "mainrainfallarrowcontroller", 0.0F, this::predicate);
   private final RainfallSerenityItem rawRainfallItem = (RainfallSerenityItem)CSItems.RAINFALL_SERENITY.get();

   public RainfallArrow(EntityType<? extends RainfallArrow> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public RainfallArrow(Level pLevel, double pX, double pY, double pZ) {
      super((EntityType)CSEntityTypes.RAINFALL_ARROW.get(), pX, pY, pZ, pLevel);
   }

   public RainfallArrow(Level pLevel, LivingEntity pShooter) {
      super((EntityType)CSEntityTypes.RAINFALL_ARROW.get(), pShooter, pLevel);
   }

   public void createLaser(Vec3 from, Vec3 to, boolean isMainArrow, boolean strictLoading) {
      double distance = from.m_82554_(to);
      Vec3 direction = to.m_82546_(from).m_82541_();

      for (double i = 0.0; i <= distance; i += 0.1) {
         Vec3 particlePos = from.m_82549_(direction.m_82490_(i));
         BlockPos particleBlockPos = new BlockPos(particlePos);
         if (strictLoading && !this.f_19853_.m_46749_(particleBlockPos)) {
            return;
         }

         SimpleParticleType curParticle = isMainArrow
            ? (SimpleParticleType)CSParticleTypes.RAINFALL_BEAM.get()
            : (SimpleParticleType)CSParticleTypes.RAINFALL_BEAM_QUASAR.get();
         ParticleUtil.sendParticles(this.f_19853_, curParticle, particlePos.f_82479_, particlePos.f_82480_, particlePos.f_82481_, 1, 0.0, 0.0, 0.0);
      }
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.isStrong() && this.f_19797_ == 3) {
         Vec3 from = new Vec3((double)this.getOrigin().m_123341_(), (double)this.getOrigin().m_123342_(), (double)this.getOrigin().m_123343_());
         Vec3 to = new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_());
         this.createLaser(from, to, true, true);
      }

      if (this.f_19797_ > 2) {
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   public void m_6686_(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
      super.m_6686_(pX, pY, pZ, pVelocity, pInaccuracy);
      this.m_20256_(this.m_20184_().m_82490_(20.0));
   }

   public void hitEffect(HitResult pResult, BlockPos hitPos) {
      if (this.m_37282_() instanceof Player && this.isStrong()) {
         if (pResult instanceof BlockHitResult || pResult instanceof EntityHitResult ehr && ehr.m_82443_() instanceof LivingEntity) {
            for (Entity potentialTarget : this.rawRainfallItem.iterateEntities(this.f_19853_, this.rawRainfallItem.createAABB(hitPos, 4.0))) {
               if (potentialTarget instanceof LivingEntity) {
                  LivingEntity target = (LivingEntity)potentialTarget;
                  if (potentialTarget != this.m_37282_()) {
                     if (this.isFlaming()) {
                        target.m_20254_(2);
                     }

                     target.m_6469_(DamageSource.m_19367_(this, this.m_37282_() != null ? this.m_37282_() : null), 2.0F);
                     target.f_19802_ = 0;
                  }
               }
            }
         }

         if (pResult instanceof EntityHitResult ehr) {
            this.m_36767_((byte)(this.m_36796_() + 1));
            if (ehr.m_82443_() instanceof LivingMixinSupport lms && this.m_37282_() instanceof Player player && lms.getQuasarImbued() == player) {
               SkillCastRainfallRain projectile = (SkillCastRainfallRain)((EntityType)CSEntityTypes.RAINFALL_RAIN.get()).m_20615_(player.f_19853_);
               projectile.targetPos = new BlockPos(ehr.m_82443_().m_20183_());
               projectile.setOwnerUuid(player.m_20148_());
               projectile.m_6027_(ehr.m_82443_().m_20185_(), ehr.m_82443_().m_20186_() + 15.0, ehr.m_82443_().m_20189_());
               player.f_19853_.m_7967_(projectile);
            }

            if (this.f_19796_.m_188503_(3) == 1) {
               for (Entity imbueSource : this.rawRainfallItem.iterateEntities(this.f_19853_, this.rawRainfallItem.createAABB(hitPos, 24.0))) {
                  if (imbueSource instanceof LivingMixinSupport) {
                     LivingMixinSupport lms = (LivingMixinSupport)imbueSource;
                     Entity offsetPi = this.m_37282_();
                     if (offsetPi instanceof Player) {
                        Player player = (Player)offsetPi;
                        if (lms.getQuasarImbued() != null && imbueSource != ehr.m_82443_()) {
                           ehr.m_82443_().f_19802_ = 0;
                           Vec3 from = new Vec3(imbueSource.m_20185_(), imbueSource.m_20186_() + 1.5, imbueSource.m_20189_());
                           Vec3 to = new Vec3(ehr.m_82443_().m_20185_(), ehr.m_82443_().m_20186_() + 1.5, ehr.m_82443_().m_20189_());
                           this.createLaser(from, to, false, false);
                           if (!this.f_19853_.m_5776_()) {
                              BlockPos imbuePos = new BlockPos(imbueSource.m_20185_(), imbueSource.m_20186_() + 1.5, imbueSource.m_20189_());
                              RainfallArrow rainfallArrow = new RainfallArrow(this.f_19853_, player);
                              rainfallArrow.m_5602_(player);
                              rainfallArrow.m_20219_(imbueSource.m_20182_());
                              rainfallArrow.f_36705_ = Pickup.CREATIVE_ONLY;
                              rainfallArrow.setOrigin(imbuePos);
                              rainfallArrow.m_36767_((byte)3);
                              rainfallArrow.m_36781_((Double)CSConfigManager.COMMON.rainfallSerenityQuasarArrowDmg.get());
                              rainfallArrow.setImbueQuasar(false);
                              double offsetHitResultY = ehr.m_82443_().m_20186_() + 1.5;
                              double finalDistX = ehr.m_82443_().m_20185_() - imbueSource.m_20185_();
                              double offsetDistY = offsetHitResultY - imbueSource.m_20186_() + 1.5;
                              double finalDistZ = ehr.m_82443_().m_20189_() - imbueSource.m_20189_();
                              rainfallArrow.m_6686_(finalDistX, offsetDistY, finalDistZ, 3.0F, 0.0F);
                              this.f_19853_.m_7967_(rainfallArrow);
                           }

                           this.f_19853_
                              .m_6263_(
                                 null,
                                 player.m_20185_(),
                                 player.m_20186_(),
                                 player.m_20189_(),
                                 (SoundEvent)CSSoundEvents.CS_LASER_SHOOT.get(),
                                 SoundSource.PLAYERS,
                                 0.7F,
                                 2.0F
                              );
                           lms.setQuasarImbued(null);
                        }
                     }
                  }
               }
            }

            if (this.isImbueQuasar() && this.m_37282_() instanceof Player player && ehr.m_82443_() instanceof LivingMixinSupport lms) {
               lms.setQuasarImbued(player);
            }
         }

         this.m_5496_(SoundEvents.f_11897_, 1.0F, 1.0F + this.f_19796_.m_188501_());
         int amount = 60;
         float expansionMultiplier = 0.65F;

         for (int e = 0; e < amount; e++) {
            double targetAngle = this.f_19796_.m_188500_() * 2.0 * Math.PI;
            double offsetPi = this.f_19796_.m_188500_() * Math.PI;
            double offX = Math.sin(offsetPi) * Math.cos(targetAngle) * (double)expansionMultiplier;
            double offY = Math.sin(offsetPi) * Math.sin(targetAngle) * (double)expansionMultiplier;
            double offZ = Math.cos(offsetPi) * (double)expansionMultiplier;
            ParticleUtil.sendParticles(
               this.f_19853_,
               (SimpleParticleType)CSParticleTypes.RAINFALL_ENERGY.get(),
               (double)hitPos.m_123341_(),
               (double)hitPos.m_123342_(),
               (double)hitPos.m_123343_(),
               0,
               offX,
               offY,
               offZ
            );
         }
      }
   }

   protected void m_8060_(BlockHitResult pResult) {
      super.m_8060_(pResult);
      this.hitEffect(pResult, pResult.m_82425_());
   }

   protected void m_5790_(EntityHitResult pResult) {
      super.m_5790_(pResult);
      this.hitEffect(pResult, pResult.m_82443_().m_20183_());
   }

   protected ItemStack m_7941_() {
      return ItemStack.f_41583_;
   }

   protected float m_6882_() {
      return 1.0F;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(IS_STRONG, false);
      this.f_19804_.m_135372_(IS_FLAMING, false);
      this.f_19804_.m_135372_(ORIGIN, BlockPos.f_121853_);
      this.f_19804_.m_135372_(SHOULD_IMBUE_QUASAR, false);
   }

   public boolean isStrong() {
      return (Boolean)this.f_19804_.m_135370_(IS_STRONG);
   }

   public void setStrong(boolean isStrong) {
      this.f_19804_.m_135381_(IS_STRONG, isStrong);
   }

   public boolean isFlaming() {
      return (Boolean)this.f_19804_.m_135370_(IS_FLAMING);
   }

   public void setFlaming(boolean isFlaming) {
      this.f_19804_.m_135381_(IS_FLAMING, isFlaming);
   }

   public BlockPos getOrigin() {
      return (BlockPos)this.f_19804_.m_135370_(ORIGIN);
   }

   public void setOrigin(BlockPos origin) {
      this.f_19804_.m_135381_(ORIGIN, origin);
   }

   public boolean isImbueQuasar() {
      return (Boolean)this.f_19804_.m_135370_(SHOULD_IMBUE_QUASAR);
   }

   public void setImbueQuasar(boolean imbueAllow) {
      this.f_19804_.m_135381_(SHOULD_IMBUE_QUASAR, imbueAllow);
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(this.mainController);
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      event.getController().setAnimation(IDLE_ANIMATION);
      return PlayState.CONTINUE;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
