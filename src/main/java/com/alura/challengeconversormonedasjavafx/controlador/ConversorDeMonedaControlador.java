package com.alura.challengeconversormonedasjavafx.controlador;

import com.alura.challengeconversormonedasjavafx.modelo.Moneda;
import com.alura.challengeconversormonedasjavafx.servicio.APIServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class ConversorDeMonedaControlador {
    @FXML
    private TextField txtValor;
    @FXML
    private ComboBox<Moneda> cmbMonedaBase;
    @FXML
    private ComboBox<Moneda> cmbMonedaDestino;
    @FXML
    private Label lblResultado;
    @FXML
    private ImageView imgMonedaBase;
    @FXML
    private ImageView imgMonedaDestino;

    private APIServicio apiServicio;

    public ConversorDeMonedaControlador() {
    }

    // Metodo para inicializar el servicio API después de cargar el FXML
    public void setApiServicio(String apiKey) {
        this.apiServicio = new APIServicio(apiKey);
    }

    @FXML
    public void initialize() {
        ObservableList<Moneda> monedas = FXCollections.observableArrayList(
                new Moneda("USD", "usd.png"),
                new Moneda("ARS", "ars.png"),
                new Moneda("BRL", "brl.png"),
                new Moneda("EUR", "eur.png"),
                new Moneda("GBP", "gbp.png")
        );

        cmbMonedaBase.setItems(monedas);
        cmbMonedaDestino.setItems(monedas);

        cmbMonedaBase.setCellFactory(createCellFactory());
        cmbMonedaBase.setButtonCell(createCellFactory().call(null));
        cmbMonedaDestino.setCellFactory(createCellFactory());
        cmbMonedaDestino.setButtonCell(createCellFactory().call(null));

        cmbMonedaBase.setOnAction(event -> updateImageView(cmbMonedaBase, imgMonedaBase));
        cmbMonedaDestino.setOnAction(event -> updateImageView(cmbMonedaDestino, imgMonedaDestino));
    }

    private Callback<ListView<Moneda>, ListCell<Moneda>> createCellFactory() {
        return new Callback<>() {
            @Override
            public ListCell<Moneda> call(ListView<Moneda> l) {
                return new ListCell<>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(Moneda item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item.nombre());
//                            imageView.setImage(new Image(getClass().getResourceAsStream("/com/alura/challengeconversormonedasjavafx/assets" + item.imagen())));
//                            setGraphic(imageView);
                            setGraphic(null);
                        }
                    }
                };
            }
        };
    }

    private void updateImageView(ComboBox<Moneda> comboBox, ImageView imageView) {
        Moneda selectedMoneda = comboBox.getValue();
        if (selectedMoneda != null) {
            imageView.setImage(new Image(getClass().getResourceAsStream("/com/alura/challengeconversormonedasjavafx/assets/" + selectedMoneda.imagen())));
        }
    }

    public void convertir() {
        try {
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
            Moneda monedaBase = cmbMonedaBase.getValue();
            Moneda monedaDestino = cmbMonedaDestino.getValue();

            if (monedaBase != null && monedaDestino != null && !monedaBase.equals(monedaDestino)) {
                // Llama al servicio para obtener la tasa de cambio
                double tasaCambio = apiServicio.consultarTasaDeCambio(monedaBase.nombre(), monedaDestino.nombre());
                double resultado = valor * tasaCambio;

                lblResultado.setText(String.format("El valor convertido es: %.2f %s", resultado, monedaDestino.nombre()));
            } else {
                lblResultado.setText("Seleccione monedas válidas.");
            }
        } catch (NumberFormatException e) {
            lblResultado.setText("Por favor, ingrese un número válido.");
        } catch (Exception e) {
            lblResultado.setText("Error al convertir la moneda.");
        }
    }
}
