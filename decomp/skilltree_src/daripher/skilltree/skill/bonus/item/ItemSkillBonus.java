package daripher.skilltree.skill.bonus.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTItemBonuses;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.player.DamageBonus;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class ItemSkillBonus implements ItemBonus<ItemSkillBonus> {
   private SkillBonus<?> bonus;

   public ItemSkillBonus(SkillBonus<?> bonus) {
      this.bonus = bonus;
   }

   @Override
   public boolean canMerge(ItemBonus<?> other) {
      return other instanceof ItemSkillBonus otherBonus ? otherBonus.bonus.canMerge(this.bonus) : false;
   }

   public ItemSkillBonus merge(ItemBonus<?> other) {
      if (other instanceof ItemSkillBonus otherBonus) {
         return new ItemSkillBonus(otherBonus.bonus.merge(this.bonus));
      } else {
         throw new IllegalArgumentException();
      }
   }

   public ItemSkillBonus copy() {
      return new ItemSkillBonus(this.bonus.copy());
   }

   public ItemSkillBonus multiply(double multiplier) {
      this.bonus.multiply(multiplier);
      return this;
   }

   @Override
   public ItemBonus.Serializer getSerializer() {
      return (ItemBonus.Serializer)PSTItemBonuses.SKILL_BONUS.get();
   }

   @Override
   public MutableComponent getTooltip() {
      return this.bonus.getTooltip();
   }

   @Override
   public boolean isPositive() {
      return this.bonus.isPositive();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<ItemBonus<?>> consumer) {
      editor.addLabel(0, 0, "Bonus Type", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.bonus)
         .setResponder(skillBonus -> this.selectSkillBonus(editor, consumer, skillBonus))
         .setMenuInitFunc(() -> this.addSkillBonusWidgets(editor, index, consumer));
      editor.increaseHeight(19);
   }

   private void addSkillBonusWidgets(SkillTreeEditor editor, int index, Consumer<ItemBonus<?>> consumer) {
      this.bonus.addEditorWidgets(editor, index, skillBonus -> {
         this.setBonus(skillBonus);
         consumer.accept(this);
      });
   }

   private void selectSkillBonus(SkillTreeEditor editor, Consumer<ItemBonus<?>> consumer, SkillBonus<?> skillBonus) {
      this.setBonus(skillBonus);
      consumer.accept(this);
      editor.rebuildWidgets();
   }

   public void setBonus(SkillBonus<?> bonus) {
      this.bonus = bonus;
   }

   public SkillBonus<?> getBonus() {
      return this.bonus;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         ItemSkillBonus that = (ItemSkillBonus)obj;
         return Objects.equals(this.bonus, that.bonus);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.bonus);
   }

   public static class Serializer implements ItemBonus.Serializer {
      public ItemBonus<?> deserialize(JsonObject json) throws JsonParseException {
         return new ItemSkillBonus(SerializationHelper.deserializeSkillBonus(json));
      }

      public void serialize(JsonObject json, ItemBonus<?> bonus) {
         if (bonus instanceof ItemSkillBonus aBonus) {
            SerializationHelper.serializeSkillBonus(json, aBonus.bonus);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(CompoundTag tag) {
         return new ItemSkillBonus(SerializationHelper.deserializeSkillBonus(tag));
      }

      public CompoundTag serialize(ItemBonus<?> bonus) {
         if (bonus instanceof ItemSkillBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeSkillBonus(tag, aBonus.bonus);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(FriendlyByteBuf buf) {
         return new ItemSkillBonus(NetworkHelper.readSkillBonus(buf));
      }

      public void serialize(FriendlyByteBuf buf, ItemBonus<?> bonus) {
         if (bonus instanceof ItemSkillBonus aBonus) {
            NetworkHelper.writeSkillBonus(buf, aBonus.bonus);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemBonus<?> createDefaultInstance() {
         return new ItemSkillBonus(new DamageBonus(0.1F, Operation.MULTIPLY_BASE));
      }
   }
}
