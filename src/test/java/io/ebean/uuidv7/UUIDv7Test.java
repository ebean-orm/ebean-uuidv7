package io.ebean.uuidv7;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UUIDv7Test {

    @Test
    void build() throws NoSuchAlgorithmException {

        UUIDv7 uuiDv7 = UUIDv7.builder()
                .clock(Clock.fixed(Instant.EPOCH, ZoneId.of("UTC")))
                .random(SecureRandom.getInstanceStrong())
                .build();

        UUID u0 = uuiDv7.next();
        UUID u0p = UUID.fromString(u0.toString());

        assertThat(u0).isEqualTo(u0p);

        int count = 10_000_000;
        Set<UUID> set = new HashSet<>();
        for (int i = 0; i < count; i++) {
            if (!set.add(uuiDv7.next())) {
                fail("Hit a duplicate");
            }
        }
        assertThat(set).hasSize(count);
    }

    @Test
    void generate() {
        UUID u0 = UUIDv7.generate();
        UUID u0p = UUID.fromString(u0.toString());
        assertThat(u0).isEqualTo(u0p);
    }

    @Test
    void generate_noClashes() {
        int count = 10_000_000;
        Set<UUID> set = new HashSet<>();
        for (int i = 0; i < count; i++) {
            if (!set.add(UUIDv7.generate())) {
                fail("Hit a duplicate");
            }
        }
        assertThat(set).hasSize(count);
    }

    @Test
    void printSomeUUID() {
      for (int i = 0; i < 10; i++) {
        System.out.println(UUIDv7.generate());
      }
    }
}
