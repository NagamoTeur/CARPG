package daripher.skilltree.client.widget.editor;

import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.client.data.SkillTreeClientData;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.Button;
import daripher.skilltree.client.widget.CheckBox;
import daripher.skilltree.client.widget.ConfirmationButton;
import daripher.skilltree.client.widget.Label;
import daripher.skilltree.client.widget.NumericTextField;
import daripher.skilltree.client.widget.SelectionList;
import daripher.skilltree.client.widget.TextArea;
import daripher.skilltree.client.widget.TextField;
import daripher.skilltree.client.widget.editor.menu.EditorMenu;
import daripher.skilltree.client.widget.editor.menu.MainEditorMenu;
import daripher.skilltree.client.widget.editor.menu.selection.SelectionMenuButton;
import daripher.skilltree.client.widget.group.WidgetGroup;
import daripher.skilltree.client.widget.skill.SkillButton;
import daripher.skilltree.client.widget.skill.SkillTreeButtons;
import daripher.skilltree.init.PSTAttributes;
import daripher.skilltree.init.PSTDamageConditions;
import daripher.skilltree.init.PSTEnchantmentConditions;
import daripher.skilltree.init.PSTEventListeners;
import daripher.skilltree.init.PSTItemBonuses;
import daripher.skilltree.init.PSTItemConditions;
import daripher.skilltree.init.PSTLivingConditions;
import daripher.skilltree.init.PSTLivingMultipliers;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.PassiveSkillTree;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.damage.DamageCondition;
import daripher.skilltree.skill.bonus.condition.enchantment.EnchantmentCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import daripher.skilltree.skill.bonus.condition.living.LivingCondition;
import daripher.skilltree.skill.bonus.event.SkillEventListener;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.multiplier.LivingMultiplier;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkillTreeEditor extends WidgetGroup<AbstractWidget> {
   private final SkillTreeButtons skillButtons;
   private final SkillSelector skillSelector;
   private final SkillMirrorer skillMirrorer;
   private final SkillDragger skillDragger;
   @NotNull
   private EditorMenu selectedMenu = new MainEditorMenu(this);

   public SkillTreeEditor(SkillTreeButtons skillButtons) {
      super(0, 0, 0, 0);
      this.skillButtons = skillButtons;
      this.skillSelector = new SkillSelector(this, skillButtons);
      this.skillMirrorer = new SkillMirrorer(this);
      this.skillDragger = new SkillDragger(this);
   }

   public void init() {
      this.clearWidgets();
      this.addWidget(this.selectedMenu);
      this.addWidget(this.skillSelector);
      this.addWidget(this.skillDragger);
      this.addWidget(this.skillMirrorer);
      this.selectedMenu.init();
   }

   @Override
   public void m_6305_(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTick) {
      this.skillMirrorer.m_6305_(graphics, mouseX, mouseY, partialTick);
      if (!this.skillSelector.getSelectedSkills().isEmpty()) {
         m_93172_(graphics, this.f_93620_, this.f_93621_, this.f_93620_ + this.f_93618_, this.f_93621_ + this.f_93619_, -587202560);
      }

      super.m_6305_(graphics, mouseX, mouseY, partialTick);
   }

   @Override
   public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
      if (keyCode != 256) {
         return super.m_7933_(keyCode, scanCode, modifiers);
      } else if (this.selectedMenu.previousMenu != null) {
         this.selectMenu(this.selectedMenu.previousMenu);
         return true;
      } else if (!this.skillSelector.getSelectedSkills().isEmpty()) {
         this.skillSelector.clearSelection();
         return true;
      } else {
         return super.m_7933_(keyCode, scanCode, modifiers);
      }
   }

   public void selectMenu(EditorMenu menu) {
      if (menu != null) {
         this.selectedMenu = menu;
         this.rebuildWidgets();
      }
   }

   public Button addButton(int x, int y, int width, int height, String message) {
      return this.addButton(x, y, width, height, Component.m_237113_(message));
   }

   public Button addButton(int x, int y, int width, int height, Component message) {
      return this.addWidget(new Button(this.getWidgetsX(x), this.getWidgetsY(y), width, height, message));
   }

   public ConfirmationButton addConfirmationButton(int x, int y, int width, int height, String message, String confirmationMessage) {
      ConfirmationButton button = new ConfirmationButton(this.getWidgetsX(x), this.getWidgetsY(y), width, height, Component.m_237113_(message));
      button.setConfirmationMessage(Component.m_237113_(confirmationMessage));
      return this.addWidget(button);
   }

   public TextField addTextField(int x, int y, int width, int height, String defaultValue) {
      return this.addWidget(new TextField(this.getWidgetsX(x), this.getWidgetsY(y), width, height, defaultValue));
   }

   public NumericTextField addNumericTextField(int x, int y, int width, int height, double defaultValue) {
      return this.addWidget(new NumericTextField(this.getWidgetsX(x), this.getWidgetsY(y), width, height, defaultValue));
   }

   public TextArea addTextArea(int x, int y, int width, int height, String defaultValue) {
      return this.addWidget(new TextArea(this.getWidgetsX(x), this.getWidgetsY(y), width, height, defaultValue));
   }

   public Label addLabel(int x, int y, String text, ChatFormatting... styles) {
      MutableComponent message = Component.m_237113_(text);

      for (ChatFormatting style : styles) {
         message.m_130940_(style);
      }

      return this.addWidget(new Label(this.getWidgetsX(x), this.getWidgetsY(y), message));
   }

   public CheckBox addCheckBox(int x, int y, boolean value) {
      return this.addWidget(new CheckBox(this.getWidgetsX(x), this.getWidgetsY(y), value));
   }

   public SelectionMenuButton<SkillBonus> addSelectionMenu(int x, int y, int width, SkillBonus defaultValue) {
      Collection<SkillBonus> values = PSTSkillBonuses.bonusList();
      return this.addSelectionMenu(x, y, width, values).setValue(defaultValue).setElementNameGetter(b -> Component.m_237113_(PSTSkillBonuses.getName(b)));
   }

   public SelectionMenuButton<ItemBonus> addSelectionMenu(int x, int y, int width, ItemBonus defaultValue) {
      Collection<ItemBonus> values = PSTItemBonuses.bonusList();
      return this.addSelectionMenu(x, y, width, values).setValue(defaultValue).setElementNameGetter(b -> Component.m_237113_(PSTItemBonuses.getName(b)));
   }

   public SelectionMenuButton<Attribute> addSelectionMenu(int x, int y, int width, Attribute defaultValue) {
      Collection<Attribute> values = PSTAttributes.attributeList();
      return this.addSelectionMenu(x, y, width, values).setValue(defaultValue).setElementNameGetter(a -> Component.m_237113_(PSTAttributes.getName(a)));
   }

   public SelectionMenuButton<LivingCondition> addSelectionMenu(int x, int y, int width, LivingCondition defaultValue) {
      Collection<LivingCondition> values = PSTLivingConditions.conditionsList();
      return this.addSelectionMenu(x, y, width, values).setValue(defaultValue).setElementNameGetter(c -> Component.m_237113_(PSTLivingConditions.getName(c)));
   }

   public SelectionMenuButton<LivingMultiplier> addSelectionMenu(int x, int y, int width, LivingMultiplier defaultValue) {
      Collection<LivingMultiplier> values = PSTLivingMultipliers.multiplierList();
      return this.addSelectionMenu(x, y, width, values).setValue(defaultValue).setElementNameGetter(m -> Component.m_237113_(PSTLivingMultipliers.getName(m)));
   }

   public SelectionMenuButton<ItemCondition> addSelectionMenu(int x, int y, int width, ItemCondition defaultValue) {
      Collection<ItemCondition> values = PSTItemConditions.conditionsList();
      return this.addSelectionMenu(x, y, width, values).setValue(defaultValue).setElementNameGetter(c -> Component.m_237113_(PSTItemConditions.getName(c)));
   }

   public SelectionMenuButton<MobEffect> addSelectionMenu(int x, int y, int width, MobEffect defaultValue) {
      Collection<MobEffect> values = ForgeRegistries.MOB_EFFECTS.getValues();
      return this.addSelectionMenu(x, y, width, values).setValue(defaultValue).setElementNameGetter(e -> Component.m_237115_(e.m_19481_()));
   }

   public SelectionMenuButton<DamageCondition> addSelectionMenu(int x, int y, int width, DamageCondition defaultValue) {
      List<DamageCondition> values = PSTDamageConditions.conditionsList();
      return this.addSelectionMenu(x, y, width, values).setValue(defaultValue).setElementNameGetter(c -> Component.m_237115_(PSTDamageConditions.getName(c)));
   }

   public SelectionMenuButton<SkillEventListener> addSelectionMenu(int x, int y, int width, SkillEventListener defaultValue) {
      List<SkillEventListener> values = PSTEventListeners.eventsList();
      return this.addSelectionMenu(x, y, width, values).setValue(defaultValue).setElementNameGetter(e -> Component.m_237115_(PSTEventListeners.getName(e)));
   }

   public SelectionMenuButton<EnchantmentCondition> addSelectionMenu(int x, int y, int width, EnchantmentCondition defaultValue) {
      List<EnchantmentCondition> values = PSTEnchantmentConditions.conditionsList();
      return this.addSelectionMenu(x, y, width, values)
         .setValue(defaultValue)
         .setElementNameGetter(c -> Component.m_237115_(PSTEnchantmentConditions.getName(c)));
   }

   public <T extends Enum<T>> SelectionMenuButton<T> addSelectionMenu(int x, int y, int width, T defaultValue) {
      List<T> values = getEnumValues(defaultValue);
      return this.addSelectionMenu(x, y, width, values).setValue(defaultValue);
   }

   public <T> SelectionMenuButton<T> addSelectionMenu(int x, int y, int width, Collection<T> values) {
      return this.addWidget(new SelectionMenuButton<>(this, this.getWidgetsX(x), this.getWidgetsY(y), width, values));
   }

   public <T> SelectionList<T> addSelection(int x, int y, int width, T defaultValue, Collection<T> values, int maxDisplayed) {
      SelectionList<T> widget = new SelectionList<>(this.getWidgetsX(x), this.getWidgetsY(y), width, values)
         .setMaxDisplayed(maxDisplayed)
         .setValue(defaultValue);
      return this.addWidget(widget);
   }

   public SelectionList<Operation> addOperationSelection(int x, int y, int width, Operation defaultValue) {
      List<Operation> values = List.of(Operation.values());
      return this.addSelection(x, y, width, defaultValue, values, 1).setNameGetter(TooltipHelper::getOperationName);
   }

   public <T extends Enum<T>> SelectionList<T> addSelection(int x, int y, int width, int maxDisplayed, T defaultValue) {
      List<T> values = getEnumValues(defaultValue);
      return this.addSelection(x, y, width, defaultValue, values, maxDisplayed);
   }

   @NotNull
   private static <T extends Enum<T>> List<T> getEnumValues(T defaultValue) {
      Class<T> enumType = (Class<T>)defaultValue.getClass();
      return List.of(enumType.getEnumConstants());
   }

   public void addMirrorerWidgets() {
      this.skillMirrorer.init();
   }

   public Set<PassiveSkill> getSelectedSkills() {
      return this.skillSelector.getSelectedSkills();
   }

   @Nullable
   public PassiveSkill getFirstSelectedSkill() {
      return this.skillSelector.getFirstSelectedSkill();
   }

   public SkillMirrorer getSkillMirrorer() {
      return this.skillMirrorer;
   }

   public void saveSelectedSkills() {
      this.skillSelector.getSelectedSkills().forEach(SkillTreeClientData::saveEditorSkill);
   }

   public int getWidgetsY(int y) {
      return this.m_93694_() + y;
   }

   public int getWidgetsX(int x) {
      return this.f_93620_ + 5 + x;
   }

   public float getScrollX() {
      return this.skillButtons.getScrollX();
   }

   public float getScrollY() {
      return this.skillButtons.getScrollY();
   }

   public float getZoom() {
      return this.skillButtons.getZoom();
   }

   public void increaseHeight(int delta) {
      this.setHeight(this.m_93694_() + delta);
   }

   public PassiveSkillTree getSkillTree() {
      return this.skillButtons.getSkillTree();
   }

   public List<PassiveSkill> getSkills() {
      return this.getSkillTree().getSkillIds().stream().map(SkillTreeClientData::getEditorSkill).toList();
   }

   public Collection<SkillButton> getSkillButtons() {
      return this.skillButtons.getWidgets();
   }

   public void addSkillButton(PassiveSkill skill) {
      SkillButton button = this.skillButtons.addSkillButton(skill, () -> 0.0F);
      button.skillLearned = true;
   }

   public void updateSkillConnections() {
      this.skillButtons.updateSkillConnections();
   }

   @Override
   public void rebuildWidgets() {
      super.rebuildWidgets();
      this.updateSkillConnections();
   }

   public boolean canEdit(Function<PassiveSkill, ?> function) {
      return this.getSelectedSkills().stream().map(function).distinct().count() <= 1L;
   }

   public void removeSkillButton(PassiveSkill skill) {
      this.skillButtons.getWidgets().removeIf(button -> button.skill == skill);
   }

   public SkillButton getSkillButton(ResourceLocation skillId) {
      return this.skillButtons.getWidgetById(skillId);
   }

   public int getScreenWidth() {
      return this.skillButtons.m_5711_();
   }

   public int getScreenHeight() {
      return this.skillButtons.m_93694_();
   }

   @NotNull
   public EditorMenu getSelectedMenu() {
      return this.selectedMenu;
   }
}
