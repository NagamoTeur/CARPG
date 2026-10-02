package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.item.inv.ExtractedStack;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentRandomize;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent;
import org.jetbrains.annotations.NotNull;

public class EffectPlaceBlock extends AbstractEffect {
   public static EffectPlaceBlock INSTANCE = new EffectPlaceBlock();

   private EffectPlaceBlock() {
      super(GlyphLib.EffectPlaceBlockID, "Place Block");
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      List<BlockPos> posList = SpellUtil.calcAOEBlocks(shooter, rayTraceResult.m_82425_(), rayTraceResult, spellStats);
      FakePlayer fakePlayer = ANFakePlayer.getPlayer((ServerLevel)world);

      for (BlockPos pos1 : posList) {
         if (world.m_46739_(pos1)) {
            pos1 = rayTraceResult.m_82436_() ? pos1 : pos1.m_121945_(rayTraceResult.m_82434_());
            boolean notReplaceable = !world.m_8055_(pos1).m_60767_().m_76336_();
            if (!notReplaceable
               && !MinecraftForge.EVENT_BUS.post(new EntityPlaceEvent(BlockSnapshot.create(world.m_46472_(), world, pos1), world.m_8055_(pos1), fakePlayer))) {
               this.place(
                  new BlockHitResult(
                     new Vec3((double)pos1.m_123341_(), (double)pos1.m_123342_(), (double)pos1.m_123343_()), rayTraceResult.m_82434_(), pos1, false
                  ),
                  world,
                  shooter,
                  spellStats,
                  spellContext,
                  resolver,
                  fakePlayer
               );
            }
         }
      }
   }

   public void place(
      BlockHitResult resolveResult,
      Level world,
      @NotNull LivingEntity shooter,
      SpellStats spellStats,
      SpellContext spellContext,
      SpellResolver resolver,
      FakePlayer fakePlayer
   ) {
      InventoryManager manager = spellContext.getCaster().getInvManager();
      ExtractedStack extractItem = spellStats.isRandomized()
         ? manager.extractRandomItem(i -> !i.m_41619_() && i.m_41720_() instanceof BlockItem, 1)
         : manager.extractItem(i -> !i.m_41619_() && i.m_41720_() instanceof BlockItem, 1);
      if (!extractItem.isEmpty()) {
         InteractionResult resultType = attemptPlace(world, extractItem.stack, (BlockItem)extractItem.stack.m_41720_(), resolveResult, fakePlayer);
         if (InteractionResult.FAIL != resultType) {
            ShapersFocus.tryPropagateBlockSpell(resolveResult, world, shooter, spellContext, resolver);
         }

         extractItem.returnOrDrop(world, shooter.m_20097_());
      }
   }

   public static InteractionResult attemptPlace(Level world, ItemStack stack, BlockItem item, BlockHitResult result, Player fakePlayer) {
      fakePlayer.m_21008_(InteractionHand.MAIN_HAND, stack);
      BlockPlaceContext context = new BlockPlaceContext(fakePlayer, InteractionHand.MAIN_HAND, stack, result);
      return item.m_40576_(context);
   }

   @Override
   public int getDefaultManaCost() {
      return 10;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAOE.INSTANCE, AugmentPierce.INSTANCE, AugmentRandomize.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Places blocks from the casters inventory. If a player is casting this, this spell will place blocks from the hot bar first.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }
}
