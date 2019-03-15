package apace.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    
    private static final int KEY_COUNT = 256;
    
    private enum KeyState {
        
        RELEASED,
        
        PRESSED,
        
        ONCE
    }
    
    private boolean[] currentKeys = null;
            
    private KeyState[] keys = null;
    
    private int buffer = -1;
    
    public KeyHandler() {
        currentKeys = new boolean[KEY_COUNT];
        
        keys = new KeyState[KEY_COUNT];
        
        for(int i = 0; i < KEY_COUNT; ++i) {
            keys[i] = KeyState.RELEASED;
        }
    }
    
    public synchronized void poll() {
        for(int i = 0; i < KEY_COUNT; ++i) {
            if(currentKeys[i]) {
                if(keys[i] == KeyState.RELEASED) {
                    keys[i] = KeyState.ONCE;
                    if(buffer < 0) {
                		buffer = i;
                	}
                } else {
                    keys[i] = KeyState.PRESSED;
                }
            }else{
                keys[i] = KeyState.RELEASED;
            }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent arg0) {
        int keyCode = arg0.getKeyCode();
        //System.out.println(keyCode);
        if(keyCode > 0 && keyCode < KEY_COUNT) {
            currentKeys[keyCode] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        int keyCode = arg0.getKeyCode();
        
        if(keyCode > 0 && keyCode < KEY_COUNT) {
            currentKeys[keyCode] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        
    }
    
    public boolean isKeyDown(int keyCode) {
        if(keys[keyCode] == KeyState.ONCE ||
           keys[keyCode] == KeyState.PRESSED) {
            return true;
        }
        return false;
    }
    
    public boolean isKeyDownBuffered(int keyCode) {
        if(keys[keyCode] == KeyState.ONCE ||
           keys[keyCode] == KeyState.PRESSED ||
           buffer == keyCode) {
        	buffer = -1;
        	return true;
        }
        return false;
    }
    
    /*public boolean isKeyDownOnce(int keyCode) {
        if(keys[keyCode] == KeyState.ONCE) {
            return true;
        }
        return false;
    }*/
    
    public boolean isKeyDownOnce(int keyCode) {
    	if(keys[keyCode] == KeyState.ONCE) {
            return true;
        }
        return false;
    }
    
    public boolean isKeyDownOnceBuffered(int keyCode) {
    	if(keys[keyCode] == KeyState.ONCE || buffer == keyCode) {
    		buffer = -1;
            return true;
        }
        return false;
    }

}
