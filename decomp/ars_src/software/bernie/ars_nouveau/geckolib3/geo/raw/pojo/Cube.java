package software.bernie.ars_nouveau.geckolib3.geo.raw.pojo;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.annotation.JsonProperty;

public class Cube {
   private Double inflate;
   private Boolean mirror;
   private double[] origin = new double[]{0.0, 0.0, 0.0};
   private double[] pivot = new double[]{0.0, 0.0, 0.0};
   private double[] rotation = new double[]{0.0, 0.0, 0.0};
   private double[] size = new double[]{1.0, 1.0, 1.0};
   private UvUnion uv;

   @JsonProperty("inflate")
   public Double getInflate() {
      return this.inflate;
   }

   @JsonProperty("inflate")
   public void setInflate(Double value) {
      this.inflate = value;
   }

   @JsonProperty("mirror")
   public Boolean getMirror() {
      return this.mirror;
   }

   @JsonProperty("mirror")
   public void setMirror(Boolean value) {
      this.mirror = value;
   }

   @JsonProperty("origin")
   public double[] getOrigin() {
      return this.origin;
   }

   @JsonProperty("origin")
   public void setOrigin(double[] value) {
      this.origin = value;
   }

   @JsonProperty("pivot")
   public double[] getPivot() {
      return this.pivot;
   }

   @JsonProperty("pivot")
   public void setPivot(double[] value) {
      this.pivot = value;
   }

   @JsonProperty("rotation")
   public double[] getRotation() {
      return this.rotation;
   }

   @JsonProperty("rotation")
   public void setRotation(double[] value) {
      this.rotation = value;
   }

   @JsonProperty("size")
   public double[] getSize() {
      return this.size;
   }

   @JsonProperty("size")
   public void setSize(double[] value) {
      this.size = value;
   }

   @JsonProperty("uv")
   public UvUnion getUv() {
      return this.uv;
   }

   @JsonProperty("uv")
   public void setUv(UvUnion value) {
      this.uv = value;
   }
}
