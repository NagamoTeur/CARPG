package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeClient;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.common.items.weapon.DividerItem;
import com.obscuria.aquamirae.common.items.weapon.PoisonedBladeItem;
import com.obscuria.aquamirae.common.items.weapon.RemnantsSaberItem;
import com.obscuria.aquamirae.common.items.weapon.WhisperOfTheAbyssItem;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.common.DynamicProjectile;
import com.obscuria.obscureapi.api.common.FlatVFX.Builder;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import com.obscuria.obscureapi.util.EntityUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

@ShipGraveyardEntity
public class CaptainCornelia extends Monster implements IAnimated {
   private static final EntityDataAccessor<Integer> REGENERATION = SynchedEntityData.m_135353_(CaptainCornelia.class, EntityDataSerializers.f_135028_);
   private final ServerBossEvent bossInfo = new ServerBossEvent(this.m_5446_(), BossBarColor.BLUE, BossBarOverlay.PROGRESS);
   public final Animation PULL_ATTACK = new Animation(1);
   public final Animation SWING_ATTACK = new Animation(2);
   public final Animation THRUST_ATTACK = new Animation(3);
   public final Animation SWITCH_WEAPON = new Animation(4);
   public final Animation DEATH = new Animation(5);
   private static final int WEAPON_SWITCH_INTERVAL = 400;
   private int weaponSwitchTick;
   private int particle1;
   private int particle2;

   public CaptainCornelia(SpawnEntity packet, Level world) {
      this((EntityType<CaptainCornelia>)AquamiraeEntities.CAPTAIN_CORNELIA.get(), world);
   }

   public CaptainCornelia(EntityType<CaptainCornelia> type, Level world) {
      super(type, world);
      this.f_21364_ = 100;
      this.m_21530_();
      this.m_8061_(EquipmentSlot.MAINHAND, this.getMainWeapon().m_7968_());
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(4, new FloatGoal(this));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(6, new WaterAvoidingRandomStrollGoal(this, 1.2));
      super.m_8099_();
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.2, false) {
         protected double m_6639_(@NotNull LivingEntity entity) {
            return 0.0;
         }
      });
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractIllager.class, false, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false, false));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(REGENERATION, (Integer)AquamiraeConfig.Common.corneliaRegenerationAbility.get());
   }

   public void m_7380_(@NotNull CompoundTag tag) {
      super.m_7380_(tag);
      CompoundTag data = new CompoundTag();
      data.m_128405_("Regeneration", (Integer)this.m_20088_().m_135370_(REGENERATION));
      tag.m_128365_("CorneliaData", data);
   }

   public void m_7378_(@NotNull CompoundTag tag) {
      super.m_7378_(tag);
      CompoundTag data = (CompoundTag)tag.m_128423_("CorneliaData");
      if (data != null) {
         this.m_20088_().m_135381_(REGENERATION, data.m_128451_("Regeneration"));
      }
   }

   public Optional<Animation> getAnimation(byte id) {
      return switch (id) {
         case 1 -> Optional.of(this.PULL_ATTACK);
         case 2 -> Optional.of(this.SWING_ATTACK);
         case 3 -> Optional.of(this.THRUST_ATTACK);
         case 4 -> Optional.of(this.SWITCH_WEAPON);
         case 5 -> Optional.of(this.DEATH);
         default -> Optional.empty();
      };
   }

   public boolean isAttacks() {
      return this.PULL_ATTACK.isPlaying() || this.SWING_ATTACK.isPlaying() || this.THRUST_ATTACK.isPlaying() || this.SWITCH_WEAPON.isPlaying();
   }

   public void m_6075_() {
      AnimationHelper.handleDeath(this, this.DEATH, 60);
      AnimationHelper.handle(new Animation[]{this.PULL_ATTACK, this.SWING_ATTACK, this.THRUST_ATTACK, this.SWITCH_WEAPON, this.DEATH});
      if (this.PULL_ATTACK.hasPlayed() && this.m_5448_() != null) {
         this.SWING_ATTACK.play(this, 40);
      }

      if (this.SWING_ATTACK.hasPlayed() && this.m_5448_() != null) {
         this.THRUST_ATTACK.play(this, 40);
      }

      if (this.SWITCH_WEAPON.getTick() == 10) {
         this.switchWeapon();
      }

      this.SWING_ATTACK.sound(this, 1, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_ATTACK_1, SoundSource.HOSTILE, 2.0F, 1.0F);
      this.THRUST_ATTACK.sound(this, 1, AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_ATTACK_2, SoundSource.HOSTILE, 2.0F, 1.0F);
      this.SWITCH_WEAPON.sound(this, 8, () -> SoundEvents.f_11677_, SoundSource.HOSTILE, 1.0F, 1.0F);
      this.weaponSwitchTick++;
      if (this.m_21224_()) {
         this.m_21219_();
      }

      if ((double)this.m_21223_() < (double)this.m_21233_() * 0.25
         && (Integer)this.m_20088_().m_135370_(REGENERATION) > 0
         && !this.m_21023_(MobEffects.f_19620_)) {
         this.rage();
      }

      if (this.m_21223_() <= 16.0F && !this.m_21023_(MobEffects.f_19613_)) {
         this.m_5634_(0.5F);
      }

      if (this.m_5448_() != null) {
         LivingEntity target = this.m_5448_();
         double distance = this.m_20280_(target);
         this.modifyYRot(EntityUtils.getYAngleBetween(this, target), this.m_20096_() ? 0.15F : 0.3F);
         this.f_21365_.m_148051_(target);
         if (!this.isAttacks() && !this.f_19853_.f_46443_) {
            if (this.weaponSwitchTick > 400 && this.f_19796_.m_188501_() < (float)(this.weaponSwitchTick - 400) / 5000.0F) {
               this.SWITCH_WEAPON.play(this, 20);
               this.weaponSwitchTick = 0;
            } else if (distance < 3.0 || this.m_21023_(MobEffects.f_19620_) || distance < 25.0 && this.f_19796_.m_188503_(100) == 1) {
               if (this.f_19796_.m_188499_()) {
                  this.SWING_ATTACK.play(this, 40);
               } else {
                  this.THRUST_ATTACK.play(this, 40);
               }
            } else if (distance < 600.0 && this.f_19796_.m_188503_(200) == 1) {
               this.PULL_ATTACK.play(this, 30);
            }
         }

         if (this.PULL_ATTACK.getTick() == 10) {
            target.m_20256_(
               new Vec3(target.m_20185_(), target.m_20186_(), target.m_20189_())
                  .m_82505_(new Vec3(this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_()))
                  .m_82490_(0.25)
            );
            if (target instanceof Player player) {
               player.f_19864_ = true;
            }
         }

         if (this.SWING_ATTACK.getTick() == 12 || this.THRUST_ATTACK.getTick() == 12) {
            this.m_20256_(
               new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_())
                  .m_82505_(new Vec3(target.m_20185_(), target.m_20186_() + 0.1F, target.m_20189_()))
                  .m_82490_(0.2F)
            );
         }

         if (this.SWING_ATTACK.getTick() == 14 && distance < 9.0) {
            EntityUtils.getRelativeEntities(this, LivingEntity.class, 5.0F, 0.0F, 0.0F, 6.0F, true, true).forEach(e -> {
               if (e != this) {
                  this.m_7327_(e);
               }
            });
         }

         if (this.THRUST_ATTACK.getTick() == 14 && distance < 12.0) {
            this.m_7327_(target);
         }

         if (this.SWING_ATTACK.getTick() == 12) {
            Builder.create(30)
               .owner(this)
               .texture("aquamirae", "swing")
               .relativePos(this, 1.0F, 0.0F, -0.7F)
               .relativeRot(this, false, true)
               .yRot(-90.0F, 0.0F, 0.0F)
               .moveForward(0.0F, 3.0F, -0.2F)
               .scale(0.4F, 0.4F, -0.013F)
               .alpha(1.0F, -0.01F, -0.01F)
               .build(this.f_19853_);
         }

         if (this.m_5842_() && !this.m_21023_(MobEffects.f_19620_)) {
            this.m_20256_(
               new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_())
                  .m_82505_(new Vec3(target.m_20185_(), target.m_20186_(), target.m_20189_()))
                  .m_82490_(0.04F)
            );
         } else if (this.m_20069_() && !this.m_21023_(MobEffects.f_19620_)) {
            this.m_20256_(
               new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_())
                  .m_82505_(new Vec3(target.m_20185_(), target.m_20186_(), target.m_20189_()))
                  .m_82490_(0.04F)
                  .m_82520_(0.0, 0.08F, 0.0)
            );
         }
      }

      if (this.isAttacks()) {
         this.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 5, 3, false, false));
      }

      if (this.m_21023_(MobEffects.f_19620_)) {
         this.m_5634_(1.0F);
         if ((Boolean)AquamiraeConfig.Common.corneliaSpinAbility.get() && !this.m_9236_().m_5776_()) {
            Vec3 center = new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_());
            List<LivingEntity> list = this.m_9236_()
               .m_6443_(LivingEntity.class, new AABB(center, center).m_82400_(10.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(ent -> ent.m_20238_(center)))
               .toList();
            MobEffectInstance LEVITATION = this.m_21124_(MobEffects.f_19620_);
            int DURATION = LEVITATION == null ? 0 : LEVITATION.m_19557_();
            list.forEach(
               entity -> {
                  if (entity instanceof Player player && (player.m_7500_() || player.m_5833_())) {
                     return;
                  }

                  if (entity.m_21233_() <= 100.0F) {
                     if (DURATION > 1) {
                        double radius = 5.0;
                        Vec3 orbit = new Vec3(
                           center.f_82479_ + Math.cos((double)((float)entity.f_19797_ * -0.1F)) * 5.0,
                           center.f_82480_,
                           center.f_82481_ + Math.sin((double)((float)entity.f_19797_ * -0.1F)) * 5.0
                        );
                        entity.m_20256_(new Vec3(entity.m_20185_(), entity.m_20186_(), entity.m_20189_()).m_82505_(orbit).m_82490_(0.2F));
                        if (entity instanceof Player _player) {
                           _player.f_19864_ = true;
                        }
                     } else if (DURATION == 1) {
                        entity.m_20256_(
                           new Vec3(this.m_20185_(), this.m_20186_(), this.m_20189_())
                              .m_82505_(new Vec3(entity.m_20185_(), entity.m_20186_(), entity.m_20189_()))
                              .m_82490_(0.2F)
                        );
                        if (entity instanceof Player _player) {
                           _player.f_19864_ = true;
                        }
                     }
                  }
               }
            );
            this.particle1++;
            if (this.m_9236_() instanceof ServerLevel server && this.particle1 > 1) {
               this.particle1 = 0;
               server.m_8767_(
                  (SimpleParticleType)AquamiraeParticleTypes.GHOST.get(), this.m_20185_(), this.m_20186_() - 0.2, this.m_20189_(), 1, 0.3, 0.1, 0.3, 0.1
               );
            }

            if (this.m_20069_()) {
               this.m_20256_(new Vec3(0.0, 0.4F, 0.0));
            }
         }
      }

      this.particle2++;
      if (this.m_9236_() instanceof ServerLevel server && this.particle2 > 9) {
         this.particle2 = 0;
         server.m_8767_(
            (SimpleParticleType)AquamiraeParticleTypes.GHOST_SHINE.get(), this.m_20185_(), this.m_20186_() + 1.7, this.m_20189_(), 1, 0.15, 0.1, 0.15, 0.1
         );
      }

      if (this.f_19853_.f_46443_) {
         Vec3 centerx = this.m_20182_();
         this.m_9236_().m_45976_(Player.class, new AABB(centerx, centerx).m_82400_(32.0)).forEach(AquamiraeClient::playCorneliaMusic);
      }

      super.m_6075_();
   }

   private void modifyYRot(float rotation, float mod) {
      this.f_19859_ = this.m_146908_();
      this.m_146922_(this.m_146908_() + (rotation - this.m_146908_()) * mod);
   }

   public Item getMainWeapon() {
      return Aquamirae.winterEvent() ? (Item)AquamiraeItems.SWEET_LANCE.get() : (Item)AquamiraeItems.CORAL_LANCE.get();
   }

   public void switchWeapon() {
      LivingEntity target = this.m_5448_();
      Item current = this.m_21205_().m_41720_();
      Item main = this.getMainWeapon();
      Item armor = (Item)AquamiraeItems.WHISPER_OF_THE_ABYSS.get();
      Item health = (Item)AquamiraeItems.DIVIDER.get();
      Item poison = (Item)AquamiraeItems.POISONED_BLADE.get();
      Item heal = (Item)AquamiraeItems.REMNANTS_SABER.get();
      List<Item> weapons = new ArrayList<>();
      if (!current.equals(main)) {
         this.add(weapons, main, 10);
      }

      if (!current.equals(poison)) {
         this.add(weapons, poison, 10);
      }

      if (!current.equals(health)) {
         this.add(weapons, health, target == null ? 1 : (int)(target.m_21223_() / 5.0F) + (target.m_21223_() >= 200.0F ? 1000 : 0));
      }

      if (!current.equals(armor)) {
         this.add(weapons, armor, target == null ? 1 : target.m_21230_());
      }

      if (!current.equals(heal)) {
         this.add(weapons, heal, (int)(this.m_21233_() - this.m_21233_() / 5.0F) + (this.m_21223_() <= 20.0F ? 1000 : 0));
      }

      if (!weapons.isEmpty()) {
         Item item = weapons.get(this.f_19796_.m_216339_(0, Math.max(1, weapons.size() - 1)));
         this.m_8061_(EquipmentSlot.MAINHAND, item.m_7968_());
         this.m_8061_(
            EquipmentSlot.OFFHAND,
            !(item instanceof DividerItem) && !(item instanceof PoisonedBladeItem) && !(item instanceof RemnantsSaberItem)
               ? ItemStack.f_41583_
               : item.m_7968_()
         );
      }
   }

   private void add(List<Item> list, Item item, int times) {
      for (int i = 1; i <= times; i++) {
         list.add(item);
      }
   }

   public void rage() {
      this.m_20088_().m_135381_(REGENERATION, (Integer)this.m_20088_().m_135370_(REGENERATION) - 1);
      this.m_7292_(new MobEffectInstance(MobEffects.f_19620_, 200, 0, false, false));
      if (this.f_19853_ instanceof ServerLevel serverLevel) {
         BlockPos pos = new BlockPos(this.m_20185_(), this.m_20186_(), this.m_20189_());
         LightningBolt entityToSpawn = new LightningBolt(EntityType.f_20465_, serverLevel);
         entityToSpawn.m_20219_(Vec3.m_82539_(pos));
         entityToSpawn.m_20874_(true);
         serverLevel.m_7967_(entityToSpawn);
         serverLevel.m_5594_(null, pos, (SoundEvent)AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HORN.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
         serverLevel.m_5594_(null, pos, (SoundEvent)AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_RAGE.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
         EntityType<? extends DynamicProjectile> type = this.f_19796_.m_188499_()
            ? (EntityType)AquamiraeEntities.POISONED_CHAKRA.get()
            : (EntityType)AquamiraeEntities.MAZE_ROSE.get();
         DynamicProjectile.create(type, this, this.f_19853_, null, 5, 0.0F, 600, 1000);
         DynamicProjectile.create(type, this, this.f_19853_, null, 5, 0.33F, 600, 1000);
         DynamicProjectile.create(type, this, this.f_19853_, null, 5, 0.66F, 600, 1000);
      }
   }

   public boolean m_7327_(Entity entity) {
      if (entity instanceof LivingEntity living) {
         Item item = this.m_21205_().m_41720_();
         if (item instanceof PoisonedBladeItem) {
            living.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 80, 2));
            return super.m_7327_(entity);
         }

         if (item instanceof WhisperOfTheAbyssItem) {
            return this.doHurtTarget(living, DamageSource.m_19370_(this).m_19380_(), 8.0F);
         }

         if (item instanceof DividerItem) {
            return this.doHurtTarget(living, DamageSource.m_19370_(this).m_19380_(), Math.max(4.0F, living.m_21223_() / 2.0F));
         }

         if (item instanceof RemnantsSaberItem) {
            this.m_5634_(Math.min(30.0F, living.m_21233_() * 0.4F));
            return super.m_7327_(entity);
         }
      }

      return super.m_7327_(entity);
   }

   private boolean doHurtTarget(LivingEntity entity, DamageSource source, float amount) {
      boolean hurt = entity.m_6469_(source, amount);
      if (hurt) {
         float knockback = (float)this.m_21133_(Attributes.f_22282_);
         if (knockback > 0.0F) {
            entity.m_147240_(
               (double)(knockback * 0.5F),
               (double)Mth.m_14031_(this.m_146908_() * (float) (Math.PI / 180.0)),
               (double)(-Mth.m_14089_(this.m_146908_() * (float) (Math.PI / 180.0)))
            );
            this.m_20256_(this.m_20184_().m_82542_(0.6, 1.0, 0.6));
         }

         this.m_19970_(this, entity);
         this.m_21335_(entity);
      }

      return hurt;
   }

   protected void m_5907_() {
      if (this.f_19853_ instanceof ServerLevel server) {
         if (Math.random() <= 0.2F) {
            ItemEntity item = new ItemEntity(EntityType.f_20461_, server);
            item.m_32045_(this.getMainWeapon().m_7968_());
            item.m_20219_(this.m_20182_());
            server.m_7967_(item);
         }

         ItemStack map = Aquamirae.getStructureMap(Aquamirae.SHELTER, server, this);
         if (!map.m_41619_()) {
            ItemEntity item = new ItemEntity(EntityType.f_20461_, server);
            item.m_32045_(map);
            item.m_20219_(this.m_20182_());
            server.m_7967_(item);
         }
      }
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_AMBIENT.get();
   }

   public SoundEvent m_7975_(@NotNull DamageSource source) {
      return (SoundEvent)AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HURT.get();
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_DEATH.get();
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (source.m_7640_() instanceof AbstractArrow) {
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
      } else {
         return source.m_19385_().equals("trident") ? false : super.m_6469_(source, amount);
      }
   }

   public SpawnGroupData m_6518_(
      @NotNull ServerLevelAccessor world,
      @NotNull DifficultyInstance difficulty,
      @NotNull MobSpawnType reason,
      @Nullable SpawnGroupData livingdata,
      @Nullable CompoundTag tag
   ) {
      if (this.m_9236_() instanceof ServerLevel serverLevel) {
         serverLevel.m_5594_(
            null,
            new BlockPos(this.m_20185_(), this.m_20186_(), this.m_20189_()),
            (SoundEvent)AquamiraeSounds.ENTITY_CAPTAIN_CORNELIA_HORN.get(),
            SoundSource.HOSTILE,
            3.0F,
            1.0F
         );
      }

      this.m_7292_(new MobEffectInstance(MobEffects.f_19620_, 120, 0, false, false));
      Aquamirae.loadFromConfig(this, Attributes.f_22279_, (Double)AquamiraeConfig.Common.corneliaMovementSpeed.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22276_, (Double)AquamiraeConfig.Common.corneliaMaxHealth.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22284_, (Double)AquamiraeConfig.Common.corneliaArmor.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22281_, (Double)AquamiraeConfig.Common.corneliaAttackDamage.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22277_, (Double)AquamiraeConfig.Common.corneliaFollowRange.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22282_, (Double)AquamiraeConfig.Common.corneliaAttackKnockback.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22278_, (Double)AquamiraeConfig.Common.corneliaKnockbackResistance.get());
      return super.m_6518_(world, difficulty, reason, livingdata, tag);
   }

   public boolean m_6072_() {
      return false;
   }

   public void m_6457_(@NotNull ServerPlayer player) {
      super.m_6457_(player);
      this.bossInfo.m_6543_(player);
   }

   public void m_6452_(@NotNull ServerPlayer player) {
      super.m_6452_(player);
      this.bossInfo.m_6539_(player);
   }

   public void m_8024_() {
      super.m_8024_();
      this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
   }

   @NotNull
   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.m_21552_()
         .m_22268_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get(), 1.1)
         .m_22268_(Attributes.f_22279_, 0.2)
         .m_22268_(Attributes.f_22276_, 200.0)
         .m_22268_(Attributes.f_22284_, 16.0)
         .m_22268_(Attributes.f_22281_, 1.0)
         .m_22268_(Attributes.f_22277_, 128.0)
         .m_22268_(Attributes.f_22278_, 0.5)
         .m_22268_(Attributes.f_22282_, 2.0);
   }
}
