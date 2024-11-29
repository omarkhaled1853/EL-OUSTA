package searchpackage;

import classes.Technician;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.JaroWinklerDistance;
public class technicianSearch {

    public List<Technician>JaroSearch(String Query, List<Technician> searchSpace)
    {
        if (searchSpace==null)      //handle nullability
            return new ArrayList<>();

        String query=Query.toLowerCase();
        double threshold=0.6;
        JaroWinklerDistance jaroWinkler = new JaroWinklerDistance();
        return searchSpace.stream()
                .filter(t -> {
                    return jaroWinkler.apply(query, t.getFName()) >= threshold
                            ||jaroWinkler.apply(query, t.getLName() ) >= threshold
                            ||jaroWinkler.apply(query, t.getRate()) >= threshold
                            ||jaroWinkler.apply(query, t.getDomain()) >= threshold
                            ||jaroWinkler.apply(query, t.getGovernorate()) >= threshold
                            ||jaroWinkler.apply(query, t.getDistrict()) >= threshold;
                })
                .collect(Collectors.toList());
    }
//    public static List<Technician>LegnSearch(String Query, List<Technician> searchSpace)
//    {
//        String query=Query.toLowerCase();
//        List<Technician>Answer=new ArrayList<>();
//        LevenshteinDistance levenshtein = new LevenshteinDistance();
//        int threshold=2;
//
//        return searchSpace.stream()
//                .filter(t -> {
//                    return levenshtein.apply(query, t.getFName()) <= threshold ||
//                            levenshtein.apply(query, t.getLName() ) <= threshold ||
//                            levenshtein.apply(query, t.getRate()) <= threshold
//                            ||
//                            levenshtein.apply(query, t.getDomain()) <= threshold||
//                            levenshtein.apply(query, t.getGovernorate()) <= threshold||
//                            levenshtein.apply(query, t.getDistrict()) <= threshold;
//                })
//                .collect(Collectors.toList());
//    }

//    public static void main(String[] args) {
//
//        List<Technician> technicians = new ArrayList<>();
//        technicians.add(new Technician(1, "jdoe", "IT", "jdoe@gmail.com", "John", "Doe", new Date(1990, 1, 10), "1234567890", "Google", "New York", "Manhattan", new Date(2021, 6, 15), "15"));
//        technicians.add(new Technician(2, "asmith", "Electrical", "asmith@gmail.com", "Alice", "Smith", new Date(1988, 2, 20), "9876543210", "Facebook", "California", "Los Angeles", new Date(2022, 4, 10), "20"));
//        technicians.add(new Technician(3, "bwhite", "HVAC", "bwhite@gmail.com", "Bob", "White", new Date(1985, 4, 15), "4567891230", "Apple", "Texas", "Houston", new Date(2020, 8, 5), "25"));
//        technicians.add(new Technician(4, "cjackson", "Plumbing", "cjackson@gmail.com", "Carol", "Jackson", new Date(1992, 9, 25), "1231231230", "Google", "Florida", "Miami", new Date(2021, 12, 12), "18"));
//        technicians.add(new Technician(5, "jwilson", "IT", "jwilson@gmail.com", "James", "Wilson", new Date(1991, 5, 30), "6543210987", "Facebook", "Illinois", "Chicago", new Date(2022, 3, 8), "16"));
//
//        technicians.add(new Technician(6, "hclark", "Carpentry", "hclark@gmail.com", "Hannah", "Clark", new Date(1987, 3, 12), "3214567890", "Apple", "New York", "Brooklyn", new Date(2021, 7, 20), "22"));
//        technicians.add(new Technician(7, "pmiller", "HVAC", "pmiller@gmail.com", "Peter", "Miller", new Date(1990, 6, 18), "6547893210", "Google", "Texas", "Austin", new Date(2020, 5, 15), "24"));
//        technicians.add(new Technician(8, "dlopez", "Electrical", "dlopez@gmail.com", "Diana", "Lopez", new Date(1995, 11, 22), "7891234560", "Facebook", "Florida", "Orlando", new Date(2022, 1, 1), "19"));
//        technicians.add(new Technician(9, "smorgan", "Plumbing", "smorgan@gmail.com", "Sam", "Morgan", new Date(1986, 7, 25), "1239876540", "Apple", "California", "San Diego", new Date(2021, 11, 30), "17"));
//        technicians.add(new Technician(10, "lreyes", "Carpentry", "lreyes@gmail.com", "Laura", "Reyes", new Date(1989, 8, 15), "4561237890", "Google", "New York", "Queens", new Date(2020, 6, 25), "23"));
//
//        technicians.add(new Technician(11, "gwalker", "IT", "gwalker@gmail.com", "George", "Walker", new Date(1983, 10, 5), "7896541230", "Facebook", "Texas", "Dallas", new Date(2021, 9, 10), "15"));
//        technicians.add(new Technician(12, "asanders", "HVAC", "asanders@gmail.com", "Anna", "Sanders", new Date(1994, 1, 15), "1236547890", "Apple", "Florida", "Tampa", new Date(2022, 7, 14), "20"));
//        technicians.add(new Technician(13, "nmurphy", "Plumbing", "nmurphy@gmail.com", "Nathan", "Murphy", new Date(1991, 4, 10), "6541239870", "Google", "California", "San Francisco", new Date(2020, 10, 20), "18"));
//        technicians.add(new Technician(14, "wroberts", "Carpentry", "wroberts@gmail.com", "Wendy", "Roberts", new Date(1988, 9, 12), "9873216540", "Facebook", "New York", "Bronx", new Date(2021, 4, 5), "21"));
//        technicians.add(new Technician(15, "mjones", "Electrical", "mjones@gmail.com", "Michael", "Jones", new Date(1985, 12, 8), "3217896540", "Apple", "Illinois", "Springfield", new Date(2022, 5, 18), "14"));
//
//        technicians.add(new Technician(16, "tgreen", "IT", "tgreen@gmail.com", "Thomas", "Green", new Date(1989, 2, 20), "4563217890", "Google", "California", "Sacramento", new Date(2020, 3, 25), "26"));
//        technicians.add(new Technician(17, "akim", "Plumbing", "akim@gmail.com", "Alice", "Kim", new Date(1993, 5, 12), "1237894560", "Facebook", "New York", "Staten Island", new Date(2021, 8, 15), "22"));
//        technicians.add(new Technician(18, "jfoster", "Carpentry", "jfoster@gmail.com", "Jason", "Foster", new Date(1984, 6, 18), "7894561230", "Apple", "Texas", "Fort Worth", new Date(2022, 9, 2), "20"));
//        technicians.add(new Technician(19, "ebrown", "HVAC", "ebrown@gmail.com", "Ethan", "Brown", new Date(1990, 7, 5), "4567893210", "Google", "Florida", "Jacksonville", new Date(2020, 11, 28), "15"));
//        technicians.add(new Technician(20, "aharris", "Electrical", "aharris@gmail.com", "Ali", "Harris", new Date(1987, 3, 22), "3216549870", "Facebook", "Illinois", "Naperville", new Date(2021, 10,19),"19"));
//
//        System.out.println(JaroSearch("a",technicians));
//
//    }


}
