package immersive_armors.network.c2s;

import immersive_armors.armorEffects.ArmorEffect;
import immersive_armors.cobalt.network.Message;
import immersive_armors.item.ExtendedArmorItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ArmorCommandMessage extends Message {
   private final int slot;
   private final String command;

   public ArmorCommandMessage(int slot, String command) {
      this.slot = slot;
      this.command = command;
   }

   public ArmorCommandMessage(FriendlyByteBuf b) {
      this.slot = b.readInt();
      this.command = b.m_130277_();
   }

   @Override
   public void encode(FriendlyByteBuf b) {
      b.writeInt(this.slot);
      b.m_130070_(this.command);
   }

   @Override
   public void receive(Player player) {
      ItemStack stack = player.m_150109_().m_8020_(this.slot);
      if (stack.m_41720_() instanceof ExtendedArmorItem item) {
         for (ArmorEffect e : item.getMaterial().getEffects()) {
            e.receiveCommand(stack, player.f_19853_, player, this.slot, this.command);
         }
      }
   }
}
