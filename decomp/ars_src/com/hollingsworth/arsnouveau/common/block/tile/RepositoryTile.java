package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class RepositoryTile extends RandomizableContainerBlockEntity implements IAnimatable, ITooltipProvider {
   public static String[][] CONFIGURATIONS = new String[][]{
      {"1", "2_3", "4_6", "7_9", "10_12", "13_15", "16_18", "19_21", "22_24", "25_27"},
      {"1", "2_3", "25_27", "22_24", "19_21", "10_12", "7_9", "4_6", "13_15", "16_18"},
      {"10_12", "13_15", "7_9", "16_18", "4_6", "19_21", "2_3", "22_24", "1", "25_27"},
      {"1", "2_3", "4_6", "13_15", "16_18", "25_27", "22_24", "10_12", "19_21", "7_9"},
      {"1", "25_27", "2_3", "22_24", "4_6", "19_21", "7_9", "16_18", "10_12", "13_15"},
      {"1", "2_3", "4_6", "10_12", "25_27", "22_24", "19_21", "13_15", "7_9", "16_18"}
   };
   private NonNullList<ItemStack> items = NonNullList.m_122780_(54, ItemStack.f_41583_);
   public int fillLevel;
   public int configuration;
   private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
      protected void m_142292_(Level p_155062_, BlockPos p_155063_, BlockState p_155064_) {
      }

      protected void m_142289_(Level level, BlockPos p_155073_, BlockState p_155074_) {
         RepositoryTile.this.updateFill();
      }

      protected void m_142148_(Level p_155066_, BlockPos p_155067_, BlockState p_155068_, int p_155069_, int p_155070_) {
      }

      protected boolean m_142718_(Player p_155060_) {
         if (p_155060_.f_36096_ instanceof ChestMenu) {
            Container container = ((ChestMenu)p_155060_.f_36096_).m_39261_();
            return container == RepositoryTile.this;
         } else {
            return false;
         }
      }
   };
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public void updateFill() {
      int i = 0;
      float f = 0.0F;

      for (int j = 0; j < this.m_6643_(); j++) {
         ItemStack itemstack = this.m_8020_(j);
         if (!itemstack.m_41619_()) {
            f++;
            i++;
         }
      }

      f /= (float)this.m_6643_();
      int oldFill = this.fillLevel;
      this.fillLevel = Mth.m_14143_(f * 14.0F) + (i > 0 ? 1 : 0);
      if (oldFill != this.fillLevel) {
         this.updateBlock();
      }
   }

   public RepositoryTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.REPOSITORY_TILE, pos, state);
   }

   protected NonNullList<ItemStack> m_7086_() {
      return this.items;
   }

   protected void m_6520_(NonNullList<ItemStack> pItemStacks) {
      this.items = pItemStacks;
   }

   public void m_6836_(int pIndex, ItemStack pStack) {
      super.m_6836_(pIndex, pStack);
      this.updateFill();
   }

   public ItemStack m_7407_(int pIndex, int pCount) {
      ItemStack stack = super.m_7407_(pIndex, pCount);
      this.updateFill();
      return stack;
   }

   public void m_5856_(Player pPlayer) {
      super.m_5856_(pPlayer);
      this.openersCounter.m_155452_(pPlayer, this.m_58904_(), this.m_58899_(), this.m_58900_());
   }

   public void m_5785_(Player pPlayer) {
      super.m_5785_(pPlayer);
      this.openersCounter.m_155468_(pPlayer, this.m_58904_(), this.m_58899_(), this.m_58900_());
   }

   protected Component m_6820_() {
      return Component.m_237115_("block.ars_nouveau.repository");
   }

   protected AbstractContainerMenu m_6555_(int pId, Inventory pPlayer) {
      return ChestMenu.m_39246_(pId, pPlayer, this);
   }

   public int m_6643_() {
      return 54;
   }

   protected void m_183515_(CompoundTag pTag) {
      super.m_183515_(pTag);
      if (!this.m_59634_(pTag)) {
         ContainerHelper.m_18973_(pTag, this.items);
      }

      pTag.m_128405_("fillLevel", this.fillLevel);
      pTag.m_128405_("configuration", this.configuration);
   }

   public void m_142466_(CompoundTag pTag) {
      super.m_142466_(pTag);
      this.items = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      if (!this.m_59631_(pTag)) {
         ContainerHelper.m_18980_(pTag, this.items);
      }

      this.fillLevel = pTag.m_128451_("fillLevel");
      this.configuration = pTag.m_128451_("configuration");
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
      super.onDataPacket(net, pkt);
      this.handleUpdateTag(pkt.m_131708_() == null ? new CompoundTag() : pkt.m_131708_());
   }

   public boolean updateBlock() {
      if (this.f_58857_ == null) {
         return false;
      } else {
         BlockState state = this.f_58857_.m_8055_(this.f_58858_);
         this.f_58857_.m_7260_(this.f_58858_, state, state, 3);
         this.m_6596_();
         return true;
      }
   }

   public CompoundTag m_5995_() {
      CompoundTag tag = new CompoundTag();
      this.m_183515_(tag);
      return tag;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.m_8077_()) {
         tooltip.add(this.m_7770_());
      }
   }
}
