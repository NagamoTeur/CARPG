package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.std;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonLocation;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationConfig;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.PropertyMetadata;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.PropertyName;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.CreatorProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;

public class JsonLocationInstantiator extends ValueInstantiator.Base {
   public JsonLocationInstantiator() {
      super(JsonLocation.class);
   }

   @Override
   public boolean canCreateFromObjectWith() {
      return true;
   }

   @Override
   public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig config) {
      JavaType intType = config.constructType(int.class);
      JavaType longType = config.constructType(long.class);
      return new SettableBeanProperty[]{
         creatorProp("sourceRef", config.constructType(Object.class), 0),
         creatorProp("byteOffset", longType, 1),
         creatorProp("charOffset", longType, 2),
         creatorProp("lineNr", intType, 3),
         creatorProp("columnNr", intType, 4)
      };
   }

   private static CreatorProperty creatorProp(String name, JavaType type, int index) {
      return new CreatorProperty(PropertyName.construct(name), type, null, null, null, null, index, null, PropertyMetadata.STD_REQUIRED);
   }

   @Override
   public Object createFromObjectWith(DeserializationContext ctxt, Object[] args) {
      return new JsonLocation(args[0], _long(args[1]), _long(args[2]), _int(args[3]), _int(args[4]));
   }

   private static final long _long(Object o) {
      return o == null ? 0L : ((Number)o).longValue();
   }

   private static final int _int(Object o) {
      return o == null ? 0 : ((Number)o).intValue();
   }
}
