package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Eye_Of_Dungeon_Entity extends Entity implements ItemSupplier {
   private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.m_135353_(
      Eye_Of_Dungeon_Entity.class, EntityDataSerializers.f_135033_
   );
   private static final EntityDataAccessor<Integer> R = SynchedEntityData.m_135353_(Eye_Of_Dungeon_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> G = SynchedEntityData.m_135353_(Eye_Of_Dungeon_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> B = SynchedEntityData.m_135353_(Eye_Of_Dungeon_Entity.class, EntityDataSerializers.f_135028_);
   private double tx;
   private double ty;
   private double tz;
   private int life;

   public Eye_Of_Dungeon_Entity(EntityType<? extends Eye_Of_Dungeon_Entity> p_36957_, Level p_36958_) {
      super(p_36957_, p_36958_);
   }

   public Eye_Of_Dungeon_Entity(Level p_36960_, double p_36961_, double p_36962_, double p_36963_) {
      this((EntityType<? extends Eye_Of_Dungeon_Entity>)ModEntities.EYE_OF_DUNGEON.get(), p_36960_);
      this.m_6034_(p_36961_, p_36962_, p_36963_);
   }

   public ItemStack m_7846_() {
      return (ItemStack)this.m_20088_().m_135370_(DATA_ITEM_STACK);
   }

   public void setItem(ItemStack p_32046_) {
      this.m_20088_().m_135381_(DATA_ITEM_STACK, (ItemStack)Util.m_137469_(p_32046_.m_41777_(), p_36978_ -> p_36978_.m_41764_(1)));
   }

   private ItemStack getItemRaw() {
      return (ItemStack)this.m_20088_().m_135370_(DATA_ITEM_STACK);
   }

   public int getR() {
      return (Integer)this.f_19804_.m_135370_(R);
   }

   public void setR(int r) {
      this.f_19804_.m_135381_(R, r);
   }

   public int getG() {
      return (Integer)this.f_19804_.m_135370_(G);
   }

   public void setG(int g) {
      this.f_19804_.m_135381_(G, g);
   }

   public int getB() {
      return (Integer)this.f_19804_.m_135370_(B);
   }

   public void setB(int b) {
      this.f_19804_.m_135381_(B, b);
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(DATA_ITEM_STACK, ItemStack.f_41583_);
      this.m_20088_().m_135372_(R, 0);
      this.m_20088_().m_135372_(G, 0);
      this.m_20088_().m_135372_(B, 0);
   }

   public void m_6001_(double p_36984_, double p_36985_, double p_36986_) {
      this.m_20334_(p_36984_, p_36985_, p_36986_);
      if (this.f_19860_ == 0.0F && this.f_19859_ == 0.0F) {
         double d0 = Math.sqrt(p_36984_ * p_36984_ + p_36986_ * p_36986_);
         this.m_146922_((float)(Mth.m_14136_(p_36984_, p_36986_) * 180.0F / (float)Math.PI));
         this.m_146926_((float)(Mth.m_14136_(p_36985_, d0) * 180.0F / (float)Math.PI));
         this.f_19859_ = this.m_146908_();
         this.f_19860_ = this.m_146909_();
      }
   }

   public void signalTo(BlockPos p_36968_) {
      double d0 = (double)p_36968_.m_123341_();
      int i = p_36968_.m_123342_();
      double d1 = (double)p_36968_.m_123343_();
      double d2 = d0 - this.m_20185_();
      double d3 = d1 - this.m_20189_();
      double d4 = Math.sqrt(d2 * d2 + d3 * d3);
      if (d4 > 12.0) {
         this.tx = this.m_20185_() + d2 / d4 * 12.0;
         this.tz = this.m_20189_() + d3 / d4 * 12.0;
         this.ty = this.m_20186_() + 8.0;
      } else {
         this.tx = d0;
         this.ty = (double)i;
         this.tz = d1;
      }

      this.life = 0;
   }

   public void m_8119_() {
      if (this.m_7846_().m_41619_()) {
         this.m_146870_();
      } else {
         super.m_8119_();
         Vec3 vec3 = this.m_20184_();
         double d0 = this.m_20185_() + vec3.f_82479_;
         double d1 = this.m_20186_() + vec3.f_82480_;
         double d2 = this.m_20189_() + vec3.f_82481_;
         double d3 = vec3.m_165924_();
         this.m_146926_(lerpRotation(this.f_19860_, (float)(Mth.m_14136_(vec3.f_82480_, d3) * 180.0F / (float)Math.PI)));
         this.m_146922_(lerpRotation(this.f_19859_, (float)(Mth.m_14136_(vec3.f_82479_, vec3.f_82481_) * 180.0F / (float)Math.PI)));
         if (!this.f_19853_.f_46443_) {
            double d4 = this.tx - d0;
            double d5 = this.tz - d2;
            float f = (float)Math.sqrt(d4 * d4 + d5 * d5);
            float f1 = (float)Mth.m_14136_(d5, d4);
            double d6 = Mth.m_14139_(0.0025, d3, (double)f);
            double d7 = vec3.f_82480_;
            if (f < 1.0F) {
               d6 *= 0.8;
               d7 *= 0.8;
            }

            int j = this.m_20186_() < this.ty ? 1 : -1;
            vec3 = new Vec3(Math.cos((double)f1) * d6, d7 + ((double)j - d7) * 0.015F, Math.sin((double)f1) * d6);
            this.m_20256_(vec3);
         }

         float f2 = 0.25F;
         if (this.m_20069_()) {
            for (int i = 0; i < 4; i++) {
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123795_,
                     d0 - vec3.f_82479_ * 0.25,
                     d1 - vec3.f_82480_ * 0.25,
                     d2 - vec3.f_82481_ * 0.25,
                     vec3.f_82479_,
                     vec3.f_82480_,
                     vec3.f_82481_
                  );
            }
         } else {
            this.f_19853_
               .m_7106_(
                  new LightningParticle.OrbData(this.getR(), this.getG(), this.getB()),
                  d0 - vec3.f_82479_ * 0.25 + this.f_19796_.m_188500_() * 0.6 - 0.3,
                  d1 - vec3.f_82480_ * 0.25 - 0.5,
                  d2 - vec3.f_82481_ * 0.25 + this.f_19796_.m_188500_() * 0.6 - 0.3,
                  vec3.f_82479_,
                  vec3.f_82480_,
                  vec3.f_82481_
               );
         }

         if (!this.f_19853_.f_46443_) {
            this.m_6034_(d0, d1, d2);
            this.life++;
            if (this.life > 80 && !this.f_19853_.f_46443_) {
               this.m_5496_(SoundEvents.f_11897_, 1.0F, 1.0F);
               this.m_146870_();
               ItemEntity itemEntity = new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_7846_());
               itemEntity.m_146915_(true);
               this.f_19853_.m_7967_(itemEntity);
            }
         } else {
            this.m_20343_(d0, d1, d2);
         }
      }
   }

   public static float lerpRotation(float p_37274_, float p_37275_) {
      while (p_37275_ - p_37274_ < -180.0F) {
         p_37274_ -= 360.0F;
      }

      while (p_37275_ - p_37274_ >= 180.0F) {
         p_37274_ += 360.0F;
      }

      return Mth.m_14179_(0.2F, p_37274_, p_37275_);
   }

   public void m_7380_(CompoundTag p_36975_) {
      ItemStack itemstack = this.getItemRaw();
      if (!itemstack.m_41619_()) {
         p_36975_.m_128365_("Item", itemstack.m_41739_(new CompoundTag()));
      }

      p_36975_.m_128405_("R", this.getR());
      p_36975_.m_128405_("G", this.getG());
      p_36975_.m_128405_("B", this.getB());
   }

   public void m_7378_(CompoundTag p_36970_) {
      ItemStack itemstack = ItemStack.m_41712_(p_36970_.m_128469_("Item"));
      this.setItem(itemstack);
      this.setR(p_36970_.m_128451_("R"));
      this.setG(p_36970_.m_128451_("G"));
      this.setB(p_36970_.m_128451_("B"));
   }

   public float m_213856_() {
      return 1.0F;
   }

   public boolean m_6097_() {
      return false;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }
}
