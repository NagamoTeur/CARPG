package com.bobmowzie.mowziesmobs.client;

import com.bobmowzie.mowziesmobs.client.render.entity.FrozenRenderHandler;
import com.bobmowzie.mowziesmobs.client.sound.BlackPinkSound;
import com.bobmowzie.mowziesmobs.client.sound.IceBreathSound;
import com.bobmowzie.mowziesmobs.client.sound.NagaSwoopSound;
import com.bobmowzie.mowziesmobs.client.sound.SpawnBoulderChargeSound;
import com.bobmowzie.mowziesmobs.client.sound.SunblockSound;
import com.bobmowzie.mowziesmobs.client.sound.SunstrikeSound;
import com.bobmowzie.mowziesmobs.server.ServerProxy;
import com.bobmowzie.mowziesmobs.server.ability.AbilityClientEventHandler;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.entity.naga.EntityNaga;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends ServerProxy {
   private static final List<SunblockSound> sunblockSounds = new ArrayList<>();
   public static final Map<UUID, ResourceLocation> bossBarRegistryNames = new HashMap<>();
   private Entity referencedMob = null;

   @Override
   public void init(IEventBus modbus) {
      super.init(modbus);
      ModLoadingContext.get().registerConfig(Type.CLIENT, ConfigHandler.CLIENT_CONFIG);
      modbus.register(MMModels.class);
      MinecraftForge.EVENT_BUS.register(ClientEventHandler.INSTANCE);
      MinecraftForge.EVENT_BUS.register(FrozenRenderHandler.INSTANCE);
      MinecraftForge.EVENT_BUS.register(AbilityClientEventHandler.INSTANCE);
      FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientLayerRegistry::onAddLayers);
   }

   @Override
   public void onLateInit(IEventBus modbus) {
      ItemPropertyFunction pulling = ItemProperties.m_117829_(Items.f_42411_, new ResourceLocation("pulling"));
      ItemProperties.register(ItemHandler.BLOWGUN.m_5456_(), new ResourceLocation("pulling"), pulling);
   }

   @Override
   public void playSunstrikeSound(EntitySunstrike strike) {
      Minecraft.m_91087_().m_91106_().m_120367_(new SunstrikeSound(strike));
   }

   @Override
   public void playIceBreathSound(Entity entity) {
      Minecraft.m_91087_().m_91106_().m_120367_(new IceBreathSound(entity));
   }

   @Override
   public void playBoulderChargeSound(LivingEntity player) {
      Minecraft.m_91087_().m_91106_().m_120367_(new SpawnBoulderChargeSound(player));
   }

   @Override
   public void playNagaSwoopSound(EntityNaga naga) {
      Minecraft.m_91087_().m_91106_().m_120367_(new NagaSwoopSound(naga));
   }

   @Override
   public void playBlackPinkSound(AbstractMinecart entity) {
      Minecraft.m_91087_().m_91106_().m_120367_(new BlackPinkSound(entity));
   }

   @Override
   public void playSunblockSound(LivingEntity entity) {
      sunblockSounds.removeIf(AbstractTickableSoundInstance::m_7801_);
      if (sunblockSounds.size() < 10) {
         SunblockSound sunblockSound = new SunblockSound(entity);
         sunblockSounds.add(sunblockSound);

         try {
            Minecraft.m_91087_().m_91106_().m_120367_(sunblockSound);
         } catch (ConcurrentModificationException var4) {
         }
      }
   }

   @Override
   public void minecartParticles(ClientLevel world, AbstractMinecart minecart, float scale, double x, double y, double z, BlockState state, BlockPos pos) {
      int size = 3;
      float offset = -0.5F * scale;

      for (int ix = 0; ix < 3; ix++) {
         for (int iy = 0; iy < 3; iy++) {
            for (int iz = 0; iz < 3; iz++) {
               double dx = (double)ix / 3.0 * (double)scale;
               double dy = (double)iy / 3.0 * (double)scale;
               double dz = (double)iz / 3.0 * (double)scale;
               Vec3 minecartMotion = minecart.m_20184_();
               Minecraft.m_91087_()
                  .f_91061_
                  .m_107344_(
                     (new TerrainParticle(
                           world,
                           x + dx + (double)offset,
                           y + dy + (double)offset,
                           z + dz + (double)offset,
                           dx + minecartMotion.m_7096_(),
                           dy + minecartMotion.m_7098_(),
                           dz + minecartMotion.m_7094_(),
                           state
                        ) {
                        })
                        .updateSprite(state, pos)
                  );
            }
         }
      }
   }

   @Override
   public void setTPS(float tickRate) {
   }

   @Override
   public Entity getReferencedMob() {
      return this.referencedMob;
   }

   @Override
   public void setReferencedMob(Entity referencedMob) {
      this.referencedMob = referencedMob;
   }
}
