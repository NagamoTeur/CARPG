package shadows.apotheosis.advancements;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.socket.SocketHelper;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.loot.LootRarity;

public class ExtendedInvTrigger extends InventoryChangeTrigger {
   public TriggerInstance m_7214_(JsonObject json, Composite andPred, DeserializationContext conditionsParser) {
      JsonObject slots = GsonHelper.m_13841_(json, "slots", new JsonObject());
      Ints occupied = Ints.m_55373_(slots.get("occupied"));
      Ints full = Ints.m_55373_(slots.get("full"));
      Ints empty = Ints.m_55373_(slots.get("empty"));
      ItemPredicate[] predicate = ItemPredicate.m_45055_(json.get("items"));
      if (json.has("apoth")) {
         predicate = this.deserializeApoth(json.getAsJsonObject("apoth"));
      }

      return new TriggerInstance(andPred, occupied, full, empty, predicate);
   }

   ItemPredicate[] deserializeApoth(JsonObject json) {
      String type = json.get("type").getAsString();
      if ("spawn_egg".equals(type)) {
         return new ItemPredicate[]{new ExtendedInvTrigger.TrueItemPredicate(s -> s.m_41720_() instanceof SpawnEggItem)};
      } else if ("enchanted".equals(type)) {
         Enchantment ench = json.has("enchantment")
            ? (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(json.get("enchantment").getAsString()))
            : null;
         Ints bound = Ints.m_55373_(json.get("level"));
         return new ItemPredicate[]{new ExtendedInvTrigger.TrueItemPredicate(s -> {
            Map<Enchantment, Integer> enchMap = EnchantmentHelper.m_44831_(s);
            return ench != null ? bound.m_55390_(enchMap.getOrDefault(ench, 0)) : enchMap.values().stream().anyMatch(bound::m_55390_);
         })};
      } else if ("affix".equals(type)) {
         return new ItemPredicate[]{new ExtendedInvTrigger.TrueItemPredicate(s -> !AffixHelper.getAffixes(s).isEmpty())};
      } else if ("rarity".equals(type)) {
         LootRarity rarity = LootRarity.byId(json.get("rarity").getAsString().toLowerCase(Locale.ROOT));
         return new ItemPredicate[]{new ExtendedInvTrigger.TrueItemPredicate(s -> AffixHelper.getRarity(s) == rarity)};
      } else if ("gem_rarity".equals(type)) {
         LootRarity rarity = LootRarity.byId(json.get("rarity").getAsString().toLowerCase(Locale.ROOT));
         return new ItemPredicate[]{new ExtendedInvTrigger.TrueItemPredicate(s -> GemItem.getLootRarity(s) == rarity)};
      } else if ("socket".equals(type)) {
         return new ItemPredicate[]{new ExtendedInvTrigger.TrueItemPredicate(s -> SocketHelper.getGems(s).stream().anyMatch(gem -> !gem.m_41619_()))};
      } else if ("nbt".equals(type)) {
         CompoundTag tag;
         try {
            tag = TagParser.m_129359_(GsonHelper.m_13805_(json.get("nbt"), "nbt"));
         } catch (CommandSyntaxException var5) {
            throw new RuntimeException(var5);
         }

         return new ItemPredicate[]{new ExtendedInvTrigger.TrueItemPredicate(s -> {
            if (!s.m_41782_()) {
               return false;
            } else {
               for (String key : tag.m_128431_()) {
                  if (!tag.m_128423_(key).equals(s.m_41783_().m_128423_(key))) {
                     return false;
                  }
               }

               return true;
            }
         })};
      } else {
         return new ItemPredicate[0];
      }
   }

   private static class TrueItemPredicate extends ItemPredicate {
      Predicate<ItemStack> predicate;

      TrueItemPredicate(Predicate<ItemStack> predicate) {
         this.predicate = predicate;
      }

      public boolean m_45049_(ItemStack item) {
         return this.predicate.test(item);
      }
   }
}
