package daripher.skilltree.client.widget.editor;

import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.client.screen.ScreenHelper;
import daripher.skilltree.client.widget.skill.SkillButton;
import daripher.skilltree.client.widget.skill.SkillTreeButtons;
import daripher.skilltree.skill.PassiveSkill;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkillSelector extends AbstractWidget {
   private static final int SELECTION_COLOR = -292164812;
   private final Set<PassiveSkill> selectedSkills = new HashSet<>();
   private final SkillTreeButtons skillButtons;
   private final SkillTreeEditor editor;
   private int selectionStartX;
   private int selectionStartY;

   public SkillSelector(SkillTreeEditor editor, SkillTreeButtons skillButtons) {
      super(0, 0, 0, 0, Component.m_237119_());
      this.skillButtons = skillButtons;
      this.editor = editor;
      this.f_93623_ = false;
   }

   public void m_6305_(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTick) {
      if (this.f_93623_) {
         this.renderSelectionArea(graphics, mouseX, mouseY);
      }

      this.renderSelectedSkillsHighlight(graphics);
   }

   private void renderSelectedSkillsHighlight(@NotNull PoseStack graphics) {
      graphics.m_85836_();
      graphics.m_85837_((double)this.skillButtons.getScrollX(), (double)this.skillButtons.getScrollY(), 0.0);
      float zoom = this.skillButtons.getZoom();

      for (SkillButton widget : this.getSelectedButtons()) {
         this.renderSkillSelection(graphics, widget, zoom);
      }

      graphics.m_85849_();
   }

   private void renderSkillSelection(@NotNull PoseStack graphics, SkillButton widget, float zoom) {
      graphics.m_85836_();
      double widgetCenterX = (double)(widget.f_93620_ + (float)widget.m_5711_() / 2.0F);
      double widgetCenterY = (double)(widget.f_93621_ + (float)widget.m_93694_() / 2.0F);
      graphics.m_85837_(widgetCenterX, widgetCenterY, 0.0);
      graphics.m_85841_(zoom, zoom, 1.0F);
      graphics.m_85837_(-widgetCenterX, -widgetCenterY, 0.0);
      int x = (int)(widget.f_93620_ - 1.0F);
      int y = (int)(widget.f_93621_ - 1.0F);
      int width = widget.m_5711_() + 2;
      int height = widget.m_93694_() + 2;
      ScreenHelper.drawRectangle(graphics, x, y, width, height, -292164812);
      graphics.m_85849_();
   }

   private void renderSelectionArea(@NotNull PoseStack graphics, int mouseX, int mouseY) {
      ScreenHelper.drawRectangle(graphics, this.selectionStartX, this.selectionStartY, mouseX - this.selectionStartX, mouseY - this.selectionStartY, -292164812);
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      if (button != 0) {
         return false;
      } else if (this.editor.getArea().contains(mouseX, mouseY)) {
         return false;
      } else if (Screen.m_96637_()) {
         return false;
      } else {
         if (Screen.m_96638_()) {
            this.f_93623_ = true;
            this.selectionStartX = (int)mouseX;
            this.selectionStartY = (int)mouseY;
         } else {
            if (!this.selectedSkills.isEmpty()) {
               this.clearSelection();
            }

            SkillButton clickedWidget = this.skillButtons.getWidgetAt(mouseX, mouseY);
            if (clickedWidget == null) {
               return false;
            }

            PassiveSkill clickedSkill = clickedWidget.skill;
            if (this.selectedSkills.contains(clickedSkill)) {
               this.selectedSkills.remove(clickedSkill);
            } else {
               this.selectedSkills.add(clickedSkill);
            }

            this.editor.rebuildWidgets();
         }

         return true;
      }
   }

   public boolean m_6348_(double mouseX, double mouseY, int button) {
      if (this.f_93623_) {
         this.addSelectedSkills(mouseX, mouseY);
         this.f_93623_ = false;
         this.editor.rebuildWidgets();
         return true;
      } else {
         return false;
      }
   }

   private void addSelectedSkills(double mouseX, double mouseY) {
      Rectangle2D selectedArea = this.getSelectionArea(mouseX, mouseY);

      for (SkillButton skillButton : this.skillButtons.getWidgets()) {
         Rectangle2D skillArea = this.getSkillArea(skillButton);
         if (selectedArea.intersects(skillArea)) {
            this.selectedSkills.add(skillButton.skill);
         }
      }

      this.editor.rebuildWidgets();
   }

   @NotNull
   private Rectangle2D getSelectionArea(double mouseX, double mouseY) {
      double selectionX = (double)((float)this.selectionStartX - this.skillButtons.getScrollX());
      double selectionY = (double)((float)this.selectionStartY - this.skillButtons.getScrollY());
      double selectionWidth = Math.abs(mouseX - (double)this.selectionStartX);
      double selectionHeight = Math.abs(mouseY - (double)this.selectionStartY);
      return new Double(selectionX, selectionY, selectionWidth, selectionHeight);
   }

   @NotNull
   private Rectangle2D getSkillArea(SkillButton skill) {
      double skillSize = (double)((float)skill.skill.getSkillSize() * this.skillButtons.getZoom());
      double skillX = (double)skill.f_93620_ + (double)skill.m_5711_() / 2.0 - skillSize / 2.0;
      double skillY = (double)skill.f_93621_ + (double)skill.m_93694_() / 2.0 - skillSize / 2.0;
      return new Double(skillX, skillY, skillSize, skillSize);
   }

   public Set<PassiveSkill> getSelectedSkills() {
      return this.selectedSkills;
   }

   public void clearSelection() {
      this.selectedSkills.clear();
      this.editor.rebuildWidgets();
   }

   @Nullable
   public PassiveSkill getFirstSelectedSkill() {
      return this.selectedSkills.isEmpty() ? null : (PassiveSkill)this.selectedSkills.toArray()[0];
   }

   @NotNull
   private List<SkillButton> getSelectedButtons() {
      return this.selectedSkills.stream().map(PassiveSkill::getId).map(this.skillButtons::getWidgetById).toList();
   }

   public void m_142291_(@NotNull NarrationElementOutput output) {
   }
}
