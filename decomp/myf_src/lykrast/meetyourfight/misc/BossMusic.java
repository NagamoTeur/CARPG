package lykrast.meetyourfight.misc;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BossMusic extends AbstractTickableSoundInstance {
   private final LivingEntity boss;

   public BossMusic(LivingEntity boss, SoundEvent sound) {
      super(sound, SoundSource.RECORDS, SoundInstance.m_235150_());
      this.boss = boss;
      this.f_119575_ = boss.m_20185_();
      this.f_119576_ = boss.m_20186_();
      this.f_119577_ = boss.m_20189_();
      this.f_119578_ = true;
   }

   public void m_7788_() {
      if (this.boss.m_6084_()) {
         this.f_119575_ = this.boss.m_20185_();
         this.f_119576_ = this.boss.m_20186_();
         this.f_119577_ = this.boss.m_20189_();
      } else {
         this.m_119609_();
      }
   }
}
