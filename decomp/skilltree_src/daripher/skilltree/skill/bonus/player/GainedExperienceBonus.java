package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.skill.bonus.SkillBonus;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class GainedExperienceBonus implements SkillBonus<GainedExperienceBonus> {
   private GainedExperienceBonus.ExperienceSource experienceSource;
   private float multiplier;

   public GainedExperienceBonus(float multiplier, GainedExperienceBonus.ExperienceSource source) {
      this.multiplier = multiplier;
      this.experienceSource = source;
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.GAINED_EXPERIENCE.get();
   }

   public GainedExperienceBonus copy() {
      return new GainedExperienceBonus(this.multiplier, this.experienceSource);
   }

   public GainedExperienceBonus multiply(double multiplier) {
      this.multiplier = (float)((double)this.multiplier * multiplier);
      return this;
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      return other instanceof GainedExperienceBonus otherBonus ? Objects.equals(otherBonus.experienceSource, this.experienceSource) : false;
   }

   @Override
   public SkillBonus<GainedExperienceBonus> merge(SkillBonus<?> other) {
      if (other instanceof GainedExperienceBonus otherBonus) {
         return new GainedExperienceBonus(otherBonus.multiplier + this.multiplier, this.experienceSource);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      MutableComponent sourceDescription = Component.m_237115_(this.experienceSource.getDescriptionId());
      MutableComponent bonusDescription = Component.m_237110_(this.getDescriptionId(), new Object[]{sourceDescription});
      return TooltipHelper.getSkillBonusTooltip(bonusDescription, (double)this.multiplier, Operation.MULTIPLY_BASE)
         .m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public boolean isPositive() {
      return this.multiplier > 0.0F;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int row, Consumer<GainedExperienceBonus> consumer) {
      editor.addLabel(0, 0, "Multiplier", ChatFormatting.GOLD);
      editor.addLabel(110, 0, "Source", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 90, 14, (double)this.multiplier).setNumericResponder(value -> this.selectMultiplier(consumer, value));
      editor.addSelection(110, 0, 90, 1, this.experienceSource)
         .setNameGetter(GainedExperienceBonus.ExperienceSource::getFormattedName)
         .setResponder(experienceSource -> this.selectExperienceSource(consumer, experienceSource));
      editor.increaseHeight(19);
   }

   private void selectExperienceSource(Consumer<GainedExperienceBonus> consumer, GainedExperienceBonus.ExperienceSource experienceSource) {
      this.setExpericenSource(experienceSource);
      consumer.accept(this.copy());
   }

   private void selectMultiplier(Consumer<GainedExperienceBonus> consumer, Double value) {
      this.setMultiplier(value.floatValue());
      consumer.accept(this.copy());
   }

   public void setMultiplier(float multiplier) {
      this.multiplier = multiplier;
   }

   public void setExpericenSource(GainedExperienceBonus.ExperienceSource experienceSource) {
      this.experienceSource = experienceSource;
   }

   public float getMultiplier() {
      return this.multiplier;
   }

   public GainedExperienceBonus.ExperienceSource getSource() {
      return this.experienceSource;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         GainedExperienceBonus that = (GainedExperienceBonus)o;
         return Float.compare(this.multiplier, that.multiplier) != 0 ? false : this.experienceSource == that.experienceSource;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.experienceSource, this.multiplier);
   }

   public static enum ExperienceSource {
      MOBS("mobs"),
      FISHING("fishing"),
      ORE("ore");

      final String name;

      private ExperienceSource(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public Component getFormattedName() {
         return Component.m_237113_(this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1));
      }

      public static GainedExperienceBonus.ExperienceSource byName(String name) {
         for (GainedExperienceBonus.ExperienceSource type : values()) {
            if (type.name.equals(name)) {
               return type;
            }
         }

         return MOBS;
      }

      public String getDescriptionId() {
         return "experience.source." + this.getName();
      }
   }

   public static class Serializer implements SkillBonus.Serializer {
      public GainedExperienceBonus deserialize(JsonObject json) throws JsonParseException {
         float multiplier = SerializationHelper.getElement(json, "multiplier").getAsFloat();
         GainedExperienceBonus.ExperienceSource experienceSource = GainedExperienceBonus.ExperienceSource.byName(json.get("experience_source").getAsString());
         return new GainedExperienceBonus(multiplier, experienceSource);
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof GainedExperienceBonus aBonus) {
            json.addProperty("multiplier", aBonus.multiplier);
            json.addProperty("experience_source", aBonus.experienceSource.name);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public GainedExperienceBonus deserialize(CompoundTag tag) {
         float multiplier = tag.m_128457_("multiplier");
         GainedExperienceBonus.ExperienceSource experienceSource = GainedExperienceBonus.ExperienceSource.byName(tag.m_128461_("experience_source"));
         return new GainedExperienceBonus(multiplier, experienceSource);
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof GainedExperienceBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("multiplier", aBonus.multiplier);
            tag.m_128359_("experience_source", aBonus.experienceSource.name);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public GainedExperienceBonus deserialize(FriendlyByteBuf buf) {
         return new GainedExperienceBonus(buf.readFloat(), GainedExperienceBonus.ExperienceSource.byName(buf.m_130277_()));
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof GainedExperienceBonus aBonus) {
            buf.writeFloat(aBonus.multiplier);
            buf.m_130070_(aBonus.experienceSource.name);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new GainedExperienceBonus(0.25F, GainedExperienceBonus.ExperienceSource.MOBS);
      }
   }
}
