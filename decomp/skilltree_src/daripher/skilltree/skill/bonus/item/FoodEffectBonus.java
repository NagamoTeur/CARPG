package daripher.skilltree.skill.bonus.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTItemBonuses;
import daripher.skilltree.network.NetworkHelper;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;

public final class FoodEffectBonus implements ItemBonus<FoodEffectBonus> {
   private MobEffectInstance effect;

   public FoodEffectBonus(MobEffectInstance effect) {
      this.effect = effect;
   }

   @Override
   public boolean canMerge(ItemBonus<?> other) {
      return other instanceof FoodEffectBonus otherBonus ? otherBonus.effect.m_19544_() == this.effect.m_19544_() : false;
   }

   public FoodEffectBonus merge(ItemBonus<?> other) {
      if (other instanceof FoodEffectBonus otherBonus) {
         int amplifier = this.effect.m_19564_() + otherBonus.effect.m_19564_() + 1;
         return new FoodEffectBonus(new MobEffectInstance(this.effect.m_19544_(), this.effect.m_19557_(), amplifier));
      } else {
         throw new IllegalArgumentException();
      }
   }

   public FoodEffectBonus copy() {
      return new FoodEffectBonus(new MobEffectInstance(this.effect));
   }

   public FoodEffectBonus multiply(double multiplier) {
      int amplifier = (int)((double)this.effect.m_19564_() * multiplier);
      return new FoodEffectBonus(new MobEffectInstance(this.effect.m_19544_(), this.effect.m_19557_(), amplifier));
   }

   @Override
   public ItemBonus.Serializer getSerializer() {
      return (ItemBonus.Serializer)PSTItemBonuses.FOOD_EFFECT.get();
   }

   @Override
   public MutableComponent getTooltip() {
      Component effectDescription = TooltipHelper.getEffectInstanceTooltip(this.effect);
      String durationDescription = MobEffectUtil.m_19581_(this.effect, 1.0F);
      return Component.m_237110_(this.getDescriptionId(), new Object[]{effectDescription, durationDescription});
   }

   @Override
   public boolean isPositive() {
      return this.effect.m_19544_().m_19483_() != MobEffectCategory.HARMFUL;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int index, Consumer<ItemBonus<?>> consumer) {
      editor.addLabel(0, 0, "Effect", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.effect.m_19544_()).setResponder(effect -> this.selectEffect(consumer, effect));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Duration", ChatFormatting.GREEN);
      editor.addLabel(110, 0, "Amplifier", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 90, 14, (double)this.getEffectInstance().m_19557_()).setNumericResponder(value -> this.selectDuration(consumer, value));
      editor.addNumericTextField(110, 0, 90, 14, (double)this.getEffectInstance().m_19564_())
         .setNumericResponder(value -> this.selectAmplifier(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectAmplifier(Consumer<ItemBonus<?>> consumer, Double value) {
      this.setAmplifier(value.intValue());
      consumer.accept(this);
   }

   private void selectDuration(Consumer<ItemBonus<?>> consumer, Double value) {
      this.setDuration(value.intValue());
      consumer.accept(this);
   }

   private void selectEffect(Consumer<ItemBonus<?>> consumer, MobEffect effect) {
      this.setEffect(effect);
      consumer.accept(this);
   }

   public void setDuration(int duration) {
      this.effect = new MobEffectInstance(this.getEffectInstance().m_19544_(), duration, this.getEffectInstance().m_19564_());
   }

   public void setAmplifier(int amplifier) {
      this.effect = new MobEffectInstance(this.getEffectInstance().m_19544_(), this.getEffectInstance().m_19557_(), amplifier);
   }

   public void setEffect(MobEffect effect) {
      this.effect = new MobEffectInstance(effect, this.getEffectInstance().m_19557_(), this.getEffectInstance().m_19564_());
   }

   public MobEffectInstance getEffectInstance() {
      return this.effect;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         FoodEffectBonus that = (FoodEffectBonus)obj;
         return Objects.equals(this.effect, that.effect);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.effect);
   }

   public static class Serializer implements ItemBonus.Serializer {
      public ItemBonus<?> deserialize(JsonObject json) throws JsonParseException {
         return new FoodEffectBonus(SerializationHelper.deserializeEffectInstance(json));
      }

      public void serialize(JsonObject json, ItemBonus<?> bonus) {
         if (bonus instanceof FoodEffectBonus aBonus) {
            SerializationHelper.serializeEffectInstance(json, aBonus.effect);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(CompoundTag tag) {
         return new FoodEffectBonus(SerializationHelper.deserializeEffectInstance(tag));
      }

      public CompoundTag serialize(ItemBonus<?> bonus) {
         if (bonus instanceof FoodEffectBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeEffectInstance(tag, aBonus.effect);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemBonus<?> deserialize(FriendlyByteBuf buf) {
         return new FoodEffectBonus(NetworkHelper.readEffectInstance(buf));
      }

      public void serialize(FriendlyByteBuf buf, ItemBonus<?> bonus) {
         if (bonus instanceof FoodEffectBonus aBonus) {
            NetworkHelper.writeEffectInstance(buf, aBonus.effect);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemBonus<?> createDefaultInstance() {
         return new FoodEffectBonus(new MobEffectInstance(MobEffects.f_19605_, 600));
      }
   }
}
