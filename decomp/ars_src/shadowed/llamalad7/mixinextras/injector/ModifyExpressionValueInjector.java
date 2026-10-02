package shadowed.llamalad7.mixinextras.injector;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.code.Injector.InjectorData;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes.InjectionNode;
import org.spongepowered.asm.mixin.injection.struct.Target.Extension;
import org.spongepowered.asm.util.Bytecode;
import shadowed.llamalad7.mixinextras.utils.CompatibilityHelper;

public class ModifyExpressionValueInjector extends Injector {
   public ModifyExpressionValueInjector(InjectionInfo info) {
      super(info, "@ModifyExpressionValue");
   }

   protected void inject(Target target, InjectionNode node) {
      this.checkTargetReturnsAValue(target, node);
      this.checkTargetModifiers(target, false);
      AbstractInsnNode valueNode = node.getCurrentTarget();
      Type valueType = this.getReturnType(valueNode);
      if (valueNode instanceof TypeInsnNode && valueNode.getOpcode() == 187) {
         valueNode = target.findInitNodeFor((TypeInsnNode)valueNode);
      }

      this.injectValueModifier(target, valueNode, valueType);
   }

   private void checkTargetReturnsAValue(Target target, InjectionNode node) {
      Type returnType = this.getReturnType(node.getCurrentTarget());
      if (returnType == Type.VOID_TYPE) {
         throw CompatibilityHelper.makeInvalidInjectionException(
            this.info, String.format("%s annotation is targeting an instruction with a return type of 'void' in %s in %s", this.annotationType, target, this)
         );
      } else if (returnType == null) {
         throw CompatibilityHelper.makeInvalidInjectionException(
            this.info, String.format("%s annotation is targeting an invalid insn in %s in %s", this.annotationType, target, this)
         );
      }
   }

   private void injectValueModifier(Target target, AbstractInsnNode valueNode, Type valueType) {
      Extension extraStack = target.extendStack();
      InsnList after = new InsnList();
      this.invokeHandler(valueType, target, extraStack, after);
      extraStack.apply();
      target.insns.insert(valueNode, after);
   }

   private void invokeHandler(Type valueType, Target target, Extension extraStack, InsnList after) {
      InjectorData handler = new InjectorData(target, "expression value modifier");
      this.validateParams(handler, valueType, new Type[]{valueType});
      if (!this.isStatic) {
         after.add(new VarInsnNode(25, 0));
         if (valueType.getSize() == 2) {
            after.add(new InsnNode(91));
            after.add(new InsnNode(87));
         } else {
            after.add(new InsnNode(95));
         }
      }

      if (handler.captureTargetArgs > 0) {
         this.pushArgs(target.arguments, after, target.getArgIndices(), 0, handler.captureTargetArgs, extraStack);
      }

      this.invokeHandler(after);
   }

   private Type getReturnType(AbstractInsnNode node) {
      if (node instanceof MethodInsnNode) {
         MethodInsnNode methodInsnNode = (MethodInsnNode)node;
         return Type.getReturnType(methodInsnNode.desc);
      } else if (node instanceof FieldInsnNode) {
         FieldInsnNode fieldInsnNode = (FieldInsnNode)node;
         return fieldInsnNode.getOpcode() != 180 && fieldInsnNode.getOpcode() != 178 ? Type.VOID_TYPE : Type.getType(fieldInsnNode.desc);
      } else if (Bytecode.isConstant(node)) {
         return Bytecode.getConstantType(node);
      } else if (node instanceof TypeInsnNode && node.getOpcode() == 187) {
         TypeInsnNode typeInsnNode = (TypeInsnNode)node;
         return Type.getObjectType(typeInsnNode.desc);
      } else {
         return null;
      }
   }
}
