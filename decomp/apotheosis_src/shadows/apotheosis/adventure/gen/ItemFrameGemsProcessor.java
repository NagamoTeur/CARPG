package shadows.apotheosis.adventure.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.affix.socket.gem.GemManager;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;

public class ItemFrameGemsProcessor extends StructureProcessor {
   public static final Codec<ItemFrameGemsProcessor> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(ResourceLocation.f_135803_.fieldOf("loot_table").forGetter(ItemFrameGemsProcessor::getLootTable))
            .apply(instance, ItemFrameGemsProcessor::new)
   );
   private final ResourceLocation lootTable;

   public ResourceLocation getLootTable() {
      return this.lootTable;
   }

   public ItemFrameGemsProcessor(ResourceLocation lootTable) {
      this.lootTable = lootTable;
   }

   protected StructureProcessorType<?> m_6953_() {
      return AdventureModule.ITEM_FRAME_LOOT;
   }

   public StructureEntityInfo processEntity(
      LevelReader world,
      BlockPos seedPos,
      StructureEntityInfo rawEntityInfo,
      StructureEntityInfo entityInfo,
      StructurePlaceSettings placementSettings,
      StructureTemplate template
   ) {
      CompoundTag entityNBT = entityInfo.f_74685_;
      String id = entityNBT.m_128461_("id");
      if (world instanceof ServerLevelAccessor sla && "minecraft:item_frame".equals(id)) {
         this.writeEntityNBT(sla.m_6018_(), entityInfo.f_74684_, placementSettings.m_230326_(entityInfo.f_74684_), entityNBT, placementSettings);
      }

      return entityInfo;
   }

   protected void writeEntityNBT(ServerLevel world, BlockPos pos, RandomSource rand, CompoundTag nbt, StructurePlaceSettings settings) {
      ItemStack stack = GemManager.createRandomGemStack(rand, world, 0.0F, IDimensional.matches(world));
      nbt.m_128365_("Item", stack.serializeNBT());
      nbt.m_128405_("TileX", pos.m_123341_());
      nbt.m_128405_("TileY", pos.m_123342_());
      nbt.m_128405_("TileZ", pos.m_123343_());
   }
}
