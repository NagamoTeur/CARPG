package com.hollingsworth.arsnouveau.common.event;

import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketSyncLitEntities;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.PacketDistributor;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class LightEvents {
   @SubscribeEvent
   public static void onTick(PlayerTickEvent e) {
      if (!e.player.f_19853_.f_46443_) {
         if (e.player.f_19853_.m_46467_() % 100L == 0L && e.player.m_20194_() != null && e.phase == Phase.END && e.player instanceof ServerPlayer serverPlayer) {
            List<Integer> litID = new ArrayList<>();

            for (ServerPlayer player : e.player.m_20194_().m_6846_().m_11314_()) {
               NonNullList<ItemStack> list = player.f_36093_.f_35974_;

               for (int i = 0; i < 9; i++) {
                  ItemStack jar = (ItemStack)list.get(i);
                  if (jar.m_41720_() == ItemsRegistry.JAR_OF_LIGHT.m_5456_()) {
                     litID.add(player.m_19879_());
                     break;
                  }
               }
            }

            Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PacketSyncLitEntities(litID));
         }
      }
   }
}
