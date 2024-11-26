package nodawoon.me_to_you.global.slack;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Color {
    RED("#ff0000"),
    GREEN("#00ff00")
    ;

    private final String code;
}
