package net.xylonity.knightquest.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition.Builder;
import net.xylonity.knightquest.registry.KnightQuestItems;

public class KQGlobalLootModifiersProvider extends GlobalLootModifierProvider {
   private static final ResourceLocation RATMAN_ID = new ResourceLocation("knightquest", "entities/ratman");
   private static final ResourceLocation LIZZY_ID = new ResourceLocation("knightquest", "entities/lizzy");

   public KQGlobalLootModifiersProvider(DataGenerator gen, String modid) {
      super(gen, modid);
   }

   protected void start() {
      this.add(
         RATMAN_ID.m_135815_() + "_ratman_eye",
         new KQAddItemModifier(
            new LootItemCondition[]{new Builder(RATMAN_ID).m_6409_()}, new ItemStack((ItemLike)KnightQuestItems.RATMAN_EYE.get()).m_41720_(), 0.5F
         )
      );
      this.add(
         LIZZY_ID.m_135815_() + "_lizzy_scale",
         new KQAddItemModifier(
            new LootItemCondition[]{new Builder(LIZZY_ID).m_6409_()}, new ItemStack((ItemLike)KnightQuestItems.LIZZY_SCALE.get()).m_41720_(), 0.5F
         )
      );
   }
}
