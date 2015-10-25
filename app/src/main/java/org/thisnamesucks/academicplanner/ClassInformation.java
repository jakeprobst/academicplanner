package org.thisnamesucks.academicplanner;

/**
 * Created by jake on 10/24/15.
 */
public class ClassInformation {
    ClassInformation(String a, String b, int c, int d) {
        name = a;
        id = b;
        currentScore = c;
        totalScore = d;
    }

    // yeah yeah getters/setters WHATEVER

    public String name;
    public String id;
    public int currentScore;
    public int totalScore;
    // hours, room, teacher, etcetcetcetc
}
