package com.hollingsworth.arsnouveau.common.entity;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.util.SummonUtil;
import com.hollingsworth.arsnouveau.common.entity.goal.lily.WagGoal;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class Lily extends TamableAnimal implements IAnimatable, IDispellable {
   public static BiMap<UUID, UUID> ownerLilyMap = HashBiMap.create();
   private static final EntityDataAccessor<Boolean> SIT = SynchedEntityData.m_135353_(Lily.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> WAG = SynchedEntityData.m_135353_(Lily.class, EntityDataSerializers.f_135035_);
   public int wagTicks;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public Lily(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public Lily(Level level) {
      this((EntityType<? extends TamableAnimal>)ModEntities.LILY.get(), level);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
      this.f_21345_.m_25352_(7, new WagGoal(this));
      this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(10, new RandomLookAroundGoal(this));
   }

   public InteractionResult m_6071_(Player pPlayer, InteractionHand pHand) {
      if (this.f_19853_.f_46443_) {
         return InteractionResult.CONSUME;
      } else if (this.m_21830_(pPlayer)) {
         this.m_21839_(!this.m_21827_());
         this.f_20899_ = false;
         this.f_21344_.m_26573_();
         this.m_6710_(null);
         return InteractionResult.SUCCESS;
      } else {
         return super.m_6071_(pPlayer, pHand);
      }
   }

   public void m_8119_() {
      super.m_8119_();
      SummonUtil.healOverTime(this);
      if (!this.f_19853_.f_46443_) {
         if (this.f_19853_.m_46467_() % 20L == 0L && !ownerLilyMap.containsValue(this.m_20148_())) {
            this.m_142687_(RemovalReason.DISCARDED);
         }

         if (this.wagTicks > 0 && this.isWagging()) {
            this.wagTicks--;
            if (this.wagTicks <= 0) {
               this.setWagging(false);
            }
         }
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SIT, false);
      this.f_19804_.m_135372_(WAG, false);
   }

   public boolean m_21827_() {
      return (Boolean)this.f_19804_.m_135370_(SIT);
   }

   public boolean m_21824_() {
      return true;
   }

   public void m_21839_(boolean pOrderedToSit) {
      this.f_19804_.m_135381_(SIT, pOrderedToSit);
   }

   public boolean isWagging() {
      return (Boolean)this.f_19804_.m_135370_(WAG);
   }

   public void setWagging(boolean pWagging) {
      this.f_19804_.m_135381_(WAG, pWagging);
   }

   protected void m_7355_(BlockPos pPos, BlockState pBlock) {
      this.m_5496_(SoundEvents.f_12624_, 0.15F, 1.0F);
   }

   public static Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22279_, 0.3F).m_22268_(Attributes.f_22276_, 40.0).m_22268_(Attributes.f_22281_, 2.0);
   }

   protected SoundEvent m_7515_() {
      if (this.f_19796_.m_188503_(3) != 0) {
         return SoundEvents.f_12617_;
      } else {
         return this.m_21824_() && this.m_21223_() < 10.0F ? SoundEvents.f_12625_ : SoundEvents.f_12622_;
      }
   }

   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return SoundEvents.f_12621_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12618_;
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return !(pSource.m_7639_() instanceof Player) ? false : super.m_6469_(pSource, pAmount);
   }

   protected float m_6121_() {
      return 0.4F;
   }

   public boolean m_6898_(ItemStack pStack) {
      Item item = pStack.m_41720_();
      return item.m_41472_() && pStack.getFoodProperties(this).m_38746_();
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel pLevel, AgeableMob pOtherParent) {
      return null;
   }

   public boolean isLookingAtMe(Player pPlayer) {
      Vec3 vec3 = pPlayer.m_20252_(1.0F).m_82541_();
      Vec3 vec31 = new Vec3(this.m_20185_() - pPlayer.m_20185_(), this.m_20188_() - pPlayer.m_20188_(), this.m_20189_() - pPlayer.m_20189_());
      double d0 = vec31.m_82553_();
      vec31 = vec31.m_82541_();
      double d1 = vec3.m_82526_(vec31);
      return d1 > 1.0 - 0.025 / d0 && pPlayer.m_142582_(this);
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController<>(this, "walk", 1.0F, event -> {
         if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      data.addAnimationController(new AnimationController<>(this, "idle", 1.0F, event -> {
         if (!event.isMoving() && !this.isWagging()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      data.addAnimationController(new AnimationController<>(this, "idle_wag", 1.0F, event -> {
         if (!event.isMoving() && this.isWagging()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle_wagging"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      data.addAnimationController(new AnimationController<>(this, "rest", 1.0F, event -> {
         if (!event.isMoving() && this.m_21827_() && !this.isWagging()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("resting"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      data.addAnimationController(new AnimationController<>(this, "rest_wag", 1.0F, event -> {
         if (!event.isMoving() && this.m_21827_() && this.isWagging()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("resting_wagging"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public boolean onDispel(@NotNull LivingEntity caster) {
      if (caster.m_20148_().equals(this.m_21805_())) {
         this.m_142687_(RemovalReason.DISCARDED);
         return true;
      } else {
         return false;
      }
   }

   public void m_20258_(CompoundTag pCompound) {
      super.m_20258_(pCompound);
      if (!ownerLilyMap.containsKey(this.m_21805_())) {
         ownerLilyMap.put(this.m_21805_(), this.m_20148_());
      }
   }
}
