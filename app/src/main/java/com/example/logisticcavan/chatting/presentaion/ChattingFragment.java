package com.example.logisticcavan.chatting.presentaion;

import static com.example.logisticcavan.common.utils.Constant.COURIER;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.domain.useCase.GetUserInfoLocallyUseCase;
import com.example.logisticcavan.chatting.domain.Message;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentChattingBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChattingFragment extends BaseFragment {

    @Inject
    public ChattingViewModel viewModel;
    @Inject
    GetUserInfoLocallyUseCase getUserInfoLocallyUseCase;
    private FragmentChattingBinding binding;
    private ChattingAdapter chattingAdapter;
    private String chatId, userType, name, userId;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chatting, container, false);
        setUpButtonSender();
        setUpClickListeners();
        return  binding.getRoot();
    }

    private void setUpClickListeners() {
        binding.iconBack.setOnClickListener(v -> {
            navController.popBackStack();
        });


        binding.sendButton.setOnClickListener(v -> {
            String text = binding.messageEditText.getText().toString();
            Message message = new Message(userId, text);
            sendMessage(message,chatId);

        });
    }


    private void sendMessage(Message message, String chatId) {
        viewModel.sendMessage(message, chatId)
                .whenComplete((aVoid, throwable) -> {
                    if (throwable != null) {
                        showError(binding.getRoot(), throwable.getMessage());
                    } else {
                        binding.messageEditText.setText("");
                        getMessages();
                    }
                });
    }


    private void initData() {
        userId = getUserInfoLocallyUseCase.getUserInfo().getId();
        userType = getUserInfoLocallyUseCase.getUserInfo().getType();
        chatId = ChattingFragmentArgs.fromBundle(getArguments()).getOrderId();
        name = ChattingFragmentArgs.fromBundle(getArguments()).getName();
        binding.name.setText(name);
    }

    private void  getMessages(){
    viewModel.getMessages(chatId).whenComplete((messages, throwable) -> {
        if (throwable != null) {
            showError(binding.getRoot(), throwable.getMessage());
        } else {
            if (messages.isEmpty()) {
                updateUi(false);
            } else {
                setUpRecyclerView(messages);

            }
        }
    });

}
    private void setUpRecyclerView(List<Message> messages) {
        chattingAdapter = new ChattingAdapter(messages, userId);
        binding.recyclerView.setAdapter(chattingAdapter);
        binding.recyclerView.scrollToPosition(messages.size() - 1);
        updateUi(true);
    }

    private void updateUi(boolean isVisible) {
        binding.progressBar.setVisibility(View.GONE);
        if (isVisible) {
            binding.noMessages.setVisibility(View.GONE);
            binding.messages.setVisibility(View.VISIBLE);
        } else {
            binding.noMessages.setVisibility(View.VISIBLE);
            binding.messages.setVisibility(View.GONE);
        }

    }

    private void setUpButtonSender() {
        binding.messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {
                    binding.sendButton.setVisibility(View.VISIBLE);
                } else {
                    binding.sendButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initData();
        changeTextBasedUser();
        getMessages();
    }

    private void changeTextBasedUser() {

        if (userType.equals(COURIER)) {
            binding.title.setText("Your Customer");
            binding.text1.setText("Need to get in touch with your customer?");
            binding.text2.setText("Start conversation with customer about your order");
        }
    }

}