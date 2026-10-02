package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.entity.Pet.Modern_Remnant_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class Remnant_Skull extends Item {
   private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.f_20408_.and(Entity::m_6087_);

   public Remnant_Skull(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_40622_, Player p_40623_, InteractionHand p_40624_) {
      ItemStack itemstack = p_40623_.m_21120_(p_40624_);
      HitResult hitresult = m_41435_(p_40622_, p_40623_, Fluid.ANY);
      if (hitresult.m_6662_() == Type.MISS) {
         return InteractionResultHolder.m_19098_(itemstack);
      } else {
         Vec3 vec3 = p_40623_.m_20252_(1.0F);
         Vec3 vec31 = hitresult.m_82450_();
         double d0 = 5.0;
         List<Entity> list = p_40622_.m_6249_(p_40623_, p_40623_.m_20191_().m_82369_(vec3.m_82490_(5.0)).m_82400_(1.0), ENTITY_PREDICATE);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               AABB aabb = entity.m_20191_().m_82400_((double)entity.m_6143_());
               if (aabb.m_82390_(vec31)) {
                  return InteractionResultHolder.m_19098_(itemstack);
               }
            }
         }

         if (hitresult.m_6662_() == Type.BLOCK) {
            Modern_Remnant_Entity remnantEntity = (Modern_Remnant_Entity)((EntityType)ModEntities.MODERN_REMNANT.get()).m_20615_(p_40622_);
            remnantEntity.m_6034_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_);
            if (!p_40622_.m_45756_(remnantEntity, remnantEntity.m_20191_())) {
               return InteractionResultHolder.m_19100_(itemstack);
            } else {
               if (!p_40622_.f_46443_) {
                  p_40622_.m_7967_(remnantEntity);
                  p_40622_.m_220400_(p_40623_, GameEvent.f_157810_, vec31);
                  if (!p_40623_.m_150110_().f_35937_) {
                     itemstack.m_41774_(1);
                  }
               }

               p_40623_.m_36246_(Stats.f_12982_.m_12902_(this));
               return InteractionResultHolder.m_19092_(itemstack, p_40622_.m_5776_());
            }
         } else {
            return InteractionResultHolder.m_19098_(itemstack);
         }
      }
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.remnant_skull.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
