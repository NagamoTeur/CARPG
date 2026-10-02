package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MudkinAiProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(10.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if ((entityiterator instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) == (entity instanceof TamableAnimal _tamEnt ? _tamEnt.m_21826_() : null)
               && entityiterator instanceof Mob _entity
               && entity instanceof LivingEntity _ent) {
               _entity.m_6710_(_ent);
            }
         }
      }
   }
}
