package LittlePet.UMC.domain.enums;

public enum RoleStatus {
    //USER, ADMIN

    ADMIN("Admin"),
    USER("User");

    private final String role;

    RoleStatus(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static RoleStatus fromString(String role) {
        for (RoleStatus value : RoleStatus.values()) {
            if (value.role.equalsIgnoreCase(role)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + role);
    }
}
