package apace.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener{
    
    public int mouseX;
    public int mouseY;
    
    public static final int PRIMARY = 0;
    public static final int WHEEL = 1;
    public static final int SECONDARY = 2;
    
    private boolean[] currentMouseButtons = new boolean[3];
    private MouseState[] mouseButtons = new MouseState[3];
    
    private enum MouseState {
        
        RELEASED,
        
        PRESSED,
        
        ONCE,
        
        DRAGGED
    }
    
    public MouseHandler() {
        for(int i = 0; i < 3; i++) {
            currentMouseButtons[i] = false;
            mouseButtons[i] = MouseState.RELEASED;
        }
    }
    
    public synchronized void poll() {
        for(int i = 0; i < 3; ++i) {
            if(currentMouseButtons[i]) {
                if(mouseButtons[i] == MouseState.RELEASED) {
                    mouseButtons[i] = MouseState.ONCE;
                }else{
                    mouseButtons[i] = MouseState.PRESSED;
                }
            }else{
                mouseButtons[i] = MouseState.RELEASED;
            }
        }
    }
    
    public boolean isButtonClicked(int id) {
        if(mouseButtons[id] == MouseState.PRESSED ||
           mouseButtons[id] == MouseState.ONCE) {
            return true;
        }
        return false;
    }
    
    public boolean isButtonClickedOnce(int id) {
        if(mouseButtons[id] == MouseState.ONCE) {
            return true;
        }
        return false;
    }
    
    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        currentMouseButtons[arg0.getButton() - 1] = true;
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        currentMouseButtons[arg0.getButton() - 1] = false;
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        mouseX = arg0.getX();
        mouseY = arg0.getY();
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        mouseX = arg0.getX();
        mouseY = arg0.getY();
    }

}
