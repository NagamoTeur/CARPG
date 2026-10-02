package com.aizistral.enigmaticlegacy.entities;

import com.aizistral.enigmaticlegacy.helpers.PotionHelper;
import com.aizistral.enigmaticlegacy.registries.EnigmaticEntities;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkHooks;

public class EnigmaticPotionEntity extends ThrowableItemProjectile implements ItemSupplier {
   private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.m_135353_(EnigmaticPotionEntity.class, EntityDataSerializers.f_135033_);

   public EnigmaticPotionEntity(EntityType<EnigmaticPotionEntity> type, Level world) {
      super(type, world);
   }

   public EnigmaticPotionEntity(Level world, LivingEntity entity) {
      super(EnigmaticEntities.ENIGMATIC_POTION, entity, world);
   }

   public EnigmaticPotionEntity(Level world, double x, double y, double z) {
      super(EnigmaticEntities.ENIGMATIC_POTION, x, y, z, world);
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(ITEM, ItemStack.f_41583_);
   }

   public ItemStack m_7846_() {
      ItemStack itemstack = (ItemStack)this.m_20088_().m_135370_(ITEM);
      return PotionHelper.isAdvancedPotion(itemstack) ? itemstack : new ItemStack(Items.f_42736_);
   }

   public void m_37446_(ItemStack stack) {
      this.m_20088_().m_135381_(ITEM, stack.m_41777_());
   }

   protected float m_7139_() {
      return 0.05F;
   }

   public void m_8119_() {
      super.m_8119_();
   }

   protected void m_6532_(HitResult result) {
      if (!this.f_19853_.f_46443_) {
         ItemStack itemstack = this.m_7846_();
         List<MobEffectInstance> list = PotionHelper.getEffects(itemstack);
         int i = 2002;
         if (list != null && !list.isEmpty()) {
            if (this.isLingering()) {
               this.makeAreaOfEffectCloud(itemstack, list);
            } else {
               this.triggerSplash(list, result.m_6662_() == Type.ENTITY ? ((EntityHitResult)result).m_82443_() : null);
            }

            for (MobEffectInstance instance : list) {
               if (instance.m_19544_().m_8093_()) {
                  i = 2007;
               }
            }
         }

         this.f_19853_.m_46796_(i, new BlockPos(this.m_20183_()), PotionHelper.getColor(itemstack));
         this.m_146870_();
      }
   }

   private void triggerSplash(List<MobEffectInstance> p_213888_1_, @Nullable Entity p_213888_2_) {
      AABB axisalignedbb = this.m_20191_().m_82377_(4.0, 2.0, 4.0);
      List<LivingEntity> list = this.f_19853_.m_45976_(LivingEntity.class, axisalignedbb);
      if (!list.isEmpty()) {
         for (LivingEntity livingentity : list) {
            if (livingentity.m_5801_()) {
               double d0 = this.m_20280_(livingentity);
               if (d0 < 16.0) {
                  double d1 = 1.0 - Math.sqrt(d0) / 4.0;
                  if (livingentity == p_213888_2_) {
                     d1 = 1.0;
                  }

                  for (MobEffectInstance effectinstance : p_213888_1_) {
                     MobEffect effect = effectinstance.m_19544_();
                     if (effect.m_8093_()) {
                        effect.m_19461_(this, this.m_37282_(), livingentity, effectinstance.m_19564_(), d1);
                     } else {
                        int i = (int)(d1 * (double)effectinstance.m_19557_() + 0.5);
                        if (i > 20) {
                           livingentity.m_7292_(
                              new MobEffectInstance(effect, i, effectinstance.m_19564_(), effectinstance.m_19571_(), effectinstance.m_19572_())
                           );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void makeAreaOfEffectCloud(ItemStack stack, List<MobEffectInstance> list) {
      AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_());
      areaeffectcloudentity.m_19718_((LivingEntity)this.m_37282_());
      areaeffectcloudentity.m_19712_(3.0F);
      areaeffectcloudentity.m_19732_(-0.5F);
      areaeffectcloudentity.m_19740_(10);
      areaeffectcloudentity.m_19738_(-areaeffectcloudentity.m_19743_() / (float)areaeffectcloudentity.m_19748_());

      for (MobEffectInstance effectInstance : list) {
         areaeffectcloudentity.m_19716_(
            new MobEffectInstance(
               effectInstance.m_19544_(), effectInstance.m_19557_() / 4, effectInstance.m_19564_(), effectInstance.m_19571_(), effectInstance.m_19572_()
            )
         );
      }

      areaeffectcloudentity.m_19714_(PotionHelper.getColor(stack));
      this.f_19853_.m_7967_(areaeffectcloudentity);
   }

   private boolean isLingering() {
      return this.m_7846_().m_41720_() == EnigmaticItems.ULTIMATE_POTION_LINGERING;
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      ItemStack itemstack = ItemStack.m_41712_(compound.m_128469_("Potion"));
      if (itemstack.m_41619_()) {
         this.m_146870_();
      } else {
         this.m_37446_(itemstack);
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      ItemStack itemstack = this.m_7846_();
      if (!itemstack.m_41619_()) {
         compound.m_128365_("Potion", itemstack.m_41739_(new CompoundTag()));
      }
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected Item m_7881_() {
      return Items.f_42736_;
   }
}
