package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AnimationGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackMoveGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.partentity.Cm_Part_Entity;
import com.github.L_Ender.cataclysm.entity.partentity.Old_Netherite_Monstrosity_Part;
import com.github.L_Ender.cataclysm.entity.projectile.Lava_Bomb_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.ForgeEventFactory;

public class Old_Netherite_Monstrosity_Entity extends LLibrary_Boss_Monster implements Enemy {
   private final CMBossInfoServer bossInfo = new CMBossInfoServer(this.m_5446_(), BossBarColor.RED, false, 0);
   public int frame;
   public static final Animation MONSTROSITY_EARTHQUAKE = Animation.create(75);
   public static final Animation MONSTROSITY_CHARGE = Animation.create(82);
   public static final Animation MONSTROSITY_ERUPTIONATTACK = Animation.create(55);
   public static final Animation MONSTROSITY_EARTHQUAKE2 = Animation.create(65);
   public static final Animation MONSTROSITY_EARTHQUAKE3 = Animation.create(70);
   public static final Animation MONSTROSITY_BERSERK = Animation.create(80);
   public static final Animation MONSTROSITY_DEATH = Animation.create(185);
   public final Old_Netherite_Monstrosity_Part headPart;
   public final Old_Netherite_Monstrosity_Part[] monstrosityParts;
   private static final EntityDataAccessor<Boolean> IS_BERSERK = SynchedEntityData.m_135353_(
      Old_Netherite_Monstrosity_Entity.class, EntityDataSerializers.f_135035_
   );
   private static final EntityDataAccessor<Boolean> IS_AWAKEN = SynchedEntityData.m_135353_(
      Old_Netherite_Monstrosity_Entity.class, EntityDataSerializers.f_135035_
   );
   private int lavabombmagazine = CMConfig.Lavabombmagazine;
   public boolean Blocking = CMConfig.NetheritemonstrosityBodyBloking;
   public float deactivateProgress;
   private int blockBreakCounter;
   public float prevdeactivateProgress;
   public static final int NATURE_HEAL_COOLDOWN = 200;
   private int timeWithoutTarget;

   public Old_Netherite_Monstrosity_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 300;
      this.headPart = new Old_Netherite_Monstrosity_Part(this, 1.6F, 2.5F);
      this.monstrosityParts = new Old_Netherite_Monstrosity_Part[]{this.headPart};
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.LAVA, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0F);
      setConfigattribute(this, CMConfig.MonstrosityHealthMultiplier, CMConfig.MonstrosityDamageMultiplier);
   }

   public float getStepHeight() {
      return 1.75F;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         MONSTROSITY_BERSERK,
         MONSTROSITY_EARTHQUAKE,
         MONSTROSITY_CHARGE,
         MONSTROSITY_EARTHQUAKE2,
         MONSTROSITY_EARTHQUAKE3,
         MONSTROSITY_ERUPTIONATTACK,
         MONSTROSITY_DEATH
      };
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new Old_Netherite_Monstrosity_Entity.BerserkGoal(this, MONSTROSITY_BERSERK));
      this.f_21345_.m_25352_(0, new Old_Netherite_Monstrosity_Entity.AwakenGoal());
      this.f_21345_.m_25352_(1, new Old_Netherite_Monstrosity_Entity.HealGoal(this, MONSTROSITY_CHARGE));
      this.f_21345_.m_25352_(1, new Old_Netherite_Monstrosity_Entity.ShootGoal(this, MONSTROSITY_ERUPTIONATTACK));
      this.f_21345_.m_25352_(1, new Old_Netherite_Monstrosity_Entity.EarthQuakeGoal(this));
      this.f_21345_.m_25352_(2, new AttackMoveGoal(this, true, 1.0));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
   }

   public static Builder netherite_monstrosity() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 50.0)
         .m_22268_(Attributes.f_22279_, 0.25)
         .m_22268_(Attributes.f_22281_, 21.0)
         .m_22268_(Attributes.f_22276_, 500.0)
         .m_22268_(Attributes.f_22284_, 10.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(IS_BERSERK, false);
      this.f_19804_.m_135372_(IS_AWAKEN, false);
   }

   private static Animation getRandomAttack(RandomSource rand) {
      switch (rand.m_188503_(3)) {
         case 0:
            return MONSTROSITY_EARTHQUAKE;
         case 1:
            return MONSTROSITY_EARTHQUAKE2;
         case 2:
            return MONSTROSITY_EARTHQUAKE3;
         default:
            return MONSTROSITY_EARTHQUAKE;
      }
   }

   public boolean m_203441_(FluidState p_230285_1_) {
      return p_230285_1_.m_205070_(FluidTags.f_13132_);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("is_Berserk", this.getIsBerserk());
      compound.m_128379_("is_Awaken", this.getIsAwaken());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setIsBerserk(compound.m_128471_("is_Berserk"));
      this.setIsAwaken(compound.m_128471_("is_Awaken"));
      if (this.m_8077_()) {
         this.bossInfo.m_6456_(this.m_5446_());
      }
   }

   public void setIsBerserk(boolean isBerserk) {
      this.f_19804_.m_135381_(IS_BERSERK, isBerserk);
   }

   public boolean getIsBerserk() {
      return (Boolean)this.f_19804_.m_135370_(IS_BERSERK);
   }

   public void setIsAwaken(boolean isAwaken) {
      this.f_19804_.m_135381_(IS_AWAKEN, isAwaken);
   }

   public boolean getIsAwaken() {
      return (Boolean)this.f_19804_.m_135370_(IS_AWAKEN);
   }

   public void m_6593_(@Nullable Component name) {
      super.m_6593_(name);
      this.bossInfo.m_6456_(this.m_5446_());
   }

   protected int m_7302_(int air) {
      return air;
   }

   public boolean attackEntityFromPart(Old_Netherite_Monstrosity_Part netherite_monstrosity_part, DamageSource source, float amount) {
      return this.m_6469_(source, amount);
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      if (this.getAnimation() == MONSTROSITY_BERSERK && !source.m_19378_()) {
         return false;
      } else {
         double range = this.calculateRange(source);
         if (range > CMConfig.MonstrosityLongRangelimit * CMConfig.MonstrosityLongRangelimit && !source.m_19378_()) {
            return false;
         } else {
            Entity entity = source.m_7640_();
            if (entity instanceof AbstractGolem) {
               damage = (float)((double)damage * 0.5);
            }

            boolean attack = super.m_6469_(source, damage);
            if (attack && !this.getIsAwaken()) {
               this.setIsAwaken(true);
            }

            return attack;
         }
      }
   }

   @Override
   public float DamageCap() {
      return (float)CMConfig.MonstrosityDamageCap;
   }

   public boolean m_5829_() {
      return this.m_6084_() && this.Blocking;
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   private void floatStrider() {
      if (this.m_20077_()) {
         CollisionContext lvt_1_1_ = CollisionContext.m_82750_(this);
         if (lvt_1_1_.m_6513_(LiquidBlock.f_54690_, this.m_20183_().m_7495_(), true)
            && !this.f_19853_.m_6425_(this.m_20183_().m_7494_()).m_205070_(FluidTags.f_13132_)) {
            this.m_6853_(true);
         } else {
            this.m_20256_(this.m_20184_().m_82490_(0.5).m_82520_(0.0, (double)this.f_19796_.m_188501_() * 0.5, 0.0));
         }
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.floatStrider();
      this.frame++;
      float moveX = (float)(this.m_20185_() - this.f_19854_);
      float moveZ = (float)(this.m_20189_() - this.f_19856_);
      float speed = Mth.m_14116_(moveX * moveX + moveZ * moveZ);
      if (!this.m_20067_() && this.frame % 25 == 1 && (double)speed > 0.05 && this.getIsAwaken()) {
         this.m_5496_((SoundEvent)ModSounds.MONSTROSITYSTEP.get(), 1.0F, 1.0F);
      }

      this.bossInfo.m_8321_(this.getIsAwaken());
      this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
      this.BlockBreaking();
      this.prevdeactivateProgress = this.deactivateProgress;
      if (!this.getIsAwaken() && this.deactivateProgress < 40.0F) {
         this.deactivateProgress = 40.0F;
      }

      if (this.getIsAwaken() && this.deactivateProgress > 0.0F) {
         this.deactivateProgress--;
         if (this.deactivateProgress == 20.0F && this.m_21223_() > 0.0F) {
            this.m_5496_((SoundEvent)ModSounds.MONSTROSITYAWAKEN.get(), 10.0F, 1.0F);
         }
      }

      LivingEntity target = this.m_5448_();
      if (!this.f_19853_.f_46443_) {
         if (this.timeWithoutTarget > 0) {
            this.timeWithoutTarget--;
         }

         if (target != null) {
            this.timeWithoutTarget = 200;
         }

         if (this.getAnimation() == NO_ANIMATION
            && this.timeWithoutTarget <= 0
            && !this.m_21525_()
            && CMConfig.MonstrosityNatureHealing > 0.0
            && this.f_19797_ % 20 == 0) {
            this.m_5634_((float)CMConfig.MonstrosityNatureHealing);
         }
      }

      if (this.getAnimation() == MONSTROSITY_EARTHQUAKE && this.getAnimationTick() == 34
         || this.getAnimation() == MONSTROSITY_EARTHQUAKE2 && this.getAnimationTick() == 24
         || this.getAnimation() == MONSTROSITY_EARTHQUAKE3 && this.getAnimationTick() == 29) {
         this.EarthQuake();
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
         this.Makeparticle(4.75F, 2.5F);
         this.Makeparticle(4.75F, -2.5F);
      }

      if (this.deactivateProgress == 0.0F && this.m_6084_()) {
         if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && this.isBerserk() && !this.getIsBerserk()) {
            this.setAnimation(MONSTROSITY_BERSERK);
         } else if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && target != null && target.m_6084_()) {
            if (this.m_20077_() && this.lavabombmagazine == 0) {
               this.setAnimation(MONSTROSITY_CHARGE);
            } else if ((
                  this.m_21525_()
                     || this.getAnimation() != NO_ANIMATION
                     || !(this.m_20270_(target) >= 18.0F)
                     || !(this.m_20270_(target) < 40.0F)
                     || this.lavabombmagazine <= 0
                     || this.f_19796_.m_188503_(48) != 0
               )
               && (
                  !(this.m_20270_(target) > 4.75F)
                     || !(this.f_19796_.m_188501_() * 100.0F < 0.3F)
                     || !(this.m_20270_(target) < 18.0F)
                     || this.lavabombmagazine <= 0
               )) {
               if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && this.m_20270_(target) < 6.0F) {
                  Animation animation = getRandomAttack(this.f_19796_);
                  if (this.isBerserk()) {
                     this.setAnimation(MONSTROSITY_EARTHQUAKE2);
                  } else {
                     this.setAnimation(animation);
                  }
               }
            } else {
               this.setAnimation(MONSTROSITY_ERUPTIONATTACK);
            }
         }
      }

      if (this.getAnimation() == MONSTROSITY_CHARGE) {
         if (this.getAnimationTick() == 34) {
            this.lavabombmagazine = CMConfig.Lavabombmagazine;
            this.doAbsorptionEffects(4, 1, 4);
            this.m_5496_(SoundEvents.f_11783_, 6.0F, 0.5F);
            this.m_5634_(15.0F * (float)CMConfig.MonstrosityHealingMultiplier);
         }

         if (this.getAnimationTick() == 44) {
            this.doAbsorptionEffects(8, 2, 8);
            this.m_5496_(SoundEvents.f_11783_, 6.0F, 0.5F);
            this.m_5634_(15.0F * (float)CMConfig.MonstrosityHealingMultiplier);
         }

         if (this.getAnimationTick() == 54) {
            this.doAbsorptionEffects(16, 4, 16);
            this.m_5496_(SoundEvents.f_11783_, 6.0F, 0.5F);
            this.m_5634_(15.0F * (float)CMConfig.MonstrosityHealingMultiplier);
         }
      }

      if (this.getAnimation() == MONSTROSITY_BERSERK) {
         this.setIsBerserk(true);
         if (this.getAnimationTick() == 20) {
            this.m_5496_((SoundEvent)ModSounds.MONSTROSITYGROWL.get(), 3.0F, 1.0F);
         }

         if (this.getAnimationTick() == 29) {
            this.berserkBlockBreaking(8, 8, 8);
            this.EarthQuake();
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
            this.Makeparticle(4.0F, 3.5F);
            this.Makeparticle(4.0F, -3.5F);
         }
      }

      if (!this.f_19853_.f_46443_ && !this.getIsAwaken() && target != null) {
         this.setIsAwaken(true);
      }

      if (!this.m_21525_()) {
         float f17 = this.m_146908_() * (float) (Math.PI / 180.0);
         float pitch = this.m_146909_() * (float) (Math.PI / 180.0);
         float f3 = Mth.m_14031_(f17) * (1.0F - Math.abs(this.m_146909_() / 90.0F));
         float f18 = Mth.m_14089_(f17) * (1.0F - Math.abs(this.m_146909_() / 90.0F));
         Vec3[] avector3d = new Vec3[this.monstrosityParts.length];

         for (int j = 0; j < this.monstrosityParts.length; j++) {
            avector3d[j] = new Vec3(this.monstrosityParts[j].m_20185_(), this.monstrosityParts[j].m_20186_(), this.monstrosityParts[j].m_20189_());
         }

         this.setPartPosition(this.headPart, (double)(f3 * -1.65F), (double)(pitch + 3.0F), (double)(-f18 * -1.65F));

         for (int l = 0; l < this.monstrosityParts.length; l++) {
            this.monstrosityParts[l].f_19854_ = avector3d[l].f_82479_;
            this.monstrosityParts[l].f_19855_ = avector3d[l].f_82480_;
            this.monstrosityParts[l].f_19856_ = avector3d[l].f_82481_;
            this.monstrosityParts[l].f_19790_ = avector3d[l].f_82479_;
            this.monstrosityParts[l].f_19791_ = avector3d[l].f_82480_;
            this.monstrosityParts[l].f_19792_ = avector3d[l].f_82481_;
         }
      }
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !(entityIn instanceof Old_Netherite_Monstrosity_Entity) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   @Override
   protected void onDeathAIUpdate() {
      super.onDeathAIUpdate();
      this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
      if (this.f_20919_ == 68) {
         this.m_5496_((SoundEvent)ModSounds.MONSTROSITYLAND.get(), 1.0F, 1.0F);
      }
   }

   private void doAbsorptionEffects(int x, int y, int z) {
      int MthX = Mth.m_14107_(this.m_20185_());
      int MthY = Mth.m_14107_(this.m_20186_());
      int MthZ = Mth.m_14107_(this.m_20189_());

      for (int k2 = -x; k2 <= x; k2++) {
         for (int l2 = -z; l2 <= z; l2++) {
            for (int j = -y; j <= y; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               this.doAbsorptionEffect(blockpos);
            }
         }
      }
   }

   private void doAbsorptionEffect(BlockPos pos) {
      BlockState state = this.f_19853_.m_8055_(pos);
      if (!this.f_19853_.f_46443_ && state.m_60713_(Blocks.f_49991_)) {
         this.f_19853_.m_46597_(pos, Blocks.f_50016_.m_49966_());
      }
   }

   private void EarthQuake() {
      this.m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(7.0))) {
         if (!this.m_7307_(entity) && !(entity instanceof Old_Netherite_Monstrosity_Entity) && entity != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(
               damagesource,
               (float)(
                  (double)((float)this.m_21133_(Attributes.f_22281_))
                     + Math.min(this.m_21133_(Attributes.f_22281_), (double)entity.m_21233_() * CMConfig.MonstrositysHpdamage)
               )
            );
            if (entity.m_21275_(damagesource) && entity instanceof Player player) {
               this.disableShield(player, 120);
            }

            if (flag) {
               this.launch(entity, true);
               if (this.getIsBerserk()) {
                  entity.m_20254_(6);
               }
            }
         }
      }
   }

   private void Makeparticle(float vec, float math) {
      if (this.f_19853_.f_46443_) {
         float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
         float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
         double theta = (double)this.f_20883_ * (Math.PI / 180.0);
         double vecX = Math.cos(++theta);
         double vecZ = Math.sin(theta);

         for (int i1 = 0; i1 < 80 + this.f_19796_.m_188503_(12); i1++) {
            double DeltaMovementX = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementY = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementZ = this.m_217043_().m_188583_() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            double extraX = (double)(2.0F * Mth.m_14031_((float)(Math.PI + (double)angle)));
            double extraY = 0.3F;
            double extraZ = (double)(2.0F * Mth.m_14089_(angle));
            int hitX = Mth.m_14107_(this.m_20185_() + (double)vec * vecX + extraX);
            int hitY = Mth.m_14107_(this.m_20186_());
            int hitZ = Mth.m_14107_(this.m_20189_() + (double)vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.f_19853_.m_8055_(hit.m_7495_());
            if (this.getIsBerserk()) {
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123744_,
                     this.m_20185_() + (double)vec * vecX + extraX + (double)(f * math),
                     this.m_20186_() + extraY,
                     this.m_20189_() + (double)vec * vecZ + extraZ + (double)(f1 * math),
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            } else if (block.m_60799_() != RenderShape.INVISIBLE) {
               this.f_19853_
                  .m_7106_(
                     new BlockParticleOption(ParticleTypes.f_123794_, block),
                     this.m_20185_() + (double)vec * vecX + extraX + (double)(f * math),
                     this.m_20186_() + extraY,
                     this.m_20189_() + (double)vec * vecZ + extraZ + (double)(f1 * math),
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }

         if (this.getIsBerserk()) {
            this.f_19853_
               .m_7106_(
                  new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 35, 0.8F, 0.305F, 0.02F, 1.0F, 30.0F, false, RingParticle.EnumRingBehavior.GROW),
                  this.m_20185_() + (double)vec * vecX + (double)(f * math),
                  this.m_20186_() + 0.2F,
                  this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
                  0.0,
                  0.0,
                  0.0
               );
         } else {
            this.f_19853_
               .m_7106_(
                  new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 35, 1.0F, 1.0F, 1.0F, 1.0F, 30.0F, false, RingParticle.EnumRingBehavior.GROW),
                  this.m_20185_() + (double)vec * vecX + (double)(f * math),
                  this.m_20186_() + 0.2F,
                  this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
                  0.0,
                  0.0,
                  0.0
               );
         }
      }
   }

   private void launch(Entity e, boolean huge) {
      double d0 = e.m_20185_() - this.m_20185_();
      double d1 = e.m_20189_() - this.m_20189_();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      float f = huge ? 2.0F : 0.5F;
      e.m_5997_(d0 / d2 * (double)f, huge ? 0.75 : 0.2F, d1 / d2 * (double)f);
   }

   private void berserkBlockBreaking(int x, int y, int z) {
      int MthX = Mth.m_14107_(this.m_20185_());
      int MthY = Mth.m_14107_(this.m_20186_());
      int MthZ = Mth.m_14107_(this.m_20189_());
      if (!this.f_19853_.f_46443_ && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
         for (int k2 = -x; k2 <= x; k2++) {
            for (int l2 = -z; l2 <= z; l2++) {
               for (int j = 0; j <= y; j++) {
                  int i3 = MthX + k2;
                  int k = MthY + j;
                  int l = MthZ + l2;
                  BlockPos blockpos = new BlockPos(i3, k, l);
                  BlockState block = this.f_19853_.m_8055_(blockpos);
                  BlockEntity tileEntity = this.f_19853_.m_7702_(blockpos);
                  if (block != Blocks.f_50016_.m_49966_() && !block.m_204336_(ModTag.NETHERITE_MONSTROSITY_IMMUNE)) {
                     if (tileEntity == null && this.f_19796_.m_188503_(4) + 1 == 4) {
                        this.f_19853_.m_7471_(blockpos, true);
                        Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                           this.f_19853_, (double)i3 + 0.5, (double)k + 0.5, (double)l + 0.5, block, 5
                        );
                        this.f_19853_.m_7731_(blockpos, block.m_60819_().m_76188_(), 3);
                        fallingBlockEntity.m_20256_(
                           fallingBlockEntity.m_20184_()
                              .m_82549_(
                                 this.m_20182_()
                                    .m_82546_(fallingBlockEntity.m_20182_())
                                    .m_82542_(
                                       (-1.2 + this.f_19796_.m_188500_()) / 3.0,
                                       (-1.1 + this.f_19796_.m_188500_()) / 3.0,
                                       (-1.2 + this.f_19796_.m_188500_()) / 3.0
                                    )
                              )
                        );
                        this.f_19853_.m_7967_(fallingBlockEntity);
                     } else {
                        this.f_19853_.m_46961_(new BlockPos(i3, k, l), this.shouldDropItem(tileEntity));
                     }
                  }
               }
            }
         }
      }
   }

   private void BlockBreaking() {
      if (this.blockBreakCounter > 0) {
         this.blockBreakCounter--;
      } else {
         if (!this.m_21525_() && !this.f_19853_.f_46443_ && this.blockBreakCounter == 0 && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            for (int a = (int)Math.round(this.m_20191_().f_82288_); a <= (int)Math.round(this.m_20191_().f_82291_); a++) {
               for (int b = (int)Math.round(this.m_20191_().f_82289_); b <= (int)Math.round(this.m_20191_().f_82292_) + 1 && b <= 127; b++) {
                  for (int c = (int)Math.round(this.m_20191_().f_82290_); c <= (int)Math.round(this.m_20191_().f_82293_); c++) {
                     BlockPos blockpos = new BlockPos(a, b, c);
                     BlockState block = this.f_19853_.m_8055_(blockpos);
                     BlockEntity tileEntity = this.f_19853_.m_7702_(blockpos);
                     if (block != Blocks.f_50016_.m_49966_() && block.m_204336_(ModTag.NETHERITE_MONSTROSITY_BREAK)) {
                        boolean flag = this.f_19853_.m_46961_(new BlockPos(a, b, c), this.shouldDropItem(tileEntity));
                        if (flag) {
                           this.blockBreakCounter = 10;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean shouldDropItem(BlockEntity tileEntity) {
      return tileEntity == null ? this.f_19796_.m_188503_(3) + 1 == 3 : true;
   }

   public boolean isBerserk() {
      return this.m_21223_() <= this.m_21233_() / 3.0F;
   }

   public boolean m_6063_() {
      return false;
   }

   public ItemEntity m_19983_(ItemStack stack) {
      ItemEntity itementity = this.m_5552_(stack, 0.0F);
      if (itementity != null) {
         itementity.m_20256_(itementity.m_20184_().m_82542_(0.0, 3.5, 0.0));
         itementity.m_146915_(true);
         itementity.m_32064_();
      }

      return itementity;
   }

   private void setPartPosition(Old_Netherite_Monstrosity_Part part, double offsetX, double offsetY, double offsetZ) {
      part.m_6034_(
         this.m_20185_() + offsetX * (double)part.scale, this.m_20186_() + offsetY * (double)part.scale, this.m_20189_() + offsetZ * (double)part.scale
      );
   }

   public boolean isMultipartEntity() {
      return true;
   }

   public PartEntity<?>[] getParts() {
      return this.monstrosityParts;
   }

   public void m_141965_(ClientboundAddEntityPacket packet) {
      super.m_141965_(packet);
      Cm_Part_Entity.assignPartIDs(this);
   }

   public void m_7023_(Vec3 travelVector) {
      this.m_7910_((float)this.m_21133_(Attributes.f_22279_) * (this.m_20077_() ? 0.2F : 1.0F));
      if (this.m_6142_() && this.m_20077_()) {
         this.m_19920_(this.m_6113_(), travelVector);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9));
      } else {
         super.m_7023_(travelVector);
      }
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.MONSTROSITYHURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.MONSTROSITYDEATH.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.MONSTROSITY_MUSIC.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && this.getIsAwaken();
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossInfo.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossInfo.m_6539_(player);
   }

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return MONSTROSITY_DEATH;
   }

   class AwakenGoal extends Goal {
      public AwakenGoal() {
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean m_8036_() {
         return Old_Netherite_Monstrosity_Entity.this.deactivateProgress > 0.0F;
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         Old_Netherite_Monstrosity_Entity.this.m_20334_(0.0, Old_Netherite_Monstrosity_Entity.this.m_20184_().f_82480_, 0.0);
      }
   }

   class BerserkGoal extends SimpleAnimationGoal<Old_Netherite_Monstrosity_Entity> {
      public BerserkGoal(Old_Netherite_Monstrosity_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         Old_Netherite_Monstrosity_Entity.this.m_20334_(0.0, Old_Netherite_Monstrosity_Entity.this.m_20184_().f_82480_, 0.0);
         LivingEntity target = Old_Netherite_Monstrosity_Entity.this.m_5448_();
         if (target != null) {
            Old_Netherite_Monstrosity_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            this.entity.m_21391_(target, 30.0F, 30.0F);
         }
      }
   }

   class EarthQuakeGoal extends AnimationGoal<Old_Netherite_Monstrosity_Entity> {
      public EarthQuakeGoal(Old_Netherite_Monstrosity_Entity entity) {
         super(entity);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      @Override
      protected boolean test(Animation animation) {
         return animation == Old_Netherite_Monstrosity_Entity.MONSTROSITY_EARTHQUAKE
            || animation == Old_Netherite_Monstrosity_Entity.MONSTROSITY_EARTHQUAKE2
            || animation == Old_Netherite_Monstrosity_Entity.MONSTROSITY_EARTHQUAKE3;
      }

      public void m_8037_() {
         LivingEntity target = Old_Netherite_Monstrosity_Entity.this.m_5448_();
         Old_Netherite_Monstrosity_Entity.this.m_20334_(0.0, Old_Netherite_Monstrosity_Entity.this.m_20184_().f_82480_, 0.0);
         if (Old_Netherite_Monstrosity_Entity.this.getAnimation() == Old_Netherite_Monstrosity_Entity.MONSTROSITY_EARTHQUAKE) {
            if ((Old_Netherite_Monstrosity_Entity.this.getAnimationTick() >= 34 || target == null)
               && (Old_Netherite_Monstrosity_Entity.this.getAnimationTick() <= 54 || target == null)) {
               Old_Netherite_Monstrosity_Entity.this.m_146922_(Old_Netherite_Monstrosity_Entity.this.f_19859_);
            } else {
               Old_Netherite_Monstrosity_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.entity.m_21391_(target, 30.0F, 30.0F);
            }
         }

         if (Old_Netherite_Monstrosity_Entity.this.getAnimation() == Old_Netherite_Monstrosity_Entity.MONSTROSITY_EARTHQUAKE2) {
            if ((Old_Netherite_Monstrosity_Entity.this.getAnimationTick() >= 24 || target == null)
               && (Old_Netherite_Monstrosity_Entity.this.getAnimationTick() <= 44 || target == null)) {
               Old_Netherite_Monstrosity_Entity.this.m_146922_(Old_Netherite_Monstrosity_Entity.this.f_19859_);
            } else {
               Old_Netherite_Monstrosity_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.entity.m_21391_(target, 30.0F, 30.0F);
            }
         }

         if (Old_Netherite_Monstrosity_Entity.this.getAnimation() == Old_Netherite_Monstrosity_Entity.MONSTROSITY_EARTHQUAKE3) {
            if ((Old_Netherite_Monstrosity_Entity.this.getAnimationTick() >= 29 || target == null)
               && (Old_Netherite_Monstrosity_Entity.this.getAnimationTick() <= 49 || target == null)) {
               Old_Netherite_Monstrosity_Entity.this.m_146922_(Old_Netherite_Monstrosity_Entity.this.f_19859_);
            } else {
               Old_Netherite_Monstrosity_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.entity.m_21391_(target, 30.0F, 30.0F);
            }
         }
      }
   }

   class HealGoal extends SimpleAnimationGoal<Old_Netherite_Monstrosity_Entity> {
      public HealGoal(Old_Netherite_Monstrosity_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = Old_Netherite_Monstrosity_Entity.this.m_5448_();
         Old_Netherite_Monstrosity_Entity.this.m_20334_(0.0, Old_Netherite_Monstrosity_Entity.this.m_20184_().f_82480_, 0.0);
         if (Old_Netherite_Monstrosity_Entity.this.getAnimation() == Old_Netherite_Monstrosity_Entity.MONSTROSITY_CHARGE && target != null) {
            if (Old_Netherite_Monstrosity_Entity.this.getAnimationTick() >= 34 && Old_Netherite_Monstrosity_Entity.this.getAnimationTick() <= 72) {
               Old_Netherite_Monstrosity_Entity.this.m_146922_(Old_Netherite_Monstrosity_Entity.this.f_19859_);
            } else {
               Old_Netherite_Monstrosity_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.entity.m_21391_(target, 30.0F, 30.0F);
            }
         }
      }
   }

   class ShootGoal extends SimpleAnimationGoal<Old_Netherite_Monstrosity_Entity> {
      public ShootGoal(Old_Netherite_Monstrosity_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = Old_Netherite_Monstrosity_Entity.this.m_5448_();
         Old_Netherite_Monstrosity_Entity.this.m_20334_(0.0, Old_Netherite_Monstrosity_Entity.this.m_20184_().f_82480_, 0.0);
         int lavabombcount = CMConfig.Lavabombamount;
         if (target != null) {
            Old_Netherite_Monstrosity_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            this.entity.m_21391_(target, 30.0F, 30.0F);
            if (Old_Netherite_Monstrosity_Entity.this.getAnimationTick() == 30) {
               Old_Netherite_Monstrosity_Entity.this.m_5496_((SoundEvent)ModSounds.MONSTROSITYSHOOT.get(), 3.0F, 0.75F);
               Old_Netherite_Monstrosity_Entity.this.lavabombmagazine--;

               for (int i = 0; i < lavabombcount; i++) {
                  Lava_Bomb_Entity lava = new Lava_Bomb_Entity(
                     (EntityType<Lava_Bomb_Entity>)ModEntities.LAVA_BOMB.get(),
                     Old_Netherite_Monstrosity_Entity.this.f_19853_,
                     Old_Netherite_Monstrosity_Entity.this
                  );
                  double d0 = target.m_20185_() - Old_Netherite_Monstrosity_Entity.this.headPart.m_20185_();
                  double d1 = target.m_20191_().f_82289_ + (double)(target.m_20206_() / 3.0F) - lava.m_20186_();
                  double d2 = target.m_20189_() - Old_Netherite_Monstrosity_Entity.this.headPart.m_20189_();
                  double d3 = (double)Mth.m_14116_((float)(d0 * d0 + d2 * d2));
                  lava.m_6686_(d0, d1 + d3 * 0.2F, d2, 1.0F, (float)(24 - Old_Netherite_Monstrosity_Entity.this.f_19853_.m_46791_().m_19028_() * 4));
                  Old_Netherite_Monstrosity_Entity.this.f_19853_.m_7967_(lava);
               }
            }
         }
      }
   }
}
