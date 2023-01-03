package com.keremyurekli.minecraftservergui;

import javafx.scene.Node;

import java.util.ArrayList;


public class PositionableList extends ArrayList<Node> {


    public void reposition(int startheight, int gapbetween, int labelgap) {


        for (int i = 0; i < this.size(); i++) {
            Node n = this.get(i);
            System.out.println(n.getTypeSelector());
            if (i == 0) {
                n.setTranslateY(startheight);
            } else {

                if (n.getTypeSelector().equals("Label")) {
                    n.setTranslateY(this.get(this.indexOf(n) - 1).getTranslateY() + gapbetween + labelgap);
                } else{
                    n.setTranslateY(this.get(this.indexOf(n) - 1).getTranslateY() + gapbetween);
                }

            }


        }


    }

}
