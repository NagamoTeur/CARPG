package daripher.skilltree.skill.bonus.item;

import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.init.PSTRegistries;
import java.util.function.Consumer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface ItemBonus<T extends ItemBonus<T>> {
   default void itemCrafted(ItemStack stack) {
   }

   boolean canMerge(ItemBonus<?> var1);

   T merge(ItemBonus<?> var1);

   T copy();

   T multiply(double var1);

   ItemBonus.Serializer getSerializer();

   default String getDescriptionId() {
      ResourceLocation id = PSTRegistries.ITEM_BONUSES.get().getKey(this.getSerializer());
      return "item_bonus.%s.%s".formatted(id.m_135827_(), id.m_135815_());
   }

   MutableComponent getTooltip();

   boolean isPositive();

   void addEditorWidgets(SkillTreeEditor var1, int var2, Consumer<ItemBonus<?>> var3);

   public interface Serializer extends daripher.skilltree.data.serializers.Serializer<ItemBonus<?>> {
      ItemBonus<?> createDefaultInstance();
   }
}
