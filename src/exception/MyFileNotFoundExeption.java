package exception;

import java.io.IOException;

public class MyFileNotFoundExeption extends IOException {
    private static final String message = "No files in this dir";
    public MyFileNotFoundExeption(){
        super(message);
    }


}
