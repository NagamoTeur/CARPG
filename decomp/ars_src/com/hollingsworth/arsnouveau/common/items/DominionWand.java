package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.entity.IDecoratable;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.nbt.ItemstackData;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.common.network.HighlightAreaPacket;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public class DominionWand extends ModItem {
   public DominionWand() {
      super(ItemsRegistry.defaultItemProperties().m_41487_(1));
   }

   public void m_6883_(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
      super.m_6883_(pStack, pLevel, pEntity, pSlotId, pIsSelected);
      if (pIsSelected && !pLevel.f_46443_ && pLevel.m_46467_() % 5L == 0L) {
         DominionWand.DominionData data = new DominionWand.DominionData(pStack);
         BlockPos pos = data.storedPos;
         if (pos != null) {
            if (pLevel.m_7702_(pos) instanceof IWandable wandable) {
               Networking.sendToPlayerClient(new HighlightAreaPacket(wandable.getWandHighlight(new ArrayList<>()), 10), (ServerPlayer)pEntity);
            }
         } else {
            if (data.getEntity(pLevel) instanceof IWandable wandable) {
               Networking.sendToPlayerClient(new HighlightAreaPacket(wandable.getWandHighlight(new ArrayList<>()), 10), (ServerPlayer)pEntity);
            }
         }
      }
   }

   public InteractionResult m_6880_(ItemStack doNotUseStack, Player playerEntity, LivingEntity target, InteractionHand hand) {
      if (!playerEntity.f_19853_.f_46443_ && hand == InteractionHand.MAIN_HAND) {
         ItemStack stack = playerEntity.m_21120_(hand);
         DominionWand.DominionData data = new DominionWand.DominionData(stack);
         if (playerEntity.m_6144_() && target instanceof IWandable wandable) {
            wandable.onWanded(playerEntity);
            this.clear(stack, playerEntity);
            return InteractionResult.SUCCESS;
         } else if (!data.hasStoredData()) {
            data.setStoredEntityID(target.m_19879_());
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.dominion_wand.stored_entity"));
            return InteractionResult.SUCCESS;
         } else {
            Level world = playerEntity.m_20193_();
            if (data.getStoredPos() != null && world.m_7702_(data.getStoredPos()) instanceof IWandable wandable) {
               wandable.onFinishedConnectionFirst(data.getStoredPos(), target, playerEntity);
            }

            if (target instanceof IWandable wandable) {
               wandable.onFinishedConnectionLast(data.getStoredPos(), target, playerEntity);
               this.clear(stack, playerEntity);
            }

            if (playerEntity.m_6144_() && target instanceof IDecoratable coolBoy) {
               coolBoy.setCosmeticItem(ItemStack.f_41583_);
            }

            return InteractionResult.SUCCESS;
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   public boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player) {
      return false;
   }

   public void clear(ItemStack stack, Player player) {
      DominionWand.DominionData data = new DominionWand.DominionData(stack);
      data.setStoredPos(null);
      data.setStoredEntityID(-1);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      if (!context.m_43725_().f_46443_ && context.m_43723_() != null) {
         BlockPos pos = context.m_8083_();
         Level world = context.m_43725_();
         Player playerEntity = context.m_43723_();
         ItemStack stack = context.m_43722_();
         DominionWand.DominionData data = new DominionWand.DominionData(stack);
         if (playerEntity.m_6144_() && world.m_7702_(pos) instanceof IWandable wandable && !data.hasStoredData()) {
            wandable.onWanded(playerEntity);
            this.clear(stack, playerEntity);
            return InteractionResult.CONSUME;
         } else if (!data.hasStoredData()) {
            data.setStoredPos(pos.m_7949_());
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.dominion_wand.position_set"));
            return InteractionResult.SUCCESS;
         } else {
            if (data.getStoredPos() != null && world.m_7702_(data.getStoredPos()) instanceof IWandable wandable) {
               wandable.onFinishedConnectionFirst(pos, (LivingEntity)world.m_6815_(data.getStoredEntityID()), playerEntity);
            }

            if (world.m_7702_(pos) instanceof IWandable wandable) {
               wandable.onFinishedConnectionLast(data.getStoredPos(), (LivingEntity)world.m_6815_(data.getStoredEntityID()), playerEntity);
            }

            if (data.getStoredEntityID() != -1 && world.m_6815_(data.getStoredEntityID()) instanceof IWandable wandable) {
               wandable.onFinishedConnectionFirst(pos, null, playerEntity);
            }

            this.clear(stack, playerEntity);
            return super.m_6225_(context);
         }
      } else {
         return super.m_6225_(context);
      }
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag p_77624_4_) {
      DominionWand.DominionData data = new DominionWand.DominionData(stack);
      if (data.getStoredEntityID() == -1) {
         tooltip.add(Component.m_237115_("ars_nouveau.dominion_wand.no_entity"));
      } else {
         tooltip.add(Component.m_237115_("ars_nouveau.dominion_wand.entity_stored"));
      }

      if (data.getStoredPos() == null) {
         tooltip.add(Component.m_237115_("ars_nouveau.dominion_wand.no_location"));
      } else {
         tooltip.add(Component.m_237110_("ars_nouveau.dominion_wand.position_stored", new Object[]{getPosString(data.getStoredPos())}));
      }
   }

   public static String getPosString(BlockPos pos) {
      return Component.m_237110_("ars_nouveau.position", new Object[]{pos.m_123341_(), pos.m_123342_(), pos.m_123343_()}).getString();
   }

   public static class DominionData extends ItemstackData {
      private BlockPos storedPos;
      private int storedEntityID;

      public DominionData(ItemStack stack) {
         super(stack);
         CompoundTag tag = this.getItemTag(stack);
         if (tag != null) {
            this.storedPos = NBTUtil.getNullablePos(tag, "stored");
            this.storedEntityID = tag.m_128451_("entityID");
         }
      }

      public boolean hasStoredData() {
         return this.getStoredPos() != null || this.getStoredEntityID() != -1;
      }

      @Nullable
      public BlockPos getStoredPos() {
         return this.storedPos != BlockPos.f_121853_ && this.storedPos != null ? this.storedPos.m_7949_() : null;
      }

      public int getStoredEntityID() {
         return this.storedEntityID == 0 ? -1 : this.storedEntityID;
      }

      @Nullable
      public Entity getEntity(Level world) {
         return world.m_6815_(this.storedEntityID);
      }

      public void setStoredPos(@Nullable BlockPos pos) {
         this.storedPos = pos;
         this.writeItem();
      }

      public void setStoredEntityID(int id) {
         this.storedEntityID = id;
         this.writeItem();
      }

      @Override
      public String getTagString() {
         return "an_dominion_wand";
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         if (this.storedPos != null) {
            NBTUtil.storeBlockPos(tag, "stored", this.storedPos);
         }

         tag.m_128405_("entityID", this.storedEntityID);
      }
   }
}
