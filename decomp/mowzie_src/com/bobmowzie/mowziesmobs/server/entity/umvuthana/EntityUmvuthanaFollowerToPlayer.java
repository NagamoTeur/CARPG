package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityUmvuthanaFollowerToPlayer extends EntityUmvuthanaFollower<Player> {
   private static final EntityDataAccessor<ItemStack> MASK_STORED = SynchedEntityData.m_135353_(
      EntityUmvuthanaFollowerToPlayer.class, EntityDataSerializers.f_135033_
   );
   @OnlyIn(Dist.CLIENT)
   public Vec3[] feetPos;

   public EntityUmvuthanaFollowerToPlayer(EntityType<? extends EntityUmvuthanaFollowerToPlayer> type, Level world) {
      this(type, world, null);
   }

   public EntityUmvuthanaFollowerToPlayer(EntityType<? extends EntityUmvuthanaFollowerToPlayer> type, Level world, Player leader) {
      super(type, world, Player.class, leader);
      this.f_21364_ = 0;
      if (world.f_46443_) {
         this.feetPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(MASK_STORED, new ItemStack(ItemHandler.UMVUTHANA_MASK_FURY, 1));
   }

   @Override
   protected SoundEvent m_7515_() {
      return (double)this.f_19796_.m_188501_() < 0.5 ? null : super.m_7515_();
   }

   @Override
   public void m_8119_() {
      if (this.f_19797_ > 30 && (this.getLeader() == null || this.getLeader().m_21223_() <= 0.0F)) {
         this.deactivate();
      }

      super.m_8119_();
      if (this.f_19853_.f_46443_ && this.feetPos != null && this.feetPos.length > 0 && this.active) {
         this.feetPos[0] = this.m_20182_().m_82520_(0.0, 0.05F, 0.0);
         if (this.f_19797_ % 10 == 0) {
            AdvancedParticleBase.spawnParticle(
               this.f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
               this.feetPos[0].m_7096_(),
               this.feetPos[0].m_7098_(),
               this.feetPos[0].m_7094_(),
               0.0,
               0.0,
               0.0,
               false,
               0.0,
               Math.PI / 2,
               0.0,
               0.0,
               1.5,
               1.0,
               0.8745098F,
               0.25882354F,
               1.0,
               1.0,
               15.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.PinLocation(this.feetPos),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 10.0F), false
                  )
               }
            );
         }
      }
   }

   protected InteractionResult m_6071_(Player playerIn, InteractionHand hand) {
      if (playerIn == this.leader) {
         this.deactivate();
      }

      return super.m_6071_(playerIn, hand);
   }

   private void deactivate() {
      if (this.getActive() && this.getActiveAbilityType() != DEACTIVATE_ABILITY) {
         AbilityHandler.INSTANCE.sendAbilityMessage(this, DEACTIVATE_ABILITY);
         this.m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_RETRACT.get(), 1.0F, 1.0F);
      }
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().m_22268_(Attributes.f_22281_, 7.0).m_22268_(Attributes.f_22276_, 20.0);
   }

   @Override
   protected int getGroupCircleTick() {
      PlayerCapability.IPlayerCapability capability = this.getPlayerCapability();
      return capability == null ? 0 : capability.getTribeCircleTick();
   }

   @Override
   protected int getPackSize() {
      PlayerCapability.IPlayerCapability capability = this.getPlayerCapability();
      return capability == null ? 0 : capability.getPackSize();
   }

   @Override
   protected void addAsPackMember() {
      PlayerCapability.IPlayerCapability capability = this.getPlayerCapability();
      if (capability != null) {
         capability.addPackMember(this);
      }
   }

   @Override
   protected void removeAsPackMember() {
      PlayerCapability.IPlayerCapability capability = this.getPlayerCapability();
      if (capability != null) {
         capability.removePackMember(this);
      }
   }

   private PlayerCapability.IPlayerCapability getPlayerCapability() {
      return CapabilityHandler.getCapability(this.leader, CapabilityHandler.PLAYER_CAPABILITY);
   }

   @Override
   public boolean isUmvuthiDevoted() {
      return false;
   }

   @Nullable
   @Override
   protected ResourceLocation m_7582_() {
      return null;
   }

   @Nullable
   public UUID getOwnerId() {
      return this.getLeader() == null ? null : this.getLeader().m_20148_();
   }

   @Nullable
   public Entity getOwner() {
      return this.leader;
   }

   public boolean isTeleportFriendlyBlock(int x, int z, int y, int xOffset, int zOffset) {
      BlockPos blockpos = new BlockPos(x + xOffset, y - 1, z + zOffset);
      BlockState iblockstate = this.f_19853_.m_8055_(blockpos);
      return iblockstate.m_60643_(this.f_19853_, blockpos, this.m_6095_())
         && this.f_19853_.m_46859_(blockpos.m_7494_())
         && this.f_19853_.m_46859_(blockpos.m_6630_(2));
   }

   public ItemStack getStoredMask() {
      return (ItemStack)this.m_20088_().m_135370_(MASK_STORED);
   }

   public void setStoredMask(ItemStack mask) {
      this.m_20088_().m_135381_(MASK_STORED, mask);
   }

   @Override
   protected ItemStack getDeactivatedMask(ItemUmvuthanaMask mask) {
      return this.getStoredMask();
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      CompoundTag compoundnbt = compound.m_128469_("storedMask");
      this.setStoredMask(ItemStack.m_41712_(compoundnbt));
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      if (!this.getStoredMask().m_41619_()) {
         compound.m_128365_("storedMask", this.getStoredMask().m_41739_(new CompoundTag()));
      }
   }
}
