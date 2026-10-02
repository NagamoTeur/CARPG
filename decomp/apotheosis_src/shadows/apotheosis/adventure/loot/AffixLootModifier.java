package shadows.apotheosis.adventure.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.AdventureConfig;

public class AffixLootModifier extends LootModifier {
   public static final Codec<AffixLootModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, AffixLootModifier::new));

   protected AffixLootModifier(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      if (!Apotheosis.enableAdventure) {
         return generatedLoot;
      } else {
         for (AdventureConfig.LootPatternMatcher m : AdventureConfig.AFFIX_ITEM_LOOT_RULES) {
            if (m.matches(context.getQueriedLootTableId())) {
               if (context.m_230907_().m_188501_() <= m.chance()) {
                  Player player = GemLootPoolEntry.findPlayer(context);
                  if (player == null) {
                     return generatedLoot;
                  }

                  ItemStack affixItem = LootController.createRandomLootItem(context.m_230907_(), null, player, context.m_78952_());
                  if (!affixItem.m_41619_()) {
                     affixItem.m_41783_().m_128379_("apoth_rchest", true);
                     generatedLoot.add(affixItem);
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
