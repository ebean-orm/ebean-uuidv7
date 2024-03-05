package io.ebean.uuidv7;

import java.time.Clock;
import java.util.Random;
import java.util.UUID;

/**
 * UUIDv7 generates version 7 UUID using Unix Epoch time plus random component.
 *
 * <pre>{@code
 *
 *   // generate using default clock and SecureRandom
 *   UUID uuid = UUIDv7.generate();
 *
 * }</pre>
 *
 * <pre>{@code
 *
 *   // specify the clock and random provider to use
 *   Clock sillyClock = Clock.fixed(Instant.EPOCH, ZoneId.of("UTC");
 *   Random myRandom = SecureRandom.getInstanceStrong();
 *
 *   UUIDv7 uuidV7 = UUIDv7.builder()
 *                 .clock(sillyClock)
 *                 .random(myRandom)
 *                 .build();
 *
 *   UUID uuid = uuidV7.generate();
 *
 * }</pre>
 */
public interface UUIDv7 {

  /**
   * Generate a Version 7 UUID using default clock and SecureRandom.
   *
   * <pre>{@code
   *
   *   // generate using default clock and SecureRandom
   *   UUID uuid = UUIDv7.generate();
   *
   * }</pre>
   */
  static UUID generate() {
    return Builder.DEFAULT.next();
  }

  /**
   * Create a Builder to define the clock and random provider.
   */
  static Builder builder() {
    return new UuidV7Generator.Builder();
  }

  /**
   * Generate a Version 7 UUID.
   */
  UUID next();

  /**
   * Builder for UUIDv7 to define the clock and random provider.
   *
   * <pre>{@code
   *
   *   // specify the clock and random provider to use
   *   Clock sillyClock = Clock.fixed(Instant.EPOCH, ZoneId.of("UTC");
   *   Random myRandom = SecureRandom.getInstanceStrong();
   *
   *   UUIDv7 uuidV7 = UUIDv7.builder()
   *                 .clock(sillyClock)
   *                 .random(myRandom)
   *                 .build();
   *
   *   UUID uuid = uuidV7.generate();
   *
   * }</pre>
   */
  interface Builder {

    UUIDv7 DEFAULT = UUIDv7.builder().build();

    /**
     * Specify the random provider to use. If none is specified then {@link java.security.SecureRandom}
     * is used.
     */
    Builder random(Random random);

    /**
     * Specify the clock to use. If none is specified then {@link Clock#systemUTC()} is used.
     */
    Builder clock(Clock clock);

    /**
     * Build and return UUIDv7.
     */
    UUIDv7 build();
  }
}
