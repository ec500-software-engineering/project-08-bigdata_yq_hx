package producer;
import java.util.Random;

public class createRandomName {
    private static String[] firstName = { "Alex", "Amy", "Emma", "Bob", "Bonnie", "Barney", "Carol", "Katherine",
            "Mark", "Ray", "laura", "Sherry", "Sarah", "Sam", "Rebeca", "Jane", "Stephen", "Kobe", "Lebron", "Kevin",
            "Rachel", "Ross", "Joey", "Chandler", "Christina", "Tiffany", "Victoria", "Amanda", "Julia", "Emily",
            "James", "Kris", "Andrew", "Tommy", "Patrick", "Peter", "George", "Henry", "Lawrence", "Justin", "Henry",
            "John", "Robert", "Paul", "Jason", "Allen", "Leo", "Joel", "Kyle", "Tony", "Jeff", "Alan", "Shawn","Charlie"
    };
    private static String[] lastName = { "James", "Bryant", "Brower", "Smith", "Robinson", "Anderson", "Brown", "Davis",
            "Walker", "Parker", "Cook", "Curry", "Price", "Barnes", "Gibson", "Crawford", "Alexander", "Mitchell",
            "Watson", "Cooper", "Rice", "Black", "Trump", "Fox", "Hudson", "Lenard",  "Pierce", "Griffin", "Thompson",
            "Durant", "Jordan", "Geller", "Russell", "Simmons", "Brooks", "Cooper", "Wright", "West", "Young", "Jackson"
    };

    public static Random rand = new Random();

    public static String generateName() {

        return firstName[rand.nextInt(firstName.length)] +" "+
                lastName[rand.nextInt(lastName.length)];
    }

}
