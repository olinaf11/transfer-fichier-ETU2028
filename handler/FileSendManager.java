package handler;

import java.io.File;

public class FileSendManager implements Runnable{
    String path;
    String[] fileinside;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getFileinside() {
        return fileinside;
    }

    public void setFileinside(String[] fileinside) {
        this.fileinside = fileinside;
    }

    public FileSendManager(String path){
        setPath(path);
    }
    @Override
    public void run() {
        while (true){
            File file = new File(getPath());
            setFileinside(file.list());
        }
    }
}
