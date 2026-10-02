package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.Tags.EntityTypes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class EntityTagProvider extends EntityTypeTagsProvider {
   public EntityTagProvider(DataGenerator p_126517_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
      super(p_126517_, modId, existingFileHelper);
   }

   protected void m_6577_() {
      this.m_206424_(EntityTypes.BOSSES).m_126582_((EntityType)ModEntities.WILDEN_BOSS.get());
      this.m_206424_(EntityTags.DISINTEGRATION_BLACKLIST);
      this.m_206424_(EntityTags.DISINTEGRATION_WHITELIST);
      this.m_206424_(EntityTags.DRYGMY_BLACKLIST).m_126582_(EntityType.f_20460_);
      this.m_206424_(EntityTags.MAGIC_FIND)
         .m_126584_(
            new EntityType[]{
               (EntityType)ModEntities.STARBUNCLE_TYPE.get(),
               (EntityType)ModEntities.ENTITY_DRYGMY.get(),
               (EntityType)ModEntities.WHIRLISPRIG_TYPE.get(),
               (EntityType)ModEntities.ENTITY_BOOKWYRM_TYPE.get(),
               (EntityType)ModEntities.ENTITY_WIXIE_TYPE.get(),
               (EntityType)ModEntities.GIFT_STARBY.get(),
               (EntityType)ModEntities.WILDEN_GUARDIAN.get(),
               (EntityType)ModEntities.WILDEN_BOSS.get(),
               (EntityType)ModEntities.WILDEN_STALKER.get(),
               (EntityType)ModEntities.WILDEN_HUNTER.get()
            }
         );
      this.m_206424_(EntityTags.SPELL_CAN_HIT);
      this.m_206424_(EntityTags.HOSTILE_MOBS)
         .m_126584_(
            new EntityType[]{
               (EntityType)ModEntities.WILDEN_HUNTER.get(), (EntityType)ModEntities.WILDEN_GUARDIAN.get(), (EntityType)ModEntities.WILDEN_STALKER.get()
            }
         );
      this.m_206424_(EntityTags.FAMILIAR)
         .m_126584_(
            new EntityType[]{
               (EntityType)ModEntities.ENTITY_FAMILIAR_STARBUNCLE.get(),
               (EntityType)ModEntities.ENTITY_FAMILIAR_SYLPH.get(),
               (EntityType)ModEntities.ENTITY_FAMILIAR_WIXIE.get(),
               (EntityType)ModEntities.ENTITY_FAMILIAR_DRYGMY.get(),
               (EntityType)ModEntities.ENTITY_FAMILIAR_BOOKWYRM.get()
            }
         );
      this.m_206424_(EntityTags.JAR_BLACKLIST).m_206428_(EntityTags.FAMILIAR);
      this.m_206424_(EntityTags.JAR_WHITELIST)
         .m_176839_(new ResourceLocation("create:contraption"))
         .m_126582_(EntityType.f_20564_)
         .m_126582_(EntityType.f_20552_)
         .m_126582_(EntityType.f_217016_)
         .m_126582_(EntityType.f_20548_)
         .m_126582_(EntityType.f_20477_)
         .m_126582_(EntityType.f_20483_)
         .m_126582_(EntityType.f_20484_)
         .m_126582_(EntityType.f_20571_)
         .m_126582_(EntityType.f_20486_)
         .m_126582_(EntityType.f_20529_)
         .m_126582_(EntityType.f_20465_)
         .m_126582_((EntityType)ModEntities.LIGHTNING_ENTITY.get())
         .m_126582_(EntityType.f_20487_);
      this.m_206424_(EntityTags.LINGERING_BLACKLIST)
         .m_126584_(
            new EntityType[]{
               (EntityType)ModEntities.LIGHTNING_ENTITY.get(), (EntityType)ModEntities.LINGER_SPELL.get(), (EntityType)ModEntities.WALL_SPELL.get()
            }
         );
      this.m_206424_(EntityTags.BERRY_BLACKLIST)
         .m_126584_(
            new EntityType[]{
               (EntityType)ModEntities.STARBUNCLE_TYPE.get(), (EntityType)ModEntities.WHIRLISPRIG_TYPE.get(), EntityType.f_20452_, EntityType.f_20550_
            }
         );
      this.m_206424_(EntityTags.JAR_RELEASE_BLACKLIST).m_126582_(EntityType.f_20565_);
      this.m_206424_(EntityTags.ANIMAL_SUMMON_BLACKLIST).m_126582_((EntityType)ModEntities.GIFT_STARBY.get());
   }

   private static TagKey<EntityType<?>> create(ResourceLocation pName) {
      return TagKey.m_203882_(Registry.f_122903_, pName);
   }
}
