package daripher.skilltree.client.widget.editor.menu;

import daripher.skilltree.client.data.SkillTreeClientData;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.client.widget.editor.menu.bonuses.SkillBonusesEditor;
import daripher.skilltree.client.widget.editor.menu.description.SkillDescriptionEditor;
import daripher.skilltree.client.widget.editor.menu.tags.SkillTagsEditor;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.PassiveSkillTree;
import java.util.Set;

public class MainEditorMenu extends EditorMenu {
   public MainEditorMenu(SkillTreeEditor editor) {
      super(editor, null);
   }

   @Override
   public void init() {
      this.clearWidgets();
      if (!this.editor.getSelectedSkills().isEmpty()) {
         this.addMenuSelectionButton(this.editor, "Bonuses", SkillBonusesEditor::new);
         this.addMenuSelectionButton(this.editor, "Textures", SkillTexturesEditor::new);
         this.addMenuSelectionButton(this.editor, "Button", SkillButtonEditor::new);
         this.addMenuSelectionButton(this.editor, "New Skill", SkillNodeEditor::new);
         this.addMenuSelectionButton(this.editor, "Tags", SkillTagsEditor::new);
         this.addMenuSelectionButton(this.editor, "Description", SkillDescriptionEditor::new);
         if (this.editor.getSelectedSkills().size() >= 2) {
            this.addMenuSelectionButton(this.editor, "Connections", SkillConnectionsEditor::new);
         }

         this.editor.addConfirmationButton(0, 0, 200, 14, "Remove", "Confirm").setPressFunc(b -> deleteSelectedSkills(this.editor));
         this.editor.increaseHeight(19);
      }
   }

   private static void deleteSelectedSkills(SkillTreeEditor editor) {
      Set<PassiveSkill> selectedSkills = editor.getSelectedSkills();
      PassiveSkillTree skillTree = editor.getSkillTree();
      selectedSkills.forEach(skill -> {
         skillTree.getSkillIds().remove(skill.getId());
         SkillTreeClientData.deleteEditorSkill(skill);
         SkillTreeClientData.saveEditorSkillTree(skillTree);
      });
      selectedSkills.clear();
      editor.rebuildWidgets();
   }

   private void addMenuSelectionButton(SkillTreeEditor editor, String name, EditorMenu.MenuConstructor menuConstructor) {
      editor.addButton(0, 0, 200, 14, name).setPressFunc(b -> this.selectMenu(editor, menuConstructor));
      editor.increaseHeight(19);
   }

   private void selectMenu(SkillTreeEditor editor, EditorMenu.MenuConstructor menuConstructor) {
      editor.selectMenu(menuConstructor.construct(editor, this));
   }
}
