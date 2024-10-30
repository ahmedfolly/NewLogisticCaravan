package com.example.logisticcavan.chatting.presentaion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.R;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentChattingBinding;

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

        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         chatId = ChattingFragmentArgs.fromBundle(getArguments()).getOrderId();
         name = ChattingFragmentArgs.fromBundle(getArguments()).getName();
         binding.name.setText(name);
        viewModel.getMessages(chatId).whenComplete((messages, throwable) -> {
            if (throwable != null) {
                showError(binding.getRoot(), throwable.getMessage());
            } else {
//                adapter = new ChattingAdapter(messages);
//                binding.recyclerView.setAdapter(adapter);
      }
    });
}
}