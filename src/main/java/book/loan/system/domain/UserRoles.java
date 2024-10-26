package book.loan.system.domain;

public enum UserRoles {
    ADMIN("admin"),

    USER("user");

    private final String role;

    UserRoles(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
