package com.keremyurekli.minecraftservergui;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Arrays;

public class PositionableList extends ArrayList<Node> {


    public void bake(){
        System.out.println("asda");
        for(Node n : this){


            n.setTranslateY(this.indexOf(n)*30);

        }
    }

}
