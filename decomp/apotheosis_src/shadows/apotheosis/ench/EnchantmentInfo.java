package shadows.apotheosis.ench;

import java.math.BigDecimal;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.registries.ForgeRegistries;
import s_com.udojava.evalex.Expression;
import shadows.apotheosis.ench.table.EnchantingStatManager;
import shadows.placebo.config.Configuration;

public class EnchantmentInfo {
   protected final Enchantment ench;
   protected final int maxLevel;
   protected final int maxLootLevel;
   protected final boolean treasure;
   protected final boolean discoverable;
   protected final boolean lootable;
   protected final boolean tradeable;
   protected final EnchantmentInfo.PowerFunc maxPower;
   protected final EnchantmentInfo.PowerFunc minPower;

   public EnchantmentInfo(
      Enchantment ench,
      int maxLevel,
      int maxLootLevel,
      EnchantmentInfo.PowerFunc max,
      EnchantmentInfo.PowerFunc min,
      boolean treasure,
      boolean discoverable,
      boolean lootable,
      boolean tradeable
   ) {
      this.ench = ench;
      this.maxLevel = maxLevel;
      this.maxLootLevel = maxLootLevel;
      this.maxPower = max;
      this.minPower = min;
      this.treasure = treasure;
      this.discoverable = discoverable;
      this.lootable = lootable;
      this.tradeable = tradeable;
   }

   @Deprecated
   public EnchantmentInfo(Enchantment ench) {
      this(ench, ench.m_6586_(), ench.m_6586_(), defaultMax(ench), defaultMin(ench), ench.m_6591_(), ench.m_6592_(), ench.m_6592_(), ench.m_6594_());
   }

   public int getMaxLevel() {
      return Math.min(EnchModule.ENCH_HARD_CAPS.getOrDefault(this.ench, 127), this.maxLevel);
   }

   public int getMaxLootLevel() {
      return Math.min(EnchModule.ENCH_HARD_CAPS.getOrDefault(this.ench, 127), this.maxLootLevel);
   }

   public int getMinPower(int level) {
      return this.minPower.getPower(level);
   }

   public int getMaxPower(int level) {
      return this.maxPower.getPower(level);
   }

   public boolean isDiscoverable() {
      return this.discoverable;
   }

   public boolean isTreasure() {
      return this.treasure;
   }

   public boolean isLootable() {
      return this.lootable;
   }

   public boolean isTradeable() {
      return this.tradeable;
   }

   public static EnchantmentInfo load(Enchantment ench, Configuration cfg) {
      String category = ForgeRegistries.ENCHANTMENTS.getKey(ench).toString();
      int max = cfg.getInt(
         "Max Level", category, EnchModule.getDefaultMax(ench), 1, 127, "The max level of this enchantment - originally " + ench.m_6586_() + "."
      );
      int maxLoot = cfg.getInt("Max Loot Level", category, ench.m_6586_(), 1, 127, "The max level of this enchantment available from loot sources.");
      String maxF = cfg.getString(
         "Max Power Function",
         category,
         "",
         "A function to determine the max enchanting power.  The variable \"x\" is level.  See: https://github.com/uklimaschewski/EvalEx#usage-examples"
      );
      String minF = cfg.getString("Min Power Function", category, "", "A function to determine the min enchanting power.");
      EnchantmentInfo.PowerFunc maxPower = (EnchantmentInfo.PowerFunc)(maxF.isEmpty() ? defaultMax(ench) : new EnchantmentInfo.ExpressionPowerFunc(maxF));
      EnchantmentInfo.PowerFunc minPower = (EnchantmentInfo.PowerFunc)(minF.isEmpty() ? defaultMin(ench) : new EnchantmentInfo.ExpressionPowerFunc(minF));
      boolean treasure = cfg.getBoolean("Treasure", category, ench.m_6591_(), "If this enchantment is only available by loot sources.");
      boolean discoverable = cfg.getBoolean(
         "Discoverable", category, ench.m_6592_(), "If this enchantment is obtainable via enchanting and enchanted loot items."
      );
      boolean lootable = cfg.getBoolean("Lootable", category, ench.m_6592_(), "If enchanted books of this enchantment are available via loot sources.");
      boolean tradeable = cfg.getBoolean("Tradeable", category, ench.m_6594_(), "If enchanted books of this enchantment are available via villager trades.");
      EnchantmentInfo info = new EnchantmentInfo(ench, max, maxLoot, maxPower, minPower, treasure, discoverable, lootable, tradeable);
      String rarity = cfg.getString(
         "Rarity", category, ench.m_44699_().name(), "The rarity of this enchantment.  Valid values are COMMON, UNCOMMON, RARE, and VERY_RARE."
      );

      try {
         Rarity r = Rarity.valueOf(rarity);
         ench.f_44674_ = r;
      } catch (Exception var16) {
         EnchModule.LOGGER.error("Failed to parse rarity for {}, as {} is not a valid rarity string.", category, rarity);
      }

      return info;
   }

   public static EnchantmentInfo.PowerFunc defaultMax(Enchantment ench) {
      return level -> (int)(EnchantingStatManager.getAbsoluteMaxEterna() * 4.0F);
   }

   public static EnchantmentInfo.PowerFunc defaultMin(Enchantment ench) {
      return level -> {
         if (level > ench.m_6586_() && level > 1) {
            int diff = ench.m_6183_(ench.m_6586_()) - ench.m_6183_(ench.m_6586_() - 1);
            if (diff == 0) {
               diff = 15;
            }

            return ench.m_6183_(level) + diff * (int)Math.pow((double)(level - ench.m_6586_()), 1.6);
         } else {
            return ench.m_6183_(level);
         }
      };
   }

   public static class ExpressionPowerFunc implements EnchantmentInfo.PowerFunc {
      Expression ex;

      public ExpressionPowerFunc(String func) {
         this.ex = new Expression(func);
      }

      @Override
      public int getPower(int level) {
         return this.ex.setVariable("x", new BigDecimal(level)).eval().intValue();
      }
   }

   public interface PowerFunc {
      int getPower(int var1);
   }
}
