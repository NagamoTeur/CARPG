package com.github.alexthe666.alexsmobs.item;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemTarantulaHawkElytra extends ArmorItem {
   public ItemTarantulaHawkElytra(Properties props, AMArmorMaterial mat) {
      super(mat, EquipmentSlot.CHEST, props);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)AlexsMobs.PROXY.getArmorRenderProperties());
   }

   public static boolean isUsable(ItemStack stack) {
      return stack.m_41773_() < stack.m_41776_() - 1;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      EquipmentSlot equipmentslottype = Mob.m_147233_(itemstack);
      ItemStack itemstack1 = playerIn.m_6844_(equipmentslottype);
      if (itemstack1.m_41619_()) {
         playerIn.m_8061_(equipmentslottype, itemstack.m_41777_());
         itemstack.m_41764_(0);
         return InteractionResultHolder.m_19092_(itemstack, worldIn.m_5776_());
      } else {
         return InteractionResultHolder.m_19100_(itemstack);
      }
   }

   public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
      return ElytraItem.m_41140_(stack);
   }

   public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
      if (!entity.f_19853_.f_46443_ && (flightTicks + 1) % 20 == 0) {
         stack.m_41622_(1, entity, e -> e.m_21166_(EquipmentSlot.CHEST));
      }

      return true;
   }

   public boolean m_6832_(ItemStack toRepair, ItemStack repair) {
      return repair.m_41720_() == AMItemRegistry.TARANTULA_HAWK_WING_FRAGMENT.get();
   }

   public EquipmentSlot getEquipmentSlot(ItemStack stack) {
      return EquipmentSlot.CHEST;
   }

   @Nullable
   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return "alexsmobs:textures/armor/tarantula_hawk_elytra.png";
   }
}
