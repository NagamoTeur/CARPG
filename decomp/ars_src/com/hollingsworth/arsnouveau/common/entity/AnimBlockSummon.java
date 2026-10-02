package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.entity.ISummon;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.goal.ConditionalLeapGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.ConditionalMeleeGoal;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class AnimBlockSummon extends TamableAnimal implements IAnimatable, ISummon, IDispellable {
   public BlockState blockState;
   public int color;
   private int ticksLeft;
   public static final EntityDataAccessor<Integer> AGE = SynchedEntityData.m_135353_(AnimBlockSummon.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Boolean> CAN_WALK = SynchedEntityData.m_135353_(AnimBlockSummon.class, EntityDataSerializers.f_135035_);
   public boolean isAlternateSpawn;
   public boolean dropItem = true;
   private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.m_135353_(AnimBlockSummon.class, EntityDataSerializers.f_135041_);
   public static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.m_135353_(AnimBlockSummon.class, EntityDataSerializers.f_135028_);
   final AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public AnimBlockSummon(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.isAlternateSpawn = this.f_19796_.m_188499_();
   }

   public AnimBlockSummon(Level pLevel, BlockState state) {
      this((EntityType<? extends TamableAnimal>)ModEntities.ANIMATED_BLOCK.get(), pLevel);
      this.blockState = state;
   }

   public double m_21133_(Attribute pAttribute) {
      return pAttribute == Attributes.f_22281_ ? super.m_21133_(pAttribute) + (double)this.getStateDamageBonus() : super.m_21133_(pAttribute);
   }

   public float getStateDamageBonus() {
      float destroySpeed = 1.0F;

      try {
         destroySpeed = this.blockState.m_60800_(this.f_19853_, this.m_20183_());
      } catch (Exception var3) {
      }

      return destroySpeed;
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ANIMATED_BLOCK.get();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(4, new ConditionalLeapGoal(this, 0.4F, () -> (Boolean)this.f_19804_.m_135370_(CAN_WALK)));
      this.f_21345_.m_25352_(5, new ConditionalMeleeGoal(this, 1.0, true, () -> (Boolean)this.f_19804_.m_135370_(CAN_WALK)));
      this.f_21345_.m_25352_(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
      this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(10, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new OwnerHurtTargetGoal(this));
      this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[]{AnimBlockSummon.class}).m_26044_(new Class[]{AnimBlockSummon.class}));
   }

   public boolean m_7327_(Entity pEntity) {
      if (this.m_21826_() != null && pEntity.m_7307_(this.m_21826_())) {
         return false;
      } else {
         boolean result = super.m_7327_(pEntity);
         if (result) {
            this.ticksLeft -= 400;
         }

         return result;
      }
   }

   public static Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22279_, 0.3F).m_22268_(Attributes.f_22276_, 4.0).m_22268_(Attributes.f_22281_, 2.0);
   }

   public boolean m_6779_(LivingEntity pTarget) {
      if (this.m_21805_() != null) {
         if (pTarget.m_20148_().equals(this.m_21805_())) {
            return false;
         }

         if (pTarget instanceof ISummon summon) {
            return super.m_6779_(pTarget) && !this.m_21805_().equals(summon.m_21805_());
         }
      }

      return super.m_6779_(pTarget);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         this.ticksLeft--;
         this.f_19804_.m_135381_(AGE, (Integer)this.f_19804_.m_135370_(AGE) + 1);
         if ((Integer)this.f_19804_.m_135370_(AGE) > 20) {
            this.f_19804_.m_135381_(CAN_WALK, true);
         }

         if (this.ticksLeft <= 0) {
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
            this.returnToFallingBlock(this.blockState);
            this.m_142687_(RemovalReason.DISCARDED);
            this.onSummonDeath(this.f_19853_, null, true);
         }
      }
   }

   public void returnToFallingBlock(BlockState blockState) {
      if (blockState != null) {
         EnchantedFallingBlock fallingBlock = new EnchantedFallingBlock(this.f_19853_, this.m_20183_(), blockState);
         fallingBlock.m_5602_(this.m_21826_());
         fallingBlock.m_20256_(this.m_20184_());
         fallingBlock.setColor(ParticleColor.fromInt(this.color));
         fallingBlock.dropItem = this.dropItem;
         if (blockState.m_60734_() == BlockRegistry.MAGE_BLOCK) {
            fallingBlock.dropItem = false;
         }

         this.f_19853_.m_7967_(fallingBlock);
      }
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel pLevel, AgeableMob pOtherParent) {
      return null;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(OWNER_UUID, Optional.of(Util.f_137441_));
      this.f_19804_.m_135372_(COLOR, ParticleColor.defaultParticleColor().getColor());
      this.f_19804_.m_135372_(AGE, 0);
      this.f_19804_.m_135372_(CAN_WALK, false);
   }

   public void m_6667_(DamageSource cause) {
      super.m_6667_(cause);
      this.returnToFallingBlock(this.getBlockState());
      this.onSummonDeath(this.f_19853_, cause, false);
   }

   @Nullable
   public LivingEntity m_21826_() {
      return this.f_20890_ ? null : super.m_21826_();
   }

   public boolean m_35506_() {
      return false;
   }

   public boolean m_7848_(Animal pOtherAnimal) {
      return false;
   }

   public boolean m_6898_(ItemStack stack) {
      return false;
   }

   public int m_213860_() {
      return 0;
   }

   @Override
   public int getTicksLeft() {
      return this.ticksLeft;
   }

   @Override
   public void setTicksLeft(int ticks) {
      this.ticksLeft = ticks;
   }

   @Nullable
   @Override
   public UUID m_21805_() {
      return ((Optional)this.m_20088_().m_135370_(OWNER_UUID)).isEmpty() ? this.m_20148_() : (UUID)((Optional)this.m_20088_().m_135370_(OWNER_UUID)).get();
   }

   @Override
   public void setOwnerID(UUID uuid) {
      this.m_20088_().m_135381_(OWNER_UUID, Optional.ofNullable(uuid));
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.setResetSpeedInTicks(0.0);
      String spawnAnim = "spawn";
      data.addAnimationController(new AnimationController<>(this, spawnAnim, 0.0F, e -> {
         if (!(Boolean)this.f_19804_.m_135370_(CAN_WALK)) {
            e.getController().setAnimation(new AnimationBuilder().addAnimation(spawnAnim));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      data.addAnimationController(new AnimationController<>(this, "run", 1.0F, e -> {
         if (e.isMoving() && (Boolean)this.f_19804_.m_135370_(CAN_WALK)) {
            e.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
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

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this, Block.m_49956_(this.getBlockState()));
   }

   public BlockState getBlockState() {
      return this.blockState != null ? this.blockState : BlockRegistry.MAGE_BLOCK.m_49966_();
   }

   public void m_141965_(ClientboundAddEntityPacket pPacket) {
      super.m_141965_(pPacket);
      this.blockState = Block.m_49803_(pPacket.m_131509_());
      double d0 = pPacket.m_131500_();
      double d1 = pPacket.m_131501_();
      double d2 = pPacket.m_131502_();
      this.m_6034_(d0, d1, d2);
   }

   public void setColor(int color) {
      this.color = color;
      this.m_20088_().m_135381_(COLOR, color);
   }

   public boolean m_20223_(CompoundTag pCompound) {
      pCompound.m_128405_("color", this.color);
      return super.m_20223_(pCompound);
   }

   public void m_20258_(CompoundTag pCompound) {
      super.m_20258_(pCompound);
      this.m_20088_().m_135381_(COLOR, pCompound.m_128451_("color"));
   }

   public void m_7378_(CompoundTag pCompound) {
      super.m_7378_(pCompound);
      this.ticksLeft = pCompound.m_128451_("left");
      this.color = pCompound.m_128451_("color");
      this.blockState = Block.m_49803_(pCompound.m_128451_("blockState"));
      this.m_20088_().m_135381_(AGE, pCompound.m_128451_("ticksAlive"));
      this.m_20088_().m_135381_(CAN_WALK, pCompound.m_128471_("canWalk"));
      this.dropItem = !pCompound.m_128441_("dropItem") || pCompound.m_128471_("dropItem");
   }

   public void m_7380_(CompoundTag pCompound) {
      super.m_7380_(pCompound);
      pCompound.m_128405_("left", this.ticksLeft);
      pCompound.m_128405_("color", this.color);
      pCompound.m_128405_("blockState", Block.m_49956_(this.blockState));
      pCompound.m_128405_("ticksAlive", (Integer)this.m_20088_().m_135370_(AGE));
      pCompound.m_128379_("canWalk", (Boolean)this.m_20088_().m_135370_(CAN_WALK));
      pCompound.m_128379_("dropItem", this.dropItem);
   }

   public int getColor() {
      if (this.color == 0) {
         this.color = (Integer)this.f_19804_.m_135370_(COLOR);
      }

      return this.color;
   }

   @Override
   public boolean onDispel(@NotNull LivingEntity caster) {
      this.setTicksLeft(0);
      return true;
   }
}
