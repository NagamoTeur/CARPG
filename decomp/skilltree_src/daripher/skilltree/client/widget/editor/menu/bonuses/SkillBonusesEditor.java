package daripher.skilltree.client.widget.editor.menu.bonuses;

import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.client.widget.editor.menu.EditorMenu;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.bonus.SkillBonus;
import java.util.List;
import net.minecraft.network.chat.Component;

public class SkillBonusesEditor extends EditorMenu {
   public SkillBonusesEditor(SkillTreeEditor editor, EditorMenu previousMenu) {
      super(editor, previousMenu);
   }

   @Override
   public void init() {
      this.editor.addButton(0, 0, 90, 14, "Back").setPressFunc(b -> this.editor.selectMenu(this.previousMenu));
      this.editor.increaseHeight(29);
      if (this.canEditBonuses(this.editor)) {
         SkillBonus<?> defaultBonus = ((SkillBonus.Serializer)PSTSkillBonuses.ATTRIBUTE.get()).createDefaultInstance();
         this.editor
            .addSelectionMenu(110, -29, 90, defaultBonus)
            .setResponder(skillBonus -> this.addSkillBonuses(this.editor, skillBonus))
            .m_93666_(Component.m_237113_("Add"));
         PassiveSkill selectedSkill = this.editor.getFirstSelectedSkill();
         if (selectedSkill != null) {
            List<SkillBonus<?>> bonuses = selectedSkill.getBonuses();

            for (int i = 0; i < bonuses.size(); i++) {
               int bonusIndex = i;
               SkillBonus<?> bonus = bonuses.get(i);
               String message = bonus.getTooltip().getString();
               message = TooltipHelper.getTrimmedString(message, 190);
               this.editor.addButton(0, 0, 200, 14, message).setPressFunc(b -> this.editor.selectMenu(new SkillBonusEditor(this.editor, this, bonusIndex)));
               this.editor.increaseHeight(19);
            }
         }
      }
   }

   private void addSkillBonuses(SkillTreeEditor editor, SkillBonus<?> skillBonus) {
      editor.getSelectedSkills().forEach(s -> s.getBonuses().add(skillBonus.copy()));
      editor.saveSelectedSkills();
      editor.selectMenu(editor.getSelectedMenu().previousMenu);
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
}
