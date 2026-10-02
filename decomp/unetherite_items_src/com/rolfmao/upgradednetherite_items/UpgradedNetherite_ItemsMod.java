package com.rolfmao.upgradednetherite_items;

import com.mojang.logging.LogUtils;
import com.rolfmao.upgradednetherite_items.client.TotemRenderer;
import com.rolfmao.upgradednetherite_items.config.ConfigHolder;
import com.rolfmao.upgradednetherite_items.handlers.ItemEventHandler;
import com.rolfmao.upgradednetherite_items.handlers.TotemEventHandler;
import com.rolfmao.upgradednetherite_items.init.CuriosInit;
import com.rolfmao.upgradednetherite_items.init.ModItems;
import com.rolfmao.upgradednetherite_items.init.UpgradedNetheriteEffects;
import com.rolfmao.upgradednetherite_items.packets.NetheriteTotemEffectPacket;
import com.rolfmao.upgradednetherite_items.packets.PacketResetEnderUpgradedNetheritePearl;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod("upgradednetherite_items")
public class UpgradedNetherite_ItemsMod {
   public static final Logger LOGGER = LogUtils.getLogger();
   public static final String MOD_ID = "upgradednetherite_items";
   public static SimpleChannel packetInstance;
   public static UpgradedNetherite_ItemsMod INSTANCE;

   public UpgradedNetherite_ItemsMod() {
      INSTANCE = this;
      FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
      FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
      UpgradedNetheriteEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
      ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
      MinecraftForge.EVENT_BUS.register(this);
      MinecraftForge.EVENT_BUS.register(new ItemEventHandler());
      MinecraftForge.EVENT_BUS.register(new TotemEventHandler());
      MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, CuriosInit::attachCaps);
      if (ModList.get() != null && ModList.get().getModContainerById("curios").isPresent()) {
         InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.CHARM.getMessageBuilder().build());
      }

      ModLoadingContext.get().registerConfig(Type.CLIENT, ConfigHolder.CLIENT_SPEC);
      ModLoadingContext.get().registerConfig(Type.SERVER, ConfigHolder.SERVER_SPEC);
   }

   private void setup(FMLCommonSetupEvent event) {
      packetInstance = ChannelBuilder.named(new ResourceLocation("upgradednetherite_items", "main"))
         .networkProtocolVersion(() -> "1")
         .clientAcceptedVersions("1"::equals)
         .serverAcceptedVersions("1"::equals)
         .simpleChannel();
      packetInstance.registerMessage(
         1,
         PacketResetEnderUpgradedNetheritePearl.class,
         PacketResetEnderUpgradedNetheritePearl::encode,
         PacketResetEnderUpgradedNetheritePearl::decode,
         PacketResetEnderUpgradedNetheritePearl::handle
      );
      packetInstance.registerMessage(
         2, NetheriteTotemEffectPacket.class, NetheriteTotemEffectPacket::encode, NetheriteTotemEffectPacket::new, NetheriteTotemEffectPacket::handle
      );
   }

   private void doClientStuff(FMLClientSetupEvent event) {
      if (ModList.get() != null && ModList.get().getModContainerById("curios").isPresent()) {
         CuriosRendererRegistry.register((Item)ModItems.NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.GOLD_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.FIRE_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.ENDER_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.WATER_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.WITHER_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.POISON_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.PHANTOM_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.FEATHER_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.CORRUPT_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.ECHO_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.ULTIMATE_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
         CuriosRendererRegistry.register((Item)ModItems.CREATIVE_UPGRADED_NETHERITE_TOTEM.get(), TotemRenderer::new);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void playActivateAnimation(ItemStack stack, Entity entity) {
      Minecraft mc = Minecraft.m_91087_();
      mc.f_91061_.m_107332_(entity, ParticleTypes.f_123767_, 30);

      assert mc.f_91073_ != null;

      mc.f_91073_.m_7785_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_12513_, entity.m_5720_(), 1.0F, 1.0F, false);
      if (entity == mc.f_91074_) {
         mc.f_91063_.m_109113_(stack);
      }
   }
}
