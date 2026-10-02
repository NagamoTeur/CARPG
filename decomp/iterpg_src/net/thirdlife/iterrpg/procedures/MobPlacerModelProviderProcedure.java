package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.GoblinEntity;
import net.thirdlife.iterrpg.entity.GoblinWarriorEntity;
import net.thirdlife.iterrpg.entity.HobgoblinEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class MobPlacerModelProviderProcedure {
   public static Entity execute(LevelAccessor world, Entity entity) {
      if (entity == null) {
         return null;
      } else if ((entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("select") == 0.0) {
         return world instanceof Level _level ? new GoblinEntity((EntityType<GoblinEntity>)IterRpgModEntities.GOBLIN.get(), _level) : null;
      } else if ((entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("select") == 1.0) {
         return world instanceof Level _level
            ? new GoblinWarriorEntity((EntityType<GoblinWarriorEntity>)IterRpgModEntities.GOBLIN_WARRIOR.get(), _level)
            : null;
      } else if ((entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("select") == 2.0) {
         return world instanceof Level _level ? new HobgoblinEntity((EntityType<HobgoblinEntity>)IterRpgModEntities.HOBGOBLIN.get(), _level) : null;
      } else {
         return null;
      }
   }
}
