package io.ebean.uuidv7;

import java.security.SecureRandom;
import java.time.Clock;
import java.util.Random;
import java.util.UUID;

final class UuidV7Generator implements UUIDv7 {

  private final Random secureRandom;
  private final Clock clock;

  UuidV7Generator(Random random, Clock clock) {
    this.secureRandom = random;
    this.clock = clock;
  }

  @Override
  public UUID next() {
    final byte[] random = new byte[16];
    secureRandom.nextBytes(random);
    final long millis = clock.millis();
    return constructV7((millis << 16) | _toShort(random), _toLong(random));
  }

  static class Builder implements UUIDv7.Builder {

    private Random random = new SecureRandom();
    private Clock clock = Clock.systemUTC();

    @Override
    public UUIDv7.Builder random(Random random) {
      this.random = random;
      return this;
    }

    @Override
    public UUIDv7.Builder clock(Clock clock) {
      this.clock = clock;
      return this;
    }

    @Override
    public UUIDv7 build() {
      return new UuidV7Generator(random, clock);
    }
  }

  private static UUID constructV7(long l1, long l2) {
    // first, ensure type is ok
    l1 &= ~0xF000L; // remove high nibble of 6th byte
    l1 |= (7 << 12);
    // second, ensure variant is properly set too (8th byte; most-sig byte of second long)
    l2 = ((l2 << 2) >>> 2); // remove 2 MSB
    l2 |= (2L << 62); // set 2 MSB to '10'
    return new UUID(l1, l2);
  }

  private static long _toShort(byte[] buffer) {
    return ((buffer[0] & 0xFF) << 8) + (buffer[1] & 0xFF);
  }

  private static long _toLong(byte[] buffer) {
    long l1 = _toInt(buffer, 2);
    long l2 = _toInt(buffer, 6);
    return (l1 << 32) + ((l2 << 32) >>> 32);
  }

  private static long _toInt(byte[] buffer, int offset) {
    return (buffer[offset] << 24)
      + ((buffer[++offset] & 0xFF) << 16)
      + ((buffer[++offset] & 0xFF) << 8)
      + (buffer[++offset] & 0xFF);
  }

}
