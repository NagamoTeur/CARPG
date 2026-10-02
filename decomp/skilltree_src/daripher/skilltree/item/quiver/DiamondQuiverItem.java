package daripher.skilltree.item.quiver;

import daripher.skilltree.item.ItemBonusProvider;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.item.ItemSkillBonus;
import daripher.skilltree.skill.bonus.player.CritChanceBonus;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DiamondQuiverItem extends QuiverItem implements ItemBonusProvider {
   public DiamondQuiverItem() {
      super(150);
   }

   @NotNull
   @Override
   public List<ItemBonus<?>> getItemBonuses() {
      return List.of(new ItemSkillBonus(new CritChanceBonus(0.05F)));
   }
}
