package ec.gob.gadma.obj;

/**
 * Created by titecnico11 on 06/09/2017.
 */

public class ObjParameters {
    private static ObjParameters mInstance= null;

    private String NAME_SID_SDE;
    private String NAME_USER_SDE;

    public static ObjParameters getmInstance() {
        return mInstance;
    }

    public static void setmInstance(ObjParameters mInstance) {
        ObjParameters.mInstance = mInstance;
    }

    public String getNAME_SID_SDE() {
        return NAME_SID_SDE;
    }

    public void setNAME_SID_SDE(String NAME_SID_SDE) {
        this.NAME_SID_SDE = NAME_SID_SDE;
    }

    public String getNAME_USER_SDE() {
        return NAME_USER_SDE;
    }

    public void setNAME_USER_SDE(String NAME_USER_SDE) {
        this.NAME_USER_SDE = NAME_USER_SDE;
    }

    public String getPASSWORD_USER_SDE() {
        return PASSWORD_USER_SDE;
    }

    public void setPASSWORD_USER_SDE(String PASSWORD_USER_SDE) {
        this.PASSWORD_USER_SDE = PASSWORD_USER_SDE;
    }

    public String getPORT_SDE() {
        return PORT_SDE;
    }

    public void setPORT_SDE(String PORT_SDE) {
        this.PORT_SDE = PORT_SDE;
    }

    public String getSERVER_SDE() {
        return SERVER_SDE;
    }

    public void setSERVER_SDE(String SERVER_SDE) {
        this.SERVER_SDE = SERVER_SDE;
    }

    private String PASSWORD_USER_SDE;
    private String PORT_SDE;
    private String SERVER_SDE;

    protected ObjParameters(){}

    public static synchronized ObjParameters getInstance(){
        if(null == mInstance){
            mInstance = new ObjParameters();
        }
        return mInstance;
    }

}
