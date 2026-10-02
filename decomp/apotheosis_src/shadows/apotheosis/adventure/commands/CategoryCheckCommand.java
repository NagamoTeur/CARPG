package shadows.apotheosis.adventure.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.loot.LootCategory;

public class CategoryCheckCommand {
   public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
      root.then(((LiteralArgumentBuilder)Commands.m_82127_("loot_category").requires(c -> c.m_6761_(2))).executes(c -> {
         Player p = ((CommandSourceStack)c.getSource()).m_81375_();
         ItemStack stack = p.m_21205_();
         LootCategory cat = LootCategory.forItem(stack);
         EquipmentSlot[] slots = cat == null ? null : cat.getSlots();
         p.m_213846_(Component.m_237113_("Loot Category - " + (cat == null ? "null" : cat.getName())));
         p.m_213846_(Component.m_237113_("Equipment Slot - " + (slots == null ? "null" : toStr(slots))));
         return 0;
      }));
   }

   static String toStr(EquipmentSlot[] slots) {
      StringBuilder b = new StringBuilder();
      b.append('{');

      for (int i = 0; i < slots.length; i++) {
         b.append(slots[i].name().toLowerCase());
         if (i == slots.length - 1) {
            b.append('}');
         } else {
            b.append(", ");
         }
      }

      return b.toString();
   }
}
