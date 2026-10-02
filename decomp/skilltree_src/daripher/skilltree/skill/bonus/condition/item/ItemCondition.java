package daripher.skilltree.skill.bonus.condition.item;

import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.init.PSTRegistries;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface ItemCondition {
   boolean met(ItemStack var1);

   default String getDescriptionId() {
      ResourceLocation id = PSTRegistries.ITEM_CONDITIONS.get().getKey(this.getSerializer());
      return "item_condition.%s.%s".formatted(id.m_135827_(), id.m_135815_());
   }

   default Component getTooltip() {
      return Component.m_237115_(this.getDescriptionId());
   }

   default Component getTooltip(String type) {
      return TooltipHelper.getOptionalTooltip(this.getDescriptionId(), type);
   }

   ItemCondition.Serializer getSerializer();

   default void addEditorWidgets(SkillTreeEditor editor, Consumer<ItemCondition> consumer) {
   }

   public interface Serializer extends daripher.skilltree.data.serializers.Serializer<ItemCondition> {
      ItemCondition createDefaultInstance();
   }
}
