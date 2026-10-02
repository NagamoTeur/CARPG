package net.mindoth.dreadsteel.item.weapon;

import java.util.List;
import javax.annotation.Nullable;
import net.mindoth.dreadsteel.registries.DreadsteelItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "dreadsteel"
)
public class DreadsteelShield extends ShieldItem {
   public DreadsteelShield() {
      super(new Properties().m_41503_(0).m_41491_(CreativeModeTab.f_40757_).m_41486_());
      DispenserBlock.m_52672_(this, ArmorItem.f_40376_);
   }

   public boolean m_8120_(ItemStack p_77616_1_) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("tooltip.dreadsteel.dreadsteel_shield"));
      super.m_7373_(stack, world, tooltip, flagIn);
   }

   @SubscribeEvent
   public static void onArrowHit(LivingAttackEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         Level world = player.f_19853_;
         if (player.m_21254_() && player.m_21211_().m_41720_() == DreadsteelItems.DREADSTEEL_SHIELD.get()) {
            if (event.getSource().m_7640_() instanceof AbstractArrow && !world.f_46443_) {
               Entity attacker = event.getSource().m_7640_();
               attacker.f_19853_
                  .m_6263_(null, attacker.m_20185_(), attacker.m_20186_(), attacker.m_20189_(), SoundEvents.f_11705_, SoundSource.PLAYERS, 1.0F, 1.0F);
               attacker.f_19853_
                  .m_6263_(null, attacker.m_20185_(), attacker.m_20186_(), attacker.m_20189_(), SoundEvents.f_12008_, SoundSource.PLAYERS, 0.5F, 1.0F);
               ServerLevel level = (ServerLevel)world;

               for (int i = 0; i < 8; i++) {
                  level.m_8767_(ParticleTypes.f_123744_, attacker.m_20185_(), attacker.m_20186_(), attacker.m_20189_(), 1, 0.0, 0.0, 0.0, 0.5);
               }

               attacker.m_146870_();
               event.setCanceled(true);
            }

            if (event.getSource().m_7640_() instanceof LivingEntity) {
               event.getSource().m_7640_().m_20254_(5);
            }
         }
      }
   }
}
