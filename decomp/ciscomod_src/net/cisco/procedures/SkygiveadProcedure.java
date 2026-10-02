package net.cisco.procedures;

import java.util.Iterator;
import javax.annotation.Nullable;
import net.cisco.init.CiscoModModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class SkygiveadProcedure {
   @SubscribeEvent
   public static void onPlayerTick(PlayerTickEvent event) {
      if (event.phase == Phase.END) {
         execute(event, event.player);
      }
   }

   public static void execute(Entity entity) {
      execute(null, entity);
   }

   private static void execute(@Nullable Event event, Entity entity) {
      if (entity != null) {
         if (entity instanceof Player _playerHasItem
            && _playerHasItem.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.SKYSPLITTER.get()))
            && entity instanceof ServerPlayer _player) {
            Advancement _adv = _player.f_8924_.m_129889_().m_136041_(new ResourceLocation("cisco_mod:wind_favoured"));
            AdvancementProgress _ap = _player.m_8960_().m_135996_(_adv);
            if (!_ap.m_8193_()) {
               Iterator _iterator = _ap.m_8219_().iterator();

               while (_iterator.hasNext()) {
                  _player.m_8960_().m_135988_(_adv, (String)_iterator.next());
               }
            }
         }

         if ((new Object() {
                  public boolean checkGamemode(Entity _ent) {
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                     } else {
                        return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                           ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                              && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                           : false;
                     }
                  }
               })
               .checkGamemode(entity)
            && entity instanceof Player _playerHasItemx
            && _playerHasItemx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.CISCO_SPAWN_EGG.get()))
            && entity instanceof Player _playerx) {
            ItemStack _stktoremove = new ItemStack((ItemLike)CiscoModModItems.CISCO_SPAWN_EGG.get());
            _playerx.m_150109_().m_36022_(p -> _stktoremove.m_41720_() == p.m_41720_(), 1, _playerx.f_36095_.m_39730_());
         }

         if ((new Object() {
                  public boolean checkGamemode(Entity _ent) {
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                     } else {
                        return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                           ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                              && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                           : false;
                     }
                  }
               })
               .checkGamemode(entity)
            && entity instanceof Player _playerHasItemx
            && _playerHasItemx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.FELLKINGBOSS_SPAWN_EGG.get()))
            && entity instanceof Player _playerx) {
            ItemStack _stktoremove = new ItemStack((ItemLike)CiscoModModItems.FELLKINGBOSS_SPAWN_EGG.get());
            _playerx.m_150109_().m_36022_(p -> _stktoremove.m_41720_() == p.m_41720_(), 1, _playerx.f_36095_.m_39730_());
         }
      }
   }
}
