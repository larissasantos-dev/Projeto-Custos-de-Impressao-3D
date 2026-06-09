package org.example;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

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

    private Label lbCompEnder;
    private Label lbCompK1;
    private Label lbCompBambu;

    private Label lbResImpressora;
    private Label lbResMaterial;
    private Label lbResPeso;
    private Label lbResLucro;
    private Label valCustoMaterial;
    private Label valCustoMaquina;
    private Label valCustoEnergia;
    private Label valCustoManuntecao;
    private Label valCustoMaoDeObra;

    private Label lbAvisoValidacao;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calculadora de Custos - Impressão 3D");

        // --- Carrega a imagem do icone ---
        try{
            Image icone = new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("/imagens/icone.png"))
            );
            stage.getIcons().add(icone);
        } catch (Exception e) {
            System.err.println("ícone não encontrado.");
        }

        HBox root = new HBox();
        root.getChildren().addAll(
                criarPainelEsquerdo(),
                criarPainelDireito()
        );

        Scene scene = new Scene(root, 900, 600);

        // --- CSS DO PROJETO ---
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

        lbPreco.getStyleClass().add("label-info-impressora");
        lbPotencia.getStyleClass().add("label-info-impressora");
        lbDescricao.getStyleClass().add("label-info-impressora");

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

        // --- Tratativa para limpar campos---
        TextField[] camposTXT = {txtPeso, txtTempo, txtHorasUsadas, txtMaoDeObra, txtMargem};
        for(TextField campo_txt : camposTXT){
            campo_txt.textProperty().addListener((obs, antigo, novo) ->{
                campo_txt.getStyleClass().remove("destaque-erro");
            });
        }

        comboImpressora.valueProperty().addListener((obs, antigo, novo) ->
                comboImpressora.getStyleClass().remove("destaque-erro"));

        comboDensidade.valueProperty().addListener((obs, antigo, novo) ->
                comboDensidade.getStyleClass().remove("destaque-erro"));

        comboMaterial.valueProperty().addListener((obs, antigo, novo) ->
                comboMaterial.getStyleClass().remove("destaque-erro"));

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
        btnCalcular.getStyleClass().add("botao");
        btnCalcular.setOnAction(e -> calcular());

        Button btnLimpar = new Button("Limpar");
        btnLimpar.setMaxWidth(Double.MAX_VALUE);
        btnLimpar.getStyleClass().add("botao");
        btnLimpar.setOnAction(e -> limpar());

        HBox botoesCL = new HBox(8, btnCalcular, btnLimpar);
        botoesCL.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnCalcular, Priority.ALWAYS);
        HBox.setHgrow(btnLimpar, Priority.ALWAYS);

        lbAvisoValidacao = new Label("");

        secao.getChildren().addAll(
                titulo,
                new Label("Arquivo .stl:"), txtArquivo,
                grid,
                lbAvisoValidacao,
                botoesCL
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
                criarSecaoComparacao(),
                criarSecaoResumoProjeto()
        );

        return painel;
    }

    // --- SEÇÃO 3: RESUMO DE CUSTOS ---
    private VBox criarSecaoResumo() {
        VBox secao = new VBox(8);
        secao.setPadding(new Insets(10));
        secao.getStyleClass().add("contorno-secoes");

        Label titulo = new Label("3. RESUMO DE CUSTOS");

        GridPane gridResumo = new GridPane();
        gridResumo.setHgap(20);
        gridResumo.setVgap(6);

        ColumnConstraints coBolinha = new ColumnConstraints(16);
        ColumnConstraints coDescricao = new ColumnConstraints();
        coDescricao.setHgrow(Priority.ALWAYS);
        ColumnConstraints coValor = new ColumnConstraints();
        coValor.setMinWidth(70);
        gridResumo.getColumnConstraints().addAll(coBolinha, coDescricao, coValor);

        lbCustoMaterial = new Label("Material (+ 10% falha):");
        lbCustoMaquina = new Label("Uso da máquina:");
        lbCustoEnergia = new Label("Energia elétrica:");
        lbCustoManuntecao = new Label("Manutenção:");
        lbCustoMaoDeObra = new Label("Mão de obra:");

        valCustoMaterial = new Label("R$ 0,00");
        valCustoMaquina = new Label("R$ 0,00");
        valCustoEnergia = new Label("R$ 0,00");
        valCustoManuntecao = new Label("R$ 0,00");
        valCustoMaoDeObra = new Label("R$ 0,00");

        valCustoMaterial.getStyleClass().add("val-material");
        valCustoMaquina.getStyleClass().add("val-maquina");
        valCustoEnergia.getStyleClass().add("val-energia");
        valCustoManuntecao.getStyleClass().add("val-manutencao");
        valCustoMaoDeObra.getStyleClass().add("val-mao-obra");

        // --- Bolinhas ---
        Circle b1 = new Circle(5, Color.web("#276749"));
        Circle b2 = new Circle(5, Color.web("#2b6cb0"));
        Circle b3 = new Circle(5, Color.web("#c05621"));
        Circle b4 = new Circle(5, Color.web("#553c9a"));
        Circle b5 = new Circle(5, Color.web("#b7791f"));

        for (Circle b : new Circle[]{b1, b2, b3, b4, b5}) {
            GridPane.setHalignment(b, HPos.CENTER);
        }

        // --- Alinha os valores na direita ---
        for (Label val : new Label[]{valCustoMaterial, valCustoMaquina, valCustoEnergia, valCustoManuntecao, valCustoMaoDeObra}) {
            GridPane.setHalignment(val, HPos.RIGHT);
        }

        gridResumo.addRow(0, b1, lbCustoMaterial,   valCustoMaterial);
        gridResumo.addRow(1, b2, lbCustoMaquina,    valCustoMaquina);
        gridResumo.addRow(2, b3, lbCustoEnergia,    valCustoEnergia);
        gridResumo.addRow(3, b4, lbCustoManuntecao, valCustoManuntecao);
        gridResumo.addRow(4, b5, lbCustoMaoDeObra,  valCustoMaoDeObra);

        // --- Custo total ---
        HBox boxTotal  = new HBox();
        boxTotal.setPadding(new Insets(8, 10, 8, 10));
        boxTotal.getStyleClass().add("box-total");
        Region espacoTotal = new Region();
        HBox.setHgrow(espacoTotal, Priority.ALWAYS);
        Label txtCustoTotal = new Label("CUSTO TOTAL:");
        txtCustoTotal.getStyleClass().add("label-total");
        lbCustoTotal = new Label("R$ 0,00");
        lbCustoTotal.getStyleClass().add("label-total");

        boxTotal.getChildren().addAll(txtCustoTotal, espacoTotal, lbCustoTotal);

        // --- valor de venda ---
        HBox boxVenda = new HBox();
        boxVenda.setPadding(new Insets(8, 10, 8, 10));
        boxVenda.getStyleClass().add("box-venda");
        Region espacoVenda = new Region();
        HBox.setHgrow(espacoVenda, Priority.ALWAYS);
        Label txtValorVenda = new Label("Valor de venda sugerido:");
        txtValorVenda.getStyleClass().add("label-venda");
        lbValorVenda = new Label("R$ 0,00");
        lbValorVenda.getStyleClass().add("label-venda");
        boxVenda.getChildren().addAll(txtValorVenda, espacoVenda, lbValorVenda);

        secao.getChildren().addAll(
                titulo,
                gridResumo,
                new Separator(),
                boxTotal,
                boxVenda
        );

        return secao;
    }

    // --- SEÇÃO 4: COMPARAÇÃO DE IMPRESSORAS ---
    private VBox criarSecaoComparacao() {
        VBox secao = new VBox(8);
        secao.setPadding(new Insets(10));
        secao.getStyleClass().add("contorno-secoes");

        Label titulo = new Label("4. COMPARAÇÃO DE IMPRESSORAS");

        GridPane gridComparacao = new GridPane();
        gridComparacao.setHgap(20);
        gridComparacao.setVgap(6);

        ColumnConstraints coInfo = new ColumnConstraints();
        coInfo.setHgrow(Priority.ALWAYS);
        ColumnConstraints coInfoPreco = new ColumnConstraints();
        coInfoPreco.setMinWidth(80);
        gridComparacao.getColumnConstraints().addAll(coInfo, coInfoPreco);

        Label nomeEnder = new Label("Ender 3:");
        Label nomeK1 = new Label("Creality K1:");
        Label nomeBambu = new Label("Bambu Lab A1:");

        lbCompEnder = new Label("- calcule primeiro");
        lbCompK1 = new Label("- calcule primeiro");
        lbCompBambu = new Label("- calcule primeiro");

        for (Label lbEstilo : new Label[]{lbCompEnder, lbCompK1, lbCompBambu}) {
            lbEstilo.getStyleClass().add("val-cinza");
        }

        // --- Alinha o valor de comparação a direita ---
        for(Label val : new Label[]{lbCompEnder, lbCompK1, lbCompBambu}){
            GridPane.setHalignment(val, HPos.RIGHT);
        }

        gridComparacao.addRow(0, nomeEnder, lbCompEnder);
        gridComparacao.addRow(1, nomeK1, lbCompK1);
        gridComparacao.addRow(2, nomeBambu, lbCompBambu);

        secao.getChildren().addAll(
                titulo,
                gridComparacao
        );

        return secao;
    }

    // Seção 5: Resumo do projeto
    private VBox criarSecaoResumoProjeto(){
        VBox secao = new VBox(8);
        secao.setPadding(new Insets(10));
        secao.getStyleClass().add("contorno-secoes");

        Label titulo = new Label("5. RESUMO DO PROJETO");

        GridPane gridResumoProjeto = new GridPane();
        gridResumoProjeto.setHgap(20);
        gridResumoProjeto.setVgap(5);

        ColumnConstraints coInfo = new ColumnConstraints();
        coInfo.setHgrow(Priority.ALWAYS);
        ColumnConstraints coInfoPreco = new ColumnConstraints();
        coInfoPreco.setMinWidth(120);
        gridResumoProjeto.getColumnConstraints().addAll(coInfo, coInfoPreco);

        lbResImpressora = new Label("- calcule primeiro");
        lbResMaterial = new Label("- calcule primeiro");
        lbResPeso = new Label("- calcule primeiro");
        lbResLucro = new Label("- calcule primeiro");

        for(Label val : new Label[]{lbResImpressora, lbResMaterial, lbResPeso, lbResLucro}){
            GridPane.setHalignment(val, HPos.RIGHT);
            val.getStyleClass().add("val-cinza");
        }

        gridResumoProjeto.addRow(0, new Label("Impressora:"), lbResImpressora);
        gridResumoProjeto.addRow(1, new Label("Material:"), lbResMaterial);
        gridResumoProjeto.addRow(2, new Label("Peso com perdas:"), lbResPeso);
        gridResumoProjeto.addRow(3, new Label("Lucro estimado:"), lbResLucro);

        secao.getChildren().addAll(
                titulo,
                gridResumoProjeto
        );

        return secao;
    }

    // --- Metodo de tratativa de erros ---
    private boolean validarCampos(){
        boolean valido = true;
        lbAvisoValidacao.setText("");
        lbAvisoValidacao.getStyleClass().remove("aviso-erro");

        TextField[] campos = {txtPeso, txtTempo, txtHorasUsadas, txtMaoDeObra, txtMargem};
        for(TextField campo : campos){
            if(campo.getText().trim().isEmpty()){
                campo.getStyleClass().remove("destaque-erro");
                campo.getStyleClass().add("destaque-erro");
                valido = false;
            }else{
                campo.getStyleClass().remove("destaque-erro");
            }
        }

        if (comboImpressora.getSelectionModel().isEmpty()) {
            lbAvisoValidacao.getStyleClass().add("aviso-erro");
            lbAvisoValidacao.setText("Selecione uma impressora.");
            return false;
        }

        if (comboDensidade.getSelectionModel().isEmpty()) {
            lbAvisoValidacao.getStyleClass().add("aviso-erro");
            lbAvisoValidacao.setText("Selecione uma densidade.");
            return false;
        }

        if (comboMaterial.getSelectionModel().isEmpty()) {
            lbAvisoValidacao.getStyleClass().add("aviso-erro");
            lbAvisoValidacao.setText("Selecione um material.");
            return false;
        }

        if (!valido) {
            lbAvisoValidacao.getStyleClass().add("aviso-erro");
            lbAvisoValidacao.setText("Preencha os campos destacados em vermelho.");
        }

        return valido;
    }

    // --- CARREGAR IMAGEM ---
    private void atualizarImagem(String nomeImpressora) {
        for(Impressora3D impressora : impressoras){
            if(impressora.getModeloImpressora().equals(nomeImpressora)) {
                var stream = getClass().getResourceAsStream(impressora.getImagemImpressora());

                if (stream != null) {
                    imageImpressora.setImage(new Image(stream));
                    imageImpressora.setVisible(true);
                }

                lbPreco.setText("Preço: R$ " + String.format("%.2f", impressora.getPrecoImpressora()));
                lbPotencia.setText("Potência: " + (int) impressora.getPotencia_WattsImpressora() + "W");
                lbDescricao.setText("Descrição: " + impressora.getDescricaoImpressora());
                break;
            }
        }
    }

    // --- Calculadora e dados da impressora ---
    private CalculadoraCusto calculadora = new CalculadoraCusto();

    // -- Cria um array que armzena os objetos das impressoras ---
    private Impressora3D[] impressoras ={
            new Impressora3D(
                    "Ender 3",
                    1500.00,
                    350,
                    "/imagens/ender3.jpg",
                    "Impressora FDM de entrada, ideal para projetos educacionais e protótipos;"),
            new Impressora3D(
                    "Creality K1",
                    3500.00,
                    500,
                    "/imagens/k1.jpg",
                    "Impressora rápida com impressão em alta velocidade."
            ),
            new Impressora3D(
                    "Bambu Lab A1",
                    4200.00,
                    400,
                    "/imagens/bambu_a1.jpg",
                    "Impressora premium com nivelamento automática e alta precisão."
            )
    };

    // --- Cria um array que armzena os objetos dos materiais ---
    private MaterialImpressao[] materiais = {
            new MaterialImpressao("PLA", 0.8, 0.08),
            new MaterialImpressao("PLA", 1.2, 0.12),
            new MaterialImpressao("PLA", 1.8, 0.18)
    };

    private void calcular(){
        lbAvisoValidacao.setText("");
        lbAvisoValidacao.getStyleClass().remove("aviso-erro");

        if (!validarCampos())
            return;

        try {
            String nomeArquivo = txtArquivo.getText();
            double peso = Double.parseDouble(txtPeso.getText());
            double horasImpressao = Double.parseDouble(txtTempo.getText());
            double horasUso = Double.parseDouble(txtHorasUsadas.getText());
            double maoDeObra = Double.parseDouble(txtMaoDeObra.getText());
            double margem = Double.parseDouble(txtMargem.getText());
            double custoKwh = 0.60;

            // --- Identifica a impressora selecionada ---
            int idxImpressora = comboImpressora.getSelectionModel().getSelectedIndex();
            Impressora3D impressoraSelecionada = impressoras[idxImpressora];

            // --- Identifica o material ---
            int idxMaterial = comboDensidade.getSelectionModel().getSelectedIndex();
            MaterialImpressao materialSelecionado = materiais[idxMaterial];

            // --- Cria o projeto ---
            ProjetoImpressao projeto = new ProjetoImpressao(
                    nomeArquivo, peso, horasImpressao, maoDeObra, margem
            );

            // --- Calcula cada custo ---
            double pesoComFalha = calculadora.taxaFalha(peso);
            double cusMaterial = calculadora.custoMaterial(materialSelecionado, pesoComFalha);
            double cusMaquina = calculadora.custoMaquinaHora(impressoraSelecionada, horasUso) * horasImpressao;
            double cusEnergia = calculadora.custoEnergia(impressoraSelecionada, projeto, custoKwh);
            double cusMaoDeObra = calculadora.custoMaoDeObra(projeto);
            double cusManutencao = calculadora.custoManutencao(horasImpressao); // 50 centavos à hora
            double cusTotal = calculadora.custoTotal(cusMaterial, cusMaquina, cusEnergia, cusManutencao, cusMaoDeObra);
            double valorVenda = calculadora.margemLucro(cusTotal, margem);

            // --- Atualiza os labels do painel direito ---
            valCustoMaterial.setText("R$ " + String.format("%.2f", cusMaterial));
            valCustoMaquina.setText("R$ " + String.format("%.2f", cusMaquina));
            valCustoEnergia.setText("R$ " + String.format("%.2f", cusEnergia));
            valCustoManuntecao.setText("R$ " + String.format("%.2f", cusManutencao));
            valCustoMaoDeObra.setText("R$ " + String.format("%.2f", cusMaoDeObra));

            lbCustoTotal.setText("R$ " + String.format("%.2f", cusTotal));
            lbValorVenda.setText("R$ " + String.format("%.2f", valorVenda));

            lbResImpressora.setText(impressoraSelecionada.getModeloImpressora());
            String[] densidades = {"Baixa", "Média", "Alta"};
            String densidadeTexto = densidades[idxMaterial];
            lbResMaterial.setText(materialSelecionado.getTipoMaterial() + " - " + densidadeTexto);
            lbResPeso.setText(String.format("%.0f g", calculadora.taxaFalha(peso)));
            lbResLucro.setText("R$ " + String.format("%.2f", valorVenda - cusTotal));

            for (Label val : new Label[]{lbResImpressora, lbResMaterial, lbResPeso, lbResLucro}) {
                val.getStyleClass().remove("val-cinza");
                val.getStyleClass().add("val-azul");
            }

            atualizarComparacao(peso, horasImpressao, horasUso, materialSelecionado, projeto, custoKwh);

        } catch (NumberFormatException e) {
            lbAvisoValidacao.setText("Digite apenas números nos campos numéricos.");
            lbAvisoValidacao.getStyleClass().add("aviso-erro");
        }

    }

    private void atualizarComparacao(double peso, double horasImpressao, double horasUso,
                                     MaterialImpressao material, ProjetoImpressao projeto, double custoKwh) {

        for (Impressora3D impressora : impressoras) {
            double pesoReal = calculadora.taxaFalha(peso);
            double mat = calculadora.custoMaterial(material, pesoReal);
            double maq = calculadora.custoMaquinaHora(impressora, horasUso) * horasImpressao;
            double en = calculadora.custoEnergia(impressora, projeto, custoKwh);
            double maoOB = calculadora.custoMaoDeObra(projeto);
            double man = calculadora.custoManutencao(horasImpressao);
            double total = calculadora.custoTotal(mat, maq, en, man, maoOB);

            String valor = "R$ " + String.format("%.2f", total);


            if (impressora.getModeloImpressora().equals("Ender 3")) {
                lbCompEnder.setText(valor);
                lbCompEnder.getStyleClass().remove("val-cinza");
                lbCompEnder.getStyleClass().add("val-azul");
            }

            if (impressora.getModeloImpressora().equals("Creality K1")) {
                lbCompK1.setText(valor);
                lbCompK1.getStyleClass().remove("val-cinza");
                lbCompK1.getStyleClass().add("val-azul");
            }

            if (impressora.getModeloImpressora().equals("Bambu Lab A1")) {
                lbCompBambu.setText(valor);
                lbCompBambu.getStyleClass().remove("val-cinza");
                lbCompBambu.getStyleClass().add("val-azul");
            }
        }
    }

    private void limpar () {
            // --- Limpa os textfields ---
            txtPeso.clear();
            txtHorasUsadas.clear();
            txtTempo.clear();
            txtMaoDeObra.clear();
            txtMargem.clear();

            // --- Limpa os comboBox ---
            comboImpressora.getSelectionModel().clearSelection();
            comboDensidade.getSelectionModel().clearSelection();
            comboMaterial.getSelectionModel().clearSelection();

            // --- Limpa a imagem e as infos da impressora ---
            imageImpressora.setImage(null);
            imageImpressora.setVisible(false);
            lbPreco.setText("Preço: -");
            lbPotencia.setText("Potência: -");
            lbDescricao.setText("Descrição: -");

            // --- Reseta os labels da seção 3 ---
            valCustoMaterial.setText("R$ 0,00");
            valCustoMaquina.setText("R$ 0,00");
            valCustoEnergia.setText("R$ 0,00");
            valCustoManuntecao.setText("R$ 0,00");
            valCustoMaoDeObra.setText("R$ 0,00");
            lbCustoTotal.setText("R$ 0,00");
            lbValorVenda.setText("R$ 0,00");

            // --- Reseta os labels de comparação da seção 4 ---
            lbCompEnder.setText("- calcule primeiro");
            lbCompK1.setText("- calcule primeiro");
            lbCompBambu.setText("- calcule primeiro");

            // --- Limpa os avisos e estilos de erro ---
            lbAvisoValidacao.setText("");
            lbAvisoValidacao.getStyleClass().remove("aviso-erro");

            // --- Reseta os labels da seção 5 ---
            lbResImpressora.setText("- calcule primeiro");
            lbResMaterial.setText("- calcule primeiro");
            lbResPeso.setText("- calcule primeiro");
            lbResLucro.setText("- calcule primeiro");

            for (Label lbEstilo : new Label[]{lbCompEnder, lbCompK1, lbCompBambu,
                    lbResImpressora, lbResMaterial, lbResPeso, lbResLucro}) {
                lbEstilo.getStyleClass().removeAll("val-azul", "val-cinza");
                lbEstilo.getStyleClass().add("val-cinza");
            }
        }


    public static void main(String[] args) {
        launch(args);
    }
}