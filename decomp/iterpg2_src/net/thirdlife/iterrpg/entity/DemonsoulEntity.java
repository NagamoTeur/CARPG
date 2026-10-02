package net.thirdlife.iterrpg.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModItems;
import net.thirdlife.iterrpg.procedures.DemonsoulConvertProcedure;
import net.thirdlife.iterrpg.procedures.DemonsoulTickProcedure;

public class DemonsoulEntity extends Monster {
   public DemonsoulEntity(SpawnEntity packet, Level world) {
      this((EntityType<DemonsoulEntity>)IterRpgModEntities.DEMONSOUL.get(), world);
   }

   public DemonsoulEntity(EntityType<DemonsoulEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 1;
      this.m_21557_(false);
      this.f_21342_ = new FlyingMoveControl(this, 10, true);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected PathNavigation m_6037_(Level world) {
      return new FlyingPathNavigation(this, world);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 1.6, true) {
         protected double m_6639_(LivingEntity entity) {
            return (double)(this.f_25540_.m_20205_() * this.f_25540_.m_20205_() + entity.m_20205_());
         }
      });
      this.f_21345_.m_25352_(2, new RandomStrollGoal(this, 0.8, 20) {
         protected Vec3 m_7037_() {
            RandomSource random = DemonsoulEntity.this.m_217043_();
            double dir_x = DemonsoulEntity.this.m_20185_() + (double)((random.m_188501_() * 2.0F - 1.0F) * 16.0F);
            double dir_y = DemonsoulEntity.this.m_20186_() + (double)((random.m_188501_() * 2.0F - 1.0F) * 16.0F);
            double dir_z = DemonsoulEntity.this.m_20189_() + (double)((random.m_188501_() * 2.0F - 1.0F) * 16.0F);
            return new Vec3(dir_x, dir_y, dir_z);
         }
      });
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, Skeleton.class, false, false));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, WitherSkeleton.class, false, false));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, Stray.class, false, false));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(7, new FloatGoal(this));
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   protected void m_7472_(DamageSource source, int looting, boolean recentlyHitIn) {
      super.m_7472_(source, looting, recentlyHitIn);
      this.m_19983_(new ItemStack((ItemLike)IterRpgModItems.MAGMANUM_CHUNK.get()));
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.campfire.crackle"));
   }

   public void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_((SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("particle.soul_escape")), 0.15F, 1.0F);
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.guardian.hurt"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.elder_guardian.hurt"));
   }

   public boolean m_142535_(float l, float d, DamageSource source) {
      return false;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (source.m_7640_() instanceof AbstractArrow) {
         return false;
      } else if (source.m_7640_() instanceof Player) {
         return false;
      } else if (source.m_7640_() instanceof ThrownPotion || source.m_7640_() instanceof AreaEffectCloud) {
         return false;
      } else if (source == DamageSource.f_19315_) {
         return false;
      } else if (source == DamageSource.f_19314_) {
         return false;
      } else if (source == DamageSource.f_19312_) {
         return false;
      } else if (source == DamageSource.f_19306_) {
         return false;
      } else if (source.m_19372_()) {
         return false;
      } else if (source.m_19385_().equals("trident")) {
         return false;
      } else if (source == DamageSource.f_19321_) {
         return false;
      } else if (source == DamageSource.f_19323_) {
         return false;
      } else if (source == DamageSource.f_19320_) {
         return false;
      } else {
         return source.m_19385_().equals("witherSkull") ? false : super.m_6469_(source, amount);
      }
   }

   public void m_5993_(Entity entity, int score, DamageSource damageSource) {
      super.m_5993_(entity, score, damageSource);
      DemonsoulConvertProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), entity, this);
   }

   public void m_6075_() {
      super.m_6075_();
      DemonsoulTickProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   public void m_20242_(boolean ignored) {
      super.m_20242_(true);
   }

   public void m_8107_() {
      super.m_8107_();
      this.m_20242_(true);
   }

   public static void init() {
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.4);
      builder = builder.m_22268_(Attributes.f_22276_, 12.0);
      builder = builder.m_22268_(Attributes.f_22284_, 0.0);
      builder = builder.m_22268_(Attributes.f_22281_, 12.0);
      builder = builder.m_22268_(Attributes.f_22277_, 66.0);
      builder = builder.m_22268_(Attributes.f_22278_, 1.0);
      return builder.m_22268_(Attributes.f_22280_, 0.4);
   }
}
