package daripher.skilltree.skill.bonus.condition.living;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.entity.player.PlayerHelper;
import daripher.skilltree.init.PSTLivingConditions;
import daripher.skilltree.item.gem.GemItem;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import daripher.skilltree.skill.bonus.condition.item.NoneItemCondition;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class HasGemsCondition implements LivingCondition {
   private int min;
   private int max;
   @Nonnull
   private ItemCondition itemCondition;

   public HasGemsCondition(int min, int max, @Nonnull ItemCondition itemCondition) {
      this.min = min;
      this.max = max;
      this.itemCondition = itemCondition;
   }

   @Override
   public boolean met(LivingEntity living) {
      int gems = this.getGems(PlayerHelper.getAllEquipment(living).filter(this.itemCondition::met));
      if (this.min == -1) {
         return gems <= this.max;
      } else {
         return this.max == -1 ? gems >= this.min : gems <= this.max && gems >= this.min;
      }
   }

   private int getGems(Stream<ItemStack> items) {
      return items.map(GemItem::getGems).map(List::size).reduce(Integer::sum).orElse(0);
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, String target) {
      String key = this.getDescriptionId();
      Component targetDescription = Component.m_237115_("%s.target.%s".formatted(key, target));
      Component itemDescription = this.itemCondition.getTooltip("where");
      if (this.min == -1) {
         return Component.m_237110_(key + ".max", new Object[]{bonusTooltip, targetDescription, this.max, itemDescription});
      } else if (this.max == -1) {
         return this.min == 1
            ? Component.m_237110_(key + ".min.1", new Object[]{bonusTooltip, targetDescription, itemDescription})
            : Component.m_237110_(key + ".min", new Object[]{bonusTooltip, targetDescription, this.min, itemDescription});
      } else {
         return Component.m_237110_(key + ".range", new Object[]{bonusTooltip, targetDescription, this.min, this.max, itemDescription});
      }
   }

   @Override
   public LivingCondition.Serializer getSerializer() {
      return (LivingCondition.Serializer)PSTLivingConditions.HAS_GEMS.get();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<LivingCondition> consumer) {
      editor.addLabel(0, 0, "Item Condition", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.itemCondition)
         .setResponder(condition -> this.selectItemCondition(editor, consumer, condition))
         .setMenuInitFunc(() -> this.addItemConditionWidgets(editor, consumer));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Min", ChatFormatting.GREEN);
      editor.addLabel(55, 0, "Max", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.min).setNumericResponder(value -> this.selectMinimum(consumer, value));
      editor.addNumericTextField(55, 0, 50, 14, (double)this.max).setNumericResponder(value -> this.selectMaximum(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectMaximum(Consumer<LivingCondition> consumer, Double value) {
      this.setMax(value.intValue());
      consumer.accept(this);
   }

   private void selectMinimum(Consumer<LivingCondition> consumer, Double value) {
      this.setMin(value.intValue());
      consumer.accept(this);
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
         HasGemsCondition that = (HasGemsCondition)o;
         return this.min == that.min && this.max == that.max && Objects.equals(this.itemCondition, that.itemCondition);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.min, this.max, this.itemCondition);
   }

   public void setItemCondition(@Nonnull ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   public void setMax(int max) {
      this.max = max;
   }

   public void setMin(int min) {
      this.min = min;
   }

   public static class Serializer implements LivingCondition.Serializer {
      public LivingCondition deserialize(JsonObject json) throws JsonParseException {
         int min = json.has("min") ? json.get("min").getAsInt() : -1;
         int max = json.has("max") ? json.get("max").getAsInt() : -1;
         ItemCondition itemCondition = SerializationHelper.deserializeItemCondition(json);
         return new HasGemsCondition(min, max, itemCondition);
      }

      public void serialize(JsonObject json, LivingCondition condition) {
         if (condition instanceof HasGemsCondition aCondition) {
            if (aCondition.min != -1) {
               json.addProperty("min", aCondition.min);
            }

            if (aCondition.max != -1) {
               json.addProperty("max", aCondition.max);
            }

            SerializationHelper.serializeItemCondition(json, aCondition.itemCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(CompoundTag tag) {
         int min = tag.m_128441_("min") ? tag.m_128451_("min") : -1;
         int max = tag.m_128441_("max") ? tag.m_128451_("max") : -1;
         ItemCondition itemCondition = SerializationHelper.deserializeItemCondition(tag);
         return new HasGemsCondition(min, max, itemCondition);
      }

      public CompoundTag serialize(LivingCondition condition) {
         if (condition instanceof HasGemsCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            if (aCondition.min != -1) {
               tag.m_128405_("min", aCondition.min);
            }

            if (aCondition.max != -1) {
               tag.m_128405_("max", aCondition.max);
            }

            SerializationHelper.serializeItemCondition(tag, aCondition.itemCondition);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(FriendlyByteBuf buf) {
         return new HasGemsCondition(buf.readInt(), buf.readInt(), NetworkHelper.readItemCondition(buf));
      }

      public void serialize(FriendlyByteBuf buf, LivingCondition condition) {
         if (condition instanceof HasGemsCondition aCondition) {
            buf.writeInt(aCondition.min);
            buf.writeInt(aCondition.max);
            NetworkHelper.writeItemCondition(buf, aCondition.itemCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingCondition createDefaultInstance() {
         return new HasGemsCondition(1, -1, NoneItemCondition.INSTANCE);
      }
   }
}
