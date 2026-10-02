package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.block.TemporaryBlock;
import com.hollingsworth.arsnouveau.common.block.tile.TemporaryTile;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import com.hollingsworth.arsnouveau.common.world.saved_data.RedstoneSavedData;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent;
import org.jetbrains.annotations.NotNull;

public class EffectRedstone extends AbstractEffect {
   public static EffectRedstone INSTANCE = new EffectRedstone();
   public IntValue BONUS_TIME;
   public static final Map<String, Map<BlockPos, Integer>> signalMap = new ConcurrentHashMap<>();

   private EffectRedstone() {
      super(GlyphLib.EffectRedstoneID, "Redstone Signal");
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      int signalModifier = Mth.m_14045_((int)spellStats.getAmpMultiplier() + 10, 1, 15);
      int timeBonus = (int)spellStats.getDurationMultiplier();
      int delay = Math.max((Integer)this.GENERIC_INT.get() + timeBonus * (Integer)this.BONUS_TIME.get(), 2);
      List<BlockPos> posList = SpellUtil.calcAOEBlocks(shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats);
      FakePlayer fakePlayer = ANFakePlayer.getPlayer((ServerLevel)world);

      for (BlockPos pos1 : posList) {
         if (spellStats.isSensitive()) {
            if (!world.m_46739_(pos1)) {
               return;
            }

            pos1 = pos1.m_7949_();
            RedstoneSavedData.from((ServerLevel)world).SIGNAL_MAP.put(pos1, new RedstoneSavedData.Entry(pos1, signalModifier, delay));
            world.m_46586_(pos1, world.m_8055_(pos1).m_60734_(), pos1);
            world.m_46672_(pos1, world.m_8055_(pos1).m_60734_());
         } else {
            pos1 = pos1.m_121945_(rayTraceResult.m_82434_());
            if (!world.m_46739_(pos1)) {
               return;
            }

            boolean notReplaceable = !world.m_8055_(pos1).m_60767_().m_76336_();
            if (!notReplaceable
               && !MinecraftForge.EVENT_BUS.post(new EntityPlaceEvent(BlockSnapshot.create(world.m_46472_(), world, pos1), world.m_8055_(pos1), fakePlayer))) {
               BlockState state1 = (BlockState)BlockRegistry.TEMPORARY_BLOCK.get().m_49966_().m_61124_(TemporaryBlock.POWER, signalModifier);
               world.m_46597_(pos1, state1);
               if (world.m_7702_(pos1) instanceof TemporaryTile tile) {
                  tile.tickDuration = delay;
                  tile.mimicState = Blocks.f_50330_.m_49966_();
                  tile.updateBlock();
               }

               world.m_7260_(pos1, world.m_8055_(pos1), world.m_8055_(pos1), 2);
            }
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addGenericInt(builder, 5, "Base time in ticks", "base_duration");
      this.BONUS_TIME = builder.comment("Extend time bonus, in ticks").defineInRange("extend_time", 10, 0, Integer.MAX_VALUE);
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentAmplify.INSTANCE,
            AugmentAOE.INSTANCE,
            AugmentDampen.INSTANCE,
            AugmentExtendTime.INSTANCE,
            AugmentDurationDown.INSTANCE,
            AugmentSensitive.INSTANCE
         }
      );
   }

   @Override
   public String getBookDescription() {
      return "Places a temporary block of redstone with configurable power and duration. Augment with Sensitive to set the target block as a power source for itself and surrounding blocks. Dampen and Amplify will adjust the power from the base value of 10.";
   }

   @Override
   public int getDefaultManaCost() {
      return 0;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }
}
