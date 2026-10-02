package io.redspace.ironsspellbooks.capabilities.magic;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;

public class MagicEvents {
   public static final ResourceLocation PLAYER_MAGIC_RESOURCE = new ResourceLocation("irons_spellbooks", "player_magic");

   public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
      if (event.getObject() instanceof ServerPlayer serverPlayer && !((Entity)event.getObject()).getCapability(PlayerMagicProvider.PLAYER_MAGIC).isPresent()) {
         event.addCapability(PLAYER_MAGIC_RESOURCE, new PlayerMagicProvider(serverPlayer));
      }
   }

   public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
      event.register(MagicData.class);
   }

   public static void onWorldTick(LevelTickEvent event) {
      if (!event.level.f_46443_) {
         if (event.phase != Phase.START) {
            IronsSpellbooks.MAGIC_MANAGER.tick(event.level);
         }
      }
   }
}
