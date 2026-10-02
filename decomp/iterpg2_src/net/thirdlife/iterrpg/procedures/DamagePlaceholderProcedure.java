package net.thirdlife.iterrpg.procedures;

import com.mojang.util.UUIDTypeAdapter;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class DamagePlaceholderProcedure {
   public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         double damage = 0.0;
         double distance = 0.0;
         boolean hit = false;
         boolean particle = false;
         entity.m_6469_(new EntityDamageSource("generic.player", world instanceof ServerLevel _serverLevelForGettingEntity ? (new Function<String, Entity>() {
            public Entity apply(String _uuidForEntity) {
               Entity _entityFromUUID = null;

               try {
                  _entityFromUUID = _serverLevelForGettingEntity.m_8791_(UUIDTypeAdapter.fromString(_uuidForEntity));
               } catch (Exception var4) {
                  _entityFromUUID = null;
               }

               return _entityFromUUID;
            }
         }).apply(sourceentity.getPersistentData().m_128461_("owner")) : null), (float)sourceentity.getPersistentData().m_128459_("damage"));
         entity.m_6469_(new EntityDamageSource("generic.player", sourceentity), (float)damage);
      }
   }
}
