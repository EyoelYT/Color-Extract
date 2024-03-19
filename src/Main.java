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
       String osName = System.getProperty("os.name");
       System.out.println("Operating System: " + osName);

       try {
           GlobalScreen.registerNativeHook();
       } catch (NativeHookException ex) {
           System.err.println("There was a problem registering the native hook.");
           System.err.println(ex.getMessage());
           System.exit(1);
       }


       GlobalScreen.addNativeMouseListener(new NativeMouseListener() {
           @Override
           public void nativeMouseClicked(NativeMouseEvent e) {
               System.out.println("Mouse Clicked: " + e.getClickCount());
               printColorAtMouseLocation();
           }
       });
    }

    // TODO Specify the x and y coordinates of the pixel from mouse input
    // Pause mouse click (next mouse click doesn't do anything to the system @
    // system level) and wait for mouse click
    // When mouse clicked: get coordinates at mouse click => then resume normal
    // mouse clicks
    private static void printColorAtMouseLocation() {
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        int x = mouseLocation.x;
        int y = mouseLocation.y;

        try {
            Robot robot = new Robot();
            Color color = robot.getPixelColor(x, y);
            System.out.println("Color at [" + x + "," + y + "]: " + color);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
