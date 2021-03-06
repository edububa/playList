import java.util.*;
import java.io.*;

public class Prompt extends Thread {
    private ArrayList<List> listOfLists = new ArrayList<List>();
    private boolean exit;
    private Scanner sc;
    private String pipe;

    public Prompt() {
        this.exit = false;
        this.sc = new Scanner(System.in);
        this.pipe = "playList>";
    }

    public void oneArg(String arg) {
        switch(arg) {
        case "help":
            help();
            break;
        case "new":
            newList();
            break;
        case "add":
            add();
            break;
        case "remove":
            remove();
            break;
        case "delete":
            delete();
            break;
        case "play":
            play();
            break;
        case "list":
            list();
            break;
        case "show":
            show();
            break;
        case "exit":
            this.exit = true;
            break;
        default:
            System.out.println("Error: Type \"help\" if you are searching for some help");
        }
    }

    public void twoArgs(String arg0, String arg1) {
        switch(arg0) {
        case "new":
            newList(arg1);
            break;
        case "add":
            add(arg1);
            break;
        case "remove":
            remove(arg1);
            break;
        case "delete":
            delete(arg1);
	    break;
        case "play":
            play(arg1);
            break;
        case "show":
            show(arg1);
            break;
        default:
            System.out.println("Error: Type \"help\" if you are searching for some help");
        }
    }

    public void threeArgs(String arg0, String arg1, String arg2) {
        switch(arg0) {
        case "add":
            add(arg1, arg2);
            break;
        case "remove":
            remove(arg1, arg2);
            break;
        default:
            System.out.println("Error: Type \"help\" if you are searching for some help");
        }
    }

    public void run() {
        while (!exit) {
            System.out.print(this.pipe + " ");
            String line = sc.nextLine();
            ArrayList<String> args = argsArrList(line);
            switch(args.size()) {
            case 0:
                break;
            case 1:
                oneArg(args.get(0));
                break;
            case 2:
                twoArgs(args.get(0), args.get(1));
                break;
            case 3:
                threeArgs(args.get(0), args.get(1), args.get(2));
                break;
            default:
                System.out.println("Error: too many arguments");
            }
        }
    }

    private void help() {
        System.out.println();
        System.out.println("------------------------------------------------------------------");
        System.out.println("Description:");
        System.out.println("\tnew    -> creates a new list");
        System.out.println("\tadd    -> adds  a new song to a list");
        System.out.println("\tremove -> removes a song from a list");
        System.out.println("\tdelete -> removes a list");
        System.out.println("\tplay   -> plays a list");
        System.out.println("\tlist   -> shows all created lists");
        System.out.println("\tshow   -> shows contents of a list");
        System.out.println("\texit   -> closes playSong");
        System.out.println("Use:");
        System.out.println("\tnew    [arg1]");
        System.out.println("\tshow   [arg1]");
        System.out.println("\tdelete [arg1]");
        System.out.println("\tadd    [arg1 [arg2]]");
        System.out.println("\tremove [arg1 [arg2]]");
        System.out.println("\tlist");
        System.out.println("\texit");
        System.out.println("------------------------------------------------------------------");
        System.out.println();
    }

    private void list() {
        if (this.listOfLists.isEmpty()) {
            System.out.println("Error: No lists created");
            return;
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------");
        System.out.println("Lists:");
        for (List l : this.listOfLists) {
            System.out.println("\t" + l.getName());
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println();
    }

    private void newList() {
        boolean quit = false;
        while (!quit) {
            System.out.print(this.pipe + "new> ");
            String line = sc.nextLine();
            ArrayList<String> args = argsArrList(line);
            switch(args.size()) {
            case 1:
                if (args.get(0).equals("quit"))
                    quit = true;
                else
                    newList(args.get(0));
                break;
            default:
                System.out.println("Error: Too many arguments");
            }
        }
    }

    private void newList(String arg1) {
        if (!inListOfLists(arg1))
            this.listOfLists.add(new List(arg1));
        else
            System.out.println("Error: \"" + arg1 + "\" list already created");
    }

    private void add() {
        boolean quit = false;
        while(!quit) {
            System.out.print(this.pipe + "add> ");
            String line = sc.nextLine();
            ArrayList<String> args = argsArrList(line);
            switch(args.size()) {
            case 0:
                quit = true;
                break;
            case 1:
                if (args.get(0).equals("quit"))
                    quit = true;
                else
                    add(args.get(0));
                break;
            case 2:
                add(args.get(0), args.get(1));
                quit = true;
                break;
            default:
                System.out.println("Error: Too many arguments");
            }
        }
    }

    private void add(String arg1) {
        if (!inListOfLists(arg1)) {
            System.out.println("Error: list \"" + arg1 + "\" not created");
            return;
        }
        boolean quit = false;
        while (!quit) {
            System.out.print(this.pipe + "add>" + arg1 + "> ");
            String line = sc.nextLine();
            ArrayList<String> args = argsArrList(line);
            switch(args.size()) {
            case 0:
                quit = true;
                break;
            case 1:
                if (args.get(0).equals("quit"))
                    quit = true;
                else
                    add(arg1, args.get(0));
                break;
            default:
                System.out.println("Error: Too many arguments");
            }
        }
    }

    private void add(String arg1, String arg2) {
        if (!inListOfLists(arg1)) {
            System.out.println("Error: list \"" + arg1 + "\" not created");
            return;
        }
        File f = new File(arg2);
        if (!f.exists())
            System.out.println("Error: Song not found");
        else
            this.listOfLists.get(posInListOfList(arg1)).addSong(new Song(f));
    }

    private void remove() {
        boolean quit = false;
        while(!quit) {
            System.out.print(this.pipe + "remove> ");
            String line = sc.nextLine();
            ArrayList<String> args = argsArrList(line);
            switch(args.size()) {
            case 0:
                quit = true;
                break;
            case 1:
                if (args.get(0).equals("quit"))
                    quit = true;
                else
                    remove(args.get(0));
                break;
            case 2:
                remove(args.get(0), args.get(1));
                break;
            default:
                System.out.println("Error: Too many arguments");
            }
        }
    }

    private void remove(String arg1) {
        if (!inListOfLists(arg1)) {
            System.out.println("Error: list \"" + arg1 + "\" not created");
            return;
        }
        boolean quit = false;
        while (!quit) {
            System.out.print(this.pipe + "remove>" + arg1 + "> ");
            String line = sc.nextLine();
            ArrayList<String> args = argsArrList(line);
            switch(args.size()) {
            case 0:
                quit = true;
                break;
            case 1:
                if (args.get(0).equals("quit"))
                    quit = true;
                else
                    remove(arg1, args.get(0));
                break;
            default:
                System.out.println("Error: Too many arguments");
            }
        }
    }

    private void remove(String arg1, String arg2) {
        if (!inListOfLists(arg1)) {
            System.out.println("Error: list \"" + arg1 + "\" not created");
            return;
        }
        if (listOfLists.get(posInListOfList(arg1)).inList(arg1))
            this.listOfLists.get(posInListOfList(arg1)).removeSong(posInListOfList(arg2));
        else
            System.out.println("Error: no song to remove");
    }

    private void delete() {
        boolean quit = false;
        while(!quit) {
            System.out.print(this.pipe + "delete> ");
            String line = sc.nextLine();
            ArrayList<String> args = argsArrList(line);
            switch(args.size()) {
            case 0:
                quit = true;
                break;
            case 1:
                if (args.get(0).equals("quit"))
                    quit = true;
                else {
                    delete(args.get(0));
                    quit = true;
                }
                break;
            default:
                System.out.println("Error: Too many arguments");
            }
        }
    }

    private void delete(String arg1) {
        if (!inListOfLists(arg1)) {
            System.out.println("Error: list \"" + arg1 + "\" not created");
            return;
        }
        this.listOfLists.remove(posInListOfList(arg1));
    }

    private void play() {
        boolean quit = false;
        while(!quit) {
            System.out.print(this.pipe + "play> ");
            String line = sc.nextLine();
            ArrayList<String> args = argsArrList(line);
            switch(args.size()) {
            case 0:
                quit = true;
                break;
            case 1:
                if (args.get(0).equals("quit"))
                    quit = true;
                else
                    play(args.get(0));
                break;
            default:
                System.out.println("Error: Too many arguments");
            }
        }
    }

    private void play(String arg1) {
        if (!inListOfLists(arg1)) {
            System.out.println("Error: list \"" + arg1 + "\" not created");
            return;
        }
        this.listOfLists.get(posInListOfList(arg1)).playList();
    }

    private void show() {
        boolean quit = false;
        while(!quit) {
            System.out.print(this.pipe + "show> ");
            String line = sc.nextLine();
            ArrayList<String> args = argsArrList(line);
            switch(args.size()) {
            case 0:
                quit = true;
                break;
            case 1:
                if (args.get(0).equals("quit"))
                    quit = true;
                else
                    show(args.get(0));
                break;
            default:
                System.out.println("Error: Too many arguments");
            }
        }
    }

    private void show(String arg1) {
        if (!inListOfLists(arg1)) {
            System.out.println("Error: list \"" + arg1 + "\" not created");
            return;
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------");
        System.out.println(this.listOfLists.get(posInListOfList(arg1)).getName() + ": ");
        System.out.println(this.listOfLists.get(posInListOfList(arg1)).toString());
        System.out.println("------------------------------------------------------------------");
        System.out.println();
    }

    // Auxiliar functions
    private ArrayList<String> argsArrList(String line) {
        Scanner sc0 = new Scanner(line);
        ArrayList<String> result = new ArrayList<String>();
        while (sc0.hasNext())
            result.add(sc0.next());
        return result;
    }

    private int posInListOfList(String name) {
        int i = 0;
        while (i < this.listOfLists.size() && !this.listOfLists.get(i).getName().equals(name))
            i++;
        return i;
    }

    private boolean inListOfLists(String name) {
        int i = 0;
        while (i < this.listOfLists.size() && !this.listOfLists.get(i).getName().equals(name))
            i++;
        return i < this.listOfLists.size() || i < listOfLists.size() && this.listOfLists.get(i).getName().equals(name);
    }

}
