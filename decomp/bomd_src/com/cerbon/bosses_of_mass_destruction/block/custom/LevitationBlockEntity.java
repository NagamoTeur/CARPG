package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlockEntities;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.capability.ChunkBlockCache;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.config.BMDConfig;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.util.AnimationUtils;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LevitationBlockEntity extends ChunkCacheBlockEntity implements IAnimatable, IAnimationTickable {
   private static final double tableOfElevationRadius = (double)((BMDConfig)AutoConfig.getConfigHolder(BMDConfig.class).getConfig()).generalConfig.tableOfElevationRadius;
   private AnimationFactory animationFactory;
   public int animationAge = 0;
   private static final HashSet<ServerPlayer> flight = new HashSet<>();

   public LevitationBlockEntity(BlockPos pos, BlockState blockState) {
      super((Block)BMDBlocks.LEVITATION_BLOCK.get(), (BlockEntityType<?>)BMDBlockEntities.LEVITATION_BLOCK_ENTITY.get(), pos, blockState);
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController(this, "idle", 0.0F, AnimationUtils.createIdlePredicate("rotate")));
   }

   public AnimationFactory getFactory() {
      if (this.animationFactory == null) {
         this.animationFactory = new AnimationFactory(this);
      }

      return this.animationFactory;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, LevitationBlockEntity entity) {
      ChunkCacheBlockEntity.tick(level, pos, state, entity);
      if (level.f_46443_) {
         entity.animationAge++;
         AABB box = getAffectingBox(level, VecUtils.asVec3(pos));

         for (Player player : level.m_45976_(Player.class, box)) {
            for (double x : List.of(box.f_82288_, box.f_82291_)) {
               double zRand = box.m_82399_().f_82481_ + box.m_82385_() * RandomUtils.randomDouble(0.5);
               LevitationBlockEntity.Particles.particlesFactory.build(randYPos(x, player, zRand), Vec3.f_82478_);
            }

            for (double z : List.of(box.f_82290_, box.f_82293_)) {
               double xRand = box.m_82399_().f_82479_ + box.m_82362_() * RandomUtils.randomDouble(0.5);
               LevitationBlockEntity.Particles.particlesFactory.build(randYPos(xRand, player, z), Vec3.f_82478_);
            }
         }
      }
   }

   private static Vec3 randYPos(double x, Player player, double z) {
      return new Vec3(x, player.m_20186_() + RandomUtils.randomDouble(0.5) + 1.0, z);
   }

   public static void tickFlight(ServerPlayer player) {
      List<BlockPos> blockToCheck = new ArrayList<>();

      for (int x = -1; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            blockToCheck.add(new BlockPos(x * (int)tableOfElevationRadius, 0, z * (int)tableOfElevationRadius));
         }
      }

      Set<ChunkPos> chunksToCheck = blockToCheck.stream().map(pos -> new ChunkPos(pos.m_121955_(player.m_20183_()))).collect(Collectors.toSet());
      boolean hasLevitationBlock = chunksToCheck.stream().anyMatch(chunkPos -> {
         Optional<ChunkBlockCache> blockCache = BMDCapabilities.getChunkBlockCache(player.f_19853_);
         if (blockCache.isPresent()) {
            List<BlockPos> blocks = blockCache.get().getBlocksFromChunk(chunkPos, (Block)BMDBlocks.LEVITATION_BLOCK.get());
            return blocks.stream().anyMatch(pos -> getAffectingBox(player.f_19853_, VecUtils.asVec3(pos)).m_82390_(player.m_20182_()));
         } else {
            return false;
         }
      });
      if (!hasLevitationBlock) {
         if (flight.contains(player)) {
            if (!player.m_7500_() && !player.m_5833_()) {
               player.m_150110_().f_35936_ = false;
               player.m_150110_().f_35935_ = false;
               player.f_8906_.m_9829_(new ClientboundPlayerAbilitiesPacket(player.m_150110_()));
            }

            flight.remove(player);
         }
      } else if (!flight.contains(player)) {
         flight.add(player);
         if (!player.m_150110_().f_35936_) {
            player.m_150110_().f_35936_ = true;
            player.f_8906_.m_9829_(new ClientboundPlayerAbilitiesPacket(player.m_150110_()));
         }
      }
   }

   private static AABB getAffectingBox(Level level, Vec3 pos) {
      return new AABB(pos.f_82479_, (double)level.m_141937_(), pos.f_82481_, pos.f_82479_ + 1.0, (double)level.m_141928_(), pos.f_82481_ + 1.0)
         .m_82377_(tableOfElevationRadius, 0.0, tableOfElevationRadius);
   }

   public void tick() {
   }

   public int tickTimer() {
      return this.animationAge;
   }

   private static class Particles {
      private static final ClientParticleBuilder particlesFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.LINE.get())
         .color(BMDColors.COMET_BLUE)
         .brightness(15728880)
         .colorVariation(0.5)
         .scale(0.075F);
   }
}
