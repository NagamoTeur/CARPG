package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.SkillBonus;
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
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class PlayerSocketsBonus implements SkillBonus<PlayerSocketsBonus> {
   @Nonnull
   private ItemCondition itemCondition;
   private int sockets;

   public PlayerSocketsBonus(@Nonnull ItemCondition itemCondition, int sockets) {
      this.itemCondition = itemCondition;
      this.sockets = sockets;
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.PLAYER_SOCKETS.get();
   }

   public PlayerSocketsBonus copy() {
      return new PlayerSocketsBonus(this.itemCondition, this.sockets);
   }

   public PlayerSocketsBonus multiply(double multiplier) {
      return new PlayerSocketsBonus(this.itemCondition, (int)((double)this.sockets * multiplier));
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      return other instanceof PlayerSocketsBonus otherBonus ? Objects.equals(otherBonus.itemCondition, this.itemCondition) : false;
   }

   @Override
   public SkillBonus<PlayerSocketsBonus> merge(SkillBonus<?> other) {
      if (other instanceof PlayerSocketsBonus otherBonus) {
         return new PlayerSocketsBonus(this.itemCondition, otherBonus.sockets + this.sockets);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      Component itemDescription = this.itemCondition.getTooltip();
      Component bonusDescription = Component.m_237110_(this.getDescriptionId(), new Object[]{itemDescription});
      Operation operation = Operation.ADDITION;
      return TooltipHelper.getSkillBonusTooltip(bonusDescription, (double)this.sockets, operation)
         .m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public boolean isPositive() {
      return this.sockets > 0;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int row, Consumer<PlayerSocketsBonus> consumer) {
      editor.addLabel(0, 0, "Multiplier", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.sockets).setNumericResponder(value -> this.selectSocketNumber(consumer, value));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Item Condition", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.itemCondition)
         .setResponder(condition -> this.selectItemCondition(editor, consumer, condition))
         .setMenuInitFunc(() -> this.addItemConditionWidgets(editor, consumer));
      editor.increaseHeight(19);
   }

   private void addItemConditionWidgets(SkillTreeEditor editor, Consumer<PlayerSocketsBonus> consumer) {
      this.itemCondition.addEditorWidgets(editor, condition -> {
         this.setItemCondition(condition);
         consumer.accept(this.copy());
      });
   }

   private void selectItemCondition(SkillTreeEditor editor, Consumer<PlayerSocketsBonus> consumer, ItemCondition condition) {
      this.setItemCondition(condition);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void selectSocketNumber(Consumer<PlayerSocketsBonus> consumer, Double value) {
      this.setSockets(value.intValue());
      consumer.accept(this.copy());
   }

   public void setItemCondition(@Nonnull ItemCondition itemCondition) {
      this.itemCondition = itemCondition;
   }

   public void setSockets(int sockets) {
      this.sockets = sockets;
   }

   @Nonnull
   public ItemCondition getItemCondition() {
      return this.itemCondition;
   }

   public int getSockets() {
      return this.sockets;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         PlayerSocketsBonus that = (PlayerSocketsBonus)obj;
         return Objects.equals(this.itemCondition, that.itemCondition) && this.sockets == that.sockets;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.itemCondition, this.sockets);
   }

   public static class Serializer implements SkillBonus.Serializer {
      public PlayerSocketsBonus deserialize(JsonObject json) throws JsonParseException {
         ItemCondition condition = SerializationHelper.deserializeItemCondition(json);
         int sockets = SerializationHelper.getElement(json, "sockets").getAsInt();
         return new PlayerSocketsBonus(condition, sockets);
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof PlayerSocketsBonus aBonus) {
            SerializationHelper.serializeItemCondition(json, aBonus.itemCondition);
            json.addProperty("sockets", aBonus.sockets);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public PlayerSocketsBonus deserialize(CompoundTag tag) {
         ItemCondition condition = SerializationHelper.deserializeItemCondition(tag);
         int sockets = tag.m_128451_("sockets");
         return new PlayerSocketsBonus(condition, sockets);
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof PlayerSocketsBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeItemCondition(tag, aBonus.itemCondition);
            tag.m_128405_("sockets", aBonus.sockets);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public PlayerSocketsBonus deserialize(FriendlyByteBuf buf) {
         return new PlayerSocketsBonus(NetworkHelper.readItemCondition(buf), buf.readInt());
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof PlayerSocketsBonus aBonus) {
            NetworkHelper.writeItemCondition(buf, aBonus.itemCondition);
            buf.writeInt(aBonus.sockets);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new PlayerSocketsBonus(NoneItemCondition.INSTANCE, 1);
      }
   }
}
