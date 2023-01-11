import java.io.*;
import java.util.Scanner;

public class Movie {
    String name;
    int year;
    int score;

    public Movie(String n, int y, int s) {
        name = n;
        year = y;
        score = s; // base score 1000
    }

    static void rankNewMovie() {
        // ...
    }

    static float probability(float rating1, float rating2) {
        return 1.0f / (1 + (float) (Math.pow(10, (rating1 - rating2) / 400)));
    }

    public void writeMovieToFile(File file, Movie newMovie) {
        try {
            FileWriter myWriter = new FileWriter(file, true);
            File movies = new File("movies.txt");
            Scanner scanner = new Scanner(movies);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine().toLowerCase();
                if(line.equals(newMovie.name + ", " + newMovie.year + ", " + newMovie.score)) {
                    System.out.println("Movie already exists.");
                    return;
                }
            }
            myWriter.write(newMovie.name + ", " + newMovie.year + ", " + newMovie.score + "\n");
            myWriter.close();
            System.out.println("Great one! Successfully added " + newMovie.name + ".");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

