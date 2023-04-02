import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class VerifyConstraints {

    private final ArrayList<Member> userToVerifyList;

    VerifyConstraints(){
        userToVerifyList = new ArrayList<>();
    }

    private ArrayList<Member> getUserToVerifyList() {
        return userToVerifyList;
    }

    public void genNewUserKey(GuildMemberJoinEvent event){
        try{
            TextChannel textChannel = event.getGuild().getTextChannelsByName(Utils.logChannel,true).get(0);
            String encodedKey = createKey();
            textChannel.sendMessage("Cześć <@!"+event.getMember().getUser().getId()+ Utils.newMemberMessage+"\n```fix\n"+encodedKey+"\n``` \n").queue();
            userToVerifyList.removeIf(obj -> obj.getId().equals(event.getMember().getUser().getId()));
            userToVerifyList.add(new Member(event.getMember().getUser().getId(),encodedKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void genNewKey(MessageReceivedEvent event){
        try {
            boolean isKeyCreated = false;
            String encodedKey = createKey();
            for (Member user : this.getUserToVerifyList()) {
                if (user.getId().equals(event.getMember().getUser().getId())) {
                    user.setKey(encodedKey);
                    isKeyCreated = true;
                }
            }
            if(!isKeyCreated) userToVerifyList.add(new Member(event.getMember().getUser().getId(),encodedKey));
            event.getChannel().sendMessage(Utils.newKeyMessage+"\n```fix\n"+encodedKey+"\n```\n").queue();

        }catch (Exception ignored){}
    }

    private String createKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(Utils.hashInstance);
            kg.init(256);
            SecretKey key = kg.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded()).substring(0 , 10);
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }

    public void verify(MessageReceivedEvent event, RumorsBot api){
        String url = event.getMessage().getContentRaw().substring(7,event.getMessage().getContentRaw().length());
        try {
            URL service = new URL(url);
            URLConnection urlConnection = service.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[100];
            StringBuilder sbd = new StringBuilder();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sbd.append(charArray, 0, numCharsRead);
            }

            if(compareKey(event, sbd) && compareDomain(url,event)) {
                assignRole(event,api,sbd);
                event.getChannel().sendMessage(Utils.successMessage).queue();
            }
            else throw(new Exception());
        } catch (Exception e) {
            e.printStackTrace();
            event.getChannel().sendMessage(Utils.errorMessage).queue();
        }
    }

    private boolean compareDomain(String url, MessageReceivedEvent event){
        event.getChannel().sendMessage(url).queue();
        return url.contains(Utils.siteContains);
    }

    private boolean compareKey(MessageReceivedEvent event, StringBuilder sbd){
        String key = "";
        for(Member obj : this.getUserToVerifyList()) {
            if(obj.getId().equals(event.getMember().getUser().getId())){
                key = obj.getKey();
            }
        }
        event.getChannel().sendMessage(sbd.toString().substring(0,1500)).queue();
        return sbd.toString().contains(key);
    }

    private void assignRole(MessageReceivedEvent event, RumorsBot api, StringBuilder sbd){
        String result = sbd.toString();
        Scanner scanner = new Scanner(result);
        String responseBody = scanner.useDelimiter(Utils.delimiter).next();
        GuildController gc = new GuildController(event.getGuild());
        gc.addSingleRoleToMember(event.getMember() , api.getJDA().getRolesByName(Utils.roleName,true).get(0)).queue();
        gc.setNickname(event.getMember() , responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf(Utils.siteTitle))).queue();
        userToVerifyList.removeIf(user -> user.getId().equals(event.getMember().getUser().getId()));
    }
}
