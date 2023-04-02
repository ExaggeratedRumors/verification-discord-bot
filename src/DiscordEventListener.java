import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DiscordEventListener extends ListenerAdapter {

    private final RumorsBot api;
    private final VerifyConstraints vc;

    DiscordEventListener(RumorsBot instance) {
        api = instance;
        vc = new VerifyConstraints();
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String cmd = event.getMessage().toString().substring(event.getMessage().toString().indexOf(":!")+2, event.getMessage().toString().indexOf("("));
        if(cmd.startsWith("verify")) vc.verify(event,api);
        else if(cmd.equals("new")) vc.genNewKey(event);
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if(!event.getMember().getUser().isBot()){
            vc.genNewUserKey(event);
        }
    }


}
