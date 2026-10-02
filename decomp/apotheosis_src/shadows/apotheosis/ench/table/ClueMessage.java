package shadows.apotheosis.ench.table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.network.NetworkEvent.Context;
import shadows.placebo.network.MessageHelper;
import shadows.placebo.network.MessageProvider;

public class ClueMessage implements MessageProvider<ClueMessage> {
   protected final int slot;
   protected final List<EnchantmentInstance> clues;
   protected final boolean all;

   public ClueMessage(int slot, List<EnchantmentInstance> clues, boolean all) {
      this.slot = slot;
      this.clues = clues;
      this.all = all;
   }

   public void write(ClueMessage msg, FriendlyByteBuf buf) {
      buf.writeByte(msg.clues.size());

      for (EnchantmentInstance e : msg.clues) {
         buf.writeShort(Registry.f_122825_.m_7447_(e.f_44947_));
         buf.writeByte(e.f_44948_);
      }

      buf.writeByte(msg.slot);
      buf.writeBoolean(msg.all);
   }

   public ClueMessage read(FriendlyByteBuf buf) {
      int size = buf.readByte();
      List<EnchantmentInstance> clues = new ArrayList<>(size);

      for (int i = 0; i < size; i++) {
         Enchantment ench = (Enchantment)Registry.f_122825_.m_200957_(buf.readShort());
         clues.add(new EnchantmentInstance(ench, buf.readByte()));
      }

      return new ClueMessage(buf.readByte(), clues, buf.readBoolean());
   }

   public void handle(ClueMessage msg, Supplier<Context> ctx) {
      MessageHelper.handlePacket(() -> () -> {
            if (Minecraft.m_91087_().f_91080_ instanceof ApothEnchantScreen es) {
               es.acceptClues(msg.slot, msg.clues, msg.all);
            }
         }, ctx);
   }
}
