## ebean-uuidv7

A Version 7 UUID generator

Refer: https://www.ietf.org/archive/id/draft-peabody-dispatch-new-uuid-format-04.html#v7

This is a derived work of the UUID v7 generator from https://github.com/cowtowncoder/java-uuid-generator
. This implementation removes the support for monotonic ordering (see https://github.com/cowtowncoder/java-uuid-generator/issues/69)
removing the associated lock and state associated with that (making this implementation stateless and lock free).

That is, UUID v7 only requires the first 48 bits to be continually increasing, just like the ULID specification.

Some implementations use an optional counter to provide additional monotonicity for identifiers created by the same generator
but monotonicity is not required. As such this implementation prefers not to support monotonicity and thus provide
a stateless and lock free V7 implementation.


## Using defaults for Clock and Random provider
```java

UUID uuid = UUIDv7.generate();

```

## Using Builder to specify Clock and Random provider
```java

Clock mySillyClock = Clock.fixed(Instant.EPOCH, ZoneId.of("UTC"));
Random myRandom = SecureRandom.getInstanceStrong();

UUIDv7 uuidV7 = UUIDv7.builder()
        .clock(mySillyClock)
        .random(myRandom)
        .build();

UUID uuid = uuidV7.next();

```

## Examples
```
018e0dd3-0b7f-707c-925f-fa706a66e90a
018e0dd3-0b7f-77ef-b588-eeea489712ec
018e0dd3-0b7f-76a5-8221-3a8efe40ab3f
018e0dd3-0b7f-7817-b455-500a669b5bfb
018e0dd3-0b7f-7018-9082-c5aeb131cdbe
018e0dd3-0b7f-7d70-9ba5-08f2d5ca66b0
018e0dd3-0b7f-7abc-8f19-247dae93c98a
018e0dd3-0b7f-7ade-b287-64c5f7dff1e4
018e0dd3-0b7f-7448-a77c-94cfc26f63a3
018e0dd3-0b7f-7b5a-a6d4-be23fe1a1d7b
```

## Maven dependency

```xml
<dependency>
  <groupId>io.ebean</groupId>
  <artifactId>ebean-uuidv7</artifactId>
  <version>1.0</version>
</dependency>
```
