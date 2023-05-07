package view;

public class UserInfo {
    private static String username;
    private static char[] password = new char[20];

    public  static void setUsername(String username1) {
        username = username1;
    }

    public static void setPassword(char[] password1) {
        if(password1.length>20){
            System.out.println("invalid password, error: more than 20 characters ");
        }else{
            for(int i=0;i<password1.length;i++){
                password[i]=password1[i];
        }
        }
    }

    public static String getUsername(){
        return username;
    }

    public static char[] getPassword() {
        return password;
    }

    public static boolean isPasswordCorrect(char[] in){
        boolean sign = true;
        char a[] = getPassword();
        for (int i=0;i< getPassword().length;i++) {
            if (a[i]!='\u0000') {
                sign= a[i]==in[i];
            }
            }
        return sign;
    }
}
