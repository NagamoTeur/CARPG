package lykrast.meetyourfight.misc;

import lykrast.meetyourfight.registry.ModItems;
import lykrast.meetyourfight.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(
   modid = "meetyourfight"
)
public class EventHandler {
   @SubscribeEvent
   public static void entityDamage(LivingHurtEvent event) {
      LivingEntity attacked = event.getEntity();
      if (attacked instanceof Player pattacked
         && !event.isCanceled()
         && !event.getSource().m_19378_()
         && CuriosApi.getCuriosHelper().findFirstCurio(pattacked, (Item)ModItems.aceOfIron.get()).isPresent()) {
         float luck = pattacked.m_36336_();
         double chance = 0.16666666666666666;
         if (luck >= 0.0F) {
            chance = (1.0 + (double)luck) / (6.0 + (double)(2.0F * luck));
         } else {
            chance = 1.0 / (6.0 - (double)(3.0F * luck));
         }

         if (pattacked.m_217043_().m_188500_() <= chance) {
            event.setCanceled(true);
            pattacked.f_19853_.m_5594_(null, attacked.m_20183_(), (SoundEvent)ModSounds.aceOfIronProc.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
         }
      }

      if (!event.isCanceled()) {
         Entity attacker = event.getSource().m_7639_();
         if (attacker != null && attacker instanceof Player pattacker) {
            if (CuriosApi.getCuriosHelper().findFirstCurio(pattacker, (Item)ModItems.slicersDice.get()).isPresent()) {
               float luckx = pattacker.m_36336_();
               double chancex = 0.2;
               if (luckx >= 0.0F) {
                  chancex = (1.0 + (double)luckx) / (5.0 + (double)luckx);
               } else {
                  chancex = 1.0 / (5.0 - (double)(3.0F * luckx));
               }

               if (pattacker.m_217043_().m_188500_() <= chancex) {
                  event.setAmount(event.getAmount() * 2.0F);
                  pattacker.f_19853_.m_5594_(null, attacked.m_20183_(), (SoundEvent)ModSounds.slicersDiceProc.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                  ((ServerLevel)pattacker.f_19853_)
                     .m_8767_(ParticleTypes.f_123797_, attacked.m_20185_(), attacked.m_20188_(), attacked.m_20189_(), 15, 0.2, 0.2, 0.2, 0.0);
               }
            }

            if (CuriosApi.getCuriosHelper().findFirstCurio(pattacker, (Item)ModItems.wiltedIdeals.get()).isPresent()) {
               event.setAmount(event.getAmount() * 1.5F);
            }
         }

         if (attacked instanceof Player pattacked && CuriosApi.getCuriosHelper().findFirstCurio(pattacked, (Item)ModItems.cagedHeart.get()).isPresent()) {
            float treshold = pattacked.m_21233_() / 4.0F;
            if (event.getAmount() > treshold) {
               event.setAmount((event.getAmount() - treshold) * 0.5F + treshold);
               pattacked.f_19853_.m_5594_(null, attacked.m_20183_(), (SoundEvent)ModSounds.cagedHeartProc.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
         }
      }
   }

   @SubscribeEvent
   public static void entityDeath(LivingDeathEvent event) {
      if (!event.isCanceled()) {
         Entity killer = event.getSource().m_7639_();
         if (killer != null && killer instanceof Player pkiller) {
            LivingEntity killed = event.getEntity();
            if (CuriosApi.getCuriosHelper().findFirstCurio(pkiller, (Item)ModItems.tombPlanter.get()).isPresent()) {
               Level lvl = killed.f_19853_;
               BlockPos pos = killed.m_20183_();
               if (!lvl.m_46859_(pos) || !lvl.m_46859_(pos = pos.m_7495_())) {
                  ItemStack dummy = new ItemStack(Items.f_42499_);
                  if ((BoneMealItem.applyBonemeal(dummy, lvl, pos, pkiller) || BoneMealItem.m_40631_(dummy, lvl, pos, null)) && !lvl.f_46443_) {
                     lvl.m_46796_(1505, pos, 0);
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void livingExperienceDrop(LivingExperienceDropEvent event) {
      if (!event.isCanceled()) {
         Player killer = event.getAttackingPlayer();
         if (killer != null
            && event.getOriginalExperience() >= 2
            && CuriosApi.getCuriosHelper().findFirstCurio(killer, (Item)ModItems.blossomingMind.get()).isPresent()) {
            int amt = Math.min(event.getOriginalExperience() / 2, 5);
            event.setDroppedExperience(
               event.getDroppedExperience() + amt + killer.f_19853_.f_46441_.m_188503_(amt + 1) + killer.f_19853_.f_46441_.m_188503_(amt + 1)
            );
         }
      }
   }
}
