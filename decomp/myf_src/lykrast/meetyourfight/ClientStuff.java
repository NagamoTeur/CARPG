package lykrast.meetyourfight;

import lykrast.meetyourfight.registry.CompatGWRItems;
import lykrast.meetyourfight.registry.ModEntities;
import lykrast.meetyourfight.registry.ModItems;
import lykrast.meetyourfight.renderer.BellringerModel;
import lykrast.meetyourfight.renderer.BellringerRenderer;
import lykrast.meetyourfight.renderer.DameFortunaModel;
import lykrast.meetyourfight.renderer.DameFortunaRenderer;
import lykrast.meetyourfight.renderer.ProjectileLineModel;
import lykrast.meetyourfight.renderer.ProjectileLineRenderer;
import lykrast.meetyourfight.renderer.RosalyneModel;
import lykrast.meetyourfight.renderer.RosalyneRenderer;
import lykrast.meetyourfight.renderer.RoseSpiritModel;
import lykrast.meetyourfight.renderer.RoseSpiritRenderer;
import lykrast.meetyourfight.renderer.SwampMineModel;
import lykrast.meetyourfight.renderer.SwampMineRenderer;
import lykrast.meetyourfight.renderer.SwampjawModel;
import lykrast.meetyourfight.renderer.SwampjawRenderer;
import net.minecraft.Util;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.RegisterColorHandlersEvent.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(
   bus = Bus.MOD,
   modid = "meetyourfight",
   value = {Dist.CLIENT}
)
public class ClientStuff {
   @SubscribeEvent
   public static void registerEntityRenders(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)ModEntities.BELLRINGER.get(), context -> new BellringerRenderer(context));
      event.registerEntityRenderer((EntityType)ModEntities.DAME_FORTUNA.get(), context -> new DameFortunaRenderer(context));
      event.registerEntityRenderer((EntityType)ModEntities.SWAMPJAW.get(), context -> new SwampjawRenderer(context));
      event.registerEntityRenderer((EntityType)ModEntities.ROSALYNE.get(), context -> new RosalyneRenderer(context));
      event.registerEntityRenderer((EntityType)ModEntities.ROSE_SPIRIT.get(), context -> new RoseSpiritRenderer(context));
      event.registerEntityRenderer((EntityType)ModEntities.PROJECTILE_LINE.get(), context -> new ProjectileLineRenderer(context));
      event.registerEntityRenderer((EntityType)ModEntities.SWAMP_MINE.get(), context -> new SwampMineRenderer(context));
   }

   @SubscribeEvent
   public static void registerLayer(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(BellringerModel.MODEL, BellringerModel::createBodyLayer);
      event.registerLayerDefinition(DameFortunaModel.MODEL, DameFortunaModel::createBodyLayer);
      event.registerLayerDefinition(SwampjawModel.MODEL, SwampjawModel::createBodyLayer);
      event.registerLayerDefinition(RosalyneModel.MODEL, () -> RosalyneModel.createBodyLayer(CubeDeformation.f_171458_, true));
      event.registerLayerDefinition(RosalyneModel.MODEL_ARMOR, () -> RosalyneModel.createBodyLayer(LayerDefinitions.f_171107_, false));
      event.registerLayerDefinition(RoseSpiritModel.MODEL, RoseSpiritModel::createBodyLayer);
      event.registerLayerDefinition(ProjectileLineModel.MODEL, ProjectileLineModel::createBodyLayer);
      event.registerLayerDefinition(SwampMineModel.MODEL, SwampMineModel::createBodyLayer);
   }

   @SubscribeEvent
   public static void itemColors(Item event) {
      event.register(
         (s, t) -> t == 1 ? Mth.m_14169_((float)(Util.m_137550_() / 1000L % 360L) / 360.0F, 1.0F, 1.0F) : -1,
         new ItemLike[]{(ItemLike)ModItems.cocktailCutlass.get()}
      );
      if (MeetYourFight.loadedGunsWithoutRoses()) {
         event.register(
            (s, t) -> t == 1 ? Mth.m_14169_((float)(Util.m_137550_() / 1000L % 360L) / 360.0F, 0.75F, 0.75F) : -1,
            new ItemLike[]{(ItemLike)CompatGWRItems.cocktailShotgun.get()}
         );
      }
   }

   @SubscribeEvent
   public static void clientStuff(FMLClientSetupEvent event) {
      ItemProperties.register(
         (net.minecraft.world.item.Item)ModItems.depthStar.get(),
         MeetYourFight.rl("charge"),
         (stack, world, entity, someint) -> entity != null && entity.m_21211_() == stack ? (float)(stack.m_41779_() - entity.m_21212_()) / 20.0F : 0.0F
      );
      ItemProperties.register(
         (net.minecraft.world.item.Item)ModItems.depthStar.get(),
         MeetYourFight.rl("charging"),
         (stack, world, entity, someint) -> entity != null && entity.m_6117_() && entity.m_21211_() == stack ? 1.0F : 0.0F
      );
   }
}
