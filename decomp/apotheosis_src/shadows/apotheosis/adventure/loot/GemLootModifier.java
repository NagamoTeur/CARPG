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
import shadows.apotheosis.adventure.affix.socket.gem.GemManager;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;

public class GemLootModifier extends LootModifier {
   public static final Codec<GemLootModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, GemLootModifier::new));

   protected GemLootModifier(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      if (!Apotheosis.enableAdventure) {
         return generatedLoot;
      } else {
         for (AdventureConfig.LootPatternMatcher m : AdventureConfig.GEM_LOOT_RULES) {
            if (m.matches(context.getQueriedLootTableId())) {
               if (context.m_230907_().m_188501_() <= m.chance()) {
                  Player player = GemLootPoolEntry.findPlayer(context);
                  if (player == null) {
                     return generatedLoot;
                  }

                  float luck = context.m_78945_();
                  ItemStack gem = GemManager.createRandomGemStack(
                     context.m_230907_(), context.m_78952_(), luck, IDimensional.matches(context.m_78952_()), GameStagesCompat.IStaged.matches(player)
                  );
                  generatedLoot.add(gem);
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
