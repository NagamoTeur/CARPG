package com.github.alexthe666.alexsmobs.item;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.IFalconry;
import com.github.alexthe666.alexsmobs.message.MessageSyncEntityPos;
import com.google.common.base.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemFalconryGlove extends Item implements ILeftClick {
   public ItemFalconryGlove(Properties properties) {
      super(properties);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)AlexsMobs.PROXY.getISTERProperties());
   }

   @Override
   public boolean onLeftClick(ItemStack stack, LivingEntity playerIn) {
      if (stack.m_41720_() == AMItemRegistry.FALCONRY_GLOVE.get()) {
         boolean flag = false;
         float dist = 128.0F;
         Vec3 Vector3d = playerIn.m_20299_(1.0F);
         Vec3 Vector3d1 = playerIn.m_20252_(1.0F);
         Vec3 Vector3d2 = Vector3d.m_82520_(Vector3d1.f_82479_ * (double)dist, Vector3d1.f_82480_ * (double)dist, Vector3d1.f_82481_ * (double)dist);
         double d1 = (double)dist;
         Entity pointedEntity = null;
         List<Entity> list = playerIn.f_19853_
            .m_6249_(
               playerIn,
               playerIn.m_20191_()
                  .m_82363_(Vector3d1.f_82479_ * (double)dist, Vector3d1.f_82480_ * (double)dist, Vector3d1.f_82481_ * (double)dist)
                  .m_82377_(1.0, 1.0, 1.0),
               new Predicate<Entity>() {
                  public boolean apply(@Nullable Entity entity) {
                     return entity != null && entity.m_6087_() && (entity instanceof Player || entity instanceof LivingEntity);
                  }
               }
            );
         double d2 = d1;

         for (int j = 0; j < list.size(); j++) {
            Entity entity1 = list.get(j);
            AABB axisalignedbb = entity1.m_20191_().m_82400_((double)entity1.m_6143_());
            Optional<Vec3> optional = axisalignedbb.m_82371_(Vector3d, Vector3d2);
            if (axisalignedbb.m_82390_(Vector3d)) {
               if (d2 >= 0.0) {
                  d2 = 0.0;
               }
            } else if (optional.isPresent()) {
               double d3 = Vector3d.m_82554_(optional.get());
               if (d3 < d2 || d2 == 0.0) {
                  if (entity1.m_20201_() != playerIn.m_20201_() || playerIn.canRiderInteract()) {
                     pointedEntity = entity1;
                     d2 = d3;
                  } else if (d2 == 0.0) {
                     pointedEntity = entity1;
                  }
               }
            }
         }

         if (!playerIn.m_20197_().isEmpty()) {
            for (Entity entity : playerIn.m_20197_()) {
               if (entity instanceof IFalconry && entity instanceof Animal) {
                  IFalconry falcon = (IFalconry)entity;
                  Animal animal = (Animal)entity;
                  animal.m_6038_();
                  animal.m_7678_(playerIn.m_20185_(), playerIn.m_20188_(), playerIn.m_20189_(), animal.m_146908_(), animal.m_146909_());
                  if (animal.f_19853_.f_46443_) {
                     AlexsMobs.sendMSGToServer(new MessageSyncEntityPos(animal.m_19879_(), playerIn.m_20185_(), playerIn.m_20188_(), playerIn.m_20189_()));
                  } else {
                     AlexsMobs.sendMSGToAll(new MessageSyncEntityPos(animal.m_19879_(), playerIn.m_20185_(), playerIn.m_20188_(), playerIn.m_20189_()));
                  }

                  if (playerIn instanceof Player) {
                     falcon.onLaunch((Player)playerIn, pointedEntity);
                  }

                  return true;
               }
            }
         }
      }

      return false;
   }
}
