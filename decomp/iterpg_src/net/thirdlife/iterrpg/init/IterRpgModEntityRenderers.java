package net.thirdlife.iterrpg.init;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.thirdlife.iterrpg.client.renderer.AirElementalRenderer;
import net.thirdlife.iterrpg.client.renderer.AuraBoulderRenderer;
import net.thirdlife.iterrpg.client.renderer.AuraMobspawnRenderer;
import net.thirdlife.iterrpg.client.renderer.AuraSoulfireRenderer;
import net.thirdlife.iterrpg.client.renderer.AuraTearburstRenderer;
import net.thirdlife.iterrpg.client.renderer.BloatedRenderer;
import net.thirdlife.iterrpg.client.renderer.BlobRenderer;
import net.thirdlife.iterrpg.client.renderer.CaltropThrownRenderer;
import net.thirdlife.iterrpg.client.renderer.CarcassRenderer;
import net.thirdlife.iterrpg.client.renderer.ChainHandleRenderer;
import net.thirdlife.iterrpg.client.renderer.DebugMobmorphRenderer;
import net.thirdlife.iterrpg.client.renderer.DemonbloodProjectileRenderer;
import net.thirdlife.iterrpg.client.renderer.DemonsoulRenderer;
import net.thirdlife.iterrpg.client.renderer.DemonspineRenderer;
import net.thirdlife.iterrpg.client.renderer.DropletProjectileRenderer;
import net.thirdlife.iterrpg.client.renderer.EarthBoulderRenderer;
import net.thirdlife.iterrpg.client.renderer.EarthElementalRenderer;
import net.thirdlife.iterrpg.client.renderer.ElementalChargeRenderer;
import net.thirdlife.iterrpg.client.renderer.FireElementalRenderer;
import net.thirdlife.iterrpg.client.renderer.ForestVinesRenderer;
import net.thirdlife.iterrpg.client.renderer.GiantSpiderRenderer;
import net.thirdlife.iterrpg.client.renderer.GoblinRenderer;
import net.thirdlife.iterrpg.client.renderer.GoblinWarriorRenderer;
import net.thirdlife.iterrpg.client.renderer.GrieverRenderer;
import net.thirdlife.iterrpg.client.renderer.GrimBoulderRenderer;
import net.thirdlife.iterrpg.client.renderer.HobgoblinRenderer;
import net.thirdlife.iterrpg.client.renderer.InsatiableRenderer;
import net.thirdlife.iterrpg.client.renderer.MarrowRenderer;
import net.thirdlife.iterrpg.client.renderer.MournstoneRenderer;
import net.thirdlife.iterrpg.client.renderer.MudkinRenderer;
import net.thirdlife.iterrpg.client.renderer.PeeperRenderer;
import net.thirdlife.iterrpg.client.renderer.RevenantRenderer;
import net.thirdlife.iterrpg.client.renderer.ScallopRenderer;
import net.thirdlife.iterrpg.client.renderer.SorrowsealedRenderer;
import net.thirdlife.iterrpg.client.renderer.SpiderlingRenderer;
import net.thirdlife.iterrpg.client.renderer.VoidElementalRenderer;
import net.thirdlife.iterrpg.client.renderer.VoidPortalRenderer;
import net.thirdlife.iterrpg.client.renderer.WaterElementalRenderer;
import net.thirdlife.iterrpg.client.renderer.WeeperRenderer;
import net.thirdlife.iterrpg.client.renderer.WindswirlRenderer;

@EventBusSubscriber(
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class IterRpgModEntityRenderers {
   @SubscribeEvent
   public static void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)IterRpgModEntities.GOBLIN.get(), GoblinRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.GOBLIN_WARRIOR.get(), GoblinWarriorRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.HOBGOBLIN.get(), HobgoblinRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.SCALLOP.get(), ScallopRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.GIANT_SPIDER.get(), GiantSpiderRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.SPIDERLING.get(), SpiderlingRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.PEEPER.get(), PeeperRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.MUDKIN.get(), MudkinRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.GRIEVER.get(), GrieverRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.MOURNSTONE.get(), MournstoneRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.WEEPER.get(), WeeperRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.SORROWSEALED.get(), SorrowsealedRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.CARCASS.get(), CarcassRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.DEMONSOUL.get(), DemonsoulRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.REVENANT.get(), RevenantRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.EARTH_ELEMENTAL.get(), EarthElementalRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.WATER_ELEMENTAL.get(), WaterElementalRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.AIR_ELEMENTAL.get(), AirElementalRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.FIRE_ELEMENTAL.get(), FireElementalRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.VOID_ELEMENTAL.get(), VoidElementalRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.BLOATED.get(), BloatedRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.INSATIABLE.get(), InsatiableRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.CHAIN_HANDLE.get(), ChainHandleRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.CALTROP_THROWN.get(), CaltropThrownRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.BLOB.get(), BlobRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.FOREST_VINES.get(), ForestVinesRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.DEMONSPINE.get(), DemonspineRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.AURA_SOULFIRE.get(), AuraSoulfireRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.AURA_TEARBURST.get(), AuraTearburstRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.AURA_MOBSPAWN.get(), AuraMobspawnRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.AURA_BOULDER.get(), AuraBoulderRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.GRIM_BOULDER.get(), GrimBoulderRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.WINDSWIRL.get(), WindswirlRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.DEBUG_MOBMORPH.get(), DebugMobmorphRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.ELEMENTAL_CHARGE.get(), ElementalChargeRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.MARROW.get(), MarrowRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.FIREBALL_PROJECTILE.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.DROPLET_PROJECTILE.get(), DropletProjectileRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.WEEPER_TEAR.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.DEMONBLOOD_PROJECTILE.get(), DemonbloodProjectileRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.GIANT_WEEPER_TEAR.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.EARTH_BOULDER.get(), EarthBoulderRenderer::new);
      event.registerEntityRenderer((EntityType)IterRpgModEntities.VOID_PORTAL.get(), VoidPortalRenderer::new);
   }
}
