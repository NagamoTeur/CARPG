package daripher.skilltree.client.widget.editor.menu.bonuses;

import daripher.skilltree.client.data.SkillTreeClientData;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.client.widget.editor.menu.EditorMenu;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.bonus.SkillBonus;
import java.util.List;

public class SkillBonusEditor extends EditorMenu {
   private final int selectedBonus;

   public SkillBonusEditor(SkillTreeEditor editor, EditorMenu previousMenu, int selectedBonus) {
      super(editor, previousMenu);
      this.selectedBonus = selectedBonus;
   }

   @Override
   public void init() {
      this.editor.addButton(0, 0, 90, 14, "Back").setPressFunc(b -> this.editor.selectMenu(this.previousMenu));
      this.editor.addConfirmationButton(110, 0, 90, 14, "Remove", "Confirm").setPressFunc(b -> this.deleteSelectedSkillBonuses(this.editor));
      this.editor.increaseHeight(29);
      if (this.canEditBonuses(this.editor)) {
         PassiveSkill selectedSkill = this.editor.getFirstSelectedSkill();
         if (selectedSkill != null) {
            List<SkillBonus<?>> bonuses = selectedSkill.getBonuses();
            if (this.selectedBonus >= bonuses.size()) {
               this.editor.selectMenu(this.previousMenu);
            } else {
               selectedSkill.getBonuses().get(this.selectedBonus).addEditorWidgets(this.editor, this.selectedBonus, b -> this.setSkillBonuses(this.editor, b));
            }
         }
      }
   }

   private void setSkillBonuses(SkillTreeEditor editor, SkillBonus<?> b) {
      editor.getSelectedSkills().forEach(s -> s.getBonuses().set(this.selectedBonus, b.copy()));
      editor.saveSelectedSkills();
   }

   private boolean canEditBonuses(SkillTreeEditor editor) {
      PassiveSkill selectedSkill = editor.getFirstSelectedSkill();
      if (selectedSkill == null) {
         return false;
      } else {
         for (PassiveSkill otherSkill : editor.getSelectedSkills()) {
            if (otherSkill != selectedSkill) {
               List<SkillBonus<?>> bonuses = otherSkill.getBonuses();
               List<SkillBonus<?>> otherBonuses = selectedSkill.getBonuses();
               if (bonuses.size() != otherBonuses.size()) {
                  return false;
               }

               for (int i = 0; i < bonuses.size(); i++) {
                  if (!bonuses.get(i).sameBonus(otherBonuses.get(i))) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private void deleteSelectedSkillBonuses(SkillTreeEditor editor) {
      editor.getSelectedSkills().forEach(s -> this.removeSkillBonus(s, this.selectedBonus));
      editor.selectMenu(this.previousMenu);
      editor.saveSelectedSkills();
      editor.rebuildWidgets();
   }

   private void removeSkillBonus(PassiveSkill skill, int index) {
      skill.getBonuses().remove(index);
      SkillTreeClientData.saveEditorSkill(skill);
   }
}
