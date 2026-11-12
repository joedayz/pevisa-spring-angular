package pe.com.pevisa.anotaciones;

@EnFuente @EnClase @EnEjecucion
public class Cuenta {

    @Auditable
    private double saldo;

    @Auditable
    public void depositar(double monto) {
        saldo += monto;
    }
}
