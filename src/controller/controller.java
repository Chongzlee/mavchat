package controller;

import model.model;
import view.chatpage;
import view.gui;

import java.awt.event.ActionListener;

public class controller {
    private model model;
    private gui gui;

    public controller(model model, gui gui) {
        this.model = model;
        this.gui = gui;
    }

    public controller() {

    }


    public String buttonClick(String firstname) {
        model md = new model();
         String result = md.checkUsername(firstname);
        return result;
    }

    public String chatClick(String firstname,String input) {
        model md = new model();
        String result = md.checkInput(firstname,input);
        return result;
    }






}
