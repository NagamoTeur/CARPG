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
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.LootUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentRandomize;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class EffectExchange extends AbstractEffect {
   public static EffectExchange INSTANCE = new EffectExchange();

   private EffectExchange() {
      super(GlyphLib.EffectExchangeID, "Exchange");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Entity entity = rayTraceResult.m_82443_();
      Vec3 origLoc = shooter.f_19825_;
      if (!ForgeEventFactory.onEnderTeleport(shooter, entity.m_20185_(), entity.m_20186_(), entity.m_20189_()).isCanceled()) {
         shooter.m_6021_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

      if (!(entity instanceof LivingEntity living)
         || !ForgeEventFactory.onEnderTeleport(living, origLoc.m_7096_(), origLoc.m_7098_(), origLoc.m_7094_()).isCanceled()) {
         entity.m_6021_(origLoc.m_7096_(), origLoc.m_7098_(), origLoc.m_7094_());
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult result, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      List<BlockPos> posList = SpellUtil.calcAOEBlocks(
         shooter, result.m_82425_(), result, spellStats.getAoeMultiplier(), spellStats.getBuffCount(AugmentPierce.INSTANCE)
      );
      BlockState origState = world.m_8055_(result.m_82425_());
      ANFakePlayer fakePlayer = ANFakePlayer.getPlayer((ServerLevel)world);
      Block firstBlock = null;
      InventoryManager manager = spellContext.getCaster().getInvManager();

      for (BlockPos pos1 : posList) {
         BlockState state = world.m_8055_(pos1);
         if (this.canBlockBeHarvested(spellStats, world, pos1)
            && origState.m_60734_() == state.m_60734_()
            && (world.m_8055_(pos1).m_60767_() == Material.f_76296_ || world.m_8055_(pos1).m_60734_() != BlockRegistry.INTANGIBLE_AIR)
            && BlockUtil.destroyRespectsClaim(this.getPlayer(shooter, (ServerLevel)world), world, pos1)) {
            Block finalFirstBlock = firstBlock;
            ExtractedStack extractedStack = manager.extractRandomItem(i -> {
               if (i.m_41720_() instanceof BlockItem blockItemx) {
                  return finalFirstBlock == null ? true : spellStats.isRandomized() || blockItemx.m_40614_() == finalFirstBlock;
               } else {
                  return false;
               }
            }, 1);
            if (!extractedStack.isEmpty()) {
               if (firstBlock == null && extractedStack.stack.m_41720_() instanceof BlockItem blockItem) {
                  firstBlock = blockItem.m_40614_();
               }

               this.attemptPlace(extractedStack.getStack(), world, pos1, result, shooter, fakePlayer, spellContext, resolver);
               extractedStack.replaceAndReturnOrDrop(extractedStack.getStack(), world, shooter.m_20097_());
            }
         }
      }
   }

   public void attemptPlace(
      ItemStack stack,
      Level world,
      BlockPos pos1,
      BlockHitResult result,
      LivingEntity shooter,
      Player fakePlayer,
      SpellContext spellContext,
      SpellResolver resolver
   ) {
      BlockItem item = (BlockItem)stack.m_41720_();
      ItemStack tool = LootUtil.getDefaultFakeTool();
      tool.m_41663_(Enchantments.f_44985_, 1);
      fakePlayer.m_21008_(InteractionHand.MAIN_HAND, stack);
      BlockPlaceContext context = BlockPlaceContext.m_43644_(
         new BlockPlaceContext(new UseOnContext(fakePlayer, InteractionHand.MAIN_HAND, result)),
         pos1.m_121945_(result.m_82434_().m_122424_()),
         result.m_82434_()
      );
      BlockState placeState = item.m_40614_().m_5573_(context);
      if (placeState == null || placeState.m_60734_() != world.m_8055_(pos1).m_60734_()) {
         if (BlockUtil.breakExtraBlock((ServerLevel)world, pos1, tool, shooter.m_20148_(), true)) {
            if (placeState != null) {
               world.m_7731_(pos1, placeState, 3);
               item.m_40614_().m_6402_(world, pos1, placeState, shooter, stack);
               BlockItem.m_40582_(world, shooter instanceof Player player ? player : fakePlayer, pos1, stack);
               stack.m_41774_(1);
               ShapersFocus.tryPropagateBlockSpell(
                  new BlockHitResult(new Vec3((double)pos1.m_123341_(), (double)pos1.m_123342_(), (double)pos1.m_123343_()), result.m_82434_(), pos1, false),
                  world,
                  shooter,
                  spellContext,
                  resolver
               );
            }
         }
      }
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE, AugmentPierce.INSTANCE, AugmentAOE.INSTANCE, AugmentRandomize.INSTANCE}
      );
   }

   @Override
   public String getBookDescription() {
      return "When used on blocks, exchanges the blocks in the players hotbar for the blocks hit as if they were mined with silk touch. Can be augmented with AOE, and Amplify is required for swapping blocks of higher hardness. When used on entities, the locations of the caster and the entity hit are swapped.";
   }

   @Override
   public int getDefaultManaCost() {
      return 50;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }
}
