package vu.travelapp.networks.comment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

/**
 * Created by trongphuong1011 on 8/21/2017.
 */

public interface CommentService {
    @PUT("comment")
    Call<ResponseCommentJSON> commentService(@Body RequestCommentJSON requestCommentJSON);
}
