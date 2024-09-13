package edu.coder.FacturacionSegundaEntregaMolina.service;

public class ComprobanteResponse {
    private String mensaje;
    private String fecha;
    private double total;
    private int cantidadTotal;

    public ComprobanteResponse(String mensaje, int cantidadTotal, String fecha, double total) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.total = total;
        this.cantidadTotal = cantidadTotal;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
