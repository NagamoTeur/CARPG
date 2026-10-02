package daripher.skilltree.item.quiver;

import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.item.ItemSkillBonus;
import daripher.skilltree.skill.bonus.player.IncomingHealingBonus;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class HealingQuiverItem extends QuiverItem {
   public HealingQuiverItem() {
      super(150);
   }

   @NotNull
   @Override
   public List<ItemBonus<?>> getItemBonuses() {
      return List.of(new ItemSkillBonus(new IncomingHealingBonus(0.05F)));
   }
}
