package com.ilexiconn.llibrary.client.model.tools;

import com.bobmowzie.mowziesmobs.client.render.MowzieRenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.model.Model;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AdvancedModelRenderer extends BasicModelRenderer {
   private static final float MINIMUM_SCALE = 1.0E-6F;
   public float defaultRotationX;
   public float defaultRotationY;
   public float defaultRotationZ;
   public float defaultPositionX;
   public float defaultPositionY;
   public float defaultPositionZ;
   public float scaleX = 1.0F;
   public float scaleY = 1.0F;
   public float scaleZ = 1.0F;
   public float opacity = 1.0F;
   public boolean scaleChildren;
   private final Model model;
   private AdvancedModelRenderer parent;
   private boolean doubleSided = true;
   private boolean hasLighting = true;
   private boolean isHidden = false;
   public ObjectList<AdvancedModelRenderer.ModelBox> cubeList;
   public ObjectList<BasicModelRenderer> childModels;
   public int textureOffsetX;
   public int textureOffsetY;
   private float textureWidth;
   private float textureHeight;
   private Matrix3f mat3Override;
   private Matrix4f mat4Override;

   public AdvancedModelRenderer(BasicModelBase model) {
      this(model, 0, 0);
   }

   public AdvancedModelRenderer(BasicModelBase model, int textureOffsetX, int textureOffsetY) {
      super(model, textureOffsetX, textureOffsetY);
      this.model = model;
      this.textureWidth = (float)model.textureWidth;
      this.textureHeight = (float)model.textureHeight;
      this.textureOffsetX = textureOffsetX;
      this.textureOffsetY = textureOffsetY;
      this.cubeList = new ObjectArrayList();
      this.childModels = new ObjectArrayList();
   }

   public AdvancedModelRenderer(AdvancedModelRenderer copyFrom) {
      this(copyFrom.getAdvancedModel(), copyFrom.textureOffsetX, copyFrom.textureOffsetY);
      this.rotationPointX = copyFrom.rotationPointX;
      this.rotationPointY = copyFrom.rotationPointY;
      this.rotationPointZ = copyFrom.rotationPointZ;
      this.rotateAngleX = copyFrom.rotateAngleX;
      this.rotateAngleY = copyFrom.rotateAngleY;
      this.rotateAngleZ = copyFrom.rotateAngleZ;
      this.scaleX = copyFrom.scaleX;
      this.scaleY = copyFrom.scaleY;
      this.scaleZ = copyFrom.scaleZ;
      this.defaultPositionX = copyFrom.defaultPositionX;
      this.defaultPositionY = copyFrom.defaultPositionY;
      this.defaultPositionZ = copyFrom.defaultPositionZ;
      this.defaultRotationX = copyFrom.defaultRotationX;
      this.defaultRotationY = copyFrom.defaultRotationY;
      this.defaultRotationZ = copyFrom.defaultRotationZ;
      this.scaleChildren = copyFrom.scaleChildren;
      this.cubeList.addAll(copyFrom.cubeList);
      this.childModels = copyFrom.childModels;
   }

   @Override
   public BasicModelRenderer setTextureSize(int textureWidthIn, int textureHeightIn) {
      this.textureWidth = (float)textureWidthIn;
      this.textureHeight = (float)textureHeightIn;
      return super.setTextureSize(textureWidthIn, textureHeightIn);
   }

   @Override
   public BasicModelRenderer setTextureOffset(int x, int y) {
      this.textureOffsetX = x;
      this.textureOffsetY = y;
      return super.setTextureOffset(x, y);
   }

   public AdvancedModelRenderer addBox(String partName, float x, float y, float z, int width, int height, int depth, float delta, int texX, int texY) {
      this.setTextureSize(texX, texY);
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, (float)width, (float)height, (float)depth, delta, delta, delta, this.mirror, false);
      return this;
   }

   public AdvancedModelRenderer addBox(float x, float y, float z, float width, float height, float depth) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, this.mirror, false);
      return this;
   }

   public AdvancedModelRenderer addBox(float x, float y, float z, float width, float height, float depth, boolean mirrorIn) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, 0.0F, 0.0F, 0.0F, mirrorIn, false);
      return this;
   }

   @Override
   public void addBox(float x, float y, float z, float width, float height, float depth, float delta) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, this.mirror, false);
   }

   @Override
   public void addBox(float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, this.mirror, false);
   }

   @Override
   public void addBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirrorIn) {
      this.addBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, mirrorIn, false);
   }

   private void addBox(
      int texOffX,
      int texOffY,
      float x,
      float y,
      float z,
      float width,
      float height,
      float depth,
      float deltaX,
      float deltaY,
      float deltaZ,
      boolean mirror,
      boolean p_228305_13_
   ) {
      this.cubeList
         .add(
            new AdvancedModelRenderer.ModelBox(
               texOffX, texOffY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, mirror, this.textureWidth, this.textureHeight
            )
         );
   }

   public void setShouldScaleChildren(boolean scaleChildren) {
      this.scaleChildren = scaleChildren;
   }

   public void setScale(float scaleX, float scaleY, float scaleZ) {
      this.setScaleX(scaleX);
      this.setScaleY(scaleY);
      this.setScaleZ(scaleZ);
   }

   public void setScale(float scale) {
      this.setScaleX(scale);
      this.setScaleY(scale);
      this.setScaleZ(scale);
   }

   public void setScaleX(float scaleX) {
      this.scaleX = Math.max(1.0E-6F, scaleX);
   }

   public void setScaleY(float scaleY) {
      this.scaleY = Math.max(1.0E-6F, scaleY);
   }

   public void setScaleZ(float scaleZ) {
      this.scaleZ = Math.max(1.0E-6F, scaleZ);
   }

   public void setOpacity(float opacity) {
      this.opacity = opacity;
   }

   public void setHasLighting(boolean hasLighting) {
      this.hasLighting = hasLighting;
   }

   public void setDoubleSided(boolean doubleSided) {
      this.doubleSided = doubleSided;
   }

   public void setIsHidden(boolean isHidden) {
      this.isHidden = isHidden;
   }

   public boolean isHidden() {
      return this.isHidden;
   }

   public float getTextureWidth() {
      return this.textureWidth;
   }

   public float getTextureHeight() {
      return this.textureHeight;
   }

   public void updateDefaultPose() {
      this.defaultRotationX = this.rotateAngleX;
      this.defaultRotationY = this.rotateAngleY;
      this.defaultRotationZ = this.rotateAngleZ;
      this.defaultPositionX = this.rotationPointX;
      this.defaultPositionY = this.rotationPointY;
      this.defaultPositionZ = this.rotationPointZ;
   }

   public void resetToDefaultPose() {
      this.rotateAngleX = this.defaultRotationX;
      this.rotateAngleY = this.defaultRotationY;
      this.rotateAngleZ = this.defaultRotationZ;
      this.rotationPointX = this.defaultPositionX;
      this.rotationPointY = this.defaultPositionY;
      this.rotationPointZ = this.defaultPositionZ;
   }

   @Override
   public void addChild(BasicModelRenderer renderer) {
      super.addChild(renderer);
      this.childModels.add(renderer);
      if (renderer instanceof AdvancedModelRenderer advancedChild) {
         advancedChild.setParent(this);
      }
   }

   public AdvancedModelRenderer getParent() {
      return this.parent;
   }

   public void setParent(AdvancedModelRenderer parent) {
      this.parent = parent;
   }

   @Override
   public void translateRotate(PoseStack matrixStackIn) {
      AdvancedModelRenderer parent = this.getParent();
      if (parent != null && !parent.scaleChildren) {
         matrixStackIn.m_85841_(1.0F / parent.scaleX, 1.0F / parent.scaleY, 1.0F / parent.scaleZ);
      }

      super.translateRotate(matrixStackIn);
      matrixStackIn.m_85841_(this.scaleX, this.scaleY, this.scaleZ);
   }

   @Override
   public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
      if (this.showModel && (!this.cubeList.isEmpty() || !this.childModels.isEmpty())) {
         matrixStackIn.m_85836_();
         this.translateRotate(matrixStackIn);
         if (!this.isHidden) {
            this.doRender(matrixStackIn.m_85850_(), bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha * this.opacity);
         }

         ObjectListIterator var9 = this.childModels.iterator();

         while (var9.hasNext()) {
            BasicModelRenderer modelrenderer = (BasicModelRenderer)var9.next();
            modelrenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
         }

         matrixStackIn.m_85849_();
      }
   }

   @Override
   protected void doRender(Pose matrixEntryIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
      Matrix4f matrix4f = matrixEntryIn.m_85861_();
      Matrix3f matrix3f = matrixEntryIn.m_85864_();
      if (this.mat3Override != null) {
         matrix3f = this.mat3Override;
      }

      if (this.mat4Override != null) {
         matrix4f = this.mat4Override;
      }

      ObjectListIterator var11 = this.cubeList.iterator();

      while (var11.hasNext()) {
         AdvancedModelRenderer.ModelPart modelrenderer$modelbox = (AdvancedModelRenderer.ModelPart)var11.next();
         modelrenderer$modelbox.render(matrix4f, matrix3f, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      }
   }

   public AdvancedModelBase getAdvancedModel() {
      return (AdvancedModelBase)this.model;
   }

   private float calculateRotation(float speed, float degree, boolean invert, float offset, float weight, float f, float f1) {
      float movementScale = this.model instanceof AdvancedModelBase ? ((AdvancedModelBase)this.model).getMovementScale() : 1.0F;
      float rotation = Mth.m_14089_(f * speed * movementScale + offset) * degree * movementScale * f1 + weight * f1;
      return invert ? -rotation : rotation;
   }

   public void walk(float speed, float degree, boolean invert, float offset, float weight, float walk, float walkAmount) {
      this.rotateAngleX = this.rotateAngleX + this.calculateRotation(speed, degree, invert, offset, weight, walk, walkAmount);
   }

   public void flap(float speed, float degree, boolean invert, float offset, float weight, float flap, float flapAmount) {
      this.rotateAngleZ = this.rotateAngleZ + this.calculateRotation(speed, degree, invert, offset, weight, flap, flapAmount);
   }

   public void swing(float speed, float degree, boolean invert, float offset, float weight, float swing, float swingAmount) {
      this.rotateAngleY = this.rotateAngleY + this.calculateRotation(speed, degree, invert, offset, weight, swing, swingAmount);
   }

   public void bob(float speed, float degree, boolean bounce, float f, float f1) {
      float movementScale = this.model instanceof AdvancedModelBase ? ((AdvancedModelBase)this.model).getMovementScale() : 1.0F;
      degree *= movementScale;
      speed *= movementScale;
      float bob = (float)(Math.sin((double)(f * speed)) * (double)f1 * (double)degree - (double)(f1 * degree));
      if (bounce) {
         bob = (float)(-Math.abs(Math.sin((double)(f * speed)) * (double)f1 * (double)degree));
      }

      this.rotationPointY += bob;
   }

   public void transitionTo(AdvancedModelRenderer to, float timer, float maxTime) {
      this.rotateAngleX = this.rotateAngleX + (to.rotateAngleX - this.rotateAngleX) / maxTime * timer;
      this.rotateAngleY = this.rotateAngleY + (to.rotateAngleY - this.rotateAngleY) / maxTime * timer;
      this.rotateAngleZ = this.rotateAngleZ + (to.rotateAngleZ - this.rotateAngleZ) / maxTime * timer;
      this.rotationPointX = this.rotationPointX + (to.rotationPointX - this.rotationPointX) / maxTime * timer;
      this.rotationPointY = this.rotationPointY + (to.rotationPointY - this.rotationPointY) / maxTime * timer;
      this.rotationPointZ = this.rotationPointZ + (to.rotationPointZ - this.rotationPointZ) / maxTime * timer;
   }

   public void setMatrixOverrides(Matrix3f mat3Override, Matrix4f mat4Override) {
      this.mat4Override = mat4Override;
      this.mat3Override = mat3Override;
   }

   public void clearMatrixOverrides() {
      this.mat3Override = null;
      this.mat4Override = null;
   }

   public void getMatrixStack(PoseStack matrixStack) {
      AdvancedModelRenderer parent = this.getParent();
      if (parent != null) {
         parent.getMatrixStack(matrixStack);
      }

      this.translateRotate(matrixStack);
   }

   public Vec3 getWorldPos(Entity entity, float delta) {
      PoseStack matrixStack = new PoseStack();
      float dx = (float)(entity.f_19790_ + (entity.m_20185_() - entity.f_19790_) * (double)delta);
      float dy = (float)(entity.f_19791_ + (entity.m_20186_() - entity.f_19791_) * (double)delta);
      float dz = (float)(entity.f_19792_ + (entity.m_20189_() - entity.f_19792_) * (double)delta);
      matrixStack.m_85837_((double)dx, (double)dy, (double)dz);
      float dYaw = Mth.m_14189_(delta, entity.f_19859_, entity.m_146908_());
      matrixStack.m_85845_(new Quaternion(0.0F, -dYaw + 180.0F, 0.0F, true));
      matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
      matrixStack.m_85837_(0.0, -1.5, 0.0);
      MowzieRenderUtils.matrixStackFromModel(matrixStack, this);
      Pose matrixEntry = matrixStack.m_85850_();
      Matrix4f matrix4f = matrixEntry.m_85861_();
      Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
      vec.m_123607_(matrix4f);
      return new Vec3((double)vec.m_123601_(), (double)vec.m_123615_(), (double)vec.m_123616_());
   }

   public void setWorldPos(Entity entity, Vec3 worldPos, float delta) {
      PoseStack matrixStack = new PoseStack();
      float dx = (float)(entity.f_19790_ + (entity.m_20185_() - entity.f_19790_) * (double)delta);
      float dy = (float)(entity.f_19791_ + (entity.m_20186_() - entity.f_19791_) * (double)delta);
      float dz = (float)(entity.f_19792_ + (entity.m_20189_() - entity.f_19792_) * (double)delta);
      matrixStack.m_85837_((double)dx, (double)dy, (double)dz);
      float dYaw = Mth.m_14189_(delta, entity.f_19859_, entity.m_146908_());
      matrixStack.m_85845_(new Quaternion(0.0F, -dYaw + 180.0F, 0.0F, true));
      matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
      matrixStack.m_85837_(0.0, -1.5, 0.0);
      Pose matrixEntry = matrixStack.m_85850_();
      Matrix4f matrix4f = matrixEntry.m_85861_();
      matrix4f.m_27657_();
      Vector4f vec = new Vector4f((float)worldPos.m_7096_(), (float)worldPos.m_7098_(), (float)worldPos.m_7094_(), 1.0F);
      vec.m_123607_(matrix4f);
      this.rotationPointX = vec.m_123601_() * 16.0F;
      this.rotationPointY = vec.m_123615_() * 16.0F;
      this.rotationPointZ = vec.m_123616_() * 16.0F;
   }

   @OnlyIn(Dist.CLIENT)
   public static class ModelBox extends AdvancedModelRenderer.ModelPart {
      protected final AdvancedModelRenderer.TexturedQuad[] quads;
      public final float posX1;
      public final float posY1;
      public final float posZ1;
      public final float posX2;
      public final float posY2;
      public final float posZ2;

      public ModelBox(
         int texOffX,
         int texOffY,
         float x,
         float y,
         float z,
         float width,
         float height,
         float depth,
         float deltaX,
         float deltaY,
         float deltaZ,
         boolean mirror,
         float texWidth,
         float texHeight
      ) {
         this.posX1 = x;
         this.posY1 = y;
         this.posZ1 = z;
         this.posX2 = x + width;
         this.posY2 = y + height;
         this.posZ2 = z + depth;
         this.quads = new AdvancedModelRenderer.TexturedQuad[6];
         float f = x + width;
         float f1 = y + height;
         float f2 = z + depth;
         x -= deltaX;
         y -= deltaY;
         z -= deltaZ;
         f += deltaX;
         f1 += deltaY;
         f2 += deltaZ;
         if (mirror) {
            float f3 = f;
            f = x;
            x = f3;
         }

         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex7 = new AdvancedModelRenderer.PositionTextureVertex(x, y, z, 0.0F, 0.0F);
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = new AdvancedModelRenderer.PositionTextureVertex(f, y, z, 0.0F, 8.0F);
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex1 = new AdvancedModelRenderer.PositionTextureVertex(
            f, f1, z, 8.0F, 8.0F
         );
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex2 = new AdvancedModelRenderer.PositionTextureVertex(
            x, f1, z, 8.0F, 0.0F
         );
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex3 = new AdvancedModelRenderer.PositionTextureVertex(
            x, y, f2, 0.0F, 0.0F
         );
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex4 = new AdvancedModelRenderer.PositionTextureVertex(
            f, y, f2, 0.0F, 8.0F
         );
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex5 = new AdvancedModelRenderer.PositionTextureVertex(
            f, f1, f2, 8.0F, 8.0F
         );
         AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex6 = new AdvancedModelRenderer.PositionTextureVertex(
            x, f1, f2, 8.0F, 0.0F
         );
         float f4 = (float)texOffX;
         float f5 = (float)texOffX + depth;
         float f6 = (float)texOffX + depth + width;
         float f7 = (float)texOffX + depth + width + width;
         float f8 = (float)texOffX + depth + width + depth;
         float f9 = (float)texOffX + depth + width + depth + width;
         float f10 = (float)texOffY;
         float f11 = (float)texOffY + depth;
         float f12 = (float)texOffY + depth + height;
         this.quads[2] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex4,
               modelrenderer$positiontexturevertex3,
               modelrenderer$positiontexturevertex7,
               modelrenderer$positiontexturevertex
            },
            f5,
            f10,
            f6,
            f11,
            texWidth,
            texHeight,
            mirror,
            Direction.DOWN
         );
         this.quads[3] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex1,
               modelrenderer$positiontexturevertex2,
               modelrenderer$positiontexturevertex6,
               modelrenderer$positiontexturevertex5
            },
            f6,
            f11,
            f7,
            f10,
            texWidth,
            texHeight,
            mirror,
            Direction.UP
         );
         this.quads[1] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex7,
               modelrenderer$positiontexturevertex3,
               modelrenderer$positiontexturevertex6,
               modelrenderer$positiontexturevertex2
            },
            f4,
            f11,
            f5,
            f12,
            texWidth,
            texHeight,
            mirror,
            Direction.WEST
         );
         this.quads[4] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex,
               modelrenderer$positiontexturevertex7,
               modelrenderer$positiontexturevertex2,
               modelrenderer$positiontexturevertex1
            },
            f5,
            f11,
            f6,
            f12,
            texWidth,
            texHeight,
            mirror,
            Direction.NORTH
         );
         this.quads[0] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex4,
               modelrenderer$positiontexturevertex,
               modelrenderer$positiontexturevertex1,
               modelrenderer$positiontexturevertex5
            },
            f6,
            f11,
            f8,
            f12,
            texWidth,
            texHeight,
            mirror,
            Direction.EAST
         );
         this.quads[5] = new AdvancedModelRenderer.TexturedQuad(
            new AdvancedModelRenderer.PositionTextureVertex[]{
               modelrenderer$positiontexturevertex3,
               modelrenderer$positiontexturevertex4,
               modelrenderer$positiontexturevertex5,
               modelrenderer$positiontexturevertex6
            },
            f8,
            f11,
            f9,
            f12,
            texWidth,
            texHeight,
            mirror,
            Direction.SOUTH
         );
      }

      @Override
      public void render(
         Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
      ) {
         for (AdvancedModelRenderer.TexturedQuad modelrenderer$texturedquad : this.quads) {
            Vector3f vector3f = modelrenderer$texturedquad.normal.m_122281_();
            vector3f.m_122249_(matrix3f);
            float f = vector3f.m_122239_();
            float f1 = vector3f.m_122260_();
            float f2 = vector3f.m_122269_();

            for (int i = 0; i < 4; i++) {
               AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = modelrenderer$texturedquad.vertexPositions[i];
               float f3 = modelrenderer$positiontexturevertex.position.m_122239_() / 16.0F;
               float f4 = modelrenderer$positiontexturevertex.position.m_122260_() / 16.0F;
               float f5 = modelrenderer$positiontexturevertex.position.m_122269_() / 16.0F;
               Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
               vector4f.m_123607_(matrix4f);
               bufferIn.m_5954_(
                  vector4f.m_123601_(),
                  vector4f.m_123615_(),
                  vector4f.m_123616_(),
                  red,
                  green,
                  blue,
                  alpha,
                  modelrenderer$positiontexturevertex.textureU,
                  modelrenderer$positiontexturevertex.textureV,
                  packedOverlayIn,
                  packedLightIn,
                  f,
                  f1,
                  f2
               );
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public abstract static class ModelPart {
      public void render(
         Matrix4f mat4, Matrix3f mat3, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
      ) {
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class PositionTextureVertex {
      public final Vector3f position;
      public final float textureU;
      public final float textureV;

      public PositionTextureVertex(float x, float y, float z, float texU, float texV) {
         this(new Vector3f(x, y, z), texU, texV);
      }

      public AdvancedModelRenderer.PositionTextureVertex setTextureUV(float texU, float texV) {
         return new AdvancedModelRenderer.PositionTextureVertex(this.position, texU, texV);
      }

      public PositionTextureVertex(Vector3f posIn, float texU, float texV) {
         this.position = posIn;
         this.textureU = texU;
         this.textureV = texV;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class TexturedQuad {
      public final AdvancedModelRenderer.PositionTextureVertex[] vertexPositions;
      public final Vector3f normal;

      public TexturedQuad(
         AdvancedModelRenderer.PositionTextureVertex[] positionsIn,
         float u1,
         float v1,
         float u2,
         float v2,
         float texWidth,
         float texHeight,
         boolean mirrorIn,
         Direction directionIn
      ) {
         this.vertexPositions = positionsIn;
         float f = 0.0F / texWidth;
         float f1 = 0.0F / texHeight;
         positionsIn[0] = positionsIn[0].setTextureUV(u2 / texWidth - f, v1 / texHeight + f1);
         positionsIn[1] = positionsIn[1].setTextureUV(u1 / texWidth + f, v1 / texHeight + f1);
         positionsIn[2] = positionsIn[2].setTextureUV(u1 / texWidth + f, v2 / texHeight - f1);
         positionsIn[3] = positionsIn[3].setTextureUV(u2 / texWidth - f, v2 / texHeight - f1);
         if (mirrorIn) {
            int i = positionsIn.length;

            for (int j = 0; j < i / 2; j++) {
               AdvancedModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex = positionsIn[j];
               positionsIn[j] = positionsIn[i - 1 - j];
               positionsIn[i - 1 - j] = modelrenderer$positiontexturevertex;
            }
         }

         this.normal = directionIn.m_122432_();
         if (mirrorIn) {
            this.normal.m_122263_(-1.0F, 1.0F, 1.0F);
         }
      }
   }
}
