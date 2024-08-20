/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author rb122
 */

public class Node {

    private String question;
    private Node yes;
    private Node no;
    private boolean isAnimal;
    private String name;

    public Node(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public Node getYesBranch() {
        return yes;
    }

    public void setYesBranch(Node yesBranch) {
        this.yes = yesBranch;
    }

    public Node getNoBranch() {
        return no;
    }

    public void setNoBranch(Node noBranch) {
        this.no = noBranch;
    }

    public boolean isAnimal() {
        return isAnimal;
    }

    public void setAnimal(boolean animal) {
        isAnimal = animal;
    }

    public String getAnimalName() {
        return name;
    }

    public void setAnimalName(String animalName) {
        this.name = animalName;
    }
}