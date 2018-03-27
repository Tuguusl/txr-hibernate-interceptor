# ec-hibernate-interceptor

USAGE:

User.java
```java
@Entity(name = "User")
@Table(name = "User")
@WithListener(UserListener.class)
public class User extends ListenedEntity {
  private long id;
  private string name;
}
```
