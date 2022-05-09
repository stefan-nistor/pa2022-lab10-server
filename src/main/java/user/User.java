package user;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private String name;
    private List<User> friends = new ArrayList<>();
    private Map<User, String> messages = new HashMap<>();
}
