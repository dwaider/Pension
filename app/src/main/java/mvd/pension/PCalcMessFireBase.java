package mvd.pension;

/**
 * Created by Dmitry on 09.09.2016.
 */
public class PCalcMessFireBase {
    int id;
    private String mMess1;
    private String mMess2;

    public PCalcMessFireBase() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getMess1(){
        return mMess1;
    }

    public void  setMess1(String m){
        mMess1 = m;
    }


    public String getMess2(){
        return mMess2;
    }

    public void  setMess2(String m){
        mMess2 = m;
    }
}
