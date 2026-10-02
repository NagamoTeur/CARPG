package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.AnimationHelper;
import com.obscuria.obscureapi.api.hekate.IAnimated;
import com.obscuria.obscureapi.api.utils.ExceptionFilter;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

@ShipGraveyardEntity
public class Maw extends Monster implements IAnimated {
   private static final EntityDataAccessor<ItemStack> ITEM_IN_MOUTH = SynchedEntityData.m_135353_(Maw.class, EntityDataSerializers.f_135033_);
   public final Animation ATTACK = new Animation(1);
   public final Animation DEATH = new Animation(2);

   public Maw(SpawnEntity packet, Level world) {
      this((EntityType<Maw>)AquamiraeEntities.MAW.get(), world);
   }

   public Maw(EntityType<Maw> type, Level world) {
      super(type, world);
      this.f_21364_ = 10;
      this.randomMawItem();
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(ITEM_IN_MOUTH, ItemStack.f_41583_);
      super.m_8097_();
   }

   public void m_7380_(@NotNull CompoundTag data) {
      data.m_128365_("ItemInMouth", this.getItemInMouth().m_41739_(new CompoundTag()));
      super.m_7380_(data);
   }

   public void m_7378_(@NotNull CompoundTag data) {
      if (data.m_128441_("ItemInMouth")) {
         this.setItemInMouth(ItemStack.m_41712_(data.m_128469_("ItemInMouth")));
      }

      super.m_7378_(data);
   }

   public void randomMawItem() {
      if (!this.f_19853_.f_46443_) {
         MinecraftServer minecraftServer = this.f_19853_.m_7654_();
         if (minecraftServer != null && this.f_19853_ instanceof ServerLevel server) {
            LootContext lootContext = new Builder(server)
               .m_230911_(this.m_217043_())
               .m_78972_(LootContextParams.f_81455_, this)
               .m_78972_(LootContextParams.f_81460_, this.m_20182_())
               .m_78975_(LootContextParamSets.f_81416_);
            LootTable treasure = minecraftServer.m_129898_().m_79217_(new ResourceLocation("aquamirae", "entities/maw_random_item"));
            ExceptionFilter.of(() -> this.setItemInMouth((ItemStack)treasure.m_230922_(lootContext).get(0)));
         }
      }
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new LeapAtTargetGoal(this, 0.4F));
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.2, false) {
         protected double m_6639_(@NotNull LivingEntity entity) {
            return 4.0 + (double)(entity.m_20205_() * entity.m_20205_());
         }
      });
      this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[0]));
      this.f_21345_.m_25352_(4, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.f_21346_.m_25352_(6, new NearestAttackableTargetGoal(this, AbstractIllager.class, false, false));
      this.f_21346_.m_25352_(6, new NearestAttackableTargetGoal(this, AbstractVillager.class, false, false));
      this.f_21345_.m_25352_(7, new FloatGoal(this));
   }

   public Optional<Animation> getAnimation(byte id) {
      return id == 1 ? Optional.of(this.ATTACK) : (id == 2 ? Optional.of(this.DEATH) : Optional.empty());
   }

   public void m_8119_() {
      AnimationHelper.handleDeath(this, this.DEATH, 40);
      AnimationHelper.handle(new Animation[]{this.ATTACK, this.DEATH});
      super.m_8119_();
   }

   public void setItemInMouth(ItemStack stack) {
      this.m_20088_().m_135381_(ITEM_IN_MOUTH, stack);
   }

   public ItemStack getItemInMouth() {
      return (ItemStack)this.m_20088_().m_135370_(ITEM_IN_MOUTH);
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)AquamiraeSounds.ENTITY_DEEP_AMBIENT.get();
   }

   public void m_7355_(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
      this.m_5496_(SoundEvents.f_12004_, 0.15F, 1.0F);
   }

   public SoundEvent m_7975_(@NotNull DamageSource source) {
      return (SoundEvent)AquamiraeSounds.ENTITY_DEEP_HURT.get();
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)AquamiraeSounds.ENTITY_DEEP_DEATH.get();
   }

   protected void m_5907_() {
      if (this.f_19853_ instanceof ServerLevel server && !this.getItemInMouth().m_41619_()) {
         ItemEntity item = new ItemEntity(EntityType.f_20461_, server);
         item.m_32045_(this.getItemInMouth());
         item.m_20219_(this.m_20182_());
         item.m_20256_(this.m_20184_().m_82490_(0.25).m_82520_(0.0, 0.2, 0.0));
         item.f_19864_ = true;
         server.m_7967_(item);
         this.setItemInMouth(ItemStack.f_41583_);
      }
   }

   public boolean m_6469_(@NotNull DamageSource source, float amount) {
      return source == DamageSource.f_19312_ ? false : super.m_6469_(source, amount);
   }

   public boolean m_7327_(@NotNull Entity entity) {
      this.ATTACK.play(this, 20);
      return super.m_7327_(entity);
   }

   public SpawnGroupData m_6518_(
      @NotNull ServerLevelAccessor world,
      @NotNull DifficultyInstance difficulty,
      @NotNull MobSpawnType reason,
      @Nullable SpawnGroupData data,
      @Nullable CompoundTag tag
   ) {
      Aquamirae.loadFromConfig(this, (Attribute)ForgeMod.SWIM_SPEED.get(), (Double)AquamiraeConfig.Common.mawSwimSpeed.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22279_, (Double)AquamiraeConfig.Common.mawSpeed.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22276_, (Double)AquamiraeConfig.Common.mawMaxHealth.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22284_, (Double)AquamiraeConfig.Common.mawArmor.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22281_, (Double)AquamiraeConfig.Common.mawAttackDamage.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22277_, (Double)AquamiraeConfig.Common.mawFollowRange.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22282_, (Double)AquamiraeConfig.Common.mawAttackKnockback.get());
      Aquamirae.loadFromConfig(this, Attributes.f_22278_, (Double)AquamiraeConfig.Common.mawKnockbackResistance.get());
      return super.m_6518_(world, difficulty, reason, data, tag);
   }

   public static SpawnPredicate<Maw> getSpawnRules() {
      return (entityType, level, spawnType, pos, random) -> pos.m_123342_() < level.m_5736_() + 6
            && Monster.m_219019_(entityType, level, spawnType, pos, random);
   }

   @NotNull
   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.m_21552_()
         .m_22268_((Attribute)ForgeMod.SWIM_SPEED.get(), 5.0)
         .m_22268_(Attributes.f_22279_, 0.2)
         .m_22268_(Attributes.f_22276_, 20.0)
         .m_22268_(Attributes.f_22284_, 0.0)
         .m_22268_(Attributes.f_22281_, 4.0)
         .m_22268_(Attributes.f_22277_, 24.0)
         .m_22268_(Attributes.f_22282_, 0.3)
         .m_22268_(Attributes.f_22278_, 0.0);
   }
}
