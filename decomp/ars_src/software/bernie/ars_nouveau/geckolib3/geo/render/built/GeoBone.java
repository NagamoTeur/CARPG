package software.bernie.ars_nouveau.geckolib3.geo.render.built;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector4f;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.core.snapshot.BoneSnapshot;

public class GeoBone implements IBone {
   public GeoBone parent;
   public List<GeoBone> childBones = new ObjectArrayList();
   public List<GeoCube> childCubes = new ObjectArrayList();
   public String name;
   private BoneSnapshot initialSnapshot;
   public Boolean mirror;
   public Double inflate;
   public Boolean dontRender;
   public boolean isHidden;
   public boolean areCubesHidden = false;
   public boolean hideChildBonesToo;
   public Boolean reset;
   private float scaleX = 1.0F;
   private float scaleY = 1.0F;
   private float scaleZ = 1.0F;
   private float positionX;
   private float positionY;
   private float positionZ;
   public float rotationPointX;
   public float rotationPointY;
   public float rotationPointZ;
   private float rotateX;
   private float rotateY;
   private float rotateZ;
   public Object extraData;
   private Matrix4f modelSpaceXform = new Matrix4f();
   private Matrix4f localSpaceXform;
   private Matrix4f worldSpaceXform;
   private Matrix3f worldSpaceNormal;
   private boolean trackXform;
   public Matrix4f rotMat;

   public GeoBone() {
      this.modelSpaceXform.m_27624_();
      this.localSpaceXform = new Matrix4f();
      this.localSpaceXform.m_27624_();
      this.worldSpaceXform = new Matrix4f();
      this.worldSpaceXform.m_27624_();
      this.worldSpaceNormal = new Matrix3f();
      this.worldSpaceNormal.m_8180_();
      this.trackXform = false;
      this.rotMat = null;
   }

   @Override
   public void setModelRendererName(String modelRendererName) {
      this.name = modelRendererName;
   }

   @Override
   public void saveInitialSnapshot() {
      if (this.initialSnapshot == null) {
         this.initialSnapshot = new BoneSnapshot(this, true);
      }
   }

   @Override
   public BoneSnapshot getInitialSnapshot() {
      return this.initialSnapshot;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public float getRotationX() {
      return this.rotateX;
   }

   @Override
   public float getRotationY() {
      return this.rotateY;
   }

   @Override
   public float getRotationZ() {
      return this.rotateZ;
   }

   @Override
   public float getPositionX() {
      return this.positionX;
   }

   @Override
   public float getPositionY() {
      return this.positionY;
   }

   @Override
   public float getPositionZ() {
      return this.positionZ;
   }

   @Override
   public float getScaleX() {
      return this.scaleX;
   }

   @Override
   public float getScaleY() {
      return this.scaleY;
   }

   @Override
   public float getScaleZ() {
      return this.scaleZ;
   }

   @Override
   public void setRotationX(float value) {
      this.rotateX = value;
   }

   @Override
   public void setRotationY(float value) {
      this.rotateY = value;
   }

   @Override
   public void setRotationZ(float value) {
      this.rotateZ = value;
   }

   @Override
   public void setPositionX(float value) {
      this.positionX = value;
   }

   @Override
   public void setPositionY(float value) {
      this.positionY = value;
   }

   @Override
   public void setPositionZ(float value) {
      this.positionZ = value;
   }

   @Override
   public void setScaleX(float value) {
      this.scaleX = value;
   }

   @Override
   public void setScaleY(float value) {
      this.scaleY = value;
   }

   @Override
   public void setScaleZ(float value) {
      this.scaleZ = value;
   }

   @Override
   public boolean isHidden() {
      return this.isHidden;
   }

   @Override
   public void setHidden(boolean hidden) {
      this.setHidden(hidden, hidden);
   }

   @Override
   public void setPivotX(float value) {
      this.rotationPointX = value;
   }

   @Override
   public void setPivotY(float value) {
      this.rotationPointY = value;
   }

   @Override
   public void setPivotZ(float value) {
      this.rotationPointZ = value;
   }

   @Override
   public float getPivotX() {
      return this.rotationPointX;
   }

   @Override
   public float getPivotY() {
      return this.rotationPointY;
   }

   @Override
   public float getPivotZ() {
      return this.rotationPointZ;
   }

   @Override
   public boolean cubesAreHidden() {
      return this.areCubesHidden;
   }

   @Override
   public boolean childBonesAreHiddenToo() {
      return this.hideChildBonesToo;
   }

   @Override
   public void setCubesHidden(boolean hidden) {
      this.areCubesHidden = hidden;
   }

   @Override
   public void setHidden(boolean selfHidden, boolean skipChildRendering) {
      this.isHidden = selfHidden;
      this.hideChildBonesToo = skipChildRendering;
   }

   public GeoBone getParent() {
      return this.parent;
   }

   public boolean isTrackingXform() {
      return this.trackXform;
   }

   public void setTrackXform(boolean trackXform) {
      this.trackXform = trackXform;
   }

   public Matrix4f getModelSpaceXform() {
      this.setTrackXform(true);
      return this.modelSpaceXform;
   }

   public void setModelSpaceXform(Matrix4f modelSpaceXform) {
      this.modelSpaceXform.m_162210_(modelSpaceXform);
   }

   public Matrix4f getLocalSpaceXform() {
      this.setTrackXform(true);
      return this.localSpaceXform;
   }

   public void setLocalSpaceXform(Matrix4f localSpaceXform) {
      this.localSpaceXform.m_162210_(localSpaceXform);
   }

   @Deprecated
   public void setWorldSpaceNormal(Matrix3f worldSpaceNormal) {
      this.worldSpaceNormal = worldSpaceNormal;
   }

   @Deprecated
   public Matrix3f getWorldSpaceNormal() {
      return this.worldSpaceNormal;
   }

   @AvailableSince("3.1.24")
   public Vector3d getLocalPosition() {
      Matrix4f matrix = this.getLocalSpaceXform();
      Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
      vec.m_123607_(matrix);
      return new Vector3d((double)vec.m_123601_(), (double)vec.m_123615_(), (double)vec.m_123616_());
   }

   public Matrix4f getWorldSpaceXform() {
      this.setTrackXform(true);
      return this.worldSpaceXform;
   }

   public void setWorldSpaceXform(Matrix4f worldSpaceXform) {
      this.worldSpaceXform.m_162210_(worldSpaceXform);
   }

   @AvailableSince("3.1.24")
   public Vector3d getModelPosition() {
      Matrix4f matrix = this.getModelSpaceXform();
      Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
      vec.m_123607_(matrix);
      return new Vector3d((double)(-vec.m_123601_() * 16.0F), (double)(vec.m_123615_() * 16.0F), (double)(vec.m_123616_() * 16.0F));
   }

   @AvailableSince("3.1.24")
   public Vector3d getWorldPosition() {
      Matrix4f matrix = this.getWorldSpaceXform();
      Vector4f vec = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
      vec.m_123607_(matrix);
      return new Vector3d((double)vec.m_123601_(), (double)vec.m_123615_(), (double)vec.m_123616_());
   }

   public void setModelPosition(Vector3d pos) {
      GeoBone parent = this.getParent();
      Matrix4f identity = new Matrix4f();
      identity.m_27624_();
      Matrix4f matrix = parent == null ? identity : parent.getModelSpaceXform().m_27658_();
      matrix.m_27657_();
      Vector4f vec = new Vector4f(-((float)pos.f_86214_) / 16.0F, (float)pos.f_86215_ / 16.0F, (float)pos.f_86216_ / 16.0F, 1.0F);
      vec.m_123607_(matrix);
      this.setPosition(-vec.m_123601_() * 16.0F, vec.m_123615_() * 16.0F, vec.m_123616_() * 16.0F);
   }

   public Matrix4f getModelRotationMat() {
      Matrix4f matrix = this.getModelSpaceXform().m_27658_();
      removeMatrixTranslation(matrix);
      return matrix;
   }

   public static void removeMatrixTranslation(Matrix4f matrix) {
      matrix.f_27606_ = 0.0F;
      matrix.f_27610_ = 0.0F;
      matrix.f_27614_ = 0.0F;
   }

   public void setModelRotationMat(Matrix4f mat) {
      this.rotMat = mat;
   }

   public void addPosition(Vector3d vec) {
      this.addPosition((float)vec.f_86214_, (float)vec.f_86215_, (float)vec.f_86216_);
   }

   public void addPosition(float x, float y, float z) {
      this.addPositionX(x);
      this.addPositionY(y);
      this.addPositionZ(z);
   }

   public void addPositionX(float x) {
      this.setPositionX(this.getPositionX() + x);
   }

   public void addPositionY(float y) {
      this.setPositionY(this.getPositionY() + y);
   }

   public void addPositionZ(float z) {
      this.setPositionZ(this.getPositionZ() + z);
   }

   public void setPosition(Vector3d vec) {
      this.setPosition((float)vec.f_86214_, (float)vec.f_86215_, (float)vec.f_86216_);
   }

   public void setPosition(float x, float y, float z) {
      this.setPositionX(x);
      this.setPositionY(y);
      this.setPositionZ(z);
   }

   public Vector3d getPosition() {
      return new Vector3d((double)this.getPositionX(), (double)this.getPositionY(), (double)this.getPositionZ());
   }

   public void addRotation(Vector3d vec) {
      this.addRotation((float)vec.f_86214_, (float)vec.f_86215_, (float)vec.f_86216_);
   }

   public void addRotation(float x, float y, float z) {
      this.addRotationX(x);
      this.addRotationY(y);
      this.addRotationZ(z);
   }

   public void addRotationX(float x) {
      this.setRotationX(this.getRotationX() + x);
   }

   public void addRotationY(float y) {
      this.setRotationY(this.getRotationY() + y);
   }

   public void addRotationZ(float z) {
      this.setRotationZ(this.getRotationZ() + z);
   }

   public void setRotation(Vector3d vec) {
      this.setRotation((float)vec.f_86214_, (float)vec.f_86215_, (float)vec.f_86216_);
   }

   public void setRotation(float x, float y, float z) {
      this.setRotationX(x);
      this.setRotationY(y);
      this.setRotationZ(z);
   }

   public Vector3d getRotation() {
      return new Vector3d((double)this.getRotationX(), (double)this.getRotationY(), (double)this.getRotationZ());
   }

   public void multiplyScale(Vector3d vec) {
      this.multiplyScale((float)vec.f_86214_, (float)vec.f_86215_, (float)vec.f_86216_);
   }

   public void multiplyScale(float x, float y, float z) {
      this.setScaleX(this.getScaleX() * x);
      this.setScaleY(this.getScaleY() * y);
      this.setScaleZ(this.getScaleZ() * z);
   }

   public void setScale(Vector3d vec) {
      this.setScale((float)vec.f_86214_, (float)vec.f_86215_, (float)vec.f_86216_);
   }

   public void setScale(float x, float y, float z) {
      this.setScaleX(x);
      this.setScaleY(y);
      this.setScaleZ(z);
   }

   public Vector3d getScale() {
      return new Vector3d((double)this.getScaleX(), (double)this.getScaleY(), (double)this.getScaleZ());
   }

   public void addRotationOffsetFromBone(GeoBone source) {
      this.setRotationX(this.getRotationX() + source.getRotationX() - source.getInitialSnapshot().rotationValueX);
      this.setRotationY(this.getRotationY() + source.getRotationY() - source.getInitialSnapshot().rotationValueY);
      this.setRotationZ(this.getRotationZ() + source.getRotationZ() - source.getInitialSnapshot().rotationValueZ);
   }
}
