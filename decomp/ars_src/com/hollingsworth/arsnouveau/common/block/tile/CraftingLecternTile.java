package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.client.container.CraftingTerminalMenu;
import com.hollingsworth.arsnouveau.client.container.StoredItemStack;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class CraftingLecternTile extends StorageLecternTile implements IAnimatable {
   private AbstractContainerMenu craftingContainer = new AbstractContainerMenu(MenuType.f_39968_, 0) {
      public boolean m_6875_(Player player) {
         return false;
      }

      public void m_6199_(Container inventory) {
         if (CraftingLecternTile.this.f_58857_ != null && !CraftingLecternTile.this.f_58857_.f_46443_) {
            CraftingLecternTile.this.onCraftingMatrixChanged();
         }
      }

      public ItemStack m_7648_(Player p_38941_, int p_38942_) {
         return ItemStack.f_41583_;
      }
   };
   private CraftingRecipe currentRecipe;
   private final TransientCustomContainer craftMatrix = new TransientCustomContainer(this.craftingContainer, 3, 3);
   private ResultContainer craftResult = new ResultContainer();
   private HashSet<CraftingTerminalMenu> craftingListeners = new HashSet<>();
   private boolean reading;
   AnimationFactory animationFactory = GeckoLibUtil.createFactory(this);

   public CraftingLecternTile(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)BlockRegistry.CRAFTING_LECTERN_TILE.get(), pos, state);
   }

   @Override
   public AbstractContainerMenu m_7208_(int id, Inventory plInv, Player arg2) {
      return new CraftingTerminalMenu(id, plInv, this);
   }

   @Override
   public void m_183515_(CompoundTag compound) {
      super.m_183515_(compound);
      ListTag listnbt = new ListTag();

      for (int i = 0; i < this.craftMatrix.m_6643_(); i++) {
         ItemStack itemstack = this.craftMatrix.m_8020_(i);
         if (!itemstack.m_41619_()) {
            CompoundTag compoundnbt = new CompoundTag();
            compoundnbt.m_128405_("Slot", i);
            itemstack.m_41739_(compoundnbt);
            listnbt.add(compoundnbt);
         }
      }

      compound.m_128365_("CraftingTable", listnbt);
   }

   @Override
   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.reading = true;
      ListTag listnbt = compound.m_128437_("CraftingTable", 10);

      for (int i = 0; i < listnbt.size(); i++) {
         CompoundTag compoundnbt = listnbt.m_128728_(i);
         int j = compoundnbt.m_128451_("Slot");
         if (j >= 0 && j < this.craftMatrix.m_6643_()) {
            this.craftMatrix.m_6836_(j, ItemStack.m_41712_(compoundnbt));
         }
      }

      this.reading = false;
   }

   public CraftingContainer getCraftingInv() {
      return this.craftMatrix;
   }

   public ResultContainer getCraftResult() {
      return this.craftResult;
   }

   public void craftShift(Player player, @Nullable String tab) {
      List<ItemStack> craftedItemsList = new ArrayList<>();
      int amountCrafted = 0;
      ItemStack crafted = this.craftResult.m_8020_(0);

      do {
         this.craft(player, tab);
         craftedItemsList.add(crafted.m_41777_());
         amountCrafted += crafted.m_41613_();
      } while (ItemStack.m_41746_(crafted, this.craftResult.m_8020_(0)) && amountCrafted + crafted.m_41613_() <= crafted.m_41741_());

      for (ItemStack craftedItem : craftedItemsList) {
         if (!player.m_150109_().m_36054_(craftedItem.m_41777_())) {
            ItemStack is = this.pushStack(craftedItem, tab);
            if (!is.m_41619_()) {
               Containers.m_18992_(this.f_58857_, player.m_20185_(), player.m_20186_(), player.m_20189_(), is);
            }
         }
      }

      crafted.m_41678_(player.f_19853_, player, amountCrafted);
      ForgeEventFactory.firePlayerCraftingEvent(player, ItemHandlerHelper.copyStackWithSize(crafted, amountCrafted), this.craftMatrix);
   }

   public void craft(Player thePlayer, @Nullable String tab) {
      if (this.currentRecipe != null) {
         NonNullList<ItemStack> remainder = this.currentRecipe.m_7457_(this.craftMatrix);
         boolean playerInvUpdate = false;

         for (int i = 0; i < remainder.size(); i++) {
            ItemStack currentStack = this.craftMatrix.m_8020_(i);
            ItemStack oldItem = currentStack.m_41777_();
            ItemStack rem = (ItemStack)remainder.get(i);
            if (!currentStack.m_41619_()) {
               this.craftMatrix.removeItemNoUpdate(i, 1);
               currentStack = this.craftMatrix.m_8020_(i);
            }

            if (currentStack.m_41619_() && !oldItem.m_41619_()) {
               StoredItemStack is = this.pullStack(new StoredItemStack(oldItem), 1, tab);
               if (is == null) {
                  for (int j = 0; j < thePlayer.m_150109_().m_6643_(); j++) {
                     ItemStack st = thePlayer.m_150109_().m_8020_(j);
                     if (ItemStack.m_41746_(oldItem, st) && ItemStack.m_41728_(oldItem, st)) {
                        st = thePlayer.m_150109_().m_7407_(j, 1);
                        if (!st.m_41619_()) {
                           is = new StoredItemStack(st, 1L);
                           playerInvUpdate = true;
                           break;
                        }
                     }
                  }
               }

               if (is != null) {
                  this.craftMatrix.setItemNoUpdate(i, is.getActualStack());
                  currentStack = this.craftMatrix.m_8020_(i);
               }
            }

            if (!rem.m_41619_()) {
               if (currentStack.m_41619_()) {
                  this.craftMatrix.setItemNoUpdate(i, rem);
               } else if (ItemStack.m_41746_(currentStack, rem) && ItemStack.m_41728_(currentStack, rem)) {
                  rem.m_41769_(currentStack.m_41613_());
                  this.craftMatrix.setItemNoUpdate(i, rem);
               } else {
                  rem = this.pushStack(rem, tab);
                  if (!rem.m_41619_() && !thePlayer.m_150109_().m_36054_(rem)) {
                     thePlayer.m_36176_(rem, false);
                  }
               }
            }
         }

         if (playerInvUpdate) {
            thePlayer.f_36096_.m_38946_();
         }

         this.onCraftingMatrixChanged();
      }
   }

   public void unregisterCrafting(CraftingTerminalMenu containerCraftingTerminal) {
      this.craftingListeners.remove(containerCraftingTerminal);
   }

   public void registerCrafting(CraftingTerminalMenu containerCraftingTerminal) {
      this.craftingListeners.add(containerCraftingTerminal);
   }

   protected void onCraftingMatrixChanged() {
      if (this.currentRecipe == null || !this.currentRecipe.m_5818_(this.craftMatrix, this.f_58857_)) {
         this.currentRecipe = (CraftingRecipe)this.f_58857_.m_7465_().m_44015_(RecipeType.f_44107_, this.craftMatrix, this.f_58857_).orElse(null);
      }

      if (this.currentRecipe == null) {
         this.craftResult.m_6836_(0, ItemStack.f_41583_);
      } else {
         this.craftResult.m_6836_(0, this.currentRecipe.m_5874_(this.craftMatrix));
      }

      this.craftingListeners.forEach(CraftingTerminalMenu::onCraftMatrixChanged);
      if (!this.reading) {
         this.m_6596_();
      }
   }

   public void clear(@Nullable String tab) {
      for (int i = 0; i < this.craftMatrix.m_6643_(); i++) {
         ItemStack st = this.craftMatrix.m_8016_(i);
         if (!st.m_41619_()) {
            this.pushOrDrop(st, tab);
         }
      }

      this.onCraftingMatrixChanged();
   }

   public void transferToGrid(Player player, ItemStack[][] ingredients, @Nullable String tab) {
      this.clear(tab);

      for (int i = 0; i < 9; i++) {
         ItemStack[] ingredient = ingredients[i];
         if (ingredient != null) {
            Map<Item, Long> inv = this.itemCounts;
            ingredient = Arrays.stream(ingredient)
               .filter(Objects::nonNull)
               .sorted(Comparator.<ItemStack>comparingLong(a -> inv.getOrDefault(a.m_41720_(), 0L)).reversed())
               .toArray(ItemStack[]::new);
            ItemStack stack = ItemStack.f_41583_;

            for (ItemStack itemStack : ingredient) {
               ItemStack pulled = this.pullStack(itemStack, tab);
               if (!pulled.m_41619_()) {
                  stack = pulled;
                  break;
               }
            }

            if (stack.m_41619_()) {
               for (ItemStack itemStackx : ingredient) {
                  boolean br = false;
                  Inventory playerInv = player.m_150109_();

                  for (int k = 0; k < playerInv.m_6643_(); k++) {
                     if (ItemStack.m_41746_(playerInv.m_8020_(k), itemStackx)) {
                        stack = playerInv.m_7407_(k, 1);
                        br = true;
                        break;
                     }
                  }

                  if (br) {
                     break;
                  }
               }
            }

            if (!stack.m_41619_()) {
               this.craftMatrix.m_6836_(i, stack);
            }
         }
      }

      this.onCraftingMatrixChanged();
   }

   private ItemStack pullStack(ItemStack itemStack, @Nullable String tab) {
      StoredItemStack is = this.pullStack(new StoredItemStack(itemStack), 1, tab);
      return is == null ? ItemStack.f_41583_ : is.getActualStack();
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController<>(this, "controller", 1.0F, event -> {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("ledger_float"));
         return PlayState.CONTINUE;
      }));
   }

   @Override
   public AnimationFactory getFactory() {
      return this.animationFactory;
   }
}
