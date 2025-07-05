package uth.hn.cajero;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named("loginbean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static class Cliente {
        private String nombre;
        private String pin;
        private double saldo;
        private int intentos;

        public Cliente(String nombre, String pin, double saldo) {
            this.nombre = nombre;
            this.pin = pin;
            this.saldo = saldo;
            this.intentos = 0;
        }

        public String getNombre() { return nombre; }
        public String getPin() { return pin; }
        public double getSaldo() { return saldo; }
        public int getIntentos() { return intentos; }
        public void setSaldo(double saldo) { this.saldo = saldo; }
        public void setIntentos(int intentos) { this.intentos = intentos; }
    }

    private List<Cliente> clientes;
    private Cliente clienteActual;
    private String usuario;
    private String pin;
    private String mensaje;

    public LoginBean() {
        clientes = new ArrayList<>();
        clientes.add(new Cliente("ignacio", "1234", 5000.0));
        clientes.add(new Cliente("vanessa", "2345", 4000.0));
        clientes.add(new Cliente("rony", "3456", 3000.0));
        clientes.add(new Cliente("alejandro", "4567", 4500.0));
        clientes.add(new Cliente("gerson", "5678", 6000.0));
        clientes.add(new Cliente("marian", "6789", 3500.0));
    }

    public String login() {
        Optional<Cliente> match = clientes.stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(usuario))
                .findFirst();

        if (match.isEmpty()) {
            mensaje = "Usuario no encontrado.";
            return null;
        }

        Cliente c = match.get();
        if (c.getIntentos() >= 3) {
            mensaje = "Usuario bloqueado por intentos fallidos.";
            return null;
        }

        if (!c.getPin().equals(pin)) {
            c.setIntentos(c.getIntentos() + 1);
            mensaje = "PIN incorrecto. Intentos: " + c.getIntentos();
            return null;
        }

        c.setIntentos(0);
        clienteActual = c;
        mensaje = null;
        return "index.xhtml?faces-redirect=true";
    }



    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public String getMensaje() { return mensaje; }

    public Cliente getClienteActual() { return clienteActual; }
}
