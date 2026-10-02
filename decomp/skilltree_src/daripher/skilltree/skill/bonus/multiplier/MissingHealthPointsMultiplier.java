package daripher.skilltree.skill.bonus.multiplier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.init.PSTLivingMultipliers;
import daripher.skilltree.skill.bonus.SkillBonus;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

public final class MissingHealthPointsMultiplier implements LivingMultiplier {
   private float divisor;

   public MissingHealthPointsMultiplier(float divisor) {
      this.divisor = divisor;
   }

   @Override
   public float getValue(LivingEntity entity) {
      return (float)((int)((entity.m_21233_() - entity.m_21223_()) / this.divisor));
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, SkillBonus.Target target) {
      String multiplierDescription = this.getDescriptionId(target);
      String divisorDescription = TooltipHelper.formatNumber((double)this.divisor);
      return Component.m_237110_(multiplierDescription, new Object[]{bonusTooltip, divisorDescription});
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<LivingMultiplier> consumer) {
      editor.addLabel(0, 0, "Divisor", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.divisor)
         .setNumericFilter(value -> value > 0.0)
         .setNumericResponder(value -> this.selectDivisor(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectDivisor(Consumer<LivingMultiplier> consumer, Double value) {
      this.setDivisor(value.floatValue());
      consumer.accept(this);
   }

   @Override
   public LivingMultiplier.Serializer getSerializer() {
      return (LivingMultiplier.Serializer)PSTLivingMultipliers.MISSING_HEALTH_POINTS.get();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MissingHealthPointsMultiplier that = (MissingHealthPointsMultiplier)o;
         return Float.compare(this.divisor, that.divisor) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.divisor);
   }

   public void setDivisor(float divisor) {
      this.divisor = divisor;
   }

   public static class Serializer implements LivingMultiplier.Serializer {
      public LivingMultiplier deserialize(JsonObject json) throws JsonParseException {
         return new MissingHealthPointsMultiplier(json.get("divisor").getAsFloat());
      }

      public void serialize(JsonObject json, LivingMultiplier multiplier) {
         if (multiplier instanceof MissingHealthPointsMultiplier aMultiplier) {
            json.addProperty("divisor", aMultiplier.divisor);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingMultiplier deserialize(CompoundTag tag) {
         float divisor = tag.m_128457_("divisor");
         return new MissingHealthPointsMultiplier(divisor);
      }

      public CompoundTag serialize(LivingMultiplier multiplier) {
         if (multiplier instanceof MissingHealthPointsMultiplier aMultiplier) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("divisor", aMultiplier.divisor);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingMultiplier deserialize(FriendlyByteBuf buf) {
         return new MissingHealthPointsMultiplier(buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, LivingMultiplier multiplier) {
         if (multiplier instanceof MissingHealthPointsMultiplier aMultiplier) {
            buf.writeFloat(aMultiplier.divisor);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingMultiplier createDefaultInstance() {
         return new MissingHealthPointsMultiplier(2.0F);
      }
   }
}
