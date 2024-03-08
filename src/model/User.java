package model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Integer user_id;
    private Boolean is_deleted;
    private String user_name;
    private String user_email;
    private String user_password;
    private String user_uuid;
    private Boolean is_verified;
}
