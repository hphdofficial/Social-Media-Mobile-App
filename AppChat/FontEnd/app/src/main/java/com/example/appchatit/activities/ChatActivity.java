package com.example.appchatit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatit.R;
import com.example.appchatit.adapters.ChatAdapter;
import com.example.appchatit.models.UserModel;
import com.example.appchatit.network.ApiServiceProvider;
import com.example.appchatit.services.ChatApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private ChatAdapter chatAdapter;
    private RecyclerView recyclerView;
    private List<UserModel> userModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listchat);
        setupRecyclerView();
//        setupBottomNavigationView();

        ImageView btnCreateChat = findViewById(R.id.btn_create_chat);
        btnCreateChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.list_chat);
        chatAdapter = new ChatAdapter(this, userModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);
        loadListChat();
        loadListMessOrtherUser();
    }

//    private void setupBottomNavigationView() {
//        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.nav_item_chat) {
//                return true;
//            } else if (itemId == R.id.nav_item_group) {
//                Intent intent = new Intent(ChatActivity.this, CreateChatActivity.class);
//                startActivity(intent);
//                return true;
//            } else {
//                return false;
//            }
//        });
//    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_create, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_item_create_chat) {
                startActivity(new Intent(ChatActivity.this, CreateChatActivity.class));
                return true;
            } else if (itemId == R.id.nav_item_create_group) {
                startActivity(new Intent(ChatActivity.this, CreateGroupActivity.class));
                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }

    private void loadListChat() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("accessToken", "");
        String userId = prefs.getString("userId", "");
        ChatApiService service = ApiServiceProvider.getChatApiService();
        Call<List<UserModel>> call = service.getChat("Bearer " + token, userId);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    List<UserModel> userList = response.body();
                    chatAdapter.setData(userList);
                } else {
                    Toast.makeText(ChatActivity.this, "Failed to fetch users: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error fetching users: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadListMessOrtherUser() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("accessToken", "");
        String userId = prefs.getString("userId", "");
        ChatApiService service = ApiServiceProvider.getChatApiService();
        Call<List<UserModel>> call = service.getListMessOtherUser("Bearer " + token, userId);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    chatAdapter.updateMessOrtherUser(response.body());
                } else {
                    Toast.makeText(ChatActivity.this, "Failed to fetch users: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error fetching users: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}