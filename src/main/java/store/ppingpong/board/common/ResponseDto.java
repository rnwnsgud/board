package store.ppingpong.board.common;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseDto<T> {
    private final int code;
    private final String msg;
    private final T data;

    @Builder
    private ResponseDto(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseDto<T> of(int code, String msg, T data) {
        return ResponseDto.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <T> ResponseDto<T[]> of(int code, String msg) {
        T[] emptyArray = (T[]) new Object[0];
        return ResponseDto.<T[]>builder()
                .code(code)
                .msg(msg)
                .data(emptyArray)
                .build();
    }


}
