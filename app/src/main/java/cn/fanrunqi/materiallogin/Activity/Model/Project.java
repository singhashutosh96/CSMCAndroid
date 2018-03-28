package cn.fanrunqi.materiallogin.Activity.Model;

/**
 * Created by Akanksha on 3/26/2018.
 */

public class Project {

    String pname,pdesc,pmilestones;

    public Project(String pname, String pdesc, String pdate) {
        this.pname = pname;
        this.pdesc = pdesc;
        this.pmilestones = pdate;

    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getPmilestones() {
        return pmilestones;
    }

    public void setPmilestones(String pmilestones) {
        this.pmilestones = pmilestones;
    }
}
