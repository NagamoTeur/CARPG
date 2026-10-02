package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.util.SummonUtil;
import com.hollingsworth.arsnouveau.common.advancement.ANCriteriaTriggers;
import com.hollingsworth.arsnouveau.common.compat.PatchouliHandler;
import com.hollingsworth.arsnouveau.common.entity.goal.UntamedFindItemGoal;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags.Items;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class GiftStarbuncle extends PathfinderMob implements IAnimatable {
   int tamingTime;
   public static final EntityDataAccessor<Boolean> BEING_TAMED = SynchedEntityData.m_135353_(GiftStarbuncle.class, EntityDataSerializers.f_135035_);
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public GiftStarbuncle(EntityType<GiftStarbuncle> type, Level level) {
      super(type, level);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_21205_().m_41619_() && !this.f_19853_.f_46443_) {
         for (ItemEntity itementity : this.f_19853_.m_45976_(ItemEntity.class, this.m_20191_().m_82400_(1.0))) {
            if (itementity.m_6084_() && !itementity.m_32055_().m_41619_() && !itementity.m_32063_()) {
               this.m_7581_(itementity);
               if (!this.m_21205_().m_41619_()) {
                  break;
               }
            }
         }
      }

      if (this.isTaming()) {
         this.tamingTime++;
         if (this.tamingTime > 60 && !this.f_19853_.f_46443_) {
            ItemStack stack = new ItemStack((ItemLike)ItemsRegistry.STARBUNCLE_SHARD.get(), 1 + this.f_19853_.f_46441_.m_188503_(2));
            ItemStack gift = new ItemStack((ItemLike)ItemsRegistry.STARBY_GIFY.get(), 1);
            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), gift));
            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), stack));
            this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12052_, SoundSource.NEUTRAL, 1.0F, 1.0F);
            ANCriteriaTriggers.rewardNearbyPlayers(ANCriteriaTriggers.POOF_MOB, (ServerLevel)this.f_19853_, this.m_20097_(), 10);
            this.m_142687_(RemovalReason.DISCARDED);
         } else if (this.tamingTime == 60 && this.f_19853_.f_46443_) {
            for (int i = 0; i < 10; i++) {
               double d0 = this.m_20185_();
               double d1 = this.m_20186_() + 0.1;
               double d2 = this.m_20189_();
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123810_,
                     d0,
                     d1,
                     d2,
                     ((double)(this.f_19853_.f_46441_.m_188501_() * 1.0F) - 0.5) / 3.0,
                     ((double)(this.f_19853_.f_46441_.m_188501_() * 1.0F) - 0.5) / 3.0,
                     ((double)(this.f_19853_.f_46441_.m_188501_() * 1.0F) - 0.5) / 3.0
                  );
            }
         }
      }
   }

   public static Builder attributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 6.0).m_22268_(Attributes.f_22279_, 0.25);
   }

   protected void m_7581_(ItemEntity itemEntity) {
      if (this.m_21205_().m_41619_()) {
         if (!this.isTaming() && itemEntity.m_32055_().m_204117_(Items.NUGGETS_GOLD)) {
            this.m_21008_(InteractionHand.MAIN_HAND, itemEntity.m_32055_().m_41620_(1));
            this.setTaming(true);
         }
      }
   }

   public boolean isTaming() {
      return (Boolean)this.f_19804_.m_135370_(BEING_TAMED);
   }

   public void setTaming(boolean taming) {
      this.f_19804_.m_135381_(BEING_TAMED, taming);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_
         .m_25352_(1, new UntamedFindItemGoal(this, () -> !(Boolean)this.f_19804_.m_135370_(BEING_TAMED), i -> i.m_32055_().m_204117_(Items.NUGGETS_GOLD)));
      this.f_21345_.m_25352_(4, new LookAtPlayerGoal(this, Player.class, 3.0F, 0.02F));
      this.f_21345_.m_25352_(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21345_.m_25352_(3, new WaterAvoidingRandomStrollGoal(this, 1.2));
      this.f_21345_.m_25352_(2, new AvoidEntityGoal(this, Player.class, 16.0F, 2.0, 1.2));
      this.f_21345_.m_25352_(0, new FloatGoal(this));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(BEING_TAMED, false);
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController<>(this, "walkController", 1.0F, this::animationPredicate));
      animationData.addAnimationController(new AnimationController<>(this, "danceController", 1.0F, this::dancePredicate));
   }

   private PlayState animationPredicate(AnimationEvent<?> event) {
      if (!event.isMoving() && (!this.f_19853_.f_46443_ || !PatchouliHandler.isPatchouliWorld())) {
         return PlayState.STOP;
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
         return PlayState.CONTINUE;
      }
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return SummonUtil.canSummonTakeDamage(pSource) && super.m_6469_(pSource, pAmount);
   }

   private PlayState dancePredicate(AnimationEvent<?> event) {
      if ((Boolean)this.f_19804_.m_135370_(BEING_TAMED)) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("dance_master"));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }
}
