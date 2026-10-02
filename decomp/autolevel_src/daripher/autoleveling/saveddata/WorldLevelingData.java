package daripher.autoleveling.saveddata;

import daripher.autoleveling.data.DimensionsLevelingSettingsReloader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(
   modid = "autoleveling"
)
public class WorldLevelingData extends SavedData {
   private float levelBonus;
   public int tickCount;

   private static WorldLevelingData create() {
      return new WorldLevelingData();
   }

   @SubscribeEvent
   public static void tick(LevelTickEvent event) {
      if (event.phase == Phase.START) {
         if (!event.level.f_46443_) {
            WorldLevelingData levelingData = get((ServerLevel)event.level);
            levelingData.tick(event.level);
         }
      }
   }

   private static WorldLevelingData load(CompoundTag tag) {
      WorldLevelingData data = create();
      data.levelBonus = tag.m_128457_("LevelBonus");
      data.tickCount = tag.m_128451_("TickCount");
      return data;
   }

   public static WorldLevelingData get(ServerLevel level) {
      return (WorldLevelingData)level.m_8895_().m_164861_(WorldLevelingData::load, WorldLevelingData::create, "world_leveling");
   }

   private void tick(Level world) {
      this.tickCount++;
      if (this.tickCount >= 24000) {
         this.levelBonus = this.levelBonus + DimensionsLevelingSettingsReloader.getSettingsForDimension(world.m_46472_()).levelsPerDay();
         this.tickCount -= 24000;
      }

      this.m_77762_();
   }

   @NotNull
   public CompoundTag m_7176_(CompoundTag tag) {
      tag.m_128350_("LevelBonus", this.levelBonus);
      tag.m_128405_("TickCount", this.tickCount);
      return tag;
   }

   public int getLevelBonus() {
      return (int)this.levelBonus;
   }
}
