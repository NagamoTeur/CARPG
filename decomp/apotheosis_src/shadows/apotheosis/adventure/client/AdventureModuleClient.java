package shadows.apotheosis.adventure.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.datafixers.util.Either;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ModelEvent.BakingCompleted;
import net.minecraftforge.client.event.ModelEvent.RegisterAdditional;
import net.minecraftforge.client.event.ModelEvent.RegisterGeometryLoaders;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.client.event.RenderTooltipEvent.GatherComponents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.commons.lang3.mutable.MutableInt;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.affix.AffixManager;
import shadows.apotheosis.adventure.affix.reforging.ReforgingScreen;
import shadows.apotheosis.adventure.affix.reforging.ReforgingTableTileRenderer;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingScreen;
import shadows.apotheosis.adventure.affix.socket.SocketHelper;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.affix.socket.gem.cutting.GemCuttingScreen;
import shadows.apotheosis.core.attributeslib.api.AddAttributeTooltipsEvent;
import shadows.apotheosis.core.attributeslib.api.GatherSkippedAttributeTooltipsEvent;

public class AdventureModuleClient {
   public static List<BossSpawnMessage.BossSpawnData> BOSS_SPAWNS = new ArrayList<>();

   public static void init() {
      MinecraftForge.EVENT_BUS.register(AdventureModuleClient.class);
      MenuScreens.m_96206_((MenuType)Apoth.Menus.REFORGING.get(), ReforgingScreen::new);
      MenuScreens.m_96206_((MenuType)Apoth.Menus.SALVAGE.get(), SalvagingScreen::new);
      MenuScreens.m_96206_((MenuType)Apoth.Menus.GEM_CUTTING.get(), GemCuttingScreen::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)Apoth.Tiles.REFORGING_TABLE.get(), k -> new ReforgingTableTileRenderer());
      MinecraftForge.EVENT_BUS.register(AdventureKeys.class);
   }

   public static void onBossSpawn(BlockPos pos, float[] color) {
      BOSS_SPAWNS.add(new BossSpawnMessage.BossSpawnData(pos, color, new MutableInt()));
      Minecraft.m_91087_()
         .m_91106_()
         .m_120367_(
            new SimpleSoundInstance(
               SoundEvents.f_11860_,
               SoundSource.HOSTILE,
               AdventureConfig.bossAnnounceVolume,
               1.25F,
               Minecraft.m_91087_().f_91074_.f_19796_,
               Minecraft.m_91087_().f_91074_.m_20183_()
            )
         );
   }

   @SubscribeEvent
   public static void render(RenderLevelStageEvent e) {
      if (e.getStage() == Stage.AFTER_TRIPWIRE_BLOCKS) {
         PoseStack stack = e.getPoseStack();
         BufferSource buf = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
         Player p = Minecraft.m_91087_().f_91074_;

         for (int i = 0; i < BOSS_SPAWNS.size(); i++) {
            BossSpawnMessage.BossSpawnData data = BOSS_SPAWNS.get(i);
            stack.m_85836_();
            float partials = e.getPartialTick();
            Vec3 vec = Minecraft.m_91087_().m_91288_().m_20299_(partials);
            stack.m_85837_(-vec.f_82479_, -vec.f_82480_, -vec.f_82481_);
            stack.m_85837_((double)data.pos().m_123341_(), (double)data.pos().m_123342_(), (double)data.pos().m_123343_());
            BeaconRenderer.m_112184_(stack, buf, BeaconRenderer.f_112102_, partials, 1.0F, p.f_19853_.m_46467_(), 0, 64, data.color(), 0.166F, 0.33F);
            stack.m_85849_();
         }

         buf.m_109911_();
      }
   }

   @SubscribeEvent
   public static void time(ClientTickEvent e) {
      if (e.phase == Phase.END) {
         for (int i = 0; i < BOSS_SPAWNS.size(); i++) {
            BossSpawnMessage.BossSpawnData data = BOSS_SPAWNS.get(i);
            if (data.ticks().getAndIncrement() > 400) {
               BOSS_SPAWNS.remove(i--);
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public static void tooltips(AddAttributeTooltipsEvent e) {
      ItemStack stack = e.getStack();
      ListIterator<Component> it = e.getAttributeTooltipIterator();
      int sockets = SocketHelper.getSockets(stack);
      if (sockets > 0) {
         it.add(Component.m_237113_("APOTH_REMOVE_MARKER"));
      }
   }

   @SubscribeEvent
   public static void ignoreSocketUUIDS(GatherSkippedAttributeTooltipsEvent e) {
      ItemStack stack = e.getStack();

      for (ItemStack gem : SocketHelper.getGems(stack)) {
         GemItem.getUUIDs(gem).forEach(e::skipUUID);
      }
   }

   @SubscribeEvent
   public static void comps(GatherComponents e) {
      int sockets = SocketHelper.getSockets(e.getItemStack());
      if (sockets != 0) {
         List<Either<FormattedText, TooltipComponent>> list = e.getTooltipElements();
         int rmvIdx = -1;

         for (int i = 0; i < list.size(); i++) {
            Optional<FormattedText> o = list.get(i).left();
            if (o.isPresent()
               && o.get() instanceof Component comp
               && comp.m_214077_() instanceof LiteralContents tc
               && "APOTH_REMOVE_MARKER".equals(tc.f_237368_())) {
               rmvIdx = i;
               list.remove(i);
               break;
            }
         }

         if (rmvIdx != -1) {
            e.getTooltipElements()
               .add(rmvIdx, Either.right(new SocketTooltipRenderer.SocketComponent(e.getItemStack(), SocketHelper.getGems(e.getItemStack()))));
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public static void affixTooltips(ItemTooltipEvent e) {
      ItemStack stack = e.getItemStack();
      if (stack.m_41782_()) {
         Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(stack);
         List<Component> components = new ArrayList<>();
         Consumer<Component> dotPrefixer = afxComp -> components.add(
               Component.m_237110_("text.apotheosis.dot_prefix", new Object[]{afxComp}).m_130940_(ChatFormatting.YELLOW)
            );
         affixes.values().stream().sorted(Comparator.comparingInt(a -> a.affix().getType().ordinal())).forEach(inst -> inst.addInformation(dotPrefixer));
         e.getToolTip().addAll(1, components);
      }
   }

   public static RenderType gray(ResourceLocation texture) {
      return AdventureModuleClient.CustomRenderTypes.GRAY.apply(texture);
   }

   public static void checkAffixLangKeys() {
      StringBuilder sb = new StringBuilder("Missing Affix Lang Keys:\n");
      boolean any = false;
      String json = "\"%s\": \"\",";

      for (Affix a : AffixManager.INSTANCE.getValues()) {
         if (!I18n.m_118936_("affix." + a.getId())) {
            sb.append(json.formatted("affix." + a.getId()) + "\n");
            any = true;
         }

         if (!I18n.m_118936_("affix." + a.getId() + ".suffix")) {
            sb.append(json.formatted("affix." + a.getId() + ".suffix") + "\n");
            any = true;
         }
      }

      if (any) {
         AdventureModule.LOGGER.error(sb.toString());
      }
   }

   private static class CustomRenderTypes extends RenderType {
      private static ShaderInstance grayShader;
      private static final ShaderStateShard RENDER_TYPE_GRAY = new ShaderStateShard(() -> grayShader);
      public static Function<ResourceLocation, RenderType> GRAY = Util.m_143827_(AdventureModuleClient.CustomRenderTypes::gray);

      private static RenderType gray(ResourceLocation loc) {
         CompositeState rendertype$state = CompositeState.m_110628_()
            .m_173292_(RENDER_TYPE_GRAY)
            .m_173290_(new TextureStateShard(loc, false, false))
            .m_110685_(f_110139_)
            .m_110675_(f_110129_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110687_(RenderStateShard.f_110114_)
            .m_110691_(true);
         return m_173215_("gray", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, false, rendertype$state);
      }

      private CustomRenderTypes(String s, VertexFormat v, Mode m, int i, boolean b, boolean b2, Runnable r, Runnable r2) {
         super(s, v, m, i, b, b2, r, r2);
         throw new IllegalStateException("This class is not meant to be constructed!");
      }
   }

   @EventBusSubscriber(
      modid = "apotheosis",
      value = {Dist.CLIENT},
      bus = Bus.MOD
   )
   public static class ModBusSub {
      @SubscribeEvent
      public static void models(RegisterAdditional e) {
         e.register(new ResourceLocation("apotheosis", "item/hammer"));
      }

      @SubscribeEvent
      public static void onRegisterGeometryLoaders(RegisterGeometryLoaders e) {
         e.register("item_layers", FItemLayerModel.Loader.INSTANCE);
      }

      @SubscribeEvent
      public static void tooltipComps(RegisterClientTooltipComponentFactoriesEvent e) {
         e.register(SocketTooltipRenderer.SocketComponent.class, SocketTooltipRenderer::new);
      }

      @SubscribeEvent
      public static void addGemModels(RegisterAdditional e) {
         for (ResourceLocation s : Minecraft.m_91087_()
            .m_91098_()
            .m_214159_("models", loc -> "apotheosis".equals(loc.m_135827_()) && loc.m_135815_().contains("/gems/") && loc.m_135815_().endsWith(".json"))
            .keySet()) {
            String path = s.m_135815_().substring("models/".length(), s.m_135815_().length() - ".json".length());
            e.register(new ResourceLocation("apotheosis", path));
         }
      }

      @SubscribeEvent
      public static void replaceGemModel(BakingCompleted e) {
         ModelResourceLocation key = new ModelResourceLocation(Apotheosis.loc("gem"), "inventory");
         BakedModel oldModel = (BakedModel)e.getModels().get(key);
         if (oldModel != null) {
            e.getModels().put(key, new GemModel(oldModel, e.getModelBakery()));
         }
      }

      @SubscribeEvent
      public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
         event.registerShader(
            new ShaderInstance(event.getResourceManager(), new ResourceLocation("apotheosis:gray"), DefaultVertexFormat.f_85812_),
            shaderInstance -> AdventureModuleClient.CustomRenderTypes.grayShader = shaderInstance
         );
      }

      @SubscribeEvent
      public static void keys(RegisterKeyMappingsEvent e) {
         e.register(AdventureKeys.TOGGLE_RADIAL);
      }
   }
}
