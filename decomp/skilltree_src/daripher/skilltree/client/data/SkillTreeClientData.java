package daripher.skilltree.client.data;

import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;
import daripher.skilltree.data.reloader.SkillTreesReloader;
import daripher.skilltree.data.reloader.SkillsReloader;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.PassiveSkillTree;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;

public class SkillTreeClientData {
   private static final Map<ResourceLocation, PassiveSkill> EDITOR_PASSIVE_SKILLS = new HashMap<>();
   private static final Map<ResourceLocation, PassiveSkillTree> EDITOR_TREES = new HashMap<>();
   public static int[] skill_points_costs;
   public static int first_skill_cost;
   public static int last_skill_cost;
   public static int max_skill_points;
   public static boolean enable_exp_exchange;
   public static boolean use_skill_cost_array;

   public static int getSkillPointCost(int level) {
      if (use_skill_cost_array) {
         return level >= skill_points_costs.length ? skill_points_costs[skill_points_costs.length - 1] : skill_points_costs[level];
      } else {
         return first_skill_cost + (last_skill_cost - first_skill_cost) * level / max_skill_points;
      }
   }

   public static PassiveSkill getEditorSkill(ResourceLocation id) {
      return EDITOR_PASSIVE_SKILLS.get(id);
   }

   @Nullable
   public static PassiveSkillTree getOrCreateEditorTree(ResourceLocation treeId) {
      try {
         File folder = getSkillTreeSavesFolder(treeId);
         if (!folder.exists()) {
            folder.mkdirs();
         }

         File mcmetaFile = new File(getEditorFolder(), "pack.mcmeta");
         if (!mcmetaFile.exists()) {
            generatePackMcmetaFile(mcmetaFile);
         }

         if (!getSkillTreeSaveFile(treeId).exists()) {
            PassiveSkillTree skillTree = SkillTreesReloader.getSkillTreeById(treeId);
            saveEditorSkillTree(skillTree);
         }

         if (!EDITOR_TREES.containsKey(treeId)) {
            loadEditorSkillTree(treeId);
         }

         PassiveSkillTree skillTree = EDITOR_TREES.getOrDefault(treeId, new PassiveSkillTree(treeId));

         for (ResourceLocation skillId : skillTree.getSkillIds()) {
            try {
               loadOrCreateEditorSkill(skillId);
            } catch (Exception var8) {
               var8.printStackTrace();
               printMessage("Couldn't read passive skill " + skillId, ChatFormatting.DARK_RED);
               printMessage("");
               String errorMessage = var8.getMessage() == null ? "No error message" : var8.getMessage();
               printMessage(errorMessage, ChatFormatting.RED);
               return null;
            }
         }

         return skillTree;
      } catch (Exception var9) {
         EDITOR_TREES.clear();
         EDITOR_PASSIVE_SKILLS.clear();
         printMessage("Couldn't read skill tree " + treeId, ChatFormatting.DARK_RED);
         printMessage("");
         String errorMessage = var9.getMessage() == null ? "No error message" : var9.getMessage();
         printMessage(errorMessage, ChatFormatting.RED);
         printMessage("");
         printMessage("Try removing files from folder", ChatFormatting.DARK_RED);
         printMessage("");
         printMessage(getSavesFolder().getPath(), ChatFormatting.RED);
         var9.printStackTrace();
         return null;
      }
   }

   private static void generatePackMcmetaFile(File file) {
      try {
         BufferedWriter writer = new BufferedWriter(new FileWriter(file));
         String contents = "{\n    \"pack\": {\n        \"description\": \"PST editor data\",\n        \"pack_format\": 9,\n        \"forge:data_pack_format\": 10\n    }\n}\n";
         writer.write(contents);
         writer.close();
      } catch (IOException var3) {
         var3.printStackTrace();
         throw new RuntimeException(var3);
      }
   }

   private static void loadOrCreateEditorSkill(ResourceLocation skillId) {
      File skillSavesFolder = getSkillSavesFolder(skillId);
      if (!skillSavesFolder.exists()) {
         skillSavesFolder.mkdirs();
      }

      if (!getSkillSaveFile(skillId).exists()) {
         PassiveSkill skill = SkillsReloader.getSkillById(skillId);
         if (skill != null) {
            saveEditorSkill(skill);
         }
      }

      if (!EDITOR_PASSIVE_SKILLS.containsKey(skillId)) {
         loadEditorSkill(skillId);
      }
   }

   public static void saveEditorSkillTree(PassiveSkillTree skillTree) {
      File file = getSkillTreeSaveFile(skillTree.getId());

      try {
         try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            SkillTreesReloader.GSON.toJson(skillTree, writer);
         }
      } catch (IOException | JsonIOException var7) {
         var7.printStackTrace();
         throw new RuntimeException("Can't save editor skill tree " + skillTree.getId());
      }
   }

   public static void loadEditorSkillTree(ResourceLocation treeId) throws IOException {
      File file = getSkillTreeSaveFile(treeId);

      PassiveSkillTree skillTree;
      try {
         skillTree = readFromFile(PassiveSkillTree.class, file);
      } catch (Exception var4) {
         skillTree = new PassiveSkillTree(treeId);
         saveEditorSkillTree(skillTree);
         EDITOR_TREES.put(treeId, skillTree);
         throw var4;
      }

      EDITOR_TREES.put(treeId, skillTree);
   }

   public static void saveEditorSkill(PassiveSkill skill) {
      File file = getSkillSaveFile(skill.getId());

      try {
         try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            SkillsReloader.GSON.toJson(skill, writer);
         }
      } catch (IOException | JsonIOException var7) {
         var7.printStackTrace();
         throw new RuntimeException("Can't save editor skill " + skill.getId());
      }
   }

   public static void loadEditorSkill(ResourceLocation skillId) {
      PassiveSkill skill;
      try {
         skill = readFromFile(PassiveSkill.class, getSkillSaveFile(skillId));
      } catch (IOException var3) {
         var3.printStackTrace();
         printMessage("Can't load editor skill " + skillId, ChatFormatting.DARK_RED);
         throw new RuntimeException("Can't load editor skill " + skillId);
      }

      EDITOR_PASSIVE_SKILLS.put(skillId, skill);
   }

   public static void deleteEditorSkill(PassiveSkill skill) {
      getSkillSaveFile(skill.getId()).delete();
      EDITOR_PASSIVE_SKILLS.remove(skill.getId());
   }

   private static File getSavesFolder() {
      return new File(getEditorFolder(), "data");
   }

   private static File getEditorFolder() {
      return new File(FMLPaths.GAMEDIR.get().toFile(), "skilltree/editor");
   }

   private static File getSkillSavesFolder(ResourceLocation skillId) {
      return new File(getSavesFolder(), skillId.m_135827_() + "/skills");
   }

   private static File getSkillTreeSavesFolder(ResourceLocation skillTreeId) {
      return new File(getSavesFolder(), skillTreeId.m_135827_() + "/skill_trees");
   }

   private static File getSkillSaveFile(ResourceLocation skillId) {
      return new File(getSkillSavesFolder(skillId), skillId.m_135815_() + ".json");
   }

   private static File getSkillTreeSaveFile(ResourceLocation skillTreeId) {
      return new File(getSkillTreeSavesFolder(skillTreeId), skillTreeId.m_135815_() + ".json");
   }

   private static <T> T readFromFile(Class<T> objectType, File file) throws IOException {
      JsonReader reader = new JsonReader(new FileReader(file, StandardCharsets.UTF_8));

      Object var3;
      try {
         var3 = SkillsReloader.GSON.fromJson(reader, objectType);
      } catch (Throwable var6) {
         try {
            reader.close();
         } catch (Throwable var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }

      reader.close();
      return (T)var3;
   }

   private static void printMessage(String text, ChatFormatting... styles) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         MutableComponent component = Component.m_237113_(text);

         for (ChatFormatting style : styles) {
            component.m_130940_(style);
         }

         player.m_213846_(component);
      }
   }
}
