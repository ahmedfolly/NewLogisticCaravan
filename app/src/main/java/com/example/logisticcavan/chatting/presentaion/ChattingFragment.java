package com.example.logisticcavan.chatting.presentaion;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.logisticcavan.R;
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
    private FragmentChattingBinding binding;
    private ChattingAdapter adapter;
    private String chatId;
    private String name;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chatting, container, false);
        setUpButtonSender();
        setUpClickListeners();
        return  binding.getRoot();
    }

    private void setUpClickListeners() {
        binding.sendButton.setOnClickListener(v -> {
            String text = binding.messageEditText.getText().toString();
            Message message = new Message("text",text);
            sendMessage(message,chatId);

        });
    }

    private void sendMessage(Message message, String chatId) {
        viewModel.sendMessage(message, chatId);

//                .whenComplete((aVoid, throwable) -> {
//            if (throwable != null) {
//                Log.e("TAG", "sendMessage: " + throwable.getMessage());
//                showError(binding.getRoot(), throwable.getMessage());
//            }else {
//                Log.e("TAG", "sendMessage: success");
//            }
//        });
    }


    private void initData() {
        chatId = ChattingFragmentArgs.fromBundle(getArguments()).getOrderId();
        name = ChattingFragmentArgs.fromBundle(getArguments()).getName();
        binding.name.setText(name);
        Log.e("TAG", "onViewCreated: " + chatId + " " + name);
    }

    private void  getMessages(){
    viewModel.getMessages(chatId).whenComplete((messages, throwable) -> {
        if (throwable != null) {
            Log.e("TAG", "  viewModel.getMessages: if  " + throwable.getMessage());
            showError(binding.getRoot(), throwable.getMessage());
        } else {
            Log.e("TAG", "  viewModel.getMessages: else  " + messages.size());
            if (messages.size() == 0) {
                updateUi(false);
            } else {
                setUpRecyclerView(messages);

            }
        }
    });

}
    private void setUpRecyclerView(List<Message> messages) {
//                adapter = new ChattingAdapter(messages);
//                binding.recyclerView.setAdapter(adapter);
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
        initData();
        getMessages();

    }
}