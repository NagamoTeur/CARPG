package com.rolfmao.upgradednetherite.handlers;

import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.init.UpgradedNetheriteEffects;
import com.rolfmao.upgradednetherite.utils.DurabilityUtil;
import com.rolfmao.upgradednetherite.utils.check.CorruptUtil;
import com.rolfmao.upgradednetherite.utils.check.EchoUtil;
import com.rolfmao.upgradednetherite.utils.check.FireUtil;
import com.scouter.netherdepthsupgrade.effect.MobEffects;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(
   modid = "upgradednetherite",
   bus = Bus.FORGE
)
public class EventHandler {
   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.HIGHEST
   )
   public static void onEnderTeleport(EntityTeleportEvent event) {
      if (event.getEntity() instanceof LivingEntity && ((LivingEntity)event.getEntity()).m_21023_((MobEffect)UpgradedNetheriteEffects.ENDER_ANCHOR.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public static void onFogDensity(RenderFog event) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null
         && (
            ModList.get() == null
               || !ModList.get().getModContainerById("netherdepthsupgrade").isPresent()
               || !player.m_21023_((MobEffect)MobEffects.LAVA_VISION.get())
         )) {
         if (FireUtil.isWearingFireArmor(player) && event.getCamera().m_167685_() == FogType.LAVA) {
            event.setFarPlaneDistance(event.getFarPlaneDistance() + 3.0F);
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void onLivingUpdate(LivingTickEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         ItemStack heldItem = player.m_21205_();
         if (CorruptUtil.intWearingCorrupt(player, false) > 0 || CorruptUtil.isCorruptToolOrWeapon(heldItem)) {
            DurabilityUtil.CorruptDurabilityGain(player);
         }
      }

      if (event.getEntity().m_21023_((MobEffect)UpgradedNetheriteEffects.ATTRACTION.get())) {
         Entity target = event.getEntity();
         double tx = target.m_20185_();
         double ty = target.m_20186_();
         double tz = target.m_20189_();
         List<LivingEntity> entities = target.f_19853_.m_45976_(LivingEntity.class, new AABB(tx - 5.0, ty - 5.0, tz - 5.0, tx + 5.0, ty + 5.0, tz + 5.0));
         int pulled = 0;

         for (LivingEntity entity : entities) {
            if (entity.m_6084_()
               && entity != event.getEntity()
               && !(target instanceof WitherBoss)
               && !(target instanceof EnderDragon)
               && (!(entity instanceof Player) || target instanceof Player)) {
               if (pulled > 32) {
                  break;
               }

               Vec3 entityVector = new Vec3(entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
               Vec3 finalVector = new Vec3(tx, ty, tz).m_82546_(entityVector);
               if (entity.m_20186_() < ty && target.m_20096_()) {
                  finalVector = new Vec3(tx, ty + 0.1, tz).m_82546_(entityVector);
                  entity.f_19789_ = 0.0F;
               }

               if (Math.sqrt(
                     finalVector.f_82479_ * finalVector.f_82479_ + finalVector.f_82480_ * finalVector.f_82480_ + finalVector.f_82481_ * finalVector.f_82481_
                  )
                  > 1.8) {
                  finalVector = finalVector.m_82541_();
                  entity.m_20334_(finalVector.f_82479_ * 0.15F, finalVector.f_82480_ * 0.15F, finalVector.f_82481_ * 0.15F);
                  pulled++;
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void onItemTooltipEvent(ItemTooltipEvent event) {
      ItemStack itemStack = event.getItemStack();
      if (EchoUtil.isEchoSoulbound(itemStack) && UpgradedNetheriteConfig.EnableSoulbound) {
         for (Component line : event.getToolTip()) {
            if (line instanceof Component
               && (
                  line.getString().contains("item.unbreakable")
                     || line.getString().contains("item.canBreak")
                     || line.getString().contains("item.canPlace")
                     || line.getString().contains("item.durability")
               )) {
               event.getToolTip().add(event.getToolTip().indexOf(line), Component.m_237115_("upgradednetherite.Echo_Tool.TT"));
               break;
            }

            if (ForgeRegistries.ITEMS.getKey(event.getItemStack().m_41720_()).toString().equals(line.getString())) {
               event.getToolTip().add(event.getToolTip().indexOf(line), Component.m_237115_("upgradednetherite.Echo_Tool.TT"));
               break;
            }

            if (line instanceof Component && line.getString().contains("item.nbt_tags")) {
               event.getToolTip().add(event.getToolTip().indexOf(line), Component.m_237115_("upgradednetherite.Echo_Tool.TT"));
               break;
            }

            if (event.getToolTip().indexOf(line) == event.getToolTip().size() - 1) {
               event.getToolTip().add(event.getToolTip().indexOf(line) + 1, Component.m_237115_("upgradednetherite.Echo_Tool.TT"));
               break;
            }
         }
      }
   }
}
