package daripher.skilltree.skill.bonus.multiplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.init.PSTLivingMultipliers;
import daripher.skilltree.skill.bonus.SkillBonus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DistanceToTargetMultiplier implements LivingMultiplier {
   @Override
   public float getValue(LivingEntity entity) {
      if (entity instanceof Player player) {
         int lastTargetId = player.getPersistentData().m_128451_("LastAttackTarget");
         Entity target = entity.f_19853_.m_6815_(lastTargetId);
         return target == null ? 1.0F : target.m_20270_(entity);
      } else {
         return 1.0F;
      }
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, SkillBonus.Target target) {
      return Component.m_237110_(this.getDescriptionId(target), new Object[]{bonusTooltip});
   }

   @Override
   public LivingMultiplier.Serializer getSerializer() {
      return (LivingMultiplier.Serializer)PSTLivingMultipliers.DISTANCE_TO_TARGET.get();
   }

   @Override
   public boolean equals(Object o) {
      return this == o ? true : o != null && this.getClass() == o.getClass();
   }

   public static class Serializer implements LivingMultiplier.Serializer {
      public LivingMultiplier deserialize(JsonObject json) throws JsonParseException {
         return new DistanceToTargetMultiplier();
      }

      public void serialize(JsonObject json, LivingMultiplier object) {
      }

      public LivingMultiplier deserialize(CompoundTag tag) {
         return new DistanceToTargetMultiplier();
      }

      public CompoundTag serialize(LivingMultiplier object) {
         return new CompoundTag();
      }

      public LivingMultiplier deserialize(FriendlyByteBuf buf) {
         return new DistanceToTargetMultiplier();
      }

      public void serialize(FriendlyByteBuf buf, LivingMultiplier object) {
      }

      @Override
      public LivingMultiplier createDefaultInstance() {
         return new DistanceToTargetMultiplier();
      }
   }
}
