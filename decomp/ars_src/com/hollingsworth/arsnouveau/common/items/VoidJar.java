package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.item.IScribeable;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class VoidJar extends ModItem implements IScribeable {
   public VoidJar() {
      super(ItemsRegistry.defaultItemProperties().m_41487_(1));
   }

   public void toggleStatus(Player playerEntity, ItemStack stack) {
      VoidJar.VoidJarData jarData = new VoidJar.VoidJarData(stack);
      jarData.setActive(!jarData.isActive());
      if (jarData.isActive()) {
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.on"));
      } else {
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.off"));
      }
   }

   public static boolean tryVoiding(Player player, ItemStack pickingUp) {
      for (ItemStack jar : player.f_36093_.f_35974_) {
         if (jar.m_41720_() == ItemsRegistry.VOID_JAR.get()) {
            return voidStack(player, jar, pickingUp);
         }
      }

      return player.m_21206_().m_150930_(ItemsRegistry.VOID_JAR.get()) ? voidStack(player, player.m_21206_(), pickingUp) : false;
   }

   public static boolean voidStack(Player player, ItemStack jarStack, ItemStack stackToVoid) {
      VoidJar.VoidJarData jarData = new VoidJar.VoidJarData(jarStack);
      if (jarData.isActive() && jarData.containsStack(stackToVoid)) {
         CapabilityRegistry.getMana(player).ifPresent(iMana -> iMana.addMana(5.0 * (double)stackToVoid.m_41613_()));
         stackToVoid.m_41764_(0);
         return true;
      } else {
         return false;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player player, InteractionHand handIn) {
      if (worldIn.f_46443_) {
         return super.m_7203_(worldIn, player, handIn);
      } else {
         ItemStack stack = player.m_21120_(handIn);
         VoidJar.VoidJarData data = new VoidJar.VoidJarData(stack);
         if (handIn == InteractionHand.MAIN_HAND) {
            ItemStack stackToWrite = player.m_21206_();
            if (player.m_6144_()) {
               this.toggleStatus(player, stack);
               return InteractionResultHolder.m_19096_(stack);
            }

            data.writeWithFeedback(player, stackToWrite);
         }

         return InteractionResultHolder.m_19090_(stack);
      }
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      if (stack.m_41782_()) {
         VoidJar.VoidJarData data = new VoidJar.VoidJarData(stack);
         if (data.isActive()) {
            tooltip2.add(Component.m_237115_("ars_nouveau.on"));
         } else {
            tooltip2.add(Component.m_237115_("ars_nouveau.off"));
         }

         for (ItemStack s : data.getItems()) {
            tooltip2.add(s.m_41786_());
         }

         super.m_7373_(stack, worldIn, tooltip2, flagIn);
      }
   }

   @Override
   public boolean onScribe(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack thisStack) {
      VoidJar.VoidJarData data = new VoidJar.VoidJarData(thisStack);
      return data.writeWithFeedback(player, player.m_21120_(handIn));
   }

   public static class VoidJarData extends ItemScroll.ItemScrollData {
      private boolean active;

      public VoidJarData(ItemStack stack) {
         super(stack);
         CompoundTag tag = this.getItemTag(stack);
         if (tag != null) {
            this.active = tag.m_128471_("on");
         }
      }

      public boolean isActive() {
         return this.active;
      }

      public void setActive(boolean active) {
         this.active = active;
         this.writeItem();
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         super.writeToNBT(tag);
         tag.m_128379_("on", this.active);
      }

      @Override
      public String getTagString() {
         return "an_voidJar";
      }
   }
}
