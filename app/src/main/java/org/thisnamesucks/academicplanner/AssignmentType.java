package org.thisnamesucks.academicplanner;

/**
 * Created by jake on 11/16/15.  Updated by Carlos 11/21/15
 */
public enum AssignmentType {
    Homework,
    Project,
    Presentation,
    Exam,
    Essay,
    Quiz,
    Lab,
    FinalExam,
    Classwork,
    Journal,
    Report,
    Other;

    //Converts the enum into a string array that can be used in other methods
    public static String[] names() {
        AssignmentType[] states = values();
        String[] names = new String[states.length];

        for (int i = 0; i < states.length; i++) {
            names[i] = states[i].name();
        }

        return names;
    }
}