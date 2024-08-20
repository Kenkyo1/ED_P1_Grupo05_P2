/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rb122
 */
public class BinaryTree {

    private Node root;

    public BinaryTree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void loadFiles(File questionsFile, File answersFile) throws IOException {
        List<String> questions = readLines(questionsFile);
        List<String> answers = readLines(answersFile);

        root = buildTree(questions, answers);
    }

    private List<String> readLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        
        return lines;
    }

    private Node buildTree(List<String> questions, List<String> answers) {
        if (questions.isEmpty()) {
            throw new IllegalArgumentException("El archivo de preguntas esta vacio.");
        }

        // Inicializa el nodo raiz con la primera pregunta del archivo
        root = new Node(questions.get(0));
        List<String> questionList = questions.subList(1, questions.size());
        
        for (String answerLine : answers) {
            String[] parts = answerLine.split(" ");
            String animalName = parts[0];
            String[] animalTraits = new String[parts.length - 1];
            System.arraycopy(parts, 1, animalTraits, 0, parts.length - 1);

            Node currentNode = root;
            for (int j = 0; j < animalTraits.length; j++) {
                Node nextNode = new Node(j < questionList.size() ? questionList.get(j) : "");
                if (animalTraits[j].equals("si")) {
                    if (currentNode.getYesBranch() == null) {
                        currentNode.setYesBranch(nextNode);
                    }
                    currentNode = currentNode.getYesBranch();
                } else {
                    if (currentNode.getNoBranch() == null) {
                        currentNode.setNoBranch(nextNode);
                    }
                    currentNode = currentNode.getNoBranch();
                }
            }
            currentNode.setAnimal(true);
            currentNode.setAnimalName(animalName);
        }
        return root;
    }

    public List<String> findPossibleAnimals(Node node, List<String> answers) {
        List<String> possibleAnimals = new ArrayList<>();
        findPossibleAnimalsRecursively(node, answers, possibleAnimals);
        return possibleAnimals;
    }

    private void findPossibleAnimalsRecursively(Node node, List<String> answers, List<String> possibleAnimals) {
        if (node == null) {
            return;
        }

        if (node.isAnimal()) {
            possibleAnimals.add(node.getAnimalName());
            return;
        }

        if (answers.isEmpty()) {
            collectAllAnimals(node, possibleAnimals);
            return;
        }

        if ("si".equals(answers.get(0))) {
            findPossibleAnimalsRecursively(node.getYesBranch(), answers.size() > 1 ? answers.subList(1, answers.size()) : new ArrayList<>(), possibleAnimals);
        } else {
            findPossibleAnimalsRecursively(node.getNoBranch(), answers.size() > 1 ? answers.subList(1, answers.size()) : new ArrayList<>(), possibleAnimals);
        }
    }

    private void collectAllAnimals(Node node, List<String> possibleAnimals) {
        if (node == null) {
            return;
        }

        if (node.isAnimal()) {
            possibleAnimals.add(node.getAnimalName());
        }

        collectAllAnimals(node.getYesBranch(), possibleAnimals);
        collectAllAnimals(node.getNoBranch(), possibleAnimals);
    }
}




