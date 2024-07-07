package Commons;

public class FriendRequest {
    private int requestId;
    private String from;
    private String to;
    private Status status;
    public enum Status{
        Accepted,
        Pending,
        Decline
    }
    //for reading from database
    public FriendRequest(int requestId,String from , String to , FriendRequest.Status status){
        this.requestId = requestId;
        this.from = from;
        this.to = to;
        this.status = status;
    }
    //for writing in database
    public FriendRequest(String from , String to , FriendRequest.Status status){

        this.from = from;
        this.to = to;
        this.status = status;
    }

    public FriendRequest.Status getStatus() {
        return status;
    }
    public String getTo() {return to;}

    public String getFrom() {
        return from;
    }
    public int getRequestId(){
        return requestId;
    }

}
