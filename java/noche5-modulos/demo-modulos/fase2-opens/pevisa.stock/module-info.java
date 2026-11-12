module pevisa.stock {
    // "exports" permite ver la clase y llamar a sus miembros publicos (en compilacion y en runtime).
    exports pe.com.pevisa.stock.modelo;

    // Pero para que un framework use reflexion profunda (leer/forzar campos privados con
    // setAccessible(true)) necesita ademas un "opens", aunque el paquete ya este exportado.
    // Aqui lo abrimos SOLO para pevisa.framework (opens calificado).
    opens pe.com.pevisa.stock.modelo to pevisa.framework;
}
