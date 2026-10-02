package net.cisco.procedures;

import java.util.Iterator;
import javax.annotation.Nullable;
import net.cisco.init.CiscoModModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class AscendantGiveAdProcedure {
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
         if (entity instanceof Player _playerHasItemxxx
            && _playerHasItemxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_ROUGE_HELMET.get()))
            && entity instanceof Player _playerHasItemxx
            && _playerHasItemxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_ROUGE_CHESTPLATE.get()))
            && entity instanceof Player _playerHasItemx
            && _playerHasItemx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_ROUGE_LEGGINGS.get()))
            && entity instanceof Player _playerHasItem
            && _playerHasItem.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_ROUGE_BOOTS.get()))
            && entity instanceof ServerPlayer _player) {
            Advancement _adv = _player.f_8924_.m_129889_().m_136041_(new ResourceLocation("cisco_mod:crimson_ascendant_ad"));
            AdvancementProgress _ap = _player.m_8960_().m_135996_(_adv);
            if (!_ap.m_8193_()) {
               Iterator _iterator = _ap.m_8219_().iterator();

               while (_iterator.hasNext()) {
                  _player.m_8960_().m_135988_(_adv, (String)_iterator.next());
               }
            }
         }

         if (entity instanceof Player _playerHasItemxxxxxxx
            && _playerHasItemxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_VIOLET_HELMET.get()))
            && entity instanceof Player _playerHasItemxxxxxx
            && _playerHasItemxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_VIOLET_CHESTPLATE.get()))
            && entity instanceof Player _playerHasItemxxxxx
            && _playerHasItemxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_VIOLET_LEGGINGS.get()))
            && entity instanceof Player _playerHasItemxxxx
            && _playerHasItemxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_VIOLET_BOOTS.get()))
            && entity instanceof ServerPlayer _playerx) {
            Advancement _adv = _playerx.f_8924_.m_129889_().m_136041_(new ResourceLocation("cisco_mod:violet_ascendant"));
            AdvancementProgress _ap = _playerx.m_8960_().m_135996_(_adv);
            if (!_ap.m_8193_()) {
               Iterator _iterator = _ap.m_8219_().iterator();

               while (_iterator.hasNext()) {
                  _playerx.m_8960_().m_135988_(_adv, (String)_iterator.next());
               }
            }
         }

         if (entity instanceof Player _playerHasItemxxxxxxxxxxx
            && _playerHasItemxxxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_HELMET.get()))
            && entity instanceof Player _playerHasItemxxxxxxxxxx
            && _playerHasItemxxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_CHESTPLATE.get()))
            && entity instanceof Player _playerHasItemxxxxxxxxx
            && _playerHasItemxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_LEGGINGS.get()))
            && entity instanceof Player _playerHasItemxxxxxxxx
            && _playerHasItemxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.ASCENDED_HERO_BOOTS.get()))
            && entity instanceof ServerPlayer _playerxx) {
            Advancement _adv = _playerxx.f_8924_.m_129889_().m_136041_(new ResourceLocation("cisco_mod:azure_ascendant"));
            AdvancementProgress _ap = _playerxx.m_8960_().m_135996_(_adv);
            if (!_ap.m_8193_()) {
               Iterator _iterator = _ap.m_8219_().iterator();

               while (_iterator.hasNext()) {
                  _playerxx.m_8960_().m_135988_(_adv, (String)_iterator.next());
               }
            }
         }

         if (entity instanceof Player _playerHasItemxxxxxxxxxxxxxxx
            && _playerHasItemxxxxxxxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.DESCENDED_HERO_HELMET.get()))
            && entity instanceof Player _playerHasItemxxxxxxxxxxxxxx
            && _playerHasItemxxxxxxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.DESCENDED_HERO_CHESTPLATE.get()))
            && entity instanceof Player _playerHasItemxxxxxxxxxxxxx
            && _playerHasItemxxxxxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.DESCENDED_HERO_LEGGINGS.get()))
            && entity instanceof Player _playerHasItemxxxxxxxxxxxx
            && _playerHasItemxxxxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.DESCENDED_HERO_BOOTS.get()))
            && entity instanceof ServerPlayer _playerxxx) {
            Advancement _adv = _playerxxx.f_8924_.m_129889_().m_136041_(new ResourceLocation("cisco_mod:heir_of_shadows"));
            AdvancementProgress _ap = _playerxxx.m_8960_().m_135996_(_adv);
            if (!_ap.m_8193_()) {
               Iterator _iterator = _ap.m_8219_().iterator();

               while (_iterator.hasNext()) {
                  _playerxxx.m_8960_().m_135988_(_adv, (String)_iterator.next());
               }
            }
         }

         if (entity instanceof Player _playerHasItemxxxxxxxxxxxxxxxxxxx
            && _playerHasItemxxxxxxxxxxxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.SOVEREIGN_ASCENDANT_HELMET.get()))
            && entity instanceof Player _playerHasItemxxxxxxxxxxxxxxxxxx
            && _playerHasItemxxxxxxxxxxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.SOVEREIGN_ASCENDANT_CHESTPLATE.get()))
            && entity instanceof Player _playerHasItemxxxxxxxxxxxxxxxxx
            && _playerHasItemxxxxxxxxxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.SOVEREIGN_ASCENDANT_LEGGINGS.get()))
            && entity instanceof Player _playerHasItemxxxxxxxxxxxxxxxx
            && _playerHasItemxxxxxxxxxxxxxxxx.m_150109_().m_36063_(new ItemStack((ItemLike)CiscoModModItems.SOVEREIGN_ASCENDANT_BOOTS.get()))
            && entity instanceof ServerPlayer _playerxxxx) {
            Advancement _adv = _playerxxxx.f_8924_.m_129889_().m_136041_(new ResourceLocation("cisco_mod:timeless_regalia"));
            AdvancementProgress _ap = _playerxxxx.m_8960_().m_135996_(_adv);
            if (!_ap.m_8193_()) {
               Iterator _iterator = _ap.m_8219_().iterator();

               while (_iterator.hasNext()) {
                  _playerxxxx.m_8960_().m_135988_(_adv, (String)_iterator.next());
               }
            }
         }
      }
   }
}
