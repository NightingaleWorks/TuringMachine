package hu.szlavik.szabolcs;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


/**
 * JavaFX application for simulating a single-tape deterministic Turing machine.
 * <p>
 * The application allows the user to:
 * </p>
 * <ul>
 *     <li>open a transition rule file,</li>
 *     <li>enter an input word,</li>
 *     <li>run the machine step by step or continuously, and</li>
 *     <li>inspect the current tape contents, head position, state, and step count.</li>
 * </ul>
 */
public class App extends Application {

    private TuringMachine machine;
    private HBox tapeView;
    private int stepCount;
    Label counter;
    Label state;
    Button stepButton;
    Button runButton;

    /**
     * Starts the JavaFX user interface.
     *
     * @param stage the primary application stage
     */
    @Override
    public void start(Stage stage) {

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Fájl"); // Ez lesz a File menü, a szöveget konstruktorban adhatjuk meg.
        Menu helpMenu = new Menu("Súgó"); // Ez lesz a help menü

        menuBar.getMenus().addAll(fileMenu, helpMenu);


        MenuItem openFileMenuItem = new MenuItem("Megnyitás");
        MenuItem exitFileMenuItem = new MenuItem("Kilépés");
        fileMenu.getItems().addAll(openFileMenuItem, exitFileMenuItem);

        MenuItem readme = new MenuItem("Súgó");
        helpMenu.getItems().add(readme);

        readme.setOnAction(e -> {
            Stage readme_stage = new Stage();
            readme_stage.setTitle("ReadMe");


            TextArea textArea = new TextArea("""
                        *** Turing gép szimulátor ***
                        Ez egy egyszerű Turing gép szimulátor, amely lehetővé teszi a felhasználók számára, hogy megértsék a Turing gépek működését.
                        A program egy szalagos Turing gépet valósít meg, amely egy végtelen hosszú szalagot használ adat tárolására és manipulálására.
                        A felhasználók megadhatnak egy bemeneti szót és egy átmenet fájlt, amely meghatározza a gép működését.
                        A program lépésenkénti végrehajtást és teljes futtatást is támogat, így a felhasználók láthatják, hogyan változik a szalag és az állapot a műveletek során.
                    
                        Az átmeneteket egyszerű szöveges fájlban adhatjuk meg a következő formátumban és szóközökkel elválasztva:
                        'a gép jelenlegi állapota'  'a leolvasott szimbólum' -> 'a szalagra írt szimbólum' 'az írófej mozgási iránya' 'a következő állapot'
                    
                        Például:                
                        q0 a -> _ R qa
                        a gép a q0 állapotban van, leolvas egy 'a' szimbólumot, akkor a gép egy '_' szimbólumot ír a szalagra, az írófej jobbra mozog, és a gép átmegy a qa állapotba.
                    
                        A következő modelleket valósíthatjuk meg:             
                        1. Két végállapot van: ACCEPT és REJECT. A gép akkor áll meg, ha eléri ezeket az állapotokat.
                        2. Csak egy elfogadó 'ACCEPT' állapot van, és a gép akkor áll meg, ha eléri ezt az állapotot. Minden más esetben végtelen ciklusban marad.
                        3. Egy megállás 'HALT' állapot van, csak ezt az állapotot kell megadnunk az átmenetfüggvényben
                    
                        A Fájl->Megnyitás menüpont segítségével töltsük be az átmeneteket tartalmazó .txt kiterjesztésű fájlt.
                        A vizsgált szó mezőbe írjuk be a vizsgálandó szót, majd nyomjuk meg az 'Indítás' gombot.
                        Ekkor betöltődik a Turing gépünk.
                    
                        A megjelenő új képernyőn léptethetjük a gépet, vagy a futtatást választva a gép addig fog lépkedni, amíg meg nem áll, vagy el nem éri a 200 lépést.
                        Ha ismét megnyomjuk a gombot, akkor lefut még 200 ciklus. Tovább próbálkozhatunk, de valószínűleg a gép már nem fog megállni.
                    
                        Példák:
                    
                        1. Az 'abba' szó felismerése, jegyzet 2.2. ábra
                    
                        q0 a -> _ R qa
                        q0 b -> _ R qb
                        qa a -> a R qa
                        qa b -> b R qa
                        qa _ -> _ L q2
                        q2 a -> _ L q3
                        q2 b -> b R REJECT
                        q3 a -> a L q3
                        q3 b -> b L q3
                        q3 _ -> _ R q0
                        qb a -> a R qb
                        qb b -> b R qb
                        qb _ -> _ L q4
                        q4 b -> _ L q3
                        q4 a -> a L REJECT
                        q0 _ -> _ R ACCEPT
                    
                    
                        2. '101101' 9 lépés után elfogadás
                    
                        q0 0 -> 0 R q1
                        q0 1 -> 1 R q2
                        q1 0 -> 0 R q1
                        q1 1 -> 1 R q2
                        q2 0 -> 0 R q3
                        q2 1 -> 1 R q3
                        q3 0 -> 0 R q3
                        q3 1 -> 1 R q4
                        q4 0 -> 1 L q0
                        q4 1 -> 0 L q0
                        q0 _ -> _ R ACCEPT
                        q1 _ -> _ R ACCEPT
                        q2 _ -> _ R ACCEPT
                        q3 _ -> _ R ACCEPT
                        q4 _ -> _ R ACCEPT
                    
                        3. '10' - 3. lépésben HALT 
                        q0 1 -> 0 R q1
                        q1 0 -> 1 L q0
                        q0 0 -> _ L HALT
                    
                        4. és egy végtelen ciklus '10' bemenetre
                        q0 1 -> 0 R q1
                        q0 0 -> 1 R q1
                        q1 0 -> 1 L q0
                        q1 1 -> 0 L q0
                    
                        Jó tanulást!    
                        Dr. Szlávik Szabolcs, 2026.
                    
                        MIT License
                    
                        Copyright (c) 2026 Dr. Szlávik Szabolcs
                    
                        Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
                        to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
                        and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
                    
                        The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
                    
                        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
                        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
                        WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.              
                    """);
            textArea.setEditable(false);

            Scene scene = new Scene(textArea, 500, 400);
            readme_stage.setScene(scene);
            readme_stage.show();
        });

        exitFileMenuItem.setOnAction(e -> stage.close());

        TextField textField = new TextField();
        textField.setPromptText("Írj szöveget...");

        Label inputLabel = new Label("A vizsgált szó:");
        inputLabel.setPadding(new Insets(10, 0, 0, 0));
        // Create a Button
        Button btnSubmit = new Button("Indítás");
        btnSubmit.setPadding(new Insets(10, 0, 0, 0));

        AtomicReference<File> selectedFile = new AtomicReference<>();

        openFileMenuItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Fájl megnyitása");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            File file = fileChooser.showOpenDialog(stage);
            selectedFile.set(file);

            if (file != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("A kiválasztott fájl");
                alert.setHeaderText("A kiválasztott fájl: " + file.getAbsolutePath());
                alert.showAndWait();
            }
        });

        BorderPane input = new BorderPane();
        Insets insets = new Insets(10, 10, 10, 10);
        //input.setPadding(insets);
        //input.setHgap(10);
        //input.setVgap(10);
        input.setTop(menuBar);
        HBox input_hbox = new HBox();
        input_hbox.setAlignment(Pos.CENTER);
        input_hbox.setSpacing(10);
        input.setCenter(input_hbox);
        input_hbox.getChildren().addAll(inputLabel, textField);
        input.setCenter(input_hbox);

        input.setCenter(input_hbox);
        HBox btnHBox = new HBox();
        btnHBox.setAlignment(Pos.CENTER);
        btnHBox.setPadding(new Insets(30, 30, 30, 30));
        btnSubmit.setPadding(insets);
        btnSubmit.setTooltip(new Tooltip("Az átmeneteket tartalmazó fájlt is nyisd meg!"));
        btnHBox.getChildren().add(btnSubmit);
        input.setBottom(btnHBox);


        btnSubmit.setOnAction(e -> {
            if (textField.getText().isEmpty()) {
                showError("Input error", "Please enter the input word.");
                return;
            }

            if (selectedFile.get() == null) {
                showError("File error", "Please open a transition file first.");
                return;
            }

            try {
                machine = new TuringMachine(
                        textField.getText(),
                        new TransitionTable(selectedFile.get().getAbsolutePath())
                );
            } catch (IOException ioException) {
                showError("File read error", "Could not read the transition file.");
                return;
            }

            stepCount = 0;

            tapeView = new HBox(5);
            tapeView.setAlignment(Pos.CENTER);

            counter = new Label("Lépésszám: " + stepCount);
            counter.setPadding(new Insets(10, 0, 0, 0));

            state = new Label("Állapot: " + machine.getState());
            state.setPadding(new Insets(10, 0, 0, 0));

            stepButton = new Button("Lépkedés");
            stepButton.setOnAction(eventStep -> {
                if (machine == null || machine.isHalted()) {
                    return;
                }

                machine.step();
                stepCount++;

                refreshTape();
                //machine.printTape();

                writeResult(stepButton, runButton);
            });

            runButton = new Button("Futtatás");
            runButton.setOnAction(eventRun -> {
                if (machine == null || machine.isHalted()) {
                    return;
                }

                for (int i = 0; i < 200 && !machine.isHalted(); i++) {
                    machine.step();
                    stepCount++;
                }

                refreshTape();
                writeResult(runButton, stepButton);
            });

            HBox information = new HBox(10, counter, state);
            information.setAlignment(Pos.CENTER);
            information.setPadding(new Insets(10, 10, 10, 10));

            HBox controls = new HBox(10, stepButton, runButton);
            controls.setAlignment(Pos.CENTER);
            controls.setPadding(new Insets(10, 10, 10, 10));
            controls.setSpacing(10);

            BorderPane root = new BorderPane();
            root.setTop(information);
            root.setCenter(tapeView);
            root.setBottom(controls);

            refreshTape();

            Scene tapeScene = new Scene(root, 400, 300);
            stage.setScene(tapeScene);
        });

        stage.setScene(new Scene(input, 600, 200));

        stage.setTitle("Egyszalagos determinisztikus Turing gép");
        stage.show();
    }

    /**
     * Updates the displayed step counter and machine state, and disables controls
     * if the machine has halted.
     *
     * @param runButton  the button used to start continuous execution
     * @param stepButton the button used to execute one step
     */
    private void writeResult(Button runButton, Button stepButton) {
        counter.setText("Lépés szám: " + stepCount);
        state.setText("Állapot: " + machine.getState());

        if (machine.isHalted()) {
            runButton.setDisable(true);
            stepButton.setDisable(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("A gép megállt");
            alert.setHeaderText("A gép megállt " + stepCount +
                    " lépés után.\n Az eredmény: " + machine.getState());
            alert.showAndWait();
        }
    }

    /**
     * Rebuilds the tape display to reflect the current tape contents and head position.
     */
    private void refreshTape() {
        tapeView.getChildren().clear();

        for (int i = 0; i < machine.getTapeSize(); i++) {
            char symbol = machine.getTapeSymbol(i);

            Label cell = new Label(" " + symbol + " ");
            cell.setStyle("-fx-font-size: 20px; -fx-border-color: black; -fx-padding: 10;");

            if (i == machine.getHeadPosition()) {
                cell.setStyle("-fx-font-size: 20px; -fx-border-color: red; -fx-padding: 10; -fx-background-color: yellow;");
            }

            tapeView.getChildren().add(cell);
        }
    }

    /**
     * Displays an error alert dialog with the specified title and message.
     *
     * @param title   the dialog title
     * @param message the message shown in the dialog header
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}