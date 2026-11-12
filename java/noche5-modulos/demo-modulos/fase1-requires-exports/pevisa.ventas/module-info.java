module pevisa.ventas {
    // "transitive" propaga la lectura de pevisa.stock a quien haga requires pevisa.ventas
    // (por ejemplo pevisa.app), sin que ese modulo tenga que declarar pevisa.stock explicitamente.
    requires transitive pevisa.stock;

    exports pe.com.pevisa.ventas;
}
