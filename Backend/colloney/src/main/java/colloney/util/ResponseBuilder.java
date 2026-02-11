package colloney.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

public class ResponseBuilder {
    public static <T, R> ApiResponse<List<R>> buildListResponse(String message, List<T> entityList,
            Function<T, R> mapper) {
        List<R> responseList = new ArrayList<>();
        for (T entity : entityList) {
            responseList.add(mapper.apply(entity));
        }
        return ApiResponse.ok(message, responseList);
    }

    public static <T, R> ApiResponse<List<R>> buildListResponse(String message, Page<T> page, Function<T, R> mapper) {
        List<R> responseList = new ArrayList<>();
        for (T entity : page.getContent()) {
            responseList.add(mapper.apply(entity));
        }
        return ApiResponse.paginated(message, responseList, page.getNumber(), page.getSize(), page.getTotalElements(),
                page.getTotalPages());
    }
}
