package visual_project;

public class StudentList {

    public static void lecture() {
        String[][] students1 = {
            {"01", "Ali"}, {"02", "Veli"}, {"03", "Ayşe"}, {"04", "Tuğçe"},
            {"05", "Büşra"}, {"06", "Zeliha"}, {"07", "Mustafa"}, {"08", "Dila"},
            {"09", "Hazal"}, {"10", "Ece"}, {"11", "Sena"}, {"12", "Zehra"},
            {"13", "Ceren"}, {"14", "Nisa"}, {"15", "Sevde"}, {"16", "Dila"},
            {"17", "Burak"}, {"18", "Berke"}, {"19", "Mehmet"}, {"20", "Murat"},
            {"21", "Recep"}
        };
        String[][] students2 = {
            {"101", "Ali"}, {"102", "Veli"}, {"103", "Ayşe"}, {"104", "Tuğçe"},
            {"105", "Büşra"}, {"106", "Zeliha"}, {"107", "Mustafa"}, {"108", "Dila"},
            {"109", "Hazal"}, {"110", "Ece"}, {"111", "Sena"}, {"112", "Zehra"},
            {"113", "Ceren"}, {"114", "Nisa"}, {"115", "Sevde"}, {"116", "Dila"},
            {"117", "Burak"}, {"118", "Berke"}, {"119", "Mehmet"}, {"120", "Murat"},
            {"121", "Recep"}
        };
        String[][] students3 = {
            {"201", "Ali"}, {"202", "Veli"}, {"203", "Ayşe"}, {"204", "Tuğçe"},
            {"205", "Büşra"}, {"206", "Zeliha"}, {"207", "Mustafa"}, {"208", "Dila"},
            {"209", "Hazal"}, {"210", "Ece"}, {"211", "Sena"}, {"212", "Zehra"},
            {"213", "Ceren"}, {"214", "Nisa"}, {"215", "Sevde"}, {"216", "Dila"},
            {"217", "Burak"}, {"218", "Berke"}, {"219", "Mehmet"}, {"220", "Murat"},
            {"221", "Recep"}
        };
        String[][] students4 = {
            {"301", "Ali"}, {"302", "Veli"}, {"303", "Ayşe"}, {"304", "Tuğçe"},
            {"305", "Büşra"}, {"306", "Zeliha"}, {"307", "Mustafa"}, {"308", "Dila"},
            {"309", "Hazal"}, {"310", "Ece"}, {"311", "Sena"}, {"312", "Zehra"},
            {"313", "Ceren"}, {"314", "Nisa"}, {"315", "Sevde"}, {"316", "Dila"},
            {"317", "Burak"}, {"318", "Berke"}, {"319", "Mehmet"}, {"320", "Murat"},
            {"321", "Recep"}
        };
        String[][] students5 = {
            {"401", "Ali"}, {"402", "Veli"}, {"403", "Ayşe"}, {"404", "Tuğçe"},
            {"405", "Büşra"}, {"406", "Zeliha"}, {"407", "Mustafa"}, {"408", "Dila"},
            {"409", "Hazal"}, {"410", "Ece"}, {"411", "Sena"}, {"412", "Zehra"},
            {"413", "Ceren"}, {"414", "Nisa"}, {"415", "Sevde"}, {"416", "Dila"},
            {"417", "Burak"}, {"418", "Berke"}, {"419", "Mehmet"}, {"420", "Murat"},
            {"421", "Recep"}
        };

        for (String[] student : students1) {

            SqlConnect.insertMath(student[0], student[1], "P", "0", "0");

            SqlConnect.insertDifferantial(student[0], student[1], "P", "0", "0");

            SqlConnect.insertPrograming(student[0], student[1], "P", "0", "0");

            SqlConnect.insertNumeric(student[0], student[1], "P", "0", "0");

            SqlConnect.insertLinear(student[0], student[1], "P", "0", "0");
        }
        for (String[] student : students2) {

            SqlConnect.insertMath(student[0], student[1], "1", "0", "0");

            SqlConnect.insertDifferantial(student[0], student[1], "1", "0", "0");

            SqlConnect.insertPrograming(student[0], student[1], "1", "0", "0");

            SqlConnect.insertNumeric(student[0], student[1], "1", "0", "0");

            SqlConnect.insertLinear(student[0], student[1], "1", "0", "0");
        }
        for (String[] student : students3) {

            SqlConnect.insertMath(student[0], student[1], "2", "0", "0");

            SqlConnect.insertDifferantial(student[0], student[1], "2", "0", "0");

            SqlConnect.insertPrograming(student[0], student[1], "2", "0", "0");

            SqlConnect.insertNumeric(student[0], student[1], "2", "0", "0");

            SqlConnect.insertLinear(student[0], student[1], "2", "0", "0");
        }
        for (String[] student : students4) {

            SqlConnect.insertMath(student[0], student[1], "3", "0", "0");

            SqlConnect.insertDifferantial(student[0], student[1], "3", "0", "0");

            SqlConnect.insertPrograming(student[0], student[1], "3", "0", "0");

            SqlConnect.insertNumeric(student[0], student[1], "3", "0", "0");

            SqlConnect.insertLinear(student[0], student[1], "3", "0", "0");
        }
        for (String[] student : students5) {

            SqlConnect.insertMath(student[0], student[1], "4", "0", "0");

            SqlConnect.insertDifferantial(student[0], student[1], "4", "0", "0");

            SqlConnect.insertPrograming(student[0], student[1], "4", "0", "0");

            SqlConnect.insertNumeric(student[0], student[1], "4", "0", "0");

            SqlConnect.insertLinear(student[0], student[1], "4", "0", "0");
        }

    }

}
