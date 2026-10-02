package com.hollingsworth.arsnouveau.common.mixin.structure;

import java.util.List;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.Palette;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({StructureTemplate.class})
public interface StructureTemplateAccessor {
   @Accessor("entityInfoList")
   List<StructureEntityInfo> getEntityInfoList();

   @Accessor
   List<Palette> getPalettes();
}
