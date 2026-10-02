package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.entity.ISummon;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityDummy extends PathfinderMob implements ISummon {
   @OnlyIn(Dist.CLIENT)
   private PlayerInfo playerInfo;
   public int ticksLeft;
   private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.m_135353_(EntityDummy.class, EntityDataSerializers.f_135041_);

   public EntityDummy(EntityType<? extends PathfinderMob> p_i48577_1_, Level p_i48577_2_) {
      super(p_i48577_1_, p_i48577_2_);
   }

   public EntityDummy(Level world) {
      super((EntityType)ModEntities.ENTITY_DUMMY.get(), world);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(0, new FloatGoal(this));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(OWNER_UUID, Optional.of(Util.f_137441_));
   }

   public Iterable<ItemStack> m_6168_() {
      return new ArrayList<>();
   }

   public boolean m_5833_() {
      return false;
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         if (this.f_19853_.m_46467_() % 10L == 0L && this.f_19853_.m_46003_(this.m_21805_()) == null) {
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
            this.m_142687_(RemovalReason.DISCARDED);
            this.onSummonDeath(this.f_19853_, null, false);
            return;
         }

         this.ticksLeft--;
         if (this.ticksLeft <= 0) {
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_());
            this.m_142687_(RemovalReason.DISCARDED);
            this.onSummonDeath(this.f_19853_, null, true);
         }
      }
   }

   public void m_6667_(DamageSource cause) {
      super.m_6667_(cause);
      this.onSummonDeath(this.f_19853_, cause, false);
   }

   public ItemStack m_6844_(EquipmentSlot p_184582_1_) {
      if (!this.f_19853_.f_46443_) {
         return ItemStack.f_41583_;
      } else {
         ItemStack heldStack = this.f_19853_.m_46003_(this.m_21805_()) != null
            ? this.f_19853_.m_46003_(this.m_21805_()).m_6844_(p_184582_1_)
            : ItemStack.f_41583_;
         return heldStack.m_41720_() == BlockRegistry.MOB_JAR.m_5456_() ? new ItemStack(BlockRegistry.MOB_JAR.m_5456_()) : heldStack;
      }
   }

   public void m_8061_(EquipmentSlot p_184201_1_, ItemStack p_184201_2_) {
   }

   public ResourceLocation getSkinTextureLocation() {
      PlayerInfo networkplayerinfo = this.getPlayerInfo();
      return networkplayerinfo == null ? DefaultPlayerSkin.m_118627_(this.m_21805_()) : networkplayerinfo.m_105337_();
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   protected PlayerInfo getPlayerInfo() {
      if (this.playerInfo == null) {
         this.playerInfo = Minecraft.m_91087_().m_91403_().m_104949_(this.m_21805_());
      }

      return this.playerInfo;
   }

   public Component m_7755_() {
      return (Component)(this.f_19853_.m_46003_(this.m_21805_()) == null ? Component.m_237113_("") : this.f_19853_.m_46003_(this.m_21805_()).m_7755_());
   }

   public Component m_5446_() {
      MutableComponent iformattabletextcomponent = Component.m_237113_("");
      return iformattabletextcomponent.m_7220_(PlayerTeam.m_83348_(this.m_5647_(), this.m_7755_()));
   }

   public HumanoidArm m_5737_() {
      return HumanoidArm.RIGHT;
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128405_("left", this.ticksLeft);
      this.writeOwner(tag);
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      this.ticksLeft = tag.m_128451_("left");
      if (this.m_21805_() != null) {
         this.setOwnerID(tag.m_128342_("owner"));
      }
   }

   @Override
   public int getTicksLeft() {
      return this.ticksLeft;
   }

   @Override
   public void setTicksLeft(int ticks) {
      this.ticksLeft = ticks;
   }

   @Nullable
   @Override
   public UUID m_21805_() {
      return ((Optional)this.m_20088_().m_135370_(OWNER_UUID)).isEmpty() ? this.m_20148_() : (UUID)((Optional)this.m_20088_().m_135370_(OWNER_UUID)).get();
   }

   @Override
   public void setOwnerID(UUID uuid) {
      this.m_20088_().m_135381_(OWNER_UUID, Optional.ofNullable(uuid));
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isSlim() {
      return this.playerInfo != null ? this.playerInfo.m_105336_().equals("slim") : false;
   }
}
