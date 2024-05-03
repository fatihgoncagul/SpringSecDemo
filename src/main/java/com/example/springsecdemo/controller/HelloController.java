package com.example.springsecdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//we want to return json data with this
// we dont want everyone to see this hello world print so
//we will create logging form
//spring security dependency creates a login page for us by default and secures our controllers
// username is user and password is shown in the console


/*
* client sends request to server
* first dispatcher servlet takes it and redirects to controllers
* when we add spring security, layer of security occurs before dispatcher servlet
* and this layer of security has filters
* filter chain concept comes up, it runs one by one in a particular order
*
* if user logged in once, he can go to another page without logging in again since its logged in
* it creates SESSIONID for logged in user everytime logging in
* we can get a hold on to this session id with httpservletrequest.getSession().getId()
* like below
    @GetMapping("hello")
    public String greet(HttpServletRequest request){
        return "Hello World"+ request.getSession().getId();

    }
* we'll look at configuring username and passowrd in application.properties file
*
spring.security.user.name=telusko
spring.security.user.password=1234
* writing password in plaintext is not a good idea
* for the entire app are we going to have one user? no, of course
* we'll do it for multiple user
*
* what if we are building a rest api and dont want to go to a logging form
* we want to send that login form from react application or our test app postman
* how?
* we did basic auth and send get request with postman
* how do we secure our password?
*
* lets say gmail has session id for us so that we dont have to login again
* what if malicious server takes that sessionid? spring security handles it by default. its called csrf.
* one of ways of securing is with every request we get returned a token, next time you submit a request you have to submit that token as well
* CSRF(Cross Site Request Forgery):
* post, put, delete  spring implements csrf for these methods; but not for the get method
* we will create studentcontroller to examine this
* we send POST request to the student controller it said unauthorized error even though we got session id
* so issue is with csrf cause we are not sending it with csrf token
* how to get that token?where to put it
* we were able to access the token through httpservletrequest object with this (CsrfToken) request.getAttribute("_csrf")
* we passed that token to our post request in postman headers
    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){

        return (CsrfToken) request.getAttribute("_csrf");
    }
* this was one way sending that token allows us to send the request post,delete,put
* another way is that not to allow other websites to get hold on to our session id
* we need to specify that malicious website you can use it from the same site
* we can disable csrf in app properties adding following:  : server.servlet.session.cookie.same-site=strict
* no one can acces it from other website
*
* there can be two types of rest api
* stateless and stateful
* in stateful we maintain the session
*
* what if we make it stateless
* most of the time we use rest application they will be stateless
* in that case we dont have to maintain session and care about csrf token
*
*  */

@RestController
public class HelloController {

    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){

        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("hello")
    public String greet(HttpServletRequest request){
        return "Hello World"+ request.getSession().getId();

    }

    @GetMapping("about")
    public String about(HttpServletRequest request){

        return "Telusko" + request.getSession().getId();
    }




}
