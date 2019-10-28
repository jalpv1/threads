package userinterface;

import java.util.ArrayList;

public class View {
    enum Message {

        first {
            public void action() {
                System.out.println("Enter please a dirictory");
            }
        },
        second {
            public void action() {
                System.out.println("It is not a dirictory, try again");
            }
        };

        public void action() {
            System.out.println("");
        }


    }

    public void getMessage(String string) {

        if (string == "first") {
            //  Message.first;
        }
    }

    public void message(String string) {
        switch (string) {
            case "first": {

                System.out.println("Enter please a dirictory");
                break;

            }
            case " second": {

                System.out.println("It is not a dirictory, try again");
                break;

            }

        }
    }

    private ArrayList<String> messege;

    public View() {
        messege = new ArrayList<>();


        messege.add("Enter Directory");
        messege.add("Enter a new word");
        messege.add("Enter a word which you want to replace");
        messege.add("All files in dirictory");
        messege.add("Modified files");
        messege.add("No modified files. MayBe there are no files with the word in dirictory");

        messege.add("It is not a dirictory. Enter please data again");
    }

    public void printMessege(int i) {
        System.out.println(messege.get(i));
    }

}
