package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.google.common.collect.Maps;
import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.common.block.AlterationTable;
import com.hollingsworth.arsnouveau.common.block.ThreePartBlock;
import com.hollingsworth.arsnouveau.common.block.tile.AlterationTile;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraftforge.client.ForgeHooksClient;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public class AlterationTableRenderer extends ArsGeoBlockRenderer<AlterationTile> {
   private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
   public final ArmorStandArmorModel innerModel;
   public final ArmorStandArmorModel outerModel;
   public static final PerlinSimplexNoise noise = new PerlinSimplexNoise(new LegacyRandomSource(2906L), IntList.of(new int[]{1, 2, 3, 4, 5}));

   public AlterationTableRenderer(Context p_i226006_1_) {
      super(p_i226006_1_, new GenericModel("alteration_table").withEmptyAnim());
      this.innerModel = new ArmorStandArmorModel(p_i226006_1_.m_173582_(ModelLayers.f_171208_));
      this.outerModel = new ArmorStandArmorModel(p_i226006_1_.m_173582_(ModelLayers.f_171261_));
   }

   public void renderEarly(
      AlterationTile tile,
      PoseStack matrixStack,
      float ticks,
      MultiBufferSource iRenderTypeBuffer,
      VertexConsumer bufferIn,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float partialTicks
   ) {
      try {
         if (tile.m_58904_().m_8055_(tile.m_58899_()).m_60734_() != BlockRegistry.ALTERATION_TABLE) {
            return;
         }

         if (tile.m_58904_().m_8055_(tile.m_58899_()).m_61143_(AlterationTable.PART) != ThreePartBlock.HEAD) {
            return;
         }

         this.renderArmorStack(tile, matrixStack, ticks, iRenderTypeBuffer, bufferIn, packedLightIn, packedOverlayIn, partialTicks);
         this.renderPerks(tile, matrixStack, ticks, iRenderTypeBuffer, bufferIn, packedLightIn, packedOverlayIn, partialTicks);
      } catch (Throwable var13) {
         var13.printStackTrace();
      }
   }

   public void renderArmorStack(
      AlterationTile tile,
      PoseStack matrixStack,
      float ticks,
      MultiBufferSource iRenderTypeBuffer,
      VertexConsumer bufferIn,
      int packedLightIn,
      int packedOverlayIn,
      float partialTicks
   ) {
      matrixStack.m_85836_();
      BlockState state = tile.m_58904_().m_8055_(tile.m_58899_());
      if (state.m_60734_() instanceof AlterationTable) {
         if (tile.armorStack.m_41720_() instanceof ArmorItem armorItem) {
            matrixStack.m_85841_(0.5F, 0.5F, 0.5F);
            matrixStack.m_85837_(-2.1, 3.3, 0.0);
            double yOffset = Mth.m_144946_((Math.sin((double)(((float)ClientInfo.ticksInGame + ticks) / 20.0F)) + 1.0) / 2.0) * 0.0625;
            if (tile.newPerkTimer >= 0) {
               yOffset = 0.0;
               float percentage = (float)Mth.m_14040_(tile.newPerkTimer - 20) / 20.0F;
               double smooooooooth = Mth.m_14197_((double)percentage);
               double perkYOffset = 0.625 - smooooooooth * 0.625;
               matrixStack.m_85845_(Vector3f.f_122225_.m_122240_((float)(Mth.m_14197_((double)((float)tile.newPerkTimer / 40.0F)) * 360.0)));
               matrixStack.m_85837_(0.0, perkYOffset, 0.0);
            }

            matrixStack.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
            matrixStack.m_85837_(0.0, yOffset + (double)this.rotForSlot(armorItem.m_40402_()), 0.0);
            this.renderArmorPiece(tile, tile.armorStack, matrixStack, iRenderTypeBuffer, packedLightIn, this.getArmorModel(armorItem.m_40402_()));
         } else {
            Minecraft.m_91087_()
               .m_91291_()
               .m_174269_(
                  tile.armorStack, TransformType.FIXED, packedLightIn, packedOverlayIn, matrixStack, iRenderTypeBuffer, (int)tile.m_58899_().m_121878_()
               );
         }

         matrixStack.m_85849_();
      }
   }

   public void renderPerks(
      AlterationTile tile,
      PoseStack matrixStack,
      float ticks,
      MultiBufferSource iRenderTypeBuffer,
      VertexConsumer bufferIn,
      int packedLightIn,
      int packedOverlayIn,
      float partialTicks
   ) {
      if (!tile.perkList.isEmpty()) {
         for (int i = 0; i < Math.min(3, tile.perkList.size()); i++) {
            ItemStack perkStack = tile.perkList.get(i);
            if (!perkStack.m_41619_()) {
               matrixStack.m_85836_();
               matrixStack.m_85837_(-0.25, 0.74 - 0.175 * (double)i, -0.3 - 0.175 * (double)i);
               GeoBone bone = (GeoBone)this.getGeoModelProvider().getBone("display");
               if (bone.getRotationZ() != 0.0F) {
                  matrixStack.m_85845_(Vector3f.f_122227_.m_122270_(-bone.getRotationZ()));
               }

               if (bone.getRotationY() != 0.0F) {
                  matrixStack.m_85845_(Vector3f.f_122225_.m_122270_(-bone.getRotationY()));
               }

               if (bone.getRotationX() != 0.0F) {
                  matrixStack.m_85845_(Vector3f.f_122223_.m_122270_(-bone.getRotationX()));
               }

               GeoBone locBone = (GeoBone)this.getGeoModelProvider().getBone("top_" + (i + 1));
               RenderUtils.translateToPivotPoint(matrixStack, locBone);
               matrixStack.m_85841_(0.18F, 0.18F, 0.18F);
               Minecraft.m_91087_()
                  .m_91291_()
                  .m_174269_(perkStack, TransformType.FIXED, packedLightIn, packedOverlayIn, matrixStack, iRenderTypeBuffer, (int)tile.m_58899_().m_121878_());
               matrixStack.m_85849_();
            }
         }
      }
   }

   public float rotForSlot(EquipmentSlot slot) {
      return switch (slot) {
         case HEAD -> 0.3F;
         case CHEST -> 0.0F;
         case LEGS -> -0.2F;
         case FEET -> -0.6F;
         default -> 0.0F;
      };
   }

   private ArmorStandArmorModel getArmorModel(EquipmentSlot pSlot) {
      return this.usesInnerModel(pSlot) ? this.innerModel : this.outerModel;
   }

   private void renderArmorPiece(
      AlterationTile tile, ItemStack itemstack, PoseStack pPoseStack, MultiBufferSource pBuffer, int packedLightIn, ArmorStandArmorModel armorModel
   ) {
      if (itemstack.m_41720_() instanceof ArmorItem armoritem) {
         EquipmentSlot var16 = armoritem.m_40402_();
         Model model = this.getArmorModelHook(itemstack, var16, armorModel);
         boolean flag1 = itemstack.m_41790_();
         if (armoritem instanceof DyeableLeatherItem dyeableLeatherItem) {
            int i = dyeableLeatherItem.m_41121_(itemstack);
            float f = (float)(i >> 16 & 0xFF) / 255.0F;
            float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
            float f2 = (float)(i & 0xFF) / 255.0F;
            this.renderModel(pPoseStack, pBuffer, packedLightIn, flag1, model, f, f1, f2, this.getArmorResource(itemstack, var16, null));
            this.renderModel(pPoseStack, pBuffer, packedLightIn, flag1, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(itemstack, var16, "overlay"));
         } else {
            this.renderModel(pPoseStack, pBuffer, packedLightIn, flag1, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(itemstack, var16, null));
         }
      }
   }

   protected Model getArmorModelHook(ItemStack itemStack, EquipmentSlot slot, HumanoidModel model) {
      return ForgeHooksClient.getArmorModel(Minecraft.m_91087_().f_91074_, itemStack, slot, model);
   }

   private void renderModel(
      PoseStack pPoseStack,
      MultiBufferSource pBuffer,
      int packedLight,
      boolean p_117111_,
      Model pModel,
      float p_117114_,
      float p_117115_,
      float p_117116_,
      ResourceLocation armorResource
   ) {
      VertexConsumer vertexconsumer = ItemRenderer.m_115184_(pBuffer, RenderType.m_110431_(armorResource), false, p_117111_);
      pModel.m_7695_(pPoseStack, vertexconsumer, packedLight, OverlayTexture.f_118083_, p_117114_, p_117115_, p_117116_, 1.0F);
   }

   public ResourceLocation getArmorResource(ItemStack stack, EquipmentSlot slot, @Nullable String type) {
      ArmorItem item = (ArmorItem)stack.m_41720_();
      String texture = item.m_40401_().m_6082_();
      String domain = "minecraft";
      int idx = texture.indexOf(58);
      if (idx != -1) {
         domain = texture.substring(0, idx);
         texture = texture.substring(idx + 1);
      }

      String s1 = String.format(
         Locale.ROOT,
         "%s:textures/models/armor/%s_layer_%d%s.png",
         domain,
         texture,
         this.usesInnerModel(slot) ? 2 : 1,
         type == null ? "" : String.format(Locale.ROOT, "_%s", type)
      );
      s1 = ForgeHooksClient.getArmorTexture(Minecraft.m_91087_().f_91074_, stack, s1, slot, type);
      ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);
      if (resourcelocation == null) {
         resourcelocation = new ResourceLocation(s1);
         ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
      }

      return resourcelocation;
   }

   private boolean usesInnerModel(EquipmentSlot pSlot) {
      return pSlot == EquipmentSlot.LEGS;
   }

   public void render(AlterationTile tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
      try {
         if (tile.m_58904_().m_8055_(tile.m_58899_()).m_60734_() != BlockRegistry.ALTERATION_TABLE) {
            return;
         }

         if (tile.m_58904_().m_8055_(tile.m_58899_()).m_61143_(AlterationTable.PART) != ThreePartBlock.HEAD) {
            return;
         }

         Direction direction = (Direction)tile.m_58904_().m_8055_(tile.m_58899_()).m_61143_(AlterationTable.FACING);
         stack.m_85836_();
         if (direction == Direction.NORTH) {
            stack.m_85845_(Vector3f.f_122225_.m_122240_(-90.0F));
            stack.m_85837_(1.0, 0.0, -1.0);
         }

         if (direction == Direction.SOUTH) {
            stack.m_85845_(Vector3f.f_122225_.m_122240_(270.0F));
            stack.m_85837_(-1.0, 0.0, -1.0);
         }

         if (direction == Direction.WEST) {
            stack.m_85845_(Vector3f.f_122225_.m_122240_(270.0F));
            stack.m_85837_(0.0, 0.0, -2.0);
         }

         if (direction == Direction.EAST) {
            stack.m_85845_(Vector3f.f_122225_.m_122240_(-90.0F));
            stack.m_85837_(0.0, 0.0, 0.0);
         }

         super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
         stack.m_85849_();
      } catch (Throwable var7) {
         var7.printStackTrace();
      }
   }

   public void render(
      GeoModel model,
      AlterationTile animatable,
      float partialTicks,
      RenderType type,
      PoseStack matrixStackIn,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      this.renderSlate(model, animatable, partialTicks, matrixStackIn, renderTypeBuffer, packedLightIn);
      super.render(
         model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha
      );
   }

   public void renderSlate(GeoModel model, AlterationTile tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
      String[] rowNames = new String[]{"top", "mid", "bot"};
      if (tile.armorStack.m_41619_()) {
         for (String s : rowNames) {
            this.setSlateRow(model, s, 0);
         }
      } else if (PerkUtil.getPerkHolder(tile.armorStack) instanceof ArmorPerkHolder armorPerkHolder) {
         List var13 = armorPerkHolder.getSlotsForTier();

         for (int remainingRows = 0; remainingRows < Math.min(var13.size(), rowNames.length); remainingRows++) {
            PerkSlot perkSlot = (PerkSlot)var13.get(remainingRows);
            this.setSlateRow(model, rowNames[remainingRows], perkSlot.value);
         }

         List<String> remainingRows = List.of(rowNames);
         remainingRows.subList(var13.size(), remainingRows.size()).forEach(s -> this.setSlateRow(model, s, 0));
      }
   }

   public void setSlateRow(GeoModel model, String loc, int tier) {
      for (int i = 0; i < 4; i++) {
         if (tier != i) {
            model.getBone(loc + "_" + i).ifPresent(bone -> bone.setHidden(true));
         } else {
            model.getBone(loc + "_" + i).ifPresent(bone -> bone.setHidden(false));
         }
      }
   }

   public static GenericItemBlockRenderer getISTER() {
      return new GenericItemBlockRenderer(new GenericModel("alteration_table").withEmptyAnim());
   }

   public RenderType getRenderType(
      AlterationTile animatable,
      float partialTicks,
      PoseStack stack,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      return RenderType.m_110473_(textureLocation);
   }

   public boolean m_5932_(BlockEntity p_188185_1_) {
      return false;
   }
}
