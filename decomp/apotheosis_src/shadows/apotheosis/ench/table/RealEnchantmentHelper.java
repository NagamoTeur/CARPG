package shadows.apotheosis.ench.table;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.random.WeightedEntry.IntrusiveBase;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.ench.EnchModule;
import shadows.apotheosis.ench.EnchantmentInfo;

public class RealEnchantmentHelper {
   public static int getEnchantmentCost(RandomSource rand, int num, float eterna, ItemStack stack) {
      int level = Math.round(eterna * 2.0F);
      if (num == 2) {
         return level;
      } else {
         float lowBound = 0.6F - 0.4F * (float)(1 - num);
         float highBound = 0.8F - 0.4F * (float)(1 - num);
         return Math.max(1, Math.round((float)level * Mth.m_216267_(rand, lowBound, highBound)));
      }
   }

   public static List<EnchantmentInstance> selectEnchantment(
      RandomSource rand, ItemStack stack, int level, float quanta, float arcana, float rectification, boolean treasure
   ) {
      List<EnchantmentInstance> chosenEnchants = Lists.newArrayList();
      int enchantability = stack.getEnchantmentValue();
      if (enchantability > 0) {
         float quantaFactor = 1.0F + Mth.m_216267_(rand, -1.0F + rectification / 100.0F, 1.0F) * quanta / 100.0F;
         level = Mth.m_14045_(Math.round((float)level * quantaFactor), 1, (int)(EnchantingStatManager.getAbsoluteMaxEterna() * 4.0F));
         ApothEnchantmentMenu.Arcana arcanaVals = ApothEnchantmentMenu.Arcana.getForThreshold(arcana);
         List<EnchantmentInstance> allEnchants = getAvailableEnchantmentResults(level, stack, treasure);
         Map<Enchantment, Integer> enchants = EnchantmentHelper.m_44831_(stack);
         allEnchants.removeIf(e -> enchants.containsKey(e.f_44947_));
         List<RealEnchantmentHelper.ArcanaEnchantmentData> possibleEnchants = allEnchants.stream()
            .map(d -> new RealEnchantmentHelper.ArcanaEnchantmentData(arcanaVals, d))
            .collect(Collectors.toList());
         if (!possibleEnchants.isEmpty()) {
            chosenEnchants.add(((RealEnchantmentHelper.ArcanaEnchantmentData)WeightedRandom.m_216822_(rand, possibleEnchants).get()).data);
            removeIncompatible(possibleEnchants, (EnchantmentInstance)Util.m_137509_(chosenEnchants));
            if (arcana >= 25.0F && !possibleEnchants.isEmpty()) {
               chosenEnchants.add(((RealEnchantmentHelper.ArcanaEnchantmentData)WeightedRandom.m_216822_(rand, possibleEnchants).get()).data);
               removeIncompatible(possibleEnchants, (EnchantmentInstance)Util.m_137509_(chosenEnchants));
            }

            if (arcana >= 75.0F && !possibleEnchants.isEmpty()) {
               chosenEnchants.add(((RealEnchantmentHelper.ArcanaEnchantmentData)WeightedRandom.m_216822_(rand, possibleEnchants).get()).data);
            }

            int randomBound = 50;
            if (level > 45) {
               level = (int)((float)level * 1.15F);
            }

            while (rand.m_188503_(randomBound) <= level) {
               if (!chosenEnchants.isEmpty()) {
                  removeIncompatible(possibleEnchants, (EnchantmentInstance)Util.m_137509_(chosenEnchants));
               }

               if (possibleEnchants.isEmpty()) {
                  break;
               }

               chosenEnchants.add(((RealEnchantmentHelper.ArcanaEnchantmentData)WeightedRandom.m_216822_(rand, possibleEnchants).get()).data);
               level /= 2;
            }
         }
      }

      return ((IEnchantableItem)stack.m_41720_()).selectEnchantments(chosenEnchants, rand, stack, level, quanta, arcana, treasure);
   }

   public static void removeIncompatible(List<RealEnchantmentHelper.ArcanaEnchantmentData> list, EnchantmentInstance data) {
      Iterator<RealEnchantmentHelper.ArcanaEnchantmentData> iterator = list.iterator();

      while (iterator.hasNext()) {
         if (!data.f_44947_.m_44695_(iterator.next().data.f_44947_)) {
            iterator.remove();
         }
      }
   }

   public static List<EnchantmentInstance> getAvailableEnchantmentResults(int power, ItemStack stack, boolean allowTreasure) {
      List<EnchantmentInstance> list = new ArrayList<>();
      IEnchantableItem enchi = (IEnchantableItem)stack.m_41720_();
      allowTreasure = enchi.isTreasureAllowed(stack, allowTreasure);

      for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
         EnchantmentInfo info = EnchModule.getEnchInfo(enchantment);
         if ((!info.isTreasure() || allowTreasure)
            && info.isDiscoverable()
            && (enchantment.canApplyAtEnchantingTable(stack) || enchi.forciblyAllowsTableEnchantment(stack, enchantment))) {
            for (int level = info.getMaxLevel(); level > enchantment.m_44702_() - 1; level--) {
               if (power >= info.getMinPower(level) && power <= info.getMaxPower(level)) {
                  list.add(new EnchantmentInstance(enchantment, level));
                  break;
               }
            }
         }
      }

      return list;
   }

   public static class ArcanaEnchantmentData extends IntrusiveBase {
      EnchantmentInstance data;

      public ArcanaEnchantmentData(ApothEnchantmentMenu.Arcana arcana, EnchantmentInstance data) {
         super(arcana.getRarities()[data.f_44947_.m_44699_().ordinal()]);
         this.data = data;
      }
   }
}
