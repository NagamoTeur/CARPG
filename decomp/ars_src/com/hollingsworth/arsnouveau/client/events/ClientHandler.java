package com.hollingsworth.arsnouveau.client.events;

import com.hollingsworth.arsnouveau.api.camera.ICameraMountable;
import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.client.gui.GuiEntityInfoHUD;
import com.hollingsworth.arsnouveau.client.gui.GuiManaHUD;
import com.hollingsworth.arsnouveau.client.gui.GuiSpellHUD;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.renderer.entity.AmethystGolemModel;
import com.hollingsworth.arsnouveau.client.renderer.entity.AmethystGolemRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.AnimBlockRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.BookwyrmRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.ChimeraProjectileRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.DrygmyModel;
import com.hollingsworth.arsnouveau.client.renderer.entity.DummyRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.EnchantedSkullRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.GiftStarbyRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.LilyRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.RenderBlank;
import com.hollingsworth.arsnouveau.client.renderer.entity.RenderFlyingItem;
import com.hollingsworth.arsnouveau.client.renderer.entity.RenderRitualProjectile;
import com.hollingsworth.arsnouveau.client.renderer.entity.RenderSpell;
import com.hollingsworth.arsnouveau.client.renderer.entity.RenderSummonSkeleton;
import com.hollingsworth.arsnouveau.client.renderer.entity.StarbuncleRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.TextureVariantRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.WealdWalkerRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.WhirlisprigRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.WildenChimeraRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.WildenGuardianRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.WildenHunterRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.WildenStalkerRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.WixieModel;
import com.hollingsworth.arsnouveau.client.renderer.entity.familiar.AnimSkullRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.familiar.FamiliarBookwyrmRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.familiar.FamiliarStarbyModel;
import com.hollingsworth.arsnouveau.client.renderer.entity.familiar.FamiliarWhirlisprigRenderer;
import com.hollingsworth.arsnouveau.client.renderer.entity.familiar.GenericFamiliarRenderer;
import com.hollingsworth.arsnouveau.client.renderer.item.ArmorRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.AgronomicRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.AlchemicalRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.AlterationTableRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ArcaneCoreRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ArcanePedestalRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ArchwoodChestRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.BasicTurretRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.EnchantedFallingBlockRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.EnchantingApparatusRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.FalseweaveRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.GhostweaveRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ImbuementRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.IntangibleAirRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ItemDetectorRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.LecternRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.MageBlockRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.MirrorweaveRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.MobJarRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.MycelialRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.PortalTileRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.PotionMelderRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.RedstoneRelayRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ReducerTurretRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.RepositoryRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.RotatingTurretRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.RuneRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ScribesRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ScryerEyeRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ScryersEyeModel;
import com.hollingsworth.arsnouveau.client.renderer.tile.SkyBlockRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.TimerTurretRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.VitalicRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.VolcanicRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.WhirlisprigFlowerRenderer;
import com.hollingsworth.arsnouveau.common.armor.HeavyArmor;
import com.hollingsworth.arsnouveau.common.armor.LightArmor;
import com.hollingsworth.arsnouveau.common.armor.MediumArmor;
import com.hollingsworth.arsnouveau.common.block.tile.MageBlockTile;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.hollingsworth.arsnouveau.common.block.tile.PotionMelderTile;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.items.PotionFlask;
import com.hollingsworth.arsnouveau.common.util.CameraUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.EntityRenderersEvent.AddLayers;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.RegisterColorHandlersEvent.Block;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoArmorRenderer;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "ars_nouveau",
   bus = Bus.MOD
)
@OnlyIn(Dist.CLIENT)
public class ClientHandler {
   public static NamedGuiOverlay cameraOverlay = new NamedGuiOverlay(
      new ResourceLocation("ars_nouveau", "scry_camera"),
      (gui, pose, partialTick, width, height) -> {
         Minecraft mc = Minecraft.m_91087_();
         Level level = mc.f_91073_;
         BlockPos pos = mc.f_91075_.m_20183_();
         if (CameraUtil.isPlayerMountedOnCamera(mc.f_91074_)) {
            if (!mc.f_91066_.f_92063_ && level.m_7702_(pos) instanceof ICameraMountable be) {
               Font font = Minecraft.m_91087_().f_91062_;
               Options settings = Minecraft.m_91087_().f_91066_;
               Component lookAround = ClientForgeHandler.localize(
                  "ars_nouveau.camera.move",
                  settings.f_92085_.m_90863_(),
                  settings.f_92086_.m_90863_(),
                  settings.f_92087_.m_90863_(),
                  settings.f_92088_.m_90863_()
               );
               Component exit = ClientForgeHandler.localize("ars_nouveau.camera.exit", settings.f_92090_.m_90863_());
               font.m_92763_(pose, lookAround, 10.0F, (float)(mc.m_91268_().m_85446_() - 40), 16777215);
               font.m_92763_(pose, exit, 10.0F, (float)(mc.m_91268_().m_85446_() - 30), 16777215);
            }
         }
      }
   );

   @SubscribeEvent
   public static void registerRenderers(RegisterRenderers event) {
      event.registerBlockEntityRenderer((BlockEntityType)BlockRegistry.ARCANE_PEDESTAL_TILE.get(), ArcanePedestalRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.ENCHANTING_APP_TILE, EnchantingApparatusRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.SCRIBES_TABLE_TILE, ScribesRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.AGRONOMIC_SOURCELINK_TILE, AgronomicRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.PORTAL_TILE_TYPE, PortalTileRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BlockRegistry.SKYWEAVE_TILE.get(), SkyBlockRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.INTANGIBLE_AIR_TYPE, IntangibleAirRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.VOLCANIC_TILE, VolcanicRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.IMBUEMENT_TILE, ImbuementRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.POTION_MELDER_TYPE, PotionMelderRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.ALCHEMICAL_TILE, AlchemicalRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.VITALIC_TILE, VitalicRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.MYCELIAL_TILE, MycelialRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.RELAY_DEPOSIT_TILE, t -> new GenericRenderer(t, "source_deposit"));
      event.registerBlockEntityRenderer(BlockRegistry.RELAY_WARP_TILE, t -> new GenericRenderer(t, "source_warp"));
      event.registerBlockEntityRenderer(BlockRegistry.ARCANE_RELAY_TILE, t -> new GenericRenderer(t, "source_relay"));
      event.registerBlockEntityRenderer(BlockRegistry.RELAY_SPLITTER_TILE, t -> new GenericRenderer(t, "source_splitter"));
      event.registerBlockEntityRenderer(BlockRegistry.BASIC_SPELL_TURRET_TILE, BasicTurretRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BlockRegistry.ROTATING_TURRET_TILE.get(), RotatingTurretRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.ENCHANTED_SPELL_TURRET_TYPE, ReducerTurretRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.TIMER_SPELL_TURRET_TILE, TimerTurretRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.ARCHWOOD_CHEST_TILE, ArchwoodChestRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.RUNE_TILE, RuneRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.WHIRLISPRIG_TILE, WhirlisprigFlowerRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.ARCANE_CORE_TILE, ArcaneCoreRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.RELAY_COLLECTOR_TILE, t -> new GenericRenderer(t, "source_collector"));
      event.registerBlockEntityRenderer(BlockRegistry.SCRYERS_OCULUS_TILE, t -> new ScryerEyeRenderer(t, new ScryersEyeModel()));
      event.registerBlockEntityRenderer(BlockRegistry.ARMOR_TILE, AlterationTableRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.MOB_JAR_TILE, MobJarRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.MIRROR_WEAVE_TILE, MirrorweaveRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.GHOST_WEAVE_TILE, GhostweaveRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.FALSE_WEAVE_TILE, FalseweaveRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BlockRegistry.TEMPORARY_TILE.get(), MirrorweaveRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BlockRegistry.CRAFTING_LECTERN_TILE.get(), LecternRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BlockRegistry.ITEM_DETECTOR_TILE.get(), ItemDetectorRenderer::new);
      event.registerBlockEntityRenderer(BlockRegistry.REPOSITORY_TILE, RepositoryRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BlockRegistry.REDSTONE_RELAY_TILE.get(), RedstoneRelayRenderer::new);
      event.registerEntityRenderer(
         (EntityType)ModEntities.SPELL_PROJ.get(),
         renderManager -> new RenderSpell(renderManager, new ResourceLocation("ars_nouveau", "textures/entity/spell_proj.png"))
      );
      event.registerEntityRenderer(
         (EntityType)ModEntities.ENTITY_FOLLOW_PROJ.get(),
         renderManager -> new RenderBlank(renderManager, new ResourceLocation("ars_nouveau", "textures/entity/spell_proj.png"))
      );
      event.registerEntityRenderer((EntityType)ModEntities.SUMMON_SKELETON.get(), RenderSummonSkeleton::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_EVOKER_FANGS_ENTITY_TYPE.get(), EvokerFangsRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ALLY_VEX.get(), VexRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.STARBUNCLE_TYPE.get(), StarbuncleRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.WHIRLISPRIG_TYPE.get(), WhirlisprigRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_WIXIE_TYPE.get(), t -> new TextureVariantRenderer(t, new WixieModel()));
      event.registerEntityRenderer((EntityType)ModEntities.WILDEN_STALKER.get(), WildenStalkerRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.WILDEN_GUARDIAN.get(), WildenGuardianRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.WILDEN_HUNTER.get(), WildenHunterRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SUMMON_WOLF.get(), WolfRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SUMMON_HORSE.get(), HorseRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.LIGHTNING_ENTITY.get(), LightningBoltRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_FLYING_ITEM.get(), RenderFlyingItem::new);
      event.registerEntityRenderer(
         (EntityType)ModEntities.ENTITY_RITUAL.get(),
         renderManager -> new RenderRitualProjectile(renderManager, new ResourceLocation("ars_nouveau", "textures/entity/spell_proj.png"))
      );
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_SPELL_ARROW.get(), TippableArrowRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_WIXIE_TYPE.get(), t -> new TextureVariantRenderer(t, new WixieModel()));
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_DUMMY.get(), DummyRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_DRYGMY.get(), t -> new TextureVariantRenderer(t, new DrygmyModel()));
      event.registerEntityRenderer(
         (EntityType)ModEntities.ORBIT_SPELL.get(),
         renderManager -> new RenderRitualProjectile(renderManager, new ResourceLocation("ars_nouveau", "textures/entity/spell_proj.png"))
      );
      event.registerEntityRenderer((EntityType)ModEntities.WILDEN_BOSS.get(), WildenChimeraRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_CHIMERA_SPIKE.get(), ChimeraProjectileRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_FAMILIAR_STARBUNCLE.get(), t -> new GenericFamiliarRenderer(t, new FamiliarStarbyModel()));
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_FAMILIAR_DRYGMY.get(), t -> new GenericFamiliarRenderer(t, new DrygmyModel()));
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_FAMILIAR_SYLPH.get(), FamiliarWhirlisprigRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_FAMILIAR_WIXIE.get(), t -> new GenericFamiliarRenderer(t, new WixieModel()));
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_BOOKWYRM_TYPE.get(), BookwyrmRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.FAMILIAR_AMETHYST_GOLEM.get(), t -> new GenericFamiliarRenderer(t, new AmethystGolemModel()));
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_FAMILIAR_BOOKWYRM.get(), FamiliarBookwyrmRenderer::new);
      event.registerEntityRenderer(
         (EntityType)ModEntities.LINGER_SPELL.get(),
         renderManager -> new RenderBlank(renderManager, new ResourceLocation("ars_nouveau", "textures/entity/spell_proj.png"))
      );
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_CASCADING_WEALD.get(), v -> new WealdWalkerRenderer(v, "cascading_weald"));
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_BLAZING_WEALD.get(), v -> new WealdWalkerRenderer(v, "blazing_weald"));
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_FLOURISHING_WEALD.get(), v -> new WealdWalkerRenderer(v, "flourishing_weald"));
      event.registerEntityRenderer((EntityType)ModEntities.ENTITY_VEXING_WEALD.get(), v -> new WealdWalkerRenderer(v, "vexing_weald"));
      event.registerEntityRenderer((EntityType)ModEntities.AMETHYST_GOLEM.get(), AmethystGolemRenderer::new);
      event.registerEntityRenderer(
         (EntityType)ModEntities.SCRYER_CAMERA.get(),
         renderManager -> new RenderBlank(renderManager, new ResourceLocation("ars_nouveau", "textures/entity/spell_proj.png"))
      );
      event.registerEntityRenderer((EntityType)ModEntities.ENCHANTED_FALLING_BLOCK.get(), EnchantedFallingBlockRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENCHANTED_MAGE_BLOCK.get(), MageBlockRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENCHANTED_HEAD_BLOCK.get(), EnchantedSkullRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.GIFT_STARBY.get(), GiftStarbyRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ANIMATED_BLOCK.get(), AnimBlockRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ANIMATED_HEAD.get(), AnimSkullRenderer::new);
      event.registerEntityRenderer(
         (EntityType)ModEntities.WALL_SPELL.get(),
         renderManager -> new RenderBlank(renderManager, new ResourceLocation("ars_nouveau", "textures/entity/spell_proj.png"))
      );
      event.registerEntityRenderer((EntityType)ModEntities.LILY.get(), LilyRenderer::new);
   }

   @SubscribeEvent
   public static void registerOverlays(RegisterGuiOverlaysEvent event) {
      event.registerAboveAll("scry_camera", cameraOverlay.overlay());
      event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "tooltip", GuiEntityInfoHUD.OVERLAY);
      event.registerAboveAll("mana_hud", GuiManaHUD.OVERLAY);
      event.registerAboveAll("spell_hud", GuiSpellHUD.OVERLAY);
   }

   @SubscribeEvent
   public static void init(FMLClientSetupEvent evt) {
      evt.enqueueWork(
         () -> {
            ItemProperties.register(
               (Item)ItemsRegistry.ENCHANTERS_SHIELD.get(),
               new ResourceLocation("ars_nouveau", "blocking"),
               (item, resourceLocation, livingEntity, arg4) -> livingEntity != null && livingEntity.m_6117_() && livingEntity.m_21211_() == item ? 1.0F : 0.0F
            );
            ItemProperties.register(ItemsRegistry.DOWSING_ROD.get(), new ResourceLocation("ars_nouveau", "uses"), new ClampedItemPropertyFunction() {
               public float m_142187_(ItemStack pStack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
                  return switch (pStack.m_41773_()) {
                     case 1 -> 0.75F;
                     case 2 -> 0.5F;
                     case 3 -> 0.25F;
                     default -> 1.0F;
                  };
               }
            });
            ItemProperties.register(BlockRegistry.POTION_JAR.m_5456_(), new ResourceLocation("ars_nouveau", "amount"), (stack, level, entity, seed) -> {
               CompoundTag tag = stack.m_41783_();
               return tag != null ? (float)tag.m_128469_("BlockEntityTag").m_128451_("currentFill") / 10000.0F : 0.0F;
            });
            ItemProperties.register(BlockRegistry.SOURCE_JAR.m_5456_(), new ResourceLocation("ars_nouveau", "source"), (stack, level, entity, seed) -> {
               CompoundTag tag = stack.m_41783_();
               return tag != null ? (float)tag.m_128469_("BlockEntityTag").m_128451_("source") / 10000.0F : 0.0F;
            });
         }
      );
   }

   @SubscribeEvent
   public static void registerLayers(AddLayers addLayers) {
      GeoArmorRenderer.registerArmorRenderer(LightArmor.class, () -> new ArmorRenderer(new GenericModel("light_armor", "items/light_armor").withEmptyAnim()));
      GeoArmorRenderer.registerArmorRenderer(MediumArmor.class, () -> new ArmorRenderer(new GenericModel("medium_armor", "items/medium_armor").withEmptyAnim()));
      GeoArmorRenderer.registerArmorRenderer(HeavyArmor.class, () -> new ArmorRenderer(new GenericModel("heavy_armor", "items/heavy_armor").withEmptyAnim()));
   }

   @SubscribeEvent
   public static void initBlockColors(Block event) {
      event.register(
         (state, reader, pos, tIndex) -> reader != null && pos != null && reader.m_7702_(pos) instanceof PotionJarTile jarTile ? jarTile.getColor() : -1,
         new net.minecraft.world.level.block.Block[]{BlockRegistry.POTION_JAR}
      );
      event.register(
         (state, reader, pos, tIndex) -> reader != null && pos != null && reader.m_7702_(pos) instanceof PotionMelderTile melderTile
               ? melderTile.getColor()
               : -1,
         new net.minecraft.world.level.block.Block[]{BlockRegistry.POTION_MELDER}
      );
      event.register(
         (state, reader, pos, tIndex) -> reader != null && pos != null && reader.m_7702_(pos) instanceof MageBlockTile mageBlockTile
               ? mageBlockTile.color.getColor()
               : -1,
         new net.minecraft.world.level.block.Block[]{BlockRegistry.MAGE_BLOCK}
      );
   }

   @SubscribeEvent
   public static void initItemColors(net.minecraftforge.client.event.RegisterColorHandlersEvent.Item event) {
      event.register((stack, color) -> color > 0 ? -1 : colorFromFlask(stack), new ItemLike[]{ItemsRegistry.POTION_FLASK});
      event.register((stack, color) -> color > 0 ? -1 : colorFromFlask(stack), new ItemLike[]{ItemsRegistry.POTION_FLASK_EXTEND_TIME});
      event.register((stack, color) -> color > 0 ? -1 : colorFromFlask(stack), new ItemLike[]{ItemsRegistry.POTION_FLASK_AMPLIFY});
      event.register(
         (stack, color) -> color > 0 ? -1 : new ParticleColor(200, 0, 200).getColor(),
         new ItemLike[]{(ItemLike)ForgeRegistries.ITEMS.getValue(new ResourceLocation("ars_nouveau", "potion_melder"))}
      );
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.NOVICE_ROBES});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.NOVICE_BOOTS});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.NOVICE_HOOD});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.NOVICE_LEGGINGS});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.APPRENTICE_ROBES});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.APPRENTICE_BOOTS});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.APPRENTICE_HOOD});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.APPRENTICE_LEGGINGS});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.ARCHMAGE_ROBES});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.ARCHMAGE_BOOTS});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.ARCHMAGE_HOOD});
      event.register((stack, color) -> color > 0 ? -1 : colorFromArmor(stack), new ItemLike[]{ItemsRegistry.ARCHMAGE_LEGGINGS});
      event.getItemColors().m_92689_((stack, color) -> {
         if (color <= 0 && stack.m_41782_()) {
            CompoundTag blockTag = stack.m_41783_().m_128469_("BlockEntityTag");
            if (blockTag.m_128441_("potionData")) {
               PotionData data = PotionData.fromTag(blockTag.m_128469_("potionData"));
               return PotionUtils.m_43564_(data.fullEffects());
            } else {
               return -1;
            }
         } else {
            return -1;
         }
      }, new ItemLike[]{BlockRegistry.POTION_JAR});
   }

   public static int colorFromArmor(ItemStack stack) {
      return PerkUtil.getPerkHolder(stack) instanceof ArmorPerkHolder armorPerkHolder
         ? DyeColor.m_41057_(armorPerkHolder.getColor(), DyeColor.PURPLE).m_41071_()
         : DyeColor.PURPLE.m_41071_();
   }

   public static int colorFromFlask(ItemStack stack) {
      PotionFlask.FlaskData data = new PotionFlask.FlaskData(stack);
      return data.getPotion().getPotion() == Potions.f_43598_ ? -1 : PotionUtils.m_43575_(data.getPotion().asPotionStack());
   }
}
