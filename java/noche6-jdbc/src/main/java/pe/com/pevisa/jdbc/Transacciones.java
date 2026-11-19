import module java.sql;

/// Diapositivas 11–12: autoCommit, commit, rollback, Savepoint y niveles de aislamiento.
static final String URL = "jdbc:h2:mem:banco;DB_CLOSE_DELAY=-1";   // la BD vive mientras dure la JVM

void main() throws SQLException {

    try (Connection con = DriverManager.getConnection(URL);
         Statement st = con.createStatement()) {

        st.executeUpdate("CREATE TABLE cuentas (id INTEGER PRIMARY KEY, titular VARCHAR(20), " +
                "saldo NUMERIC(8,2) CHECK (saldo >= 0))");

        st.executeUpdate("INSERT INTO cuentas VALUES (1, 'Ana', 100), (2, 'Luis', 50)");
        IO.println("autoCommit por defecto: " + con.getAutoCommit());

    }


    saldos("inicio");

    //1) Transaccion con Savepoint
    try (Connection con = DriverManager.getConnection(URL)) {
        con.setAutoCommit(false); //desactiva el autocommit

        try (Statement st = con.createStatement()) {
            st.executeUpdate("UPDATE cuentas SET saldo = saldo + 10 WHERE id = 1");
            Savepoint sp1 = con.setSavepoint("antes del bono");
            st.executeUpdate("UPDATE cuentas SET saldo = saldo + 1000 WHERE id = 2");
            IO.println("otra conexión ve el cambio sin confirmar? " + (saldoDesdeOtraConexion(2).intValue()!= 50));
            con.rollback(sp1); // deshace solo lo posterior a sp1, confirma el +10
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        }


    }

    saldos("tras savepoint + commit");


    transferir(1, 2, new BigDecimal("30"));
    transferir(2, 1, new BigDecimal("500"));

    saldos("otras transferencias");


}



void transferir(int origen , int destino, BigDecimal monto) throws SQLException {
    String sql = "UPDATE cuentas SET saldo = saldo + ? WHERE id = ?";
    try(Connection con = DriverManager.getConnection(URL)){

        con.setAutoCommit(false);

        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setBigDecimal(1, monto);
            ps.setInt(2, destino);
            ps.executeUpdate();                                      // primero el abono...
            ps.setBigDecimal(1, monto.negate());
            ps.setInt(2, origen);
            ps.executeUpdate();                                      // ...luego el cargo (puede fallar)
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            IO.println("transferencia de " + monto + " deshecha: " + e.getClass().getSimpleName() + " (SQLState " + e.getSQLState() + ")");
        }

    }
}



BigDecimal saldoDesdeOtraConexion(int id) throws SQLException {
    try (Connection otra = DriverManager.getConnection(URL);
         PreparedStatement ps = otra.prepareStatement("SELECT saldo FROM cuentas WHERE id = ?")) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getBigDecimal(1);
        }
    }
}



private void saldos(String momento) throws SQLException {
    try (var con = DriverManager.getConnection(URL);
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery("SELECT titular, saldo FROM cuentas ORDER BY id")) {
        var sb = new StringBuilder();

        while (rs.next()) sb.append(rs.getString(1)).append('=').append(rs.getBigDecimal(2)).append("  ");
        IO.println("%-30s %s".formatted(momento + ":", sb.toString().strip()));
    }
}