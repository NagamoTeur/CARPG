package com.bobmowzie.mowziesmobs.server.entity.grottol;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.server.advancement.AdvancementHandler;
import com.bobmowzie.mowziesmobs.server.ai.EntityAIGrottolFindMinecart;
import com.bobmowzie.mowziesmobs.server.ai.MMAIAvoidEntity;
import com.bobmowzie.mowziesmobs.server.ai.MMEntityMoveHelper;
import com.bobmowzie.mowziesmobs.server.ai.MMPathNavigateGround;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDieAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationTakeDamage;
import com.bobmowzie.mowziesmobs.server.ai.animation.SimpleAnimationAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.entity.grottol.ai.EntityAIGrottolIdle;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.bobmowzie.mowziesmobs.server.tag.TagHandler;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class EntityGrottol extends MowzieLLibraryEntity {
   public static final Animation DIE_ANIMATION = Animation.create(73);
   public static final Animation HURT_ANIMATION = Animation.create(10);
   public static final Animation IDLE_ANIMATION = EntityAIGrottolIdle.animation();
   public static final Animation BURROW_ANIMATION = Animation.create(20);
   private static final Animation[] ANIMATIONS = new Animation[]{DIE_ANIMATION, HURT_ANIMATION, IDLE_ANIMATION, BURROW_ANIMATION};
   public int fleeTime = 0;
   private int timeSinceFlee = 50;
   private int timeSinceMinecart = 0;
   private final BlackPinkRailLine reader = BlackPinkRailLine.create();
   private EntityGrottol.EnumDeathType death = EntityGrottol.EnumDeathType.NORMAL;
   private int timeSinceDeflectSound = 0;
   private static final EntityDataAccessor<Boolean> DEEPSLATE = SynchedEntityData.m_135353_(EntityGrottol.class, EntityDataSerializers.f_135035_);

   public EntityGrottol(EntityType<? extends EntityGrottol> type, Level world) {
      super(type, world);
      this.f_21364_ = 15;
      this.f_19793_ = 1.15F;
      this.f_21342_ = new MMEntityMoveHelper(this, 45.0F);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.m_21441_(BlockPathTypes.DANGER_OTHER, 1.0F);
      this.m_21441_(BlockPathTypes.WATER, 3.0F);
      this.m_21441_(BlockPathTypes.WATER_BORDER, 3.0F);
      this.m_21441_(BlockPathTypes.LAVA, 1.0F);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 1.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 1.0F);
      this.m_21441_(BlockPathTypes.DANGER_CACTUS, 1.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_CACTUS, 1.0F);
      this.f_21345_.m_25352_(3, new FloatGoal(this));
      this.f_21345_.m_25352_(4, new RandomStrollGoal(this, 0.3));
      this.f_21345_.m_25352_(1, new EntityAIGrottolFindMinecart(this));
      this.f_21345_.m_25352_(2, new MMAIAvoidEntity<EntityGrottol, Player>(this, Player.class, 16.0F, 0.5, 0.7) {
         private int fleeCheckCounter = 0;

         @Override
         protected void onSafe() {
            this.fleeCheckCounter = 0;
         }

         @Override
         protected void onPathNotFound() {
            if (this.fleeCheckCounter < 4) {
               this.fleeCheckCounter++;
            } else if (EntityGrottol.this.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, EntityGrottol.BURROW_ANIMATION);
            }
         }

         @Override
         public void m_8037_() {
            super.m_8037_();
            this.entity.fleeTime++;
         }

         @Override
         public void m_8041_() {
            super.m_8041_();
            this.entity.timeSinceFlee = 0;
            this.fleeCheckCounter = 0;
         }
      });
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(1, new AnimationTakeDamage<>(this));
      this.f_21345_.m_25352_(1, new AnimationDieAI<>(this));
      this.f_21345_.m_25352_(5, new EntityAIGrottolIdle(this));
      this.f_21345_.m_25352_(2, new SimpleAnimationAI<>(this, BURROW_ANIMATION, false));
   }

   public int m_6056_() {
      return 256;
   }

   public int m_5792_() {
      return 1;
   }

   protected PathNavigation m_6037_(Level world) {
      return new MMPathNavigateGround(this, world);
   }

   public boolean m_6063_() {
      return false;
   }

   protected float m_6108_() {
      return 1.0F;
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean m_6051_() {
      return false;
   }

   public boolean m_6142_() {
      return super.m_6142_() && !this.isInMinecart();
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().m_22268_(Attributes.f_22276_, 20.0).m_22268_(Attributes.f_22278_, 1.0);
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.GROTTOL.spawnConfig;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(DEEPSLATE, false);
   }

   public boolean m_5545_(LevelAccessor world, MobSpawnType reason) {
      return this.getEntitiesNearby(EntityGrottol.class, 20.0, 20.0, 20.0, 20.0).isEmpty() && super.m_5545_(world, reason);
   }

   public boolean m_7313_(Entity entity) {
      if (entity instanceof Player player && EnchantmentHelper.m_44843_(Enchantments.f_44985_, player.m_21205_()) > 0) {
         if (!this.f_19853_.f_46443_ && this.m_6084_()) {
            this.m_5552_(ItemHandler.CAPTURED_GROTTOL.create(this), 0.0F);
            BlockState state = Blocks.f_50069_.m_49966_();
            SoundType sound = state.m_60734_().getSoundType(state, this.f_19853_, this.m_20183_(), entity);
            this.f_19853_
               .m_6263_(
                  null,
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  sound.m_56775_(),
                  this.m_5720_(),
                  (sound.m_56773_() + 1.0F) / 2.0F,
                  sound.m_56774_() * 0.8F
               );
            if (this.f_19853_ instanceof ServerLevel) {
               ((ServerLevel)this.f_19853_)
                  .m_8767_(
                     new BlockParticleOption(ParticleTypes.f_123794_, state),
                     this.m_20185_(),
                     this.m_20186_() + (double)this.m_20206_() / 2.0,
                     this.m_20189_(),
                     32,
                     (double)(this.m_20205_() / 4.0F),
                     (double)(this.m_20206_() / 4.0F),
                     (double)(this.m_20205_() / 4.0F),
                     0.05
                  );
            }

            this.m_146870_();
            if (player instanceof ServerPlayer) {
               AdvancementHandler.GROTTOL_KILL_SILK_TOUCH_TRIGGER.trigger((ServerPlayer)player);
            }
         }

         return true;
      }

      return super.m_7313_(entity);
   }

   @Override
   public boolean m_6469_(DamageSource source, float amount) {
      Entity entity = source.m_7639_();
      if (entity instanceof Player && !source.m_19360_()) {
         Player player = (Player)entity;
         if (!player.m_21205_().m_41735_(Blocks.f_50089_.m_49966_()) && !player.m_21205_().m_204117_(TagHandler.CAN_HIT_GROTTOL)) {
            if (this.timeSinceDeflectSound >= 5) {
               this.timeSinceDeflectSound = 0;
               this.m_5496_((SoundEvent)MMSounds.ENTITY_GROTTOL_UNDAMAGED.get(), 0.4F, 2.0F);
            }

            return false;
         } else {
            if (EnchantmentHelper.m_44843_(Enchantments.f_44987_, player.m_21205_()) > 0) {
               this.death = EntityGrottol.EnumDeathType.FORTUNE_PICKAXE;
               if (player instanceof ServerPlayer) {
                  AdvancementHandler.GROTTOL_KILL_FORTUNE_TRIGGER.trigger((ServerPlayer)player);
               }
            } else {
               this.death = EntityGrottol.EnumDeathType.PICKAXE;
            }

            return super.m_6469_(source, this.m_21223_());
         }
      } else {
         return entity instanceof Mob ? false : super.m_6469_(source, amount);
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         Entity e = this.m_20202_();
         if (isMinecart(e)) {
            AbstractMinecart minecart = (AbstractMinecart)e;
            this.reader.accept(minecart);
            boolean onRail = isBlockRail(this.f_19853_.m_8055_(e.m_20183_()).m_60734_());
            if ((this.timeSinceMinecart <= 3 || !(e.m_20184_().m_82553_() < 0.001)) && onRail) {
               if (onRail) {
                  if (minecart.m_20184_().m_82553_() < 0.001) {
                     minecart.m_20256_(minecart.m_20156_().m_82490_(2.7));
                  } else {
                     minecart.m_20256_(minecart.m_20184_().m_82541_().m_82490_(2.7));
                  }

                  this.timeSinceMinecart++;
               }
            } else {
               minecart.m_20153_();
               this.timeSinceMinecart = 0;
            }
         }
      }

      if (this.f_19853_.f_46443_ && this.m_6084_() && this.f_19796_.m_188503_(15) == 0) {
         double x = this.m_20185_() + (double)(0.5F * (2.0F * this.f_19796_.m_188501_() - 1.0F));
         double y = this.m_20186_() + 0.8F + (double)(0.3F * (2.0F * this.f_19796_.m_188501_() - 1.0F));
         double z = this.m_20189_() + (double)(0.5F * (2.0F * this.f_19796_.m_188501_() - 1.0F));
         if (this.isBlackPinkInYourArea()) {
            this.f_19853_.m_7106_(ParticleTypes.f_123758_, x, y, z, this.f_19796_.m_188500_() / 2.0, 0.0, 0.0);
         } else {
            this.f_19853_.m_7106_((ParticleOptions)ParticleHandler.SPARKLE.get(), x, y, z, 0.0, 0.0, 0.0);
         }
      }

      float moveX = (float)(this.m_20185_() - this.f_19854_);
      float moveZ = (float)(this.m_20189_() - this.f_19856_);
      float speed = Mth.m_14116_(moveX * moveX + moveZ * moveZ);
      if (this.frame % 6 == 0 && (double)speed > 0.05) {
         this.m_5496_((SoundEvent)MMSounds.ENTITY_GROTTOL_STEP.get(), 1.0F, 1.8F);
      }

      if (this.timeSinceFlee < 50) {
         this.timeSinceFlee++;
      } else {
         this.fleeTime = 0;
      }

      if (this.timeSinceDeflectSound < 5) {
         this.timeSinceDeflectSound++;
      }

      if (!this.f_19853_.f_46443_
         && this.fleeTime >= 55
         && this.getAnimation() == NO_ANIMATION
         && !this.m_21525_()
         && !this.m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         BlockState blockBeneath = this.f_19853_.m_8055_(this.m_20183_().m_7495_());
         if (this.isBlockDiggable(blockBeneath)) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, BURROW_ANIMATION);
         }
      }

      if (!this.f_19853_.f_46443_ && this.getAnimation() == BURROW_ANIMATION && this.getAnimationTick() % 4 == 3) {
         this.m_5496_((SoundEvent)MMSounds.ENTITY_GROTTOL_BURROW.get(), 1.0F, 0.8F + this.f_19796_.m_188501_() * 0.4F);
         BlockState blockBeneath = this.f_19853_.m_8055_(this.m_20183_().m_7495_());
         Material mat = blockBeneath.m_60767_();
         if (mat == Material.f_76314_ || mat == Material.f_76317_ || mat == Material.f_76313_ || mat == Material.f_76278_ || mat == Material.f_76315_) {
            Vec3 pos = new Vec3(0.5, 0.05, 0.0).m_82524_((float)Math.toRadians((double)(-this.f_20883_ - 90.0F)));
            if (this.f_19853_ instanceof ServerLevel) {
               ((ServerLevel)this.f_19853_)
                  .m_8767_(
                     new BlockParticleOption(ParticleTypes.f_123794_, blockBeneath),
                     this.m_20185_() + pos.f_82479_,
                     this.m_20186_() + pos.f_82480_,
                     this.m_20189_() + pos.f_82481_,
                     8,
                     0.25,
                     0.025,
                     0.25,
                     0.1
                  );
            }
         }
      }
   }

   @Override
   protected void onAnimationFinish(Animation animation) {
      if (animation == BURROW_ANIMATION) {
         this.m_146870_();
      }
   }

   public static boolean isBlockRail(Block block) {
      return block == Blocks.f_50156_ || block == Blocks.f_50285_ || block == Blocks.f_50030_ || block == Blocks.f_50031_;
   }

   private boolean isBlackPinkInYourArea() {
      Entity e = this.m_20202_();
      return false;
   }

   public boolean isInMinecart() {
      return isMinecart(this.m_20202_());
   }

   private static boolean isMinecart(Entity entity) {
      return entity instanceof Minecart;
   }

   protected void m_7324_(Entity entity) {
      if (!isMinecart(entity)) {
         super.m_7324_(entity);
      }
   }

   public boolean m_7998_(Entity entity, boolean force) {
      return super.m_7998_(entity, force);
   }

   public void m_8127_() {
      super.m_8127_();
   }

   protected SoundEvent m_5592_() {
      this.m_5496_((SoundEvent)MMSounds.ENTITY_GROTTOL_DIE.get(), 1.0F, 1.3F);
      return null;
   }

   @Override
   public Animation getDeathAnimation() {
      return DIE_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return HURT_ANIMATION;
   }

   @Override
   public Animation[] getAnimations() {
      return ANIMATIONS;
   }

   public EntityGrottol.EnumDeathType getDeathType() {
      return this.death;
   }

   protected ResourceLocation m_7582_() {
      return LootTableHandler.GROTTOL;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.GROTTOL.combatConfig;
   }

   public boolean isBlockDiggable(BlockState blockState) {
      Material mat = blockState.m_60767_();
      return mat != Material.f_76315_ && mat != Material.f_76314_ && mat != Material.f_76278_ && mat != Material.f_76313_ && mat != Material.f_76317_
         ? false
         : blockState.m_60734_() != Blocks.f_50335_
            && blockState.m_60734_() != Blocks.f_50451_
            && !(blockState.m_60734_() instanceof FenceBlock)
            && blockState.m_60734_() != Blocks.f_50085_
            && blockState.m_60734_() != Blocks.f_50453_
            && blockState.m_60734_() != Blocks.f_50201_
            && blockState.m_60734_() != Blocks.f_50258_
            && blockState.m_60734_() != Blocks.f_50265_
            && blockState.m_60734_() != Blocks.f_50374_
            && blockState.m_60734_() != Blocks.f_50332_
            && !blockState.m_155947_();
   }

   public boolean getDeepslate() {
      return (Boolean)this.m_20088_().m_135370_(DEEPSLATE);
   }

   public void setDeepslate(boolean deepslate) {
      this.m_20088_().m_135381_(DEEPSLATE, deepslate);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("deepslate", this.getDeepslate());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setDeepslate(compound.m_128471_("deepslate"));
   }

   @Override
   public SpawnGroupData m_6518_(
      ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag
   ) {
      if (this.m_20186_() < 8.0 && reason != MobSpawnType.MOB_SUMMONED) {
         this.setDeepslate(true);
      }

      return super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   public static enum EnumDeathType {
      NORMAL,
      PICKAXE,
      FORTUNE_PICKAXE;
   }
}
