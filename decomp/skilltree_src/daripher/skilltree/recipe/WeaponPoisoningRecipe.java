package daripher.skilltree.recipe;

import com.google.gson.JsonObject;
import daripher.skilltree.init.PSTRecipeSerializers;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.skill.bonus.condition.item.EquipmentCondition;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class WeaponPoisoningRecipe extends CustomRecipe implements SkillRequiringRecipe {
   public WeaponPoisoningRecipe(ResourceLocation id) {
      super(id);
   }

   public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
      if (this.isUncraftable(container, this)) {
         return false;
      } else {
         int weaponsCount = 0;
         int poisonsCount = 0;

         for (int slot = 0; slot < container.m_6643_(); slot++) {
            ItemStack stackInSlot = container.m_8020_(slot);
            if (!stackInSlot.m_41619_()) {
               if (EquipmentCondition.isMeleeWeapon(stackInSlot)) {
                  weaponsCount++;
               } else if (this.isPoison(stackInSlot)) {
                  poisonsCount++;
               }
            }
         }

         return weaponsCount == 1 && poisonsCount == 1;
      }
   }

   @NotNull
   public ItemStack assemble(@NotNull CraftingContainer container) {
      if (this.isUncraftable(container, this)) {
         return ItemStack.f_41583_;
      } else {
         ItemStack weaponStack = ItemStack.f_41583_;
         ItemStack poisonStack = ItemStack.f_41583_;

         for (int slot = 0; slot < container.m_6643_(); slot++) {
            ItemStack stackInSlot = container.m_8020_(slot);
            if (!stackInSlot.m_41619_()) {
               if (EquipmentCondition.isMeleeWeapon(stackInSlot)) {
                  weaponStack = stackInSlot;
               }

               if (this.isPoison(stackInSlot)) {
                  poisonStack = stackInSlot;
               }
            }
         }

         ItemStack result = weaponStack.m_41777_();
         ItemHelper.setPoisons(result, poisonStack);
         return result;
      }
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   @NotNull
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)PSTRecipeSerializers.WEAPON_POISONING.get();
   }

   private boolean isPoison(ItemStack stack) {
      return PotionUtils.m_43566_(stack.m_41783_()).stream().anyMatch(effect -> effect.m_19544_().m_19483_() == MobEffectCategory.HARMFUL);
   }

   public static class Serializer implements RecipeSerializer<WeaponPoisoningRecipe> {
      @NotNull
      public WeaponPoisoningRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject jsonObject) {
         return new WeaponPoisoningRecipe(id);
      }

      public WeaponPoisoningRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
         return new WeaponPoisoningRecipe(id);
      }

      public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull WeaponPoisoningRecipe recipe) {
      }
   }
}
