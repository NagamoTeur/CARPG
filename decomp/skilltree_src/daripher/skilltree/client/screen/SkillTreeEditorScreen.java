package daripher.skilltree.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.client.data.SkillTreeClientData;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.client.widget.editor.menu.SkillNodeEditor;
import daripher.skilltree.client.widget.skill.SkillTreeButtons;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.PassiveSkillTree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SkillTreeEditorScreen extends Screen {
   private final PassiveSkillTree skillTree;
   private final SkillTreeButtons skillButtons;
   private final SkillTreeEditor editorWidgets;
   private boolean shouldCloseOnEsc = true;
   private int prevMouseX;
   private int prevMouseY;

   public SkillTreeEditorScreen(ResourceLocation skillTreeId) {
      super(Component.m_237119_());
      this.f_96541_ = Minecraft.m_91087_();
      this.skillTree = SkillTreeClientData.getOrCreateEditorTree(skillTreeId);
      this.skillButtons = new SkillTreeButtons(this.skillTree, () -> 0.0F);
      this.editorWidgets = new SkillTreeEditor(this.skillButtons);
   }

   public void m_7856_() {
      if (this.skillTree == null) {
         this.getMinecraft().m_91152_(null);
      } else {
         this.m_169413_();
         this.skillButtons.m_93674_(this.f_96543_);
         this.skillButtons.setHeight(this.f_96544_);
         this.editorWidgets.m_93674_(210);
         this.editorWidgets.setHeight(10);
         this.editorWidgets.f_93620_ = this.f_96543_ - this.editorWidgets.m_5711_();
         this.editorWidgets.init();
         this.editorWidgets.increaseHeight(5);
         this.editorWidgets.setRebuildFunc(this::m_232761_);
         this.skillButtons.setRebuildFunc(this::m_232761_);
         this.skillButtons.clearWidgets();
         this.editorWidgets.getSkills().forEach(this.editorWidgets::addSkillButton);
         this.skillButtons.updateSkillConnections();
         this.calculateMaxScroll();
         this.m_142416_(this.skillButtons);
         this.m_142416_(this.editorWidgets);
      }
   }

   protected void m_232761_() {
      this.getMinecraft().m_6937_(() -> super.m_232761_());
   }

   private void calculateMaxScroll() {
      this.skillButtons.setMaxScrollX(Math.min(0, this.f_96543_ / 2 - 350));
      this.skillButtons.setMaxScrollY(Math.min(0, this.f_96544_ / 2 - 350));
      this.skillButtons.getWidgets().forEach(button -> {
         float skillX = button.skill.getPositionX();
         float skillY = button.skill.getPositionY();
         int maxScrollX = (int)Math.max((float)this.skillButtons.getMaxScrollX(), Mth.m_14154_(skillX));
         int maxScrollY = (int)Math.max((float)this.skillButtons.getMaxScrollY(), Mth.m_14154_(skillY));
         this.skillButtons.setMaxScrollX(maxScrollX);
         this.skillButtons.setMaxScrollY(maxScrollY);
      });
   }

   public void m_6305_(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTick) {
      this.m_7333_(graphics);
      this.skillButtons.m_6305_(graphics, mouseX, mouseY, partialTick);
      this.renderOverlay(graphics);
      this.editorWidgets.m_6305_(graphics, mouseX, mouseY, partialTick);
      if (mouseX < this.editorWidgets.f_93620_ || mouseY > this.editorWidgets.m_93694_()) {
         float tooltipX = (float)mouseX + (float)(this.prevMouseX - mouseX) * partialTick;
         float tooltipY = (float)mouseY + (float)(this.prevMouseY - mouseY) * partialTick;
         this.skillButtons.renderTooltip(graphics, tooltipX, tooltipY);
      }

      this.prevMouseX = mouseX;
      this.prevMouseY = mouseY;
   }

   private void createBlankSkill() {
      ResourceLocation background = new ResourceLocation("skilltree", "textures/icons/background/lesser.png");
      ResourceLocation icon = new ResourceLocation("skilltree", "textures/icons/void.png");
      ResourceLocation border = new ResourceLocation("skilltree", "textures/tooltip/lesser.png");
      ResourceLocation skillId = SkillNodeEditor.createNewSkillId();
      PassiveSkill skill = new PassiveSkill(skillId, 16, background, icon, border, false);
      skill.setPosition(0.0F, 0.0F);
      SkillTreeClientData.saveEditorSkill(skill);
      SkillTreeClientData.loadEditorSkill(skill.getId());
      this.editorWidgets.getSkillTree().getSkillIds().add(skill.getId());
      SkillTreeClientData.saveEditorSkillTree(this.editorWidgets.getSkillTree());
   }

   public boolean m_6913_() {
      if (!this.shouldCloseOnEsc) {
         this.shouldCloseOnEsc = true;
         return false;
      } else {
         return super.m_6913_();
      }
   }

   public void m_86600_() {
      this.editorWidgets.onWidgetTick();
   }

   private void renderOverlay(PoseStack poseStack) {
      ScreenHelper.prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/skill_tree_overlay.png"));
      m_93143_(poseStack, 0, 0, 0, 0.0F, 0.0F, this.f_96543_, this.f_96544_, this.f_96543_, this.f_96544_);
   }

   public void m_7333_(PoseStack poseStack) {
      ScreenHelper.prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/skill_tree_background.png"));
      poseStack.m_85836_();
      poseStack.m_85837_((double)(this.skillButtons.getScrollX() / 3.0F), (double)(this.skillButtons.getScrollY() / 3.0F), 0.0);
      int size = 2048;
      m_93143_(poseStack, (this.f_96543_ - size) / 2, (this.f_96544_ - size) / 2, 0, 0.0F, 0.0F, size, size, size, size);
      poseStack.m_85849_();
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      return this.editorWidgets.m_6375_(mouseX, mouseY, button);
   }

   public boolean m_6348_(double mouseX, double mouseY, int button) {
      return this.editorWidgets.m_6348_(mouseX, mouseY, button);
   }

   public boolean m_6050_(double mouseX, double mouseY, double amount) {
      return this.editorWidgets.m_6050_(mouseX, mouseY, amount) || this.skillButtons.m_6050_(mouseX, mouseY, amount);
   }

   public boolean m_7979_(double mouseX, double mouseY, int button, double dragX, double dragY) {
      return this.editorWidgets.m_7979_(mouseX, mouseY, button, dragX, dragY) | this.skillButtons.m_7979_(mouseX, mouseY, button, dragX, dragY);
   }

   public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
      if (this.editorWidgets.m_7933_(keyCode, scanCode, modifiers)) {
         if (keyCode == 256) {
            this.shouldCloseOnEsc = false;
         }

         return true;
      } else if (keyCode == 256 && this.m_6913_()) {
         this.m_7379_();
         return true;
      } else if (keyCode == 78 && Screen.m_96637_()) {
         this.createBlankSkill();
         this.m_232761_();
         return true;
      } else {
         return false;
      }
   }

   public boolean m_5534_(char codePoint, int modifiers) {
      return this.editorWidgets.m_5534_(codePoint, modifiers);
   }
}
