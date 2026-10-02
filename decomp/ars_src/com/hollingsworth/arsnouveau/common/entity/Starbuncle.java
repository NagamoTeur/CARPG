package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.client.IVariantColorProvider;
import com.hollingsworth.arsnouveau.api.entity.IDecoratable;
import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.familiar.PersistentFamiliarData;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.api.util.SummonUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.advancement.ANCriteriaTriggers;
import com.hollingsworth.arsnouveau.common.compat.PatchouliHandler;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.entity.debug.DebugEvent;
import com.hollingsworth.arsnouveau.common.entity.debug.EntityDebugger;
import com.hollingsworth.arsnouveau.common.entity.debug.IDebugger;
import com.hollingsworth.arsnouveau.common.entity.debug.IDebuggerProvider;
import com.hollingsworth.arsnouveau.common.entity.goal.AvoidEntityGoalMC;
import com.hollingsworth.arsnouveau.common.entity.goal.carbuncle.StarbyTransportBehavior;
import com.hollingsworth.arsnouveau.common.entity.goal.carbuncle.UntamedFindItem;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.MinecoloniesAdvancedPathNavigate;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.MovementHandler;
import com.hollingsworth.arsnouveau.common.entity.pathfinding.PathingStuckHandler;
import com.hollingsworth.arsnouveau.common.network.ITagSyncable;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketSyncTag;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.hollingsworth.arsnouveau.setup.reward.Rewards;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class Starbuncle
   extends PathfinderMob
   implements IAnimatable,
   IDecoratable,
   IDispellable,
   ITooltipProvider,
   IWandable,
   IDebuggerProvider,
   ITagSyncable,
   IVariantColorProvider<Starbuncle> {
   public EntityDebugger debugger = new EntityDebugger(this);
   public Starbuncle.StarbuncleGoalState goalState = Starbuncle.StarbuncleGoalState.NONE;
   private MinecoloniesAdvancedPathNavigate pathNavigate;
   public static final EntityDataAccessor<Boolean> TAMED = SynchedEntityData.m_135353_(Starbuncle.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<String> COLOR = SynchedEntityData.m_135353_(Starbuncle.class, EntityDataSerializers.f_135030_);
   public static final EntityDataAccessor<String> PATH_BLOCK = SynchedEntityData.m_135353_(Starbuncle.class, EntityDataSerializers.f_135030_);
   public static final EntityDataAccessor<ItemStack> HEAD_COSMETIC = SynchedEntityData.m_135353_(Starbuncle.class, EntityDataSerializers.f_135033_);
   public static final EntityDataAccessor<CompoundTag> BEHAVIOR_TAG = SynchedEntityData.m_135353_(Starbuncle.class, EntityDataSerializers.f_135042_);
   private int backOff;
   private int bedBackoff;
   public int tamingTime;
   private int lastAABBCalc;
   private AABB cachedAAB;
   public BlockPos jukeboxPos;
   public boolean partyCarby;
   public PathNavigation minecraftPathNav;
   public Starbuncle.StarbuncleData data = new Starbuncle.StarbuncleData(new CompoundTag());
   public ChangeableBehavior dynamicBehavior = new StarbyTransportBehavior(this, new CompoundTag());
   public boolean canSleep;
   AnimationFactory manager = GeckoLibUtil.createFactory(this);
   private boolean setBehaviors;
   public static String[] carbyColors = Arrays.stream(DyeColor.values()).map(DyeColor::m_41065_).toArray(String[]::new);

   public Starbuncle(EntityType<? extends Starbuncle> entityCarbuncleEntityType, Level world) {
      super(entityCarbuncleEntityType, world);
      this.f_19793_ = 1.1F;
      this.dynamicBehavior = new StarbyTransportBehavior(this, new CompoundTag());
      this.reloadGoals();
      this.f_21342_ = new MovementHandler(this);
   }

   public Starbuncle(Level world, boolean tamed) {
      this((EntityType<? extends Starbuncle>)ModEntities.STARBUNCLE_TYPE.get(), world);
      this.setTamed(tamed);
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
         if (this.isTamed()) {
            this.pathNavigate.setStuckHandler(PathingStuckHandler.createStuckHandler().withTeleportOnFullStuck().withTeleportSteps(5));
         }

         this.pathNavigate.getPathingOptions().setCanFitInOneCube(true);
         this.pathNavigate.getPathingOptions().onPathCost = 0.1;
         this.pathNavigate.getPathingOptions().withRoadState(this::isOnRoad);
      }

      return this.pathNavigate;
   }

   public Boolean isOnRoad(BlockState state) {
      return state.m_60734_() instanceof DirtPathBlock || this.data.pathBlock != null && this.data.pathBlock == state.m_60734_();
   }

   public void setBehavior(ChangeableBehavior behavior) {
      this.dynamicBehavior = behavior;
      this.m_20088_().m_135381_(BEHAVIOR_TAG, this.dynamicBehavior.toTag(new CompoundTag()));
      this.reloadGoals();
      this.syncBehavior();
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController<>(this, "walkController", 1.0F, event -> {
         if (!event.isMoving() && (!this.f_19853_.f_46443_ || !PatchouliHandler.isPatchouliWorld())) {
            return PlayState.STOP;
         } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
            return PlayState.CONTINUE;
         }
      }));
      animationData.addAnimationController(
         new AnimationController<>(
            this,
            "danceController",
            1.0F,
            event -> {
               if ((this.isTamed() || !this.getHeldStack().m_204117_(Items.NUGGETS_GOLD))
                  && (!this.partyCarby || this.jukeboxPos == null || !(BlockUtil.distanceFrom(this.f_19825_, this.jukeboxPos) <= 8.0))) {
                  return PlayState.STOP;
               } else {
                  event.getController().setAnimation(new AnimationBuilder().addAnimation("dance"));
                  return PlayState.CONTINUE;
               }
            }
         )
      );
      animationData.addAnimationController(new AnimationController<>(this, "sleepController", 1.0F, event -> {
         if (!event.isMoving() && this.canSleep) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("resting"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      animationData.addAnimationController(new AnimationController<>(this, "idleController", 1.0F, event -> {
         if (!event.isMoving() && !this.canSleep) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
   }

   @Override
   public AnimationFactory getFactory() {
      return this.manager;
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return SummonUtil.canSummonTakeDamage(pSource) && super.m_6469_(pSource, pAmount);
   }

   public boolean isTamed() {
      return (Boolean)this.f_19804_.m_135370_(TAMED);
   }

   public void setTamed(boolean tamed) {
      this.f_19804_.m_135381_(TAMED, tamed);
   }

   public String pathBlockDesc() {
      return (String)this.f_19804_.m_135370_(PATH_BLOCK);
   }

   public void setPathBlockDesc(String name) {
      this.f_19804_.m_135381_(PATH_BLOCK, name);
   }

   public void attemptTame() {
      if (!this.isTamed() && this.getHeldStack().m_204117_(Items.NUGGETS_GOLD)) {
         this.tamingTime++;
         if (this.tamingTime > 60 && !this.f_19853_.f_46443_) {
            ItemStack stack = new ItemStack((ItemLike)ItemsRegistry.STARBUNCLE_SHARD.get(), 1 + this.f_19853_.f_46441_.m_188503_(2));
            if (this.data.adopter != null) {
               stack.m_41764_(1);
               stack.m_41751_(this.data.toTag(this, new CompoundTag()));
            }

            this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), stack));
            this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12052_, SoundSource.NEUTRAL, 1.0F, 1.0F);
            ANCriteriaTriggers.rewardNearbyPlayers(ANCriteriaTriggers.POOF_MOB, (ServerLevel)this.f_19853_, this.m_20097_(), 10);
            this.m_142687_(RemovalReason.DISCARDED);
         } else if (this.tamingTime > 55 && this.f_19853_.f_46443_) {
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

   public void m_8119_() {
      try {
         super.m_8119_();
      } catch (NoClassDefFoundError var3) {
         System.out.println("Starbuncle threaded pathing failed.");
         System.out.println(this);
         return;
      }

      if (this.f_19853_.f_46443_ && this.f_19853_.m_46467_() % 5L == 0L) {
         this.canSleep = this.m_20075_().m_204336_(BlockTagProvider.SUMMON_SLEEPABLE);
      }

      SummonUtil.healOverTime(this);
      if (!this.f_19853_.f_46443_ && this.f_19853_.m_46467_() % 10L == 0L && this.m_7755_().getString().toLowerCase(Locale.ROOT).equals("jeb_")) {
         this.f_19804_.m_135381_(COLOR, carbyColors[this.f_19853_.f_46441_.m_188503_(carbyColors.length)]);
      }

      if (!this.f_19853_.f_46443_) {
         this.lastAABBCalc++;
         if (this.backOff > 0) {
            this.backOff--;
         }

         if (this.bedBackoff > 0) {
            this.bedBackoff--;
         }
      }

      if (!this.f_19853_.f_46443_ && this.dynamicBehavior != null && this.f_19853_.m_46467_() % 100L == 0L) {
         this.dynamicBehavior.syncTag();
      }

      if (!this.f_20890_) {
         if (this.getHeldStack().m_41619_() && !this.f_19853_.f_46443_) {
            for (ItemEntity itementity : this.f_19853_.m_45976_(ItemEntity.class, this.m_20191_().m_82400_(1.0))) {
               if (itementity.m_6084_() && !itementity.m_32055_().m_41619_() && !itementity.m_32063_()) {
                  this.m_7581_(itementity);
                  if (this.getHeldStack() != null && !this.getHeldStack().m_41619_()) {
                     break;
                  }
               }
            }
         }

         this.attemptTame();
      }
   }

   @Override
   public void onWanded(Player playerEntity) {
      boolean removeAcc = this.dynamicBehavior.clearOrRemove();
      this.dynamicBehavior.onWanded(playerEntity);
      this.data.pathBlock = null;
      this.data.bedPos = null;
      if (!this.getCosmeticItem().m_41619_() && removeAcc) {
         this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.getCosmeticItem().m_41620_(1)));
         if (!(this.dynamicBehavior instanceof StarbyTransportBehavior)) {
            this.dynamicBehavior = new StarbyTransportBehavior(this, new CompoundTag());
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.starbuncle.default_behavior"));
            this.syncBehavior();
         }

         this.setCosmeticItem(ItemStack.f_41583_);
      }

      PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.starbuncle.cleared"));
   }

   @Override
   public List<ColorPos> getWandHighlight(List<ColorPos> list) {
      return (List<ColorPos>)(this.dynamicBehavior == null ? new ArrayList<>() : this.dynamicBehavior.getWandHighlight(list));
   }

   public void syncBehavior() {
      Networking.sendToNearby(this.f_19853_, this, new PacketSyncTag(this.dynamicBehavior.toTag(new CompoundTag()), this.m_19879_()));
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      this.dynamicBehavior.onFinishedConnectionFirst(storedPos, storedEntity, playerEntity);
   }

   @Override
   public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      this.dynamicBehavior.onFinishedConnectionLast(storedPos, storedEntity, playerEntity);
   }

   public static Builder attributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 20.0).m_22268_(Attributes.f_22279_, 0.2);
   }

   public boolean m_7337_(Entity pEntity) {
      return pEntity instanceof Player ? false : super.m_7337_(pEntity);
   }

   public boolean m_6094_() {
      return false;
   }

   protected void m_6138_() {
   }

   protected void m_7581_(ItemEntity itemEntity) {
      if (this.getHeldStack().m_41619_()) {
         if (!this.isTamed() && itemEntity.m_32055_().m_204117_(Items.NUGGETS_GOLD)) {
            this.setHeldStack(itemEntity.m_32055_().m_41620_(1));
         } else {
            this.dynamicBehavior.pickUpItem(itemEntity);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_6818_(BlockPos pos, boolean hasSound) {
      super.m_6818_(pos, hasSound);
      this.jukeboxPos = pos;
      this.partyCarby = hasSound;
   }

   protected void reloadGoals() {
      if (!this.f_19853_.m_5776_()) {
         this.f_21345_.f_25345_.clear();

         for (WrappedGoal goal : this.getGoals()) {
            this.f_21345_.m_25352_(goal.m_26012_(), goal.m_26015_());
         }
      }
   }

   public List<WrappedGoal> getGoals() {
      List<WrappedGoal> list = new ArrayList<>();
      list.add(new WrappedGoal(0, new FloatGoal(this)));
      if (!this.isTamed()) {
         list.add(new WrappedGoal(1, new UntamedFindItem(this)));
         list.add(new WrappedGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0F, 0.02F)));
         list.add(new WrappedGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F)));
         list.add(new WrappedGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.2)));
         list.add(new WrappedGoal(2, new AvoidEntityGoalMC(this, Player.class, 16.0F, 2.0, 1.2)));
         list.add(new WrappedGoal(0, new FloatGoal(this)));
      } else {
         list.addAll(this.dynamicBehavior.goals);
      }

      return list;
   }

   protected float m_6108_() {
      return 0.875F;
   }

   public void m_6667_(DamageSource source) {
      if (!this.f_19853_.f_46443_ && this.isTamed()) {
         this.dropData();
      }

      super.m_6667_(source);
   }

   public void dropData() {
      ItemStack stack = new ItemStack((ItemLike)ItemsRegistry.STARBUNCLE_CHARM.get());
      stack.m_41751_(this.data.toTag(this, new CompoundTag()));
      this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), stack));
      if (this.getHeldStack() != null) {
         this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.getHeldStack()));
      }
   }

   public AABB getAABB() {
      if (this.cachedAAB == null || this.lastAABBCalc >= 60) {
         this.cachedAAB = new AABB(this.m_20183_()).m_82400_(8.0);
         this.lastAABBCalc = 0;
      }

      return this.cachedAAB;
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (hand == InteractionHand.MAIN_HAND && !player.m_20193_().f_46443_ && this.isTamed()) {
         ItemStack stack = player.m_21120_(hand);
         if (player.m_21205_().m_204117_(Items.DYES)) {
            DyeColor color = DyeColor.getColor(stack);
            if (color != null && !((String)this.f_19804_.m_135370_(COLOR)).equals(color.m_41065_()) && Arrays.asList(carbyColors).contains(color.m_41065_())) {
               this.setColor(color.m_41065_());
               player.m_21205_().m_41774_(1);
               return InteractionResult.SUCCESS;
            } else {
               return InteractionResult.SUCCESS;
            }
         } else {
            if (player.m_21205_().m_41720_() instanceof BlockItem blockItem) {
               this.data.pathBlock = blockItem.m_40614_();
               this.setPathBlockDesc(Component.m_237115_(this.data.pathBlock.m_7705_()).getString());
               PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.starbuncle.path"));
            }

            return this.dynamicBehavior.mobInteract(player, hand);
         }
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.STARBUNCLE_TYPE.get();
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(TAMED, false);
      this.f_19804_.m_135372_(COLOR, DyeColor.ORANGE.m_41065_());
      this.f_19804_.m_135372_(PATH_BLOCK, "");
      this.f_19804_.m_135372_(HEAD_COSMETIC, ItemStack.f_41583_);
      this.f_19804_.m_135372_(BEHAVIOR_TAG, new CompoundTag());
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   public void setHeldStack(ItemStack stack) {
      this.m_8061_(EquipmentSlot.MAINHAND, stack == null ? ItemStack.f_41583_ : stack);
   }

   public ItemStack getHeldStack() {
      return this.m_21205_();
   }

   @Override
   public ItemStack getCosmeticItem() {
      return (ItemStack)this.f_19804_.m_135370_(HEAD_COSMETIC);
   }

   @Override
   public void setCosmeticItem(ItemStack stack) {
      if (!((ItemStack)this.f_19804_.m_135370_(HEAD_COSMETIC)).m_41619_()) {
         this.f_19853_
            .m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), (ItemStack)this.f_19804_.m_135370_(HEAD_COSMETIC)));
      }

      this.f_19804_.m_135381_(HEAD_COSMETIC, stack);
      this.data.cosmetic = stack;
   }

   @Override
   public boolean onDispel(@Nullable LivingEntity caster) {
      if (this.m_213877_()) {
         return false;
      } else {
         if (!this.f_19853_.f_46443_ && this.isTamed()) {
            this.dropData();
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
            this.m_142687_(RemovalReason.DISCARDED);
         }

         return this.isTamed();
      }
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      this.data = new Starbuncle.StarbuncleData(tag.m_128441_("starbuncleData") ? tag.m_128469_("starbuncleData") : new CompoundTag());
      if (tag.m_128441_("held")) {
         this.setHeldStack(ItemStack.m_41712_((CompoundTag)tag.m_128423_("held")));
      }

      this.backOff = tag.m_128451_("backoff");
      this.f_19804_.m_135381_(TAMED, tag.m_128471_("tamed"));
      if (!this.setBehaviors) {
         this.f_21345_.f_25345_ = new LinkedHashSet();
         this.reloadGoals();
         this.setBehaviors = true;
         this.restoreFromTag();
         if (this.dynamicBehavior != null && !this.f_19853_.f_46443_) {
            this.dynamicBehavior.syncTag();
         }
      }
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128365_("starbuncleData", this.data.toTag(this, new CompoundTag()));
      if (this.getHeldStack() != null) {
         CompoundTag itemTag = new CompoundTag();
         this.getHeldStack().m_41739_(itemTag);
         tag.m_128365_("held", itemTag);
      }

      tag.m_128405_("backoff", this.backOff);
      tag.m_128379_("tamed", (Boolean)this.f_19804_.m_135370_(TAMED));
   }

   public void restoreFromTag() {
      if (this.data.color != null) {
         this.f_19804_.m_135381_(COLOR, this.data.color);
      }

      if (this.data.pathBlock != null) {
         this.setPathBlockDesc(Component.m_237115_(this.data.pathBlock.m_7705_()).getString());
      }

      if (this.data.cosmetic != null && !this.data.cosmetic.m_41619_()) {
         this.f_19804_.m_135381_(HEAD_COSMETIC, this.data.cosmetic);
      }

      this.m_6593_(this.data.name);
      if (this.data.behaviorTag != null) {
         this.dynamicBehavior = BehaviorRegistry.create(this, this.data.behaviorTag);
         this.f_19804_.m_135381_(BEHAVIOR_TAG, this.dynamicBehavior.toTag(new CompoundTag()));
         this.reloadGoals();
      } else if (this.isTamed()) {
         this.dynamicBehavior = new StarbyTransportBehavior(this, new CompoundTag());
         this.f_19804_.m_135381_(BEHAVIOR_TAG, this.dynamicBehavior.toTag(new CompoundTag()));
         this.reloadGoals();
      }

      if (!this.f_19853_.f_46443_ && this.dynamicBehavior != null) {
         this.dynamicBehavior.syncTag();
      }
   }

   public void m_7350_(EntityDataAccessor<?> pKey) {
      super.m_7350_(pKey);
      if (pKey == BEHAVIOR_TAG) {
         this.dynamicBehavior = BehaviorRegistry.create(this, (CompoundTag)this.f_19804_.m_135370_(BEHAVIOR_TAG));
      }
   }

   public void m_6593_(@Nullable Component pName) {
      super.m_6593_(pName);
      this.data.name = pName;
   }

   public int getBedBackoff() {
      return this.bedBackoff;
   }

   public void setBedBackoff(int bedBackoff) {
      this.bedBackoff = bedBackoff;
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.isTamed()) {
         if (this.dynamicBehavior != null) {
            this.dynamicBehavior.getTooltip(tooltip);
         }

         if (this.pathBlockDesc() != null && !this.pathBlockDesc().isEmpty()) {
            tooltip.add(Component.m_237110_("ars_nouveau.starbuncle.pathing", new Object[]{this.f_19804_.m_135370_(PATH_BLOCK)}));
         }
      }
   }

   public int m_213860_() {
      return 0;
   }

   public void setColor(String color) {
      this.data.color = color;
      this.f_19804_.m_135381_(COLOR, this.data.color);
   }

   public void setColor(String color, Starbuncle object) {
      this.setColor(color);
   }

   public String getColor(Starbuncle object) {
      return (String)this.f_19804_.m_135370_(COLOR);
   }

   public String getColor() {
      return (String)this.f_19804_.m_135370_(COLOR);
   }

   public ResourceLocation getTexture(Starbuncle entity) {
      String color = this.getColor(entity);
      if (color.isEmpty()) {
         color = DyeColor.ORANGE.m_41065_();
      }

      return new ResourceLocation("ars_nouveau", "textures/entity/starbuncle_" + color.toLowerCase() + ".png");
   }

   @org.jetbrains.annotations.Nullable
   public SpawnGroupData m_6518_(
      ServerLevelAccessor pLevel,
      DifficultyInstance pDifficulty,
      MobSpawnType pReason,
      @org.jetbrains.annotations.Nullable SpawnGroupData pSpawnData,
      @org.jetbrains.annotations.Nullable CompoundTag pDataTag
   ) {
      RandomSource randomSource = pLevel.m_213780_();
      if (randomSource.m_188501_() <= 0.1F && !Rewards.starbuncles.isEmpty()) {
         try {
            Rewards.ContributorStarby contributorStarby = Rewards.starbuncles.get(randomSource.m_188503_(Rewards.starbuncles.size()));
            this.setColor(contributorStarby.color);
            this.m_6593_(Component.m_237113_(contributorStarby.name));
            this.data.bio = contributorStarby.bio;
            this.data.adopter = contributorStarby.adopter;
         } catch (Exception var8) {
         }
      } else {
         this.setColor(carbyColors[randomSource.m_188503_(carbyColors.length)]);
      }

      return super.m_6518_(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
   }

   public int getBackOff() {
      return this.backOff;
   }

   public void setBackOff(int backOff) {
      this.backOff = backOff;
   }

   @Override
   public IDebugger getDebugger() {
      return this.debugger;
   }

   public void addGoalDebug(Goal goal, DebugEvent debugEvent) {
      debugEvent.id = goal.getClass().getSimpleName() + "_" + debugEvent.id;
      debugEvent.message = debugEvent.message + " ===== current state: " + this.goalState.name();
      this.addDebugEvent(debugEvent);
   }

   @Override
   public void onTagSync(CompoundTag tag) {
      if (this.f_19853_.f_46443_) {
         this.dynamicBehavior = BehaviorRegistry.create(this, tag);
      }
   }

   public static class StarbuncleData extends PersistentFamiliarData<Starbuncle> {
      public Block pathBlock;
      public BlockPos bedPos;
      public CompoundTag behaviorTag;
      public String adopter = null;
      public String bio = null;

      public StarbuncleData(CompoundTag tag) {
         super(tag);
         if (tag.m_128441_("path")) {
            this.pathBlock = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation(tag.m_128461_("path")));
         }

         this.bedPos = NBTUtil.getBlockPos(tag, "bed_");
         if (this.bedPos.equals(BlockPos.f_121853_)) {
            this.bedPos = null;
         }

         if (tag.m_128441_("behavior")) {
            this.behaviorTag = tag.m_128469_("behavior");
         }

         if (tag.m_128441_("cosmetic")) {
            this.cosmetic = ItemStack.m_41712_(tag.m_128469_("cosmetic"));
         }

         if (tag.m_128441_("adopter")) {
            this.adopter = tag.m_128461_("adopter");
         }

         if (tag.m_128441_("bio")) {
            this.bio = tag.m_128461_("bio");
         }
      }

      public CompoundTag toTag(Starbuncle starbuncle, CompoundTag tag) {
         super.toTag(starbuncle, tag);
         if (this.pathBlock != null) {
            tag.m_128359_("path", RegistryHelper.getRegistryName(this.pathBlock).toString());
         }

         if (this.bedPos != null) {
            NBTUtil.storeBlockPos(tag, "bed_", this.bedPos);
         }

         if (!starbuncle.getCosmeticItem().m_41619_()) {
            tag.m_128365_("cosmetic", starbuncle.getCosmeticItem().serializeNBT());
         }

         tag.m_128365_("behavior", starbuncle.dynamicBehavior.toTag(new CompoundTag()));
         if (this.adopter != null) {
            tag.m_128359_("adopter", this.adopter);
         }

         if (this.bio != null) {
            tag.m_128359_("bio", this.bio);
         }

         return tag;
      }
   }

   public static enum StarbuncleGoalState {
      FORAGING,
      HUNTING_ITEM,
      TAKING_ITEM,
      STORING_ITEM,
      RESTING,
      NONE;
   }
}
