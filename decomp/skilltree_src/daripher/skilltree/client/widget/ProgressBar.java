package daripher.skilltree.client.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.capability.skill.IPlayerSkills;
import daripher.skilltree.capability.skill.PlayerSkillsProvider;
import daripher.skilltree.client.data.SkillTreeClientData;
import daripher.skilltree.client.screen.ScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ProgressBar extends net.minecraft.client.gui.components.Button {
   public boolean showProgressInNumbers;

   public ProgressBar(int x, int y, OnPress pressFunc) {
      super(x, y, 235, 19, Component.m_237119_(), pressFunc);
   }

   private static int getCurrentLevel() {
      IPlayerSkills capability = PlayerSkillsProvider.get(getLocalPlayer());
      int skills = capability.getPlayerSkills().size();
      int points = capability.getSkillPoints();
      return skills + points;
   }

   private static boolean isMaxLevel(int currentLevel) {
      return currentLevel >= SkillTreeClientData.max_skill_points;
   }

   public void m_6303_(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(poseStack);
      this.renderCurrentLevel(poseStack);
      this.renderNextLevel(poseStack);
      this.renderProgress(poseStack);
   }

   protected void renderBackground(PoseStack poseStack) {
      float experienceProgress = this.getExperienceProgress();
      int filledBarWidth = (int)(experienceProgress * 183.0F);
      ScreenHelper.prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/progress_bars.png"));
      this.m_93228_(poseStack, this.f_93620_ + 26, this.f_93621_ + 7, 0, 0, 182, 5);
      if (filledBarWidth != 0) {
         this.m_93228_(poseStack, this.f_93620_ + 26, this.f_93621_ + 7, 0, 5, filledBarWidth, 5);
      }
   }

   protected void renderProgress(PoseStack poseStack) {
      if (this.showProgressInNumbers) {
         int cost = SkillTreeClientData.getSkillPointCost(getCurrentLevel());
         int exp = getLocalPlayer().f_36079_;
         String text = exp + "/" + cost;
         ScreenHelper.drawCenteredOutlinedText(poseStack, text, this.f_93620_ + this.f_93618_ / 2, this.getTextY(), 16573030);
      } else {
         float experienceProgress = this.getExperienceProgress();
         String text = (int)(experienceProgress * 100.0F) + "%";
         ScreenHelper.drawCenteredOutlinedText(poseStack, text, this.f_93620_ + this.f_93618_ / 2, this.getTextY(), 16573030);
      }
   }

   protected void renderNextLevel(PoseStack poseStack) {
      int currentLevel = getCurrentLevel();
      if (isMaxLevel(currentLevel)) {
         currentLevel--;
      }

      int nextLevel = currentLevel + 1;
      ScreenHelper.drawCenteredOutlinedText(poseStack, nextLevel + "", this.f_93620_ + this.f_93618_ - 17, this.getTextY(), 16573030);
   }

   protected void renderCurrentLevel(PoseStack poseStack) {
      int currentLevel = getCurrentLevel();
      if (isMaxLevel(currentLevel)) {
         currentLevel--;
      }

      ScreenHelper.drawCenteredOutlinedText(poseStack, currentLevel + "", this.f_93620_ + 17, this.getTextY(), 16573030);
   }

   protected int getTextY() {
      return this.f_93621_ + 5;
   }

   private float getExperienceProgress() {
      int level = getCurrentLevel();
      float progress = 1.0F;
      if (level < SkillTreeClientData.max_skill_points) {
         int levelupCost = SkillTreeClientData.getSkillPointCost(level);
         progress = (float)getLocalPlayer().f_36079_ / (float)levelupCost;
         progress = Math.min(1.0F, progress);
      }

      return progress;
   }

   private static LocalPlayer getLocalPlayer() {
      return Minecraft.m_91087_().f_91074_;
   }
}
