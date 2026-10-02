package com.bobmowzie.mowziesmobs.server.entity.foliaath;

import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationBabyFoliaathEatAI;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class EntityBabyFoliaath extends MowzieLLibraryEntity {
   private static final EntityDataAccessor<Integer> GROWTH = SynchedEntityData.m_135353_(EntityBabyFoliaath.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> INFANT = SynchedEntityData.m_135353_(EntityBabyFoliaath.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> HUNGRY = SynchedEntityData.m_135353_(EntityBabyFoliaath.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<ItemStack> EATING = SynchedEntityData.m_135353_(EntityBabyFoliaath.class, EntityDataSerializers.f_135033_);
   public static final Animation EAT_ANIMATION = Animation.create(20);
   public ControlledAnimation activate = new ControlledAnimation(5);
   private double prevActivate;

   public EntityBabyFoliaath(EntityType<? extends EntityBabyFoliaath> type, Level world) {
      super(type, world);
      this.setInfant(true);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new AnimationBabyFoliaathEatAI<>(this, EAT_ANIMATION));
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().m_22268_(Attributes.f_22276_, 1.0).m_22268_(Attributes.f_22278_, 1.0);
   }

   protected boolean isMovementNoisy() {
      return false;
   }

   public boolean m_6063_() {
      return false;
   }

   public void m_5997_(double x, double y, double z) {
      super.m_5997_(0.0, y, 0.0);
   }

   protected void m_6138_() {
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
      this.f_20883_ = 0.0F;
      if (this.arePlayersCarryingMeat(this.getPlayersNearby(3.0, 3.0, 3.0, 3.0)) && this.getAnimation() == NO_ANIMATION && this.getHungry()) {
         this.activate.increaseTimer();
      } else {
         this.activate.decreaseTimer();
      }

      if (this.activate.getTimer() == 1 && this.prevActivate - (double)this.activate.getTimer() < 0.0) {
         this.m_5496_((SoundEvent)MMSounds.ENTITY_FOLIAATH_GRUNT.get(), 0.5F, 1.5F);
      }

      this.prevActivate = (double)this.activate.getTimer();
      if (!this.f_19853_.f_46443_ && this.getHungry() && this.getAnimation() == NO_ANIMATION) {
         for (ItemEntity meat : this.getMeatsNearby(0.4, 0.2, 0.4, 0.4)) {
            ItemStack stack = meat.m_32055_().m_41620_(1);
            if (!stack.m_41619_()) {
               this.setEating(stack);
               AnimationHandler.INSTANCE.sendAnimationMessage(this, EAT_ANIMATION);
               this.m_5496_((SoundEvent)MMSounds.ENTITY_FOLIAATH_BABY_EAT.get(), 0.5F, 1.2F);
               this.incrementGrowth();
               this.setHungry(false);
               break;
            }
         }
      }

      if (this.f_19853_.f_46443_
         && this.getAnimation() == EAT_ANIMATION
         && (
            this.getAnimationTick() == 3
               || this.getAnimationTick() == 7
               || this.getAnimationTick() == 11
               || this.getAnimationTick() == 15
               || this.getAnimationTick() == 19
         )) {
         for (int i = 0; i <= 5; i++) {
            this.f_19853_
               .m_7106_(
                  new ItemParticleOption(ParticleTypes.f_123752_, this.getEating()),
                  this.m_20185_(),
                  this.m_20186_() + 0.2,
                  this.m_20189_(),
                  (double)this.f_19796_.m_188501_() * 0.2 - 0.1,
                  (double)this.f_19796_.m_188501_() * 0.2,
                  (double)this.f_19796_.m_188501_() * 0.2 - 0.1
               );
         }
      }

      if (!this.f_19853_.f_46443_) {
         if (this.f_19797_ % 20 == 0 && !this.getHungry()) {
            this.incrementGrowth();
         }

         this.setInfant(this.getGrowth() < 600);
         if (this.getInfant()) {
            this.setHungry(false);
         }

         if (this.getGrowth() == 600) {
            this.setHungry(true);
         }

         if (this.getGrowth() == 1200) {
            this.setHungry(true);
         }

         if (this.getGrowth() == 1800) {
            this.setHungry(true);
         }

         if (this.getGrowth() == 2400) {
            EntityFoliaath adultFoliaath = new EntityFoliaath((EntityType<? extends EntityFoliaath>)EntityHandler.FOLIAATH.get(), this.f_19853_);
            adultFoliaath.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
            adultFoliaath.setCanDespawn(false);
            this.f_19853_.m_7967_(adultFoliaath);
            this.m_146870_();
         }
      }
   }

   @Override
   public Animation getDeathAnimation() {
      return null;
   }

   @Override
   public Animation getHurtAnimation() {
      return null;
   }

   private boolean arePlayersCarryingMeat(List<Player> players) {
      if (players.size() > 0) {
         for (Player player : players) {
            FoodProperties food = player.m_21205_().m_41720_().m_41473_();
            if (food != null && food.m_38746_()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void m_6667_(DamageSource source) {
      super.m_6667_(source);

      for (int i = 0; i < 10; i++) {
         this.f_19853_
            .m_7106_(
               new BlockParticleOption(ParticleTypes.f_123794_, Blocks.f_50053_.m_49966_()),
               this.m_20185_(),
               this.m_20186_() + 0.2,
               this.m_20189_(),
               0.0,
               0.0,
               0.0
            );
      }

      this.m_146870_();
   }

   public boolean m_6094_() {
      return false;
   }

   @Override
   public void m_7334_(Entity collider) {
      this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
   }

   protected SoundEvent m_5592_() {
      this.m_5496_(SoundEvents.f_11988_, 1.0F, 0.8F);
      return null;
   }

   public boolean m_5545_(LevelAccessor world, MobSpawnType reason) {
      if (world.m_45784_(this) && world.m_45786_(this) && !world.m_46855_(this.m_20191_())) {
         BlockPos ground = new BlockPos(Mth.m_14107_(this.m_20185_()), Mth.m_14107_(this.m_20191_().f_82289_) - 1, Mth.m_14107_(this.m_20189_()));
         BlockState block = world.m_8055_(ground);
         if (block.m_60734_() == Blocks.f_50440_ || block.m_60767_() == Material.f_76314_ || block.m_60767_() == Material.f_76274_) {
            this.m_5496_(SoundEvents.f_11990_, 1.0F, 0.8F);
            return true;
         }
      }

      return false;
   }

   public List<ItemEntity> getMeatsNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      List<Entity> list = this.f_19853_.m_45933_(this, this.m_20191_().m_82377_(distanceX, distanceY, distanceZ));
      ArrayList<ItemEntity> listEntityItem = new ArrayList<>();

      for (Entity entityNeighbor : list) {
         if (entityNeighbor instanceof ItemEntity && (double)this.m_20270_(entityNeighbor) <= radius) {
            FoodProperties food = ((ItemEntity)entityNeighbor).m_32055_().m_41720_().m_41473_();
            if (food != null && food.m_38746_()) {
               listEntityItem.add((ItemEntity)entityNeighbor);
            }
         }
      }

      return listEntityItem;
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("tickGrowth", this.getGrowth());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setGrowth(compound.m_128451_("tickGrowth"));
   }

   public boolean m_8023_() {
      return true;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(GROWTH, 0);
      this.m_20088_().m_135372_(INFANT, false);
      this.m_20088_().m_135372_(HUNGRY, false);
      this.m_20088_().m_135372_(EATING, ItemStack.f_41583_);
   }

   public int getGrowth() {
      return (Integer)this.m_20088_().m_135370_(GROWTH);
   }

   public void setGrowth(int growth) {
      this.m_20088_().m_135381_(GROWTH, growth);
   }

   public void incrementGrowth() {
      this.setGrowth(this.getGrowth() + 1);
   }

   public boolean getInfant() {
      return (Boolean)this.m_20088_().m_135370_(INFANT);
   }

   public void setInfant(boolean infant) {
      this.m_20088_().m_135381_(INFANT, infant);
   }

   public boolean getHungry() {
      return (Boolean)this.m_20088_().m_135370_(HUNGRY);
   }

   public void setHungry(boolean hungry) {
      this.m_20088_().m_135381_(HUNGRY, hungry);
   }

   public void setEating(ItemStack stack) {
      this.m_20088_().m_135381_(EATING, stack);
   }

   public ItemStack getEating() {
      return (ItemStack)this.m_20088_().m_135370_(EATING);
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{EAT_ANIMATION};
   }
}
