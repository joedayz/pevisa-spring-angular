module pevisa.app {
    // OJO: no declaramos "requires pevisa.stock". Aun asi podremos usar
    // pe.com.pevisa.stock.* porque pevisa.ventas lo expuso con "requires transitive".
    requires pevisa.ventas;
}
