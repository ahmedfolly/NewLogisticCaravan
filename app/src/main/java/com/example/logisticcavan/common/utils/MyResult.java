package com.example.logisticcavan.common.utils;

import java.util.function.Consumer;

public abstract class MyResult<T> {
    private MyResult(){}

    public abstract void handle(
            Consumer<T> onSuccess,
            Consumer<Exception> onError,
            Runnable onLoading
    );

    public static final class Success<T> extends MyResult<T>{
        private final T data;
        public Success(T data){
            this.data = data;
        }

        public T getData() {
            return data;
        }

        @Override
        public void handle(Consumer<T> onSuccess, Consumer<Exception> onError, Runnable onLoading) {
            onSuccess.accept(data);
        }
    }
    public static final class Error<T> extends MyResult<T>{
        private final Exception e;

        public Error(Exception e){
            this.e = e;
        }

        public Exception getE() {
            return e;
        }

        @Override
        public void handle(Consumer<T> onSuccess, Consumer<Exception> onError, Runnable onLoading) {
            onError.accept(e);
        }
    }
    public static final class Loading<T> extends MyResult<T>{
        @Override
        public void handle(Consumer<T> onSuccess, Consumer<Exception> onError, Runnable onLoading) {
            onLoading.run();
        }
    }

    public static<T> MyResult<T> success(T data){
        return new Success<>(data);
    }
    public static <T> MyResult<T> error(Exception e){
        return new Error<>(e);
    }
    public static <T> MyResult<T> loading(){
        return new Loading<>();
    }
}
