package software.bernie.ars_nouveau.geckolib3.util;

import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.core.manager.InstancedAnimationFactory;
import software.bernie.ars_nouveau.geckolib3.core.manager.SingletonAnimationFactory;
import software.bernie.ars_nouveau.geckolib3.world.storage.GeckoLibIdTracker;

public class GeckoLibUtil {
   private static final String GECKO_LIB_ID_NBT = "GeckoLibID";

   public static int getIDFromStack(ItemStack stack) {
      return stackHasIDTag(stack) ? stack.m_41783_().m_128451_("GeckoLibID") : Objects.hash(stack.m_41720_().toString(), stack.m_41783_(), stack.m_41613_());
   }

   public static void writeIDToStack(ItemStack stack, ServerLevel world) {
      if (!stackHasIDTag(stack)) {
         int id = GeckoLibIdTracker.from(world).getNextId(GeckoLibIdTracker.Type.ITEM);
         stack.m_41784_().m_128405_("GeckoLibID", id);
      }
   }

   public static int guaranteeIDForStack(ItemStack stack, ServerLevel world) {
      if (!stackHasIDTag(stack)) {
         int id = GeckoLibIdTracker.from(world).getNextId(GeckoLibIdTracker.Type.ITEM);
         stack.m_41784_().m_128405_("GeckoLibID", id);
         return id;
      } else {
         return stack.m_41783_().m_128451_("GeckoLibID");
      }
   }

   public static void removeIDFromStack(ItemStack stack) {
      if (stackHasIDTag(stack)) {
         stack.m_41783_().m_128473_("GeckoLibID");
      }
   }

   public static boolean stackHasIDTag(ItemStack stack) {
      return stack.m_41782_() && stack.m_41783_().m_128425_("GeckoLibID", 3);
   }

   public static AnimationController getControllerForStack(AnimationFactory factory, ItemStack stack, String controllerName) {
      return getControllerForID(factory, getIDFromStack(stack), controllerName);
   }

   public static AnimationController getControllerForID(AnimationFactory factory, Integer id, String controllerName) {
      return factory.getOrCreateAnimationData(id.intValue()).getAnimationControllers().get(controllerName);
   }

   public static AnimationFactory createFactory(IAnimatable animatable) {
      return createFactory(animatable, !(animatable instanceof Entity) && !(animatable instanceof BlockEntity));
   }

   public static AnimationFactory createFactory(IAnimatable animatable, boolean singletonObject) {
      return (AnimationFactory)(singletonObject ? new SingletonAnimationFactory(animatable) : new InstancedAnimationFactory(animatable));
   }
}
