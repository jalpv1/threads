package file;

import exception.MyFileNotFoundExeption;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

public class DirectoryHandler implements Callable<File> {
    private File directoryPath;
    private String word;
    private String newWord;
    private ExecutorService service;

    private ArrayList<File> myfiles;

    public ArrayList<File> getFilesModified() {
        return filesModified;
    }

    public void setFilesModified(ArrayList<File> filesModified) {
        this.filesModified = filesModified;
    }

    private ArrayList<File> filesModified;

    public ArrayList<File> getMyfiles() {
        return myfiles;
    }

    public void setMyfiles(ArrayList<File> myfiles) {
        this.myfiles = myfiles;
    }

    public File call() {

        //  System.out.println("Work with "+ directoryPath.getAbsolutePath());
        try {
            if (!directoryPath.isDirectory()) {
                searchAndReplace(directoryPath);

                myfiles.add(directoryPath);

                return directoryPath;
            } else {
                File[] files = converToList(directoryPath);
                for (File file : files) {
                    DirectoryHandler directoryHandler =
                            new DirectoryHandler(file.getAbsolutePath(), word, newWord, service, filesModified);
                    Future<File> future = service.submit(directoryHandler);
                    //resultFiles.add(future);
                    File f = future.get();

                    myfiles.add(f);

                }

            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return directoryPath;
    }

    public String etention(String string) {

        String[] segments = string.split(".");
        if(string.length()==0){
            return "0";
        }
        return segments[segments.length - 1];



    }


    public void searchAndReplace(File file) {
        boolean flag = false;
        String str = "";
        if (!file.getName().contains("cs")) {
            return;
        }
        try (Scanner scanner = new Scanner(new FileInputStream(file));
        ) {

            while (scanner.hasNext()) {
                String string = scanner.nextLine();
                if (string.contains(word)) {
                    string = string.replace(word, newWord);
                    flag = true;
                }

                str = str + string + "\r\n";
            }
            try (FileOutputStream fos = new FileOutputStream(file, false)) {
                fos.write(str.getBytes());
            }
            if (flag) {
                filesModified.add(file);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }


    }


    public static File[] converToList(File dir) {
        return dir.listFiles();
    }

    public DirectoryHandler(String directoryPath, String word, String newWord
            , ExecutorService service, ArrayList<File> mofifFiles) {
        this.directoryPath = new File(directoryPath);
        this.word = word;
        this.newWord = newWord;
        //  resultFiles = new ArrayList<>();
        myfiles = new ArrayList<>();
        this.service = service;
        filesModified = mofifFiles;


    }


    public File getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(File directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getNewWord() {
        return newWord;
    }

    public void setNewWord(String newWord) {
        this.newWord = newWord;
    }


}
