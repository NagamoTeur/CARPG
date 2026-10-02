package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModKeybind;
import com.github.L_Ender.cataclysm.message.MessageArmorKey;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class Cursium_Armor extends ArmorItem implements KeybindUsingArmor {
   public Cursium_Armor(Armortier material, EquipmentSlot slot, Properties properties) {
      super(material, slot, properties);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getArmorRenderProperties());
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return "cataclysm:textures/armor/cursium_armor" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
   }

   public void setDamage(ItemStack stack, int damage) {
      if (CMConfig.Armor_Infinity_Durability) {
         super.setDamage(stack, 0);
      } else {
         super.setDamage(stack, damage);
      }
   }

   public boolean m_6832_(ItemStack p_41134_, ItemStack p_41135_) {
      return p_41135_.m_150930_((Item)ModItems.IGNITIUM_INGOT.get());
   }

   public void m_6883_(ItemStack stack, Level level, Entity entity, int i, boolean held) {
      super.m_6883_(stack, level, entity, i, held);
      if (entity instanceof Player living) {
         if (living.m_6844_(EquipmentSlot.HEAD).m_41720_() == ModItems.CURSIUM_HELMET.get()
            && level.f_46443_
            && Cataclysm.PROXY.getClientSidePlayer() == entity
            && Cataclysm.PROXY.isKeyDown(5)) {
            Cataclysm.sendMSGToServer(new MessageArmorKey(EquipmentSlot.HEAD.ordinal(), living.m_19879_(), 5));
            this.onKeyPacket(living, stack, 5);
         }

         if (living.m_6844_(EquipmentSlot.FEET).m_41720_() == ModItems.CURSIUM_BOOTS.get()
            && level.f_46443_
            && Cataclysm.PROXY.getClientSidePlayer() == entity
            && Cataclysm.PROXY.isKeyDown(7)) {
            Cataclysm.sendMSGToServer(new MessageArmorKey(EquipmentSlot.FEET.ordinal(), living.m_19879_(), 7));
            this.onKeyPacket(living, stack, 7);
         }
      }
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      if (this.f_40377_ == EquipmentSlot.HEAD) {
         tooltip.add(Component.m_237115_("item.cataclysm.cursium_helmet.desc").m_130940_(ChatFormatting.DARK_GREEN));
         tooltip.add(
            Component.m_237110_("item.cataclysm.cursium_helmet.desc2", new Object[]{ModKeybind.HELMET_KEY_ABILITY.m_90863_()})
               .m_130940_(ChatFormatting.DARK_GREEN)
         );
      }

      if (this.f_40377_ == EquipmentSlot.CHEST) {
         tooltip.add(Component.m_237115_("item.cataclysm.cursium_chestplate.desc").m_130940_(ChatFormatting.DARK_GREEN));
         tooltip.add(Component.m_237115_("item.cataclysm.cursium_chestplate.desc2").m_130940_(ChatFormatting.DARK_GREEN));
         tooltip.add(Component.m_237115_("item.cataclysm.cursium_chestplate.desc3").m_130940_(ChatFormatting.DARK_GREEN));
      }

      if (this.f_40377_ == EquipmentSlot.LEGS) {
         tooltip.add(Component.m_237115_("item.cataclysm.cursium_leggings.desc").m_130940_(ChatFormatting.DARK_GREEN));
         tooltip.add(Component.m_237115_("item.cataclysm.cursium_leggings.desc2").m_130940_(ChatFormatting.DARK_GREEN));
      }

      if (this.f_40377_ == EquipmentSlot.FEET) {
         tooltip.add(Component.m_237115_("item.cataclysm.cursium_boots.desc").m_130940_(ChatFormatting.DARK_GREEN));
         tooltip.add(
            Component.m_237110_("item.cataclysm.cursium_boots.desc2", new Object[]{ModKeybind.BOOTS_KEY_ABILITY.m_90863_()})
               .m_130940_(ChatFormatting.DARK_GREEN)
         );
      }
   }

   @Override
   public void onKeyPacket(Player player, ItemStack itemStack, int Type) {
      if (Type == 5 && player != null && !player.m_36335_().m_41519_((Item)ModItems.CURSIUM_HELMET.get())) {
         boolean flag = false;

         for (Entity entity : player.f_19853_.m_45933_(player, player.m_20191_().m_82400_(24.0))) {
            if (entity instanceof LivingEntity living) {
               flag = true;
               living.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 160));
            }

            if (flag) {
               player.m_36335_().m_41524_((Item)ModItems.CURSIUM_HELMET.get(), 200);
            }
         }
      }

      if (Type == 7 && player != null && player.m_20096_() && !player.m_36335_().m_41519_((Item)ModItems.CURSIUM_BOOTS.get())) {
         float speed = -1.8F;
         float dodgeYaw = (float)Math.toRadians((double)(player.m_146908_() + 90.0F));
         Vec3 m = player.m_20184_().m_82520_((double)speed * Math.cos((double)dodgeYaw), 0.0, (double)speed * Math.sin((double)dodgeYaw));
         player.m_20334_(m.f_82479_, 0.4, m.f_82481_);
         player.m_36335_().m_41524_((Item)ModItems.CURSIUM_BOOTS.get(), 200);
      }
   }
}
