package shadows.apotheosis.adventure.loot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import org.apache.commons.lang3.mutable.MutableInt;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;

public class LootController {
   static Random jRand = new Random();

   public static ItemStack createLootItem(ItemStack stack, LootRarity rarity, RandomSource rand) {
      LootCategory cat = LootCategory.forItem(stack);
      return cat.isNone() ? stack : createLootItem(stack, cat, rarity, rand);
   }

   public static ItemStack createLootItem(ItemStack stack, LootCategory cat, LootRarity rarity, RandomSource rand) {
      Set<Affix> selected = new LinkedHashSet<>();
      MutableInt sockets = new MutableInt(0);
      float durability = 0.0F;

      for (LootRarity.LootRule rule : rarity.rules()) {
         if (rule.type() == AffixType.DURABILITY) {
            durability = rule.chance();
         } else {
            rule.execute(stack, rarity, selected, sockets, rand);
         }
      }

      Map<Affix, AffixInstance> loaded = new HashMap<>();
      List<AffixInstance> nameList = new ArrayList<>(selected.size());

      for (Affix a : selected) {
         AffixInstance inst = new AffixInstance(a, stack, rarity, rand.m_188501_());
         loaded.put(a, inst);
         nameList.add(inst);
      }

      if (nameList.size() == 0) {
         throw new RuntimeException(
            String.format("Failed to locate any affixes for %s{%s} with category %s and rarity %s.", stack.m_41720_(), stack.m_41783_(), cat, rarity)
         );
      } else {
         if (sockets.intValue() > 0) {
            loaded.put((Affix)Apoth.Affixes.SOCKET.get(), new AffixInstance((Affix)Apoth.Affixes.SOCKET.get(), stack, rarity, (float)sockets.intValue()));
         }

         if (durability > 0.0F) {
            loaded.put(
               (Affix)Apoth.Affixes.DURABLE.get(),
               new AffixInstance((Affix)Apoth.Affixes.DURABLE.get(), stack, rarity, durability + AffixHelper.step(-0.07F, 14, 0.01F).get(rand.m_188501_()))
            );
         }

         jRand.setSeed(rand.m_188505_());
         Collections.shuffle(nameList, jRand);
         String key = nameList.size() > 1 ? "misc.apotheosis.affix_name.three" : "misc.apotheosis.affix_name.two";
         MutableComponent name = Component.m_237110_(
               key, new Object[]{nameList.get(0).getName(true), "", nameList.size() > 1 ? nameList.get(1).getName(false) : ""}
            )
            .m_130948_(Style.f_131099_.m_131148_(rarity.color()));
         AffixHelper.setRarity(stack, rarity);
         AffixHelper.setAffixes(stack, loaded);
         AffixHelper.setName(stack, name);
         return stack;
      }
   }

   public static ItemStack createRandomLootItem(RandomSource rand, @Nullable LootRarity rarity, Player player, ServerLevelAccessor level) {
      AffixLootEntry entry = (AffixLootEntry)AffixLootManager.INSTANCE
         .getRandomItem(rand, player.m_36336_(), new Predicate[]{IDimensional.matches(level.m_6018_()), GameStagesCompat.IStaged.matches(player)});
      if (entry == null) {
         return ItemStack.f_41583_;
      } else {
         if (rarity == null) {
            rarity = LootRarity.random(rand, player.m_36336_(), entry);
         }

         return createLootItem(entry.getStack(), entry.getType(), rarity, rand);
      }
   }
}
