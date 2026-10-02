package net.thirdlife.iterrpg.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.procedures.BoulderExplodeProcedure;

public class GrimBoulderEntity extends Monster {
   public GrimBoulderEntity(SpawnEntity packet, Level world) {
      this((EntityType<GrimBoulderEntity>)IterRpgModEntities.GRIM_BOULDER.get(), world);
   }

   public GrimBoulderEntity(EntityType<GrimBoulderEntity> type, Level world) {
      super(type, world);
      this.f_19793_ = 0.6F;
      this.f_21364_ = 0;
      this.m_21557_(false);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8099_() {
      super.m_8099_();
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.gilded_blackstone.break"));
   }

   public void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_((SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.gilded_blackstone.break")), 0.15F, 1.0F);
   }

   public SoundEvent m_7975_(DamageSource ds) {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.gilded_blackstone.break"));
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.gilded_blackstone.break"));
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

   public void m_6075_() {
      super.m_6075_();
      BoulderExplodeProcedure.execute(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this);
   }

   public static void init() {
   }

   public static Builder createAttributes() {
      Builder builder = Mob.m_21552_();
      builder = builder.m_22268_(Attributes.f_22279_, 0.3);
      builder = builder.m_22268_(Attributes.f_22276_, 20.0);
      builder = builder.m_22268_(Attributes.f_22284_, 0.0);
      builder = builder.m_22268_(Attributes.f_22281_, 69.0);
      builder = builder.m_22268_(Attributes.f_22277_, 16.0);
      return builder.m_22268_(Attributes.f_22278_, 2.0);
   }
}
