# Verification Discord Bot

Manual-verification Discord Bot based on Discord API.

Site and channels utils need to be fill in Utils class.

Bot generates code and asks new user for an address of predefined website he put the code and assign him special role and nickname.


## Requirements

- JDA-3.5.1_339 library (attached to repo)


## Application execution

1. Make sure your JAVA_HOME paths to jdk directory.
2. Clone repository:
```
git clone https://github.com/ExaggeratedRumors/verification-discord-bot.git
```
3. Fill attributes by your server's parameters:
```
token             - bot's token
roleName          - name of the role new user will be assigned
logChannel        - name of the channel the announcements about new users will be placed
siteTitle         - title of a target website
siteContains      - website address
errorMessage      - error exception message
successMessage    - success message
newMemberMessage  - message after new user join the server
newKeyMessage     - message after use !new command
```
4. Open application root directory and put commands:
```
javac -cp lib/JDA-3.5.1_339-javadoc.jar;lib/JDA-3.5.1_339-withDependencies.jar src/*.java
java -cp lib/JDA-3.5.1_339-javadoc.jar;lib/JDA-3.5.1_339-withDependencies.jar; Main
```
5. Use services commands in your Discord server:
```
!new      - generates new key

!verify   - attempt to verify the account
```