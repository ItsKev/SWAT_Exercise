package ch.hslu.appe.fbs.model.db;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "grp05")
public class User {
    private Integer id;
    private Integer userRole;
    private String userName;
    private String password;
    private Byte deleted;
    private Collection<Order> ordersById;
    private UserRole userRoleByUserRole;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userRole")
    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    @Basic
    @Column(name = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "deleted")
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(userRole, user.userRole) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(deleted, user.deleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userRole, userName, password, deleted);
    }

    @OneToMany(mappedBy = "userByUserId", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    public Collection<Order> getOrdersById() {
        return ordersById;
    }

    public void setOrdersById(Collection<Order> ordersById) {
        this.ordersById = ordersById;
    }

    @ManyToOne
    @JoinColumn(name = "userRole", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UserRole getUserRoleByUserRole() {
        return userRoleByUserRole;
    }

    public void setUserRoleByUserRole(UserRole userRoleByUserRole) {
        this.userRoleByUserRole = userRoleByUserRole;
    }
}
