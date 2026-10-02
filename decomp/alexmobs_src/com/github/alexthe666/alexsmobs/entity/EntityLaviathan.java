package com.github.alexthe666.alexsmobs.entity;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.entity.ai.AnimalAIFindWaterLava;
import com.github.alexthe666.alexsmobs.entity.ai.AnimalAIHerdPanic;
import com.github.alexthe666.alexsmobs.entity.ai.BoneSerpentPathNavigator;
import com.github.alexthe666.alexsmobs.entity.ai.GroundPathNavigatorWide;
import com.github.alexthe666.alexsmobs.entity.ai.LaviathanAIRandomSwimming;
import com.github.alexthe666.alexsmobs.entity.ai.TameableAIRide;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.misc.AMAdvancementTriggerRegistry;
import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import com.github.alexthe666.alexsmobs.misc.AMTagRegistry;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;

public class EntityLaviathan extends Animal implements ISemiAquatic, IHerdPanic {
   private static final EntityDataAccessor<Boolean> OBSIDIAN = SynchedEntityData.m_135353_(EntityLaviathan.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Float> HEAD_HEIGHT = SynchedEntityData.m_135353_(EntityLaviathan.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> HEAD_YROT = SynchedEntityData.m_135353_(EntityLaviathan.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> CHILL_TIME = SynchedEntityData.m_135353_(EntityLaviathan.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.m_135353_(EntityLaviathan.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> HAS_BODY_GEAR = SynchedEntityData.m_135353_(EntityLaviathan.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> HAS_HEAD_GEAR = SynchedEntityData.m_135353_(EntityLaviathan.class, EntityDataSerializers.f_135035_);
   private static final Predicate<EntityCrimsonMosquito> HEALTHY_MOSQUITOES = mob -> mob.m_6084_() && mob.m_21223_() > 0.0F && !mob.isSick();
   public static final ResourceLocation OBSIDIAN_LOOT = new ResourceLocation("alexsmobs", "entities/laviathan_obsidian");
   public final EntityLaviathanPart headPart;
   public final EntityLaviathanPart neckPart1;
   public final EntityLaviathanPart neckPart2;
   public final EntityLaviathanPart neckPart3;
   public final EntityLaviathanPart neckPart4;
   public final EntityLaviathanPart neckPart5;
   public final EntityLaviathanPart seat1;
   public final EntityLaviathanPart seat2;
   public final EntityLaviathanPart seat3;
   public final EntityLaviathanPart seat4;
   public final EntityLaviathanPart[] theEntireNeck;
   public final EntityLaviathanPart[] allParts;
   public final EntityLaviathanPart[] seatParts;
   private final UUID[] riderPositionMap = new UUID[4];
   public float prevHeadHeight = 0.0F;
   public float swimProgress = 0.0F;
   public float prevSwimProgress = 0.0F;
   public float biteProgress;
   public float prevBiteProgress;
   public int revengeCooldown = 0;
   private boolean isLandNavigator;
   private int conversionTime = 0;
   private int dismountCooldown = 0;
   private int headPeakCooldown = 0;
   private boolean hasObsidianArmor;
   private int blockBreakCounter;

   protected EntityLaviathan(EntityType<? extends Animal> entityType, Level level) {
      super(entityType, level);
      this.f_19793_ = 1.3F;
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.m_21441_(BlockPathTypes.WATER_BORDER, 0.0F);
      this.m_21441_(BlockPathTypes.LAVA, 0.0F);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0F);
      this.headPart = new EntityLaviathanPart(this, 1.2F, 0.9F);
      this.neckPart1 = new EntityLaviathanPart(this, 0.9F, 0.9F);
      this.neckPart2 = new EntityLaviathanPart(this, 0.9F, 0.9F);
      this.neckPart3 = new EntityLaviathanPart(this, 0.9F, 0.9F);
      this.neckPart4 = new EntityLaviathanPart(this, 0.9F, 0.9F);
      this.neckPart5 = new EntityLaviathanPart(this, 0.9F, 0.9F);
      this.seat1 = new EntityLaviathanPart(this, 0.9F, 0.4F);
      this.seat2 = new EntityLaviathanPart(this, 0.9F, 0.4F);
      this.seat3 = new EntityLaviathanPart(this, 0.9F, 0.4F);
      this.seat4 = new EntityLaviathanPart(this, 0.9F, 0.4F);
      this.theEntireNeck = new EntityLaviathanPart[]{this.neckPart1, this.neckPart2, this.neckPart3, this.neckPart4, this.neckPart5, this.headPart};
      this.allParts = new EntityLaviathanPart[]{
         this.neckPart1, this.neckPart2, this.neckPart3, this.neckPart4, this.neckPart5, this.headPart, this.seat1, this.seat2, this.seat3, this.seat4
      };
      this.seatParts = new EntityLaviathanPart[]{this.seat1, this.seat2, this.seat3, this.seat4};
      this.switchNavigator(true);
   }

   public boolean m_5545_(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
      return AMEntityRegistry.rollSpawn(AMConfig.laviathanSpawnRolls, this.m_217043_(), spawnReasonIn);
   }

   public static boolean canLaviathanSpawn(
      EntityType<EntityLaviathan> p_234314_0_, LevelAccessor p_234314_1_, MobSpawnType p_234314_2_, BlockPos p_234314_3_, RandomSource p_234314_4_
   ) {
      MutableBlockPos blockpos$mutable = p_234314_3_.m_122032_();

      do {
         blockpos$mutable.m_122173_(Direction.UP);
      } while (p_234314_1_.m_6425_(blockpos$mutable).m_205070_(FluidTags.f_13132_));

      return p_234314_1_.m_8055_(blockpos$mutable).m_60795_();
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)AMSoundRegistry.LAVIATHAN_IDLE.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)AMSoundRegistry.LAVIATHAN_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)AMSoundRegistry.LAVIATHAN_HURT.get();
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      return this.isObsidian() ? OBSIDIAN_LOOT : super.m_7582_();
   }

   public static Builder bakeAttributes() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22276_, 60.0)
         .m_22268_(Attributes.f_22281_, 1.0)
         .m_22268_(Attributes.f_22284_, 10.0)
         .m_22268_(Attributes.f_22279_, 0.3F)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   public boolean m_5829_() {
      return this.m_6084_();
   }

   protected boolean m_7310_(Entity p_38390_) {
      return this.m_20197_().size() < 4 && !this.m_204029_(FluidTags.f_13132_) && !this.m_204029_(FluidTags.f_13131_);
   }

   public void m_7334_(Entity entity) {
      entity.m_20256_(entity.m_20184_().m_82549_(this.m_20184_()));
   }

   public boolean m_6898_(ItemStack stack) {
      return stack.m_150930_((Item)AMItemRegistry.MOSQUITO_LARVA.get());
   }

   public boolean m_6094_() {
      return false;
   }

   public float m_6143_() {
      return 0.0F;
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      Item item = itemstack.m_41720_();
      if (item == Items.f_42542_ && this.m_21223_() < this.m_21233_()) {
         if (!player.m_7500_()) {
            itemstack.m_41774_(1);
         }

         this.m_5634_(10.0F);
         return InteractionResult.SUCCESS;
      } else if (item == AMItemRegistry.STRADDLE_HELMET.get() && !this.hasHeadGear() && !this.m_6162_()) {
         if (!player.m_7500_()) {
            itemstack.m_41774_(1);
         }

         this.setHeadGear(true);
         return InteractionResult.SUCCESS;
      } else if (item == AMItemRegistry.STRADDLE_SADDLE.get() && !this.hasBodyGear() && !this.m_6162_()) {
         if (!player.m_7500_()) {
            itemstack.m_41774_(1);
         }

         this.setBodyGear(true);
         return InteractionResult.SUCCESS;
      } else {
         InteractionResult type = super.m_6071_(player, hand);
         InteractionResult interactionresult = itemstack.m_41647_(player, this, hand);
         if (interactionresult != InteractionResult.SUCCESS
            && type != InteractionResult.SUCCESS
            && !this.m_6898_(itemstack)
            && this.hasBodyGear()
            && !this.m_6162_()) {
            if (!player.m_6144_()) {
               if (!this.f_19853_.f_46443_) {
                  player.m_20329_(this);
               }
            } else {
               this.m_20153_();
            }

            return InteractionResult.SUCCESS;
         } else {
            return type;
         }
      }
   }

   public int getClosestOpenSeat(Vec3 entityPos) {
      int closest = -1;
      double closestDistance = Double.MAX_VALUE;

      for (int i = 0; i < this.seatParts.length; i++) {
         double dist = entityPos.m_82554_(this.seatParts[i].m_20182_());
         if ((closest == -1 || closestDistance > dist) && this.riderPositionMap[i] == null) {
            closest = i;
            closestDistance = dist;
         }
      }

      return closest;
   }

   @Nullable
   public Entity m_6688_() {
      int playerPosition = -1;
      Player player = null;
      if (this.hasHeadGear() && this.hasBodyGear()) {
         for (Entity passenger : this.m_20197_()) {
            if (passenger instanceof Player) {
               Player player2 = (Player)passenger;
               int player2Position = this.getRiderPosition(passenger);
               if (player == null || playerPosition > player2Position) {
                  player = player2;
                  playerPosition = player2Position;
               }
            }
         }
      }

      return player;
   }

   public int getSeatRaytrace(Entity player) {
      HitResult result = player.m_19907_((double)player.m_20270_(this), 0.0F, false);
      if (result != null) {
         Vec3 vec = result.m_82450_();
         return this.getClosestOpenSeat(vec);
      } else {
         return -1;
      }
   }

   public void m_20351_(Entity entity) {
      super.m_20351_(entity);
      this.dismountCooldown = 40 + this.f_19796_.m_188503_(40);
      if (entity != null && entity.m_20148_() != null) {
         for (int i = 0; i < this.riderPositionMap.length; i++) {
            if (this.riderPositionMap[i] != null && this.riderPositionMap[i].equals(entity.m_20148_())) {
               this.riderPositionMap[i] = null;
            }
         }
      }
   }

   public int getRiderPosition(Entity passenger) {
      int posit = -1;

      for (int i = 0; i < this.riderPositionMap.length; i++) {
         if (this.riderPositionMap[i] != null && passenger != null && passenger.m_20148_().equals(this.riderPositionMap[i])) {
            posit = i;
         }
      }

      return posit;
   }

   protected void m_20348_(Entity entity) {
      int rayTrace = this.getSeatRaytrace(entity);
      if (rayTrace >= 0 && rayTrace < 4) {
         if (this.riderPositionMap[rayTrace] != null && !this.f_19853_.f_46443_ && this.f_19853_ instanceof ServerLevel) {
            Entity kickOff = ((ServerLevel)this.f_19853_).m_8791_(this.riderPositionMap[rayTrace]);
            this.riderPositionMap[rayTrace] = null;
            if (kickOff != null) {
               kickOff.m_8127_();
            }
         }

         this.riderPositionMap[rayTrace] = entity.m_20148_();
         super.m_20348_(entity);
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("Obsidian", this.isObsidian());
      compound.m_128379_("HeadGear", this.hasHeadGear());
      compound.m_128379_("BodyGear", this.hasBodyGear());
      compound.m_128405_("ChillTime", this.getChillTime());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setObsidian(compound.m_128471_("Obsidian"));
      this.setHeadGear(compound.m_128471_("HeadGear"));
      this.setBodyGear(compound.m_128471_("BodyGear"));
      this.setChillTime(compound.m_128451_("ChillTime"));
   }

   public void m_7332_(Entity passenger) {
      if (this.m_20363_(passenger)) {
         int posit = this.getRiderPosition(passenger);
         if (posit >= 0 && posit <= 3) {
            EntityLaviathanPart seat = this.seatParts[posit];
            passenger.m_6034_(seat.m_20185_(), this.m_20186_() + this.m_6048_() + passenger.m_6049_(), seat.m_20189_());
         } else {
            passenger.m_8127_();
         }
      }
   }

   public double m_6048_() {
      float f = this.f_20925_;
      float f1 = this.f_20924_;
      float f2 = 0.0F;
      return (double)this.m_20206_() - 0.4F;
   }

   protected void m_5907_() {
      super.m_5907_();
      if (this.hasBodyGear() && !this.f_19853_.f_46443_) {
         this.m_19998_((ItemLike)AMItemRegistry.STRADDLE_SADDLE.get());
      }

      if (this.hasHeadGear() && !this.f_19853_.f_46443_) {
         this.m_19998_((ItemLike)AMItemRegistry.STRADDLE_HELMET.get());
      }

      this.setBodyGear(false);
      this.setHeadGear(false);
   }

   private void switchNavigator(boolean onLand) {
      if (onLand) {
         this.f_21365_ = new LookControl(this);
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = this.m_6037_(this.f_19853_);
         this.isLandNavigator = true;
      } else {
         this.f_21365_ = new SmoothSwimmingLookControl(this, 15);
         this.f_21342_ = new EntityLaviathan.MoveController(this);
         this.f_21344_ = new BoneSerpentPathNavigator(this, this.f_19853_);
         this.isLandNavigator = false;
      }
   }

   protected PathNavigation m_6037_(Level p_21480_) {
      return new GroundPathNavigatorWide(this, p_21480_);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new AnimalAIHerdPanic(this, 1.0) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && !EntityLaviathan.this.hasHeadGear();
         }
      });
      this.f_21345_.m_25352_(1, new BreedGoal(this, 1.0));
      this.f_21345_.m_25352_(2, new TameableAIRide(this, 1.0) {
         @Override
         public double modifyYPosition(double lookVecY) {
            float waterAt = (float)EntityLaviathan.this.getMaxFluidHeight();
            float half = EntityLaviathan.this.m_20206_() * 0.5F;
            if (waterAt > half) {
               return (double)Mth.m_14036_(waterAt - half, 0.0F, 0.5F);
            } else {
               return waterAt < half ? (double)Mth.m_14036_(waterAt - half, -0.5F, 0.0F) : 0.0;
            }
         }
      });
      this.f_21345_
         .m_25352_(3, new TemptGoal(this, 1.1, Ingredient.m_43929_(new ItemLike[]{Items.f_42542_, (ItemLike)AMItemRegistry.MOSQUITO_LARVA.get()}), false));
      this.f_21345_.m_25352_(4, new AnimalAIFindWaterLava(this, 1.0));
      this.f_21345_.m_25352_(5, new LaviathanAIRandomSwimming(this, 1.0, 22) {
         @Override
         public boolean m_8036_() {
            return super.m_8036_() && !EntityLaviathan.this.hasHeadGear() && !EntityLaviathan.this.hasBodyGear();
         }
      });
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
   }

   public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider) {
      return true;
   }

   protected float m_6041_() {
      return !this.shouldSwim() && !this.m_6046_() ? super.m_6041_() : 1.0F;
   }

   public float m_5610_(BlockPos pos, LevelReader worldIn) {
      if (!worldIn.m_8055_(pos).m_60819_().m_205070_(FluidTags.f_13131_) && !worldIn.m_8055_(pos).m_60819_().m_205070_(FluidTags.f_13132_)) {
         return this.m_20077_() ? -1.0F : 0.0F;
      } else {
         return 10.0F;
      }
   }

   public int m_6056_() {
      return 256;
   }

   public int m_5792_() {
      return 1;
   }

   public boolean m_7296_(int sizeIn) {
      return false;
   }

   public boolean m_6109_() {
      return false;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      boolean prev = super.m_6469_(source, amount);
      if (prev && source.m_7639_() != null) {
         double range = 15.0;
         int fleeTime = 100 + this.m_217043_().m_188503_(150);
         this.revengeCooldown = fleeTime;
         this.setChillTime(0);
      }

      return prev;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(OBSIDIAN, false);
      this.f_19804_.m_135372_(HAS_BODY_GEAR, false);
      this.f_19804_.m_135372_(HAS_HEAD_GEAR, false);
      this.f_19804_.m_135372_(HEAD_HEIGHT, 0.0F);
      this.f_19804_.m_135372_(HEAD_YROT, 0.0F);
      this.f_19804_.m_135372_(CHILL_TIME, 0);
      this.f_19804_.m_135372_(ATTACK_TICK, 0);
   }

   public void m_7023_(Vec3 travelVector) {
      boolean liquid = this.shouldSwim();
      if (this.m_6688_() != null && liquid && this.m_20184_().f_82480_ < 0.0) {
         this.m_20256_(this.m_20184_().m_82542_(1.0, 0.3F, 1.0));
      }

      if (this.m_6142_() && liquid) {
         this.m_19920_(this.m_6113_(), travelVector);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9));
         if (!this.isChilling()) {
         }
      } else {
         super.m_7023_(travelVector);
      }
   }

   public void m_20256_(Vec3 p_20257_) {
      super.m_20256_(p_20257_);
   }

   public int m_8132_() {
      return 50;
   }

   public int m_8085_() {
      return 50;
   }

   public int m_21529_() {
      return 4;
   }

   protected BodyRotationControl m_7560_() {
      return new EntityLaviathan.LaviathanBodyRotationControl(this);
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean m_6063_() {
      return false;
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public void m_8119_() {
      super.m_8119_();
      this.prevSwimProgress = this.swimProgress;
      this.prevBiteProgress = this.biteProgress;
      this.prevHeadHeight = this.getHeadHeight();
      this.f_20883_ = this.m_146908_();
      if (this.shouldSwim() && this.swimProgress < 5.0F) {
         this.swimProgress++;
      }

      if (!this.shouldSwim() && this.swimProgress > 0.0F) {
         this.swimProgress--;
      }

      if (this.isObsidian() && !this.hasObsidianArmor) {
         this.hasObsidianArmor = true;
         this.m_21051_(Attributes.f_22284_).m_22100_(30.0);
      }

      if (!this.isObsidian() && this.hasObsidianArmor) {
         this.hasObsidianArmor = false;
         this.m_21051_(Attributes.f_22284_).m_22100_(10.0);
      }

      if (!this.f_19853_.f_46443_) {
         if (!this.isObsidian() && this.m_20072_()) {
            if (this.conversionTime < 300) {
               this.conversionTime++;
            } else {
               this.setObsidian(true);
            }
         }

         if (this.shouldSwim()) {
            this.f_19789_ = 0.0F;
         }
      }

      float neckBase = 0.8F;
      if (!this.m_21525_()) {
         Vec3[] avector3d = new Vec3[this.allParts.length];

         for (int j = 0; j < this.allParts.length; j++) {
            this.allParts[j].collideWithNearbyEntities();
            avector3d[j] = new Vec3(this.allParts[j].m_20185_(), this.allParts[j].m_20186_(), this.allParts[j].m_20189_());
         }

         float yaw = this.m_146908_() * (float) (Math.PI / 180.0);
         float neckContraction = 2.0F * Math.abs(this.getHeadHeight() / 3.0F) + 0.5F * Math.abs(this.getHeadYaw(0.0F) / 50.0F);

         for (int l = 0; l < this.theEntireNeck.length; l++) {
            float f = (float)l / (float)this.theEntireNeck.length;
            float f1 = -(2.2F + (float)l - f * neckContraction);
            float f2 = Mth.m_14031_(yaw + (float)Math.toRadians((double)(f * this.getHeadYaw(0.0F)))) * (1.0F - Math.abs(this.m_146909_() / 90.0F));
            float f3 = Mth.m_14089_(yaw + (float)Math.toRadians((double)(f * this.getHeadYaw(0.0F)))) * (1.0F - Math.abs(this.m_146909_() / 90.0F));
            this.setPartPosition(
               this.theEntireNeck[l],
               (double)(f2 * f1),
               (double)neckBase + Math.sin((double)f * Math.PI * 0.5) * (double)(this.getHeadHeight() * 1.1F),
               (double)(-f3 * f1)
            );
         }

         this.setPartPosition(this.seat1, (double)(this.getXForPart(yaw, 145.0F) * 0.75F), 2.0, (double)(this.getZForPart(yaw, 145.0F) * 0.75F));
         this.setPartPosition(this.seat2, (double)(this.getXForPart(yaw, -145.0F) * 0.75F), 2.0, (double)(this.getZForPart(yaw, -145.0F) * 0.75F));
         this.setPartPosition(this.seat3, (double)(this.getXForPart(yaw, 35.0F) * 0.95F), 2.0, (double)(this.getZForPart(yaw, 35.0F) * 0.95F));
         this.setPartPosition(this.seat4, (double)(this.getXForPart(yaw, -35.0F) * 0.95F), 2.0, (double)(this.getZForPart(yaw, -35.0F) * 0.95F));
         if (this.f_19853_.f_46443_ && this.isChilling()) {
            if (!this.m_6162_()) {
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123762_,
                     this.m_20185_() + (double)(this.getXForPart(yaw, 158.0F) * 1.75F),
                     this.m_20227_(1.0),
                     this.m_20189_() + (double)(this.getZForPart(yaw, 158.0F) * 1.75F),
                     0.0,
                     this.f_19796_.m_188500_() / 5.0,
                     0.0
                  );
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123762_,
                     this.m_20185_() + (double)(this.getXForPart(yaw, -166.0F) * 1.48F),
                     this.m_20227_(1.0),
                     this.m_20189_() + (double)(this.getZForPart(yaw, -166.0F) * 1.48F),
                     0.0,
                     this.f_19796_.m_188500_() / 5.0,
                     0.0
                  );
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123762_,
                     this.m_20185_() + (double)(this.getXForPart(yaw, 14.0F) * 1.78F),
                     this.m_20227_(0.9),
                     this.m_20189_() + (double)(this.getZForPart(yaw, 14.0F) * 1.78F),
                     0.0,
                     this.f_19796_.m_188500_() / 5.0,
                     0.0
                  );
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123762_,
                     this.m_20185_() + (double)(this.getXForPart(yaw, -14.0F) * 1.6F),
                     this.m_20227_(1.1),
                     this.m_20189_() + (double)(this.getZForPart(yaw, -14.0F) * 1.6F),
                     0.0,
                     this.f_19796_.m_188500_() / 5.0,
                     0.0
                  );
            }

            this.f_19853_
               .m_7106_(
                  ParticleTypes.f_123762_,
                  this.headPart.m_20208_(0.6),
                  this.headPart.m_20227_(0.9),
                  this.headPart.m_20262_(0.6),
                  0.0,
                  this.f_19796_.m_188500_() / 5.0,
                  0.0
               );
         }

         for (int l = 0; l < this.allParts.length; l++) {
            this.allParts[l].f_19854_ = avector3d[l].f_82479_;
            this.allParts[l].f_19855_ = avector3d[l].f_82480_;
            this.allParts[l].f_19856_ = avector3d[l].f_82481_;
            this.allParts[l].f_19790_ = avector3d[l].f_82479_;
            this.allParts[l].f_19791_ = avector3d[l].f_82480_;
            this.allParts[l].f_19792_ = avector3d[l].f_82481_;
         }
      }

      if ((this.m_20077_() || this.m_20072_()) && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (!this.m_20077_() && !this.m_20072_() && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      if (!this.f_19853_.f_46443_) {
         if (this.getChillTime() > 0) {
            this.setChillTime(this.getChillTime() - 1);
         } else if (this.shouldSwim() && this.f_19796_.m_188503_(this.m_20160_() ? 200 : 2000) == 0 && this.revengeCooldown == 0) {
            this.setChillTime(100 + this.f_19796_.m_188503_(500));
         }

         if (this.revengeCooldown > 0) {
            this.revengeCooldown--;
         }

         if (this.headPeakCooldown > 0) {
            this.headPeakCooldown--;
         }

         if (this.revengeCooldown == 0 && this.m_21188_() != null) {
            this.m_6703_(null);
         }
      }

      if (!this.f_19853_.f_46443_) {
         if (this.m_6688_() == null && (this.getChillTime() > 0 || this.hasHeadGear() || this.dismountCooldown > 0)) {
            this.floatLaviathan();
         }

         if (!this.isChilling() && this.headPeakCooldown == 0) {
            float low = this.getLowHeadHeight();
            this.setHeadHeight(this.getHeadHeight() + (0.5F + (this.getLowHeadHeight() + this.getHighHeadHeight(low)) / 2.0F - this.getHeadHeight()) * 0.2F);
         } else if (this.getMaxFluidHeight() <= (double)(this.m_20206_() * 0.5F) && this.getMaxFluidHeight() >= (double)(this.m_20206_() * 0.25F)) {
            float mot = (float)this.m_20184_().m_82556_();
            this.setHeadHeight(Mth.m_14036_(this.getHeadHeight() + 0.1F - 0.2F * mot, 0.0F, 2.0F));
            this.headPeakCooldown = 5;
         }
      }

      if (this.isChilling()) {
         boolean keepChillin = false;
         boolean startBiting = false;

         for (EntityCrimsonMosquito entity : this.f_19853_.m_6443_(EntityCrimsonMosquito.class, this.m_20191_().m_82400_(30.0), HEALTHY_MOSQUITOES)) {
            entity.setLuringLaviathan(this.m_19879_());
            keepChillin = true;
         }

         if (keepChillin) {
            this.setChillTime(Math.max(20, this.getChillTime()));
         }

         for (EntityCrimsonMosquito entity : this.f_19853_.m_6443_(EntityCrimsonMosquito.class, this.headPart.m_20191_().m_82400_(1.0), HEALTHY_MOSQUITOES)) {
            startBiting = true;
            if (this.biteProgress == 5.0F) {
               entity.m_6469_(DamageSource.m_19370_(this), 1000.0F);
               entity.setShrink(true);
               this.setChillTime(0);
            }
         }

         if (startBiting && (Integer)this.f_19804_.m_135370_(ATTACK_TICK) <= 0 && this.biteProgress == 0.0F) {
            this.f_19804_.m_135381_(ATTACK_TICK, 7);
         }
      }

      if ((Integer)this.f_19804_.m_135370_(ATTACK_TICK) > 0) {
         this.f_19804_.m_135381_(ATTACK_TICK, (Integer)this.f_19804_.m_135370_(ATTACK_TICK) - 1);
      }

      if ((Integer)this.f_19804_.m_135370_(ATTACK_TICK) > 0 && this.biteProgress < 5.0F) {
         this.biteProgress++;
      }

      if ((Integer)this.f_19804_.m_135370_(ATTACK_TICK) <= 0 && this.biteProgress > 0.0F) {
         this.biteProgress--;
      }

      if (this.dismountCooldown > 0) {
         this.dismountCooldown--;
      }

      if (this.hasBodyGear()) {
         List<Entity> list = this.f_19853_.m_6249_(this, this.m_20191_().m_82377_(0.2F, -0.01F, 0.2F), EntitySelector.m_20421_(this));
         if (!list.isEmpty()) {
            boolean flag2 = !this.f_19853_.f_46443_;

            for (int j = 0; j < list.size(); j++) {
               Entity entityx = list.get(j);
               if (!entityx.m_20363_(this)) {
                  if (flag2
                     && !(entityx instanceof Player)
                     && !entityx.m_20159_()
                     && entityx.m_20205_() < this.m_20205_()
                     && !(entityx instanceof EntityLaviathan)
                     && !(entityx instanceof Enemy)
                     && entityx instanceof Mob
                     && this.m_7310_(entityx)
                     && !(entityx instanceof WaterAnimal)) {
                     entityx.m_20329_(this);
                  } else {
                     this.m_7334_(entityx);
                  }
               }
            }
         }
      }

      if (this.m_20160_() && !this.f_19853_.f_46443_ && this.f_19797_ % 40 == 0 && this.m_20197_().size() > 3) {
         for (Entity entityx : this.m_20197_()) {
            if (entityx instanceof ServerPlayer) {
               AMAdvancementTriggerRegistry.LAVIATHAN_FOUR_PASSENGERS.trigger((ServerPlayer)entityx);
            }
         }
      }
   }

   public void m_8024_() {
      super.m_8024_();
      this.breakBlock();
   }

   public void breakBlock() {
      if (this.blockBreakCounter > 0) {
         this.blockBreakCounter--;
      } else {
         boolean flag = false;
         if (!this.f_19853_.f_46443_ && this.m_20160_() && this.blockBreakCounter == 0 && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            for (int a = (int)Math.round(this.m_20191_().f_82288_); a <= (int)Math.round(this.m_20191_().f_82291_); a++) {
               for (int b = (int)Math.round(this.m_20191_().f_82289_) - 1; b <= (int)Math.round(this.m_20191_().f_82292_) + 1 && b <= 127; b++) {
                  for (int c = (int)Math.round(this.m_20191_().f_82290_); c <= (int)Math.round(this.m_20191_().f_82293_); c++) {
                     BlockPos pos = new BlockPos(a, b, c);
                     BlockState state = this.f_19853_.m_8055_(pos);
                     FluidState fluidState = this.f_19853_.m_6425_(pos);
                     Block block = state.m_60734_();
                     if (!state.m_60795_()
                        && !state.m_60808_(this.f_19853_, pos).m_83281_()
                        && state.m_204336_(AMTagRegistry.LAVIATHAN_BREAKABLES)
                        && fluidState.m_76178_()
                        && block != Blocks.f_50016_) {
                        this.m_20256_(this.m_20184_().m_82542_(0.6F, 1.0, 0.6F));
                        flag = true;
                        this.f_19853_.m_46961_(pos, true);
                     }
                  }
               }
            }
         }

         if (flag) {
            this.blockBreakCounter = 10;
         }
      }
   }

   public float getLowHeadHeight() {
      float checkAt = 0.0F;

      while (checkAt > -3.0F && !this.isHeadInWall((float)this.m_20186_() + checkAt) && !this.isHeadInLava((float)this.m_20186_() + checkAt)) {
         checkAt -= 0.2F;
      }

      return checkAt;
   }

   public float getHighHeadHeight(float low) {
      float checkAt = 3.0F;

      while (checkAt > 0.0F && (!this.isHeadInWall((float)this.m_20186_() + checkAt) || this.isHeadInLava((float)this.m_20186_() + checkAt))) {
         checkAt -= 0.2F;
      }

      return checkAt;
   }

   public boolean canStandOnFluid(Fluid p_230285_1_) {
      return false;
   }

   public boolean isHeadInWall(float offset) {
      if (this.f_19794_) {
         return false;
      } else {
         float f = 0.8F;
         Vec3 vec3 = new Vec3(this.headPart.m_20185_(), (double)offset, this.headPart.m_20189_());
         AABB axisalignedbb = AABB.m_165882_(vec3, (double)f, 1.0E-6, (double)f);
         return this.f_19853_
            .m_45556_(axisalignedbb)
            .filter(Predicate.not(BlockStateBase::m_60795_))
            .anyMatch(
               p_185969_ -> {
                  BlockPos blockpos = new BlockPos(vec3);
                  return p_185969_.m_60828_(this.f_19853_, blockpos)
                     && Shapes.m_83157_(
                        p_185969_.m_60812_(this.f_19853_, blockpos).m_83216_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_),
                        Shapes.m_83064_(axisalignedbb),
                        BooleanOp.f_82689_
                     );
               }
            );
      }
   }

   public boolean isHeadInLava(float offset) {
      if (this.f_19794_) {
         return false;
      } else {
         float f = 0.8F;
         BlockPos pos = new BlockPos(this.headPart.m_20185_(), (double)offset, this.headPart.m_20189_());
         return !this.f_19853_.m_6425_(pos).m_76178_();
      }
   }

   private void floatLaviathan() {
      if (this.shouldSwim()) {
         if (this.getMaxFluidHeight() >= (double)this.m_20206_()) {
            this.m_20334_(this.m_20184_().f_82479_, 0.12F, this.m_20184_().f_82481_);
         } else if (this.getMaxFluidHeight() >= (double)(this.m_20206_() * 0.5F)) {
            this.m_20334_(this.m_20184_().f_82479_, 0.08F, this.m_20184_().f_82481_);
         } else {
            this.m_20334_(this.m_20184_().f_82479_, 0.0, this.m_20184_().f_82481_);
         }
      }
   }

   public Vec3 m_7688_(LivingEntity livingEntity) {
      float expand = this.m_20205_() + 1.0F;
      Vec3[] avector3d = new Vec3[]{
         m_19903_((double)expand, (double)livingEntity.m_20205_(), livingEntity.m_146908_()),
         m_19903_((double)expand, (double)livingEntity.m_20205_(), livingEntity.m_146908_() - 22.5F),
         m_19903_((double)expand, (double)livingEntity.m_20205_(), livingEntity.m_146908_() + 22.5F),
         m_19903_((double)expand, (double)livingEntity.m_20205_(), livingEntity.m_146908_() - 45.0F),
         m_19903_((double)expand, (double)livingEntity.m_20205_(), livingEntity.m_146908_() + 45.0F)
      };
      Set<BlockPos> set = Sets.newLinkedHashSet();
      double d0 = this.m_20191_().f_82292_;
      double d1 = this.m_20191_().f_82289_ - 0.5;
      MutableBlockPos blockpos$mutable = new MutableBlockPos();

      for (Vec3 vector3d : avector3d) {
         blockpos$mutable.m_122169_(this.m_20185_() + vector3d.f_82479_, d0, this.m_20189_() + vector3d.f_82481_);

         for (double d2 = d0; d2 > d1; d2--) {
            set.add(blockpos$mutable.m_7949_());
            blockpos$mutable.m_122173_(Direction.DOWN);
         }
      }

      for (BlockPos blockpos : set) {
         if (!this.f_19853_.m_6425_(blockpos).m_205070_(FluidTags.f_13132_)) {
            double d3 = this.f_19853_.m_45573_(blockpos);
            if (DismountHelper.m_38439_(d3)) {
               Vec3 vector3d1 = Vec3.m_82514_(blockpos, d3);
               UnmodifiableIterator var15 = livingEntity.m_7431_().iterator();

               while (var15.hasNext()) {
                  Pose pose = (Pose)var15.next();
                  AABB axisalignedbb = livingEntity.m_21270_(pose);
                  if (DismountHelper.m_38456_(this.f_19853_, livingEntity, axisalignedbb.m_82383_(vector3d1))) {
                     livingEntity.m_20124_(pose);
                     return vector3d1;
                  }
               }
            }
         }
      }

      return new Vec3(this.m_20185_(), this.m_20191_().f_82292_, this.m_20189_());
   }

   public float getWaterLevelAbove() {
      AABB axisalignedbb = this.m_20191_();
      int i = Mth.m_14107_(axisalignedbb.f_82288_);
      int j = Mth.m_14165_(axisalignedbb.f_82291_);
      int k = Mth.m_14107_(axisalignedbb.f_82292_);
      int l = Mth.m_14165_(axisalignedbb.f_82292_);
      int i1 = Mth.m_14107_(axisalignedbb.f_82290_);
      int j1 = Mth.m_14165_(axisalignedbb.f_82293_);
      MutableBlockPos blockpos$mutable = new MutableBlockPos();

      label44:
      for (int k1 = k; k1 < l; k1++) {
         float f = 0.0F;

         for (int l1 = i; l1 < j; l1++) {
            for (int i2 = i1; i2 < j1; i2++) {
               blockpos$mutable.m_122178_(l1, k1, i2);
               FluidState fluidstate = this.f_19853_.m_6425_(blockpos$mutable);
               if (fluidstate.m_205070_(FluidTags.f_13131_) || fluidstate.m_205070_(FluidTags.f_13132_)) {
                  f = Math.max(f, fluidstate.m_76155_(this.f_19853_, blockpos$mutable));
               }

               if (f >= 1.0F) {
                  continue label44;
               }
            }
         }

         if (f < 1.0F) {
            return (float)blockpos$mutable.m_123342_() + f;
         }
      }

      return (float)(l + 1);
   }

   public boolean m_6914_(LevelReader worldIn) {
      return worldIn.m_45784_(this);
   }

   public boolean shouldSwim() {
      return this.getMaxFluidHeight() >= 0.1F || this.m_20077_() || this.m_20072_();
   }

   private float getXForPart(float yaw, float degree) {
      return Mth.m_14031_((float)((double)yaw + Math.toRadians((double)degree)));
   }

   private float getZForPart(float yaw, float degree) {
      return -Mth.m_14089_((float)((double)yaw + Math.toRadians((double)degree)));
   }

   public float getHeadHeight() {
      return Mth.m_14036_((Float)this.f_19804_.m_135370_(HEAD_HEIGHT), -3.0F, 3.0F);
   }

   public void setHeadHeight(float height) {
      this.f_19804_.m_135381_(HEAD_HEIGHT, Mth.m_14036_(height, -3.0F, 3.0F));
   }

   public float getHeadYRotPlecio() {
      return Mth.m_14177_((Float)this.f_19804_.m_135370_(HEAD_YROT));
   }

   public void setHeadYRotPlecio(float rot) {
      this.f_19804_.m_135381_(HEAD_YROT, rot);
   }

   public boolean isObsidian() {
      return (Boolean)this.f_19804_.m_135370_(OBSIDIAN);
   }

   public void setObsidian(boolean obsidian) {
      this.f_19804_.m_135381_(OBSIDIAN, obsidian);
   }

   public boolean hasHeadGear() {
      return (Boolean)this.f_19804_.m_135370_(HAS_HEAD_GEAR);
   }

   public void setHeadGear(boolean headGear) {
      this.f_19804_.m_135381_(HAS_HEAD_GEAR, headGear);
   }

   public boolean hasBodyGear() {
      return (Boolean)this.f_19804_.m_135370_(HAS_BODY_GEAR);
   }

   public void setBodyGear(boolean bodyGear) {
      this.f_19804_.m_135381_(HAS_BODY_GEAR, bodyGear);
   }

   public int getChillTime() {
      return (Integer)this.f_19804_.m_135370_(CHILL_TIME);
   }

   public void setChillTime(int chillTime) {
      this.f_19804_.m_135381_(CHILL_TIME, chillTime);
   }

   public float getHeadYaw(float interp) {
      float f;
      if (interp == 0.0F) {
         f = this.m_6080_() - this.f_20883_;
      } else {
         float yBodyRot1 = this.f_20884_ + (this.f_20883_ - this.f_20884_) * interp;
         float yHeadRot1 = this.f_20886_ + (this.m_6080_() - this.f_20886_) * interp;
         f = yHeadRot1 - yBodyRot1;
      }

      return Mth.m_14036_(Mth.m_14177_(f), -50.0F, 50.0F);
   }

   private void setPartPosition(EntityLaviathanPart part, double offsetX, double offsetY, double offsetZ) {
      part.m_6034_(
         this.m_20185_() + offsetX * (double)part.scale, this.m_20186_() + offsetY * (double)part.scale, this.m_20189_() + offsetZ * (double)part.scale
      );
   }

   public boolean isMultipartEntity() {
      return true;
   }

   public PartEntity<?>[] getParts() {
      return this.allParts;
   }

   public boolean attackEntityPartFrom(EntityLaviathanPart part, DamageSource source, float amount) {
      return this.m_6469_(source, amount);
   }

   @Override
   public boolean shouldEnterWater() {
      return !this.m_20160_();
   }

   @Override
   public boolean shouldLeaveWater() {
      return this.m_20160_();
   }

   @Override
   public boolean shouldStopMoving() {
      return this.m_20160_();
   }

   @Override
   public int getWaterSearchRange() {
      return 15;
   }

   private double getMaxFluidHeight() {
      return Math.max(this.m_204036_(FluidTags.f_13132_), this.m_204036_(FluidTags.f_13131_));
   }

   public boolean isChilling() {
      return this.getChillTime() > 0 && this.getMaxFluidHeight() <= (double)(this.m_20206_() * 0.5F);
   }

   public void scaleParts() {
      for (EntityLaviathanPart parts : this.allParts) {
         float prev = parts.scale;
         parts.scale = this.m_6162_() ? 0.5F : 1.0F;
         if (prev != parts.scale) {
            parts.m_6210_();
         }
      }
   }

   public void m_8107_() {
      super.m_8107_();
      this.scaleParts();
   }

   public Vec3 getLureMosquitoPos() {
      return new Vec3(this.headPart.m_20185_(), this.headPart.m_20227_(0.4F), this.headPart.m_20189_());
   }

   @Override
   public void onPanic() {
   }

   @Override
   public boolean canPanic() {
      return true;
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel serverWorld, AgeableMob ageableEntity) {
      return (AgeableMob)((EntityType)AMEntityRegistry.LAVIATHAN.get()).m_20615_(serverWorld);
   }

   private class LaviathanBodyRotationControl extends BodyRotationControl {
      private final EntityLaviathan laviathan;

      public LaviathanBodyRotationControl(EntityLaviathan laviathan) {
         super(laviathan);
         this.laviathan = laviathan;
      }

      public void m_8121_() {
      }
   }

   static class MoveController extends MoveControl {
      private final EntityLaviathan laviathan;

      public MoveController(EntityLaviathan dolphinIn) {
         super(dolphinIn);
         this.laviathan = dolphinIn;
      }

      public void m_8126_() {
         float speed = (float)(this.f_24978_ * 3.0 * this.laviathan.m_21133_(Attributes.f_22279_));
         if (!this.laviathan.m_20160_()) {
            if (this.laviathan.isChilling()) {
               speed *= 0.5F;
            } else if (this.laviathan.shouldSwim()) {
               this.laviathan.m_20256_(this.laviathan.m_20184_().m_82520_(0.0, -0.005, 0.0));
            }
         }

         if (this.f_24981_ == Operation.MOVE_TO && (!this.laviathan.m_21573_().m_26571_() || this.laviathan.m_6688_() != null)) {
            double lvt_1_1_ = this.f_24975_ - this.laviathan.m_20185_();
            double lvt_3_1_ = this.f_24976_ - this.laviathan.m_20186_();
            double lvt_5_1_ = this.f_24977_ - this.laviathan.m_20189_();
            double lvt_7_1_ = lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_;
            if (lvt_7_1_ < 2.5) {
               this.laviathan.m_21564_(0.0F);
            } else {
               float lvt_9_1_ = (float)(Mth.m_14136_(lvt_5_1_, lvt_1_1_) * 180.0F / (float)Math.PI) - 90.0F;
               this.laviathan.m_146922_(this.m_24991_(this.laviathan.m_146908_(), lvt_9_1_, 5.0F));
               this.laviathan.m_5616_(this.m_24991_(this.laviathan.m_6080_(), lvt_9_1_, 90.0F));
               BlockPos blockpos = this.f_24974_.m_20183_();
               BlockState blockstate = this.f_24974_.f_19853_.m_8055_(blockpos);
               VoxelShape voxelshape = blockstate.m_60812_(this.f_24974_.f_19853_, blockpos);
               if (lvt_3_1_ >= 0.0 && this.laviathan.f_19862_) {
                  this.laviathan.m_20256_(this.laviathan.m_20184_().m_82520_(0.0, 0.5, 0.0));
               } else {
                  this.laviathan.m_20256_(this.laviathan.m_20184_().m_82520_(0.0, (double)this.laviathan.m_6113_() * lvt_3_1_ * 0.6, 0.0));
               }

               if (this.laviathan.shouldSwim()) {
                  this.laviathan.m_7910_(speed * 0.03F);
                  float lvt_11_1_ = -(
                     (float)(Mth.m_14136_(lvt_3_1_, (double)Mth.m_14116_((float)(lvt_1_1_ * lvt_1_1_ + lvt_5_1_ * lvt_5_1_))) * 180.0F / (float)Math.PI)
                  );
                  lvt_11_1_ = Mth.m_14036_(Mth.m_14177_(lvt_11_1_), -85.0F, 85.0F);
                  this.laviathan.m_146926_(this.m_24991_(this.laviathan.m_146909_(), lvt_11_1_, 25.0F));
                  float lvt_12_1_ = Mth.m_14089_(this.laviathan.m_146909_() * (float) (Math.PI / 180.0));
                  float lvt_13_1_ = Mth.m_14031_(this.laviathan.m_146909_() * (float) (Math.PI / 180.0));
                  this.laviathan.f_20902_ = lvt_12_1_ * speed;
                  this.laviathan.f_20901_ = -lvt_13_1_ * speed;
               } else {
                  this.laviathan.m_7910_(speed * 0.1F);
               }
            }
         } else if (!this.laviathan.f_19853_.m_8055_(this.laviathan.m_20183_().m_7494_()).m_60819_().m_76178_() && this.laviathan.getChillTime() <= 0) {
            this.laviathan.m_20256_(this.laviathan.m_20184_().m_82520_(0.0, -0.05, 0.0));
         }
      }
   }
}
