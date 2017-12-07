/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import javafx.stage.Stage;

/**
 *
 * @author omari
 */
public class StageHolder {
    
    private static Stage stage;
    
    public static Stage getStag(){
        return stage;
    }
    
    public static void setStage(Stage s){
    stage = s;
    }
    
    
}
