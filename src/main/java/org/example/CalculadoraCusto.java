package org.example; /* Todas as classes pertencem a esse pacote.
    Por isso se enxergam automaticamente, sem necessidade de import.
    Isso permite a passagem de objetos de outras classes como parâmetro.
    Esse conceito é chamado de "associação" em POO.*/

public class CalculadoraCusto {

    public double custoMaquinaHora(Impressora3D objImpressora, double horas_uso){
        double horas_totais = 2 * 365 * horas_uso;
        return (objImpressora.getPrecoImpressora() / horas_totais);
    }

    public double custoMaterial(MaterialImpressao objMaterialImpressora, double gramas_utilizadas){
        return gramas_utilizadas * objMaterialImpressora.getCustoPorGrama();
    }

    public double custoEnergia(Impressora3D objImpressora, ProjetoImpressao objProjeto, double valor_kwh){
        return ((objImpressora.getPotencia_WattsImpressora() / 1000.0) * objProjeto.getHorasImpressao()) * valor_kwh;
    }

    public double custoMaoDeObra(ProjetoImpressao objProjeto) {
        return objProjeto.getHorasImpressao() * objProjeto.getValorMaoDeObra();
    }

    public double taxaFalha(double material_previsto){
        return material_previsto * 1.10;
    }

    public double margemLucro(double custo_total, double margemPercent) {
        return custo_total * (1 + margemPercent / 100);
    }

    public double custoTotal(double custo_material, double custo_maquina, double custo_energia, double custo_mao_manutencao, double custo_mao_obra){
        return custo_material + custo_maquina + custo_energia + custo_mao_manutencao + custo_mao_obra;
    }

    public double custoManutencao(double horasImpressao) {
        return 0.50 * horasImpressao;
    }


}