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
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.Set;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;

public class EffectInteract extends AbstractEffect {
   public static EffectInteract INSTANCE = new EffectInteract();

   private EffectInteract() {
      super(GlyphLib.EffectInteractID, "Interact");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Entity e = rayTraceResult.m_82443_();
      Player player = this.getPlayer(shooter, (ServerLevel)world);
      if (!this.isRealPlayer(shooter)) {
         InventoryManager manager = spellContext.getCaster().getInvManager();
         Player var12 = this.setupFakeInventory(spellContext, world);
         this.useOnEntity(var12, spellStats, e);

         for (ItemStack i : var12.f_36093_.f_35974_) {
            manager.insertOrDrop(i, world, e.m_20183_());
         }
      } else {
         this.useOnEntity(player, spellStats, e);
      }
   }

   public InteractionHand getHand(Player player) {
      return player instanceof ANFakePlayer ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
   }

   public boolean handleBucket(
      ItemStack item, BucketItem bucket, Player player, BlockState state, Level world, BlockPos pos, BlockHitResult rayTraceResult, InteractionHand hand
   ) {
      if (bucket.getFluid() != Fluids.f_76191_) {
         boolean placed = bucket.emptyContents(player, world, pos, rayTraceResult, item);
         if (placed) {
            if (!player.m_150110_().f_35937_) {
               ItemStack result = ItemUtils.m_41813_(item, player, new ItemStack(Items.f_42446_));
               if (player.m_21120_(hand).m_41619_()) {
                  player.m_21008_(hand, result);
               } else if (!player.m_36356_(result)) {
                  player.f_19853_.m_7967_(new ItemEntity(player.f_19853_, player.m_20185_(), player.m_20186_(), player.m_20189_(), result));
               }
            }

            if (player instanceof ServerPlayer serverPlayer) {
               CriteriaTriggers.f_10591_.m_59469_(serverPlayer, pos, item);
            }

            player.m_36246_(Stats.f_12982_.m_12902_(bucket));
         }

         return placed;
      } else {
         boolean isBucketPickup = state.m_60734_() instanceof BucketPickup && world.m_6425_(pos) != Fluids.f_76191_.m_76145_();
         BlockPos target = isBucketPickup ? pos : pos.m_121945_(rayTraceResult.m_82434_());
         if (world.m_6425_(target) == Fluids.f_76191_.m_76145_()) {
            return false;
         } else {
            BlockState targetState = world.m_8055_(target);
            if (targetState.m_60734_() instanceof BucketPickup bp) {
               ItemStack pickup = bp.m_142598_(world, target, targetState);
               if (!pickup.m_41619_() && !player.m_150110_().f_35937_) {
                  bp.getPickupSound(targetState).ifPresent(sound -> player.m_5496_(sound, 1.0F, 1.0F));
                  world.m_142346_(player, GameEvent.f_157816_, target);
                  ItemStack result = ItemUtils.m_41813_(item, player, pickup);
                  if (player instanceof ServerPlayer serverPlayer) {
                     CriteriaTriggers.f_10576_.m_38772_(serverPlayer, item);
                  }

                  player.m_36246_(Stats.f_12982_.m_12902_(bucket));
                  if (player.m_21120_(hand).m_41619_()) {
                     player.m_21008_(hand, result);
                  } else if (!player.m_36356_(result)) {
                     player.f_19853_.m_7967_(new ItemEntity(player.f_19853_, player.m_20185_(), player.m_20186_(), player.m_20189_(), result));
                  }
               }

               return !pickup.m_41619_();
            } else {
               return false;
            }
         }
      }
   }

   public void useOnEntity(Player player, SpellStats spellStats, Entity target) {
      if (spellStats.isSensitive()) {
         ItemStack item = player.m_21120_(this.getHand(player));
         if (target instanceof LivingEntity livingEntity) {
            InteractionResult res = item.m_41647_(player, livingEntity, this.getHand(player));
            if (res != InteractionResult.SUCCESS) {
               target.m_6096_(player, this.getHand(player));
            }
         } else {
            target.m_6096_(player, this.getHand(player));
         }
      } else {
         player.m_36157_(target, InteractionHand.MAIN_HAND);
      }
   }

   public void useOnBlock(Player player, SpellStats spellStats, BlockPos pos, BlockState state, Level world, BlockHitResult rayTraceResult) {
      if (spellStats.isSensitive()) {
         ItemStack item = player.m_21120_(this.getHand(player));
         if (item.m_41720_() instanceof BucketItem bucket) {
            this.handleBucket(item, bucket, player, state, world, pos, rayTraceResult, this.getHand(player));
            return;
         }

         UseOnContext context = new UseOnContext(player, this.getHand(player), rayTraceResult);
         item.m_41661_(context);
      } else {
         state.m_60664_(world, player, InteractionHand.MAIN_HAND, rayTraceResult);
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos blockPos = rayTraceResult.m_82425_();
      BlockState blockState = world.m_8055_(blockPos);
      if (BlockUtil.destroyRespectsClaim(this.getPlayer(shooter, (ServerLevel)world), world, blockPos)) {
         Player player = this.getPlayer(shooter, (ServerLevel)world);
         if (this.isRealPlayer(shooter)) {
            this.useOnBlock(player, spellStats, blockPos, blockState, world, rayTraceResult);
         } else {
            InventoryManager manager = spellContext.getCaster().getInvManager();
            Player var13 = this.setupFakeInventory(spellContext, world);
            this.useOnBlock(var13, spellStats, blockPos, blockState, world, rayTraceResult);

            for (ItemStack i : var13.f_36093_.f_35974_) {
               manager.insertOrDrop(i, world, rayTraceResult.m_82425_());
            }

            for (ItemStack i : var13.f_36093_.f_35976_) {
               manager.insertOrDrop(i, world, rayTraceResult.m_82425_());
            }
         }
      }
   }

   public FakePlayer setupFakeInventory(SpellContext context, Level level) {
      InventoryManager manager = context.getCaster().getInvManager();
      ANFakePlayer player = ANFakePlayer.getPlayer((ServerLevel)level);
      player.f_36093_.m_6211_();
      player.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
      player.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
      ExtractedStack stack = manager.extractItem(i -> !i.m_41619_(), 1);
      if (!stack.isEmpty()) {
         player.m_8061_(EquipmentSlot.MAINHAND, stack.getStack().m_41777_());
      }

      return player;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentSensitive.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Interacts with blocks or entities as it were a player. Useful for reaching levers, chests, or animals. Sensitive will use your off-hand item on the block or entity.";
   }

   @Override
   public int getDefaultManaCost() {
      return 10;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }
}
