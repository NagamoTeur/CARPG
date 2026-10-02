package com.github.L_Ender.cataclysm.entity.Pet;

import com.github.L_Ender.cataclysm.entity.etc.IFollower;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AnimationPet extends TamableAnimal implements IFollower {
   private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.m_135353_(AnimationPet.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.m_135353_(AnimationPet.class, EntityDataSerializers.f_135028_);

   public AnimationPet(EntityType entity, Level world) {
      super(entity, world);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(COMMAND, 0);
      this.f_19804_.m_135372_(SITTING, false);
   }

   public int getCommand() {
      return (Integer)this.f_19804_.m_135370_(COMMAND);
   }

   public void setCommand(int command) {
      this.f_19804_.m_135381_(COMMAND, command);
   }

   public boolean isSitting() {
      return (Boolean)this.f_19804_.m_135370_(SITTING);
   }

   public void m_21839_(boolean sit) {
      this.f_19804_.m_135381_(SITTING, sit);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("CmPetSitting", this.isSitting());
      compound.m_128405_("Command", this.getCommand());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.m_21839_(compound.m_128471_("CmPetSitting"));
      this.setCommand(compound.m_128451_("Command"));
   }

   public static void setConfigattribute(LivingEntity entity, double hpconfig, double dmgconfig) {
      AttributeInstance maxHealthAttr = entity.m_21051_(Attributes.f_22276_);
      if (maxHealthAttr != null) {
         double difference = maxHealthAttr.m_22115_() * hpconfig - maxHealthAttr.m_22115_();
         maxHealthAttr.m_22118_(
            new AttributeModifier(UUID.fromString("36b1441b-4dd7-4ba3-90a0-0618bb37dede"), "Health config multiplier", difference, Operation.ADDITION)
         );
         entity.m_21153_(entity.m_21233_());
      }

      AttributeInstance attackDamageAttr = entity.m_21051_(Attributes.f_22281_);
      if (attackDamageAttr != null) {
         double difference = attackDamageAttr.m_22115_() * dmgconfig - attackDamageAttr.m_22115_();
         attackDamageAttr.m_22118_(
            new AttributeModifier(UUID.fromString("6920e51b-c80a-4482-831c-f630a35fa2d7"), "Attack config multiplier", difference, Operation.ADDITION)
         );
      }
   }

   public boolean m_6785_(double p_21542_) {
      return false;
   }

   public void circleEntity(LivingEntity target, float radius, float speed, boolean direction, int circleFrame, float offset, float moveSpeedMultiplier) {
      int directionInt = direction ? 1 : -1;
      double t = (double)(directionInt * circleFrame) * 0.5 * (double)speed / (double)radius + (double)offset;
      Vec3 movePos = target.m_20182_().m_82520_((double)radius * Math.cos(t), 0.0, (double)radius * Math.sin(t));
      this.m_21573_().m_26519_(movePos.f_82479_, movePos.f_82480_, movePos.f_82481_, (double)(speed * moveSpeedMultiplier));
   }

   @Override
   public boolean shouldFollow() {
      return false;
   }

   public AgeableMob m_142606_(ServerLevel p_146743_, AgeableMob p_146744_) {
      return null;
   }
}
