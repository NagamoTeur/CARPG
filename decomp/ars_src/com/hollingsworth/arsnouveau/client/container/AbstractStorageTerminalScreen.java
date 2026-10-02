package com.hollingsworth.arsnouveau.client.container;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.gui.NoShadowTextField;
import com.hollingsworth.arsnouveau.client.gui.book.BaseBook;
import com.hollingsworth.arsnouveau.client.gui.buttons.StateButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.StorageSettingsButton;
import com.hollingsworth.arsnouveau.client.gui.buttons.StorageTabButton;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.TooltipFlag.Default;
import org.lwjgl.glfw.GLFW;

public abstract class AbstractStorageTerminalScreen<T extends StorageTerminalMenu> extends AbstractContainerScreen<T> {
   private static final LoadingCache<StoredItemStack, List<String>> tooltipCache = CacheBuilder.newBuilder()
      .expireAfterAccess(5L, TimeUnit.SECONDS)
      .build(
         new CacheLoader<StoredItemStack, List<String>>() {
            public List<String> load(StoredItemStack key) throws Exception {
               return key.getStack()
                  .m_41651_(Minecraft.m_91087_().f_91074_, AbstractStorageTerminalScreen.getTooltipFlag())
                  .stream()
                  .<String>map(Component::getString)
                  .collect(Collectors.toList());
            }
         }
      );
   protected Minecraft mc = Minecraft.m_91087_();
   protected float currentScroll;
   protected boolean isScrolling;
   private boolean refreshItemList;
   protected boolean wasClicking;
   protected NoShadowTextField searchField;
   protected int slotIDUnderMouse = -1;
   protected int controllMode;
   protected int rowCount;
   protected int searchType;
   protected boolean expanded;
   private String searchLast = "";
   protected boolean loadedSearch = false;
   private StoredItemStack.IStoredItemStackComparator comparator = new StoredItemStack.ComparatorAmount(false);
   protected static final ResourceLocation scrollBall = new ResourceLocation("ars_nouveau", "textures/gui/scroll_ball.png");
   protected static final ResourceLocation tabImages = new ResourceLocation("ars_nouveau", "textures/gui/bookwyrm_storage_tabs.png");
   protected StateButton buttonSortingType;
   protected StateButton buttonDirection;
   protected StateButton buttonSearchType;
   private Comparator<StoredItemStack> sortComp;
   List<String> tabNames = new ArrayList<>();
   public List<StorageTabButton> tabButtons = new ArrayList<>();
   public String selectedTab = null;
   private FakeSlot fakeSlotUnderMouse = new FakeSlot();

   public AbstractStorageTerminalScreen(T screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);
      screenContainer.onPacket = this::onPacket;
   }

   protected void onPacket() {
      SortSettings s = ((StorageTerminalMenu)this.f_97732_).terminalData;
      if (s != null) {
         this.controllMode = s.controlMode;
         this.comparator = StoredItemStack.SortingTypes.VALUES[s.sortType % StoredItemStack.SortingTypes.VALUES.length].create(s.reverseSort);
         this.searchType = s.searchType;
         this.buttonSortingType.state = s.sortType;
         this.buttonDirection.state = s.reverseSort ? 1 : 0;
         this.buttonSearchType.state = this.searchType;
         this.expanded = s.expanded;
      }

      if (((StorageTerminalMenu)this.f_97732_).tabNames != null && !((StorageTerminalMenu)this.f_97732_).tabNames.isEmpty()) {
         for (StorageTabButton tabButton : this.tabButtons) {
            tabButton.f_93624_ = false;
         }

         this.tabButtons.get(0).f_93624_ = true;
         List<String> names = new ArrayList<>(new HashSet<>(((StorageTerminalMenu)this.f_97732_).tabNames));
         names.sort(String::compareToIgnoreCase);

         for (int i = 0; i < names.size() && i < this.tabButtons.size(); i++) {
            this.tabButtons.get(i + 1).f_93624_ = true;
            this.tabButtons.get(i + 1).highlightText = names.get(i);
         }
      }

      if (!this.loadedSearch && ((StorageTerminalMenu)this.f_97732_).search != null) {
         this.loadedSearch = true;
         this.searchField.m_94144_(((StorageTerminalMenu)this.f_97732_).search);
         this.searchField.m_94178_(true);
         if (this.searchField.m_94155_().isEmpty()) {
            this.searchField.m_94167_(Component.m_237115_("ars_nouveau.spell_book_gui.search").getString());
         } else {
            this.searchField.m_94167_("");
         }
      }
   }

   protected void sendUpdate() {
      CompoundTag c = new CompoundTag();
      c.m_128365_("sortSettings", this.getSortSettings().toTag());
      StorageTabButton selectedTabButton = this.tabButtons.stream().filter(i -> i.f_93624_ && i.isSelected).findFirst().orElse(null);
      if (selectedTabButton != null && selectedTabButton.highlightText != null) {
         c.m_128359_("selectedTab", selectedTabButton.highlightText);
      }

      CompoundTag msg = new CompoundTag();
      msg.m_128365_("termData", c);
      ((StorageTerminalMenu)this.f_97732_).sendMessage(msg);
   }

   public SortSettings getSortSettings() {
      return new SortSettings(this.controllMode, this.comparator.isReversed(), this.comparator.type(), this.searchType, this.expanded);
   }

   protected void m_7856_() {
      this.m_169413_();
      this.f_97731_ = this.f_97727_ - 92;
      super.m_7856_();
      this.searchField = new NoShadowTextField(
         this.getFont(), this.f_97735_ + 115, this.f_97736_ + 6, 60, 9, Component.m_237115_("narrator.ars_nouveau.search")
      );
      this.searchField.m_94199_(100);
      this.searchField.m_94182_(false);
      this.searchField.m_94194_(true);
      this.searchField.m_94144_(this.searchLast);
      this.searchLast = "";
      this.m_142416_(this.searchField);
      this.buttonSortingType = (StateButton)this.m_142416_(
         new StorageSettingsButton(
            this.f_97735_ - 17,
            this.f_97736_ + 14,
            22,
            12,
            44,
            13,
            0,
            new ResourceLocation("ars_nouveau", "textures/gui/sort_type.png"),
            b -> {
               this.comparator = StoredItemStack.SortingTypes.VALUES[(this.comparator.type() + 1) % StoredItemStack.SortingTypes.VALUES.length]
                  .create(this.comparator.isReversed());
               this.buttonSortingType.state = this.comparator.type();
               this.sendUpdate();
               this.refreshItemList = true;
            }
         )
      );
      this.buttonDirection = (StateButton)this.m_142416_(
         new StorageSettingsButton(
            this.f_97735_ - 17, this.f_97736_ + 29, 22, 12, 44, 13, 0, new ResourceLocation("ars_nouveau", "textures/gui/sort_order.png"), b -> {
               this.comparator.setReversed(!this.comparator.isReversed());
               this.buttonDirection.state = this.comparator.isReversed() ? 1 : 0;
               this.sendUpdate();
               this.refreshItemList = true;
            }
         )
      );
      this.buttonSearchType = (StateButton)this.m_142416_(
         new StorageSettingsButton(
            this.f_97735_ - 17, this.f_97736_ + 44, 22, 12, 44, 13, 0, new ResourceLocation("ars_nouveau", "textures/gui/search_sync.png"), b -> {
               this.searchType = this.searchType == 0 ? 1 : 0;
               this.buttonSearchType.state = this.searchType;
               this.sendUpdate();
            }
         )
      );

      for (int i = 0; i < 12; i++) {
         StorageTabButton button = (StorageTabButton)this.m_142416_(
            new StorageTabButton(this.f_97735_ - 13, this.f_97736_ + 59 + i * 15, 18, 12, 256, 13, i, 0, tabImages, b -> {
               StorageTabButton tabButton = (StorageTabButton)b;
               this.setSelectedTab(tabButton.state);
               this.sendUpdate();
            })
         );
         button.f_93624_ = false;
         if (i == 0) {
            button.isAll = true;
            button.isSelected = true;
         }

         this.tabButtons.add(button);
      }

      this.updateSearch();
   }

   public void setSelectedTab(int index) {
      for (int i = 0; i < this.tabButtons.size(); i++) {
         this.tabButtons.get(i).isSelected = i == index;
      }
   }

   protected void updateSearch() {
      String searchString = this.searchField.m_94155_().trim();
      if (this.searchField.m_94155_().isEmpty()) {
         this.searchField.m_94167_(Component.m_237115_("ars_nouveau.spell_book_gui.search").getString());
      } else {
         this.searchField.m_94167_("");
      }

      if (this.refreshItemList || !this.searchLast.equals(searchString)) {
         ((StorageTerminalMenu)this.m_6262_()).itemListClientSorted.clear();
         boolean searchMod = false;
         String search = searchString;
         if (searchString.startsWith("@")) {
            searchMod = true;
            search = searchString.substring(1);
         }

         Pattern m = null;

         try {
            m = Pattern.compile(search.toLowerCase(), 2);
         } catch (Throwable var12) {
            try {
               m = Pattern.compile(Pattern.quote(search.toLowerCase()), 2);
            } catch (Throwable var11) {
               return;
            }
         }

         try {
            for (int i = 0; i < ((StorageTerminalMenu)this.m_6262_()).itemListClient.size(); i++) {
               StoredItemStack is = ((StorageTerminalMenu)this.m_6262_()).itemListClient.get(i);
               if (is != null && is.getStack() != null) {
                  String dspName = searchMod ? TerminalSyncManager.getItemId(is.getStack().m_41720_()).m_135827_() : is.getStack().m_41786_().getString();
                  boolean notDone = true;
                  if (m.matcher(dspName.toLowerCase()).find()) {
                     this.addStackToClientList(is);
                     notDone = false;
                  }

                  if (notDone) {
                     for (String lp : (List)tooltipCache.get(is)) {
                        if (m.matcher(lp).find()) {
                           this.addStackToClientList(is);
                           break;
                        }
                     }
                  }
               }
            }
         } catch (Exception var13) {
         }

         Object var10001 = ((StorageTerminalMenu)this.f_97732_).noSort ? this.sortComp : this.comparator;
         Collections.sort(((StorageTerminalMenu)this.m_6262_()).itemListClientSorted, (Comparator<? super StoredItemStack>)var10001);
         if (!this.searchLast.equals(searchString)) {
            ((StorageTerminalMenu)this.m_6262_()).scrollTo(0.0F);
            this.currentScroll = 0.0F;
            if (this.searchType == 1) {
               IAutoFillTerminal.sync(searchString);
            }

            CompoundTag nbt = new CompoundTag();
            nbt.m_128359_("search", searchString);
            ((StorageTerminalMenu)this.f_97732_).sendMessage(nbt);
            this.onUpdateSearch(searchString);
         } else {
            ((StorageTerminalMenu)this.m_6262_()).scrollTo(this.currentScroll);
         }

         this.refreshItemList = false;
         this.searchLast = searchString;
      }
   }

   private void addStackToClientList(StoredItemStack is) {
      ((StorageTerminalMenu)this.m_6262_()).itemListClientSorted.add(is);
   }

   public static TooltipFlag getTooltipFlag() {
      return Minecraft.m_91087_().f_91066_.f_92125_ ? Default.ADVANCED : Default.NORMAL;
   }

   protected void m_181908_() {
      this.updateSearch();
   }

   public void m_6305_(PoseStack st, int mouseX, int mouseY, float partialTicks) {
      boolean flag = GLFW.glfwGetMouseButton(Minecraft.m_91087_().m_91268_().m_85439_(), 0) != 0;
      int i = this.f_97735_;
      int j = this.f_97736_;
      int k = i + 187;
      int l = j + 18;
      int i1 = k + 14;
      int j1 = l + this.rowCount * 18;
      if (m_96638_()) {
         if (!((StorageTerminalMenu)this.f_97732_).noSort) {
            List<StoredItemStack> list = ((StorageTerminalMenu)this.m_6262_()).itemListClientSorted;
            Object2IntMap<StoredItemStack> map = new Object2IntOpenHashMap();
            map.defaultReturnValue(Integer.MAX_VALUE);

            for (int m = 0; m < list.size(); m++) {
               map.put(list.get(m), m);
            }

            this.sortComp = Comparator.comparing(map::getInt);
            ((StorageTerminalMenu)this.f_97732_).noSort = true;
         }
      } else if (((StorageTerminalMenu)this.f_97732_).noSort) {
         this.sortComp = null;
         ((StorageTerminalMenu)this.f_97732_).noSort = false;
         this.refreshItemList = true;
         ((StorageTerminalMenu)this.f_97732_).itemListClient = new ArrayList<>(((StorageTerminalMenu)this.f_97732_).itemList);
      }

      if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1) {
         this.isScrolling = this.needsScrollBars();
      }

      if (!flag) {
         this.isScrolling = false;
      }

      this.wasClicking = flag;
      if (this.isScrolling) {
         this.currentScroll = ((float)(mouseY - l) - 7.5F) / ((float)(j1 - l) - 15.0F);
         this.currentScroll = Mth.m_14036_(this.currentScroll, 0.0F, 1.0F);
         ((StorageTerminalMenu)this.m_6262_()).scrollTo(this.currentScroll);
      }

      super.m_6305_(st, mouseX, mouseY, partialTicks);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, scrollBall);
      m_93133_(st, k, l + 3 + (int)((float)(j1 - l - 14) * this.currentScroll), 0.0F, 0.0F, 12, 12, 12, 12);
      if (((StorageTerminalMenu)this.f_97732_).m_142621_().m_41619_() && this.slotIDUnderMouse != -1) {
         SlotStorage slot = ((StorageTerminalMenu)this.m_6262_()).storageSlotList.get(this.slotIDUnderMouse);
         if (slot.stack != null) {
            if (slot.stack.getQuantity() > 9999L) {
               ClientInfo.setTooltip(Component.m_237110_("tooltip.ars_nouveau.amount", new Object[]{slot.stack.getQuantity()}));
            }

            this.m_6057_(st, slot.stack.getActualStack(), mouseX, mouseY);
            ClientInfo.setTooltip();
         }
      } else {
         this.m_7025_(st, mouseX, mouseY);
      }

      if (this.buttonSortingType.m_198029_()) {
         this.m_96602_(st, Component.m_237115_("tooltip.ars_nouveau.sorting_" + this.buttonSortingType.state), mouseX, mouseY);
      }

      if (this.buttonSearchType.m_198029_()) {
         this.m_96602_(
            st,
            Component.m_237110_("tooltip.ars_nouveau.search_" + this.buttonSearchType.state, new Object[]{IAutoFillTerminal.getHandlerName()}),
            mouseX,
            mouseY
         );
      }

      if (this.buttonDirection.m_198029_()) {
         this.m_96602_(
            st,
            Component.m_237110_("tooltip.ars_nouveau.direction_" + this.buttonDirection.state, new Object[]{IAutoFillTerminal.getHandlerName()}),
            mouseX,
            mouseY
         );
      }

      for (StorageTabButton tabButton : this.tabButtons) {
         if (tabButton.m_198029_() && tabButton.isAll) {
            this.m_96602_(st, Component.m_237115_("tooltip.ars_nouveau.master_tab"), mouseX, mouseY);
         } else if (tabButton.m_198029_() && tabButton.highlightText != null) {
            this.m_96602_(st, Component.m_237113_(tabButton.highlightText), mouseX, mouseY);
         }
      }
   }

   protected void m_7027_(PoseStack st, int mouseX, int mouseY) {
      st.m_85836_();
      this.slotIDUnderMouse = this.drawSlots(st, mouseX, mouseY);
      st.m_85849_();
   }

   protected int drawSlots(PoseStack st, int mouseX, int mouseY) {
      StorageTerminalMenu term = (StorageTerminalMenu)this.m_6262_();
      int slotHover = -1;

      for (int i = 0; i < term.storageSlotList.size(); i++) {
         if (this.drawSlot(st, term.storageSlotList.get(i), mouseX, mouseY)) {
            slotHover = i;
         }
      }

      return slotHover;
   }

   protected boolean drawSlot(PoseStack st, SlotStorage slot, int mouseX, int mouseY) {
      if (slot.stack != null) {
         this.m_93250_(100);
         this.f_96542_.f_115093_ = 100.0F;
         ItemStack stack = slot.stack.getStack().m_41777_().m_41620_(1);
         int i = slot.xDisplayPosition;
         int j = slot.yDisplayPosition;
         this.f_96542_.m_174229_(this.f_96541_.f_91074_, stack, i, j, 0);
         this.f_96542_.m_115174_(this.f_96547_, stack, i, j, null);
         this.drawStackSize(st, this.getFont(), slot.stack.getQuantity(), i, j);
         this.f_96542_.f_115093_ = 0.0F;
         this.m_93250_(0);
      }

      if (mouseX >= this.getGuiLeft() + slot.xDisplayPosition - 1
         && mouseY >= this.getGuiTop() + slot.yDisplayPosition - 1
         && mouseX < this.getGuiLeft() + slot.xDisplayPosition + 17
         && mouseY < this.getGuiTop() + slot.yDisplayPosition + 17) {
         int l = slot.xDisplayPosition;
         int t = slot.yDisplayPosition;
         m_169606_(st, l, t, this.m_93252_());
         return true;
      } else {
         return false;
      }
   }

   private void drawStackSize(PoseStack st, Font fr, long size, int x, int y) {
      float scaleFactor = 0.6F;
      RenderSystem.m_69465_();
      RenderSystem.m_69461_();
      String stackSize = NumberFormatUtil.formatNumber(size);
      st.m_85836_();
      st.m_85841_(scaleFactor, scaleFactor, scaleFactor);
      st.m_85837_(0.0, 0.0, 450.0);
      float inverseScaleFactor = 1.0F / scaleFactor;
      int X = (int)(((float)x + 0.0F + 16.0F - (float)fr.m_92895_(stackSize) * scaleFactor) * inverseScaleFactor);
      int Y = (int)(((float)y + 0.0F + 16.0F - 7.0F * scaleFactor) * inverseScaleFactor);
      fr.m_92750_(st, stackSize, (float)X, (float)Y, 16777215);
      st.m_85849_();
      RenderSystem.m_69482_();
   }

   protected boolean needsScrollBars() {
      return ((StorageTerminalMenu)this.m_6262_()).itemListClientSorted.size() > this.rowCount * 9;
   }

   public boolean m_6375_(double mouseX, double mouseY, int mouseButton) {
      this.searchField.m_6375_(mouseX, mouseY, mouseButton);
      if (this.slotIDUnderMouse > -1) {
         if (this.isPullOne(mouseButton)) {
            if (((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack != null
               && ((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack.getQuantity() > 0L) {
               this.storageSlotClick(
                  ((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack,
                  StorageTerminalMenu.SlotAction.PULL_ONE,
                  this.isTransferOne(mouseButton)
               );
               return true;
            }

            return true;
         }

         if (this.pullHalf(mouseButton)) {
            if (!((StorageTerminalMenu)this.f_97732_).m_142621_().m_41619_()) {
               this.storageSlotClick(null, m_96637_() ? StorageTerminalMenu.SlotAction.GET_QUARTER : StorageTerminalMenu.SlotAction.GET_HALF, false);
            } else if (((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack != null
               && ((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack.getQuantity() > 0L) {
               this.storageSlotClick(
                  ((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack,
                  m_96637_() ? StorageTerminalMenu.SlotAction.GET_QUARTER : StorageTerminalMenu.SlotAction.GET_HALF,
                  false
               );
               return true;
            }
         } else if (this.pullNormal(mouseButton)) {
            if (!((StorageTerminalMenu)this.f_97732_).m_142621_().m_41619_()) {
               this.storageSlotClick(null, StorageTerminalMenu.SlotAction.PULL_OR_PUSH_STACK, false);
            } else if (((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack != null
               && ((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack.getQuantity() > 0L) {
               this.storageSlotClick(
                  ((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack,
                  m_96638_() ? StorageTerminalMenu.SlotAction.SHIFT_PULL : StorageTerminalMenu.SlotAction.PULL_OR_PUSH_STACK,
                  false
               );
               return true;
            }
         }
      } else if (GLFW.glfwGetKey(this.mc.m_91268_().m_85439_(), 32) != 0) {
         this.storageSlotClick(null, StorageTerminalMenu.SlotAction.SPACE_CLICK, false);
      }

      return super.m_6375_(mouseX, mouseY, mouseButton);
   }

   protected void storageSlotClick(StoredItemStack slotStack, StorageTerminalMenu.SlotAction act, boolean pullOne) {
      ((StorageTerminalMenu)this.f_97732_).sync.sendClientInteract(slotStack, act, pullOne);
   }

   public boolean isPullOne(int mouseButton) {
      return mouseButton == 1 && m_96638_();
   }

   public boolean isTransferOne(int mouseButton) {
      return m_96638_() && m_96637_();
   }

   public boolean pullHalf(int mouseButton) {
      return mouseButton == 1;
   }

   public boolean pullNormal(int mouseButton) {
      return mouseButton == 0;
   }

   public Font getFont() {
      return this.f_96547_;
   }

   public boolean m_7933_(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      if (p_keyPressed_1_ == 256) {
         this.m_7379_();
         return true;
      } else {
         return this.searchField.m_7933_(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)
            || this.searchField.m_94204_()
            || super.m_7933_(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
      }
   }

   public boolean m_5534_(char p_charTyped_1_, int p_charTyped_2_) {
      return this.searchField.m_5534_(p_charTyped_1_, p_charTyped_2_) ? true : super.m_5534_(p_charTyped_1_, p_charTyped_2_);
   }

   public boolean m_6050_(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
      if (!this.needsScrollBars()) {
         return false;
      } else {
         int i = (((StorageTerminalMenu)this.f_97732_).itemListClientSorted.size() + 9 - 1) / 9 - 5;
         this.currentScroll = (float)((double)this.currentScroll - p_mouseScrolled_5_ / (double)i);
         this.currentScroll = Mth.m_14036_(this.currentScroll, 0.0F, 1.0F);
         ((StorageTerminalMenu)this.f_97732_).scrollTo(this.currentScroll);
         return true;
      }
   }

   public abstract ResourceLocation getGui();

   protected void m_7286_(PoseStack st, float partialTicks, int mouseX, int mouseY) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, this.getGui());
      this.m_93228_(st, this.f_97735_, this.f_97736_, 0, 0, this.f_97726_, this.f_97727_);
      BaseBook.drawFromTexture(
         new ResourceLocation("ars_nouveau", "textures/gui/search_paper.png"), this.f_97735_ + 102, this.f_97736_ + 3, 0, 0, 72, 15, 72, 15, st
      );
   }

   protected void onUpdateSearch(String text) {
   }

   public void receive(CompoundTag tag) {
      ((StorageTerminalMenu)this.f_97732_).receiveClientNBTPacket(tag);
      this.refreshItemList = true;
   }

   public Slot getSlotUnderMouse() {
      Slot s = super.getSlotUnderMouse();
      if (s != null) {
         return s;
      } else if (this.slotIDUnderMouse > -1 && ((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack != null) {
         this.fakeSlotUnderMouse.f_40218_.m_6836_(0, ((StorageTerminalMenu)this.m_6262_()).getSlotByID(this.slotIDUnderMouse).stack.getStack());
         return this.fakeSlotUnderMouse;
      } else {
         return null;
      }
   }
}
