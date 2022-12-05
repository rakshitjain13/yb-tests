import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    Integer id;
    String name;
    Integer age;
    String email;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String emp_email) {
        this.email = emp_email;
    }
}
