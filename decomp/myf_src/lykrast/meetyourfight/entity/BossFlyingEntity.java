package lykrast.meetyourfight.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lykrast.meetyourfight.misc.BossMusic;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public abstract class BossFlyingEntity extends FlyingMob implements Enemy, IEntityAdditionalSpawnData {
   private final ServerBossEvent bossInfo = new ServerBossEvent(this.m_5446_(), BossBarColor.RED, BossBarOverlay.PROGRESS);

   protected BossFlyingEntity(EntityType<? extends FlyingMob> type, Level worldIn) {
      super(type, worldIn);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (this.m_8077_()) {
         this.bossInfo.m_6456_(this.m_5446_());
      }
   }

   public void m_6593_(@Nullable Component name) {
      super.m_6593_(name);
      this.bossInfo.m_6456_(this.m_5446_());
   }

   protected void m_8024_() {
      super.m_8024_();
      this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossInfo.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossInfo.m_6539_(player);
   }

   public boolean m_6072_() {
      return false;
   }

   protected boolean m_8028_() {
      return true;
   }

   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   @Nonnull
   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
   }

   @OnlyIn(Dist.CLIENT)
   public void readSpawnData(FriendlyByteBuf additionalData) {
      Minecraft.m_91087_().m_91106_().m_120367_(new BossMusic(this, this.getMusic()));
   }

   protected abstract SoundEvent getMusic();
}
