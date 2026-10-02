package daripher.skilltree.item.necklace;

import daripher.skilltree.init.PSTCreativeTabs;
import daripher.skilltree.item.ItemBonusProvider;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class NecklaceItem extends Item implements ICurioItem, ItemBonusProvider {
   public NecklaceItem() {
      super(new Properties().m_41491_(PSTCreativeTabs.SKILLTREE).m_41487_(1));
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
      this.getItemBonuses().stream().map(ItemBonus::getTooltip).forEach(tooltips::add);
      return tooltips;
   }

   @NotNull
   @Override
   public List<ItemBonus<?>> getItemBonuses() {
      return List.of();
   }
}
