package io.redspace.ironsspellbooks.setup;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AdjustmentModifier;
import dev.kosmx.playerAnim.api.layered.modifier.MirrorModifier;
import dev.kosmx.playerAnim.api.layered.modifier.AdjustmentModifier.PartModifier;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.block.alchemist_cauldron.AlchemistCauldronRenderer;
import io.redspace.ironsspellbooks.block.pedestal.PedestalRenderer;
import io.redspace.ironsspellbooks.block.scroll_forge.ScrollForgeRenderer;
import io.redspace.ironsspellbooks.compat.tetra.TetraProxy;
import io.redspace.ironsspellbooks.effect.PlanarSightEffect;
import io.redspace.ironsspellbooks.entity.VisualFallingBlockRenderer;
import io.redspace.ironsspellbooks.entity.armor.ArchevokerArmorModel;
import io.redspace.ironsspellbooks.entity.armor.CryomancerArmorModel;
import io.redspace.ironsspellbooks.entity.armor.CultistArmorModel;
import io.redspace.ironsspellbooks.entity.armor.ElectromancerArmorModel;
import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import io.redspace.ironsspellbooks.entity.armor.GoldCrownModel;
import io.redspace.ironsspellbooks.entity.armor.PlaguedArmorModel;
import io.redspace.ironsspellbooks.entity.armor.PriestArmorModel;
import io.redspace.ironsspellbooks.entity.armor.PyromancerArmorModel;
import io.redspace.ironsspellbooks.entity.armor.ShadowwalkerArmorModel;
import io.redspace.ironsspellbooks.entity.armor.TarnishedCrownModel;
import io.redspace.ironsspellbooks.entity.armor.WanderingMagicianModel;
import io.redspace.ironsspellbooks.entity.armor.netherite.NetheriteMageArmorModel;
import io.redspace.ironsspellbooks.entity.armor.netherite.NetheriteMageArmorRenderer;
import io.redspace.ironsspellbooks.entity.armor.pumpkin.PumpkinArmorModel;
import io.redspace.ironsspellbooks.entity.armor.pumpkin.PumpkinArmorRenderer;
import io.redspace.ironsspellbooks.entity.armor.simple_wizard.WizardArmorRenderer;
import io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingRenderer;
import io.redspace.ironsspellbooks.entity.mobs.debug_wizard.DebugWizardRenderer;
import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoidRenderer;
import io.redspace.ironsspellbooks.entity.mobs.horse.SpectralSteedRenderer;
import io.redspace.ironsspellbooks.entity.mobs.keeper.KeeperRenderer;
import io.redspace.ironsspellbooks.entity.mobs.necromancer.NecromancerRenderer;
import io.redspace.ironsspellbooks.entity.mobs.raise_dead_summons.SummonedSkeletonMultiRenderer;
import io.redspace.ironsspellbooks.entity.mobs.raise_dead_summons.SummonedZombieMultiRenderer;
import io.redspace.ironsspellbooks.entity.mobs.wizards.alchemist.ApothecaristRenderer;
import io.redspace.ironsspellbooks.entity.mobs.wizards.archevoker.ArchevokerRenderer;
import io.redspace.ironsspellbooks.entity.mobs.wizards.cryomancer.CryomancerRenderer;
import io.redspace.ironsspellbooks.entity.mobs.wizards.cultist.CultistRenderer;
import io.redspace.ironsspellbooks.entity.mobs.wizards.priest.PriestRenderer;
import io.redspace.ironsspellbooks.entity.mobs.wizards.pyromancer.PyromancerRenderer;
import io.redspace.ironsspellbooks.entity.spells.acid_orb.AcidOrbRenderer;
import io.redspace.ironsspellbooks.entity.spells.ball_lightning.BallLightningRenderer;
import io.redspace.ironsspellbooks.entity.spells.black_hole.BlackHoleRenderer;
import io.redspace.ironsspellbooks.entity.spells.blood_needle.BloodNeedleRenderer;
import io.redspace.ironsspellbooks.entity.spells.blood_slash.BloodSlashRenderer;
import io.redspace.ironsspellbooks.entity.spells.comet.CometRenderer;
import io.redspace.ironsspellbooks.entity.spells.cone_of_cold.ConeOfColdRenderer;
import io.redspace.ironsspellbooks.entity.spells.creeper_head.CreeperHeadRenderer;
import io.redspace.ironsspellbooks.entity.spells.devour_jaw.DevourJawRenderer;
import io.redspace.ironsspellbooks.entity.spells.eldritch_blast.EldritchBlastRenderer;
import io.redspace.ironsspellbooks.entity.spells.electrocute.ElectrocuteRenderer;
import io.redspace.ironsspellbooks.entity.spells.fireball.FireballRenderer;
import io.redspace.ironsspellbooks.entity.spells.firebolt.FireboltRenderer;
import io.redspace.ironsspellbooks.entity.spells.flame_strike.FlameStrikeRenderer;
import io.redspace.ironsspellbooks.entity.spells.guiding_bolt.GuidingBoltRenderer;
import io.redspace.ironsspellbooks.entity.spells.gust.GustRenderer;
import io.redspace.ironsspellbooks.entity.spells.ice_block.IceBlockRenderer;
import io.redspace.ironsspellbooks.entity.spells.icicle.IcicleRenderer;
import io.redspace.ironsspellbooks.entity.spells.lightning_lance.LightningLanceRenderer;
import io.redspace.ironsspellbooks.entity.spells.magic_arrow.MagicArrowRenderer;
import io.redspace.ironsspellbooks.entity.spells.magic_missile.MagicMissileRenderer;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.MagmaBallRenderer;
import io.redspace.ironsspellbooks.entity.spells.poison_arrow.PoisonArrowRenderer;
import io.redspace.ironsspellbooks.entity.spells.portal.PortalRenderer;
import io.redspace.ironsspellbooks.entity.spells.ray_of_frost.RayOfFrostRenderer;
import io.redspace.ironsspellbooks.entity.spells.root.RootRenderer;
import io.redspace.ironsspellbooks.entity.spells.shield.ShieldModel;
import io.redspace.ironsspellbooks.entity.spells.shield.ShieldRenderer;
import io.redspace.ironsspellbooks.entity.spells.shield.ShieldTrimModel;
import io.redspace.ironsspellbooks.entity.spells.small_magic_arrow.SmallMagicArrowRenderer;
import io.redspace.ironsspellbooks.entity.spells.spectral_hammer.SpectralHammerRenderer;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetAreaRenderer;
import io.redspace.ironsspellbooks.entity.spells.void_tentacle.VoidTentacleRenderer;
import io.redspace.ironsspellbooks.entity.spells.wisp.WispRenderer;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.WaywardCompass;
import io.redspace.ironsspellbooks.item.armor.ArchevokerArmorItem;
import io.redspace.ironsspellbooks.item.armor.CryomancerArmorItem;
import io.redspace.ironsspellbooks.item.armor.CultistArmorItem;
import io.redspace.ironsspellbooks.item.armor.ElectromancerArmorItem;
import io.redspace.ironsspellbooks.item.armor.GoldCrownArmorItem;
import io.redspace.ironsspellbooks.item.armor.NetheriteMageArmorItem;
import io.redspace.ironsspellbooks.item.armor.PlaguedArmorItem;
import io.redspace.ironsspellbooks.item.armor.PriestArmorItem;
import io.redspace.ironsspellbooks.item.armor.PumpkinArmorItem;
import io.redspace.ironsspellbooks.item.armor.PyromancerArmorItem;
import io.redspace.ironsspellbooks.item.armor.ShadowwalkerArmorItem;
import io.redspace.ironsspellbooks.item.armor.TarnishedCrownArmorItem;
import io.redspace.ironsspellbooks.item.armor.WanderingMagicianArmorItem;
import io.redspace.ironsspellbooks.item.armor.WizardArmorItem;
import io.redspace.ironsspellbooks.item.weapons.AutoloaderCrossbow;
import io.redspace.ironsspellbooks.particle.AcidBubbleParticle;
import io.redspace.ironsspellbooks.particle.AcidParticle;
import io.redspace.ironsspellbooks.particle.BlastwaveParticle;
import io.redspace.ironsspellbooks.particle.BloodGroundParticle;
import io.redspace.ironsspellbooks.particle.BloodParticle;
import io.redspace.ironsspellbooks.particle.DragonFireParticle;
import io.redspace.ironsspellbooks.particle.ElectricityParticle;
import io.redspace.ironsspellbooks.particle.EmberParticle;
import io.redspace.ironsspellbooks.particle.FireParticle;
import io.redspace.ironsspellbooks.particle.FireflyParticle;
import io.redspace.ironsspellbooks.particle.FogParticle;
import io.redspace.ironsspellbooks.particle.PortalFrameParticle;
import io.redspace.ironsspellbooks.particle.RingSmokeParticle;
import io.redspace.ironsspellbooks.particle.ShockwaveParticle;
import io.redspace.ironsspellbooks.particle.SiphonParticle;
import io.redspace.ironsspellbooks.particle.SnowDustParticle;
import io.redspace.ironsspellbooks.particle.SnowflakeParticle;
import io.redspace.ironsspellbooks.particle.SparkParticle;
import io.redspace.ironsspellbooks.particle.UnstableEnderParticle;
import io.redspace.ironsspellbooks.particle.WispParticle;
import io.redspace.ironsspellbooks.particle.ZapParticle;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.render.AffinityRingRenderer;
import io.redspace.ironsspellbooks.render.AngelWingsLayer;
import io.redspace.ironsspellbooks.render.AngelWingsModel;
import io.redspace.ironsspellbooks.render.ChargeSpellLayer;
import io.redspace.ironsspellbooks.render.EnergySwirlLayer;
import io.redspace.ironsspellbooks.render.GlowingEyesLayer;
import io.redspace.ironsspellbooks.render.ReplacedFireballRenderer;
import io.redspace.ironsspellbooks.render.ScrollModel;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import io.redspace.ironsspellbooks.render.SpellTargetingLayer;
import io.redspace.ironsspellbooks.util.IMinecraftInstanceHelper;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.FrogRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.EntityRenderersEvent.AddLayers;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.ModelEvent.BakingCompleted;
import net.minecraftforge.client.event.ModelEvent.RegisterAdditional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(
   modid = "irons_spellbooks",
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class ClientSetup {
   @SubscribeEvent
   public static void onRegisterLayers(RegisterLayerDefinitions event) {
      LayerDefinition energyOverlayLayer = LayerDefinition.m_171565_(HumanoidModel.m_170681_(new CubeDeformation(0.5F), 0.0F), 64, 64);
      LayerDefinition outerLayer = LayerDefinition.m_171565_(HumanoidModel.m_170681_(LayerDefinitions.f_171106_, 0.0F), 64, 32);
      LayerDefinition innerLayer = LayerDefinition.m_171565_(HumanoidModel.m_170681_(LayerDefinitions.f_171107_, 0.0F), 64, 32);
      event.registerLayerDefinition(ShieldModel.LAYER_LOCATION, ShieldModel::createBodyLayer);
      event.registerLayerDefinition(AcidOrbRenderer.MODEL_LAYER_LOCATION, AcidOrbRenderer::createBodyLayer);
      event.registerLayerDefinition(GustRenderer.MODEL_LAYER_LOCATION, GustRenderer::createBodyLayer);
      event.registerLayerDefinition(RayOfFrostRenderer.MODEL_LAYER_LOCATION, RayOfFrostRenderer::createBodyLayer);
      event.registerLayerDefinition(EldritchBlastRenderer.MODEL_LAYER_LOCATION, EldritchBlastRenderer::createBodyLayer);
      event.registerLayerDefinition(FireballRenderer.MODEL_LAYER_LOCATION, FireballRenderer::createBodyLayer);
      event.registerLayerDefinition(FireboltRenderer.MODEL_LAYER_LOCATION, FireboltRenderer::createBodyLayer);
      event.registerLayerDefinition(GuidingBoltRenderer.MODEL_LAYER_LOCATION, GuidingBoltRenderer::createBodyLayer);
      event.registerLayerDefinition(IcicleRenderer.MODEL_LAYER_LOCATION, IcicleRenderer::createBodyLayer);
      event.registerLayerDefinition(ShieldTrimModel.LAYER_LOCATION, ShieldTrimModel::createBodyLayer);
      event.registerLayerDefinition(AngelWingsModel.ANGEL_WINGS_LAYER, AngelWingsModel::createLayer);
      event.registerLayerDefinition(EnergySwirlLayer.Vanilla.ENERGY_LAYER, () -> energyOverlayLayer);
      event.registerLayerDefinition(BallLightningRenderer.MODEL_LAYER_LOCATION, BallLightningRenderer::createBodyLayer);
   }

   @SubscribeEvent
   public static void replaceRenderers(RegisterRenderers event) {
      event.registerEntityRenderer(EntityType.f_20527_, context -> new ReplacedFireballRenderer(context, 0.75F, 0.75F));
      event.registerEntityRenderer(EntityType.f_20463_, context -> new ReplacedFireballRenderer(context, 1.25F, 3.0F));
   }

   @SubscribeEvent
   public static void registerRenderers(AddLayers event) {
      GeoArmorRenderer.registerArmorRenderer(WizardArmorItem.class, WizardArmorRenderer::new);
      GeoArmorRenderer.registerArmorRenderer(WanderingMagicianArmorItem.class, () -> new GenericCustomArmorRenderer(new WanderingMagicianModel()));
      GeoArmorRenderer.registerArmorRenderer(PyromancerArmorItem.class, () -> new GenericCustomArmorRenderer(new PyromancerArmorModel()));
      GeoArmorRenderer.registerArmorRenderer(ElectromancerArmorItem.class, () -> new GenericCustomArmorRenderer(new ElectromancerArmorModel()));
      GeoArmorRenderer.registerArmorRenderer(ArchevokerArmorItem.class, () -> new GenericCustomArmorRenderer(new ArchevokerArmorModel()));
      GeoArmorRenderer.registerArmorRenderer(CultistArmorItem.class, () -> new GenericCustomArmorRenderer(new CultistArmorModel()));
      GeoArmorRenderer.registerArmorRenderer(CryomancerArmorItem.class, () -> new GenericCustomArmorRenderer(new CryomancerArmorModel()));
      GeoArmorRenderer.registerArmorRenderer(ShadowwalkerArmorItem.class, () -> new GenericCustomArmorRenderer(new ShadowwalkerArmorModel()));
      GeoArmorRenderer.registerArmorRenderer(PriestArmorItem.class, () -> new GenericCustomArmorRenderer(new PriestArmorModel()));
      GeoArmorRenderer.registerArmorRenderer(TarnishedCrownArmorItem.class, () -> new GenericCustomArmorRenderer(new TarnishedCrownModel()));
      GeoArmorRenderer.registerArmorRenderer(PumpkinArmorItem.class, () -> new PumpkinArmorRenderer(new PumpkinArmorModel()));
      GeoArmorRenderer.registerArmorRenderer(PlaguedArmorItem.class, () -> new GenericCustomArmorRenderer(new PlaguedArmorModel()));
      GeoArmorRenderer.registerArmorRenderer(GoldCrownArmorItem.class, () -> new GenericCustomArmorRenderer(new GoldCrownModel()));
      GeoArmorRenderer.registerArmorRenderer(NetheriteMageArmorItem.class, () -> new NetheriteMageArmorRenderer(new NetheriteMageArmorModel()));
      addLayerToPlayerSkin(event, "default");
      addLayerToPlayerSkin(event, "slim");
   }

   private static void addLayerToPlayerSkin(AddLayers event, String skinName) {
      EntityRenderer<? extends Player> render = event.getSkin(skinName);
      if (render instanceof LivingEntityRenderer livingRenderer) {
         livingRenderer.m_115326_(new AngelWingsLayer(livingRenderer));
         livingRenderer.m_115326_(new EnergySwirlLayer.Vanilla(livingRenderer, EnergySwirlLayer.EVASION_TEXTURE, 2L));
         livingRenderer.m_115326_(new EnergySwirlLayer.Vanilla(livingRenderer, EnergySwirlLayer.CHARGE_TEXTURE, 64L));
         livingRenderer.m_115326_(new ChargeSpellLayer.Vanilla(livingRenderer));
         livingRenderer.m_115326_(new GlowingEyesLayer.Vanilla(livingRenderer));
         livingRenderer.m_115326_(new SpellTargetingLayer.Vanilla(livingRenderer));
      }

      for (Entry<EntityType<?>, EntityRenderer<?>> entry : Minecraft.m_91087_().m_91290_().f_114362_.entrySet()) {
         EntityRenderer<?> livingEntityRendererTest = entry.getValue();
         if (livingEntityRendererTest instanceof LivingEntityRenderer) {
            EntityType<?> entityType = entry.getKey();
            LivingEntityRenderer renderer = event.getRenderer(entityType);
            if (renderer != null) {
               renderer.m_115326_(new SpellTargetingLayer.Vanilla(renderer));
            }
         }
      }
   }

   @SubscribeEvent
   public static void rendererRegister(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)EntityRegistry.MAGIC_MISSILE_PROJECTILE.get(), MagicMissileRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.CONE_OF_COLD_PROJECTILE.get(), ConeOfColdRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.BLOOD_SLASH_PROJECTILE.get(), BloodSlashRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.FLAME_STRIKE.get(), FlameStrikeRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.ELECTROCUTE_PROJECTILE.get(), ElectrocuteRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.FIREBOLT_PROJECTILE.get(), FireboltRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.ICICLE_PROJECTILE.get(), IcicleRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.FIRE_BREATH_PROJECTILE.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.POISON_BREATH_PROJECTILE.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.DRAGON_BREATH_PROJECTILE.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.DEBUG_WIZARD.get(), renderManager -> new DebugWizardRenderer(renderManager));
      event.registerEntityRenderer((EntityType)EntityRegistry.PYROMANCER.get(), PyromancerRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.APOTHECARIST.get(), ApothecaristRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.NECROMANCER.get(), NecromancerRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SPECTRAL_STEED.get(), SpectralSteedRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SHIELD_ENTITY.get(), ShieldRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.WALL_OF_FIRE_ENTITY.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.WISP.get(), WispRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SPECTRAL_HAMMER.get(), SpectralHammerRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SUMMONED_VEX.get(), VexRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SUMMONED_ZOMBIE.get(), SummonedZombieMultiRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SUMMONED_SKELETON.get(), SummonedSkeletonMultiRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.LIGHTNING_LANCE_PROJECTILE.get(), LightningLanceRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.WITHER_SKULL_PROJECTILE.get(), WitherSkullRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.MAGIC_ARROW_PROJECTILE.get(), MagicArrowRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.CREEPER_HEAD_PROJECTILE.get(), CreeperHeadRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.FROZEN_HUMANOID.get(), FrozenHumanoidRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SMALL_FIREBALL_PROJECTILE.get(), context -> new FireballRenderer(context, 0.75F));
      event.registerEntityRenderer((EntityType)EntityRegistry.COMET.get(), context -> new CometRenderer(context, 0.75F));
      event.registerEntityRenderer((EntityType)EntityRegistry.MAGIC_FIREBALL.get(), context -> new FireballRenderer(context, 1.25F));
      event.registerEntityRenderer((EntityType)EntityRegistry.SUMMONED_POLAR_BEAR.get(), PolarBearRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.DEAD_KING.get(), DeadKingRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.DEAD_KING_CORPSE.get(), DeadKingRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.ARCHEVOKER.get(), ArchevokerRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.KEEPER.get(), KeeperRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SCULK_TENTACLE.get(), VoidTentacleRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.ROOT.get(), RootRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.ICE_BLOCK_PROJECTILE.get(), IceBlockRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.CRYOMANCER.get(), CryomancerRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.POISON_CLOUD.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SUNBEAM.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.DRAGON_BREATH_POOL.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.POISON_ARROW.get(), PoisonArrowRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.POISON_SPLASH.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SUMMONED_FROG.get(), FrogRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.ACID_ORB.get(), AcidOrbRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.BLACK_HOLE.get(), BlackHoleRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.BLOOD_NEEDLE.get(), BloodNeedleRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.FIRE_FIELD.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.FIRE_BOMB.get(), MagmaBallRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.TARGET_AREA_ENTITY.get(), TargetAreaRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.HEALING_AOE.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.PRIEST.get(), PriestRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.GUIDING_BOLT.get(), GuidingBoltRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.GUST_COLLIDER.get(), GustRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.CHAIN_LIGHTNING.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.DEVOUR_JAW.get(), DevourJawRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.FIREFLY_SWARM.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.EARTHQUAKE_AOE.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.FALLING_BLOCK.get(), VisualFallingBlockRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.RAY_OF_FROST_VISUAL_ENTITY.get(), RayOfFrostRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.ELDRITCH_BLAST_VISUAL_ENTITY.get(), EldritchBlastRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.PORTAL.get(), PortalRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SMALL_MAGIC_ARROW.get(), SmallMagicArrowRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.ARROW_VOLLEY_ENTITY.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.STOMP_AOE.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.ECHOING_STRIKE.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.LIGHTNING_STRIKE.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.CULTIST.get(), CultistRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.BALL_LIGHTNING.get(), BallLightningRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BlockRegistry.SCROLL_FORGE_TILE.get(), ScrollForgeRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BlockRegistry.PEDESTAL_TILE.get(), PedestalRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BlockRegistry.ALCHEMIST_CAULDRON_TILE.get(), AlchemistCauldronRenderer::new);
   }

   @SubscribeEvent
   public static void registerParticles(RegisterParticleProvidersEvent event) {
      event.register((ParticleType)ParticleRegistry.WISP_PARTICLE.get(), WispParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.BLOOD_PARTICLE.get(), BloodParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.BLOOD_GROUND_PARTICLE.get(), BloodGroundParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.SNOWFLAKE_PARTICLE.get(), SnowflakeParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.ELECTRICITY_PARTICLE.get(), ElectricityParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.UNSTABLE_ENDER_PARTICLE.get(), UnstableEnderParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.DRAGON_FIRE_PARTICLE.get(), DragonFireParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.FIRE_PARTICLE.get(), FireParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.EMBER_PARTICLE.get(), EmberParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.SIPHON_PARTICLE.get(), SiphonParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.FOG_PARTICLE.get(), FogParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.SHOCKWAVE_PARTICLE.get(), ShockwaveParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.ACID_PARTICLE.get(), AcidParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.ACID_BUBBLE_PARTICLE.get(), AcidBubbleParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.ZAP_PARTICLE.get(), ZapParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.FIREFLY_PARTICLE.get(), FireflyParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.RING_SMOKE_PARTICLE.get(), RingSmokeParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.PORTAL_FRAME_PARTICLE.get(), PortalFrameParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.BLASTWAVE_PARTICLE.get(), BlastwaveParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.SPARK_PARTICLE.get(), SparkParticle.Provider::new);
      event.register((ParticleType)ParticleRegistry.SNOW_DUST.get(), SnowDustParticle.Provider::new);
   }

   @SubscribeEvent
   public static void clientSetup(FMLClientSetupEvent e) {
      e.enqueueWork(
         () -> {
            ItemProperties.register(
               (Item)ItemRegistry.WAYWARD_COMPASS.get(),
               new ResourceLocation("angle"),
               new CompassItemPropertyFunction((level, itemStack, entity) -> WaywardCompass.getCatacombsLocation(entity, itemStack.m_41784_()))
            );
            MinecraftInstanceHelper.instance = new IMinecraftInstanceHelper() {
               @Nullable
               @Override
               public Player player() {
                  return Minecraft.m_91087_().f_91074_;
               }
            };
            ItemProperties.register(
               (Item)ItemRegistry.AUTOLOADER_CROSSBOW.get(),
               new ResourceLocation("pull"),
               (itemStack, clientLevel, livingEntity, i) -> CrossbowItem.m_40932_(itemStack)
                     ? 0.0F
                     : (float)AutoloaderCrossbow.getLoadingTicks(itemStack) / (float)AutoloaderCrossbow.m_40939_(itemStack)
            );
            ItemProperties.register(
               (Item)ItemRegistry.AUTOLOADER_CROSSBOW.get(),
               new ResourceLocation("pulling"),
               (itemStack, clientLevel, livingEntity, i) -> AutoloaderCrossbow.isLoading(itemStack) && !CrossbowItem.m_40932_(itemStack) ? 1.0F : 0.0F
            );
            ItemProperties.register(
               (Item)ItemRegistry.AUTOLOADER_CROSSBOW.get(),
               new ResourceLocation("charged"),
               (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && CrossbowItem.m_40932_(itemStack) ? 1.0F : 0.0F
            );
            ItemProperties.register(
               (Item)ItemRegistry.AUTOLOADER_CROSSBOW.get(),
               new ResourceLocation("firework"),
               (itemStack, clientLevel, livingEntity, i) -> livingEntity != null
                        && CrossbowItem.m_40932_(itemStack)
                        && CrossbowItem.m_40871_(itemStack, Items.f_42688_)
                     ? 1.0F
                     : 0.0F
            );
            FogRenderer.f_234164_.add(new PlanarSightEffect.EcholocationBlindnessFogFunction());
            ItemRegistry.getIronsItems()
               .stream()
               .filter(item -> item.get() instanceof SpellBook)
               .forEach(item -> CuriosRendererRegistry.register((Item)item.get(), SpellBookCurioRenderer::new));
         }
      );
      PlayerAnimationFactory.ANIMATION_DATA_FACTORY
         .registerFactory(
            SpellAnimations.ANIMATION_RESOURCE,
            42,
            player -> {
               ModifierLayer<IAnimation> animation = new ModifierLayer();
               animation.addModifierLast(
                  new AdjustmentModifier(
                     partName -> {
                        switch (partName) {
                           case "rightArm":
                           case "leftArm":
                              return Optional.of(
                                 new PartModifier(
                                    new Vec3f(
                                       player.m_146909_() * (float) (Math.PI / 180.0), (float) (Math.PI / 180.0) * (player.f_20885_ - player.f_20883_), 0.0F
                                    ),
                                    Vec3f.ZERO
                                 )
                              );
                           default:
                              return Optional.empty();
                        }
                     }
                  )
               );
               animation.addModifierLast(new MirrorModifier() {
                  public boolean isEnabled() {
                     return ClientMagicData.getSyncedSpellData(player).getCastingEquipmentSlot().equals(SpellSelectionManager.OFFHAND);
                  }
               });
               return animation;
            }
         );
      TetraProxy.PROXY.initClient();
   }

   @SubscribeEvent
   public static void registerSpecialModels(RegisterAdditional event) {
      event.register(IronsSpellbooks.id("item/keeper_flamberge_gui"));
      event.register(IronsSpellbooks.id("item/keeper_flamberge_normal"));
      event.register(IronsSpellbooks.id("item/magehunter_gui"));
      event.register(IronsSpellbooks.id("item/magehunter_normal"));
      event.register(IronsSpellbooks.id("item/truthseeker_gui"));
      event.register(IronsSpellbooks.id("item/truthseeker_normal"));

      for (SchoolType schoolType : SchoolRegistry.REGISTRY.get().getValues()) {
         event.register(AffinityRingRenderer.getAffinityRingModelLocation(schoolType));
         event.register(ScrollModel.getScrollModelLocation(schoolType));
      }
   }

   @SubscribeEvent
   public static void replaceItemModels(BakingCompleted event) {
      ModelResourceLocation key = new ModelResourceLocation(IronsSpellbooks.id("scroll"), "inventory");
      BakedModel model = (BakedModel)event.getModels().get(key);
      IronsSpellbooks.LOGGER.debug("replaceItemModels {}: {}", key, model.getClass());
      event.getModels().put(key, new ScrollModel(model, event.getModelBakery()));
   }
}
