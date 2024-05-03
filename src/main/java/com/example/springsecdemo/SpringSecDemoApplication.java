package com.example.springsecdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecDemoApplication.class, args);
    }

}
/*
* summary til handling user details
*
* * security config is for getting from database
* to achieve that we need to change authprovider
* to do that we have to connect to database
* we need to use daoauthprovider, cause its our database access
* then to achieve that we need to specify userdetailsservice
* we need to create that seperately (myuserdetailsservice) via using "implements userdetailsservice"
* and we need repo in myuserrdetailsservice. in repo we have findbyusername method
* and we are calling that method in myuserrdetailsservice
* if found we return user if not throw exception
* but we cant simply return user we need to create UserPrincipal which
* implements UserDetails. userprincipal means current user
* userprinncipal has get and and isAccount expired methods and more
* we also have a user table @data @entity class which we pass this to userprincipal
* after this we will secure the password which is plaintext in postgresql database
*
* we can use cryptography (encrypt - decrypt), hashing (one way decryption) (MD5,SHA256)
* To do hashing we will use Bcrypt password encoder
* added user service and user controller with mapping /register
* implemented Bcrypt first we used bcryt for storing new user; how to verify tho?
* authentication?
* changed Security config file with this line in authprovider method : provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
* now we can send a post request to /register with json data of user
* and its get encoded and added to the database
* after that, new registered user can enter with the password which is authenticated by arranging passwordencoder in security config file
*
*
*
*
* */