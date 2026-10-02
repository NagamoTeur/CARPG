package daripher.skilltree.skill.bonus.condition.living;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.entity.player.PlayerHelper;
import daripher.skilltree.init.PSTLivingConditions;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.condition.item.EquipmentCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

public final class DualWieldingCondition implements LivingCondition {
   @Nonnull
   private ItemCondition weaponCondition;

   public DualWieldingCondition(@Nonnull ItemCondition weaponCondition) {
      this.weaponCondition = weaponCondition;
   }

   @Override
   public boolean met(LivingEntity living) {
      return PlayerHelper.getItemsInHands(living).allMatch(this.weaponCondition::met);
   }

   @Override
   public MutableComponent getTooltip(MutableComponent bonusTooltip, String target) {
      String key = this.getDescriptionId();
      Component targetDescription = Component.m_237115_("%s.target.%s".formatted(key, target));
      Component itemDescription = this.weaponCondition.getTooltip();
      return Component.m_237110_(key, new Object[]{bonusTooltip, targetDescription, itemDescription});
   }

   @Override
   public LivingCondition.Serializer getSerializer() {
      return (LivingCondition.Serializer)PSTLivingConditions.DUAL_WIELDING.get();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<LivingCondition> consumer) {
      this.weaponCondition.addEditorWidgets(editor, c -> {
         this.setWeaponCondition(c);
         consumer.accept(this);
      });
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         DualWieldingCondition that = (DualWieldingCondition)o;
         return Objects.equals(this.weaponCondition, that.weaponCondition);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.weaponCondition);
   }

   public void setWeaponCondition(@Nonnull ItemCondition weaponCondition) {
      this.weaponCondition = weaponCondition;
   }

   public static class Serializer implements LivingCondition.Serializer {
      public LivingCondition deserialize(JsonObject json) throws JsonParseException {
         return new DualWieldingCondition(SerializationHelper.deserializeItemCondition(json));
      }

      public void serialize(JsonObject json, LivingCondition condition) {
         if (condition instanceof DualWieldingCondition aCondition) {
            SerializationHelper.serializeItemCondition(json, aCondition.weaponCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(CompoundTag tag) {
         return new DualWieldingCondition(SerializationHelper.deserializeItemCondition(tag));
      }

      public CompoundTag serialize(LivingCondition condition) {
         if (condition instanceof DualWieldingCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeItemCondition(tag, aCondition.weaponCondition);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LivingCondition deserialize(FriendlyByteBuf buf) {
         return new DualWieldingCondition(NetworkHelper.readItemCondition(buf));
      }

      public void serialize(FriendlyByteBuf buf, LivingCondition condition) {
         if (condition instanceof DualWieldingCondition aCondition) {
            NetworkHelper.writeItemCondition(buf, aCondition.weaponCondition);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public LivingCondition createDefaultInstance() {
         return new DualWieldingCondition(new EquipmentCondition(EquipmentCondition.Type.WEAPON));
      }
   }
}
