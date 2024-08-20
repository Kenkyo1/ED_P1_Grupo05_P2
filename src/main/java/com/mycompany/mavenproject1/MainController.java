/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mavenproject1;

import Clases.BinaryTree;
import Clases.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author rb122
 */
public class MainController {

    @FXML
    private TextField numQuestionsField;
    @FXML
    private Button loadFilesButton;
    @FXML
    private Button startGameButton;
    @FXML
    private Label questionLabel;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;

    private BinaryTree tree;
    private Node currentNode;
    private int maxQuestions;
    private List<String> answers;

    @FXML
    private void initialize() {
        tree = new BinaryTree();
        loadFilesButton.setOnAction(e -> {
            try {
                loadFiles();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        startGameButton.setOnAction(e -> startGame());
        yesButton.setOnAction(e -> hAnswer(true));
        noButton.setOnAction(e -> hAnswer(false));
    }

    private void loadFiles() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar Archivos");

        File questionsFile = fileChooser.showOpenDialog(null);
        File answersFile = fileChooser.showOpenDialog(null);

        if (questionsFile != null && answersFile != null) {
            tree.loadFiles(questionsFile, answersFile);
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION, "Archivos cargados exitosamente.");
            alert3.show();
        }
    }

    private void startGame() {
        String numQuestionsText = numQuestionsField.getText();
        try {
            maxQuestions = Integer.parseInt(numQuestionsText);

            // Validar el numero maximo de preguntas
            if (maxQuestions <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El numero maximo de preguntas debe ser mayor a 0.");
                alert.show();
                return;
            } else if (maxQuestions > 20) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El numero maximo de preguntas no puede ser mayor a 20.");
                alert.show();
                return;
            }

            currentNode = tree.getRoot();
            answers = new ArrayList<>();

            if (currentNode == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El arbol de decisiones no se ha cargado correctamente. Recuerda cargar los archivos necesarios.");
                alert.show();
                return;
            }

            askQuestion();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, ingrese un numero valido entre 1 y 20");
            alert.show();
        }
    }

    private void askQuestion() {
        if (currentNode == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se ha encontrado el animal.");
            alert.show();
            return;
        }

        // Verificar si el numero maximo de preguntas
        if (maxQuestions <= 0) {
            List<String> possibleAnimals = tree.findPossibleAnimals(tree.getRoot(), answers);
            Alert alert;
            if (possibleAnimals.isEmpty()) {
                alert = new Alert(Alert.AlertType.INFORMATION, "No se encontro ningun animal con esas caracteristicas.");
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION, "Los posibles animales son: " + String.join(", ", possibleAnimals));
            }
            alert.show();
            return;
        }

        if (currentNode.isAnimal()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "El animal es: " + currentNode.getAnimalName());
            alert.show();
            return;
        }

        questionLabel.setText(currentNode.getQuestion());
    }

    private void hAnswer(boolean yes) {
        if (currentNode == null) {
            return;
        }

        answers.add(yes ? "si" : "no");
        currentNode = yes ? currentNode.getYesBranch() : currentNode.getNoBranch();
        maxQuestions--;

        if (currentNode == null || maxQuestions <= 0) {
            List<String> possibleAnimals = tree.findPossibleAnimals(tree.getRoot(), answers);
            Alert alert;
            if (possibleAnimals.isEmpty()) {
                alert = new Alert(Alert.AlertType.INFORMATION, "No se encontro ningun animal con esas caracteristicas.");
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION, "Los posibles animales son: " + String.join(", ", possibleAnimals));
            }
            alert.show();
            return;
        }

        askQuestion();
        //saveHistorial(yes);
    }
    
     /*private void seeHistorial(MouseEvent event) {
        Alert al = new Alert(AlertType.INFORMATION, his);
        al.show();
    }
    
    private void saveHistorial(boolean yes){
        String ans = yes ? "sí" : "no";
        if(his == null) his = ans;
        his += ", "+ans;
    }
    */
   
}




