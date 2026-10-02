package com.aizistral.enigmaticlegacy.registries;

import com.aizistral.enigmaticlegacy.blocks.BlockAstralDust;
import com.aizistral.enigmaticlegacy.blocks.BlockBigLamp;
import com.aizistral.enigmaticlegacy.blocks.BlockCosmicCake;
import com.aizistral.enigmaticlegacy.blocks.BlockEndAnchor;
import com.aizistral.enigmaticlegacy.blocks.BlockMassiveLamp;
import com.aizistral.enigmaticlegacy.items.CosmicCake;
import com.aizistral.enigmaticlegacy.items.EndAnchor;
import com.aizistral.enigmaticlegacy.items.generic.GenericBlockItem;
import com.aizistral.etherium.blocks.BlockEtherium;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class EnigmaticBlocks extends AbstractRegistry<Block> {
   private static final Map<ResourceLocation, EnigmaticBlocks.BlockItemSupplier> BLOCK_ITEM_MAP = new HashMap<>();
   private static final EnigmaticBlocks INSTANCE = new EnigmaticBlocks();
   @ObjectHolder(
      value = "enigmaticlegacy:massive_lamp",
      registryName = "block"
   )
   public static final BlockMassiveLamp MASSIVE_LAMP = null;
   @ObjectHolder(
      value = "enigmaticlegacy:massive_shroomlamp",
      registryName = "block"
   )
   public static final BlockMassiveLamp MASSIVE_SHROOMLAMP = null;
   @ObjectHolder(
      value = "enigmaticlegacy:massive_redstonelamp",
      registryName = "block"
   )
   public static final BlockMassiveLamp MASSIVE_REDSTONELAMP = null;
   @ObjectHolder(
      value = "enigmaticlegacy:big_lamp",
      registryName = "block"
   )
   public static final BlockBigLamp BIG_LAMP = null;
   @ObjectHolder(
      value = "enigmaticlegacy:big_shroomlamp",
      registryName = "block"
   )
   public static final BlockBigLamp BIG_SHROOMLAMP = null;
   @ObjectHolder(
      value = "enigmaticlegacy:big_redstonelamp",
      registryName = "block"
   )
   public static final BlockBigLamp BIG_REDSTONELAMP = null;
   @ObjectHolder(
      value = "enigmaticlegacy:etherium_block",
      registryName = "block"
   )
   public static final BlockEtherium ETHERIUM_BLOCK = null;
   @ObjectHolder(
      value = "enigmaticlegacy:cosmic_cake",
      registryName = "block"
   )
   public static final BlockCosmicCake COSMIC_CAKE = null;
   @ObjectHolder(
      value = "enigmaticlegacy:astral_block",
      registryName = "block"
   )
   public static final BlockAstralDust ASTRAL_BLOCK = null;
   @ObjectHolder(
      value = "enigmaticlegacy:end_anchor",
      registryName = "block"
   )
   public static final BlockEndAnchor END_ANCHOR = null;

   private EnigmaticBlocks() {
      super(ForgeRegistries.BLOCKS);
      this.register("massive_lamp", BlockMassiveLamp::new, GenericBlockItem::new);
      this.register("massive_shroomlamp", BlockMassiveLamp::new, GenericBlockItem::new);
      this.register("massive_redstonelamp", BlockMassiveLamp::new, GenericBlockItem::new);
      this.register("big_lamp", BlockBigLamp::new, GenericBlockItem::new);
      this.register("big_shroomlamp", BlockBigLamp::new, GenericBlockItem::new);
      this.register("big_redstonelamp", BlockBigLamp::new, GenericBlockItem::new);
      this.register("cosmic_cake", BlockCosmicCake::new, block -> new CosmicCake());
      this.register("end_anchor", BlockEndAnchor::new, block -> new EndAnchor());
      this.register("etherium_block", BlockEtherium::new, block -> new GenericBlockItem(block, GenericBlockItem.getDefaultProperties().m_41497_(Rarity.RARE)));
      this.register(
         "astral_block",
         BlockAstralDust::new,
         block -> new GenericBlockItem(block, GenericBlockItem.getDefaultProperties().m_41497_(Rarity.EPIC).m_41491_(null))
      );
   }

   protected void register(String name, Supplier<Block> block, EnigmaticBlocks.BlockItemSupplier item) {
      super.register(name, block);
      BLOCK_ITEM_MAP.put(new ResourceLocation("enigmaticlegacy", name), item);
   }

   protected static Map<ResourceLocation, EnigmaticBlocks.BlockItemSupplier> getBlockItemMap() {
      return Collections.unmodifiableMap(BLOCK_ITEM_MAP);
   }

   @FunctionalInterface
   protected interface BlockItemSupplier extends Function<Block, BlockItem> {
      BlockItem apply(Block var1);
   }
}
