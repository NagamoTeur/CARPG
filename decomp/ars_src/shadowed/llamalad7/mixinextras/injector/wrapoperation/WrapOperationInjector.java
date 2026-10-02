package shadowed.llamalad7.mixinextras.injector.wrapoperation;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.code.Injector.InjectorData;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes.InjectionNode;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.asm.ASM;
import shadowed.llamalad7.mixinextras.utils.CompatibilityHelper;
import shadowed.llamalad7.mixinextras.utils.InjectorUtils;

class WrapOperationInjector extends Injector {
   private static final Handle LMF_HANDLE = new Handle(
      6,
      "java/lang/invoke/LambdaMetafactory",
      "metafactory",
      Bytecode.generateDescriptor(
         CallSite.class, new Object[]{Lookup.class, String.class, MethodType.class, MethodType.class, MethodHandle.class, MethodType.class}
      ),
      false
   );
   private static final String NPE = Type.getInternalName(NullPointerException.class);

   public WrapOperationInjector(InjectionInfo info) {
      super(info, "@WrapOperation");
   }

   protected void inject(Target target, InjectionNode node) {
      this.checkTargetModifiers(target, false);
      this.checkNode(target, node);
      this.wrapOperation(target, node);
   }

   private void checkNode(Target target, InjectionNode node) {
      AbstractInsnNode originalTarget = node.getOriginalTarget();
      AbstractInsnNode currentTarget = node.getCurrentTarget();
      if (!(currentTarget instanceof MethodInsnNode) && !(currentTarget instanceof FieldInsnNode) && originalTarget.getOpcode() != 193) {
         throw CompatibilityHelper.makeInvalidInjectionException(
            this.info, String.format("%s annotation is targeting an invalid insn in %s in %s", this.annotationType, target, this)
         );
      }
   }

   private void wrapOperation(Target target, InjectionNode node) {
      AbstractInsnNode currentTarget = node.getCurrentTarget();
      Type[] argTypes = this.getEffectiveArgTypes(currentTarget);
      Type returnType = this.getReturnType(node);
      InsnList insns = new InsnList();
      AbstractInsnNode champion = this.invokeHandler(target, node, argTypes, returnType, insns);
      target.wrapNode(currentTarget, champion, insns, new InsnList());
      target.insns.remove(currentTarget);
   }

   private AbstractInsnNode invokeHandler(Target target, InjectionNode node, Type[] argTypes, Type returnType, InsnList insns) {
      InjectorData handler = new InjectorData(target, "operation wrapper");
      boolean hasExtraThis = node.isReplaced() && node.getCurrentTarget().getOpcode() != 184;
      if (hasExtraThis) {
         argTypes = (Type[])ArrayUtils.remove(argTypes, 0);
      }

      Type[] originalArgs = this.getEffectiveArgTypes(node.getOriginalTarget());
      this.validateParams(handler, returnType, (Type[])ArrayUtils.add(originalArgs, Type.getType(Operation.class)));
      int[] argMap = this.storeArgs(target, argTypes, insns, 0);
      if (hasExtraThis) {
         insns.add(new InsnNode(87));
      }

      if (!this.isStatic) {
         insns.add(new VarInsnNode(25, 0));
      }

      this.pushArgs(this.methodArgs, insns, argMap, 0, originalArgs.length);
      if (hasExtraThis) {
         insns.add(new VarInsnNode(25, 0));
      }

      this.pushArgs(argTypes, insns, argMap, originalArgs.length, argMap.length);
      this.makeSupplier(
         target, originalArgs, returnType, node, insns, hasExtraThis, (Type[])ArrayUtils.subarray(argTypes, originalArgs.length, argTypes.length)
      );
      if (handler.captureTargetArgs > 0) {
         this.pushArgs(target.arguments, insns, target.getArgIndices(), 0, handler.captureTargetArgs);
      }

      AbstractInsnNode champion = super.invokeHandler(insns);
      if (InjectorUtils.isDynamicInstanceofRedirect(node)) {
         insns.add(new InsnNode(95));
         insns.add(new InsnNode(87));
      }

      return champion;
   }

   private void makeSupplier(Target target, Type[] argTypes, Type returnType, InjectionNode node, InsnList insns, boolean hasExtraThis, Type[] trailingParams) {
      Type[] descriptorArgs = trailingParams;
      if (hasExtraThis) {
         descriptorArgs = (Type[])ArrayUtils.add(trailingParams, 0, Type.getObjectType(this.classNode.name));
      }

      insns.add(
         new InvokeDynamicInsnNode(
            "call",
            Bytecode.generateDescriptor(Operation.class, descriptorArgs),
            LMF_HANDLE,
            new Object[]{
               Type.getMethodType(Type.getType(Object.class), new Type[]{Type.getType(Object[].class)}),
               this.generateSyntheticBridge(target, node, argTypes, hasExtraThis, trailingParams),
               Type.getMethodType(
                  returnType.getDescriptor().length() == 1
                     ? Type.getObjectType(returnType == Type.VOID_TYPE ? "java/lang/Void" : Bytecode.getBoxingType(returnType))
                     : returnType,
                  new Type[]{Type.getType(Object[].class)}
               )
            }
         )
      );
   }

   private Handle generateSyntheticBridge(final Target target, final InjectionNode node, final Type[] argTypes, final boolean virtual, final Type[] boundParams) {
      final Type returnType = this.getReturnType(node);
      MethodNode method = new MethodNode(
         ASM.API_VERSION,
         4098 | (virtual ? 0 : 8),
         "mixinextras$bridge$" + UUID.randomUUID() + '$' + this.getName(node.getCurrentTarget()),
         Bytecode.generateDescriptor(
            returnType.getDescriptor().length() == 1
               ? Type.getObjectType(returnType == Type.VOID_TYPE ? "java/lang/Void" : Bytecode.getBoxingType(returnType))
               : returnType,
            (Type[])ArrayUtils.add(boundParams, Type.getType(Object[].class))
         ),
         null,
         null
      );
      method.instructions = new InsnList() {
         {
            if (virtual) {
               this.add(new VarInsnNode(25, 0));
            }

            int paramArrayIndex = virtual ? 1 : 0;

            for (Type boundParamType : boundParams) {
               paramArrayIndex += boundParamType.getSize();
            }

            this.add(new VarInsnNode(25, paramArrayIndex));

            for (int i = 0; i < argTypes.length; i++) {
               Type argType = argTypes[i];
               this.add(new InsnNode(89));
               this.add(new IntInsnNode(16, i));
               this.add(new InsnNode(50));
               if (argType.getDescriptor().length() == 1) {
                  this.add(new TypeInsnNode(192, Bytecode.getBoxingType(argType)));
                  this.add(
                     new MethodInsnNode(
                        182, Bytecode.getBoxingType(argType), Bytecode.getUnboxingMethod(argType), Type.getMethodDescriptor(argType, new Type[0]), false
                     )
                  );
               } else {
                  this.add(new TypeInsnNode(192, argType.getInternalName()));
               }

               if (argType.getSize() == 2) {
                  this.add(new InsnNode(93));
                  this.add(new InsnNode(88));
               } else {
                  this.add(new InsnNode(95));
               }
            }

            this.add(new InsnNode(87));
            int boundParamIndex = virtual ? 1 : 0;

            for (Type boundParamType : boundParams) {
               this.add(new VarInsnNode(boundParamType.getOpcode(21), boundParamIndex));
               boundParamIndex += boundParamType.getSize();
            }

            this.add(WrapOperationInjector.this.copyNode(node, paramArrayIndex, target));
            if (returnType == Type.VOID_TYPE) {
               this.add(new InsnNode(1));
               this.add(new TypeInsnNode(192, "java/lang/Void"));
            } else if (returnType.getDescriptor().length() == 1) {
               this.add(
                  new MethodInsnNode(
                     184,
                     Bytecode.getBoxingType(returnType),
                     "valueOf",
                     Bytecode.generateDescriptor(Type.getObjectType(Bytecode.getBoxingType(returnType)), new Type[]{returnType}),
                     false
                  )
               );
            }

            this.add(new InsnNode(176));
         }
      };
      this.classNode.methods.add(method);
      return new Handle(virtual ? 7 : 6, this.classNode.name, method.name, method.desc, (this.classNode.access & 512) != 0);
   }

   private InsnList copyNode(InjectionNode node, int paramArrayIndex, Target target) {
      InsnList insns = new InsnList();
      insns.add(node.getCurrentTarget().clone(Collections.emptyMap()));
      if (InjectorUtils.isDynamicInstanceofRedirect(node)) {
         insns.add(new VarInsnNode(25, paramArrayIndex));
         insns.add(new InsnNode(3));
         insns.add(new InsnNode(50));
         insns.add(new InsnNode(95));
         this.checkAndMoveNodes(
            target.insns,
            insns,
            node.getCurrentTarget().getNext(),
            it -> it.getOpcode() == 89,
            it -> it.getOpcode() == 199,
            it -> it.getOpcode() == 187 && ((TypeInsnNode)it).desc.equals(NPE),
            it -> it.getOpcode() == 89,
            it -> it instanceof LdcInsnNode && ((LdcInsnNode)it).cst instanceof String,
            it -> it.getOpcode() == 183 && ((MethodInsnNode)it).owner.equals(NPE),
            it -> it.getOpcode() == 191,
            it -> it instanceof LabelNode,
            it -> it.getOpcode() == 95,
            it -> it.getOpcode() == 89,
            it -> it.getOpcode() == 198,
            it -> it.getOpcode() == 182 && ((MethodInsnNode)it).name.equals("getClass"),
            it -> it.getOpcode() == 182 && ((MethodInsnNode)it).name.equals("isAssignableFrom"),
            it -> it.getOpcode() == 167,
            it -> it instanceof LabelNode,
            it -> it.getOpcode() == 87,
            it -> it.getOpcode() == 87,
            it -> it.getOpcode() == 3,
            it -> it instanceof LabelNode
         );
      }

      return insns;
   }

   @SafeVarargs
   private final void checkAndMoveNodes(InsnList from, InsnList to, AbstractInsnNode current, Predicate<AbstractInsnNode>... predicates) {
      for (Predicate<AbstractInsnNode> predicate : predicates) {
         if (!predicate.test(current)) {
            throw new AssertionError("Failed assertion when wrapping instructions. Please inform LlamaLad7!");
         }

         AbstractInsnNode old = current;

         do {
            current = current.getNext();
         } while (current instanceof FrameNode);

         from.remove(old);
         to.add(old);
      }
   }

   private Type getReturnType(InjectionNode node) {
      AbstractInsnNode originalTarget = node.getOriginalTarget();
      AbstractInsnNode currentTarget = node.getCurrentTarget();
      if (originalTarget.getOpcode() == 193) {
         return Type.BOOLEAN_TYPE;
      } else if (currentTarget instanceof MethodInsnNode) {
         MethodInsnNode methodInsnNode = (MethodInsnNode)currentTarget;
         return Type.getReturnType(methodInsnNode.desc);
      } else if (currentTarget instanceof FieldInsnNode) {
         FieldInsnNode fieldInsnNode = (FieldInsnNode)currentTarget;
         return fieldInsnNode.getOpcode() != 180 && fieldInsnNode.getOpcode() != 178 ? Type.VOID_TYPE : Type.getType(fieldInsnNode.desc);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   private Type[] getEffectiveArgTypes(AbstractInsnNode node) {
      if (node instanceof MethodInsnNode) {
         MethodInsnNode methodInsnNode = (MethodInsnNode)node;
         return node.getOpcode() == 184
            ? Type.getArgumentTypes(methodInsnNode.desc)
            : (Type[])ArrayUtils.addAll(new Type[]{Type.getObjectType(methodInsnNode.owner)}, Type.getArgumentTypes(methodInsnNode.desc));
      } else {
         if (node instanceof FieldInsnNode) {
            FieldInsnNode fieldInsnNode = (FieldInsnNode)node;
            switch (fieldInsnNode.getOpcode()) {
               case 178:
                  return new Type[0];
               case 179:
                  return new Type[]{Type.getType(fieldInsnNode.desc)};
               case 180:
                  return new Type[]{Type.getObjectType(fieldInsnNode.owner)};
               case 181:
                  return new Type[]{Type.getObjectType(fieldInsnNode.owner), Type.getType(fieldInsnNode.desc)};
            }
         }

         if (node.getOpcode() == 193) {
            return new Type[]{Type.getType(Object.class)};
         } else {
            throw new UnsupportedOperationException();
         }
      }
   }

   private String getName(AbstractInsnNode node) {
      if (node instanceof MethodInsnNode) {
         return ((MethodInsnNode)node).name;
      } else if (node instanceof FieldInsnNode) {
         return ((FieldInsnNode)node).name;
      } else if (node.getOpcode() == 193) {
         String desc = ((TypeInsnNode)node).desc;
         return "instanceof" + desc.substring(desc.lastIndexOf(47) + 1);
      } else {
         throw new UnsupportedOperationException();
      }
   }
}
