package com.cerbon.bosses_of_mass_destruction.block;

import com.cerbon.bosses_of_mass_destruction.block.custom.LevitationBlockEntity;
import com.cerbon.bosses_of_mass_destruction.block.custom.MobWardBlockEntity;
import com.cerbon.bosses_of_mass_destruction.block.custom.MonolithBlockEntity;
import com.cerbon.bosses_of_mass_destruction.block.custom.VoidBlossomSummonBlockEntity;
import com.cerbon.bosses_of_mass_destruction.block.custom.VoidLilyBlockEntity;
import com.cerbon.bosses_of_mass_destruction.client.render.BMDBlockEntityRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.GeoModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BMDBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> BLOCKS_ENTITIES = DeferredRegister.create(
      ForgeRegistries.BLOCK_ENTITY_TYPES, "bosses_of_mass_destruction"
   );
   public static final RegistryObject<BlockEntityType<MobWardBlockEntity>> MOB_WARD = BLOCKS_ENTITIES.register(
      "mob_ward", () -> Builder.m_155273_(MobWardBlockEntity::new, new Block[]{(Block)BMDBlocks.MOB_WARD.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<MonolithBlockEntity>> MONOLITH_BLOCK_ENTITY = BLOCKS_ENTITIES.register(
      "monolith_block", () -> Builder.m_155273_(MonolithBlockEntity::new, new Block[]{(Block)BMDBlocks.MONOLITH_BLOCK.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<LevitationBlockEntity>> LEVITATION_BLOCK_ENTITY = BLOCKS_ENTITIES.register(
      "levitation_block", () -> Builder.m_155273_(LevitationBlockEntity::new, new Block[]{(Block)BMDBlocks.LEVITATION_BLOCK.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<VoidBlossomSummonBlockEntity>> VOID_BLOSSOM_SUMMON_BLOCK_ENTITY = BLOCKS_ENTITIES.register(
      "void_blossom_block",
      () -> Builder.m_155273_(VoidBlossomSummonBlockEntity::new, new Block[]{(Block)BMDBlocks.VOID_BLOSSOM_SUMMON_BLOCK.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<VoidLilyBlockEntity>> VOID_LILY_BLOCK_ENTITY = BLOCKS_ENTITIES.register(
      "void_lily", () -> Builder.m_155273_(VoidLilyBlockEntity::new, new Block[]{(Block)BMDBlocks.VOID_LILY_BLOCK.get()}).m_58966_(null)
   );

   @OnlyIn(Dist.CLIENT)
   public static void initClient() {
      BlockEntityRenderers.m_173590_(
         (BlockEntityType)LEVITATION_BLOCK_ENTITY.get(),
         context -> new BMDBlockEntityRenderer(
               context,
               new GeoModel<LevitationBlockEntity>(
                  entity -> new ResourceLocation("bosses_of_mass_destruction", "geo/levitation_block.geo.json"),
                  entity -> new ResourceLocation("bosses_of_mass_destruction", "textures/block/levitation_block.png"),
                  new ResourceLocation("bosses_of_mass_destruction", "animations/levitation_block.animation.json"),
                  (animatable, data, geoModel) -> {
                  }
               ),
               (bone, packedLight) -> 15728880
            )
      );
   }

   public static void register(IEventBus eventBus) {
      BLOCKS_ENTITIES.register(eventBus);
   }
}
