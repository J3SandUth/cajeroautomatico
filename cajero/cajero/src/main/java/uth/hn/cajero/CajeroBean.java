package uth.hn.cajero;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("cajerobean")
@SessionScoped
public class CajeroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private double monto;
    private String mensaje;

    public String realizarDeposito(LoginBean.Cliente cliente) {
        if (monto <= 0) {
            mensaje = "Monto inválido.";
            return null;
        }
        cliente.setSaldo(cliente.getSaldo() + monto);
        mensaje = "Depósito exitoso. Nuevo saldo: L. " + cliente.getSaldo();
        monto = 0;
        return "index.xhtml?faces-redirect=true";
    }

    public String realizarRetiro(LoginBean.Cliente cliente) {
        if (monto <= 0) {
            mensaje = "Monto inválido.";
            return null;
        }
        if (cliente.getSaldo() < monto) {
            mensaje = "Saldo insuficiente.";
            return null;
        }
        cliente.setSaldo(cliente.getSaldo() - monto);
        mensaje = "Retiro exitoso. Nuevo saldo: L. " + cliente.getSaldo();
        monto = 0;
        return "index.xhtml?faces-redirect=true";
    }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getMensaje() { return mensaje; }
}
