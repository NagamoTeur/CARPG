package com.aqutheseal.celestisynth.common.entity.tempestboss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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

public class TempestBoss extends Monster implements IAnimatable {
   private static final EntityDataAccessor<Integer> BATTLE_PHASE = SynchedEntityData.m_135353_(TempestBoss.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.m_135353_(TempestBoss.class, EntityDataSerializers.f_135028_);
   public static int NONE = 0;
   public static int PHASE_TRANSITION_DASH_1 = 1;
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public TempestBoss(EntityType<? extends Monster> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public static Builder createAttributes() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22276_, 500.0)
         .m_22268_(Attributes.f_22277_, 64.0)
         .m_22268_(Attributes.f_22279_, 0.2F)
         .m_22268_(Attributes.f_22281_, 12.0)
         .m_22268_(Attributes.f_22284_, 15.0)
         .m_22268_(Attributes.f_22280_, 1.0);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new AITempestPhaseChangeGoal(this));
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.0, false));
      this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]).m_26044_(new Class[]{TempestBoss.class}));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.f_19797_ == 1) {
         this.cyclePhase();
      }
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      this.cyclePhase();
      if (pSource.m_7640_() instanceof Player player && this.f_19853_.m_5776_()) {
         player.m_5661_(Component.m_237113_("New Phase: " + this.getPhase().ordinal() + " - " + this.getPhase().name()), false);
      }

      return super.m_6469_(pSource, pAmount);
   }

   public void cyclePhase() {
      int newPhase = this.getPhase().ordinal() + 1;
      if (newPhase < TempestPhases.values().length) {
         this.modifyPhaseOnCycle(newPhase);
         this.setPhase(TempestPhases.values()[newPhase]);
      } else {
         this.setPhase(TempestPhases.values()[TempestPhases.values().length - 1]);
      }
   }

   public void modifyPhaseOnCycle(int newPhase) {
      switch (TempestPhases.values()[newPhase]) {
         case PHASE_1:
            this.modifyAttribute(Attributes.f_22279_, 0.45);
            break;
         case PHASE_2:
            this.modifyAttribute(Attributes.f_22279_, 0.65);
      }
   }

   public void modifyAttribute(Attribute attribute, double value) {
      AttributeInstance mod = this.m_21051_(attribute);

      assert mod != null;

      mod.m_22100_(value);
   }

   public void setPhase(TempestPhases phase) {
      this.f_19804_.m_135381_(BATTLE_PHASE, phase.ordinal());
   }

   public TempestPhases getPhase() {
      return TempestPhases.values()[this.f_19804_.m_135370_(BATTLE_PHASE)];
   }

   public void setAttackState(int state) {
      this.f_19804_.m_135381_(ATTACK_STATE, state);
   }

   public int getAttackState() {
      return (Integer)this.f_19804_.m_135370_(ATTACK_STATE);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(BATTLE_PHASE, 1);
      this.f_19804_.m_135372_(ATTACK_STATE, 0);
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      if (event.isMoving()) {
         switch (this.getPhase()) {
            case PHASE_1:
               this.loopAnim(event.getController(), "run");
               break;
            case PHASE_2:
            default:
               this.loopAnim(event.getController(), "walk_slow");
               break;
            case IDLE:
               this.loopAnim(event.getController(), "walk_slow");
               break;
            case APPROACH_SLOW:
               this.loopAnim(event.getController(), "walk_slow");
         }
      } else {
         this.loopAnim(event.getController(), "idle");
      }

      return PlayState.CONTINUE;
   }

   public <E extends IAnimatable> void loopAnim(AnimationController<E> controller, String name) {
      controller.setAnimation(new AnimationBuilder().addAnimation("animation.tempest." + name, EDefaultLoopTypes.LOOP));
   }

   public void m_7378_(CompoundTag compoundNBT) {
      this.setPhase(TempestPhases.values()[compoundNBT.m_128451_("battlePhase_tempest")]);
   }

   public void m_7380_(CompoundTag compoundNBT) {
      compoundNBT.m_128405_("battlePhase_tempest", this.getPhase().ordinal());
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
