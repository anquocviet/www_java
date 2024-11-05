package fit.se.week03_lab_anquocviet_21080821.dtos;

import java.time.LocalDate;

/**
 * @description
 * @author: vie
 * @date: 19/9/24
 */
public record StatisticDto(
      LocalDate date,
      LocalDate fromDate,
      LocalDate toDate,
      long employeeId
) {
}
