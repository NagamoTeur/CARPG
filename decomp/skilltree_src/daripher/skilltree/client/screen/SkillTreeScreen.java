package daripher.skilltree.client.screen;

import com.google.common.collect.Streams;
import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.capability.skill.IPlayerSkills;
import daripher.skilltree.capability.skill.PlayerSkillsProvider;
import daripher.skilltree.client.data.SkillTreeClientData;
import daripher.skilltree.client.widget.Button;
import daripher.skilltree.client.widget.Label;
import daripher.skilltree.client.widget.ProgressBar;
import daripher.skilltree.client.widget.ScrollableComponentList;
import daripher.skilltree.client.widget.TextField;
import daripher.skilltree.client.widget.skill.SkillButton;
import daripher.skilltree.client.widget.skill.SkillConnection;
import daripher.skilltree.config.ClientConfig;
import daripher.skilltree.data.reloader.SkillTreesReloader;
import daripher.skilltree.data.reloader.SkillsReloader;
import daripher.skilltree.network.NetworkDispatcher;
import daripher.skilltree.network.message.GainSkillPointMessage;
import daripher.skilltree.network.message.LearnSkillMessage;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.PassiveSkillTree;
import daripher.skilltree.skill.bonus.SkillBonus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SkillTreeScreen extends Screen {
   public static final int BACKGROUND_SIZE = 2048;
   private final Map<ResourceLocation, SkillButton> skillButtons = new HashMap<>();
   private final List<SkillConnection> skillConnections = new ArrayList<>();
   private final List<ResourceLocation> learnedSkills = new ArrayList<>();
   public final List<ResourceLocation> newlyLearnedSkills = new ArrayList<>();
   private final List<SkillButton> startingPoints = new ArrayList<>();
   private final PassiveSkillTree skillTree;
   private String search = "";
   public float renderAnimation;
   public int skillPoints;
   protected double scrollSpeedX;
   protected double scrollSpeedY;
   protected double scrollX;
   protected double scrollY;
   protected int maxScrollX;
   protected int maxScrollY;
   private Button buyButton;
   private Label pointsInfo;
   private ProgressBar progressBar;
   private ScrollableComponentList statsInfo;
   private boolean firstInitDone;
   private boolean showStats;
   private boolean showProgressInNumbers;
   private int prevMouseX;
   private int prevMouseY;
   private float zoom = 1.0F;

   public SkillTreeScreen(ResourceLocation skillTreeId) {
      super(Component.m_237119_());
      this.skillTree = SkillTreesReloader.getSkillTreeById(skillTreeId);
      this.f_96541_ = Minecraft.m_91087_();
   }

   public void m_7856_() {
      this.m_169413_();
      this.progressBar = new ProgressBar(this.f_96543_ / 2 - 117, this.f_96544_ - 17, b -> {
         this.progressBar.showProgressInNumbers ^= true;
         this.showProgressInNumbers ^= true;
      });
      this.progressBar.showProgressInNumbers = this.showProgressInNumbers;
      this.m_142416_(this.progressBar);
      this.addTopWidgets();
      if (!SkillTreeClientData.enable_exp_exchange) {
         this.progressBar.f_93624_ = false;
         this.buyButton.f_93624_ = false;
      }

      if (!this.firstInitDone) {
         this.firstInit();
      }

      this.addSkillButtons();
      this.statsInfo = new ScrollableComponentList(48, this.f_96544_ - 60);
      this.statsInfo.setComponents(this.getMergedSkillBonusesTooltips());
      this.m_142416_(this.statsInfo);
      this.maxScrollX = this.maxScrollX - (this.f_96543_ / 2 - 80);
      this.maxScrollY = this.maxScrollY - (this.f_96544_ / 2 - 80);
      if (this.maxScrollX < 0) {
         this.maxScrollX = 0;
      }

      if (this.maxScrollY < 0) {
         this.maxScrollY = 0;
      }

      this.addSkillConnections();
      this.highlightSkillsThatCanBeLearned();
      this.updateSearch();
   }

   private void addTopWidgets() {
      Component buyButtonText = Component.m_237115_("widget.buy_skill_button");
      Component pointsInfoText = Component.m_237110_("widget.skill_points_left", new Object[]{100});
      Component confirmButtonText = Component.m_237115_("widget.confirm_button");
      Component cancelButtonText = Component.m_237115_("widget.cancel_button");
      Component showStatsButtonText = Component.m_237115_("widget.show_stats");
      int buttonWidth = Math.max(this.f_96547_.m_92852_(buyButtonText), this.f_96547_.m_92852_(pointsInfoText));
      buttonWidth = Math.max(buttonWidth, this.f_96547_.m_92852_(confirmButtonText));
      buttonWidth = Math.max(buttonWidth, this.f_96547_.m_92852_(cancelButtonText));
      buttonWidth += 20;
      int buttonsY = 8;
      Button showStatsButton = new Button(this.f_96543_ - buttonWidth - 8, buttonsY, buttonWidth, 14, showStatsButtonText);
      showStatsButton.setPressFunc(b -> this.showStats ^= true);
      this.m_142416_(showStatsButton);
      ((TextField)this.m_142416_(new TextField(8, buttonsY, buttonWidth, 14, this.search))).setHint("Search...").m_94151_(s -> {
         this.search = s;
         this.updateSearch();
      });
      this.buyButton = new Button(this.f_96543_ / 2 - 8 - buttonWidth, buttonsY, buttonWidth, 14, buyButtonText);
      this.buyButton.setPressFunc(b -> this.buySkillPoint());
      this.m_142416_(this.buyButton);
      this.pointsInfo = new Label(this.f_96543_ / 2 + 8, buttonsY, buttonWidth, 14, Component.m_237119_());
      if (!SkillTreeClientData.enable_exp_exchange) {
         this.pointsInfo.f_93620_ = this.f_96543_ / 2 - buttonWidth / 2;
      }

      this.m_142416_(this.pointsInfo);
      buttonsY += 20;
      Button confirmButton = new Button(this.f_96543_ / 2 - 8 - buttonWidth, buttonsY, buttonWidth, 14, confirmButtonText);
      confirmButton.setPressFunc(b -> this.confirmLearnSkills());
      this.m_142416_(confirmButton);
      Button cancelButton = new Button(this.f_96543_ / 2 + 8, buttonsY, buttonWidth, 14, cancelButtonText);
      cancelButton.setPressFunc(b -> this.cancelLearnSkills());
      this.m_142416_(cancelButton);
      confirmButton.f_93623_ = cancelButton.f_93623_ = !this.newlyLearnedSkills.isEmpty();
   }

   private void updateSearch() {
      if (this.search.isEmpty()) {
         for (SkillButton button : this.skillButtons.values()) {
            button.searched = false;
         }
      } else {
         label36:
         for (SkillButton button : this.skillButtons.values()) {
            for (MutableComponent component : button.getTooltip(this.skillTree)) {
               if (component.getString().toLowerCase().contains(this.search.toLowerCase())) {
                  button.searched = true;
                  continue label36;
               }
            }

            button.searched = false;
         }
      }
   }

   public void m_6305_(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      this.updateScreen(partialTick);
      this.renderAnimation += partialTick;
      this.m_7333_(poseStack);
      this.renderConnections(poseStack, mouseX, mouseY);
      this.renderSkills(poseStack, mouseX, mouseY, partialTick);
      this.renderOverlay(poseStack);
      this.renderWidgets(poseStack, mouseX, mouseY, partialTick);
      this.renderSkillTooltip(poseStack, mouseX, mouseY, partialTick);
      this.prevMouseX = mouseX;
      this.prevMouseY = mouseY;
   }

   private void renderWidgets(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      MutableComponent pointsLeft = Component.m_237113_(this.skillPoints + "").m_130948_(Style.f_131099_.m_178520_(16573030));
      this.pointsInfo.m_93666_(Component.m_237110_("widget.skill_points_left", new Object[]{pointsLeft}));
      this.statsInfo.f_93620_ = this.f_96543_ - this.statsInfo.m_5711_() - 10;
      this.statsInfo.f_93624_ = this.showStats;

      for (Widget widget : this.f_169369_) {
         if (!(widget instanceof SkillButton)) {
            widget.m_6305_(poseStack, mouseX, mouseY, partialTick);
         }
      }
   }

   private void renderSkillTooltip(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      if (!this.getWidgetAt((double)mouseX, (double)mouseY).isPresent()) {
         SkillButton skill = this.getSkillAt((double)mouseX, (double)mouseY);
         if (skill != null) {
            float tooltipX = (float)mouseX + (float)(this.prevMouseX - mouseX) * partialTick;
            float tooltipY = (float)mouseY + (float)(this.prevMouseY - mouseY) * partialTick;
            ScreenHelper.renderSkillTooltip(this.skillTree, skill, poseStack, tooltipX, tooltipY, this.f_96543_, this.f_96544_);
         }
      }
   }

   private void renderSkills(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      poseStack.m_85836_();
      poseStack.m_85837_(this.scrollX, this.scrollY, 0.0);

      for (SkillButton widget : this.skillButtons.values()) {
         poseStack.m_85836_();
         poseStack.m_85837_((double)widget.f_93620_ + (double)widget.m_5711_() / 2.0, (double)widget.f_93621_ + (double)widget.m_93694_() / 2.0, 0.0);
         poseStack.m_85841_(this.zoom, this.zoom, 1.0F);
         poseStack.m_85837_((double)(-widget.f_93620_) - (double)widget.m_5711_() / 2.0, (double)(-widget.f_93621_) - (double)widget.m_93694_() / 2.0, 0.0);
         widget.m_6305_(poseStack, mouseX, mouseY, partialTick);
         poseStack.m_85849_();
      }

      poseStack.m_85849_();
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      Optional<GuiEventListener> widget = this.getWidgetAt(mouseX, mouseY);
      if (widget.isPresent()) {
         return widget.get().m_6375_(mouseX, mouseY, button);
      } else {
         SkillButton skill = this.getSkillAt(mouseX, mouseY);
         if (skill == null) {
            return false;
         } else if (button == 0) {
            this.playButtonSound();
            this.skillButtonPressed(skill);
            return true;
         } else if (button == 1) {
            ClientConfig.toggleFavoriteSkill(skill.skill);
            this.playButtonSound();
            return true;
         } else {
            return false;
         }
      }
   }

   private void playButtonSound() {
      this.getMinecraft().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12490_, 1.0F));
   }

   public void m_86600_() {
      this.textFields().forEach(EditBox::m_94120_);
   }

   public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
      return this.keyPressedOnTextField(keyCode, scanCode, modifiers) ? true : super.m_7933_(keyCode, scanCode, modifiers);
   }

   public boolean m_7920_(int keyCode, int scanCode, int modifiers) {
      this.textFields().toList().forEach(b -> b.m_7920_(keyCode, scanCode, modifiers));
      return super.m_7933_(keyCode, scanCode, modifiers);
   }

   public boolean m_5534_(char character, int keyCode) {
      for (EditBox textField : this.textFields().toList()) {
         if (textField.m_5534_(character, keyCode)) {
            return true;
         }
      }

      return false;
   }

   private boolean keyPressedOnTextField(int keyCode, int scanCode, int modifiers) {
      return this.textFields().toList().stream().anyMatch(b -> b.m_7933_(keyCode, scanCode, modifiers));
   }

   private Stream<EditBox> textFields() {
      return this.m_6702_().stream().filter(EditBox.class::isInstance).map(EditBox.class::cast);
   }

   public Optional<GuiEventListener> getWidgetAt(double mouseX, double mouseY) {
      Predicate<GuiEventListener> isSkillButton = SkillButton.class::isInstance;
      return super.m_94729_(mouseX, mouseY).filter(isSkillButton.negate());
   }

   @Nullable
   public SkillButton getSkillAt(double mouseX, double mouseY) {
      mouseX -= this.scrollX;
      mouseY -= this.scrollY;

      for (SkillButton button : this.skillButtons.values()) {
         double skillSize = (double)((float)button.skill.getSkillSize() * this.zoom);
         double skillX = (double)button.f_93620_ + (double)button.m_5711_() / 2.0 - skillSize / 2.0;
         double skillY = (double)button.f_93621_ + (double)button.m_93694_() / 2.0 - skillSize / 2.0;
         if (mouseX >= skillX && mouseY >= skillY && mouseX < skillX + skillSize && mouseY < skillY + skillSize) {
            return button;
         }
      }

      return null;
   }

   private List<Component> getMergedSkillBonusesTooltips() {
      List<SkillBonus<?>> bonuses = new ArrayList<>();
      this.learnedSkills
         .stream()
         .map(this.skillButtons::get)
         .map(button -> button.skill)
         .map(PassiveSkill::getBonuses)
         .flatMap(Collection::stream)
         .forEach(b -> addToMergeList((SkillBonus<?>)b, bonuses));
      return bonuses.stream().sorted().map(SkillBonus::getTooltip).map(Component.class::cast).toList();
   }

   private static void addToMergeList(SkillBonus<?> b, List<SkillBonus<?>> bonuses) {
      Optional<SkillBonus<?>> same = bonuses.stream().filter(b::canMerge).findAny();
      if (same.isPresent()) {
         bonuses.remove(same.get());
         bonuses.add(same.get().merge(b));
      } else {
         bonuses.add(b);
      }
   }

   protected void firstInit() {
      IPlayerSkills capability = PlayerSkillsProvider.get(this.getPlayer());
      List<PassiveSkill> skills = capability.getPlayerSkills();
      skills.stream().map(PassiveSkill::getId).forEach(this.learnedSkills::add);
      this.skillPoints = capability.getSkillPoints();
      this.firstInitDone = true;
   }

   public void addSkillButtons() {
      this.startingPoints.clear();
      this.skillButtons.clear();
      this.skillTree.getSkillIds().forEach(this::addSkillButton);
   }

   protected void addSkillButton(ResourceLocation skillId) {
      PassiveSkill skill = SkillsReloader.getSkillById(skillId);
      if (skill != null) {
         float buttonX = this.getSkillButtonX(skill);
         float buttonY = this.getSkillButtonY(skill);
         SkillButton button = new SkillButton(this::getAnimation, buttonX, buttonY, skill);
         this.m_142416_(button);
         this.skillButtons.put(skillId, button);
         if (skill.isStartingPoint()) {
            this.startingPoints.add(button);
         }

         if (this.isSkillLearned(skill)) {
            button.skillLearned = true;
         }

         if ((float)this.maxScrollX < Mth.m_14154_(skill.getPositionX())) {
            this.maxScrollX = (int)Mth.m_14154_(skill.getPositionX());
         }

         if ((float)this.maxScrollY < Mth.m_14154_(skill.getPositionY())) {
            this.maxScrollY = (int)Mth.m_14154_(skill.getPositionY());
         }
      }
   }

   private float getSkillButtonX(PassiveSkill skill) {
      float skillX = skill.getPositionX();
      return skillX - (float)skill.getSkillSize() / 2.0F + (float)this.f_96543_ / 2.0F + skillX * (this.zoom - 1.0F);
   }

   private float getSkillButtonY(PassiveSkill skill) {
      float skillY = skill.getPositionY();
      return skillY - (float)skill.getSkillSize() / 2.0F + (float)this.f_96544_ / 2.0F + skillY * (this.zoom - 1.0F);
   }

   protected boolean isSkillLearned(PassiveSkill skill) {
      return this.learnedSkills.contains(skill.getId()) || this.newlyLearnedSkills.contains(skill.getId());
   }

   public void addSkillConnections() {
      this.skillConnections.clear();
      this.getTreeSkills().forEach(this::addSkillConnections);
   }

   private Stream<PassiveSkill> getTreeSkills() {
      return this.skillTree.getSkillIds().stream().map(SkillsReloader::getSkillById);
   }

   private void addSkillConnections(PassiveSkill skill) {
      skill.getDirectConnections().forEach(id -> this.connectSkills(SkillConnection.Type.DIRECT, skill.getId(), id));
      skill.getLongConnections().forEach(id -> this.connectSkills(SkillConnection.Type.LONG, skill.getId(), id));
      skill.getOneWayConnections().forEach(id -> this.connectSkills(SkillConnection.Type.ONE_WAY, skill.getId(), id));
   }

   protected void connectSkills(SkillConnection.Type type, ResourceLocation skillId1, ResourceLocation skillId2) {
      SkillButton button1 = this.skillButtons.get(skillId1);
      SkillButton button2 = this.skillButtons.get(skillId2);
      this.skillConnections.add(new SkillConnection(type, button1, button2));
   }

   private void highlightSkillsThatCanBeLearned() {
      if (this.skillPoints != 0) {
         if (this.learnedSkills.isEmpty() && this.newlyLearnedSkills.isEmpty()) {
            this.startingPoints.forEach(SkillButton::setCanLearn);
         } else if (this.learnedSkills.size() + this.newlyLearnedSkills.size() < SkillTreeClientData.max_skill_points) {
            this.skillConnections.forEach(connection -> {
               SkillButton button1 = connection.getFirstButton();
               SkillButton button2 = connection.getSecondButton();
               if (button1.skillLearned != button2.skillLearned) {
                  if (connection.getType() != SkillConnection.Type.ONE_WAY && !button1.skillLearned && this.canLearnSkill(button1.skill)) {
                     button1.setCanLearn();
                     button1.setActive();
                  }

                  if (!button2.skillLearned && this.canLearnSkill(button2.skill)) {
                     button2.setCanLearn();
                     button2.setActive();
                  }
               }
            });
         }
      }
   }

   private boolean canLearnSkill(PassiveSkill skill) {
      Map<String, Integer> limitations = this.skillTree.getSkillLimitations();

      for (String tag : skill.getTags()) {
         int limit = limitations.getOrDefault(tag, 0);
         if (limit > 0 && this.getLearnedSkillsWithTag(tag) >= (long)limit) {
            return false;
         }
      }

      return true;
   }

   private long getLearnedSkillsWithTag(String tag) {
      return Streams.concat(new Stream[]{this.learnedSkills.stream(), this.newlyLearnedSkills.stream()})
         .map(SkillsReloader::getSkillById)
         .filter(Objects::nonNull)
         .filter(skill -> skill.getTags().contains(tag))
         .count();
   }

   private void confirmLearnSkills() {
      this.newlyLearnedSkills.forEach(id -> this.learnSkill(this.skillButtons.get(id).skill));
      this.newlyLearnedSkills.clear();
   }

   private void cancelLearnSkills() {
      this.skillPoints = this.skillPoints + this.newlyLearnedSkills.size();
      this.newlyLearnedSkills.clear();
      this.m_232761_();
   }

   private void buySkillPoint() {
      int currentLevel = this.getCurrentLevel();
      if (this.canBuySkillPoint(currentLevel)) {
         int cost = SkillTreeClientData.getSkillPointCost(currentLevel);
         NetworkDispatcher.network_channel.sendToServer(new GainSkillPointMessage());
         this.getPlayer().m_6756_(-cost);
      }
   }

   private boolean canBuySkillPoint(int currentLevel) {
      if (!SkillTreeClientData.enable_exp_exchange) {
         return false;
      } else if (this.isMaxLevel(currentLevel)) {
         return false;
      } else {
         int cost = SkillTreeClientData.getSkillPointCost(currentLevel);
         return this.getPlayer().f_36079_ >= cost;
      }
   }

   private boolean isMaxLevel(int currentLevel) {
      return currentLevel >= SkillTreeClientData.max_skill_points;
   }

   private int getCurrentLevel() {
      IPlayerSkills capability = PlayerSkillsProvider.get(this.getPlayer());
      int learnedSkills = capability.getPlayerSkills().size();
      int skillPoints = capability.getSkillPoints();
      return learnedSkills + skillPoints;
   }

   protected void skillButtonPressed(SkillButton button) {
      Objects.requireNonNull(this.f_96541_);
      PassiveSkill skill = button.skill;
      if (!this.newlyLearnedSkills.isEmpty()) {
         int lastLearned = this.newlyLearnedSkills.size() - 1;
         if (this.newlyLearnedSkills.get(lastLearned).equals(skill.getId())) {
            this.skillPoints++;
            this.newlyLearnedSkills.remove(lastLearned);
            this.m_232761_();
            return;
         }
      }

      if (button.canLearn) {
         this.skillPoints--;
         this.newlyLearnedSkills.add(skill.getId());
         this.m_232761_();
      } else {
         ResourceLocation connectedTree = skill.getConnectedTreeId();
         if (connectedTree != null) {
            this.f_96541_.m_91152_(new SkillTreeScreen(connectedTree));
         }
      }
   }

   protected void learnSkill(PassiveSkill skill) {
      this.learnedSkills.add(skill.getId());
      NetworkDispatcher.network_channel.sendToServer(new LearnSkillMessage(skill));
      this.m_232761_();
   }

   private void updateScreen(float partialTick) {
      this.updateBuyPointButton();
      this.scrollX = this.scrollX + this.scrollSpeedX * (double)partialTick;
      this.scrollX = Math.max((double)((float)(-this.maxScrollX) * this.zoom), Math.min((double)((float)this.maxScrollX * this.zoom), this.scrollX));
      this.scrollSpeedX *= 0.8;
      this.scrollY = this.scrollY + this.scrollSpeedY * (double)partialTick;
      this.scrollY = Math.max((double)((float)(-this.maxScrollY) * this.zoom), Math.min((double)((float)this.maxScrollY * this.zoom), this.scrollY));
      this.scrollSpeedY *= 0.8;
   }

   protected void updateBuyPointButton() {
      int currentLevel = this.getCurrentLevel();
      this.buyButton.f_93623_ = false;
      if (!this.isMaxLevel(currentLevel)) {
         int pointCost = SkillTreeClientData.getSkillPointCost(currentLevel);
         this.buyButton.f_93623_ = this.getPlayer().f_36079_ >= pointCost;
      }
   }

   private void renderOverlay(PoseStack poseStack) {
      ScreenHelper.prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/skill_tree_overlay.png"));
      m_93143_(poseStack, 0, 0, 0, 0.0F, 0.0F, this.f_96543_, this.f_96544_, this.f_96543_, this.f_96544_);
   }

   public void m_7333_(PoseStack poseStack) {
      ScreenHelper.prepareTextureRendering(new ResourceLocation("skilltree:textures/screen/skill_tree_background.png"));
      poseStack.m_85836_();
      poseStack.m_85837_(this.scrollX / 3.0, this.scrollY / 3.0, 0.0);
      int size = 2048;
      m_93143_(poseStack, (this.f_96543_ - size) / 2, (this.f_96544_ - size) / 2, 0, 0.0F, 0.0F, size, size, size, size);
      poseStack.m_85849_();
   }

   public boolean m_7979_(double mouseX, double mouseY, int mouseButton, double dragAmountX, double dragAmountY) {
      if (mouseButton != 0 && mouseButton != 2) {
         return false;
      } else {
         if (this.maxScrollX > 0) {
            this.scrollSpeedX += dragAmountX * 0.25;
         }

         if (this.maxScrollY > 0) {
            this.scrollSpeedY += dragAmountY * 0.25;
         }

         return true;
      }
   }

   public boolean m_6050_(double mouseX, double mouseY, double amount) {
      if (this.getWidgetAt(mouseX, mouseY).filter(ScrollableComponentList.class::isInstance).isEmpty()) {
         if (amount > 0.0 && this.zoom < 2.0F) {
            this.zoom += 0.05F;
         }

         if (amount < 0.0 && this.zoom > 0.25F) {
            this.zoom -= 0.05F;
         }

         this.m_232761_();
      }

      return super.m_6050_(mouseX, mouseY, amount);
   }

   protected void renderConnections(PoseStack poseStack, int mouseX, int mouseY) {
      this.skillConnections.stream().filter(c -> c.getType() == SkillConnection.Type.DIRECT).forEach(c -> this.renderDirectConnection(poseStack, c));
      this.skillConnections
         .stream()
         .filter(c -> c.getType() == SkillConnection.Type.LONG)
         .forEach(c -> this.renderLongConnection(poseStack, c, mouseX, mouseY));
      this.skillConnections.stream().filter(c -> c.getType() == SkillConnection.Type.ONE_WAY).forEach(c -> this.renderOneWayConnection(poseStack, c));
   }

   private void renderDirectConnection(PoseStack poseStack, SkillConnection c) {
      ScreenHelper.renderConnection(poseStack, this.scrollX, this.scrollY, c, this.zoom, this.renderAnimation);
   }

   private void renderLongConnection(PoseStack poseStack, SkillConnection connection, int mouseX, int mouseY) {
      SkillButton button1 = connection.getFirstButton();
      SkillButton button2 = connection.getSecondButton();
      SkillButton hoveredSkill = this.getSkillAt((double)mouseX, (double)mouseY);
      boolean learned = this.isSkillLearned(button1.skill) || this.isSkillLearned(button2.skill);
      boolean hovered = hoveredSkill == button1 || hoveredSkill == button2;
      if (learned || hovered) {
         ScreenHelper.renderGatewayConnection(poseStack, this.scrollX, this.scrollY, connection, learned, this.zoom, this.renderAnimation);
      }
   }

   private void renderOneWayConnection(PoseStack poseStack, SkillConnection connection) {
      boolean highlighted = this.isSkillLearned(connection.getFirstButton().skill);
      ScreenHelper.renderOneWayConnection(poseStack, this.scrollX, this.scrollY, connection, highlighted, this.zoom, this.renderAnimation);
   }

   public float getAnimation() {
      return this.renderAnimation;
   }

   @Nonnull
   private LocalPlayer getPlayer() {
      return Objects.requireNonNull(this.getMinecraft().f_91074_);
   }
}
