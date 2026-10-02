package shadows.apotheosis.adventure.affix.socket.gem;

import com.google.common.base.Preconditions;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.ICondition.IContext;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.WeightedJsonReloadListener;

public class GemManager extends WeightedJsonReloadListener<Gem> {
   public static final GemManager INSTANCE = new GemManager();

   public GemManager() {
      super(AdventureModule.LOGGER, "gems", true, false);
   }

   protected void registerBuiltinSerializers() {
      this.registerSerializer(DEFAULT, Gem.SERIALIZER);
   }

   @SafeVarargs
   public static ItemStack createRandomGemStack(RandomSource rand, ServerLevel level, float luck, Predicate<Gem>... filter) {
      Gem gem = (Gem)INSTANCE.getRandomItem(rand, luck, filter);
      if (gem == null) {
         return ItemStack.f_41583_;
      } else {
         LootRarity.Clamped clamp = AdventureConfig.GEM_DIM_RARITIES.get(level.m_46472_().m_135782_());
         LootRarity rarity = gem.clamp(LootRarity.random(rand, luck, clamp));
         return createGemStack(gem, rarity);
      }
   }

   public static ItemStack createGemStack(Gem gem, LootRarity rarity) {
      ItemStack stack = new ItemStack((ItemLike)Apoth.Items.GEM.get());
      GemItem.setGem(stack, gem);
      GemItem.setLootRarity(stack, rarity);
      return stack;
   }

   protected <T extends Gem> void validateItem(T item) {
      super.validateItem(item);

      for (LootRarity r = item.minRarity; r != item.maxRarity; r = r.next()) {
         boolean atLeastOne = false;

         for (GemBonus bonus : item.bonuses) {
            if (bonus.supports(r)) {
               atLeastOne = true;
            }
         }

         Preconditions.checkArgument(
            atLeastOne, "No bonuses provided for supported rarity %s. At least one bonus must be provided, or the rarity should not be supported.", r.id()
         );
      }
   }

   public final IContext _getContext() {
      return this.getContext();
   }
}
