package io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob;

import com.mojang.math.Vector3f;
import java.util.HashMap;
import java.util.Map;
import software.bernie.geckolib3.core.processor.IBone;

public class TransformStack {
   private final Map<IBone, Vector3f> positionStack = new HashMap<>();
   private final Map<IBone, Vector3f> rotationStack = new HashMap<>();

   public void pushPosition(IBone bone, Vector3f appendVec) {
      Vector3f vec = this.positionStack.getOrDefault(bone, new Vector3f(0.0F, 0.0F, 0.0F));
      vec.m_122253_(appendVec);
      this.positionStack.put(bone, vec);
   }

   public void pushPosition(IBone bone, float x, float y, float z) {
      this.pushPosition(bone, new Vector3f(x, y, z));
   }

   public void overridePosition(IBone bone, Vector3f newVec) {
      this.positionStack.put(bone, newVec);
   }

   public void pushRotation(IBone bone, Vector3f appendVec) {
      Vector3f vec = this.rotationStack.getOrDefault(bone, new Vector3f(0.0F, 0.0F, 0.0F));
      vec.m_122253_(appendVec);
      this.rotationStack.put(bone, vec);
   }

   public void pushRotation(IBone bone, float x, float y, float z) {
      this.pushRotation(bone, new Vector3f(x, y, z));
   }

   public void pushRotationWithBase(IBone bone, float x, float y, float z) {
      Vector3f base = new Vector3f(bone.getRotationX(), bone.getRotationY(), bone.getRotationZ());
      base.m_122272_(x, y, z);
      this.pushRotation(bone, base);
   }

   public void overrideRotation(IBone bone, Vector3f newVec) {
      this.rotationStack.put(bone, newVec);
   }

   public void popStack() {
      this.positionStack.forEach(this::setPosImpl);
      this.rotationStack.forEach(this::setRotImpl);
      this.positionStack.clear();
      this.rotationStack.clear();
   }

   public void setRotImpl(IBone bone, Vector3f vector3f) {
      bone.setRotationX(wrapRadians(vector3f.m_122239_()));
      bone.setRotationY(wrapRadians(vector3f.m_122260_()));
      bone.setRotationZ(wrapRadians(vector3f.m_122269_()));
   }

   public void setPosImpl(IBone bone, Vector3f vector3f) {
      bone.setPositionX(vector3f.m_122239_());
      bone.setPositionY(vector3f.m_122260_());
      bone.setPositionZ(vector3f.m_122269_());
   }

   public static float wrapRadians(float pValue) {
      float twoPi = 6.2831F;
      float pi = 3.14155F;
      float f = pValue % twoPi;
      if (f >= pi) {
         f -= twoPi;
      }

      if (f < -pi) {
         f += twoPi;
      }

      return f;
   }
}
