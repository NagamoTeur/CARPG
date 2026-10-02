package software.bernie.ars_nouveau.geckolib3.renderers.geo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import software.bernie.ars_nouveau.geckolib3.GeckoLib;
import software.bernie.ars_nouveau.geckolib3.compat.PatchouliCompat;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatableModel;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.util.EModelRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.GeoUtils;
import software.bernie.ars_nouveau.geckolib3.util.IRenderCycle;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public abstract class GeoArmorRenderer<T extends ArmorItem & IAnimatable> extends HumanoidModel implements IGeoRenderer<T>, AnimationController.ModelFetcher<T> {
   protected static Map<Class<? extends ArmorItem>, Supplier<GeoArmorRenderer>> CONSTRUCTORS = new ConcurrentHashMap<>();
   public static Map<Class<? extends ArmorItem>, ConcurrentHashMap<UUID, GeoArmorRenderer<?>>> LIVING_ENTITY_RENDERERS = new ConcurrentHashMap<>();
   protected Class<? extends ArmorItem> assignedItemClass = null;
   protected T currentArmorItem;
   protected LivingEntity entityLiving;
   protected ItemStack itemStack;
   protected EquipmentSlot armorSlot;
   protected float widthScale = 1.0F;
   protected float heightScale = 1.0F;
   protected Matrix4f dispatchedMat = new Matrix4f();
   protected Matrix4f renderEarlyMat = new Matrix4f();
   public String headBone = "armorHead";
   public String bodyBone = "armorBody";
   public String rightArmBone = "armorRightArm";
   public String leftArmBone = "armorLeftArm";
   public String rightLegBone = "armorRightLeg";
   public String leftLegBone = "armorLeftLeg";
   public String rightBootBone = "armorRightBoot";
   public String leftBootBone = "armorLeftBoot";
   private final AnimatedGeoModel<T> modelProvider;
   protected MultiBufferSource rtb;
   private IRenderCycle currentModelRenderCycle = EModelRenderCycle.INITIAL;

   @Nullable
   public IAnimatableModel<T> apply(IAnimatable t) {
      return t instanceof ArmorItem && t.getClass() == this.assignedItemClass ? this.getGeoModelProvider() : null;
   }

   @Deprecated(
      forRemoval = true
   )
   public static void registerArmorRenderer(Class<? extends ArmorItem> itemClass, GeoArmorRenderer renderer) {
      for (Constructor<?> constructor : renderer.getClass().getConstructors()) {
         if (constructor.getParameterCount() != 0) {
            GeckoLib.LOGGER
               .error(
                  "Registration of armor renderer for item class {} failed cause the renderer class {} does not feature a zero-args constructor!",
                  itemClass.getName(),
                  renderer.getClass().getName()
               );
            throw new IllegalArgumentException("If you still use the registration using instances, please give it a no-args constructor!");
         }

         registerArmorRenderer(itemClass, () -> {
            try {
               return (GeoArmorRenderer)constructor.newInstance();
            } catch (Exception var2) {
               var2.printStackTrace();
               return null;
            }
         });
      }
   }

   public static void registerArmorRenderer(Class<? extends ArmorItem> itemClass, Supplier<GeoArmorRenderer> rendererConstructor) {
      CONSTRUCTORS.put(itemClass, rendererConstructor);
      LIVING_ENTITY_RENDERERS.put(itemClass, new ConcurrentHashMap<>());
   }

   @Deprecated(
      forRemoval = true
   )
   public static GeoArmorRenderer getRenderer(Class<? extends ArmorItem> item, Entity wearer, boolean forExtendedEntity) {
      return getRenderer(item, wearer);
   }

   public static GeoArmorRenderer getRenderer(Class<? extends ArmorItem> item, Entity wearer) {
      ConcurrentHashMap<UUID, GeoArmorRenderer<?>> renderers = LIVING_ENTITY_RENDERERS.get(item);
      UUID uuid = wearer.m_20148_();
      GeoArmorRenderer armorRenderer;
      if (renderers == null || (armorRenderer = renderers.get(uuid)) == null) {
         armorRenderer = CONSTRUCTORS.get(item).get();
         if (armorRenderer == null) {
            throw new IllegalArgumentException("Renderer not registered for item " + item);
         }

         armorRenderer.assignedItemClass = item;
         if (renderers == null) {
            renderers = new ConcurrentHashMap<>();
            LIVING_ENTITY_RENDERERS.put(item, renderers);
         }

         renderers.put(uuid, armorRenderer);
      }

      return armorRenderer;
   }

   public GeoArmorRenderer(AnimatedGeoModel<T> modelProvider) {
      super(Minecraft.m_91087_().m_167973_().m_171103_(ModelLayers.f_171164_));
      AnimationController.addModelFetcher(this);
      this.modelProvider = modelProvider;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.render(0.0F, poseStack, buffer, packedLight);
   }

   public void render(float partialTick, PoseStack poseStack, VertexConsumer buffer, int packedLight) {
      GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelResource(this.currentArmorItem));
      AnimationEvent animationEvent = new AnimationEvent(
         this.currentArmorItem, 0.0F, 0.0F, Minecraft.m_91087_().m_91296_(), false, Arrays.asList(this.itemStack, this.entityLiving, this.armorSlot)
      );
      poseStack.m_85836_();
      poseStack.m_85837_(0.0, 1.5, 0.0);
      poseStack.m_85841_(-1.0F, -1.0F, 1.0F);
      this.dispatchedMat = poseStack.m_85850_().m_85861_().m_27658_();
      this.modelProvider.setLivingAnimations((T)this.currentArmorItem, this.getInstanceId(this.currentArmorItem), animationEvent);
      this.setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);
      this.fitToBiped();
      RenderSystem.m_157456_(0, this.getTextureLocation(this.currentArmorItem));
      Color renderColor = this.getRenderColor(this.currentArmorItem, partialTick, poseStack, null, buffer, packedLight);
      RenderType renderType = this.getRenderType(
         this.currentArmorItem, partialTick, poseStack, null, buffer, packedLight, this.getTextureLocation(this.currentArmorItem)
      );
      MultiBufferSource bufferSource = Minecraft.m_91087_().m_91269_().m_110104_();
      buffer = ItemRenderer.m_115184_(bufferSource, RenderType.m_110431_(this.getTextureLocation(this.currentArmorItem)), false, this.itemStack.m_41790_());
      this.render(
         model,
         this.currentArmorItem,
         partialTick,
         renderType,
         poseStack,
         bufferSource,
         buffer,
         packedLight,
         OverlayTexture.f_118083_,
         (float)renderColor.getRed() / 255.0F,
         (float)renderColor.getGreen() / 255.0F,
         (float)renderColor.getBlue() / 255.0F,
         (float)renderColor.getAlpha() / 255.0F
      );
      if (ModList.get().isLoaded("patchouli")) {
         PatchouliCompat.patchouliLoaded(poseStack);
      }

      poseStack.m_85849_();
   }

   public void renderEarly(
      T animatable,
      PoseStack poseStack,
      float partialTick,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      this.renderEarlyMat = poseStack.m_85850_().m_85861_().m_27658_();
      this.currentArmorItem = animatable;
      this.rtb = bufferSource;
      IGeoRenderer.super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (bone.isTrackingXform()) {
         Matrix4f poseState = poseStack.m_85850_().m_85861_();
         Vec3 renderOffset = this.getRenderOffset(this.currentArmorItem, 1.0F);
         Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);
         bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
         localMatrix.m_27648_(new Vector3f(renderOffset));
         bone.setLocalSpaceXform(localMatrix);
      }

      buffer = ItemRenderer.m_115184_(
         this.getCurrentRTB(), RenderType.m_110431_(this.getTextureLocation(this.currentArmorItem)), false, this.itemStack.m_41790_()
      );
      IGeoRenderer.super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public Vec3 getRenderOffset(T entity, float partialTick) {
      return Vec3.f_82478_;
   }

   protected void fitToBiped() {
      if (this.headBone != null) {
         IBone headBone = this.modelProvider.getBone(this.headBone);
         GeoUtils.copyRotations(this.f_102808_, headBone);
         headBone.setPositionX(this.f_102808_.f_104200_);
         headBone.setPositionY(-this.f_102808_.f_104201_);
         headBone.setPositionZ(this.f_102808_.f_104202_);
      }

      if (this.bodyBone != null) {
         IBone bodyBone = this.modelProvider.getBone(this.bodyBone);
         GeoUtils.copyRotations(this.f_102810_, bodyBone);
         bodyBone.setPositionX(this.f_102810_.f_104200_);
         bodyBone.setPositionY(-this.f_102810_.f_104201_);
         bodyBone.setPositionZ(this.f_102810_.f_104202_);
      }

      if (this.rightArmBone != null) {
         IBone rightArmBone = this.modelProvider.getBone(this.rightArmBone);
         GeoUtils.copyRotations(this.f_102811_, rightArmBone);
         rightArmBone.setPositionX(this.f_102811_.f_104200_ + 5.0F);
         rightArmBone.setPositionY(2.0F - this.f_102811_.f_104201_);
         rightArmBone.setPositionZ(this.f_102811_.f_104202_);
      }

      if (this.leftArmBone != null) {
         IBone leftArmBone = this.modelProvider.getBone(this.leftArmBone);
         GeoUtils.copyRotations(this.f_102812_, leftArmBone);
         leftArmBone.setPositionX(this.f_102812_.f_104200_ - 5.0F);
         leftArmBone.setPositionY(2.0F - this.f_102812_.f_104201_);
         leftArmBone.setPositionZ(this.f_102812_.f_104202_);
      }

      if (this.rightLegBone != null) {
         IBone rightLegBone = this.modelProvider.getBone(this.rightLegBone);
         GeoUtils.copyRotations(this.f_102813_, rightLegBone);
         rightLegBone.setPositionX(this.f_102813_.f_104200_ + 2.0F);
         rightLegBone.setPositionY(12.0F - this.f_102813_.f_104201_);
         rightLegBone.setPositionZ(this.f_102813_.f_104202_);
         if (this.rightBootBone != null) {
            IBone rightBootBone = this.modelProvider.getBone(this.rightBootBone);
            GeoUtils.copyRotations(this.f_102813_, rightBootBone);
            rightBootBone.setPositionX(this.f_102813_.f_104200_ + 2.0F);
            rightBootBone.setPositionY(12.0F - this.f_102813_.f_104201_);
            rightBootBone.setPositionZ(this.f_102813_.f_104202_);
         }
      }

      if (this.leftLegBone != null) {
         IBone leftLegBone = this.modelProvider.getBone(this.leftLegBone);
         GeoUtils.copyRotations(this.f_102814_, leftLegBone);
         leftLegBone.setPositionX(this.f_102814_.f_104200_ - 2.0F);
         leftLegBone.setPositionY(12.0F - this.f_102814_.f_104201_);
         leftLegBone.setPositionZ(this.f_102814_.f_104202_);
         if (this.leftBootBone != null) {
            IBone leftBootBone = this.modelProvider.getBone(this.leftBootBone);
            GeoUtils.copyRotations(this.f_102814_, leftBootBone);
            leftBootBone.setPositionX(this.f_102814_.f_104200_ - 2.0F);
            leftBootBone.setPositionY(12.0F - this.f_102814_.f_104201_);
            leftBootBone.setPositionZ(this.f_102814_.f_104202_);
         }
      }
   }

   public AnimatedGeoModel<T> getGeoModelProvider() {
      return this.modelProvider;
   }

   @Nonnull
   @AvailableSince("3.1.24")
   @Override
   public IRenderCycle getCurrentModelRenderCycle() {
      return this.currentModelRenderCycle;
   }

   @AvailableSince("3.1.24")
   @Override
   public void setCurrentModelRenderCycle(IRenderCycle currentModelRenderCycle) {
      this.currentModelRenderCycle = currentModelRenderCycle;
   }

   @AvailableSince("3.1.24")
   public float getWidthScale(T animatable) {
      return this.widthScale;
   }

   @AvailableSince("3.1.24")
   public float getHeightScale(T entity) {
      return this.heightScale;
   }

   public ResourceLocation getTextureLocation(T animatable) {
      return this.modelProvider.getTextureResource(animatable);
   }

   public GeoArmorRenderer setCurrentItem(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot) {
      this.entityLiving = entity;
      this.itemStack = itemStack;
      this.armorSlot = armorSlot;
      this.currentArmorItem = (T)itemStack.m_41720_();
      return this;
   }

   public final GeoArmorRenderer applyEntityStats(HumanoidModel defaultArmor) {
      this.f_102610_ = defaultArmor.f_102610_;
      this.f_102817_ = defaultArmor.f_102817_;
      this.f_102609_ = defaultArmor.f_102609_;
      this.f_102816_ = defaultArmor.f_102816_;
      this.f_102815_ = defaultArmor.f_102815_;
      return this;
   }

   public GeoArmorRenderer applySlot(EquipmentSlot slot) {
      this.modelProvider.getModel(this.modelProvider.getModelResource(this.currentArmorItem));
      this.setBoneVisibility(this.headBone, false);
      this.setBoneVisibility(this.bodyBone, false);
      this.setBoneVisibility(this.rightArmBone, false);
      this.setBoneVisibility(this.leftArmBone, false);
      this.setBoneVisibility(this.rightLegBone, false);
      this.setBoneVisibility(this.leftLegBone, false);
      this.setBoneVisibility(this.rightBootBone, false);
      this.setBoneVisibility(this.rightBootBone, false);
      this.setBoneVisibility(this.leftBootBone, false);
      switch (slot) {
         case HEAD:
            this.setBoneVisibility(this.headBone, true);
            break;
         case CHEST:
            this.setBoneVisibility(this.bodyBone, true);
            this.setBoneVisibility(this.rightArmBone, true);
            this.setBoneVisibility(this.leftArmBone, true);
            break;
         case LEGS:
            this.setBoneVisibility(this.rightLegBone, true);
            this.setBoneVisibility(this.leftLegBone, true);
            break;
         case FEET:
            this.setBoneVisibility(this.rightBootBone, true);
            this.setBoneVisibility(this.rightBootBone, true);
            this.setBoneVisibility(this.leftBootBone, true);
      }

      return this;
   }

   protected void setBoneVisibility(String boneName, boolean isVisible) {
      if (boneName != null) {
         this.modelProvider.getBone(boneName).setHidden(!isVisible);
      }
   }

   @Deprecated(
      forRemoval = true
   )
   protected IBone getAndHideBone(String boneName) {
      this.setBoneVisibility(boneName, false);
      return this.modelProvider.getBone(boneName);
   }

   @Deprecated(
      forRemoval = true
   )
   public Integer getUniqueID(T animatable) {
      return this.getInstanceId(animatable);
   }

   public int getInstanceId(T animatable) {
      return Objects.hash(
         this.armorSlot,
         this.itemStack.m_41720_(),
         this.itemStack.m_41613_(),
         this.itemStack.m_41782_() ? this.itemStack.m_41783_().toString() : 1,
         this.entityLiving.m_20148_().toString()
      );
   }

   @Override
   public void setCurrentRTB(MultiBufferSource bufferSource) {
      this.rtb = bufferSource;
   }

   @Override
   public MultiBufferSource getCurrentRTB() {
      return this.rtb;
   }
}
