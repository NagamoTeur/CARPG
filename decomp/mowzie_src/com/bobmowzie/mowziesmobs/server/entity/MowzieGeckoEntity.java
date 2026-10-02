package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.SimpleAnimationAbility;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class MowzieGeckoEntity extends MowzieEntity implements IAnimatable, IAnimationTickable {
   protected AnimationFactory factory = new AnimationFactory(this);
   protected MowzieAnimationController<MowzieGeckoEntity> controller = new MowzieAnimationController<>(this, "controller", 5.0F, this::predicate, 0.0);

   public MowzieGeckoEntity(EntityType<? extends MowzieEntity> type, Level world) {
      super(type, world);
   }

   @Override
   public int tickTimer() {
      return this.f_19797_;
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   protected int getDeathDuration() {
      Ability deathAbility = this.getActiveAbility();
      return deathAbility instanceof SimpleAnimationAbility ? ((SimpleAnimationAbility)deathAbility).getDuration() : 20;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
   }

   public abstract AbilityType getHurtAbility();

   public abstract AbilityType getDeathAbility();

   public boolean m_6469_(DamageSource source, float damage) {
      boolean attack = super.m_6469_(source, damage);
      if (attack) {
         if (this.m_21223_() > 0.0F && (this.getActiveAbility() == null || this.getActiveAbility().damageInterrupts()) && this.playsHurtAnimation) {
            this.sendAbilityMessage(this.getHurtAbility());
         } else if (this.m_21223_() <= 0.0F) {
            this.sendAbilityMessage(this.getDeathAbility());
         }
      }

      return attack;
   }

   protected <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      AbilityCapability.IAbilityCapability abilityCapability = this.getAbilityCapability();
      FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(this, CapabilityHandler.FROZEN_CAPABILITY);
      if (abilityCapability == null) {
         return PlayState.STOP;
      } else if (frozenCapability != null && frozenCapability.getFrozen()) {
         return PlayState.STOP;
      } else if (abilityCapability.getActiveAbility() != null) {
         this.getController().transitionLengthTicks = 0.0;
         return abilityCapability.animationPredicate(event, null);
      } else {
         this.loopingAnimations(event);
         return PlayState.CONTINUE;
      }
   }

   protected <E extends IAnimatable> void loopingAnimations(AnimationEvent<E> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(this.controller);
   }

   public MowzieAnimationController<MowzieGeckoEntity> getController() {
      return this.controller;
   }

   public AbilityType<?, ?>[] getAbilities() {
      return new AbilityType[0];
   }

   public AbilityCapability.IAbilityCapability getAbilityCapability() {
      return AbilityHandler.INSTANCE.getAbilityCapability(this);
   }

   public Ability getActiveAbility() {
      AbilityCapability.IAbilityCapability capability = this.getAbilityCapability();
      return capability == null ? null : this.getAbilityCapability().getActiveAbility();
   }

   public AbilityType getActiveAbilityType() {
      Ability ability = this.getActiveAbility();
      return ability == null ? null : ability.getAbilityType();
   }

   public Ability getAbility(AbilityType abilityType) {
      AbilityCapability.IAbilityCapability capability = this.getAbilityCapability();
      return capability == null ? null : this.getAbilityCapability().getAbilityMap().get(abilityType);
   }

   public void sendAbilityMessage(AbilityType abilityType) {
      AbilityHandler.INSTANCE.sendAbilityMessage(this, abilityType);
   }
}
