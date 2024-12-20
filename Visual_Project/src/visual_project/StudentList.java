package visual_project;

public class StudentList {

    public static void lecture() {
        String[][] students = {
            {"01", "Ali"}, {"02", "Veli"}, {"03", "Ayşe"}, {"04", "Tuğçe"},
            {"05", "Büşra"}, {"06", "Zeliha"}, {"07", "Mustafa"}, {"08", "Dila"},
            {"09", "Hazal"}, {"10", "Ece"}, {"11", "Sena"}, {"12", "Zehra"},
            {"13", "Ceren"}, {"14", "Nisa"}, {"15", "Sevde"}, {"16", "Dila"},
            {"17", "Burak"}, {"18", "Berke"}, {"19", "Mehmet"}, {"20", "Murat"},
            {"21", "Recep"}
        };

        for (String[] student : students) {

            SqlConnect.insertMath(student[0], student[1], "P", "0", "0");

            SqlConnect.insertDifferantial(student[0], student[1], "P", "0", "0");

            SqlConnect.insertPrograming(student[0], student[1], "P", "0", "0");

            SqlConnect.insertNumeric(student[0], student[1], "P", "0", "0");

            SqlConnect.insertLinear(student[0], student[1], "P", "0", "0");
        }

    }

}
