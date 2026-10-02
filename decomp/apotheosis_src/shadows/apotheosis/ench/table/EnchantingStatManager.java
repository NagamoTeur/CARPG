package shadows.apotheosis.ench.table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.ench.EnchModule;
import shadows.apotheosis.ench.api.IEnchantingBlock;
import shadows.placebo.json.PSerializer;
import shadows.placebo.json.PlaceboJsonReloadListener;
import shadows.placebo.json.TypeKeyed.TypeKeyedBase;

public class EnchantingStatManager extends PlaceboJsonReloadListener<EnchantingStatManager.BlockStats> {
   public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   public static final EnchantingStatManager INSTANCE = new EnchantingStatManager();
   private final Map<Block, EnchantingStatManager.Stats> statsPerBlock = new HashMap<>();
   private float absoluteMaxEterna = 50.0F;

   protected EnchantingStatManager() {
      super(EnchModule.LOGGER, "enchanting_stats", true, false);
   }

   protected void registerBuiltinSerializers() {
      this.registerSerializer(DEFAULT, EnchantingStatManager.BlockStats.SERIALIZER);
   }

   protected void beginReload() {
      super.beginReload();
      this.statsPerBlock.clear();
   }

   protected void onReload() {
      super.onReload();

      for (EnchantingStatManager.BlockStats bStats : this.registry.values()) {
         bStats.blocks.forEach(b -> this.statsPerBlock.put(b, bStats.stats));
      }

      this.computeAbsoluteMaxEterna();
   }

   public static float getEterna(BlockState state, Level world, BlockPos pos) {
      Block block = state.m_60734_();
      return INSTANCE.statsPerBlock.containsKey(block) ? INSTANCE.statsPerBlock.get(block).eterna : state.getEnchantPowerBonus(world, pos);
   }

   public static float getMaxEterna(BlockState state, Level world, BlockPos pos) {
      Block block = state.m_60734_();
      return INSTANCE.statsPerBlock.containsKey(block)
         ? INSTANCE.statsPerBlock.get(block).maxEterna
         : ((IEnchantingBlock)block).getMaxEnchantingPower(state, world, pos);
   }

   public static float getQuanta(BlockState state, Level world, BlockPos pos) {
      Block block = state.m_60734_();
      if (INSTANCE.statsPerBlock.containsKey(block)) {
         return INSTANCE.statsPerBlock.get(block).quanta;
      } else {
         return block instanceof IEnchantingBlock ? ((IEnchantingBlock)block).getQuantaBonus(state, world, pos) : 0.0F;
      }
   }

   public static float getArcana(BlockState state, Level world, BlockPos pos) {
      Block block = state.m_60734_();
      if (INSTANCE.statsPerBlock.containsKey(block)) {
         return INSTANCE.statsPerBlock.get(block).arcana;
      } else {
         return block instanceof IEnchantingBlock ? ((IEnchantingBlock)block).getArcanaBonus(state, world, pos) : 0.0F;
      }
   }

   public static float getQuantaRectification(BlockState state, Level world, BlockPos pos) {
      Block block = state.m_60734_();
      if (INSTANCE.statsPerBlock.containsKey(block)) {
         return INSTANCE.statsPerBlock.get(block).rectification;
      } else {
         return block instanceof IEnchantingBlock ? ((IEnchantingBlock)block).getQuantaRectification(state, world, pos) : 0.0F;
      }
   }

   public static int getBonusClues(BlockState state, Level world, BlockPos pos) {
      Block block = state.m_60734_();
      if (INSTANCE.statsPerBlock.containsKey(block)) {
         return INSTANCE.statsPerBlock.get(block).clues;
      } else {
         return block instanceof IEnchantingBlock ? ((IEnchantingBlock)block).getBonusClues(state, world, pos) : 0;
      }
   }

   public static float getAbsoluteMaxEterna() {
      return INSTANCE.absoluteMaxEterna;
   }

   private void computeAbsoluteMaxEterna() {
      this.absoluteMaxEterna = this.registry.values().stream().max(Comparator.comparingDouble(s -> (double)s.stats.maxEterna)).get().stats.maxEterna;
   }

   public static class BlockStats extends TypeKeyedBase<EnchantingStatManager.BlockStats> {
      public static Codec<EnchantingStatManager.BlockStats> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  Codec.list(ForgeRegistries.BLOCKS.getCodec()).optionalFieldOf("blocks", Collections.emptyList()).forGetter(bs -> bs.blocks),
                  TagKey.m_203877_(Registry.f_122901_).optionalFieldOf("tag").forGetter(bs -> Optional.empty()),
                  ForgeRegistries.BLOCKS.getCodec().optionalFieldOf("block").forGetter(bs -> Optional.empty()),
                  EnchantingStatManager.Stats.CODEC.fieldOf("stats").forGetter(bs -> bs.stats)
               )
               .apply(inst, EnchantingStatManager.BlockStats::new)
      );
      public static final PSerializer<EnchantingStatManager.BlockStats> SERIALIZER = PSerializer.fromCodec("Enchanting Stats", CODEC);
      public final List<Block> blocks = new ArrayList<>();
      public final EnchantingStatManager.Stats stats;

      public BlockStats(List<Block> blocks, Optional<TagKey<Block>> tag, Optional<Block> block, EnchantingStatManager.Stats stats) {
         if (!blocks.isEmpty()) {
            this.blocks.addAll(blocks);
         }

         if (tag.isPresent()) {
            this.blocks.addAll(EnchantingStatManager.INSTANCE.getContext().getTag(tag.get()).stream().map(Holder::m_203334_).toList());
         }

         if (block.isPresent()) {
            this.blocks.add(block.get());
         }

         this.stats = stats;
      }

      public PSerializer<? extends EnchantingStatManager.BlockStats> getSerializer() {
         return SERIALIZER;
      }
   }

   public static record Stats(float maxEterna, float eterna, float quanta, float arcana, float rectification, int clues) {
      public static Codec<EnchantingStatManager.Stats> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  Codec.FLOAT.optionalFieldOf("maxEterna", 15.0F).forGetter(EnchantingStatManager.Stats::maxEterna),
                  Codec.FLOAT.optionalFieldOf("eterna", 0.0F).forGetter(EnchantingStatManager.Stats::eterna),
                  Codec.FLOAT.optionalFieldOf("quanta", 0.0F).forGetter(EnchantingStatManager.Stats::quanta),
                  Codec.FLOAT.optionalFieldOf("arcana", 0.0F).forGetter(EnchantingStatManager.Stats::arcana),
                  Codec.FLOAT.optionalFieldOf("rectification", 0.0F).forGetter(EnchantingStatManager.Stats::rectification),
                  Codec.INT.optionalFieldOf("clues", 0).forGetter(EnchantingStatManager.Stats::clues)
               )
               .apply(inst, EnchantingStatManager.Stats::new)
      );

      public void write(FriendlyByteBuf buf) {
         buf.writeFloat(this.maxEterna);
         buf.writeFloat(this.eterna);
         buf.writeFloat(this.quanta);
         buf.writeFloat(this.arcana);
         buf.writeFloat(this.rectification);
         buf.writeByte(this.clues);
      }

      public static EnchantingStatManager.Stats read(FriendlyByteBuf buf) {
         return new EnchantingStatManager.Stats(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readByte());
      }
   }
}
