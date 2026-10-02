package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.core.async;

public interface NonBlockingInputFeeder {
   boolean needMoreInput();

   void endOfInput();
}
