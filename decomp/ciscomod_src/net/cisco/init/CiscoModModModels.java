package net.cisco.init;

import net.cisco.client.model.ModelFallen3d;
import net.cisco.client.model.ModelFallenBottomHalf;
import net.cisco.client.model.ModelFallenTopHalf;
import net.cisco.client.model.Modeladventurerbottom;
import net.cisco.client.model.Modeladventurertop;
import net.cisco.client.model.Modelascendedbottom;
import net.cisco.client.model.Modelascendedtop;
import net.cisco.client.model.Modelbjornbottom;
import net.cisco.client.model.Modelbjorntophalf;
import net.cisco.client.model.Modelblueboots;
import net.cisco.client.model.Modelbluechest;
import net.cisco.client.model.Modelbluehelm;
import net.cisco.client.model.Modelbrightlower;
import net.cisco.client.model.Modelbrightrelower;
import net.cisco.client.model.Modelbrightretop;
import net.cisco.client.model.Modelbrightsteeltophalf;
import net.cisco.client.model.Modelciscoentity;
import net.cisco.client.model.Modelcrimsonascendedbottom;
import net.cisco.client.model.Modelcrimsonascendedtop;
import net.cisco.client.model.Modelcustom_model;
import net.cisco.client.model.Modeldarkchest;
import net.cisco.client.model.Modeldarkhelm;
import net.cisco.client.model.Modeldarkleggings;
import net.cisco.client.model.Modeldarklower;
import net.cisco.client.model.Modeldarksteeltop;
import net.cisco.client.model.Modeleagletophalf;
import net.cisco.client.model.Modelfallenentity;
import net.cisco.client.model.Modelfallgelower;
import net.cisco.client.model.Modelfallgenxtophalf;
import net.cisco.client.model.Modelfellbottomre;
import net.cisco.client.model.Modelfellkingnew;
import net.cisco.client.model.Modelfellkingultitop;
import net.cisco.client.model.Modelfelltophalf;
import net.cisco.client.model.Modelfixedwind;
import net.cisco.client.model.Modelgildedlower;
import net.cisco.client.model.Modelsylvibottom;
import net.cisco.client.model.Modelsylvitophalf;
import net.cisco.client.model.Modelvioletascendedbottom;
import net.cisco.client.model.Modelvioletascendedtop;
import net.cisco.client.model.Modelwindbottomhalf;
import net.cisco.client.model.Modelwindlegs;
import net.cisco.client.model.Modelwindwalkertophalf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class CiscoModModModels {
   @SubscribeEvent
   public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(Modeldarksteeltop.LAYER_LOCATION, Modeldarksteeltop::createBodyLayer);
      event.registerLayerDefinition(Modelbrightsteeltophalf.LAYER_LOCATION, Modelbrightsteeltophalf::createBodyLayer);
      event.registerLayerDefinition(Modelwindbottomhalf.LAYER_LOCATION, Modelwindbottomhalf::createBodyLayer);
      event.registerLayerDefinition(Modelvioletascendedbottom.LAYER_LOCATION, Modelvioletascendedbottom::createBodyLayer);
      event.registerLayerDefinition(Modelfallgenxtophalf.LAYER_LOCATION, Modelfallgenxtophalf::createBodyLayer);
      event.registerLayerDefinition(Modeleagletophalf.LAYER_LOCATION, Modeleagletophalf::createBodyLayer);
      event.registerLayerDefinition(Modelbjornbottom.LAYER_LOCATION, Modelbjornbottom::createBodyLayer);
      event.registerLayerDefinition(Modelbrightlower.LAYER_LOCATION, Modelbrightlower::createBodyLayer);
      event.registerLayerDefinition(Modelfelltophalf.LAYER_LOCATION, Modelfelltophalf::createBodyLayer);
      event.registerLayerDefinition(Modelfallenentity.LAYER_LOCATION, Modelfallenentity::createBodyLayer);
      event.registerLayerDefinition(Modelfallgelower.LAYER_LOCATION, Modelfallgelower::createBodyLayer);
      event.registerLayerDefinition(Modelwindwalkertophalf.LAYER_LOCATION, Modelwindwalkertophalf::createBodyLayer);
      event.registerLayerDefinition(Modelwindlegs.LAYER_LOCATION, Modelwindlegs::createBodyLayer);
      event.registerLayerDefinition(Modeldarkleggings.LAYER_LOCATION, Modeldarkleggings::createBodyLayer);
      event.registerLayerDefinition(Modelgildedlower.LAYER_LOCATION, Modelgildedlower::createBodyLayer);
      event.registerLayerDefinition(Modeladventurerbottom.LAYER_LOCATION, Modeladventurerbottom::createBodyLayer);
      event.registerLayerDefinition(Modelascendedtop.LAYER_LOCATION, Modelascendedtop::createBodyLayer);
      event.registerLayerDefinition(Modelbjorntophalf.LAYER_LOCATION, Modelbjorntophalf::createBodyLayer);
      event.registerLayerDefinition(ModelFallen3d.LAYER_LOCATION, ModelFallen3d::createBodyLayer);
      event.registerLayerDefinition(Modelbrightretop.LAYER_LOCATION, Modelbrightretop::createBodyLayer);
      event.registerLayerDefinition(Modelfellbottomre.LAYER_LOCATION, Modelfellbottomre::createBodyLayer);
      event.registerLayerDefinition(Modelbluehelm.LAYER_LOCATION, Modelbluehelm::createBodyLayer);
      event.registerLayerDefinition(Modelcrimsonascendedbottom.LAYER_LOCATION, Modelcrimsonascendedbottom::createBodyLayer);
      event.registerLayerDefinition(ModelFallenBottomHalf.LAYER_LOCATION, ModelFallenBottomHalf::createBodyLayer);
      event.registerLayerDefinition(Modelcrimsonascendedtop.LAYER_LOCATION, Modelcrimsonascendedtop::createBodyLayer);
      event.registerLayerDefinition(Modelbluechest.LAYER_LOCATION, Modelbluechest::createBodyLayer);
      event.registerLayerDefinition(Modelfixedwind.LAYER_LOCATION, Modelfixedwind::createBodyLayer);
      event.registerLayerDefinition(Modelfellkingnew.LAYER_LOCATION, Modelfellkingnew::createBodyLayer);
      event.registerLayerDefinition(Modelcustom_model.LAYER_LOCATION, Modelcustom_model::createBodyLayer);
      event.registerLayerDefinition(ModelFallenTopHalf.LAYER_LOCATION, ModelFallenTopHalf::createBodyLayer);
      event.registerLayerDefinition(Modelvioletascendedtop.LAYER_LOCATION, Modelvioletascendedtop::createBodyLayer);
      event.registerLayerDefinition(Modelciscoentity.LAYER_LOCATION, Modelciscoentity::createBodyLayer);
      event.registerLayerDefinition(Modelblueboots.LAYER_LOCATION, Modelblueboots::createBodyLayer);
      event.registerLayerDefinition(Modeldarkchest.LAYER_LOCATION, Modeldarkchest::createBodyLayer);
      event.registerLayerDefinition(Modeldarklower.LAYER_LOCATION, Modeldarklower::createBodyLayer);
      event.registerLayerDefinition(Modeldarkhelm.LAYER_LOCATION, Modeldarkhelm::createBodyLayer);
      event.registerLayerDefinition(Modelsylvitophalf.LAYER_LOCATION, Modelsylvitophalf::createBodyLayer);
      event.registerLayerDefinition(Modelascendedbottom.LAYER_LOCATION, Modelascendedbottom::createBodyLayer);
      event.registerLayerDefinition(Modelsylvibottom.LAYER_LOCATION, Modelsylvibottom::createBodyLayer);
      event.registerLayerDefinition(Modelfellkingultitop.LAYER_LOCATION, Modelfellkingultitop::createBodyLayer);
      event.registerLayerDefinition(Modeladventurertop.LAYER_LOCATION, Modeladventurertop::createBodyLayer);
      event.registerLayerDefinition(Modelbrightrelower.LAYER_LOCATION, Modelbrightrelower::createBodyLayer);
   }
}
