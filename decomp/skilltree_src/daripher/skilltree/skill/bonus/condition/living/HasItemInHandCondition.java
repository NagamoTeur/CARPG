package daripher.skilltree.skill.bonus.condition.living;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTLivingConditions;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import daripher.skilltree.skill.bonus.condition.item.NoneItemCondition;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

public final class HasItemInHandCondition implements LivingCondition {
   @Nonnull
   private ItemCondition itemCondition;

   public HasItemInHandCondition(@Nonnull ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   @Override
   public boolean met(LivingEntity living) {
      return this.itemCondition.met(living.m_21205_()) || this.itemCondition.met(living.m_21206_());
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, String target) {
      String key = this.getDescriptionId();
      Component targetDescription = Component.m_237115_("%s.target.%s".formatted(key, target));
      Component itemDescription = this.itemCondition.getTooltip();
      return Component.m_237110_(key, new Object[]{bonusTooltip, targetDescription, itemDescription});
   }

   @Override
   public LivingCondition.Serializer getSerializer() {
      return (LivingCondition.Serializer)PSTLivingConditions.HAS_ITEM_IN_HAND.get();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<LivingCondition> consumer) {
      editor.addLabel(0, 0, "Item Condition", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.itemCondition)
         .setResponder(condition -> this.selectItemCondition(editor, consumer, condition))
         .setMenuInitFunc(() -> this.addItemConditionWidgets(editor, consumer));
      editor.increaseHeight(19);
   }

   private void addItemConditionWidgets(SkillTreeEditor editor, Consumer<LivingCondition> consumer) {
      this.itemCondition.addEditorWidgets(editor, condition -> {
         this.setItemCondition(condition);
         consumer.accept(this);
      });
   }

   private void selectItemCondition(SkillTreeEditor editor, Consumer<LivingCondition> consumer, ItemCondition condition) {
      this.setItemCondition(condition);
      consumer.accept(this);
      editor.rebuildWidgets();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         HasItemInHandCondition that = (HasItemInHandCondition)o;
         return Objects.equals(this.itemCondition, that.itemCondition);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.itemCondition);
   }

   public void setItemCondition(@Nonnull ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   public static class Serializer implements LivingCondition.Serializer {
      public LivingCondition deserialize(JsonObject json) throws JsonParseException {
         return new HasItemInHandCondition(SerializationHelper.deserializeItemCondition(json));
      }

      public void serialize(JsonObject json, LivingCondition condition) {
         if (condition instanceof HasItemInHandCondition aCondition) {
            SerializationHelper.serializeItemCondition(json, aCondition.itemCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(CompoundTag tag) {
         return new HasItemInHandCondition(SerializationHelper.deserializeItemCondition(tag));
      }

      public CompoundTag serialize(LivingCondition condition) {
         if (condition instanceof HasItemInHandCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeItemCondition(tag, aCondition.itemCondition);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(FriendlyByteBuf buf) {
         return new HasItemInHandCondition(NetworkHelper.readItemCondition(buf));
      }

      public void serialize(FriendlyByteBuf buf, LivingCondition condition) {
         if (condition instanceof HasItemInHandCondition aCondition) {
            NetworkHelper.writeItemCondition(buf, aCondition.itemCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingCondition createDefaultInstance() {
         return new HasItemInHandCondition(NoneItemCondition.INSTANCE);
      }
   }
}
