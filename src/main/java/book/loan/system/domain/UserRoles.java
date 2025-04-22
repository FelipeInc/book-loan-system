package book.loan.system.domain;


import lombok.Getter;

@Getter
public enum UserRoles {
    ADMIN("admin"),

    USER("user");

    private final String role;

    UserRoles(String role){
        this.role = role;
    }

}
