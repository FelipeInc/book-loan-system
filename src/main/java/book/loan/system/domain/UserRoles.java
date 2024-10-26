package book.loan.system.domain;

public enum BookLoanUserRoles {
    ADMIN("admin"),

    USER("user");

    private final String role;

    BookLoanUserRoles(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
