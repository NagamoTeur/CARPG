package com.github.L_Ender.cataclysm;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.config.BiomeConfig;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.config.ConfigHolder;
import com.github.L_Ender.cataclysm.event.ServerEventHandler;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModJigsaw;
import com.github.L_Ender.cataclysm.init.ModMenu;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModRecipeSerializers;
import com.github.L_Ender.cataclysm.init.ModRecipeTypes;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModStructurePlacementType;
import com.github.L_Ender.cataclysm.init.ModStructureProcessor;
import com.github.L_Ender.cataclysm.init.ModStructures;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.github.L_Ender.cataclysm.init.Modfeatures;
import com.github.L_Ender.cataclysm.message.MessageArmorKey;
import com.github.L_Ender.cataclysm.message.MessageCMMultipart;
import com.github.L_Ender.cataclysm.message.MessageCharge;
import com.github.L_Ender.cataclysm.message.MessageHookFalling;
import com.github.L_Ender.cataclysm.message.MessageMiniinventory;
import com.github.L_Ender.cataclysm.message.MessageMusic;
import com.github.L_Ender.cataclysm.message.MessageParryFrame;
import com.github.L_Ender.cataclysm.message.MessageParticle;
import com.github.L_Ender.cataclysm.message.MessageRenderRush;
import com.github.L_Ender.cataclysm.message.MessageSwingArm;
import com.github.L_Ender.cataclysm.message.MessageTidalTentacle;
import com.github.L_Ender.cataclysm.message.MessageUpdateBossBar;
import com.github.L_Ender.cataclysm.message.MessageUpdateblockentity;
import com.github.L_Ender.cataclysm.world.CMMobSpawnBiomeModifier;
import com.github.L_Ender.cataclysm.world.CMMobSpawnStructureModifier;
import com.min01.archaeology.init.ArchaeologyBlockEntityType;
import com.min01.archaeology.init.ArchaeologyBlocks;
import com.min01.archaeology.init.ArchaeologyItems;
import com.min01.archaeology.init.ArchaeologyParticleTypes;
import com.min01.archaeology.init.ArchaeologyRecipeSerializer;
import com.min01.archaeology.init.ArchaeologySounds;
import com.min01.archaeology.init.ArchaeologyStructureProcessor;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.SlotTypeMessage.Builder;

@Mod("cataclysm")
@EventBusSubscriber(
   modid = "cataclysm"
)
public class Cataclysm {
   public static final String MODID = "cataclysm";
   public static final Logger LOGGER = LogManager.getLogger();
   public static final SimpleChannel NETWORK_WRAPPER;
   private static final String PROTOCOL_VERSION = Integer.toString(1);
   public static CommonProxy PROXY = (CommonProxy)DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
   private static int packetsRegistered;

   public Cataclysm() {
      IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
      bus.addListener(this::setup);
      bus.addListener(this::setupClient);
      bus.addListener(this::onModConfigEvent);
      bus.addListener(this::setupEntityModelLayers);
      bus.addListener(this::enqueueIMC);
      ModLoadingContext.get().registerConfig(Type.COMMON, ConfigHolder.COMMON_SPEC, "cataclysm.toml");
      ModItems.ITEMS.register(bus);
      ModEffect.EFFECTS.register(bus);
      ModBlocks.BLOCKS.register(bus);
      ModParticle.PARTICLE.register(bus);
      ModStructures.STRUCTURE_PIECE_DEF_REG.register(bus);
      ModStructures.STRUCTURE_TYPE_DEF_REG.register(bus);
      Modfeatures.FEATURES.register(bus);
      ModTileentites.TILE_ENTITY_TYPES.register(bus);
      ModEntities.ENTITY_TYPE.register(bus);
      ModSounds.SOUNDS.register(bus);
      ModRecipeSerializers.RECIPE_SERIALIZERS.register(bus);
      ModRecipeTypes.RECIPE_TYPES.register(bus);
      ModMenu.DEF_REG.register(bus);
      ModStructurePlacementType.STRUCTURE_PLACEMENT_TYPE.register(bus);
      ModStructureProcessor.STRUCTURE_PROCESSOR.register(bus);
      ArchaeologyItems.ITEMS.register(bus);
      ArchaeologySounds.SOUND_EVENTS.register(bus);
      ArchaeologyBlocks.BLOCKS.register(bus);
      ArchaeologyBlockEntityType.BLOCK_ENTITIES.register(bus);
      ArchaeologyRecipeSerializer.RECIPE_SERIALIZERS.register(bus);
      ArchaeologyStructureProcessor.STRUCTURE_PROCESSOR.register(bus);
      ArchaeologyParticleTypes.PARTICLES.register(bus);
      PROXY.init();
      MinecraftForge.EVENT_BUS.register(this);
      MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
      MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, ModCapabilities::attachEntityCapability);
      bus.addListener(ModCapabilities::registerCapabilities);
      DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(Keys.BIOME_MODIFIER_SERIALIZERS, "cataclysm");
      biomeModifiers.register(bus);
      biomeModifiers.register("cataclysm_mob_spawns", CMMobSpawnBiomeModifier::makeCodec);
      DeferredRegister<Codec<? extends StructureModifier>> structureModifiers = DeferredRegister.create(Keys.STRUCTURE_MODIFIER_SERIALIZERS, "cataclysm");
      structureModifiers.register(bus);
      structureModifiers.register("cataclysm_structure_spawns", CMMobSpawnStructureModifier::makeCodec);
   }

   public void enqueueIMC(InterModEnqueueEvent event) {
      SlotTypePreset[] types = new SlotTypePreset[]{SlotTypePreset.HEAD, SlotTypePreset.NECKLACE, SlotTypePreset.BELT};

      for (SlotTypePreset type : types) {
         InterModComms.sendTo("curios", "register_type", () -> type.getMessageBuilder().build());
      }

      InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).build());
      InterModComms.sendTo("curios", "register_type", () -> new Builder("feet").priority(220).icon(InventoryMenu.f_39696_).build());
   }

   private void setupEntityModelLayers(RegisterLayerDefinitions event) {
      CMModelLayers.register(event);
   }

   @SubscribeEvent
   public void onModConfigEvent(ModConfigEvent event) {
      ModConfig config = event.getConfig();
      if (config.getSpec() == ConfigHolder.COMMON_SPEC) {
         CMConfig.bake(config);
      }

      BiomeConfig.init();
   }

   public static <MSG> void sendMSGToServer(MSG message) {
      NETWORK_WRAPPER.sendToServer(message);
   }

   public static <MSG> void sendMSGToAll(MSG message) {
      for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().m_6846_().m_11314_()) {
         sendNonLocal(message, player);
      }
   }

   public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) {
      NETWORK_WRAPPER.sendTo(msg, player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
   }

   private void setupClient(FMLClientSetupEvent event) {
      PROXY.clientInit();
   }

   private void setup(FMLCommonSetupEvent event) {
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++, MessageCMMultipart.class, MessageCMMultipart::encode, MessageCMMultipart::new, MessageCMMultipart.Handler::onMessage
      );
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++,
         MessageUpdateblockentity.class,
         MessageUpdateblockentity::write,
         MessageUpdateblockentity::read,
         MessageUpdateblockentity.Handler::handle
      );
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++, MessageSwingArm.class, MessageSwingArm::write, MessageSwingArm::read, MessageSwingArm.Handler::handle
      );
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++, MessageHookFalling.class, MessageHookFalling::encode, MessageHookFalling::new, MessageHookFalling.Handler::onMessage
      );
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++, MessageTidalTentacle.class, MessageTidalTentacle::encode, MessageTidalTentacle::new, MessageTidalTentacle.Handler::onMessage
      );
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageCharge.class, MessageCharge::encode, MessageCharge::new, MessageCharge.Handler::onMessage);
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++, MessageUpdateBossBar.class, MessageUpdateBossBar::write, MessageUpdateBossBar::read, MessageUpdateBossBar::handle
      );
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageArmorKey.class, MessageArmorKey::write, MessageArmorKey::read, MessageArmorKey::handle);
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++, MessageParticle.class, MessageParticle::encode, MessageParticle::new, MessageParticle.Handler::onMessage
      );
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++, MessageRenderRush.class, MessageRenderRush::encode, MessageRenderRush::new, MessageRenderRush.Handler::onMessage
      );
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageMusic.class, MessageMusic::write, MessageMusic::read, MessageMusic.Handler::onMessage);
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++, MessageParryFrame.class, MessageParryFrame::encode, MessageParryFrame::new, MessageParryFrame.Handler::onMessage
      );
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++, MessageMiniinventory.class, MessageMiniinventory::write, MessageMiniinventory::read, MessageMiniinventory.Handler::handle
      );
      event.enqueueWork(ModItems::initDispenser);
      event.enqueueWork(ModJigsaw::registerJigsawElements);
   }

   static {
      ChannelBuilder channel = ChannelBuilder.named(new ResourceLocation("cataclysm", "main_channel"));
      String version = PROTOCOL_VERSION;
      channel = channel.clientAcceptedVersions(version::equals);
      version = PROTOCOL_VERSION;
      NETWORK_WRAPPER = channel.serverAcceptedVersions(version::equals).networkProtocolVersion(() -> Cataclysm.PROTOCOL_VERSION).simpleChannel();
   }
}
