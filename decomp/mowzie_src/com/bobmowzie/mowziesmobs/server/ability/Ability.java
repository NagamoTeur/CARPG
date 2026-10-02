package com.bobmowzie.mowziesmobs.server.ability;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class Ability<T extends LivingEntity> {
   private final AbilitySection[] sectionTrack;
   private final int cooldownMax;
   private final AbilityType<T, ? extends Ability> abilityType;
   private final T user;
   private final AbilityCapability.IAbilityCapability abilityCapability;
   private int ticksInUse;
   private int ticksInSection;
   private int currentSectionIndex;
   private boolean isUsing;
   private int cooldownTimer;
   protected Random rand;
   protected AnimationBuilder activeAnimation;

   public Ability(AbilityType<T, ? extends Ability> abilityType, T user, AbilitySection[] sectionTrack, int cooldownMax) {
      this.abilityType = abilityType;
      this.user = user;
      this.abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(user);
      this.sectionTrack = sectionTrack;
      this.cooldownMax = cooldownMax;
      this.rand = new Random();
   }

   public Ability(AbilityType<T, ? extends Ability> abilityType, T user, AbilitySection[] sectionTrack) {
      this(abilityType, user, sectionTrack, 0);
   }

   public void start() {
      if (!this.runsInBackground()) {
         this.abilityCapability.setActiveAbility(this);
      }

      this.ticksInUse = 0;
      this.ticksInSection = 0;
      this.currentSectionIndex = 0;
      this.isUsing = true;
      this.beginSection(this.getSectionTrack()[0]);
   }

   public void playAnimation(String animationName, boolean shouldLoop) {
      if (this.getUser() instanceof MowzieGeckoEntity && this.getUser().f_19853_.m_5776_()) {
         MowzieGeckoEntity entity = (MowzieGeckoEntity)this.getUser();
         AnimationBuilder newActiveAnimation = new AnimationBuilder().addAnimation(animationName, shouldLoop);
         this.activeAnimation = newActiveAnimation;
         MowzieAnimationController<MowzieGeckoEntity> controller = entity.getController();
         if (controller != null) {
            controller.playAnimation(entity, newActiveAnimation);
         }
      }
   }

   public void tick() {
      if (this.isUsing()) {
         if (this.getUser().m_6142_() && !this.canContinueUsing()) {
            AbilityHandler.INSTANCE.sendInterruptAbilityMessage(this.getUser(), this.abilityType);
         }

         this.tickUsing();
         this.ticksInUse++;
         this.ticksInSection++;
         AbilitySection section = this.getCurrentSection();
         if (section instanceof AbilitySection.AbilitySectionInstant) {
            this.nextSection();
         } else if (section instanceof AbilitySection.AbilitySectionDuration sectionDuration && this.ticksInSection > sectionDuration.duration) {
            this.nextSection();
         }
      } else {
         this.tickNotUsing();
         if (this.getCooldownTimer() > 0) {
            this.cooldownTimer--;
         }
      }
   }

   public void tickUsing() {
   }

   public void tickNotUsing() {
   }

   public void end() {
      this.ticksInUse = 0;
      this.ticksInSection = 0;
      this.isUsing = false;
      this.cooldownTimer = this.getMaxCooldown();
      this.currentSectionIndex = 0;
      if (!this.runsInBackground()) {
         this.abilityCapability.setActiveAbility(null);
      }
   }

   public void interrupt() {
      this.end();
   }

   public void complete() {
      this.end();
   }

   public boolean canUse() {
      if (this.getUser().m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         return false;
      } else {
         boolean toReturn = (!this.isUsing() || this.canCancelSelf()) && this.cooldownTimer == 0;
         if (!this.runsInBackground()) {
            toReturn = toReturn
               && (
                  this.abilityCapability.getActiveAbility() == null
                     || this.canCancelActiveAbility()
                     || this.abilityCapability.getActiveAbility().canBeCanceledByAbility(this)
               );
         }

         return toReturn;
      }
   }

   public boolean tryAbility() {
      return true;
   }

   public boolean canCancelActiveAbility() {
      return false;
   }

   public Ability getActiveAbility() {
      AbilityCapability.IAbilityCapability capability = this.getAbilityCapability();
      return capability == null ? null : this.getAbilityCapability().getActiveAbility();
   }

   public boolean canCancelSelf() {
      return false;
   }

   public boolean canBeCanceledByAbility(Ability ability) {
      return false;
   }

   protected boolean canContinueUsing() {
      return !this.getUser().m_21023_((MobEffect)EffectHandler.FROZEN.get());
   }

   public boolean isUsing() {
      return this.isUsing;
   }

   public T getUser() {
      return this.user;
   }

   public Level getLevel() {
      return this.user.m_9236_();
   }

   public int getTicksInUse() {
      return this.ticksInUse;
   }

   public int getTicksInSection() {
      return this.ticksInSection;
   }

   public int getCooldownTimer() {
      return this.cooldownTimer;
   }

   public void nextSection() {
      this.jumpToSection(this.currentSectionIndex + 1);
   }

   public void jumpToSection(int sectionIndex) {
      this.endSection(this.getCurrentSection());
      this.currentSectionIndex = sectionIndex;
      this.ticksInSection = 0;
      if (this.currentSectionIndex >= this.getSectionTrack().length) {
         this.complete();
      } else {
         this.beginSection(this.getCurrentSection());
      }
   }

   protected void endSection(AbilitySection section) {
   }

   protected void beginSection(AbilitySection section) {
   }

   public AbilitySection getCurrentSection() {
      return this.currentSectionIndex >= this.getSectionTrack().length ? null : this.getSectionTrack()[this.currentSectionIndex];
   }

   public boolean damageInterrupts() {
      return false;
   }

   public void onTakeDamage(LivingHurtEvent event) {
      if (this.isUsing() && event.getResult() != Result.DENY && (double)event.getAmount() > 0.0 && this.damageInterrupts()) {
         AbilityHandler.INSTANCE.sendInterruptAbilityMessage(this.getUser(), this.getAbilityType());
      }
   }

   public boolean runsInBackground() {
      return false;
   }

   public boolean preventsAttacking() {
      return true;
   }

   public boolean preventsBlockBreakingBuilding() {
      return true;
   }

   public boolean preventsInteracting() {
      return true;
   }

   public boolean preventsItemUse(ItemStack stack) {
      return true;
   }

   public AbilitySection[] getSectionTrack() {
      return this.sectionTrack;
   }

   public int getMaxCooldown() {
      return this.cooldownMax;
   }

   public AbilityCapability.IAbilityCapability getAbilityCapability() {
      return this.abilityCapability;
   }

   public <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> e, GeckoPlayer.Perspective perspective) {
      if (this.activeAnimation != null && !this.activeAnimation.getRawAnimationList().isEmpty()) {
         e.getController().setAnimation(this.activeAnimation);
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   public void codeAnimations(MowzieAnimatedGeoModel<? extends IAnimatable> model, float partialTick) {
   }

   public boolean isAnimating() {
      return this.isUsing();
   }

   public AbilityType<T, ? extends Ability> getAbilityType() {
      return this.abilityType;
   }

   public List<LivingEntity> getEntityLivingBaseNearby(LivingEntity player, double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(player, (Class<T>)LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(LivingEntity player, Class<T> entityClass, double r) {
      return player.f_19853_.m_6443_(entityClass, player.m_20191_().m_82377_(r, r, r), e -> e != player && (double)player.m_20270_(e) <= r);
   }

   public <T extends Entity> List<T> getEntitiesNearby(LivingEntity player, Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return player.f_19853_.m_6443_(entityClass, player.m_20191_().m_82377_(dX, dY, dZ), e -> e != player && (double)player.m_20270_(e) <= r);
   }

   public CompoundTag writeNBT() {
      CompoundTag compound = new CompoundTag();
      if (this.isUsing()) {
         compound.m_128405_("ticks_in_use", this.ticksInUse);
         compound.m_128405_("ticks_in_section", this.ticksInSection);
         compound.m_128405_("current_section", this.currentSectionIndex);
      } else if (this.cooldownTimer > 0) {
         compound.m_128405_("cooldown_timer", this.cooldownTimer);
      }

      return compound;
   }

   public void readNBT(Tag nbt) {
      CompoundTag compound = (CompoundTag)nbt;
      this.isUsing = compound.m_128441_("ticks_in_use");
      if (this.isUsing) {
         this.ticksInUse = compound.m_128451_("ticks_in_use");
         this.ticksInSection = compound.m_128451_("ticks_in_section");
         this.currentSectionIndex = compound.m_128451_("current_section");
      } else {
         this.cooldownTimer = compound.m_128451_("cooldown_timer");
      }
   }

   public void onRenderTick(RenderTickEvent event) {
   }
}
