package spring.taxi.app.user.util;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private List<String> errors;
    private LocalDateTime timestamp;

    public ErrorResponse(List<String> errors, LocalDateTime timestamp) {
        this.errors = errors;
        this.timestamp = timestamp;
    }
}
