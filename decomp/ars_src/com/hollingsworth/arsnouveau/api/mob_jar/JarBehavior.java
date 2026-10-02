package com.hollingsworth.arsnouveau.api.mob_jar;

import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class JarBehavior<T extends Entity> {
   public void use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, MobJarTile tile) {
      T entity = this.entityFromJar(tile);
      if (!entity.m_6095_().m_204039_(EntityTags.INTERACT_JAR_BLACKLIST)) {
         if (entity instanceof LivingEntity livingEntity) {
            ItemStack handItem = player.m_21120_(handIn);
            InteractionResult result = handItem.m_41720_().m_6880_(handItem, player, livingEntity, handIn);
            if (result != InteractionResult.PASS) {
               this.syncClient(tile);
               return;
            }
         }

         if (entity instanceof Mob mob) {
            mob.m_6096_(player, handIn);
            this.syncClient(tile);
         }
      }
   }

   public void tick(MobJarTile tile) {
   }

   public void syncClient(MobJarTile tile) {
      tile.updateBlock();
   }

   @NotNull
   public T entityFromJar(MobJarTile tile) {
      return (T)tile.getEntity();
   }

   public boolean isEntityBaby(Entity entity) {
      return entity instanceof AgeableMob ageableMob ? ageableMob.m_6162_() : false;
   }

   public Vec3 scaleOffset(MobJarTile pBlockEntity) {
      return new Vec3(0.0, 0.0, 0.0);
   }

   public Vec3 translate(MobJarTile pBlockEntity) {
      return new Vec3(0.0, 0.0, 0.0);
   }

   public boolean shouldUsePartialTicks(MobJarTile pBlockEntity) {
      return false;
   }

   public int lightLevel(MobJarTile pBlockEntity) {
      return 0;
   }

   public int getAnalogPower(MobJarTile tile) {
      return tile.getEntity() == null ? 0 : 15;
   }

   public void onRedstonePower(MobJarTile tile) {
      if (this.entityFromJar(tile) instanceof Mob mob) {
         mob.m_8032_();
      }
   }

   public int getSignalPower(MobJarTile tile) {
      return 0;
   }

   public void getTooltip(MobJarTile tile, List<Component> tooltips) {
   }
}
