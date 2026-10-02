package daripher.skilltree.item.quiver;

import daripher.skilltree.item.ItemBonusProvider;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.item.ItemSkillBonus;
import daripher.skilltree.skill.bonus.player.LootDuplicationBonus;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class BoneQuiverItem extends QuiverItem implements ItemBonusProvider {
   @NotNull
   @Override
   public List<ItemBonus<?>> getItemBonuses() {
      return List.of(new ItemSkillBonus(new LootDuplicationBonus(0.05F, 2.0F, LootDuplicationBonus.LootType.MOBS)));
   }
}
