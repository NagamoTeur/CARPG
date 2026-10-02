package com.hollingsworth.arsnouveau.client.patchouli.component;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.world.item.crafting.Ingredient;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;

abstract class RotatingItemListComponentBase implements ICustomComponent {
   protected transient List<Ingredient> ingredients;
   protected transient int x;
   protected transient int y;

   public void build(int componentX, int componentY, int pageNum) {
      this.x = componentX != -1 ? componentX : 17;
      this.y = componentY;
      this.ingredients = this.makeIngredients();
   }

   protected abstract List<Ingredient> makeIngredients();

   public void render(PoseStack ms, IComponentRenderContext context, float pticks, int mouseX, int mouseY) {
      int degreePerInput = (int)(360.0F / (float)this.ingredients.size());
      int ticksElapsed = 0;
      float currentDegree = (float)ticksElapsed;

      for (Ingredient input : this.ingredients) {
         this.renderIngredientAtAngle(ms, context, currentDegree, input, mouseX, mouseY);
         currentDegree += (float)degreePerInput;
      }
   }

   private void renderIngredientAtAngle(PoseStack ms, IComponentRenderContext context, float angle, Ingredient ingredient, int mouseX, int mouseY) {
      if (!ingredient.m_43947_()) {
         angle -= 90.0F;
         int radius = 32;
         double xPos = (double)this.x + Math.cos((double)angle * Math.PI / 180.0) * (double)radius + 32.0;
         double yPos = (double)this.y + Math.sin((double)angle * Math.PI / 180.0) * (double)radius + 32.0;
         ms.m_85836_();
         ms.m_85837_(xPos - (double)((int)xPos), yPos - (double)((int)yPos), 0.0);
         context.renderIngredient(ms, (int)xPos, (int)yPos, mouseX, mouseY, ingredient);
         ms.m_85849_();
      }
   }
}
