package net.thirdlife.iterrpg.procedures;

import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;

public class StingerFiredProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         ItemStack ammo = ItemStack.f_41583_;
         double slot = 0.0;
         boolean shoot = false;
         slot = 0.0;
         if ((new Object() {
               public boolean checkGamemode(Entity _ent) {
                  if (_ent instanceof ServerPlayer _serverPlayer) {
                     return _serverPlayer.f_8941_.m_9290_() == GameType.CREATIVE;
                  } else {
                     return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                        ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                           && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.CREATIVE
                        : false;
                  }
               }
            })
            .checkGamemode(entity)) {
            ammo = new ItemStack(Items.f_42412_);
            shoot = true;
         } else {
            shoot = false;
         }

         for (int index0 = 0; index0 < 35; index0++) {
            if ((new Object() {
               public ItemStack getItemStack(int sltid, Entity entity) {
                  AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.f_41583_);
                  entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> _retval.set(capability.getStackInSlot(sltid).m_41777_()));
                  return _retval.get();
               }
            }).getItemStack((int)slot, entity).m_41720_() != Items.f_42412_) {
               slot++;
            } else {
               shoot = true;
               ammo = (new Object() {
                     public ItemStack getItemStack(int sltid, Entity entity) {
                        AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.f_41583_);
                        entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null)
                           .ifPresent(capability -> _retval.set(capability.getStackInSlot(sltid).m_41777_()));
                        return _retval.get();
                     }
                  })
                  .getItemStack((int)slot, entity);
            }
         }

         if (shoot) {
            if (entity.m_6144_()) {
               if (entity instanceof Player _player) {
                  _player.m_36335_().m_41524_(itemstack.m_41720_(), 32);
               }

               for (int index1 = 0; index1 < 6; index1++) {
                  Level projectileLevel = entity.f_19853_;
                  if (!projectileLevel.m_5776_()) {
                     Projectile _entityToSpawn = (new Object() {
                           public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
                              AbstractArrow entityToSpawn = new Arrow(EntityType.f_20548_, level);
                              entityToSpawn.m_5602_(shooter);
                              entityToSpawn.m_36781_((double)damage);
                              entityToSpawn.m_36735_(knockback);
                              entityToSpawn.m_36767_(piercing);
                              entityToSpawn.f_36705_ = Pickup.CREATIVE_ONLY;
                              return entityToSpawn;
                           }
                        })
                        .getArrow(
                           projectileLevel,
                           entity,
                           (float)(Mth.m_216271_(RandomSource.m_216327_(), 25, 30) / 10),
                           Mth.m_216271_(RandomSource.m_216327_(), 0, 15) / 10,
                           (byte)Mth.m_216271_(RandomSource.m_216327_(), 0, 2)
                        );
                     _entityToSpawn.m_6034_(entity.m_20185_(), entity.m_20188_() - 0.1, entity.m_20189_());
                     _entityToSpawn.m_6686_(entity.m_20154_().f_82479_, entity.m_20154_().f_82480_, entity.m_20154_().f_82481_, 2.0F, 14.0F);
                     projectileLevel.m_7967_(_entityToSpawn);
                  }
               }
            } else {
               if (entity instanceof Player _player) {
                  _player.m_36335_().m_41524_(itemstack.m_41720_(), 9);
               }

               Level projectileLevel = entity.f_19853_;
               if (!projectileLevel.m_5776_()) {
                  Projectile _entityToSpawn = (new Object() {
                     public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                        AbstractArrow entityToSpawn = new Arrow(EntityType.f_20548_, level);
                        entityToSpawn.m_5602_(shooter);
                        entityToSpawn.m_36781_((double)damage);
                        entityToSpawn.m_36735_(knockback);
                        entityToSpawn.f_36705_ = Pickup.ALLOWED;
                        return entityToSpawn;
                     }
                  }).getArrow(projectileLevel, entity, (float)(Mth.m_216271_(RandomSource.m_216327_(), 25, 30) / 10), 0);
                  _entityToSpawn.m_6034_(entity.m_20185_(), entity.m_20188_() - 0.1, entity.m_20189_());
                  _entityToSpawn.m_6686_(entity.m_20154_().f_82479_, entity.m_20154_().f_82480_, entity.m_20154_().f_82481_, 2.0F, 2.0F);
                  projectileLevel.m_7967_(_entityToSpawn);
               }
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")),
                     SoundSource.PLAYERS,
                     0.75F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 0.9, 1.1)
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")),
                     SoundSource.PLAYERS,
                     0.75F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 0.9, 1.1),
                     false
                  );
               }
            }

            if (!(new Object() {
                  public boolean checkGamemode(Entity _ent) {
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        return _serverPlayer.f_8941_.m_9290_() == GameType.CREATIVE;
                     } else {
                        return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                           ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                              && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.CREATIVE
                           : false;
                     }
                  }
               })
               .checkGamemode(entity)) {
               if (entity instanceof Player _player) {
                  ItemStack _stktoremove = ammo;
                  _player.m_150109_().m_36022_(p -> _stktoremove.m_41720_() == p.m_41720_(), 1, _player.f_36095_.m_39730_());
               }

               if (itemstack.m_220157_(1, RandomSource.m_216327_(), null)) {
                  itemstack.m_41774_(1);
                  itemstack.m_41721_(0);
               }
            }
         }
      }
   }
}
