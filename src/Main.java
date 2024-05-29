import java.awt.Color;
import java.awt.Robot;
import java.awt.TextField;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;


public class Main extends Application {

    private static boolean extractColor = false;
    private static boolean appJustOpened = true;

    // public text field
    private static javafx.scene.control.TextField colorCodeText;

    // Application primary stage here
    @Override
    public void start(Stage primaryStage) {
        // Scene components
        Button extractButton = new Button("Start Color Extract");
        colorCodeText = new javafx.scene.control.TextField("#000000");
        colorCodeText.setEditable(false);

        // Event handlers
        extractButton.setOnAction(event -> {
            extractColor = !extractColor;
            extractButton.setText(extractColor ? "Extracting Color" : "Start Color Extract");
            if (extractColor) {
                startGetColorAtMouseLocation();
            } else {
                System.out.println("Color Extraction has been paused");
            }

        });

        // Layout
        // StackPane root = new StackPane();
        VBox root = new VBox(10);
        root.getChildren().add(extractButton);
        root.getChildren().add(colorCodeText);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Color Extract");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }

    public static void registerGlobalNativeHook() {
        // Register JnativeHook to the global screen for getting mouse coordinates
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    // Start procedure where mouse clicks generate color reports
    public static void startGetColorAtMouseLocation() {
        // Print OS information
        String osName = System.getProperty("os.name");
        System.out.println("Operating System: " + osName);
        if (appJustOpened){
            registerGlobalNativeHook();
            appJustOpened = false;
        }

        // Add mouse click event listener
        GlobalScreen.addNativeMouseListener(new NativeMouseListener() {

            // Print color at mouse location when mouse button is pressed
            @Override
            public void nativeMouseClicked(NativeMouseEvent e) {
                if (extractColor) {
                    printColorAtMouseLocation();
                    System.out.println("Mouse Clicked: " + e.getClickCount());
                } else {
                    System.out.println("Color Extraction is Paused");
                }
            }
        });
    }

    // Print the color at current mouse coordinates
    private static void printColorAtMouseLocation() {
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        int x = mouseLocation.x;
        int y = mouseLocation.y;
        try {
            Robot robot = new Robot();
            Color color = robot.getPixelColor(x, y);
            System.out.println("Color at [" + x + "," + y + "]: " + color);
            System.out.println("HexColor = #" + getHexString(color.getRed())
                                              + getHexString(color.getGreen())
                                              + getHexString(color.getBlue())
                                              );
            colorCodeText.setText("#" + getHexString(color.getRed())
                                      + getHexString(color.getGreen())
                                      + getHexString(color.getBlue()));
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    // https://www.tabnine.com/code/java/methods/java.awt.Robot/getPixelColor
    // Turns 255,255,255 color value into #RRGGBB hex format
    public static String getHexString(int rgb) {
        String hexString = Integer.toHexString(rgb);
        hexString = hexString.length() > 1 ? hexString : "0" + hexString;
        return hexString;
    }

    // Function that ends the terminal application when the javafx is closed
    @Override
    public void stop() {
        System.exit(0);
    }

}
