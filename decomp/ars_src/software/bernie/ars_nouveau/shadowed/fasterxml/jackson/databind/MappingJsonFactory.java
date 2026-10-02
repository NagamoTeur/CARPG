package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind;

import java.io.IOException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonFactory;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.format.InputAccessor;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.format.MatchStrength;

public class MappingJsonFactory extends JsonFactory {
   private static final long serialVersionUID = -1L;

   public MappingJsonFactory() {
      this(null);
   }

   public MappingJsonFactory(ObjectMapper mapper) {
      super(mapper);
      if (mapper == null) {
         this.setCodec(new ObjectMapper(this));
      }
   }

   public MappingJsonFactory(JsonFactory src, ObjectMapper mapper) {
      super(src, mapper);
      if (mapper == null) {
         this.setCodec(new ObjectMapper(this));
      }
   }

   public final ObjectMapper getCodec() {
      return (ObjectMapper)this._objectCodec;
   }

   @Override
   public JsonFactory copy() {
      this._checkInvalidCopy(MappingJsonFactory.class);
      return new MappingJsonFactory(this, null);
   }

   @Override
   public String getFormatName() {
      return "JSON";
   }

   @Override
   public MatchStrength hasFormat(InputAccessor acc) throws IOException {
      return this.getClass() == MappingJsonFactory.class ? this.hasJSONFormat(acc) : null;
   }
}
