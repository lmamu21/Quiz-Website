package Commons;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestManager {
    private FriendRequestDao dao;

    public FriendRequestManager(FriendRequestDao _dao){
        this.dao = _dao;
    }


    public List<FriendRequest> getFriendRequestsTo(String to){
        return dao.getFriendRequestsTo(to);
    }


    public List<FriendRequest> getPendingFriendRequests(String to){
        ArrayList<FriendRequest> requests =(ArrayList<FriendRequest>) dao.getFriendRequestsTo(to);
        ArrayList<FriendRequest> pending = new ArrayList<>();
        for(FriendRequest var : requests){
            if(var.getStatus() == FriendRequest.Status.Pending)
                pending.add(var);
        }
        return pending;
    }

    public FriendRequest.Status sendFriendRequest(String from ,String to){
        FriendRequest request = dao.getFriendRequestsBetween(from,to);
        if(request != null){
            // user has already sent request
            return request.getStatus();
        }
        request = dao.getFriendRequestsBetween(to,from);
        if(request != null){
            // user2 has already sent request - so it automatically accepts
            dao.deleteFriendRequest(request.getRequestId());
            dao.addFriendRequest(new FriendRequest(from,to, FriendRequest.Status.Accepted));
            dao.addFriendRequest(new FriendRequest(to , from, FriendRequest.Status.Accepted));
            return request.getStatus();
        }

        dao.addFriendRequest(new FriendRequest(from,to, FriendRequest.Status.Pending));
        return null;
    }
    public FriendRequest.Status acceptFriendRequest(String from,String to){
        FriendRequest request = dao.getFriendRequestsBetween(to,from);
        if(request == null){
            return FriendRequest.Status.Decline;
        }
        dao.deleteFriendRequest(request.getRequestId());
        dao.addFriendRequest(new FriendRequest(from,to, FriendRequest.Status.Accepted));
        dao.addFriendRequest(new FriendRequest(to,from, FriendRequest.Status.Accepted));
        return FriendRequest.Status.Accepted;
    }

    public void removeFriend(String from ,String to){
        FriendRequest fromto = dao.getFriendRequestsBetween(from,to);
        if(fromto != null)
            dao.deleteFriendRequest(fromto.getRequestId());
        FriendRequest tofrom = dao.getFriendRequestsBetween(to,from);
        if(tofrom != null)
            dao.deleteFriendRequest(tofrom.getRequestId());
    }
}
