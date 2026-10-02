package io.redspace.ironsspellbooks.gui.overlays.network;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.gui.overlays.SpellSelection;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class ServerboundSelectSpell {
   private final SpellSelection spellSelection;

   public ServerboundSelectSpell(SpellSelection spellSelection) {
      this.spellSelection = spellSelection;
   }

   public ServerboundSelectSpell(FriendlyByteBuf buf) {
      SpellSelection tmpSpellSelection = new SpellSelection();
      tmpSpellSelection.readFromBuffer(buf);
      this.spellSelection = tmpSpellSelection;
   }

   public void toBytes(FriendlyByteBuf buf) {
      this.spellSelection.writeToBuffer(buf);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> {
         ServerPlayer serverPlayer = ctx.getSender();
         if (serverPlayer != null) {
            MagicData.getPlayerMagicData(serverPlayer).getSyncedData().setSpellSelection(this.spellSelection);
         }
      });
      return true;
   }
}
