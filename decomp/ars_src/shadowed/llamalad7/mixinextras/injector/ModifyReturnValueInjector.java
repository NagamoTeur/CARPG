package shadowed.llamalad7.mixinextras.injector;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.code.Injector.InjectorData;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes.InjectionNode;
import shadowed.llamalad7.mixinextras.utils.CompatibilityHelper;

public class ModifyReturnValueInjector extends Injector {
   public ModifyReturnValueInjector(InjectionInfo info) {
      super(info, "@ModifyReturnValue");
   }

   protected void inject(Target target, InjectionNode node) {
      int opcode = node.getCurrentTarget().getOpcode();
      if (opcode >= 172 && opcode < 177) {
         this.checkTargetModifiers(target, false);
         this.injectReturnValueModifier(target, node);
      } else {
         throw CompatibilityHelper.makeInvalidInjectionException(
            this.info, String.format("%s annotation is targeting an invalid insn in %s in %s", this.annotationType, target, this)
         );
      }
   }

   private void injectReturnValueModifier(Target target, InjectionNode node) {
      InjectorData handler = new InjectorData(target, "return value modifier");
      InsnList insns = new InsnList();
      this.validateParams(handler, target.returnType, new Type[]{target.returnType});
      if (!this.isStatic) {
         insns.add(new VarInsnNode(25, 0));
         if (target.returnType.getSize() == 2) {
            insns.add(new InsnNode(91));
            insns.add(new InsnNode(87));
         } else {
            insns.add(new InsnNode(95));
         }
      }

      if (handler.captureTargetArgs > 0) {
         this.pushArgs(target.arguments, insns, target.getArgIndices(), 0, handler.captureTargetArgs);
      }

      this.invokeHandler(insns);
      target.insertBefore(node, insns);
   }
}
