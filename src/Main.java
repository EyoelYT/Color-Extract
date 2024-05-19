import java.awt.Color;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

public class Main {


    public static void main(String args[]) {

        // Print OS information
        String osName = System.getProperty("os.name");
        System.out.println("Operating System: " + osName);

        // Register JnativeHook to the global screen for getting mouse coordinates
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        // Add mouse click event listener
        GlobalScreen.addNativeMouseListener(new NativeMouseListener() {

            // Print color at mouse location when mouse button is pressed
            @Override
            public void nativeMouseClicked(NativeMouseEvent e) {
                System.out.println("Mouse Clicked: " + e.getClickCount());
                printColorAtMouseLocation();
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
}
