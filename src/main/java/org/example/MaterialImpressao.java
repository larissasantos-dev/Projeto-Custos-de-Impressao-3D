package org.example;

public class MaterialImpressao {
    private String tipoMaterial;
    private double densidade;
    private double custoPorGrama;

    // Construtor
    public MaterialImpressao(String tipoMaterial, double densidade, double custoPorGrama){
        this.tipoMaterial = tipoMaterial;
        this.densidade = densidade;
        this.custoPorGrama = custoPorGrama;
    }

    // Getters
    public String getTipoMaterial(){
        return tipoMaterial;
    }

    public double getDensidade(){
        return densidade;
    }

    public double getCustoPorGrama(){
        return custoPorGrama;
    }
}