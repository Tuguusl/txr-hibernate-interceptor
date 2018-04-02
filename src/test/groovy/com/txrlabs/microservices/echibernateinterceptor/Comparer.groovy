package com.txrlabs.microservices.echibernateinterceptor

import com.txrlabs.microservices.echibernateinterceptor.test.classes.FrontEnd
import com.txrlabs.microservices.echibernateinterceptor.test.classes.Group
import com.txrlabs.microservices.echibernateinterceptor.test.classes.User
import org.apache.commons.lang.SerializationUtils
import spock.lang.Specification

class Comparer extends Specification{


    def setup(){

    }

    def "same objects"(){
        given: "a current user"
            User current = new User("name","last",18,1.50)
        and: "a previous state"
            User old = new User("name","last",18,1.50)
            current._embedded.previousStatus = old
        when: "call get differences"
            List<String> differences = current._embedded.getPreviousChange(current)
        then: "must be equal"
            differences.size() == 0
    }

    def "same object class, different values"(){
        given: "a current user"
            User current = new User("name","last",18,1.50)
        and: "a previous state"
            User old = new User("name2","last2",20,1.51)
            current._embedded.previousStatus = old
        when: "call get differences"
            List<String> differences = current._embedded.getPreviousChange(current)
        then: "must be equal"
            differences.containsAll(["name","lastName","age","salary"])
        and:
            ["name","lastName","age","salary"].containsAll(differences)
    }

    def "different object class"(){
        given: "a current user"
            User current = new User("name","last",18,1.50)
        and: "a previous state"
            FrontEnd old = new FrontEnd("name","last2",false)
            current._embedded.previousStatus = old
        when: "call get differences"
            List<String> differences = current._embedded.getPreviousChange(current)
        then: "must be equal"
            differences.containsAll(["lastName","age","salary","admin"])
        and:
            ["lastName","age","salary","admin"].containsAll(differences)
    }

    def "complex property object"(){
        given: "a current group"
            Group current = new Group(
                    [1,2],
                    [new User("n","l",0,0.0),new User("n1","l1",1,0.1)],
                    new User("n2","l2",2,0.2)
            )
        and: "a previous state"
            Group old = (Group) SerializationUtils.clone(current)
            old.departments = [2,1]
            current._embedded.previousStatus = old
        when: "call get differences"
            List<String> differences = current._embedded.getPreviousChange(current)
        then: "must be equal"
            differences.containsAll(["departments"])

    }
    def "complex property object different"(){
        given: "a current group"
            Group current = new Group(
                    [1,2],
                    [new User("n","l",0,0.0),new User("n1","l1",1,0.1)],
                    new User("n2","l2",2,0.2)
            )
        and: "a previous state"
            Group old = (Group) SerializationUtils.clone(current)
            old.users[0].age += 1
            current._embedded.previousStatus = old
        when: "call get differences"
            List<String> differences = current._embedded.getPreviousChange(current)
        then: "must be equal"
            differences.size() == 1
            differences.contains("users")
    }


}
