package userinterface;

import file.DirectoryHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Controller {
    private DirectoryHandler directoryHandler;
    private String dir;
    private String word;
    private String newWord;
    private  View view ;
    public Controller(){
        view = new View();
    }
    public void menu() {
        View view = new View();
        char c = '0';

        System.out.println("1");
        do
                {

                    Scanner scanner = new Scanner(System.in);
                    c = scanner.next().charAt(0);

                    switch (c) {
                        case '1': {
                            takedata();

                            //move this to  take data()
                            if (!new File(dir).exists()) {
                                view.printMessege(6);
                                break ;
                            }
                            ExecutorService service = Executors.newCachedThreadPool();
                            ArrayList<File> res = new ArrayList<>();
                            directoryHandler = new DirectoryHandler(dir, word, newWord, service,res);
                            activateThreads(directoryHandler);



                        }

                        break;


                        case '2': {
                            break;
                        }
                    }
                }
        while (c != 'q');
    }

    public void takedata() {
        Scanner in = new Scanner(System.in);
        view.printMessege(1);
        newWord = in.nextLine();
        view.printMessege(2);
        word = in.nextLine();
        view.printMessege(0);
        System.out.println("dir");
        dir = in.nextLine();
    }

    public void activateThreads(DirectoryHandler directoryHandler) {

        try {
            ExecutorService service = Executors.newCachedThreadPool();
            //"E:\\Documents\\javaTest\\file1";
            Future<File> future =
                    service.submit(directoryHandler);

            service.shutdown();
            view.printMessege(3);
            if (service.awaitTermination(20, TimeUnit.SECONDS)) {
                for (int i = 0; i < directoryHandler.getMyfiles().size(); i++) {
                    System.out.println("path " + directoryHandler.getMyfiles().get(i).getAbsolutePath());
                }
                if(directoryHandler.getFilesModified().size()==0){
                    view.printMessege(5);
                }
                view.printMessege(4);
                for (int i = 0; i < directoryHandler.getFilesModified().size(); i++) {

                    System.out.println("path m" + directoryHandler.getFilesModified().get(i).getAbsolutePath());
                }

            } else {
                System.out.println("Cannot finish in the given timeout");
                // print cannot finish in timeout 1 sec
            }

        } catch (InterruptedException n) {
            n.printStackTrace();
        }
    }

}
