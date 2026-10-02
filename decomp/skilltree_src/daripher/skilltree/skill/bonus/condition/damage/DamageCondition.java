package daripher.skilltree.skill.bonus.condition.damage;

import daripher.skilltree.init.PSTRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;

public interface DamageCondition {
   boolean met(DamageSource var1);

   default String getDescriptionId() {
      ResourceLocation id = PSTRegistries.DAMAGE_CONDITIONS.get().getKey(this.getSerializer());
      return "damage_condition.%s.%s".formatted(id.m_135827_(), id.m_135815_());
   }

   default MutableComponent getTooltip() {
      return Component.m_237115_(this.getDescriptionId());
   }

   default MutableComponent getTooltip(String type) {
      return Component.m_237115_(this.getDescriptionId() + "." + type);
   }

   DamageCondition.Serializer getSerializer();

   public interface Serializer extends daripher.skilltree.data.serializers.Serializer<DamageCondition> {
      DamageCondition createDefaultInstance();
   }
}
