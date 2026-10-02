package shadowed.llamalad7.mixinextras.utils;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes.InjectionNode;

public class InjectorUtils {
   public static boolean isVirtualRedirect(InjectionNode node) {
      return node.isReplaced() && node.hasDecoration("redirector") && node.getCurrentTarget().getOpcode() != 184;
   }

   public static boolean isDynamicInstanceofRedirect(InjectionNode node) {
      AbstractInsnNode originalTarget = node.getOriginalTarget();
      AbstractInsnNode currentTarget = node.getCurrentTarget();
      return originalTarget.getOpcode() == 193
         && currentTarget instanceof MethodInsnNode
         && Type.getReturnType(((MethodInsnNode)currentTarget).desc).equals(Type.getType(Class.class));
   }
}
