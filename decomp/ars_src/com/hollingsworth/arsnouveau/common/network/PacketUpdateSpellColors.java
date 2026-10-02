package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.particle.ParticleColorRegistry;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUpdateSpellColors {
   int castSlot;
   ParticleColor color;
   boolean mainHand;

   public PacketUpdateSpellColors(int slot, ParticleColor color, boolean mainHand) {
      this.castSlot = slot;
      this.color = color;
      this.mainHand = mainHand;
   }

   public PacketUpdateSpellColors(FriendlyByteBuf buf) {
      this.castSlot = buf.readInt();
      this.color = ParticleColorRegistry.from(buf.m_130260_());
      this.mainHand = buf.readBoolean();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeInt(this.castSlot);
      buf.m_130079_(this.color.serialize());
      buf.writeBoolean(this.mainHand);
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get()
         .enqueueWork(
            () -> {
               if (ctx.get().getSender() != null) {
                  ItemStack stack = ctx.get().getSender().m_21120_(this.mainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
                  if (stack.m_41720_() instanceof SpellBook) {
                     ISpellCaster caster = CasterUtil.getCaster(stack);
                     caster.setColor(this.color, this.castSlot);
                     caster.setCurrentSlot(this.castSlot);
                     Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> ctx.get().getSender()), new PacketUpdateBookGUI(stack));
                     Networking.INSTANCE
                        .send(
                           PacketDistributor.PLAYER.with(() -> ctx.get().getSender()),
                           new PacketOpenSpellBook(this.mainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND)
                        );
                  }
               }
            }
         );
      ctx.get().setPacketHandled(true);
   }
}
