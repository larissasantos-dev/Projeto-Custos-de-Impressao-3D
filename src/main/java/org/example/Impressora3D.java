package org.example;

public class Impressora3D {
    private String modeloImpressora;
    private double precoImpressora;
    private double potencia_WattsImpressora;
    private String imagemImpressora;
    private String descricaoImpressora;

    // Construtor
    public Impressora3D(String modeloImpressora, double precoImpressora, double potencia_WattsImpressora,
                        String imagemImpressora, String descricaoImpressora){
        this.modeloImpressora = modeloImpressora;
        this.precoImpressora = precoImpressora;
        this.potencia_WattsImpressora = potencia_WattsImpressora;
        this.imagemImpressora = imagemImpressora;
        this.descricaoImpressora = descricaoImpressora;
    }

    // Getters
    public String getModeloImpressora(){
        return modeloImpressora;
    }

    public double getPrecoImpressora(){
        return precoImpressora;
    }

    public double getPotencia_WattsImpressora(){
        return potencia_WattsImpressora;
    }

    public String getImagemImpressora(){
        return  imagemImpressora;
    }

    public String getDescricaoImpressora(){
        return descricaoImpressora;
    }

}

