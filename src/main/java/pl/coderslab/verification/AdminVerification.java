package pl.coderslab.verification;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import java.util.Map;

public class AdminVerification {

    public static boolean adminVerification(Admin admin, String password) {
        if (admin != null) {
            if (BCrypt.checkpw(password, admin.getPassword()) == true) {
                if (admin.getEnable() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean verifyEmail(String email) {
        String emailRegex = "^.+@.+\\.[a-z]{2,3}$";
        return email.matches(emailRegex);
    }

    public static boolean verifyParameters(Map mapParam) {
        Map<String, String[]> newMap = mapParam;
        for (Map.Entry<String, String[]> entry : newMap.entrySet()) {
            if (entry.getValue()[0].equals("")) {
                return false;
            }
        }
        return true;
    }

    public static boolean sameEmail(String email) {
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.readEmail(email);
        if (admin == null) {
            return false;
        }
        return true;
    }
}
