package software.bernie.ars_nouveau.geckolib3.network;

public interface ISyncable {
   void onAnimationSync(int var1, int var2);

   default String getSyncKey() {
      return this.getClass().getName();
   }
}
