module pevisa.stock {
    // Paquete publico: cualquier modulo que haga "requires pevisa.stock" puede usarlo.
    exports pe.com.pevisa.stock;

    // Paquete interno: solo visible para pevisa.ventas (export calificado / "exports ... to").
    exports pe.com.pevisa.stock.interno to pevisa.ventas;
}
