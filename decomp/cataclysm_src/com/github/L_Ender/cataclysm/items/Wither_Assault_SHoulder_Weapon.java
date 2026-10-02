package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Howitzer_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Missile_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class Wither_Assault_SHoulder_Weapon extends Item {
   public Wither_Assault_SHoulder_Weapon(Properties group) {
      super(group);
   }

   public UseAnim m_6164_(ItemStack p_77661_1_) {
      return UseAnim.BOW;
   }

   public int m_8105_(ItemStack p_77626_1_) {
      return 72000;
   }

   public void m_5551_(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
      if (p_43396_ instanceof Player player) {
         int i = this.m_8105_(p_43394_) - p_43397_;
         float f = getPowerForTime(i);
         if (!((double)f < 0.5)) {
            p_43395_.m_6263_(
               (Player)null,
               player.m_20185_(),
               player.m_20186_(),
               player.m_20189_(),
               (SoundEvent)ModSounds.ROCKET_LAUNCH.get(),
               SoundSource.PLAYERS,
               1.0F,
               0.7F
            );
            if (p_43396_.m_6144_()) {
               player.m_36335_().m_41524_(this, CMConfig.WASWHowitzerCooldown);
               if (!p_43395_.f_46443_) {
                  Wither_Howitzer_Entity rocket = new Wither_Howitzer_Entity(
                     (EntityType<Wither_Howitzer_Entity>)ModEntities.WITHER_HOWITZER.get(), p_43395_, player
                  );
                  rocket.setRadius(3.5F);
                  rocket.m_37251_(player, player.m_146909_(), player.m_146908_(), 0.0F, f * 1.0F, 1.0F);
                  p_43395_.m_7967_(rocket);
               }
            } else {
               player.m_36335_().m_41524_(this, CMConfig.WASWMissileCooldown);
               if (!p_43395_.f_46443_) {
                  float d7 = p_43396_.m_146908_();
                  float d = p_43396_.m_146909_();
                  float d1 = -Mth.m_14031_(d7 * (float) (Math.PI / 180.0)) * Mth.m_14089_(d * (float) (Math.PI / 180.0));
                  float d2 = -Mth.m_14031_(d * (float) (Math.PI / 180.0));
                  float d3 = Mth.m_14089_(d7 * (float) (Math.PI / 180.0)) * Mth.m_14089_(d * (float) (Math.PI / 180.0));
                  double theta = (double)d7 * (Math.PI / 180.0);
                  double vecX = Math.cos(++theta);
                  double vecZ = Math.sin(theta);
                  double x = p_43396_.m_20185_() + vecX;
                  double Z = p_43396_.m_20189_() + vecZ;
                  Wither_Missile_Entity rocket = new Wither_Missile_Entity(
                     (EntityType<? extends Wither_Missile_Entity>)ModEntities.WITHER_MISSILE.get(),
                     player,
                     x,
                     p_43396_.m_20188_(),
                     Z,
                     (double)d1,
                     (double)d2,
                     (double)d3,
                     (float)CMConfig.WASWMissileDamage,
                     p_43395_
                  );
                  p_43395_.m_7967_(rocket);
               }
            }
         }
      }
   }

   public static float getPowerForTime(int p_40662_) {
      float f = (float)p_40662_ / 20.0F;
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      player.m_6672_(hand);
      return InteractionResultHolder.m_19096_(itemstack);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.wither_assault_shoulder_weapon.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.wither_assault_shoulder_weapon.desc2").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
