package com.example.appchatit.services;

import com.example.appchatit.models.ChatModel;
import com.example.appchatit.models.DetailsChatModel;
import com.example.appchatit.models.GroupChatModel;
import com.example.appchatit.models.GroupMemberModel;
import com.example.appchatit.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatApiService {
    @GET("get-listMessOrtherUser")
    Call<List<UserModel>> getListMessOtherUser(
            @Header("Authorization") String token,
            @Query("userId") String userId
    );

    @GET("get-chat")
    Call<List<UserModel>> getChat(
            @Header("Authorization") String token,
            @Query("userId") String userId
    );

    @GET("get-listDetailsChat")
    Call<List<DetailsChatModel>> getListDetailsChat(
            @Header("Authorization") String token,
            @Query("chatId") String chatId
    );

    @POST("create-message")
    Call<DetailsChatModel> createMessage(
            @Header("Authorization") String token,
            @Body DetailsChatModel detailsChatModel
    );

    @POST("create-chat-message")
    Call<ChatModel> createChat(
            @Header("Authorization") String token,
            @Body ChatModel chatModel
    );

    @POST("create-group-chat-message")
    Call<GroupChatModel> createGroupChat(
            @Header("Authorization") String token,
            @Body GroupChatModel groupChatModel
    );

    @POST("add-member-group-chat")
    Call<GroupMemberModel> addMemberGroupChat(
            @Header("Authorization") String token,
            @Body GroupMemberModel groupMemberModel
    );
}