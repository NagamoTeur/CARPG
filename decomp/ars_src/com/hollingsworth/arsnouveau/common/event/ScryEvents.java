package com.hollingsworth.arsnouveau.common.event;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.scrying.IScryer;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketGetPersistentData;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.PacketDistributor;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "ars_nouveau"
)
public class ScryEvents {
   @SubscribeEvent
   public static void playerLoginEvent(PlayerLoggedInEvent event) {
      if (!event.getEntity().f_19853_.f_46443_ && event.getEntity().m_21023_((MobEffect)ModPotions.SCRYING_EFFECT.get())) {
         CompoundTag tag = event.getEntity().getPersistentData().m_128469_("PlayerPersisted");
         Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new PacketGetPersistentData(tag));
      }
   }

   @SubscribeEvent
   public static void playerTickEvent(PlayerTickEvent event) {
      if (event.side == LogicalSide.CLIENT
         && event.phase == Phase.END
         && event.player.m_21124_((MobEffect)ModPotions.SCRYING_EFFECT.get()) != null
         && ClientInfo.ticksInGame % 30 == 0) {
         List<BlockPos> scryingPos = new ArrayList<>();
         CompoundTag tag = ClientInfo.persistentData;
         if (!tag.m_128441_("an_scryer")) {
            return;
         }

         IScryer scryer = ArsNouveauAPI.getInstance()
            .getScryer(new ResourceLocation(tag.m_128469_("an_scryer").m_128461_("id")))
            .fromTag(tag.m_128469_("an_scryer"));
         if (scryer == null) {
            return;
         }

         Player playerEntity = event.player;
         Level world = playerEntity.f_19853_;
         Vec3i scrySize = scryer.getScryingSize();

         for (BlockPos p : BlockPos.m_121925_(playerEntity.m_20183_(), scrySize.m_123341_(), scrySize.m_123342_(), scrySize.m_123343_())) {
            if (!world.m_151570_(p) && !world.m_8055_(p).m_60795_()) {
               if (scryingPos.size() >= scryer.getScryMax()) {
                  break;
               }

               if (scryer.shouldRevealBlock(world.m_8055_(p), p, playerEntity)) {
                  scryingPos.add(new BlockPos(p));
               }
            }
         }

         ClientInfo.scryingPositions = scryingPos;
      }
   }

   @SubscribeEvent
   public static void onRenderHighlights(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_WEATHER) {
         ClientLevel world = Minecraft.m_91087_().f_91073_;
         if (ClientInfo.highlightTicks > 0) {
            ClientInfo.highlightTicks--;

            for (ColorPos pos : ClientInfo.highlightPositions) {
               double xzOffset = 0.15;
               double xOffset = ParticleUtil.inRange(-xzOffset / 4.0, xzOffset / 4.0) + 0.5;
               double zOffset = ParticleUtil.inRange(-xzOffset / 4.0, xzOffset / 4.0) + 0.5;
               double centerX = pos.pos.f_82479_ + xOffset;
               double centerZ = pos.pos.f_82479_ + zOffset;
               double xSpeedOffset = 0.0;
               double ySpeedOffset = ParticleUtil.inRange(0.0, 0.03F);
               double zSpeedOffset = 0.0;
               xSpeedOffset = ParticleUtil.inRange(-0.01F, 0.01F);
               zSpeedOffset = ParticleUtil.inRange(-0.01F, 0.01F);
               Vec3 renderPos = pos.pos;
               ParticleColor color = pos.color;
               world.m_7106_(
                  GlowParticleData.createData(color, true),
                  renderPos.f_82479_,
                  renderPos.f_82480_ + ParticleUtil.inRange(-0.0, 0.1),
                  renderPos.f_82481_,
                  xSpeedOffset,
                  ySpeedOffset,
                  zSpeedOffset
               );
            }
         }
      }
   }

   @SubscribeEvent
   public static void renderScry(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_WEATHER) {
         ClientLevel world = Minecraft.m_91087_().f_91073_;
         Player playerEntity = Minecraft.m_91087_().f_91074_;
         if (playerEntity != null && playerEntity.m_21124_((MobEffect)ModPotions.SCRYING_EFFECT.get()) != null) {
            Vec3 vector3d = Minecraft.m_91087_().f_91063_.m_109153_().m_90583_();
            double yView = vector3d.m_7098_();
            if (!Minecraft.m_91087_().m_91104_()) {
               RandomSource rand = playerEntity.m_217043_();

               for (BlockPos p : ClientInfo.scryingPositions) {
                  ParticleColor color = new ParticleColor(rand.m_188503_(255), rand.m_188503_(255), rand.m_188503_(255));
                  BlockPos renderPos = new BlockPos(p);
                  if (Math.abs(yView - (double)p.m_123342_()) >= 30.0) {
                     renderPos = new BlockPos((double)p.m_123341_(), (double)p.m_123342_() > yView ? yView + 20.0 : yView - 20.0, (double)p.m_123343_());
                     color = new ParticleColor(rand.m_188503_(30), rand.m_188503_(255), rand.m_188503_(50));
                  }

                  if (Math.abs(yView - (double)p.m_123342_()) >= 60.0) {
                     renderPos = new BlockPos((double)p.m_123341_(), (double)p.m_123342_() > yView ? yView + 20.0 : yView - 20.0, (double)p.m_123343_());
                     color = new ParticleColor(rand.m_188503_(50), rand.m_188503_(50), rand.m_188503_(255));
                  }

                  world.m_7106_(
                     GlowParticleData.createData(color, true),
                     (double)renderPos.m_123341_() + 0.5 + ParticleUtil.inRange(-0.1, 0.1),
                     (double)renderPos.m_123342_() + 0.2 + ParticleUtil.inRange(-0.1, 0.1),
                     (double)renderPos.m_123343_() + 0.5 + ParticleUtil.inRange(-0.1, 0.1),
                     0.0,
                     0.03F,
                     0.0
                  );
               }
            }
         }
      }
   }
}
