package pe.com.pevisa.anotaciones.diseno;


@PoliticasNegocio({
    @PoliticaNegocio(nombre = "Devoluciones", paises = "PE", value = "4 semanas"),
    @PoliticaNegocio(paises = {"PE", "CL"}, value = "Despacho en 48 h")
})
public class TiendaExplicita {
}
