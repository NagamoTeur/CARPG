package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.capabilities.ChargeCapability;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class Bulwark_of_the_flame extends Item {
   public Bulwark_of_the_flame(Properties group) {
      super(group);
   }

   public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
      return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
   }

   public UseAnim m_6164_(ItemStack p_77661_1_) {
      return UseAnim.BLOCK;
   }

   public int m_8105_(ItemStack p_77626_1_) {
      return 72000;
   }

   public void m_5551_(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
      if (entityLiving.m_6144_() && !entityLiving.m_21255_()) {
         int i = this.m_8105_(stack) - timeLeft;
         int t = Mth.m_14045_(i, 1, 4);
         float f7 = entityLiving.m_146908_();
         float f = entityLiving.m_146909_();
         float f1 = -Mth.m_14031_(f7 * (float) (Math.PI / 180.0)) * Mth.m_14089_(f * (float) (Math.PI / 180.0));
         float f2 = -Mth.m_14031_(f * (float) (Math.PI / 180.0));
         float f3 = Mth.m_14089_(f7 * (float) (Math.PI / 180.0)) * Mth.m_14089_(f * (float) (Math.PI / 180.0));
         float f4 = Mth.m_14116_(f1 * f1 + f2 * f2 + f3 * f3);
         float f5 = 3.0F * ((float)t / 6.0F);
         f1 *= f5 / f4;
         f3 *= f5 / f4;
         entityLiving.m_5997_((double)f1, 0.0, (double)f3);
         if (entityLiving.m_20096_()) {
            float f6 = 1.1999999F;
            entityLiving.m_6478_(MoverType.SELF, new Vec3(0.0, (double)f6 / 2.0, 0.0));
         }

         ChargeCapability.IChargeCapability ChargeCapability = ModCapabilities.getCapability(entityLiving, ModCapabilities.CHARGE_CAPABILITY);
         if (ChargeCapability != null) {
            ChargeCapability.setCharge(true);
            ChargeCapability.setTimer(t * 2);
            ChargeCapability.seteffectiveChargeTime(t * 2);
            ChargeCapability.setknockbackSpeedIndex((float)(t * 2));
            ChargeCapability.setdamagePerEffectiveCharge(0.6F);
            ChargeCapability.setdx(f1 * 0.1F);
            ChargeCapability.setdZ(f3 * 0.1F);
         }

         if (!level.f_46443_) {
            ((Player)entityLiving).m_36335_().m_41524_(this, CMConfig.BulwarkOfTheFlameCooldown);
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
      ItemStack lvt_4_1_ = p_77659_2_.m_21120_(p_77659_3_);
      p_77659_2_.m_6672_(p_77659_3_);
      return InteractionResultHolder.m_19096_(lvt_4_1_);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.bulwark_of_the_flame.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.bulwark_of_the_flame2.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
