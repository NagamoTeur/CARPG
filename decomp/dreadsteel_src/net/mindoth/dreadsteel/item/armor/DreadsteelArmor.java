package net.mindoth.dreadsteel.item.armor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.mindoth.dreadsteel.DreadsteelClient;
import net.mindoth.dreadsteel.client.models.armor.DreadsteelModel;
import net.mindoth.dreadsteel.registries.DreadsteelItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "dreadsteel"
)
public class DreadsteelArmor extends ArmorItem {
   private final LazyLoadedValue<HumanoidModel<?>> model;

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("tooltip.dreadsteel.dreadsteel_setbonus"));
      super.m_7373_(stack, world, tooltip, flagIn);
   }

   public boolean m_8120_(ItemStack p_77616_1_) {
      return true;
   }

   public DreadsteelArmor(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
      super(material, slot, properties.m_41486_());
      this.model = (LazyLoadedValue<HumanoidModel<?>>)DistExecutor.unsafeRunForDist(
         () -> () -> new LazyLoadedValue(() -> this.provideArmorModelForSlot(slot)), () -> () -> null
      );
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      CompoundTag tag = stack.m_41783_();
      String color = null;
      if (tag != null) {
         if (tag.m_128451_("CustomModelData") == 1) {
            color = "dreadsteel:textures/item/dreadsteel_armor_model_white.png";
         }

         if (tag.m_128451_("CustomModelData") == 2) {
            color = "dreadsteel:textures/item/dreadsteel_armor_model_black.png";
         }

         if (tag.m_128451_("CustomModelData") == 3) {
            color = "dreadsteel:textures/item/dreadsteel_armor_model_bronze.png";
         }

         if (tag.m_128451_("CustomModelData") == 0) {
            color = "dreadsteel:textures/item/dreadsteel_armor_model_default.png";
         }
      } else {
         color = "dreadsteel:textures/item/dreadsteel_armor_model_default.png";
      }

      return color;
   }

   @OnlyIn(Dist.CLIENT)
   public HumanoidModel<?> provideArmorModelForSlot(EquipmentSlot slot) {
      return new DreadsteelModel(Minecraft.m_91087_().m_167973_().m_171103_(DreadsteelClient.DREADSTEEL_ARMOR), slot);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
            return (HumanoidModel<?>)DreadsteelArmor.this.model.m_13971_();
         }
      });
   }

   public static enum MaterialDreadsteel implements ArmorMaterial {
      DREADSTEEL(
         "dreadsteel",
         0,
         new int[]{0, 0, 0, 0},
         25,
         SoundEvents.f_11679_,
         0.0F,
         0.0F,
         () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)DreadsteelItems.DREADSTEEL_INGOT.get()})
      );

      private static final int[] MAX_DAMAGE_ARRAY = new int[]{0, 0, 0, 0};
      private final String name;
      private final int maxDamageFactor;
      private final int[] damageReductionAmountArray;
      private final int enchantability;
      private final SoundEvent soundEvent;
      private final float toughness;
      private final float knockbackResistance;
      private final Supplier<Ingredient> repairMaterial;

      private MaterialDreadsteel(
         String name,
         int maxDamageFactor,
         int[] damageReductionAmountArray,
         int enchantability,
         SoundEvent soundEvent,
         float toughness,
         float knockbackResistance,
         Supplier<Ingredient> repairMaterial
      ) {
         this.name = name;
         this.maxDamageFactor = maxDamageFactor;
         this.damageReductionAmountArray = damageReductionAmountArray;
         this.enchantability = enchantability;
         this.soundEvent = soundEvent;
         this.toughness = toughness;
         this.knockbackResistance = knockbackResistance;
         this.repairMaterial = repairMaterial;
      }

      public int m_7366_(EquipmentSlot slotIn) {
         return MAX_DAMAGE_ARRAY[slotIn.m_20749_()] * this.maxDamageFactor;
      }

      public int m_7365_(EquipmentSlot slotIn) {
         return this.damageReductionAmountArray[slotIn.m_20749_()];
      }

      public int m_6646_() {
         return this.enchantability;
      }

      public SoundEvent m_7344_() {
         return this.soundEvent;
      }

      public Ingredient m_6230_() {
         return this.repairMaterial.get();
      }

      @OnlyIn(Dist.CLIENT)
      public String m_6082_() {
         return this.name;
      }

      public float m_6651_() {
         return this.toughness;
      }

      public float m_6649_() {
         return this.knockbackResistance;
      }
   }
}
