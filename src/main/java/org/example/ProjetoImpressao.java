package org.example;

public class ProjetoImpressao {
    private String nomeArquivo;
    private double pesoGramas;
    private double horasImpressao;
    private double valorMaoDeObra;
    private double margemLucro;

    // Construtor
    public ProjetoImpressao(String nomeArquivo, double pesoGramas, double horasImpressao,
                            double valorMaoDeObra, double margemLucro){
        this.nomeArquivo = nomeArquivo;
        this.pesoGramas = pesoGramas;
        this.horasImpressao = horasImpressao;
        this.valorMaoDeObra = valorMaoDeObra;
        this.margemLucro = margemLucro;
    }

    // Getters
    public String getNomeArquivo(){
        return nomeArquivo;
    }

    public double getPesoGramas(){
        return pesoGramas;
    }

    public double getHorasImpressao(){
        return horasImpressao;
    }

    public double getValorMaoDeObra(){
        return valorMaoDeObra;
    }

    public double getMargemLucro(){
        return margemLucro;
    }
}