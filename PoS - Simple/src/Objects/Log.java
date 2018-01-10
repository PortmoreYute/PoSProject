package Objects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class Log {
    private String user;
    private Date loggedIn;
    private Date loggedOut;

    public Log() {
        this.user = null;
        this.loggedIn = null;
        this.loggedOut = null;
    }

    public Log(String user, Date loggedIn, Date loggedOut) {
        this.user = user;
        this.loggedIn = loggedIn;
        this.loggedOut = loggedOut;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Date loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Date getLoggedOut() {
        return loggedOut;
    }

    public void setLoggedOut(Date loggedOut) {
        this.loggedOut = loggedOut;
    }

    public void logUser(){
        this.loggedOut = Date.from(Instant.now());
        File logFile = new File("UserLog.txt");
        try {
            FileWriter logFW = new FileWriter(logFile, true);
            BufferedWriter logBW = new BufferedWriter(logFW);
            logBW.write(user + " ");
            logBW.write("LoggedIn: ");
            logBW.write(loggedIn + " ");
            logBW.write("LoggedOut: ");
            logBW.write(loggedOut + " ");
            logBW.newLine();
            logBW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
