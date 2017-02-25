package datapole.dbmsdhruvrathi.model;

/**
 * Created by jatin on 25/2/17.
 */
public class Playlist {
    int pid;
    String name;

    public Playlist(int pid, String name) {
        this.pid = pid;
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
