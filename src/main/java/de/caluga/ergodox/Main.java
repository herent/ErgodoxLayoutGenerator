package de.caluga.ergodox; /**
 * Created by stephan on 29.03.16.
 */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * TODO: Add Documentation here
 **/
public class Main extends Application {
    public final int pixelWidth = 40;
    public final int pixelHeight = 25;
    public final int pixelOffsetX=5;
    public final int pixelOffsetY=5;

    public final int offsetX=25;
    public final int offsetY=75;

    public int rightHalfOffset=400;
    private double scaleX =1.0;
    private double scaleY =1.0;

    private Label selectedGuiKey =null;
    private int selectedKeyIndexInLayout=0;

    private List<ErgodoxLayoutLayer> layers = new ArrayList<>();

    private Button setSourceDir;
    private Label sourceDirLabel;
    private Button createKeymap;
    private Button openBtn;
    private Button saveBtn;
    private int initialWindowWidth;
    private int initialWindowHeight;
    private double currentWindowHeight;
    private double currentWindowWidth;
    private ComboBox<String> macroCombo;

    private ComboBox<String> layerCombo;
    private Button createLayer;
    private Button deleteLayer;

    private ErgodoxLayoutLayer currentLayer;
    private Pane canvas;
    private File qmkSourceDir;
    private Label legendStandardKey;
    private Label legendLayerSwitch;
    private Label legendTempSwitch;
    private Label legendMacroCall;
    private Label legendModifierKey;
    private Label legendCombination;


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {


        StackPane root = new StackPane();

//        Canvas c = new Canvas(800, 600);
//        c.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            public void handle(MouseEvent event) {
//                double x = event.getX();
//                double y = event.getY();
//
//                boolean rightHalf=x>rightHalfOffset;
//                if (rightHalf){
//                    x-=rightHalfOffset;
//                }
//                int column=(int)((x-offsetX)/(pixelWidth +pixelOffset));
//                int row=(int)((y-offsetY)/(pixelHeight +pixelOffset));
//                System.out.println("Clicked! "+ x +"/"+ y+" == row: "+row+"  col: "+column);
//
//                int indexInLayout=0;
//                for (int i=0;i<row;i++){
//                    indexInLayout +=layers.getRowLength().get(i);
//                }
//                if (rightHalf) indexInLayout+=layers.keysOnHalf();
//                Key k=layers.getLayout().get(indexInLayout);
//                System.out.println("Got key; "+k.getWidth()+"x"+k.getHeight());
//
//            }
//        });
//        layout(c.getGraphicsCocntext2D());


        layers.add(new ErgodoxLayoutLayer("base"));
        currentLayer =layers.get(0); //base


        canvas = new Pane();
        canvas.setStyle("-fx-background-color: white;");
        initialWindowWidth = 800;
        initialWindowHeight = 500;
        this.currentWindowWidth=initialWindowWidth;
        this.currentWindowHeight=initialWindowHeight;
        rightHalfOffset= initialWindowWidth /2;
        canvas.setPrefSize(initialWindowWidth, initialWindowHeight);

        root.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
           // System.out.println("KEyEvent: "+event.getCode().impl_getCode()+" name: KC_"+event.getCode().getName());
            if (selectedGuiKey !=null){
                currentLayer.getLayout().get(selectedKeyIndexInLayout).setValue(event.getCode().getName().toUpperCase());
                unmarkKey();
                selectedGuiKey =null;
                layout(canvas);
            } else {
                if(event.getCode().getName().equals("O")){
                    openDialog(primaryStage);
                } else if (event.getCode().getName().equals("K")){
                    openKeymap(primaryStage);
                }
            }
        });
        int idx=0;
        for (Key k: currentLayer.getLayout()) {
//            Button btn=new Button("");
            if (k instanceof Key.NullKey) {
                idx++;
                continue;
            }
            final Label label=new Label("");
            label.backgroundProperty().setValue(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
            label.setTextAlignment(TextAlignment.CENTER);
            label.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(1))));
            DropShadow ds = new DropShadow();
            ds.setOffsetY(3.0f);
            ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
            label.setEffect(ds);
            final int i=idx;
            label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (selectedGuiKey!=null){
                    unmarkKey();
                    selectedGuiKey=null;
                    return;
                }
                Key k1 = currentLayer.getLayout().get(i);
                selectedKeyIndexInLayout=i;
                System.out.println("Key at "+i+" "+ k1.getWidth()+"x"+ k1.getHeight()+" value: "+k1.getValue());
                if (selectedGuiKey !=null){
                    unmarkKey();
                }
                label.setEffect(null);
                label.borderProperty().setValue(new Border(new BorderStroke(Color.GREEN,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2))));
                selectedGuiKey =label;
            });
            canvas.getChildren().add(label);
            idx++;
        }

        setSourceDir=new Button("Set qmk-SourceDir");
        setSourceDir.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openDialog(primaryStage);

            }
        });
        openBtn=new Button("open");
        openBtn.addEventHandler(ActionEvent.ACTION, event -> {
            openKeymap(primaryStage);
        });

        saveBtn=new Button("save");
        sourceDirLabel=new Label("...");
        createKeymap=new Button("create");

        macroCombo = new ComboBox<>();
        macroCombo.getItems().add("Smiley :-D");
        macroCombo.getItems().add("Smiley :-(");
        macroCombo.getItems().add("CTRL_SHIFT/#");

        layerCombo= new ComboBox<>();
        layerCombo.getItems().add("Base");
        layerCombo.getSelectionModel().select(0);
        layerCombo.addEventHandler(ActionEvent.ACTION, event -> {
            System.out.println("Selected!");
            if (layerCombo.getSelectionModel().getSelectedIndex()<0)return;
            currentLayer=layers.get(layerCombo.getSelectionModel().getSelectedIndex());
            layout(canvas);
        });

        deleteLayer=new Button("delete layer");
        deleteLayer.addEventHandler(ActionEvent.ACTION, event -> {
            if (layers.indexOf(currentLayer)==0){
                System.err.println("Cannot delete base layer");
            } else {
                layers.remove(currentLayer);
                layerCombo.getItems().remove(currentLayer.getName());
                currentLayer=layers.get(0);
                layerCombo.getSelectionModel().select(0);
                layout(canvas);
            }
        });
        createLayer=new Button("add layer");
        createLayer.addEventHandler(ActionEvent.ACTION, event -> {
            ErgodoxLayoutLayer l = new ErgodoxLayoutLayer("Layer " + layers.size());
            layers.add(l);
            currentLayer=l;
            layerCombo.getItems().add(l.getName());
            layerCombo.getSelectionModel().select(layerCombo.getItems().size()-1);
            layout(canvas);
        });

        legendStandardKey=new Label("std key");
        layoutLabel(legendStandardKey,Color.LIGHTGRAY);
        legendLayerSwitch=new Label("layer toggle");
        layoutLabel(legendLayerSwitch,Color.LIGHTCORAL);
        legendTempSwitch=new Label("key\ntemp layer");
        layoutLabel(legendTempSwitch,Color.LIGHTGREEN);
        legendMacroCall=new Label("Macro call");
        layoutLabel(legendMacroCall,Color.LIGHTYELLOW);
        legendModifierKey=new Label("Modifier");
        layoutLabel(legendModifierKey,Color.LIGHTBLUE);
        legendCombination=new Label("Combinations");
        layoutLabel(legendCombination, Color.LIGHTBLUE);

        canvas.getChildren().add(macroCombo);
        canvas.getChildren().add(setSourceDir);
        canvas.getChildren().add(openBtn);
        canvas.getChildren().add(saveBtn);
        canvas.getChildren().add(sourceDirLabel);
        canvas.getChildren().add(createKeymap);
        canvas.getChildren().add(createLayer);
        canvas.getChildren().add(deleteLayer);
        canvas.getChildren().add(layerCombo);
        canvas.getChildren().add(legendLayerSwitch);
        canvas.getChildren().add(legendMacroCall);
        canvas.getChildren().add(legendModifierKey);
        canvas.getChildren().add(legendStandardKey);
        canvas.getChildren().add(legendTempSwitch);
        canvas.getChildren().add(legendCombination);
        layout(canvas);

        root.getChildren().add(canvas);
//        Circle circle = new Circle(50,Color.BLUE);
//        circle.relocate(20, 20);
//        Rectangle rectangle = new Rectangle(100,100,Color.RED);
//        rectangle.relocate(70,70);
//        canvas.getChildren().addAll(circle,rectangle);




        Scene scene = new Scene(root, initialWindowWidth, initialWindowHeight);

        primaryStage.setTitle("ErgodoxLayoutGenerator");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("New width: "+newValue.toString());
            currentWindowWidth=newValue.doubleValue();
            if (currentWindowWidth> initialWindowWidth){
                scaleX =currentWindowWidth/(double) initialWindowWidth;
                rightHalfOffset= (int) (currentWindowWidth/2);
//                    System.out.println("Drawing with new scale of "+scaleX);
                layout(canvas);

            }
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("New height: "+newValue.toString());
            currentWindowHeight=newValue.doubleValue();
            if (currentWindowHeight> initialWindowHeight){
                scaleY  =currentWindowHeight/(double) initialWindowHeight;
                layout(canvas);

            }
        });
    }

    private void layoutLabel(Label l,Color c) {
        l.backgroundProperty().setValue(new Background(new BackgroundFill(c, new CornerRadii(5), Insets.EMPTY)));
        l.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(1))));
        l.setFont(Font.font(12));
        l.setAlignment(Pos.CENTER);
        l.setTextAlignment(TextAlignment.CENTER);
        l.setContentDisplay(ContentDisplay.CENTER);
    }

    private void openKeymap(Stage primaryStage) {
        KeymapParser parser=new KeymapParser();
        try {
            DirectoryChooser fc=new DirectoryChooser();
            if (qmkSourceDir==null) {
                fc.setInitialDirectory(new File(System.getProperty("user.home")));
            } else {
                fc.setInitialDirectory(new File(qmkSourceDir.getPath()+"/keyboard/ergodox_ez/keymaps"));
            }
            fc.setTitle("Choose ergodox-keymap directory");
            File selected=fc.showDialog(primaryStage);
            if (selected==null) return;

            Map<String, ErgodoxLayoutLayer> layouts= parser.parse(selected.getAbsolutePath()+"/keymap.c");
            layerCombo.getItems().clear();
            layers.clear();
            for (String k: layouts.keySet()){
                layerCombo.getItems().add(0,k);
                layers.add(0,layouts.get(k));
            }
            currentLayer=layers.get(0);
            layerCombo.getSelectionModel().select(0);
            layout(canvas);
        } catch (Exception e) {
            //TODO: Implement Handling
            throw new RuntimeException(e);
        }
    }

    private void openDialog(Stage primaryStage) {
        DirectoryChooser fc=new DirectoryChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.setTitle("Choose qmk-source dir");
        qmkSourceDir = fc.showDialog(primaryStage);
        if (qmkSourceDir ==null) return;
//                if (!qmkSourceDir.isDirectory()) {
//                    Alert alert=new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("No dir");
//                    alert.setContentText("the selected file is not a directory!");
//                    alert.show();
//                    return;
//                }
        sourceDirLabel.setText(qmkSourceDir.getPath());
    }

    private void unmarkKey() {
        selectedGuiKey.borderProperty().setValue(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(1))));
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        selectedGuiKey.setEffect(ds);
    }

    public void layout(Pane canvas) {
        int row = 0;
        int x = (int)(offsetX*scaleX);
        int y = (int)(offsetY*scaleY);
        int idx = 0;
        int xoff = 0;
        int canvasIdx=0;
        for (Key k : currentLayer.getLayout()) {
            idx++;
            if (k != null && !(k instanceof Key.NullKey)) {
                Label b= (Label) canvas.getChildren().get(canvasIdx++);
                String kval = k.getValue() != null ? k.getValue() : "";
                if (kval.endsWith("SFT") || kval.endsWith("CTL") || kval.endsWith("ALT")|| kval.endsWith("GUI")) {
                    b.backgroundProperty().setValue(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), Insets.EMPTY)));
                }  else if (kval.startsWith("LT(")) {
                    b.backgroundProperty().setValue(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(5), Insets.EMPTY)));
                } else if(kval.startsWith("M(")){
                    b.backgroundProperty().setValue(new Background(new BackgroundFill(Color.LIGHTYELLOW, new CornerRadii(5), Insets.EMPTY)));
                } else if(kval.startsWith("TG(")){
                    b.backgroundProperty().setValue(new Background(new BackgroundFill(Color.LIGHTCORAL, new CornerRadii(5), Insets.EMPTY)));
                 } else if(kval.contains("(")){
                    b.backgroundProperty().setValue(new Background(new BackgroundFill(Color.LIGHTCYAN, new CornerRadii(5), Insets.EMPTY)));

                } else {
                    b.backgroundProperty().setValue(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
                }
                b.setText(getKeyDisplayText(kval));
                b.setFont(Font.font(8));
                b.setAlignment(Pos.CENTER);
                b.setTextAlignment(TextAlignment.CENTER);
                b.setContentDisplay(ContentDisplay.CENTER);
                b.setMaxWidth(k.getWidth()*pixelWidth*scaleX);
                b.setPrefWidth(k.getWidth()*pixelWidth*scaleX);
                b.setMinWidth(k.getWidth()*pixelWidth*scaleX);

                b.setMaxHeight(k.getHeight()*pixelHeight*scaleY);
                b.setPrefHeight(k.getHeight()*pixelHeight*scaleY);
                b.setMinHeight(k.getHeight()*pixelHeight*scaleY);

                b.relocate(x+k.getxOffset()*scaleX,y+k.getyOffset()*scaleY);

//                g.setFill(Color.DARKGRAY);
//                g.fillRoundRect(x+4, y+4, k.getWidth() * pixelWidth, k.getHeight() * pixelHeight, 10, 10);
//                g.setFill(Color.GRAY);
//                g.fillRoundRect(x, y, k.getWidth() * pixelWidth, k.getHeight() * pixelHeight, 10, 10);
//                g.strokeRoundRect(x, y, k.getWidth() * pixelWidth, k.getHeight() * pixelHeight, 10, 10);
//                g.strokeText(idx + "/" + row, x, y + 15);
            }
            if (k instanceof Key.NullKey) idx--;
            if (idx >= currentLayer.getRowLength().get(row)) {

                row++;

                if (row >= currentLayer.getRowLength().size()) {
                    //switch to right half
                    y = (int)(offsetY*scaleY);
                    xoff = rightHalfOffset;
                    row = 0;
                    idx = 0;
                } else {
                    y += pixelHeight*scaleY +pixelOffsetY;
                    idx = 0;
                }
                x = xoff + (int)(offsetX*scaleX);
            } else {
                if (k != null) {
                    x += pixelWidth * k.getWidth()*scaleX+pixelOffsetX;
                } else {
                    x += pixelWidth +pixelOffsetX;
                }
            }

        }

        macroCombo.relocate(currentWindowWidth/2,currentWindowHeight-100);
        setSourceDir.relocate(25,currentWindowHeight-30);
        sourceDirLabel.relocate(25,currentWindowHeight-60);
        createKeymap.relocate(currentWindowWidth-100,currentWindowHeight-30);
        saveBtn.relocate(currentWindowWidth/2,currentWindowHeight-60);
        openBtn.relocate(currentWindowWidth/2,currentWindowHeight-30);

        layerCombo.relocate(5,20);
        createLayer.relocate(150,5);
        deleteLayer.relocate(150,40);

        legendLayerSwitch.relocate(20,currentWindowHeight-100);
        legendMacroCall.relocate(20,currentWindowHeight-120);
        legendModifierKey.relocate(20,currentWindowHeight-140);
        legendTempSwitch.relocate(currentWindowWidth-100,currentWindowHeight-100);
        legendCombination.relocate(currentWindowWidth-100,currentWindowHeight-120);
        legendStandardKey.relocate(currentWindowWidth-100,currentWindowHeight-140);

    }

    private String getKeyDisplayText(String kval) {
        if (kval.equals("KC_TRNS")) {
            return "";
        } else if (kval.startsWith("TG(")) {
            return kval.substring(3).replaceAll("\\)", "");
        } else if (kval.startsWith("ALL_T(")){
            return "Hyper\n"+kval.substring(6).replaceAll("\\)","");
         } else if (kval.startsWith("MEH_T(")){
            return "Meh\n"+kval.substring(6).replaceAll("\\)","");
        } else if (kval.startsWith("ALT_T(")){
            String s = kval.substring(6).replaceAll("\\)", "");
            return s+"\nALT";
        } else if (kval.startsWith("CTL_T(")){
            String s = kval.substring(6).replaceAll("\\)", "");
            return s+"\nCtrl";
        } else if (kval.startsWith("LT(")) {
            String s = kval.substring(3).replaceAll("\\)", "");
            String lt[] = s.split(",");
            String layer = lt[0];
            String key = lt[1];
            return key.substring(key.lastIndexOf('_') + 1) + "\n" + layer;
        } else if (kval.startsWith("LGUI(")){
            return "GUI+"+kval.substring(5).replaceAll("\\)","").substring(kval.lastIndexOf('_'));
        } else if (kval.startsWith("LALT(")){
            return "ALT+"+kval.substring(5).replaceAll("\\)","").substring(kval.lastIndexOf('_'));
        } else if (kval.startsWith("LSFT(")){
            return "Shift+"+kval.substring(5).replaceAll("\\)","").substring(kval.lastIndexOf('_'));
        } else if (kval.startsWith("M(") || !kval.contains("_") || kval.contains("(")) {
            return kval;
        } else {
            return kval.substring(kval.lastIndexOf('_') + 1);
        }
    }
}
