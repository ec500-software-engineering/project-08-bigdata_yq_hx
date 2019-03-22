package producer;
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class productLog {
    private int users = 50;
    private String startTime = "2018-01-01";
    private String endTime = "2018-12-31";

    // Produce random phone number
    private List<String> phoneList = new ArrayList<String>();
    private Map<String,String> phoneMap = new HashMap<String, String>();

    public String randomBuildTime(String startTime, String endTime) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf1.parse(startTime);
            Date endDate = sdf1.parse(endTime);

            if (endDate.getTime() <= startDate.getTime()) return null;

            long randomTS = startDate.getTime() + (long) ((endDate.getTime() - startDate.getTime()) * Math.random());
            Date resultDate = new Date(randomTS);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String resultTimeString = sdf2.format(resultDate);
            return resultTimeString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    // sadasddsac

    private void initPhone() {
        for(int i = 0; i < users; i++) {
            String phoneNumber = "617";
            Random random = new Random();
            for (int j = 0; j < 7; j++) {
                phoneNumber += random.nextInt(9);
            }
            String name = createRandomName.generateName();
            phoneList.add(phoneNumber);
            phoneMap.put(phoneNumber,name);
        }
    }

    public String product() {
        String caller = null;
        String callee = null;

        String callerName = null;
        String calleeName = null;

        //get caller
        int callerIndex = (int) (Math.random() * phoneList.size());
        caller = phoneList.get(callerIndex);
        callerName = phoneMap.get(caller);
        while (true) {
            //get callee
            int calleeIndex = (int) (Math.random() * phoneList.size());
            callee = phoneList.get(calleeIndex);
            calleeName = phoneMap.get(callee);
            if (!caller.equals(callee)) break;
        }

        String buildTime = randomBuildTime(startTime, endTime);
        //0000
        DecimalFormat df = new DecimalFormat("0000");
        String duration = df.format((int) (30 * 60 * Math.random()));
        StringBuilder sb = new StringBuilder();
        sb.append(caller + ",").append(callee + ",").append(buildTime + ",").append(duration);
        return sb.toString();
    }

    public void writeLog(String filePath) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
            while (true) {
                Thread.sleep(500);
                String log = product();
                System.out.println(log);
                osw.write(log + "\n");
                //ensure very data stream to file
                osw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        /*
        if (args == null || args.length <= 0) {
            System.out.println("no arguments");
            return;
        }
        */
        productLog productLog = new productLog();
        productLog.initPhone();
        productLog.writeLog("logs.csv");
    }
}

    



