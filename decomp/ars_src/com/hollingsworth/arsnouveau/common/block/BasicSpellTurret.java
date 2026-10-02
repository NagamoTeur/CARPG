package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.ITurretBehavior;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.TileCaster;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.common.block.tile.BasicSpellTurretTile;
import com.hollingsworth.arsnouveau.common.block.tile.RotatingTurretTile;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketOneShotAnimation;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.HashMap;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;

public class BasicSpellTurret extends TickableModBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty TRIGGERED = BlockStateProperties.f_61360_;
   public static final DirectionProperty FACING = DirectionalBlock.f_52588_;
   public static HashMap<AbstractCastMethod, ITurretBehavior> TURRET_BEHAVIOR_MAP = new HashMap<>();
   static final VoxelShape shape = Block.m_49796_(4.6, 4.6, 4.6, 11.6, 11.6, 11.6);

   public BasicSpellTurret(Properties properties) {
      super(properties);
      this.m_49959_(
         (BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(BlockStateProperties.f_61362_, false))
               .m_61124_(FACING, Direction.NORTH))
            .m_61124_(TRIGGERED, Boolean.FALSE)
      );
   }

   public BasicSpellTurret() {
      this(defaultProperties().m_60955_());
   }

   public void m_213897_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
      this.shootSpell(worldIn, pos);
   }

   public void shootSpell(ServerLevel world, BlockPos pos) {
      if (world.m_7702_(pos) instanceof BasicSpellTurretTile tile) {
         ISpellCaster caster = tile.getSpellCaster();
         if (!caster.getSpell().isEmpty()) {
            int manaCost = tile.getManaCost();
            if (manaCost <= 0 || SourceUtil.takeSourceWithParticles(pos, world, 10, manaCost) != null) {
               Networking.sendToNearby(world, pos, new PacketOneShotAnimation(pos));
               Position iposition = getDispensePosition(new BlockSourceImpl(world, pos));
               Direction direction = (Direction)world.m_8055_(pos).m_61143_(FACING);
               FakePlayer fakePlayer = ANFakePlayer.getPlayer(world);
               fakePlayer.m_6034_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
               EntitySpellResolver resolver = new EntitySpellResolver(
                  new SpellContext(world, caster.getSpell(), fakePlayer, new TileCaster(tile, SpellContext.CasterType.TURRET))
               );
               if (resolver.castType != null && TURRET_BEHAVIOR_MAP.containsKey(resolver.castType)) {
                  TURRET_BEHAVIOR_MAP.get(resolver.castType).onCast(resolver, world, pos, fakePlayer, iposition, direction);
                  caster.playSound(pos, world, null, caster.getCurrentSound(), SoundSource.BLOCKS);
               }
            }
         }
      }
   }

   public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      boolean neighborSignal = worldIn.m_46753_(pos) || worldIn.m_46753_(pos.m_7494_());
      boolean isTriggered = (Boolean)state.m_61143_(TRIGGERED);
      if (neighborSignal && !isTriggered) {
         worldIn.m_186460_(pos, this, 4);
         worldIn.m_7731_(pos, (BlockState)state.m_61124_(TRIGGERED, Boolean.TRUE), 4);
      } else if (!neighborSignal && isTriggered) {
         worldIn.m_7731_(pos, (BlockState)state.m_61124_(TRIGGERED, Boolean.FALSE), 4);
      }
   }

   public static Position getDispensePosition(BlockSource coords) {
      Direction direction = (Direction)coords.m_6414_().m_61143_(FACING);
      double d0 = coords.m_7096_() + 0.5 * (double)direction.m_122429_();
      double d1 = coords.m_7098_() + 0.5 * (double)direction.m_122430_();
      double d2 = coords.m_7094_() + 0.5 * (double)direction.m_122431_();
      return new PositionImpl(d0, d1, d2);
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(BlockStateProperties.f_61362_) ? Fluids.f_76193_.m_76068_(false) : Fluids.f_76191_.m_76145_();
   }

   @NotNull
   public BlockState m_5573_(BlockPlaceContext context) {
      FluidState fluidState = context.m_43725_().m_6425_(context.m_8083_());
      return (BlockState)((BlockState)this.m_49966_().m_61124_(FACING, context.m_7820_().m_122424_()))
         .m_61124_(BlockStateProperties.f_61362_, fluidState.m_76152_() == Fluids.f_76193_);
   }

   public BlockState m_7417_(BlockState stateIn, Direction side, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.m_61143_(BlockStateProperties.f_61362_)) {
         worldIn.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(worldIn));
      }

      return stateIn;
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (handIn != InteractionHand.MAIN_HAND) {
         return InteractionResult.PASS;
      } else if (worldIn.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         ItemStack stack = player.m_21120_(handIn);
         Spell spell = CasterUtil.getCaster(stack).getSpell();
         if (!spell.isEmpty()) {
            if (spell.getCastMethod() == null) {
               PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.alert.turret_needs_form"));
               return InteractionResult.SUCCESS;
            }

            if (!TURRET_BEHAVIOR_MAP.containsKey(spell.getCastMethod())) {
               PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.alert.turret_type"));
               return InteractionResult.SUCCESS;
            }

            if (worldIn.m_7702_(pos) instanceof BasicSpellTurretTile tile) {
               tile.spellCaster.copyFromCaster(CasterUtil.getCaster(stack));
               tile.spellCaster.setSpell(spell.clone());
               tile.updateBlock();
               PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.alert.spell_set"));
               worldIn.m_7260_(pos, state, state, 2);
            }
         }

         return super.m_6227_(state, worldIn, pos, player, handIn, hit);
      }
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING, TRIGGERED, BlockStateProperties.f_61362_});
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new BasicSpellTurretTile(pos, state);
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return shape;
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }

   static {
      TURRET_BEHAVIOR_MAP.put(MethodProjectile.INSTANCE, new ITurretBehavior() {
         @Override
         public void onCast(SpellResolver resolver, ServerLevel world, BlockPos pos, Player fakePlayer, Position iposition, Direction direction) {
            EntityProjectileSpell spell = new EntityProjectileSpell(world, resolver);
            spell.m_5602_(fakePlayer);
            spell.m_6034_(iposition.m_7096_(), iposition.m_7098_(), iposition.m_7094_());
            if (world.m_7702_(pos) instanceof RotatingTurretTile rotatingTurretTile) {
               Vec3 vec3d = rotatingTurretTile.getShootAngle().m_82541_();
               spell.m_6686_(vec3d.m_7096_(), vec3d.m_7098_(), vec3d.m_7094_(), 0.5F, 0.0F);
            } else {
               spell.m_6686_((double)direction.m_122429_(), (double)((float)direction.m_122430_()), (double)direction.m_122431_(), 0.5F, 0.0F);
            }

            world.m_7967_(spell);
         }
      });
      TURRET_BEHAVIOR_MAP.put(
         MethodTouch.INSTANCE,
         new ITurretBehavior() {
            @Override
            public void onCast(SpellResolver resolver, ServerLevel serverLevel, BlockPos pos, Player fakePlayer, Position dispensePosition, Direction facingDir) {
               BlockPos touchPos = pos.m_121945_(facingDir);
               List<LivingEntity> entityList = serverLevel.m_45976_(LivingEntity.class, new AABB(touchPos));
               if (!entityList.isEmpty()) {
                  LivingEntity entity = entityList.get(serverLevel.f_46441_.m_188503_(entityList.size()));
                  resolver.onCastOnEntity(ItemStack.f_41583_, entity, InteractionHand.MAIN_HAND);
               } else {
                  Vec3 hitVec = new Vec3(
                     (double)touchPos.m_123341_() + (double)facingDir.m_122429_() * 0.5,
                     (double)touchPos.m_123342_() + (double)facingDir.m_122430_() * 0.5,
                     (double)touchPos.m_123343_() + (double)facingDir.m_122431_() * 0.5
                  );
                  resolver.onCastOnBlock(
                     new BlockHitResult(hitVec, facingDir.m_122424_(), new BlockPos(touchPos.m_123341_(), touchPos.m_123342_(), touchPos.m_123343_()), true)
                  );
               }
            }
         }
      );
   }
}
