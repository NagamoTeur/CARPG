package daripher.skilltree.data.generation.skills;

import com.google.gson.JsonElement;
import daripher.skilltree.data.reloader.SkillTreesReloader;
import daripher.skilltree.skill.PassiveSkillTree;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PSTSkillTreesProvider implements DataProvider {
   private final DataGenerator dataGenerator;
   private final PSTSkillsProvider skillsProvider;

   public PSTSkillTreesProvider(DataGenerator dataGenerator, PSTSkillsProvider skillsProvider) {
      this.dataGenerator = dataGenerator;
      this.skillsProvider = skillsProvider;
   }

   public void m_213708_(@NotNull CachedOutput output) {
      PassiveSkillTree skillTree = new PassiveSkillTree(new ResourceLocation("skilltree", "main_tree"));
      this.skillsProvider.getSkills().keySet().forEach(skillTree.getSkillIds()::add);
      Path path = this.dataGenerator.m_123916_().resolve(this.getSkillTreePath(skillTree));
      JsonElement json = SkillTreesReloader.GSON.toJsonTree(skillTree);

      try {
         DataProvider.m_236072_(output, json, path);
      } catch (IOException var6) {
         var6.printStackTrace();
      }
   }

   public String getSkillTreePath(PassiveSkillTree skillTree) {
      ResourceLocation id = skillTree.getId();
      return "data/%s/skill_trees/%s.json".formatted(id.m_135827_(), id.m_135815_());
   }

   @NotNull
   public String m_6055_() {
      return "Skill Trees Provider";
   }
}
