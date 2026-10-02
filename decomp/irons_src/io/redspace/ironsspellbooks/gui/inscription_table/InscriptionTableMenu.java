package io.redspace.ironsspellbooks.gui.inscription_table;

import io.redspace.ironsspellbooks.api.events.InscribeSpellEvent;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.item.Scroll;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.MenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;

public class InscriptionTableMenu extends AbstractContainerMenu {
   private final Level level;
   private final Slot spellBookSlot;
   private final Slot scrollSlot;
   private final Slot resultSlot;
   private int selectedSpellIndex = -1;
   private boolean fromCurioSlot = false;
   protected final ResultContainer resultSlots = new ResultContainer();
   protected final Container inputSlots = new SimpleContainer(2) {
      public void m_6596_() {
         super.m_6596_();
         InscriptionTableMenu.this.m_6199_(this);
      }
   };
   protected final ContainerLevelAccess access;
   private static final int HOTBAR_SLOT_COUNT = 9;
   private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
   private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
   private static final int PLAYER_INVENTORY_SLOT_COUNT = 27;
   private static final int VANILLA_SLOT_COUNT = 36;
   private static final int VANILLA_FIRST_SLOT_INDEX = 0;
   private static final int TE_INVENTORY_FIRST_SLOT_INDEX = 36;
   private static final int TE_INVENTORY_SLOT_COUNT = 3;

   public InscriptionTableMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
      this(containerId, inv, ContainerLevelAccess.f_39287_);
   }

   public InscriptionTableMenu(int containerId, Inventory inv, ContainerLevelAccess access) {
      super((MenuType)MenuRegistry.INSCRIPTION_TABLE_MENU.get(), containerId);
      this.access = access;
      m_38869_(inv, 3);
      this.level = inv.f_35978_.f_19853_;
      this.addPlayerInventory(inv);
      this.addPlayerHotbar(inv);
      this.spellBookSlot = new Slot(this.inputSlots, 0, 17, 21) {
         public boolean m_5857_(ItemStack stack) {
            return stack.m_41720_() instanceof SpellBook;
         }

         public void m_142406_(Player pPlayer, ItemStack pStack) {
            InscriptionTableMenu.this.setSelectedSpell(-1);
            super.m_142406_(pPlayer, pStack);
         }
      };
      this.scrollSlot = new Slot(this.inputSlots, 1, 17, 53) {
         public boolean m_5857_(ItemStack stack) {
            return stack.m_150930_((Item)ItemRegistry.SCROLL.get());
         }
      };
      this.resultSlot = new Slot(this.resultSlots, 2, 208, 136) {
         public boolean m_5857_(ItemStack stack) {
            return false;
         }

         public void m_142406_(Player player, ItemStack stack) {
            ItemStack spellBookStack = InscriptionTableMenu.this.spellBookSlot.m_7993_();
            ISpellContainer spellList = ISpellContainer.get(spellBookStack);
            spellList.removeSpellAtIndex(InscriptionTableMenu.this.selectedSpellIndex, spellBookStack);
            super.m_142406_(player, spellBookStack);
         }
      };
      this.m_38897_(this.spellBookSlot);
      this.m_38897_(this.scrollSlot);
      this.m_38897_(this.resultSlot);
      ItemStack spellbookStack = Utils.getPlayerSpellbookStack(inv.f_35978_);
      if (spellbookStack != null) {
         this.fromCurioSlot = true;
         this.spellBookSlot.m_5852_(spellbookStack);
      }
   }

   public Slot getSpellBookSlot() {
      return this.spellBookSlot;
   }

   public Slot getScrollSlot() {
      return this.scrollSlot;
   }

   public Slot getResultSlot() {
      return this.resultSlot;
   }

   public void m_6199_(Container pContainer) {
      super.m_6199_(pContainer);
      this.setupResultSlot();
   }

   public void setSelectedSpell(int index) {
      this.selectedSpellIndex = index;
      this.setupResultSlot();
   }

   public void doInscription(int selectedIndex) {
      ItemStack spellBookItemStack = this.getSpellBookSlot().m_7993_();
      ItemStack scrollItemStack = this.getScrollSlot().m_7993_();
      if (spellBookItemStack.m_41720_() instanceof SpellBook && scrollItemStack.m_41720_() instanceof Scroll) {
         ISpellContainer bookContainer = ISpellContainer.get(spellBookItemStack);
         ISpellContainer scrollContainer = ISpellContainer.get(scrollItemStack);
         SpellData scrollSlot = scrollContainer.getSpellAtIndex(0);
         if (bookContainer.addSpellAtIndex(scrollSlot.getSpell(), scrollSlot.getLevel(), selectedIndex, false, spellBookItemStack)) {
            this.getScrollSlot().m_6201_(1);
         }
      }
   }

   public boolean m_6366_(Player pPlayer, int pId) {
      if (pId < 0) {
         ItemStack scrollStack = this.getScrollSlot().m_7993_();
         if (this.selectedSpellIndex >= 0 && scrollStack.m_41720_() instanceof Scroll scroll) {
            SpellData spellData = ISpellContainer.get(scrollStack).getSpellAtIndex(0);
            if (MinecraftForge.EVENT_BUS.post(new InscribeSpellEvent(pPlayer, spellData))) {
               return false;
            }

            this.doInscription(this.selectedSpellIndex);
         }
      } else {
         this.setSelectedSpell(pId);
      }

      return true;
   }

   private void setupResultSlot() {
      ItemStack resultStack = ItemStack.f_41583_;
      ItemStack spellBookStack = this.spellBookSlot.m_7993_();
      if (spellBookStack.m_41720_() instanceof SpellBook) {
         ISpellContainer spellList = ISpellContainer.get(spellBookStack);
         if (this.selectedSpellIndex >= 0) {
            SpellData spellData = spellList.getSpellAtIndex(this.selectedSpellIndex);
            if (spellData != SpellData.EMPTY && spellData.canRemove()) {
               resultStack = new ItemStack((ItemLike)ItemRegistry.SCROLL.get());
               resultStack.m_41764_(1);
               ISpellContainer.createScrollContainer(spellData.getSpell(), spellData.getLevel(), resultStack);
            }
         }
      }

      if (!ItemStack.m_41728_(resultStack, this.resultSlot.m_7993_())) {
         this.resultSlot.m_5852_(resultStack);
      }
   }

   public ItemStack m_7648_(Player playerIn, int index) {
      Slot sourceSlot = (Slot)this.f_38839_.get(index);
      if (sourceSlot != null && sourceSlot.m_6657_()) {
         ItemStack sourceStack = sourceSlot.m_7993_();
         ItemStack copyOfSourceStack = sourceStack.m_41777_();
         if (index < 36) {
            if (!this.m_38903_(sourceStack, 36, 39, false)) {
               return ItemStack.f_41583_;
            }
         } else {
            if (index >= 39) {
               return ItemStack.f_41583_;
            }

            if (!this.m_38903_(sourceStack, 0, 36, false)) {
               return ItemStack.f_41583_;
            }
         }

         if (sourceStack.m_41613_() == 0) {
            sourceSlot.m_5852_(ItemStack.f_41583_);
         } else {
            sourceSlot.m_6654_();
         }

         sourceSlot.m_142406_(playerIn, sourceStack);
         return copyOfSourceStack;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public boolean m_6875_(Player pPlayer) {
      return (Boolean)this.access
         .m_39299_(
            (level, blockPos) -> !level.m_8055_(blockPos).m_60713_((Block)BlockRegistry.INSCRIPTION_TABLE_BLOCK.get())
                  ? false
                  : pPlayer.m_20275_((double)blockPos.m_123341_() + 0.5, (double)blockPos.m_123342_() + 0.5, (double)blockPos.m_123343_() + 0.5) <= 64.0,
            true
         );
   }

   private void addPlayerInventory(Inventory playerInventory) {
      for (int i = 0; i < 3; i++) {
         for (int l = 0; l < 9; l++) {
            this.m_38897_(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
         }
      }
   }

   private void addPlayerHotbar(Inventory playerInventory) {
      for (int i = 0; i < 9; i++) {
         this.m_38897_(new Slot(playerInventory, i, 8 + i * 18, 142));
      }
   }

   public void m_6877_(Player pPlayer) {
      if (this.fromCurioSlot) {
         if (!pPlayer.m_21224_() && !pPlayer.m_213877_()) {
            Utils.setPlayerSpellbookStack(pPlayer, this.spellBookSlot.m_6201_(1));
         } else {
            pPlayer.f_19853_
               .m_7967_(new ItemEntity(pPlayer.f_19853_, pPlayer.m_20185_(), pPlayer.m_20186_(), pPlayer.m_20189_(), this.spellBookSlot.m_6201_(1)));
         }
      }

      super.m_6877_(pPlayer);
      this.access.m_39292_((p_39796_, p_39797_) -> this.m_150411_(pPlayer, this.inputSlots));
   }
}
