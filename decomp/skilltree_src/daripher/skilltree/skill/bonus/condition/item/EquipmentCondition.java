package daripher.skilltree.skill.bonus.condition.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.init.PSTItemConditions;
import daripher.skilltree.init.PSTTags;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class EquipmentCondition implements ItemCondition {
   public EquipmentCondition.Type type;

   public EquipmentCondition(EquipmentCondition.Type type) {
      this.type = type;
   }

   @Override
   public boolean met(ItemStack stack) {
      return switch (this.type) {
         case ARMOR -> isArmor(stack);
         case AXE -> isAxe(stack);
         case BOOTS -> isBoots(stack);
         case BOW -> isBow(stack);
         case HOE -> isHoe(stack);
         case TOOL -> isTool(stack);
         case SWORD -> isSword(stack);
         case HELMET -> isHelmet(stack);
         case SHIELD -> isShield(stack);
         case SHOVEL -> isShovel(stack);
         case CHESTPLATE -> isChestplate(stack);
         case WEAPON -> isWeapon(stack);
         case CROSSBOW -> isCrossbow(stack);
         case PICKAXE -> isPickaxe(stack);
         case TRIDENT -> isTrident(stack);
         case LEGGINGS -> isLeggings(stack);
         case MELEE_WEAPON -> isMeleeWeapon(stack);
         case RANGED_WEAPON -> isRangedWeapon(stack);
         default -> isEquipment(stack);
      };
   }

   public static boolean isEquipment(ItemStack stack) {
      return isArmor(stack) || isWeapon(stack) || isShield(stack) || isTool(stack);
   }

   public static boolean isRangedWeapon(ItemStack stack) {
      return isCrossbow(stack) || isBow(stack) || stack.m_204117_(PSTTags.RANGED_WEAPON);
   }

   public static boolean isMeleeWeapon(ItemStack stack) {
      return isSword(stack) || isAxe(stack) || isTrident(stack) || stack.m_204117_(PSTTags.MELEE_WEAPON);
   }

   public static boolean isLeggings(ItemStack stack) {
      return stack.m_41720_() instanceof ArmorItem armor && armor.m_40402_() == EquipmentSlot.LEGS || stack.m_204117_(Items.ARMORS_LEGGINGS);
   }

   public static boolean isTrident(ItemStack stack) {
      ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.m_41720_());
      return Objects.requireNonNull(id).toString().equals("tetra:modular_single")
         ? true
         : stack.m_41720_() instanceof TridentItem || stack.m_204117_(Items.TOOLS_TRIDENTS);
   }

   public static boolean isPickaxe(ItemStack stack) {
      return stack.m_41720_() instanceof PickaxeItem || stack.m_204117_(Items.TOOLS_PICKAXES);
   }

   public static boolean isCrossbow(ItemStack stack) {
      ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.m_41720_());
      return Objects.requireNonNull(id).toString().equals("tetra:modular_crossbow")
         ? true
         : stack.m_41720_() instanceof CrossbowItem || stack.m_204117_(Items.TOOLS_CROSSBOWS);
   }

   public static boolean isWeapon(ItemStack stack) {
      return isMeleeWeapon(stack) || isRangedWeapon(stack);
   }

   public static boolean isChestplate(ItemStack stack) {
      return stack.m_41720_() instanceof ArmorItem armor && armor.m_40402_() == EquipmentSlot.CHEST || stack.m_204117_(Items.ARMORS_CHESTPLATES);
   }

   public static boolean isShovel(ItemStack stack) {
      return stack.m_41720_() instanceof ShovelItem || stack.m_204117_(Items.TOOLS_SHOVELS);
   }

   public static boolean isShield(ItemStack stack) {
      ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.m_41720_());
      return Objects.requireNonNull(id).toString().equals("tetra:modular_shield")
         ? true
         : stack.m_41720_() instanceof ShieldItem || stack.m_204117_(Items.TOOLS_SHIELDS);
   }

   public static boolean isHelmet(ItemStack stack) {
      return stack.m_41720_() instanceof ArmorItem armor && armor.m_40402_() == EquipmentSlot.HEAD || stack.m_204117_(Items.ARMORS_HELMETS);
   }

   public static boolean isSword(ItemStack stack) {
      ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.m_41720_());
      return Objects.requireNonNull(id).toString().equals("tetra:modular_sword")
         ? true
         : stack.m_41720_() instanceof SwordItem || stack.m_204117_(Items.TOOLS_SWORDS);
   }

   public static boolean isTool(ItemStack stack) {
      return stack.m_41720_() instanceof DiggerItem || stack.m_204117_(Items.TOOLS);
   }

   public static boolean isHoe(ItemStack stack) {
      return stack.m_41720_() instanceof HoeItem || stack.m_204117_(Items.TOOLS_HOES);
   }

   public static boolean isBow(ItemStack stack) {
      ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.m_41720_());
      return Objects.requireNonNull(id).toString().equals("tetra:modular_bow")
         ? true
         : stack.m_41720_() instanceof BowItem || stack.m_204117_(Items.TOOLS_BOWS);
   }

   public static boolean isBoots(ItemStack stack) {
      return stack.m_41720_() instanceof ArmorItem armor && armor.m_40402_() == EquipmentSlot.FEET || stack.m_204117_(Items.ARMORS_BOOTS);
   }

   public static boolean isAxe(ItemStack stack) {
      return stack.m_41720_() instanceof AxeItem || stack.m_204117_(Items.TOOLS_AXES);
   }

   public static boolean isArmor(ItemStack stack) {
      return isHelmet(stack) || isBoots(stack) || isChestplate(stack) || isLeggings(stack);
   }

   @Override
   public String getDescriptionId() {
      return ItemCondition.super.getDescriptionId() + "." + this.type.name().toLowerCase();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EquipmentCondition that = (EquipmentCondition)o;
         return Objects.equals(this.type, that.type);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.type);
   }

   @Override
   public ItemCondition.Serializer getSerializer() {
      return (ItemCondition.Serializer)PSTItemConditions.EQUIPMENT_TYPE.get();
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, Consumer<ItemCondition> consumer) {
      editor.addLabel(0, 0, "Type", ChatFormatting.GREEN);
      editor.increaseHeight(19);
      editor.addSelectionMenu(0, 0, 200, this.type)
         .setResponder(type -> this.selectEquipmentType(consumer, type))
         .setElementNameGetter(EquipmentCondition.Type::getName);
      editor.increaseHeight(19);
   }

   private void selectEquipmentType(Consumer<ItemCondition> consumer, EquipmentCondition.Type type) {
      this.setType(type);
      consumer.accept(this);
   }

   public void setType(EquipmentCondition.Type type) {
      this.type = type;
   }

   public static class Serializer implements ItemCondition.Serializer {
      public ItemCondition deserialize(JsonObject json) throws JsonParseException {
         EquipmentCondition.Type type = EquipmentCondition.Type.valueOf(json.get("equipment_type").getAsString().toUpperCase());
         return new EquipmentCondition(type);
      }

      public void serialize(JsonObject json, ItemCondition condition) {
         if (condition instanceof EquipmentCondition aCondition) {
            json.addProperty("equipment_type", aCondition.type.name().toLowerCase());
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemCondition deserialize(CompoundTag tag) {
         EquipmentCondition.Type type = EquipmentCondition.Type.valueOf(tag.m_128461_("equipment_type").toUpperCase());
         return new EquipmentCondition(type);
      }

      public CompoundTag serialize(ItemCondition condition) {
         if (condition instanceof EquipmentCondition aCondition) {
            CompoundTag tag = new CompoundTag();
            tag.m_128359_("equipment_type", aCondition.type.name().toLowerCase());
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public ItemCondition deserialize(FriendlyByteBuf buf) {
         return new EquipmentCondition(EquipmentCondition.Type.values()[buf.readInt()]);
      }

      public void serialize(FriendlyByteBuf buf, ItemCondition condition) {
         if (condition instanceof EquipmentCondition aCondition) {
            buf.writeInt(aCondition.type.ordinal());
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public ItemCondition createDefaultInstance() {
         return new EquipmentCondition(EquipmentCondition.Type.ANY);
      }
   }

   public static enum Type {
      ANY,
      HELMET,
      CHESTPLATE,
      LEGGINGS,
      BOOTS,
      ARMOR,
      SHIELD,
      WEAPON,
      SWORD,
      AXE,
      TRIDENT,
      MELEE_WEAPON,
      BOW,
      CROSSBOW,
      RANGED_WEAPON,
      PICKAXE,
      HOE,
      SHOVEL,
      TOOL;

      public Component getName() {
         return Component.m_237113_(TooltipHelper.idToName(this.name().toLowerCase()));
      }
   }
}
