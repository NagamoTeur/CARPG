package com.github.L_Ender.cataclysm.client.render;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.blocks.Abstract_Cataclysm_Skull_Block;
import com.github.L_Ender.cataclysm.blocks.Cataclysm_Skull_Block;
import com.github.L_Ender.cataclysm.client.model.block.Abyssal_Egg_Model;
import com.github.L_Ender.cataclysm.client.model.block.Altar_of_Abyss_Model;
import com.github.L_Ender.cataclysm.client.model.block.Altar_of_Amethyst_Model;
import com.github.L_Ender.cataclysm.client.model.block.Altar_of_Fire_Model;
import com.github.L_Ender.cataclysm.client.model.block.Altar_of_Void_Model;
import com.github.L_Ender.cataclysm.client.model.block.Cataclysm_Skull_Model_Base;
import com.github.L_Ender.cataclysm.client.model.block.EMP_Model;
import com.github.L_Ender.cataclysm.client.model.block.Mechanical_Anvil_Model;
import com.github.L_Ender.cataclysm.client.model.entity.Coral_Bardiche_Model;
import com.github.L_Ender.cataclysm.client.model.entity.Coral_Spear_Model;
import com.github.L_Ender.cataclysm.client.model.item.Ancient_Spear_Model;
import com.github.L_Ender.cataclysm.client.model.item.Black_Steel_Targe_Model;
import com.github.L_Ender.cataclysm.client.model.item.Bulwark_of_the_flame_Model;
import com.github.L_Ender.cataclysm.client.model.item.Cursed_Bow_Model;
import com.github.L_Ender.cataclysm.client.model.item.Gauntlet_of_Bulwark_Model;
import com.github.L_Ender.cataclysm.client.model.item.Gauntlet_of_Guard_Model;
import com.github.L_Ender.cataclysm.client.model.item.Gauntlet_of_Maelstrom_Model;
import com.github.L_Ender.cataclysm.client.model.item.Incinerator_Model;
import com.github.L_Ender.cataclysm.client.model.item.Laser_Gatling_Model;
import com.github.L_Ender.cataclysm.client.model.item.Meat_Shredder_Model;
import com.github.L_Ender.cataclysm.client.model.item.Soul_render_Model;
import com.github.L_Ender.cataclysm.client.model.item.The_Annihilator_Model;
import com.github.L_Ender.cataclysm.client.model.item.The_Immolator_Model;
import com.github.L_Ender.cataclysm.client.model.item.Tidal_Claws_Model;
import com.github.L_Ender.cataclysm.client.model.item.Void_Forge_Model;
import com.github.L_Ender.cataclysm.client.model.item.Wither_Assault_SHoulder_Weapon_Model;
import com.github.L_Ender.cataclysm.client.model.item.Wrath_of_Desert_Model;
import com.github.L_Ender.cataclysm.client.render.blockentity.Cataclysm_Skull_Block_Renderer;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.items.Cursed_bow;
import com.github.L_Ender.cataclysm.items.Laser_Gatling;
import com.github.L_Ender.cataclysm.items.Wrath_of_the_desert;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CMItemstackRenderer extends BlockEntityWithoutLevelRenderer {
   public static int ticksExisted = 0;
   private static final Bulwark_of_the_flame_Model BULWARK_OF_THE_FLAME_MODEL = new Bulwark_of_the_flame_Model();
   private static final Black_Steel_Targe_Model BLACK_STEEL_TARGE_MODEL = new Black_Steel_Targe_Model();
   private static final EMP_Model EMP_MODEL = new EMP_Model();
   private static final Mechanical_Anvil_Model MF_MODEL = new Mechanical_Anvil_Model();
   private static final Gauntlet_of_Guard_Model GAUNTLET_OF_GUARD_MODEL = new Gauntlet_of_Guard_Model();
   private static final Gauntlet_of_Bulwark_Model GAUNTLET_OF_BULWARK_MODEL = new Gauntlet_of_Bulwark_Model();
   private static final Gauntlet_of_Maelstrom_Model GAUNTLET_OF_MAELSTROM_MODEL = new Gauntlet_of_Maelstrom_Model();
   private static final Incinerator_Model THE_INCINERATOR_MODEL = new Incinerator_Model();
   private static final Coral_Spear_Model CORAL_SPEAR_MODEL = new Coral_Spear_Model();
   private static final Coral_Bardiche_Model CORAL_BARDICHE_MODEL = new Coral_Bardiche_Model();
   private static final Altar_of_Fire_Model ALTAR_OF_FIRE_MODEL = new Altar_of_Fire_Model();
   private static final Altar_of_Void_Model ALTAR_OF_VOID_MODEL = new Altar_of_Void_Model();
   private static final Altar_of_Amethyst_Model ALTAR_OF_AMETHYST_MODEL = new Altar_of_Amethyst_Model();
   private static final Altar_of_Abyss_Model ALTAR_OF_ABYSS_MODEL = new Altar_of_Abyss_Model();
   private static final Abyssal_Egg_Model ABYSSAL_MODEL = new Abyssal_Egg_Model();
   private static final Wither_Assault_SHoulder_Weapon_Model WASW_MODEL = new Wither_Assault_SHoulder_Weapon_Model();
   private static final Void_Forge_Model VOID_FORGE_MODEL = new Void_Forge_Model();
   private static final Tidal_Claws_Model TIDAL_CLAWS_MODEL = new Tidal_Claws_Model();
   private static final Meat_Shredder_Model MEAT_SHREDDER_MODEL = new Meat_Shredder_Model();
   private static final Laser_Gatling_Model LASER_GATLING_MODEL = new Laser_Gatling_Model();
   private static final Ancient_Spear_Model ANCIENT_SPEAR_MODEL = new Ancient_Spear_Model();
   private static final Cursed_Bow_Model CURSED_BOW_MODEL = new Cursed_Bow_Model();
   private static final Wrath_of_Desert_Model WRATH_OF_DESERT_MODEL = new Wrath_of_Desert_Model();
   private static final The_Annihilator_Model THE_ANNIHILATOR = new The_Annihilator_Model();
   private static final The_Immolator_Model THE_IMMOLATOR_MODEL = new The_Immolator_Model();
   private static final Soul_render_Model SOUL_RENDER = new Soul_render_Model();
   private static final ResourceLocation CURSED_BOW_TEXTURE = new ResourceLocation("cataclysm", "textures/item/cursed_bow.png");
   private static final ResourceLocation CURSED_BOW_GHOST_TEXTURE = new ResourceLocation("cataclysm", "textures/item/cursed_bow_ghost.png");
   private static final ResourceLocation WRATH_OF_DESERT_TEXTURE = new ResourceLocation("cataclysm", "textures/item/wrath_of_desert.png");
   private static final ResourceLocation WRATH_OF_DESERT_GHOST_TEXTURE = new ResourceLocation("cataclysm", "textures/item/wrath_of_desert_ghost.png");
   private static final ResourceLocation SOUL_RENDER_TEXTURE = new ResourceLocation("cataclysm", "textures/item/soul_render.png");
   private static final ResourceLocation SOUL_RENDER_GHOST_TEXTURE = new ResourceLocation("cataclysm", "textures/item/soul_render_ghost.png");
   private static final ResourceLocation THE_ANNIHILATOR_TEXTURE = new ResourceLocation("cataclysm", "textures/item/the_annihilator.png");
   private static final ResourceLocation THE_ANNIHILATOR_GHOST_TEXTURE = new ResourceLocation("cataclysm", "textures/item/the_annihilator_ghost.png");
   private static final ResourceLocation THE_IMMOLATOR_TEXTURE = new ResourceLocation("cataclysm", "textures/item/the_immolator.png");
   private static final ResourceLocation THE_IMMOLATOR_GHOST_TEXTURE = new ResourceLocation("cataclysm", "textures/item/the_immolator_ghost.png");
   private static final ResourceLocation BULWARK_OF_THE_FLAME_TEXTURE = new ResourceLocation("cataclysm", "textures/item/bulwark_of_the_flame.png");
   private static final ResourceLocation BLACK_STEEL_TARGE_TEXTURE = new ResourceLocation("cataclysm", "textures/item/black_steel_targe.png");
   private static final ResourceLocation GAUNTLET_OF_GUARD_TEXTURE = new ResourceLocation("cataclysm", "textures/item/gauntlet_of_guard.png");
   private static final ResourceLocation GAUNTLET_OF_MAELSTROM_TEXTURE = new ResourceLocation("cataclysm", "textures/item/gauntlet_of_maelstrom.png");
   private static final ResourceLocation GAUNTLET_OF_BULWARK_TEXTURE = new ResourceLocation("cataclysm", "textures/item/gauntlet_of_bulwark.png");
   private static final ResourceLocation GAUNTLET_OF_GUARD_LAYER_TEXTURE = new ResourceLocation("cataclysm", "textures/item/gauntlet_of_guard_layer.png");
   private static final ResourceLocation GAUNTLET_OF_BULWARK_LAYER_TEXTURE = new ResourceLocation("cataclysm", "textures/item/gauntlet_of_bulwark_layer.png");
   private static final ResourceLocation GAUNTLET_OF_MAELSTROM_LAYER_TEXTURE = new ResourceLocation(
      "cataclysm", "textures/item/gauntlet_of_maelstrom_layer.png"
   );
   private static final ResourceLocation THE_INCINERATOR_TEXTURE = new ResourceLocation("cataclysm", "textures/item/the_incinerator.png");
   private static final ResourceLocation VOID_FORGE_TEXTURE = new ResourceLocation("cataclysm", "textures/item/void_forge.png");
   private static final ResourceLocation VOID_FORGE_LAYER_TEXTURE = new ResourceLocation("cataclysm", "textures/item/void_forge_layer.png");
   private static final ResourceLocation TIDAL_CLAWS_TEXTURE = new ResourceLocation("cataclysm", "textures/item/tidal_claws.png");
   private static final ResourceLocation MEAT_SHREDDER_TEXTURE = new ResourceLocation("cataclysm", "textures/item/meat_shredder.png");
   private static final ResourceLocation MEAT_SHREDDER_LAYER_TEXTURE = new ResourceLocation("cataclysm", "textures/item/meat_shredder_layer.png");
   private static final ResourceLocation LASER_GATLING_TEXTURE = new ResourceLocation("cataclysm", "textures/item/laser_gatling.png");
   private static final ResourceLocation LASER_GATLING_LAYER_TEXTURE = new ResourceLocation("cataclysm", "textures/item/laser_gatling_layer.png");
   private static final ResourceLocation ALTAR_OF_FIRE_TEXTURE = new ResourceLocation("cataclysm", "textures/block/altar_of_fire/altar_of_fire.png");
   private static final ResourceLocation ALTAR_OF_VOID_TEXTURE = new ResourceLocation("cataclysm", "textures/block/altar_of_void.png");
   private static final ResourceLocation ALTAR_OF_AMETHYST_TEXTURE = new ResourceLocation("cataclysm", "textures/block/altar_of_amethyst.png");
   private static final ResourceLocation ALTAR_OF_ABYSS_TEXTURE = new ResourceLocation("cataclysm", "textures/block/altar_of_abyss.png");
   private static final ResourceLocation ABYSSAL_EGG_TEXTURE = new ResourceLocation("cataclysm", "textures/block/abyssal_egg.png");
   private static final ResourceLocation ABYSSAL_EGG_LAYER_TEXTURE = new ResourceLocation("cataclysm", "textures/block/abyssal_egg_layer.png");
   private static final ResourceLocation MIF_TEXTURE = new ResourceLocation("cataclysm", "textures/block/mechanical_fusion_anvil.png");
   private static final ResourceLocation WASW_TEXTURE = new ResourceLocation("cataclysm", "textures/item/wither_assualt_shoulder_weapon.png");
   private static final ResourceLocation WASW_LAYER_TEXTURE = new ResourceLocation("cataclysm", "textures/item/wither_assualt_shoulder_weapon_layer.png");
   private static final ResourceLocation VASW_TEXTURE = new ResourceLocation("cataclysm", "textures/item/void_assualt_shoulder_weapon.png");
   private static final ResourceLocation VASW_LAYER_TEXTURE = new ResourceLocation("cataclysm", "textures/item/void_assualt_shoulder_weapon_layer.png");
   private static final ResourceLocation EMP_TEXTURE = new ResourceLocation("cataclysm", "textures/block/emp.png");
   private static final ResourceLocation[] TEXTURE_FIRE_PROGRESS = new ResourceLocation[8];
   private static final ResourceLocation CORAL_SPEAR_TEXTURE = new ResourceLocation("cataclysm", "textures/entity/coral_spear.png");
   private static final ResourceLocation CORAL_BARDICHE_TEXTURE = new ResourceLocation("cataclysm", "textures/entity/coral_bardiche.png");
   private static final ResourceLocation ANCIENT_SPEAR_TEXTURE = new ResourceLocation("cataclysm", "textures/item/ancient_spear.png");
   private Map<Cataclysm_Skull_Block.Type, Cataclysm_Skull_Model_Base> skullModels = Cataclysm_Skull_Block_Renderer.createSkullRenderers(
      Minecraft.m_91087_().m_167973_()
   );
   public static final Map<Cataclysm_Skull_Block.Type, ResourceLocation> SKIN_BY_TYPE = (Map<Cataclysm_Skull_Block.Type, ResourceLocation>)Util.m_137469_(
      Maps.newHashMap(), p_261388_ -> {
         p_261388_.put(Cataclysm_Skull_Block.Types.KOBOLEDIATOR, new ResourceLocation("cataclysm", "textures/entity/koboleton/kobolediator.png"));
         p_261388_.put(Cataclysm_Skull_Block.Types.APTRGANGR, new ResourceLocation("cataclysm", "textures/entity/draugar/aptrgangr.png"));
         p_261388_.put(Cataclysm_Skull_Block.Types.DRAUGR, new ResourceLocation("cataclysm", "textures/entity/draugar/draugr.png"));
      }
   );

   public CMItemstackRenderer() {
      super(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_());

      for (int i = 0; i < 8; i++) {
         TEXTURE_FIRE_PROGRESS[i] = new ResourceLocation("cataclysm", "textures/block/altar_of_fire/altarfire_" + i + ".png");
      }
   }

   public void m_6213_(ResourceManager manager) {
      this.skullModels = Cataclysm_Skull_Block_Renderer.createSkullRenderers(Minecraft.m_91087_().m_167973_());
      Cataclysm.LOGGER.debug("Reloaded ItemStackRenderer!");
   }

   public static void incrementTick() {
      ticksExisted++;
   }

   public void m_108829_(
      ItemStack itemStackIn, TransformType transformType, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      float partialTick = Minecraft.m_91087_().getPartialTick();
      if (transformType != TransformType.THIRD_PERSON_LEFT_HAND && transformType != TransformType.FIRST_PERSON_LEFT_HAND) {
         boolean var56 = false;
      } else {
         boolean var10000 = true;
      }

      int tick;
      if (Minecraft.m_91087_().f_91074_ != null && !Minecraft.m_91087_().m_91104_()) {
         tick = Minecraft.m_91087_().f_91074_.f_19797_;
      } else {
         tick = ticksExisted;
      }

      if (itemStackIn.m_41720_() instanceof BlockItem blockItem) {
         Block block = blockItem.m_40614_();
         if (block instanceof Abstract_Cataclysm_Skull_Block) {
            Cataclysm_Skull_Block.Type skullblock$type = ((Abstract_Cataclysm_Skull_Block)block).getType();
            Cataclysm_Skull_Model_Base skullmodelbase = this.skullModels.get(skullblock$type);
            ResourceLocation resourcelocation = SKIN_BY_TYPE.get(skullblock$type);
            RenderType rendertype = RenderType.m_110464_(resourcelocation);
            Cataclysm_Skull_Block_Renderer.renderSkull((Direction)null, 180.0F, 0.0F, matrixStackIn, bufferIn, combinedLightIn, skullmodelbase, rendertype);
         }
      }

      if (itemStackIn.m_41720_() == ModItems.BULWARK_OF_THE_FLAME.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(BULWARK_OF_THE_FLAME_TEXTURE), false, itemStackIn.m_41790_());
         BULWARK_OF_THE_FLAME_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.BLACK_STEEL_TARGE.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(BLACK_STEEL_TARGE_TEXTURE), false, itemStackIn.m_41790_());
         BLACK_STEEL_TARGE_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.GAUNTLET_OF_GUARD.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110458_(GAUNTLET_OF_GUARD_TEXTURE), false, itemStackIn.m_41790_());
         GAUNTLET_OF_GUARD_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(
            bufferIn, CMRenderTypes.m_110488_(GAUNTLET_OF_GUARD_LAYER_TEXTURE), false, itemStackIn.m_41790_()
         );
         GAUNTLET_OF_GUARD_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.GAUNTLET_OF_BULWARK.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110458_(GAUNTLET_OF_BULWARK_TEXTURE), false, itemStackIn.m_41790_());
         GAUNTLET_OF_BULWARK_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(
            bufferIn, CMRenderTypes.m_110488_(GAUNTLET_OF_BULWARK_LAYER_TEXTURE), false, itemStackIn.m_41790_()
         );
         GAUNTLET_OF_BULWARK_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.GAUNTLET_OF_MAELSTROM.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110458_(GAUNTLET_OF_MAELSTROM_TEXTURE), false, itemStackIn.m_41790_());
         GAUNTLET_OF_MAELSTROM_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(
            bufferIn, CMRenderTypes.m_110488_(GAUNTLET_OF_MAELSTROM_LAYER_TEXTURE), false, itemStackIn.m_41790_()
         );
         GAUNTLET_OF_MAELSTROM_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.THE_INCINERATOR.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(THE_INCINERATOR_TEXTURE), false, itemStackIn.m_41790_());
         THE_INCINERATOR_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.WITHER_ASSULT_SHOULDER_WEAPON.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110458_(WASW_TEXTURE), false, itemStackIn.m_41790_());
         WASW_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(bufferIn, CMRenderTypes.m_110488_(WASW_LAYER_TEXTURE), false, itemStackIn.m_41790_());
         WASW_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.VOID_ASSULT_SHOULDER_WEAPON.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110458_(VASW_TEXTURE), false, itemStackIn.m_41790_());
         WASW_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(bufferIn, CMRenderTypes.m_110488_(VASW_LAYER_TEXTURE), false, itemStackIn.m_41790_());
         WASW_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.CORAL_SPEAR.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(CORAL_SPEAR_TEXTURE), false, itemStackIn.m_41790_());
         CORAL_SPEAR_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.CORAL_BARDICHE.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(CORAL_BARDICHE_TEXTURE), false, itemStackIn.m_41790_());
         CORAL_BARDICHE_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.VOID_FORGE.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110458_(VOID_FORGE_TEXTURE), false, itemStackIn.m_41790_());
         VOID_FORGE_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(bufferIn, CMRenderTypes.m_110488_(VOID_FORGE_LAYER_TEXTURE), false, itemStackIn.m_41790_());
         VOID_FORGE_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.TIDAL_CLAWS.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(TIDAL_CLAWS_TEXTURE), false, itemStackIn.m_41790_());
         TIDAL_CLAWS_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.MEAT_SHREDDER.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110458_(MEAT_SHREDDER_TEXTURE), false, itemStackIn.m_41790_());
         MEAT_SHREDDER_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(bufferIn, CMRenderTypes.CMEyes(MEAT_SHREDDER_LAYER_TEXTURE), false, itemStackIn.m_41790_());
         MEAT_SHREDDER_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         MEAT_SHREDDER_MODEL.animateStack(itemStackIn);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.LASER_GATLING.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110458_(LASER_GATLING_TEXTURE), false, itemStackIn.m_41790_());
         float ageInTicks = Minecraft.m_91087_().f_91074_ == null ? 0.0F : (float)Minecraft.m_91087_().f_91074_.f_19797_ + partialTick;
         float openAmount = Minecraft.m_91087_().f_91074_ != null && Laser_Gatling.isCharged(itemStackIn)
            ? (float)Minecraft.m_91087_().f_91074_.f_19797_ + partialTick
            : 0.0F;
         LASER_GATLING_MODEL.m_6973_(null, openAmount, 0.0F, ageInTicks, 0.0F, 0.0F);
         LASER_GATLING_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(bufferIn, CMRenderTypes.m_110488_(LASER_GATLING_LAYER_TEXTURE), false, itemStackIn.m_41790_());
         LASER_GATLING_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ModItems.ANCIENT_SPEAR.get()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(ANCIENT_SPEAR_TEXTURE), false, itemStackIn.m_41790_());
         ANCIENT_SPEAR_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_150930_((Item)ModItems.CURSED_BOW.get())) {
         float ageInTicks = Minecraft.m_91087_().f_91074_ == null ? 0.0F : (float)Minecraft.m_91087_().f_91074_.f_19797_ + partialTick;
         float pullAmount = Cursed_bow.getPullingAmount(itemStackIn, partialTick);
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         CURSED_BOW_MODEL.m_6973_(null, pullAmount, ageInTicks, 0.0F, 0.0F, 0.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(CURSED_BOW_TEXTURE), false, itemStackIn.m_41790_());
         CURSED_BOW_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(bufferIn, CMRenderTypes.getGhost(CURSED_BOW_GHOST_TEXTURE), false, itemStackIn.m_41790_());
         CURSED_BOW_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_150930_((Item)ModItems.WRATH_OF_THE_DESERT.get())) {
         float ageInTicks = Minecraft.m_91087_().f_91074_ == null ? 0.0F : (float)Minecraft.m_91087_().f_91074_.f_19797_ + partialTick;
         float pullAmount = Wrath_of_the_desert.getPullingAmount(itemStackIn, partialTick);
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         WRATH_OF_DESERT_MODEL.m_6973_(null, pullAmount, ageInTicks, ageInTicks, 0.0F, 0.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(WRATH_OF_DESERT_TEXTURE), false, itemStackIn.m_41790_());
         WRATH_OF_DESERT_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(bufferIn, CMRenderTypes.getGhost(WRATH_OF_DESERT_GHOST_TEXTURE), false, itemStackIn.m_41790_());
         WRATH_OF_DESERT_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_150930_((Item)ModItems.SOUL_RENDER.get())) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(SOUL_RENDER_TEXTURE), false, itemStackIn.m_41790_());
         SOUL_RENDER.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(bufferIn, CMRenderTypes.getGhost(SOUL_RENDER_GHOST_TEXTURE), false, itemStackIn.m_41790_());
         SOUL_RENDER.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_150930_((Item)ModItems.THE_ANNIHILATOR.get())) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(THE_ANNIHILATOR_TEXTURE), false, itemStackIn.m_41790_());
         THE_ANNIHILATOR.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(bufferIn, CMRenderTypes.getGhost(THE_ANNIHILATOR_GHOST_TEXTURE), false, itemStackIn.m_41790_());
         THE_ANNIHILATOR.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_150930_((Item)ModItems.THE_IMMOLATOR.get())) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 0.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(bufferIn, RenderType.m_110431_(THE_IMMOLATOR_TEXTURE), false, itemStackIn.m_41790_());
         THE_IMMOLATOR_MODEL.m_7695_(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         VertexConsumer vertexconsumer2 = ItemRenderer.m_115184_(bufferIn, CMRenderTypes.getGhost(THE_IMMOLATOR_GHOST_TEXTURE), false, itemStackIn.m_41790_());
         THE_IMMOLATOR_MODEL.m_7695_(matrixStackIn, vertexconsumer2, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ((Block)ModBlocks.ALTAR_OF_FIRE.get()).m_5456_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         ALTAR_OF_FIRE_MODEL.resetToDefaultPose();
         ALTAR_OF_FIRE_MODEL.m_7695_(
            matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(ALTAR_OF_FIRE_TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F
         );
         ALTAR_OF_FIRE_MODEL.m_7695_(
            matrixStackIn,
            bufferIn.m_6299_(CMRenderTypes.getGlowingEffect(this.getIdleTexture((int)((float)tick * 0.5F % 7.0F)))),
            210,
            OverlayTexture.f_118083_,
            1.0F,
            1.0F,
            1.0F,
            1.0F
         );
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ((Block)ModBlocks.ALTAR_OF_VOID.get()).m_5456_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         ALTAR_OF_VOID_MODEL.resetToDefaultPose();
         ALTAR_OF_VOID_MODEL.m_7695_(
            matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(ALTAR_OF_VOID_TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F
         );
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ((Block)ModBlocks.ALTAR_OF_AMETHYST.get()).m_5456_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         ALTAR_OF_AMETHYST_MODEL.resetToDefaultPose();
         ALTAR_OF_AMETHYST_MODEL.m_7695_(
            matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(ALTAR_OF_AMETHYST_TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F
         );
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ((Block)ModBlocks.ALTAR_OF_ABYSS.get()).m_5456_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         ALTAR_OF_ABYSS_MODEL.resetToDefaultPose();
         ALTAR_OF_ABYSS_MODEL.m_7695_(
            matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(ALTAR_OF_ABYSS_TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F
         );
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ((Block)ModBlocks.EMP.get()).m_5456_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         EMP_MODEL.resetToDefaultPose();
         EMP_MODEL.m_7695_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(EMP_TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ((Block)ModBlocks.MECHANICAL_FUSION_ANVIL.get()).m_5456_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         MF_MODEL.resetToDefaultPose();
         MF_MODEL.m_7695_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(MIF_TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      if (itemStackIn.m_41720_() == ((Block)ModBlocks.ABYSSAL_EGG.get()).m_5456_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
         matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
         ABYSSAL_MODEL.resetToDefaultPose();
         ABYSSAL_MODEL.m_7695_(
            matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(ABYSSAL_EGG_TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F
         );
         ABYSSAL_MODEL.m_7695_(
            matrixStackIn, bufferIn.m_6299_(CMRenderTypes.getGhost(ABYSSAL_EGG_LAYER_TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F
         );
         matrixStackIn.m_85849_();
      }
   }

   private void renderMapHand(PoseStack poseStack, MultiBufferSource bufferSource, int i, HumanoidArm humanoidArm) {
      RenderSystem.m_157456_(0, Minecraft.m_91087_().f_91074_.m_108560_());
      PlayerRenderer playerrenderer = (PlayerRenderer)Minecraft.m_91087_().m_91290_().m_114382_(Minecraft.m_91087_().f_91074_);
      poseStack.m_85836_();
      float f = humanoidArm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(92.0F));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(45.0F));
      poseStack.m_85845_(Vector3f.f_122227_.m_122240_(f * -41.0F));
      poseStack.m_85837_((double)(f * 0.3F), -1.1F, 0.45F);
      if (humanoidArm == HumanoidArm.RIGHT) {
         playerrenderer.m_117770_(poseStack, bufferSource, i, Minecraft.m_91087_().f_91074_);
      } else {
         playerrenderer.m_117813_(poseStack, bufferSource, i, Minecraft.m_91087_().f_91074_);
      }

      poseStack.m_85849_();
   }

   private ResourceLocation getIdleTexture(int age) {
      return TEXTURE_FIRE_PROGRESS[Mth.m_14045_(age, 0, 7)];
   }
}
