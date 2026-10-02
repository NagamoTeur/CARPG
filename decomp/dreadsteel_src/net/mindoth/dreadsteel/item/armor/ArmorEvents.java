package net.mindoth.dreadsteel.item.armor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.mindoth.dreadsteel.config.DreadsteelCommonConfig;
import net.mindoth.dreadsteel.registries.DreadsteelItems;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(
   modid = "dreadsteel"
)
public class ArmorEvents {
   public static final Map<String, UUID> NAME_UUID_MAP = new HashMap<>();

   public static UUID getUUID(ItemStack stack) {
      return NAME_UUID_MAP.computeIfAbsent(ForgeRegistries.ITEMS.getKey(stack.m_41720_()).toString(), s -> UUID.nameUUIDFromBytes(s.getBytes()));
   }

   @SubscribeEvent
   public static void noHat(RenderPlayerEvent event) {
      Player player = event.getEntity();
      if (player.m_6844_(EquipmentSlot.HEAD).m_41720_() == DreadsteelItems.DREADSTEEL_HELMET.get()) {
         ((PlayerModel)event.getRenderer().m_7200_()).f_102809_.f_104207_ = false;
      }
   }

   @SubscribeEvent
   public static void dreadsteelSetDefence(LivingAttackEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity.m_6844_(EquipmentSlot.HEAD).m_41720_() == DreadsteelItems.DREADSTEEL_HELMET.get()
         && entity.m_6844_(EquipmentSlot.CHEST).m_41720_() == DreadsteelItems.DREADSTEEL_CHESTPLATE.get()
         && entity.m_6844_(EquipmentSlot.LEGS).m_41720_() == DreadsteelItems.DREADSTEEL_LEGGINGS.get()
         && entity.m_6844_(EquipmentSlot.FEET).m_41720_() == DreadsteelItems.DREADSTEEL_BOOTS.get()
         && (
            event.getSource().m_19385_().equals(DamageSource.f_19306_.m_19385_())
               || event.getSource().m_19385_().equals(DamageSource.f_19305_.m_19385_())
               || event.getSource().m_19385_().equals(DamageSource.f_19307_.m_19385_())
               || event.getSource().m_19385_().equals(DamageSource.f_19314_.m_19385_())
         )) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void dreadsteelAttributeEvent(ItemAttributeModifierEvent event) {
      ItemStack stack = event.getItemStack();
      Item item = stack.m_41720_();
      if (item == DreadsteelItems.DREADSTEEL_HELMET.get() && event.getSlotType() == EquipmentSlot.HEAD) {
         event.addModifier(
            Attributes.f_22284_,
            new AttributeModifier(
               getUUID(event.getItemStack()), "dreadsteel_armor", (double)((Integer)DreadsteelCommonConfig.HELMET_ARMOR.get()).intValue(), Operation.ADDITION
            )
         );
         event.addModifier(
            Attributes.f_22285_,
            new AttributeModifier(
               getUUID(event.getItemStack()),
               "dreadsteel_toughness",
               (double)((Integer)DreadsteelCommonConfig.ARMOR_TOUGHNESS.get()).intValue(),
               Operation.ADDITION
            )
         );
         event.addModifier(
            Attributes.f_22278_,
            new AttributeModifier(
               getUUID(event.getItemStack()),
               "dreadsteel_knockback_resistance",
               (Double)DreadsteelCommonConfig.ARMOR_KNOCKBACK_RESISTANCE.get(),
               Operation.ADDITION
            )
         );
      }

      if (item == DreadsteelItems.DREADSTEEL_CHESTPLATE.get() && event.getSlotType() == EquipmentSlot.CHEST) {
         event.addModifier(
            Attributes.f_22284_,
            new AttributeModifier(
               getUUID(event.getItemStack()),
               "dreadsteel_armor",
               (double)((Integer)DreadsteelCommonConfig.CHESTPLATE_ARMOR.get()).intValue(),
               Operation.ADDITION
            )
         );
         event.addModifier(
            Attributes.f_22285_,
            new AttributeModifier(
               getUUID(event.getItemStack()),
               "dreadsteel_toughness",
               (double)((Integer)DreadsteelCommonConfig.ARMOR_TOUGHNESS.get()).intValue(),
               Operation.ADDITION
            )
         );
         event.addModifier(
            Attributes.f_22278_,
            new AttributeModifier(
               getUUID(event.getItemStack()),
               "dreadsteel_knockback_resistance",
               (Double)DreadsteelCommonConfig.ARMOR_KNOCKBACK_RESISTANCE.get(),
               Operation.ADDITION
            )
         );
      }

      if (item == DreadsteelItems.DREADSTEEL_LEGGINGS.get() && event.getSlotType() == EquipmentSlot.LEGS) {
         event.addModifier(
            Attributes.f_22284_,
            new AttributeModifier(
               getUUID(event.getItemStack()), "dreadsteel_armor", (double)((Integer)DreadsteelCommonConfig.LEGGINGS_ARMOR.get()).intValue(), Operation.ADDITION
            )
         );
         event.addModifier(
            Attributes.f_22285_,
            new AttributeModifier(
               getUUID(event.getItemStack()),
               "dreadsteel_toughness",
               (double)((Integer)DreadsteelCommonConfig.ARMOR_TOUGHNESS.get()).intValue(),
               Operation.ADDITION
            )
         );
         event.addModifier(
            Attributes.f_22278_,
            new AttributeModifier(
               getUUID(event.getItemStack()),
               "dreadsteel_knockback_resistance",
               (Double)DreadsteelCommonConfig.ARMOR_KNOCKBACK_RESISTANCE.get(),
               Operation.ADDITION
            )
         );
      }

      if (item == DreadsteelItems.DREADSTEEL_BOOTS.get() && event.getSlotType() == EquipmentSlot.FEET) {
         event.addModifier(
            Attributes.f_22284_,
            new AttributeModifier(
               getUUID(event.getItemStack()), "dreadsteel_armor", (double)((Integer)DreadsteelCommonConfig.BOOTS_ARMOR.get()).intValue(), Operation.ADDITION
            )
         );
         event.addModifier(
            Attributes.f_22285_,
            new AttributeModifier(
               getUUID(event.getItemStack()),
               "dreadsteel_toughness",
               (double)((Integer)DreadsteelCommonConfig.ARMOR_TOUGHNESS.get()).intValue(),
               Operation.ADDITION
            )
         );
         event.addModifier(
            Attributes.f_22278_,
            new AttributeModifier(
               getUUID(event.getItemStack()),
               "dreadsteel_knockback_resistance",
               (Double)DreadsteelCommonConfig.ARMOR_KNOCKBACK_RESISTANCE.get(),
               Operation.ADDITION
            )
         );
      }
   }

   @SubscribeEvent
   public static void onPlayerUseArmorItem(RightClickItem event) {
      ItemStack headStack = event.getEntity().m_6844_(EquipmentSlot.HEAD);
      ItemStack chestStack = event.getEntity().m_6844_(EquipmentSlot.CHEST);
      ItemStack legsStack = event.getEntity().m_6844_(EquipmentSlot.LEGS);
      ItemStack feetStack = event.getEntity().m_6844_(EquipmentSlot.FEET);
      ItemStack mainStack = event.getEntity().m_6844_(EquipmentSlot.MAINHAND);
      ItemStack offStack = event.getEntity().m_6844_(EquipmentSlot.OFFHAND);
      if (event.getItemStack().m_41720_().equals(DreadsteelItems.WHITE_KIT.get())) {
         if (headStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_HELMET.get())) {
            CompoundTag tag = headStack.m_41784_();
            tag.m_128405_("CustomModelData", 1);
            headStack.m_41751_(tag);
         }

         if (chestStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_CHESTPLATE.get())) {
            CompoundTag tag = chestStack.m_41784_();
            tag.m_128405_("CustomModelData", 1);
            chestStack.m_41751_(tag);
         }

         if (legsStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_LEGGINGS.get())) {
            CompoundTag tag = legsStack.m_41784_();
            tag.m_128405_("CustomModelData", 1);
            legsStack.m_41751_(tag);
         }

         if (feetStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_BOOTS.get())) {
            CompoundTag tag = feetStack.m_41784_();
            tag.m_128405_("CustomModelData", 1);
            feetStack.m_41751_(tag);
         }

         if (mainStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SCYTHE.get())) {
            CompoundTag tag = mainStack.m_41784_();
            tag.m_128405_("CustomModelData", 1);
            mainStack.m_41751_(tag);
         }

         if (offStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SCYTHE.get())) {
            CompoundTag tag = offStack.m_41784_();
            tag.m_128405_("CustomModelData", 1);
            offStack.m_41751_(tag);
         }

         if (mainStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SHIELD.get())) {
            CompoundTag tag = mainStack.m_41784_();
            tag.m_128405_("CustomModelData", 1);
            mainStack.m_41751_(tag);
         }

         if (offStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SHIELD.get())) {
            CompoundTag tag = offStack.m_41784_();
            tag.m_128405_("CustomModelData", 1);
            offStack.m_41751_(tag);
         }

         if (!event.getEntity().m_7500_()) {
            event.getItemStack().m_41774_(1);
         }

         event.getEntity().m_6330_(SoundEvents.f_11675_, SoundSource.PLAYERS, 1.0F, 1.0F);
      }

      if (event.getItemStack().m_41720_().equals(DreadsteelItems.BLACK_KIT.get())) {
         if (headStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_HELMET.get())) {
            CompoundTag tag = headStack.m_41784_();
            tag.m_128405_("CustomModelData", 2);
            headStack.m_41751_(tag);
         }

         if (chestStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_CHESTPLATE.get())) {
            CompoundTag tag = chestStack.m_41784_();
            tag.m_128405_("CustomModelData", 2);
            chestStack.m_41751_(tag);
         }

         if (legsStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_LEGGINGS.get())) {
            CompoundTag tag = legsStack.m_41784_();
            tag.m_128405_("CustomModelData", 2);
            legsStack.m_41751_(tag);
         }

         if (feetStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_BOOTS.get())) {
            CompoundTag tag = feetStack.m_41784_();
            tag.m_128405_("CustomModelData", 2);
            feetStack.m_41751_(tag);
         }

         if (mainStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SCYTHE.get())) {
            CompoundTag tag = mainStack.m_41784_();
            tag.m_128405_("CustomModelData", 2);
            mainStack.m_41751_(tag);
         }

         if (offStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SCYTHE.get())) {
            CompoundTag tag = offStack.m_41784_();
            tag.m_128405_("CustomModelData", 2);
            offStack.m_41751_(tag);
         }

         if (mainStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SHIELD.get())) {
            CompoundTag tag = mainStack.m_41784_();
            tag.m_128405_("CustomModelData", 2);
            mainStack.m_41751_(tag);
         }

         if (offStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SHIELD.get())) {
            CompoundTag tag = offStack.m_41784_();
            tag.m_128405_("CustomModelData", 2);
            offStack.m_41751_(tag);
         }

         if (!event.getEntity().m_7500_()) {
            event.getItemStack().m_41774_(1);
         }

         event.getEntity().m_6330_(SoundEvents.f_11675_, SoundSource.PLAYERS, 1.0F, 1.0F);
      }

      if (event.getItemStack().m_41720_().equals(DreadsteelItems.BRONZE_KIT.get())) {
         if (headStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_HELMET.get())) {
            CompoundTag tag = headStack.m_41784_();
            tag.m_128405_("CustomModelData", 3);
            headStack.m_41751_(tag);
         }

         if (chestStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_CHESTPLATE.get())) {
            CompoundTag tag = chestStack.m_41784_();
            tag.m_128405_("CustomModelData", 3);
            chestStack.m_41751_(tag);
         }

         if (legsStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_LEGGINGS.get())) {
            CompoundTag tag = legsStack.m_41784_();
            tag.m_128405_("CustomModelData", 3);
            legsStack.m_41751_(tag);
         }

         if (feetStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_BOOTS.get())) {
            CompoundTag tag = feetStack.m_41784_();
            tag.m_128405_("CustomModelData", 3);
            feetStack.m_41751_(tag);
         }

         if (mainStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SCYTHE.get())) {
            CompoundTag tag = mainStack.m_41784_();
            tag.m_128405_("CustomModelData", 3);
            mainStack.m_41751_(tag);
         }

         if (offStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SCYTHE.get())) {
            CompoundTag tag = offStack.m_41784_();
            tag.m_128405_("CustomModelData", 3);
            offStack.m_41751_(tag);
         }

         if (mainStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SHIELD.get())) {
            CompoundTag tag = mainStack.m_41784_();
            tag.m_128405_("CustomModelData", 3);
            mainStack.m_41751_(tag);
         }

         if (offStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SHIELD.get())) {
            CompoundTag tag = offStack.m_41784_();
            tag.m_128405_("CustomModelData", 3);
            offStack.m_41751_(tag);
         }

         if (!event.getEntity().m_7500_()) {
            event.getItemStack().m_41774_(1);
         }

         event.getEntity().m_6330_(SoundEvents.f_11675_, SoundSource.PLAYERS, 1.0F, 1.0F);
      }

      if (event.getItemStack().m_41720_().equals(DreadsteelItems.DEFAULT_KIT.get())) {
         if (headStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_HELMET.get())) {
            headStack.m_41749_("CustomModelData");
         }

         if (chestStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_CHESTPLATE.get())) {
            chestStack.m_41749_("CustomModelData");
         }

         if (legsStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_LEGGINGS.get())) {
            legsStack.m_41749_("CustomModelData");
         }

         if (feetStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_BOOTS.get())) {
            feetStack.m_41749_("CustomModelData");
         }

         if (mainStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SCYTHE.get())) {
            mainStack.m_41749_("CustomModelData");
         }

         if (offStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SCYTHE.get())) {
            offStack.m_41749_("CustomModelData");
         }

         if (mainStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SHIELD.get())) {
            mainStack.m_41749_("CustomModelData");
         }

         if (offStack.m_41720_().equals(DreadsteelItems.DREADSTEEL_SHIELD.get())) {
            offStack.m_41749_("CustomModelData");
         }

         if (!event.getEntity().m_7500_()) {
            event.getItemStack().m_41774_(1);
         }

         event.getEntity().m_6330_(SoundEvents.f_11675_, SoundSource.PLAYERS, 1.0F, 1.0F);
      }
   }
}
