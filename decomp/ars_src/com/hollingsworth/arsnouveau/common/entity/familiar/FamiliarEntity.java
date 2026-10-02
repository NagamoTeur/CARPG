package com.hollingsworth.arsnouveau.common.entity.familiar;

import com.hollingsworth.arsnouveau.api.client.IVariantTextureProvider;
import com.hollingsworth.arsnouveau.api.entity.IDecoratable;
import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.event.FamiliarSummonEvent;
import com.hollingsworth.arsnouveau.api.familiar.IFamiliar;
import com.hollingsworth.arsnouveau.api.familiar.PersistentFamiliarData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.capability.IPlayerCap;
import com.hollingsworth.arsnouveau.common.entity.goal.familiar.FamOwnerHurtByTargetGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.familiar.FamOwnerHurtTargetGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.familiar.FamiliarFollowGoal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class FamiliarEntity extends PathfinderMob implements IAnimatable, IFamiliar, IDispellable, IDecoratable, IVariantTextureProvider<FamiliarEntity> {
   public double manaReserveModifier = 0.15;
   private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.m_135353_(FamiliarEntity.class, EntityDataSerializers.f_135041_);
   public static final EntityDataAccessor<String> COLOR = SynchedEntityData.m_135353_(FamiliarEntity.class, EntityDataSerializers.f_135030_);
   public static final EntityDataAccessor<ItemStack> COSMETIC = SynchedEntityData.m_135353_(FamiliarEntity.class, EntityDataSerializers.f_135033_);
   public static Set<FamiliarEntity> FAMILIAR_SET = Collections.newSetFromMap(new WeakHashMap<>());
   public boolean terminatedFamiliar;
   public ResourceLocation holderID;
   public PersistentFamiliarData persistentData = new PersistentFamiliarData(new CompoundTag());
   public AnimationController<?> controller;
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public FamiliarEntity(EntityType<? extends PathfinderMob> p_i48575_1_, Level p_i48575_2_) {
      super(p_i48575_1_, p_i48575_2_);
      if (!this.f_19853_.f_46443_) {
         FAMILIAR_SET.add(this);
      }
   }

   public boolean m_6785_(double p_213397_1_) {
      return false;
   }

   public double getManaReserveModifier() {
      return this.manaReserveModifier;
   }

   public void m_6593_(@Nullable Component pName) {
      super.m_6593_(pName);
      this.persistentData.name = pName;
      this.syncTag();
   }

   public boolean m_6084_() {
      return super.m_6084_() && !this.terminatedFamiliar && (this.f_19853_.f_46443_ || FAMILIAR_SET.contains(this));
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.terminatedFamiliar) {
         this.m_142687_(RemovalReason.DISCARDED);
         FAMILIAR_SET.remove(this);
      }

      if (this.f_19853_.m_46467_() % 20L == 0L
         && !this.f_19853_.f_46443_
         && (this.getOwnerID() == null || ((ServerLevel)this.f_19853_).m_8791_(this.getOwnerID()) == null || this.terminatedFamiliar)) {
         this.m_142687_(RemovalReason.DISCARDED);
         this.terminatedFamiliar = true;
         FAMILIAR_SET.remove(this);
      }
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (source == DamageSource.f_19312_ || source == DamageSource.f_19310_ || source == DamageSource.f_19316_ || source == DamageSource.f_19315_) {
         return false;
      } else if (source.m_7639_() == null) {
         return false;
      } else {
         return source.m_7639_() == this.getOwner() ? false : super.m_6469_(source, amount);
      }
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(3, new FamiliarFollowGoal(this, 2.0, 6.0F, 4.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21346_.m_25352_(1, new FamOwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new FamOwnerHurtTargetGoal(this));
   }

   public PlayState walkPredicate(AnimationEvent<?> event) {
      return PlayState.CONTINUE;
   }

   @Override
   public void registerControllers(AnimationData data) {
      this.controller = new AnimationController<>(this, "walkController", 1.0F, this::walkPredicate);
      data.addAnimationController(this.controller);
   }

   public boolean canTeleport() {
      return this.getOwner() != null && this.getOwner().m_20096_();
   }

   @Nullable
   @Override
   public LivingEntity getOwner() {
      return !this.f_19853_.f_46443_ && this.getOwnerID() != null ? (LivingEntity)((ServerLevel)this.f_19853_).m_8791_(this.getOwnerID()) : null;
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(OWNER_UUID, Optional.empty());
      this.f_19804_.m_135372_(COLOR, "");
      this.f_19804_.m_135372_(COSMETIC, ItemStack.f_41583_);
   }

   @Override
   public ResourceLocation getHolderID() {
      return this.holderID;
   }

   @Override
   public void setHolderID(ResourceLocation id) {
      this.holderID = id;
   }

   @Nullable
   @Override
   public UUID getOwnerID() {
      return (UUID)((Optional)this.m_20088_().m_135370_(OWNER_UUID)).orElse(null);
   }

   @Override
   public void setOwnerID(UUID uuid) {
      this.m_20088_().m_135381_(OWNER_UUID, Optional.of(uuid));
   }

   @Override
   public ItemStack getCosmeticItem() {
      return (ItemStack)this.f_19804_.m_135370_(COSMETIC);
   }

   public void setCosmeticItem(ItemStack stack, boolean shouldDrop) {
      if (!((ItemStack)this.f_19804_.m_135370_(COSMETIC)).m_41619_() && shouldDrop) {
         this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), (ItemStack)this.f_19804_.m_135370_(COSMETIC)));
      }

      this.f_19804_.m_135381_(COSMETIC, stack);
      this.persistentData.cosmetic = stack;
      this.syncTag();
   }

   @Override
   public void setCosmeticItem(ItemStack stack) {
      this.setCosmeticItem(stack, true);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public static Builder attributes() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 100.0)
         .m_22268_(Attributes.f_22279_, 0.2)
         .m_22268_(Attributes.f_22280_, Attributes.f_22280_.m_22082_())
         .m_22268_(Attributes.f_22277_, 16.0);
   }

   public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
      return false;
   }

   protected boolean m_7341_(Entity p_184228_1_) {
      return false;
   }

   @Override
   public boolean onDispel(@Nullable LivingEntity caster) {
      if (!this.f_19853_.f_46443_ && this.getOwner() != null && this.getOwner().equals(caster)) {
         this.m_142687_(RemovalReason.DISCARDED);
         ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
         return true;
      } else {
         return false;
      }
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      if (this.getOwnerID() != null) {
         tag.m_128362_("ownerID", this.getOwnerID());
      }

      tag.m_128379_("terminated", this.terminatedFamiliar);
      tag.m_128365_("familiarData", this.getPersistentFamiliarData().toTag(this, new CompoundTag()));
      if (this.holderID != null) {
         tag.m_128359_("holderID", this.holderID.toString());
      }

      tag.m_128359_("color", (String)this.f_19804_.m_135370_(COLOR));
      if (!((ItemStack)this.f_19804_.m_135370_(COSMETIC)).m_41619_()) {
         CompoundTag cosmeticTag = new CompoundTag();
         ((ItemStack)this.f_19804_.m_135370_(COSMETIC)).m_41739_(cosmeticTag);
         tag.m_128365_("cosmetic", cosmeticTag);
      }
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      if (tag.m_128403_("ownerID")) {
         this.setOwnerID(tag.m_128342_("ownerID"));
      }

      this.terminatedFamiliar = tag.m_128471_("terminated");
      this.holderID = new ResourceLocation(tag.m_128461_("holderID"));
      this.persistentData = this.deserializePersistentData(tag.m_128469_("familiarData"));
      this.f_19804_.m_135381_(COLOR, tag.m_128461_("color"));
      this.f_19804_.m_135381_(COSMETIC, ItemStack.m_41712_(tag.m_128469_("cosmetic")));
      this.syncAfterPersistentFamiliarInit();
   }

   @Override
   public void onFamiliarSpawned(FamiliarSummonEvent event) {
      if (!this.f_19853_.f_46443_) {
         IFamiliar.super.onFamiliarSpawned(event);
         if (!event.getEntity().equals(this) && event.owner.equals(this.getOwner())) {
            this.terminatedFamiliar = true;
         }
      }
   }

   public String getColor() {
      return (String)this.f_19804_.m_135370_(COLOR);
   }

   public void setColor(DyeColor color) {
      this.setColor(color.m_41065_());
   }

   public void setColor(String color) {
      this.f_19804_.m_135381_(COLOR, color);
      this.getPersistentFamiliarData().color = color;
      this.syncTag();
   }

   public void setTagData(@Nullable CompoundTag tag) {
      this.persistentData = this.deserializePersistentData(tag != null && tag.m_128441_("familiarData") ? tag.m_128469_("familiarData") : new CompoundTag());
      this.syncAfterPersistentFamiliarInit();
   }

   public void syncTag() {
      IPlayerCap cap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(this.getOwner()).orElse(null);
      if (cap != null && this.persistentData != null) {
         cap.getFamiliarData(this.getHolderID()).entityTag.m_128365_("familiarData", this.persistentData.toTag(this, new CompoundTag()));
      }
   }

   public PersistentFamiliarData deserializePersistentData(CompoundTag tag) {
      return new PersistentFamiliarData(tag);
   }

   public PersistentFamiliarData getPersistentFamiliarData() {
      return this.persistentData;
   }

   public void syncAfterPersistentFamiliarInit() {
      this.m_6593_(this.persistentData.name);
      if (this.persistentData.color != null) {
         this.setColor(this.persistentData.color);
      }

      if (this.persistentData.cosmetic != null) {
         this.setCosmeticItem(this.persistentData.cosmetic, false);
      }
   }
}
