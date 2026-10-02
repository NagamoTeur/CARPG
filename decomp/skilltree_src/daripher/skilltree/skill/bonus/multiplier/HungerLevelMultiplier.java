package daripher.skilltree.skill.bonus.multiplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.init.PSTLivingMultipliers;
import daripher.skilltree.skill.bonus.SkillBonus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HungerLevelMultiplier implements LivingMultiplier {
   @Override
   public float getValue(LivingEntity entity) {
      return entity instanceof Player player ? (float)player.m_36324_().m_38702_() : 1.0F;
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, SkillBonus.Target target) {
      return Component.m_237110_(this.getDescriptionId(target), new Object[]{bonusTooltip});
   }

   @Override
   public LivingMultiplier.Serializer getSerializer() {
      return (LivingMultiplier.Serializer)PSTLivingMultipliers.FOOD_LEVEL.get();
   }

   @Override
   public boolean equals(Object o) {
      return this == o ? true : o != null && this.getClass() == o.getClass();
   }

   public static class Serializer implements LivingMultiplier.Serializer {
      public LivingMultiplier deserialize(JsonObject json) throws JsonParseException {
         return new HungerLevelMultiplier();
      }

      public void serialize(JsonObject json, LivingMultiplier object) {
      }

      public LivingMultiplier deserialize(CompoundTag tag) {
         return new HungerLevelMultiplier();
      }

      public CompoundTag serialize(LivingMultiplier object) {
         return new CompoundTag();
      }

      public LivingMultiplier deserialize(FriendlyByteBuf buf) {
         return new HungerLevelMultiplier();
      }

      public void serialize(FriendlyByteBuf buf, LivingMultiplier object) {
      }

      @Override
      public LivingMultiplier createDefaultInstance() {
         return new HungerLevelMultiplier();
      }
   }
}
