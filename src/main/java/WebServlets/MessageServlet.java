package WebServlets;

import Commons.Message.ChallengeMessage;
import Commons.Message.FriendRequestMessage;
import Commons.Message.MessageService;
import Commons.Message.NoteMessage;

import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MessageServlet extends HttpServlet {

    private final static String DatabaseName = "quiz_test";
    private MessageService messageService;
    @Override
    public void init() throws ServletException {
        super.init();
        messageService = new MessageService(DatabaseName);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String messageType = httpServletRequest.getParameter("messageType");

        switch (messageType){
            case "FriendRequest":
                handleFriendRequestMessage(httpServletRequest,httpServletResponse);
                break;
            case "NoteMessage":
                handleNoteMessage(httpServletRequest,httpServletResponse);
                break;
            case "ChallengeMessage":
                handleChallengeMessage(httpServletRequest,httpServletResponse);
                break;
            default:
                httpServletResponse.getWriter().write("Invalid message type.");
                break;
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String messageType = request.getParameter("messageType");
        String userId = request.getParameter("userId");

        int userIdInt = Integer.parseInt(userId);

        switch (messageType) {
            case "friendRequest":
                handleGetFriendRequests(userIdInt, response);
                break;
            case "challenge":
                handleGetChallenges(userIdInt, response);
                break;
            case "note":
                handleGetNotes(userIdInt, response);
                break;
            default:
                response.getWriter().write("Invalid message type.");
                break;
        }
    }


    private void handleGetFriendRequests(int userId, HttpServletResponse response) throws IOException {
        // Get user's friend requests using MessageService
        List<FriendRequestMessage> friendRequests = messageService.getUserFriendRequestMessages(userId);
        if (friendRequests != null) {
            //TODO Handle retrieved friend requests (e.g., display them, format etc.)
        } else {
            response.getWriter().write("Failed to retrieve friend requests. Please try again.");
        }
    }

    private void handleGetChallenges(int userId, HttpServletResponse response) throws IOException {
        // Get user's challenges using MessageService
        List<ChallengeMessage> challenges = messageService.getUserChallengeMessages(userId);

        if (challenges != null) {
            //TODO Handle retrieved friend requests (e.g., display them, format etc.)
        } else {
            response.getWriter().write("Failed to retrieve challenges. Please try again.");
        }
    }

    private void handleGetNotes(int userId, HttpServletResponse response) throws IOException {
        // Get user's notes using MessageService
        List<NoteMessage> notes = messageService.getUserNoteMessages(userId);

        if (notes != null) {
            //TODO Handle retrieved friend requests (e.g., display them, format etc.)
        } else {
            response.getWriter().write("Failed to retrieve notes. Please try again.");
        }
    }



    private void handleChallengeMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String senderId = request.getParameter("senderId");
        String recipientId = request.getParameter("recipientId");
        String quizLink = request.getParameter("quizLink");
        String bestScore = request.getParameter("bestScore");

        int senderIdInt = Integer.parseInt(senderId);
        int recipientIdInt = Integer.parseInt(recipientId);
        int bestScoreInt = Integer.parseInt(bestScore);

        ChallengeMessage message = new ChallengeMessage(0, senderIdInt, recipientIdInt, new Timestamp(System.currentTimeMillis()), quizLink,bestScoreInt);

        boolean sentSuccessfully = messageService.sendMessage(message);

        if (sentSuccessfully) {
            response.getWriter().write("Challenge sent successfully!");
        } else {
            response.getWriter().write("Failed to send challenge. Please try again.");
        }
    }
    private void handleNoteMessage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String senderId = httpServletRequest.getParameter("senderId");
        String recipientId = httpServletRequest.getParameter("recipientId");
        String noteContent = httpServletRequest.getParameter("noteContent");

        int senderIdInt = Integer.parseInt(senderId);
        int recipientIdInt = Integer.parseInt(recipientId);

        NoteMessage message = new NoteMessage(0,senderIdInt,recipientIdInt,new Timestamp(System.currentTimeMillis()),noteContent);

        boolean noteMessageSentSuccessfully =messageService.sendMessage(message);
        if(noteMessageSentSuccessfully){
            httpServletResponse.getWriter().write("Note sent successfully");
        }else{
            httpServletResponse.getWriter().write("Failed to send note, please try again");
        }

    }

    private void handleFriendRequestMessage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String senderId = httpServletRequest.getParameter("senderId");
        String recipientId = httpServletRequest.getParameter("recipientId");
        String friendRequestStatus = httpServletRequest.getParameter("friendRequestStatus");

        int senderIdInt = Integer.parseInt(senderId);
        int recipientIdInt = Integer.parseInt(recipientId);

        FriendRequestMessage message = new FriendRequestMessage(0,senderIdInt,recipientIdInt,new Timestamp(System.currentTimeMillis()),friendRequestStatus);
        boolean friendRequestSentSuccessfully=messageService.sendMessage(message);
        if(friendRequestSentSuccessfully){
            httpServletResponse.getWriter().write("Friend request sent successfully");
        }else{
            httpServletResponse.getWriter().write("Failed to send friend request, please try again");
        }


    }
}
