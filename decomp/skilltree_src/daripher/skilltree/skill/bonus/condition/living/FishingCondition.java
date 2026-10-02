package daripher.skilltree.skill.bonus.condition.living;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.init.PSTLivingConditions;
import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public record FishingCondition() implements LivingCondition {
   @Override
   public boolean met(LivingEntity living) {
      if (living instanceof Player player && player.f_36083_ != null) {
         return true;
      }

      return false;
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, String target) {
      String key = this.getDescriptionId();
      MutableComponent targetDescription = Component.m_237115_("%s.target.%s".formatted(key, target));
      return Component.m_237110_(key, new Object[]{bonusTooltip, targetDescription});
   }

   @Override
   public LivingCondition.Serializer getSerializer() {
      return (LivingCondition.Serializer)PSTLivingConditions.FISHING.get();
   }

   @Override
   public boolean equals(Object o) {
      return this == o ? true : o != null && this.getClass() == o.getClass();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getSerializer());
   }

   public static class Serializer implements LivingCondition.Serializer {
      public LivingCondition deserialize(JsonObject json) throws JsonParseException {
         return new FishingCondition();
      }

      public void serialize(JsonObject json, LivingCondition condition) {
         if (!(condition instanceof FishingCondition)) {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(CompoundTag tag) {
         return new FishingCondition();
      }

      public CompoundTag serialize(LivingCondition condition) {
         if (!(condition instanceof FishingCondition)) {
            throw new IllegalArgumentException();
         } else {
            return new CompoundTag();
         }
      }

      public LivingCondition deserialize(FriendlyByteBuf buf) {
         return new FishingCondition();
      }

      public void serialize(FriendlyByteBuf buf, LivingCondition condition) {
         if (!(condition instanceof FishingCondition)) {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingCondition createDefaultInstance() {
         return new FishingCondition();
      }
   }
}
