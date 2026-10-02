package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUpdateMana {
   public double mana;
   public int maxMana;
   public int glyphBonus;
   public int tierBonus;
   public float reserved;

   public PacketUpdateMana(FriendlyByteBuf buf) {
      this.mana = buf.readDouble();
      this.maxMana = buf.readInt();
      this.glyphBonus = buf.readInt();
      this.tierBonus = buf.readInt();
      this.reserved = buf.readFloat();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeDouble(this.mana);
      buf.writeInt(this.maxMana);
      buf.writeInt(this.glyphBonus);
      buf.writeInt(this.tierBonus);
      buf.writeFloat(this.reserved);
   }

   public PacketUpdateMana(double mana, int maxMana, int glyphBonus, int tierBonus, float reserved) {
      this.mana = mana;
      this.maxMana = maxMana;
      this.glyphBonus = glyphBonus;
      this.tierBonus = tierBonus;
      this.reserved = reserved;
   }

   public PacketUpdateMana(double mana, int maxMana, int glyphBonus, int tierBonus) {
      this.mana = mana;
      this.maxMana = maxMana;
      this.glyphBonus = glyphBonus;
      this.tierBonus = tierBonus;
      this.reserved = -1.0F;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (ArsNouveau.proxy.getPlayer() != null) {
            CapabilityRegistry.getMana(ArsNouveau.proxy.getPlayer()).ifPresent(mana -> {
               mana.setMana(this.mana);
               mana.setMaxMana(this.maxMana);
               mana.setGlyphBonus(this.glyphBonus);
               mana.setBookTier(this.tierBonus);
            });
            if (ctx.get().getDirection().getReceptionSide().isClient() && this.reserved != -1.0F) {
               ClientInfo.reservedOverlayMana = this.reserved;
            }
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
