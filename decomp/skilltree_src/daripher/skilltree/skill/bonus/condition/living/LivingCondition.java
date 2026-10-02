package daripher.skilltree.skill.bonus.condition.living;

import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.init.PSTRegistries;
import java.util.function.Consumer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public interface LivingCondition {
   boolean met(LivingEntity var1);

   default String getDescriptionId() {
      ResourceLocation id = PSTRegistries.LIVING_CONDITIONS.get().getKey(this.getSerializer());
      if (!<unrepresentable>.$assertionsDisabled && id == null) {
         throw new AssertionError();
      } else {
         return "living_condition.%s.%s".formatted(id.m_135827_(), id.m_135815_());
      }
   }

   MutableComponent getTooltip(MutableComponent var1, String var2);

   LivingCondition.Serializer getSerializer();

   default void addEditorWidgets(SkillTreeEditor editor, Consumer<LivingCondition> consumer) {
   }

   static {
      if (<unrepresentable>.$assertionsDisabled) {
      }
   }

   public interface Serializer extends daripher.skilltree.data.serializers.Serializer<LivingCondition> {
      LivingCondition createDefaultInstance();
   }
}
