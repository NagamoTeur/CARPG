package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.items.EndAnchor;
import com.aizistral.enigmaticlegacy.objects.AnchorSearchResult;
import com.aizistral.enigmaticlegacy.registries.EnigmaticBlocks;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PlayerList.class})
public class MixinPlayerList {
   @Shadow
   @Final
   private MinecraftServer f_11195_;
   @Shadow
   @Final
   private List<ServerPlayer> f_11196_;
   @Shadow
   @Final
   private Map<UUID, ServerPlayer> f_11197_;

   @Inject(
      method = {"respawn"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void respawn(ServerPlayer player, boolean keep, CallbackInfoReturnable<ServerPlayer> info) {
      BlockPos pos = player.m_8961_();
      float angle = player.m_8962_();
      boolean forced = player.m_8964_();
      boolean override = false;
      boolean endRespawn = false;
      ServerLevel level = this.f_11195_.m_129880_(player.m_8963_());
      Optional<Vec3> optional = Optional.empty();
      if (level != null && pos != null) {
         AnchorSearchResult result = EndAnchor.findEndAnchor(level, pos, angle, forced, keep);
         if (result.found()) {
            optional = result.location();
            override = true;
            if (keep && level.m_46472_() == Level.f_46430_ && optional.isPresent()) {
               endRespawn = true;
               optional = Optional.empty();
            }
         }
      }

      if (override) {
         this.f_11196_.remove(player);
         player.m_9236_().m_143261_(player, RemovalReason.DISCARDED);
         PlayerList list = (PlayerList)this;
         ServerLevel newLevel = level != null && optional.isPresent() ? level : this.f_11195_.m_129783_();
         ServerPlayer newPlayer = new ServerPlayer(this.f_11195_, newLevel, player.m_36316_(), null);
         newPlayer.f_8906_ = player.f_8906_;
         newPlayer.m_9015_(player, keep);
         newPlayer.m_20234_(player.m_19879_());
         newPlayer.m_36163_(player.m_5737_());

         for (String tag : player.m_19880_()) {
            newPlayer.m_20049_(tag);
         }

         boolean usedAnchorCharge = false;
         if (optional.isPresent()) {
            BlockState respawnState = newLevel.m_8055_(pos);
            boolean isAnchor = respawnState.m_60713_(Blocks.f_50724_) || respawnState.m_60713_(EnigmaticBlocks.END_ANCHOR);
            Vec3 vec3 = optional.get();
            float f1;
            if (!respawnState.m_204336_(BlockTags.f_13038_) && !isAnchor) {
               f1 = angle;
            } else {
               Vec3 vec31 = Vec3.m_82539_(pos).m_82546_(vec3).m_82541_();
               f1 = (float)Mth.m_14175_(Mth.m_14136_(vec31.f_82481_, vec31.f_82479_) * 180.0F / (float)Math.PI - 90.0);
            }

            newPlayer.m_7678_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, f1, 0.0F);
            newPlayer.m_9158_(newLevel.m_46472_(), pos, angle, forced, false);
            usedAnchorCharge = !keep && isAnchor && EndAnchor.useEndAnchor(newLevel, pos, respawnState);
         } else if (pos != null && !endRespawn) {
            newPlayer.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132153_, 0.0F));
         } else if (pos != null && endRespawn) {
            newPlayer.m_9158_(Level.f_46430_, pos, angle, forced, false);
         }

         while (!newLevel.m_45786_(newPlayer) && newPlayer.m_20186_() < (double)newLevel.m_151558_()) {
            newPlayer.m_6034_(newPlayer.m_20185_(), newPlayer.m_20186_() + 1.0, newPlayer.m_20189_());
         }

         LevelData leveldata = newPlayer.f_19853_.m_6106_();
         newPlayer.f_8906_
            .m_9829_(
               new ClientboundRespawnPacket(
                  newPlayer.f_19853_.m_220362_(),
                  newPlayer.f_19853_.m_46472_(),
                  BiomeManager.m_47877_(newPlayer.m_9236_().m_7328_()),
                  newPlayer.f_8941_.m_9290_(),
                  newPlayer.f_8941_.m_9293_(),
                  newPlayer.m_9236_().m_46659_(),
                  newPlayer.m_9236_().m_8584_(),
                  keep,
                  newPlayer.m_219759_()
               )
            );
         newPlayer.f_8906_.m_9774_(newPlayer.m_20185_(), newPlayer.m_20186_(), newPlayer.m_20189_(), newPlayer.m_146908_(), newPlayer.m_146909_());
         newPlayer.f_8906_.m_9829_(new ClientboundSetDefaultSpawnPositionPacket(newLevel.m_220360_(), newLevel.m_220361_()));
         newPlayer.f_8906_.m_9829_(new ClientboundChangeDifficultyPacket(leveldata.m_5472_(), leveldata.m_5474_()));
         newPlayer.f_8906_.m_9829_(new ClientboundSetExperiencePacket(newPlayer.f_36080_, newPlayer.f_36079_, newPlayer.f_36078_));
         list.m_11229_(newPlayer, newLevel);
         list.m_11289_(newPlayer);
         newLevel.m_8845_(newPlayer);
         this.f_11196_.add(newPlayer);
         this.f_11197_.put(newPlayer.m_20148_(), newPlayer);
         newPlayer.m_143429_();
         newPlayer.m_21153_(newPlayer.m_21223_());
         ForgeEventFactory.firePlayerRespawnEvent(newPlayer, keep);
         if (usedAnchorCharge && pos != null) {
            newPlayer.f_8906_
               .m_9829_(
                  new ClientboundSoundPacket(
                     SoundEvents.f_12377_,
                     SoundSource.BLOCKS,
                     (double)pos.m_123341_(),
                     (double)pos.m_123342_(),
                     (double)pos.m_123343_(),
                     1.0F,
                     1.0F,
                     newLevel.m_213780_().m_188505_()
                  )
               );
         }

         info.setReturnValue(newPlayer);
      }
   }
}
