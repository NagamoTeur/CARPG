package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.ITurretBehavior;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.TileCaster;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.common.block.tile.RotatingTurretTile;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketOneShotAnimation;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import java.util.HashMap;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.Nullable;

public class RotatingSpellTurret extends BasicSpellTurret {
   public static HashMap<AbstractCastMethod, ITurretBehavior> ROT_TURRET_BEHAVIOR_MAP = new HashMap<>();

   @Override
   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (player.m_21120_(handIn).m_41720_() instanceof WarpScroll) {
         BlockPos aimPos = new WarpScroll.WarpScrollData(player.m_21120_(handIn)).getPos();
         if (player.m_9236_().m_7702_(pos) instanceof RotatingTurretTile tile) {
            tile.aim(aimPos, player);
         }
      }

      return super.m_6227_(state, worldIn, pos, player, handIn, hit);
   }

   @Override
   public void shootSpell(ServerLevel world, BlockPos pos) {
      if (world.m_7702_(pos) instanceof RotatingTurretTile tile) {
         ISpellCaster caster = tile.getSpellCaster();
         if (!caster.getSpell().isEmpty()) {
            int manaCost = tile.getManaCost();
            if (manaCost <= 0 || SourceUtil.takeSourceWithParticles(pos, world, 10, manaCost) != null) {
               Networking.sendToNearby(world, pos, new PacketOneShotAnimation(pos));
               Position iposition = getDispensePosition(new BlockSourceImpl(world, pos), tile);
               FakePlayer fakePlayer = ANFakePlayer.getPlayer(world);
               fakePlayer.m_6034_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
               EntitySpellResolver resolver = new EntitySpellResolver(
                  new SpellContext(world, caster.getSpell(), fakePlayer, new TileCaster(tile, SpellContext.CasterType.TURRET))
               );
               if (resolver.castType != null && ROT_TURRET_BEHAVIOR_MAP.containsKey(resolver.castType)) {
                  ROT_TURRET_BEHAVIOR_MAP.get(resolver.castType).onCast(resolver, world, pos, fakePlayer, iposition, orderedByNearest(tile)[0].m_122424_());
                  caster.playSound(pos, world, null, caster.getCurrentSound(), SoundSource.BLOCKS);
               }
            }
         }
      }
   }

   public static Position getDispensePosition(BlockSource coords, RotatingTurretTile tile) {
      Vec3 direction = tile.getShootAngle().m_82541_();
      double d0 = coords.m_7096_() + 0.5 * direction.m_7096_();
      double d1 = coords.m_7098_() + 0.5 * direction.m_7098_();
      double d2 = coords.m_7094_() + 0.5 * direction.m_7094_();
      return new PositionImpl(d0, d1, d2);
   }

   public static Direction[] orderedByNearest(RotatingTurretTile pEntity) {
      float f = pEntity.getRotationY() * (float) Math.PI / 180.0F;
      float f1 = (90.0F + pEntity.getRotationX()) * (float) Math.PI / 180.0F;
      float f2 = Mth.m_14031_(f);
      float f3 = Mth.m_14089_(f);
      float f4 = Mth.m_14031_(f1);
      float f5 = Mth.m_14089_(f1);
      boolean flag = f4 > 0.0F;
      boolean flag1 = f2 < 0.0F;
      boolean flag2 = f5 > 0.0F;
      float f6 = flag ? f4 : -f4;
      float f7 = flag1 ? -f2 : f2;
      float f8 = flag2 ? f5 : -f5;
      float f9 = f6 * f3;
      float f10 = f8 * f3;
      Direction direction = flag ? Direction.EAST : Direction.WEST;
      Direction direction1 = flag1 ? Direction.UP : Direction.DOWN;
      Direction direction2 = flag2 ? Direction.SOUTH : Direction.NORTH;
      if (f6 > f8) {
         if (f7 > f9) {
            return makeDirectionArray(direction1, direction, direction2);
         } else {
            return f10 > f7 ? makeDirectionArray(direction, direction2, direction1) : makeDirectionArray(direction, direction1, direction2);
         }
      } else if (f7 > f10) {
         return makeDirectionArray(direction1, direction2, direction);
      } else {
         return f9 > f7 ? makeDirectionArray(direction2, direction, direction1) : makeDirectionArray(direction2, direction1, direction);
      }
   }

   static Direction[] makeDirectionArray(Direction pFirst, Direction pSecond, Direction pThird) {
      return new Direction[]{pFirst, pSecond, pThird, pThird.m_122424_(), pSecond.m_122424_(), pFirst.m_122424_()};
   }

   public void m_6402_(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      Direction orientation = placer == null ? Direction.WEST : Direction.m_122382_(placer)[0].m_122424_();
      if (world.m_7702_(pos) instanceof RotatingTurretTile turretTile) {
         switch (orientation) {
            case DOWN:
               turretTile.rotationY = -90.0F;
               break;
            case UP:
               turretTile.rotationY = 90.0F;
               break;
            case NORTH:
               turretTile.rotationX = 270.0F;
               break;
            case SOUTH:
               turretTile.rotationX = 90.0F;
            case WEST:
            default:
               break;
            case EAST:
               turretTile.rotationX = 180.0F;
         }
      }
   }

   @Override
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new RotatingTurretTile(pos, state);
   }

   static {
      ROT_TURRET_BEHAVIOR_MAP.put(MethodProjectile.INSTANCE, new ITurretBehavior() {
         @Override
         public void onCast(SpellResolver resolver, ServerLevel world, BlockPos pos, Player fakePlayer, Position iposition, Direction direction) {
            if (world.m_7702_(pos) instanceof RotatingTurretTile rotatingTurretTile) {
               EntityProjectileSpell spell = new EntityProjectileSpell(world, resolver);
               spell.m_5602_(fakePlayer);
               spell.m_6034_(iposition.m_7096_(), iposition.m_7098_(), iposition.m_7094_());
               Vec3 vec3d = rotatingTurretTile.getShootAngle().m_82541_();
               spell.m_6686_(vec3d.m_7096_(), vec3d.m_7098_(), vec3d.m_7094_(), 0.5F, 0.0F);
               world.m_7967_(spell);
            }
         }
      });
      ROT_TURRET_BEHAVIOR_MAP.put(
         MethodTouch.INSTANCE,
         new ITurretBehavior() {
            @Override
            public void onCast(SpellResolver resolver, ServerLevel serverLevel, BlockPos pos, Player fakePlayer, Position dispensePosition, Direction facingDir) {
               BlockPos touchPos = pos.m_121945_(facingDir);
               if (serverLevel.m_7702_(pos) instanceof RotatingTurretTile rotatingTurretTile) {
                  Vec3 var12 = rotatingTurretTile.getShootAngle()
                     .m_82520_(rotatingTurretTile.getX() + 0.5, rotatingTurretTile.getY() + 0.5, rotatingTurretTile.getZ() + 0.5);
                  List<LivingEntity> entityList = serverLevel.m_45976_(LivingEntity.class, new AABB(touchPos));
                  if (!entityList.isEmpty()) {
                     LivingEntity entity = entityList.get(serverLevel.f_46441_.m_188503_(entityList.size()));
                     resolver.onCastOnEntity(ItemStack.f_41583_, entity, InteractionHand.MAIN_HAND);
                  } else {
                     resolver.onCastOnBlock(new BlockHitResult(var12, facingDir, new BlockPos(var12.m_7096_(), var12.m_7098_(), var12.m_7094_()), true));
                  }
               }
            }
         }
      );
   }
}
