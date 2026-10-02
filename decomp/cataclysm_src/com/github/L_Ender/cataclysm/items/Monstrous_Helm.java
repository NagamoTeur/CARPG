package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModEffect;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class Monstrous_Helm extends ArmorItem {
   public Monstrous_Helm(ArmorMaterials material, EquipmentSlot slot, Properties properties) {
      super(material, slot, properties);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getArmorRenderProperties());
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return "cataclysm:textures/armor/monstrous_helm.png";
   }

   public void setDamage(ItemStack stack, int damage) {
      if (CMConfig.Armor_Infinity_Durability) {
         super.setDamage(stack, 0);
      } else {
         super.setDamage(stack, damage);
      }
   }

   public boolean m_6832_(ItemStack p_41134_, ItemStack p_41135_) {
      return p_41135_.m_150930_(Items.f_42418_);
   }

   public void onArmorTick(ItemStack stack, Level world, Player player) {
      boolean berserk = player.m_21233_() * 1.0F / 2.0F >= player.m_21223_();
      double radius = 4.0;
      List<Entity> list = world.m_45933_(player, player.m_20191_().m_82400_(radius));
      if (berserk && !player.m_36335_().m_41519_(this)) {
         for (Entity entity : list) {
            if (entity instanceof LivingEntity) {
               entity.m_6469_(DamageSource.m_19370_(player), (float)player.m_21133_(Attributes.f_22281_) * 1.0F / 2.0F);
               double d0 = entity.m_20185_() - player.m_20185_();
               double d1 = entity.m_20189_() - player.m_20189_();
               double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
               entity.m_5997_(d0 / d2 * 1.5, 0.15, d1 / d2 * 1.5);
            }
         }

         player.m_36335_().m_41524_(this, 350);
         player.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTMONSTROUS.get(), 200, 0, false, true));
      }
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.f_44672_ != EnchantmentCategory.BREAKABLE && enchantment.f_44672_ == EnchantmentCategory.ARMOR
         || enchantment.f_44672_ == EnchantmentCategory.ARMOR_HEAD;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.monstrous_helm.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.monstrous_helm2.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
