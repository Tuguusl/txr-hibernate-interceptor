
# ec-hibernate-interceptor

###  Implementation:

```
User.java
```
```java
@Entity(name = "User")
@Table(name = "User")
@WithListener(UserListener.class)
public class User extends ListenedEntity {
  private long id;
  private string name;
}
```

```
UserListener.java
```
```java
@Component  
public class UserListener implements HibernateListener {  
    @Override  
	public void onCreate(ListenedEntity entity) {  
    }  
    @Override  
	public void onDelete(ListenedEntity entity) {  
    }  
    @Override  
	public void onUpdate(ListenedEntity oldEntity, ListenedEntity newEntity) {  
    }  
}
```

