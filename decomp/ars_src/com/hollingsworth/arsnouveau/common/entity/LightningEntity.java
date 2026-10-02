package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class LightningEntity extends LightningBolt {
   private int lightningState;
   public long boltVertex;
   private int boltLivingTime;
   private boolean effectOnly;
   List<Integer> f_147134_ = new ArrayList<>();
   @Nullable
   private ServerPlayer caster;
   public float amps;
   public int extendTimes;
   public float ampScalar;
   public float wetBonus;

   public LightningEntity(EntityType<? extends LightningBolt> p_i231491_1_, Level world) {
      super(p_i231491_1_, world);
      this.f_19811_ = true;
      this.lightningState = 2;
      this.boltVertex = this.f_19796_.m_188505_();
      this.boltLivingTime = this.f_19796_.m_188503_(3) + 1;
   }

   public void m_20874_(boolean effectOnly) {
      this.effectOnly = effectOnly;
   }

   public SoundSource m_5720_() {
      return SoundSource.WEATHER;
   }

   public void m_20879_(@Nullable ServerPlayer casterIn) {
      this.caster = casterIn;
   }

   public void m_8119_() {
      this.m_6075_();
      if (this.lightningState == 2) {
         Difficulty difficulty = this.f_19853_.m_46791_();
         this.f_19853_
            .m_6263_(
               null,
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               SoundEvents.f_12090_,
               SoundSource.WEATHER,
               1.0F,
               0.8F + this.f_19796_.m_188501_() * 0.2F
            );
         this.f_19853_
            .m_6263_(
               null,
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               SoundEvents.f_12089_,
               SoundSource.WEATHER,
               1.0F,
               0.5F + this.f_19796_.m_188501_() * 0.2F
            );
      }

      this.lightningState--;
      if (this.lightningState < 0) {
         if (this.boltLivingTime == 0) {
            this.m_142687_(RemovalReason.DISCARDED);
         } else if (this.lightningState < -this.f_19796_.m_188503_(10)) {
            this.boltLivingTime--;
            this.lightningState = 1;
            this.boltVertex = this.f_19796_.m_188505_();
         }
      }

      if (this.lightningState >= 0) {
         if (!(this.f_19853_ instanceof ServerLevel)) {
            this.f_19853_.m_6580_(2);
         } else if (!this.effectOnly) {
            List<Entity> list = this.f_19853_
               .m_6249_(
                  this,
                  new AABB(
                     this.m_20185_() - 3.0,
                     this.m_20186_() - 3.0,
                     this.m_20189_() - 3.0,
                     this.m_20185_() + 3.0,
                     this.m_20186_() + 6.0 + 3.0,
                     this.m_20189_() + 3.0
                  ),
                  Entity::m_6084_
               );

            for (Entity entity : list) {
               if (!ForgeEventFactory.onEntityStruckByLightning(entity, this)) {
                  float origDamage = this.getDamage();
                  this.setDamage(this.getDamage(entity));
                  EntityStruckByLightningEvent event = new EntityStruckByLightningEvent(entity, this);
                  MinecraftForge.EVENT_BUS.post(event);
                  if (!event.isCanceled()) {
                     entity.m_8038_((ServerLevel)this.f_19853_, this);
                     this.setDamage(origDamage);
                     if (!this.f_19853_.f_46443_ && !this.f_147134_.contains(entity.m_19879_()) && entity instanceof LivingEntity) {
                        MobEffectInstance effectInstance = ((LivingEntity)entity).m_21124_((MobEffect)ModPotions.SHOCKED_EFFECT.get());
                        int amp = effectInstance != null ? effectInstance.m_19564_() : -1;
                        ((LivingEntity)entity)
                           .m_7292_(new MobEffectInstance((MobEffect)ModPotions.SHOCKED_EFFECT.get(), 200 + 200 * this.extendTimes, Math.min(2, amp + 1)));
                     }

                     if (!this.f_19853_.f_46443_ && !this.f_147134_.contains(entity.m_19879_())) {
                        this.f_147134_.add(entity.m_19879_());
                     }
                  }
               }
            }

            if (this.caster != null) {
               CriteriaTriggers.f_10554_.m_21721_(this.caster, list);
            }
         }
      }
   }

   private void igniteBlocks(int extraIgnitions) {
      if (!this.effectOnly && !this.f_19853_.f_46443_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46131_)) {
         BlockPos blockpos = this.m_20183_();
         BlockState blockstate = BaseFireBlock.m_49245_(this.f_19853_, blockpos);
         if (this.f_19853_.m_8055_(blockpos).m_60795_() && blockstate.m_60710_(this.f_19853_, blockpos)) {
            this.f_19853_.m_46597_(blockpos, blockstate);
         }

         for (int i = 0; i < extraIgnitions; i++) {
            BlockPos blockpos1 = blockpos.m_7918_(this.f_19796_.m_188503_(3) - 1, this.f_19796_.m_188503_(3) - 1, this.f_19796_.m_188503_(3) - 1);
            blockstate = BaseFireBlock.m_49245_(this.f_19853_, blockpos1);
            if (this.f_19853_.m_8055_(blockpos1).m_60795_() && blockstate.m_60710_(this.f_19853_, blockpos1)) {
               this.f_19853_.m_46597_(blockpos1, blockstate);
            }
         }
      }
   }

   public float getDamage(Entity entity) {
      float baseDamage = this.getDamage() + this.ampScalar * this.amps + (entity.m_20070_() ? this.wetBonus : 0.0F);
      int multiplier = 1;

      for (ItemStack i : entity.m_6168_()) {
         IEnergyStorage energyStorage = (IEnergyStorage)i.getCapability(ForgeCapabilities.ENERGY).orElse(null);
         if (energyStorage != null) {
            multiplier++;
         }
      }

      if (entity instanceof LivingEntity) {
         IEnergyStorage energyStorage = (IEnergyStorage)((LivingEntity)entity).m_21205_().getCapability(ForgeCapabilities.ENERGY).orElse(null);
         if (energyStorage != null) {
            multiplier++;
         }

         energyStorage = (IEnergyStorage)((LivingEntity)entity).m_21206_().getCapability(ForgeCapabilities.ENERGY).orElse(null);
         if (energyStorage != null) {
            multiplier++;
         }
      }

      return baseDamage * (float)multiplier;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6783_(double distance) {
      double d0 = 64.0 * m_20150_();
      return distance < d0 * d0;
   }

   protected void m_8097_() {
   }

   protected void m_7378_(CompoundTag compound) {
   }

   protected void m_7380_(CompoundTag compound) {
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.LIGHTNING_ENTITY.get();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public LightningEntity(SpawnEntity packet, Level world) {
      super((EntityType)ModEntities.LIGHTNING_ENTITY.get(), world);
   }
}
