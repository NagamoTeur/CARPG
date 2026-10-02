package com.aizistral.enigmaticlegacy.items.generic;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.client.models.UnseenArmorModel;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public abstract class ItemBaseArmor extends ArmorItem {
   @OnlyIn(Dist.CLIENT)
   private HumanoidModel<?> model;

   public ItemBaseArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
      super(materialIn, slot, builder);
   }

   public ItemBaseArmor(ArmorMaterial materialIn, EquipmentSlot slot) {
      this(materialIn, slot, getDefaultProperties());
   }

   @OnlyIn(Dist.CLIENT)
   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         @OnlyIn(Dist.CLIENT)
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> original) {
            return ItemBaseArmor.this.provideArmorModelForSlot(armorSlot, original);
         }
      });
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public HumanoidModel<?> provideArmorModelForSlot(EquipmentSlot slot, HumanoidModel<?> original) {
      return this.model != null ? this.model : (this.model = new UnseenArmorModel(original));
   }

   @OnlyIn(Dist.CLIENT)
   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return "enigmaticlegacy:textures/models/armor/unseen_armor.png";
   }

   public boolean hasFullSet(@Nonnull Player player) {
      if (player == null) {
         return false;
      } else {
         for (ItemStack stack : player.m_6168_()) {
            if (stack.m_41720_().getClass() != this.getClass()) {
               return false;
            }
         }

         return true;
      }
   }

   public static Properties getDefaultProperties() {
      Properties props = new Properties();
      props.m_41491_(EnigmaticLegacy.MAIN_TAB);
      props.m_41487_(1);
      props.m_41497_(Rarity.COMMON);
      return props;
   }
}
