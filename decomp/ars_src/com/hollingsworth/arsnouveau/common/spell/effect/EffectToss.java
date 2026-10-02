package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.item.inv.ExtractedStack;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.TileCaster;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

public class EffectToss extends AbstractEffect {
   public static EffectToss INSTANCE = new EffectToss();

   public EffectToss() {
      super(GlyphLib.EffectTossID, "Toss");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = rayTraceResult.m_82443_().m_20183_();
      this.summonStack(shooter, spellContext, world, pos, new InventoryManager(spellContext.getCaster()));
   }

   public void summonStack(LivingEntity shooter, SpellContext context, Level world, BlockPos pos, InventoryManager inventoryManager) {
      ExtractedStack casterStack = inventoryManager.extractItem(i -> {
         if (!i.m_41619_() && context.getCaster() instanceof TileCaster) {
            return true;
         } else {
            return !i.m_41619_() && shooter instanceof Player ? !ItemStack.m_41728_(shooter.m_21205_(), i) : false;
         }
      }, 64);
      world.m_7967_(new ItemEntity(world, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5, casterStack.stack.m_41777_()));
      casterStack.stack.m_41764_(0);
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = rayTraceResult.m_82425_().m_121945_(rayTraceResult.m_82434_());
      InventoryManager manager = new InventoryManager(spellContext.getCaster());
      if (world.m_7702_(rayTraceResult.m_82425_()) == null) {
         this.summonStack(shooter, spellContext, world, pos, manager);
      } else {
         BlockEntity tileEntity = world.m_7702_(rayTraceResult.m_82425_());
         IItemHandler targetInv = (IItemHandler)tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
         if (targetInv != null) {
            ExtractedStack casterStack = manager.extractByAmount(stackToExtract -> {
               if (stackToExtract.m_41619_()) {
                  return 0;
               } else if (shooter instanceof Player && ItemStack.m_41728_(shooter.m_21205_(), stackToExtract)) {
                  return 0;
               } else {
                  for (int i = 0; i < targetInv.getSlots(); i++) {
                     ItemStack stackInTarget = targetInv.getStackInSlot(i);
                     if (stackInTarget.m_41619_()) {
                        return targetInv.getSlotLimit(i);
                     }

                     if (ItemHandlerHelper.canItemStacksStack(stackInTarget, stackToExtract)) {
                        int origSize = stackToExtract.m_41613_();
                        ItemStack simReturn = targetInv.insertItem(i, stackToExtract, true);
                        int maxRoom = origSize - simReturn.m_41613_();
                        int adjustedMax = Math.min(maxRoom, targetInv.getSlotLimit(i));
                        if (adjustedMax > 0) {
                           return adjustedMax;
                        }
                     }
                  }

                  return 0;
               }
            });
            casterStack.stack = ItemHandlerHelper.insertItemStacked(targetInv, casterStack.getStack(), false);
            casterStack.returnOrDrop(world, pos);
         }
      }
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }

   @Override
   public int getDefaultManaCost() {
      return 10;
   }

   @Override
   public String getBookDescription() {
      return "Causes the caster to place an item from their inventory to a location. If this glyph is used on an inventory, the item will attempt to be inserted into it.";
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[0]);
   }
}
