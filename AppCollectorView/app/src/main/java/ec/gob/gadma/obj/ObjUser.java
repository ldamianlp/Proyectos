package ec.gob.gadma.obj;

/**
 * Created by titecnico11 on 06/09/2017.
 */

public class ObjUser {
    private static ObjUser mInstance= null;

    private String userConnect;
    private String userPassword;

    protected ObjUser(){}

    public static synchronized ObjUser getInstance(){
        if(null == mInstance){
            mInstance = new ObjUser();
        }
        return mInstance;
    }
    public String getUserConnect() {
        return userConnect;
    }

    public void setUserConnect(String userConnect) {
        this.userConnect = userConnect;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
