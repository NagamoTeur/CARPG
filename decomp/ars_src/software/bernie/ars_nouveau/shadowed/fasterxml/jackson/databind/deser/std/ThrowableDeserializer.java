package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.std;

import java.io.IOException;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.JsonToken;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.JsonDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.BeanDeserializer;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.util.NameTransformer;

public class ThrowableDeserializer extends BeanDeserializer {
   private static final long serialVersionUID = 1L;
   protected static final String PROP_NAME_MESSAGE = "message";

   public ThrowableDeserializer(BeanDeserializer baseDeserializer) {
      super(baseDeserializer);
      this._vanillaProcessing = false;
   }

   protected ThrowableDeserializer(BeanDeserializer src, NameTransformer unwrapper) {
      super(src, unwrapper);
   }

   @Override
   public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer unwrapper) {
      return this.getClass() != ThrowableDeserializer.class ? this : new ThrowableDeserializer(this, unwrapper);
   }

   @Override
   public Object deserializeFromObject(JsonParser p, DeserializationContext ctxt) throws IOException {
      if (this._propertyBasedCreator != null) {
         return this._deserializeUsingPropertyBased(p, ctxt);
      } else if (this._delegateDeserializer != null) {
         return this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(p, ctxt));
      } else if (this._beanType.isAbstract()) {
         return ctxt.handleMissingInstantiator(this.handledType(), this.getValueInstantiator(), p, "abstract type (need to add/enable type information?)");
      } else {
         boolean hasStringCreator = this._valueInstantiator.canCreateFromString();
         boolean hasDefaultCtor = this._valueInstantiator.canCreateUsingDefault();
         if (!hasStringCreator && !hasDefaultCtor) {
            return ctxt.handleMissingInstantiator(
               this.handledType(),
               this.getValueInstantiator(),
               p,
               "Throwable needs a default contructor, a single-String-arg constructor; or explicit @JsonCreator"
            );
         } else {
            Object throwable = null;
            Object[] pending = null;
            int pendingIx = 0;

            while (p.getCurrentToken() != JsonToken.END_OBJECT) {
               String propName = p.getCurrentName();
               SettableBeanProperty prop = this._beanProperties.find(propName);
               p.nextToken();
               if (prop != null) {
                  if (throwable != null) {
                     prop.deserializeAndSet(p, ctxt, throwable);
                  } else {
                     if (pending == null) {
                        int len = this._beanProperties.size();
                        pending = new Object[len + len];
                     }

                     pending[pendingIx++] = prop;
                     pending[pendingIx++] = prop.deserialize(p, ctxt);
                  }
               } else if ("message".equals(propName) && hasStringCreator) {
                  throwable = this._valueInstantiator.createFromString(ctxt, p.getText());
                  if (pending != null) {
                     int i = 0;

                     for (int len = pendingIx; i < len; i += 2) {
                        prop = (SettableBeanProperty)pending[i];
                        prop.set(throwable, pending[i + 1]);
                     }

                     pending = null;
                  }
               } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                  p.skipChildren();
               } else if (this._anySetter != null) {
                  this._anySetter.deserializeAndSet(p, ctxt, throwable, propName);
               } else {
                  this.handleUnknownProperty(p, ctxt, throwable, propName);
               }

               p.nextToken();
            }

            if (throwable == null) {
               if (hasStringCreator) {
                  throwable = this._valueInstantiator.createFromString(ctxt, null);
               } else {
                  throwable = this._valueInstantiator.createUsingDefault(ctxt);
               }

               if (pending != null) {
                  int i = 0;

                  for (int len = pendingIx; i < len; i += 2) {
                     SettableBeanProperty prop = (SettableBeanProperty)pending[i];
                     prop.set(throwable, pending[i + 1]);
                  }
               }
            }

            return throwable;
         }
      }
   }
}
