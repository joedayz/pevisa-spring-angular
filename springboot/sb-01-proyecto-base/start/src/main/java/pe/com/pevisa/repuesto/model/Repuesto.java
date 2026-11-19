package pe.com.pevisa.repuesto.model;

import java.math.BigDecimal;

/**
 * TODO 1: Agregar las anotaciones de Lombok (@Data, @Builder, @NoArgsConstructor, @AllArgsConstructor)
 *         para eliminar getters, setters y constructores manuales.
 *
 * TODO 2: ¿Por qué usamos BigDecimal para el precio y no double?
 */
public class Repuesto {

    private Long id;
    private String codigo;
    private String descripcion;
    private String division;
    private BigDecimal precio;
    private Integer stock;

    // TODO 3: Eliminar estos getters/setters manuales una vez que agregues @Data
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
