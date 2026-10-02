package com.min01.archaeology.blockentity;

import com.min01.archaeology.misc.DecoratedPotPatterns;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class DecoratedPotRenderer implements BlockEntityRenderer<DecoratedPotBlockEntity> {
   private static final String NECK = "neck";
   private static final String FRONT = "front";
   private static final String BACK = "back";
   private static final String LEFT = "left";
   private static final String RIGHT = "right";
   private static final String TOP = "top";
   private static final String BOTTOM = "bottom";
   private final ModelPart neck;
   private final ModelPart frontSide;
   private final ModelPart backSide;
   private final ModelPart leftSide;
   private final ModelPart rightSide;
   private final ModelPart top;
   private final ModelPart bottom;
   private final Material baseMaterial = Objects.requireNonNull(getDecoratedPotMaterial(DecoratedPotPatterns.BASE));
   public static final ModelLayerLocation DECORATED_POT_BASE = new ModelLayerLocation(new ResourceLocation("decorated_pot_base"), "main");
   public static final ModelLayerLocation DECORATED_POT_SIDES = new ModelLayerLocation(new ResourceLocation("decorated_pot_sides"), "main");
   public static final ResourceLocation DECORATED_POT_SHEET = InventoryMenu.f_39692_;
   public static final Map<ResourceKey<String>, Material> DECORATED_POT_MATERIALS = DecoratedPotPatterns.bootstrap()
      .stream()
      .collect(Collectors.toMap(Function.identity(), DecoratedPotRenderer::createDecoratedPotMaterial));
   private static final float WOBBLE_AMPLITUDE = 0.125F;

   public DecoratedPotRenderer(Context context) {
      ModelPart base = context.m_173582_(DECORATED_POT_BASE);
      this.neck = base.m_171324_("neck");
      this.top = base.m_171324_("top");
      this.bottom = base.m_171324_("bottom");
      ModelPart sides = context.m_173582_(DECORATED_POT_SIDES);
      this.frontSide = sides.m_171324_("front");
      this.backSide = sides.m_171324_("back");
      this.leftSide = sides.m_171324_("left");
      this.rightSide = sides.m_171324_("right");
   }

   public static LayerDefinition createBaseLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      CubeDeformation cubedeformation = new CubeDeformation(0.2F);
      CubeDeformation cubedeformation1 = new CubeDeformation(-0.1F);
      partdefinition.m_171599_(
         "neck",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(4.0F, 17.0F, 4.0F, 8.0F, 3.0F, 8.0F, cubedeformation1)
            .m_171514_(0, 5)
            .m_171488_(5.0F, 20.0F, 5.0F, 6.0F, 1.0F, 6.0F, cubedeformation),
         PartPose.m_171423_(0.0F, 37.0F, 16.0F, (float) Math.PI, 0.0F, 0.0F)
      );
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(-14, 13).m_171481_(0.0F, 0.0F, 0.0F, 14.0F, 0.0F, 14.0F);
      partdefinition.m_171599_("top", cubelistbuilder, PartPose.m_171423_(1.0F, 16.0F, 1.0F, 0.0F, 0.0F, 0.0F));
      partdefinition.m_171599_("bottom", cubelistbuilder, PartPose.m_171423_(1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public static LayerDefinition createSidesLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(1, 0).m_171481_(0.0F, 0.0F, 0.0F, 14.0F, 16.0F, 0.0F);
      partdefinition.m_171599_("back", cubelistbuilder, PartPose.m_171423_(15.0F, 16.0F, 1.0F, 0.0F, 0.0F, (float) Math.PI));
      partdefinition.m_171599_("left", cubelistbuilder, PartPose.m_171423_(1.0F, 16.0F, 1.0F, 0.0F, (float) (-Math.PI / 2), (float) Math.PI));
      partdefinition.m_171599_("right", cubelistbuilder, PartPose.m_171423_(15.0F, 16.0F, 15.0F, 0.0F, (float) (Math.PI / 2), (float) Math.PI));
      partdefinition.m_171599_("front", cubelistbuilder, PartPose.m_171423_(1.0F, 16.0F, 15.0F, (float) Math.PI, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 16, 16);
   }

   @Nullable
   private static Material getMaterial(Item item) {
      Material material = getDecoratedPotMaterial(DecoratedPotPatterns.getResourceKey(item));
      if (material == null) {
         material = getDecoratedPotMaterial(DecoratedPotPatterns.getResourceKey(Items.f_42460_));
      }

      return material;
   }

   private static Material createDecoratedPotMaterial(ResourceKey<String> materialKey) {
      return new Material(DECORATED_POT_SHEET, DecoratedPotPatterns.location(materialKey));
   }

   @Nullable
   public static Material getDecoratedPotMaterial(@Nullable ResourceKey<String> materialKey) {
      return materialKey == null ? null : DECORATED_POT_MATERIALS.get(materialKey);
   }

   public void render(
      DecoratedPotBlockEntity potEntity, float partialTick, PoseStack pose, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay
   ) {
      pose.m_85836_();
      Direction direction = potEntity.getDirection();
      this.rotateAround(pose, Vector3f.f_122225_.m_122240_(180.0F - direction.m_122435_()));
      DecoratedPotBlockEntity.WobbleStyle wobbleStyle = potEntity.lastWobbleStyle;
      if (wobbleStyle != null && potEntity.m_58904_() != null) {
         float g = ((float)(potEntity.m_58904_().m_46467_() - potEntity.wobbleStartedAtTick) + partialTick) / (float)wobbleStyle.duration;
         if (g >= 0.0F && g < 1.0F) {
            if (wobbleStyle == DecoratedPotBlockEntity.WobbleStyle.POSITIVE) {
               float h = 0.015625F;
               float k = g * (float) (Math.PI * 2);
               float l = -1.5F * (Mth.m_14089_(k) + 0.5F) * Mth.m_14031_(k / 2.0F);
               this.rotateAround(pose, Vector3f.f_122223_.m_122270_(l * h));
               float m = Mth.m_14031_(k);
               this.rotateAround(pose, Vector3f.f_122227_.m_122270_(m * h));
            } else {
               float h = (float)Mth.m_14205_((double)(-g * 3.0F) * Math.PI) * 0.125F;
               float k = 1.0F - g;
               this.rotateAround(pose, Vector3f.f_122225_.m_122270_(h * k));
            }
         }
      }

      VertexConsumer vertexconsumer = this.baseMaterial.m_119194_(buffer, RenderType::m_110446_);
      this.neck.m_104301_(pose, vertexconsumer, packedLight, packedOverlay);
      this.top.m_104301_(pose, vertexconsumer, packedLight, packedOverlay);
      this.bottom.m_104301_(pose, vertexconsumer, packedLight, packedOverlay);
      DecoratedPotBlockEntity.Decorations decoratedpotblockentity$decorations = potEntity.getDecorations();
      this.renderSide(this.frontSide, pose, buffer, packedLight, packedOverlay, getMaterial(decoratedpotblockentity$decorations.front()));
      this.renderSide(this.backSide, pose, buffer, packedLight, packedOverlay, getMaterial(decoratedpotblockentity$decorations.back()));
      this.renderSide(this.leftSide, pose, buffer, packedLight, packedOverlay, getMaterial(decoratedpotblockentity$decorations.left()));
      this.renderSide(this.rightSide, pose, buffer, packedLight, packedOverlay, getMaterial(decoratedpotblockentity$decorations.right()));
      pose.m_85849_();
   }

   private void rotateAround(PoseStack pose, Quaternion quaternion) {
      pose.m_85837_(0.5, 0.0, 0.5);
      pose.m_85845_(quaternion);
      pose.m_85837_(-0.5, 0.0, -0.5);
   }

   private void renderSide(ModelPart modelPart, PoseStack pose, MultiBufferSource buffer, int packedLight, int packedOverlay, @Nullable Material material) {
      if (material == null) {
         material = getMaterial(Items.f_42460_);
      }

      if (material != null) {
         modelPart.m_104301_(pose, material.m_119194_(buffer, RenderType::m_110446_), packedLight, packedOverlay);
      }
   }
}
