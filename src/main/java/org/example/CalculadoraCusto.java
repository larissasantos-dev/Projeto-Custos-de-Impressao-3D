package org.example; /* Todas as classes estão importadas desse pacote.
    Significa que elas se enxergam  automaticamente. Permitindo as passagens de objeto como parâmetro existentes nessa classe
    Esse conceito é chamado de "associação" em POO*/

public class CalculadoraCusto {

    public double custoMaquinaHora(Impressora3D objImpressora, double horas_uso){
        double horas_totais = 2 * 365 * horas_uso;
        return (objImpressora.getPrecoImpressora() / horas_totais); // retorna o que seria a variavel custo_maquina_por_hora
    }

    public double custoMaterial(MaterialImpressao objMaterialImpressora, double gramas_utilizadas){
        return gramas_utilizadas * objMaterialImpressora.getCustoPorGrama(); // retorna o que seria a variavel custo_material
    }

    public double custoEnergia(Impressora3D objImpressora, ProjetoImpressao objProjeto, double valor_kwh){
        return ((objImpressora.getPotencia_WattsImpressora() / 1000.0) * objProjeto.getHorasImpressao()) * valor_kwh; // retorna o que seria a variavel custo_energia
    }

    public double custoMaoDeObra(ProjetoImpressao objProjeto) {
        return objProjeto.getHorasImpressao() * objProjeto.getValorMaoDeObra(); // retorna o que seria a variavel custo_mao_obra
    }

    public double taxaFalha(double material_previsto){
        return material_previsto * 1.10; // retorna o que seria a variavel material_real
    }

    public double margemLucro(double custo_total){
        return custo_total * 1.30; // retorna o que seria a variavel valor_venda
    }

    public double custoTotal(double custo_material, double custo_maquina, double custo_energia, double custo_mao_manutencao, double custo_mao_obra){
        return custo_material + custo_maquina + custo_energia + custo_mao_manutencao + custo_mao_obra; // retorna o que seria a variavel custo_total_final
    }
}