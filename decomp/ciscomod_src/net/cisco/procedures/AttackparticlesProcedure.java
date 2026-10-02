package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.init.CiscoModModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class AttackparticlesProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingAttackEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(
            event,
            event.getEntity().f_19853_,
            event.getEntity().m_20185_(),
            event.getEntity().m_20186_(),
            event.getEntity().m_20189_(),
            event.getSource().m_7639_()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
      execute(null, world, x, y, z, sourceentity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity sourceentity) {
      if (sourceentity != null) {
         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.EQUILLIBRIUM.get()
            && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123799_, x, y, z, 65, 0.5, 0.5, 0.5, 1.0);
         }

         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.NIGHTFALL.get()
            && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123746_, x, y, z, 65, 0.5, 0.5, 0.5, 1.0);
         }

         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ABSOLUTE_EQUILLIBRIUM.get()
            && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_175830_, x, y, z, 65, 0.5, 0.5, 0.5, 1.0);
         }

         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.REFINED_EQUILLIBRIUM.get()
            && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123799_, x, y, z, 65, 0.5, 0.5, 0.5, 1.0);
         }

         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.GLACIES.get()
            && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_175821_, x, y, z, 65, 0.5, 0.5, 0.5, 1.0);
         }

         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.ADJUDICATOR.get()
            && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123810_, x, y, z, 65, 0.5, 0.5, 0.5, 1.0);
         }

         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.SKYSPLITTER.get()
            && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123796_, x, y, z, 65, 0.5, 0.5, 0.5, 1.0);
         }

         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.FELL_RAGNAROK.get()
            && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123745_, x, y, z, 65, 0.5, 0.5, 0.5, 1.0);
         }
      }
   }
}
