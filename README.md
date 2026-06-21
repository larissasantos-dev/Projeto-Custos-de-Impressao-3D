# 🖨️ Calculadora de Custos de Impressão 3D

Aplicação desktop em **Java + JavaFX** para calcular o custo real e o preço de venda sugerido de uma peça impressa em 3D, considerando material, energia, depreciação da máquina, manutenção e mão de obra.

## 📋 Sobre o projeto

A calculadora permite simular o custo de produção de uma peça com base em:

- **Impressora utilizada** (preço, potência e tempo de vida útil)
- **Material e densidade** (PLA com diferentes custos por grama)
- **Peso da peça** (com acréscimo automático de 10% para taxa de falha/desperdício)
- **Tempo de impressão e uso diário da máquina**
- **Custo de energia elétrica**
- **Mão de obra** e **margem de lucro** desejada

Com base nesses dados, o sistema calcula automaticamente:

- Custo de material (já com taxa de falha)
- Custo da máquina (depreciação por hora de uso)
- Custo de energia elétrica
- Custo de manutenção
- Custo de mão de obra
- **Custo total** da peça
- **Valor de venda sugerido**, aplicando a margem de lucro informada
- **Comparação de custos** entre as 3 impressoras cadastradas (Ender 3, Creality K1 e Bambu Lab A1)

## 🖥️ Telas

A interface é dividida em duas colunas:

- **Painel esquerdo:** seleção da impressora e preenchimento dos dados do projeto
- **Painel direito:** resumo dos custos, comparação entre impressoras e resumo final do projeto

## ⚙️ Tecnologias utilizadas

- **Java 25**
- **JavaFX 23** (interface gráfica)
- **Maven** (gerenciamento de dependências e build)

## 📁 Estrutura do projeto

```
src/main/java/org/example/
├── MainApp.java            # Interface gráfica (JavaFX) e fluxo de telas
├── CalculadoraCusto.java   # Regras de cálculo dos custos
├── Impressora3D.java       # Modelo de dados da impressora
├── MaterialImpressao.java  # Modelo de dados do material
└── ProjetoImpressao.java   # Modelo de dados do projeto de impressão

src/main/resources/
├── css/style.css           # Estilização da interface
└── imagens/                # Imagens das impressoras
```

## ▶️ Como executar

### Pré-requisitos

- JDK 25 instalado
- Maven instalado

### Passos

```bash
# Clone o repositório
git clone https://github.com/larissasantos-dev/Projeto-Custos-de-Impressao-3D.git

# Entre na pasta do projeto
cd Projeto-Custos-de-Impressao-3D

# Execute a aplicação
mvn javafx:run
```

## 🧮 Lógica de cálculo

| Custo | Fórmula resumida |
|---|---|
| Material | peso (com +10% de falha) × custo por grama |
| Máquina | (preço da impressora ÷ horas totais de vida útil) × horas de impressão |
| Energia | (potência em kW × horas de impressão) × tarifa de energia (R$/kWh) |
| Manutenção | R$ 0,50 × horas de impressão |
| Mão de obra | horas de impressão × valor da hora informado |
| **Total** | soma de todos os custos acima |
| **Venda sugerida** | custo total + margem de lucro (%) |

## 👥 Equipe

Projeto desenvolvido em dupla:

| Integrante | Responsabilidade |
|---|---|
| **Lana** | Modelagem das classes (regras de negócio, cálculos e entidades) |
| **Larissa** | Desenvolvimento da interface gráfica (telas, layout e estilização) |

## 📄 Licença

Projeto acadêmico, desenvolvido para fins de estudo de Programação Orientada a Objetos com Java.
