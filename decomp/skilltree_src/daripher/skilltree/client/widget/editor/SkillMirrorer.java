package daripher.skilltree.client.widget.editor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import daripher.skilltree.client.screen.ScreenHelper;
import daripher.skilltree.skill.PassiveSkill;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SkillMirrorer extends AbstractWidget {
   private final SkillTreeEditor editor;
   private float mirrorCenterX;
   private float mirrorCenterY;
   private float mirrorAngle;
   private int mirrorSides = 2;

   public SkillMirrorer(SkillTreeEditor editor) {
      super(0, 0, 0, 0, Component.m_237119_());
      this.editor = editor;
      this.f_93623_ = false;
   }

   public void init() {
      this.editor.addLabel(0, 0, "Mirror", ChatFormatting.GOLD);
      this.editor.addCheckBox(186, 0, this.f_93623_).setResponder(v -> this.setActive(this.editor, v));
      this.editor.increaseHeight(19);
      if (this.f_93623_) {
         this.editor.addLabel(0, 0, "Sectors", ChatFormatting.GOLD);
         this.editor
            .addNumericTextField(160, 0, 40, 14, (double)this.mirrorSides)
            .setNumericFilter(v -> v > 1.0)
            .setNumericResponder(v -> this.mirrorSides = v.intValue());
         this.editor.increaseHeight(19);
         this.editor.addLabel(0, 0, "Angle", ChatFormatting.GOLD);
         this.editor.addNumericTextField(160, 0, 40, 14, (double)this.mirrorAngle).setNumericResponder(v -> this.mirrorAngle = v.floatValue());
         this.editor.increaseHeight(19);
         this.editor.addLabel(0, 0, "Center", ChatFormatting.GOLD);
         this.editor.addNumericTextField(160, 0, 40, 14, (double)this.mirrorCenterX).setNumericResponder(v -> this.mirrorCenterX = v.floatValue());
         this.editor.addNumericTextField(115, 0, 40, 14, (double)this.mirrorCenterY).setNumericResponder(v -> this.mirrorCenterY = v.floatValue());
         if (this.editor.getSelectedSkills().size() == 1) {
            this.editor.addButton(70, 0, 40, 14, "Set").setPressFunc(b -> this.setMirrorCenter(this.editor));
            this.editor.increaseHeight(19);
         }
      }
   }

   public void m_6305_(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTick) {
      if (this.f_93623_) {
         graphics.m_85836_();
         int width = this.editor.getScreenWidth();
         int height = this.editor.getScreenHeight();
         float mirrorX = (float)width / 2.0F + this.mirrorCenterX * this.editor.getZoom() + this.editor.getScrollX();
         float mirrorY = (float)height / 2.0F + this.mirrorCenterY * this.editor.getZoom() + this.editor.getScrollY();
         graphics.m_85837_((double)mirrorX, (double)mirrorY, 0.0);
         graphics.m_85845_(Vector3f.f_122227_.m_122240_(this.mirrorAngle));

         for (int i = 0; i < this.mirrorSides; i++) {
            graphics.m_85845_(Vector3f.f_122227_.m_122240_(360.0F / (float)this.mirrorSides));
            m_93172_(graphics, -1, -1, 1, width * 2, 1439682511);
         }

         ScreenHelper.drawRectangle(graphics, -4, -4, 8, 8, 1439682511);
         graphics.m_85849_();
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
      }
   }

   private void setActive(SkillTreeEditor editor, boolean active) {
      this.f_93623_ = active;
      editor.rebuildWidgets();
   }

   private void setMirrorCenter(SkillTreeEditor editor) {
      PassiveSkill selectedSkill = editor.getFirstSelectedSkill();
      if (selectedSkill != null) {
         this.mirrorCenterX = selectedSkill.getPositionX();
         this.mirrorCenterY = selectedSkill.getPositionY();
         editor.rebuildWidgets();
      }
   }

   @Nullable
   public PassiveSkill getMirroredSkill(PassiveSkill skill, int sector) {
      float skillX = skill.getPositionX();
      float skillY = skill.getPositionY();
      if (this.mirrorCenterX == skillX && this.mirrorCenterY == skillY) {
         return skill;
      } else {
         float originalAngle = (float)Math.toDegrees(Math.atan2((double)(skillY - this.mirrorCenterY), (double)(skillX - this.mirrorCenterX))) + 90.0F;
         float sectorSize = 360.0F / (float)this.mirrorSides;
         float angle = (float)Math.toRadians(
            this.mirrorSides == 2 ? (double)(-originalAngle + this.mirrorAngle * 2.0F) : (double)(originalAngle + sectorSize * (float)sector)
         );
         float distance = (float)Math.hypot((double)(skillX - this.mirrorCenterX), (double)(skillY - this.mirrorCenterY));
         float mirroredSkillX = this.mirrorCenterX + Mth.m_14031_(angle) * distance;
         float mirroredSkillY = this.mirrorCenterY + Mth.m_14089_((float)((double)angle + Math.PI)) * distance;
         return this.getSkillAtPosition(mirroredSkillX, mirroredSkillY);
      }
   }

   public void createSkills(float angle, float distance, SkillFactory skillFactory) {
      if (this.f_93623_) {
         float sectorSize = 360.0F / (float)this.mirrorSides;

         for (int i = 1; i < this.mirrorSides; i++) {
            angle = this.mirrorSides == 2 ? -angle - this.mirrorAngle * 2.0F : angle - sectorSize;
            float finalAngle = (float)Math.toRadians((double)angle);
            int sector = i;
            this.editor.getSelectedSkills().forEach(skill -> this.createSkill(distance, finalAngle, sector, skill, skillFactory));
         }
      }
   }

   private void createSkill(float distance, float angle, int sector, PassiveSkill skill, SkillFactory skillFactory) {
      skill = this.getMirroredSkill(skill, sector);
      if (skill != null) {
         float skillSize = (float)skill.getSkillSize() / 2.0F + 8.0F;
         float skillX = skill.getPositionX() + Mth.m_14031_(angle) * (distance + skillSize);
         float skillY = skill.getPositionY() + Mth.m_14089_(angle) * (distance + skillSize);
         skillFactory.accept(skillX, skillY, skill);
      }
   }

   @Nullable
   private PassiveSkill getSkillAtPosition(float x, float y) {
      for (PassiveSkill skill : this.editor.getSkills()) {
         double distance = Math.hypot((double)(x - skill.getPositionX()), (double)(y - skill.getPositionY()));
         if (distance < (double)skill.getSkillSize()) {
            return skill;
         }
      }

      return null;
   }

   public void m_142291_(@NotNull NarrationElementOutput output) {
   }
}
