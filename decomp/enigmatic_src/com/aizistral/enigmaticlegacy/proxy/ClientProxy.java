package com.aizistral.enigmaticlegacy.proxy;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.client.QuoteHandler;
import com.aizistral.enigmaticlegacy.client.fx.PermanentItemPickupParticle;
import com.aizistral.enigmaticlegacy.client.renderers.EndAnchorRenderer;
import com.aizistral.enigmaticlegacy.client.renderers.EnigmaticElytraLayer;
import com.aizistral.enigmaticlegacy.client.renderers.PermanentItemRenderer;
import com.aizistral.enigmaticlegacy.client.renderers.UltimateWitherSkullRenderer;
import com.aizistral.enigmaticlegacy.gui.PermadeathScreen;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.objects.RevelationTomeToast;
import com.aizistral.enigmaticlegacy.objects.TransientPlayerData;
import com.aizistral.enigmaticlegacy.registries.EnigmaticEntities;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.enigmaticlegacy.registries.EnigmaticTiles;
import com.aizistral.etherium.client.ShieldAuraLayer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.AddLayers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy extends CommonProxy {
   private static final Random RANDOM = new Random();
   protected final Map<Player, TransientPlayerData> clientTransientPlayerData = new WeakHashMap<>();
   protected final List<ClientProxy.InfinitumCounterEntry> theInfinitumHoldTicks = new ArrayList<>();

   public ClientProxy() {
      IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
      modBus.addListener(this::onClientSetup);
      MinecraftForge.EVENT_BUS.register(QuoteHandler.INSTANCE);
   }

   @Override
   public void displayPermadeathScreen() {
      if (Minecraft.m_91087_().f_91073_ != null) {
         boolean local = Minecraft.m_91087_().m_91090_();
         Minecraft.m_91087_().f_91073_.m_7462_();
         if (local) {
            Minecraft.m_91087_().m_91320_(new GenericDirtMessageScreen(Component.m_237115_("menu.savingLevel")));
         } else {
            Minecraft.m_91087_().m_91399_();
         }
      }

      PermadeathScreen permadeath = new PermadeathScreen(
         new SelectWorldScreen(new TitleScreen()),
         Component.m_237115_("gui.enigmaticlegacy.permadeathTitle"),
         Component.m_237115_("message.enigmaticlegacy.permadeath")
      );
      PermadeathScreen.active = permadeath;
      Minecraft.m_91087_().m_91152_(permadeath);
   }

   @Override
   public void clearTransientData() {
      super.clearTransientData();
      this.clientTransientPlayerData.clear();
      this.theInfinitumHoldTicks.clear();
   }

   @Override
   public Map<Player, TransientPlayerData> getTransientPlayerData(boolean clientOnly) {
      return clientOnly ? this.clientTransientPlayerData : this.commonTransientPlayerData;
   }

   @Override
   public void displayReviveAnimation(int entityID, int reviveType) {
      Player player = this.getClientPlayer();
      Entity entity = player.f_19853_.m_6815_(entityID);
      if (entity != null) {
         Item item = (Item)(reviveType == 0 ? EnigmaticItems.COSMIC_SCROLL : EnigmaticItems.THE_CUBE);
         int i = 40;
         Minecraft.m_91087_().f_91061_.m_107332_(entity, ParticleTypes.f_123767_, 30);
         int amount = 50;

         for (int counter = 0; counter <= amount; counter++) {
            player.f_19853_
               .m_6493_(
                  ParticleTypes.f_123744_,
                  true,
                  entity.m_20185_() + (Math.random() - 0.5),
                  entity.m_20186_() + (double)(entity.m_20206_() / 2.0F) + (Math.random() - 0.5),
                  entity.m_20189_() + (Math.random() - 0.5),
                  (Math.random() - 0.5) * 0.2,
                  (Math.random() - 0.5) * 0.2,
                  (Math.random() - 0.5) * 0.2
               );
         }

         player.f_19853_.m_7785_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_12513_, entity.m_5720_(), 1.0F, 1.0F, false);
         if (entity == player) {
            ItemStack stack = SuperpositionHandler.getCurioStack(player, item);
            if (stack == null) {
               stack = new ItemStack(item, 1);
            }

            Minecraft.m_91087_().f_91063_.m_109113_(stack);
         }
      }
   }

   @Override
   public void handleItemPickup(int pickuper_id, int item_id) {
      try {
         Entity pickuper = Minecraft.m_91087_().f_91073_.m_6815_(pickuper_id);
         Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(item_id);
         Minecraft.m_91087_()
            .f_91061_
            .m_107344_(
               new PermanentItemPickupParticle(
                  Minecraft.m_91087_().m_91290_(), Minecraft.m_91087_().m_91269_(), Minecraft.m_91087_().f_91073_, pickuper, entity
               )
            );
         Minecraft.m_91087_()
            .f_91073_
            .m_7785_(
               pickuper.m_20185_(),
               pickuper.m_20186_(),
               pickuper.m_20189_(),
               SoundEvents.f_12019_,
               SoundSource.PLAYERS,
               0.2F,
               (RANDOM.nextFloat() - RANDOM.nextFloat()) * 1.4F + 2.0F,
               false
            );
      } catch (Throwable var5) {
         Exception log = new Exception("Unknown error when rendering permanent item pickup", var5);
         EnigmaticLegacy.LOGGER.catching(log);
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void addLayers(AddLayers evt) {
      this.addPlayerLayer(evt, "default");
      this.addPlayerLayer(evt, "slim");
      this.addEntityLayer(evt, EntityType.f_20529_);
   }

   @OnlyIn(Dist.CLIENT)
   private void addPlayerLayer(AddLayers evt, String skin) {
      EntityRenderer<? extends Player> renderer = evt.getSkin(skin);
      if (renderer instanceof LivingEntityRenderer livingRenderer) {
         livingRenderer.m_115326_(new EnigmaticElytraLayer(livingRenderer, evt.getEntityModels()));
         livingRenderer.m_115326_(new ShieldAuraLayer(livingRenderer, evt.getEntityModels()));
      }
   }

   @OnlyIn(Dist.CLIENT)
   private <T extends LivingEntity, M extends HumanoidModel<T>, R extends LivingEntityRenderer<T, M>> void addEntityLayer(
      AddLayers evt, EntityType<? extends T> entityType
   ) {
      R renderer = (R)evt.getRenderer(entityType);
      if (renderer != null) {
         renderer.m_115326_(new EnigmaticElytraLayer(renderer, evt.getEntityModels()));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void onClientSetup(FMLClientSetupEvent event) {
      ItemProperties.register(
         EnigmaticItems.INFERNAL_SHIELD,
         new ResourceLocation("blocking"),
         (stack, world, entity, seed) -> entity != null && entity.m_6117_() && entity.m_21211_() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(EnigmaticItems.THE_INFINITUM, new ResourceLocation("enigmaticlegacy", "the_infinitum_open"), (stack, world, entity, seed) -> {
         if (entity instanceof Player player) {
            for (ClientProxy.InfinitumCounterEntry entry : this.theInfinitumHoldTicks) {
               if (entry.getPlayer() == player && entry.getStack() == stack) {
                  return entry.animValue;
               }
            }

            ItemStack mainhand = player.m_21205_();
            ItemStack offhand = player.m_21206_();
            if ((mainhand == stack || offhand == stack) && SuperpositionHandler.isTheWorthyOne(player)) {
               this.theInfinitumHoldTicks.add(new ClientProxy.InfinitumCounterEntry(player, stack));
            }
         }

         return 0.0F;
      });
      BlockEntityRenderers.m_173590_(EnigmaticTiles.END_ANCHOR, EndAnchorRenderer::new);
   }

   @Override
   public void updateInfinitumCounters() {
      for (ClientProxy.InfinitumCounterEntry entry : new ArrayList<>(this.theInfinitumHoldTicks)) {
         if (entry.getPlayer() != null && entry.getStack() != null) {
            ItemStack stack = entry.getStack();
            Player player = entry.getPlayer();
            int holdTicks = entry.counter;
            ItemStack mainhand = player.m_21205_();
            ItemStack offhand = player.m_21206_();
            if ((mainhand == stack || offhand == stack) && SuperpositionHandler.isTheWorthyOne(player)) {
               if (entry.counter > 5) {
                  entry.animValue = 1.0F;
               } else if (entry.counter == 5) {
                  entry.animValue = 0.5F;
               } else {
                  entry.animValue = 0.0F;
               }

               entry.counter++;
            } else if (entry.counter <= 0) {
               this.theInfinitumHoldTicks.remove(entry);
            } else {
               if (entry.counter > 5) {
                  entry.counter = 5;
               }

               if (entry.counter > 1) {
                  entry.animValue = 1.0F;
               } else if (entry.counter == 1) {
                  entry.animValue = 0.5F;
               } else {
                  entry.animValue = 0.0F;
               }

               entry.counter--;
            }
         } else {
            this.theInfinitumHoldTicks.remove(entry);
         }
      }
   }

   @Override
   public void initEntityRendering() {
      EntityRenderers.m_174036_(
         EnigmaticEntities.PERMANENT_ITEM_ENTITY, renderManager -> new PermanentItemRenderer(renderManager, Minecraft.m_91087_().m_91291_())
      );
      EntityRenderers.m_174036_(EnigmaticEntities.ENIGMATIC_POTION, ThrownItemRenderer::new);
      EntityRenderers.m_174036_(EnigmaticEntities.ULTIMATE_WITHER_SKULL, UltimateWitherSkullRenderer::new);
   }

   @Override
   public void loadComplete(FMLLoadCompleteEvent event) {
      this.initAuxiliaryRender();
   }

   @Override
   public boolean isInVanillaDimension(Player player) {
      return player.f_19853_.m_46472_().equals(this.getOverworldKey())
         || player.f_19853_.m_46472_().equals(this.getNetherKey())
         || player.f_19853_.m_46472_().equals(this.getEndKey());
   }

   @Override
   public boolean isInDimension(Player player, ResourceKey<Level> world) {
      return player.f_19853_.m_46472_().equals(world);
   }

   @Override
   public Level getCentralWorld() {
      return Minecraft.m_91087_().f_91073_;
   }

   @Override
   public UseAnim getVisualBlockAction() {
      return UseAnim.BLOCK;
   }

   @Override
   public Player getPlayer(UUID playerID) {
      return Minecraft.m_91087_().f_91073_ != null ? Minecraft.m_91087_().f_91073_.m_46003_(playerID) : null;
   }

   @Override
   public void pushRevelationToast(ItemStack renderedStack, int xp, int knowledge) {
      ToastComponent gui = Minecraft.m_91087_().m_91300_();
      gui.m_94922_(new RevelationTomeToast(renderedStack, xp, knowledge));
   }

   @Override
   public void spawnBonemealParticles(Level world, BlockPos pos, int data) {
      if (data == 0) {
         data = 15;
      }

      BlockState blockstate = world.m_8055_(pos);
      if (!blockstate.m_60795_()) {
         double d0 = 0.5;
         double d1;
         if (blockstate.m_60713_(Blocks.f_49990_)) {
            data *= 3;
            d1 = 1.0;
            d0 = 3.0;
         } else if (blockstate.m_60804_(world, pos)) {
            pos = pos.m_7494_();
            data *= 3;
            d0 = 3.0;
            d1 = 1.0;
         } else {
            d1 = blockstate.m_60808_(world, pos).m_83297_(Axis.Y);
         }

         world.m_7106_(ParticleTypes.f_123748_, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, 0.0, 0.0, 0.0);

         for (int i = 0; i < data; i++) {
            double d2 = RANDOM.nextGaussian() * 0.02;
            double d3 = RANDOM.nextGaussian() * 0.02;
            double d4 = RANDOM.nextGaussian() * 0.02;
            double d5 = 0.5 - d0;
            double d6 = (double)pos.m_123341_() + d5 + RANDOM.nextDouble() * d0 * 2.0;
            double d7 = (double)pos.m_123342_() + RANDOM.nextDouble() * d1;
            double d8 = (double)pos.m_123343_() + d5 + RANDOM.nextDouble() * d0 * 2.0;
            world.m_7106_(ParticleTypes.f_123748_, d6, d7, d8, d2, d3, d4);
         }
      }
   }

   @Override
   public Player getClientPlayer() {
      return Minecraft.m_91087_().f_91074_;
   }

   @Override
   public String getClientUsername() {
      return Minecraft.m_91087_().m_91094_().m_92546_();
   }

   private static class InfinitumCounterEntry {
      private final WeakReference<ItemStack> stack;
      private final WeakReference<Player> player;
      private int counter = 0;
      private float animValue = 0.0F;

      public InfinitumCounterEntry(Player player, ItemStack stack) {
         this.player = new WeakReference<>(player);
         this.stack = new WeakReference<>(stack);
      }

      public Player getPlayer() {
         return this.player.get();
      }

      public ItemStack getStack() {
         return this.stack.get();
      }
   }
}
