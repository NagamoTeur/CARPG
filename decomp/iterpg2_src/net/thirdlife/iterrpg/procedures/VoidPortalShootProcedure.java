package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class VoidPortalShootProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         Entity targetentity = null;
         double distance = 0.0;
         double xiter = 0.0;
         double yiter = 0.0;
         double ziter = 0.0;
         boolean stoptarget = false;
         boolean doattack = false;
         entity.getPersistentData().m_128347_("charge", entity.getPersistentData().m_128459_("charge") + 1.0);
         if (entity.getPersistentData().m_128459_("charge") == 25.0) {
            stoptarget = true;
            doattack = false;
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(37.5), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (stoptarget
                  && (
                     entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))
                        || entityiterator instanceof Player
                  )) {
                  targetentity = entityiterator;
                  stoptarget = false;
                  doattack = true;
               }
            }

            if (doattack) {
               entity.getPersistentData().m_128347_("xtarget", targetentity.m_20185_());
               entity.getPersistentData().m_128347_("ytarget", targetentity.m_20186_() + (double)(targetentity.m_20206_() / 2.0F));
               entity.getPersistentData().m_128347_("ztarget", targetentity.m_20189_());
               entity.m_7618_(
                  Anchor.EYES, new Vec3(targetentity.m_20185_(), targetentity.m_20186_() + (double)(targetentity.m_20206_() / 2.0F), targetentity.m_20189_())
               );
            }
         }

         if (entity.getPersistentData().m_128459_("charge") >= 16.0
            && entity.getPersistentData().m_128459_("charge") <= 35.0
            && world instanceof ServerLevel _level) {
            _level.m_8767_(
               ParticleTypes.f_123799_,
               x,
               y + (double)(entity.m_20206_() / 2.0F),
               z,
               2,
               (double)(entity.m_20205_() / 3.0F),
               (double)(entity.m_20206_() / 3.0F),
               (double)(entity.m_20205_() / 3.0F),
               0.0
            );
         }

         if (entity.getPersistentData().m_128459_("charge") >= 35.0 && entity.getPersistentData().m_128459_("charge") <= 75.0) {
            distance = 0.0;
            stoptarget = true;
            doattack = false;
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(37.5), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (stoptarget
                  && (
                     entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))
                        || entityiteratorx instanceof Player
                  )) {
                  targetentity = entityiteratorx;
                  stoptarget = false;
                  doattack = true;
               }
            }

            if (doattack) {
               entity.getPersistentData()
                  .m_128347_(
                     "xtarget",
                     entity.getPersistentData().m_128459_("xtarget") + (targetentity.m_20185_() - entity.getPersistentData().m_128459_("xtarget")) / 30.0
                  );
               entity.getPersistentData()
                  .m_128347_(
                     "ytarget",
                     entity.getPersistentData().m_128459_("ytarget")
                        + (targetentity.m_20186_() + (double)(targetentity.m_20206_() / 2.0F) - entity.getPersistentData().m_128459_("ytarget")) / 30.0
                  );
               entity.getPersistentData()
                  .m_128347_(
                     "ztarget",
                     entity.getPersistentData().m_128459_("ztarget") + (targetentity.m_20189_() - entity.getPersistentData().m_128459_("ztarget")) / 30.0
                  );
               entity.m_7618_(
                  Anchor.EYES,
                  new Vec3(
                     entity.getPersistentData().m_128459_("xtarget"),
                     entity.getPersistentData().m_128459_("ytarget"),
                     entity.getPersistentData().m_128459_("ztarget")
                  )
               );

               for (int index0 = 0; index0 < 32; index0++) {
                  xiter = x + distance * (entity.getPersistentData().m_128459_("xtarget") - x);
                  yiter = y + (double)(entity.m_20206_() / 2.0F) + distance * (entity.getPersistentData().m_128459_("ytarget") - y);
                  ziter = z + distance * (entity.getPersistentData().m_128459_("ztarget") - z);
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.PORTAL_SPARK_PARTICLE.get(), xiter, yiter, ziter, 1, 0.02, 0.02, 0.02, 0.0);
                  }

                  distance += Mth.m_216263_(RandomSource.m_216327_(), 0.085, 0.115);
               }

               _center = new Vec3(xiter, yiter, ziter);

               for (Entity entityiteratorxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.5), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiteratorxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))
                     || entityiteratorxx instanceof Player) {
                     entityiteratorxx.m_6469_(DamageSource.f_19318_, 8.0F);
                  }
               }
            }
         }

         if (entity.getPersistentData().m_128459_("charge") >= 90.0 && !entity.f_19853_.m_5776_()) {
            entity.m_146870_();
         }
      }
   }
}
