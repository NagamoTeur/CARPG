package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.api.nbt.AbstractData;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class PiglinBehavior extends JarBehavior<Piglin> {
   @Override
   public void use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, MobJarTile tile) {
      if (!world.f_46443_) {
         ItemStack heldStack = player.m_21120_(handIn);
         Piglin piglin = this.entityFromJar(tile);
         boolean hasCurrency = piglin.m_21206_().isPiglinCurrency();
         if (heldStack.isPiglinCurrency() && !hasCurrency && piglin.m_34667_()) {
            piglin.m_21008_(InteractionHand.OFF_HAND, player.m_21120_(handIn).m_41620_(1));
            tile.updateBlock();
         }
      }
   }

   @Override
   public void tick(MobJarTile tile) {
      if (!tile.m_58904_().f_46443_) {
         PiglinBehavior.ExtraData data = new PiglinBehavior.ExtraData(tile.getExtraDataTag());
         Piglin piglin = this.entityFromJar(tile);
         boolean hasCurrency = piglin.m_21206_().isPiglinCurrency();
         if (!hasCurrency && piglin.m_34667_() && tile.m_58904_().m_213780_().m_188503_(20) == 0) {
            List<ItemEntity> itemEntities = piglin.f_19853_.m_6443_(ItemEntity.class, new AABB(tile.m_58899_()).m_82400_(3.0), Entity::m_6084_);
            if (!itemEntities.isEmpty()) {
               ItemEntity itemEntity = itemEntities.stream().filter(item -> item.m_32055_().isPiglinCurrency()).findFirst().orElse(null);
               if (itemEntity != null) {
                  piglin.m_21008_(InteractionHand.OFF_HAND, itemEntity.m_32055_().m_41620_(1));
                  tile.updateBlock();
               }
            }
         }

         boolean isAdmiring = piglin.m_34667_() && piglin.m_21206_().isPiglinCurrency();
         if (isAdmiring) {
            data.ticksWithCurrency++;
            if (data.ticksWithCurrency >= 60) {
               data.ticksWithCurrency = 0;
               throwItems(piglin, getBarterResponseItems(piglin));
               piglin.m_21008_(InteractionHand.OFF_HAND, ItemStack.f_41583_);
               tile.updateBlock();
            }

            CompoundTag updateTag = new CompoundTag();
            data.writeToNBT(updateTag);
            tile.setExtraDataTag(updateTag);
         }
      }
   }

   private static List<ItemStack> getBarterResponseItems(Piglin pPiglin) {
      LootTable loottable = pPiglin.f_19853_.m_7654_().m_129898_().m_79217_(BuiltInLootTables.f_78738_);
      List<ItemStack> list = loottable.m_230922_(
         new Builder((ServerLevel)pPiglin.f_19853_)
            .m_78972_(LootContextParams.f_81455_, pPiglin)
            .m_230911_(pPiglin.f_19853_.f_46441_)
            .m_78975_(LootContextParamSets.f_81417_)
      );
      return list;
   }

   private static void throwItems(Piglin pPilgin, List<ItemStack> pStacks) {
      List<Player> players = pPilgin.f_19853_.m_45976_(Player.class, pPilgin.m_20191_().m_82400_(3.0));
      if (!players.isEmpty()) {
         throwItemsTowardPlayer(pPilgin, players.get(0), pStacks);
      } else {
         throwItemsTowardRandomPos(pPilgin, pStacks);
      }
   }

   private static void throwItemsTowardRandomPos(Piglin pPiglin, List<ItemStack> pStacks) {
      throwItemsTowardPos(pPiglin, pStacks, getRandomNearbyPos(pPiglin));
   }

   private static void throwItemsTowardPlayer(Piglin pPiglin, Player pPlayer, List<ItemStack> pStacks) {
      throwItemsTowardPos(pPiglin, pStacks, pPlayer.m_20182_());
   }

   private static Vec3 getRandomNearbyPos(Piglin pPiglin) {
      Vec3 vec3 = LandRandomPos.m_148488_(pPiglin, 4, 2);
      return vec3 == null ? pPiglin.m_20182_() : vec3;
   }

   private static void throwItemsTowardPos(Piglin pPiglin, List<ItemStack> pStacks, Vec3 pPos) {
      if (!pStacks.isEmpty()) {
         pPiglin.m_6674_(InteractionHand.OFF_HAND);

         for (ItemStack itemstack : pStacks) {
            BehaviorUtils.m_22613_(pPiglin, itemstack, pPos.m_82520_(0.0, 0.0, 0.0));
         }
      }
   }

   public static class ExtraData extends AbstractData {
      boolean hasCurrency;
      int ticksWithCurrency;

      public ExtraData(CompoundTag tag) {
         super(tag);
         this.hasCurrency = tag.m_128471_("hasCurrency");
         this.ticksWithCurrency = tag.m_128451_("ticksWithCurrency");
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         tag.m_128379_("hasCurrency", this.hasCurrency);
         tag.m_128405_("ticksWithCurrency", this.ticksWithCurrency);
      }
   }
}
