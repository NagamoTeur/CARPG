package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.GoblinWarriorEntity;

public class HurtTimerDownProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         boolean agr = false;
         entity.getPersistentData().m_128347_("otcup", 0.0);
         entity.getPersistentData().m_128347_("war", entity.getPersistentData().m_128459_("war") + 3000.0);
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(16.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:goblins")))) {
               entityiterator.getPersistentData().m_128347_("war", entityiterator.getPersistentData().m_128459_("war") + 3000.0);
            }
         }

         _center = new Vec3(x, y, z);

         for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(8.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiteratorx instanceof GoblinWarriorEntity && entityiteratorx instanceof Mob) {
               Mob _entity = (Mob)entityiteratorx;
               if (sourceentity instanceof LivingEntity _ent) {
                  _entity.m_6710_(_ent);
               }
            }
         }
      }
   }
}
