package com.rolfmao.upgradednetherite.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.rolfmao.upgradednetherite.init.ModItems;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.Holder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "upgradednetherite",
   bus = Bus.MOD
)
public class ShieldRenderer extends BlockEntityWithoutLevelRenderer {
   public static ShieldRenderer instance;

   public ShieldRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
      super(p_172550_, p_172551_);
   }

   @SubscribeEvent
   public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
      instance = new ShieldRenderer(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_());
      event.registerReloadListener(instance);
   }

   public void m_108829_(ItemStack stack, TransformType p_239207_2_, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
      matrixStack.m_85836_();
      matrixStack.m_85841_(1.0F, -1.0F, -1.0F);
      boolean flag = stack.m_41737_("BlockEntityTag") != null;
      Material rendermaterial = flag ? ModelBakery.f_119225_ : ModelBakery.f_119226_;
      Item shield = stack.m_41720_();
      if (shield == ModItems.NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_NETHERITE_SHIELD_BASE;
      } else if (shield == ModItems.GOLD_UPGRADED_NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_GOLD_UPGRADED_NETHERITE_SHIELD_BASE;
      } else if (shield == ModItems.FIRE_UPGRADED_NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_FIRE_UPGRADED_NETHERITE_SHIELD_BASE;
      } else if (shield == ModItems.ENDER_UPGRADED_NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_ENDER_UPGRADED_NETHERITE_SHIELD_BASE;
      } else if (shield == ModItems.WATER_UPGRADED_NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_WATER_UPGRADED_NETHERITE_SHIELD_BASE;
      } else if (shield == ModItems.WITHER_UPGRADED_NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_WITHER_UPGRADED_NETHERITE_SHIELD_BASE;
      } else if (shield == ModItems.POISON_UPGRADED_NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_POISON_UPGRADED_NETHERITE_SHIELD_BASE;
      } else if (shield == ModItems.PHANTOM_UPGRADED_NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_PHANTOM_UPGRADED_NETHERITE_SHIELD_BASE;
      } else if (shield == ModItems.FEATHER_UPGRADED_NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_FEATHER_UPGRADED_NETHERITE_SHIELD_BASE;
      } else if (shield == ModItems.CORRUPT_UPGRADED_NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_CORRUPT_UPGRADED_NETHERITE_SHIELD_BASE;
      } else if (shield == ModItems.ECHO_UPGRADED_NETHERITE_SHIELD.get()) {
         rendermaterial = ShieldTextures.LOCATION_ECHO_UPGRADED_NETHERITE_SHIELD_BASE;
      }

      VertexConsumer ivertexbuilder = rendermaterial.m_119204_()
         .m_118381_(ItemRenderer.m_115222_(buffer, this.f_108823_.m_103119_(rendermaterial.m_119193_()), true, stack.m_41790_()));
      this.f_108823_.m_103711_().m_104306_(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
      if (flag) {
         List<Pair<Holder<BannerPattern>, DyeColor>> list = BannerBlockEntity.m_58484_(ShieldItem.m_43102_(stack), BannerBlockEntity.m_58487_(stack));
         BannerRenderer.m_112074_(
            matrixStack, buffer, combinedLight, combinedOverlay, this.f_108823_.m_103701_(), rendermaterial, false, list, stack.m_41790_()
         );
      } else {
         this.f_108823_.m_103701_().m_104306_(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      matrixStack.m_85849_();
   }
}
