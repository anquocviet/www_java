package fit.se.week03_lab_anquocviet_21080821.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description
 * @author: vie
 * @date: 30/9/24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductImageId implements Serializable {
   private long id;
   private long product;
}
