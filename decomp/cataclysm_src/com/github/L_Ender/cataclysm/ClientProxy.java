package com.github.L_Ender.cataclysm;

import com.github.L_Ender.cataclysm.client.event.ClientEvent;
import com.github.L_Ender.cataclysm.client.gui.GUIWeponfusion;
import com.github.L_Ender.cataclysm.client.particle.CursedFlameParticle;
import com.github.L_Ender.cataclysm.client.particle.CustomExplodeParticle;
import com.github.L_Ender.cataclysm.client.particle.EM_PulseParticle;
import com.github.L_Ender.cataclysm.client.particle.FlameJetParticle;
import com.github.L_Ender.cataclysm.client.particle.LightTrailParticle;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.client.particle.Phantom_Wing_FlameParticle;
import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.client.particle.SandStormParticle;
import com.github.L_Ender.cataclysm.client.particle.Shock_WaveParticle;
import com.github.L_Ender.cataclysm.client.particle.SoulLavaParticle;
import com.github.L_Ender.cataclysm.client.particle.StormParticle;
import com.github.L_Ender.cataclysm.client.particle.TrackLightningParticle;
import com.github.L_Ender.cataclysm.client.particle.TrapFlameParticle;
import com.github.L_Ender.cataclysm.client.render.CMItemstackRenderer;
import com.github.L_Ender.cataclysm.client.render.blockentity.Cataclysm_Skull_Block_Renderer;
import com.github.L_Ender.cataclysm.client.render.blockentity.Cursed_Tombstone_Renderer;
import com.github.L_Ender.cataclysm.client.render.blockentity.Door_Of_Seal_Renderer;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererAbyssal_Egg;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererAltar_of_Abyss;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererAltar_of_Amethyst;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererAltar_of_Fire;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererAltar_of_Void;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererEMP;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererMechanical_fusion_anvil;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Blast_Portal_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Blast_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Mark_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Mine_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Orb_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Portal_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Amethyst_Cluster_Projectile_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Amethyst_Crab_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ancient_Desert_Stele_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ancient_Remnant_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ancient_Remnant_Rework_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Aptrgangr_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Axe_Blade_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Blazing_Bone_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Boltstrike_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Cm_Falling_Block_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Coral_Golem_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Coralssus_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Cursed_Sandstorm_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Death_Laser_beam_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Deepling_Angler_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Deepling_Brute_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Deepling_Priest_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Deepling_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Deepling_Warlock_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Dimensional_Rift_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Draugr_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Elite_Draugr_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ender_Golem_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ender_Guardian_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ender_Guardian_bullet_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Endermaptera_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Flame_Strike_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Flare_Bomb_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ignis_Abyss_Fireball_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ignis_Fireball_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ignis_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ignited_Berserker_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ignited_Revenant_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Kobolediator_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Koboleton_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Laser_Beam_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Lava_Bomb_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Lionfish_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Lionfish_Spike_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Maledictus_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Mini_Abyss_Blast_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Modern_Remnant_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Nameless_Sorcerer_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Netherite_Ministrosity_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Netherite_Monstrosity_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.New_Netherite_Monstrosity_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Phantom_Arrow_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Phantom_Halberd_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Poison_Dart_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Portal_Abyss_Blast_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.RendererNull;
import com.github.L_Ender.cataclysm.client.render.entity.Royal_Draugr_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Sandstorm_Projectile_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Sandstorm_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.The_Baby_Leviathan_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.The_Harbinger_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.The_Leviathan_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.The_Prowler_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.The_Watcher_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Thrown_Coral_Bardiche_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Thrown_Coral_Spear_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Tidal_Hook_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Tidal_Tentacle_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Void_Howitzer_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Void_Rune_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Void_Scatter_Arrow_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Void_Vortex_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Wadjet_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Wither_Homing_Missile_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Wither_Howitzer_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Wither_Missile_Renderer;
import com.github.L_Ender.cataclysm.client.render.etc.CurioHeadRenderer;
import com.github.L_Ender.cataclysm.client.render.item.CMItemRenderProperties;
import com.github.L_Ender.cataclysm.client.render.item.CustomArmorRenderProperties;
import com.github.L_Ender.cataclysm.client.render.item.CuriosItemREnderer.Blazing_Grips_Renderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosItemREnderer.RendererSandstorm_In_A_Bottle;
import com.github.L_Ender.cataclysm.client.render.item.CuriosItemREnderer.RendererSticky_Gloves;
import com.github.L_Ender.cataclysm.client.sound.MeatShredderSound;
import com.github.L_Ender.cataclysm.client.sound.SandstormSound;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModKeybind;
import com.github.L_Ender.cataclysm.init.ModMenu;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(
   modid = "cataclysm",
   value = {Dist.CLIENT}
)
public class ClientProxy extends CommonProxy {
   public static final Int2ObjectMap<AbstractTickableSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap();
   public static final Map<BlockEntity, AbstractTickableSoundInstance> BLOCK_ENTITY_SOUND_INSTANCE_MAP = new HashMap<>();
   public static Map<UUID, Integer> bossBarRenderTypes = new HashMap<>();
   public static List<UUID> blockedEntityRenders = new ArrayList<>();
   private Entity referencedMob = null;

   @Override
   public void init() {
      FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupParticles);
      FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerKeybinds);
   }

   public void setupParticles(RegisterParticleProvidersEvent registry) {
      Cataclysm.LOGGER.debug("Registered particle factories");
      registry.register((ParticleType)ModParticle.SOUL_LAVA.get(), SoulLavaParticle.Factory::new);
      registry.register((ParticleType)ModParticle.CURSED_FLAME.get(), CursedFlameParticle.Provider::new);
      registry.register((ParticleType)ModParticle.SMALL_CURSED_FLAME.get(), CursedFlameParticle.SmallFlameProvider::new);
      registry.register((ParticleType)ModParticle.PHANTOM_WING_FLAME.get(), Phantom_Wing_FlameParticle.EmissiveProvider::new);
      registry.register((ParticleType)ModParticle.EM_PULSE.get(), new EM_PulseParticle.Factory());
      registry.register((ParticleType)ModParticle.SHOCK_WAVE.get(), new Shock_WaveParticle.Factory());
      registry.register((ParticleType)ModParticle.LIGHTNING.get(), new LightningParticle.OrbFactory());
      registry.register((ParticleType)ModParticle.TRACK_LIGHTNING.get(), new TrackLightningParticle.OrbFactory());
      registry.register((ParticleType)ModParticle.STORM.get(), new StormParticle.OrbFactory());
      registry.register((ParticleType)ModParticle.RING.get(), RingParticle.RingFactory::new);
      registry.register((ParticleType)ModParticle.SANDSTORM.get(), SandStormParticle.Factory::new);
      registry.register((ParticleType)ModParticle.TRAP_FLAME.get(), TrapFlameParticle.Factory::new);
      registry.register((ParticleType)ModParticle.LIGHT_TRAIL.get(), new LightTrailParticle.OrbFactory());
      registry.register((ParticleType)ModParticle.FLAME_JET.get(), FlameJetParticle.Factory::new);
      registry.register((ParticleType)ModParticle.FLARE_EXPLODE.get(), CustomExplodeParticle.FlareFactory::new);
   }

   @Override
   public void clientInit() {
      ItemRenderer itemRendererIn = Minecraft.m_91087_().m_91291_();
      EntityRenderers.m_174036_((EntityType)ModEntities.ENDER_GOLEM.get(), Ender_Golem_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.NETHERITE_MONSTROSITY.get(), New_Netherite_Monstrosity_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.NETHERITE_MINISTROSITY.get(), Netherite_Ministrosity_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.OLD_NETHERITE_MONSTROSITY.get(), Netherite_Monstrosity_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.LAVA_BOMB.get(), Lava_Bomb_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.FLARE_BOMB.get(), Flare_Bomb_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.FLAME_JET.get(), RendererNull::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.NAMELESS_SORCERER.get(), Nameless_Sorcerer_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.IGNIS.get(), Ignis_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ENDER_GUARDIAN.get(), Ender_Guardian_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ENDER_GUARDIAN_BULLET.get(), Ender_Guardian_bullet_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.VOID_RUNE.get(), Void_Rune_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ENDERMAPTERA.get(), Endermaptera_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.IGNITED_REVENANT.get(), Ignited_Revenant_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.IGNITED_BERSERKER.get(), Ignited_Berserker_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.THE_HARBINGER.get(), The_Harbinger_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.VOID_SCATTER_ARROW.get(), Void_Scatter_Arrow_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.POISON_DART.get(), Poison_Dart_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.PHANTOM_ARROW.get(), Phantom_Arrow_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.SCREEN_SHAKE.get(), RendererNull::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.WITHER_SMOKE_EFFECT.get(), RendererNull::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ASHEN_BREATH.get(), RendererNull::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.WALL_WATCHER.get(), RendererNull::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.FLAME_STRIKE.get(), Flame_Strike_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.BOLT_STRIKE.get(), Boltstrike_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.CM_FALLING_BLOCK.get(), Cm_Falling_Block_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.IGNIS_FIREBALL.get(), Ignis_Fireball_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.IGNIS_ABYSS_FIREBALL.get(), Ignis_Abyss_Fireball_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.DEATH_LASER_BEAM.get(), Death_Laser_beam_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ABYSS_BLAST.get(), Abyss_Blast_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.MINI_ABYSS_BLAST.get(), Mini_Abyss_Blast_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.LASER_BEAM.get(), Laser_Beam_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.WITHER_MISSILE.get(), Wither_Missile_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.WITHER_HOMING_MISSILE.get(), Wither_Homing_Missile_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.WITHER_HOWITZER.get(), Wither_Howitzer_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.VOID_HOWITZER.get(), Void_Howitzer_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.VOID_VORTEX.get(), Void_Vortex_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.THE_LEVIATHAN.get(), The_Leviathan_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.THE_BABY_LEVIATHAN.get(), The_Baby_Leviathan_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ABYSS_PORTAL.get(), Abyss_Portal_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ABYSS_ORB.get(), Abyss_Orb_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ABYSS_BLAST_PORTAL.get(), Abyss_Blast_Portal_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.PORTAL_ABYSS_BLAST.get(), Portal_Abyss_Blast_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.DEEPLING.get(), Deepling_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ABYSS_MINE.get(), Abyss_Mine_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.CORAL_SPEAR.get(), Thrown_Coral_Spear_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.CORAL_BARDICHE.get(), Thrown_Coral_Bardiche_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.DEEPLING_BRUTE.get(), Deepling_Brute_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.DEEPLING_PRIEST.get(), Deepling_Priest_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.DIMENSIONAL_RIFT.get(), Dimensional_Rift_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.DEEPLING_ANGLER.get(), Deepling_Angler_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.DEEPLING_WARLOCK.get(), Deepling_Warlock_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ABYSS_MARK.get(), Abyss_Mark_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.CORAL_GOLEM.get(), Coral_Golem_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.CORALSSUS.get(), Coralssus_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.LIONFISH.get(), Lionfish_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.TIDAL_HOOK.get(), Tidal_Hook_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.AMETHYST_CRAB.get(), Amethyst_Crab_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ANCIENT_ANCIENT_REMNANT.get(), Ancient_Remnant_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ANCIENT_REMNANT.get(), Ancient_Remnant_Rework_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.MODERN_REMNANT.get(), Modern_Remnant_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.SANDSTORM.get(), Sandstorm_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.SANDSTORM_PROJECTILE.get(), Sandstorm_Projectile_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.CURSED_SANDSTORM.get(), Cursed_Sandstorm_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.THE_WATCHER.get(), The_Watcher_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.THE_PROWLER.get(), The_Prowler_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.KOBOLETON.get(), Koboleton_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.KOBOLEDIATOR.get(), Kobolediator_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.WADJET.get(), Wadjet_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.MALEDICTUS.get(), Maledictus_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.DRAUGR.get(), Draugr_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ROYAL_DRAUGR.get(), Royal_Draugr_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ELITE_DRAUGR.get(), Elite_Draugr_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.APTRGANGR.get(), Aptrgangr_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.AXE_BLADE.get(), Axe_Blade_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.PHANTOM_HALBERD.get(), Phantom_Halberd_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.EARTHQUAKE.get(), RendererNull::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.ANCIENT_DESERT_STELE.get(), Ancient_Desert_Stele_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(), Amethyst_Cluster_Projectile_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.THE_LEVIATHAN_TONGUE.get(), RendererNull::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.VOID_SHARD.get(), render -> new ThrownItemRenderer(render, 0.75F, true));
      EntityRenderers.m_174036_((EntityType)ModEntities.EYE_OF_DUNGEON.get(), render -> new ThrownItemRenderer(render, 1.0F, true));
      EntityRenderers.m_174036_((EntityType)ModEntities.BLAZING_BONE.get(), Blazing_Bone_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.LIONFISH_SPIKE.get(), Lionfish_Spike_Renderer::new);
      EntityRenderers.m_174036_((EntityType)ModEntities.TIDAL_TENTACLE.get(), Tidal_Tentacle_Renderer::new);
      MinecraftForge.EVENT_BUS.register(new ClientEvent());

      try {
         ItemProperties.register(
            (Item)ModItems.BULWARK_OF_THE_FLAME.get(),
            new ResourceLocation("blocking"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.m_6117_() && p_239421_2_.m_21211_() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.SOUL_RENDER.get(),
            new ResourceLocation("blocking"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.m_6117_() && p_239421_2_.m_21211_() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.CORAL_SPEAR.get(),
            new ResourceLocation("throwing"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.m_6117_() && p_239421_2_.m_21211_() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.CORAL_BARDICHE.get(),
            new ResourceLocation("throwing"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.m_6117_() && p_239421_2_.m_21211_() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.MEAT_SHREDDER.get(),
            new ResourceLocation("using"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.m_6117_() && p_239421_2_.m_21211_() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            Items.f_42717_,
            new ResourceLocation("cataclysm", "void_scatter_arrow"),
            (stack, world, entity, j) -> entity != null
                     && CrossbowItem.m_40932_(stack)
                     && CrossbowItem.m_40871_(stack, (Item)ModItems.VOID_SCATTER_ARROW.get())
                  ? 1.0F
                  : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.CORAL_CHUNK.get(),
            new ResourceLocation("chunk"),
            (stack, level, living, j) -> stack.m_41613_() % 3 == 0 ? 0.0F : (stack.m_41613_() % 3 == 1 ? 0.5F : 1.0F)
         );
         ItemProperties.register(
            (Item)ModItems.BLACK_STEEL_TARGE.get(),
            new ResourceLocation("blocking"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.m_6117_() && p_239421_2_.m_21211_() == stack ? 1.0F : 0.0F
         );
      } catch (Exception var3) {
         Cataclysm.LOGGER.warn("Could not load item models for weapons");
      }

      BlockEntityRenderers.m_173590_((BlockEntityType)ModTileentites.ALTAR_OF_FIRE.get(), RendererAltar_of_Fire::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)ModTileentites.ALTAR_OF_VOID.get(), RendererAltar_of_Void::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)ModTileentites.DOOR_OF_SEAL.get(), Door_Of_Seal_Renderer::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)ModTileentites.CURSED_TOMBSTONE.get(), Cursed_Tombstone_Renderer::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)ModTileentites.EMP.get(), RendererEMP::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)ModTileentites.MECHANICAL_FUSION_ANVIL.get(), RendererMechanical_fusion_anvil::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)ModTileentites.ALTAR_OF_AMETHYST.get(), RendererAltar_of_Amethyst::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)ModTileentites.CATACLYSM_SKULL.get(), Cataclysm_Skull_Block_Renderer::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)ModTileentites.ALTAR_OF_ABYSS.get(), RendererAltar_of_Abyss::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)ModTileentites.ABYSSAL_EGG.get(), RendererAbyssal_Egg::new);
      MenuScreens.m_96206_((MenuType)ModMenu.WEAPON_FUSION.get(), GUIWeponfusion::new);
      CuriosRendererRegistry.register((Item)ModItems.SANDSTORM_IN_A_BOTTLE.get(), RendererSandstorm_In_A_Bottle::new);
      CuriosRendererRegistry.register((Item)ModItems.STICKY_GLOVES.get(), RendererSticky_Gloves::new);
      CuriosRendererRegistry.register((Item)ModItems.KOBOLEDIATOR_SKULL.get(), CurioHeadRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.APTRGANGR_HEAD.get(), CurioHeadRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.DRAUGR_HEAD.get(), CurioHeadRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.BLAZING_GRIPS.get(), Blazing_Grips_Renderer::new);
   }

   @OnlyIn(Dist.CLIENT)
   public static Callable<BlockEntityWithoutLevelRenderer> getTEISR() {
      return CMItemstackRenderer::new;
   }

   @Override
   public Player getClientSidePlayer() {
      return Minecraft.m_91087_().f_91074_;
   }

   @Override
   public void blockRenderingEntity(UUID id) {
      blockedEntityRenders.add(id);
   }

   @Override
   public void releaseRenderingEntity(UUID id) {
      blockedEntityRenders.remove(id);
   }

   @Override
   public boolean isFirstPersonPlayer(Entity entity) {
      return entity.equals(Minecraft.m_91087_().f_91075_) && Minecraft.m_91087_().f_91066_.m_92176_().m_90612_();
   }

   @Override
   public Object getISTERProperties() {
      return new CMItemRenderProperties();
   }

   @Override
   public Object getArmorRenderProperties() {
      return new CustomArmorRenderProperties();
   }

   @Override
   public void clearSoundCacheFor(Entity entity) {
      ENTITY_SOUND_INSTANCE_MAP.remove(entity.m_19879_());
   }

   @Override
   public void clearSoundCacheFor(BlockEntity entity) {
      BLOCK_ENTITY_SOUND_INSTANCE_MAP.remove(entity);
   }

   @Override
   public float getPartialTicks() {
      return Minecraft.m_91087_().getPartialTick();
   }

   @Override
   public boolean isKeyDown(int keyType) {
      if (keyType != -1) {
         if (keyType == 0) {
            return Minecraft.m_91087_().f_91066_.f_92089_.m_90857_();
         } else if (keyType == 1) {
            return Minecraft.m_91087_().f_91066_.f_92091_.m_90857_();
         } else if (keyType == 2) {
            return ModKeybind.KEY_ABILITY.m_90859_();
         } else if (keyType == 3) {
            return Minecraft.m_91087_().f_91066_.f_92096_.m_90857_();
         } else if (keyType == 4) {
            return Minecraft.m_91087_().f_91066_.f_92090_.m_90857_();
         } else if (keyType == 5) {
            return ModKeybind.HELMET_KEY_ABILITY.m_90857_();
         } else if (keyType == 6) {
            return ModKeybind.CHESTPLATE_KEY_ABILITY.m_90857_();
         } else {
            return keyType == 7 ? ModKeybind.BOOTS_KEY_ABILITY.m_90857_() : false;
         }
      } else {
         return Minecraft.m_91087_().f_91066_.f_92086_.m_90857_()
            || Minecraft.m_91087_().f_91066_.f_92088_.m_90857_()
            || Minecraft.m_91087_().f_91066_.f_92085_.m_90857_()
            || Minecraft.m_91087_().f_91066_.f_92087_.m_90857_()
            || Minecraft.m_91087_().f_91066_.f_92089_.m_90857_();
      }
   }

   @Override
   public void playWorldSound(@Nullable Object soundEmitter, byte type) {
      if (soundEmitter instanceof Entity entity && !entity.f_19853_.f_46443_) {
         return;
      }

      switch (type) {
         case 1:
            if (soundEmitter instanceof LivingEntity livingEntity) {
               MeatShredderSound sound;
               label38: {
                  AbstractTickableSoundInstance old = (AbstractTickableSoundInstance)ENTITY_SOUND_INSTANCE_MAP.get(livingEntity.m_19879_());
                  if (old != null && old instanceof MeatShredderSound shredderSound && shredderSound.isSameEntity(livingEntity)) {
                     sound = (MeatShredderSound)old;
                     break label38;
                  }

                  sound = new MeatShredderSound(livingEntity);
                  ENTITY_SOUND_INSTANCE_MAP.put(livingEntity.m_19879_(), sound);
               }

               if (!Minecraft.m_91087_().m_91106_().m_120403_(sound) && sound.m_7767_()) {
                  Minecraft.m_91087_().m_91106_().m_120372_(sound);
               }
            }
            break;
         case 2:
            if (soundEmitter instanceof Sandstorm_Entity sandstom) {
               SandstormSound soundx;
               label45: {
                  AbstractTickableSoundInstance old = (AbstractTickableSoundInstance)ENTITY_SOUND_INSTANCE_MAP.get(sandstom.m_19879_());
                  if (old != null && old instanceof SandstormSound sandstomSound && sandstomSound.isSameEntity(sandstom)) {
                     soundx = (SandstormSound)old;
                     break label45;
                  }

                  soundx = new SandstormSound(sandstom);
                  ENTITY_SOUND_INSTANCE_MAP.put(sandstom.m_19879_(), soundx);
               }

               if (!Minecraft.m_91087_().m_91106_().m_120403_(soundx) && soundx.m_7767_()) {
                  Minecraft.m_91087_().m_91106_().m_120372_(soundx);
               }
            }
      }
   }

   private void registerKeybinds(RegisterKeyMappingsEvent e) {
      e.register(ModKeybind.KEY_ABILITY);
      e.register(ModKeybind.HELMET_KEY_ABILITY);
      e.register(ModKeybind.CHESTPLATE_KEY_ABILITY);
      e.register(ModKeybind.BOOTS_KEY_ABILITY);
   }

   @Override
   public void removeBossBarRender(UUID bossBar) {
      bossBarRenderTypes.remove(bossBar);
   }

   @Override
   public void setBossBarRender(UUID bossBar, int renderType) {
      bossBarRenderTypes.put(bossBar, renderType);
   }
}
