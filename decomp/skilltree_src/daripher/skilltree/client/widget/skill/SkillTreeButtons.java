package daripher.skilltree.client.widget.skill;

import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.client.data.SkillTreeClientData;
import daripher.skilltree.client.screen.ScreenHelper;
import daripher.skilltree.client.widget.group.ScrollableZoomableWidgetGroup;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.PassiveSkillTree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SkillTreeButtons extends ScrollableZoomableWidgetGroup<SkillButton> {
   private final PassiveSkillTree skillTree;
   private final List<SkillConnection> skillConnections = new ArrayList<>();
   private final Map<ResourceLocation, SkillButton> idToWidget = new HashMap<>();
   private final Supplier<Float> animationFunc;

   public SkillTreeButtons(PassiveSkillTree skillTree, Supplier<Float> animationFunc) {
      super(0, 0, 0, 0);
      this.skillTree = skillTree;
      this.animationFunc = animationFunc;
   }

   @NotNull
   public <W extends SkillButton> W addWidget(@NotNull W widget) {
      this.idToWidget.put(widget.skill.getId(), widget);
      return super.addWidget(widget);
   }

   @Override
   public void clearWidgets() {
      this.idToWidget.clear();
      super.clearWidgets();
   }

   @Override
   public void m_6305_(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTick) {
      this.renderConnections(graphics, mouseX, mouseY);
      super.m_6305_(graphics, mouseX, mouseY, partialTick);
   }

   protected void renderConnections(PoseStack graphics, int mouseX, int mouseY) {
      this.skillConnections.stream().filter(c -> c.getType() == SkillConnection.Type.DIRECT).forEach(c -> this.renderDirectConnection(graphics, c));
      this.skillConnections.stream().filter(c -> c.getType() == SkillConnection.Type.LONG).forEach(c -> this.renderLongConnection(graphics, c, mouseX, mouseY));
      this.skillConnections.stream().filter(c -> c.getType() == SkillConnection.Type.ONE_WAY).forEach(c -> this.renderOneWayConnection(graphics, c));
   }

   private void renderDirectConnection(PoseStack graphics, SkillConnection connection) {
      ScreenHelper.renderConnection(graphics, (double)this.scrollX, (double)this.scrollY, connection, this.getZoom(), this.animationFunc.get());
   }

   private void renderLongConnection(PoseStack graphics, SkillConnection connection, int mouseX, int mouseY) {
      SkillButton hoveredSkill = this.getWidgetAt((double)mouseX, (double)mouseY);
      if (hoveredSkill == connection.getFirstButton() || hoveredSkill == connection.getSecondButton()) {
         ScreenHelper.renderGatewayConnection(graphics, (double)this.scrollX, (double)this.scrollY, connection, true, this.getZoom(), this.animationFunc.get());
      }
   }

   private void renderOneWayConnection(PoseStack graphics, SkillConnection connection) {
      ScreenHelper.renderOneWayConnection(graphics, (double)this.scrollX, (double)this.scrollY, connection, true, this.getZoom(), this.animationFunc.get());
   }

   public void renderTooltip(PoseStack graphics, float tooltipX, float tooltipY) {
      SkillButton skill = this.getWidgetAt((double)tooltipX, (double)tooltipY);
      if (skill != null) {
         ScreenHelper.renderSkillTooltip(this.skillTree, skill, graphics, tooltipX, tooltipY, this.f_93618_, this.f_93619_);
      }
   }

   public PassiveSkillTree getSkillTree() {
      return this.skillTree;
   }

   public SkillButton addSkillButton(PassiveSkill skill, Supplier<Float> animationFunc) {
      float skillX = skill.getPositionX();
      float skillY = skill.getPositionY();
      int skillSize = skill.getSkillSize();
      float buttonX = skillX - (float)skillSize / 2.0F + (float)this.f_93618_ / 2.0F + skillX * (this.getZoom() - 1.0F);
      float buttonY = skillY - (float)skillSize / 2.0F + (float)this.f_93619_ / 2.0F + skillY * (this.getZoom() - 1.0F);
      SkillButton button = new SkillButton(animationFunc, buttonX, buttonY, skill);
      return this.addWidget(button);
   }

   public void updateSkillConnections() {
      this.skillConnections.clear();
      this.getWidgets().forEach(this::addSkillConnections);
   }

   private void addSkillConnections(SkillButton skillButton) {
      PassiveSkill skill = skillButton.skill;
      this.readSkillConnections(skill, SkillConnection.Type.DIRECT, skill.getDirectConnections());
      this.readSkillConnections(skill, SkillConnection.Type.LONG, skill.getLongConnections());
      this.readSkillConnections(skill, SkillConnection.Type.ONE_WAY, skill.getOneWayConnections());
   }

   private void readSkillConnections(PassiveSkill skill, SkillConnection.Type type, List<ResourceLocation> connections) {
      for (ResourceLocation connectedSkillId : new ArrayList<>(connections)) {
         if (SkillTreeClientData.getEditorSkill(connectedSkillId) == null) {
            connections.remove(connectedSkillId);
            SkillTreeClientData.saveEditorSkill(skill);
         } else {
            this.connectSkills(type, skill.getId(), connectedSkillId);
         }
      }
   }

   protected void connectSkills(SkillConnection.Type type, ResourceLocation skillId1, ResourceLocation skillId2) {
      SkillButton button1 = this.idToWidget.get(skillId1);
      SkillButton button2 = this.idToWidget.get(skillId2);
      this.skillConnections.add(new SkillConnection(type, button1, button2));
   }

   public SkillButton getWidgetById(ResourceLocation id) {
      return this.idToWidget.get(id);
   }
}
