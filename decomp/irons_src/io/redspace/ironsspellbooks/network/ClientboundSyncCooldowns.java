package io.redspace.ironsspellbooks.network;

import io.redspace.ironsspellbooks.capabilities.magic.CooldownInstance;
import io.redspace.ironsspellbooks.capabilities.magic.PlayerCooldowns;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientboundSyncCooldowns {
   private final Map<String, CooldownInstance> spellCooldowns;

   public static String readSpellID(FriendlyByteBuf buffer) {
      return buffer.m_130277_();
   }

   public static CooldownInstance readCoolDownInstance(FriendlyByteBuf buffer) {
      int spellCooldown = buffer.readInt();
      int spellCooldownRemaining = buffer.readInt();
      return new CooldownInstance(spellCooldown, spellCooldownRemaining);
   }

   public static void writeSpellId(FriendlyByteBuf buf, String spellId) {
      buf.m_130070_(spellId);
   }

   public static void writeCoolDownInstance(FriendlyByteBuf buf, CooldownInstance cooldownInstance) {
      buf.writeInt(cooldownInstance.getSpellCooldown());
      buf.writeInt(cooldownInstance.getCooldownRemaining());
   }

   public ClientboundSyncCooldowns(Map<String, CooldownInstance> spellCooldowns) {
      this.spellCooldowns = spellCooldowns;
   }

   public ClientboundSyncCooldowns(FriendlyByteBuf buf) {
      this.spellCooldowns = buf.m_236847_(ClientboundSyncCooldowns::readSpellID, ClientboundSyncCooldowns::readCoolDownInstance);
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_236831_(this.spellCooldowns, ClientboundSyncCooldowns::writeSpellId, ClientboundSyncCooldowns::writeCoolDownInstance);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> {
         PlayerCooldowns cooldowns = ClientMagicData.getCooldowns();
         cooldowns.clearCooldowns();
         this.spellCooldowns.forEach((k, v) -> cooldowns.addCooldown(k, v.getSpellCooldown(), v.getCooldownRemaining()));
         ClientMagicData.resetClientCastState(null);
      });
      return true;
   }
}
