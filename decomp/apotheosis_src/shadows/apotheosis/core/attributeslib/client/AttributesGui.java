package shadows.apotheosis.core.attributeslib.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Registry;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.core.attributeslib.AttributesLib;
import shadows.apotheosis.core.attributeslib.api.IFormattableAttribute;
import shadows.placebo.PlaceboClient;

public class AttributesGui extends GuiComponent implements Widget, GuiEventListener {
   public static final ResourceLocation TEXTURES = Apotheosis.loc("textures/gui/attributes_gui.png");
   public static final int ENTRY_HEIGHT = 22;
   public static final int MAX_ENTRIES = 6;
   public static final int WIDTH = 131;
   public static boolean wasOpen = false;
   protected static float scrollOffset = 0.0F;
   protected static boolean hideUnchanged = false;
   protected final InventoryScreen parent;
   protected final Player player;
   protected final Font font = Minecraft.m_91087_().f_91062_;
   protected final ImageButton toggleBtn;
   protected final ImageButton recipeBookButton;
   protected final AttributesGui.HideUnchangedButton hideUnchangedBtn;
   protected int leftPos;
   protected int topPos;
   protected boolean scrolling;
   protected int startIndex;
   protected List<AttributeInstance> data = new ArrayList<>();
   @Nullable
   protected AttributeInstance selected = null;
   protected boolean open = false;
   protected long lastRenderTick = -1L;
   private static DecimalFormat f = ItemStack.f_41584_;

   public AttributesGui(InventoryScreen parent) {
      this.parent = parent;
      this.player = Minecraft.m_91087_().f_91074_;
      this.refreshData();
      this.leftPos = parent.getGuiLeft() - 131;
      this.topPos = parent.getGuiTop();
      this.toggleBtn = new ImageButton(
         parent.getGuiLeft() + 63,
         parent.getGuiTop() + 10,
         10,
         10,
         131,
         0,
         10,
         TEXTURES,
         256,
         256,
         btnx -> this.toggleVisibility(),
         Component.m_237115_("attributeslib.gui.show_attributes")
      );
      if (this.parent.m_6702_().size() > 1) {
         GuiEventListener btn = (GuiEventListener)this.parent.m_6702_().get(0);
         this.recipeBookButton = btn instanceof ImageButton imgBtn ? imgBtn : null;
      } else {
         this.recipeBookButton = null;
      }

      this.hideUnchangedBtn = new AttributesGui.HideUnchangedButton(0, 0);
   }

   public void refreshData() {
      this.data.clear();
      ForgeRegistries.ATTRIBUTES
         .getValues()
         .stream()
         .<AttributeInstance>map(this.player::m_21051_)
         .filter(Objects::nonNull)
         .filter(ai -> !hideUnchanged ? true : ai.m_22115_() != ai.m_22135_())
         .forEach(this.data::add);
      this.data.sort(this::compareAttrs);
      this.startIndex = (int)((double)(scrollOffset * (float)this.getOffScreenRows()) + 0.5);
   }

   public void toggleVisibility() {
      this.open = !this.open;
      if (this.open && this.parent.m_5564_().m_100385_()) {
         this.parent.m_5564_().m_100384_();
      }

      this.hideUnchangedBtn.f_93624_ = this.open;
      int newLeftPos;
      if (this.open && this.parent.f_96543_ >= 379) {
         newLeftPos = 177 + (this.parent.f_96543_ - this.parent.f_97726_ - 200) / 2;
      } else {
         newLeftPos = (this.parent.f_96543_ - this.parent.f_97726_) / 2;
      }

      this.parent.f_97735_ = newLeftPos;
      this.leftPos = this.parent.getGuiLeft() - 131;
      this.topPos = this.parent.getGuiTop();
      if (this.recipeBookButton != null) {
         this.recipeBookButton.m_94278_(this.parent.getGuiLeft() + 104, this.parent.f_96544_ / 2 - 22);
      }

      this.hideUnchangedBtn.m_94278_(this.leftPos + 7, this.topPos + 151);
   }

   protected int compareAttrs(AttributeInstance a1, AttributeInstance a2) {
      String name = I18n.m_118938_(a1.m_22099_().m_22087_(), new Object[0]);
      String name2 = I18n.m_118938_(a2.m_22099_().m_22087_(), new Object[0]);
      return name.compareTo(name2);
   }

   public boolean m_5953_(double pMouseX, double pMouseY) {
      return !this.open ? false : this.isHovering(0, 0, 131, 166, pMouseX, pMouseY);
   }

   public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
      this.toggleBtn.f_93620_ = this.parent.getGuiLeft() + 63;
      this.toggleBtn.f_93621_ = this.parent.getGuiTop() + 10;
      if (this.parent.m_5564_().m_100385_()) {
         this.open = false;
      }

      wasOpen = this.open;
      if (this.open) {
         if (this.lastRenderTick != PlaceboClient.ticks) {
            this.lastRenderTick = PlaceboClient.ticks;
            this.refreshData();
         }

         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157456_(0, TEXTURES);
         int left = this.leftPos;
         int top = this.topPos;
         this.m_93228_(stack, left, top, 0, 0, 131, 166);
         int scrollbarPos = (int)(117.0F * scrollOffset);
         this.m_93228_(stack, left + 111, top + 16 + scrollbarPos, 244, this.isScrollBarActive() ? 0 : 15, 12, 15);

         for (int idx = this.startIndex; idx < this.startIndex + 6 && idx < this.data.size(); idx++) {
            this.renderEntry(stack, this.data.get(idx), this.leftPos + 8, this.topPos + 16 + 22 * (idx - this.startIndex), mouseX, mouseY);
         }

         this.renderTooltip(stack, mouseX, mouseY);
         this.font.m_92889_(stack, Component.m_237115_("attributeslib.gui.attributes"), (float)(this.leftPos + 8), (float)(this.topPos + 5), 4210752);
         this.font.m_92889_(stack, Component.m_237113_("Hide Unchanged"), (float)(this.leftPos + 20), (float)(this.topPos + 152), 4210752);
      }
   }

   protected void renderTooltip(PoseStack stack, int mouseX, int mouseY) {
      AttributeInstance inst = this.getHoveredSlot(mouseX, mouseY);
      if (inst != null) {
         Attribute attr = inst.m_22099_();
         IFormattableAttribute fAttr = (IFormattableAttribute)attr;
         List<Component> list = new ArrayList<>();
         MutableComponent name = Component.m_237115_(attr.m_22087_()).m_130948_(Style.f_131099_.m_131140_(ChatFormatting.GOLD).m_131162_(true));
         if (AttributesLib.getTooltipFlag().m_7050_()) {
            Style style = Style.f_131099_.m_131140_(ChatFormatting.GRAY).m_131162_(false);
            name.m_7220_(Component.m_237113_(" [" + Registry.f_122866_.m_7981_(attr).toString() + "]").m_130948_(style));
         }

         list.add(name);
         String key = attr.m_22087_() + ".desc";
         if (I18n.m_118936_(key)) {
            Component txt = Component.m_237115_(key).m_130944_(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.ITALIC});
            list.add(txt);
         } else if (AttributesLib.getTooltipFlag().m_7050_()) {
            Component txt = Component.m_237113_(key).m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC});
            list.add(txt);
         }

         list.add(CommonComponents.f_237098_);
         ChatFormatting color = ChatFormatting.GRAY;
         if (attr instanceof RangedAttribute ra) {
            if (inst.m_22135_() > inst.m_22115_()) {
               color = ChatFormatting.YELLOW;
            } else if (inst.m_22135_() < inst.m_22115_()) {
               color = ChatFormatting.RED;
            }
         }

         MutableComponent valueComp = fAttr.toValueComponent(Operation.ADDITION, inst.m_22135_(), AttributesLib.getTooltipFlag());
         list.add(Component.m_237110_("Current: %s", new Object[]{valueComp.m_130940_(color)}).m_130940_(ChatFormatting.GRAY));
         MutableComponent baseVal = fAttr.toValueComponent(Operation.ADDITION, inst.m_22115_(), AttributesLib.getTooltipFlag());
         baseVal = Component.m_237110_("attributeslib.gui.base", new Object[]{baseVal});
         if (attr instanceof RangedAttribute rax) {
            Component min = fAttr.toValueComponent(Operation.ADDITION, rax.m_147361_(), AttributesLib.getTooltipFlag());
            min = Component.m_237110_("attributeslib.gui.min", new Object[]{min});
            Component max = fAttr.toValueComponent(Operation.ADDITION, rax.m_147362_(), AttributesLib.getTooltipFlag());
            max = Component.m_237110_("attributeslib.gui.max", new Object[]{max});
            list.add(Component.m_237110_("%s ┇ %s ┇ %s", new Object[]{baseVal, min, max}).m_130940_(ChatFormatting.GRAY));
         } else {
            list.add(baseVal.m_130940_(ChatFormatting.GRAY));
         }

         List<ClientTooltipComponent> finalTooltip = new ArrayList<>(list.size());

         for (Component txt : list) {
            this.addComp(txt, finalTooltip);
         }

         if (inst.m_22122_().stream().anyMatch(modifx -> modifx.m_22218_() != 0.0)) {
            this.addComp(CommonComponents.f_237098_, finalTooltip);
            this.addComp(Component.m_237115_("attributeslib.gui.modifiers").m_130940_(ChatFormatting.GOLD), finalTooltip);
            Map<UUID, ModifierSource<?>> modifiersToSources = new HashMap<>();

            for (ModifierSourceType<?> type : ModifierSourceType.getTypes()) {
               type.extract(this.player, (modifx, source) -> modifiersToSources.put(modifx.m_22209_(), source));
            }

            Component[] opValues = new Component[3];

            for (Operation op : Operation.values()) {
               List<AttributeModifier> modifiers = new ArrayList<>(inst.m_22104_(op));
               double opValue = modifiers.stream()
                  .mapToDouble(AttributeModifier::m_22218_)
                  .reduce(op == Operation.MULTIPLY_TOTAL ? 1.0 : 0.0, (res, elem) -> op == Operation.MULTIPLY_TOTAL ? res * (1.0 + elem) : res + elem);
               modifiers.sort(ModifierSourceType.compareBySource(modifiersToSources));

               for (AttributeModifier modif : modifiers) {
                  if (modif.m_22218_() != 0.0) {
                     Component comp = fAttr.toComponent(modif, AttributesLib.getTooltipFlag());
                     ModifierSource<?> src = modifiersToSources.get(modif.m_22209_());
                     finalTooltip.add(new AttributeModifierComponent(src, comp, this.font, this.leftPos - 16));
                  }
               }

               color = ChatFormatting.GRAY;
               double threshold = op == Operation.MULTIPLY_TOTAL ? 1.0005 : 5.0E-4;
               if (opValue > threshold) {
                  color = ChatFormatting.YELLOW;
               } else if (opValue < -threshold) {
                  color = ChatFormatting.RED;
               }

               Component valueComp2 = fAttr.toValueComponent(op, opValue, AttributesLib.getTooltipFlag()).m_130940_(color);
               Component comp = Component.m_237110_("attributeslib.gui." + op.name().toLowerCase(Locale.ROOT), new Object[]{valueComp2})
                  .m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC});
               opValues[op.ordinal()] = comp;
            }

            if (AttributesLib.getTooltipFlag().m_7050_()) {
               this.addComp(CommonComponents.f_237098_, finalTooltip);

               for (Component comp : opValues) {
                  this.addComp(comp, finalTooltip);
               }
            }
         }

         this.parent.renderTooltip(stack, List.of(), 0, 0, this.font);
         this.parent
            .m_169383_(stack, finalTooltip, this.leftPos - 16 - finalTooltip.stream().map(c -> c.m_142069_(this.font)).max(Integer::compare).get(), mouseY);
      }
   }

   private void addComp(Component comp, List<ClientTooltipComponent> finalTooltip) {
      if (comp == CommonComponents.f_237098_) {
         finalTooltip.add(ClientTooltipComponent.m_169948_(comp.m_7532_()));
      } else {
         for (FormattedText fTxt : this.font.m_92865_().m_92414_(comp, this.leftPos - 16, comp.m_7383_())) {
            finalTooltip.add(ClientTooltipComponent.m_169948_(Language.m_128107_().m_5536_(fTxt)));
         }
      }
   }

   private void renderEntry(PoseStack stack, AttributeInstance inst, int x, int y, int mouseX, int mouseY) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURES);
      boolean hover = this.getHoveredSlot(mouseX, mouseY) == inst;
      this.m_93228_(stack, x, y, 142, hover ? 22 : 0, 100, 22);
      Component txt = Component.m_237115_(inst.m_22099_().m_22087_());
      int splitWidth = 60;

      List<FormattedCharSequence> lines;
      for (lines = this.font.m_92923_(txt, splitWidth); lines.size() > 2; lines = this.font.m_92923_(txt, splitWidth)) {
         splitWidth += 10;
      }

      stack.m_85836_();
      float scale = 1.0F;
      int maxWidth = lines.stream().<Integer>map(this.font::m_92724_).max(Integer::compareTo).get();
      if (maxWidth > 66) {
         scale = 66.0F / (float)maxWidth;
         stack.m_85841_(scale, scale, 1.0F);
      }

      for (int i = 0; i < lines.size(); i++) {
         FormattedCharSequence line = lines.get(i);
         float width = (float)this.font.m_92724_(line) * scale;
         float lineX = ((float)(x + 1) + (68.0F - width) / 2.0F) / scale;
         float lineY = (float)(y + (lines.size() == 1 ? 7 : 2) + i * 10) / scale;
         this.font.m_92877_(stack, line, lineX, lineY, 4210752);
      }

      stack.m_85849_();
      stack.m_85836_();
      IFormattableAttribute attr = (IFormattableAttribute)inst.m_22099_();
      MutableComponent value = attr.toValueComponent(Operation.ADDITION, inst.m_22135_(), Default.NORMAL);
      scale = 1.0F;
      if (this.font.m_92852_(value) > 27) {
         scale = 27.0F / (float)this.font.m_92852_(value);
         stack.m_85841_(scale, scale, 1.0F);
      }

      int color = 16777215;
      if (attr instanceof RangedAttribute ra) {
         if (inst.m_22135_() > inst.m_22115_()) {
            color = 5627221;
         } else if (inst.m_22135_() < inst.m_22115_()) {
            color = 16736352;
         }
      }

      this.font.m_92763_(stack, value, ((float)(x + 72) + (27.0F - (float)this.font.m_92852_(value) * scale) / 2.0F) / scale, (float)(y + 7) / scale, color);
      stack.m_85849_();
   }

   public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
      if (this.open && this.isScrollBarActive()) {
         this.scrolling = false;
         int left = this.leftPos + 111;
         int top = this.topPos + 15;
         if (pMouseX >= (double)left && pMouseX < (double)(left + 12) && pMouseY >= (double)top && pMouseY < (double)(top + 155)) {
            this.scrolling = true;
            int i = this.topPos + 15;
            int j = i + 138;
            scrollOffset = ((float)pMouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            scrollOffset = Mth.m_14036_(scrollOffset, 0.0F, 1.0F);
            this.startIndex = (int)((double)(scrollOffset * (float)this.getOffScreenRows()) + 0.5);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean m_7979_(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
      if (!this.open) {
         return false;
      } else if (this.scrolling && this.isScrollBarActive()) {
         int i = this.topPos + 15;
         int j = i + 138;
         scrollOffset = ((float)pMouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
         scrollOffset = Mth.m_14036_(scrollOffset, 0.0F, 1.0F);
         this.startIndex = (int)((double)(scrollOffset * (float)this.getOffScreenRows()) + 0.5);
         return true;
      } else {
         return false;
      }
   }

   public boolean m_6050_(double pMouseX, double pMouseY, double pDelta) {
      if (!this.open) {
         return false;
      } else if (this.isScrollBarActive()) {
         int i = this.getOffScreenRows();
         scrollOffset = (float)((double)scrollOffset - pDelta / (double)i);
         scrollOffset = Mth.m_14036_(scrollOffset, 0.0F, 1.0F);
         this.startIndex = (int)((double)(scrollOffset * (float)i) + 0.5);
         return true;
      } else {
         return false;
      }
   }

   public boolean m_5755_(boolean pFocus) {
      return true;
   }

   private boolean isScrollBarActive() {
      return this.data.size() > 6;
   }

   protected int getOffScreenRows() {
      return Math.max(0, this.data.size() - 6);
   }

   @Nullable
   public AttributeInstance getHoveredSlot(int mouseX, int mouseY) {
      for (int i = 0; i < 6; i++) {
         if (this.startIndex + i < this.data.size() && this.isHovering(8, 14 + 22 * i, 100, 22, (double)mouseX, (double)mouseY)) {
            return this.data.get(this.startIndex + i);
         }
      }

      return null;
   }

   protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
      int i = this.leftPos;
      int j = this.topPos;
      pMouseX -= (double)i;
      pMouseY -= (double)j;
      return pMouseX >= (double)(pX - 1) && pMouseX < (double)(pX + pWidth + 1) && pMouseY >= (double)(pY - 1) && pMouseY < (double)(pY + pHeight + 1);
   }

   public static String format(int n) {
      int log = (int)StrictMath.log10((double)n);
      if (log <= 4) {
         return String.valueOf(n);
      } else if (log == 5) {
         return f.format((double)n / 1000.0) + "K";
      } else {
         return log <= 8 ? f.format((double)n / 1000000.0) + "M" : f.format((double)n / 1.0E9) + "B";
      }
   }

   public class HideUnchangedButton extends ImageButton {
      public HideUnchangedButton(int pX, int pY) {
         super(pX, pY, 10, 10, 131, 20, 10, AttributesGui.TEXTURES, 256, 256, null, Component.m_237113_("Hide Unchanged Attributes"));
         this.f_93624_ = false;
      }

      public void m_5691_() {
         AttributesGui.hideUnchanged = !AttributesGui.hideUnchanged;
      }

      public void m_6303_(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, AttributesGui.TEXTURES);
         int u = 131;
         int v = 20;
         int vOffset = AttributesGui.hideUnchanged ? 0 : 10;
         if (this.f_93622_) {
            vOffset += 20;
         }

         RenderSystem.m_69482_();
         pPoseStack.m_85836_();
         pPoseStack.m_85837_(0.0, 0.0, 100.0);
         m_93133_(pPoseStack, this.f_93620_, this.f_93621_, (float)u, (float)(v + vOffset), 10, 10, 256, 256);
         pPoseStack.m_85849_();
      }
   }
}
