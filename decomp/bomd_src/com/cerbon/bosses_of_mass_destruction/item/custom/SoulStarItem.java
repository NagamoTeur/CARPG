package com.cerbon.bosses_of_mass_destruction.item.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.random.ModRandom;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.block.custom.ChiseledStoneAltarBlock;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichEntity;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.CompoundTagEntityProvider;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.HorizontalRangedSpawnPosition;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.MobEntitySpawnPredicate;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.MobPlacementLogic;
import com.cerbon.bosses_of_mass_destruction.entity.spawn.SimpleMobSpawner;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.structure.BMDStructures;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.Arrays;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulStarItem extends Item {
   public SoulStarItem(Properties properties) {
      super(properties);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
      tooltipComponents.add(Component.m_237115_("item.bosses_of_mass_destruction.soul_star.tooltip").m_130940_(ChatFormatting.DARK_GRAY));
   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      Level level = context.m_43725_();
      BlockPos blockPos = context.m_8083_();
      BlockState blockState = level.m_8055_(blockPos);
      if (!blockState.m_60713_((Block)BMDBlocks.CHISELED_STONE_ALTAR.get()) || (Boolean)blockState.m_61143_(ChiseledStoneAltarBlock.lit)) {
         return InteractionResult.PASS;
      } else if (level.m_5776_()) {
         this.clientSoulStartPlace(blockPos);
         return InteractionResult.SUCCESS;
      } else {
         this.serverSoulStarPlace(blockState, level, blockPos, context);
         level.m_6263_(
            null,
            context.m_43720_().f_82479_,
            context.m_43720_().f_82480_,
            context.m_43720_().f_82481_,
            (SoundEvent)BMDSounds.SOUL_STAR.get(),
            SoundSource.NEUTRAL,
            0.5F,
            BMDUtils.randomPitch(level.f_46441_)
         );
         return InteractionResult.PASS;
      }
   }

   private void serverSoulStarPlace(BlockState blockState, Level level, BlockPos blockPos, UseOnContext context) {
      BlockState blockState2 = (BlockState)blockState.m_61124_(ChiseledStoneAltarBlock.lit, true);
      level.m_7731_(blockPos, blockState2, 2);
      context.m_43722_().m_41774_(1);
      List<BlockPos> quarterAltarPosition = List.of(new BlockPos(12, 0, 0), new BlockPos(6, 0, 6));
      List<BlockPos> allPotentialAltarPositions = Arrays.stream(Rotation.values())
         .flatMap(rot -> quarterAltarPosition.stream().map(blockPos1 -> blockPos1.m_7954_(rot)))
         .toList();
      int numberOfAltarsFilled = (int)allPotentialAltarPositions.stream().filter(pos -> {
         BlockState state = level.m_8055_(blockPos.m_121955_(pos));
         return state.m_61138_(ChiseledStoneAltarBlock.lit) && (Boolean)state.m_61143_(ChiseledStoneAltarBlock.lit);
      }).count();
      if (numberOfAltarsFilled == 3) {
         EventScheduler eventScheduler = BMDCapabilities.getLevelEventScheduler(level);
         eventScheduler.addEvent(new TimedEvent(() -> {
            allPotentialAltarPositions.forEach(blockPos1 -> {
               if (level.m_8055_(blockPos.m_121955_(blockPos1)).m_61138_(ChiseledStoneAltarBlock.lit)) {
                  level.m_46961_(blockPos.m_121955_(blockPos1), false);
               }
            });
            level.m_46961_(blockPos, false);
            this.spawnLich(blockPos, level);
         }, 20));
      }
   }

   @OnlyIn(Dist.CLIENT)
   private void clientSoulStartPlace(BlockPos blockPos) {
      Vec3 centralPos = VecUtils.asVec3(blockPos).m_82549_(new Vec3(0.5, 1.2, 0.5));
      MathUtils.circleCallback(0.5, 15, VecUtils.yAxis, vec3 -> {
         Vec3 particleVel = VecUtils.yAxis.m_82490_(0.03 + RandomUtils.randomDouble(0.01));
         Vec3 particlePos = centralPos.m_82549_(vec3);
         ChiseledStoneAltarBlock.Particles.blueFireParticleFactory.build(particlePos, particleVel);
      });
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
      ItemStack itemStack = player.m_21120_(usedHand);
      BlockHitResult hitResult = m_41435_(level, player, Fluid.NONE);
      if (hitResult.m_6662_() == Type.BLOCK && level.m_8055_(hitResult.m_82425_()).m_60713_((Block)BMDBlocks.CHISELED_STONE_ALTAR.get())) {
         return InteractionResultHolder.m_19098_(itemStack);
      } else {
         player.m_6672_(usedHand);
         if (level instanceof ServerLevel serverLevel) {
            BlockPos blockPos = serverLevel.m_215011_(BMDStructures.SOUL_STAR_STRUCTURE_KEY, player.m_20183_(), 100, false);
            if (blockPos != null) {
               SoulStarEntity entity = new SoulStarEntity(level, player.m_20185_(), player.m_20188_(), player.m_20189_());
               entity.setItem(itemStack);
               entity.initTargetPos(blockPos);
               level.m_7967_(entity);
               level.m_6263_(
                  null,
                  player.m_20185_(),
                  player.m_20186_(),
                  player.m_20189_(),
                  SoundEvents.f_11898_,
                  SoundSource.NEUTRAL,
                  0.5F,
                  0.4F / level.m_213780_().m_188501_() * 0.4F + 0.8F
               );
               if (!player.m_150110_().f_35937_) {
                  itemStack.m_41774_(1);
               }

               player.m_36246_(Stats.f_12982_.m_12902_(this));
               player.m_21011_(usedHand, true);
               return InteractionResultHolder.m_19090_(itemStack);
            } else {
               return InteractionResultHolder.m_19098_(itemStack);
            }
         } else {
            return InteractionResultHolder.m_19096_(itemStack);
         }
      }
   }

   public void spawnLich(BlockPos blockPos, Level level) {
      CompoundTag compoundTag = new CompoundTag();
      compoundTag.m_128359_("id", new ResourceLocation("bosses_of_mass_destruction", "lich").toString());
      Vec3 spawnPos = VecUtils.asVec3(blockPos);
      boolean spawned = new MobPlacementLogic(
            new HorizontalRangedSpawnPosition(spawnPos, 15.0, 30.0, new ModRandom()),
            new CompoundTagEntityProvider(compoundTag, level),
            new MobEntitySpawnPredicate(level),
            new SimpleMobSpawner((ServerLevel)level)
         )
         .tryPlacement(200);
      if (!spawned) {
         LichEntity entity = (LichEntity)((EntityType)BMDEntities.LICH.get()).m_20615_(level);
         if (entity != null) {
            Vec3 defaultSpawnPos = spawnPos.m_82549_(VecUtils.xAxis.m_82490_(5.0));
            entity.m_217006_(defaultSpawnPos.f_82479_, defaultSpawnPos.f_82480_, defaultSpawnPos.f_82481_);
            entity.m_20248_(defaultSpawnPos.f_82479_, defaultSpawnPos.f_82480_, defaultSpawnPos.f_82481_);
            level.m_7967_(entity);
         }
      }
   }
}
