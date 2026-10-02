package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.living.LivingCondition;
import daripher.skilltree.skill.bonus.condition.living.NoneLivingCondition;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class BlockBreakSpeedBonus implements SkillBonus<BlockBreakSpeedBonus> {
   @Nonnull
   private LivingCondition playerCondition;
   private float multiplier;

   public BlockBreakSpeedBonus(@Nonnull LivingCondition playerCondition, float multiplier) {
      this.playerCondition = playerCondition;
      this.multiplier = multiplier;
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.BLOCK_BREAK_SPEED.get();
   }

   public BlockBreakSpeedBonus copy() {
      return new BlockBreakSpeedBonus(this.playerCondition, this.multiplier);
   }

   public BlockBreakSpeedBonus multiply(double multiplier) {
      this.multiplier = (float)((double)this.multiplier * multiplier);
      return this;
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      return other instanceof BlockBreakSpeedBonus otherBonus ? Objects.equals(otherBonus.playerCondition, this.playerCondition) : false;
   }

   @Override
   public SkillBonus<BlockBreakSpeedBonus> merge(SkillBonus<?> other) {
      if (other instanceof BlockBreakSpeedBonus otherBonus) {
         return new BlockBreakSpeedBonus(this.playerCondition, otherBonus.multiplier + this.multiplier);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      MutableComponent bonusTooltip = TooltipHelper.getSkillBonusTooltip(this.getDescriptionId(), (double)this.multiplier, Operation.MULTIPLY_BASE);
      bonusTooltip = this.playerCondition.getTooltip(bonusTooltip, "you");
      return bonusTooltip.m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public boolean isPositive() {
      return this.multiplier > 0.0F;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<BlockBreakSpeedBonus> consumer) {
      editor.addLabel(0, 0, "Multiplier", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 50, 14, (double)this.multiplier).setNumericResponder(value -> this.selectMultiplier(consumer, value));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Player Condition", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.playerCondition)
         .setResponder(condition -> this.selectPlayerCondition(editor, consumer, condition))
         .setMenuInitFunc(() -> this.addPlayerConditionWidgets(editor, consumer));
      editor.increaseHeight(19);
   }

   private void selectPlayerCondition(SkillTreeEditor editor, Consumer<BlockBreakSpeedBonus> consumer, LivingCondition condition) {
      this.setPlayerCondition(condition);
      consumer.accept(this.copy());
      editor.rebuildWidgets();
   }

   private void selectMultiplier(Consumer<BlockBreakSpeedBonus> consumer, Double value) {
      this.setMultiplier(value.floatValue());
      consumer.accept(this.copy());
   }

   private void addPlayerConditionWidgets(SkillTreeEditor editor, Consumer<BlockBreakSpeedBonus> consumer) {
      this.playerCondition.addEditorWidgets(editor, c -> {
         this.setPlayerCondition(c);
         consumer.accept(this.copy());
      });
   }

   public void setPlayerCondition(@Nonnull LivingCondition playerCondition) {
      this.playerCondition = playerCondition;
   }

   public void setMultiplier(float multiplier) {
      this.multiplier = multiplier;
   }

   @Nonnull
   public LivingCondition getPlayerCondition() {
      return this.playerCondition;
   }

   public float getMultiplier() {
      return this.multiplier;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         BlockBreakSpeedBonus that = (BlockBreakSpeedBonus)obj;
         return !Objects.equals(this.playerCondition, that.playerCondition) ? false : this.multiplier == that.multiplier;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.playerCondition, this.multiplier);
   }

   public static class Serializer implements SkillBonus.Serializer {
      public BlockBreakSpeedBonus deserialize(JsonObject json) throws JsonParseException {
         LivingCondition condition = SerializationHelper.deserializeLivingCondition(json, "player_condition");
         float multiplier = SerializationHelper.getElement(json, "multiplier").getAsFloat();
         return new BlockBreakSpeedBonus(condition, multiplier);
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof BlockBreakSpeedBonus aBonus) {
            SerializationHelper.serializeLivingCondition(json, aBonus.playerCondition, "player_condition");
            json.addProperty("multiplier", aBonus.multiplier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public BlockBreakSpeedBonus deserialize(CompoundTag tag) {
         LivingCondition condition = SerializationHelper.deserializeLivingCondition(tag, "player_condition");
         float multiplier = tag.m_128457_("multiplier");
         return new BlockBreakSpeedBonus(condition, multiplier);
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof BlockBreakSpeedBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeLivingCondition(tag, aBonus.playerCondition, "player_condition");
            tag.m_128350_("multiplier", aBonus.multiplier);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public BlockBreakSpeedBonus deserialize(FriendlyByteBuf buf) {
         return new BlockBreakSpeedBonus(NetworkHelper.readLivingCondition(buf), buf.readFloat());
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof BlockBreakSpeedBonus aBonus) {
            NetworkHelper.writeLivingCondition(buf, aBonus.playerCondition);
            buf.writeFloat(aBonus.multiplier);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new BlockBreakSpeedBonus(NoneLivingCondition.INSTANCE, 0.1F);
      }
   }
}
