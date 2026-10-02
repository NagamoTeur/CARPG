package net.thirdlife.iterrpg.item;

import io.netty.buffer.Unpooled;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import net.thirdlife.iterrpg.item.inventory.MobPlacerInventoryCapability;
import net.thirdlife.iterrpg.procedures.MobPlacerActivateProcedure;
import net.thirdlife.iterrpg.world.inventory.MobPlacerGUIMenu;

public class MobPlacerItem extends Item {
   public MobPlacerItem() {
      super(new Properties().m_41491_(null).m_41487_(1).m_41497_(Rarity.EPIC));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, final Player entity, final InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      ItemStack itemstack = (ItemStack)ar.m_19095_();
      double x = entity.m_20185_();
      double y = entity.m_20186_();
      double z = entity.m_20189_();
      if (entity instanceof ServerPlayer serverPlayer) {
         NetworkHooks.openScreen(serverPlayer, new MenuProvider() {
            public Component m_5446_() {
               return Component.m_237113_("Mob Placer");
            }

            public AbstractContainerMenu m_7208_(int id, Inventory inventory, Player player) {
               FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
               packetBuffer.m_130064_(entity.m_20183_());
               packetBuffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
               return new MobPlacerGUIMenu(id, inventory, packetBuffer);
            }
         }, buf -> {
            buf.m_130064_(entity.m_20183_());
            buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
         });
      }

      return ar;
   }

   public InteractionResult m_6225_(UseOnContext context) {
      super.m_6225_(context);
      MobPlacerActivateProcedure.execute(
         context.m_43725_(),
         (double)context.m_8083_().m_123341_(),
         (double)context.m_8083_().m_123342_(),
         (double)context.m_8083_().m_123343_(),
         context.m_43722_()
      );
      return InteractionResult.SUCCESS;
   }

   public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag compound) {
      return new MobPlacerInventoryCapability();
   }

   public CompoundTag getShareTag(ItemStack stack) {
      CompoundTag nbt = super.getShareTag(stack);
      if (nbt != null) {
         stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null)
            .ifPresent(capability -> nbt.m_128365_("Inventory", ((ItemStackHandler)capability).serializeNBT()));
      }

      return nbt;
   }

   public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
      super.readShareTag(stack, nbt);
      if (nbt != null) {
         stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null)
            .ifPresent(capability -> ((ItemStackHandler)capability).deserializeNBT((CompoundTag)nbt.m_128423_("Inventory")));
      }
   }
}
