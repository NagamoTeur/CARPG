package daripher.skilltree.client.widget.skill;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import daripher.skilltree.client.screen.ScreenHelper;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.config.ClientConfig;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.PassiveSkillTree;
import daripher.skilltree.skill.bonus.SkillBonus;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SkillButton extends Button {
   private static final Style LESSER_TITLE_STYLE = Style.f_131099_.m_178520_(15376745);
   private static final Style NOTABLE_TITLE_STYLE = Style.f_131099_.m_178520_(10184408);
   private static final Style CLASS_TITLE_STYLE = Style.f_131099_.m_178520_(16766815);
   private static final Style KEYSTONE_TITLE_STYLE = Style.f_131099_.m_178520_(15430960);
   private static final Style GATEWAY_TITLE_STYLE = Style.f_131099_.m_178520_(8689302);
   private static final Style DESCRIPTION_STYLE = Style.f_131099_.m_178520_(8092645);
   private static final Style ID_STYLE = Style.f_131099_.m_178520_(5526612);
   private final Supplier<Float> animationFunction;
   public final PassiveSkill skill;
   public float f_93620_;
   public float f_93621_;
   public boolean skillLearned;
   public boolean canLearn;
   public boolean searched;
   public boolean selected;

   public SkillButton(Supplier<Float> animationFunc, float x, float y, PassiveSkill skill) {
      super((int)x, (int)y, skill.getSkillSize(), skill.getSkillSize(), Component.m_237119_(), b -> {
      }, (b, s, tx, ty) -> {
      });
      this.f_93620_ = x;
      this.f_93621_ = y;
      this.skill = skill;
      this.animationFunction = animationFunc;
      this.f_93623_ = false;
   }

   public void m_6303_(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      poseStack.m_85836_();
      poseStack.m_85837_((double)this.f_93620_, (double)this.f_93621_, 0.0);
      this.renderFavoriteSkillHighlight(poseStack);
      RenderSystem.m_157456_(0, this.skill.getFrameTexture());
      this.renderBackground(poseStack);
      poseStack.m_85836_();
      poseStack.m_85837_((double)this.f_93618_ / 2.0, (double)this.f_93619_ / 2.0, 0.0);
      poseStack.m_85841_(0.5F, 0.5F, 1.0F);
      if (this.f_93618_ == 32) {
         poseStack.m_85841_(0.75F, 0.75F, 1.0F);
      }

      poseStack.m_85837_((double)(-this.f_93618_) / 2.0, (double)(-this.f_93619_) / 2.0, 0.0);
      RenderSystem.m_157456_(0, this.skill.getIconTexture());
      this.renderIcon(poseStack);
      poseStack.m_85849_();
      RenderSystem.m_157456_(0, this.skill.getFrameTexture());
      float animation = (Mth.m_14031_(this.animationFunction.get() / 3.0F) + 1.0F) / 2.0F;
      float rb = this.searched ? 0.1F : 1.0F;
      if (this.canLearn || this.searched) {
         RenderSystem.m_157429_(rb, 1.0F, rb, 1.0F - animation);
      }

      if (!this.skillLearned) {
         this.renderDarkening(poseStack);
      }

      if (this.canLearn || this.searched) {
         RenderSystem.m_157429_(rb, 1.0F, rb, animation);
      }

      if (this.skillLearned || this.canLearn || this.searched) {
         this.renderFrame(poseStack);
      }

      if (this.canLearn || this.searched || this.selected) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      }

      poseStack.m_85849_();
   }

   private void renderFavoriteSkillHighlight(PoseStack poseStack) {
      if (ClientConfig.favorite_skills.contains(this.skill.getId())) {
         ScreenHelper.prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/favorite_skill.png"));
         int color;
         if (ClientConfig.favorite_color_is_rainbow) {
            color = Color.getHSBColor(this.animationFunction.get() / 240.0F, 1.0F, 1.0F).getRGB();
         } else {
            color = ClientConfig.favorite_color;
         }

         float r = (float)(color >> 16 & 0xFF) / 255.0F;
         float g = (float)(color >> 8 & 0xFF) / 255.0F;
         float b = (float)(color & 0xFF) / 255.0F;
         RenderSystem.m_157429_(r, g, b, 1.0F);
         int size = (int)((double)this.f_93618_ * 1.4);
         poseStack.m_85836_();
         poseStack.m_85837_((double)((float)this.f_93618_ / 2.0F), (double)((float)this.f_93619_ / 2.0F), 0.0);
         float animation = 1.0F + 0.3F * (Mth.m_14031_(this.animationFunction.get() / 3.0F) + 1.0F) / 2.0F;
         poseStack.m_85841_(animation, animation, 1.0F);
         poseStack.m_85845_(Vector3f.f_122227_.m_122240_(this.animationFunction.get()));
         poseStack.m_85837_((double)((float)(-size) / 2.0F), (double)((float)(-size) / 2.0F), 0.0);
         m_93160_(poseStack, 0, 0, size, size, 0.0F, 0.0F, 80, 80, 80, 80);
         poseStack.m_85849_();
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private void renderFrame(PoseStack poseStack) {
      m_93160_(poseStack, 0, 0, this.f_93618_, this.f_93619_, (float)(this.f_93618_ * 2), 0.0F, this.f_93618_, this.f_93619_, this.f_93618_ * 3, this.f_93619_);
   }

   private void renderDarkening(PoseStack poseStack) {
      m_93160_(poseStack, 0, 0, this.f_93618_, this.f_93619_, (float)this.f_93618_, 0.0F, this.f_93618_, this.f_93619_, this.f_93618_ * 3, this.f_93619_);
   }

   private void renderIcon(PoseStack poseStack) {
      m_93160_(poseStack, 0, 0, this.f_93618_, this.f_93619_, 0.0F, 0.0F, this.f_93618_, this.f_93619_, this.f_93618_, this.f_93619_);
   }

   private void renderBackground(PoseStack poseStack) {
      m_93160_(poseStack, 0, 0, this.f_93618_, this.f_93619_, 0.0F, 0.0F, this.f_93618_, this.f_93619_, this.f_93618_ * 3, this.f_93619_);
   }

   public void setButtonSize(int size) {
      this.f_93618_ = this.f_93619_ = size;
   }

   public List<MutableComponent> getTooltip(PassiveSkillTree skillTree) {
      ArrayList<MutableComponent> tooltip = new ArrayList<>();
      this.addTitleTooltip(tooltip);
      this.addLimitationsTooltip(skillTree, tooltip);
      List<MutableComponent> description = this.skill.getDescription();
      if (description != null) {
         tooltip.addAll(description);
      } else {
         this.addSkillBonusTooltip(tooltip);
      }

      this.addAdvancedTooltip(tooltip);
      return tooltip;
   }

   public void addSkillBonusTooltip(List<MutableComponent> tooltip) {
      this.addDescriptionTooltip(tooltip);
      this.addInfoTooltip(tooltip);
   }

   private void addInfoTooltip(List<MutableComponent> tooltip) {
      List<MutableComponent> info = new ArrayList<>();

      for (SkillBonus<?> skillBonus : this.skill.getBonuses()) {
         skillBonus.gatherInfo(component -> {
            component = component.m_130944_(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY});
            info.add(component);
         });
      }

      if (!info.isEmpty()) {
         tooltip.add(Component.m_237119_());
         tooltip.addAll(info);
      }
   }

   protected void addAdvancedTooltip(List<MutableComponent> tooltip) {
      Minecraft minecraft = Minecraft.m_91087_();
      if (minecraft.f_91066_.f_92125_) {
         this.addIdTooltip(tooltip);
      }
   }

   protected void addDescriptionTooltip(List<MutableComponent> tooltip) {
      this.skill.getBonuses().stream().map(SkillBonus::getTooltip).forEach(tooltip::add);
      String descriptionId = this.getSkillId() + ".description";
      String description = Component.m_237115_(descriptionId).getString();
      if (!description.equals(descriptionId)) {
         List<String> descriptionStrings = Arrays.asList(description.split("/n"));
         descriptionStrings.stream().<MutableComponent>map(Component::m_237115_).map(this::applyDescriptionStyle).forEach(tooltip::add);
      }
   }

   private void addLimitationsTooltip(PassiveSkillTree skillTree, ArrayList<MutableComponent> tooltips) {
      boolean addedLimitTooltip = false;

      for (String tag : this.skill.getTags()) {
         int limit = skillTree.getSkillLimitations().getOrDefault(tag, 0);
         if (limit > 0) {
            addedLimitTooltip = true;
            AtomicReference<MutableComponent> tagTooltip = new AtomicReference<>(Component.m_237113_(tag));
            TooltipHelper.consumeTranslated("skill.tag.%s.name".formatted(tag), tagTooltip::set);
            tagTooltip.set(Component.m_237113_(limit + " " + tagTooltip.get().getString()));
            tagTooltip.set(tagTooltip.get().m_130948_(TooltipHelper.getItemBonusStyle(true)));
            MutableComponent tooltip = Component.m_237110_("skill.limitation", new Object[]{tagTooltip.get()});
            tooltip = tooltip.m_130948_(TooltipHelper.getSkillBonusStyle(true));
            tooltips.add(tooltip);
         }
      }

      if (addedLimitTooltip) {
         tooltips.add(Component.m_237119_());
      }
   }

   protected void addTitleTooltip(List<MutableComponent> tooltip) {
      MutableComponent title;
      if (this.skill.getTitle().isEmpty()) {
         title = Component.m_237115_(this.getSkillId() + ".name");
      } else {
         title = Component.m_237113_(this.skill.getTitle());
      }

      tooltip.add(title.m_130948_(this.getTitleStyle()));
   }

   private Style getTitleStyle() {
      String titleColor = this.skill.getTitleColor();
      if (titleColor.isEmpty()) {
         return this.f_93618_ == 30
            ? GATEWAY_TITLE_STYLE
            : (
               this.f_93618_ == 24
                  ? CLASS_TITLE_STYLE
                  : (this.f_93618_ == 20 ? NOTABLE_TITLE_STYLE : (this.f_93618_ == 32 ? KEYSTONE_TITLE_STYLE : LESSER_TITLE_STYLE))
            );
      } else {
         try {
            return Style.f_131099_.m_178520_(Integer.parseInt(titleColor, 16));
         } catch (NumberFormatException var3) {
            return Style.f_131099_;
         }
      }
   }

   protected void addIdTooltip(List<MutableComponent> tooltip) {
      MutableComponent idComponent = Component.m_237113_(this.skill.getId().toString()).m_130948_(ID_STYLE);
      tooltip.add(idComponent);
   }

   protected MutableComponent applyDescriptionStyle(MutableComponent component) {
      return component.m_130948_(DESCRIPTION_STYLE);
   }

   public void setCanLearn() {
      this.canLearn = true;
   }

   public void setActive() {
      this.f_93623_ = true;
   }

   private String getSkillId() {
      return "skill." + this.skill.getId().m_135827_() + "." + this.skill.getId().m_135815_();
   }
}
