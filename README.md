# User Details Handling

## Summary

This document outlines the process for handling user details within the application, focusing on security considerations and the implementation of user authentication.

## Security Configuration

- The security configuration involves retrieving user details from the database.
- To achieve this, the authentication provider needs to be modified to connect to the database.
- Utilize `DaoAuthenticationProvider` for database access.
- Specify a `UserDetailsService`, implemented separately as `MyUserDetailsService` using `implements UserDetailsService`.
- Implement repository (`Repo`) in `MyUserDetailsService`, including a `findByUsername` method.
- Call the `findByUsername` method in `MyUserDetailsService`.
- Return the user if found, otherwise throw an exception.
- Create a `UserPrincipal` class that implements `UserDetails` to represent the current user.
- `UserPrincipal` includes methods like `get` and `isAccountExpired`.
- Utilize a `User` table class annotated with `@Data` and `@Entity`, passing it to `UserPrincipal`.
- Secure passwords stored as plaintext in the PostgreSQL database.

## Password Security

- Various methods for password security include cryptography (encrypt-decrypt) and hashing (one-way decryption) such as MD5 and SHA256.
- Implement Bcrypt password encoder for hashing.
- Update the authentication provider in the Security config file with `provider.setPasswordEncoder(new BCryptPasswordEncoder(12));`.

## User Registration

- Add a user service and user controller with a mapping to `/register`.
- Implement Bcrypt for storing new user passwords.
- Authentication of passwords for new users.

## Usage

- Send a POST request to `/register` with JSON data of the user.
- The password is encoded and added to the database.
- Newly registered users can authenticate with their passwords, validated by the configured password encoder in the Security config file.
