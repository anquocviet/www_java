package fit.se.week03_lab_anquocviet_21080821.enums;

import java.util.stream.Stream;

public enum EmployeeStatus {
   ACTIVE(1),
   IN_ACTIVE(0),
   TERMINATED(-1);

   private final int value;

   EmployeeStatus(int value) {
      this.value = value;
   }

   public int getValue() {
      return value;
   }

   public static EmployeeStatus fromValue(int value) {
      return Stream.of(EmployeeStatus.values())
                   .filter(status -> status.value == value)
                   .findFirst()
                   .orElseThrow(IllegalArgumentException::new);
   }
}