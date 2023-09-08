import model.model;
import view.gui;
import controller.controller;

public class Main {

    public static void main(String[] args){
        model md = new model();
        gui gui = new gui();

        controller cnt = new controller(md,gui);


//
//        md.checkInput("tipango",8,"oho jing jing 888");

//        new gui();
    }

}
