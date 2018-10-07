package Objects;


public class Customer {
    private static int customerCount;
    private int serviceTime;
    private int arrivalTime;
    private int customerId;
    private int waitingTime;
    private int queueNumber;

    public Customer(int serviceTime, int arrivalTime){
        this.serviceTime = serviceTime;
        this.arrivalTime = arrivalTime;
        this.customerId = ++customerCount;
    }

    public int getCustomerId(){
        return customerId;
    }

    public int getServiceTime(){
        return serviceTime;
    }

    public int getWaitingTime(){
        return waitingTime + serviceTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void decreaseWaitingTime(){
        waitingTime--;
    }

    public int getQueueNr(){
        return queueNumber;
    }

    public void setQueueNr(int queueNumber){
        this.queueNumber = queueNumber;
    }

    public void decreaseServiceTime(){
        serviceTime--;
    }

    public boolean isZero(){
        return serviceTime <= 0;
    }


    public String customerInfo(){
        return "Client nou ... ID:" + customerId + " timp servire:" + serviceTime + " timpul sosirii:" + arrivalTime + " timp de stat la coada:" + this.getWaitingTime();
    }

}
