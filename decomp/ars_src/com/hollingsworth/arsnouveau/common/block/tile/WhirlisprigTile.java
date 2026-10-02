package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.DropDistribution;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.EntityFollowProjectile;
import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.Config;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class WhirlisprigTile extends SummoningTile implements IAnimatable {
   public static TagKey<Block> KINDA_LIKES = BlockTags.create(new ResourceLocation("ars_nouveau", "whirlisprig/kinda_likes"));
   public static TagKey<Block> GREATLY_LIKES = BlockTags.create(new ResourceLocation("ars_nouveau", "whirlisprig/greatly_likes"));
   public static TagKey<Item> DENIED_DROP = ItemTags.create(new ResourceLocation("ars_nouveau", "whirlisprig/denied_drop"));
   public List<ItemStack> ignoreItems = new ArrayList<>();
   public int ticksToNextEval;
   public int moodScore;
   public int diversityScore;
   public int progress;
   public Map<BlockState, Integer> genTable = new HashMap<>();
   public Map<BlockState, Integer> scoreMap = new HashMap<>();
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public WhirlisprigTile(BlockPos pPos, BlockState pState) {
      super(BlockRegistry.WHIRLISPRIG_TILE, pPos, pState);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.f_58857_.f_46443_) {
         for (int i = 0; i < this.progress / 20; i++) {
            this.f_58857_
               .m_7106_(
                  GlowParticleData.createData(new ParticleColor(50, 255, 20)),
                  (double)this.m_58899_().m_123341_() + 0.5 + ParticleUtil.inRange(-0.1, 0.1),
                  (double)this.m_58899_().m_123342_() + 1.3 + ParticleUtil.inRange(-0.1, 0.1),
                  (double)this.m_58899_().m_123343_() + 0.5 + ParticleUtil.inRange(-0.1, 0.1),
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      if (!this.f_58857_.f_46443_) {
         if (this.ticksToNextEval > 0) {
            this.ticksToNextEval--;
         }

         if (this.ticksToNextEval <= 0) {
            this.evaluateGrove();
         }

         if (this.f_58857_.m_46467_() % 60L == 0L
            && this.progress >= (Integer)Config.WHIRLISPRIG_MAX_PROGRESS.get()
            && SourceUtil.takeSourceWithParticles(this.f_58858_, this.f_58857_, 5, (Integer)Config.SYLPH_MANA_COST.get()) != null) {
            this.progress = 0;
            DropDistribution<BlockState> blockDropDistribution = new DropDistribution<>(this.genTable);
            int numDrops = this.getDropsByDiversity() + 3;

            for (int i = 0; i < numDrops; i++) {
               BlockState block = blockDropDistribution.nextDrop();
               if (block == null) {
                  return;
               }

               for (ItemStack s : this.getDrops(blockDropDistribution)) {
                  BlockUtil.insertItemAdjacent(this.f_58857_, this.f_58858_, s);
               }
            }

            this.updateBlock();
         }
      }
   }

   public boolean isValidReward(ItemStack stack) {
      if (stack.m_204117_(DENIED_DROP)) {
         return false;
      } else {
         return this.ignoreItems != null && !this.ignoreItems.isEmpty() ? this.ignoreItems.stream().noneMatch(i -> i.m_41656_(stack)) : true;
      }
   }

   public int getDropsByDiversity() {
      return this.diversityScore / 2;
   }

   public List<ItemStack> getDrops(DropDistribution<BlockState> blockDropDistribution) {
      Supplier<List<ItemStack>> getDrops = () -> Block.m_49869_(blockDropDistribution.nextDrop(), (ServerLevel)this.f_58857_, this.f_58858_, null);
      boolean bonusReroll = false;

      for (int numRerolls = 0; numRerolls < (bonusReroll ? 16 : 8); numRerolls++) {
         List<ItemStack> drops = getDrops.get();
         if (!drops.isEmpty()) {
            List<ItemStack> successfulDrops = drops.stream().filter(this::isValidReward).collect(Collectors.toCollection(ArrayList::new));
            bonusReroll = true;
            if (!successfulDrops.isEmpty()) {
               return successfulDrops;
            }
         }
      }

      return new ArrayList<>();
   }

   public void addProgress() {
      this.progress = this.progress + this.moodScore / 30;
      if (this.progress > (Integer)Config.WHIRLISPRIG_MAX_PROGRESS.get()) {
         this.progress = (Integer)Config.WHIRLISPRIG_MAX_PROGRESS.get();
      }

      this.updateBlock();
   }

   public void evaluateGrove() {
      Level world = this.m_58904_();
      Map<BlockState, Integer> defaultMap = new HashMap<>();
      Map<BlockState, Integer> dropMap = new HashMap<>();
      int score = 0;

      for (BlockPos b : BlockPos.m_121940_(this.m_58899_().m_122013_(10).m_122025_(10).m_6625_(1), this.m_58899_().m_122020_(10).m_122030_(10).m_6630_(30))) {
         BlockState state = world.m_8055_(b);
         BlockState defaultState = state.m_60734_().m_49966_();
         int points = getScore(defaultState);
         if (!world.m_151570_(b) && points != 0) {
            if (!defaultMap.containsKey(defaultState)) {
               defaultMap.put(defaultState, 0);
            }

            if (!dropMap.containsKey(state)) {
               dropMap.put(state, 0);
            }

            if (!state.m_155947_()) {
               dropMap.put(state, dropMap.get(state) + 1);
            }

            defaultMap.put(defaultState, defaultMap.get(defaultState) + 1);
            score += defaultMap.get(defaultState) <= 50 ? points : 0;
         }
      }

      this.ticksToNextEval = 2400;
      this.genTable = dropMap;
      this.scoreMap = defaultMap;
      this.diversityScore = defaultMap.keySet().size();
      this.moodScore = score;
   }

   public static int getScore(BlockState state) {
      if (state.m_60767_() == Material.f_76296_) {
         return 0;
      } else if (state == Blocks.f_49990_.m_49966_()
         || state == Blocks.f_50440_.m_49966_()
         || state == Blocks.f_50599_.m_49966_()
         || state == Blocks.f_152481_.m_49966_()) {
         return 1;
      } else if (state.m_60734_() instanceof BushBlock) {
         return 2;
      } else if (state.m_60734_() instanceof StemGrownBlock) {
         return 2;
      } else if (state.m_204336_(BlockTags.f_13106_)) {
         return 2;
      } else if (!state.m_204336_(BlockTags.f_13035_) && !(state.m_60734_() instanceof LeavesBlock)) {
         if (state.m_60767_() == Material.f_76300_ || state.m_60767_() == Material.f_76302_) {
            return 1;
         } else if (state.m_60734_() instanceof BonemealableBlock) {
            return 1;
         } else if (state.m_204336_(KINDA_LIKES)) {
            return 1;
         } else {
            return state.m_204336_(GREATLY_LIKES) ? 2 : 0;
         }
      } else {
         return 1;
      }
   }

   @Override
   public void convertedEffect() {
      super.convertedEffect();
      if (this.f_58857_ != null && !this.f_58857_.f_46443_) {
         if (this.tickCounter >= 120) {
            this.converted = true;
            this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.f_58857_.m_8055_(this.f_58858_).m_61124_(SummoningTile.CONVERTED, true));
            Whirlisprig entityWhirlisprig = new Whirlisprig(this.f_58857_, true, new BlockPos(this.m_58899_()));
            entityWhirlisprig.m_6034_((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 1.0, (double)this.f_58858_.m_123343_() + 0.5);
            this.f_58857_.m_7967_(entityWhirlisprig);
            ParticleUtil.spawnPoof((ServerLevel)this.f_58857_, this.f_58858_.m_7494_());
            this.tickCounter = 0;
            return;
         }

         if (this.tickCounter % 10 == 0) {
            RandomSource r = this.f_58857_.f_46441_;
            int min = -2;
            int max = 2;
            EntityFollowProjectile proj1 = new EntityFollowProjectile(
               this.f_58857_,
               this.f_58858_.m_7918_(r.m_188503_(max - min) + min, 3, r.m_188503_(max - min) + min),
               this.f_58858_,
               r.m_188503_(255),
               r.m_188503_(255),
               r.m_188503_(255)
            );
            this.f_58857_.m_7967_(proj1);
         }
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128405_("moodScore", this.moodScore);
      tag.m_128405_("diversityScore", this.diversityScore);
      tag.m_128405_("progress", this.progress);
      tag.m_128405_("evalTicks", this.ticksToNextEval);
      if (this.ignoreItems != null && !this.ignoreItems.isEmpty()) {
         NBTUtil.writeItems(tag, "ignored_", this.ignoreItems);
      }
   }

   @Override
   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.moodScore = compound.m_128451_("moodScore");
      this.diversityScore = compound.m_128451_("diversityScore");
      this.progress = compound.m_128451_("progress");
      this.ticksToNextEval = compound.m_128451_("evalTicks");
      this.ignoreItems = NBTUtil.readItems(compound, "ignored_");
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController<>(this, "rotateController", 1.0F, this::walkPredicate));
   }

   private <T extends IAnimatable> PlayState walkPredicate(AnimationEvent<T> tAnimationEvent) {
      tAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("spin"));
      return PlayState.CONTINUE;
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }
}
