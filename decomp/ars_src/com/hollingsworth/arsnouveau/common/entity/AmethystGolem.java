package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.api.util.SummonUtil;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.compat.PatchouliHandler;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.entity.goal.GoBackHomeGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.amethyst_golem.ConvertBuddingGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.amethyst_golem.DepositAmethystGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.amethyst_golem.GrowClusterGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.amethyst_golem.HarvestClusterGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.amethyst_golem.PickupAmethystGoal;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.MinecoloniesAdvancedPathNavigate;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.PathingStuckHandler;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class AmethystGolem extends PathfinderMob implements IAnimatable, IDispellable, ITooltipProvider, IWandable {
   public static final EntityDataAccessor<Optional<BlockPos>> HOME = SynchedEntityData.m_135353_(AmethystGolem.class, EntityDataSerializers.f_135039_);
   public static final EntityDataAccessor<Boolean> IMBUEING = SynchedEntityData.m_135353_(AmethystGolem.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> STOMPING = SynchedEntityData.m_135353_(AmethystGolem.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<BlockPos> IMBUE_POS = SynchedEntityData.m_135353_(AmethystGolem.class, EntityDataSerializers.f_135038_);
   public int growCooldown;
   public int convertCooldown;
   public int pickupCooldown;
   public int harvestCooldown;
   public List<BlockPos> buddingBlocks = new ArrayList<>();
   public List<BlockPos> amethystBlocks = new ArrayList<>();
   public int scanCooldown;
   public MinecoloniesAdvancedPathNavigate pathNavigate;
   public PathNavigation minecraftPathNav;
   public AmethystGolem.AmethystGolemGoalState goalState;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public AmethystGolem(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
      super(p_21683_, p_21684_);
   }

   public MinecoloniesAdvancedPathNavigate getNavigation() {
      if (this.pathNavigate == null) {
         this.pathNavigate = new MinecoloniesAdvancedPathNavigate(this, this.f_19853_);
         this.minecraftPathNav = this.f_21344_;
         this.f_21344_ = this.pathNavigate;
         this.pathNavigate.m_7008_(true);
         this.pathNavigate.setSwimSpeedFactor(2.0);
         this.pathNavigate.getPathingOptions().setEnterDoors(true);
         this.pathNavigate.getPathingOptions().setCanOpenDoors(true);
         this.pathNavigate.setStuckHandler(PathingStuckHandler.createStuckHandler());
      }

      return this.pathNavigate;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new GoBackHomeGoal(this, this::getHome, 10, () -> true));
      this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(3, new ConvertBuddingGoal(this, () -> this.convertCooldown <= 0 && this.getHome() != null && this.getHeldStack().m_41619_()));
      this.f_21345_.m_25352_(4, new GrowClusterGoal(this, () -> this.growCooldown <= 0 && this.getHome() != null && this.getHeldStack().m_41619_()));
      this.f_21345_
         .m_25352_(
            5, new HarvestClusterGoal(this, () -> this.harvestCooldown <= 0 && this.getHome() != null && !this.isImbueing() && this.getHeldStack().m_41619_())
         );
      this.f_21345_.m_25352_(2, new PickupAmethystGoal(this, () -> this.getHome() != null && this.pickupCooldown <= 0));
      this.f_21345_.m_25352_(2, new DepositAmethystGoal(this, () -> this.getHome() != null && !this.getHeldStack().m_41619_()));
   }

   public void m_8119_() {
      super.m_8119_();
      SummonUtil.healOverTime(this);
      if (this.harvestCooldown > 0) {
         this.harvestCooldown--;
      }

      if (this.growCooldown > 0) {
         this.growCooldown--;
      }

      if (this.convertCooldown > 0) {
         this.convertCooldown--;
      }

      if (this.scanCooldown > 0) {
         this.scanCooldown--;
      }

      if (this.pickupCooldown > 0) {
         this.pickupCooldown--;
      }

      if (!this.f_19853_.f_46443_ && this.scanCooldown == 0 && this.getHome() != null) {
         this.scanCooldown = 3600;
         this.scanBlocks();
      }

      if (this.f_19853_.f_46443_ && this.isImbueing() && this.getImbuePos() != null) {
         Vec3 vec = new Vec3((double)this.getImbuePos().m_123341_() + 0.5, (double)this.getImbuePos().m_123342_(), (double)this.getImbuePos().m_123343_() + 0.5);
         this.f_19853_
            .m_7106_(
               GlowParticleData.createData(new ParticleColor(255, 50, 150)),
               (double)((float)vec.f_82479_) - Math.sin((double)ClientInfo.ticksInGame / 8.0),
               (double)((float)vec.f_82480_) + Math.sin((double)ClientInfo.ticksInGame / 5.0) / 8.0 + 0.5,
               (double)((float)vec.f_82481_) - Math.cos((double)ClientInfo.ticksInGame / 8.0),
               0.0,
               0.0,
               0.0
            );
      }
   }

   public void setHeldStack(ItemStack stack) {
      this.m_8061_(EquipmentSlot.MAINHAND, stack);
   }

   public ItemStack getHeldStack() {
      return this.m_21205_();
   }

   public boolean isStomping() {
      return (Boolean)this.f_19804_.m_135370_(STOMPING);
   }

   public void setStomping(boolean imbueing) {
      this.f_19804_.m_135381_(STOMPING, imbueing);
   }

   public boolean isImbueing() {
      return (Boolean)this.f_19804_.m_135370_(IMBUEING);
   }

   public void setImbueing(boolean imbueing) {
      this.f_19804_.m_135381_(IMBUEING, imbueing);
   }

   public BlockPos getImbuePos() {
      return (BlockPos)this.f_19804_.m_135370_(IMBUE_POS);
   }

   public void setImbuePos(BlockPos pos) {
      this.f_19804_.m_135381_(IMBUE_POS, pos);
   }

   public void scanBlocks() {
      BlockPos pos = this.getHome().m_7949_();
      this.amethystBlocks = new ArrayList<>();
      this.buddingBlocks = new ArrayList<>();

      for (BlockPos b : BlockPos.m_121940_(pos.m_6625_(3).m_122020_(5).m_122030_(5), pos.m_6630_(10).m_122013_(5).m_122025_(5))) {
         if (!this.f_19853_.m_8055_(b).m_60795_()) {
            if (this.f_19853_.m_8055_(b).m_60734_() == Blocks.f_152490_) {
               this.amethystBlocks.add(b.m_7949_());
            }

            if (this.f_19853_.m_8055_(b).m_204336_(BlockTagProvider.BUDDING_BLOCKS)) {
               this.buddingBlocks.add(b.m_7949_());
            }
         }
      }
   }

   protected void m_7355_(BlockPos pPos, BlockState pBlock) {
      SoundEvent soundtype = SoundEvents.f_144054_;
      this.m_5496_(soundtype, (float)(Math.random() * 0.45F), (float)(Math.random() * 1.0));
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null) {
         this.setHome(storedPos);
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.home_set"));
      }
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.getHome() != null) {
         tooltip.add(Component.m_237110_("ars_nouveau.gathering_at", new Object[]{this.getHome().m_123344_()}));
      }
   }

   public void m_6667_(DamageSource source) {
      if (!this.f_19853_.f_46443_) {
         ItemStack stack = new ItemStack((ItemLike)ItemsRegistry.AMETHYST_GOLEM_CHARM.get());
         this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), stack));
         if (this.getHeldStack() != null) {
            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.getHeldStack()));
         }
      }

      super.m_6667_(source);
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return SummonUtil.canSummonTakeDamage(pSource) && super.m_6469_(pSource, pAmount);
   }

   @Override
   public boolean onDispel(@org.jetbrains.annotations.Nullable LivingEntity caster) {
      if (this.m_213877_()) {
         return false;
      } else {
         if (!this.f_19853_.f_46443_) {
            ItemStack stack = new ItemStack((ItemLike)ItemsRegistry.AMETHYST_GOLEM_CHARM.get());
            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), stack.m_41777_()));
            stack = this.getHeldStack();
            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), stack));
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
            this.m_142687_(RemovalReason.DISCARDED);
         }

         return true;
      }
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      NBTUtil.storeBlockPos(tag, "home", this.getHome());
      tag.m_128405_("grow", this.growCooldown);
      tag.m_128405_("convert", this.convertCooldown);
      tag.m_128405_("harvest", this.harvestCooldown);
      tag.m_128405_("pickup", this.pickupCooldown);
      if (this.getHeldStack() != null) {
         CompoundTag itemTag = new CompoundTag();
         this.getHeldStack().m_41739_(itemTag);
         tag.m_128365_("held", itemTag);
      }
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      if (NBTUtil.hasBlockPos(tag, "home")) {
         this.setHome(NBTUtil.getBlockPos(tag, "home"));
      }

      this.growCooldown = tag.m_128451_("grow");
      this.convertCooldown = tag.m_128451_("convert");
      this.harvestCooldown = tag.m_128451_("harvest");
      this.pickupCooldown = tag.m_128451_("pickup");
      if (tag.m_128441_("held")) {
         this.setHeldStack(ItemStack.m_41712_((CompoundTag)tag.m_128423_("held")));
      }
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController<>(this, "run_controller", 1.0F, this::runController));
      data.addAnimationController(new AnimationController<>(this, "attack_controller", 5.0F, this::attackController));
   }

   private PlayState attackController(AnimationEvent<?> animationEvent) {
      return PlayState.CONTINUE;
   }

   private PlayState runController(AnimationEvent<?> animationEvent) {
      if (this.isStomping()) {
         animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("harvest2"));
         return PlayState.CONTINUE;
      } else if (!this.isImbueing() && (!this.f_19853_.f_46443_ || !PatchouliHandler.isPatchouliWorld())) {
         if (animationEvent.isMoving()) {
            String anim = this.getHeldStack().m_41619_() ? "run" : "run_carry";
            animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation(anim));
            return PlayState.CONTINUE;
         } else if (!this.getHeldStack().m_41619_()) {
            animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("carry_idle"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      } else {
         animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("tending_master"));
         return PlayState.CONTINUE;
      }
   }

   public void setHome(BlockPos home) {
      this.f_19804_.m_135381_(HOME, Optional.of(home));
   }

   @org.jetbrains.annotations.Nullable
   public BlockPos getHome() {
      return (BlockPos)((Optional)this.f_19804_.m_135370_(HOME)).orElse(null);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HOME, Optional.empty());
      this.f_19804_.m_135372_(IMBUEING, false);
      this.f_19804_.m_135372_(IMBUE_POS, BlockPos.f_121853_);
      this.f_19804_.m_135372_(STOMPING, false);
   }

   public boolean m_6785_(double p_213397_1_) {
      return false;
   }

   public int m_213860_() {
      return 0;
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   public static Builder attributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 20.0).m_22268_(Attributes.f_22279_, 0.2);
   }

   public static enum AmethystGolemGoalState {
      NONE,
      CONVERT,
      GROW,
      HARVEST,
      PICKUP,
      DEPOSIT;
   }
}
