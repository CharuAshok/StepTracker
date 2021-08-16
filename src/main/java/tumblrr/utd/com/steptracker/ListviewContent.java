package tumblrr.utd.com.steptracker;

public class ListviewContent {

    String recordedTime;
    String stepsTaken;
    String distance;
    String timeTaken;

    public ListviewContent(String recordedTime, String stepsTaken, String distance, String feature, String timeTaken) {
        this.recordedTime = recordedTime;
        this.stepsTaken = stepsTaken;
        this.distance = distance;
        this.timeTaken = timeTaken;
    }

    public String getRecordedTime() {
        return recordedTime;
    }

    public String getStepsTaken() {
        return stepsTaken;
    }

    public String getDistance() {
        return distance;
    }

    public String getTimeTaken(){
        return timeTaken;
    }

}
