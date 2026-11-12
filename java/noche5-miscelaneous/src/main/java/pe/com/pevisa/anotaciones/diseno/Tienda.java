package pe.com.pevisa.anotaciones.diseno;

@PoliticaNegocio(nombre = "Devoluciones", paises = "PE", value = "4 semanas")
@PoliticaNegocio(paises = {"PE", "CL"}, value = "Despacho en 48 h")
@Revision(fecha = "2026-10-19", autor = @Autor("JoeDayz"), etiquetas = {"ventas", "web"})
public class Tienda {
}
