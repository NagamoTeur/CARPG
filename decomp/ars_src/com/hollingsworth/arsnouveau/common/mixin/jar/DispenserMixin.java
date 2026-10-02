package com.hollingsworth.arsnouveau.common.mixin.jar;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({DispenserBlock.class})
public abstract class DispenserMixin {
   @Shadow
   protected abstract DispenseItemBehavior m_7216_(ItemStack var1);

   @Inject(
      method = {"dispenseFrom"},
      at = {@At(
         value = "INVOKE_ASSIGN",
         target = "Lnet/minecraft/world/level/block/DispenserBlock;getDispenseMethod(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/core/dispenser/DispenseItemBehavior;"
      )},
      locals = LocalCapture.CAPTURE_FAILHARD,
      cancellable = true
   )
   public void onDispenseFromInject(
      ServerLevel level, BlockPos pos, CallbackInfo ci, BlockSourceImpl source, DispenserBlockEntity dispenser, int slot, ItemStack stack
   ) {
      BlockState inFront = level.m_8055_(pos.m_121945_((Direction)source.m_6414_().m_61143_(DispenserBlock.f_52659_)));
      if (inFront.m_60713_(BlockRegistry.MOB_JAR) && stack.m_41720_() instanceof ShearsItem) {
         BlockPos relativePos = pos.m_121945_((Direction)source.m_6414_().m_61143_(DispenserBlock.f_52659_));
         ANFakePlayer fakePlayer = ANFakePlayer.getPlayer(level);
         fakePlayer.m_21008_(InteractionHand.MAIN_HAND, stack);
         BlockRegistry.MOB_JAR
            .m_6227_(
               inFront,
               level,
               relativePos,
               fakePlayer,
               InteractionHand.MAIN_HAND,
               new BlockHitResult(
                  new Vec3((double)relativePos.m_123341_(), (double)relativePos.m_123342_(), (double)relativePos.m_123343_()),
                  (Direction)source.m_6414_().m_61143_(DispenserBlock.f_52659_),
                  relativePos,
                  false
               )
            );
         dispenser.m_6836_(slot, stack);
         ci.cancel();
      }
   }
}
