package com.bobmowzie.mowziesmobs.client;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelEvent.BakingCompleted;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class MMModels {
   public static final String[] HAND_MODEL_ITEMS = new String[]{"wrought_axe", "spear", "earthbore_gauntlet", "sculptor_staff"};

   @SubscribeEvent
   public static void onModelBakeEvent(BakingCompleted event) {
      Map<ResourceLocation, BakedModel> map = event.getModels();

      for (String item : HAND_MODEL_ITEMS) {
         ResourceLocation modelInventory = new ModelResourceLocation("mowziesmobs:" + item, "inventory");
         ResourceLocation modelHand = new ModelResourceLocation("mowziesmobs:" + item + "_in_hand", "inventory");
         final BakedModel bakedModelDefault = map.get(modelInventory);
         final BakedModel bakedModelHand = map.get(modelHand);
         BakedModel modelWrapper = new BakedModel() {
            public List<BakedQuad> m_213637_(BlockState state, Direction side, RandomSource rand) {
               return bakedModelDefault.m_213637_(state, side, rand);
            }

            public boolean m_7541_() {
               return bakedModelDefault.m_7541_();
            }

            public boolean m_7539_() {
               return bakedModelDefault.m_7539_();
            }

            public boolean m_7547_() {
               return false;
            }

            public boolean m_7521_() {
               return bakedModelDefault.m_7521_();
            }

            public TextureAtlasSprite m_6160_() {
               return bakedModelDefault.m_6160_();
            }

            public ItemOverrides m_7343_() {
               return bakedModelDefault.m_7343_();
            }

            public BakedModel applyTransform(TransformType cameraTransformType, PoseStack mat, boolean applyLeftHandTransform) {
               BakedModel modelToUse = bakedModelDefault;
               if (cameraTransformType == TransformType.FIRST_PERSON_LEFT_HAND
                  || cameraTransformType == TransformType.FIRST_PERSON_RIGHT_HAND
                  || cameraTransformType == TransformType.THIRD_PERSON_LEFT_HAND
                  || cameraTransformType == TransformType.THIRD_PERSON_RIGHT_HAND) {
                  modelToUse = bakedModelHand;
               }

               return ForgeHooksClient.handleCameraTransforms(mat, modelToUse, cameraTransformType, applyLeftHandTransform);
            }
         };
         map.put(modelInventory, modelWrapper);
      }

      for (MaskType type : MaskType.values()) {
         ModelResourceLocation maskModelInventory = new ModelResourceLocation("mowziesmobs:umvuthana_mask_" + type.name, "inventory");
         ModelResourceLocation maskModelFrame = new ModelResourceLocation("mowziesmobs:umvuthana_mask_" + type.name + "_frame", "inventory");
         bakeMask(map, maskModelInventory, maskModelFrame);
      }

      ModelResourceLocation maskModelInventory = new ModelResourceLocation("mowziesmobs:sol_visage", "inventory");
      ModelResourceLocation maskModelFrame = new ModelResourceLocation("mowziesmobs:sol_visage_frame", "inventory");
      bakeMask(map, maskModelInventory, maskModelFrame);
   }

   private static void bakeMask(Map<ResourceLocation, BakedModel> map, ModelResourceLocation maskModelInventory, ModelResourceLocation maskModelFrame) {
      final BakedModel maskBakedModelDefault = map.get(maskModelInventory);
      final BakedModel maskBakedModelFrame = map.get(maskModelFrame);
      BakedModel maskModelWrapper = new BakedModel() {
         public List<BakedQuad> m_213637_(BlockState state, Direction side, RandomSource rand) {
            return maskBakedModelDefault.m_213637_(state, side, rand);
         }

         public boolean m_7541_() {
            return maskBakedModelDefault.m_7541_();
         }

         public boolean m_7539_() {
            return maskBakedModelDefault.m_7539_();
         }

         public boolean m_7547_() {
            return false;
         }

         public boolean m_7521_() {
            return maskBakedModelDefault.m_7521_();
         }

         public TextureAtlasSprite m_6160_() {
            return maskBakedModelDefault.m_6160_();
         }

         public ItemOverrides m_7343_() {
            return maskBakedModelDefault.m_7343_();
         }

         public BakedModel applyTransform(TransformType cameraTransformType, PoseStack mat, boolean applyLeftHandTransform) {
            BakedModel modelToUse = maskBakedModelDefault;
            if (cameraTransformType == TransformType.FIXED) {
               modelToUse = maskBakedModelFrame;
            }

            return ForgeHooksClient.handleCameraTransforms(mat, modelToUse, cameraTransformType, applyLeftHandTransform);
         }
      };
      map.put(maskModelInventory, maskModelWrapper);
   }
}
