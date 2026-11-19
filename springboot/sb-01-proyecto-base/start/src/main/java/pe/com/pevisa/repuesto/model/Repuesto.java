package pe.com.pevisa.repuesto.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Repuesto {

    private Long id;

    private String codigo;

    private String descripcion;

    private String division;

    private BigDecimal precio;

    private Integer stock;

}
