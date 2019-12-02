package org.mmm.finals;

public final class ImmutableClass {

  private final String attribute1;
  private final Long attribute2;

  public ImmutableClass(String attribute1, Long attribute2) {
    this.attribute1 = attribute1;
    this.attribute2 = attribute2;
  }

  public String getAttribute1() {
    return attribute1;
  }

  public Long getAttribute2() {
    return attribute2;
  }
}
