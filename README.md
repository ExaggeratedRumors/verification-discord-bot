# Verify Discord Bot

Manual-verification Discord Bot based on Discord API.

Site and chanell utils need to be fill in Constant class.

Bot generates code and asks new user user for an address of predefined website he put the code and assign him special role and nickname.


## Discord commands
```
!new      - generates new key

!verify   - attempt to verify the account
```

## Attributes to fill
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
