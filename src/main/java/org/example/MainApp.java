package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    // Entradas - Lado esquerdo
    private ComboBox<String> comboImpressora;
    private ImageView imageImpressora;
    private Label lbPreco, lbPotencia, lbDescricao;

    private TextField txtArquivo;
    private ComboBox<String> comboMaterial;
    private ComboBox<String> comboDensidade;
    private TextField txtPeso;
    private TextField txtHorasUsadas;
    private TextField txtTempo;
    private TextField txtMaoDeObra;
    private TextField txtMargem;

    // Saídas - Lado direito
    private Label lbCustoMaterial;
    private Label lbCustoMaquina;
    private Label lbCustoEnergia;
    private Label lbCustoManuntecao;
    private Label lbCustoMaoDeObra;
    private Label lbCustoTotal;
    private Label lbValorVenda;

    // Comparação entre impressoras
    private Label lbCompEnder, lbCompK1, lbCompBambu;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calculadora de Custos - Impressão 3D");

        HBox root = new HBox();
        root.getChildren().addAll(
                criarPainelEsquerdo(),
                criarPainelDireito()
        );

        Scene scene = new Scene(root, 900, 600);

        // CSS DO PROJETO
        try {
            String cssPath = Objects.requireNonNull(
                    getClass().getResource("/css/style.css")
            ).toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.err.println("style.css não encontrado.");
        }

        stage.setScene(scene);
        stage.show();
    }

    // ---  PAINEL ESQUERDO ---
    private VBox criarPainelEsquerdo() {
        VBox painel = new VBox(10);
        painel.setPadding(new Insets(14));
        painel.setPrefWidth(460);

        painel.getChildren().addAll(
                criarSecaoImpressora(),
                criarSecaoProjeto()
        );

        return painel;
    }

    // --- SEÇÃO 1: INFORMAÇÕES DA IMPRESSORA ---
    private VBox criarSecaoImpressora() {
        VBox secao = new VBox(8);
        secao.setPadding(new Insets(10));
        secao.getStyleClass().add("contorno-secoes");

        Label titulo = new Label("1. INFORMAÇÕES DA IMPRESSORA");

        comboImpressora = new ComboBox<>();
        comboImpressora.getItems().addAll("Ender 3", "Creality K1", "Bambu Lab A1");
        comboImpressora.setMaxWidth(Double.MAX_VALUE);
        comboImpressora.setOnAction(e -> atualizarImagem(comboImpressora.getValue()));

        imageImpressora = new ImageView();
        imageImpressora.setFitWidth(110);
        imageImpressora.setFitHeight(90);
        imageImpressora.setPreserveRatio(true);
        imageImpressora.setVisible(false);

        lbPreco      = new Label("Preço: -");
        lbPotencia   = new Label("Potência: -");
        lbDescricao  = new Label("Descrição: -");
        lbDescricao.setWrapText(true);

        VBox infoImpressora = new VBox(4, lbPreco, lbPotencia, lbDescricao);
        HBox blocoImagem    = new HBox(12, imageImpressora, infoImpressora);

        secao.getChildren().addAll(titulo, comboImpressora, blocoImagem);

        return secao;
    }

    // --- SEÇÃO 2: DADOS DO PROJETO ---
    private VBox criarSecaoProjeto() {
        VBox secao = new VBox(8);
        secao.setPadding(new Insets(10));
        secao.getStyleClass().add("contorno-secoes");

        Label titulo = new Label("2. DADOS DO PROJETO");

        txtArquivo = new TextField();
        txtArquivo.setPromptText("suporte-celular.stl");

        comboMaterial = new ComboBox<>();
        comboMaterial.getItems().addAll("PLA");
        comboMaterial.setMaxWidth(Double.MAX_VALUE);

        comboDensidade = new ComboBox<>();
        comboDensidade.getItems().addAll(
                "Baixa (R$0,08/g)",
                "Média (R$0,12/g)",
                "Alta (R$0,18/g)"
        );
        comboDensidade.setMaxWidth(Double.MAX_VALUE);

        txtPeso      = new TextField(); txtPeso.setPromptText("Peso (g)");
        txtHorasUsadas = new TextField(); txtHorasUsadas.setPromptText("Horas usadas por dia (h)");
        txtTempo     = new TextField(); txtTempo.setPromptText("Tempo impressão (h)");
        txtMaoDeObra = new TextField(); txtMaoDeObra.setPromptText("Mão de obra (R$/h)");
        txtMargem    = new TextField(); txtMargem.setPromptText("Margem de lucro (%)");

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(6);
        grid.addRow(0, new Label("Material:"),     comboMaterial);
        grid.addRow(1, new Label("Densidade:"),    comboDensidade);
        grid.addRow(2, new Label("Peso (g):"),     txtPeso);
        grid.addRow(3, new  Label("Horas usadas (h):"), txtHorasUsadas);
        grid.addRow(4, new Label("Tempo (h):"),    txtTempo);
        grid.addRow(5, new Label("Mão de obra:"),  txtMaoDeObra);
        grid.addRow(6, new Label("Margem (%):"),   txtMargem);

        Button btnCalcular = new Button("Calcular custos");
        btnCalcular.setMaxWidth(Double.MAX_VALUE);
        btnCalcular.getStyleClass().add("botao-calcular");
//        btnCalcular.setOnAction(e -> calcular());  conecta depois


        secao.getChildren().addAll(
                titulo,
                new Label("Arquivo .stl:"), txtArquivo,
                grid,
                btnCalcular
        );

        return secao;
    }

    // --- PAINEL DIREITO ---
    private VBox criarPainelDireito() {
        VBox painel = new VBox(10);
        painel.setPadding(new Insets(14));
        painel.setPrefWidth(440);

        painel.getChildren().addAll(
                criarSecaoResumo(),
                criarSecaoComparacao()
        );

        return painel;
    }

    // --- SEÇÃO 3: RESUMO DE CUSTOS ---
    private VBox criarSecaoResumo() {
        VBox secao = new VBox(8);
        secao.setPadding(new Insets(10));
        secao.getStyleClass().add("contorno-secoes");

        Label titulo = new Label("3. RESUMO DE CUSTOS");

        lbCustoMaterial   = new Label("Material (+ 10% falha):  R$ 0,00");
        lbCustoMaquina    = new Label("Uso da máquina:          R$ 0,00");
        lbCustoEnergia    = new Label("Energia elétrica:        R$ 0,00");
        lbCustoManuntecao = new Label("Manutenção:              R$ 0,00");
        lbCustoMaoDeObra  = new Label("Mão de obra:             R$ 0,00");
        lbCustoTotal      = new Label("CUSTO TOTAL:             R$ 0,00");
        lbValorVenda      = new Label("Valor de venda sugerido: R$ 0,00");

        secao.getChildren().addAll(
                titulo,
                lbCustoMaterial, lbCustoMaquina, lbCustoEnergia,
                lbCustoManuntecao, lbCustoMaoDeObra,
                new Separator(),
                lbCustoTotal,
                lbValorVenda
        );

        return secao;
    }

    // --- SEÇÃO 4: COMPARAÇÃO DE IMPRESSORAS ---
    private VBox criarSecaoComparacao() {
        VBox secao = new VBox(8);
        secao.setPadding(new Insets(10));
        secao.getStyleClass().add("contorno-secoes");

        Label titulo = new Label("4. COMPARAÇÃO DE IMPRESSORAS");

        lbCompEnder = new Label("Ender 3:      - calcule primeiro");
        lbCompK1    = new Label("Creality K1:  - calcule primeiro");
        lbCompBambu = new Label("Bambu Lab A1: - calcule primeiro");

        secao.getChildren().addAll(
                titulo,
                lbCompEnder,
                lbCompK1,
                lbCompBambu
        );

        return secao;
    }

    // --- CARREGAR IMAGEM ---
    private void atualizarImagem(String nomeImpressora) {
        String arquivo = switch (nomeImpressora) {
            case "Ender 3"      -> "/imagens/ender3.jpg";
            case "Creality K1"  -> "/imagens/k1.jpg";
            case "Bambu Lab A1" -> "/imagens/bambu_a1.jpg";
            default             -> null;
        };

        if (arquivo != null) {
            var stream = getClass().getResourceAsStream(arquivo);
            if (stream != null) {
                imageImpressora.setImage(new Image(stream));
                imageImpressora.setVisible(true);
            } else {
                System.err.println("Imagem não encontrada: " + arquivo);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}