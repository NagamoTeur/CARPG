package daripher.skilltree.skill.bonus.condition.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTItemConditions;
import daripher.skilltree.network.NetworkHelper;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;

public final class PotionCondition implements ItemCondition {
   private PotionCondition.Type type;

   public PotionCondition(PotionCondition.Type type) {
      this.type = type;
   }

   @Override
   public boolean met(ItemStack stack) {
      if (!(stack.m_41720_() instanceof PotionItem)) {
         return false;
      } else {
         return switch (this.type) {
            case ANY -> true;
            case NEUTRAL -> this.hasEffects(stack, MobEffectCategory.NEUTRAL);
            case HARMFUL -> this.hasEffects(stack, MobEffectCategory.HARMFUL);
            case BENEFICIAL -> this.hasEffects(stack, MobEffectCategory.BENEFICIAL);
         };
      }
   }

   private boolean hasEffects(ItemStack stack, MobEffectCategory category) {
      return PotionUtils.m_43566_(stack.m_41784_()).stream().<MobEffect>map(MobEffectInstance::m_19544_).anyMatch(effect -> effect.m_19483_() == category);
   }

   @Override
   public String getDescriptionId() {
      return "%s.%s".formatted(ItemCondition.super.getDescriptionId(), this.type.getName());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         PotionCondition that = (PotionCondition)o;
         return this.type == that.type;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.type);
   }

   @Override
   public ItemCondition.Serializer getSerializer() {
      return (ItemCondition.Serializer)PSTItemConditions.POTIONS.get();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<ItemCondition> consumer) {
      editor.addLabel(0, 0, "Type", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelection(0, 0, 200, 1, this.type)
         .setNameGetter(PotionCondition.Type::getFormattedName)
         .setResponder(type -> this.selectPotionType(consumer, type));
      editor.increaseHeight(19);
   }

   private void selectPotionType(Consumer<ItemCondition> consumer, PotionCondition.Type type) {
      this.setType(type);
      consumer.accept(this);
   }

   public void setType(PotionCondition.Type type) {
      this.type = type;
   }

   public static class Serializer implements ItemCondition.Serializer {
      public ItemCondition deserialize(JsonObject json) throws JsonParseException {
         return new PotionCondition(SerializationHelper.deserializePotionType(json));
      }

      public void serialize(JsonObject json, ItemCondition condition) {
         if (condition instanceof PotionCondition aCondition) {
            SerializationHelper.serializePotionType(json, aCondition.type);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemCondition deserialize(CompoundTag tag) {
         return new PotionCondition(SerializationHelper.deserializePotionType(tag));
      }

      public CompoundTag serialize(ItemCondition condition) {
         if (condition instanceof PotionCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializePotionType(tag, aCondition.type);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemCondition deserialize(FriendlyByteBuf buf) {
         return new PotionCondition(NetworkHelper.readEnum(buf, PotionCondition.Type.class));
      }

      public void serialize(FriendlyByteBuf buf, ItemCondition condition) {
         if (condition instanceof PotionCondition aCondition) {
            NetworkHelper.writeEnum(buf, aCondition.type);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemCondition createDefaultInstance() {
         return new PotionCondition(PotionCondition.Type.ANY);
      }
   }

   public static enum Type {
      HARMFUL("harmful"),
      NEUTRAL("neutral"),
      BENEFICIAL("beneficial"),
      ANY("any");

      final String name;

      private Type(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public Component getFormattedName() {
         return Component.m_237113_(this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1));
      }

      public static PotionCondition.Type byName(String name) {
         for (PotionCondition.Type type : values()) {
            if (type.name.equals(name)) {
               return type;
            }
         }

         return ANY;
      }
   }
}
