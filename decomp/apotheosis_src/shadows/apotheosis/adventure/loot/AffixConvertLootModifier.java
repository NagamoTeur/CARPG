package shadows.apotheosis.adventure.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.affix.AffixHelper;

public class AffixConvertLootModifier extends LootModifier {
   public static final Codec<AffixConvertLootModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, AffixConvertLootModifier::new));

   protected AffixConvertLootModifier(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      if (!Apotheosis.enableAdventure) {
         return generatedLoot;
      } else {
         for (AdventureConfig.LootPatternMatcher m : AdventureConfig.AFFIX_CONVERT_LOOT_RULES) {
            if (m.matches(context.getQueriedLootTableId())) {
               LootRarity.Clamped rarities = AdventureConfig.AFFIX_CONVERT_RARITIES.get(context.m_78952_().m_46472_().m_135782_());
               RandomSource rand = context.m_230907_();
               float luck = context.m_78945_();
               ObjectListIterator var8 = generatedLoot.iterator();

               while (var8.hasNext()) {
                  ItemStack s = (ItemStack)var8.next();
                  if (!LootCategory.forItem(s).isNone() && AffixHelper.getAffixes(s).isEmpty() && rand.m_188501_() <= m.chance()) {
                     LootController.createLootItem(s, LootRarity.random(rand, luck, rarities), rand);
                  }
               }
               break;
            }
         }

         return generatedLoot;
      }
   }

   public Codec<? extends IGlobalLootModifier> codec() {
      return CODEC;
   }
}
