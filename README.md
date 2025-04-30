# RedisAuthentication

authentication implementation:
- uses jwt and spring authentication, saves session in Redis.
- to run: set up DB, use password, set up redis -> enter port.
- since the properties file already has a ddl-auto setting, the users DB will be made in the selected schema automatically.
