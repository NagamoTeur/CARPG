package shadows.apotheosis.village.fletching;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.DistExecutor;
import shadows.apotheosis.Apoth;

public class FletchingContainer extends AbstractContainerMenu {
   protected final CraftingContainer craftMatrix = new CraftingContainer(this, 1, 3);
   protected final ResultContainer craftResult = new ResultContainer();
   protected final Level world;
   protected final BlockPos pos;
   protected final Player player;

   public FletchingContainer(int id, Inventory inv, Level world, BlockPos pos) {
      super((MenuType)Apoth.Menus.FLETCHING.get(), id);
      this.world = world;
      this.pos = pos;
      this.player = inv.f_35978_;
      this.m_38897_(new FletchingContainer.FletchingResultSlot(inv.f_35978_, this.craftMatrix, this.craftResult, 0, 124, 35));

      for (int i = 0; i < 3; i++) {
         this.m_38897_(new Slot(this.craftMatrix, i, 48, 17 + i * 18));
      }

      for (int k = 0; k < 3; k++) {
         for (int i1 = 0; i1 < 9; i1++) {
            this.m_38897_(new Slot(inv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.m_38897_(new Slot(inv, l, 8 + l * 18, 142));
      }
   }

   public FletchingContainer(int id, Inventory inv) {
      this(id, inv, (Level)DistExecutor.callWhenOn(Dist.CLIENT, () -> () -> Minecraft.m_91087_().f_91073_), BlockPos.f_121853_);
   }

   public void m_6199_(Container inventory) {
      if (!this.world.f_46443_) {
         ServerPlayer serverplayerentity = (ServerPlayer)this.player;
         ItemStack itemstack = ItemStack.f_41583_;
         Optional<FletchingRecipe> optional = this.player.m_20194_().m_129894_().m_44015_(Apoth.RecipeTypes.FLETCHING, this.craftMatrix, this.world);
         if (optional.isPresent()) {
            FletchingRecipe icraftingrecipe = optional.get();
            itemstack = icraftingrecipe.assemble(this.craftMatrix);
         }

         this.craftResult.m_6836_(0, itemstack);
         serverplayerentity.f_8906_.m_9829_(new ClientboundContainerSetSlotPacket(this.f_38840_, 0, 0, itemstack));
      }
   }

   public void m_6877_(Player playerIn) {
      super.m_6877_(playerIn);
      this.m_150411_(playerIn, this.craftMatrix);
   }

   public boolean m_6875_(Player player) {
      return this.world.m_8055_(this.pos).m_60734_() == Blocks.f_50622_
         && player.m_20275_((double)this.pos.m_123341_(), (double)this.pos.m_123342_(), (double)this.pos.m_123343_()) < 64.0;
   }

   public ItemStack m_7648_(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(index);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (index == 0) {
            itemstack1.m_41720_().m_7836_(itemstack1, this.world, playerIn);
            if (!this.m_38903_(itemstack1, 4, 40, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (index >= 4 && index < 31) {
            if (!this.m_38903_(itemstack1, 31, 40, false)) {
               return ItemStack.f_41583_;
            }
         } else if (index >= 31 && index < 40) {
            if (!this.m_38903_(itemstack1, 4, 31, false)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 4, 40, false)) {
            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }

         if (itemstack1.m_41613_() == itemstack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(playerIn, itemstack1);
         if (index == 0) {
            playerIn.m_36176_(itemstack1, false);
         }
      }

      return itemstack;
   }

   public boolean m_5882_(ItemStack stack, Slot slotIn) {
      return slotIn.f_40218_ != this.craftResult && super.m_5882_(stack, slotIn);
   }

   protected class FletchingResultSlot extends ResultSlot {
      protected FletchingResultSlot(Player player, CraftingContainer inv, Container result, int slot, int x, int y) {
         super(player, inv, result, slot, x, y);
      }

      public void m_142406_(Player thePlayer, ItemStack stack) {
         this.m_5845_(stack);
         ForgeHooks.setCraftingPlayer(thePlayer);
         NonNullList<ItemStack> nonnulllist = thePlayer.f_19853_
            .m_7465_()
            .m_44069_(Apoth.RecipeTypes.FLETCHING, FletchingContainer.this.craftMatrix, thePlayer.f_19853_);
         ForgeHooks.setCraftingPlayer(null);

         for (int i = 0; i < nonnulllist.size(); i++) {
            ItemStack itemstack = FletchingContainer.this.craftMatrix.m_8020_(i);
            ItemStack itemstack1 = (ItemStack)nonnulllist.get(i);
            if (!itemstack.m_41619_()) {
               FletchingContainer.this.craftMatrix.m_7407_(i, 1);
               itemstack = FletchingContainer.this.craftMatrix.m_8020_(i);
            }

            if (!itemstack1.m_41619_()) {
               if (itemstack.m_41619_()) {
                  FletchingContainer.this.craftMatrix.m_6836_(i, itemstack1);
               } else if (ItemStack.m_41746_(itemstack, itemstack1) && ItemStack.m_41658_(itemstack, itemstack1)) {
                  itemstack1.m_41769_(itemstack.m_41613_());
                  FletchingContainer.this.craftMatrix.m_6836_(i, itemstack1);
               } else if (!FletchingContainer.this.player.m_150109_().m_36054_(itemstack1)) {
                  FletchingContainer.this.player.m_36176_(itemstack1, false);
               }
            }
         }
      }
   }
}
