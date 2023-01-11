import java.util.*;
import java.io.*;

public class ELO {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("ELO MOVIE RATING SYSTEM , by brian gunsel.");
        System.out.println("what would you like to do?\n1. enter new movie\n2. search for movie\n3. view your movies\n4. exit\n> ");
        Scanner input = new Scanner(System.in);
        int userInput = 0;
        while(userInput < 1 || userInput > 3) {
            userInput = input.nextInt();
            if(userInput == 1) { enterMovie(); }
            else if(userInput == 2) { searchMovie(); }
            else if(userInput == 3) { viewMovies(); }
            else if(userInput == 4) {
                System.out.println("exiting...");
                break;
            }
            else {
                System.out.println("not a valid choice\n1. enter new movie\n2. search for movie\n3. view your movies\n> ");
            }
        }
    }

    public static void enterMovie() {
        File movies = null;
        try {
            movies = new File("movies.txt");
            if (movies.createNewFile()) {
                System.out.println("File created: " + movies.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("enter new movie");
        Scanner input = new Scanner(System.in);
        System.out.println("enter name: ");
        String name = input.nextLine();
        System.out.println("enter year");
        int year = input.nextInt();

        Movie newMovie = new Movie(name, year, 1000);
        newMovie.writeMovieToFile(movies, newMovie);
    }

    public static void searchMovie() throws FileNotFoundException {
        System.out.println("search movie");
        Scanner input = new Scanner(System.in);
        System.out.println("enter movie title");
        String name = input.nextLine();

        File movies = new File("movies.txt");
        Scanner scanner = new Scanner(movies);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String title = line.split(",")[0];
            String score = line.split(",")[2];
            if(title.equals(name)) {
                System.out.println("Title: " + name.toUpperCase());
                System.out.println("Score: " + score);
                return;
            }
        }
        System.out.println("Movie not found");
    }

    public static void viewMovies() throws FileNotFoundException {
        File movies = new File("movies.txt");
        Scanner scanner = new Scanner(movies);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }
    }
}
