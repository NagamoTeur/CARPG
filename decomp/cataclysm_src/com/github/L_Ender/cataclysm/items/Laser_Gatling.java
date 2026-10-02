package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class Laser_Gatling extends Item {
   private static final String TAG_CHARGED = "Charged";
   public static final Predicate<ItemStack> REDSTONE = stack -> stack.m_41720_() == Items.f_42451_;

   public Laser_Gatling(Properties properties) {
      super(properties);
   }

   public int m_8105_(ItemStack stack) {
      return isUsable(stack) ? Integer.MAX_VALUE : 0;
   }

   public boolean m_142522_(ItemStack itemStack) {
      return super.m_142522_(itemStack) && isUsable(itemStack);
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BOW;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public static boolean isUsable(ItemStack stack) {
      return stack.m_41773_() < stack.m_41776_() - 1;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      playerIn.m_6672_(handIn);
      if (!isUsable(itemstack)) {
         ItemStack ammo = this.findAmmo(playerIn);
         boolean flag = playerIn.m_7500_();
         if (!ammo.m_41619_()) {
            ammo.m_41774_(1);
            flag = true;
         }

         if (flag) {
            itemstack.m_41721_(0);
         }
      }

      return InteractionResultHolder.m_19096_(itemstack);
   }

   public ItemStack findAmmo(Player entity) {
      if (entity.m_7500_()) {
         return ItemStack.f_41583_;
      } else {
         for (int i = 0; i < entity.m_150109_().m_6643_(); i++) {
            ItemStack itemstack1 = entity.m_150109_().m_8020_(i);
            if (REDSTONE.test(itemstack1)) {
               return itemstack1;
            }
         }

         return ItemStack.f_41583_;
      }
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return !ItemStack.m_41746_(oldStack, newStack);
   }

   public void m_5929_(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
      if (isUsable(stack)) {
         setCharged(stack, true);
         if (count % 2 == 0) {
            Laser_Beam_Entity laser = new Laser_Beam_Entity(worldIn, livingEntityIn);
            laser.setDamage((float)CMConfig.Laserdamage);
            Vec3 vector3d = livingEntityIn.m_20252_(1.0F);
            RandomSource rand = worldIn.m_213780_();
            livingEntityIn.m_146850_(GameEvent.f_223698_);
            livingEntityIn.m_5496_((SoundEvent)ModSounds.HARBINGER_LASER.get(), 0.2F, 1.0F + (rand.m_188501_() - rand.m_188501_()) * 0.2F);
            laser.m_6686_(vector3d.m_7096_(), vector3d.m_7098_(), vector3d.m_7094_(), 1.0F, 3.0F);
            if (!worldIn.f_46443_) {
               worldIn.m_7967_(laser);
            }

            stack.m_41622_(1, livingEntityIn, player -> player.m_21190_(livingEntityIn.m_7655_()));
         }
      } else if (livingEntityIn instanceof Player) {
         ItemStack ammo = this.findAmmo((Player)livingEntityIn);
         boolean flag = ((Player)livingEntityIn).m_7500_();
         if (!ammo.m_41619_()) {
            ammo.m_41774_(1);
            flag = true;
         }

         if (flag) {
            ((Player)livingEntityIn).m_36335_().m_41524_(this, 20);
            stack.m_41721_(0);
         }

         livingEntityIn.m_5810_();
      }
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity living, int remainingUseTicks) {
      setCharged(stack, false);
   }

   public void m_6883_(ItemStack stack, Level level, Entity entity, int i, boolean held) {
      boolean var10000;
      label22: {
         super.m_6883_(stack, level, entity, i, held);
         if (entity instanceof LivingEntity living && living.m_21211_().equals(stack)) {
            var10000 = true;
            break label22;
         }

         var10000 = false;
      }

      boolean using = var10000;
      if (level.f_46443_) {
         if (using) {
            setCharged(stack, true);
         }

         if (!using) {
            setCharged(stack, false);
         }
      }
   }

   public static boolean isCharged(ItemStack p_40933_) {
      CompoundTag compoundtag = p_40933_.m_41783_();
      return compoundtag != null && compoundtag.m_128471_("Charged");
   }

   public static void setCharged(ItemStack p_40885_, boolean p_40886_) {
      CompoundTag compoundtag = p_40885_.m_41784_();
      compoundtag.m_128379_("Charged", p_40886_);
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.laser_gatling.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
